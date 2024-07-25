package org.iconpln.resource;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.iconpln.model.Author;
import org.iconpln.params.AuthorParam;
import org.iconpln.params.MessageResult;
import org.iconpln.service.AuthorService;
import org.iconpln.service.BookService;

@Path("/api/author")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {
    @Inject
    AuthorService authorService;
    @Inject
    BookService bookService;
    @GET
    @Path("/")
    public Uni<Response> getAllAuthor(){
        return authorService.getAll().onItem().transform(items->Response.ok(items).build());
    }
    @POST
    @Path("/store")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> postAuthor(AuthorParam param){
        Author author = new Author(param.name,param.email,param.avatar);
        return authorService.persist(author).onItem().transform(item->Response.ok(item).build());
    }
    @GET
    @Path("/books/{id}")
    public Uni<Response> getAuthorBooks(@PathParam("id") Long id){
        return bookService.getBookByAuthor(id).onItem().transform(items->Response.ok(items).build());
    }
    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteAuthor(@PathParam("id") Long id){
        MessageResult success = new MessageResult(true,"Hapus Pengarang Sukses!");
        MessageResult failed = new MessageResult(false,"Hapus Pengarang Gagal!");
        MessageResult nulled = new MessageResult(true,"Nulled Pengarang Sukses!");
//        return authorService.findById(id).onItem().ifNotNull().transformToUni(author -> {
//            return bookService.setAuthorNull(id).onItem().transformToUni(item->author.delete()
//                    .onItem().transform(deleted->Response.ok(success).build()));
//        }).onItem().ifNull().continueWith(Response.ok(failed).status(404).build());
        return Panache.withTransaction(()->authorService.deleteById(id).map(deleted->{
            return Response.ok(success).build();
        }));
    }
}
