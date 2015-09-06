package Algorithms.GraphGenerators;

import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.graph.Graph;

import java.util.ArrayList;

/**
 * The generator of grid graph.
 *
 *  + - + - + -
 *  |   |   |
 *  + - + - + -
 *  |   |   |
 *  + - + - + -
 *  |   |   |
 *
 * Created by mitsunobu on 15/09/05.
 */
public class GridGraphGenerator<V,E> implements GraphGenerator<V,E>, org.apache.commons.collections15.Factory<Graph<V,E>> {

    private org.apache.commons.collections15.Factory<Graph<V,E>> graphFactory;
    private org.apache.commons.collections15.Factory<V> vertexFactory;
    private org.apache.commons.collections15.Factory<E> edgeFactory;
    private int numVertices;

    /**
     * Create a new instance of the generator
     * @param graphFactory the factory to use to generate the graph
     * @param vertexFactory the factory to use to create vertex
     * @param edgeFactory the factory to use to create edge
     * @param numVertices the number of vertices for the generated graph. it must be more than 4 and form n^2.
     */
    public GridGraphGenerator (org.apache.commons.collections15.Factory<Graph<V,E>> graphFactory,
                               org.apache.commons.collections15.Factory<V> vertexFactory,
                               org.apache.commons.collections15.Factory<E> edgeFactory,
                               int numVertices) {
        this.graphFactory = graphFactory;
        this.vertexFactory = vertexFactory;
        this.edgeFactory = edgeFactory;
        this.numVertices = numVertices;
    }

    /**
     * create grid graph
     *
     * this is how to create grid graph
     * 0 - 1 - 4 -
     * |   |   |
     * 3 - 2 - 5 -
     * |   |   |
     * 8 - 7 - 6 -
     * |   |   |
     *
     *
     * @return grid graph
     */
    @Override
    public Graph<V, E> create() {
        Graph<V,E> graph = graphFactory.create();
        int column = (int) Math.sqrt(numVertices);
        int i = 3;
        int m = 3;

        // create all vertices
        ArrayList<V> vList = new ArrayList<V>();
        for(int j = 0; j < numVertices ; j++) vList.add(vertexFactory.create());

        // add first four vertices and edges
        for(int j = 0; j < 4; j++) graph.addVertex(vList.get(0));
        graph.addEdge(edgeFactory.create(), vList.get(0), vList.get(1));
        graph.addEdge(edgeFactory.create(), vList.get(1), vList.get(2));
        graph.addEdge(edgeFactory.create(), vList.get(2), vList.get(3));
        graph.addEdge(edgeFactory.create(), vList.get(0), vList.get(3));

        // add other vertices and edges
        for(int j = 2; j < column; j++){
            graph.addVertex(vList.get(++i));
            graph.addEdge(edgeFactory.create(), vList.get(i-m), vList.get(i));

            for(int k = 1; k < j; k++){
                graph.addVertex(vList.get(++i));
                graph.addEdge(edgeFactory.create(), vList.get(i-m), vList.get(i));
                graph.addEdge(edgeFactory.create(), vList.get(i-1), vList.get(i));
            }

            graph.addVertex(vList.get(++i));
            graph.addEdge(edgeFactory.create(), vList.get(i-1), vList.get(i));

            m += 2;

            for(int k = 0; k < j; k++){
                graph.addVertex(vList.get(++i));
                graph.addEdge(edgeFactory.create(), vList.get(i-m), vList.get(i));
                graph.addEdge(edgeFactory.create(), vList.get(i-1), vList.get(i));
            }
        }
        return graph;
    }
}
