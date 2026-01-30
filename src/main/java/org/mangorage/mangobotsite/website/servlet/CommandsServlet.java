package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotcore.api.command.v1.AbstractCommand;
import org.mangorage.mangobotcore.api.plugin.v1.PluginManager;
import org.mangorage.mangobotplugin.entrypoint.MangoBot;
import org.mangorage.mangobotsite.website.WebsiteConstants;
import org.mangorage.mangobotsite.website.servlet.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.util.MapBuilder;
import org.mangorage.mangobotsite.website.util.WebUtil;
import java.io.IOException;

public final class CommandsServlet extends StandardHttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        final var plugin = PluginManager.getInstance().getPlugin("mangobot").getInstance(MangoBot.class);
        final var dispatcher = plugin.getCommandDispatcher();
        final var command = dispatcher.getCommand("trick").buildCommandParts();

        WebUtil.processTemplate(
                MapBuilder.of()
                        .put("headers", WebsiteConstants.headers)
                        .put("commandDataList",
                                dispatcher.getAllRegisteredCommands()
                                        .stream()
                                        .map(AbstractCommand::buildCommandParts)
                                        .toList()
                        )
                        .get(),
                "commands.ftl",
                resp.getWriter()
        );
    }
}
