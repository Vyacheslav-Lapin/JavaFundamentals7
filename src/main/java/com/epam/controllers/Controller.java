package com.epam.controllers;

import io.vavr.Tuple;
import lombok.Cleanup;
import lombok.SneakyThrows;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.stream.Collectors;

import static com.epam.fp.StreamUtils.toStream;

@WebServlet("/Controller")
public class Controller extends HttpServlet {

    @SneakyThrows
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html");
        @Cleanup PrintWriter out = response.getWriter();
        Iterator<String> iterator = request.getParameterNames().asIterator();
        String collect = toStream(iterator)
                .map(paramName -> Tuple.of(paramName, request.getParameter(paramName)))
                .map(stringStringTuple2 -> stringStringTuple2._1 + " = " + stringStringTuple2._2)
                .collect(Collectors.joining("<br/>"));

        RequestDispatcher requestDispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");

        request.setAttribute("collect", collect);

        requestDispatcher.forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }
}
