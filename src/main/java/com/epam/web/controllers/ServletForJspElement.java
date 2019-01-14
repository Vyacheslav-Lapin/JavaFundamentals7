package com.epam.web.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bean-mapping")
public class ServletForJspElement extends PostHttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    if ("naming".equals(req.getParameter("command")))
      req.getRequestDispatcher("/bean-mapping.jsp")
        .forward(req, resp);
  }
}
