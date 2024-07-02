package org.iconpln.service;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.iconpln.model.Book;

import java.util.List;

@ApplicationScoped
public class BookService implements PanacheRepository<Book> {
    public Uni<List<Book>> searchTitle(String keyword){
        return list("title like ?1",keyword);
    }

    public Uni<Book> findById(String id) {
        return find("id=?1",id).firstResult();
    }

    public Uni<List<Book>> getAll() {
        return findAll().list();
    }
    public Uni<Book> save(Book book){
        return Panache.withTransaction(book::persist);
    }

}
