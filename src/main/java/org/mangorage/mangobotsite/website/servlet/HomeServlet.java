package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotcore.api.plugin.v1.PluginContainer;
import org.mangorage.mangobotcore.api.plugin.v1.PluginManager;
import org.mangorage.mangobotplugin.entrypoint.MangoBot;
import org.mangorage.mangobotsite.website.Header;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.servlet.data.HeaderData;
import org.mangorage.mangobotsite.website.servlet.data.PluginData;
import org.mangorage.mangobotsite.website.util.MapBuilder;
import org.mangorage.mangobotsite.website.util.WebUtil;

import java.io.IOException;
import java.util.List;

public class HomeServlet extends StandardHttpServlet {
    private static final List<HeaderData> headers = List.of(
            new HeaderData("/home", "Home", true),
            new HeaderData("/home#plugins", "Plugins", true),
            new HeaderData("/tricks", "Tricks", true),
            new HeaderData("/contact", "Contact", true)
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebUtil.processTemplate(
                MapBuilder.of()
                        .put("pluginCount", 1)
                        .put("guildCount", 12)
                        .put("headers", headers)
                        .put(
                                "plugins",
                                PluginManager.getInstance().getPlugins().stream()
                                        .map(PluginContainer::getMetadata)
                                        .map(p ->
                                                new PluginData(
                                                        p.getType(),
                                                        p.getName(),
                                                        "Description not available",
                                                        p.getVersion()
                                                )
                                        )
                                        .toList()
                        )
                        .get(),
                "home.ftl",
                resp.getWriter()
        );
    }
}
