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

import com.ipad.basic.Configuracao;
import com.ipad.basic.ImovelConta;
import com.ipad.business.ControladorImoveis;
import com.ipad.component.ComboBox;
import com.ipad.component.TextField;
import com.ipad.filters.FiltroImovel;
import com.ipad.io.Repositorio;
import com.ipad.util.Util;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.DataChangedListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.GridLayout;

public class Consultas implements ActionListener, DataChangedListener {

    public static final char QUADRA = 0;
    public static final char NUMERO_HIDROMETRO = 1;
    public static final char MATRICULA = 2;
    public static final char SEQUENCIAL_ROTA = 3;
    public static final char SEQUENCIAL = 4;
    public static final char IMOVEIS_REVISITADOS = 5;

    public static final int OPCAO_NUMERO_HIDROMETRO = 1;

    private Container ctFiltro;
    private Label lbFiltro;
    private ComboBox cbConsultas;
    private Label lbChave;
    private Component tfChave;
    private FmResultadoConsulta fmResultado;

    private Form fmConsultas;

    private Command consultar;
    private Command voltar;
    // private boolean opcao = false;

    private static Consultas instancia;

    public void criarConsulta() {

	if (Repositorio.indicesCarregados) {
	    fmConsultas = new Form("Consultas");

	    // Cria e adiciona as opções do Combo Box de consulta na tela
	    cbConsultas = new ComboBox();
	    cbConsultas.addItem("Quadra");
	    cbConsultas.addItem("Número do Hidrômetro");
	    cbConsultas.addItem("Número de Matrícula");
	    cbConsultas.addItem("Sequêncial Rota");
	    cbConsultas.addItem("Sequêncial");
	    cbConsultas.addItem("Imóveis para Revisitar");

	    cbConsultas.setSelectedIndex(2);

	    cbConsultas.addActionListener(this);

	    // adicona o campo chave alfa numerico caso seja numero do
	    // hidrometro
	    tfChave = new TextField();
	    tfChave.setLabelForComponent(new Label("Chave: "));

	    voltar = new Command("Voltar");
	    fmConsultas.addCommand(voltar);

	    consultar = new Command("Consultar");
	    fmConsultas.addCommand(consultar);

	    fmConsultas.setCommandListener(this);

	    // Adiciona o ComboBox e o TextField na tela
	    ctFiltro = new Container(new GridLayout(2, 2));
	    lbFiltro = new Label("Filtro:");
	    lbFiltro.setFocusable(false);
	    lbChave = new Label("Chave:");
	    lbChave.setFocusable(false);

	    ctFiltro.addComponent(lbFiltro);
	    ctFiltro.addComponent(lbChave);
	    ctFiltro.addComponent(cbConsultas);

	    ctFiltro.addComponent(tfChave);

	    fmConsultas.setLayout(new BorderLayout());
	    fmConsultas.addComponent(BorderLayout.NORTH, ctFiltro);
	    tfChave.requestFocus();
	    fmConsultas.setIsScrollVisible(true);
	    fmConsultas.show();
	} else {
	    Util.mostrarErro("Carregando índices. Aguarde um momento antes de tentar novamente...");
	}
    }

    public static Consultas getInstancia() {

	if (Consultas.instancia == null) {
	    Consultas.instancia = new Consultas();
	}

	return Consultas.instancia;
    }

    public void actionPerformed(ActionEvent evt) {

	if (evt.getSource() == cbConsultas) {
	    // adicona o campo chave alfa numerico caso seja numero do
	    // hidrometro
	    if (ctFiltro.contains(tfChave)) {
		ctFiltro.removeComponent(tfChave);
	    }

	    if (cbConsultas.getSelectedIndex() == OPCAO_NUMERO_HIDROMETRO) {
		tfChave = new com.sun.lwuit.TextField();
		tfChave.setLabelForComponent(new Label("Chave: "));
		((com.sun.lwuit.TextField) tfChave).setInputMode("ABC");
	    } else {
		tfChave = new TextField();
		tfChave.setLabelForComponent(new Label("Chave: "));
	    }

	    ctFiltro.addComponent(tfChave);
	}

	if (evt.getCommand() == consultar) {

	    int selected = cbConsultas.getSelectedIndex();

	    String chave = "";

	    if (tfChave instanceof com.ipad.component.TextField) {
		chave = ((com.ipad.component.TextField) tfChave).getText();
	    } else {
		chave = ((com.sun.lwuit.TextField) tfChave).getText().toUpperCase();
	    }

	    Vector resultados = null;

	    if ( ( chave != null && !chave.equals("") ) || cbConsultas.getSelectedIndex() == IMOVEIS_REVISITADOS ) {

		switch (selected) {
		case QUADRA:

		    resultados = Repositorio.consultarImoveis(
			    FiltroImovel.PESQUISAR_POR_QUADRA, chave);
		    break;
		case NUMERO_HIDROMETRO:

		    resultados = Repositorio
			    .consultarImoveis(
				    FiltroImovel.PESQUISAR_POR_NUMERO_HIDROMETRO,
				    chave);
		    break;
		case MATRICULA:
		    resultados = Repositorio.consultarImoveis(
			    FiltroImovel.PESQUISAR_POR_MATRICULA, chave);
		    break;
		case SEQUENCIAL_ROTA:
		    resultados = Repositorio.consultarImoveis(
			    FiltroImovel.PESQUISAR_POR_SEQUENCIAL_ROTA, chave);
		    break;
		    
		case SEQUENCIAL:
		    
		    resultados = new Vector();
		    
		    if ( Integer.parseInt( chave ) >= 1 &&  Integer.parseInt( chave ) <= Configuracao.getInstancia().getQtdImoveis() ){
			ImovelConta imovelConta = new ImovelConta();			
			Repositorio.carregarObjeto( imovelConta, Integer.parseInt( chave ) );
			resultados.addElement( imovelConta );
			break;
		    }
		    
		case IMOVEIS_REVISITADOS:
		    
		    Vector matriculas = Configuracao.getInstancia().getMatriculasRevisitar();
		    resultados = new Vector( matriculas.size() );
		    
		    for ( int i = 0; i < matriculas.size(); i++ ){
			Vector resultadosTemp = Repositorio.consultarImoveis(
				FiltroImovel.PESQUISAR_POR_MATRICULA, (String) matriculas.elementAt( i ) );
			
			resultados.addElement( (ImovelConta) resultadosTemp.elementAt(0) );
		    }
		    
		    break;
		}

		if (resultados.size() == 0) {
		    Util.mostrarErro("Nenhum imóvel encontrado");
		    tfChave.requestFocus();
		} else if (resultados.size() == 1) {
		    ControladorImoveis.getInstancia().setImovelSelecionado(
			    (ImovelConta) resultados.elementAt(0));		    
		    Abas.getInstancia().criarAbas();
		} else {
		    fmResultado = FmResultadoConsulta.getInstancia(resultados);
		    fmResultado.criarResultado();
		}

	    } else {
		Util.mostrarErro("Informe a chave para continuar...");
		tfChave.requestFocus();
	    }
	}

	if (evt.getCommand() == voltar) {

	    fmConsultas.setTransitionOutAnimator(CommonTransitions.createSlide(
		    CommonTransitions.SLIDE_HORIZONTAL, false, 2000));

	    TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
	}

    }

    public Form getFmConsultas() {
	return fmConsultas;
    }

    public void setFmConsultas(Form fmConsultas) {
	this.fmConsultas = fmConsultas;
    }

    public ComboBox getCbConsultas() {
	return cbConsultas;
    }

    public void setCbConsultas(ComboBox cbConsultas) {
	this.cbConsultas = cbConsultas;
    }

    public void dataChanged(int arg0, int arg1) {
	
	this.tfChave.setEnabled( arg1 != IMOVEIS_REVISITADOS );
	
    }
}
