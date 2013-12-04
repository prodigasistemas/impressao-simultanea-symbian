package com.ipad.basic;

import java.util.Vector;

import com.ipad.util.Constantes;

import net.sourceforge.floggy.persistence.IDable;
import net.sourceforge.floggy.persistence.Persistable;

public class DadosRelatorio implements Persistable, IDable {

    public static DadosRelatorio instancia;

    private int id;

    public Vector idsLidosRelatorio =  new Vector();
    public Vector idsNaoLidosRelatorio =  new Vector();
    public Vector quadras = new Vector();

    public int quadraAnterior = Constantes.NULO_INT;
    public String valoresRelatorio = "{0000}[0000]";


    public DadosRelatorio() {
	super();
    }

    public void setId(int id) {

	this.id = id;
    }

    public int getId() {
	return id;
    }

    public String getRecordStoreName() {
	
	return "DadosRelatorio";
	
    }   
     
    public static DadosRelatorio getInstancia() {

	if (DadosRelatorio.instancia == null) {
	    DadosRelatorio.instancia = new DadosRelatorio();

	}

	return DadosRelatorio.instancia;
    }

}
