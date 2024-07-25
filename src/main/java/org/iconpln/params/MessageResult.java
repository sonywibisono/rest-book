package org.iconpln.params;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MessageResult {
    public boolean status;

    public MessageResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public String message;
    public MessageResult(){}

}
