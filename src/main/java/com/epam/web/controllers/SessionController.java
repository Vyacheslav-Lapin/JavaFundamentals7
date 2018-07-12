package com.epam.web.controllers;

import com.epam.fp.StreamUtils;
import io.vavr.Tuple;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;

@WebServlet("/SessionController")
public class SessionController extends PostHttpServlet {

    @Override
    @SneakyThrows
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.setAttribute(
                request.getParameter("paramName"),
                request.getParameter("paramValue"));

        request.setAttribute("out",
                StreamUtils.toStream(session.getAttributeNames().asIterator())
                        .map(name -> Tuple.of(name, session.getAttribute(name)))
                        .map(param -> String.format("%s - %s", param._1, param._2))
                        .collect(Collectors.joining("<br/>")));

        request.getRequestDispatcher("/session-demo.jsp")
                .forward(request, response);
    }
}
