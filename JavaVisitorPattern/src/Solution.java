
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

import java.util.ArrayList;
import java.util.Scanner;

enum Color {
    RED, GREEN
}

abstract class Tree {

    private int value;
    private Color color;
    private int depth;

    public Tree(int value, Color color, int depth) {
        this.value = value;
        this.color = color;
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int getDepth() {
        return depth;
    }
    

    public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

    private ArrayList<Tree> children = new ArrayList<>();

    public TreeNode(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitNode(this);

        for (Tree child : children) {
            child.accept(visitor);
        }
    }

    public void addChild(Tree child) {
        children.add(child);
    }
}

class TreeLeaf extends Tree {

    public TreeLeaf(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitLeaf(this);
    }
}

abstract class TreeVis
{
    public abstract int getResult();
    public abstract void visitNode(TreeNode node);
    public abstract void visitLeaf(TreeLeaf leaf);

}

class SumInLeavesVisitor extends TreeVis {
    private int results = 0;
    
    public int getResult() {
        return results;
    }

    public void visitNode(TreeNode node) {
        // not needed
    }

    public void visitLeaf(TreeLeaf leaf) {
      	results += leaf.getValue();
    }
}

class ProductOfRedNodesVisitor extends TreeVis {
    private final int mod = 1000000007;
    private long results = 1;
    
    public int getResult() {
      	return (int)results;
    }

    public void visitNode(TreeNode node) {   
      	if (node.getColor() == Color.RED) {
              results = (results%mod * node.getValue()%mod) % mod;
        }
    }

    public void visitLeaf(TreeLeaf leaf) {
        if (leaf.getColor() == Color.RED) {
             //results = (results%mod * leaf.getValue()%mod)%mod;
             results = (results%mod * leaf.getValue()%mod) % mod;
        }
    }
}

class FancyVisitor extends TreeVis {
    private int nodeEvenSum = 0;
    private int leafGreenSum = 0;
    
    public int getResult() {
        return Math.abs(nodeEvenSum - leafGreenSum);
    }

    public void visitNode(TreeNode node) {
    	if (node.getDepth() % 2 == 0) {
            nodeEvenSum += node.getValue();
        }
    }

    public void visitLeaf(TreeLeaf leaf) {
    	if (leaf.getColor() == Color.GREEN) {
            leafGreenSum += leaf.getValue();
        }
    }
}

public class Solution {

	private static int nodeCount = 0;
	
    private static HashMap<Integer, Integer> nodeToValue
        = new HashMap<Integer, Integer>();
        
    private static HashMap<Integer, Color> nodeToColor 
        = new HashMap<Integer, Color>();
        
    private static HashMap<Integer, ArrayList<ArrayList<Integer>>> edges 
    	= new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
    
    private static void addEdge(int index, ArrayList<Integer> edge) {
    	ArrayList<ArrayList<Integer>> edgeList = edges.get(index);
    	
    	edgeList = (edgeList == null) ? new ArrayList<ArrayList<Integer>>() : edgeList;
    
    	edgeList.add(edge);
    	edges.put(index, edgeList);
    }
    
    private static ArrayList<Integer> getChildList(int parent, int grandParent) {

        ArrayList<Integer> childList = new ArrayList<Integer>();
        
        for (ArrayList<Integer> edge : edges.get(parent)) {
        	if (edge.contains(parent) && !edge.contains(grandParent)) {
        		int child = (edge.get(0) == parent) ? edge.get(1) : edge.get(0);
        		childList.add(child);
        	}
        }
        
        return childList;
    }
    
    private static Tree createTree(int index, int parentIndex, int depth) {
        int value = nodeToValue.get(index);
        Color color = nodeToColor.get(index);
        
        ArrayList<Integer> childList = getChildList(index, parentIndex);
        if (!childList.isEmpty()) {
        	TreeNode node = new TreeNode(value, color, depth);
        	
	        for (int childIndex : childList) {
	        	node.addChild(createTree(childIndex, index, depth + 1));
	        }
	        
	        return node;
        }
        
        return new TreeLeaf(value, color, depth);
    }
    
    public static Tree solve() {
        Scanner input = new Scanner(System.in);
        
        // First line of input. How many nodes there will be total
        nodeCount = input.nextInt();
        
        // Second line of input: map node number to its respective value
        for (int i = 1; i <= nodeCount; i++) {
            nodeToValue.put(i, input.nextInt());
        }
        
        // Third line of input: map node color to its respective color
        for (int i = 1; i <= nodeCount; i++) {
            nodeToColor.put(i, Color.values()[input.nextInt()]);
        }
        
        // nodeCount - 1 subsequent lines describe edges.
        for (int i = 1; i < nodeCount; i++) {
        	int v = input.nextInt();
        	int u = input.nextInt();
        	ArrayList<Integer> edge = new ArrayList<>();
        	edge.add(u);
        	edge.add(v);
        	addEdge(u, edge);
        	addEdge(v, edge);
        }
        
        // Done with raw input
        input.close();
        
        Tree root = createTree(1, 0, 0);
        return root;
    }
    
    public static void main(String[] args) {
      	Tree root = solve();
		SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
      	ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
      	FancyVisitor vis3 = new FancyVisitor();

      	root.accept(vis1);
      	root.accept(vis2);
      	root.accept(vis3);

      	int res1 = vis1.getResult();
      	int res2 = vis2.getResult();
      	int res3 = vis3.getResult();

      	System.out.println(res1);
     	System.out.println(res2);
    	System.out.println(res3);
	}
}
