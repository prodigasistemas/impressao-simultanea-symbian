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

import net.sourceforge.floggy.persistence.Persistable;

import com.ipad.util.Constantes;
import com.ipad.util.ParserUtil;
import com.ipad.util.Util;

/**
 * Dados dos Tipos de Medição
 */
public class ImovelReg8 extends Registro implements Persistable {

    private int tipoMedicao;
    private String numeroHidrometro;
    private Date dataInstalacaoHidrometro;
    private int numDigitosLeituraHidrometro;
    private int leituraAnteriorFaturamento;
    private Date dataLeituraAnteriorFaturado;
    private Date dataLeituraAnteriorInformada;
    private Date dataLigacaoFornecimento = null;
    private int codigoSituacaoLeituraAnterior;
    private int leituraEsperadaInicial;
    private int leituraEsperadaFinal;
    private String consumoMedio;
    private int leitura = Constantes.NULO_INT;
    private int anormalidade = Constantes.NULO_INT;
    private Date dataLeitura;
    private String localInstalacao;
    private int leituraAnteriorInformada;
    private int leituraAnterior;
    private Date dataLeituraAtualFaturamento;
    private int qtdDiasAjustado = Constantes.NULO_INT;
    private int leituraAtualFaturamento = Constantes.NULO_INT;
    private int leituraRelatorio = Constantes.NULO_INT;
    private int anormalidadeRelatorio = Constantes.NULO_INT;
    private short tipoRateio = Constantes.NULO_SHORT;
    private int leituraInstalacaoHidrometro = Constantes.NULO_INT;

    
    // Tipos de Rateio
    public static final short TIPO_RATEIO_SEM_RATEIO = 0;
    public static final short TIPO_RATEIO_POR_IMOVEL = 1;
    public static final short TIPO_RATEIO_AREA_CONSTRUIDA = 2;
    public static final short TIPO_RATEIO_NUMERO_MORADORES = 3;
    public static final short TIPO_RATEIO_NAO_MEDIDO_AGUA = 4;

    public ImovelReg8() {
	super();
    }

    public Date getDataInstalacaoHidrometro() {
	return dataInstalacaoHidrometro;
    }

    public void setDataInstalacaoHidrometro(String dataInstalacaoHidrometro) {
	this.dataInstalacaoHidrometro = Util.getData(Util
		.verificarNuloString(dataInstalacaoHidrometro));
    }

    public int getNumDigitosLeituraHidrometro() {
	return numDigitosLeituraHidrometro;
    }

    public void setNumDigitosLeituraHidrometro(
	    String numDigitosLeituraHidrometro) {
	this.numDigitosLeituraHidrometro = Util
		.verificarNuloInt(numDigitosLeituraHidrometro);
    }

    public int getLeituraAnteriorFaturamento() {
	return leituraAnteriorFaturamento;
    }

    public void setLeituraAnteriorFaturamento(String leituraAnteriorFaturamento) {
	this.leituraAnteriorFaturamento = Util
		.verificarNuloInt(leituraAnteriorFaturamento);
    }

    public int getCodigoSituacaoLeituraAnterior() {
	return codigoSituacaoLeituraAnterior;
    }

    public void setCodigoSituacaoLeituraAnterior(
	    String codigoSituacaoLeituraAnterior) {
	this.codigoSituacaoLeituraAnterior = Util
		.verificarNuloInt(codigoSituacaoLeituraAnterior);
    }

    public int getConsumoMedio() {
	return Util.verificarNuloInt(consumoMedio);
    }

    public void setConsumoMedio(String consumoMedio) {
	this.consumoMedio = Util.verificarNuloString(consumoMedio);
    }

    public void setTipoMedicao(String tipoMedicao) {
	this.tipoMedicao = Util.verificarNuloInt(tipoMedicao);
    }

    public void setNumeroHidrometro(String numeroHidrometro) {
	this.numeroHidrometro = Util.verificarNuloString(numeroHidrometro);
    }

    public void setDataLeituraAnteriorFaturado(String dataLeituraAnteriorFaturado) {
	this.dataLeituraAnteriorFaturado = Util.getData(Util
		.verificarNuloString(dataLeituraAnteriorFaturado));
    }

    public void setDataLeituraAnteriorInformada(String dataLeituraAnteriorInformada) {
    	this.dataLeituraAnteriorInformada = Util.getData(Util
    		.verificarNuloString(dataLeituraAnteriorInformada));
        }

    public void setDataLigacaoFornecimento(String dataLigacaoFornecimento) {
    	this.dataLigacaoFornecimento = Util.getData(Util
    		.verificarNuloString(dataLigacaoFornecimento));
        }
    
    public void setLeituraEsperadaInicial(String leituraEsperadaInicial) {
	this.leituraEsperadaInicial = Util
		.verificarNuloInt(leituraEsperadaInicial);
    }

    public void setLeituraEsperadaFinal(String leituraEsperadaFinal) {
	this.leituraEsperadaFinal = Util.verificarNuloInt(leituraEsperadaFinal);
    }

    public ImovelReg8(String linhaArquivo) {

		// Pulamos a matricula do imovel
		Util.resumoMemoria();
	
		ParserUtil parser = new ParserUtil(linhaArquivo);
	
		// Ignoramos a matricula do imovel e o tipo de arquivo
		parser.obterDadoParser(11);
	
		this.setTipoMedicao(parser.obterDadoParser(1));
		this.setNumeroHidrometro(parser.obterDadoParser(11));
		this.setDataInstalacaoHidrometro(parser.obterDadoParser(8));
		this.setNumDigitosLeituraHidrometro(parser.obterDadoParser(1));
		this.setLeituraAnteriorFaturamento(parser.obterDadoParser(7));
		this.setDataLeituraAnteriorFaturado(parser.obterDadoParser(8));
		this.setCodigoSituacaoLeituraAnterior(parser.obterDadoParser(1));
		this.setLeituraEsperadaInicial(parser.obterDadoParser(7));
		this.setLeituraEsperadaFinal(parser.obterDadoParser(7));
		this.setConsumoMedio(parser.obterDadoParser(6));
		this.setLocalInstalacao(parser.obterDadoParser(20));
		this.setLeituraAnteriorInformada(parser.obterDadoParser(7));
	// Daniel - incluindo campo DataLeituraAnteriorInformada para apresentar na conta impressa.
		this.setDataLeituraAnteriorInformada(parser.obterDadoParser(8));
	// Daniel - incluindo campo DataLigacaoFornecimento para descobrir se é nova instalação ou apenas imovel que mudo de fixo para hidrometrado.
		this.setDataLigacaoFornecimento(parser.obterDadoParser(8));
		this.setTipoRateio(parser.obterDadoParser(1));
		
		if (linhaArquivo.length() == 119){
			this.setLeituraInstalacaoHidrometro(parser.obterDadoParser(7));
		}else{
			this.leituraInstalacaoHidrometro = 0;
		}
    }

    public int getTipoMedicao() {
	return tipoMedicao;
    }

    public String getNumeroHidrometro() {
	return numeroHidrometro;
    }

    public int getLeitura() {
	return leitura;
    }

    public int getAnormalidade() {
	return anormalidade;
    }

    public int getSituacaoLeituraAnterior() {
	return codigoSituacaoLeituraAnterior;
    }

    public int getLeituraEsperadaInicial() {
	return leituraEsperadaInicial;
    }

    public int getLeituraEsperadaFinal() {
	return leituraEsperadaFinal;
    }

    public int getNumDigitosLeitura() {
	return numDigitosLeituraHidrometro;
    }

    public Date getDataInstalacao() {
	return dataInstalacaoHidrometro;
    }

    public Date getDataLeitura() {
	return dataLeitura;
    }

    public Date getDataLeituraAnteriorFaturada() {
	return dataLeituraAnteriorFaturado;
    }

    public Date getDataLeituraAnteriorInformada() {
    	return dataLeituraAnteriorInformada;
        }

    public Date getDataLigacaoFornecimento() {
    	return dataLigacaoFornecimento;
        }

    
    public void setLeitura(String leitura) {
	this.leitura = Util.verificarNuloInt(leitura);
    }

    public void setLeitura(int leitura) {
	this.leitura = leitura;
    }

    public void setAnormalidade(String anormalidade) {
	this.anormalidade = Util.verificarNuloInt(anormalidade);
    }

    public void setAnormalidade(int anormalidade) {
	this.anormalidade = anormalidade;
    }

    public void setDataLeitura(Date dataLeitura) {
	this.dataLeitura = dataLeitura;
    }

    public String getTipoMedicaoString() {
	String literal = "Não inform.";

	if (this.tipoMedicao == Constantes.LIGACAO_AGUA) {
	    literal = "Lig Àgua";
	}

	if (this.tipoMedicao == Constantes.LIGACAO_POCO) {
	    literal = "Lig Poço";
	}
	return literal;
    }

    public double getNumeroHidrometroConvertendoLetraParaNumeros() {
	return Util.convertendoLetraParaNumeros(this.getNumeroHidrometro());
    }

    public String getLocalInstalacao() {
	return localInstalacao;
    }

    public void setLocalInstalacao(String localInstalacao) {
	this.localInstalacao = Util.verificarNuloString(localInstalacao);
    }

    public int getLeituraAnteriorInformada() {
	return leituraAnteriorInformada;
    }

    public void setLeituraAnteriorInformada(String leituraAnteriorInformada) {
	this.leituraAnteriorInformada = Util
		.verificarNuloInt(leituraAnteriorInformada);
    }

    public int getLeituraAnterior() {
	return leituraAnterior;
    }

    public void setLeituraAnterior(int leituraAnterior) {
	this.leituraAnterior = leituraAnterior;
    }

    public Date getDataLeituraAtualFaturamento() {
	return dataLeituraAtualFaturamento;
    }

    public void setDataLeituraAtualFaturamento(Date dataLeituraAtualFaturamento) {
	this.dataLeituraAtualFaturamento = dataLeituraAtualFaturamento;
    }

    public int getLeituraAtualFaturamento() {
	return leituraAtualFaturamento;
    }

    public void setLeituraAtualFaturamento(int leituraAtualFaturamento) {
	this.leituraAtualFaturamento = leituraAtualFaturamento;
    }

    public int getLeituraRelatorio() {
	return leituraRelatorio;
    }

    public void setLeituraRelatorio(int leituraRelatorio) {
	this.leituraRelatorio = leituraRelatorio;
    }

    public int getAnormalidadeRelatorio() {
	return anormalidadeRelatorio;
    }

    public void setAnormalidadeRelatorio(int anormalidadeRelatorio) {
	this.anormalidadeRelatorio = anormalidadeRelatorio;
    }

    public int getQtdDiasAjustado() {
	return qtdDiasAjustado;
    }

    public void setQtdDiasAjustado(int qtdDiasAjustado) {
	this.qtdDiasAjustado = qtdDiasAjustado;
    }

    public void setTipoRateio(String tipoRateio) {
	this.tipoRateio = Util.verificarNuloShort(tipoRateio);
    }

    public short getTipoRateio() {
	return tipoRateio;
    }
 
    public int getLeituraInstalacaoHidrometro() {
    	return leituraInstalacaoHidrometro;
	}

	public void setLeituraInstalacaoHidrometro(String leituraInstalacaoHidrometro) {
		if ( Util.verificarNuloInt(leituraInstalacaoHidrometro) == Constantes.NULO_INT ){
			this.leituraInstalacaoHidrometro = 0;
		}else{
			this.leituraInstalacaoHidrometro = Util.verificarNuloInt(leituraInstalacaoHidrometro);
		}
	}

}
