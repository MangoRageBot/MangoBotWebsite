package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotcore.api.plugin.v1.PluginContainer;
import org.mangorage.mangobotcore.api.plugin.v1.PluginManager;
import org.mangorage.mangobotplugin.entrypoint.MangoBot;
import org.mangorage.mangobotsite.website.Header;
import org.mangorage.mangobotsite.website.WebsiteConstants;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.servlet.data.HeaderData;
import org.mangorage.mangobotsite.website.servlet.data.PluginData;
import org.mangorage.mangobotsite.website.util.MapBuilder;
import org.mangorage.mangobotsite.website.util.WebUtil;

import java.io.IOException;
import java.util.List;

public class HomeServlet extends StandardHttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        WebUtil.processTemplate(
                MapBuilder.of()
                        .put("pluginCount", 1)
                        .put("guildCount", 12)
                        .put("headers", WebsiteConstants.headers)
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
