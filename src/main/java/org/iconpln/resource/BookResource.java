package org.iconpln.resource;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.iconpln.service.BookService;

@Path("/api/book")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    @Inject
    BookService bookService;
    @GET
    public Uni<Response> getAllBook(){
        return bookService.getAll().onItem().ifNotNull().transform(items-> Response.ok(items).build())
                .onItem().ifNull().continueWith(Response.ok("Data Not Found").status(404).build());
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> postBook(Book item){
        return bookService.save(item).onItem().transform(item->Response.ok(item).build())
                .onFailure().recoverWithItem(Response.ok("Terjadi Kesalahan").status(505).build());
    }
    @GET
    @Path("/{id}")
    public Uni<Response> getBook(@PathParam("id") String id){
        return bookService.findById(id).onItem().ifNotNull().transformToUni(item->Response.ok(item).build())
                .onItem().ifNull().continueWith(Response.ok("Data Not Found").status(404).build());
    }
}
