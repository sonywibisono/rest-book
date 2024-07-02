package org.iconpln.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "book",schema = "sony")
public class Book extends PanacheEntityBase {
    @Id
    private UUID id;
    @Column(name = "title",length = 255)
    public String title;
    @Column(name = "slug",length = 255,unique = true)
    public String slug;
    @Column(length = 13,name = "isbn_13")
    public String isbn13;
    @Column(name = "author",length = 100)
    public String author;
    public Book(){
        this.id = UUID.randomUUID();
    }

    public Book(String title, String isbn13, String author) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.isbn13 = isbn13;
        this.author = author;
        this.slug = title.toLowerCase(Locale.ROOT).replaceAll(" ","_");
    }
}
