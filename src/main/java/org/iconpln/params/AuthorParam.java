package org.iconpln.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Column;

@RegisterForReflection
public class AuthorParam {
    @JsonProperty("name")
    public String name;
    @JsonProperty("email")
    public String email;
    @JsonProperty("avatar")
    public String avatar;
}
