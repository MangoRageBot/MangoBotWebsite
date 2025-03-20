package org.mangorage.mangobotsite.website.util;

public record ResolveString(String value) {
    public ResolveString resolve(String value) {
        return new ResolveString("%s/%s".formatted(this.value, value));
    }

    public String resolveFully(String value) {
        return "%s/%s".formatted(this.value, value);
    }
}
