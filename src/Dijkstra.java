
import java.io.*;
import java.util.*;

class Graph {
	
	static String resultPath = "";
	private final Map<String, Vertex> graph; // mapping of vertex names to Vertex objects, built from a set of Edges

	/** One edge of the graph (only used by Graph constructor) */
	public static class Edge {
		public String from, to;
		public int weight;
		public Edge() {
			this.from = "";
			this.to = "";
			this.weight = 0;
		};
		
		public Edge(String from, String to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
	}

	/** One vertex of the graph, complete with mappings to neighbouring vertices */
	public static class Vertex implements Comparable<Vertex> {
		public final String name;
		public int dist = Integer.MAX_VALUE; // MAX_VALUE assumed to be infinity
		public Vertex previous = null;
		public final Map<Vertex, Integer> neighbours = new HashMap<>();

	public Vertex(String name) {
		this.name = name;
	}

	private String printPath() {
		String result = new String();
		if (this == this.previous) {
			resultPath = resultPath + this.name;
		} else if (this.previous == null) {
			resultPath = resultPath + "(unreached) " + this.name;
		} else {
			this.previous.printPath();
			resultPath = resultPath + " -> " + this.name + "(" + this.dist + ")";
		}
		result = resultPath;
		return result;
	}

	public int compareTo(Vertex other) {
		return Integer.compare(dist, other.dist);
	}
	}

	/** Builds a graph from a set of edges */
	public Graph(Edge[] edges) {
		graph = new HashMap<>(edges.length);

		//one pass to find all vertices
		for (Edge e : edges) {
			if (!graph.containsKey(e.from)) graph.put(e.from, new Vertex(e.from));
			if (!graph.containsKey(e.to)) graph.put(e.to, new Vertex(e.to));
		}

		//another pass to set neighbouring vertices
		for (Edge e : edges) {
			graph.get(e.from).neighbours.put(graph.get(e.to), e.weight);
			//graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
		}
	}

	/** Runs dijkstra using a specified source vertex */
	public void dijkstra(String startName) {
	if (!graph.containsKey(startName)) {
		System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
		return;
	}
	final Vertex source = graph.get(startName);
	NavigableSet<Vertex> q = new TreeSet<>();

	// set-up vertices
	for (Vertex v : graph.values()) {
		v.previous = v == source ? source : null;
		v.dist = v == source ? 0 : Integer.MAX_VALUE;
		q.add(v);
	}

	dijkstra(q);
	}

	/** Implementation of dijkstra's algorithm using a binary heap. */
	private void dijkstra(final NavigableSet<Vertex> q) { 
		Vertex u, v;
		while (!q.isEmpty()) {

			u = q.pollFirst(); // vertex with shortest distance (first iteration will return source)
			if (u.dist == Integer.MAX_VALUE) break; // we can ignore u (and any other remaining vertices) since they are unreachable

			//look at distances to each neighbour
			for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
				v = a.getKey(); //the neighbour in this iteration

				final int alternateDist = u.dist + a.getValue();
				if (alternateDist < v.dist) { // shorter path to neighbour found
					q.remove(v);
					v.dist = alternateDist;
					v.previous = u;
					q.add(v);
				} 
			}
		}
	}

	/** Prints a path from the source to the specified vertex 
	 * @return */
	public String printPath(String endName) {
		if (!graph.containsKey(endName)) {
			System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
			return null;
		}
	resultPath = "";
	String result = graph.get(endName).printPath();
	return result;
	}
	/** Prints the path from the source to every vertex (output order is not guaranteed) */
}
