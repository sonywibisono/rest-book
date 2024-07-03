package org.iconpln.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class BookNumbers {
    private String asin;
    @JsonProperty("isbn_10")
    private String isbn10;
    @JsonProperty("isbn_13")
    private String isbn13;
    @JsonProperty("ean_8")
    private String ean8;
    @JsonProperty("ean_13")
    private String ean13;

    // Getters and Setters
    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getEan8() {
        return ean8;
    }

    public void setEan8(String ean8) {
        this.ean8 = ean8;
    }

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }
}