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

import com.ipad.io.Repositorio;
import com.ipad.util.Util;
import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

public class ApagarTudoBusiness implements ActionListener {

	private static Command sim;
	private static Command nao;
	private static ApagarTudoBusiness instancia;
	Dialog dialogPergunta;
	
	public void validarAcaoApagar(){
		
		Label mensagemPergunta = null;
    	Label mensagem =  null;
		sim =  new Command("Sim");
		nao =  new Command("Nao");
        mensagemPergunta = new Label( "Tem certeza que deseja" ); 
        mensagemPergunta.getStyle().setPadding(0, 0, 0, 0);
        mensagemPergunta.getStyle().setMargin(0, 0, 0, 0);
    	mensagem =  new Label("apagar os dados?");
    	mensagem.getStyle().setPadding(0, 0, 0, 0);
        mensagem.getStyle().setMargin(0, 0, 0, 0);
        
        dialogPergunta = new Dialog("Validar Acao!");
        dialogPergunta.getTitleStyle().setBgTransparency(200);
        dialogPergunta.getDialogStyle().setBgTransparency(200);
        dialogPergunta.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        dialogPergunta.addCommand(nao);
        dialogPergunta.addCommand(sim);
        dialogPergunta.setCommandListener(this);
        dialogPergunta.addComponent(mensagemPergunta);
        dialogPergunta.addComponent(mensagem);
        dialogPergunta.show();    	
	}
	
	public static ApagarTudoBusiness getInstancia(){
		if(ApagarTudoBusiness.instancia ==  null){
			ApagarTudoBusiness.instancia = new ApagarTudoBusiness();
		}
		
		return ApagarTudoBusiness.instancia;
	}
	
	public void actionPerformed(ActionEvent evt) {
		if(evt.getCommand() == sim){
			Repositorio.deleteRecordStore();
			
			Util.mensagemAviso("Apagado!", "Dados apagados com   sucesso!");

		}
		else if(evt.getCommand() ==  nao){
			dialogPergunta.setTimeout(0);
		}
		
	}

}
