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

package com.github.jiefenn8.graphloom.rdf.r2rml;

import com.github.jiefenn8.graphloom.api.RelationMap;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import java.util.Map;

/**
 * Implementation of R2RML PredicateMap with {@link RelationMap} interface.
 * This term map will return either a rr:IRI for its main term.
 */
public class PredicateMap extends BaseTermMap implements RelationMap {

    //Constant PredicateMap
    public PredicateMap(TermMapType type, Property predicate) {
        super(type, predicate);
    }

    //Template PredicateMap
    public PredicateMap(TermMapType type, String template) {
        super(type, template, TermType.IRI);
    }

    @Override
    public Property generateRelationTerm(Map<String, String> entityRecords) {
        Resource term = generateRDFTerm(entityRecords).asResource();
        return ResourceFactory.createProperty(term.getURI());
    }
}
