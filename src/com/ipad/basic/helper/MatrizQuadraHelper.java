package com.ipad.basic.helper;

import net.sourceforge.floggy.persistence.IDable;
import net.sourceforge.floggy.persistence.Persistable;

public class MatrizQuadraHelper implements Persistable,	IDable{

    private int id = 0;
    
    int quadra;
    int totalQuadra;
    int visitadosQuadra;
    int naoVisitadosQuadra;
    
    public MatrizQuadraHelper(){
	super();
    }
    
    public MatrizQuadraHelper(int quadra, int totalQuadra, int visitadosQuadra, int naoVisitadosQuadra){
	
	this.quadra = quadra;
	this.totalQuadra = totalQuadra;
	this.visitadosQuadra = visitadosQuadra;
	this.naoVisitadosQuadra = naoVisitadosQuadra;
    }
    
    public int getQuadra() {
        return quadra;
    }
    public void setQuadra(int quadra) {
        this.quadra = quadra;
    }
    public int getTotalQuadra() {
        return totalQuadra;
    }
    public void setTotalQuadra(int totalQuadra) {
        this.totalQuadra = totalQuadra;
    }
    public int getVisitadosQuadra() {
        return visitadosQuadra;
    }
    public void setVisitadosQuadra(int visitadosQuadra) {
        this.visitadosQuadra = visitadosQuadra;
    }
    public int getNaoVisitadosQuadra() {
        return naoVisitadosQuadra;
    }
    public void setNaoVisitadosQuadra(int naoVisitadosQuadra) {
        this.naoVisitadosQuadra = naoVisitadosQuadra;
    }
    
    public String getRecordStoreName() {
	return "helper";
    }
    
    public void setId(int arg0) {
	this.id = arg0;
    }
    
    public int getId(){
	return id;
    }
    
    public boolean equals( Object obj ){
	return this.getQuadra() == ( (MatrizQuadraHelper) obj ).getQuadra();
    }
}
