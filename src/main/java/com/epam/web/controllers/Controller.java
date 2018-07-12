package com.epam.web.controllers;

import io.vavr.Tuple;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.stream.Collectors;

import static com.epam.fp.StreamUtils.toStream;

@WebServlet("/Controller")
public class Controller extends PostHttpServlet {

    @Override
    @SneakyThrows
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        Iterator<String> iterator = request.getParameterNames().asIterator();

        request.setAttribute("collect", toStream(iterator)
                .map(paramName -> Tuple.of(paramName, request.getParameter(paramName)))
                .map(param -> param._1 + " = " + param._2)
                .collect(Collectors.joining("<br/>")));

        request.getRequestDispatcher("/WEB-INF/jsp/main.jsp")
                .forward(request, response);
    }
}
