package com.epam.xml.ws.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;

import static javax.jws.soap.SOAPBinding.Style.RPC;

@FunctionalInterface
@SOAPBinding(style = RPC)
@WebService(name = "Hello", targetNamespace = "http://ws.xml.epam.com/")
public interface Hello {

  @WebMethod
  @WebResult(partName = "return")
  @Action(input = "http://ws.xml.epam.com/Hello/sayHelloRequest",
    output = "http://ws.xml.epam.com/Hello/sayHelloResponse")
  String sayHello(
    @WebParam(name = "name", partName = "name")
      String name);
}
