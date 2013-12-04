/* * Copyright (C) 2007-2009 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento * * This file is part of GSAN, an integrated service management system for Sanitation * * GSAN is free software; you can redistribute it and/or modify * it under the terms of the GNU General Public License as published by * the Free Software Foundation; either version 2 of the License. * * GSAN is distributed in the hope that it will be useful, * but WITHOUT ANY WARRANTY; without even the implied warranty of * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the * GNU General Public License for more details. * * You should have received a copy of the GNU General Public License * along with this program; if not, write to the Free Software * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA *//* * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento * Copyright (C) <2007>  * Adriano Britto Siqueira * Alexandre Santos Cabral * Ana Carolina Alves Breda * Ana Maria Andrade Cavalcante * Aryed Lins de Araújo * Bruno Leonardo Rodrigues Barros * Carlos Elmano Rodrigues Ferreira * Clêudio de Andrade Lira * Denys Guimarães Guenes Tavares * Eduardo Breckenfeld da Rosa Borges * Fabíola Gomes de Araújo * Flêvio Leonardo Cavalcanti Cordeiro * Francisco do Nascimento Júnior * Homero Sampaio Cavalcanti * Ivan Sérgio da Silva Júnior * José Edmar de Siqueira * José Thiago Tenório Lopes * Kássia Regina Silvestre de Albuquerque * Leonardo Luiz Vieira da Silva * Márcio Roberto Batista da Silva * Maria de Fátima Sampaio Leite * Micaela Maria Coelho de Araújo * Nelson Mendonça de Carvalho * Newton Morais e Silva * Pedro Alexandre Santos da Silva Filho * Rafael Corrêa Lima e Silva * Rafael Francisco Pinto * Rafael Koury Monteiro * Rafael Palermo de Araújo * Raphael Veras Rossiter * Roberto Sobreira Barbalho * Roberto Souza * Rodrigo Avellar Silveira * Rosana Carvalho Barbosa * Sávio Luiz de Andrade Cavalcante * Tai Mu Shih * Thiago Augusto Souza do Nascimento * Tiago Moreno Rodrigues * Vivianne Barbosa Sousa * * Este programa é software livre; você pode redistribuí-lo e/ou * modificá-lo sob os termos de Licença Pública Geral GNU, conforme * publicada pela Free Software Foundation; versão 2 da * Licença. * Este programa é distribuído na expectativa de ser útil, mas SEM * QUALQUER GARANTIA; sem mesmo a garantia implêcita de * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais * detalhes. * Você deve ter recebido uma cópia da Licença Pública Geral GNU * junto com este programa; se não, escreva para Free Software * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA * 02111-1307, USA. */package com.ipad.view;import com.ipad.background.BackgroundTaskCarregarIndices;import com.ipad.background.BackgroundTaskGerarArquivoRetorno;import com.ipad.background.BackgroundTaskImprimirImoveisFixos;import com.ipad.background.BackgroundTaskImprimirTodosImoveisMedia;import com.ipad.background.BackgroundTaskProcessarArquivoIda;import com.ipad.basic.Configuracao;import com.ipad.basic.ImovelConta;import com.ipad.basic.ImovelReg11;import com.ipad.business.ControladorImoveis;import com.ipad.facade.Fachada;import com.ipad.io.ArquivoRetorno;import com.ipad.io.InquiryList;import com.ipad.io.Repositorio;import com.ipad.util.Constantes;import com.ipad.util.Util;import com.sun.lwuit.Command;import com.sun.lwuit.Font;import com.sun.lwuit.Form;import com.sun.lwuit.List;import com.sun.lwuit.animations.CommonTransitions;import com.sun.lwuit.events.ActionEvent;import com.sun.lwuit.events.ActionListener;import com.sun.lwuit.layouts.BorderLayout;public class TelaMenuPrincipal implements ActionListener {    private Form fmMenuPrincipal;    private List listMenuPrincipal;    private Command ok;    private Command sair;    private Font font;    private int opcaoMenuPrincipal;    private static TelaMenuPrincipal instancia;    public static TelaMenuPrincipal getInstancia() {	if (instancia == null) {	    instancia = new TelaMenuPrincipal();	}	return instancia;    }    public TelaMenuPrincipal() {    }    public void criarTelaMenuPrincipal() {		ImovelConta imovel = (ImovelConta) Repositorio.pesquisarPrimeiroObjeto(ImovelConta.class);		fmMenuPrincipal = new Form("Menu Principal");	font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD | Font.STYLE_ITALIC, Font.SIZE_LARGE);	// Cria command Sair	sair = new Command("Sair");	fmMenuPrincipal.addCommand(sair);	// Cria commando OK	ok = new Command("OK");	fmMenuPrincipal.addCommand(ok);	fmMenuPrincipal.setCommandListener(this);	// Tela do Menu Principal	listMenuPrincipal = new List();	listMenuPrincipal.setFixedSelection(List.FIXED_NONE_CYCLIC);	listMenuPrincipal.addItem("Lista de Imóveis - Grupo " + imovel.getGrupoFaturamento() );//	listMenuPrincipal.addItem("Inverter Roteiro");	listMenuPrincipal.addItem("Finalizar Roteiro");	listMenuPrincipal.addItem("Consultas");	listMenuPrincipal.addItem("Relatórios");	listMenuPrincipal.addItem("Apagar Tudo");	listMenuPrincipal.addItem("Selecionar Impressora");	listMenuPrincipal.addItem("Finalizar Roteiro Incompleto");	listMenuPrincipal.addItem("Enviar Imóveis Concluidos Até Agora");	listMenuPrincipal.addItem("Selecionar Papel");		ImovelReg11 reg11 = ImovelReg11.getInstancia();	if(reg11.getIndicadorTransmissaoOffline() == Constantes.NAO		|| reg11.getIndicadorTransmissaoOffline() == Constantes.NULO_INT ){	    listMenuPrincipal.addItem("Tornar Roteiro OffLine");	}else{	    listMenuPrincipal.addItem("Tornar Roteiro OnLine");	}	listMenuPrincipal.addItem( "Gerar arquivo de retorno COMPLETO" );	listMenuPrincipal.addItem("Informações sobre a rota");// Daniel - Imprimir lista de imoveis nao hidrometrados.	listMenuPrincipal.addItem("Imprimir imóveis não-hidrometrados");	if (Fachada.getInstancia().getCalcularPeloConsumoMedio().equals("1") ||		(ImovelReg11.getInstancia().getIdCalculoMedia() == Constantes.SIM)){		listMenuPrincipal.addItem("Imprimir todos imoveis pela média");	}		if ( reg11.getIndcAtualizarSequencialRota() == Constantes.SIM ){	    	    if ( Configuracao.getInstancia().getIndcRotaMarcacaoAtivada() == Constantes.SIM ){		listMenuPrincipal.addItem("Rota de Marcação [HABILITADO]");			    } else {		listMenuPrincipal.addItem("Rota de Marcação [DESABILITADO]");	    }	}			fmMenuPrincipal.setSmoothScrolling(true);	fmMenuPrincipal.getStyle().setFont(font);	fmMenuPrincipal.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 1500));	fmMenuPrincipal.setLayout(new BorderLayout());	fmMenuPrincipal.addComponent(BorderLayout.CENTER, listMenuPrincipal);	fmMenuPrincipal.show();	if (!Repositorio.indicesCarregados) {	    BackgroundTaskCarregarIndices BTIndices = new BackgroundTaskCarregarIndices();	    BTIndices.start();	}    }    public void actionPerformed(ActionEvent evt) {		if (evt.getCommand() == ok) {	    	    this.opcaoMenuPrincipal = listMenuPrincipal.getSelectedIndex();	    	    if (this.opcaoMenuPrincipal == Constantes.LISTAR_IMOVEIS) {						ControladorImoveis.getInstancia().carregarImovelSelecionado();		Abas abas = Abas.getInstancia();		abas.criarAbas();/*	    } else if (this.opcaoMenuPrincipal == Constantes.INVERTER_ROTEIRO) {		// Configuracao.getInstancia().setIdImovelSelecionado(Configuracao.getInstancia().getQtdImoveis());		ControladorImoveis.getInstancia().inverterRoteiro();		Util.mensagemSucesso("Roteiro invertido com  sucesso!");		*/	    } else if (this.opcaoMenuPrincipal == Constantes.CONSULTAS) {    		// Verificamos se existe roteiro em andamento		Consultas.getInstancia().criarConsulta();			    } else if (this.opcaoMenuPrincipal == Constantes.RELATORIOS) {						new Relatorios();  		    	    } else if ( this.opcaoMenuPrincipal == Constantes.FINALIZAR_ROTEIRO ) {		//Daniel - Considerar imoveis nao impressos				if ( Configuracao.getInstancia().getIdsImoveisPendentes().size() > 0 ) {		    			Util.mensagemAviso("Erro Roteiro", "Ainda há " + (Configuracao.getInstancia().getQtdImoveis() - Configuracao.getInstancia().getIdsImoveisConcluidos().size())+ " imoveis pendentes!");		    ControladorImoveis.getInstancia().proximoPendente();		   		} else if ( Configuracao.getInstancia().getMatriculasRevisitar().size() > 0 ) {		    Util.mensagemAviso("Erro Roteiro", "Ainda há imoveis a revisitar!");		    ControladorImoveis.getInstancia().proximoARevisitar();		} else {	    	if (Util.hasInternetAccess()){				BackgroundTaskGerarArquivoRetorno BTEnviarArquivoRetorno = new BackgroundTaskGerarArquivoRetorno(ArquivoRetorno.ARQUIVO_COMPLETO, false);			    TelaProgresso.getInstancia().criarTelaProgresso("Finalizando Roteiro...", BTEnviarArquivoRetorno);			    	}else{	    		Util.mostrarErro("Operação abortada!");	    	}		}	    } else if (this.opcaoMenuPrincipal == Constantes.FINALIZAR_ROTEIRO_INCOMPLETO) {		//		if (Util.perguntarAcao("A ROTA SERÁ FINALIZADA E O APLICATIVO SERÁ FECHADO, TEM CERTEZA ?", true, true)) {//Daniel - Nao será permitido finalizar a rota incompleta!//		    BackgroundTaskGerarArquivoRetorno BTEnviarArquivoRetorno = new BackgroundTaskGerarArquivoRetorno(ArquivoRetorno.ARQUIVO_INCOMPLETO, false );//		    TelaProgresso.getInstancia().criarTelaProgresso("Finalizando Roteiro...", BTEnviarArquivoRetorno);	    	Util.mensagemAviso("Aviso:", "Serviço indisponivel.");//		}			    } else if (this.opcaoMenuPrincipal == Constantes.ENVIAR_TODOS_IMOVEIS_CONCLUIDOS_E_NAO_ENVIADOS) {//Daniel	    	if (Util.hasInternetAccess()){		    	BackgroundTaskGerarArquivoRetorno BTEnviarArquivoRetorno = new BackgroundTaskGerarArquivoRetorno(ArquivoRetorno.ARQUIVO_CONCLUIDOS_ATE_AGORA, false );		    	TelaProgresso.getInstancia().criarTelaProgresso("Enviando imóveis lidos e impressos mas não enviados...", BTEnviarArquivoRetorno);	    	}else{	    		Util.mostrarErro("Operação Cancelada!");	    	}	    		// opcao apagar tudo	    } else if (this.opcaoMenuPrincipal == Constantes.APAGAR_TUDO) {				if (Util.perguntarAcao("Tem certeza que deseja apagar os dados?", true, false)) {		    Repositorio.deleteRecordStore();		    Util.mensagemAviso("Apagado!", "Dados apagados com   sucesso!");		    Fachada.getInstancia().exit();		} else {		    System.out.println("Nao apagar tudo!");		}		                // ApagarTudoBusiness.getInstancia().validarAcaoApagar();		// Apos apagar os registros a aplicacao sera fechada		// Fachada.getInstancia().exit();	    } else if ( this.opcaoMenuPrincipal == Constantes.SELECIONAR_IMPRESSORA) {				InquiryList.getInstancia().criarTelaPesquisaDispositivos(false);			    } else if ( this.opcaoMenuPrincipal == Constantes.SELECIONAR_PAPEL ) {				TelaSelecaoPapel.getInstancia().criarTelaSelecaoPapel();			    // opcao sobre	    } else if (this.opcaoMenuPrincipal == Constantes.INFORMACOES_ROTA) {				InformacoesRota.getInstancia().criarTelaInformaçaoRota();// Daniel - imprimir imoveis nao-hidrometrados			    } else if (this.opcaoMenuPrincipal == Constantes.IMPRIMIR_IMOVEIS_NAO_HIDROMETRADOS){		    		    BackgroundTaskImprimirImoveisFixos imprimeLista = new BackgroundTaskImprimirImoveisFixos();		    TelaProgresso.getInstancia().criarTelaProgresso(			    "Imprimindo Imóveis Fixos...", imprimeLista);	    } else if (this.opcaoMenuPrincipal == Constantes.CALCULAR_E_IMPRIMIR_TODOS_IMOVEIS_MEDIA){		    BackgroundTaskImprimirTodosImoveisMedia imprimeLista = new BackgroundTaskImprimirTodosImoveisMedia();		    TelaProgresso.getInstancia().criarTelaProgresso("Imprimindo Imóveis pelo consumo médio...", imprimeLista);	    } else if (this.opcaoMenuPrincipal == Constantes.TORNAR_OFFLLINE){			      if ( ImovelReg11.getInstancia().getIndicadorTransmissaoOffline() == Constantes.NAO || 		   ImovelReg11.getInstancia().getIndicadorTransmissaoOffline() == Constantes.NULO_INT ) {		    boolean transformarOffline = 			Util.perguntarAcao( "Tem certeza que deseja transformar o roteiro para OffLine?", true, false );		    if (transformarOffline == true) {						ImovelReg11.getInstancia().setIndicadorTransmissaoOffline( String.valueOf(Constantes.SIM) );			Repositorio.salvarObjeto( ImovelReg11.getInstancia() );						Util.mensagemSucesso(				"Roteiro tranformado para OffLine com sucesso! Por favor inicie a aplicação novamente." );						Fachada.getInstancia().exit();					    } else {						Util.mostrarErro("O roteiro não foi tranformado para OffLine!");					    }		} else {		    boolean transformarOnline = Util.perguntarAcao( "Tem certeza que deseja transformar o roteiro para OnLine?", true, false );		    if ( transformarOnline == true ) {						ImovelReg11.getInstancia().setIndicadorTransmissaoOffline( String.valueOf( Constantes.NAO ) );			Repositorio.salvarObjeto(ImovelReg11.getInstancia());						Util.mensagemSucesso( "Roteiro tranformado para OnLine com sucesso! Por favor inicie a aplicação novamente." );			Fachada.getInstancia().exit();					    } else {						Util.mostrarErro("O roteiro não foi tranformado para OnLine!");					    }		}//	    } else if ( this.opcaoMenuPrincipal == Constantes.ROTA_DE_MARCACAO) {//		//		Configuracao conf = Configuracao.getInstancia();//		//		String habilitaDesabilita = "HABILITAR";//		//		if ( conf.getIndcRotaMarcacaoAtivada() == Constantes.SIM ){//		    habilitaDesabilita = "DESABILITAR";//		}//		//		String mensagem = "Deseja " + habilitaDesabilita + " a funcionalidade de rota de marcação ? ";//		//		if ( Util.perguntarAcao( mensagem , false, false ) ){//		    //		    if ( conf.getIndcRotaMarcacaoAtivada() == Constantes.SIM ){//			conf.setIndcRotaMarcacaoAtivada( Short.parseShort( Constantes.NAO + "" ) );//		    } else {//			conf.setIndcRotaMarcacaoAtivada( Short.parseShort( Constantes.SIM + "" ) );//		    }//		    //		    TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();		    //		}//			    } else if ( this.opcaoMenuPrincipal == Constantes.GERAR_ARQUIVO_RETORNO_COMPLETO ) {			ControladorImoveis.getInstancia().carregarImovelSelecionado();	    	if ( Configuracao.getInstancia().getIdsImoveisPendentes().size() > 0 ) {		    Util.mensagemAviso("Erro Roteiro", "Ainda há " + (Configuracao.getInstancia().getQtdImoveis() - Configuracao.getInstancia().getIdsImoveisConcluidos().size())+ " imoveis a percorrer!");		    ControladorImoveis.getInstancia().proximoPendente();		} else {		    BackgroundTaskGerarArquivoRetorno BTEnviarArquivoRetorno = new BackgroundTaskGerarArquivoRetorno( ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS, false );		    TelaProgresso.getInstancia().criarTelaProgresso( "Gerando Arquivo Completo...", BTEnviarArquivoRetorno );		}	    }	}		if (evt.getCommand() == sair) {	    	    Fachada.getInstancia().exit();	    	}    }}