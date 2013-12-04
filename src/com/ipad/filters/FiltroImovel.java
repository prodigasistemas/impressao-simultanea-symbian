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
package com.ipad.filters;

import net.sourceforge.floggy.persistence.Persistable;

import com.ipad.basic.ImovelConta;
import com.ipad.util.Constantes;

public class FiltroImovel implements net.sourceforge.floggy.persistence.Filter {
	
	public static final char PESQUISAR_POR_ID = 0;
	public static final char PESQUISAR_POR_FAIXA = 1;
	public static final char PESQUISAR_POR_QUADRA = 2;
	public static final char PESQUISAR_POR_NUMERO_HIDROMETRO = 3;
	public static final char PESQUISAR_POR_MATRICULA = 4;
	public static final char PESQUISAR_POR_SEQUENCIAL_ROTA = 5;	
	
	private int id;
	private int faixaInicial = Constantes.NULO_INT;
	private int faixaFinal = Constantes.NULO_INT;
	private int matricula;
	private String numeroHidrometro;
	private int quadra;
	private int seqRota;
	private char tipoPesquisa;
	private boolean soCalculados;
	
	public FiltroImovel( char tipoPesquisa, int chave ){
		if ( tipoPesquisa == PESQUISAR_POR_ID ){
			this.id = chave;
		} else	if ( tipoPesquisa == PESQUISAR_POR_MATRICULA ){
			this.matricula = chave;
		} else if ( tipoPesquisa == PESQUISAR_POR_SEQUENCIAL_ROTA ){
			this.seqRota = chave;			
		} else if ( tipoPesquisa == PESQUISAR_POR_QUADRA ){
			this.quadra = chave;			
		}
		
		this.tipoPesquisa = tipoPesquisa;
	}
	
	public FiltroImovel( char tipoPesquisa, String chave ){
		if ( tipoPesquisa == PESQUISAR_POR_NUMERO_HIDROMETRO ){
			this.numeroHidrometro = chave;			
			this.tipoPesquisa = PESQUISAR_POR_NUMERO_HIDROMETRO;
		}
	}
	
	public FiltroImovel( int chaveInicial, int chaveFinal, boolean soCalculados ){
		this.faixaInicial = chaveInicial;
		this.faixaFinal = chaveFinal;
		this.tipoPesquisa = PESQUISAR_POR_FAIXA;
		this.soCalculados = soCalculados;
	}
	
    public boolean matches(Persistable persistable) {
    	
    	boolean retorno = false;
    	
    	ImovelConta imovel = (ImovelConta) persistable;
    	    	
		if ( this.tipoPesquisa == PESQUISAR_POR_ID ){
			retorno = this.id == imovel.getId();			
		} else if ( this.tipoPesquisa == PESQUISAR_POR_MATRICULA ){
			retorno = this.matricula == imovel.getMatricula();			
		} else if ( this.tipoPesquisa == PESQUISAR_POR_NUMERO_HIDROMETRO ){
			if ( this.numeroHidrometro == null || imovel.getNumeroHidrometro( Constantes.LIGACAO_AGUA ) == null ){
				retorno = false;
			} else {
				retorno = this.numeroHidrometro.equals( imovel.getNumeroHidrometro( Constantes.LIGACAO_AGUA ) );
			}
		} else if ( this.tipoPesquisa == PESQUISAR_POR_SEQUENCIAL_ROTA ){
			retorno = this.seqRota == imovel.getSequencialRota();			
		} else if ( this.tipoPesquisa == PESQUISAR_POR_QUADRA ){
			retorno = this.quadra == imovel.getQuadra();			
		} else if ( this.tipoPesquisa == PESQUISAR_POR_FAIXA ){
			if ( soCalculados ){
				retorno =  
					imovel.getId() >= faixaInicial &&
					imovel.getId() <= faixaFinal;
			} else {
				retorno =
					imovel.getId() >= faixaInicial &&
					imovel.getId() <= faixaFinal;		
			}
		}
				
		return retorno;
    }
} 
