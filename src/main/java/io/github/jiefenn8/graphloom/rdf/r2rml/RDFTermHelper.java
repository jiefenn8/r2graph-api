/*
 * Copyright (c) 2019 - GraphLoom contributors (github.com/jiefenn8/graphloom)
 * This software is made available under the terms of Apache License, Version 2.0.
 */

package io.github.jiefenn8.graphloom.rdf.r2rml;

import io.github.jiefenn8.graphloom.exceptions.MapperException;
import io.github.jiefenn8.graphloom.rdf.r2rml.TermMap.TermType;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;

import java.util.Objects;

/**
 * This class defines the base methods in managing common RDF related
 * operations.
 */
public class RDFTermHelper {

    /**
     * Returns the term created from the given value and term type
     * specified to be mapped to.
     *
     * @param value the value of the term
     * @param type  the term type to map the value into
     * @return the generated term value to the type specified
     */
    public static RDFNode asRDFTerm(String value, TermType type) {
        Objects.requireNonNull(value);
        return switch (Objects.requireNonNull(type)) {
            case IRI -> ResourceFactory.createResource(value);
            case BLANK -> ResourceFactory.createResource();
            case LITERAL -> ResourceFactory.createStringLiteral(value);
            default -> throw new MapperException("Term type is UNDEFINED.");
        };
    }
}
