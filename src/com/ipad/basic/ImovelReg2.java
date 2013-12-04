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

import net.sourceforge.floggy.persistence.Persistable;

import com.ipad.util.ParserUtil;
import com.ipad.util.Util;

/**
 * Dados Economias e Subcategorias
 */
public class ImovelReg2 extends Registro implements Persistable {
    
    private int codigoCategoria;
    private String descricaoCategoria;
    private String codigoSubcategoria;
    private String descricaoSubcategoria;
    private int qtdEconomiasSubcategoria;
    private DadosTarifa tarifa;
    private DadosFaturamento faturamentoAgua;
    private DadosFaturamento faturamentoEsgoto;
    private DadosFaturamento faturamentoAguaProporcional;
    private DadosFaturamento faturamentoEsgotoProporcional;
    private String descricaoAbreviadaCategoria;
    private String descricaoAbreviadaSubcategoria;
    private String fatorEconomiaCategoria;
    
    
  //CATEGORIAS
	public final static int RESIDENCIAL = 1;

	public final static int COMERCIAL = 2;
	
	public final static int INDUSTRIAL = 3;
	
	public final static int PUBLICO = 4;
    
	public ImovelReg2(){
		super();
	}
    
    
    public ImovelReg2( String linhaArquivo ) {

    	ParserUtil parser = new ParserUtil( linhaArquivo );
    	
    	// Ignoramos a matricula do imovel e o tipo de arquivo
    	parser.obterDadoParser(11);
    	
    	this.setCodigoCategoria( parser.obterDadoParser(1) );
    	// System.out.println("Codigo Categoria = " + this.getCodigoCategoria() );
    	
    	this.setDescricaoCategoria( parser.obterDadoParser(15) );
    	// System.out.println("Descricao Categoria = " + this.getCodigoCategoria() );
    	
    	this.setCodigoSubcategoria( parser.obterDadoParser(3) );
    	// System.out.println("Codigo Subcategoria = " + this.getCodigoSubcategoria() );
    	
    	this.setDescricaoSubcategoria( parser.obterDadoParser(50) );
    	// System.out.println("Descricao Subcategoria = " + this.getDescricaoSubcategoria() );
    	
    	this.setQtdEconomiasSubcategoria( parser.obterDadoParser(4) );
    	// System.out.println("Qtd Economias Subcategoria = " + this.getQtdEconomiasSubcategoria() );
        
    	this.setDescricaoAbreviadaCategoria( parser.obterDadoParser(3) );
    	// System.out.println("Descricao Abreviada Categoria = " + this.getDescricaoAbreviadaCategoria() );
    	
    	this.setDescricaoAbreviadaSubcategoria( parser.obterDadoParser(20) );
    	// System.out.println("Descricao Abreviada Subcategoria = " + this.getDescricaoAbreviadaSubcategoria() );
    	
    	this.setFatorEconomiaCategoria( parser.obterDadoParser( 2 ) );
    	// System.out.println("Fator Economias Categoria = " + this.getFatorEconomiaCategoria() );
    	
        this.faturamentoAgua = null;
        this.faturamentoEsgoto = null;
        
        Util.resumoMemoria();        
    }

    public int getCodigoCategoria() {
        return codigoCategoria;
    }
    
    public void setCodigoCategoria(String codigoCategoria) {
		this.codigoCategoria = Util.verificarNuloInt( codigoCategoria );
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = Util.verificarNuloString( descricaoCategoria );
	}

	public void setCodigoSubcategoria(String codigoSubcategoria) {
		this.codigoSubcategoria = Util.verificarNuloString( codigoSubcategoria );
	}

	public void setDescricaoSubcategoria(String descricaoSubcategoria) {
		this.descricaoSubcategoria = Util.verificarNuloString( descricaoSubcategoria );
	}

	public void setQtdEconomiasSubcategoria(String qtdEconomiasSubcategoria) {
		this.qtdEconomiasSubcategoria = Util.verificarNuloInt( qtdEconomiasSubcategoria );
	}

	public void setDescricaoAbreviadaCategoria(String descricaoAbreviadaCategoria) {
		this.descricaoAbreviadaCategoria = Util.verificarNuloString( descricaoAbreviadaCategoria );
	}

	public void setDescricaoAbreviadaSubcategoria(
			String descricaoAbreviadaSubcategoria) {
		this.descricaoAbreviadaSubcategoria = Util.verificarNuloString( descricaoAbreviadaSubcategoria );
	}

	public int getQtdEconomiasSubcategoria() {
        return qtdEconomiasSubcategoria;
    }
    
    public DadosTarifa getTarifa() {
        return this.tarifa;
    }
    
    public void setTarifa(DadosTarifa tarifa) {
        this.tarifa = tarifa;
    }

    public void setFaturamentoAgua(DadosFaturamento faturamento) {
        this.faturamentoAgua = faturamento;
    }
    
    public void setFaturamentoEsgoto(DadosFaturamento faturamento) {
        this.faturamentoEsgoto = faturamento;
    }
    
    public DadosFaturamento getFaturamentoAgua() {
        return this.faturamentoAgua;
    }
    
    public DadosFaturamento getFaturamentoEsgoto() {
        return this.faturamentoEsgoto;
    }

    public String getCodigoSubcategoria() {
        return codigoSubcategoria;
    }

    public String getDescricaoCategoria() {
        return descricaoCategoria;
    }

    public String getDescricaoSubcategoria() {
        return descricaoSubcategoria;
    }

    public String getDescricaoAbreviadaCategoria(){
        return descricaoAbreviadaCategoria;
    }

    public String getDescricaoAbreviadaSubcategoria(){
        return descricaoAbreviadaSubcategoria;
    }

    /**
     * @return the fatorEconomiaCategoria
     */
    public String getFatorEconomiaCategoria() {
        return fatorEconomiaCategoria;
    }

    /**
     * @param fatorEconomiaCategoria the fatorEconomiaCategoria to set
     */
    public void setFatorEconomiaCategoria(String fatorEconomiaCategoria) {
        this.fatorEconomiaCategoria = Util.verificarNuloString( fatorEconomiaCategoria );
    }    
    
	public double valorTotalTarifaFaixa(){
    	double soma = 0d;
    	
    	for ( int i = 0; i < this.faturamentoAgua.getFaixas().size(); i++ ){
    		
    		DadosFaturamentoFaixa dadosFaturamento = ( DadosFaturamentoFaixa ) this.faturamentoAgua.getFaixas().elementAt( i );    		
    		
    		soma += 
    			( dadosFaturamento.getValorFaturado() * this.qtdEconomiasSubcategoria ); 
    	}
    	
		soma += 
			faturamentoAgua.getValorTarifaMinima();
    	
    	
    	return soma;		
	}
	
	public double consumoTotalTarifaFaixa(){
    	double soma = 0d;
    	
    	for ( int i = 0; i < this.faturamentoAgua.getFaixas().size(); i++ ){
    		
    		DadosFaturamentoFaixa dadosFaturamento = ( DadosFaturamentoFaixa ) this.faturamentoAgua.getFaixas().elementAt( i );
    		
    		soma += 
    			( dadosFaturamento.getConsumoFaturado() * this.qtdEconomiasSubcategoria );
    	}
    	
		soma += 
			faturamentoAgua.getConsumoMinimo();    	
    	
    	return soma;		
	}


	public DadosFaturamento getFaturamentoAguaProporcional() {
		return faturamentoAguaProporcional;
	}


	public void setFaturamentoAguaProporcional(
			DadosFaturamento faturamentoAguaProporcional) {
		this.faturamentoAguaProporcional = faturamentoAguaProporcional;
	}


	public DadosFaturamento getFaturamentoEsgotoProporcional() {
		return faturamentoEsgotoProporcional;
	}


	public void setFaturamentoEsgotoProporcional(
			DadosFaturamento faturamentoEsgotoProporcional) {
		this.faturamentoEsgotoProporcional = faturamentoEsgotoProporcional;
	}
	
}
