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
import java.util.Vector;

import net.sourceforge.floggy.persistence.Persistable;

import com.ipad.util.ParserUtil;
import com.ipad.util.Util;

/**
 *
 * @author gsan
 */
public class ImovelReg9 extends Registro implements Persistable{

    private int codigoTarifaReg9;
    private Date dataVigenciaReg9;
    private int codigoCategoriaReg9;
    private int codigoSubcategoriaReg9;
    private int consumoMinimoSubcategoriaReg9;
    private double tarifaMinimaCategoriaReg9;
    
    private Vector faixas;
    
	public ImovelReg9(){
		super();
	}    

    public ImovelReg9( String linhaArquivo ){
    	
    	ParserUtil parser = new ParserUtil( linhaArquivo );    	
    	
    	parser.obterDadoParser(2);
    	
    	this.setCodigoTarifaReg9( parser.obterDadoParser(2) );
    	// System.out.println("Código da tarifa = " + this.getCodigoTarifaReg9() );
    	
    	this.setDataVigenciaReg9( parser.obterDadoParser(8) );
    	// System.out.println("Data da vigencia = " + this.getDataVigenciaReg9().toString() );
    	
    	this.setCodigoCategoriaReg9( parser.obterDadoParser(1) );
    	// System.out.println("Codigo da Categoria = " + this.getCodigoCategoriaReg9() );    	
    	
    	this.setCodigoSubcategoriaReg9( parser.obterDadoParser(3) );
    	// System.out.println("Codigo da Subcategoria = " + getCodigoSubcategoriaReg9() );
    	
    	this.setConsumoMinimoSubcategoriaReg9( parser.obterDadoParser(6) );
    	// System.out.println("Consumo Minimo = " + getConsumoMinimoSubcategoriaReg9() );    	
    	
    	this.setTarifaMinimaCategoriaReg9( parser.obterDadoParser(14) );
    	// System.out.println("Tarifa Minima = " + getTarifaMinimaCategoriaReg9() );    	
    }


    public void setCodigoTarifaReg9(String codigoTarifaReg9){
        this.codigoTarifaReg9 = Util.verificarNuloInt( codigoTarifaReg9 );
    }

    public void setDataVigenciaReg9(String dataVigenciaReg9){
        this.dataVigenciaReg9 = Util.getData( Util.verificarNuloString( dataVigenciaReg9 ) );
    }

    public void setCodigoCategoriaReg9(String codigoCategoriaReg9){
        this.codigoCategoriaReg9 = Util.verificarNuloInt( codigoCategoriaReg9 );
    }

    public void setCodigoSubcategoriaReg9(String codigoSubcategoriaReg9){
        this.codigoSubcategoriaReg9 = Util.verificarNuloInt( codigoSubcategoriaReg9 );
    }

    public void setConsumoMinimoSubcategoriaReg9(String consumoMinimoSubcategoriaReg9){
        this.consumoMinimoSubcategoriaReg9 = Util.verificarNuloInt( consumoMinimoSubcategoriaReg9 );
    }

    public void setTarifaMinimaCategoriaReg9(String tarifaMinimaCategoriaReg9){
        this.tarifaMinimaCategoriaReg9 = Util.verificarNuloDouble(tarifaMinimaCategoriaReg9);
    }

    public int getCodigoTarifaReg9(){
        return codigoTarifaReg9;
    }

    public Date getDataVigenciaReg9(){
        return dataVigenciaReg9;
    }

    public int getCodigoCategoriaReg9(){
        return codigoCategoriaReg9;
    }

    public int getCodigoSubcategoriaReg9(){
        return codigoSubcategoriaReg9;
    }

    public int getConsumoMinimoSubcategoriaReg9(){
        return consumoMinimoSubcategoriaReg9;
    }

    public double getTarifaMinimaCategoriaReg9(){
        return tarifaMinimaCategoriaReg9;
    }
    
    public void adicionarFaixa( ImovelReg10 faixa ){
    	if ( this.faixas == null ){
    		this.faixas = new Vector();
    	}
    	
    	this.faixas.addElement( faixa );
    }
}
