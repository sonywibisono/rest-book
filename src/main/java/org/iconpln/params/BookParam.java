package org.iconpln.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class BookParam {
    @JsonProperty("title")
    public String title;
    @JsonProperty("author_id")
    public Long authorId;
//    @JsonProperty("isbn13")
//    public String isbn13;
}
