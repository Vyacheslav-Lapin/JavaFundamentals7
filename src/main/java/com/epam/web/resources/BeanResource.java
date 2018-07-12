package com.epam.web.resources;

import com.epam.xml.Food;
import lombok.experimental.FieldDefaults;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import static lombok.AccessLevel.PRIVATE;

@Path("bean")
@FieldDefaults(level = PRIVATE)
public class BeanResource implements JsonRestfulWebResource {

    @GET
    public Response get() {
        return ok(new Food(1, "waffles", "$4", "delicious!", 100));
    }

//    @POST
//    public Response post() {
//
//    }
}
