package org.iconpln.params;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MessageResult {
    public boolean status;
    public String message;
}
