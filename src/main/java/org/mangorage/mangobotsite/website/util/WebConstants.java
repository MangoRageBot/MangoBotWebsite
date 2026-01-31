package org.mangorage.mangobotsite.website.util;

import org.mangorage.mangobotsite.website.servlet.data.HeaderData;

import java.util.List;

public final class WebConstants {
    public static final String LOGIN_SERVICE = "login_service";
    public static final String WEB_OBJECT_ID = "web_object";
    public static final String FILE_MANAGER = "file_manager";
    public static final String ENTITY_MANAGER = "entity_manager";

    public static final List<HeaderData> HEADERS = List.of(
            new HeaderData("/home", "Home", true),
            new HeaderData("/home#plugins", "Plugins", true),
            new HeaderData("/tricks", "Tricks", true),
            new HeaderData("/home#contact", "Contact", true),
            new HeaderData("/commands", "Commands", true)
    );
}
