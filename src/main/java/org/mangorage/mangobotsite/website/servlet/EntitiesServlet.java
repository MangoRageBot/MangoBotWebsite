package org.mangorage.mangobotsite.website.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.servlet.entity.EntityManager;
import org.mangorage.mangobotsite.website.util.WebConstants;

import java.io.IOException;
import java.util.ArrayList;

public final class EntitiesServlet extends StandardHttpServlet {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public record Entity(String id, long age) {}

    public record StandardList(ArrayList<Entity> entities) {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var entityId = req.getRequestURI().replaceFirst("/api/entities/", "");
        final var entityManager = getObjectMap().get(WebConstants.ENTITY_MANAGER, EntityManager.class);

        final var list = entityManager.findAllByType(entityId)
                .stream()
                .map(e -> new Entity(e.getId(), e.getAge()))
                .toList();

        if (list.isEmpty()) {
            resp.getWriter().write("No entities found!");
        } else {
            resp.getWriter().write(
                    GSON.toJson(new StandardList(new ArrayList<>(list)))
            );
        }
    }
}
