package webapi;

import dao.GunInstanceDao;
import listeners.Initer;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Value
@Path("instance")
@Produces(APPLICATION_JSON)
public final class InstanceResource implements JsonRestfulWebResource {

  @NonFinal
  GunInstanceDao gunInstanceDao;

  @Context
  public void init(@NotNull ServletContext context) {
    gunInstanceDao = (GunInstanceDao) context.getAttribute(Initer.INSTANCE_DAO);
  }

  @GET
  public Response getAll() {
    return gunInstanceDao.mapAll(instanceStream -> {
      val instances = instanceStream.collect(Collectors.toList());
      return instances.isEmpty() ? noContent() : ok(instances);
    });
  }

  @GET
  @Path("{id}")
  public Response get(@PathParam("id") long id) {
    return gunInstanceDao.get(id)
      .map(this::ok)
      .orElse(noContent());
  }
}
