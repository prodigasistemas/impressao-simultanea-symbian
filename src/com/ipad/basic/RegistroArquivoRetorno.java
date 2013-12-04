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
package com.ipad.basic;

import net.sourceforge.floggy.persistence.IDable;
import net.sourceforge.floggy.persistence.Persistable;

import com.ipad.util.Constantes;

public class RegistroArquivoRetorno implements Persistable, IDable {
	
	public RegistroArquivoRetorno( ImovelConta imovel ) {
		super();
		
		this.matriculaImovel = imovel.getMatricula();
		
		ImovelReg8 reg8Agua = imovel.getRegistro8( Constantes.LIGACAO_AGUA );
		ImovelReg8 reg8Poco = imovel.getRegistro8( Constantes.LIGACAO_POCO );
		
		if ( reg8Agua != null ){
			this.setAnormalidadeAgua( reg8Agua.getAnormalidade() );
			this.setValorLeituraAgua( reg8Agua.getLeitura() );
		}
		
		if ( reg8Poco != null ){
			this.setAnormalidadePoco( reg8Poco.getAnormalidade() );
			this.setValorLeituraPoco( reg8Poco.getLeitura() );
		}
	}
	public RegistroArquivoRetorno() {
		super();
	}
	public int id;
	public int matriculaImovel;
	public double valorLeituraAgua;
	public double valorLeituraPoco;
	public double anormalidadeAgua;
	public double anormalidadePoco;
	
	public int getMatriculaImovel() {
		return matriculaImovel;
	}
	public void setMatriculaImovel(int matriculaImovel) {
		this.matriculaImovel = matriculaImovel;
	}
	public double getValorLeituraAgua() {
		return valorLeituraAgua;
	}
	public void setValorLeituraAgua(double valorLeituraAgua) {
		this.valorLeituraAgua = valorLeituraAgua;
	}
	public double getValorLeituraPoco() {
		return valorLeituraPoco;
	}
	public void setValorLeituraPoco(double valorLeituraPoco) {
		this.valorLeituraPoco = valorLeituraPoco;
	}
	public double getAnormalidadeAgua() {
		return anormalidadeAgua;
	}
	public void setAnormalidadeAgua(double anormalidadeAgua) {
		this.anormalidadeAgua = anormalidadeAgua;
	}
	public double getAnormalidadePoco() {
		return anormalidadePoco;
	}
	public void setAnormalidadePoco(double anormalidadePoco) {
		this.anormalidadePoco = anormalidadePoco;
	}
	
	public boolean registroAlterado( ImovelConta imovel ){
		
		ImovelReg8 reg8Agua = imovel.getRegistro8( Constantes.LIGACAO_AGUA );
		ImovelReg8 reg8Poco = imovel.getRegistro8( Constantes.LIGACAO_POCO );
		
		if ( reg8Agua != null ){
			if ( reg8Agua.getLeitura() != this.getValorLeituraAgua() ||
				 reg8Agua.getAnormalidade() != this.getAnormalidadeAgua() ){
				return true;
			}
		}
		
		if ( reg8Poco != null ){
			if ( reg8Poco.getLeitura() != this.getValorLeituraPoco() ||
			     reg8Poco.getAnormalidade() != this.getAnormalidadePoco() ){
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * Altera o valor do registro, para o imovel informado.
	 * 
	 * @param imovel
	 */
	public void alterarRegistro( ImovelConta imovel ){
		ImovelReg8 reg8Agua = imovel.getRegistro8( Constantes.LIGACAO_AGUA );
		ImovelReg8 reg8Poco = imovel.getRegistro8( Constantes.LIGACAO_POCO );
		
		if ( reg8Agua != null ){
			if ( reg8Agua.getLeitura() != this.getValorLeituraAgua() ||
				 reg8Agua.getAnormalidade() != this.getAnormalidadeAgua() ){
				this.setAnormalidadeAgua( reg8Agua.getAnormalidade() );
				this.setValorLeituraAgua( reg8Agua.getLeitura() );
			}
		}
		
		if ( reg8Poco != null ){
			if ( reg8Poco.getLeitura() != this.getValorLeituraPoco() ||
			     reg8Poco.getAnormalidade() != this.getAnormalidadePoco() ){
				this.setAnormalidadePoco( reg8Poco.getAnormalidade() );
				this.setValorLeituraPoco( reg8Poco.getLeitura() );
			}
		}
	}
	public void setId(int arg0) {
		this.id = arg0;		
	}
	public String getRecordStoreName() {
		
		return "ArquivoRetorno";
	}
}
