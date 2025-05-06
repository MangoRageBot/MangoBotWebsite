package org.mangorage.mangobotsite.website.servlet.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

public record DeferredEntity(Object object, String type, UUID id, long age) implements IEntity {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public DeferredEntity(Object object, String type) {
        this(object, type, UUID.randomUUID(), System.currentTimeMillis());
    }

    @Override
    public long getAge() {
        return age();
    }

    @Override
    public String getId() {
        return id().toString();
    }

    @Override
    public String getType() {
        return "deferred";
    }

    @Override
    public String getJson() {
        return GSON.toJson(object);
    }
}
