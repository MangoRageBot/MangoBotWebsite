package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotapi.core.plugin.PluginContainer;
import org.mangorage.mangobotapi.core.plugin.PluginManager;
import org.mangorage.mangobotapi.core.plugin.PluginMetadata;
import org.mangorage.mangobotsite.website.Header;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.util.MapBuilder;

import java.io.IOException;
import java.util.List;

import static org.mangorage.mangobotsite.website.util.WebUtil.processTemplate;

@WebServlet
public class InfoServlet extends StandardHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set content type for HTML response
        resp.setContentType("text/html");

        List<PluginMetadata> plugins = PluginManager.getPluginContainers().stream()
                .map(PluginContainer::getMetadata)
                .toList();

        processTemplate(
                MapBuilder.of()
                        .put("plugins", plugins)
                        .get(),
                "info.ftl",
                resp.getWriter());

    }

    @Override
    public boolean useDefaultStyles() {
        return false;
    }
}
