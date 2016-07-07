package com.github.ajanthan.lightrest.http.response;

import java.lang.reflect.Type;

/**
 * Created by ajanthan on 7/2/16.
 */
public class EntityContext {
    private Object entityObject;
    private Class entityClass;
    private Type entityType;

    public Object getEntityObject() {
        return entityObject;
    }

    public void setEntityObject(Object entityObject) {
        this.entityObject = entityObject;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public Type getEntityType() {
        return entityType;
    }

    public void setEntityType(Type entityType) {
        this.entityType = entityType;
    }
}
