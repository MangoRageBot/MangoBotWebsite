package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.servlet.entity.EntityManager;
import org.mangorage.mangobotsite.website.util.WebConstants;

import java.io.IOException;

public class EntityServlet extends StandardHttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var entityId = req.getRequestURI().replaceFirst("/api/entity/", "");
        final var entityManager = getObjectMap().get(WebConstants.ENTITY_MANAGER, EntityManager.class);

        final var entity = entityManager.findEntity(entityId);
        if (entity.isPresent()) {
            resp.getWriter().write(entity.get().getJson());
        } else {
            resp.getWriter().write("Unable to find anything");
        }
    }
}
