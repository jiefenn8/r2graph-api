/*
 * Copyright (c) 2019 - GraphLoom contributors (github.com/jiefenn8/graphloom)
 * This software is made available under the terms of Apache License, Version 2.0.
 */

package io.github.jiefenn8.graphloom.api;

import io.github.jiefenn8.graphloom.util.UniqueId;

import java.util.Set;

/**
 * This interface defines the base methods that manages the mapping
 * of data to their graph terms sharing the same entity.
 */
public interface EntityMap extends PropertyMap, UniqueId {

    /**
     * Returns the source map that has the information and data required to
     * map into graph.
     *
     * @return the source map associated with this entity map
     */
    SourceMap getSourceMap();

    /**
     * Returns the unique id name that identify this entity map.
     *
     * @return unique id name of this entity map
     */
    String getIdName();

    /**
     * Returns all relation maps that this entity map has.
     *
     * @return the set containing all relation maps in this entity map
     */
    Set<RelationMap> listRelationMaps();

    /**
     * Returns the node map associated with the given relation map.
     *
     * @param relationMap the relation mapping to be searched in entity map for
     *                    its paired node map
     * @return the node map found with the given relation map
     */
    NodeMap getNodeMapWithRelation(RelationMap relationMap);

    /**
     * Returns true if this entity mapping has any relation map and
     * node map pairs'.
     *
     * @return true if there are any pair, otherwise false
     */
    boolean hasRelationNodeMaps();

}
