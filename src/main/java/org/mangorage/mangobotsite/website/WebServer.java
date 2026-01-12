package org.mangorage.mangobotsite.website;



import jakarta.servlet.DispatcherType;
import jakarta.servlet.MultipartConfigElement;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.jetbrains.annotations.NotNull;
import org.mangorage.mangobotcore.api.util.log.LogHelper;
import org.mangorage.mangobotsite.Helper;
import org.mangorage.mangobotsite.website.file.FileUploadManager;
import org.mangorage.mangobotsite.website.filters.RequestInterceptorFilter;
import org.mangorage.mangobotsite.website.handlers.DefaultErrorHandler;
import org.mangorage.mangobotsite.website.impl.ObjectMap;
import org.mangorage.mangobotsite.website.servlet.AccountServlet;

import org.mangorage.mangobotsite.website.servlet.EntitiesServlet;
import org.mangorage.mangobotsite.website.servlet.EntityServlet;
import org.mangorage.mangobotsite.website.servlet.FileServlet;
import org.mangorage.mangobotsite.website.servlet.FileUploadServlet;
import org.mangorage.mangobotsite.website.servlet.HomeServlet;
import org.mangorage.mangobotsite.website.servlet.InfoServlet;
import org.mangorage.mangobotsite.website.servlet.LoginServlet;
import org.mangorage.mangobotsite.website.servlet.StreamingServlet;
import org.mangorage.mangobotsite.website.servlet.TestAuthServlet;
import org.mangorage.mangobotsite.website.servlet.TricksServlet;
import org.mangorage.mangobotsite.website.util.ResolveString;
import org.mangorage.mangobotsite.website.util.ServletContextHandlerBuilder;
import org.mangorage.mangobotsite.website.util.WebConstants;

import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Set;

public final class WebServer {
    public static final ResolveString WEBPAGE_INTERNAL = new ResolveString("webpage-internal");
    public static final ResolveString WEBPAGE_ROOT = new ResolveString("webpage-root");
    public static final ResolveString WEBPAGE_PAGE = WEBPAGE_ROOT.resolve("webpage");


    public static void startWebServerSafely(ObjectMap objectMap) {
        new Thread(() -> {
            try {
                FolderPruner.init();
                startWebServer(objectMap);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static void main(String[] args) {
        ObjectMap map = new ObjectMap();
        map.put(WebConstants.FILE_MANAGER, new FileUploadManager(Path.of("webpage-root/uploads")));
        startWebServerSafely(map);
    }

    public static void startWebServer(ObjectMap objectMap) throws Exception {
        Server server = new Server();

        var builder = configureBuilders(objectMap);
        var contextHandler = builder.getServletContextHandler();
        var securityHandler = builder.getConstraintSecurityHandler();

        // Combine the handlers (file, jar resource handlers, and security)
        HandlerCollection handlers = new HandlerCollection();
        handlers.addHandler(configureInternalResourceHandler());
        handlers.addHandler(configureExternalResourceHandler());
        handlers.addHandler(contextHandler);

        securityHandler.setHandler(handlers);
        server.setHandler(securityHandler);


        ServerConnector connector = getServerConnector(server);
        server.addConnector(connector);

        objectMap.put(WebConstants.LOGIN_SERVICE, securityHandler);
        objectMap.put("auth", securityHandler.getAuthenticator());

        server.start();
        LogHelper.info("Webserver Started");
        server.join();
    }

    private static @NotNull ResourceHandler configureInternalResourceHandler() {
        // Create the ResourceHandler for resources in the JAR
        var file = Thread.currentThread().getContextClassLoader().getResource(WEBPAGE_INTERNAL.value());
        if (file == null) throw new RuntimeException("Unable to find resource directory");
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setBaseResource(Resource.newResource(file));

        return resourceHandler;
    }

    private static @NotNull ResourceHandler configureExternalResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(WEBPAGE_PAGE.value());
        return resourceHandler;
    }


    private static @NotNull ServletContextHandlerBuilder configureBuilders(ObjectMap objectMap) {
        var builder = ServletContextHandlerBuilder.create(new ServletContextHandler(ServletContextHandler.SESSIONS));

        builder
                .setContextPath("/")
                .setResourceBase(WEBPAGE_PAGE.value())
                .dynamic(h -> {
                    h.setErrorHandler(new DefaultErrorHandler());
                })

                .addServlet(StreamingServlet.class, "/watch")

                .addHttpServlet(HomeServlet.class, "/home")
                .addHttpServlet(InfoServlet.class, "/info")
                .addHttpServlet(TricksServlet.class, "/trick")
                .addHttpServlet(FileServlet.class, "/file")
                .addHttpServlet(TestAuthServlet.class, "/testAuth")
                .addHttpServlet(LoginServlet.class, "/login")
                .addHttpServlet(AccountServlet.class, "/account")
                .addHttpServlet(FileUploadServlet.class, "/upload", h -> {
                    h.getRegistration().setMultipartConfig(
                            new MultipartConfigElement("/tmp/uploads")
                    );
                })
                .addHttpServlet(EntityServlet.class, "/api/entity/*")
                .addHttpServlet(EntitiesServlet.class, "/api/entities/*")
                .setAttribute(WebConstants.WEB_OBJECT_ID, objectMap)
                .addFilter(RequestInterceptorFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST))
                .configureLoginBuilder(security -> {
                    security
                            .setFullValidate(true)
                            .setAuthenticator(new BasicAuthenticator())
                            .addUser("admin", "pass", Set.of("admin"))
                            .lock(
                                Set.of("admin"),
                                "/testAuth"
                            );
                });

        return builder;
    }

    private static @NotNull ServerConnector getServerConnector(Server server) {
        if (Helper.isDevMode()) {
            SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
            sslContextFactory.setTrustAll(true);

            sslContextFactory.setKeyStorePath(WEBPAGE_ROOT.resolveFully("keystore.jks")); // Path to your keystore
            sslContextFactory.setKeyStorePassword("mango12"); // Keystore password
            sslContextFactory.setKeyManagerPassword("mango12"); // Key manager password

            // HTTPS Connector
            ServerConnector sslConnector = new ServerConnector(
                    server,
                    sslContextFactory
            );

            sslConnector.setPort(1443); // HTTPS port
            return sslConnector;
        } else {
            ServerConnector connector = new ServerConnector(
                    server
            );
            connector.setPort(18080);
            return connector;
        }
    }
}
