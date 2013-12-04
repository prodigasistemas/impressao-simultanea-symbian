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

package com.ipad.util;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

/**
 * Classe que mostra a confirmação das perguntas realizadas pelo sistema
 * 
 * @author Bruno Barros
 * @date 25/11/2009
 */
public class DialogAcaoPerguntar implements ActionListener {

    protected boolean resultadoAcao = false;

    private Command cmdNao = new Command("Nao");
    private Command cmdSim = new Command("Sim");
    private TextField txSenha;
    private boolean valorSenha = false;
    private Label nomeTxSenha;
    private boolean verificarSenhaAdmistrador = false;
    private static String senhaAdministrador = Constantes.SENHA_ADMINISTRADOR;
    private static String senhaNormal = Constantes.SENHA_LEITURISTA;

    public void actionPerformed(ActionEvent arg0) {

	if (arg0.getCommand() == cmdSim) {
	    if (valorSenha == true) {

		String senha = "";

		if (verificarSenhaAdmistrador) {
		    senha = senhaAdministrador;
		} else {
		    senha = senhaNormal;
		}

		if (txSenha.getText().equals(senha)) {
		    resultadoAcao = true;
		} else {
		    Util.mostrarErro("Senha incorreta!");
		    resultadoAcao = false;
		}
	    } else {
		resultadoAcao = true;
	    }
	}

	if (arg0.getCommand() == cmdNao) {
	    resultadoAcao = false;
	}
    }

    public DialogAcaoPerguntar(String pergunta, boolean senha, boolean verificarSenhaAdmistrador) {
	
	super();
	
	try{

    	this.verificarSenhaAdmistrador = verificarSenhaAdmistrador;
    
    	valorSenha = senha;
    	// Tamanho maximo para uma linha da mensagem do Dialog
    	int tamanhoDialog = 23;
    
    	// Tamanho do texto recebido
    	int tamanhoMensagem = pergunta.length();
    
    	int indice = 0;
    
    	Dialog dialogMensagem = new Dialog("Validar Acao!");
    	dialogMensagem.setCommandListener(this);
    
    	if (valorSenha == false) {
    
    	    if (tamanhoMensagem < tamanhoDialog) {
    		Label lbPartePergunta = new Label(pergunta);
    
    		dialogMensagem.getTitleStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
    		dialogMensagem.getDialogStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
    		dialogMensagem.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    		dialogMensagem.addComponent(lbPartePergunta);
    		dialogMensagem.addCommand(cmdNao);
    		dialogMensagem.addCommand(cmdSim);
    
    		dialogMensagem.show();
    	    } else {
    
    		int falta = tamanhoMensagem;
    
    		while (falta % tamanhoDialog > 0) {
    
    		    Label lbPartePergunta = null;
    
    		    if (falta < tamanhoDialog) {
    			lbPartePergunta = new Label(pergunta.substring(tamanhoDialog * indice, tamanhoMensagem));
    		    } else {
    			lbPartePergunta = new Label(pergunta.substring(tamanhoDialog * indice, tamanhoDialog * (indice + 1)));
    		    }
    
    		    lbPartePergunta.getStyle().setMargin(0, 0, 0, 0);
    		    lbPartePergunta.getStyle().setPadding(0, 0, 0, 0);
    
    		    dialogMensagem.addComponent(lbPartePergunta);
    
    		    falta -= tamanhoDialog;
    		    ++indice;
    		}
    
    		dialogMensagem.getTitleStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
    		dialogMensagem.getDialogStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
    		dialogMensagem.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    		dialogMensagem.addCommand(cmdNao);
    		dialogMensagem.addCommand(cmdSim);
    
    		dialogMensagem.show();
    	    }
    
    	} else {
    	    if (tamanhoMensagem < tamanhoDialog) {
    		Label lbPartePergunta = new Label(pergunta);
    		Label linha = new Label();
    		Label linha1 = new Label();
    		Label linha2 = new Label();
    		nomeTxSenha = new Label("Senha");
    		nomeTxSenha.getStyle().setPadding(0, 1, 0, 0);
    		nomeTxSenha.getStyle().setMargin(0, 1, 0, 0);
    		txSenha = new TextField();
    		txSenha.setConstraint(TextArea.PASSWORD);
    		txSenha.setInputMode("abc");
    
    		dialogMensagem.getTitleStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
    		dialogMensagem.getDialogStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
    		dialogMensagem.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    		dialogMensagem.addComponent(lbPartePergunta);
    		dialogMensagem.addComponent(linha);
    		dialogMensagem.addComponent(linha1);
    		dialogMensagem.addComponent(linha2);
    		dialogMensagem.addComponent(nomeTxSenha);
    		dialogMensagem.addComponent(txSenha);
    		dialogMensagem.addCommand(cmdNao);
    		dialogMensagem.addCommand(cmdSim);
    
    		dialogMensagem.show();
    	    } else {
    
    		int falta = tamanhoMensagem;
    		Label linha = new Label();
    		Label linha1 = new Label();
    		Label linha2 = new Label();
    		nomeTxSenha = new Label("Senha");
    		nomeTxSenha.getStyle().setPadding(0, 1, 0, 0);
    		nomeTxSenha.getStyle().setMargin(0, 1, 0, 0);
    		txSenha = new TextField();
    		txSenha.setConstraint(TextArea.PASSWORD);
    		txSenha.setInputMode("abc");
    
    		while (falta % tamanhoDialog > 0) {
    
    		    Label lbPartePergunta = null;
    
    		    if (falta < tamanhoDialog) {
    			lbPartePergunta = new Label(pergunta.substring(tamanhoDialog * indice, tamanhoMensagem));
    		    } else {
    			lbPartePergunta = new Label(pergunta.substring(tamanhoDialog * indice, tamanhoDialog * (indice + 1)));
    		    }
    
    		    lbPartePergunta.getStyle().setMargin(0, 0, 0, 0);
    		    lbPartePergunta.getStyle().setPadding(0, 0, 0, 0);
    
    		    dialogMensagem.addComponent(lbPartePergunta);
    
    		    falta -= tamanhoDialog;
    		    ++indice;
    		}
    
    		dialogMensagem.getTitleStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
    		dialogMensagem.getDialogStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
    		dialogMensagem.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    		dialogMensagem.addComponent(linha);
    		dialogMensagem.addComponent(linha1);
    		dialogMensagem.addComponent(linha2);
    		dialogMensagem.addComponent(nomeTxSenha);
    		dialogMensagem.addComponent(txSenha);
    		dialogMensagem.addCommand(cmdNao);
    		dialogMensagem.addCommand(cmdSim);
    
    		dialogMensagem.show();
    	    }
    	}
	} catch ( Exception e ){
	    new DialogAcaoPerguntar( pergunta, senha, verificarSenhaAdmistrador );
	}
    }
}
