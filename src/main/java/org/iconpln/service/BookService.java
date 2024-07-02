package org.iconpln.service;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.iconpln.model.Book;

@ApplicationScoped
public class BookService implements PanacheRepository<Book> {
    public Uni<List<Book>> searchTitle(String keyword){
        return list("title like ?1",keyword);
    }

    @Override
    public Uni<Book> findById(String id) {
        return findById(id);
    }
}
