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
import com.ipad.basic.ImovelReg11;
import com.ipad.basic.ImovelReg2;
import com.ipad.business.ControladorImoveis;
import com.ipad.util.Constantes;
import com.ipad.util.Util;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.Layout;

public class AbaImoveis extends Container {

    // Campos necessarios para tala de listagem de imoveis
    private static Label lbNomeEndereco;
    private static Label lbEndereco;
    private static Label nomeMatricula;
    private static Label matricula;
    private static Label nomeCliente;
    private static Label cliente;
    private static Label nomeInscricao;
    private static Label inscricao;
    private static Label nomeCatgoria;
    private static Label nomeLigAgua;
    private static Label ligAgua;
    private static Label nomeLigEsgoto;
    private static Label ligEsgoto;
    private static Label nomeEconomias;
    private static Label economias;
    private static Label nomeLocal;
    private static Label local;
    private static Label nomeRota;
    private static Label rota;
    private static Font font;
//Daniel
    private static Label lbInformativo;
    private static boolean isImovelInformativo; 
    private static Container dadosCategoria;

    public static AbaImoveis instanciaAbaImoveis;

    private AbaImoveis(Layout layout) {
	super(layout);
    }

    public Container criarAbaImoveis() {

	try {

// Daniel - rota completa
    	if(getImovelSelecionado().isImovelInformativo()  &&
    			getImovelSelecionado().getIndcCondominio() != Constantes.SIM	){
    		isImovelInformativo = true;
    		lbInformativo.setText("                  IMÓVEL INFORMATIVO");
    	}else{
    		lbInformativo.setText("");
   		
    	}
	    // Setamos o endereço
	    lbEndereco.setText(getImovelSelecionado().getEndereco());

	    matricula.setText(getImovelSelecionado().getMatricula() + "");
	    inscricao.setText(getImovelSelecionado().getInscricao());

	    nomeLocal.setVisible(getImovelSelecionado().getEnderecoEntrega() != null
		    && !getImovelSelecionado().getEnderecoEntrega().equals(""));
	    local.setVisible(getImovelSelecionado().getEnderecoEntrega() != null
		    && !getImovelSelecionado().getEnderecoEntrega().equals(""));
	    local.setText(getImovelSelecionado().getEnderecoEntrega());

	    rota.setText(getImovelSelecionado().getSequencialRota() + "");

	    ligAgua.setText(getImovelSelecionado().getDescricaoSitLigacaoAgua(getImovelSelecionado()
		    .getSituacaoLigAgua()));

	    ligEsgoto.setText(getImovelSelecionado().getDescricaoSitLigacaoEsgoto(getImovelSelecionado()
		    .getSituacaoLigEsgoto()));

	    cliente.setText(getImovelSelecionado().getNomeUsuario());

	    Label categoriaLabel = null;

	    dadosCategoria.removeAll();

	    Font font = Font.createSystemFont(Font.FACE_MONOSPACE,
		    Font.STYLE_PLAIN, Font.SIZE_MEDIUM);

	    ImovelReg11 reg11 = ImovelReg11.getInstancia();

	    for (int i = 0; i < getImovelSelecionado().getRegistros2().size(); i++) {

		if (reg11.getIndcTarifaCatgoria() == ImovelReg11.CALCULO_POR_CATEGORA) {

		    categoriaLabel = new Label((Util.completaString(
			    ((ImovelReg2) ControladorImoveis.getInstancia()
				    .getImovelSelecionado().getRegistros2()
				    .elementAt(i))
				    .getQtdEconomiasSubcategoria()
				    + "", 19))
			    + (Util.completaString(String.valueOf(
				    ((ImovelReg2) ControladorImoveis
					    .getInstancia()
					    .getImovelSelecionado()
					    .getRegistros2().elementAt(i))
					    .getCodigoCategoria()).trim(), 10))
			    + (Util.completaString(
				    ((ImovelReg2) ControladorImoveis
					    .getInstancia()
					    .getImovelSelecionado()
					    .getRegistros2().elementAt(i))
					    .getDescricaoCategoria(), 22)));

		    categoriaLabel.getStyle().setFont(font);

		} else {
		    String qtdEconomias = String
			    .valueOf(((ImovelReg2) ControladorImoveis
				    .getInstancia().getImovelSelecionado()
				    .getRegistros2().elementAt(i))
				    .getQtdEconomiasSubcategoria());

		    categoriaLabel = new Label((Util.completaString(
			    qtdEconomias, 05))
			    + (Util.completaString(
				    ((ImovelReg2) ControladorImoveis
					    .getInstancia()
					    .getImovelSelecionado()
					    .getRegistros2().elementAt(i))
					    .getDescricaoSubcategoria(), 40)));

		    categoriaLabel.getStyle().setFont(font);

		}

		dadosCategoria.setFocusable(true);
		dadosCategoria.addComponent(categoriaLabel);
	    }
	    // camposImovel.addComponent(5, dadosCategoria);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return instanciaAbaImoveis;
    }

    public static AbaImoveis getInstancia() {
	if (AbaImoveis.instanciaAbaImoveis == null) {
	    instanciaAbaImoveis = new AbaImoveis(
		    new BoxLayout(BoxLayout.Y_AXIS));

	    font = Font.createSystemFont(Font.FACE_PROPORTIONAL,
		    Font.STYLE_BOLD, Font.SIZE_SMALL);

	    lbNomeEndereco = new Label("Endereço:");
	    lbNomeEndereco.getStyle().setFont(font);
	    lbNomeEndereco.getStyle().setPadding(0, 0, 3, 0);
	    lbNomeEndereco.getStyle().setMargin(0, 0, 3, 0);

	    lbEndereco = new Label();
	    lbEndereco.setFocusable(true);
	    lbEndereco.getStyle().setPadding(0, 0, 3, 0);
	    lbEndereco.getStyle().setMargin(0, 0, 3, 0);

	    nomeMatricula = new Label("Matrícula:");

	    nomeMatricula.getStyle().setPadding(1, 0, 1, 0);
	    nomeMatricula.getStyle().setMargin(1, 0, 1, 0);
	    nomeMatricula.getStyle().setFont(font);

	    matricula = new Label();
	    matricula.getStyle().setPadding(0, 3, 1, 0);
	    matricula.getStyle().setMargin(0, 3, 1, 0);
	    matricula.setFocusable(true);

	    nomeInscricao = new Label("Inscrição:");
	    nomeInscricao.getStyle().setFont(font);
	    nomeInscricao.getStyle().setPadding(1, 0, 1, 0);
	    nomeInscricao.getStyle().setMargin(1, 0, 1, 0);

	    inscricao = new Label();
	    inscricao.getStyle().setPadding(0, 3, 1, 0);
	    inscricao.getStyle().setMargin(0, 3, 1, 0);
	    inscricao.setFocusable(true);

	    nomeLocal = new Label();
	    nomeLocal.setText("Endereço de Entrega:");
	    nomeLocal.getStyle().setPadding(1, 0, 1, 0);
	    nomeLocal.getStyle().setMargin(1, 0, 1, 0);
	    nomeLocal.getStyle().setFont(font);

	    local = new Label();
	    local.getStyle().setPadding(0, 3, 1, 0);
	    local.getStyle().setMargin(0, 3, 1, 0);
	    local.setFocusable(true);

	    nomeRota = new Label("Seq. Rota:");
	    nomeRota.getStyle().setPadding(1, 0, 1, 0);
	    nomeRota.getStyle().setMargin(1, 0, 1, 0);
	    nomeRota.getStyle().setFont(font);

	    rota = new Label();
	    rota.getStyle().setPadding(0, 3, 1, 0);
	    rota.getStyle().setMargin(0, 3, 1, 0);
	    rota.setFocusable(true);

	    nomeLigAgua = new Label("Sit. Lig. Água:");
	    nomeLigAgua.getStyle().setPadding(1, 0, 1, 0);
	    nomeLigAgua.getStyle().setMargin(1, 0, 1, 0);
	    nomeLigAgua.getStyle().setFont(font);

	    ligAgua = new Label();
	    ligAgua.getStyle().setPadding(0, 3, 1, 0);
	    ligAgua.getStyle().setMargin(0, 3, 1, 0);
	    ligAgua.setFocusable(true);

// Daniel
        lbInformativo = new Label();
        lbInformativo.getStyle().setFgColor(0xff0000);
        lbInformativo.setVisible(true);
        lbInformativo.getStyle().setFont(font);

	    nomeLigEsgoto = new Label("Sit. Lig. Esgoto:");
	    nomeLigEsgoto.getStyle().setPadding(1, 0, 1, 0);
	    nomeLigEsgoto.getStyle().setMargin(1, 0, 1, 0);
	    nomeLigEsgoto.getStyle().setFont(font);

	    ligEsgoto = new Label();
	    ligEsgoto.getStyle().setPadding(0, 3, 1, 0);
	    ligEsgoto.getStyle().setMargin(0, 3, 1, 0);
	    ligEsgoto.setFocusable(true);

	    nomeCliente = new Label("Nome Usuário: ");
	    nomeCliente.getStyle().setPadding(1, 0, 1, 0);
	    nomeCliente.getStyle().setMargin(1, 0, 1, 0);
	    nomeCliente.getStyle().setFont(font);

	    cliente = new Label();
	    cliente.getStyle().setPadding(0, 3, 1, 0);
	    cliente.getStyle().setMargin(0, 3, 1, 0);
	    cliente.setFocusable(true);

	    if (ImovelReg11.getInstancia().getIndcTarifaCatgoria() == ImovelReg11.CALCULO_POR_CATEGORA) {

		nomeCatgoria = new Label(Util.completaString("Economias", 15)
			+ Util.completaString("Cod. Cat", 25)
			+ Util.completaString("Subcategoria", 20));
		nomeCatgoria.getStyle().setFont(font);

	    } else {
		nomeCatgoria = new Label((Util.completaString("Eco.", 40))
			+ (Util.completaString("Subcategoria", 20)));
		nomeCatgoria.getStyle().setFont(font);
	    }

	    nomeEconomias = new Label("Economias: ");
	    nomeEconomias.getStyle().setFont(font);
	    economias = new Label();
	    economias.setFocusable(true);

	    instanciaAbaImoveis.setScrollable(true);
	    instanciaAbaImoveis.setScrollableX(false);

// Daniel - rota completa
 		instanciaAbaImoveis.addComponent(lbInformativo);
	    instanciaAbaImoveis.addComponent(nomeLigAgua);
	    instanciaAbaImoveis.addComponent(ligAgua);
	    instanciaAbaImoveis.addComponent(nomeLigEsgoto);
	    instanciaAbaImoveis.addComponent(ligEsgoto);
	    instanciaAbaImoveis.addComponent(nomeCatgoria);

	    dadosCategoria = new Container(new BoxLayout(BoxLayout.Y_AXIS));

	    instanciaAbaImoveis.addComponent(dadosCategoria);

	    instanciaAbaImoveis.addComponent(lbNomeEndereco);
	    instanciaAbaImoveis.addComponent(lbEndereco);
	    instanciaAbaImoveis.addComponent(nomeCliente);
	    instanciaAbaImoveis.addComponent(cliente);
	    instanciaAbaImoveis.addComponent(nomeMatricula);
	    instanciaAbaImoveis.addComponent(matricula);
	    instanciaAbaImoveis.addComponent(nomeInscricao);
	    instanciaAbaImoveis.addComponent(inscricao);
	    instanciaAbaImoveis.addComponent(nomeRota);
	    instanciaAbaImoveis.addComponent(rota);
	}

	return instanciaAbaImoveis;
    }

//Daniel
    public static Label imovelInformativo(){
        lbInformativo = new Label();;
    	if(!isImovelInformativo){
    		lbInformativo.setText("             IMÓVEL INFORMATIVO");
    	}else{
            lbInformativo.setText("");	
        }
        lbInformativo.getStyle().setFgColor(0xff0000);
        lbInformativo.setVisible(true);
        lbInformativo.getStyle().setFont(font);
        return lbInformativo;
    }
    public Label getMatricula() {
	return matricula;
    }
    
    public ImovelConta getImovelSelecionado(){
    	return ControladorImoveis.getInstancia().getImovelSelecionado();
    }

}