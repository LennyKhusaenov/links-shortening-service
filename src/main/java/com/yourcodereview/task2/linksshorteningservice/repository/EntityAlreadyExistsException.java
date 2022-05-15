package com.yourcodereview.task2.linksshorteningservice.repository;

import javax.persistence.PersistenceException;

public class EntityAlreadyExistsException extends PersistenceException {
    public EntityAlreadyExistsException() {
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
