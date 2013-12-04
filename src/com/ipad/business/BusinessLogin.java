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

package com.ipad.business;

import java.util.Vector;

import com.ipad.background.BackGroundTaskEnviarArquivoRetornoOnline;
import com.ipad.background.BackgroundTaskReceberNovaVersao;
import com.ipad.basic.Configuracao;
import com.ipad.basic.DadosRelatorio;
import com.ipad.facade.Fachada;
import com.ipad.io.ArquivoRetorno;
import com.ipad.io.FileManager;
import com.ipad.io.Repositorio;
import com.ipad.util.Util;
import com.ipad.view.TelaArquivos;
import com.ipad.view.TelaLogin;
import com.ipad.view.TelaMenuPrincipal;
import com.ipad.view.TelaProgresso;

public class BusinessLogin {

    private Repositorio repositorio = Repositorio.getInstancia();

    private ControladorImoveis controladorImoveis;

    private static BusinessLogin instancia;

    /**
     * Vetor de imóveis.
     */
    public static Vector imoveis;

    /**
     * Vetor de Tarifas
     */
    public static Vector tafiras;

    public BusinessLogin() {

    }

    public void acoesLogin() {

	if (reenviarRoteiro()) {	    
	    if ( Configuracao.getInstancia().getNomeArquivoRetorno().indexOf( "COMPLETO" ) != -1 ){
	    	Util.MensagemConfirmacao("Rota pendente, com arquivo completo gerado. Enviar o arquivo ao supervisor...");
//Daniel - nao sair da aplicação, apenar avisar e voltar ao menu principal.
		TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
//		Fachada.getInstancia().exit();
	    } else {
	    	if (Util.hasInternetAccess()){

	    		Util.mensagemAviso("Aviso", "Rota pendente, tentando enviar...");
// Daniel - Evitando Loop infinito - Rota pendente e tentando enviar
	    		BackGroundTaskEnviarArquivoRetornoOnline bk = new BackGroundTaskEnviarArquivoRetornoOnline(
	    					BackGroundTaskEnviarArquivoRetornoOnline.SAIR_APLICACAO,
	    					BackGroundTaskEnviarArquivoRetornoOnline.MENU_PRINCIPAL,
	    					ArquivoRetorno.ARQUIVO_COMPLETO, null);
	    		TelaProgresso.getInstancia().criarTelaProgresso("Enviando Rota Pendente...", bk);
	    	}else{
	    		Util.mostrarErro("Operação cancelada.");
	    		TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
	    	}
	    }
	} else {
	    if (!repositorio.existeRoteiroEmAndamento()) {
/*		FachadaRede.getInstancia().baixarRoteiro();

		if (!FachadaRede.getInstancia().isRequestOK()) {
		    Util.mostrarErro("Servidor fora do ar...");
		    mostrarDiretorio();
		} else {
		    BackgroundTaskReceberArquivoIdaOnline bk = new BackgroundTaskReceberArquivoIdaOnline();
		    TelaProgresso.getInstancia().criarTelaProgresso(
			    "Carregando arquivo on-line...", bk);
		}
*/

//		BackgroundTaskReceberNovaVersao bk = new BackgroundTaskReceberNovaVersao();
//		TelaProgresso.getInstancia().criarTelaProgresso("Verificando versão...", bk);
	    	mostrarDiretorio();

	    } else {
		// TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
		if (DadosRelatorio.instancia == null) {
		    DadosRelatorio.instancia = (DadosRelatorio) Repositorio
			    .pesquisarPrimeiroObjeto(DadosRelatorio.class);
		} else {
		    DadosRelatorio.getInstancia();
		}
// Daniel
		FileManager.getInstancia();
		TelaLogin.getInstancia().criarTelaLogin();
	    }
	}

	/*
	 * if (reenviarRoteiro()) { if (!repositorio.existeRoteiroEmAndamento())
	 * { FachadaRede.getInstancia().baixarRoteiro();
	 * 
	 * if (!FachadaRede.getInstancia().isRequestOK()) {
	 * Util.mostrarErro("Servidor fora do ar..."); mostrarDiretorio(); }
	 * else { BackgroundTaskReceberArquivoIdaOnline bk = new
	 * BackgroundTaskReceberArquivoIdaOnline();
	 * TelaProgresso.getInstancia().
	 * criarTelaProgresso("Carregando arquivo on-line...", bk); } } else {
	 * // TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
	 * TelaLogin.getInstancia().criarTelaLogin(); } } else { // se não
	 * houver roteiro em aberto... if
	 * (!repositorio.existeRoteiroEmAndamento()) { this.mostrarDiretorio();
	 * } else { TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal(); }
	 * }
	 */}

    private boolean reenviarRoteiro() {
	/*
	 * Configuracao conf = Configuracao.getInstancia();
	 * 
	 * if (conf != null) { if (conf.getNomeArquivoRetorno() != null &&
	 * !conf.getNomeArquivoRetorno().equals("")) { if
	 * (!FileManager.verificarExistenciaArquivo
	 * (conf.getNomeArquivoRetorno())) { Repositorio.deleteRecordStore();
	 * System.exit(0); } else {
	 * Util.mostrarErro("Rota pendente, tentando enviar...");
	 * BackGroundTaskEnviarArquivoRetornoOnline bk = new
	 * BackGroundTaskEnviarArquivoRetornoOnline
	 * (BackGroundTaskEnviarArquivoRetornoOnline.LOGIN, true, null );
	 * TelaProgresso
	 * .getInstancia().criarTelaProgresso("Enviando Rota Pendente...", bk);
	 * } } else { return true; } } else { return true; }
	 * 
	 * return false;
	 */


	return 
		Configuracao.getInstancia() != null && 
		Configuracao.getInstancia().getNomeArquivoRetorno() != null && 
		!Configuracao.getInstancia().getNomeArquivoRetorno().equals("") && 
		FileManager.verificarExistenciaArquivo( Configuracao.getInstancia().getNomeArquivoRetorno() );
    }

    public ControladorImoveis getControladorImoveis() {
	return this.controladorImoveis;
    }

    public void setControladorImoveis(ControladorImoveis controladorImoveis) {
	this.controladorImoveis = controladorImoveis;
    }

    public static BusinessLogin getInstancia() {
	if (BusinessLogin.instancia == null) {
	    BusinessLogin.instancia = new BusinessLogin();
	}
	return BusinessLogin.instancia;
    }

    public void mostrarDiretorio() {

	Vector arquivos = null;

	// se não houver roteiro em aberto...
	if (!repositorio.existeRoteiroEmAndamento()) {
	    arquivos = FileManager.getInstancia().abrirDiretorio();
	    TelaArquivos.getInstancia().criarTelaArquivos(arquivos);

	} else {
	    TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
	}
    }

}
