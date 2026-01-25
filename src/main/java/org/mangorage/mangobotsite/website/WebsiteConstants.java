package org.mangorage.mangobotsite.website;

import org.mangorage.mangobotsite.website.servlet.data.HeaderData;

import java.util.List;

public final class WebsiteConstants {
    public static final List<HeaderData> headers = List.of(
            new HeaderData("/home", "Home", true),
            new HeaderData("/home#plugins", "Plugins", true),
            new HeaderData("/tricks", "Tricks", true),
            new HeaderData("/home#contact", "Contact", true),
            new HeaderData("/commands", "Commands", true)
    );
}
