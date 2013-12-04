/*
 * Copyright (C) 2007-2009 the GSAN - Sistema Integrado de GestÃ£o de ServiÃ§os de Saneamento
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
 * GSAN - Sistema Integrado de GestÃ£o de ServiÃ§os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de AraÃºjo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * ClÃªudio de Andrade Lira
 * Denys GuimarÃ£es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * FabÃ­ola Gomes de AraÃºjo
 * FlÃªvio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento JÃºnior
 * Homero Sampaio Cavalcanti
 * Ivan SÃ©rgio da Silva JÃºnior
 * JosÃ© Edmar de Siqueira
 * JosÃ© Thiago TenÃ³rio Lopes
 * KÃ¡ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * MÃ¡rcio Roberto Batista da Silva
 * Maria de FÃ¡tima Sampaio Leite
 * Micaela Maria Coelho de AraÃºjo
 * Nelson MendonÃ§a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael CorrÃªa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de AraÃºjo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Roberto Souza
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * SÃ¡vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa Ã© software livre; vocÃª pode redistribuÃ­-lo e/ou
 * modificÃ¡-lo sob os termos de LicenÃ§a PÃºblica Geral GNU, conforme
 * publicada pela Free Software Foundation; versÃ£o 2 da
 * LicenÃ§a.
 * Este programa Ã© distribuÃ­do na expectativa de ser Ãºtil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implÃªcita de
 * COMERCIALIZAÃ‡ÃƒO ou de ADEQUAÃ‡ÃƒO A QUALQUER PROPÃ“SITO EM
 * PARTICULAR. Consulte a LicenÃ§a PÃºblica Geral GNU para obter mais
 * detalhes.
 * VocÃª deve ter recebido uma cÃ³pia da LicenÃ§a PÃºblica Geral GNU
 * junto com este programa; se nÃ£o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package com.ipad.view;

import com.ipad.basic.DadosRelatorio;
import com.ipad.business.BusinessRelatorio;
import com.ipad.io.Repositorio;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.GridLayout;
import com.sun.lwuit.plaf.Border;

public class Relatorios implements ActionListener {

    private static Relatorios instancia;

    private Form fmRelatorios;

    private Label imoveisVisitadosAnormalidade;
    private Label imoveisVisitadosSemAnormalidade;
    private Label imoveisVisitar;

    private Command voltar;
    private Command resumoQuadra;

    String[] examples = { "piechart3D.txt", "realtime.txt" };
    int currentExample = 0;

    public Relatorios() {

	fmRelatorios = new Form("Relatório");
	fmRelatorios.setLayout(new BorderLayout());

	BorderLayout border = new BorderLayout();
	GridLayout resumoRelatorio = new GridLayout(3, 3);

	Container relatorioTotal = new Container();
	relatorioTotal.setLayout(border);
	relatorioTotal.setScrollable(false);
	// resumoTotal.setLayout(gridResumoTotal);

	Container linhasRelatorio = new Container();
	linhasRelatorio.setLayout(resumoRelatorio);
	linhasRelatorio.getStyle().setBorder(Border.createLineBorder(1));
	linhasRelatorio.setScrollable(false);

	Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);

	int visitadosAnormalidade = Integer.parseInt(DadosRelatorio.getInstancia().valoresRelatorio.substring(7, 11));
	this.imoveisVisitadosAnormalidade = new Label(visitadosAnormalidade + "");
	this.imoveisVisitadosAnormalidade.getStyle().setFont(font);
	this.imoveisVisitadosAnormalidade.setFocusable(false);
	this.imoveisVisitadosAnormalidade.getStyle().setBorder(Border.createLineBorder(1));
	this.imoveisVisitadosAnormalidade.getStyle().setFgColor(0xff0000);

	int visitadosSemAnormalidade = Integer.parseInt(DadosRelatorio.getInstancia().valoresRelatorio.substring(1, 5));
	this.imoveisVisitadosSemAnormalidade = new Label(visitadosSemAnormalidade + "");
	this.imoveisVisitadosSemAnormalidade.getStyle().setFont(font);
	this.imoveisVisitadosSemAnormalidade.setFocusable(false);
	this.imoveisVisitadosSemAnormalidade.getStyle().setBorder(Border.createLineBorder(1));
	this.imoveisVisitadosSemAnormalidade.getStyle().setFgColor(0x556b3f);

	this.imoveisVisitar = new Label(DadosRelatorio.getInstancia().idsNaoLidosRelatorio.size() + "");
	this.imoveisVisitar.getStyle().setFont(font);
	this.imoveisVisitar.setFocusable(false);
	this.imoveisVisitar.getStyle().setBorder(Border.createLineBorder(1));
	this.imoveisVisitar.getStyle().setFgColor(0x00ff);

	Label nomeImoveisVisitar = new Label("A Visitar");
	nomeImoveisVisitar.getStyle().setBorder(Border.createLineBorder(1));
	nomeImoveisVisitar.getStyle().setFgColor(0x00ff);
	Label nomeImoveisVisitadosSemAnormalidade = new Label("Vis. S/ Anor.");
	nomeImoveisVisitadosSemAnormalidade.getStyle().setBorder(Border.createLineBorder(1));
	Label nomeImoveisVisitadosAnormalidade = new Label("Vis. C/ Anor.");
	nomeImoveisVisitadosAnormalidade.getStyle().setBorder(Border.createLineBorder(1));
	nomeImoveisVisitadosAnormalidade.getStyle().setFgColor(0xff0000);

	Label porcentagemVisitar = new Label(Repositorio.getInstancia().porcentagemImoveisVisitar() + "%" + "");
	porcentagemVisitar.getStyle().setBorder(Border.createLineBorder(1));
	porcentagemVisitar.getStyle().setFgColor(0x00ff);

	double porcentagemAnormalidade = Repositorio.getInstancia().porcentagemImoveisVisitadosAnormalidade();
	Label porcentagemVsistadosAnormalidade = new Label(porcentagemAnormalidade + "%" + "");
	porcentagemVsistadosAnormalidade.getStyle().setBorder(Border.createLineBorder(1));
	porcentagemVsistadosAnormalidade.getStyle().setFgColor(0xff0000);

	Label porcentagemVisitadosSemAnormalidade = new Label(Repositorio.getInstancia().porcentagemImoveisVisitadosSemAnormalidade() + "%" + "");
	porcentagemVisitadosSemAnormalidade.getStyle().setBorder(Border.createLineBorder(1));

	linhasRelatorio.addComponent(nomeImoveisVisitar);
	linhasRelatorio.addComponent(this.imoveisVisitar);
	linhasRelatorio.addComponent(porcentagemVisitar);
	linhasRelatorio.addComponent(nomeImoveisVisitadosSemAnormalidade);
	linhasRelatorio.addComponent(this.imoveisVisitadosSemAnormalidade);
	linhasRelatorio.addComponent(porcentagemVisitadosSemAnormalidade);
	linhasRelatorio.addComponent(nomeImoveisVisitadosAnormalidade);
	linhasRelatorio.addComponent(this.imoveisVisitadosAnormalidade);
	linhasRelatorio.addComponent(porcentagemVsistadosAnormalidade);

	relatorioTotal.addComponent(BorderLayout.NORTH, linhasRelatorio);

	voltar = new Command("Voltar");
	fmRelatorios.addCommand(voltar);

	resumoQuadra = new Command("Resumo Quadra");
	fmRelatorios.addCommand(resumoQuadra);

	fmRelatorios.setCommandListener(this);

	fmRelatorios.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 2000));

	fmRelatorios.addComponent(BorderLayout.CENTER, relatorioTotal);

	fmRelatorios.show();
    }

    public static Relatorios getInstancia() {
	if (Relatorios.instancia == null) {
	    Relatorios.instancia = new Relatorios();
	}
	return Relatorios.instancia;
    }

    public void actionPerformed(ActionEvent evt) {
	if (evt.getCommand() == voltar) {
	    TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
	}

	if (evt.getCommand() == resumoQuadra) {
	    System.out.println("Resumo Quadra!");
	    BusinessRelatorio.getInstancia().criarResumoQuadra();

	}
    }
}
