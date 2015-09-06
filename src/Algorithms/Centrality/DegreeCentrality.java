package Algorithms.Centrality;

import edu.uci.ics.jung.graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Calculator of the degree centrality.
 *
 * The degree centrality C(V), for a given graph G and any vertex V, is defined as
 * C(V) = deg(V)
 *
 * Bigger the degree centrality it has, more central vertex it is.
 *
 * Notes
 *
 * All properties are stored when creating a new instance, and all methods return cached properties as default.
 * Therefore, if graph is edited after creating a instance, call reset() in order to update properties.
 * Or you can specify whether results are cached or not when creating an instance.
 *
 * Created by mitsunobu on 15/09/05.
 */
public class DegreeCentrality<V,E> implements Centrality<V> {

    private Graph<V,E> graph;
    private List<V> centralVertices;
    private Map<V, Double> map;
    private boolean cached = true;

    /**
     * Create a new instance with a given graph.
     * @param graph a given graph
     */
    public DegreeCentrality (Graph<V,E> graph) {
        this.graph = graph;
        calculate();
    }

    /**
     * Create a new instance with a given graph
     * @param graph a given graph
     * @param cached specifies whether the result is cached or not
     */
    public DegreeCentrality (Graph<V,E> graph, boolean cached) {
        this.graph = graph;
        this.cached = cached;
        if (cached) {
            calculate();
        }
    }

    /**
     * Return the centrality of target vertex
     * @param target target vertex
     * @return the centrality of the target. return null if target vertex is not an element of a given graph.
     */
    @Override
    public double getCentrality(V target) {
        // immediately return if result is cached
        if (cached) {
            return map.get(target);
        }

        // calculate and return results
        reset();
        return map.get(target);
    }

    /**
     * Return the list of central vertices, vertices whose degree is maximum in a given graph
     * @return the list of central vertices
     */
    @Override
    public List<V> getCentralVertices() {
        // immediately return if result is cached
        if (cached) {
            return centralVertices;
        }

        // calculate and return results
        reset();
        return centralVertices;
    }

    /**
     * Return the Map which maps each vertex in the graph to its degree
     * @return the Map which maps each vertex in the graph to its degree
     */
    @Override
    public Map<V, Double> getCentralMap() {
        // immediately return if result is cached
        if (cached) {
            return map;
        }

        // calculate and return results
        reset();
        return map;
    }

    /**
     * Reset all caches and recalculate
     */
    public void reset() {
       centralVertices = null;
       map = null;
       calculate();
    }

    /**
     * Calculate and store central vertices and the map
     */
    private void calculate() {
        // calculate and store central vertices
        int maximumDegree = Integer.MIN_VALUE;
        for(V v: graph.getVertices()) {
            int degree = graph.degree(v);
            if (degree > maximumDegree) {
                centralVertices = new ArrayList<V>();
                centralVertices.add(v);
                maximumDegree = degree;
            } else if (degree == maximumDegree) {
                centralVertices.add(v);
                maximumDegree = degree;
            } else {
                // nothing
            }
        }

        // create the Map which maps each vertex in the graph to its degree
        map = new HashMap<V, Double>();
        for(V v: graph.getVertices()) {
            map.put(v, (double)graph.degree(v));
        }
    }

}
