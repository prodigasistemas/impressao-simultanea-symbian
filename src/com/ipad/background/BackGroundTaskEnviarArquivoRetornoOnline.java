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

package com.ipad.background;

import java.io.IOException;
import java.util.Vector;

import com.ipad.basic.Configuracao;
import com.ipad.basic.ImovelConta;
import com.ipad.facade.Fachada;
import com.ipad.facade.FachadaRede;
import com.ipad.io.ArquivoRetorno;
import com.ipad.io.FileManager;
import com.ipad.io.MessageDispatcher;
import com.ipad.io.Repositorio;
import com.ipad.util.Constantes;
import com.ipad.util.Util;
import com.ipad.view.TelaLogin;
import com.ipad.view.TelaMenuPrincipal;

public class BackGroundTaskEnviarArquivoRetornoOnline extends BackgroundTask {

    /**
     * Identificador da requisição Cliente->Servidor de envio das atualizações
     * pendentes dos imóveis.
     */    
    public static final byte MENU_PRINCIPAL = 1;
    public static final byte SAIR_APLICACAO = 2;
    public static final byte LOGIN = 3;

    private static boolean conseguiuEnviar = false;
    private static byte caminhoSucesso;
    private static short tipoFinalizacao;
    private static Vector idsImoveisGerados = null;
    private static byte caminhoErro;    

    /**
     * Metodo utilizado para criar a conexao e enviar os dados de retorno ao
     * servidor.
     */
    public void performTask() {
    	if (ArquivoRetorno.getConteudoArquivoRetorno() != null){
   	    	try {
   		
	    		if (tipoFinalizacao == ArquivoRetorno.ARQUIVO_INCOMPLETO || 
	        			tipoFinalizacao == ArquivoRetorno.ARQUIVO_COMPLETO || 
	    	    		tipoFinalizacao == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS){
	    			
	    			Configuracao.getInstancia().setNomeArquivoRetorno(Configuracao.getInstancia().getNomeArquivoRetorno());
			    	Repositorio.salvarObjeto(Configuracao.getInstancia());   			

	    			p.setProgress((byte)100);
				    p.repaint();
	     			FachadaRede.getInstancia().finalizarLeitura(ArquivoRetorno.getConteudoArquivoRetorno().getBytes(), tipoFinalizacao);
	 	
	    		}else{
	    			
	    			p.setProgress((byte)100);
				    p.repaint();

				    FachadaRede.getInstancia().enviarImovel(ArquivoRetorno.getConteudoArquivoRetorno().getBytes());
				
			    	// Removemos o arquivo enviado
			    	FileManager.abrir(Configuracao.getInstancia().getNomeArquivoRetorno(), false,FileManager.APAGAR);
		
			    	Configuracao.getInstancia().setNomeArquivoRetorno(null);
				
			    	Repositorio.salvarObjeto(Configuracao.getInstancia());   			
	    		}
   			
   	    	} catch (IOException e) {
				e.printStackTrace();
			}

    	}else{
			try {
			    Vector linhasRetorno = FileManager.getInstancia().lerArquivoRetorno();
		
			    String arquivoImovel = "";
			    int linhasLidas = 0;
			    
			    if (linhasRetorno != null) {
				
			    	while (0 < linhasRetorno.size()) {
						String linha = (String) linhasRetorno.elementAt(0);
				
						linhasLidas++;
		
						for (int j = 0; j < linha.length(); j++) {
						    arquivoImovel += linha.charAt(j);		
						}
		
						double d = (double) (linhasLidas) / (linhasRetorno.size() - 1);
					    byte percentual = (byte) ((d) * 100);
			
					    p.setProgress(percentual);
					    p.repaint();
		
						arquivoImovel += "\n";
						linhasRetorno.removeElementAt(0);
				    }
					 // Daniel - set linhasRetorno to null
				    linhasRetorno = null;
			    }
		
			    if ( tipoFinalizacao == ArquivoRetorno.ARQUIVO_INCOMPLETO || 
			   		 tipoFinalizacao == ArquivoRetorno.ARQUIVO_COMPLETO || 
			    	 tipoFinalizacao == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS ) {
			    	
			    	FachadaRede.getInstancia().finalizarLeitura(arquivoImovel.getBytes(), tipoFinalizacao);
		
			    } else {
			    	FachadaRede.getInstancia().enviarImovel(arquivoImovel.getBytes());
		
			    	// Removemos o arquivo enviado
			    	FileManager.abrir(Configuracao.getInstancia().getNomeArquivoRetorno(), false,FileManager.APAGAR);
		
			    	Configuracao.getInstancia().setNomeArquivoRetorno(null);
				
			    	Repositorio.salvarObjeto(Configuracao.getInstancia());
		
			    }
			} catch (IOException e) {
				// Exceção silenciosa, pois nao paramos o processamento caso
			    // aconteça algum problema
			}
    	}
	conseguiuEnviar = FachadaRede.getInstancia().isRequestOK();
    }

    public void taskFinished() {
	
	boolean sucesso = false;
	boolean naoRedirecionar = false;

	if (conseguiuEnviar) {
	    if ( tipoFinalizacao == ArquivoRetorno.ARQUIVO_INCOMPLETO || 
	    	 tipoFinalizacao == ArquivoRetorno.ARQUIVO_COMPLETO ||
	    	 tipoFinalizacao == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS ) {

		// Removemos o arquivo enviado
		FileManager.abrir(Configuracao.getInstancia().getNomeArquivoRetorno(), false, FileManager.APAGAR);

		// apagar e finalizar aplicacao
		Repositorio.deleteRecordStore();

		Util.MensagemConfirmacao("Roteiro finalizado, a  aplicação será fechada...");
		
		sucesso = true;

	    } else {
		if (idsImoveisGerados != null && idsImoveisGerados.size() > 0) {
		    // Marcamos todos os imóveis enviados como já enviados
		    for (int i = 0; i < idsImoveisGerados.size(); i++) {
			ImovelConta imovelConta = new ImovelConta();
			Repositorio.carregarObjeto(imovelConta,
				((Integer) idsImoveisGerados.elementAt(i))
					.intValue());
			imovelConta.setIndcImovelEnviado(Constantes.SIM);
			Repositorio.salvarObjeto(imovelConta);

		    }
		}

		Repositorio.salvarObjeto(Configuracao.getInstancia());
		Util.MensagemConfirmacao("Todos os imóveis lidos e impressos foram enviados...");
		
		sucesso = true;
	    }
	} else {
	    
	    String resposta = MessageDispatcher.getMensagemError();
	    
	    if ( resposta != null && !resposta.equals( "" ) ){
		Util.perguntarAcao( resposta , false, false );
		
		// Removemos o arquivo enviado
//		FileManager.abrir(Configuracao.getInstancia().getNomeArquivoRetorno(), false, FileManager.APAGAR);
		
//		boolean reenvio = caminhoSucesso == SAIR_APLICACAO;
		
		// Criamos o novo arquivo de retorno
//		BackgroundTaskGerarArquivoRetorno BTEnviarArquivoRetorno = 
//		    new BackgroundTaskGerarArquivoRetorno( ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS, false );
//		
//		TelaProgresso.getInstancia().criarTelaProgresso("Gerando Arquivo Completo...", BTEnviarArquivoRetorno);
//		
//		naoRedirecionar = true;
	    } else {
			Util.MensagemConfirmacao("Não foi possivel enviar arquivo de retorno");
			sucesso = false;
	    }

	}

	ArquivoRetorno.cleanConteudoArquivoRetorno();


	if ( !naoRedirecionar ){
	    if ( sucesso ){
		
		switch ( caminhoSucesso ) {
		     case MENU_PRINCIPAL:
			 TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
			 break;
		     case LOGIN:
			 TelaLogin.getInstancia().criarTelaLogin();
			 break;
		     case SAIR_APLICACAO:
			 Fachada.getInstancia().exit();
			 break;
		}
		
	    } else {
		
		switch ( caminhoErro ) {
		     case MENU_PRINCIPAL:
			 TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
			 break;
		     case LOGIN:
			 TelaLogin.getInstancia().criarTelaLogin();
			 break;
		     case SAIR_APLICACAO:
			 Fachada.getInstancia().exit();
			 break;
		}	
		
	    }
	}
    }

    public BackGroundTaskEnviarArquivoRetornoOnline(
	    byte caminhoSucesso,
	    byte caminhoErro,	    
	    short tipoFinalizacao, 
	    Vector idsImoveisGerados ) {
	super();
	BackGroundTaskEnviarArquivoRetornoOnline.caminhoSucesso = caminhoSucesso;
	BackGroundTaskEnviarArquivoRetornoOnline.caminhoErro = caminhoErro;
	BackGroundTaskEnviarArquivoRetornoOnline.tipoFinalizacao = tipoFinalizacao;
	BackGroundTaskEnviarArquivoRetornoOnline.idsImoveisGerados = idsImoveisGerados;
    }

    public void taskStarted() {
	this.setPriority(Thread.NORM_PRIORITY);
    }

}
