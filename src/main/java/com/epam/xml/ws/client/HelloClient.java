package com.epam.xml.ws.client;

public interface HelloClient {

  static void main(String... __) {
    HelloService service = new HelloService();
    Hello hello = service.getHelloPort();
    System.out.println(hello.sayHello("Henry"));
  }
}
