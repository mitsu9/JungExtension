package Algorithms.Centrality;

import java.util.List;
import java.util.Map;

/**
 * An interface for classes which calculate the centrality of one vertex.
 *
 * Created by mitsunobu on 15/09/05.
 */
public interface Centrality<V> {

    /**
     * Return the centrality of the target vertex
     */
    double getCentrality (V target);

    /**
     * Return the central vertices. Note that several vertices can be central.
     */
    List<V> getCentralVertices ();

    /**
     * Return the Map which maps each vertex in the graph to its centrality
     */
    Map<V, Double> getCentralMap ();
}
