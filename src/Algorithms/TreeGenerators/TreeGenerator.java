package Algorithms.TreeGenerators;

import edu.uci.ics.jung.graph.Graph;

/**
 * An interface for algorithms that generate trees.
 *
 * Created by mitsunobu on 15/09/05.
 */
public interface TreeGenerator<V,E> extends org.apache.commons.collections15.Factory<Graph<V,E>> {
    /**
     * create tree
     * @return tree
     */
    @Override
    Graph<V, E> create();
}
