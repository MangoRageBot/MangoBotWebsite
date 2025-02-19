package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mangorage.mangobotsite.website.util.WebUtil.processTemplate;

public class ChatServlet extends StandardHttpServlet {

    private static final List<String> messages = new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("self", this);

        var username = req.getParameter("username");
        model.put("hasUsername", username != null);
        if (username != null)
            model.put("username", username);

        processTemplate(
                model,
                "chat.ftl",
                resp.getWriter()
        );
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String message = req.getParameter("message");
        if (username != null && !username.trim().isEmpty() && message != null && !message.trim().isEmpty()) {
            messages.add(STR."[\{username}]: \{message}");
        }
        resp.sendRedirect(STR."/chat?username=\{username}");
    }

    @Override
    public boolean useDefaultStyles() {
        return false;
    }
}
