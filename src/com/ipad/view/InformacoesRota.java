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

import com.ipad.basic.ImovelConta;
import com.ipad.io.Repositorio;
import com.sun.lwuit.Command;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

public class InformacoesRota implements ActionListener{
	
	private Form fmSobre;
    private Font font;
	private Label grupoLabel;
	private Label localidadeLabel;
	private Label setorLabel;
	private Label rotaLabel;
	private Command voltar;
	
	private static InformacoesRota instancia;
	
	
	public InformacoesRota(){
		
		fmSobre =  new Form("Informações sobre a rota");
		font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
		
		ImovelConta imovel = (ImovelConta) Repositorio.pesquisarPrimeiroObjeto(ImovelConta.class);

		grupoLabel = new Label("Grupo: " + imovel.getGrupoFaturamento());
		localidadeLabel = new Label("Localidade: " + imovel.getLocalidade());
		setorLabel = new Label("Setor Comercial: " + imovel.getSetorComercial());
		rotaLabel = new Label("Código da Rota: " + imovel.getCodigoRota());
		
		fmSobre.setSmoothScrolling(true);
		fmSobre.getStyle().setFont(font);
		fmSobre.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
	    
		voltar =  new Command("Voltar");
		
		fmSobre.addComponent(grupoLabel);
		fmSobre.addComponent(localidadeLabel);
		fmSobre.addComponent(setorLabel);
		fmSobre.addComponent(rotaLabel);
		fmSobre.addCommand(voltar);
		fmSobre.setCommandListener(this);
		fmSobre.show();
		
	
	}

	public void criarTelaInformaçaoRota(){
		this.fmSobre.show();
	}

	public void actionPerformed(ActionEvent evt) {
		if(evt.getCommand() ==  voltar){
			TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
		}
		
	}
	
	public static InformacoesRota getInstancia(){
		if(instancia == null){
			instancia = new InformacoesRota();
	    }
	        return instancia;
	}	

}
