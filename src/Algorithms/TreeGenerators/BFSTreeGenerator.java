package Algorithms.TreeGenerators;

import edu.uci.ics.jung.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * The generator of BFS tree as a subgraph of a given graph.
 *
 *
 * References
 *
 * Breadth-first search. (n.d.).
 * In Wikipedia. Retrieved September 5, 2015, from
 * https://en.wikipedia.org/wiki/Breadth-first_search
 *
 * Created by mitsunobu on 15/09/05.
 */
public class BFSTreeGenerator<V,E> implements TreeGenerator<V,E> {

    private Graph<V,E> graph;
    private org.apache.commons.collections15.Factory<Graph<V,E>> treeFactory;
    private V rootVertex;

    /**
     * Create a new instance of the generator
     * @param graph a given graph
     * @param treeFactory the factory to use to generate the tree
     * @param rootVertex the vertex which is a starting point of the algorithm
     */
    public BFSTreeGenerator(Graph<V,E> graph,
                            org.apache.commons.collections15.Factory<Graph<V,E>> treeFactory,
                            V rootVertex) {
        this.graph = graph;
        this.treeFactory = treeFactory;
        this.rootVertex = rootVertex;
    }

    /**
     * Create the BFS tree as a subgraph of a given graph
     * @return the BFS tree
     */
    @Override
    public Graph<V, E> create() {
        Graph<V, E> tree = treeFactory.create();

        Deque<V> queue = new ArrayDeque<V>();
        ArrayList<V> didVisitedNodeArray = new ArrayList<V>();

        tree.addVertex(rootVertex);
        queue.offer(rootVertex);

        while(!queue.isEmpty()){
            V target = queue.poll();
            V nextVertex = null;

            boolean hasUnvisitedNeighbor = true;

            while(hasUnvisitedNeighbor){
                hasUnvisitedNeighbor = false;
                for(V v : graph.getNeighbors(target)){
                    if(!didVisitedNodeArray.contains(v)){
                        hasUnvisitedNeighbor = true;
                        nextVertex = v;
                    }
                }
                if(hasUnvisitedNeighbor){
                    tree.addVertex(nextVertex);
                    didVisitedNodeArray.add(nextVertex);
                    E e = graph.findEdge(target, nextVertex);
                    tree.addEdge(e, target, nextVertex);
                    queue.offer(nextVertex);
                }
            }
            didVisitedNodeArray.add(target);
        }

        return tree;
    }
}
