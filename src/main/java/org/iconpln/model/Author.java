package org.iconpln.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors",schema = "sony")
public class Author extends PanacheEntity {
    public String name;
    @Column(unique = true)
    public String email;
    public String avatar;
    @JsonBackReference
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    public List<Book> books;

    public Author(){}
    public Author(String name, String email, String avatar) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
    }
    @JsonProperty("books")
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
