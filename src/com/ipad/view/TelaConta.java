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

import com.sun.lwuit.Command;import com.sun.lwuit.Container;import com.sun.lwuit.Form;import com.sun.lwuit.Label;import com.sun.lwuit.events.ActionEvent;import com.sun.lwuit.events.ActionListener;import com.sun.lwuit.layouts.BoxLayout;

public class TelaConta implements ActionListener{
	
	private Form fmConta;
	
	private Command imprimir;
	private Command proximo;
	private Command anterior;
	private Command voltar;
	
	private Label leituraFaturadaAgua;
	private Label leituraFaturadaPoco; 
	private Label consumoAgua;
	private Label consumoEsgoto;
	private Label diaConsumo;
	private Label consumoTipoAgua;
	private Label consumoTipoEsgoto;
	private Label anormalidadeConsumoAgua;
	private Label anormalidadeConsumoEsgoto;
	private Label valorAgua;
	private Label valorEsgoto;
	private Label valorDebitos;
	private Label valorCreditos;
	private Label valorTotal;
	
	
	public void criarTelaConta(){
		
		fmConta =  new Form("Conta");
		
		imprimir =  new Command("IMPRIMIR");
		anterior =  new Command("ANTERIOR");
		proximo =  new Command("PROXIMO");
		voltar =  new Command("VOLTAR");
		
		leituraFaturadaAgua =  new Label("Leitura Faturada Água: ");
		leituraFaturadaPoco =  new Label("Leitura Faturada Poço: ");
		consumoAgua =  new Label("Consumo Água: ");
		consumoEsgoto =  new  Label("Consumo Esgoto: ");
		diaConsumo = new Label("Dias de Consumo: ");
		consumoTipoAgua = new Label("Consumo Tipo Água: ");
		consumoTipoEsgoto =  new Label("Consumo Tipo Esgoto: ");
		anormalidadeConsumoAgua =  new Label("Anormalidade Consumo Água: ");
		anormalidadeConsumoEsgoto =  new Label("Anormalidade Consumo Esgoto: ");
		valorAgua =  new Label("Valor da Água(R$): ");
		valorEsgoto =  new Label("Valor do Esgoto(R$(: ");
		valorDebitos =  new Label("Valor Débitos(R$): ");
		valorCreditos =  new Label("Valor Créditos(R$): ");
		valorTotal =  new Label("Valor Total(R$): ");
		
		Container conta = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		
		conta.addComponent(leituraFaturadaAgua);
		conta.addComponent(leituraFaturadaPoco);
		conta.addComponent(consumoAgua);
		conta.addComponent(consumoEsgoto);
		conta.addComponent(diaConsumo);
		conta.addComponent(consumoTipoAgua);
		conta.addComponent(consumoTipoEsgoto);
		conta.addComponent(anormalidadeConsumoAgua);
		conta.addComponent(anormalidadeConsumoEsgoto);
		conta.addComponent(valorAgua);
		conta.addComponent(valorEsgoto);
		conta.addComponent(valorDebitos);
		conta.addComponent(valorCreditos);
		conta.addComponent(valorTotal);
		
		fmConta.addComponent(conta);
		fmConta.addCommand(imprimir);
		fmConta.addCommand(proximo);
		fmConta.addCommand(anterior);
		fmConta.addCommand(this.voltar);
		fmConta.setCommandListener(this);
		fmConta.show();
	}


	public void actionPerformed(ActionEvent evt) {
		// System.out.println("entrou no actionevent de conta");
		if(evt.getCommand() ==  this.voltar){
			// System.out.println("entrou no voltar de conta");
			TelaImovel.getInstancia().criarTelaImovel();
		}
		
	}

}
