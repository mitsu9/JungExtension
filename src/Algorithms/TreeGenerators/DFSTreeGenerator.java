package Algorithms.TreeGenerators;

import edu.uci.ics.jung.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * The generator of DFS tree as a subgraph of a given graph.
 *
 *
 * References
 *
 * Depth-first search. (n.d.).
 * In Wikipedia. Retrieved September 5, 2015, from
 * https://en.wikipedia.org/wiki/Depth-first_search
 *
 * Created by mitsunobu on 15/09/05.
 */
public class DFSTreeGenerator<V,E> implements TreeGenerator<V,E> {

    private Graph<V,E> graph;
    private org.apache.commons.collections15.Factory<Graph<V,E>> treeFactory;
    private V rootVertex;

    /**
     * Create a new instance of the generator
     * @param graph a given graph
     * @param treeFactory the factory to use to generate the tree
     * @param rootVertex the vertex which is a starting point of the algorithm
     */
    public DFSTreeGenerator(Graph<V,E> graph,
                            org.apache.commons.collections15.Factory<Graph<V,E>> treeFactory,
                            V rootVertex) {
        this.graph = graph;
        this.treeFactory = treeFactory;
        this.rootVertex = rootVertex;
    }

    /**
     * Create the DFS tree as a subgraph of a given graph
     * @return the DFS tree
     */
    @Override
    public Graph<V, E> create() {
        Graph<V, E> tree = treeFactory.create();

        Deque<V> stack = new ArrayDeque<V>();
        ArrayList<V> didVisitedNodeArray = new ArrayList<V>();

        stack.push(rootVertex);

        while(!stack.isEmpty()){
            V target = stack.pop();
            tree.addVertex(target);
            didVisitedNodeArray.add(target);

            boolean hasUnvisitedNeighbor = false;
            V nextVertex = null;

            for(V n : graph.getNeighbors(target)){
                if(!didVisitedNodeArray.contains(n)){
                    stack.push(target);
                    nextVertex = n;
                    hasUnvisitedNeighbor = true;
                }
            }

            if(hasUnvisitedNeighbor){
                E e = graph.findEdge(target, nextVertex);
                tree.addEdge(e, target, nextVertex);
                stack.push(nextVertex);
            }
        }

        return tree;
    }
}
