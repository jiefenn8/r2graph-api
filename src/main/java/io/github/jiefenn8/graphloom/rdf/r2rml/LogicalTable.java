/*
 * Copyright (c) 2019 - GraphLoom contributors (github.com/jiefenn8/graphloom)
 * This software is made available under the terms of Apache License, Version 2.0.
 */

package io.github.jiefenn8.graphloom.rdf.r2rml;

import com.google.gson.GsonBuilder;
import io.github.jiefenn8.graphloom.api.EntityChild;
import io.github.jiefenn8.graphloom.api.EntityMap;
import io.github.jiefenn8.graphloom.api.EntityReference;
import io.github.jiefenn8.graphloom.api.SourceMap;
import io.github.jiefenn8.graphloom.exceptions.MapperException;
import io.github.jiefenn8.graphloom.util.GsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of R2RML LogicalTable with {@link SourceMap} interface.
 */
public class LogicalTable implements SourceMap, EntityChild {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogicalTable.class);
    private final UUID uuid;
    private final TriplesMap parent;
    private final EntityReference entityReference;

    /**
     * Constructs a LogicalTable with the specified Builder containing the
     * properties to populate and initialise an immutable instance.
     *
     * @param builder the logical table builder to build from
     */
    private LogicalTable(Builder builder) {
        Objects.requireNonNull(builder);
        entityReference = builder.entityReference;
        parent = builder.parent;
        uuid = builder.uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LogicalTable that = (LogicalTable) obj;
        return Objects.equals(entityReference, that.entityReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityReference);
    }

    @Override
    public EntityReference getEntityReference() {
        return entityReference;
    }

    @Override
    public EntityMap getEntityMap() {
        return parent;
    }

    @Override
    public String toString() {
        return GsonHelper.loadTypeAdapters(new GsonBuilder())
                .create()
                .toJson(this);
    }

    @Override
    public String getUniqueId() {
        return uuid.toString();
    }

    /**
     * Builder class for LogicalTable.
     */
    public static class Builder {

        private UUID uuid;
        private EntityReference entityReference;
        private TriplesMap parent;

        /**
         * Constructs a Builder with the specified SourceConfig instance.
         *
         * @param entityReference the query config to set on this logical table
         */
        public Builder(EntityReference entityReference) {
            this.entityReference = Objects.requireNonNull(entityReference, "Payload must not be null.");
        }

        /**
         * Constructs a Builder with the specified LogicalTable containing the
         * query config instance.
         *
         * @param logicalTable the logical table with the query config needed
         *                     to set on this logical table
         */
        public Builder(LogicalTable logicalTable) {
            this.entityReference = Objects.requireNonNull(logicalTable.entityReference, "Payload must not be null.");
        }

        /**
         * Adds association to an triples map that this logical table belongs to.
         *
         * @param triplesMap the triples map to associate with
         * @return this builder for fluent method chaining
         */
        protected Builder withTriplesMap(TriplesMap triplesMap) {
            parent = triplesMap;
            return this;
        }

        /**
         * Builds a query config with a join query consisting of two query,
         * table or mixed that is associated to each other through join
         * conditions.
         *
         * @param logicalTable   the second query or table to build a joint query
         * @param joinConditions the set of joins conditions to use
         * @return this builder for fluent method chaining
         */
        public Builder withJointQuery(LogicalTable logicalTable, Set<JoinCondition> joinConditions) {
            if (joinConditions.isEmpty()) {
                throw new MapperException("Expected JoinConditions with joint query creation.");
            }

            String jointQuery = "SELECT child.* FROM " + prepareQuery(entityReference) + " AS child, ";
            jointQuery += prepareQuery(logicalTable.entityReference) + " AS parent";
            jointQuery += " WHERE " + buildJoinStatement(joinConditions.iterator());
            String parentVersion = entityReference.getProperty("sqlVersion");
            this.entityReference = R2RMLFactory.createR2RMLView(jointQuery, parentVersion);
            return this;
        }

        /**
         * Returns the ending query segment containing all the join conditions
         * recursively built from the given iterator of a join condition collection.
         *
         * @param iterator of the join condition collection
         * @return the ending segment containing SQL built join conditions
         */
        private String buildJoinStatement(Iterator<JoinCondition> iterator) {
            JoinCondition join = iterator.next();
            String joinStatement = "child." + join.getChild() + "=parent." + join.getParent();
            if (iterator.hasNext()) {
                joinStatement = joinStatement.concat(" AND " + buildJoinStatement(iterator));
            }
            return joinStatement;
        }

        /**
         * Returns a prepared query using the query/table in the given source
         * config. If the query config is a r2rml view, wrap the query before
         * returning it.
         *
         * @param sourceConfig the config to containing the query
         * @return the query prepared for further manipulation
         */
        private String prepareQuery(EntityReference sourceConfig) {
            if (sourceConfig instanceof R2RMLView) {
                return "(" + sourceConfig.getPayload() + ")";
            }
            return sourceConfig.getPayload();
        }

        /**
         * Returns an immutable instance of logical table containing the properties
         * given to its builder.
         *
         * @return instance of logical table created with the info in this builder
         */
        public LogicalTable build() {
            uuid = UUID.randomUUID();
            LOGGER.debug("Building logical table from parameters. ID:{}", uuid);
            LogicalTable logicalTable = new LogicalTable(this);
            LOGGER.debug("{}", logicalTable);
            return logicalTable;
        }
    }
}
