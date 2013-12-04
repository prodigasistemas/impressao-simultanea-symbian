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
import com.ipad.basic.Configuracao;
import com.ipad.basic.ImovelConta;
import com.ipad.business.ControladorImoveis;
import com.ipad.facade.Fachada;
import com.ipad.io.FileManager;
import com.ipad.io.Repositorio;
import com.ipad.util.Util;
import com.ipad.view.TelaLogin;

public class BackgroundTaskProcessarArquivoIda extends BackgroundTask {
    
    private boolean error = false;
    
    public void performTask() {
	
	/*Configuracao config = (Configuracao) Repositorio.pesquisarPrimeiroObjeto(Configuracao.class);

	if (config != null && Configuracao.getInstancia().getSucessoCarregamento() != null) {
	    if (Configuracao.getInstancia().getSucessoCarregamento().equals(
		    Boolean.FALSE)){
		Repositorio.deleteRecordStore();
	    }
	}*/
	
	try {
	    Configuracao.getInstancia().setSucessoCarregamento(Boolean.FALSE);
	    Repositorio.salvarObjeto(Configuracao.getInstancia());
	    
            // separa o array de bytes em vetor de linhas
            Vector linhas = null;
        
            ImovelConta ultimoImovelSelecionado = null;	
        do {
		linhas = FileManager.getInstancia().lerArquivoImoveis();
		ultimoImovelSelecionado = ControladorImoveis.getInstancia().carregarDadosParaRecordStore(linhas, p, ultimoImovelSelecionado);
	    } while (linhas.size() == 65);

        	if (ultimoImovelSelecionado != null){
	            Repositorio.salvarObjeto(Repositorio.indiceMatriculaImovel);
	            Repositorio.salvarObjeto(Repositorio.indiceNumeroHidrometroImovel);
	            Repositorio.salvarObjeto(Repositorio.indiceQuadraImovel);
	            Repositorio.salvarObjeto(Repositorio.indiceSequencialRotaImovel);
	        
	            Configuracao.getInstancia().setQtdImoveis(ControladorImoveis.getInstancia().getQuantidadeImoveis());
	            Configuracao.getInstancia().setIdImovelSelecionado(1);
	            Repositorio.salvarObjeto(Configuracao.getInstancia());
	            Repositorio.indicesCarregados = true;
	            
	            Configuracao.getInstancia().setSucessoCarregamento(Boolean.TRUE);
	            Repositorio.salvarObjeto(Configuracao.getInstancia());
	    		          
        	}
	} catch (Exception e) {
	    e.printStackTrace();
	    Util.mostrarErro("Problema ao carregar o arquivo. Saindo do sistema...");
	    error = true;
	}	
    }

    public void taskFinished() {
	if ( !error ){
	    TelaLogin.getInstancia().criarTelaLogin();
	} else {
	    // Caso qualquer erro aconteça no carregamento do arquivo
	    // limpamos o record store e saimos da aplicação
	    Repositorio.deleteRecordStore();
	    Fachada.getInstancia().exit();	    
	}
    }

    public void taskStarted() {
	this.setPriority(Thread.NORM_PRIORITY);
    }

}
