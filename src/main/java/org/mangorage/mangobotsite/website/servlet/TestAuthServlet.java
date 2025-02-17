package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;

import java.io.IOException;

public class TestAuthServlet extends StandardHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Test");
    }

    @Override
    public boolean hasEmbed() {
        return true;
    }
}
