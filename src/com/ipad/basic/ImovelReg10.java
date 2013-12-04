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

import java.util.Date;

import net.sourceforge.floggy.persistence.IDable;
import net.sourceforge.floggy.persistence.Persistable;

import com.ipad.util.ParserUtil;
import com.ipad.util.Util;

/**
 *
 * @author gsan
 */
public class ImovelReg10 implements Persistable, IDable{

    private int id;
	
	private int codigoTarifaReg10;
    private Date dataInicioVigenciaReg10;
    private int codigoCategoriaReg10;
    private int codigoSubcategoriaReg10;
    private int limiteInicialFaixaReg10;
    private int limiteFinalFaixaReg10;
    private double valorM3FaixaReg10;
    
	public ImovelReg10(){
		super();
	}    



    public ImovelReg10( String linhaArquivo ){ 

    	ParserUtil parser = new ParserUtil( linhaArquivo );
    	
    	parser.obterDadoParser(2);
    	
    	this.setCodigoTarifaReg10( parser.obterDadoParser(2) );
    	// System.out.println("Codigo Tarifa = " + this.getCodigoTarifaReg10() );
    	
    	this.setDataInicioVigenciaReg10( parser.obterDadoParser(8) );
    	// System.out.println("Date de Vigencia = " + this.getDataInicioVigenciaReg10() );
    	
    	this.setCodigoCategoriaReg10( parser.obterDadoParser(1) );
    	// System.out.println("Codigo da categoria = " + this.getCodigoCategoriaReg10() );
    	
    	this.setCodigoSubcategoriaReg10( parser.obterDadoParser(3) );
    	// System.out.println("Codigo da Subcategoria = " + this.getCodigoSubcategoriaReg10() );
    	
    	this.setLimiteInicialFaixaReg10( parser.obterDadoParser(6) );
    	// System.out.println("Limite Inicial Faixa = " + this.getLimiteInicialFaixaReg10() );
    	
    	this.setLimiteFinalFaixaReg10( parser.obterDadoParser(6) );
    	// System.out.println("Limite final Faixa = " + this.getLimiteFinalFaixaReg10() );    	
    	
    	this.setValorM3FaixaReg10( parser.obterDadoParser(14) );
    	// System.out.println("Valor do M3 na faixa = " + this.getValorM3FaixaReg10() );    	
    }


    public void setCodigoTarifaReg10(String codigoTarifaReg10){
        this.codigoTarifaReg10 = Util.verificarNuloInt( codigoTarifaReg10 );
    }

    public void setValorM3FaixaReg10(String valorM3FaixaReg10){
        this.valorM3FaixaReg10 = Util.verificarNuloDouble( valorM3FaixaReg10 );
    }

    public void setDataInicioVigenciaReg10(String dataInicioVigenciaReg10){
        this.dataInicioVigenciaReg10 = Util.getData( dataInicioVigenciaReg10 );
    }

    public void setCodigoCategoriaReg10(String codigoCategoriaReg10){
        this.codigoCategoriaReg10 = Util.verificarNuloInt( codigoCategoriaReg10 );
    }

    public void setCodigoSubcategoriaReg10(String codigoSubcategoriaReg10){
        this.codigoSubcategoriaReg10 = Util.verificarNuloInt( codigoSubcategoriaReg10 );
    }

    public void setLimiteInicialFaixaReg10(String limiteInicialFaixaReg10){
        this.limiteInicialFaixaReg10 = Util.verificarNuloInt( limiteInicialFaixaReg10 );
    }

    public void setLimiteFinalFaixaReg10(String limiteFinalFaixaReg10){
        this.limiteFinalFaixaReg10 = Util.verificarNuloInt( limiteFinalFaixaReg10 );
    }

    public int getCodigoTarifaReg10(){
        return codigoTarifaReg10;
    }

    public Date getDataInicioVigenciaReg10(){
        return dataInicioVigenciaReg10;
    }

    public int getCodigoCategoriaReg10(){
        return codigoCategoriaReg10;
    }

    public int getCodigoSubcategoriaReg10(){
        return codigoSubcategoriaReg10;
    }

    public int getLimiteInicialFaixaReg10(){
        return limiteInicialFaixaReg10;
    }

    public int getLimiteFinalFaixaReg10(){
        return limiteFinalFaixaReg10;
    }

    public double getValorM3FaixaReg10(){
        return valorM3FaixaReg10;
    }



	public void setId(int arg0) {
		this.id = arg0;
	}
	
	public int getId(){
		return this.id;
	}
}
