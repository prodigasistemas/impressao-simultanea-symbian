package com.ipad.io.binarytree;

import net.sourceforge.floggy.persistence.IDable;
import net.sourceforge.floggy.persistence.Persistable;

// BinaryTree.java
public abstract class BinaryTree implements Persistable, IDable {

    // Root node pointer. Will be null for an empty tree.
    private Node root;

    /*
     * --Node-- The binary tree is built using this nested node class. Each node
     * stores one data element, and has left and right sub-tree pointer which
     * may be null. The node is a "dumb" nested class -- we just use it for
     * storage; it does not have any methods.
     */
    private static class Node implements Persistable {
	Node left;
	Node right;
	BinaryTreeItem data;

	Node(BinaryTreeItem newData) {
	    left = null;
	    right = null;
	    data = newData;
	}

	public Node() {
	    super();
	}
    }

    /**
     * Creates an empty binary tree -- a null root pointer.
     */
    public BinaryTree() {
	root = null;
    }

    /**
     * Returns true if the given target is in the binary tree. Uses a recursive
     * helper.
     */
    public int lookup(double data) {
	return (lookup(root, new BinaryTreeItem(data, -1)));
    }

    public BinaryTreeItem lookupNode(BinaryTreeItem data) {
	return (lookupData(root, data));
    }

    /**
     * Recursive lookup -- given a node, recur down searching for the given
     * data.
     */
    private int lookup(Node node, BinaryTreeItem data) {
	if (node == null) {
	    return -1;
	}

	if (data.getIndice() == node.data.getIndice()) {
//Daniel
		if( (data.getId() != -1) && (node.data.getId() == 0)){
			node.data.setId(data.getId());
		    node.data.setIds(data.getIds());
	    }
	    return node.data.getId();
	} else if (data.getIndice() < node.data.getIndice()) {
	    return (lookup(node.left, data));
	} else {
	    return (lookup(node.right, data));
	}
    }

    /**
     * Recursive lookup -- given a node, recur down searching for the given
     * data.
     */
    private BinaryTreeItem lookupData(Node node, BinaryTreeItem data) {
	if (node == null) {
	    return null;
	}

	if (data.getIndice() == node.data.getIndice()) {
//Daniel
		if( (data.getId() != -1) && (node.data.getId() == 0)){
			node.data.setId(data.getId());
		    node.data.setIds(data.getIds());
	    }
		return node.data;
	} else if (data.getIndice() < node.data.getIndice()) {
	    return (lookupData(node.left, data));
	} else {
	    return (lookupData(node.right, data));
	}
    }

    /**
     * Inserts the given data into the binary tree. Uses a recursive helper.
     */
    public void insert(BinaryTreeItem data) {
	BinaryTreeItem item = lookupNode(data);

	if (item == null) {
	    root = insert(root, data);
	}
    }

    /**
     * Inserts the given data into the binary tree. Uses a recursive helper.
     */
    public void insertId(BinaryTreeItem data) {

	BinaryTreeItem item = lookupData(root, data);

	if (item != null) {

	    boolean achou = false;
	    int[] ids = item.getIds();

	    for (int i = 0; i < ids.length; i++) {
		if (ids[i] == data.getId()) {
		    achou = true;
		    break;
		}
	    }

	    if (!achou) {
		item.addId(data.getId());
	    }
	} else {
	    root = insert(root, data);
	}
    }

    /**
     * Recursive insert -- given a node pointer, recur down and insert the given
     * data into the tree. Returns the new node pointer (the standard way to
     * communicate a changed pointer back to the caller).
     */
    private Node insert(Node node, BinaryTreeItem data) {
	if (node == null) {
	    node = new Node(data);
	} else {
	    if (data.getIndice() <= node.data.getIndice()) {
		node.left = insert(node.left, data);
	    } else {
		node.right = insert(node.right, data);
	    }
	}

	return (node);
    }
}