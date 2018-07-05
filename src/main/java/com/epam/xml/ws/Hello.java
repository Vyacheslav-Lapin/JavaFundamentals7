package com.epam.xml.ws;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

import static javax.jws.soap.SOAPBinding.Style.RPC;

@WebService
@SOAPBinding(style = RPC)
public class Hello {

    public String sayHello(@WebParam(name="name") String name) {
        return "Hello, " + name;
    }

    // wsimport -d . -p client -keep http://localhost:1212/hello\?wsdl
    public static void main(String... __) {
        String url = "http://localhost:1212/hello";
        Endpoint.publish(url, new Hello());
        System.out.printf("Service started @ %s?wsdl%n", url);
    }
}
