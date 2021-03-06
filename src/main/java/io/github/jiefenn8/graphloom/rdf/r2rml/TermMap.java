/*
 * Copyright (c) 2019 - GraphLoom contributors (github.com/jiefenn8/graphloom)
 * This software is made available under the terms of Apache License, Version 2.0.
 */

package io.github.jiefenn8.graphloom.rdf.r2rml;

import io.github.jiefenn8.graphloom.api.inputsource.Entity;
import org.apache.jena.rdf.model.RDFNode;

import java.util.Set;

/**
 * This interface defines the base methods that manages the common mapping of
 * any given source to their respective rdf terms.
 */
public interface TermMap {

    /**
     * Returns a generated term node using the provided entity
     * properties collections. The type of term node returned
     * depends on any class term type declared.
     *
     * @param entity containing any data needed to generate term
     * @return the term node generated by this term map
     */
    RDFNode generateRDFTerm(Entity entity);

    /**
     * Returns a generated term node using the provided entity
     * properties collections. The type of term node returned
     * depends on any class term type declared.
     * <p>
     * Note: This method is for reference object map where
     * the property typically used to match a column in entity
     * is mapped as another name; So providing a join condition
     * is needed to determine the best id to use to get the value
     * from entity.
     *
     * @param joins  the collection of join conditions to get mapped id
     * @param entity containing any data needed to generate term
     * @return the term node generated by this term map
     */
    RDFNode generateRDFTerm(Set<JoinCondition> joins, Entity entity);

    /**
     * The TermType to return generated term as.
     */
    enum TermType {
        UNDEFINED, IRI, BLANK, LITERAL
    }
}
