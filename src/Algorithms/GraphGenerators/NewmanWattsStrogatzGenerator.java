package Algorithms.GraphGenerators;

import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.graph.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * The generator of Newman-watts-strogatz small world graph.
 *
 *
 * References
 *
 * M. E. J. Newman and D. J. Watts,
 * Renormalization group analysis of the small-world network model,
 * Physics Letters A, 263, 341, 1999.
 * http://dx.doi.org/10.1016/S0375-9601(99)00757-4
 *
 * Created by mitsunobu on 15/09/05.
 */
public class NewmanWattsStrogatzGenerator<V,E> implements GraphGenerator<V,E>, org.apache.commons.collections15.Factory<Graph<V,E>> {

    private org.apache.commons.collections15.Factory<Graph<V,E>> graphFactory;
    private org.apache.commons.collections15.Factory<V> vertexFactory;
    private org.apache.commons.collections15.Factory<E> edgeFactory;
    private int numVertices;
    private int k;
    private double p;

    /**
     * Create a new instance of the generator
     * @param graphFactory the factory to use to generate the graph
     * @param vertexFactory the factory to use to create vertex
     * @param edgeFactory the factory to use to create edge
     * @param numVertices the number of vertices for the generated graph
     * @param k each node is connected to k nearest neighbors in ring topology
     * @param p the probability of adding a new edge for each edge
     */
    public NewmanWattsStrogatzGenerator (org.apache.commons.collections15.Factory<Graph<V,E>> graphFactory,
                                         org.apache.commons.collections15.Factory<V> vertexFactory,
                                         org.apache.commons.collections15.Factory<E> edgeFactory,
                                         int numVertices,
                                         int k,
                                         double p) {
        this.graphFactory = graphFactory;
        this.vertexFactory = vertexFactory;
        this.edgeFactory = edgeFactory;
        this.numVertices = numVertices;
        if (k % 2 == 1) {
            k = k - 1;
        }
        this.k = k;
        this.p = p;
    }

    /**
     * Generate Newman-watts-strogatz small world graph
     * @return Newman-watts-strogatz small world graph
     */
    @Override
    public Graph<V,E> create() {
        Graph<V,E> graph = graphFactory.create();
        List<V> vList = new ArrayList<V>();

        // create n vertices
        for(int i = 0; i < numVertices; i++) {
            vList.add(vertexFactory.create());
            graph.addVertex(vList.get(i));
        }

        // create a ring over k nodes
        for (int i = 0; i < numVertices; i++) {
            for(int j = 1; j <= k/2; j++) {
                graph.addEdge(edgeFactory.create(), vList.get(i), vList.get((i+j)%numVertices));
                graph.addEdge(edgeFactory.create(), vList.get(i), vList.get((i-j+numVertices)%numVertices));
            }
        }

        // add new edges with probability p
        //
        // v(i) already connects to v(i+1), v(i+2), ..., v(i + k/2). (when making cycle)
        // therefore, v(i) try to add edges between v(i) and from v(i + k/2 + 1) to v(numVertices).
        //
        // however, when i is small number, v(i) already connects to v(numVertices) or v(bigNumber).
        // to eliminate letting these combination try adding edges,
        // if j == (numVertices - k/2 + i), then break and go to next vertex.
        for (int i = 0; i < numVertices; i++) {
            for(int j = i + k/2 + 1; j < numVertices; j++) {
                if(j == (numVertices - k/2 + i)) {
                    break;
                }
                double random = Math.random();
                if (0 <= random && random <= p) {
                    graph.addEdge(edgeFactory.create(), vList.get(i), vList.get(j));
                }
            }
        }

        return graph;
    }
}
