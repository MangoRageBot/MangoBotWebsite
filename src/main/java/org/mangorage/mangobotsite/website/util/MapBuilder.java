package org.mangorage.mangobotsite.website.util;

import org.mangorage.mangobotsite.website.Header;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class MapBuilder {
    public static MapBuilder of() {
        return new MapBuilder(new HashMap<>());
    }

    private final Map<String, Object> map;

    private MapBuilder(Map<String, Object> map) {
        this.map = map;
        put("headers", Header.DEFAULT);
    }

    public MapBuilder self(Object object) {
        return put("self", object);
    }

    public MapBuilder dynamic(Consumer<MapBuilder> builderConsumer) {
        builderConsumer.accept(this);
        return this;
    }

    public MapBuilder put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Map<String, Object> get() {
        return map;
    }
}
