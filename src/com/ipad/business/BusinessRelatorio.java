package com.ipad.business;

import net.sourceforge.floggy.persistence.ObjectSet;

import com.ipad.basic.DadosRelatorio;
import com.ipad.basic.ImovelConta;
import com.ipad.io.Repositorio;
import com.ipad.util.Constantes;
import com.ipad.util.Util;
import com.ipad.util.Util;
import com.ipad.view.Relatorios;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.GridLayout;
import com.sun.lwuit.plaf.Border;

public class BusinessRelatorio implements ActionListener{
	
	private static BusinessRelatorio instancia;
	
	private Command voltar;
	
	private int total = 0;

	public static BusinessRelatorio getInstancia(){
		if(BusinessRelatorio.instancia == null){
			BusinessRelatorio.instancia = new BusinessRelatorio();
		}
		return BusinessRelatorio.instancia;
	}
	
	public void criarResumoQuadra(){
		Form fmResumoQuadra =  new Form("Resumo Por Quadra");
		fmResumoQuadra.setScrollable(false);
		
		voltar =  new Command("Voltar");
		fmResumoQuadra.addCommand(voltar);
		
		fmResumoQuadra.setLayout(new BorderLayout());
		
		fmResumoQuadra.setCommandListener(this);

		fmResumoQuadra.addComponent(BorderLayout.CENTER,this.createTable2());
		fmResumoQuadra.show();
	}
    
    public Container createTable1(){
    	  
        ImovelConta imovel = null;
        ImovelConta primeiroImovel =  new ImovelConta();
        Repositorio.carregarObjeto(primeiroImovel, 1);
        
    	  String quadra = String.valueOf(primeiroImovel.getQuadra());
          ObjectSet qtd = Repositorio.listarObjetos(ImovelConta.class);
          
        int numeroLinhas = 2;
        this.total = qtd.size();
        int visitadosQuadra = 0;
        int aVisitar = 0;
        int totalQuadra = 0;
        int quadrasCounter = -1; 
        
    	for (int i = 1; i < total; i++) {
    		imovel = new ImovelConta();
    		Repositorio.carregarObjeto(imovel, i);
  
            if (!quadra.equals(String.valueOf(imovel.getQuadra()))) {
                numeroLinhas++;
                quadra = String.valueOf(imovel.getQuadra());
            }
        }
    	System.out.println("Numero Linhas: "+numeroLinhas);
    	Repositorio.carregarObjeto(imovel, 1);
    	
    	GridLayout resumoQuadraLayout =  new GridLayout(numeroLinhas,4);        
        
        Container resumoQuadra =  new Container();
        resumoQuadra.setLayout(resumoQuadraLayout);
        resumoQuadra.getStyle().setBorder(Border.createLineBorder(1));
        
        Label label1 = new Label("Quadra");
        label1.getStyle().setBorder(Border.createLineBorder(1));
        Label label2 = new Label("Total");
        label2.getStyle().setBorder(Border.createLineBorder(1));
        Label label3 = new Label("Visitados");
        label3.getStyle().setBorder(Border.createLineBorder(1));
        Label label4 = new Label("A Visitar");
        label4.getStyle().setBorder(Border.createLineBorder(1));
        
        resumoQuadra.addComponent(label1);
        resumoQuadra.addComponent(label2);
        resumoQuadra.addComponent(label3);
        resumoQuadra.addComponent(label4);
        
        Label test;
        Label test1;
        Label test2;
        Label test3;

        
        for (int i = 0; i <= this.total; i++) {
        	
        	if(i < this.total){
        	 imovel = new ImovelConta();

           	 Repositorio.carregarObjeto(imovel, i+1);
        	}
           	 
            if (!quadra.equals(String.valueOf(imovel.getQuadra())) ||
            		i >= this.total) {
                aVisitar = totalQuadra - visitadosQuadra;
                quadrasCounter++;
                
                if(totalQuadra != 0){
                test =  new Label(quadra+"");
                test.setFocusable(true);
                test1 = new Label(totalQuadra+"");
                test1.setFocusable(true);
                test2 = new Label(visitadosQuadra+"");
                test2.setFocusable(true);
                test3 = new Label(aVisitar+"");
                test3.setFocusable(true);
                
                resumoQuadra.addComponent(test);
                resumoQuadra.addComponent(test1);
                resumoQuadra.addComponent(test2);
                resumoQuadra.addComponent(test3);
                
                totalQuadra = 0;
                visitadosQuadra = 0;
                }

            }
            totalQuadra++;
            System.out.println("Total Quadra!");
            if (imovel.getIndcImovelCalculado() == Constantes.SIM) {
                visitadosQuadra++;
            }
            quadra = String.valueOf(imovel.getQuadra());
        }

        aVisitar = totalQuadra - visitadosQuadra;
        quadrasCounter++;
        
        return resumoQuadra;
    }
    
    
    public Container createTable2(){
        
	DadosRelatorio dadosRelatorio = DadosRelatorio.getInstancia();

        this.total = dadosRelatorio.quadras.size();        
    	int numeroLinhas = total + 1;
        
    	BorderLayout border =  new BorderLayout();
    	GridLayout linhaNomesResumo =  new GridLayout(1,4);
    	GridLayout resumoQuadraLayout =  new GridLayout(numeroLinhas,4);        
        
    	Container resumoTotal = new Container();
    	resumoTotal.setLayout(border);
    	resumoTotal.setScrollable(false);
    	//resumoTotal.setLayout(gridResumoTotal);
    	
    	Container linhaResumo = new Container();
        Container resumoQuadra =  new Container();
        linhaResumo.setLayout(linhaNomesResumo);
        linhaResumo.setScrollable(false);
        linhaResumo.getStyle().setBorder(Border.createLineBorder(1));
        resumoQuadra.setLayout(resumoQuadraLayout);
        resumoQuadra.getStyle().setBorder(Border.createLineBorder(1));
        resumoQuadra.setScrollable(true);
        
        Label label1 = new Label("Quadra");
        label1.getStyle().setBorder(Border.createLineBorder(1));
        Label label2 = new Label("Total");
        label2.getStyle().setBorder(Border.createLineBorder(1));
        Label label3 = new Label("Visitados");
        label3.getStyle().setBorder(Border.createLineBorder(1));
        Label label4 = new Label("A Visitar");
        label4.getStyle().setBorder(Border.createLineBorder(1));
        
        linhaResumo.addComponent(label1);
        linhaResumo.addComponent(label2);
        linhaResumo.addComponent(label3);
        linhaResumo.addComponent(label4);
        
        Label test;
        Label test1;
        Label test2;
        Label test3;

        
        for (int i = 0; i < this.total; i++) {
        	
            	String stringQuadra = "(" + Util.adicionarZerosEsquerdaNumero(4, String.valueOf(dadosRelatorio.quadras.elementAt(i))) + ")";
            	int intQuadra = Integer.parseInt(dadosRelatorio.quadras.elementAt(i) + "");
                int indice = dadosRelatorio.valoresRelatorio.indexOf(stringQuadra);
            	
            	if(dadosRelatorio.quadraAnterior != intQuadra || this.total == 1){
            	    
                test =  new Label(intQuadra+"");
                test.setFocusable(true);
                test.getStyle().setBorder(Border.createLineBorder(1));
                
                int total = Integer.parseInt(dadosRelatorio.valoresRelatorio.substring(indice + 6, indice + 10));
                test1 = new Label(total+"");
                //test1.setFocusable(true);
                test1.getStyle().setBorder(Border.createLineBorder(1));
                int visitados = Integer.parseInt(dadosRelatorio.valoresRelatorio.substring(indice + 10, indice + 14)); 
                test2 = new Label(visitados+"");
                //test2.setFocusable(true);
                test2.getStyle().setBorder(Border.createLineBorder(1));
                int naoVisitados = Integer.parseInt(dadosRelatorio.valoresRelatorio.substring(indice + 14, indice + 18));
                test3 = new Label(naoVisitados+"");
               // test3.setFocusable(true);
                test3.getStyle().setBorder(Border.createLineBorder(1));
                
                resumoQuadra.addComponent(test);
                resumoQuadra.addComponent(test1);
                resumoQuadra.addComponent(test2);
                resumoQuadra.addComponent(test3);
                
                dadosRelatorio.quadraAnterior = intQuadra;
            	
                    }
                
                }
        
        	//dadosRelatorio.setQuadraRelatorio(Constantes.NULO_INT);
        	Repositorio.salvarObjeto(dadosRelatorio);
        
        	resumoTotal.addComponent(BorderLayout.NORTH,linhaResumo);
        	resumoTotal.addComponent(BorderLayout.CENTER,resumoQuadra);
        
        return resumoTotal;
    }
    

	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getCommand() == voltar){
			new Relatorios();
		}
		
	}

}
