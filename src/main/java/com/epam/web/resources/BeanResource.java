package com.epam.web.resources;

import com.epam.xml.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.val;

import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Providers;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static lombok.AccessLevel.PRIVATE;

@Path("bean")
@FieldDefaults(level = PRIVATE)
public class BeanResource implements JsonRestfulWebResource {

    @Context
    Application app;

    @Context
    UriInfo uri;

    @Context
    HttpHeaders headers;

    @Context
    Request request;

    @Context
    SecurityContext security;

    @Context
    Providers providers;

    // bean/123
    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        return ok(new Food(id, "waffles", "$4", "delicious!", 100));
    }

    //webapi/bean/create
    @POST
    @Path("create")
    @Consumes({APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML})
    @Produces(APPLICATION_JSON)
    public Response post(//Obj obj
                         @FormParam("id") int id,
                         @FormParam("name") String name,
                         @CookieParam("JSESSIONID") String sessionId,
                         @HeaderParam("X-My-Header") int x
    ) {

        val client = ClientBuilder.newClient();
        String food = client
                .target("http://localhost:8080/webapi/bean")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .get(String.class);

        return ok(fromJSON(food, Food.class));

//        return ok(new Obj(id, name, sessionId, x));
    }
}

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class Obj {
    int id;
    String name;
    String sessionId;
    int x;
}