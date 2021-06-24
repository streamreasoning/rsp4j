package org.streamreasoning.rsp4j.io.utils.serialization;

/**
 * Strategy for serializing object to String, e.g. RDF graphs or triples
 *
 * @param <T>  type of object that needs serialization.
 */
@FunctionalInterface
public interface StringSerializationStrategy<T> {

    /**
     * Serializes an object to string.
     *
     * @param object object that needs serialization
     * @return  string representation of the object
     */
    public String serialize(T object);
}
