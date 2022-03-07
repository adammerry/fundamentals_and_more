package classicProblems;

import dataStructures.GraphGeneric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Kruskal's algorithm to find a minimum spanning tree for a weighted,
 * undirected graph. Uses a custom implementation of a union-find data structure to store
 * connected components as the algorithm executes.
 */
public class Kruskal {

    /**
     * A union-find data structure used to store the connected components created by Kruskal's.
     */
    private static class UnionFind<E> {
        private final Map<GraphGeneric<E>.Node, GraphGeneric<E>.Node> reps = new HashMap<>();
        private final Map<GraphGeneric<E>.Node, Integer> ranks = new HashMap<>();

        /**
         * Create the initial forest of one-node trees.
         * @param graph the graph on which Kruskal's algorithm will be performed
         */
        private void initialize(GraphGeneric<E> graph) {
            for (GraphGeneric<E>.Node node : graph.getNodes()) {
                reps.put(node, node);
                ranks.put(node, 0);
            }
        }

        /**
         * Find the representative node for the connected component that contains the given node.
         * This implementation uses the "Path Compression" enhancement, which dictates that each
         * node on the path to the root of the component tree will have its representative changed
         * to the root of the tree. This will cause every node in the path to sit one level below
         * the root, thus flattening a portion of the tree and improving runtimes for operations
         * on nodes in the flattened path.
         * @param node the node for which the representative is desired
         * @return the representative node
         */
        private GraphGeneric<E>.Node find(GraphGeneric<E>.Node node) {
            if (reps.get(node) != node) reps.put(node, find(reps.get(node)));
            return reps.get(node);
        }

        /**
         * Combine the trees of the two given nodes into one. This implementation uses the
         * enhancement of "Union by Rank", which dictates that when combining two trees, the
         * smaller tree is always attached to the root of the larger tree. Thus, the height of
         * any tree only increases when it is combined with a tree of equal height. This reduces
         * the worst-case runtime to O(log(n)) for both Union and Find operations.
         * @param node1 a node in the graph
         * @param node2 a node in the graph
         */
        private void union(GraphGeneric<E>.Node node1, GraphGeneric<E>.Node node2) {
            GraphGeneric<E>.Node rep1 = find(node1), rep2 = find(node2);
            if (rep1 != rep2) {
                int rank1 = ranks.get(rep1), rank2 = ranks.get(rep2);
                if (rank1 > rank2) reps.put(rep2, rep1);
                else reps.put(rep1, rep2);
                if (rank1 == rank2) ranks.put(rep2, ranks.get(rep2) + 1);
            }
        }
    }

    /**
     * Performs Kruskal's algorithm to find a minimum spanning tree of the given graph.
     * @param graph a graph
     * @param <E> the type of data contained in each node
     * @return a minimum spanning tree
     */
    public static <E> Set<GraphGeneric<E>.Edge> runKruskal(GraphGeneric<E> graph) {
        UnionFind<E> unionFind = new UnionFind<>();
        unionFind.initialize(graph);
        List<GraphGeneric<E>.Edge> edges = new ArrayList<>(graph.getEdges());
        edges.sort((GraphGeneric<E>.Edge e1, GraphGeneric<E>.Edge e2) ->
                e1.getWeight() - e2.getWeight());
        Set<GraphGeneric<E>.Edge> minSpanningTree = new HashSet<>();
        int numNodes = graph.nodeCount();
        for (GraphGeneric<E>.Edge edge : edges) {
            GraphGeneric<E>.Node node1 = edge.getNode1(), node2 = edge.getNode2();
            if (unionFind.find(node1) != unionFind.find(node2)) {
                minSpanningTree.add(edge);
                unionFind.union(node1, node2);
            }
            if (minSpanningTree.size() == numNodes - 1) break; // A spanning tree has been found.
        }
        return minSpanningTree;
    }
}
