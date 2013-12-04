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

import com.ipad.basic.Configuracao;
import com.ipad.io.Repositorio;
import com.ipad.util.Constantes;
import com.ipad.util.Util;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;


public class TelaSelecaoPapel implements ActionListener {

    private static Form frmSelecaoPapel;
    private static TelaSelecaoPapel instancia; 
    private static Command cmdOk;
    private static Command cmdVoltar;
    private static List lstOpcoesPapel;
    
    public static TelaSelecaoPapel getInstancia(){
	if ( instancia == null ){
	    instancia = new TelaSelecaoPapel();
	}
	
	return instancia;
    }
    
    public void criarTelaSelecaoPapel(){
	if ( frmSelecaoPapel == null ){
	    frmSelecaoPapel = new Form( "Tipo de papel..." );
	    frmSelecaoPapel.setLayout( new BorderLayout() );
	    
	    Object[] objLista = new Object[2];
	    objLista[0] = "Papel layout Regispel";
	    objLista[1] = "Papel layout Centauro";
	    
	    lstOpcoesPapel = new List( objLista );
	    
	    frmSelecaoPapel.addComponent( BorderLayout.CENTER, lstOpcoesPapel );	    
	    
	    cmdOk = new Command( "Ok" );
	    cmdVoltar = new Command( "Voltar" );
	    
	    frmSelecaoPapel.addCommand( cmdOk );
	    frmSelecaoPapel.addCommand( cmdVoltar );	
	    frmSelecaoPapel.setCommandListener( this );
	}
	
	frmSelecaoPapel.show();
    }
        

    public void actionPerformed(ActionEvent arg0) {	
	if ( arg0.getCommand() == cmdOk ){
	    
	    if ( lstOpcoesPapel.getSelectedIndex() == Constantes.PAPEL_CENTAURO ){
		if ( Util.MensagemConfirmacao( "Atenção, extrato do macro medidor não será impresso.") ) {		    
			Configuracao.getInstancia().setTipoPapel( Constantes.PAPEL_CENTAURO );
		} else {
		    return;
		}
	    } else {
	    	Configuracao.getInstancia().setTipoPapel( Constantes.PAPEL_REGISPEL );
	    }
	    
	    Repositorio.salvarObjeto( Configuracao.getInstancia() );
	    TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
	} else {
	    TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
	}
    }

}
