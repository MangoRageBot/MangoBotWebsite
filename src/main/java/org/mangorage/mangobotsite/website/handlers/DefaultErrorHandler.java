package org.mangorage.mangobotsite.website.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.mangorage.mangobotsite.website.util.MapBuilder;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

import static org.mangorage.mangobotsite.website.util.WebUtil.processTemplate;

public class DefaultErrorHandler extends ErrorHandler {



    @Override
    protected void handleErrorPage(HttpServletRequest request, Writer writer, int code, String message) throws IOException {
        processTemplate(
                MapBuilder.of()
                        .self(this)
                        .put("code", code)
                        .put("message", message)
                        .put("handler", getClass().getName())
                        .put("backUrl", code == 404 ? "/home" : resolveBackUrl(request))
                        .get(),
                "general/error.ftl",
                writer
        );
    }

    private String resolveBackUrl(HttpServletRequest request) {
        // e.g. "/tricks"
        String uri = request.getRequestURI();

        if (uri != null && !uri.isBlank()) {
            return uri;
        }

        return "/home";
    }
}
