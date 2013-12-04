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

import com.ipad.basic.Anormalidade;
import com.ipad.basic.Configuracao;
import com.ipad.basic.ImovelConta;
import com.ipad.business.ControladorConta;
import com.ipad.business.ControladorImoveis;
import com.ipad.component.ComboBox;
import com.ipad.component.TextField;
import com.ipad.io.FileManager;
import com.ipad.io.Repositorio;
import com.ipad.util.Constantes;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.DataChangedListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.layouts.FlowLayout;
import com.sun.lwuit.layouts.GridLayout;
import com.sun.lwuit.layouts.Layout;

public class AbaHidrometroPoco extends Container implements FocusListener,
	DataChangedListener {

    private static Label lbNomeEndereco;
    private static Label lbEndereco;
    private static Label lbNomeHidrometro;
    private static Label lbNumeroHidrometro;
    private static Label lbNomeLeitura;
    private static Label lbNomeLocalInstalacao;
    private static Label lbLocalInstalacao;
    private static Label lbNomeSeqRota;
    private static Label lbSeqRota;

    private static TextField txtLeitura;
    private static TextField codAnormalidade;
    private static Label lbNomeAnormalidade;
    private static ComboBox cbAnormalidade;
    private static AbaHidrometroPoco instanciaAbaHidrometroPoco;
    private static Font font;

    private static Label lbMensagem;

    private int valorAnormalidade = 0;

    /**
     * Altermos o construtor para privado fazemos isso para utilizarmos
     * singleton
     * 
     * @author Bruno Barros
     * @return Instância da aba de poco
     */
    private AbaHidrometroPoco() {
	super();
    }

    private AbaHidrometroPoco(Layout layout) {
	super(layout);
    }

    /**
     * Cria a instancia da aba de poco, jutamente com todos os componentes
     * necessários para seu funcionamento
     * 
     * @author Bruno Barros
     * @return Instância da aba de poco
     */
    public static AbaHidrometroPoco getInstancia() {
	if (instanciaAbaHidrometroPoco == null) {
	    instanciaAbaHidrometroPoco = new AbaHidrometroPoco(new GridLayout(
		    7, 1));

	    font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD,
		    Font.SIZE_SMALL);

	    lbNomeEndereco = new Label("Endereço:");
	    lbNomeEndereco.getStyle().setFont(font);
	    lbNomeEndereco.getStyle().setPadding(0, 0, 3, 0);
	    lbNomeEndereco.getStyle().setMargin(0, 0, 3, 0);

	    lbEndereco = new Label();
	    lbEndereco.setFocusable(false);
	    lbEndereco.getStyle().setPadding(0, 0, 3, 0);
	    lbEndereco.getStyle().setMargin(0, 0, 3, 0);
	    
	    lbNomeSeqRota = new Label("Seq. Rota: ");
	    lbNomeSeqRota.getStyle().setFont(font);
	    lbNomeSeqRota.getStyle().setPadding(0, 0, 6, 0);
	    lbNomeSeqRota.getStyle().setMargin(0, 0, 6, 0);
	    
	    lbSeqRota =  new Label();
	    lbSeqRota.setFocusable(true);
	    lbSeqRota.getStyle().setPadding(0, 0, 3, 0);
	    lbSeqRota.getStyle().setMargin(0, 0, 3, 0);

	    lbNomeHidrometro = new Label("Hidrômetro:     ");
	    lbNomeHidrometro.getStyle().setFont(font);
	    lbNomeHidrometro.getStyle().setPadding(2, 2, 3, 0);
	    lbNomeHidrometro.getStyle().setMargin(2, 2, 3, 0);

	    lbNumeroHidrometro = new Label();
	    lbNumeroHidrometro.setFocusable(false);
	    lbNumeroHidrometro.getStyle().setPadding(2, 0, 0, 0);
	    lbNumeroHidrometro.getStyle().setMargin(2, 0, 0, 0);

	    lbNomeLeitura = new Label("Leitura:");
	    lbNomeLeitura.getStyle().setFont(font);

	    txtLeitura = new TextField();
	    txtLeitura.setConstraint(TextField.NUMERIC);
	    txtLeitura.setFocusable(true);

	    lbNomeAnormalidade = new Label("Anormalidade:");
	    lbNomeAnormalidade.getStyle().setFont(font);

	    lbNomeLocalInstalacao = new Label("Loc. Instalação:     ");
	    lbNomeLocalInstalacao.getStyle().setFont(font);
	    lbNomeLocalInstalacao.getStyle().setPadding(2, 2, 3, 0);
	    lbNomeLocalInstalacao.getStyle().setMargin(2, 2, 3, 0);

	    codAnormalidade = new TextField();
	    codAnormalidade.setConstraint(TextField.NUMERIC);
	    codAnormalidade.setFocusable(true);

	    codAnormalidade.addFocusListener(instanciaAbaHidrometroPoco);
	    codAnormalidade.addDataChangeListener(instanciaAbaHidrometroPoco);

	    lbLocalInstalacao = new Label();
	    lbLocalInstalacao.setFocusable(false);
	    lbLocalInstalacao.getStyle().setPadding(2, 0, 0, 0);
	    lbLocalInstalacao.getStyle().setMargin(2, 0, 0, 0);

	    cbAnormalidade = new ComboBox(FileManager.getAnormalidades(true));

	    cbAnormalidade.addFocusListener(instanciaAbaHidrometroPoco);
	    cbAnormalidade.setFocusable(true);

	    lbMensagem = new Label();
	    lbMensagem.getStyle().setFgColor(0xff0000);
	    lbMensagem.setVisible(false);

	    Container containerNomeEndereco = new Container(
		    new GridLayout(1, 2));
	    containerNomeEndereco.addComponent(lbNomeEndereco);

	    /*
	     * lbEndereco.setNextFocusDown(lbNumeroHidrometro);
	     * lbEndereco.setNextFocusUp(cbAnormalidade);
	     */
	    containerNomeEndereco.addComponent(lbMensagem);
	    containerNomeEndereco.getStyle().setPadding(0, 0, 0, 0);
	    containerNomeEndereco.getStyle().setMargin(0, 0, 0, 0);

	    instanciaAbaHidrometroPoco.addComponent(containerNomeEndereco);

	    instanciaAbaHidrometroPoco.addComponent(lbEndereco);

	    // Container containerHidrometro = new Container(new GridLayout(1,
	    // 2));
	    Container containerHidrometro = new Container(new FlowLayout());
	    containerHidrometro.addComponent(lbNomeHidrometro);
	    containerHidrometro.addComponent(lbNumeroHidrometro);

	    /*
	     * lbNumeroHidrometro.setNextFocusDown(lbLocalInstalacao);
	     * lbNumeroHidrometro.setNextFocusUp(lbEndereco);
	     */
	    // Container containerHidrometroInstalacao = new Container(new
	    // GridLayout(1, 2));
	    Container containerHidrometroInstalacao = new Container(
		    new FlowLayout());
	    containerHidrometroInstalacao.addComponent(lbNomeLocalInstalacao);
	    containerHidrometroInstalacao.addComponent(lbLocalInstalacao);
	    // containerHidrometroInstalacao.getStyle().setPadding(0, 0, 0, 0);
	    // containerHidrometroInstalacao.getStyle().setMargin(0, 0, 0, 0);

	    /*
	     * lbLocalInstalacao.setNextFocusDown(txtLeitura);
	     * lbLocalInstalacao.setNextFocusUp(lbNumeroHidrometro);
	     */
	    
	    if(ControladorImoveis.getInstancia().getImovelSelecionado().getSequencialRota() != Constantes.NULO_INT &&
	    		ControladorImoveis.getInstancia().getImovelSelecionado().getSequencialRota() != 0){
		    containerHidrometroInstalacao.addComponent(lbNomeSeqRota);
		    containerHidrometroInstalacao.addComponent(lbSeqRota);
	    }
	    
	    instanciaAbaHidrometroPoco.addComponent(containerHidrometro);
	    instanciaAbaHidrometroPoco
		    .addComponent(containerHidrometroInstalacao);

	    // Container containerLocalInstalacao = new Container( new
	    // FlowLayout() );
	    // containerHidrometro.addComponent(lbNomeLocalInstalacao);
	    // containerHidrometro.addComponent(lbLocalInstalacao);

	    // cntHidrometroAgua.addComponent( containerLocalInstalacao );
	    // cntHidrometroAgua.addComponent(lbNomeLocalInstalacao);
	    // cntHidrometroAgua.addComponent(lbLocalInstalacao);

	    Container containerLeitura = new Container(new FlowLayout());
	    containerLeitura.addComponent(lbNomeLeitura);
	    containerLeitura.addComponent(txtLeitura);
	    /*
	     * Container containerLeituraValor = new Container(new
	     * FlowLayout()); containerLeituraValor.addComponent(txtLeitura);
	     */

	    /*
	     * txtLeitura.setNextFocusDown( codAnormalidade );
	     * txtLeitura.setNextFocusUp(lbLocalInstalacao);
	     */
	    instanciaAbaHidrometroPoco.addComponent(containerLeitura);
	    // instanciaAbaHidrometroPoco.addComponent(containerLeituraValor);

	    Container containerAnormalidade = new Container(new FlowLayout());
	    containerAnormalidade.addComponent(lbNomeAnormalidade);
	    Container containerAnormalidadeValor = new Container(
		    new FlowLayout());
	    containerAnormalidadeValor.addComponent(codAnormalidade);

	    /*
	     * codAnormalidade.setNextFocusDown(cbAnormalidade);
	     * codAnormalidade.setNextFocusUp(txtLeitura);
	     */
	    containerAnormalidadeValor.addComponent(cbAnormalidade);

	    /*
	     * cbAnormalidade.setNextFocusUp(codAnormalidade);
	     * cbAnormalidade.setNextFocusDown(lbEndereco);
	     */

	    instanciaAbaHidrometroPoco.addComponent(containerAnormalidade);
	    instanciaAbaHidrometroPoco.addComponent(containerAnormalidadeValor);
	}

	instanciaAbaHidrometroPoco.setScrollableY(false);

	return instanciaAbaHidrometroPoco;
    }

    public String getLeitura() {
	return txtLeitura.getText();
    }

    public TextField getLeituraCampo() {
	return txtLeitura;
    }

    public ComboBox getAnormalidadeCampo() {
	return cbAnormalidade;
    }

    /**
     * Altera as informações da aba para o imovel corrente
     * 
     * @author Bruno Barros
     * @return Instância da aba de poco
     */
    public Container criarAbaHidrometro() {

	lbMensagem.setVisible(false);
//    ImovelConta imovelSelecionado;

	// Selecionamos as informações do imovel selecionado carregado
//	imovelSelecionado = controladorImoveis.getImovelSelecionado();

	// Setamos o endereço
	lbEndereco.setText(getImovelSelecionado().getEndereco());

	// Hidrometro
	lbNumeroHidrometro.setText(getImovelSelecionado()
		.getNumeroHidrometro(Constantes.LIGACAO_POCO));

	lbLocalInstalacao.setText(getImovelSelecionado().getRegistro8(
			Constantes.LIGACAO_POCO).getLocalInstalacao());
	
	//Informacoes do Sequencial de Rota
	if(getImovelSelecionado().getSequencialRota() != Constantes.NULO_INT &&
			getImovelSelecionado().getSequencialRota() != 0){
	    lbSeqRota.setText(String.valueOf(getImovelSelecionado().getSequencialRota()));
    }

	// Numero de colunas do COD. Anormalidade
	codAnormalidade.setColumns(3);

	// Numero de colunas do hidrometro
	txtLeitura
		.setColumns(getImovelSelecionado().getRegistro8(
			ControladorConta.LIGACAO_POCO)
			.getNumDigitosLeituraHidrometro() + 3);

	// Leitura
	if (getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_POCO)
		.getLeitura() == Constantes.NULO_INT) {
	    txtLeitura.setText("");
	} else {
	    txtLeitura.setText(getImovelSelecionado().getRegistro8(
		    ControladorConta.LIGACAO_POCO).getLeitura()
		    + "");
	}

	// Anormalidade
	if (getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_POCO)
		.getAnormalidade() == Constantes.NULO_INT) {
	    cbAnormalidade.setSelectedIndex(0);
	} else {
	    Anormalidade objAnormalidade = new Anormalidade();
	    objAnormalidade.setCodigo(getImovelSelecionado().getRegistro8(
		    ControladorConta.LIGACAO_POCO).getAnormalidade());

	    cbAnormalidade.setSelectedItem(objAnormalidade);

	    objAnormalidade = null;
	}

	// Codigo da Anormalidade
	if (getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_POCO)
		.getAnormalidade() == Constantes.NULO_INT
		|| getImovelSelecionado()
			.getRegistro8(ControladorConta.LIGACAO_POCO)
			.getAnormalidade() == 0) {
	    codAnormalidade.setText("");
	} else {
	    codAnormalidade.setText(String.valueOf(getImovelSelecionado()
		    .getRegistro8(ControladorConta.LIGACAO_POCO)
		    .getAnormalidade()));
	}

	if (getImovelSelecionado().getIndcImovelImpresso() == Constantes.NAO
		&& getImovelSelecionado().getIndcImovelCalculado() == Constantes.SIM) {
	    // lbMensagem.setText( "Imovel calculado"+
	    // imovelSelecionado.getRegistro8(
	    // ControladorConta.LIGACAO_AGUA ).getLeitura());
	    lbMensagem.setText("Imovel ja Calculado");
	    lbMensagem.setVisible(true);
	}

	if (getImovelSelecionado().getIndcImovelImpresso() == Constantes.SIM) {
	    lbMensagem.setText("Conta já impressa");
	    lbMensagem.setVisible(true);
	}
	if (Configuracao.getInstancia().getIdsImoveisPendentes() != null && 
		Configuracao.getInstancia().getIdsImoveisPendentes().size() == 0 ){
	    
		lbMensagem.setText("Rota já concluida");
	    lbMensagem.setVisible(true);
	}
	
	if ( Configuracao.getInstancia().getMatriculasRevisitar() != null &&
	     !Configuracao.getInstancia().getMatriculasRevisitar().isEmpty()  ){
	    lbMensagem.setText("REVISITAR IMÓVEIS");
	    lbMensagem.setVisible(true);	    
	}	

	// Verificamos se o imovel é do tipo condominio
	if (getImovelSelecionado().isImovelCondominio()) {
	    ImovelConta hidrometroMacro = new ImovelConta();

	    if (getImovelSelecionado().getIndcCondominio() == Constantes.SIM) {
		Repositorio.carregarObjeto(hidrometroMacro, getImovelSelecionado()
			.getId());
	    } else {
		Repositorio.carregarObjeto(hidrometroMacro, getImovelSelecionado()
			.getIdImovelCondominio());
	    }

	    lbMensagem.setText("I.C. "
		    + ControladorImoveis.getInstancia()
			    .getIndiceAtualImovelCondominio() + " de "
		    + hidrometroMacro.getQuantidadeImoveisCondominio());

	    lbMensagem.setVisible(true);
	}

	// System.out.println( imovelSelecionado.getIndcCondominio() );

	// Verificamos se devemos permitir que uma conta seja alterada
	// após a sua impressão, respeitando o indicador
	boolean habilitar = getImovelSelecionado().podeAlterarLeituraAnormalidade();

	codAnormalidade.setEnabled(habilitar);
	txtLeitura.setEnabled(habilitar);
	cbAnormalidade.setEnabled(habilitar);
	// -----------------------------------------------------------
// Daniel - setting to null
//	imovelSelecionado = null;
	return instanciaAbaHidrometroPoco;
    }

    public void setLeituraCampo(String leitura) {
	getLeituraCampo().setText(leitura);
    }

    public int getAnormalidadeIndex() {
	return cbAnormalidade.getSelectedIndex();
    }

    public void focusGained(Component evt) {

	if (evt == cbAnormalidade) {
	    cbAnormalidade.getStyle().setBgColor(0x99ccff);
	}

	if (evt == codAnormalidade) {
	   /* if (imovelSelecionado.getRegistro8(ControladorConta.LIGACAO_POCO)
		    .getAnormalidade() != 0
		    && imovelSelecionado.getRegistro8(
			    ControladorConta.LIGACAO_POCO).getAnormalidade() != Constantes.NULO_INT) {
		codAnormalidade.setCursorPosition(String.valueOf(
			imovelSelecionado.getRegistro8(
				ControladorConta.LIGACAO_POCO)
				.getAnormalidade()).length());

	    } else {*/
		codAnormalidade.setCursorPosition(codAnormalidade.getText()
			.length());
	    //}
	}

    }

    public void focusLost(Component evt) {
	if (evt == cbAnormalidade) {

	}

    }

    public void dataChanged(int arg0, int arg1) {

	int tamanhoAnormalidade = codAnormalidade.getText().length();

	if (tamanhoAnormalidade < 4) {

	    if (codAnormalidade.getText() != null
		    && !codAnormalidade.getText().equals("")) {

		valorAnormalidade = Integer.parseInt(codAnormalidade.getText());

		Anormalidade objAnormalidade = new Anormalidade();
		objAnormalidade.setCodigo(valorAnormalidade);
		boolean contem = FileManager.getAnormalidades(true).contains(
			objAnormalidade);

		if (contem) {
		    cbAnormalidade.setSelectedItem(objAnormalidade);
		} else {
		    cbAnormalidade.setSelectedIndex(0);
		}

		objAnormalidade = null;

	    } else {
		cbAnormalidade.setSelectedIndex(0);
	    }

	} else {
	    codAnormalidade.deleteChar();
	}
    }

    public ImovelConta getImovelSelecionado(){
    	return ControladorImoveis.getInstancia().getImovelSelecionado();
    }

    public void cleanAbaHidrometroPoco(){
    	instanciaAbaHidrometroPoco = null;
    }

}
