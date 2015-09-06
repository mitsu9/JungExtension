package Algorithms.GraphGenerators;

import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.graph.Graph;

import java.util.ArrayList;

/**
 * The generator of ladder graph.
 *
 *  + - + - + -
 *  |   |   |
 *  + - + - + -
 *
 * Created by mitsunobu on 15/09/05.
 */
public class LadderGraphGenerator<V,E> implements GraphGenerator<V,E>, org.apache.commons.collections15.Factory<Graph<V,E>> {

    private org.apache.commons.collections15.Factory<Graph<V,E>> graphFactory;
    private org.apache.commons.collections15.Factory<V> vertexFactory;
    private org.apache.commons.collections15.Factory<E> edgeFactory;
    private int numVertices;

    /**
     * Create a new instance of the generator
     * @param graphFactory the factory to use to generate the graph
     * @param vertexFactory the factory to use to create vertex
     * @param edgeFactory the factory to use to create edge
     * @param numVertices the number of vertices for the generated graph
     */
    public LadderGraphGenerator (org.apache.commons.collections15.Factory<Graph<V,E>> graphFactory,
                                 org.apache.commons.collections15.Factory<V> vertexFactory,
                                 org.apache.commons.collections15.Factory<E> edgeFactory,
                                 int numVertices) {
        this.graphFactory = graphFactory;
        this.vertexFactory = vertexFactory;
        this.edgeFactory = edgeFactory;
        this.numVertices = numVertices;
    }

    /**
     * create ladder graph
     *
     * This is how to create ladder graph
     * 0 - 2 - 4 -
     * |   |   |
     * 1 - 3 - 5 -
     *
     * @return ladder graph
     */
    @Override
    public Graph<V, E> create() {
        Graph<V,E> graph = graphFactory.create();

        // make sure that the number of vertex is even number
        if (numVertices % 2 != 0) {
            numVertices++;
        }

        // create all vertices
        ArrayList<V> vList = new ArrayList<V>();
        for(int j = 0; j < numVertices ; j++) vList.add(vertexFactory.create());

        // add first two vertices and edges
        graph.addVertex(vList.get(0));
        graph.addVertex(vList.get(1));
        graph.addEdge(edgeFactory.create(), vList.get(0), vList.get(1));

        // add other vertices and edges like this
        //     - v
        // {G}   |
        //     - v
        int nodeIndex = 1;
        int ladderLength = (numVertices - 2) /2 ;
        for(int j = 0; j < ladderLength ; j++){
            graph.addVertex(vList.get(++nodeIndex));
            graph.addEdge(edgeFactory.create(), vList.get(nodeIndex-2), vList.get(nodeIndex));

            graph.addVertex(vList.get(++nodeIndex));
            graph.addEdge(edgeFactory.create(), vList.get(nodeIndex-2), vList.get(nodeIndex));
            graph.addEdge(edgeFactory.create(), vList.get(nodeIndex-1), vList.get(nodeIndex));
        }

        return graph;
    }
}
