package Algorithms.Centrality;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Calculator of the eccentricity centrality.
 *
 * The eccentricity centrality C(V) is defined as the maximum distance between V and any other vertex u of graph
 * Therefore, it can be formulated as follows:
 * C(V) = max(distance(V, U))
 *
 * Smaller the eccentricity centrality it has, more central vertex it is.
 * Moreover, the minimum eccentricity is called the radius.
 *
 *
 * Notes
 *
 * All properties are stored when creating a new instance, and all methods return cached properties as default.
 * Therefore, if graph is edited after creating a instance, call reset() in order to update properties.
 * Or you can specify whether results are cached or not when creating an instance.
 *
 * Created by mitsunobu on 15/09/05.
 */
public class EccentricityCentrality<V,E> implements Centrality<V> {

    private Graph<V,E> graph;
    private List<V> centralVertices;
    private Map<V, Double> map;
    private boolean cached = true;

    /**
     * Create a new instance with a given graph.
     * @param graph a given graph
     */
    public EccentricityCentrality(Graph<V, E> graph) {
        this.graph = graph;
        calculate();
    }

    /**
     * Create a new instance with a given graph.
     * @param graph a given graph
     * @param cached specifies whether the result is cached or not
     */
    public EccentricityCentrality(Graph<V, E> graph, boolean cached) {
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
     * Return the list of central vertices, vertices whose eccentricity is minimum in a given graph
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
     * Return the Map which maps each vertex in the graph to its eccentricity
     * @return the Map which maps each vertex in the graph to its eccentricity
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
        this.centralVertices = null;
        this.map = null;
        calculate();
    }

    /**
     * Calculate and store central vertices and the map
     */
    private void calculate() {
        // create the Map which maps each vertex in the graph to its radius
        for (V target: graph.getVertices()) {
            DijkstraDistance dd = new DijkstraDistance<V,E>(graph);
            double eccentricity = 0;
            for(V v: graph.getVertices()){
                Number r = 0;
                r = dd.getDistance(target, v);
                if(eccentricity < r.intValue()){
                    eccentricity = r.intValue();
                }
            }
            map.put(target, eccentricity);
        }

        // calculate and store central vertices
        int radius = Integer.MAX_VALUE;
        for (V v: graph.getVertices()) {
            int eccentricity = map.get(v).intValue();
            if (eccentricity < radius) {
                centralVertices = new ArrayList<V>();
                centralVertices.add(v);
                radius = eccentricity;
            } else if (eccentricity == radius) {
                centralVertices.add(v);
                radius = eccentricity;
            } else {
                // nothing
            }
        }
    }


}
