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

import java.util.Vector;

import com.ipad.background.BackgroundTaskProcessarArquivoIda;
import com.ipad.facade.Fachada;
import com.ipad.io.FileManager;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.List;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;

public class TelaArquivos implements ActionListener {

    private Form fmArquivos;
    private List opcoesArquivos;

    private Command selecionar;
    private Command voltar;
    private FileManager fileManager = FileManager.getInstancia();

    private static TelaArquivos instancia = null;

    public void run() {

    }

    public TelaArquivos() {

    }

    public static TelaArquivos getInstancia() {
	if (instancia == null) {
	    instancia = new TelaArquivos();
	}
	return instancia;
    }

    public void criarTelaArquivos(Vector nomeArquivos) {
	fmArquivos = new Form("Arquivos disponíveis");

	voltar = new Command("Sair");
	selecionar = new Command("Selecionar");

	opcoesArquivos = new List();
	opcoesArquivos.setIsScrollVisible(false);

	if (nomeArquivos == null || nomeArquivos.size() == 0) {
	    fmArquivos.setTitle("Nenhum arquivo encontrado");
	}

	for (int i = 0; i < nomeArquivos.size(); i++) {
	    opcoesArquivos.addItem(nomeArquivos.elementAt(i));
	}

	fmArquivos.setTransitionOutAnimator(CommonTransitions.createSlide(
		CommonTransitions.SLIDE_HORIZONTAL, false, 1500));
	fmArquivos.addCommand(voltar);
	fmArquivos.addCommand(selecionar);
	fmArquivos.addComponent(opcoesArquivos);

	fmArquivos.setCommandListener(this);

	fmArquivos.show();
    }

    public void actionPerformed(ActionEvent evt) {

// Daniel - somente executar a selecao de rota se existir rota na lista.    	
	if ((evt.getCommand() == selecionar)  && (this.opcoesArquivos.size() > 0) ) {
	    fileManager.setNomeArquivo(this.opcoesArquivos.getSelectedItem()
		    .toString());

	    fmArquivos.setTransitionOutAnimator(CommonTransitions.createSlide(
		    CommonTransitions.SLIDE_HORIZONTAL, false, 1500));
	    BackgroundTaskProcessarArquivoIda BTArquivoIda = new BackgroundTaskProcessarArquivoIda();
	    TelaProgresso.getInstancia().criarTelaProgresso(
		    "Carregando Arquivo...", BTArquivoIda);
	} else if (evt.getCommand() == voltar) {
	    fmArquivos.setTransitionOutAnimator(CommonTransitions.createSlide(
		    CommonTransitions.SLIDE_HORIZONTAL, true, 1500));
	    Fachada.getInstancia().exit();
	}
    }

    public Form getFmArquivos() {
	return fmArquivos;
    }

    public void setFmArquivos(Form fmArquivos) {
	this.fmArquivos = fmArquivos;
    }
}