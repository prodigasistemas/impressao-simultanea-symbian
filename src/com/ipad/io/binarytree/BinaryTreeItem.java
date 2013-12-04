package com.ipad.io.binarytree;

import java.util.Vector;

import net.sourceforge.floggy.persistence.Persistable;

public class BinaryTreeItem implements Persistable {

    private double indice;
    private int id;
    private Vector ids;

    public BinaryTreeItem(double indice, int id) {
	super();
	this.indice = indice;
	this.id = id;

	if (this.ids == null) {
	    ids = new Vector();
	    ids.addElement(new Integer(id));
	}
    }

    public BinaryTreeItem() {
	super();
    }

    public double getIndice() {
	return indice;
    }

    public void setIndice(int indice) {
	this.indice = indice;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public void addId(int id) {
	if (this.ids == null) {
	    this.ids = new Vector();
	}

	if (!ids.contains(new Integer(id))) {
	    this.ids.addElement(new Integer(id));
	}
    }

    public int[] getIds() {

	int[] retorno = new int[ids.size()];

	for (int i = 0; i < ids.size(); i++) {
	    retorno[i] = ((Integer) ids.elementAt(i)).intValue();
	}

	return retorno;
    }
    
    public void setIds(int[] ids){
    	this.ids.removeAllElements();
    	for (int i = 0; i < ids.length; i++) {
    	    this.ids.addElement(new Integer(ids[i]));
    	}
    }

}
