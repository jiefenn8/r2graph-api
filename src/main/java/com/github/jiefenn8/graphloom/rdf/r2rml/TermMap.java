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

import org.apache.jena.rdf.model.RDFNode;

import java.util.Map;

public interface TermMap {

    public enum TermMapType {
        CONSTANT, TEMPLATE, COLUMN
    }

    public enum TermType {
        IRI, BLANK, LITERAL
    }

    TermMapType getTermMapType();

    RDFNode generateRDFTerm(Map<String, String> entityRow);

    RDFNode generateConstantTerm();

    RDFNode generateTemplateTerm(Map<String, String> entityRow);

    RDFNode generateColumnTerm(Map<String, String> entityRow);
}
