import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Stack;
import org.objectweb.asm.commons.*;
import org.objectweb.asm.tree.*;

public class CFG {
    Set<Node> nodes = new HashSet<Node>();
    Map<Node, Set<Node>> edges = new HashMap<Node, Set<Node>>();

    static class Node {
	int position;
	MethodNode method;
	ClassNode clazz;

	Node(int p, MethodNode m, ClassNode c) {
	    position = p; method = m; clazz = c;
	}

	public boolean equals(Object o) {
	    if (!(o instanceof Node)) return false;
	    Node n = (Node)o;
	    return (position == n.position) &&
		method.equals(n.method) && clazz.equals(n.clazz);
	}

	public int hashCode() {
	    return position + method.hashCode() + clazz.hashCode();
	}

	public String toString() {
	    return clazz.name + "." +
		method.name + method.signature + ": " + position;
	}
    }

    public void addNode(int p, MethodNode m, ClassNode c) {
    	Node newNode = new Node(p, m, c);
    	for (Node n : nodes) {
    		if ( n.equals(newNode) ) {return;}
		}
		nodes.add(newNode);
    }

    public void addEdge(int p1, MethodNode m1, ClassNode c1,
			int p2, MethodNode m2, ClassNode c2) {

		Node node1 = new Node(p1, m1, c1);
		Node node2 = new Node(p2, m2, c2);
		if (!nodes.contains(node1)){ nodes.add(node1); }
		if (!nodes.contains(node2)){ nodes.add(node2); }

		if (edges.get(node1) != null)
			edges.get(node1).add(node2);
		else{
			Set<Node> tempNodes = new HashSet<Node>();
			tempNodes.add(node2);
			edges.put(node1, tempNodes);
		}

    }
	
	public void deleteNode(int p, MethodNode m, ClassNode c) {

		Node node = new Node(p, m, c);
		//for (Node nn : nodes) {
			//if (nn.equals(node)){
		if (edges.get(node) != null){
			edges.remove(node);
		}
		nodes.remove(node);

		for (Node n : nodes) {
		    if (edges.get(n) != null){
				if (edges.get(n).contains(node)) { 
					edges.get(n).remove(node); 
					deleteEdge(n.position, n.method, n.clazz,
						node.position, node.method, node.clazz);
				}
			}
		}		
			//}	
		//}	
    }
	
    public void deleteEdge(int p1, MethodNode m1, ClassNode c1,
						int p2, MethodNode m2, ClassNode c2) {
		Node node1 = new Node(p1, m1, c1);
		Node node2 = new Node(p2, m2, c2);
		if (edges.get(node1)==null){ return; } 
		else if (!edges.get(node1).contains(node2)) { return; }
		else{
			edges.get(node1).remove(node2);
		}
    }
	

    public boolean isReachable(int p1, MethodNode m1, ClassNode c1,
			       int p2, MethodNode m2, ClassNode c2) {
    	Node node1 = new Node(p1, m1, c1);
		Node node2 = new Node(p2, m2, c2);
		if (node1.equals(node2)) { return true; }
		else if ( !(nodes.contains(node1) && nodes.contains(node2)) )
    		{return false;}
    	else if (edges.get(node1) == null) { return false; }
    	else if ( edges.get(node1).contains(node2) ) { 
    		return true; 
    	}
    	else{
    		for ( Node n : edges.get(node1) ){
    			if(isReachable(n.position, n.method, n.clazz,
    				node2.position, node2.method, node2.clazz)){
    				return true;
    			}
    		}
    		return false;
    	}
    }
}
