package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotcore.api.plugin.v1.PluginManager;
import org.mangorage.mangobotplugin.entrypoint.MangoBot;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.servlet.data.GuildsData;
import org.mangorage.mangobotsite.website.servlet.data.HeaderData;
import org.mangorage.mangobotsite.website.servlet.data.TrickData;
import org.mangorage.mangobotsite.website.servlet.data.TrickInfoData;
import org.mangorage.mangobotsite.website.util.MapBuilder;
import org.mangorage.mangobotsite.website.util.WebUtil;

import java.io.IOException;
import java.util.List;

public final class TricksServlet extends StandardHttpServlet {
    private static final List<HeaderData> headers = List.of(
            new HeaderData("/home", "Home", true),
            new HeaderData("/home#plugins", "Plugins", true),
            new HeaderData("/tricks", "Tricks", true),
            new HeaderData("/contact", "Contact", true)
    );

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var plugin = PluginManager.getInstance().getPlugin("mangobot").getInstance(MangoBot.class);
        final var manager = plugin.getTrickManager();

        final var selectedGuildId = req.getParameter("guildId");
        final var selectedTrickId = req.getParameter("trickId");

        final var guildsList = GuildsData.get(manager.getAllGuilds(), plugin.getJDA());


        final MapBuilder mapBuilder = MapBuilder.of()
                .put("headers", headers);

        if (selectedTrickId != null) {
            final var trick = manager.getTrickForGuildByName(Long.parseLong(selectedGuildId), selectedTrickId);
            mapBuilder.put("trick", new TrickData(trick));
            WebUtil.processTemplate(
                    mapBuilder.get(),
                    "tricks.ftl",
                    resp.getWriter()
            );
            return;
        } else {
            if (selectedGuildId != null) {
                mapBuilder.put("selectedGuild", guildsList.stream().filter(g -> g.getId().equals(selectedGuildId)).findFirst().orElse(null));
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


