package org.iconpln.proxy;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.iconpln.params.BookNumbers;

@RegisterRestClient(configKey = "number.proxy")
@Path("/api/numbers/book")
@ApplicationScoped
public interface NumberProxy {
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<BookNumbers> getBookNumber();
}
