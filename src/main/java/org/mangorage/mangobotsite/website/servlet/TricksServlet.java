package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotcore.api.plugin.v1.PluginManager;
import org.mangorage.mangobotplugin.entrypoint.MangoBot;
import org.mangorage.mangobotsite.website.WebsiteConstants;
import org.mangorage.mangobotsite.website.servlet.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.servlet.data.GuildsData;
import org.mangorage.mangobotsite.website.servlet.data.category.CategoryData;
import org.mangorage.mangobotsite.website.servlet.data.trick.TrickData;
import org.mangorage.mangobotsite.website.servlet.data.trick.TrickInfoData;
import org.mangorage.mangobotsite.website.util.MapBuilder;
import org.mangorage.mangobotsite.website.util.WebUtil;

import java.io.IOException;

public final class TricksServlet extends StandardHttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        final var plugin = PluginManager.getInstance().getPlugin("mangobot").getInstance(MangoBot.class);
        final var manager = plugin.getTrickManager();

        final var selectedGuildId = req.getParameter("guildId");
        final var selectedTrickId = req.getParameter("trickId");

        final var guildsList = GuildsData.get(manager.getAllGuilds(), plugin.getJDA());


        final MapBuilder mapBuilder = MapBuilder.of()
                .put("headers", WebsiteConstants.headers);

        if (selectedTrickId != null) {
            final var trick = manager.getTrickForGuildByName(Long.parseLong(selectedGuildId), selectedTrickId);
            mapBuilder.put("trick", new TrickData(trick));
            mapBuilder.put("selectedGuildId", selectedGuildId);
            WebUtil.processTemplate(
                    mapBuilder
                            .put("categories", CategoryData.of(trick, plugin.getJDA()))
                            .get(),
                    "tricks.ftl",
                    resp.getWriter()
            );
            return;
        } else {
            if (selectedGuildId != null) {
                mapBuilder.put("selectedGuild", guildsList.stream().filter(g -> g.getId().equals(selectedGuildId)).findFirst().orElse(new GuildsData("N/A", "N/A", "N/A", -1)));
                mapBuilder.put("selectedGuildId", selectedGuildId);
                mapBuilder.put("tricks",
                        TrickInfoData.get(manager, Long.parseLong(selectedGuildId))
                );
            }

            mapBuilder.put(
                    "guilds",
                    guildsList
            );


            WebUtil.processTemplate(
                    mapBuilder.get(),
                    "guilds.ftl",
                    resp.getWriter()
            );
        }




    }
}


