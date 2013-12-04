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

package com.ipad.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Vector;

import javax.microedition.io.file.FileConnection;

import com.ipad.basic.Configuracao;
import com.ipad.basic.Consumo;
import com.ipad.basic.DadosFaturamento;
import com.ipad.basic.DadosFaturamentoFaixa;
import com.ipad.basic.ImovelConta;
import com.ipad.basic.ImovelReg11;
import com.ipad.basic.ImovelReg2;
import com.ipad.basic.ImovelReg6;
import com.ipad.basic.ImovelReg8;
import com.ipad.basic.RegistroArquivoRetorno;
import com.ipad.business.ControladorImoveis;
import com.ipad.component.Progress;
import com.ipad.facade.Fachada;
import com.ipad.util.Constantes;
import com.ipad.util.Util;
import com.ipad.view.Abas;

public class ArquivoRetorno {

    private StringBuffer registrosTipo1 = null;
    private StringBuffer registrosTipo2e3 = null;
    private StringBuffer registrosTipo4 = null;
    private StringBuffer registroTipo0 =  null;
    private Vector registrosArquivoRetorno = new Vector();
    private static StringBuffer arquivo;
    private static boolean linhaTipo1Anexada;
    
    private static ArquivoRetorno instancia;
    public static final short ARQUIVO_COMPLETO = 0; 
    public static final short ARQUIVO_CONCLUIDOS_ATE_AGORA = 1;
    public static final short ARQUIVO_INCOMPLETO = 2;
    public static final short ARQUIVO_TODOS_OS_CALCULADOS = 3;
    
    public static String getCaminhoArquivoRetorno(short tipoArquivo) {

	ImovelConta imovel = (ImovelConta) Repositorio.pesquisarPrimeiroObjeto(ImovelConta.class);

	if (tipoArquivo == ARQUIVO_TODOS_OS_CALCULADOS) {
	    return FileManager.INPUT_FILE_PATH + "Retorno/" + "GCOMPLETO"
		    + imovel.getLocalidade() + imovel.getSetorComercial()
		    + imovel.getCodigoRota()
		    + ImovelReg11.getInstancia().getRegistro11().getAnoMesFaturamento() + ".txt";
	} else {
	    return FileManager.INPUT_FILE_PATH + "Retorno/" + "G"
	    	+ imovel.getLocalidade() + imovel.getSetorComercial()
	    	+ imovel.getCodigoRota()
	    	+ ImovelReg11.getInstancia().getRegistro11().getAnoMesFaturamento() + ".txt";
	}
    }

    private ArquivoRetorno() {
	super();
    }

    public static ArquivoRetorno getInstancia() {
	if (instancia == null) {
	    instancia = new ArquivoRetorno();
	}

	return instancia;
    }

    private void gerarRegistroTipo1(ImovelConta imovel) {

	registrosTipo1 = new StringBuffer();

	ImovelReg8 reg8Agua = imovel.getRegistro8(Constantes.LIGACAO_AGUA);
	ImovelReg8 reg8Poco = imovel.getRegistro8(Constantes.LIGACAO_POCO);
	Consumo consumoAgua = imovel.getConsumoAgua();
	Consumo consumoEsgoto = imovel.getConsumoEsgoto();
	boolean temAgua = false;
	boolean temEsgoto = false;

	if(!linhaTipo1Anexada){
		linhaTipo1Anexada = true;
		if ( (consumoAgua != null || (reg8Agua != null && !reg8Agua.equals(""))) &&
				 (consumoAgua != null && consumoAgua.getAnormalidadeConsumo() != 0) ){
			
			temAgua = true;
	
		    String indicadorSituacao = "" + Constantes.LEITURA_REALIZADA;
	
		    if (reg8Agua != null && reg8Agua.getLeitura() >= reg8Agua.getLeituraEsperadaInicial() && reg8Agua.getLeitura() <= reg8Agua.getLeituraEsperadaFinal()) {
		    	indicadorSituacao = "" + Constantes.LEITURA_REALIZADA;
		    } else {
		    	if (reg8Agua != null) {
		    		indicadorSituacao = "" + Constantes.LEITURA_CONFIRMADA;
		    	}
		    }
	
		    String anormalidadeLeitura = "";
		    Date dataLeitura = new Date();
		    
		    if (reg8Agua == null && reg8Poco == null && imovel.getDataImpressaoNaoMedido() != null){
		    	dataLeitura = imovel.getDataImpressaoNaoMedido();
		    
		    }else if (reg8Agua != null && !reg8Agua.equals("")) {
				anormalidadeLeitura = "" + reg8Agua.getAnormalidade();
				dataLeitura = reg8Agua.getDataLeitura();
		    
		    } else if(reg8Poco != null && !reg8Poco.equals("") && reg8Poco.getDataLeitura() != null) {
		    	dataLeitura = reg8Poco.getDataLeitura();
		    }
	
	    	// Tipo de Registro
	    	registrosTipo1.append("1");
	    	// Matricula
	    	registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula()+ ""));
	    	// Tipo de Medição
	    	registrosTipo1.append("1");
		    // Ano/Mês do faturamento
	    	registrosTipo1.append(Util.getMesAno(ImovelReg11.getInstancia().getRegistro11().getAnoMesFaturamento()));
		    // Número da Conta
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getNumeroConta() + ""));
		    // Grupo de faturamento
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(3, imovel.getGrupoFaturamento() + ""));
		    // Código da rota
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(7, imovel.getCodigoRota() + ""));
		    // Leitura do Hidrômetro
		    registrosTipo1.append(Util.adicionarCharEsquerda(7, (reg8Agua != null ? reg8Agua.getLeitura() + "" : ""), ' '));
		    // Anormalidade de Leitura
		    registrosTipo1.append(Util.adicionarCharEsquerda(2, anormalidadeLeitura, ' '));
		    // Data e hora da leitura
		    registrosTipo1.append(Util.adicionarCharEsquerda(26, (Util.formatarData(dataLeitura)), ' '));
		    // Indicador de situação da leitura
		    registrosTipo1.append(indicadorSituacao);
		    // Leitura de faturamento
		    registrosTipo1.append(Util.adicionarCharEsquerda(7, consumoAgua != null ? consumoAgua.getLeituraAtual() + "" : "", ' '));
		    // Consumo Medido no Mes
		    registrosTipo1.append(Util.adicionarCharEsquerda(6, consumoAgua != null ? consumoAgua.getConsumoMedidoMes() + "": "", ' '));
		    // Consumo Cobrado no Mes
		    registrosTipo1.append(Util.adicionarCharEsquerda(6, consumoAgua != null ? consumoAgua.getConsumoCobradoMes() + "": "", ' '));
		    // Consumo Rateio Agua
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(6, imovel.getConsumoRateioAgua() > 0 ? imovel.getConsumoRateioAgua() + "": ""));
		    // Valor Rateio Agua
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(8, imovel.getValorRateioAgua() > 0 ? Util.formatarDoubleParaMoedaReal(imovel.getValorRateioAgua()) + "": ""));
		    // Consumo Rateio Esgoto
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(6, imovel.getConsumoRateioEsgoto() > 0 ? imovel.getConsumoRateioEsgoto() + "" : "") );
		    // Valor Rateio Esgoto
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(8, imovel.getValorRateioEsgoto() > 0 ? Util.formatarDoubleParaMoedaReal(imovel.getValorRateioEsgoto()) + "" : "") );
		    // Tipo de Consumo
		    registrosTipo1.append(Util.adicionarCharEsquerda(2, consumoAgua != null ? consumoAgua.getTipoConsumo() + "": "", ' ') );
		    // Anormalidade de Consumo
		    registrosTipo1.append(Util.adicionarCharEsquerda(2, consumoAgua != null ? consumoAgua.getAnormalidadeConsumo() + "": "", ' ') );
		    // Indicador de emissao de conta
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(1, imovel.getIndcImovelImpresso() + "") );
		    // Inscricao do Imovel
		    registrosTipo1.append(imovel.getInscricao());
		    // Indicador Conta Retida
		    registrosTipo1.append(imovel.getIndcGeracao());
		    // Consumo Imóveis MICRO Sem Rateio
		    registrosTipo1.append(Util.adicionarCharEsquerda(6, consumoAgua != null ? consumoAgua.getConsumoCobradoMesImoveisMicro() + "": "", ' ') );
		    // Anormalidade de faturamento
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(2, consumoAgua != null ? consumoAgua.getAnormalidadeLeituraFaturada() + "": ""));
		    // ID do documento de cobrança
		    registrosTipo1.append(Util.adicionarCharEsquerda(9,imovel.getNumeroDocumentoNotificacaoDebito(), ' '));
		    // Leitura Anterior do Hidrômetro
		    registrosTipo1.append(Util.adicionarCharEsquerda(7, (reg8Agua != null ? reg8Agua.getLeituraAnterior() + "" : ""), ' '));
		    //latitude
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(20, imovel.getLatitude() != Constantes.NULO_DOUBLE ? imovel.getLatitude()+"" : "") );
		    //longitude
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(20, imovel.getLongitude() != Constantes.NULO_DOUBLE ? imovel.getLongitude()+"" : "") );
		    // Versao do I.S. em uso
		    registrosTipo1.append(Util.adicionarCharEsquerda(12, Fachada.getInstancia().getVersao(), ' '));
		    registrosTipo1.append("\n");
		
		    System.out.println(registrosTipo1);
		}
	
		if ( (consumoEsgoto != null || (reg8Poco != null && !reg8Poco.equals(""))) && 
			 (consumoEsgoto != null && consumoEsgoto.getAnormalidadeConsumo() != 0) ) {
			
			temEsgoto = true;
	
		    String indicadorSituacao = "" + Constantes.LEITURA_REALIZADA;
	
		    if (reg8Poco != null && reg8Poco.getLeitura() >= reg8Poco.getLeituraEsperadaInicial() && reg8Poco.getLeitura() <= reg8Poco.getLeituraEsperadaFinal()) {
		    	indicadorSituacao = "" + Constantes.LEITURA_REALIZADA;
		    } else {
				if (reg8Poco != null) {
				    indicadorSituacao = "" + Constantes.LEITURA_CONFIRMADA;
				}
		    }
	
		    String anormalidadeLeitura = "";
		    Date dataLeitura = new Date();
		    if (reg8Poco != null && !reg8Poco.equals("")) {
		    	anormalidadeLeitura = "" + reg8Poco.getAnormalidade();
		    	dataLeitura = reg8Poco.getDataLeitura();
		    } else {
				if (imovel.getAnormalidadeSemHidrometro() != Constantes.NULO_INT) {
				    anormalidadeLeitura = "" + imovel.getAnormalidadeSemHidrometro();
				}
				if (reg8Agua != null && !reg8Agua.equals("") && reg8Agua.getDataLeitura() != null) {
				    dataLeitura = reg8Agua.getDataLeitura();
				}
		    }
	
	    	// Tipo de Registro
    		registrosTipo1.append("1");
    		// Matricula
    		registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula() + ""));
		    // Tipo de Medição
    		registrosTipo1.append("2");
    		// Ano/Mês do faturamento
	    	registrosTipo1.append(Util.getMesAno(ImovelReg11.getInstancia().getRegistro11().getAnoMesFaturamento()));
		    // Número da Conta
    		registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getNumeroConta() + "") );
		    // Grupo de faturamento
    		registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(3, imovel.getGrupoFaturamento() + "") );
		    // Código da rota
    		registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(7, imovel.getCodigoRota() + "") );
		    // Leitura do Hidrômetro
    		registrosTipo1.append(Util.adicionarCharEsquerda(7, (reg8Poco != null ? reg8Poco.getLeitura() + "" : ""), ' ') ); 
		    // Anormalidade de Leitura
    		registrosTipo1.append(Util.adicionarCharEsquerda(2, anormalidadeLeitura, ' ') );
		    // Data e hora da leitura
    		registrosTipo1.append(Util.adicionarCharEsquerda(26, (Util.formatarData(dataLeitura)), ' ') );
		    // Indicador de situação da leitura
    		registrosTipo1.append(indicadorSituacao);
		    // Leitura de faturamento
    		registrosTipo1.append(Util.adicionarCharEsquerda(7, consumoEsgoto != null ? consumoEsgoto.getLeituraAtual() + "": "", ' ') );
		    // Consumo Medido no Mes
    		registrosTipo1.append(Util.adicionarCharEsquerda(6, consumoEsgoto != null ? consumoEsgoto.getConsumoMedidoMes() + "": "", ' ') );
		    // Consumo Cobrado no Mes
    		registrosTipo1.append(Util.adicionarCharEsquerda(6, consumoEsgoto != null ? consumoEsgoto.getConsumoCobradoMes() + "": "", ' ') );
		    // Consumo Rateio Agua
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(6, imovel.getConsumoRateioAgua() > 0 ? imovel.getConsumoRateioAgua() + "": ""));
		    // Valor Rateio Agua
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(8, imovel.getValorRateioAgua() > 0 ? Util.formatarDoubleParaMoedaReal(imovel.getValorRateioAgua()) + "": ""));
		    // Consumo Rateio Esgoto
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(6, imovel.getConsumoRateioEsgoto() > 0 ? imovel.getConsumoRateioEsgoto() + "" : "") );
		    // Valor Rateio Esgoto
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(8, imovel.getValorRateioEsgoto() > 0 ? Util.formatarDoubleParaMoedaReal(imovel.getValorRateioEsgoto()) + "" : "") );
		    // Tipo de Consumo
    		registrosTipo1.append(Util.adicionarCharEsquerda(2, consumoEsgoto != null ? consumoEsgoto.getTipoConsumo() + "": "", ' ') );
		    // Anormalidade de Consumo
    		registrosTipo1.append(Util.adicionarCharEsquerda(2, consumoEsgoto != null ? consumoEsgoto.getAnormalidadeConsumo() + "": "", ' ') );
		    // Indicador de emissao de conta
    		registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(1, imovel.getIndcImovelImpresso() + "") );
		    // Inscricao do Imovel
    		registrosTipo1.append(imovel.getInscricao() );
    		// Indicador Conta Retida
    		registrosTipo1.append(imovel.getIndcGeracao() );
		    // Consumo Imóveis MICRO Sem Rateio
    		registrosTipo1.append(Util.adicionarCharEsquerda(6, consumoEsgoto != null ? consumoEsgoto.getConsumoCobradoMesImoveisMicro() + "": "", ' ') );
		    // Anormalidade de faturamento
    		registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(2, consumoEsgoto != null ? consumoEsgoto.getAnormalidadeLeituraFaturada() + "": ""));
    		// ID do documento de cobrança
		    registrosTipo1.append(Util.adicionarCharEsquerda(9,imovel.getNumeroDocumentoNotificacaoDebito(), ' '));
		    // Leitura Anterior do Hidrômetro
		    registrosTipo1.append(Util.adicionarCharEsquerda(7, (reg8Poco != null ? reg8Poco.getLeituraAnterior() + "" : ""), ' '));
		    //latitude
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(20, imovel.getLatitude() != Constantes.NULO_DOUBLE ? imovel.getLatitude()+"" : "") );
		    //longitude
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(20, imovel.getLongitude() != Constantes.NULO_DOUBLE ? imovel.getLongitude()+"" : "") );
		    // Versao do I.S. em uso
		    registrosTipo1.append(Util.adicionarCharEsquerda(12, Fachada.getInstancia().getVersao(), ' '));
    		registrosTipo1.append("\n");
	
		    System.out.println(registrosTipo1);
		}
	
		// Caso nao tenha consumo nem de agua nem de esgoto
		// geramos um registro tipo 1 apenas com os débitos.
		if (!temAgua && !temEsgoto) {
		    // Tipo de Registro
		    registrosTipo1.append("1");
		    // Matricula
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula() + ""));
		    // Tipo de Medição
		    registrosTipo1.append("1" );
		    // Ano/Mês do faturamento
	    	registrosTipo1.append(Util.getMesAno(ImovelReg11.getInstancia().getRegistro11().getAnoMesFaturamento()));
		    // Número da Conta
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getNumeroConta() + ""));
		    // Grupo de faturamento
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(3, imovel.getGrupoFaturamento() + "") );
		    // Código da rota
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(7, imovel.getCodigoRota() + ""));
		    // Leitura do Hidrômetro
		    registrosTipo1.append(Util.adicionarCharEsquerda(7, (reg8Agua != null ? reg8Agua.getLeitura() + "" : ""), ' ') );
		    // Anormalidade de Leitura
		    registrosTipo1.append(
			    Util.adicionarCharEsquerda(
				    2, ( imovel.getAnormalidadeSemHidrometro() != Constantes.NULO_INT ? imovel.getAnormalidadeSemHidrometro() + "" : (reg8Agua != null ? reg8Agua.getAnormalidade() + "" : "")), ' ') );
		    // Data e hora da leitura
		    registrosTipo1.append(Util.adicionarCharEsquerda(26, Util.formatarData(new Date()), ' ') );
		    // Indicador de situação da leitura
		    registrosTipo1.append(" " );
		    // Leitura de faturamento
		    registrosTipo1.append(Util.adicionarCharEsquerda(7, (reg8Agua != null ? reg8Agua.getLeitura() + "" : ""), ' '));
		    // Consumo Medido no Mes
		    registrosTipo1.append(Util.adicionarCharEsquerda(6, " ", ' ') );
		    // Consumo Cobrado no Mes
		    registrosTipo1.append(Util.adicionarCharEsquerda(6, " ", ' ') );
		    // Consumo Rateio Agua
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(6, "0") );
		    // Valor Rateio Agua
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(8, "0") );
		    // Consumo Rateio Esgoto
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(6, "0") );
		    // Valor Rateio Esgoto
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(8, "0") );
		    // Tipo de Consumo
		    registrosTipo1.append(Util.adicionarCharEsquerda(2, " ", ' '));
		    // Anormalidade de Consumo
		    registrosTipo1.append(Util.adicionarCharEsquerda(2, " ", ' '));
		    // Indicador de emissao de conta
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(1, imovel.getIndcImovelImpresso() + ""));
		    // Inscricao do Imovel
		    registrosTipo1.append(imovel.getInscricao());
		    // Indicador Conta Retida
		    registrosTipo1.append(imovel.getIndcGeracao());
		    // Consumo Imóveis MICRO Sem Rateio
		    registrosTipo1.append(Util.adicionarCharEsquerda(6, "", ' '));
		    // Anormalidade de faturamento
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(2, ""));
		    // ID do documento de cobrança
		    registrosTipo1.append(Util.adicionarCharEsquerda(9,imovel.getNumeroDocumentoNotificacaoDebito(), ' '));
		    // Leitura Anterior do Hidrômetro
		    registrosTipo1.append(Util.adicionarCharEsquerda(7, (reg8Agua != null ? reg8Agua.getLeituraAnterior() + "" : ""), ' '));
		    //latitude
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(20, imovel.getLatitude() != Constantes.NULO_DOUBLE ? imovel.getLatitude()+"" : "") );
		    //longitude
		    registrosTipo1.append(Util.adicionarZerosEsquerdaNumero(20, imovel.getLongitude() != Constantes.NULO_DOUBLE ? imovel.getLongitude()+"" : "") );
		    // Versao do I.S. em uso
		    registrosTipo1.append(Util.adicionarCharEsquerda(12, Fachada.getInstancia().getVersao(), ' '));
		    registrosTipo1.append("\n");
		
		    System.out.println(registrosTipo1);
		
		}
    }
    }

    private void gerarRegistroTipo2e3(ImovelConta imovel) {

    	registrosTipo2e3 = new StringBuffer();
    	
    	Vector registrosTipo2 = imovel.getRegistros2();
	
    	if (registrosTipo2 != null) {
	
    	    for (int i = 0; i < registrosTipo2.size(); i++) {
	
	    		ImovelReg2 registroTipo2 = (ImovelReg2) registrosTipo2.elementAt(i);
		
	    		DadosFaturamento faturamentoAgua = new DadosFaturamento();
		
	    		if (registroTipo2.getFaturamentoAgua() != null) {
	    		    faturamentoAgua = registroTipo2.getFaturamentoAgua();
	    		}
		
	    		DadosFaturamento faturamentoEsgoto = new DadosFaturamento();
		
	    		if (registroTipo2.getFaturamentoEsgoto() != null) {
	    		    faturamentoEsgoto = registroTipo2.getFaturamentoEsgoto();
	    		}
		
	    		// Tipo de registro (1)
	    		registrosTipo2e3.append("2");
	    		// Matrícula (9);
	    		registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula() + ""));
	    		// Código da categoria (1)
	    		registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(1, registroTipo2.getCodigoCategoria() + ""));
	    		// Código da subcategoria (3)    		
	    		short indicadorTarifa = ImovelReg11.getInstancia().getIndcTarifaCatgoria();
	    		String codigoSubcategoria = "";
	    		if (indicadorTarifa == ImovelReg11.CALCULO_POR_CATEGORA) {
	    		    codigoSubcategoria = Util.adicionarCharEsquerda(3, "0", ' ');
	    		} else {
	    		    codigoSubcategoria = Util.adicionarCharEsquerda(3, registroTipo2.getCodigoSubcategoria(), ' ');
	    		}
				
	    		registrosTipo2e3.append(codigoSubcategoria);
	    		// Valor faturado de água na categoria (13,2)
	    		registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faturamentoAgua.getValorFaturado())));
	    		// Consumo faturado de água na categoria (6);
	    		registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(6, faturamentoAgua.getConsumoFaturado() + ""));
	    		// Valor da tarifa mínima de água da categoria (13,2);
	    		registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faturamentoAgua.getValorTarifaMinima())));
	    		// Consumo mínimo de água da categoria (6);
	    		registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(6, faturamentoAgua.getConsumoMinimo() + ""));
	    		// Valor faturado de esgoto na categoria (13,2)
	    		registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faturamentoEsgoto.getValorFaturado())));
	    		// Consumo faturado de esgoto na categoria (6);
	    		registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(6, faturamentoEsgoto.getConsumoFaturado() + ""));
	    		// Valor da tarifa mínima de esgoto da categoria (13,2);
	    		registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faturamentoEsgoto.getValorTarifaMinima())));
	    		// Consumo mínimo de esgoto da categoria (6);
	    		registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(6, faturamentoEsgoto.getConsumoMinimo() + ""));
    			// Numero de vezes que a conta foi impressa
    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(2, String.valueOf(imovel.getQuantidadeContasImpressas())));
    			// Valores das contas impressas
//    			for (int count = 0; count < imovel.getValoresContasImpressas().size(); count++){
//	    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(10, Util.formatarDoubleParaMoedaReal(Double.parseDouble((String)imovel.getValoresContasImpressas().elementAt(count)))));
//    			}
	    		registrosTipo2e3.append("\n");
		
	    		Vector faixasAgua = faturamentoAgua.getFaixas();
	    		Vector faixasEsgoto = faturamentoEsgoto.getFaixas();
		
	    		if (faixasAgua != null) {
	    		    for (int j = 0; j < faixasAgua.size(); j++) {
		    			DadosFaturamentoFaixa faixaAgua = (DadosFaturamentoFaixa) faixasAgua.elementAt(j);
			
		    			DadosFaturamentoFaixa faixaEsgoto = new DadosFaturamentoFaixa();
			
		    			if (faixasEsgoto != null && !faixasEsgoto.isEmpty()) {
		    			    
		    			    for (int k = 0; k < faixasEsgoto.size(); k++) {
			
			    				faixaEsgoto = (DadosFaturamentoFaixa) faixasEsgoto.elementAt(k);
				
			    				if (faixaAgua.equals(faixaEsgoto)) {
			    				    break;
			    				}
		    			    }
		    			}
			
		    			// Tipo de registro (1)
		    			registrosTipo2e3.append("3");
		    			// Matrícula (9);
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula() + ""));
		    			// Código da categoria (1)
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(1, registroTipo2.getCodigoCategoria() + ""));
		    			// Código da subcategoria (3)
		    			registrosTipo2e3.append(Util.adicionarCharEsquerda(3, registroTipo2.getCodigoSubcategoria(), ' '));
		    			// Consumo faturado de água na faixa (6)
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(6, faixaAgua.getConsumoFaturado() + ""));
		    			// Valor faturado de água na faixa (13,2)
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faixaAgua.getValorFaturado())));
		    			// Limite inicial de consumo na faixa (6);
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(6, faixaAgua.getLimiteInicialConsumo() + ""));
		    			// Limite final de consumo na faixa (6);
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(6, faixaAgua.getLimiteFinalConsumo() + ""));
		    			// Valor da tarifa de água na faixa (13,2);
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faixaAgua.getValorTarifa())));
		    			// Valor da tarifa de esgoto na faixa (13,2);
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faixaEsgoto.getValorTarifa())));
		    			// Consumo Faturado de esgoto na faixa (6)
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(6, faixaEsgoto.getConsumoFaturado() + ""));
		    			// Valor faturado de esgoto na faixa (13,2)
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faixaEsgoto.getValorFaturado())));
		    			registrosTipo2e3.append("\n");
	    		    }
	    		} else if (faixasEsgoto != null) {
		
	    		    DadosFaturamentoFaixa faixaEsgoto = new DadosFaturamentoFaixa();
		
	    		    for (int k = 0; k < faixasEsgoto.size(); k++) {
		
		    			faixaEsgoto = (DadosFaturamentoFaixa) faixasEsgoto.elementAt(k);
			
		    			// Tipo de registro (1)
		    			registrosTipo2e3.append("3");
		    			// Matrícula (9);
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula() + ""));
		    			// Código da categoria (1)
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(1, registroTipo2.getCodigoCategoria() + ""));
		    			// Código da subcategoria (3)
		    			registrosTipo2e3.append(Util.adicionarCharEsquerda(3, registroTipo2.getCodigoSubcategoria(), ' '));
		    			// Consumo faturado de água na faixa (6)
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(6, ""));
		    			// Valor faturado de água na faixa (13,2)
		    			registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(16, ""));
		    			// Limite inicial de consumo na faixa (6);
						registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(6, faixaEsgoto.getLimiteInicialConsumo() + ""));
						// Limite final de consumo na faixa (6);
						registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(6, faixaEsgoto.getLimiteFinalConsumo() + ""));
						// Valor da tarifa de água na faixa (13,2);
						registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(16, ""));
						// Valor da tarifa de esgoto na faixa (13,2);
						registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faixaEsgoto.getValorTarifa())));
						// Consumo Faturado de esgoto na faixa (6)
						registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(6, faixaEsgoto.getConsumoFaturado() + ""));
						// Valor faturado de esgoto na faixa (13,2)
						registrosTipo2e3.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faixaEsgoto.getValorFaturado())));
						registrosTipo2e3.append("\n");
	    		    }
	    		}
    	    }
    	}
    }

    private void gerarRegistroTipo4(ImovelConta imovel) {

    	registrosTipo4 = new StringBuffer();

    	Vector regs6 = imovel.getRegistros6();
	
    	if (regs6 != null) {
    	    for (int i = 0; i < regs6.size(); i++) {
    		ImovelReg6 reg6 = (ImovelReg6) regs6.elementAt(i);
		
    		// Tipo de registro (1)
    		registrosTipo4.append("4");
				
    		// Matricula (9)
    		registrosTipo4.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula() + ""));
    		
    		// Tipo de Imposto(1)
    		registrosTipo4.append(Util.adicionarZerosEsquerdaNumero(1, reg6.getTipoImposto() + ""));
    		// Descrição do imposto (15);
    		registrosTipo4.append(Util.adicionarCharEsquerda(15, reg6.getDescricaoImposto(), ' '));
    		// Percentual da Alíquota (6);
    		registrosTipo4.append(Util.adicionarZerosEsquerdaNumero(6, Util.formatarDoubleParaMoedaReal(reg6.getPercentualAlicota())));
    		// Valor do Imposto (13,2)
    		double valorImposto = imovel.getValorContaSemImposto() * Util.arredondar((reg6.getPercentualAlicota() / 100), 7);
    		registrosTipo4.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(valorImposto)) );
    		registrosTipo4.append("\n");
    	    }
    	}
    }
    
    private void gerarRegistroTipo0(int tipoFinalizacao, ImovelConta imovel) {

    	registroTipo0 = new StringBuffer();
		
    	// Tipo de registro (1)
    	registroTipo0.append("0");				
    	// Tipo de finalização (1)
    	registroTipo0.append(tipoFinalizacao + "");    	
    	// Localidade(3)
    	registroTipo0.append(Util.adicionarZerosEsquerdaNumero(3, imovel.getLocalidade() + ""));    	
    	//Setor Comercial(3)
    	registroTipo0.append(Util.adicionarZerosEsquerdaNumero(3, imovel.getSetorComercial() + ""));				
    	// Rota (3);
    	registroTipo0.append(Util.adicionarZerosEsquerdaNumero(7, imovel.getCodigoRota() + ""));
// Daniel - Id da Rota   	
    	if (ImovelReg11.getInstancia().getIdRota() != 9999){
        	registroTipo0.append(Util.adicionarZerosEsquerdaNumero(4, ImovelReg11.getInstancia().getIdRota() + ""));		
    	}

// Daniel - Indicador de rota dividida    	
    	if (ImovelReg11.getInstancia().getIndicadorRotaDividida() != 999999){
        	registroTipo0.append(Util.adicionarZerosEsquerdaNumero(2, ImovelReg11.getInstancia().getIndicadorRotaDividida() + ""));		
    	}else{
        	registroTipo0.append(Util.adicionarZerosEsquerdaNumero(2, "00"));		
    	}
    	
    	registroTipo0.append("\n");				
    }

    /**
     * Métodos gera o arquivo de retorno de todos os imóveis não 
     * enviados até o momento;
     * 
     * @param p
     *            Progress bar que irá mostrar o progresso do processo
     * @return Vetor de objetos com dois objetos 1 - boolean com indicação de
     *         erro na geração 2 - todas os id's dos imóveis que foram gerados
     *         para envio
     */
    public Object[] gerarArquivoRetorno( Progress p, short tipoArquivoRetorno ){

	Object[] retorno = new Object[2];
	Vector idsImoveisGerados = new Vector();

	arquivo = new StringBuffer();

	FileConnection fc = null;
	OutputStream out = null;

	int qtdImoveisCalculados = 0;
	
	// Sequencial de rota de marcacão
	StringBuffer sequencialRotaMarcacao = new StringBuffer();
	
	if ( tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_COMPLETO || 
	     tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS ){	
	    qtdImoveisCalculados = Configuracao.getInstancia().getQtdImoveis();
	} else if ( tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_CONCLUIDOS_ATE_AGORA || 
		    tipoArquivoRetorno == ARQUIVO_INCOMPLETO ) {
// Daniel - considerar numero de imoveis impressos
		qtdImoveisCalculados = Configuracao.getInstancia().getIdsImoveisConcluidos().size();
	}

	System.out.println("Quantidade de imoveis na lista para enviar: " + qtdImoveisCalculados);
	try {

	    FileManager.abrir(FileManager.INPUT_FILE_PATH + "Retorno/", true, FileManager.CRIAR_SE_NAO_EXISTIR);

	    /*
	     * concatena o nome do arquivo com : G - letra "G" fixo FFF -
	     * ftgr_id id do grupo de faturamento LLL - loca_id do 1º imóvel SSS
	     * - stcm_id dp 1º imóvel RRRR - rota_cdrota AAAAMM - ano/mês
	     * referencia do faturamento
	     */
// Daniel - sempre apagar o anterior e criar um novo.		
	    FileManager.abrir(getCaminhoArquivoRetorno( tipoArquivoRetorno ), false, FileManager.APAGAR);
	    fc = FileManager.abrir(getCaminhoArquivoRetorno( tipoArquivoRetorno ), false, FileManager.CRIAR_SE_NAO_EXISTIR);
	    out = fc.openOutputStream();
	    
	    ImovelConta primeiroImovel = (ImovelConta) Repositorio.pesquisarPrimeiroObjeto(ImovelConta.class);
	    
	    // Criamos o registro tipo 0
	    if ( tipoArquivoRetorno == ARQUIVO_COMPLETO ){			    
	    	this.gerarRegistroTipo0( Constantes.INDC_FINALIZAR_ROTEIRO , primeiroImovel);		
	    } else if ( tipoArquivoRetorno == ARQUIVO_TODOS_OS_CALCULADOS ){
	    	this.gerarRegistroTipo0( Constantes.INDC_FINALIZAR_ROTEIRO_TODOS_IMOVEIS , primeiroImovel );
	    } else if ( tipoArquivoRetorno == ARQUIVO_INCOMPLETO ){
			this.gerarRegistroTipo0( Constantes.INDC_FINALIZAR_ROTEIRO_INCOMPLETO , primeiroImovel );
	    }
	    
	    if ( registroTipo0 != null ){
		arquivo = arquivo.append(registroTipo0);
	    }

	    for (int i = 0; i < qtdImoveisCalculados; i++) {
		int id = 0;
		
		if ( tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_COMPLETO ||
		     tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS ){
		    id = i + 1;
		} else if ( tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_CONCLUIDOS_ATE_AGORA || 
			    tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_INCOMPLETO ) {
		    id = ( (Integer) Configuracao.getInstancia().getIdsImoveisConcluidos().elementAt( i ) ).intValue(); 
		}

		// Informamos quanto ainda falta
		double d = (double) (i+1) / qtdImoveisCalculados;
		byte percentual = (byte) ((d) * 100);

		p.setProgress(percentual);
		p.repaint();
		
		ImovelConta imovel = new ImovelConta();
		Repositorio.carregarObjeto(imovel, id);
		
//		ControladorImoveis.getInstancia().setImovelSelecionado(imovel);
//		imovel = null;
//Daniel - Rota completa - checa se o imovel selecionado é informativo ou nao.
//		Caso seja informativo, nao deve entrar no arquivo de retorno.
		// Guardamos todos os sequencias e matriculas, para a rota de marcação
		if (!imovel.isImovelInformativo()  || imovel.isImovelCondominio()){
			System.out.println("Incluindo Imovel: " + imovel.getMatricula());
			if ( ( tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_COMPLETO ||
			       tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS ) && 
			       Configuracao.getInstancia().getIndcRotaMarcacaoAtivada() == Constantes.SIM ) {
			    
			    if ( imovel.getSequencialRotaMarcacao() == Constantes.NULO_INT ){
				
				int indicador = Configuracao.getInstancia().getSequencialAtualRotaMarcacao();
				
				sequencialRotaMarcacao.append( 
					"5" +
					Util.adicionarZerosEsquerdaNumero( 9, imovel.getMatricula()+"" ) + 
					Util.adicionarZerosEsquerdaNumero( 4, indicador+"" ) + "\n" );
				
				imovel.setSequencialRotaMarcacao( indicador );
			    } else {
				sequencialRotaMarcacao.append( 
					"5" +
					Util.adicionarZerosEsquerdaNumero( 9, imovel.getMatricula()+"" ) + 
					Util.adicionarZerosEsquerdaNumero( 4, imovel.getSequencialRotaMarcacao()+"" ) + "\n" );
			    }
			}
	
			 /*Caso estejamos enviando apenas os lidos e não enviados até agora
			 verificamos se o imovel deve ser enviado imediatamente. Como imóveis
			 condominio podem ser impressos parcialmente, como no caso de conta 
			 menor do que o valor limite ou valor do crédito maior do que o valor
			 da conta, não enviamos imóvel condominio para o arquivo de não enviados
			 e lidos até agora. Esse procedimento só deve ser feito na finalização
			 do roteiro. Caso estejamos enviando o imóvel completo, enviamos todos 
			 os calculados e não enviados até agora;*/
			   // Condição 1 
			if ( ( tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_CONCLUIDOS_ATE_AGORA && 
					imovel.enviarAoImprimir() && !imovel.isImovelCondominio() ) ||
			   // Condição 2
			     ( (tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_COMPLETO ||
			        tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_INCOMPLETO	) &&
			        imovel.enviarAoFinalizar() ) ||
			   // Condicao 3
			     ( tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS &&
			    		 imovel.getIndcImovelCalculado() == Constantes.SIM ) ) {

				System.out.println("vai enviar o movel: " + imovel.getMatricula());

			    	// Criamos os registros tipo 1
			    	this.gerarRegistroTipo1(imovel);
	
			    	// Criamos os registros tipo 2 e 3
			    	this.gerarRegistroTipo2e3(imovel);
	
			    	// Criamos os registros tipo 4
			    	this.gerarRegistroTipo4(imovel);
	 	
			    	arquivo = arquivo.append(registrosTipo1);
			    	arquivo = arquivo.append(registrosTipo2e3);
			    	arquivo = arquivo.append(registrosTipo4);
	
					linhaTipo1Anexada = false;

			    	idsImoveisGerados.addElement(new Integer(imovel.getId()));
	
			    	imovel = null;
			} else if ( tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS ){
			    arquivo = null;
			    Util.mostrarErro( "O imovel de matricula " + imovel.getMatricula() + " nao esta calculado. Favor recalcular" );
			    out.close();
			    fc.close();
			    out = null;
			    fc = null;
			    
			    retorno[0] = Boolean.TRUE;
			    retorno[1] = null;
			    
			    ControladorImoveis.getInstancia().setImovelSelecionado( imovel );
			    Abas.getInstancia().criarAbas();
			}
	    }else{
			System.out.println("Excluindo Imovel: " + imovel.getMatricula());

	    }
	    }
	    
	    if ( tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_COMPLETO || 
	    		tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS ){
	    	
	    	arquivo.append( sequencialRotaMarcacao.toString() );
	    }
	    
	    out.write(arquivo.toString().getBytes());
//	    arquivo = null;

	    out.close();
	    fc.close();
	    out = null;
	    fc = null;

	    Configuracao.getInstancia().setNomeArquivoRetorno(getCaminhoArquivoRetorno( tipoArquivoRetorno ));
	    Repositorio.salvarObjeto(Configuracao.getInstancia());

	    retorno[0] = Boolean.FALSE;
	    retorno[1] = idsImoveisGerados;
	} catch (IOException e) {
	    e.printStackTrace();
	    Util.mostrarErro("Erro na criação do arquivo de retorno", e);
	    retorno[0] = Boolean.TRUE;
	}
	return retorno;
    }

    public RegistroArquivoRetorno imovelJaIncluido(ImovelConta imovel) {
	for (int i = 0; i < this.registrosArquivoRetorno.size(); i++) {
	    RegistroArquivoRetorno registro = 
		(RegistroArquivoRetorno) this.registrosArquivoRetorno.elementAt(i);

	    if (imovel.getMatricula() == registro.getMatriculaImovel()) {
		return registro;
	    }
	}

	return null;
    }

    public void adicionarImovel(ImovelConta imovel) {
	RegistroArquivoRetorno registro = this.imovelJaIncluido(imovel);

	if (registro != null) {
	    if (registro.registroAlterado(imovel)) {
		registro.alterarRegistro(imovel);
	    }
	} else {
	    registro = new RegistroArquivoRetorno(imovel);
	}

	Repositorio.salvarObjeto(registro);
    }

    public StringBuffer gerarArquivoRetornoOnLine(ImovelConta imovel) {
	
	StringBuffer arquivo = new StringBuffer();

	// Criamos os registros tipo 1
	this.gerarRegistroTipo1(imovel);
	// Criamos os registros tipo 2 e 3
	this.gerarRegistroTipo2e3(imovel);
	// Criamos os registros tipo 4
	this.gerarRegistroTipo4(imovel);
	arquivo = arquivo.append(registrosTipo1);
	arquivo = arquivo.append(registrosTipo2e3);
	arquivo = arquivo.append(registrosTipo4);

	linhaTipo1Anexada = false;

	return arquivo;
    }
    
    public static String getConteudoArquivoRetorno(){
    	if (arquivo != null){
    	   	return arquivo.toString();
    	}else{
    		return null;
    	}
 	}
    public static void cleanConteudoArquivoRetorno(){
    	arquivo = null;
	}

}
