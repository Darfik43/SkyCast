package com.darfik.skycast.servlet;

import com.darfik.skycast.util.ThymeleafUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

public class RenderServlet extends HttpServlet {

    ITemplateEngine engine;

    @Override
    public void init() {
        engine = ThymeleafUtil.buildTemplateEngine(this.getServletContext());
    }

    protected void processTemplate(String templateName, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        WebContext webContext = ThymeleafUtil.buildWebContext(req, resp, getServletContext());

        engine.process(templateName, webContext, resp.getWriter());
    }
}
