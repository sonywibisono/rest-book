package org.iconpln.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "book",schema = "sony")
public class Book extends PanacheEntityBase {
    @Id
    public UUID id;
    @Column(name = "title",length = 255)
    public String title;
    @Column(name = "slug",length = 255,unique = true)
    public String slug;
    @Column(length = 13,name = "isbn_13")
    public String isbn13;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @JsonProperty("author")
    public Author author;

    public Book(){
        this.id = UUID.randomUUID();
    }

    public Book(String title, String isbn13, Author author) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.isbn13 = isbn13;
        this.author = author;
        this.slug = title.toLowerCase(Locale.ROOT).replaceAll(" ","_");
    }

}
