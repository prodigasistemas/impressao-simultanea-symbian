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

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.file.FileConnection;

import com.ipad.business.BusinessLogin;
import com.ipad.facade.Fachada;
import com.ipad.facade.FachadaRede;
import com.ipad.io.FileManager;
import com.ipad.io.MessageDispatcher;
import com.ipad.util.Util;
import com.ipad.view.TelaProgresso;

public class BackgroundTaskReceberNovaVersao extends BackgroundTask {

    public static final short ARQUIVO_JAD = 1;
    public static final short ARQUIVO_JAR = 2;
    
    public static final String NOME_ARQUIVO_JAD = "ImpressaoSimultanea.jad";
    public static final String NOME_ARQUIVO_JAR = "ImpressaoSimultanea.jar";
    public static final String NOME_ARQUIVO_JAD_PARA_DOWNLOAD = "ImpressaoSimultaneaJAD";
    public static final String NOME_ARQUIVO_JAR_PARA_DOWNLOAD = "ImpressaoSimultaneaJAR";

    private short tipoArquivo;

    private boolean error = false;
    private boolean temNovaVersao = false;
    private boolean versaoAntigaBaixada = false;

    public void performTask() {

	try {
	    // Setamos os caminhos
	    String caminhoVersao = FileManager.INPUT_FILE_PATH + "Versao/";
	    String caminhoJadParaDownload = caminhoVersao + NOME_ARQUIVO_JAD_PARA_DOWNLOAD;
	    String caminhoJarParaDownload = caminhoVersao + NOME_ARQUIVO_JAR_PARA_DOWNLOAD;
	    //String caminhoJad = caminhoVersao + NOME_ARQUIVO_JAD;

	    DataOutputStream dos = null;
	    FileConnection fcArquivo = null;
	    FileConnection fcDiretorio = null;
	    InputStream is = null;
	    byte[] byteFile = null;

	    // Selecionamos o tipo de arquivo a ser baixado
	    if (tipoArquivo == ARQUIVO_JAD) {
		FachadaRede.getInstancia().baixarNovaVersaoJad();
	    } else if (tipoArquivo == ARQUIVO_JAR) {
		FachadaRede.getInstancia().baixarNovaVersaoJar();
	    }

	    if (FachadaRede.getInstancia().isRequestOK()) {

		try {
		    // Pegamos o arquivo do servidor
		    is = MessageDispatcher.getInstancia().getRespostaOnline();
		    byteFile = FileManager.getInstancia()
			    .carregaByteArrayAtualizandoProgressBar(is, p,
			    		MessageDispatcher.getInstancia().getFileLength());
		    
		    // Verificamos se o diretorio existe
		    fcDiretorio = FileManager.abrir( caminhoVersao , true, FileManager.CRIAR_SE_NAO_EXISTIR );
		    
		    // Verificamos o tipo do arquivo
		    if (tipoArquivo == ARQUIVO_JAD) {
			
			// Verificamos se devemos baixar a versão
			String arquivo = new String(byteFile);
			String versaoServidor = arquivo.substring(13, 20).trim();
			versaoServidor = Util.replaceAll(versaoServidor, ".", "");

			// Achamos a nossa versão
			String versaoLocal = Fachada.versao;
			versaoLocal = Util.replaceAll(versaoLocal, ".", "");

			// Comparamos
			temNovaVersao = Integer.parseInt(versaoLocal) < Integer
				.parseInt(versaoServidor);

			if (temNovaVersao) {

//			    FileConnection fcArquivoAntigo = null;

/*			    // Caso tenha nova versão, verificamos
			    // se já existe alguma outra ja baixada
			    // em /Versao
			    try {
				fcArquivoAntigo = (FileConnection) Connector.open(caminhoVersao, Connector.READ_WRITE);

				// Caso exista algum arquivo antigo,
				// não deixamos prosseguir
				if (fcArquivoAntigo.list().hasMoreElements()) {
				    versaoAntigaBaixada = true;
				    Util.mostrarErro("Existe versão baixada. Favor instalar e apagar...");
				    Fachada.getInstancia().exit();
				}
			    } catch (SecurityException e) {
				versaoAntigaBaixada = true;
				Util.mostrarErro("Existe versão baixada. Favor instalar e apagar...");
				Fachada.getInstancia().exit();
			    } finally {
				if (fcArquivoAntigo != null && fcArquivoAntigo.isOpen()) {
				    fcArquivoAntigo.close();
				    fcArquivoAntigo = null;
				}
			    }
*/
			    //if (!versaoAntigaBaixada) {
				try{
				   fcArquivo = FileManager.abrir(
					   caminhoJadParaDownload, false,
					   FileManager.CRIAR_SE_NAO_EXISTIR);
					
					// Salvamos os dados
				   dos = fcArquivo.openDataOutputStream();
				   dos.write(byteFile);
				   dos.close();
				   
				   fcArquivo.rename(NOME_ARQUIVO_JAD);
			    	} catch ( IOException e ){
			    	    versaoAntigaBaixada = true;
			    	    Util.mostrarErro("Existe versão baixada. Favor instalar e apagar...");
			    	    fcArquivo.delete();
			    	    Fachada.getInstancia().exit();			    	    
			    	}
			    //}
			}
		    } else {
			fcArquivo = FileManager.abrir(
				caminhoJarParaDownload, false,
				FileManager.CRIAR_SE_NAO_EXISTIR);
			    
			// Salvamos os dados
			dos = fcArquivo.openDataOutputStream();
			dos.write(byteFile);
			dos.close();

			fcArquivo.rename(NOME_ARQUIVO_JAR);			
		    }
		} catch (IOException io) {
		    // Qualquer erro de io deve ser logado
		    Util.mostrarErro("Erro de IO ao carregar nova versão", io);
		    error = true;
		} finally {
		    // Fechamos tudo o que usamod
		    try {

			if (fcDiretorio != null && fcDiretorio.isOpen()) {
			    fcDiretorio.close();
			}

			if (dos != null) {
			    dos.close();
			}

			if (fcArquivo != null && fcArquivo.isOpen()) {
			    fcArquivo.close();
			}

			if (is != null) {
			    is.close();
			}

			fcDiretorio = null;
			dos = null;
			fcArquivo = null;
			is = null;

			byteFile = null;
		    } catch (IOException io) {
			Util.mostrarErro("Erro de IO ao carregar nova versão",
				io);
			error = true;
		    }
		}
	    } else {
		error = true;
	    }

	} catch (Exception e) {
	    error = true;
	    Util.mostrarErro(e.toString(), e);
	}
    }

    public void taskStarted() {
	if (tipoArquivo == 0) {
	    tipoArquivo = ARQUIVO_JAD;
	} else {
	    tipoArquivo = ARQUIVO_JAR;
	}
    }

    public void taskFinished() {

	if ( !versaoAntigaBaixada ){
	    if (!error) {
		if (tipoArquivo == ARQUIVO_JAD) {
		    if (!temNovaVersao) {
			FachadaRede.getInstancia().baixarRoteiro();

			if (!FachadaRede.getInstancia().isRequestOK()) {
				Util.mostrarErro("Servidor fora do ar...");
			    BusinessLogin.getInstancia().mostrarDiretorio();
			} else {
			    BackgroundTaskReceberArquivoIdaOnline bk = new BackgroundTaskReceberArquivoIdaOnline();
			    TelaProgresso.getInstancia().criarTelaProgresso(
				    "Carregando arquivo on-line...", bk);
			}
		    } else {
			BackgroundTaskReceberNovaVersao bgt = new BackgroundTaskReceberNovaVersao(
				ARQUIVO_JAR);
			TelaProgresso.getInstancia().criarTelaProgresso(
				"Recebendo nova versão...", bgt);
		    }
		} else if (tipoArquivo == ARQUIVO_JAR) {
			Util.mostrarErro("Nova versão disponivel, favor instalar");
		    Fachada.getInstancia().exit();
		}
	    } else {
	    	Util.mostrarErro("Não foi possivel verificar se há nova versão...");
		
		FachadaRede.getInstancia().baixarRoteiro();

		if (!FachadaRede.getInstancia().isRequestOK()) {
			Util.mostrarErro("Servidor fora do ar...");
		    BusinessLogin.getInstancia().mostrarDiretorio();
		} else {
		    BackgroundTaskReceberArquivoIdaOnline bk = new BackgroundTaskReceberArquivoIdaOnline();
		    TelaProgresso.getInstancia().criarTelaProgresso(
			    "Carregando arquivo on-line...", bk);
		}

	    }
	}
	
    }

    private BackgroundTaskReceberNovaVersao(short tipoArquivo) {
	super();
	this.tipoArquivo = tipoArquivo;
    }

    public BackgroundTaskReceberNovaVersao() {
	super();
	FileManager.getInstancia().descobrirRootsDispositivo();
    }
}
