/*
 *    Copyright (c) 2019 - Javen Liu (github.com/jiefenn8)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.jiefenn8.graphloom.mapper;

import com.github.jiefenn8.graphloom.configmap.ConfigMap;
import com.github.jiefenn8.graphloom.configmap.EntityMap;
import com.github.jiefenn8.graphloom.configmap.PredicateMap;
import com.github.jiefenn8.graphloom.io.InputDatabase;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.jena.ext.com.google.common.base.Preconditions.checkNotNull;

/**
 * Process ConfigMap map and Input Database to RDF Triples.
 */
public class Mapper {
    private final static Logger LOGGER = LoggerFactory.getLogger(Mapper.class.getName());
    private final Pattern pattern = Pattern.compile("\\{(.*?)}");

    private Model rdfGraph;
    private boolean cancelled = false;

    /**
     * Main mapping function converting RDF SQL database data to RDF triples
     * using {@code ConfigMap} map tree.
     *
     * @param input     of the sql database to send query to retrieve data.
     * @param configMap to configure the mapping of data from database into RDF triples.
     */
    public Model mapToGraph(InputDatabase input, ConfigMap configMap) {
        checkNotNull(input);
        checkNotNull(configMap);

        rdfGraph = ModelFactory.createDefaultModel();
        configMap.listEntityMaps().forEach((key, triplesMap) -> processRow(input, triplesMap));

        LOGGER.info("Mapping complete");
        return rdfGraph;
    }

    /**
     * Generate Triple for subject and for its properties.
     *
     * @param input     database interface to query sql data from.
     * @param entityMap configuration specifying what data to map to Triples.
     */
    private void processRow(InputDatabase input, EntityMap entityMap) {
        for (Map<String, String> row : input.getRows(entityMap.getEntitySource())) {
            if (cancelled) break;

            String subjectURI = generateURIFromTemplate(entityMap.getTemplate(), row);
            if (subjectURI.isEmpty()) break;

            Resource subject = rdfGraph.createResource(subjectURI).addProperty(RDF.type, entityMap.getClassType());
            generateTriplesFromRowColumns(subject, row, entityMap.listPredicateMaps());
        }
    }

    /**
     * Generate uri for a row using provided template and data.
     *
     * @param template to use to generate the uri.
     * @param row      containing the row data.
     * @return String uri of subject.
     */
    private String generateURIFromTemplate(String template, Map<String, String> row) {
        Matcher matcher = pattern.matcher(template);
        if (matcher.find()) {
            return template.replace(matcher.group(0), row.get(matcher.group(1)));
        }
        return "";
    }

    /**
     * Generate RDF Triples for each Column of a single Row that is specified
     * in each predicate and {@code ObjectMap}.
     *
     * @param subject of the Row (Or known as row/record id)
     * @param row     containing the row data to map to Triples.
     * @param pms     list to use to determine what data to map to Triples.
     */
    private void generateTriplesFromRowColumns(Resource subject, Map<String, String> row, List<PredicateMap> pms) {
        pms.forEach((pm) -> {
            Property predicate = rdfGraph.createProperty(pm.getPredicate());
            RDFNode object = rdfGraph.createLiteral(row.get(pm.getObjectMap().getObjectSource()));
            subject.addProperty(predicate, object);
        });
    }
}