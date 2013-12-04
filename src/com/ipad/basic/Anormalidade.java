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

/**
 *
 * @author Administrador
 */
public class Anormalidade implements Persistable, IDable {
    
    public static final int TER_LEITURA = 1;
    public static final int NAO_TER_LEITURA = 2;
    public static final int DUAS_OPCOES = 0;
    
    public String getRecordStoreName() {
		return "Anormalidade";
	}

    private int codigo;
    
    private String descricao;
    
    private int indicadorLeitura;
    
    private int idConsumoACobrarSemLeitura;
    
    private int idConsumoACobrarComLeitura;
    
    private int idLeituraFaturarSemLeitura;
    
    private int idLeituraFaturarComLeitura;
    
    private int indcUso;
    
    private int id;
    
    private double numeroFatorSemLeitura;
    
    private double numeroFatorComLeitura;
    
       /** Creates a new instance of Anormalidade */
    public Anormalidade(byte codigo, String descricao, int indicador) {
        this.codigo = codigo;
        
        if ( descricao != null && descricao.length() > 21 ){
            this.descricao = descricao.substring( 21 );    
        } else {
            this.descricao = descricao;
        }        
        
        this.indicadorLeitura = indicador;
    }
    
    /** Creates a new instance of Anormalidade */
    public Anormalidade() {
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getIndicadorLeitura() {
        return indicadorLeitura;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setDescricao(String descricao) {
        if ( descricao != null && descricao.length() > 21 ){
            this.descricao = descricao.substring( 0, 21 );    
        } else {
            this.descricao = descricao;
        }         
    }

    public void setIndicadorLeitura(int indicadorLeitura) {
        this.indicadorLeitura = indicadorLeitura;
       
    }

    public int getIdConsumoACobrarComLeitura() {
        return idConsumoACobrarComLeitura;
    }

    public int getIdConsumoACobrarSemLeitura() {
        return idConsumoACobrarSemLeitura;
    }

    public void setIdConsumoACobrarComLeitura(int idConsumoACobrarComLeitura) {
        this.idConsumoACobrarComLeitura = idConsumoACobrarComLeitura;
    }

    public void setIdConsumoACobrarSemLeitura(int idConsumoACobrarSemLeitura) {
        this.idConsumoACobrarSemLeitura = idConsumoACobrarSemLeitura;
    }

    public int getIdLeituraFaturarSemLeitura() {
        return idLeituraFaturarSemLeitura;
    }

    public void setIdLeituraFaturarSemLeitura(int idLeituraFaturarSemLeitura) {
        this.idLeituraFaturarSemLeitura = idLeituraFaturarSemLeitura;
    }

    public void setIdLeituraFaturarComLeitura(int idLeituraFaturarComLeitura) {
        this.idLeituraFaturarComLeitura = idLeituraFaturarComLeitura;
    }

    public int getIdLeituraFaturarComLeitura() {
        return idLeituraFaturarComLeitura;
    }
    
    public void setIndcUso(int indcUso){
    	this.indcUso = indcUso;
    }
    
    public int getIndcUso(){
    	return indcUso;
    }

	public void setId(int arg0) {
		this.id = arg0;
	}
    
	public int getId() {
		return this.id;
	}
	
	
	public double getNumeroFatorSemLeitura() {
		return numeroFatorSemLeitura;
	}

	public void setNumeroFatorSemLeitura(double numeroFatorSemLeitura) {
		this.numeroFatorSemLeitura = numeroFatorSemLeitura;
	}

	public double getNumeroFatorComLeitura() {
		return numeroFatorComLeitura;
	}

	public void setNumeroFatorComLeitura(double numeroFatorComLeitura) {
		this.numeroFatorComLeitura = numeroFatorComLeitura;
	}

	public String toString(){
		return this.getDescricao();
	}
	
	public boolean equals( Object obj ){
		return this.getCodigo() == ( (Anormalidade) obj ).getCodigo();
	}
}
