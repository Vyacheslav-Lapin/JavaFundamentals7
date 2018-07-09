package com.epam.controllers;

import io.vavr.Tuple;
import lombok.Cleanup;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.ORDERED;

@WebServlet("/Controller")
public class Controller extends HttpServlet {

    @SneakyThrows
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html");
        @Cleanup PrintWriter out = response.getWriter();
        out.println("<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Hello, World!!!</h1>\n" +
                        StreamSupport.stream(
                                Spliterators.spliteratorUnknownSize(
                                        request.getParameterNames().asIterator(), ORDERED),
                                false)
                                .map(paramName -> Tuple.of(paramName, request.getParameter(paramName)))
                                .map(stringStringTuple2 -> stringStringTuple2._1 + " = " + stringStringTuple2._2)
                                .collect(Collectors.joining("<br/>")) +
                "\n" +
                "</body>\n" +
                "</html>");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }
}
