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

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;

import com.ipad.background.BackgroudTaskRatearConsumoImovelCondominio;
import com.ipad.basic.Configuracao;
import com.ipad.basic.Consumo;
import com.ipad.basic.ImovelConta;
import com.ipad.basic.ImovelReg11;
import com.ipad.basic.ImovelReg8;
import com.ipad.business.BusinessConta;
import com.ipad.business.ControladorConta;
import com.ipad.business.ControladorImoveis;
import com.ipad.component.ComboBox;
import com.ipad.component.FormDefault;
import com.ipad.component.TextField;
import com.ipad.facade.Fachada;
import com.ipad.io.InquiryList;
import com.ipad.io.MessageDispatcher;
import com.ipad.io.Repositorio;
import com.ipad.util.Constantes;
import com.ipad.util.Util;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.TabbedPane;
import com.sun.lwuit.animations.Motion;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.SelectionListener;
import com.sun.lwuit.layouts.BorderLayout;

public class Abas implements ActionListener, SelectionListener {

    // Focus Componentes
    private Component futureFocus;
    private Motion focusMotionX;
    private Motion focusMotionY;
    private Motion focusMotionWidth;
    private Motion focusMotionHeight;

    private static TabbedPane abas;
    private static Font font;

    private static Label indcImovelAtual;
    private static Label indcNumeroImoveis;

    // Criacao dos campos para o form e respectivos comandos
    private static FormDefault fmImovel;
    private static Command voltarMenu;
    private static Command imprimir;
//    private static Command calcular;
    public static Command imprimirImovelCondominio;
    private static Command proximoImovel;
    private static Command imovelAnterior;
    private static Command localizarPendente;
    private static Command localizarProximoARevisitar;

    private int numeroAbas;
    private boolean executandoAcao = false;

    private static Abas instanciaAbas;

    private static boolean habilitaOpcaoImpressao = true;
    private static boolean habilitaOpcaoCalculo = true;
    
    public static Abas getInstancia() {
	if (instanciaAbas == null) {
	    
		instanciaAbas = new Abas();
	    abas = new TabbedPane();

	    fmImovel = new FormDefault() {

		public void keyPressed(int keyCode) {

		    if (keyCode == -4 || keyCode == -3) {
			// Mudamos de aba
			if (keyCode == -4) {
			    abas.setSelectedIndex((abas.getSelectedIndex() + 1) % abas.getTabCount());

			} else {
			    if (abas.getSelectedIndex() - 1 < 0) {
				abas.setSelectedIndex(abas.getTabCount() - 1);

			    } else {
				abas.setSelectedIndex(abas.getSelectedIndex() - 1);
			    }
			}

			// Selecionamos o componente que ira receber o foco
			TextField text = null;
			Label label = null;
			ComboBox anormalidade = null;

			if (this.componenteRecebeFoco instanceof TextField) {
			    text = ((TextField) this.componenteRecebeFoco);

			} else {
			    if (this.componenteRecebeFoco == null) {
				anormalidade = AbaAnormalidade.getInstancia().getAnormalidade();
				anormalidade.requestFocus();
			    } else {
				if (this.componenteRecebeFoco instanceof Label) {
				    label = ((Label) this.componenteRecebeFoco);
				} else {
				    anormalidade = AbaAnormalidade.getInstancia().getAnormalidade();
				    anormalidade.requestFocus();
				}
			    }
			}

			// Setamos o foco e formatamos o texto, se
			// necessário.
			if (text != null) {
			    if (keyCode == -4) {
				text.setCursorPosition(text.getText().length() - 1);
			    } else {
				text.setCursorPosition(0);
			    }
			    text.requestFocus();
			} else {
			    if (label == null) {
				anormalidade = AbaAnormalidade.getInstancia().getAnormalidade();
				anormalidade.requestFocus();
			    } else {
				label.requestFocus();
				label.getStyle().setFont(this.fonteSelecionado);
			    }

			}

			// Limpamos tudo para o GC
			this.componenteRecebeFoco = null;
			text = null;
			label = null;
		    } else {
			super.keyPressed(keyCode);
		    }
		}
	    };

	    fmImovel.setLayout(new BorderLayout());
	    fmImovel.setScrollable(false);
	    
	    font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);

	    indcImovelAtual = new Label();
	    indcNumeroImoveis = new Label();
	    indcImovelAtual.getStyle().setFgColor(200);
	    indcNumeroImoveis.getStyle().setFgColor(200);

	    voltarMenu = new Command("Voltar");
	    imovelAnterior = new Command("Imóvel Anterior");
	    proximoImovel = new Command("Próximo Imóvel");
//	    calcular = new Command("Calcular");
	    imprimir = new Command("Imprimir");
	    imprimirImovelCondominio = new Command("Imprimir Condomínio");
	    localizarPendente = new Command("Localizar Pendente");
	    localizarProximoARevisitar = new Command( "Próximo a Revisitar" );
	    
	    fmImovel.addCommand(voltarMenu);
	    fmImovel.addCommand(localizarPendente);	    
	    fmImovel.addCommand(imovelAnterior);
	    fmImovel.addCommand(proximoImovel);
//	    fmImovel.addCommand(calcular);
	    fmImovel.addCommand(imprimir);
	    
	    abas.addTab("Imóvel " + indcImovelAtual.getText() + " de " + indcNumeroImoveis.getText(), AbaImoveis.getInstancia());
	}

	fmImovel.addCommand(imprimir);	
	fmImovel.removeCommand(imprimirImovelCondominio);
	
	habilitaOpcaoImpressao = true;
	habilitaOpcaoCalculo = true;

	return instanciaAbas;
    }

    private Abas() {
	super();
    }

    public void criarAbas() {

	try {

	    int len = abas.getTabCount();

	    for (int i = len - 1; i > 0; --i) {
	    	abas.removeTabAt(i);
	    }

	    this.numeroAbas = 0;

	    abas.getStyle().setFont(font);
	    abas.addTabsListener(this);
	    
	    indcImovelAtual.setText("" + ControladorImoveis.getInstancia().getIndiceAtual());
	    indcNumeroImoveis.setText("" + Configuracao.getInstancia().getQtdImoveis());

	    if ( Configuracao.getInstancia().getIndcRotaMarcacaoAtivada() == Constantes.SIM &&
	    		getImovelSelecionado() != null ){
		if ( getImovelSelecionado().getSequencialRotaMarcacao() == Constantes.NULO_INT ){
		    abas.setTabTitle("Imóvel " + indcImovelAtual.getText() + " de " + indcNumeroImoveis.getText(), null, 0 );
		} else {
		    abas.setTabTitle("Imóvel " + indcImovelAtual.getText() + " de " + indcNumeroImoveis.getText() + " (" + getImovelSelecionado().getSequencialRotaMarcacao() + ")", null, 0 );
		}
	    } else {
	    	abas.setTabTitle( "Imóvel " + indcImovelAtual.getText() + " de " + indcNumeroImoveis.getText(), null, 0 );
	    }
	    
	    
	    AbaImoveis.getInstancia().criarAbaImoveis();

	    this.numeroAbas++;

//Daniel - Nao criar abas de hidrometro, se for calcular toda o rota pela média de hidrometro.
	    if(ImovelReg11.getInstancia().getIdCalculoMedia() == Constantes.NAO){

	    	if ( (getImovelSelecionado().getRegistro8(Constantes.LIGACAO_AGUA) != null) 
	    			&& 
	    			( !getImovelSelecionado().getSituacaoLigAgua().equals(Constantes.CORTADO)) )
		    {
	
		    	if ( !getImovelSelecionado().isImovelInformativo()  ||
		    			(getImovelSelecionado().getIndcCondominio() == Constantes.SIM) )
		    	{
			    	if (!abas.contains(AbaHidrometroAgua.getInstancia())) {
					    abas.addTab("Hidrômetro Água", AbaHidrometroAgua.getInstancia());
					}
			
			    	AbaHidrometroAgua.getInstancia().criarAbaHidrometro();
					this.numeroAbas++;
		    	}
		    }else{
		    	// Daniel - eliminar resquicios da leitura anterior
		    	AbaHidrometroAgua.getInstancia().cleanAbaHidrometroAgua();
		    }
	
	//Daniel - rota completa
		    if ( (getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO) != null) 
	    			&& 
	    			( !getImovelSelecionado().getSituacaoLigAgua().equals(Constantes.CORTADO)) )
		    {
	
		    	if ( !getImovelSelecionado().isImovelInformativo() ||
		    			(getImovelSelecionado().getIndcCondominio() == Constantes.SIM) )
		    	{
			    	if (!abas.contains(AbaHidrometroPoco.getInstancia())) {
					    abas.addTab("Hidrômetro Poço", AbaHidrometroPoco.getInstancia());
					}
			
			    	AbaHidrometroPoco.getInstancia().criarAbaHidrometro();
					this.numeroAbas++;
		    	}
		    }else{
		    	// Daniel - eliminar resquicios da leitura anterior
		    	AbaHidrometroPoco.getInstancia().cleanAbaHidrometroPoco();
		    }
	    }
//Daniel - rota completa
	    if ((getImovelSelecionado().getRegistro8(Constantes.LIGACAO_AGUA) == null && 
	    		getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO) == null &&
	    		!getImovelSelecionado().isImovelInformativo()) 
	    		||
	    		((getImovelSelecionado().getSituacaoLigAgua().equals(Constantes.CORTADO)) &&
	    		 (!getImovelSelecionado().isImovelInformativo()) 
//	    		 && !(getImovelSelecionado().isImovelMicroCondominio())
	    		 )
	    		||
	    		(ImovelReg11.getInstancia().getIdCalculoMedia() == Constantes.SIM))
		{
	    	
			if (getImovelSelecionado().getIndcImovelCalculado() == Constantes.NAO){

				// Daniel -  Deve salvar microcondominiais!
				if (getImovelSelecionado().isImovelMicroCondominio()){
					BusinessConta.getInstancia().imprimirCalculo(true, false);
					
				}else{
					BusinessConta.getInstancia().imprimirCalculo(false, false);
				}
			}
			if (!abas.contains(AbaConta.getInstancia())) {
			    abas.addTab("Conta", AbaConta.getInstancia());
			}
	
			AbaConta.getInstancia().criarAbaContaCalculo();
	
			abas.setSelectedIndex(1);
			componenteSelecionado(2);
			fmImovel.componenteRecebeFoco.requestFocus();
			fmImovel.componenteRecebeFoco.getStyle().setFont(fmImovel.fonteSelecionado);
			fmImovel.componenteRecebeFoco = null;
		}

	    fmImovel.setCommandListener(this);

//Daniel - Rota completa
	    if( (!getImovelSelecionado().isImovelCondominio() && getImovelSelecionado().isImovelInformativo())  
//	    	|| (getImovelSelecionado().isImovelCondominio() && (getImovelSelecionado().getSituacaoLigAgua().equals(Constantes.CORTADO)))
	    	){
		    
	    	abas.setSelectedIndex(0);
	    	
	    }else{
		    abas.setSelectedIndex(1);
	    	
	    }

	    if (getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO) != null && 
	    		abas.getTabCount() == 2) {
	    	fmImovel.componenteRecebeFoco = AbaHidrometroPoco.getInstancia().getLeituraCampo();
	    }

	    if (getImovelSelecionado().getRegistro8(Constantes.LIGACAO_AGUA) != null && 
	    		getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO) != null && 
	    		abas.getTabCount() == 3) {
	    	fmImovel.componenteRecebeFoco = AbaHidrometroAgua.getInstancia().getLeituraCampo();
	    }

	    if (getImovelSelecionado().getRegistro8(Constantes.LIGACAO_AGUA) == null && 
	    		getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO) == null) {
	    	fmImovel.componenteRecebeFoco = AbaAnormalidade.getInstancia().getAnormalidade();
	    }

	    if (fmImovel.componenteRecebeFoco == null) {
	    	fmImovel.componenteRecebeFoco = AbaAnormalidade.getInstancia().getAnormalidade();
	    }

	    if (!fmImovel.contains(abas)) {
	    	fmImovel.addComponent(BorderLayout.CENTER, abas);
	    }
	    
	    if ( Configuracao.getInstancia().getMatriculasRevisitar() != null && 
	    		!Configuracao.getInstancia().getMatriculasRevisitar().isEmpty() ){		
	    	fmImovel.addCommand( localizarProximoARevisitar, fmImovel.getCommandCount() - 1 );
	    }
	    
	    if (!getImovelSelecionado().isImovelCondominio()) {

//Daniel - Rota Completa
	    	// Nao permitir que seja calculado
	    	if(getImovelSelecionado().isImovelInformativo()){
	    		habilitaOpcaoImpressao = false;
	    		habilitaOpcaoCalculo = false;
		    
	    	} else{
				if (getImovelSelecionado().getIndcImovelImpresso() == Constantes.SIM &&
					  (getImovelSelecionado().getRegistro8(Constantes.LIGACAO_AGUA) != null || 
					  getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO) != null)) {
					
					habilitaOpcaoCalculo = false;
				}
		    }

	    } else {
	    	habilitaOpcaoImpressao = false;
	    	habilitaOpcaoCalculo = false;
	    }
	    
	    if ( getImovelSelecionado().getIdImovelCondominio() != Constantes.NULO_INT  ){
			
	    	ImovelConta hidrometroMacro = new ImovelConta();
			Repositorio.carregarObjeto( hidrometroMacro , getImovelSelecionado().getIdImovelCondominio() );	    
		
			if ( hidrometroMacro.getQuantidadeImoveisCondominio() == ControladorImoveis.getInstancia().getIndiceAtualImovelCondominio() ){
			    fmImovel.removeCommand(imprimir);
				fmImovel.addCommand(imprimirImovelCondominio);		    
			}
	    }

	    abas.repaint();
	    fmImovel.show();
	    
	    String mensagem = MessageDispatcher.getMensagemError();

	    if ( mensagem != null && !mensagem.equals( "" ) ){
		boolean respostaPositiva = false;
		
		while ( !respostaPositiva ){
		    respostaPositiva = Util.perguntarAcao( mensagem + ". Confirma ?" , false, false );
		}
		
		TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
		
	    } else {				
		fmImovel.componenteRecebeFoco.requestFocus();
	    }
	    
	} catch (Exception e) {
	    Util.mostrarErro("Erro ao criar as abas", e);
	    e.printStackTrace();
	}
    }

    public void actionPerformed(ActionEvent evt) {

	if (!executandoAcao) {
	    executandoAcao = true;

	    if (evt.getCommand() == voltarMenu) {
	    	TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
	    } else if (evt.getCommand() == imprimir) {
	    	if (habilitaOpcaoImpressao){
	    		//Daniel - Verificar se a data atual é anterior ao mes de referencia da rota em andamento.
	    		if(Util.compararData(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), Util.dataAtual()) > 0){
	    			Util.MensagemConfirmacao("Data do celular está   errada. Por favor, verifique a configuração do celular e tente novamente.");

	    		// Data do celular esta correta.
	    		}else{

	    			boolean leituraInvalida = true;
	    			
	    			// Se o imovel já foi concluido e possui consumo de agua ou esgoto já calculado.
	   				if ( (Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(getImovelSelecionado().getId()))) &&
	   					 (getImovelSelecionado().getConsumoAgua() != null || getImovelSelecionado().getConsumoEsgoto() != null) ) {
	   				
	   					// Nao será recalculado o consumo
		    			Util.mostrarErro("Novos valores de leitura e anormalidade serão desconsiderados.");
				    	leituraInvalida = BusinessConta.getInstancia().imprimirCalculo(true, true);
	    			
	    			}else{
	    				// calcula consumo
			    		leituraInvalida = BusinessConta.getInstancia().imprimirCalculo(true, false);
	    			}
		
    				getImovelSelecionado().setIndcGeracao(Constantes.SIM);
	    			
    				// Caso o valor da conta seja menor que o valor permitido para ser impresso, não imprimir a conta.
	    			boolean valorAcimaDoMinimo = true;
	    			boolean valorContaMaiorPermitido = false;
	    			boolean emiteConta = true; 
	    			boolean reterConta = false; 
		    		boolean permiteImpressao = true;

	    			double valorConta = getImovelSelecionado().getValorConta();			
	    			valorAcimaDoMinimo = getImovelSelecionado().isValorContaAcimaDoMinimo();
	    			valorContaMaiorPermitido = getImovelSelecionado().isValorContaMaiorPermitido();

		    		if (getImovelSelecionado().getIndcEmissaoConta() == 2 && leituraInvalida == false) {
	
						// A conta já não seria impressa. Mas nos casos abaixo, deve reter a conta, isto é, não deve ser faturado no Gsan.
						if ( Integer.parseInt(getImovelSelecionado().getCodigoPerfil()) == ImovelConta.PERFIL_GOVERNO_METROPOLITANO){
							
		    				reterConta = true;

						}else{
							// Conta centralizada nao permite impressao. E não é retido.
		    				emiteConta = false;
						}
		    		// Daniel - Verificando Consumo de agua e Anormalidades de Consumo e Anormalidades de Leitura para imoveis CORPORATIVOS e CONDOMINIAIS
	    			}else if (getImovelSelecionado().getConsumoAgua() != null){
	    				
	    				if ( (getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ALTO_CONSUMO ||
	    					getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ESTOURO_MEDIA ||
	    					getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ESTOURO ||
	    					getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO)
	    					
	    					||
	    					
	    					( (Integer.parseInt(getImovelSelecionado().getCodigoPerfil()) == ImovelConta.PERFIL_CORPORATIVO ||
	    					   Integer.parseInt(getImovelSelecionado().getCodigoPerfil()) == ImovelConta.PERFIL_CONDOMINIAL) 
	    					   &&
	    					  (getImovelSelecionado().getConsumoAgua().getAnormalidadeLeituraFaturada() == ControladorConta.ANORM_HIDR_LEITURA_IMPEDIDA_CLIENTE ||
	  	    				   getImovelSelecionado().getConsumoAgua().getAnormalidadeLeituraFaturada() == ControladorConta.ANORM_HIDR_PORTAO_FECHADO) ) ){
				    
		    				reterConta = true;
	    				}
	    			}
		    		
    				if (!leituraInvalida && !emiteConta){
    					
	    				Util.MensagemConfirmacao("Conta do imóvel nao pode ser emitida!");	
	    				permiteImpressao = false;
    				
    				} else if (!leituraInvalida && (valorContaMaiorPermitido || reterConta)){
    				
	    				getImovelSelecionado().setIndcGeracao(Constantes.NAO);
	    				Repositorio.salvarObjeto(getImovelSelecionado());
	    				Util.mostrarErro("Conta retida, entrega posterior!");	
	    				permiteImpressao = false;

    				} else if (!leituraInvalida && !valorAcimaDoMinimo) {
	    				Util.mostrarErro("Valor da conta menor que o permitido!");
	    				// Imovel com conta abaixo do minimo nao deve ser impresso, mas não deve fazer parte dos imoveis com conta a imprimir no Gsan. 
	    				getImovelSelecionado().setIndcImovelImpresso(Constantes.SIM);
	    				Repositorio.salvarObjeto(getImovelSelecionado());
	    				permiteImpressao = false;
	
	    			} else if (!leituraInvalida && 
	    					    (getImovelSelecionado().getIndicadorParalizarFaturamentoAgua() == Constantes.SIM || 
	    					     getImovelSelecionado().getIndicadorParalizarFaturamentoEsgoto() == Constantes.SIM)){
						
	    				getImovelSelecionado().setIndcGeracao(Constantes.NAO);
	    				Repositorio.salvarObjeto(getImovelSelecionado());
	    				Util.mostrarErro("Não é permitido a impressão de conta deste imóvel.");	
	    				permiteImpressao = false;

	    			} else if ( !leituraInvalida && valorConta == 0d && getImovelSelecionado().getValorResidualCredito() == 0d) {
	    				Util.mostrarErro("Conta com valor zerado e sem crédito. Não imprimir!");
	    				permiteImpressao = false;

	    			// Daniel - Imovel com Endereço alternativo
					// caso nao haja erro de leitura e imovel contem endereço alternativo
					// E  existe imovel sem endereço de entrega normal ainda pendente. 
	    			} else if (!leituraInvalida && 
				    			Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId()))){

	    				Util.mostrarErro("Conta do imóvel nao pode ser emitida! Entrega  posterior!");	
	    				permiteImpressao = false;

//	    			} else if (!leituraInvalida && 
//			    		Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId())) &&
//			    		(Configuracao.getInstancia().getQtdImoveis() - Configuracao.getInstancia().getIdsImoveisConcluidos().size()) > Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().size()){
//				    	
//	    				System.out.println("Imovel com endereço de entrega alternativo!");
//	    				Util.mostrarErro("Imovel com endereço de entrega alternativo! A conta deste imóvel deve ser impressa somente no final da rota.");
		    	
	    			}else {
	    				// Validar situacao do retorno do calculo
	    				if (leituraInvalida == false) {
	    					boolean pesquisarDispositivos = true;
	    					boolean erroImpressao = false;
		
					    // Verificamos se algo ja foi salvo nas configurações
//Daniel
	    					if (Fachada.getInstancia().getPrinter().equals("1")){
						    	if (!Constantes.NULO_STRING.equals(Configuracao.getInstancia().getBluetoothAddress())) {
			
						    		pesquisarDispositivos = false;
							
//						    		if (Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId()))){
//						    			Util.mensagemAviso("ATENÇÃO!", "Imóvel com endereço de entrega alternativo.");
//						    		}
						    		erroImpressao = Util.imprimirConta(Constantes.IMPRESSAO_NORMAL);
						    	}
			
						    	try {
							
								if (pesquisarDispositivos) {
							    	LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
							    	InquiryList.getInstancia().criarTelaPesquisaDispositivos(true);
								}
							
						    	} catch (Exception e) {
									Util.mostrarErro("Bluetooth desativado!", e);
									e.printStackTrace();
						    	}
	    					 }else{
//					    		if (Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId()))){
//					    			Util.mensagemAviso("ATENÇÃO!", "Imóvel com endereço de entrega alternativo.");
//					    		}
	    						 erroImpressao = Util.imprimirConta(Constantes.IMPRESSAO_NORMAL);
	    						 pesquisarDispositivos = false;
	    					 }
		
					    	if (!pesquisarDispositivos && !erroImpressao) {
					    		ControladorImoveis.getInstancia().proximo();
					    		getInstancia().criarAbas();
					    }
					}
			    }
	   			if (!permiteImpressao){
	   				// Daniel - lista de imoveis impressos
	   				Configuracao.getInstancia().getIdsImoveisPendentes().removeElement( new Integer(getImovelSelecionado().getId()) );
	   				if (!Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(getImovelSelecionado().getId()) )) {
	   					Configuracao.getInstancia().getIdsImoveisConcluidos().addElement( new Integer(getImovelSelecionado().getId()) );
	   				}

//	   				if(	Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId())) ){
//	   					Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().removeElement( new Integer(getImovelSelecionado().getId()) );
//	   				}				

					ControladorImoveis.getInstancia().proximo();
    				Abas.getInstancia().criarAbas();

	   			}
	    		
	    	}
	    }else{
	    	Util.mostrarErro("Não é permitido imprimir.");
	    }
	    } else if (evt.getCommand() == proximoImovel) {

		boolean leituraInvalida = false;

		// Calculamos sempre para o imóveis tipo condomínio
		if (getImovelSelecionado().isImovelCondominio()) {
		    if (getImovelSelecionado().verificarAlteracaoDadosImovel()) {
				if (getImovelSelecionado().verificarLeituraAnormalidadeZeradas()) {
	
				    ImovelReg8 ligacaoAgua = getImovelSelecionado().getRegistro8(Constantes.LIGACAO_AGUA);
				    ImovelReg8 ligacaoPoco = getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO);
	
				    if (ligacaoAgua != null) {
					ligacaoAgua.setLeitura(Constantes.NULO_INT);
					ligacaoAgua.setAnormalidade(Constantes.NULO_INT);
				    }
	
				    if (ligacaoPoco != null) {
					ligacaoPoco.setLeitura(Constantes.NULO_INT);
					ligacaoPoco.setAnormalidade(Constantes.NULO_INT);
				    }
	
				    getImovelSelecionado().setConsumoAgua( null );
				    getImovelSelecionado().setConsumoEsgoto( null );
				    
				    Repositorio.salvarObjeto( getImovelSelecionado() );
				    
				    ligacaoAgua = null;
				    ligacaoPoco = null;
				} else {
										
	    			// Se o imovel já foi concluido.
	   				if (Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(getImovelSelecionado().getId()) )) {
	   				
		    			if (!getImovelSelecionado().getSituacaoLigAgua().equals(Constantes.CORTADO)){
		    				Util.mostrarErro("Novos valores de leitura e anormalidade serão desconsiderados.");
		    			}
	   					// Nao será recalculado o consumo
				    	leituraInvalida = BusinessConta.getInstancia().imprimirCalculo(true, true);
	    			
	    			}else{
	    				// calcula consumo
			    		leituraInvalida = BusinessConta.getInstancia().imprimirCalculo(true, false);
	    			}
				}
		    }
		}

		if (!leituraInvalida) {
		    ControladorImoveis.getInstancia().proximo();
		    getInstancia().criarAbas();
		}

	    } else if (evt.getCommand() == imovelAnterior) {

		boolean leituraInvalida = false;

		// Calculamos sempre para o imóveis tipo condomínio
		if (getImovelSelecionado().isImovelCondominio()) {
		    if (getImovelSelecionado().verificarAlteracaoDadosImovel()) {
				if (getImovelSelecionado().verificarLeituraAnormalidadeZeradas()) {
				    
				    ImovelReg8 ligacaoAgua = getImovelSelecionado().getRegistro8(Constantes.LIGACAO_AGUA);
				    ImovelReg8 ligacaoPoco = getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO);
	
				    if (ligacaoAgua != null) {
					ligacaoAgua.setLeitura(Constantes.NULO_INT);
					ligacaoAgua.setAnormalidade(Constantes.NULO_INT);
				    }
	
				    if (ligacaoPoco != null) {
					ligacaoPoco.setLeitura(Constantes.NULO_INT);
					ligacaoPoco.setAnormalidade(Constantes.NULO_INT);
				    }			    
				    
				    getImovelSelecionado().setConsumoAgua( null );
				    getImovelSelecionado().setConsumoEsgoto( null );
				    
				    Repositorio.salvarObjeto( getImovelSelecionado() );
				    
				    ligacaoAgua = null;
				    ligacaoPoco = null;			    
				} else {
	    			// Se o imovel já foi concluido.
	   				if (Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(getImovelSelecionado().getId()) )) {
	   				
	   					// Nao será recalculado o consumo
		    			Util.mostrarErro("Novos valores de leitura e anormalidade serão desconsiderados.");
				    	leituraInvalida = BusinessConta.getInstancia().imprimirCalculo(true, true);
	    			
	    			}else{
	    				// calcula consumo
			    		leituraInvalida = BusinessConta.getInstancia().imprimirCalculo(true, false);
	    			}
				}
		    }
		}

		if (!leituraInvalida) {
		    ControladorImoveis.getInstancia().anterior();
		    getInstancia().criarAbas();
		}
	    

	    // Comando e validacoes para possibilitar calculo da conta
//	    } else if (evt.getCommand() == calcular) {
//
//	    	if (habilitaOpcaoCalculo){
//			    int entradaConta = 0;
//				entradaConta++;
//		
//				boolean leituraInvalida = true;
//
//				// Se o imovel já foi concluido.
//   				if (Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(getImovelSelecionado().getId()) )) {
//   				
//   					// Nao será recalculado o consumo
//	    			Util.mostrarErro("Novos valores de leitura e anormalidade serão desconsiderados.");
//			    	leituraInvalida = BusinessConta.getInstancia().imprimirCalculo(true, true);
//    			
//    			}else{
//    				// calcula consumo
//		    		leituraInvalida = BusinessConta.getInstancia().imprimirCalculo(true, false);
//    			}
//				
//   				if (leituraInvalida == false) {
//		
//				    ImovelReg8 registroAgua = getImovelSelecionado().getRegistro8(Constantes.LIGACAO_AGUA);
//				    ImovelReg8 registroPoco = getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO);
//		
//				    if (abas.getTabCount() == 2) {
//		
//						if (registroAgua != null) {
//			
//						    abas.addTab("Conta", AbaConta.getInstancia().criarAbaContaCalculo());
//			
//						    abas.setSelectedIndex(2);
//						    componenteSelecionado(2);
//						    fmImovel.componenteRecebeFoco.requestFocus();
//						    fmImovel.componenteRecebeFoco.getStyle().setFont(fmImovel.fonteSelecionado);
//						    fmImovel.componenteRecebeFoco = null;
//			
//						}
//						if (registroPoco != null) {
//			
//						    abas.addTab("Conta", AbaConta.getInstancia().criarAbaContaCalculo());
//			
//						    abas.setSelectedIndex(2);
//						    componenteSelecionado(2);
//			
//						    fmImovel.componenteRecebeFoco.requestFocus();
//						    fmImovel.componenteRecebeFoco.getStyle().setFont(fmImovel.fonteSelecionado);
//						    fmImovel.componenteRecebeFoco = null;
//						}
//				    } else {
//		
//						// Verificaa se existem as 3 abas
//						if (abas.getTabCount() == 3) {
//			
//						    if (registroAgua != null && registroPoco != null) {
//								abas.addTab("Conta", AbaConta.getInstancia().criarAbaContaCalculo());
//				
//								// abas.removeTabAt(3);
//								abas.setSelectedIndex(3);
//								componenteSelecionado(3);
//				
//								fmImovel.componenteRecebeFoco.requestFocus();
//								fmImovel.componenteRecebeFoco.getStyle().setFont(fmImovel.fonteSelecionado);
//								fmImovel.componenteRecebeFoco = null;
//						    }
//			
//						    if (registroAgua != null && registroPoco == null) {
//								abas.addTab("Conta", AbaConta.getInstancia().criarAbaContaCalculo());
//				
//								abas.removeTabAt(2);
//								abas.setSelectedIndex(2);
//								componenteSelecionado(2);
//								fmImovel.componenteRecebeFoco.requestFocus();
//								fmImovel.componenteRecebeFoco.getStyle().setFont(fmImovel.fonteSelecionado);
//								fmImovel.componenteRecebeFoco = null;		
//						    }
//			
//						    if (registroPoco != null && registroAgua == null) {
//			
//								abas.addTab("Conta", AbaConta.getInstancia().criarAbaContaCalculo());
//				
//								abas.removeTabAt(2);
//								abas.setSelectedIndex(2);
//								componenteSelecionado(2);
//								fmImovel.componenteRecebeFoco.requestFocus();
//								fmImovel.componenteRecebeFoco.getStyle().setFont(fmImovel.fonteSelecionado);
//								fmImovel.componenteRecebeFoco = null;
//						    }
//			
//						    if (registroAgua == null && registroPoco == null) {
//								abas.addTab("Conta", AbaConta.getInstancia().criarAbaContaCalculo());
//				
//								abas.removeTabAt(2);
//								abas.setSelectedIndex(2);
//								componenteSelecionado(2);
//								fmImovel.componenteRecebeFoco.requestFocus();
//								fmImovel.componenteRecebeFoco.getStyle().setFont(fmImovel.fonteSelecionado);
//								fmImovel.componenteRecebeFoco = null;
//						    }
//						} else if (abas.getTabCount() == 4) {
//						    if (registroAgua != null && registroPoco != null) {
//								abas.addTab("Conta", AbaConta.getInstancia().criarAbaContaCalculo());
//				
//								abas.removeTabAt(3);
//								abas.setSelectedIndex(3);
//								componenteSelecionado(3);
//				
//								fmImovel.componenteRecebeFoco.requestFocus();
//								fmImovel.componenteRecebeFoco.getStyle().setFont(fmImovel.fonteSelecionado);
//								fmImovel.componenteRecebeFoco = null;
//						    }
//						}
//				    }
//				}
//	    	}else{
//	    		Util.mostrarErro("Não é permitido calcular.");
//	    	}
	    } else if (evt.getCommand() == imprimirImovelCondominio) {

			// Antes de qualquer coisa calculamos o consumo do imovel atual
			boolean leituraInvalida = false;
	
			if (getImovelSelecionado().verificarAlteracaoDadosImovel()) {
    			// Se o imovel já foi concluido.
   				if (Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(getImovelSelecionado().getId()) )) {
   				
   					// Nao será recalculado o consumo
	    			Util.mostrarErro("Novos valores de leitura e anormalidade de todo o condomínio serão desconsiderados.");
			    	leituraInvalida = BusinessConta.getInstancia().imprimirCalculo(true, true);
    			
    			}else{
    				// calcula consumo
		    		leituraInvalida = BusinessConta.getInstancia().imprimirCalculo(true, false);
    			}
			}
	
			if (!leituraInvalida) {
			    // Selecionamos sempre o hidrometro macro, pois ele sempre
			    // será nosso ponto de partida		    
			    ImovelConta imovelHidrometroMacro = new ImovelConta();
			    Repositorio.carregarObjeto( imovelHidrometroMacro, getImovelSelecionado().getIdImovelCondominio() );
	
			    int idPrimeiroImovel = imovelHidrometroMacro.getId() + 1;
			    int idUltimoImovel = imovelHidrometroMacro.getId() + imovelHidrometroMacro.getQuantidadeImoveisCondominio() - 1;
	
				if (Fachada.getInstancia().getPrinter().equals("1")){

				    // Verificamos se algo ja foi salvo nas configurações
				    if (Constantes.NULO_STRING.equals(Configuracao.getInstancia().getBluetoothAddress())) {
						try {
						    LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
						    InquiryList.getInstancia().criarTelaPesquisaDispositivos(true);
						} catch (BluetoothStateException e) {
						    Util.mostrarErro("Erro na pesquisa do dispositivo", e);
						}
				    } else {
					// [UC0970] Efetuar Rateio de Consumo no Dispositivo Movel
						BackgroudTaskRatearConsumoImovelCondominio bkRatear = new BackgroudTaskRatearConsumoImovelCondominio(idPrimeiroImovel, idUltimoImovel);
						TelaProgresso.getInstancia().criarTelaProgresso("Efetuando rateio/impressão...", bkRatear);
				    }
			    
			    }else{
					BackgroudTaskRatearConsumoImovelCondominio bkRatear = new BackgroudTaskRatearConsumoImovelCondominio(idPrimeiroImovel, idUltimoImovel);
					TelaProgresso.getInstancia().criarTelaProgresso("Efetuando rateio/impressão...", bkRatear);			    	
			    }
			}
	    } else if (evt.getCommand() == localizarPendente) {
		ControladorImoveis.getInstancia().proximoPendente();
	    } else if (evt.getCommand() == localizarProximoARevisitar) {
		ControladorImoveis.getInstancia().proximoARevisitar();
	    }

	    executandoAcao = false;
	}
    }

    public void selectionChanged(int inicio, int proxima) {
	componenteSelecionado(proxima);
    }

    public void componenteSelecionado(int proxima) {

	ImovelReg8 tipoAgua = getImovelSelecionado().getRegistro8(Constantes.LIGACAO_AGUA);
	ImovelReg8 tipoPoco = getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO);

	if (proxima == 1) {
	    if (tipoAgua != null) {
		fmImovel.componenteRecebeFoco = AbaHidrometroAgua.getInstancia().getLeituraCampo();
	    } else if (tipoPoco != null) {
		fmImovel.componenteRecebeFoco = AbaHidrometroPoco.getInstancia().getLeituraCampo();
	    } else {
		fmImovel.componenteRecebeFoco = AbaConta.getInstancia().getLeituraFaturadaAgua();
	    }
	} else if (proxima == 2) {
	    if (tipoAgua != null && tipoPoco != null) {
		fmImovel.componenteRecebeFoco = AbaHidrometroPoco.getInstancia().getLeituraCampo();
	    } else {
		fmImovel.componenteRecebeFoco = AbaConta.getInstancia().getLeituraFaturadaAgua();
	    }
	} else if (proxima == 0) {
	    fmImovel.componenteRecebeFoco = AbaImoveis.getInstancia().getMatricula();
	} else {
	    fmImovel.componenteRecebeFoco = AbaConta.getInstancia().getLeituraFaturadaAgua();
	}

    }

    // Focus Componentes
    public void setFocused1(Component c) {
	// Component current = fmImovel.getFocused();
	Component current = c;
	// if (current != null && current != c) {
	if (current != null) {
	    futureFocus = c;
	    focusMotionX = Motion.createSplineMotion(current.getAbsoluteX(), futureFocus.getAbsoluteX(), 300);
	    focusMotionY = Motion.createSplineMotion(current.getAbsoluteY(), futureFocus.getAbsoluteY(), 300);
	    focusMotionWidth = Motion.createSplineMotion(current.getWidth(), futureFocus.getWidth(), 300);
	    focusMotionHeight = Motion.createSplineMotion(current.getHeight(), futureFocus.getHeight(), 300);
	    focusMotionX.start();
	    focusMotionY.start();
	    focusMotionWidth.start();
	    focusMotionHeight.start();
	    fmImovel.setFocused(null);
	} else {
	    fmImovel.setFocused(c);
	}
    }

    public boolean animate1() {
	boolean val = fmImovel.animate();
	if (futureFocus != null && focusMotionX.isFinished()) {
	    fmImovel.setFocused(futureFocus);
	    futureFocus = null;
	    focusMotionX = null;
	    focusMotionY = null;
	    focusMotionWidth = null;
	    focusMotionHeight = null;
	}
	return val || futureFocus != null;
    }

    public void KeyPressed(int KeyCode) {

	// System.out.println("entrou no keypressed abas");
	if (KeyCode == -3) {
	    if (abas.getSelectedIndex() == 3) {
		abas.setSelectedIndex(2);
	    }
	    if (abas.getSelectedIndex() == 2) {
		abas.setSelectedIndex(1);
	    }
	    if (abas.getSelectedIndex() == 1) {
		abas.setSelectedIndex(0);
	    }
	}

	if (KeyCode == -4) {
	    if (abas.getSelectedIndex() == 0) {
		abas.setSelectedIndex(1);
	    }
	    if (abas.getSelectedIndex() == 1) {
		abas.setSelectedIndex(2);
	    }
	    if (abas.getSelectedIndex() == 2) {
		abas.setSelectedIndex(3);
	    }
	}

    }

    public ImovelConta getImovelSelecionado(){
    	return ControladorImoveis.getInstancia().getImovelSelecionado();
    }
}
