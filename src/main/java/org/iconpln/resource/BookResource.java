package org.iconpln.resource;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.iconpln.model.Author;
import org.iconpln.model.Book;
import org.iconpln.params.BookNumbers;
import org.iconpln.params.BookParam;
import org.iconpln.params.MessageResult;
import org.iconpln.proxy.NumberProxy;
import org.iconpln.service.AuthorService;
import org.iconpln.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Path("/api/book")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    final Logger LOG = LoggerFactory.getLogger(BookResource.class.getName());
    @Inject
    BookService bookService;
    @Inject
    AuthorService authorService;

    @Inject
    @RestClient
    NumberProxy numberProxy;
    @GET
    public Uni<Response> getAllBook(){
        return bookService.getAll().onItem().ifNotNull().transform(items-> Response.ok(items).build())
                .onItem().ifNull().continueWith(Response.ok("Data Not Found").status(404).build());
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> postBook(BookParam param){
        MessageResult errorMsg = new MessageResult(false,"Terjadi Kesalahan Server");
        MessageResult notfoundMsg = new MessageResult(false,"ID Pengarang Tidak ada");
        //Langkah 1, ambil nomor isbn
        return numberProxy.getBookNumber().onItem().transformToUni(nomor->{
            LOG.info("Number ISBN 13 : {}",nomor.getIsbn13());
            //Langkah ke 2 ambil Author dari Author id

            return authorService.findById(param.authorId).onItem().ifNotNull().transformToUni(author->{
                Book book = new Book(param.title,nomor.getIsbn13(),author);
                return bookService.save(book).onItem().transform(item->Response.ok(item).build())
                        .onFailure().recoverWithItem(Response.ok(errorMsg).status(505).build());
            }).onItem().ifNull().continueWith(Response.ok(notfoundMsg).status(404).build());
        });
    }
    @GET
    @Path("/{id}")
    public Uni<Response> getBook(@PathParam("id") String id){
        LOG.info("id : {}",id);
        try {
            UUID uuid = UUID.fromString(id);
            return bookService.findById(uuid).onItem().transform(item->Response.ok(item).build())
                    .onFailure().recoverWithItem(Response.ok("Data Not Found").status(404).build());
        } catch (Exception ex){
            MessageResult result = new MessageResult();
            result.status = false;
            result.message = ex.getMessage();
            return Uni.createFrom().item(Response.ok(result).build());
        }

    }
}
