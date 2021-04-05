import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EdgeGraphAdapter implements EdgeGraph {

    private Graph g;

    EdgeGraphAdapter(Graph g) {
        this.g = g;
    }

    public boolean addEdge(Edge e) {

        String node_s = e.getSrc();
        String node_d = e.getDst();

        g.addNode(node_s);
        g.addNode(node_d);

        if (!g.hasEdge(node_s, node_d)) {
                // ! check if it can be optimized?! the g has the option to  throw exception
                g.addEdge(node_s, node_d);
                return true;
        }
        return false;
    }

    public boolean hasNode(String n) {
        return g.hasNode(n);
    }

    public boolean hasEdge(Edge e) {
        String node_s = e.getSrc();
        String node_d = e.getDst();
        return g.hasEdge(node_s, node_d);
    }

    public boolean removeEdge(Edge e) {
        String node_s = e.getSrc();
        String node_d = e.getDst();
        boolean flag = false;
        try {
            flag = g.removeEdge(node_s, node_d);
        } catch (NoSuchElementException exception) {
            /* System.out.println("Non exception is required"); */
            return false;
        }
        if (g.succ(node_s).isEmpty() && g.pred(node_s).isEmpty()) {
            g.removeNode(node_s);
        }
        if (g.succ(node_d).isEmpty() && g.pred(node_d).isEmpty()) {
            g.removeNode(node_d);
        }

        return flag;
    }

    public List<Edge> outEdges(String n) {
        List<Edge> edges = new ArrayList<Edge>();
        try {
            List<String> vertices = g.succ(n);
            for (String vertex : vertices) {
                Edge e = new Edge(n, vertex);
                edges.add(e);
            }
        } catch (NoSuchElementException exception) {
           // System.out.println("Non exception is required");
            return edges; // return empty list
        }
        return edges;
    }

    public List<Edge> inEdges(String n) {
        List<Edge> edges = new ArrayList<Edge>();
        try {
            List<String> vertices = g.pred(n);
            for (String vertex : vertices) {
                Edge e = new Edge(vertex, n);
                edges.add(e);
            }
        } catch (NoSuchElementException exception) {
            //System.out.println("Non exception is required");
            return edges;// return empty list
        }
        return edges;
    }

    public List<Edge> edges() {
        List<Edge> allEdges = new ArrayList<>();
        for (String a_node : g.nodes()) {
            // Q. instead of this, using g.succ(a_node)?
            allEdges.addAll(this.outEdges(a_node));
        }
        return allEdges;
    }

    public EdgeGraph union(EdgeGraph g) {
        Graph t = new ListGraph();
        EdgeGraph new_graph = new EdgeGraphAdapter(t);// EdgeGraph or EdgeGraphAdapter for lhs
        if (this.edges().isEmpty() || g.edges().isEmpty()){
            if (!this.edges().isEmpty()){return g;}
            if (!g.edges().isEmpty()){return this;}
            else{return new_graph;}
        }
        for (Edge edge : this.edges()) {
            new_graph.addEdge(edge);
        }
        for (Edge edge : g.edges()) {
            new_graph.addEdge(edge);

        }
        return new_graph;
    }

    public boolean hasPath(List<Edge> e) {
        for (Edge an_e : e) {
            if (!this.hasEdge(an_e)) {
                return false;
        }
    }
        for (int i = 0; i < e.size() - 1; i++) {
            if (!e.get(i).getDst().equals(e.get(i + 1).getSrc())) {
                throw new BadPath();
            }
        }
        return true;
    }

}
