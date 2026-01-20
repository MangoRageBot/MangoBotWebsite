package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.dv8tion.jda.api.JDA;
import org.mangorage.mangobotplugin.commands.trick.Trick;
import org.mangorage.mangobotplugin.commands.trick.TrickManager;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.util.MapBuilder;
import java.io.IOException;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;

import static org.mangorage.mangobotsite.website.util.WebUtil.processTemplate;

public class TricksServlet extends StandardHttpServlet {

    public record Guild(String id, String name) {}


    public static String getUser(JDA jda, long id) {
        var user = jda.getUserById(id);
        return user != null ? user.getName() : "";
    }

    public static String getGuild(JDA jda, long id) {
        var guild = jda.getGuildById(id);
        return guild != null ? guild.getName() : "";
    }

    private static long getLong(String value) {
        try {
            return Long.valueOf(value);
        } catch (Exception ignored) {
            return -1;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve shared objects from the servlet context
        var map = getObjectMap();
        var trickManager = map.get("trickManager", TrickManager.class);
        var jda = map.get("jda", JDA.class);

        resp.setContentType("text/html");
        var guildId = req.getParameter("guildId");
        var trickId = req.getParameter("trickId");

        if (guildId != null) {
            try {
                long id = Long.parseLong(guildId);
                if (trickManager.getTricksForGuild(id).isEmpty()) {
                    processTemplate(
                            MapBuilder.of()
                                    .self(this)
                                    .put("title", "No Tricks Exist")
                                    .put("message", "No Tricks Exist")
                                    .put("url", "/trick")
                                    .get(),
                            "general/redirect.ftl",
                            resp.getWriter()
                    );
                    return;
                }
            } catch (Exception e) {
                processTemplate(
                        MapBuilder.of()
                                .self(this)
                                .put("title", "Invalid GuildID")
                                .put("message", "Invalid GuildID")
                                .put("url", "/trick")
                                .get(),
                        "general/redirect.ftl",
                        resp.getWriter()
                );
                return;
            }
        }

        var trick = guildId != null && trickId != null ? trickManager.getTrickForGuildByName(Long.parseLong(guildId), trickId) : null;

        if (trickId != null && guildId != null && trick == null) {
            processTemplate(
                    MapBuilder.of()
                            .self(this)
                            .put("title", "Invalid Trick")
                            .put("message", "Invalid Trick")
                            .put("url", "/trick?guildId=%s".formatted(guildId))
                            .get(),
                    "general/redirect.ftl",
                    resp.getWriter()
            );
            return;
        }



        processTemplate(
                MapBuilder.of()
                        .self(this)
                        .put("guildId", guildId)
                        .put("trickId", trickId)
                        .dynamic(b -> {

                            if (guildId == null) {
                                b.put(
                                        "guilds",
                                        trickManager.getAllGuilds()
                                                .stream()
                                                .map(id -> new Guild(id.toString(), getGuild(jda, id)))
                                                .toList()
                                );
                            }

                            if (guildId != null) {
                                b.put("tricks",
                                        trickManager.getTricksForGuild(Long.parseLong(guildId))
                                                .stream()
                                                .sorted(Comparator.comparing(Trick::getTrickID))
                                                .toList()
                                );
                            }

                            if (trick != null) {
                                b.put("trick", trick);
                                b.put("ownerName", getUser(jda, trick.getOwnerID()));
                                b.put("guildName", getGuild(jda, Long.parseLong(guildId)));
                                b.put("lastUserName", getUser(jda, trick.getLastUserEdited()));
                                b.put("lastEdited", Date.from(Instant.ofEpochMilli(trick.getLastEdited())).toString());
                                b.put("created", Date.from(Instant.ofEpochMilli(trick.getCreated())).toString());
                            }
                        })
                        .get(),
                "tricks.ftl",
                resp.getWriter()
        );
    }

    @Override
    public boolean useDefaultStyles() {
        return false;
    }
}
