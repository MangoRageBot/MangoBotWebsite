package org.mangorage.mangobotsite.website.servlet.data;

public record PluginData(
        String type,
        String name,
        String description,
        String version
) {
    // FreeMarker compatibility getters
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }
}