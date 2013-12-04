/*
* Copyright (C) 2007-2009 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
*
* This file is part of GSAN, an integrated service management system for Sanitation
*
* GSAN is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License.
*
* GSAN is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
*/

/*
* GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
* Copyright (C) <2007> 
* Adriano Britto Siqueira
* Alexandre Santos Cabral
* Ana Carolina Alves Breda
* Ana Maria Andrade Cavalcante
* Aryed Lins de Araújo
* Bruno Leonardo Rodrigues Barros
* Carlos Elmano Rodrigues Ferreira
* Clêudio de Andrade Lira
* Denys Guimarães Guenes Tavares
* Eduardo Breckenfeld da Rosa Borges
* Fabíola Gomes de Araújo
* Flêvio Leonardo Cavalcanti Cordeiro
* Francisco do Nascimento Júnior
* Homero Sampaio Cavalcanti
* Ivan Sérgio da Silva Júnior
* José Edmar de Siqueira
* José Thiago Tenório Lopes
* Kássia Regina Silvestre de Albuquerque
* Leonardo Luiz Vieira da Silva
* Márcio Roberto Batista da Silva
* Maria de Fátima Sampaio Leite
* Micaela Maria Coelho de Araújo
* Nelson Mendonça de Carvalho
* Newton Morais e Silva
* Pedro Alexandre Santos da Silva Filho
* Rafael Corrêa Lima e Silva
* Rafael Francisco Pinto
* Rafael Koury Monteiro
* Rafael Palermo de Araújo
* Raphael Veras Rossiter
* Roberto Sobreira Barbalho
* Roberto Souza
* Rodrigo Avellar Silveira
* Rosana Carvalho Barbosa
* Sávio Luiz de Andrade Cavalcante
* Tai Mu Shih
* Thiago Augusto Souza do Nascimento
* Tiago Moreno Rodrigues
* Vivianne Barbosa Sousa
*
* Este programa é software livre; você pode redistribuí-lo e/ou
* modificá-lo sob os termos de Licença Pública Geral GNU, conforme
* publicada pela Free Software Foundation; versão 2 da
* Licença.
* Este programa é distribuído na expectativa de ser útil, mas SEM
* QUALQUER GARANTIA; sem mesmo a garantia implêcita de
* COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
* PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
* detalhes.
* Você deve ter recebido uma cópia da Licença Pública Geral GNU
* junto com este programa; se não, escreva para Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
* 02111-1307, USA.
*/   


package com.ipad.view;

import com.ipad.basic.Configuracao;
import com.ipad.basic.Consumo;
import com.ipad.basic.ImovelConta;
import com.ipad.business.ControladorConta;
import com.ipad.business.ControladorImoveis;
import com.ipad.component.BlackLabel;
import com.ipad.component.FieldLabel;
import com.ipad.io.Repositorio;
import com.ipad.util.Constantes;
import com.ipad.util.Util;
import com.sun.lwuit.Container;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.GridLayout;

public class AbaConta extends Container {
	
	// Imovel selecionado
//	private static ImovelConta imovelSelecionado;
	
	// Todos os campos fixos;
	private static BlackLabel lbLeituraFaturadaAgua = new BlackLabel( "Leitura Faturada Agua:" );
	private static BlackLabel lbConsumoAgua = new BlackLabel( "Consumo Agua:" );
	private static BlackLabel lbConsumoTipoAgua = new BlackLabel( "Consumo Tipo Agua:" );
	private static BlackLabel lbAnormalidadeConsumoAgua = new BlackLabel( "Anorm. Consumo Agua:" );
	private static BlackLabel lbValorAgua = new BlackLabel( "Valor de Agua(R$):" );
	private static BlackLabel lbLeituraFaturadaPoco = new BlackLabel( "Leitura Faturada Poco:" );
	private static BlackLabel lbConsumoEsgoto = new BlackLabel( "Consumo Esgoto:" );
	private static BlackLabel lbConsumoTipoEsgoto = new BlackLabel( "Consumo Tipo Esgoto:" );
	private static BlackLabel lbAnormalidadeConsumoEsgoto = new BlackLabel( "Anorm. Consumo Esgoto:" );
	private static BlackLabel lbValorEsgoto = new BlackLabel( "Valor de Esgoto(R$):" );
	private static BlackLabel lbDiasConsumo = new BlackLabel( "Dias de Consumo:" );
	private static BlackLabel lbValorDebitos = new BlackLabel( "Valor Debitos(R$):" );
	private static BlackLabel lbValorCreditos = new BlackLabel( "Valor Creditos(R$):" );
	private static BlackLabel lbValorTotal = new BlackLabel( "Valor Total(R$):" );
	
	// Todos os labels dinamicos
	private static FieldLabel lbCampoLeituraFaturadaAgua = new FieldLabel();
	private static FieldLabel lbCampoConsumoAgua = new FieldLabel();
	private static FieldLabel lbCampoConsumoTipoAgua = new FieldLabel();
	private static FieldLabel lbCampoAnormalidadeConsumoAgua = new FieldLabel();
	private static FieldLabel lbCampoValorAgua = new FieldLabel();
	private static FieldLabel lbCampoLeituraFaturadaPoco = new FieldLabel();
	private static FieldLabel lbCampoConsumoEsgoto = new FieldLabel();
	private static FieldLabel lbCampoConsumoTipoEsgoto = new FieldLabel();
	private static FieldLabel lbCampoAnormalidadeConsumoEsgoto = new FieldLabel();
	private static FieldLabel lbCampoValorEsgoto = new FieldLabel();
	private static FieldLabel lbCampoDiasConsumo = new FieldLabel();
	private static FieldLabel lbCampoValorDebitos = new FieldLabel();
	private static FieldLabel lbCampoValorCreditos = new FieldLabel();
	private static FieldLabel lbCampoValorTotal = new FieldLabel();
	
	private static FieldLabel lbCampoMensagem = new FieldLabel();
	
	// Instancia
	private static AbaConta instanciaAbaConta;
	
	public static AbaConta getInstancia(){
		if ( instanciaAbaConta == null ){
			instanciaAbaConta = new AbaConta();
			instanciaAbaConta.setLayout( new GridLayout( 15, 2 ) );
			instanciaAbaConta.setScrollableY(true);
			instanciaAbaConta.setX( 0 );
			
			instanciaAbaConta.addComponent( new Label("") );
			instanciaAbaConta.addComponent( lbCampoMensagem );
			lbCampoMensagem.getStyle().setFgColor(0xff0000);
			instanciaAbaConta.addComponent( lbLeituraFaturadaAgua );
			instanciaAbaConta.addComponent( lbCampoLeituraFaturadaAgua );
			instanciaAbaConta.addComponent( lbConsumoAgua );
			instanciaAbaConta.addComponent( lbCampoConsumoAgua );
			instanciaAbaConta.addComponent( lbConsumoTipoAgua );
			instanciaAbaConta.addComponent( lbCampoConsumoTipoAgua );
			instanciaAbaConta.addComponent( lbAnormalidadeConsumoAgua );
			instanciaAbaConta.addComponent( lbCampoAnormalidadeConsumoAgua );
			instanciaAbaConta.addComponent( lbValorAgua );
			instanciaAbaConta.addComponent( lbCampoValorAgua );
			instanciaAbaConta.addComponent( lbLeituraFaturadaPoco );
			instanciaAbaConta.addComponent( lbCampoLeituraFaturadaPoco );
			instanciaAbaConta.addComponent( lbConsumoEsgoto );
			instanciaAbaConta.addComponent( lbCampoConsumoEsgoto );
			instanciaAbaConta.addComponent( lbConsumoTipoEsgoto );
			instanciaAbaConta.addComponent( lbCampoConsumoTipoEsgoto );
			instanciaAbaConta.addComponent( lbAnormalidadeConsumoEsgoto );
			instanciaAbaConta.addComponent( lbCampoAnormalidadeConsumoEsgoto );
			instanciaAbaConta.addComponent( lbValorEsgoto );
			instanciaAbaConta.addComponent( lbCampoValorEsgoto );
			instanciaAbaConta.addComponent( lbDiasConsumo );
			instanciaAbaConta.addComponent( lbCampoDiasConsumo );
			instanciaAbaConta.addComponent( lbValorDebitos );
			instanciaAbaConta.addComponent( lbCampoValorDebitos );
			instanciaAbaConta.addComponent( lbValorCreditos );
			instanciaAbaConta.addComponent( lbCampoValorCreditos );
			instanciaAbaConta.addComponent( lbValorTotal );
			instanciaAbaConta.addComponent( lbCampoValorTotal );	
		}
		
		return instanciaAbaConta;
	}
	
	// Setamos todos os valores necessários
	public AbaConta criarAbaContaCalculo(){
		
		lbCampoMensagem.setVisible( false );
		
		this.setScrollY( 0 );		

		Consumo consumoAgua = getImovelSelecionado().getConsumoAgua();
		if ( consumoAgua == null ){
			consumoAgua = new Consumo();
		}
		
		Consumo consumoEsgoto = getImovelSelecionado().getConsumoEsgoto();
		if ( consumoEsgoto == null ){
			consumoEsgoto = new Consumo();
		}
		
		int leituraAgua = 0;
		int leituraEsgoto = 0;
		int qtdDiasAjustado = 0;
		boolean ajustado = false;
		
		
		
		if(getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_AGUA) != null
		     && getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_AGUA)
			.getLeituraAtualFaturamento() != Constantes.NULO_INT){
		    
		   leituraAgua = getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_AGUA)
			.getLeituraAtualFaturamento();
		   
		   qtdDiasAjustado = getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_AGUA).getQtdDiasAjustado();
 
		   lbCampoLeituraFaturadaAgua.setTextNullValidate( leituraAgua +"" );
		   lbCampoDiasConsumo.setTextNullValidate( qtdDiasAjustado +"" );
		   
		   ajustado = true;
		   
		}else{
		    
		    if(!ajustado){
		    	lbCampoLeituraFaturadaAgua.setTextNullValidate( consumoAgua.getLeituraAtual() +"" );
		    	lbCampoDiasConsumo.setTextNullValidate( consumoAgua.getDiasConsumo()+"" );
		    }
		}
		
		if(getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_POCO) != null &&
			getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_POCO)
			.getLeituraAtualFaturamento() != Constantes.NULO_INT){
		    
		    leituraEsgoto = getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_POCO)
			.getLeituraAtualFaturamento(); 
		    
		    qtdDiasAjustado = getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_POCO).getQtdDiasAjustado();
		    
		    lbCampoLeituraFaturadaPoco.setTextNullValidate( leituraEsgoto +"" );
		    lbCampoDiasConsumo.setTextNullValidate( qtdDiasAjustado +"" );
		    
		    ajustado = true;
		    
		}else{
		    
		    if(!ajustado){
			lbCampoLeituraFaturadaPoco.setTextNullValidate( consumoEsgoto.getLeituraAtual() +"" );
			lbCampoDiasConsumo.setTextNullValidate( consumoAgua.getDiasConsumo()+"" );
		    }
		}
		
		if(getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_AGUA) == null &&
			getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_POCO) == null){
		    
		    lbCampoLeituraFaturadaAgua.setTextNullValidate( consumoAgua.getLeituraAtual() +"" );
		    lbCampoLeituraFaturadaPoco.setTextNullValidate( consumoEsgoto.getLeituraAtual()+"" );
		    lbCampoDiasConsumo.setTextNullValidate( consumoAgua.getDiasConsumo()+"" );	
		    
		}
		
		/*lbCampoLeituraFaturadaAgua.setTextNullValidate( getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_AGUA)
			.getLeituraAtualFaturamento()+"" );*/		
		lbCampoConsumoAgua.setTextNullValidate( consumoAgua.getConsumoCobradoMes()+"" );
		lbCampoConsumoTipoAgua.setTextNullValidate( consumoAgua.getTipoConsumo()+"" );
		lbCampoAnormalidadeConsumoAgua.setTextNullValidate( consumoAgua.getAnormalidadeConsumo()+"" );
		lbCampoValorAgua.setTextNullValidate( Util.formatarDoubleParaMoedaReal( getImovelSelecionado().getValorAgua() ) );
		//lbCampoLeituraFaturadaPoco.setTextNullValidate( consumoEsgoto.getLeituraAtual()+"" );		
		lbCampoConsumoEsgoto.setTextNullValidate( consumoEsgoto.getConsumoCobradoMes()+"" );
		lbCampoConsumoTipoEsgoto.setTextNullValidate( consumoEsgoto.getTipoConsumo()+"" );
		lbCampoAnormalidadeConsumoEsgoto.setTextNullValidate( consumoEsgoto.getAnormalidadeConsumo()+"" );
		lbCampoValorEsgoto.setTextNullValidate( Util.formatarDoubleParaMoedaReal( getImovelSelecionado().getValorEsgoto() ) );
		//lbCampoDiasConsumo.setTextNullValidate( consumoAgua.getDiasConsumo()+"" );		
		lbCampoValorDebitos.setTextNullValidate( Util.formatarDoubleParaMoedaReal( getImovelSelecionado().getValorDebitos() ) );
		lbCampoValorCreditos.setTextNullValidate( Util.formatarDoubleParaMoedaReal( getImovelSelecionado().getValorCreditos() ) );
		lbCampoValorTotal.setTextNullValidate( Util.formatarDoubleParaMoedaReal( getImovelSelecionado().getValorConta() ) );
		
		if(getImovelSelecionado().getIndcImovelImpresso() == Constantes.NAO &&
				getImovelSelecionado().getIndcImovelCalculado() == Constantes.SIM){
			//lbMensagem.setText( "Imovel calculado"+
			//		getImovelSelecionado().getRegistro8(	
			//				ControladorConta.LIGACAO_AGUA ).getLeitura());
			lbCampoMensagem.setText("Imovel ja Calculado");
			lbCampoMensagem.setVisible(true);
		}
		
		if ( getImovelSelecionado().getIndcImovelImpresso() == Constantes.SIM ){
			lbCampoMensagem.setText( "Conta já impressa" );
			lbCampoMensagem.setVisible(true);
		}
		if(Configuracao.getInstancia().getContadorVisitados() == Configuracao.getInstancia().getQtdImoveis()){
			lbCampoMensagem.setText("Rota já concluida");
			lbCampoMensagem.setVisible(true);
		}		
		
		if ( getImovelSelecionado().isImovelCondominio() ){
		    ImovelConta hidrometroMacro = new ImovelConta();
		    Repositorio.carregarObjeto( hidrometroMacro , getImovelSelecionado().getIdImovelCondominio() );	    
		    
		    lbCampoMensagem.setText("I.C. " + ControladorImoveis.getInstancia().getIndiceAtualImovelCondominio() + " de " + hidrometroMacro.getQuantidadeImoveisCondominio());
			
		    lbCampoMensagem.setVisible( true );
		}
		
		return instanciaAbaConta;
	}
	
	// Alteramos a visibilidade do construtor
	// para que ele não possa ser chamado externamente
	// obrigando assim a usar o getInstancia
	private AbaConta(){
		super();
	}
	
    public Label getLeituraFaturadaAgua() {
        return lbCampoLeituraFaturadaAgua;
    }
    public ImovelConta getImovelSelecionado(){
    	return ControladorImoveis.getInstancia().getImovelSelecionado();
    }
}
