package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.mangorage.mangobotcore.api.plugin.v1.Metadata;
import org.mangorage.mangobotcore.api.plugin.v1.PluginContainer;
import org.mangorage.mangobotcore.api.plugin.v1.PluginManager;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.util.MapBuilder;

import java.io.IOException;

import static org.mangorage.mangobotsite.website.util.WebUtil.processTemplate;

@WebServlet
public class InfoServlet extends StandardHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set content type for HTML response
        resp.setContentType("text/html");

        var plugins = PluginManager.getInstance().getPlugins().stream()
                .map(PluginContainer::getMetadata)
                .map(MyMetadata::new)
                .toList();

        processTemplate(
                MapBuilder.of()
                        .put("plugins", plugins)
                        .get(),
                "info.ftl",
                resp.getWriter()
        );

    }

    @Override
    public boolean useDefaultStyles() {
        return false;
    }

    public record MyMetadata(Metadata metadata) {
        public String getName() {
            return metadata.getName();
        }

        public String getType() {
            return metadata.getType();
        }

        public String getId() {
            return metadata.getId();
        }

        public String getVersion() {
            return metadata.getVersion();
        }
    }
}
