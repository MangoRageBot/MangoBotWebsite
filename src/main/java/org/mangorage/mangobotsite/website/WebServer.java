package org.mangorage.mangobotsite.website;

import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;
import org.mangorage.bootstrap.api.logging.IDeferredMangoLogger;
import org.mangorage.bootstrap.api.logging.ILoggerFactory;
import org.mangorage.mangobotsite.website.filters.RequestInterceptorFilter;
import org.mangorage.mangobotsite.website.handlers.DefaultErrorHandler;
import org.mangorage.mangobotsite.website.servlet.CommandsServlet;
import org.mangorage.mangobotsite.website.util.ObjectMap;
import org.mangorage.mangobotsite.website.servlet.HomeServlet;
import org.mangorage.mangobotsite.website.servlet.TricksServlet;
import org.mangorage.mangobotsite.website.util.ResolveString;
import org.mangorage.mangobotsite.website.util.ServletContextHandlerBuilder;
import org.mangorage.mangobotsite.website.util.WebConstants;
import java.util.EnumSet;

public final class WebServer {
    private static final IDeferredMangoLogger LOGGER = ILoggerFactory.getDefault().getWrappedProvider("slf4j", WebServer.class);

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

    public static void startWebServer(ObjectMap objectMap) throws Exception {
        Server server = new Server();

        var builder = configureBuilders(objectMap);
        var contextHandler = builder.getServletContextHandler();

        // Combine the handlers (file, jar resource handlers, and security)
        HandlerCollection handlers = new HandlerCollection();
        handlers.addHandler(configureInternalResourceHandler());
        handlers.addHandler(configureExternalResourceHandler());
        handlers.addHandler(contextHandler);


        ServerConnector connector = getServerConnector(server);
        server.addConnector(connector);
        server.setHandler(handlers);

        server.start();
        LOGGER.get().info("Webserver Started");
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

                .dynamic(handler -> {
                    handler.setErrorHandler(new DefaultErrorHandler());
                })

                .addHttpServlet(HomeServlet.class, "/home")
                .addHttpServlet(TricksServlet.class, "/tricks")
                .addHttpServlet(CommandsServlet.class, "/commands")

                .setAttribute(WebConstants.WEB_OBJECT_ID, objectMap)
                .addFilter(RequestInterceptorFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

        return builder;
    }

    private static @NotNull ServerConnector getServerConnector(Server server) {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(18080);
        return connector;
    }
}
