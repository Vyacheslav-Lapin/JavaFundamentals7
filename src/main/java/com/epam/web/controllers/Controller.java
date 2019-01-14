package com.epam.web.controllers;

import com.epam.fp.StreamUtils;
import io.vavr.Tuple;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.stream.Collectors;

import static com.epam.fp.StreamUtils.streamOf;

@WebServlet("/Controller")
public class Controller extends PostHttpServlet {

  @Override
  @SneakyThrows
  public void doPost(HttpServletRequest request, HttpServletResponse response) {

    Iterator<String> iterator = request.getParameterNames().asIterator();

    request.setAttribute("collect", StreamUtils.streamOf(iterator)
      .map(paramName -> Tuple.of(paramName, request.getParameter(paramName)))
      .map(param -> param._1 + " = " + param._2)
      .collect(Collectors.joining("<br/>")));

    request.getRequestDispatcher("/WEB-INF/jsp/getStudents.jsp")
      .forward(request, response);
  }
}
