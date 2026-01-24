package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotcore.api.plugin.v1.PluginContainer;
import org.mangorage.mangobotcore.api.plugin.v1.PluginManager;
import org.mangorage.mangobotplugin.entrypoint.MangoBot;
import org.mangorage.mangobotsite.website.WebsiteConstants;
import org.mangorage.mangobotsite.website.servlet.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.servlet.data.PluginData;
import org.mangorage.mangobotsite.website.util.MapBuilder;
import org.mangorage.mangobotsite.website.util.WebUtil;

import java.io.IOException;

public class HomeServlet extends StandardHttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        final var JDA = PluginManager.getInstance().getPlugin("mangobot").getInstance(MangoBot.class).getJDA();

        WebUtil.processTemplate(
                MapBuilder.of()
                        .put("pluginCount", PluginManager.getInstance().getPlugins().size())
                        .put("guildCount", JDA.getGuilds().size())
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
