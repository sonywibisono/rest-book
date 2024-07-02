package org.iconpln.resource;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.iconpln.model.Book;
import org.iconpln.params.BookParam;
import org.iconpln.params.MessageResult;
import org.iconpln.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/book")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    final Logger LOG = LoggerFactory.getLogger(BookResource.class.getName());
    @Inject
    BookService bookService;
    @GET
    public Uni<Response> getAllBook(){
        return bookService.getAll().onItem().ifNotNull().transform(items-> Response.ok(items).build())
                .onItem().ifNull().continueWith(Response.ok("Data Not Found").status(404).build());
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> postBook(BookParam param){
        Book book = new Book(param.title,param.isbn13,param.author);
        return bookService.save(book).onItem().transform(item->Response.ok(item).build())
                .onFailure().recoverWithItem(Response.ok("Terjadi Kesalahan").status(505).build());
    }
    @GET
    @Path("/{id}")
    public Uni<Response> getBook(@PathParam("id") String id){
        LOG.info("id : {}",id);
        try {
            return bookService.findById(id).onItem().transform(item->Response.ok(item).build())
                    .onFailure().recoverWithItem(Response.ok("Data Not Found").status(404).build());
        } catch (Exception ex){
            MessageResult result = new MessageResult();
            result.status = false;
            result.message = ex.getMessage();
            return Uni.createFrom().item(Response.ok(result).build());
        }

    }
}
