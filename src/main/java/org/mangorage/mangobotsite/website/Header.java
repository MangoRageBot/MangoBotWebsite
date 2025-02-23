package org.mangorage.mangobotsite.website;

import java.util.List;

public record Header(String page, String text) {
    public static final List<Header> DEFAULT = List.of(
            new Header("/home", "Home"),
            new Header("/info", "Info"),
            new Header("/trick", "Tricks"),
            new Header("/upload", "Upload"),
            new Header("https://discord.mangorage.org/", "Discord")
    );
}
