package org.mangorage.mangobotsite.website.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.Nullable;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.UUID;

public final class WebUtil {
    private static final String TOKEN_NAME = "user_token";

    public static String getOrCreateUserToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TOKEN_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return setVisitorIdCookie(response, STR."\{UUID.randomUUID()}"); // No visitor ID cookie found
    }

    public static String setVisitorIdCookie(HttpServletResponse response, String visitorId) {
        Cookie cookie = new Cookie(TOKEN_NAME, visitorId);
        cookie.setMaxAge(60 * 60 * 24 * 365); // 1 year expiration
        cookie.setPath("/"); // Cookie is available to the entire site
        response.addCookie(cookie);
        return visitorId;
    }

    public static @Nullable TemplateException processTemplate(Map<String, Object> data, String templateURL, Writer writer) throws IOException {
        // Configure FreeMarker
        Configuration cfg = new Configuration(new Version("2.3.31"));
        cfg.setClassForTemplateLoading(StandardHttpServlet.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");

        // Load the template
        Template template = cfg.getTemplate(templateURL);
        try {
            template.process(data, writer);
        } catch (TemplateException e) {
            return e;
        }
        return null;
    }

    public static String processStaticTemplateOrThrow(Map<String, Object> data, String templateURL) throws IOException {
        StringWriter writer = new StringWriter();
        var exception = processTemplate(data, templateURL, writer);
        if (exception != null) throw new IllegalStateException(exception);
        return writer.getBuffer().toString();
    }
}
