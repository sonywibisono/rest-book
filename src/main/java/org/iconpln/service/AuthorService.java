package org.iconpln.service;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.iconpln.model.Author;

import java.util.List;

@ApplicationScoped
public class AuthorService implements PanacheRepository<Author> {
    @Override
    public Uni<Author> findById(Long aLong) {
        return Author.findById(aLong);
    }

    @Override
    public Uni<Author> persist(Author author) {
        return Panache.withTransaction(author::persist);
    }

    public Uni<List<Author>> getAll() {
        return Author.listAll();
    }

    @Override
    public Uni<Boolean> deleteById(Long id) {
        return Author.deleteById(id);
    }
}
