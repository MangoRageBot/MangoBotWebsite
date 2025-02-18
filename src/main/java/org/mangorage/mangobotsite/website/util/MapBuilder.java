package org.mangorage.mangobotsite.website.util;

import java.util.Map;
import java.util.function.Consumer;

public final class MapBuilder {
    private final Map<String, Object> map;

    public MapBuilder(Map<String, Object> map) {
        this.map = map;
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
