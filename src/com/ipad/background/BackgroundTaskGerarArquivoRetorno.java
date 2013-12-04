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

import java.util.Vector;

import com.ipad.facade.Fachada;
import com.ipad.io.ArquivoRetorno;
import com.ipad.util.Util;
import com.ipad.view.TelaMenuPrincipal;
import com.ipad.view.TelaProgresso;

public class BackgroundTaskGerarArquivoRetorno extends BackgroundTask {

    private boolean erroGeracaoArquivo = false;
    private short tipoFinalizacao;
    private Vector idsImoveisGerados = null;
    private boolean reenvioRotaPendente = false;


    public void taskFinished() {
		if (!erroGeracaoArquivo ) {
		    
			if ( tipoFinalizacao == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS){
	//			Nao deve tentar transmitir.
				Util.MensagemConfirmacao("Arquivo de retorno COMPLETO gerado com sucesso. Enviar o arquivo ao supervisor para carregar via cabo USB.");
				TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
//				Fachada.getInstancia().exit();
	
			}else{
			    BackGroundTaskEnviarArquivoRetornoOnline bk = null;
			    if ( reenvioRotaPendente ){
			    	bk = new BackGroundTaskEnviarArquivoRetornoOnline(
	// Daniel - Evitando Loop infinito - Rota pendente e tentando enviar
						BackGroundTaskEnviarArquivoRetornoOnline.SAIR_APLICACAO,
						BackGroundTaskEnviarArquivoRetornoOnline.MENU_PRINCIPAL,
					tipoFinalizacao, idsImoveisGerados);
			    } else {
				
					if ( tipoFinalizacao == ArquivoRetorno.ARQUIVO_CONCLUIDOS_ATE_AGORA ){		
					    // Sempre que gerarmos, enviamos, ou ,pelo menos, tentamos
					    bk = new BackGroundTaskEnviarArquivoRetornoOnline(
						    BackGroundTaskEnviarArquivoRetornoOnline.MENU_PRINCIPAL,
						    BackGroundTaskEnviarArquivoRetornoOnline.MENU_PRINCIPAL,
						    tipoFinalizacao, idsImoveisGerados);
					} else {
					    // Sempre que gerarmos, enviamos, ou ,pelo menos, tentamos
					    bk = new BackGroundTaskEnviarArquivoRetornoOnline(
						    BackGroundTaskEnviarArquivoRetornoOnline.SAIR_APLICACAO,
						    BackGroundTaskEnviarArquivoRetornoOnline.MENU_PRINCIPAL,
						    tipoFinalizacao, idsImoveisGerados);
					}
			    }
			    
			    TelaProgresso.getInstancia().criarTelaProgresso("Enviando arquivo de retorno...", bk);	    
			}
		}
    }

    public void performTask() {
	
	Object[] retorno = null;
	retorno = ArquivoRetorno.getInstancia().gerarArquivoRetorno( p, tipoFinalizacao );

	erroGeracaoArquivo = ((Boolean) retorno[0]).booleanValue();
	idsImoveisGerados = ((Vector) retorno[1]);
	
    }

    public BackgroundTaskGerarArquivoRetorno( short tipoFinalizacao, boolean reenvioRotaPendente ) {
	super();
	this.tipoFinalizacao = tipoFinalizacao;
	this.reenvioRotaPendente = reenvioRotaPendente;

    }

    public void taskStarted() {
	this.setPriority(Thread.NORM_PRIORITY);
    }
}