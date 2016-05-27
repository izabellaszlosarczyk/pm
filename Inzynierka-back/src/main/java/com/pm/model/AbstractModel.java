package com.pm.model;

import org.springframework.data.annotation.Id;

/**
 * Created by izabella on 29.04.16.
 */
public abstract class AbstractModel {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
