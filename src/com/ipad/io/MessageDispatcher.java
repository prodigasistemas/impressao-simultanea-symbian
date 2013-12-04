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

package com.ipad.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import com.ipad.basic.Configuracao;
import com.ipad.util.Util;

/**
 * Classe reponsável por enviar as mensagens de requisição de serviço ao
 * servidor.
 * 
 * @author Rafael Palermo de Araújo
 */
public class MessageDispatcher {

    private static MessageDispatcher instancia;
    private InputStream respostaOnline;
    private int fileLength;
    private char tipoArquivo;
    private static String mensagemError = null;

    public static final String RESPOSTA_OK = "*";
    public static final String RESPOSTA_ERRO = "#";
    
    // Tipos de parametros que podem ser retornados
    public static final String PARAMETRO_IMOVEIS_PARA_REVISITAR = "imoveis=";
    public static final String PARAMETRO_MENSAGEM = "mensagem=";
    public static final String PARAMETRO_JAR = "jar=";
    public static final String PARAMETRO_JAD = "jad=";
    public static final String PARAMETRO_TIPO_ARQUIVO = "tipoArquivo=";
    public static final String PARAMETRO_ARQUIVO_ROTEIRO = "arquivoRoteiro=";
    
    public static final char CARACTER_FIM_PARAMETRO = '&';

        
    /**
     * URL do servidor.
     */
    private String URL_SERVIDOR;

    /**
     * Conexão.
     */
    private HttpConnection conexao;

    /**
     * Mensagem de requisição a ser enviada ao servidor.
     */
    private byte[] mensagem;

    private static String respostaServidor = RESPOSTA_OK;

    public static String getRespostaServidor() {
	return respostaServidor;
    }

    /**
     * Define a mensagem de requisição a ser enviada ao servidor.
     * 
     * @param mensagem
     *            Mensagem empacotada.
     */
    public void setMensagem(byte[] mensagem) {
	this.mensagem = mensagem;
    }

    /*
     * Inicia a execução na nova thread criada.
     */
    public void enviarMensagem() {
	
	synchronized ( mensagem ) {
	    
	    InputStream resposta = null;
	
	    try {
		// Abrimos a conexão
		conexao = (HttpConnection) Connector.open(URL_SERVIDOR,
			Connector.READ_WRITE);

		// Passamos os parametros, para a requisição
		conexao.setRequestMethod(HttpConnection.POST);
		conexao.setRequestProperty("Content-Type",
			"application/octet-stream");
		conexao.setRequestProperty("User-Agent",
			"Profile/MIDP-2.0 Configuration/CLDC-1.1");
		conexao.setRequestProperty("Content-Length", Integer
			.toString(this.mensagem.length));

		// envia os dados, passados na mensagem informada
		OutputStream os = conexao.openOutputStream();
		os.write(this.mensagem);
		os.close();

		// lê a resposta do servidor
		int rc = conexao.getResponseCode();

		// Verificamos se a resposta da conexão foi ok
		if (rc == HttpConnection.HTTP_OK) {

		    // Agora, verificamos se o servidor nos enviou
		    // mensagem de processamento ok
		    resposta = conexao.openInputStream();

		    InputStreamReader isr = new InputStreamReader(resposta);
		    char c = (char) isr.read();
		    String valor = String.valueOf(c);
		    
		    StringBuffer buffer = new StringBuffer();
		    StringBuffer bufferValorParametro = new StringBuffer();		    
		    
		    // Só foi ok, caso o servidor tenha enviado
		    // o caracter esperado
		    if (valor.equals(RESPOSTA_OK)) {
			
			for (int i = 1; i <= conexao.getLength(); i++) {
			    
			    char caracter = (char) isr.read();
			    buffer.append( caracter );
			    
			    boolean ultimoCaracter = i == conexao.getLength();
			    
			    if ( controlarParametros(
				    buffer, 
				    caracter, 
				    bufferValorParametro, 
				    ultimoCaracter ) ){
				
				buffer = new StringBuffer();
				bufferValorParametro = new StringBuffer();
				
				continue;
			    }
			    
			    // Caso ache arquivos de retorno,
		            // não haverão parametros subsequentes				
			    if ( buffer.toString().indexOf( PARAMETRO_ARQUIVO_ROTEIRO ) > -1 ||
				 buffer.toString().indexOf( PARAMETRO_JAD ) > -1 ||
                                 buffer.toString().indexOf( PARAMETRO_JAR ) > -1 ){				
				fileLength = (int)conexao.getLength() - (i+1);
				break;
				
			    }
			}
			
			buffer = null;

			respostaServidor = RESPOSTA_OK;
			
			// Setamos a resposta encontrada
			this.setRespostaOnline((InputStream) resposta);
			// Em qualquer outra situação
		    } else {
			// Guardamos a resposta
			respostaServidor = RESPOSTA_ERRO;

			for (int i = 1; i <= conexao.getLength(); i++) {
			    
			    char caracter = (char) isr.read();
			    buffer.append( caracter );
			    
			    boolean ultimoCaracter = i == conexao.getLength();
			    
			    if ( controlarParametros(
				    buffer, 
				    caracter, 
				    bufferValorParametro, 
				    ultimoCaracter ) ){
				buffer = new StringBuffer();
				bufferValorParametro = new StringBuffer();
				continue;
			    }
			}
		    }
		} else {
		    // Caso não consiga a conexão, informa o erro
		    respostaServidor = RESPOSTA_ERRO;
		}
	    } catch (IOException e) {
			// Informamos que um erro ocorreu
			respostaServidor = RESPOSTA_ERRO;
	    } catch (SecurityException se) {
			// Informamos que um erro ocorreu
			respostaServidor = RESPOSTA_ERRO;
			Util.mostrarErro("Acesso a internet nao  permitido!");		    	
		}
	    
	    finally {
		try {
//Daniel - se nao houver permissao, a conexao = null 
			if (conexao != null){
				conexao.close();
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		    Util.mostrarErro("Erro ao fechar a conexão com o servidor.", e);
		}
	    }
	}
    }

    /**
     * Set URL de conexão com o sevidor
     **/
    public void setURL_SERVIDOR(String URL_SERVIDOR) {
	this.URL_SERVIDOR = URL_SERVIDOR;
    }

    public InputStream getRespostaOnline() {
	return this.respostaOnline;
    }

    public void setRespostaOnline(InputStream respostaOnline) {
	this.respostaOnline = respostaOnline;
    }

    public static MessageDispatcher getInstancia() {
	if (instancia == null) {
	    instancia = new MessageDispatcher();
	}

	return instancia;
    }

    protected MessageDispatcher() {
	super();
    }

    public void setFileLength(int fileLength) {
	this.fileLength = fileLength;
    }

    public int getFileLength() {
	return fileLength;
    }

    public void setTipoArquivo(char tipoArquivo) {
	this.tipoArquivo = tipoArquivo;
    }

    public char getTipoArquivo() {
	return tipoArquivo;
    }

    // Só podemos pegar a mensagem de error uma vez
    public static String getMensagemError() {
	String temp = mensagemError;
	mensagemError = null;
	return temp;
    }

    public boolean controlarParametros( 
	    StringBuffer buffer, 
	    char caracter, 
	    StringBuffer bufferValorParametro,
	    boolean ultimoCaracter ){
	
	/* Caso se ja um dos parametros abaixo
	   só podemos comecar a receber o valor
	   quando estivermos lendo o primeiro caracter
	   pos identificador de parametros. Por isso
	   a comparacao com o tamanho do buffer */
	
	// Mensagem	
	if ( ( buffer.toString().indexOf( PARAMETRO_MENSAGEM ) > -1 &&
	       buffer.toString().length() > PARAMETRO_MENSAGEM.length() ) ||
	     // Imoveis a revisitar
	     ( buffer.toString().indexOf( PARAMETRO_IMOVEIS_PARA_REVISITAR ) > -1 &&
	       buffer.toString().length() > PARAMETRO_IMOVEIS_PARA_REVISITAR.length() ) ||
             // Tipo de arquivo carregado   
	     ( buffer.toString().indexOf( PARAMETRO_TIPO_ARQUIVO ) > -1 &&
               buffer.toString().length() > PARAMETRO_TIPO_ARQUIVO.length() ) ){
				
	    // Caso encontremos o caracter de fim de parametro
	    if ( caracter == CARACTER_FIM_PARAMETRO || ultimoCaracter ){	
			    
		// Mensagem
		if ( buffer.toString().indexOf( PARAMETRO_MENSAGEM ) > -1 ){				    
		    mensagemError =  bufferValorParametro.toString();		
		// Imoveis a revisitar					
		} else if ( buffer.toString().indexOf( PARAMETRO_IMOVEIS_PARA_REVISITAR ) > -1 ){
		    //this.setMatriculasImoveisRevisitar( bufferValorParametro.toString().getBytes() );
//		    Configuracao.getInstancia().setMatriculasRevisitar( bufferValorParametro.toString() );
//		    Repositorio.salvarObjeto( Configuracao.getInstancia() );
		// Tipo de arquivo carregado					
		} else if ( buffer.toString().indexOf( PARAMETRO_TIPO_ARQUIVO ) > -1 ){
		    this.setTipoArquivo( bufferValorParametro.toString().charAt( 0 ) );
		}
		
		return true;
	    } else {
		bufferValorParametro.append( caracter );
	    }
	}
	
	return false;
    }
}
