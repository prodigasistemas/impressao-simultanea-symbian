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
import com.ipad.basic.ImovelConta;
import com.ipad.business.ControladorImoveis;
import com.ipad.component.ComboBox;
import com.ipad.io.FileManager;
import com.ipad.io.Repositorio;
import com.ipad.util.Constantes;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.FlowLayout;
import com.sun.lwuit.layouts.GridLayout;
import com.sun.lwuit.layouts.Layout;

public class AbaAnormalidade extends Container {

//    private static ControladorImoveis controladorImoveis;

    private static Label lbNomeEndereco;
    private static Label lbEndereco;

    private static Label lbNomeAnormalidade;
    private static ComboBox cbAnormalidade;
    private static AbaAnormalidade instanciaAbaAnormalidade;
    private static ImovelConta imovelSelecionado;
    private static Font font;

    private static Label lbMensagem;

    /**
     * Altermos o construtor para privado fazemos isso para utilizarmos
     * singleton
     * 
     * @author Bruno Barros
     * @return Instância da aba de poco
     * 
     */
    private AbaAnormalidade() {
	super();
    }

    private AbaAnormalidade(Layout layout) {
	super(layout);
    }

    public static AbaAnormalidade getInstancia() {
	if (instanciaAbaAnormalidade == null) {
	    instanciaAbaAnormalidade = new AbaAnormalidade(new GridLayout(7, 1));

//	    controladorImoveis = ControladorImoveis.getInstancia();

	    font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD,
		    Font.SIZE_SMALL);

	    lbNomeEndereco = new Label("Endereco:");
	    lbNomeEndereco.getStyle().setFont(font);

	    lbEndereco = new Label();
	    lbEndereco.setFocusable(true);

	    lbNomeAnormalidade = new Label("Anormalidade:");
	    lbNomeAnormalidade.getStyle().setFont(font);

	    cbAnormalidade = new ComboBox(FileManager.getAnormalidades(true));

	    Container containerNomeEndereco = new Container(
		    new GridLayout(1, 2));
	    containerNomeEndereco.addComponent(lbNomeEndereco);

	    lbMensagem = new Label("Rota já concluida");
	    lbMensagem.getStyle().setFgColor(0xff0000);
	    lbMensagem.setVisible(false);

	    containerNomeEndereco.addComponent(lbMensagem);
	    instanciaAbaAnormalidade.addComponent(containerNomeEndereco);

	    instanciaAbaAnormalidade.addComponent(lbEndereco);

	    Container containerAnormalidade = new Container(new FlowLayout());
	    containerAnormalidade.addComponent(lbNomeAnormalidade);
	    Container containerAnormalidadeValor = new Container(
		    new FlowLayout());
	    containerAnormalidadeValor.addComponent(cbAnormalidade);

	    instanciaAbaAnormalidade.addComponent(containerAnormalidade);
	    instanciaAbaAnormalidade.addComponent(containerAnormalidadeValor);
	}

	return instanciaAbaAnormalidade;
    }

    public ComboBox getAnormalidade() {
	return cbAnormalidade;
    }

    public Container criarAbaAnormalidade() {

	lbMensagem.setVisible(false);

	lbEndereco.setText(imovelSelecionado.getEndereco());

	if (imovelSelecionado.getAnormalidadeSemHidrometro() == Constantes.NULO_INT) {
	    cbAnormalidade.setSelectedIndex(0);
	} else {
	    Anormalidade objAnormalidade = new Anormalidade();
	    objAnormalidade.setCodigo(imovelSelecionado
		    .getAnormalidadeSemHidrometro());

	    cbAnormalidade.setSelectedItem(objAnormalidade);
	    objAnormalidade = null;
	}

	if (imovelSelecionado.getIndcImovelImpresso() == Constantes.NAO
		&& imovelSelecionado.getIndcImovelCalculado() == Constantes.SIM) {

	    lbMensagem.setText("Imovel ja Calculado");
	    lbMensagem.setVisible(true);

	}

	if (imovelSelecionado.getIndcImovelImpresso() == Constantes.SIM) {
	    lbMensagem.setText("Conta já impressa");
	}

	// Verificamos se o imovel é do tipo condominio
	if (imovelSelecionado.isImovelCondominio()) {
	    ImovelConta hidrometroMacro = new ImovelConta();

	    if ( imovelSelecionado.getIndcCondominio() == Constantes.SIM ){
	    	Repositorio.carregarObjeto( hidrometroMacro , imovelSelecionado.getId() );
	    } else {
	    	Repositorio.carregarObjeto( hidrometroMacro , imovelSelecionado.getIdImovelCondominio() );
	    }

	    lbMensagem.setText("I.C. "
		    + ControladorImoveis.getInstancia()
			    .getIndiceAtualImovelCondominio() + " de "
		    + hidrometroMacro.getQuantidadeImoveisCondominio());

	    lbMensagem.setVisible(true);
	}

	imovelSelecionado = null;

	return instanciaAbaAnormalidade;
    }

    public void setAnormalidade(ComboBox anor) {
	cbAnormalidade = anor;
    }

    public int getAnormalidadeIndex() {
	return cbAnormalidade.getSelectedIndex();
    }
}
