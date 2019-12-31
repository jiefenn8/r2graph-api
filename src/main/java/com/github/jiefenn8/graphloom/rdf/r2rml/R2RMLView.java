/*
 *    Copyright (c) 2019 - GraphLoom contributors (github.com/jiefenn8/graphloom)
 *    This software is made available under the terms of Apache License, Version 2.0.
 */

package com.github.jiefenn8.graphloom.rdf.r2rml;

import com.github.jiefenn8.graphloom.api.InputSourceConfig;
import com.github.jiefenn8.graphloom.api.SourceConfig;
import org.apache.commons.lang3.StringUtils;

/**
 * This class extends the base methods of the {@link InputSourceConfig} to manage
 * configuration to only handle SQL retrieval through the use of custom SQL query.
 */
public class R2RMLView extends InputSourceConfig implements SourceConfig {

    /**
     * Constructs a R2RMLView with the specified custom SQL query as the source
     * payload.
     *
     * @param payload
     */
    protected R2RMLView(String payload) {
        super(payload, DatabaseType.QUERY, StringUtils.EMPTY);
    }
}
