import java.util.*;

public class ListGraph implements Graph {
    private HashMap<String, LinkedList<String>> nodes = new HashMap<>();

    public boolean addNode(String n) {
        if (nodes.containsKey(n)){
            return false;
    }
        LinkedList<String> l= new LinkedList<>();
        nodes.put(n,l);
        return true;
    }

    public boolean addEdge(String n1, String n2) {

        boolean flag = nodes.containsKey(n1) && nodes.containsKey(n2);
        if (flag) {
            for (String n : nodes.get(n1)) {
                if (n.equals(n2)) {
                    return false;
                }
            }
            nodes.get(n1).add(n2);
            return true;
        }
        throw new NoSuchElementException();
    }

    public boolean hasNode(String n) {
        return nodes.containsKey(n);
    }

    public boolean hasEdge(String n1, String n2) {
        if (!(nodes.containsKey(n1) && nodes.containsKey(n2))){ // edge case
            return false;
        }
        for (String n : nodes.get(n1)) {
            if (n.equals(n2)){
                return true;
            }
        }
        return false;
    }

    public boolean removeNode(String n) {
        if (!nodes.containsKey(n)){
            return false;
        }
        //remove incoming edges to n
        for (String name:nodes.keySet()) {
            nodes.get(name).remove(n); // remove the value from linkedList
        }
        // remove outgoing edges
        nodes.remove(n); // remove key from hashmap
        return true;
    }

    public boolean removeEdge(String n1, String n2) {
        if (nodes.containsKey(n1) && nodes.containsKey(n2)) {
            for (String n : nodes.get(n1)){
                if (n2.equals(n)){
                    nodes.get(n1).remove(n2);
                    return true;
                }
             return false;
            }

        }
        throw new NoSuchElementException();
    }

    public List<String> nodes() {
        List<String> listAllNodes = new ArrayList<String>();
        for (String name : nodes.keySet()) {
            listAllNodes.add(name);
        }
        return listAllNodes;
    }
    public List<String> succ(String n) {
	     if (nodes.containsKey(n)){
             List<String> successorList = new ArrayList<String>(nodes.get(n)); //casting
             return successorList;
        }
        throw new NoSuchElementException();
    }

    public List<String> pred(String n) {
        if (nodes.containsKey(n)){
            List<String> listPredNodes = new ArrayList<String>();
            for (String key : nodes.keySet()){ // shall we check for self loop?
                for(String val :  nodes.get(key)){
                    if (val.equals(n)){
                        listPredNodes.add(key);
                        break;
                    }
                }
                    }
            return listPredNodes;
        }
        throw new NoSuchElementException();
    }

    public Graph union(Graph g) {

        Graph newGraph = new ListGraph(); // Graph or ListGraph for lhs

        if (this.nodes.isEmpty() && g.nodes().isEmpty()){return newGraph;}//empty list
        else if (this.nodes.isEmpty() && !(g.nodes().isEmpty())){return g;}//
        else if (!(this.nodes.isEmpty()) && g.nodes().isEmpty()){return this;}//

        for (String key : this.nodes()) { // add nodes of current graph
            newGraph.addNode(key);
        }
        for (String key : g.nodes()) { // add nodes of graph g
            newGraph.addNode(key);
        }
        for (String key : this.nodes()) { // add edges, successor of current graph
            List<String> successors = this.succ(key);
            if (!successors.isEmpty()) {
                for (String a_key : successors) {
                    newGraph.addEdge(key, a_key);
                }
            }
        }

        for (String key : g.nodes()) { // add edges, successor of graph g
            List<String> successors = g.succ(key);
            if (!successors.isEmpty()) {
                for (String a_key : successors) {
                    newGraph.addEdge(key, a_key);
                }
            }
        }
        return newGraph;
    }

    public Graph subGraph(Set<String> nodes) {

        Graph newGraph = new ListGraph(); //Graph or ListGraph for lhs

        if (nodes.isEmpty()) {return newGraph;}
        if (this.nodes().isEmpty()){return this;}

        // Also check nodes in the passed argument has valid nodes in current graph
        for (String a_node : nodes){
            if (this.nodes().contains(a_node)){
            newGraph.addNode(a_node);
            }
        }
        for (String a_node:newGraph.nodes()){
            List<String> temp = this.succ(a_node);
            for (String element : temp){
                if (newGraph.nodes().contains(element)){
                    newGraph.addEdge(a_node, element);
                }
            }
        }
        return newGraph;
    }

    public boolean connected(String n1, String n2) { // BFS

        if (!(nodes.containsKey(n1) && nodes.containsKey(n2))){throw new NoSuchElementException();}

        if (n1.equals(n2)){return true;}

        Set<String> visited = new LinkedHashSet<String>();
        Queue<String> queue = new LinkedList<String>();

        queue.add(n1);
        visited.add(n1);

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            for (String v : nodes.get(vertex)) {
                if (!visited.contains(v)) {
                    if (v.equals(n2)){return true;}
                    visited.add(v);
                    queue.add(v);
                }
            }
        }
        return false;

    }
}
