/*
 * Copyright (c) 2019 - GraphLoom contributors (github.com/jiefenn8/graphloom
 * This software is made available under the terms of Apache License, Version 2.0.
 */

package com.github.jiefenn8.graphloom.api;

/**
 * This interface defines the base methods to manage a collection that contains
 * multiple record with the same properties.
 */
@Deprecated
public interface EntityRecord extends Iterable<Record> {

    /**
     * Returns the number of records in this collection. If this
     * set contains more than the {@link Integer#MAX_VALUE} elements,
     * returns the max value possible of Integer.
     *
     * @return the number of elements in this collection
     */
    int size();

    /**
     * Returns true if this collection contains the given Record.
     *
     * @param record the record to be searched in collection
     * @return true if this collection contains the given record
     * @throws NullPointerException if the given record is null and this
     *                              collection does not allow null elements
     */
    boolean containsRecord(Record record);

    /**
     * Removes all the records from this collection. The collection
     * will be empty after this call returns.
     */
    void clear();

    /**
     * Removes the given record from this collection if it does exist.
     * Returns true if this collection contains given record and is removed.
     *
     * @param record the record to be removed from collection
     * @return true if the given record exists and is removed
     */
    boolean removeRecord(Record record);

    /**
     * Adds the given record to this collection if it does not exist.
     * If this collection already contains previous record with different
     * properties, the method will leave the collection unchanged and
     * returns false.
     *
     * @param record the record to be added to the collection
     * @return true if the given record element does not exist and is added
     */
    boolean addRecord(Record record);

    /**
     * Returns true if this collection contains no records.
     *
     * @return true if this collection contains no records
     */
    boolean isEmpty();

    /**
     * Returns the number of columns in this entity record.
     *
     * @return the number of columns
     */
    int columnSize();

    /**
     * Compares the given object with this collection for equality. Returns
     * true if the given object is also a collection that has the same
     * amount of records and their values associated with them.
     *
     * @param obj the object to be compared for equality with this collection
     * @return true if given object is equal to this collection
     */
    @Override
    boolean equals(Object obj);

    /**
     * Returns the hash code value for this collection. The hash code of
     * this collection is defined to be the sum of the record elements.
     *
     * @return the hash code value for this collection
     * @see Object#equals(Object)
     */
    @Override
    int hashCode();
}
