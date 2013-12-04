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
import com.ipad.basic.ImovelReg2;
import com.ipad.basic.ImovelReg8;
import com.ipad.business.ControladorImoveis;
import com.ipad.util.Constantes;
import com.ipad.util.Util;
import com.ipad.component.ComboBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

public class TelaImovel implements ActionListener {

	
//	private ControladorImoveis controladorImoveis;

	private int tipoMedicao = 1;
	
	//Campos necessarios para tela de listagem de imoveis
	private Label nomeMatricula;
	private Label matricula;
	private Label nomeEndereco;
	private Label endereco;
	private Label nomeCliente;
	private Label cliente;
	private Label nomeInscricao;
	private Label inscricao;
	private Label nomeCatgoria;
	private Label categoria;
	private Label nomeLigAgua;
	private Label ligAgua;
	private Label nomeLigEsgoto;
	private Label ligEsgoto;
	private Label nomeEconomias;
	private Label economias;
	private Label nomeHidrometro;
	private Label hidrometro;
	private Label nomeTipoMedicao;
	private Label tipoMedicaoImovel;
	private Label nomeLocal;
	private Label local;
	private Label nomeRota;
	private Label rota;
	private Label nomeCampoLeitura;
	private TextArea campoLeitura;
	private ComboBox grupoAnormalidade;

	//Criacao dos campos para o form e respectivos comandos
	private Form fmImovel;
	private Command conta;
	private Command poco;
	private Command anterior;
	private Command proximoNaoLido;
	private Command consultas;
	private Command inverterRoteiro;
	private Command sair;
	private Command proximo;
	private static TelaImovel instancia;
	
	private TelaImovel() {

	}
	
	public static TelaImovel getInstancia(){
		if ( instancia == null ){
			instancia = new TelaImovel();
		}
		
		return instancia;
	}
	
	public void criarTelaImovel(){
		
//		this.controladorImoveis = ControladorImoveis.getInstancia();
		
		fmImovel = new Form("Imovel");
		fmImovel.setScrollableY(true);
		fmImovel.setIsScrollVisible(true);
		
		//espaco1 =  new Label(
		nomeMatricula =  new Label("Matricula:");
		//matricula = new Label(ControladorImoveis.getInstancia().getImovel().getMatricula());
		matricula = new Label(ControladorImoveis.getInstancia().getImovelSelecionado().getMatricula()+"");
		nomeEndereco = new Label("Endereco: ");
		endereco = new Label(ControladorImoveis.getInstancia().getImovelSelecionado().getEndereco());
		nomeCliente = new Label("Nome Usuario: ");
		cliente = new Label(ControladorImoveis.getInstancia().getImovelSelecionado().getNomeUsuario());
		nomeInscricao =  new Label("Inscricao: ");
		inscricao = new Label(ControladorImoveis.getInstancia().getImovelSelecionado().getInscricao());
		nomeCatgoria = new Label("Categoria: ");
		categoria = new Label("CATEGORIA");
		nomeLigAgua =  new Label("Sit. Agua: ");
		ligAgua = new Label(ControladorImoveis.getInstancia().getImovelSelecionado().getSituacaoLigAgua());
		nomeLigEsgoto =  new Label("Sit. Esgoto: ");
		ligEsgoto = new Label(ControladorImoveis.getInstancia().getImovelSelecionado().getSituacaoLigEsgoto());
		nomeEconomias =  new Label("Economias: ");
		economias = new Label("ECONOMIAS");
		//Depois pegar o tipo de medicao real
		nomeHidrometro =  new Label("Numero Hidrometro: ");
		hidrometro = new Label(ControladorImoveis.getInstancia().getImovelSelecionado().getNumeroHidrometro(tipoMedicao));
		nomeTipoMedicao =  new Label(" Tipo Medicao: ");
		tipoMedicaoImovel = new Label("Tipo Medicao");
		nomeLocal =  new Label("Endereco de Entrega:");
		local = new Label(ControladorImoveis.getInstancia().getImovelSelecionado().getEnderecoEntrega());
		nomeRota =  new Label("Seq. Rota: ");
		rota = new Label(ControladorImoveis.getInstancia().getImovelSelecionado().getSequencialRota()+"");
		nomeCampoLeitura =  new Label("Leitura: ");
		campoLeitura = new TextArea(1,10);
		campoLeitura.setConstraint(TextArea.NUMERIC);
		//campoLeitura.setConstraint(TextField.NUMERIC);
		//campoLeitura.setCursorPosition(0);
		//campoLeitura.
		grupoAnormalidade = new ComboBox();
		
		
		Container camposImovel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		camposImovel.addComponent(nomeMatricula);
		camposImovel.addComponent(matricula);
		camposImovel.addComponent(nomeEndereco);
		camposImovel.addComponent(endereco);
		camposImovel.addComponent(nomeCliente);
		camposImovel.addComponent(cliente);
		camposImovel.addComponent(nomeInscricao);
		camposImovel.addComponent(inscricao);
		camposImovel.addComponent(nomeCatgoria);
		camposImovel.addComponent(categoria);
		camposImovel.addComponent(nomeLigAgua);
		camposImovel.addComponent(ligAgua);
		camposImovel.addComponent(nomeLigEsgoto);
		camposImovel.addComponent(ligEsgoto);
		camposImovel.addComponent(nomeEconomias);
		camposImovel.addComponent(economias);
		camposImovel.addComponent(nomeHidrometro);
		camposImovel.addComponent(hidrometro);
		camposImovel.addComponent(nomeTipoMedicao);
		camposImovel.addComponent(tipoMedicaoImovel);
		camposImovel.addComponent(nomeLocal);
		camposImovel.addComponent(local);
		camposImovel.addComponent(nomeRota);
		camposImovel.addComponent(rota);
		camposImovel.addComponent(nomeCampoLeitura);
		//camposImovel.addComponent(campoLeitura);
		//camposImovel.addComponent(grupoAnormalidade);
		
		
		

		conta = new Command("CONTA");
		fmImovel.addCommand(conta);
		

		poco = new Command("POCO");
		fmImovel.addCommand(poco);
		
		
		anterior =  new Command("ANTERIOR");
		fmImovel.addCommand(anterior);
		
		
		proximoNaoLido = new Command("PROXIMO NAO LIDO");
		fmImovel.addCommand(proximoNaoLido);
		
		consultas = new Command("CONSULTAS");
		fmImovel.addCommand(consultas);
		
		inverterRoteiro = new Command("INVERTER ROTEIRO");
		fmImovel.addCommand(inverterRoteiro);
		
		sair = new Command("SAIR");
		fmImovel.addCommand(sair);
		
		proximo = new Command("PROXIMO");
		fmImovel.addCommand(proximo);
		
		fmImovel.setCommandListener(this);
		
		
		//super.append(new Spacer(WIDTH, 0));
	
		//fmImovel.addComponent(nomeMatricula);
		//fmImovel.addComponent(espaco1);
		//fmImovel.addComponent(matricula);
		//fmImovel.addComponent(nomeInscricao);
		//fmImovel.addComponent(inscricao);
		//fmImovel.addComponent(nomeEndereco);
		//fmImovel.addComponent(endereco);
		//fmImovel.addComponent(nomeCliente);
		//fmImovel.addComponent(cliente);
		//fmImovel.addComponent(nomeCatgoria);
		//fmImovel.addComponent(categoria);
		//fmImovel.addComponent(nomeLigAgua);
		//fmImovel.addComponent(ligAgua);
		//fmImovel.addComponent(nomeLigEsgoto);
		//fmImovel.addComponent(ligEsgoto);
		//fmImovel.addComponent(nomeEconomias);
		//fmImovel.addComponent(economias);
		//fmImovel.addComponent(nomeHidrometro);
		//fmImovel.addComponent(hidrometro);
		//fmImovel.addComponent(nomeTipoMedicao);
		//fmImovel.addComponent(tipoMedicaoImovel);
		//fmImovel.addComponent(nomeLocal);
		//fmImovel.addComponent(local);
		//fmImovel.addComponent(nomeRota);
		//fmImovel.addComponent(rota);
		//fmImovel.addComponent(nomeCampoLeitura);
		//fmImovel.addComponent(campoLeitura);
		//fmImovel.addComponent(grupoAnormalidade);
		fmImovel.addComponent(camposImovel);
		fmImovel.addComponent(campoLeitura);
		fmImovel.addComponent(grupoAnormalidade);
		
		fmImovel.show();
	}

	public void update(ImovelConta imovel, int tipoMedicao) {
		this.setTipoMedicaoImovel(tipoMedicaoImovel);
		this.endereco.setText(imovel.getEndereco());
		this.cliente.setText(imovel.getNomeUsuario());
		this.inscricao.setText(imovel.getInscricao());
		this.categoria.setText(((ImovelReg2) imovel.getCategorias()
				.elementAt(0)).getDescricaoCategoria().substring(0, 3)
				.toUpperCase());
		this.ligAgua.setText(imovel.getSituacaoLigAgua());
		this.ligEsgoto.setText(imovel.getSituacaoLigEsgoto());
		int size = imovel.getCategorias().size();
		int qnt = 0;
		for (int i = 0; i < size; i++) {
			ImovelReg2 reg2 = (ImovelReg2) imovel.getCategorias().elementAt(i);
			qnt = qnt + reg2.getQtdEconomiasSubcategoria();
		}
		this.economias.setText("" + qnt);
		this.hidrometro.setText(imovel.getRegistro8(tipoMedicao)
				.getNumeroHidrometro());
		this.tipoMedicaoImovel.setText(imovel.getRegistro8(tipoMedicao)
				.getTipoMedicaoString());
		this.local.setText(Util.dateToString(imovel.getRegistro8(tipoMedicao)
				.getDataInstalacao()));
		this.rota.setText("");

		// se leitura não nula
		if (imovel.getRegistro8(tipoMedicao).getLeitura() != -1) {
			this.campoLeitura.setText(String.valueOf(imovel.getRegistro8(
					tipoMedicao).getLeitura()));
		} else {
			this.campoLeitura.setText("");
		}

		this.grupoAnormalidade.setSelectedIndex(imovel
				.getRegistro8(tipoMedicao).getAnormalidade(), true);
		fmImovel.removeCommand(conta);
		fmImovel.removeCommand(poco);
		if (tipoMedicao == Constantes.LIGACAO_AGUA) {
			ImovelReg8 reg8 = imovel.getRegistro8(Constantes.LIGACAO_POCO);
			if (reg8 != null) {
				fmImovel.addCommand(poco);
			} else {
				fmImovel.addCommand(conta);
			}
		} else {
			fmImovel.addCommand(conta);
		}
	}

	public void reset() {
		this.campoLeitura.setText("");
		this.grupoAnormalidade.setSelectedIndex(0, true);
	}

	public Label getEndereco() {
		return endereco;
	}

	public void setEndereco(Label endereco) {
		this.endereco = endereco;
	}

	public Label getCliente() {
		return cliente;
	}

	public void setCliente(Label cliente) {
		this.cliente = cliente;
	}

	public Label getInscricao() {
		return inscricao;
	}

	public void setInscricao(Label inscricao) {
		this.inscricao = inscricao;
	}

	public Label getCategoria() {
		return categoria;
	}

	public void setCategoria(Label categoria) {
		this.categoria = categoria;
	}

	public Label getLigAgua() {
		return ligAgua;
	}

	public void setLigAgua(Label ligAgua) {
		this.ligAgua = ligAgua;
	}

	public Label getLigEsgoto() {
		return ligEsgoto;
	}

	public void setLigEsgoto(Label ligEsgoto) {
		this.ligEsgoto = ligEsgoto;
	}

	public Label getEconomias() {
		return economias;
	}

	public void setEconomias(Label economias) {
		this.economias = economias;
	}

	public Label getHidrometro() {
		return hidrometro;
	}

	public void setHidrometro(Label hidrometro) {
		this.hidrometro = hidrometro;
	}

	public Label getLocal() {
		return local;
	}

	public void setLocal(Label local) {
		this.local = local;
	}

	public Label getRota() {
		return rota;
	}

	public void setRota(Label rota) {
		this.rota = rota;
	}

	public TextArea getCampoLeitura() {
		return campoLeitura;
	}

	public void setCampoLeitura(TextField campoLeitura) {
		this.campoLeitura = campoLeitura;
	}

	public ComboBox getGrupoAnormalidade() {
		return grupoAnormalidade;
	}

	public void setGrupoAnormalidade(ComboBox grupoAnormalidade) {
		this.grupoAnormalidade = grupoAnormalidade;
	}

	public Command getConta() {
		return conta;
	}

	public void setConta(Command conta) {
		this.conta = conta;
	}

	public Label getTipoMedicaoImovel() {
		return tipoMedicaoImovel;
	}

	public void setTipoMedicaoImovel(Label tipoMedicaoImovel) {
		this.tipoMedicaoImovel = tipoMedicaoImovel;
	}

	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getCommand() == conta){
			TelaConta conta =  new TelaConta();
			conta.criarTelaConta();
		}

	}

}
