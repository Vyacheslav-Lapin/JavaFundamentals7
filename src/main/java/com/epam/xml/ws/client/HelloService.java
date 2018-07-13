package com.epam.xml.ws.client;

import com.epam.fp.CheckedFunction1;
import lombok.experimental.FieldDefaults;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.URL;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@WebServiceClient(name = "HelloService",
        targetNamespace = "http://ws.xml.epam.com/",
        wsdlLocation = "http://localhost:1212/hello?wsdl")
public class HelloService extends Service {

    static URL HELLOSERVICE_WSDL_LOCATION;
    static QName HELLOSERVICE_QNAME;
    static QName HELLO_PORT;

    static {
        WebServiceClient webServiceClient = HelloService.class
                .getDeclaredAnnotation(WebServiceClient.class);

        HELLO_PORT = new QName(webServiceClient.targetNamespace(), "HelloPort");
        HELLOSERVICE_QNAME = new QName(
                webServiceClient.targetNamespace(),
                webServiceClient.name());

        HELLOSERVICE_WSDL_LOCATION = CheckedFunction1.<String, URL>of(URL::new).unchecked()
                .apply(webServiceClient.wsdlLocation());

    }

    public HelloService() {
        super(HELLOSERVICE_WSDL_LOCATION, HELLOSERVICE_QNAME);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Hello
     */
    @WebEndpoint(name = "HelloPort")
    public Hello getHelloPort(WebServiceFeature... features) {
        return getPort(HELLO_PORT, Hello.class, features);
    }

}
