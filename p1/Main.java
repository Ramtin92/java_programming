import java.util.*;

public class Main {

    // Run "java -ea Main" to run with assertions enabled (If you run
    // with assertions disabled, the default, then assert statements
    // will not execute!)

    public static void test1() {
        Graph g = new ListGraph();
        assert g.addNode("a");
        assert g.hasNode("a");
    }

    public static void test2() {
        Graph g = new ListGraph();
        EdgeGraph eg = new EdgeGraphAdapter(g);
        Edge e = new Edge("a", "b");
        assert eg.addEdge(e);
        assert eg.hasEdge(e);

    }
//test function for connected method
    public static void testConnected(){
        Graph g = new ListGraph();
        assert g.addNode("a");
        assert g.addNode("b");
        assert g.addNode("c");
        assert g.addNode("d");
        assert g.addNode("e");
        assert g.addNode("f");
        assert g.addEdge("a", "b");
        assert g.addEdge("b", "c");
        assert g.addEdge("c", "d");
        assert g.addEdge("e", "f");

        assert g.connected("a", "b"); //should return true
        assert g.connected("a", "d");// should return true
        assert g.removeNode("f");
        assert g.connected("a", "f"); //should throw exception

    }

    public static void main(String[] args){
        test1();
        test2();
        testConnected();
    }

}