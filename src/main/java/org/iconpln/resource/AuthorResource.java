package org.iconpln.resource;

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

@Path("/api/author")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {
    @Inject
    AuthorService authorService;
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
    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteAuthor(@PathParam("id") Long id){
        MessageResult success = new MessageResult(true,"Hapus Pengarang Sukses!");
        MessageResult failed = new MessageResult(false,"Hapus Pengarang Gagal!");
        return authorService.findById(id).onItem().ifNotNull().transformToUni(author -> {
            return author.delete().onItem().transform(deleted->Response.ok(success).build());
        }).onItem().ifNull().continueWith(Response.ok(failed).status(404).build());
    }
}