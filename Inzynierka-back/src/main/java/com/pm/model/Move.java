package com.pm.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by izabella on 27.05.16.
 */
@Document(collection = "Move")
public class Move extends  AbstractModel{
    @DBRef
    private Node node;
    private boolean wasIn;

    public Move(){}


    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public boolean getWasIn() {
        return wasIn;
    }

    public void setWasIn(boolean wasIn) {
        this.wasIn = wasIn;
    }
}
