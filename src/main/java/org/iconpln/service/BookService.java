package org.iconpln.service;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.iconpln.model.Book;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BookService implements PanacheRepository<Book> {
    public Uni<List<Book>> searchTitle(String keyword){
        return list("slug like ?1",keyword);
    }

    public Uni<Book> findById(UUID id) {
//
        return find("id=?1",id).firstResult();
    }

    public Uni<List<Book>> getAll() {
        return findAll().list();
    }
    public Uni<List<Book>> getBookByAuthor(Long authorId) {
        return find("author.id=?1",authorId).list();
    }
    public Uni<Book> save(Book book){
        return Panache.withTransaction(book::persist);
    }
    public Uni<Boolean> bookExist(Long authorId) {
        return Book.find("author.id=?1",authorId).firstResult().onItem().transform(item->true)
                .onItem().ifNull().continueWith(false);
    }
    public Uni<Boolean> setAuthorNull(Long authorId){
        return getBookByAuthor(authorId).onItem().transform(items->{
            items.forEach(item->{
                item.author=null;
                Panache.withTransaction(item::persist);
            });
            return true;
        }).onFailure().recoverWithItem(false);
    }

}
