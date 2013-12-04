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

package com.ipad.util;

import java.util.Calendar;
import java.util.Vector;

import com.ipad.basic.Consumo;
import com.ipad.basic.DadosFaturamentoFaixa;
import com.ipad.basic.ImovelConta;
import com.ipad.basic.ImovelReg11;
import com.ipad.basic.ImovelReg2;
import com.ipad.basic.ImovelReg3;
import com.ipad.basic.ImovelReg6;
import com.ipad.basic.ImovelReg7;
import com.ipad.basic.ImovelReg8;
import com.ipad.basic.RegistroBasico;
import com.ipad.basic.helper.EfetuarRateioConsumoDispositivoMovelHelper;
import com.ipad.business.ControladorConta;
import com.ipad.business.ControladorImoveis;
import com.ipad.facade.Fachada;
import com.ipad.io.FileManager;

/**
 * Classe que irá gerar a string de retorno para impressão da conta ou da
 * notificação de débito
 * 
 * @author Bruno Barros
 * @date 04/08/2009
 */
public class ImpressaoContaCosanpa {

    private static ImovelConta imovel;
    public static final int IMPRIMIR_CONTA = 1;
    public static final int IMPRIMIR_NOTIFICACAO_DEBITO = 2;
    private static ImpressaoContaCosanpa instancia;

    private ImpressaoContaCosanpa() {
	super();
    }

    protected static ImpressaoContaCosanpa getInstancia(ImovelConta imovelInformado) {
		if (instancia == null) {
		    instancia = new ImpressaoContaCosanpa();
		}
		imovel = imovelInformado;
		return instancia;
    }

    public String imprimirConta() {
	String retorno = "";
	try {
	    retorno = "! 0 200 200 1720 1\n";
	    // Grade da conta
	    retorno +=
		// Caixa e linhas das leituras atual\anterior\consumo\num. de dias
		"BOX 32 435 802 482 1\n" + 
		"LINE 720 415 720 455 1\n" + 
		"LINE 403 415 403 477 1\n" + 
		"BOX 32 411 802 435 1\n" + 
		"LINE 278 415 278 477 1\n" + 
		"BOX 283 518 802 545 1\n" + 
		"BOX 283 545 802 692 1\n" + 
		"LINE 656 518 656 692 1\n" + 
		"LINE 425 518 425 692 1\n" + 
		"LINE 535 518 535 692 1\n";
	    
//Daniel - incluindo data de impressao na conta
	    //Versao do Leitura e Impressao Simultanea e
	    //Data e hora de impressão
	    retorno +=
	    "T 0 2 135 121 " + "Versao: " + Fachada.getInstancia().getVersao() + " - "+ Util.getDataHora() + " /" + (imovel.getQuantidadeContasImpressas()+1) + "\n";
	    // Matricula do imovel
	    retorno +=
	    // "T 0 0 341 80 Matricula:\n" +
	    "T 7 1 464 90 " + imovel.getMatricula() + "\n";
	    // Ano mes de referencia da conta
	    retorno +=
	    // "T 0 0 596 80 Referencia:\n" +
	    "T 7 1 669 90 " + Util.retornaDescricaoAnoMes(imovel.getAnoMesConta()) + "\n";
	    // Cnpj da empresa
	    retorno +=
	    // "T 7 0 46 186 CNPJ:\n" +
	    "T 0 0 201 47 " + Util.formatarCnpj(ImovelReg11.getInstancia().getRegistro11().getCnpjEmpresa()).trim() + "\n";
	    // Inscrição estadual
	    retorno +=
	    // "T 7 0 46 217 INSC. ESTADUAL:\n" +
	    "T 0 0 285 64 " + ImovelReg11.getInstancia().getRegistro11().getInscricaoEstadualEmpresa().trim() + "\n";
	    // Grupo
	    retorno += formarLinha(0, 0, 222, 81, imovel.getGrupoFaturamento() + "", 0, 0);
	  retorno +=
	    formarLinha(0, 0, 140, 108, (imovel.getEnderecoAtendimento() != null && !imovel.getEnderecoAtendimento().equals("") ? imovel.getEnderecoAtendimento() + " - " : "")
		    + (imovel.getTelefoneLocalidadeDDD() != null && !imovel.getTelefoneLocalidadeDDD().equals("") ? imovel.getTelefoneLocalidadeDDD().trim() : ""), 0, 0);
	    String cpfCnpjFormatado = "";
	    if (imovel.getCpfCnpjCliente() != null && !imovel.getCpfCnpjCliente().equals("")) {
		cpfCnpjFormatado = imovel.getCpfCnpjCliente().trim();
	    }
	    // Dados do cliente
	    if (imovel.getEnderecoEntrega().trim().length() == 0){
		    retorno +=
	    	formarLinha(0, 2, 52, 172, imovel.getNomeUsuario().trim(), 0, 0) + formarLinha(0, 2, 52, 199, cpfCnpjFormatado, 0, 0) + dividirLinha(0, 2, 434, 169, imovel.getEndereco(), 40, 27);
	    }else{
		    retorno +=
	    	formarLinha(0, 2, 52, 172, imovel.getNomeUsuario().trim(), 0, 0) + formarLinha(0, 2, 52, 199, cpfCnpjFormatado, 0, 0) + dividirLinha(0, 2, 434, 169, imovel.getEnderecoEntrega(), 40, 27);
	    }
	    // Inscricao
	    retorno += formarLinha(7, 0, 15, 250, imovel.getInscricaoFormatada(), 0, 0);
	    // Codigo da Rota
	    retorno += formarLinha(7, 0, 315, 250, imovel.getCodigoRota() + "", 0, 0);
	    // Sequencial da Rota
	    retorno += formarLinha(7, 0, 415, 250, imovel.getSequencialRota() + "", 0, 0);
	    // Verificamos é por categoria ou subcategoria
	    Vector regsTipo2 = imovel.getRegistros2();
	    Vector quantidadeEconomias = categoriasEconomias(regsTipo2);
	    
	    for (int i = 0; i < quantidadeEconomias.size(); i++) {
			Object[] dadosCategoria = (Object[]) quantidadeEconomias.elementAt(i);
			retorno += formarLinha(0, 0, 470, 254, dadosCategoria[0] + "", i * 85, 0);
			retorno += formarLinha(7, 0, 539, 250, dadosCategoria[1] + "", i * 85, 0);
	    }
	    
	    // Numero do Hidrometro
	    String hidrometro = "NÃO MEDIDO";
	    String dataInstacao = Constantes.NULO_STRING;
	    String situacaoAgua = imovel.getDescricaoSitLigacaoAgua(imovel.getSituacaoLigAgua());
	    String situacaoEsgoto = imovel.getDescricaoSitLigacaoEsgoto(imovel.getSituacaoLigEsgoto());
	    String leituraAnteriorInformada = Constantes.NULO_STRING;
	    String leituraAtualInformada = Constantes.NULO_STRING;
	    String leituraAnteriorFaturada = Constantes.NULO_STRING;
	    String leituraAtualFaturada = Constantes.NULO_STRING;
	    String consumo = Constantes.NULO_STRING;
	    String diasConsumo = Constantes.NULO_STRING;
	    Consumo consumoAgua = null;
	    Consumo consumoEsgoto = null;
	    ImovelReg8 registro8Agua = imovel.getRegistro8(Constantes.LIGACAO_AGUA);
	    ImovelReg8 registro8Poco = imovel.getRegistro8(Constantes.LIGACAO_POCO);
	    String dataLeituraAnteriorInformada = "";
	    String dataLeituraAtualInformada = "";
	    String dataLeituraAnteriorFaturada = "";
	    String dataLeituraAtualFaturada = "";
	    consumoAgua = imovel.getConsumoAgua();
	    consumoEsgoto = imovel.getConsumoEsgoto();
	    String media = "0";
	    int tipoConsumo = 0;
	    
	    if (registro8Agua != null) {

			hidrometro = registro8Agua.getNumeroHidrometro();
			media = String.valueOf(registro8Agua.getConsumoMedio());
	
			if (registro8Agua.getLeituraAnteriorInformada() != Constantes.NULO_INT){
				leituraAnteriorInformada = registro8Agua.getLeituraAnteriorInformada() + "";
			}
			dataLeituraAnteriorFaturada = Util.dateToString(registro8Agua.getDataLeituraAnteriorFaturada());
			dataLeituraAnteriorInformada = Util.dateToString(registro8Agua.getDataLeituraAnteriorInformada());
			dataLeituraAtualInformada = Util.dateToString(registro8Agua.getDataLeitura());
			dataInstacao = Util.dateToString(registro8Agua.getDataInstalacao());
	
			if (consumoAgua != null) {
	
				tipoConsumo = consumoAgua.getTipoConsumo();
				
				if(registro8Agua.getLeituraAnteriorFaturamento() != Constantes.NULO_INT){
					leituraAnteriorFaturada = String.valueOf(registro8Agua.getLeituraAnteriorFaturamento());
				}
				
			    if (registro8Agua.getLeituraAtualFaturamento() != Constantes.NULO_INT) {
	
			    	leituraAtualFaturada = String.valueOf(registro8Agua.getLeituraAtualFaturamento());
			    	dataLeituraAtualFaturada = Util.dateToString(registro8Agua.getDataLeituraAtualFaturamento());
			    	
//				    if (imovel.getIdImovelCondominio() != Constantes.NULO_INT ) {
//				    	consumo = (consumoAgua.getConsumoCobradoMes() - consumoAgua.getConsumoRateio()) + "";
//
//				    }else{
				    	consumo = consumoAgua.getConsumoCobradoMes() + "";				    	
				    
//				    }
				    
					diasConsumo = Long.toString(registro8Agua.getQtdDiasAjustado());
	
			    } else {
	
			    	leituraAtualFaturada = "";
			    	consumo = consumoAgua.getConsumoCobradoMes() + "";
					diasConsumo = consumoAgua.getDiasConsumo() + "";
			    }
	
				if (registro8Agua.getLeitura()!= Constantes.NULO_INT &&
						consumoAgua.getLeituraAtual() != Constantes.NULO_INT) {
				    
					leituraAtualInformada = registro8Agua.getLeitura() + "";
				} else {
				    leituraAtualInformada = "";
				    dataLeituraAtualInformada = "";
				}
			}

	    } else if (registro8Poco != null) {

			media = String.valueOf(registro8Poco.getConsumoMedio());
			hidrometro = registro8Poco.getNumeroHidrometro();
	
			if (registro8Poco.getLeituraAnteriorInformada() != Constantes.NULO_INT){
				leituraAnteriorInformada = registro8Poco.getLeituraAnteriorInformada()+ "";
			}
			dataLeituraAnteriorFaturada = Util.dateToString(registro8Poco.getDataLeituraAnteriorFaturada());
			dataLeituraAnteriorInformada = Util.dateToString(registro8Poco.getDataLeituraAnteriorInformada());
			dataLeituraAtualInformada = Util.dateToString(registro8Poco.getDataLeitura());
			dataInstacao = Util.dateToString(registro8Poco.getDataInstalacao());
	
			if (consumoEsgoto != null) {
	
				tipoConsumo = consumoEsgoto.getTipoConsumo();
	
				if(registro8Poco.getLeituraAnteriorFaturamento() != Constantes.NULO_INT){
					leituraAnteriorFaturada = String.valueOf(registro8Poco.getLeituraAnteriorFaturamento());
				}

				if (registro8Poco.getLeituraAtualFaturamento() != Constantes.NULO_INT) {
	
			    	leituraAtualFaturada = String.valueOf(registro8Poco.getLeituraAtualFaturamento());
			    	dataLeituraAtualFaturada = Util.dateToString(registro8Poco.getDataLeituraAtualFaturamento());

//				    if (imovel.getIdImovelCondominio() != Constantes.NULO_INT ) {
//				    	consumo = (consumoEsgoto.getConsumoCobradoMes() - consumoEsgoto.getConsumoRateio()) + "";
//				    }else{
				    	consumo = consumoEsgoto.getConsumoCobradoMes() + "";				    	
//				    }
					diasConsumo = Long.toString(registro8Poco.getQtdDiasAjustado());
	
			    } else {
	
			    	leituraAtualFaturada = "";
			    	consumo = consumoEsgoto.getConsumoCobradoMes() + "";
					diasConsumo = consumoEsgoto.getDiasConsumo() + "";
			    }
	
				if (registro8Poco.getLeitura()!= Constantes.NULO_INT &&
						consumoEsgoto.getLeituraAtual() != Constantes.NULO_INT) {
	
				    leituraAtualInformada = registro8Poco.getLeitura() + "";
				} else {
				    leituraAtualInformada = "";
				    dataLeituraAtualInformada = "";
				}
	
			}
	    }

	    else if (registro8Agua == null && registro8Poco == null) {

	    	Vector regsTipo3 = imovel.getRegistros3();
		    
	    	if (regsTipo3 != null) {
				int sumConsumo = 0;
				for (int i = 0; i < regsTipo3.size(); i++) {
				    ImovelReg3 reg3 = (ImovelReg3) regsTipo3.elementAt(i);
				    sumConsumo += Integer.parseInt(reg3.getConsumo());
				}
				media = (int) (sumConsumo / regsTipo3.size()) + "";
		    }


		if (consumoAgua != null) {

//		    if (consumoAgua.getLeituraAtual() != Constantes.NULO_INT) {
//		    	leituraAtual = consumoAgua.getLeituraAtual() + "";
//		    } else {
		    	leituraAtualInformada = "";
			    dataLeituraAtualInformada = "";
//		    }

		    consumo = consumoAgua.getConsumoCobradoMes() + "";
		 // Daniel - Numero de dias de consumo nunca deve ser ZERO mesmo para imoveis fixos.
		    diasConsumo =  Long.toString(Util.quantidadeDiasMes(Calendar.getInstance())) + "";
		}
	    }

	    // Hidrometro
	    retorno += formarLinha(7, 0, 48, 301, hidrometro, 0, 0);
	    // Data da Instalação
	    retorno += formarLinha(7, 0, 248, 301, dataInstacao, 0, 0);
	    // Situacao da ligacao de Agua

	    if (situacaoAgua.length() > 13) {
		retorno += formarLinha(7, 0, 446, 301, situacaoAgua.substring(0, 13), 0, 0);
	    } else {
		retorno += formarLinha(7, 0, 446, 301, situacaoAgua, 0, 0);
	    }
	    // Situacao da ligacao de esgoto
	    retorno +=
	    formarLinha(7, 0, 627, 301, situacaoEsgoto, 0, 0);
	    
//	  Daniel - LEITURA INFORMADA
	    // Leitura Anterior e Atual
	    retorno += formarLinha(7, 0, 168, 330, "LEITURA", 0, 0) + formarLinha(7, 0, 190, 354, leituraAnteriorInformada, 0, 0) + formarLinha(7, 0, 190, 378, leituraAtualInformada, 0, 0);
	    // Data Leitura Anterior e Atual
	    retorno += formarLinha(7, 0, 313, 330, "DATA", 0, 0) + formarLinha(7, 0, 285, 354, dataLeituraAnteriorInformada, 0, 0) + formarLinha(7, 0, 285, 378, dataLeituraAtualInformada, 0, 0);

	    retorno += formarLinha(7, 0, 37, 354, "ANTERIOR", 0, 0) + formarLinha(7, 0, 37, 378, "ATUAL", 0, 0);

//Daniel - imprimindo Anormalidade de Leitura quando existe.
		if (imovel.getConsumoAgua() != null){
			if (imovel.getConsumoAgua().getAnormalidadeLeituraFaturada() != 0 &&
					imovel.getConsumoAgua().getAnormalidadeLeituraFaturada() != Constantes.NULO_INT	){

				retorno += formarLinha(0, 2, 460, 374, "ANORM. LEITURA: " + FileManager.getAnormalidade(imovel.getConsumoAgua().getAnormalidadeLeituraFaturada()).getDescricao(), 0, 0);
			}
		}
	    
//	    Daniel - LEITURA FATURADA
		
	    // Leitura Anterior
	    retorno += formarLinha(7, 0, 163, 412, "FATURADO", 0, 0) + formarLinha(7, 0, 190, 436, leituraAnteriorFaturada, 0, 0) + formarLinha(7, 0, 190, 460, leituraAtualFaturada, 0, 0);
	    // Leitura Atual
	    retorno += formarLinha(7, 0, 313, 412, "DATA", 0, 0) + formarLinha(7, 0, 285, 436, dataLeituraAnteriorFaturada, 0, 0) + formarLinha(7, 0, 285, 460, dataLeituraAtualFaturada, 0, 0);
	    // Consumo
//	    if ((imovel.getIndcFaturamentoAgua() == Constantes.SIM || imovel.getIndcFaturamentoEsgoto() == Constantes.SIM) && imovel.getIdImovelCondominio() != Constantes.NULO_INT ) {
		if (imovel.isImovelCondominio()) {

//			if (imovel.getIndcFaturamentoAgua() == Constantes.SIM) {
//			if (imovel.getConsumoAgua() != null) {
//			    consumo += " + (" + imovel.getConsumoAgua().getConsumoRateio() + ")";
////			} else if (imovel.getIndcFaturamentoEsgoto() == Constantes.SIM) {
//			} else if (imovel.getConsumoEsgoto() != null) {
//			    consumo += " + (" + imovel.getConsumoEsgoto().getConsumoRateio() + ")";
//			}
	
//				retorno += formarLinha(7, 0, 414, 412, "CONSUMO + RATEIO (m3)", 0, 0) + formarLinha(7, 0, 511, 436, consumo, 0, 0);
				retorno += formarLinha(7, 0, 418, 412, "CONSUMO (m3)", 0, 0) + formarLinha(7, 0, 511, 436, consumo, 0, 0);
	    
	    } else {
	    	retorno +=	formarLinha(7, 0, 412, 412, ControladorConta.getInstancia().getTipoConsumoToPrint(tipoConsumo), 0, 0);
	    	retorno +=	formarLinha(7, 0, 511, 436, consumo, 0, 0);
	    }
	    // Numero de dias
	    retorno += formarLinha(7, 0, 745, 412, "DIAS", 0, 0) + formarLinha(7, 0, 760, 436, diasConsumo, 0, 0);

//Daniel - imprimindo Anormalidade de Consumo quando existe.
		if (imovel.getConsumoAgua() != null){
			String anormalidadeConsumo = Util.validarAnormalidadeConsumo(imovel.getConsumoAgua());
			if( anormalidadeConsumo != null){
				retorno += formarLinha(0, 2, 460, 460, "ANORM. CONSUMO: " + anormalidadeConsumo, 0, 0);
			}
		}
	    // Média de dias
	    // Data das Leituras
	    retorno += formarLinha(7, 0, 37, 436, "ANTERIOR", 0, 0) + formarLinha(7, 0, 37, 460, "ATUAL", 0, 0);
	    
	    Vector regsTipo3 = imovel.getRegistros3();

	    if (regsTipo3 != null) {

	    	retorno += formarLinha(7, 0, 50, 499, "ULTIMOS CONSUMOS", 0, 0);
	    	retorno += "LINE 115 525 115 665 1\n";
		
		    // Ultimos Consumos
			for (int i = 0; i < regsTipo3.size(); i++) {

				ImovelReg3 reg3 = (ImovelReg3) regsTipo3.elementAt(i);

			    retorno += formarLinha(0, 2, 44, 522, Util.getAnoBarraMesReferencia(reg3.getAnoMesReferencia()) + "", 0, i * 25);
			    String anormalidade = "";
			    if (reg3.getAnormalidadeLeitura() != Constantes.NULO_INT && reg3.getAnormalidadeLeitura() != 0) {
			    	anormalidade = "A. Leit.:" + reg3.getAnormalidadeLeitura() + "";
			    } else if (reg3.getAnormalidadeConsumo() != Constantes.NULO_INT && reg3.getAnormalidadeConsumo() != 0) {
			    	anormalidade = "A. Cons.:" + reg3.getAnormalidadeConsumo() + "";
			    }
			    retorno += formarLinha(0, 2, 127, 522, Util
			    		.verificarNuloInt(reg3.getConsumo()) + " m3 " + anormalidade, 0, i * 25);
			}
	    }else{
	    	retorno += formarLinha(7, 0, 50, 499, "HISTORICO DE CONSUMO", 0, 0) + formarLinha(7, 0, 50, 520, "INEXISTENTE", 0, 0);

	    }
	    retorno += formarLinha(7, 0, 75, 672, "MEDIA(m3):", 0, 0) + formarLinha(7, 0, 195, 672, media, 0, 0);

	    
	    // Qualidade da agua
	    retorno += formarLinha(7, 0, 448, 496, "QUALIDADE DA AGUA", 0, 0);
	    retorno += formarLinha(0, 0, 672, 505, "Ref: ", 0, 0) + formarLinha(0, 0, 705, 505, Util.retornaDescricaoAnoMes(imovel.getAnoMesConta()), 0, 0);
	    retorno += formarLinha(7, 0, 287, 520, "PARAMETROS", 0, 0) + formarLinha(7, 0, 428, 520, "PORT. 518", 0, 0) + formarLinha(7, 0, 540, 520, "ANALISADO", 0, 0) + formarLinha(7, 0, 672, 520, "CONFORME", 0, 0);
	    
// Daniel - Parametro
	    retorno += formarLinha(0, 0, 287, 552, "COR(uH)", 0, 0);
	    retorno += formarLinha(0, 0, 287, 571, "TURBIDEZ(UT)", 0, 0);
	    retorno += formarLinha(0, 0, 287, 590, "CLORO(mg/L)", 0, 0);
	    retorno += formarLinha(0, 0, 287, 609, "FLUOR(mg/L)", 0, 0);
	    retorno += formarLinha(0, 0, 287, 628, "COLIFORME TOTAL", 0, 0) + formarLinha(0, 0, 287, 640, "Pres/Aus)", 0, 0);
	    retorno += formarLinha(0, 0, 287, 657, "COLIFORME TERMO", 0, 0) + formarLinha(0, 0, 287, 671, "TOLER.(Pres/Aus)", 0, 0);
	    
// Daniel - Exigido Portaria 518/2004
	    if (imovel.getQuantidadeCorExigidas() != Constantes.NULO_INT){
	    	retorno += formarLinha(0, 0, 469, 552, String.valueOf(imovel.getQuantidadeCorExigidas()), 0, 0);
//	    	retorno += formarLinha(0, 0, 469, 552, "71", 0, 0);
	    }
	    if (imovel.getQuantidadeTurbidezExigidas() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 469, 571, String.valueOf(imovel.getQuantidadeTurbidezExigidas()), 0, 0);
//		    retorno += formarLinha(0, 0, 469, 571, "71", 0, 0);
	    }
	    if (imovel.getQuantidadeCloroExigidas() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 469, 590, String.valueOf(imovel.getQuantidadeCloroExigidas()), 0, 0);
//		    retorno += formarLinha(0, 0, 469, 590, "258", 0, 0);
	    }
	    if (imovel.getQuantidadeFluorExigidas() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 469, 609, String.valueOf(imovel.getQuantidadeFluorExigidas()), 0, 0);
//		    retorno += formarLinha(0, 0, 469, 609, "35", 0, 0);
	    }
	    if (imovel.getQuantidadeColiformesTotaisExigidas() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 469, 628, String.valueOf(imovel.getQuantidadeColiformesTotaisExigidas()), 0, 0);
//		    retorno += formarLinha(0, 0, 469, 628, "258", 0, 0);
	    }
	    if (imovel.getQuantidadeColiformesTermoTolerantesExigidas() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 469, 657, String.valueOf(imovel.getQuantidadeColiformesTermoTolerantesExigidas()), 0, 0);
//		    retorno += formarLinha(0, 0, 469, 657, "258", 0, 0);
	    }
    
// Daniel - Analisado
	    if (imovel.getQuantidadeCorAnalisadas() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 582, 552, String.valueOf(imovel.getQuantidadeCorAnalisadas()), 0, 0);
//		    retorno += formarLinha(0, 0, 582, 552, "77", 0, 0);
	    }
	    if (imovel.getQuantidadeTurbidezAnalisadas() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 582, 571, String.valueOf(imovel.getQuantidadeTurbidezAnalisadas()), 0, 0);
//		    retorno += formarLinha(0, 0, 582, 571, "77", 0, 0);
	    }
	    if (imovel.getQuantidadeCloroAnalisadas() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 582, 590, String.valueOf(imovel.getQuantidadeCloroAnalisadas()), 0, 0);
//		    retorno += formarLinha(0, 0, 582, 590, "77", 0, 0);
	    }
	    if (imovel.getQuantidadeFluorAnalisadas() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 582, 609, String.valueOf(imovel.getQuantidadeFluorAnalisadas()), 0, 0);
//		    retorno += formarLinha(0, 0, 582, 609, "28", 0, 0);
	    }
	    if (imovel.getQuantidadeColiformesTotaisAnalisadas() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 582, 628, String.valueOf(imovel.getQuantidadeColiformesTotaisAnalisadas()), 0, 0);
//		    retorno += formarLinha(0, 0, 582, 628, "77", 0, 0);
	    }
	    if (imovel.getQuantidadeColiformesTermoTolerantesAnalisadas() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 582, 657, String.valueOf(imovel.getQuantidadeColiformesTermoTolerantesAnalisadas()), 0, 0);	    	
//		    retorno += formarLinha(0, 0, 582, 657, "77", 0, 0);	    	
	    }
	    
// daniel - Conforme
	    if (imovel.getQuantidadeCorConforme() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 726, 552, String.valueOf(imovel.getQuantidadeCorConforme()), 0, 0);
//		    retorno += formarLinha(0, 0, 726, 552, "76", 0, 0);
	    }
	    if (imovel.getQuantidadeTurbidezConforme() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 726, 571, String.valueOf(imovel.getQuantidadeTurbidezConforme()), 0, 0);
//		    retorno += formarLinha(0, 0, 726, 571, "77", 0, 0);
	    }
	    if (imovel.getQuantidadeCloroConforme() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 726, 590, String.valueOf(imovel.getQuantidadeCloroConforme()), 0, 0);
//		    retorno += formarLinha(0, 0, 726, 590, "71", 0, 0);
	    }
	    if (imovel.getQuantidadeFluorConforme() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 726, 609, String.valueOf(imovel.getQuantidadeFluorConforme()), 0, 0);
//		    retorno += formarLinha(0, 0, 726, 609, "0", 0, 0);
	    }
	    if (imovel.getQuantidadeColiformesTotaisConforme() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 726, 628, String.valueOf(imovel.getQuantidadeColiformesTotaisConforme()), 0, 0);
//		    retorno += formarLinha(0, 0, 726, 628, "75", 0, 0);
	    }
	    if (imovel.getQuantidadeColiformesTermoTolerantesConforme() != Constantes.NULO_INT){
		    retorno += formarLinha(0, 0, 726, 657, String.valueOf(imovel.getQuantidadeColiformesTermoTolerantesConforme()), 0, 0);
//		    retorno += formarLinha(0, 0, 726, 657, "77", 0, 0);
	    }

	    retorno += formarLinha(7, 0, 53, 708, "DESCRICAO", 0, 0) + formarLinha(7, 0, 571, 708, "CONSUMO", 0, 0) + formarLinha(7, 0, 687, 708, "TOTAL(R$)", 0, 0);
	    int ultimaLinhaAgua = 0;
	    int ultimaLinhaPoco = 0;
	    int quantidadeLinhasAtual = 0;
	    int quantidadeMaximaLinhas = 18;
	    Vector linhaAgua = this.gerarLinhasTarifaAgua(consumoAgua);
	    retorno += linhaAgua.elementAt(0);
	    ultimaLinhaAgua = (((Integer) linhaAgua.elementAt(1)).intValue());
	    if (ultimaLinhaAgua != 0) {
	    	quantidadeLinhasAtual = quantidadeLinhasAtual + ultimaLinhaAgua + 1;
	    }
	    ultimaLinhaAgua *= 25;
	    Vector tarifasPoco = this.gerarLinhasTarifaPoco();
	    ultimaLinhaPoco = ultimaLinhaAgua;
	    for (int i = 0; i < tarifasPoco.size(); i++) {
		String[] tarifaPoco = (String[]) tarifasPoco.elementAt(i);
		ultimaLinhaPoco = ultimaLinhaAgua + ((i + 1) * 25);
		quantidadeLinhasAtual++;
		int deslocaDireitaColuna;
		if (i == 0 || i == 1 || i == 2) {
		    deslocaDireitaColuna = i;
		} else {
		    deslocaDireitaColuna = 2;
		}
		if (tarifaPoco[0] != null) {
		    retorno += formarLinha(7, 0, 53, 710, tarifaPoco[0], deslocaDireitaColuna * 10, (i + 1) * 25 + ultimaLinhaAgua);
		}
		if (tarifaPoco[1] != null) {
		    retorno += formarLinha(7, 0, 571, 710, tarifaPoco[1], 0, (i + 1) * 25 + ultimaLinhaAgua);
		}
		if (tarifaPoco[2] != null) {
		    retorno += formarLinha(7, 0, 697, 710, tarifaPoco[2], 0, (i + 1) * 25 + ultimaLinhaAgua);
		}
	    }
	    
	    // Daniel Dados dos Valores de Rateio de Água e Esgoto
	    Vector rateios = gerarLinhasRateioAguaEsgotoCobrados();
	    int ultimaLinhaRateio = ultimaLinhaPoco;
	    for (int i = 0; i < rateios.size(); i++) {
			String[] debito = (String[]) rateios.elementAt(i);
			ultimaLinhaRateio = ultimaLinhaPoco + ((i + 1) * 25);
			quantidadeLinhasAtual++;
			if (debito[0] != null) {
			    retorno += formarLinha(7, 0, 53, 710, debito[0], 0, (i + 1) * 25 + ultimaLinhaPoco);
			}
			if (debito[1] != null) {
			    retorno += formarLinha(7, 0, 697, 710, debito[1], 0, (i + 1) * 25 + ultimaLinhaPoco);
			}
	    }
	    
	    int indicadorDiscriminarDescricao = retornaIndicadorDiscriminar(quantidadeMaximaLinhas, quantidadeLinhasAtual, 'd');
	    Vector debitos = this.gerarLinhasDebitosCobrados(indicadorDiscriminarDescricao);
	    int ultimaLinhaDebito = ultimaLinhaRateio;
	    for (int i = 0; i < debitos.size(); i++) {
		String[] debito = (String[]) debitos.elementAt(i);
		ultimaLinhaDebito = ultimaLinhaRateio + ((i + 1) * 25);
		quantidadeLinhasAtual++;
		// int deslocaDireitaColuna;
		// if( i == 0 || i == 1 || i==2 ){
		// deslocaDireitaColuna = i;
		// } else {
		// deslocaDireitaColuna = 2;
		// }
		if (debito[0] != null) {
		    retorno += formarLinha(7, 0, 53, 710, debito[0], 0, (i + 1) * 25 + ultimaLinhaRateio);
		}
		if (debito[1] != null) {
		    retorno += formarLinha(7, 0, 571, 710, debito[1], 0, (i + 1) * 25 + ultimaLinhaRateio);
		}
		if (debito[2] != null) {
		    retorno += formarLinha(7, 0, 697, 710, debito[2], 0, (i + 1) * 25 + ultimaLinhaRateio);
		}
	    }
	    indicadorDiscriminarDescricao = retornaIndicadorDiscriminar(quantidadeMaximaLinhas, quantidadeLinhasAtual, 'c');
	    Vector creditos = this.gerarLinhasCreditosRealizados(indicadorDiscriminarDescricao);
	    int ultimaLinhaCredito = ultimaLinhaDebito;
	    for (int i = 0; i < creditos.size(); i++) {
		String[] credito = (String[]) creditos.elementAt(i);
		ultimaLinhaCredito = ultimaLinhaDebito + ((i + 1) * 25);
		// int deslocaDireitaColuna;
		// if( i == 0 || i == 1 || i==2 ){
		// deslocaDireitaColuna = i;
		// } else {
		// deslocaDireitaColuna = 2;
		// }
		if (credito[0] != null) {
		    retorno += formarLinha(7, 0, 53, 710, credito[0], 0, (i + 1) * 25 + ultimaLinhaDebito);
		}
		if (credito[1] != null) {
		    retorno += formarLinha(7, 0, 571, 710, credito[1], 0, (i + 1) * 25 + ultimaLinhaDebito);
		}
		if (credito[2] != null) {
		    retorno += formarLinha(7, 0, 697, 710, credito[2], 0, (i + 1) * 25 + ultimaLinhaDebito);
		}
	    }
	    Vector impostos = this.gerarLinhasImpostosRetidos();
	    for (int i = 0; i < impostos.size(); i++) {
		String[] imposto = (String[]) impostos.elementAt(i);
		int deslocaDireitaColuna;
		if (i == 0 || i == 1) {
		    deslocaDireitaColuna = i;
		} else {
		    deslocaDireitaColuna = 1;
		}
		if (imposto[0] != null) {
		    retorno += formarLinha(7, 0, 53, 710, imposto[0], deslocaDireitaColuna * 10, (i + 1) * 25 + ultimaLinhaCredito);
		}
		if (imposto[1] != null) {
		    retorno += formarLinha(7, 0, 571, 710, imposto[1], 0, (i + 1) * 25 + ultimaLinhaCredito);
		}
		if (imposto[2] != null) {
		    retorno += formarLinha(7, 0, 697, 710, imposto[2], 0, (i + 1) * 25 + ultimaLinhaCredito);
		}
	    }

	    retorno += formarLinha(7, 1, 160, 1210, Util.dateToString(imovel.getDataVencimento()), 0, 0);
	    double valorTotalConta = imovel.getValorConta();
	    retorno +=
	    formarLinha(4, 0, 640, 1210, Util.formatarDoubleParaMoedaReal(valorTotalConta), 0, 0);
	    retorno += formarLinha(0, 2, 424, 1265, "OPCAO PELO DEB. AUTOMATICO: ", 0, 0) + formarLinha(5, 0, 649, 1266, ( imovel.getOpcaoDebitoAutomatico() == Constantes.NULO_INT ? "" : imovel.getOpcaoDebitoAutomatico()+"" ), 0, 0);

		if (imovel.getMensagemQuitacaoAnual() != null && !imovel.getMensagemQuitacaoAnual().equals(Constantes.NULO_STRING)){
			retorno += dividirLinha(7, 0, 35, 1300, imovel.getMensagemQuitacaoAnual(), 60, 20);
	    
		}else if (imovel.getMensagemEstouroConsumo1() != null && !imovel.getMensagemEstouroConsumo1().equals("")) {
			String mensagensEstouro = "";
			if (imovel.getMensagemEstouroConsumo1() != null){
				mensagensEstouro = imovel.getMensagemEstouroConsumo1();
			}
			
			if (imovel.getMensagemEstouroConsumo2() != null){
				mensagensEstouro += " " + imovel.getMensagemEstouroConsumo2();
			}

			if (imovel.getMensagemEstouroConsumo3() != null){
				mensagensEstouro += " " + imovel.getMensagemEstouroConsumo3();
			}

			if (mensagensEstouro != null){
				if (mensagensEstouro.length() > 135){
					retorno += dividirLinha(7, 0, 35, 1300, mensagensEstouro.substring(0, 240), 60, 20);
				}else{
					retorno += dividirLinha(7, 0, 35, 1300, mensagensEstouro, 60, 20);							
				}
			}

	    } else {
			String mensagensConta = "";
			if (imovel.getMensagemConta1() != null){
				mensagensConta = imovel.getMensagemConta1();
			}
			
			if (imovel.getMensagemConta2() != null){
				mensagensConta += " " + imovel.getMensagemConta2();
			}

			if (imovel.getMensagemConta3() != null){
				mensagensConta += " " + imovel.getMensagemConta3();
			}

			if (mensagensConta != null){
				if (mensagensConta.length() > 240){
					retorno += dividirLinha(7, 0, 35, 1300, mensagensConta.substring(0, 240), 60, 20);
				}else{
					retorno += dividirLinha(7, 0, 35, 1300, mensagensConta, 60, 20);							
				}
			}
	    }

		retorno += formarLinha(0, 2, 344, 1456, imovel.getMatricula() + "", 0, 0) + formarLinha(0, 2, 443, 1456, Util.formatarAnoMesParaMesAno(imovel.getAnoMesConta()), 0, 0) + formarLinha(0, 2, 558, 1456, Util.dateToString(imovel.getDataVencimento()), 0, 0) + formarLinha(0, 2, 694, 1456, Util.formatarDoubleParaMoedaReal(valorTotalConta), 0, 0);

//	    Daniel - Situação criada para imprimir codigo de barras apenas quando o valor da conta for maior que o mínimo.
//	    if(imovel.isValorContaAcimaDoMinimo()){
		    System.out.println("##COD AGENCIA: " + imovel.getCodigoAgencia());

		    if (imovel.getCodigoAgencia() == null || imovel.getCodigoAgencia().equals("")) {
				System.out.println("##COD AGENCIA DO IF: " + imovel.getCodigoAgencia());
				
				String representacaoNumericaCodBarra = Util.obterRepresentacaoNumericaCodigoBarra(new Integer(3), valorTotalConta, new Integer(Integer.parseInt(imovel.getInscricao().substring(0, 3))), new Integer(imovel.getMatricula()),
					Util.formatarAnoMesParaMesAnoSemBarra(imovel.getAnoMesConta()), new Integer(imovel.getDigitoVerificadorConta()), null, null, null, null, null, null);
				String representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarra.substring(0, 11).trim() + "-" + representacaoNumericaCodBarra.substring(11, 12).trim() + " " + representacaoNumericaCodBarra.substring(12, 23).trim() + "-"
					+ representacaoNumericaCodBarra.substring(23, 24).trim() + " " + representacaoNumericaCodBarra.substring(24, 35).trim() + "-" + representacaoNumericaCodBarra.substring(35, 36).trim() + " " + representacaoNumericaCodBarra.substring(36, 47).trim() + "-"
					+ representacaoNumericaCodBarra.substring(47, 48);
				retorno += formarLinha(5, 0, 66, 1515, representacaoNumericaCodBarraFormatada, 0, 0);
				String representacaoCodigoBarrasSemDigitoVerificador = representacaoNumericaCodBarra.substring(0, 11) + representacaoNumericaCodBarra.substring(12, 23) + representacaoNumericaCodBarra.substring(24, 35) + representacaoNumericaCodBarra.substring(36, 47);
				retorno += "B I2OF5 1 2 90 35 1538 " + representacaoCodigoBarrasSemDigitoVerificador + "\n";

		    } else {
				retorno += formarLinha(4, 0, 182, 1538, "DÉBITO AUTOMÁTICO", 0, 0);
		    }	    	
//	    }
	    retorno += formarLinha(5, 0, 109, 1661, imovel.getGrupoFaturamento() + "", 0, 0);
	    // TODO - Verificar como pega o código da empresa que está prestando
	    // o servico a cosanpa
	    retorno += formarLinha(5, 0, 352, 1661, "4", 0, 0);
	    retorno += "FORM\n" + "PRINT ";
	} catch (Exception ex) {
	    ex.printStackTrace();
	    Util.mostrarErro("Erro na impressão...", ex);
	    FileManager.salvarErro(ex);
	}
	System.out.println(retorno);
	return retorno;
    }

    /**
     * [SB0003] - Gerar Linhas da Tarifa de Agua
     * 
     * @return Os dados estão dividos em 3 partes Descricao, de indice 0
     *         Consumo, de indice 1 Valor, de indice 2
     */
    private Vector gerarLinhasTarifaAgua(Consumo consumoAgua) {
	String linhas = "";
	// Verificamos se o tipo de calculo é por categoria ou por subcategoria
	boolean tipoTarifaPorCategoria = ControladorImoveis.getInstancia().tipoTarifaPorCategoria(imovel);
	int qtdLinhas = 0;
	// 3
	if (imovel.getRegistros2() != null) {
	    for (int i = 0; i < imovel.getRegistros2().size(); i++) {
		ImovelReg2 dadosEconomiasSubcategorias = (ImovelReg2) imovel.getRegistros2().elementAt(i);
		if (dadosEconomiasSubcategorias.getFaturamentoAgua() == null) {
		    continue;
		}
		qtdLinhas++;
//		if (linhas.equals("")) {
//		    // 2
//		    linhas += formarLinha(7, 0, 53, 733, "AGUA", 0, 0);
//		}
		// 3.1
		int quantidaEconomias = 0;
		// 3.1.1
		if (!Constantes.NULO_STRING.equals(dadosEconomiasSubcategorias.getFatorEconomiaCategoria().trim())) {
		    quantidaEconomias = Integer.parseInt(dadosEconomiasSubcategorias.getFatorEconomiaCategoria());
		    // 3.1.2
		} else {
		    quantidaEconomias = dadosEconomiasSubcategorias.getQtdEconomiasSubcategoria();
		}
		// 3.3.1
		String descricao = "";
		if (tipoTarifaPorCategoria) {
		    descricao = dadosEconomiasSubcategorias.getDescricaoCategoria() + " " + quantidaEconomias + " " + "UNIDADE(S)";
		    // 3.3.1.1, 3.3.1.2 e 3.3.2.2, 3.3.3
		    if (descricao.length() > 40) {
			linhas += formarLinha(7, 0, 63, 710, descricao.substring(0, 40), 0, qtdLinhas * 25);
		    } else {
			linhas += formarLinha(7, 0, 63, 710, descricao, 0, qtdLinhas * 25);
		    }
		} else {
		    descricao = dadosEconomiasSubcategorias.getDescricaoAbreviadaSubcategoria() + " " + quantidaEconomias + " " + "UNIDADE(S)";
		    // 3.3.2.1, 3.3.1.2 e 3.3.2.2, 3.3.3
		    if (descricao.length() > 40) {
			linhas += formarLinha(7, 0, 63, 710, descricao.substring(0, 40), 0, qtdLinhas * 25);
		    } else {
			linhas += formarLinha(7, 0, 63, 710, descricao, 0, qtdLinhas * 25);
		    }
		}
		int consumoMinimo = 0;
		if (imovel.getConsumoMinAgua() > imovel.getConsumoMinimoImovel()) {
		    consumoMinimo = imovel.getConsumoMinAgua();
		} else {
		    consumoMinimo = imovel.getConsumoMinimoImovel();
		}
		// 3.4
		if (consumoAgua == null && dadosEconomiasSubcategorias.getFaturamentoAgua() != null && dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoFaturado() <= consumoMinimo) {
		    qtdLinhas++;
		    descricao = "";
		    // 3.4.2
		    descricao = "TARIFA MINIMA " + Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorTarifaMinima() / quantidaEconomias) + " POR UNIDADE ";
		    linhas += formarLinha(7, 0, 63, 710, descricao, 0, qtdLinhas * 25);
		    descricao = consumoMinimo + " m3";
		    // 3.4.3
		    linhas += formarLinha(7, 0, 571, 710, descricao, 0, qtdLinhas * 25);
		    // 3.4.4
		    linhas += formarLinha(7, 0, 697, 710, Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorTarifaMinima()), 0, qtdLinhas * 25);
		    // 3.5
		} else {
		    // 3.5.1
			System.out.println("dadosEconomiasSubcategorias.getFaturamentoAgua(): " + dadosEconomiasSubcategorias.getFaturamentoAgua());
			System.out.println("dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas(): " + dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas());
			System.out.println("dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas().size(): " + dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas().size());
			if (dadosEconomiasSubcategorias.getFaturamentoAgua() != null && dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas() != null && dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas().size() > 0) {
			qtdLinhas++;
			// 3.5.1.1
			    descricao = "ATE " + ((int) dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoMinimo() / quantidaEconomias) + " m3 - " + Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorTarifaMinima() / quantidaEconomias)
				    + " POR UNIDADE";
			    linhas += formarLinha(7, 0, 73, 710, descricao, 0, qtdLinhas * 25);
			    // 3.5.1.2.3
			    linhas += formarLinha(7, 0, 571, 710, dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoMinimo() + " m3", 0, qtdLinhas * 25);
			    // 3.5.1.2.4
			    linhas += formarLinha(7, 0, 697, 710, Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorTarifaMinima()), 0, qtdLinhas * 25);
			    // 3.5.1.2.5
			    for (int j = 0; j < dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas().size(); j++) {
				qtdLinhas++;
				// 3.5.1.2.5
				DadosFaturamentoFaixa faixa = (DadosFaturamentoFaixa) dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas().elementAt(j);
				// 3.5.1.2.5.1
				if (faixa.getLimiteFinalConsumo() == Constantes.LIMITE_SUPERIOR_FAIXA_FINAL) {
				    // 3.5.1.2.5.1.2.1, 3.5.1.2.5.1.2.2,
				    // 3.5.1.2.5.1.2.3, 3.5.1.2.5.1.2.4,
				    // 3.5.1.2.5.1.2.5
				    descricao = "ACIMA DE " + (faixa.getLimiteInicialConsumo() - 1) + " m3 - R$ " + Util.formatarDoubleParaMoedaReal(faixa.getValorTarifa()) + " POR m3";
				    linhas += formarLinha(7, 0, 73, 710, descricao, 0, qtdLinhas * 25);
				    // 3.5.1.2.5.1.3.1
				    linhas += formarLinha(7, 0, 571, 710, faixa.getConsumoFaturado() * quantidaEconomias + " m3 ", 0, qtdLinhas * 25);
				    // 3.5.1.2.5.1.3.2
				    // 3.5.1.2.5.1.4
				    linhas += formarLinha(7, 0, 697, 710, Util.formatarDoubleParaMoedaReal(faixa.getValorFaturado() * quantidaEconomias), 0, qtdLinhas * 25);
				    // 3.5.1.2.5.2
				} else {
				    // 3.5.1.2.5.2.2.1, 3.5.1.2.5.2.2.2,
				    // 3.5.1.2.5.2.2.3, 3.5.1.2.5.2.2.4,
				    // 3.5.1.2.5.2.2.5, 3.5.1.2.5.2.2.6
				    descricao = faixa.getLimiteInicialConsumo() + " m3 A " + faixa.getLimiteFinalConsumo() + " m3 - R$ " + Util.formatarDoubleParaMoedaReal(faixa.getValorTarifa()) + " POR M3 ";
				    // 3.5.1.2.5.2.3.1
				    linhas += formarLinha(7, 0, 73, 710, descricao, 0, qtdLinhas * 25);
				    // 3.5.1.2.5.1.3.2
				    linhas += formarLinha(7, 0, 571, 710, faixa.getConsumoFaturado() * quantidaEconomias + " m3 ", 0, qtdLinhas * 25);
				    // 3.5.1.2.5.1.4.1
				    linhas += formarLinha(7, 0, 697, 710, Util.formatarDoubleParaMoedaReal(faixa.getValorFaturado() * quantidaEconomias), 0, qtdLinhas * 25);
				}
			    }
//			}
		    } else {
			if (dadosEconomiasSubcategorias.getFaturamentoAgua() != null) {
			    qtdLinhas++;
			    descricao = "CONSUMO DE AGUA";
			    // 3.5.1.1.2.1
			    linhas += formarLinha(7, 0, 73, 710, descricao, 0, qtdLinhas * 25);
			    linhas += formarLinha(7, 0, 571, 710, ((int) dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoFaturado()) + " m3", 0, qtdLinhas * 25);
			    // 3.5.1.1.2.2
			    linhas += formarLinha(7, 0, 697, 710, Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorFaturado()), 0, qtdLinhas * 25);
			}
		    }
		}
	    }
	}
	Vector retornar = new Vector();
	retornar.addElement(linhas);
	retornar.addElement(new Integer(qtdLinhas));
	return retornar;
    }

    /**
     * [SB0004] - Gerar Linhas da Tarifa de Esgoto
     * 
     * @return Os dados estão dividos em 3 partes Descricao, de indice 0
     *         Consumo, de indice 1 Valor, de indice 2
     */
    private Vector gerarLinhasTarifaPoco() {
	Vector retorno = new Vector();
	// Os dados estão dividos em 3 partes
	// Descricao, de indice 0
	// Consumo, de indice 1
	// Valor, de indice 2
	String[] dados = new String[3];
	double valorEsgoto = 0d;
	int consumoAgua = 0;
	int consumoEsgoto = 0;
	double valorAgua = 0d;
	// 3
	if (imovel.getRegistros2() != null) {
	    for (int i = 0; i < imovel.getRegistros2().size(); i++) {
		ImovelReg2 dadosEconomiasSubcategorias = (ImovelReg2) imovel.getRegistros2().elementAt(i);
		// 1
		if (dadosEconomiasSubcategorias.getFaturamentoEsgoto() != null && dadosEconomiasSubcategorias.getFaturamentoEsgoto().getValorFaturado() != Constantes.NULO_DOUBLE) {
		    valorEsgoto += dadosEconomiasSubcategorias.getFaturamentoEsgoto().getValorFaturado();
		}
		if (dadosEconomiasSubcategorias.getFaturamentoEsgoto() != null && dadosEconomiasSubcategorias.getFaturamentoEsgoto().getConsumoFaturado() != Constantes.NULO_INT) {
		    consumoEsgoto += dadosEconomiasSubcategorias.getFaturamentoEsgoto().getConsumoFaturado();
		}
		if (dadosEconomiasSubcategorias.getFaturamentoAgua() != null) {
		    if (dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoFaturado() != Constantes.NULO_INT) {
			consumoAgua += dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoFaturado();
		    }
		    if (dadosEconomiasSubcategorias.getFaturamentoAgua().getValorFaturado() != Constantes.NULO_DOUBLE) {
			valorAgua += dadosEconomiasSubcategorias.getFaturamentoAgua().getValorFaturado();
		    }
		}
	    }
	    dados = new String[3];
	    if (consumoAgua == consumoEsgoto && valorAgua != 0) {
		if (valorEsgoto != 0) {
		    // 1.2.1
		    dados[0] = "ESGOTO ";
		    // 1.2.3
		    dados[0] += Util.formatarDoubleParaMoedaReal(imovel.getPercentCobrancaEsgoto());
		    // 1.2.3
		    dados[0] += " % DO VALOR DE ÁGUA";
		    // 1.4
		    dados[2] = Util.formatarDoubleParaMoedaReal(valorEsgoto);
		    retorno.addElement(dados);
		}
	    } else {
		if (valorEsgoto != 0) {
		    // 1.2.1
		    dados[0] = "ESGOTO ";
		    // 1.3.1
		    dados[1] = consumoEsgoto + "";
		    // 1.3.2
		    dados[1] += " m3";
		    // 1.4
		    dados[2] = Util.formatarDoubleParaMoedaReal(valorEsgoto);
		    retorno.addElement(dados);
		}
	    }
	}
	return retorno;
    }

    /**
     * [SB0005] - Gerar Linhas da Debitos Cobrados
     * 
     * @return Os dados estão dividos em 3 partes Descricao, de indice 0
     *         Consumo, de indice 1 Valor, de indice 2
     */
    private Vector gerarLinhasDebitosCobrados(int indicadorDiscriminarDescricao) {
	Vector retorno = new Vector();
	// Os dados estão dividos em 3 partes
	// Descricao, de indice 0
	// Consumo, de indice 1
	// Valor, de indice 2
	String[] dados = new String[3];
	// 3
	if (imovel.getRegistros4() != null) {
	    // caso seja para discriminar os dados dos débitos
	    if (indicadorDiscriminarDescricao == 1) {
		for (int i = 0; i < imovel.getRegistros4().size(); i++) {
		    dados = new String[3];
		    RegistroBasico dadosDebitosCobrados = (RegistroBasico) imovel.getRegistros4().elementAt(i);
		    // 1.1.2
		    dados[0] = dadosDebitosCobrados.getDescricao();
		    // 1.1.3
		    dados[2] = Util.formatarDoubleParaMoedaReal(dadosDebitosCobrados.getValor());
		    retorno.addElement(dados);
		}
	    } else {
		double soma = 0d;
		for (int i = 0; i < imovel.getRegistros4().size(); i++) {
		    RegistroBasico dadosDebitosCobrados = (RegistroBasico) imovel.getRegistros4().elementAt(i);
		    soma += dadosDebitosCobrados.getValor();
		}
		dados = new String[3];
		// 1.1.2
		dados[0] = "DEBITOS";
		// 1.1.3
		dados[2] = Util.formatarDoubleParaMoedaReal(soma);
		retorno.addElement(dados);
	    }
	}
	return retorno;
    }

    /**
     * [SB0005] - Gerar Linhas da Debitos Cobrados
     * 
     * @return Os dados estão dividos em 3 partes Descricao, de indice 0
     *         Consumo, de indice 1 Valor, de indice 2
     */
    private Vector gerarLinhasRateioAguaEsgotoCobrados() {
		Vector retorno = new Vector();
		// Os dados estão dividos em 2 partes:
		// Descricao, de indice 0
		// Valor, de indice 2
		String[] dados = new String[2];
		
		// Valor do rateio de Água
		if (imovel.getConsumoAgua() != null && imovel.getValorRateioAgua() > 0) {

		    dados = new String[2];
		    dados[0] = "RATEIO DE ÁGUA DO CONDOMÍNIO";
		    dados[1] = Util.formatarDoubleParaMoedaReal(imovel.getValorRateioAgua());
		    retorno.addElement(dados);
		}

		// Valor do rateio de Esgoto
		if (imovel.getConsumoEsgoto() != null && imovel.getValorRateioEsgoto() > 0) {

		    dados = new String[2];
		    dados[0] = "RATEIO DE ESGOTO DO CONDOMÍNIO";
		    dados[1] = Util.formatarDoubleParaMoedaReal(imovel.getValorRateioEsgoto());
		    retorno.addElement(dados);
		}
		return retorno;
    }


    /**
     * [SB0006] - Gerar Linhas de Creditos Realizados
     * 
     * @return Os dados estão dividos em 3 partes Descricao, de indice 0
     *         Consumo, de indice 1 Valor, de indice 2
     */
    private Vector gerarLinhasCreditosRealizados(int indicadorDiscriminarDescricao) {
	Vector retorno = new Vector();
	// Os dados estão dividos em 3 partes
	// Descricao, de indice 0
	// Consumo, de indice 1
	// Valor, de indice 2
	String[] dados = new String[3];
	// 3
	if (imovel.getRegistros5() != null) {
	    // caso seja para discriminar os dados dos créditos
	    if (indicadorDiscriminarDescricao == 1) {
		// caso o valor do crédito seja maior que o valor da conta sem o
		// crédito
		double valorContaSemCreditos = 0d;
		double valorContaResidual = 0d;
		boolean valorCreditoMaiorValorConta = false;
		boolean naoEmitirMaisCreditos = false;
		if (imovel.getValorResidualCredito() != 0d) {
		    valorContaSemCreditos = imovel.getValorContaSemCreditos();
		    valorCreditoMaiorValorConta = true;
		}
		for (int i = 0; i < imovel.getRegistros5().size(); i++) {
		    RegistroBasico dadosCreditosRealizado = (RegistroBasico) imovel.getRegistros5().elementAt(i);
		    // caso o valor dos créditos n seja maior que o valor da
		    // conta sem os créditos
		    
		    // Daniel - Verificar se o imovel deve ou nao considerar Bonus Social.
	    	if( ((RegistroBasico) (imovel.getRegistros5().elementAt(i))).getCodigo().equalsIgnoreCase(ImovelConta.CODIGO_BONUS_SOCIAL)  &&
		    		Integer.parseInt(imovel.getCodigoPerfil()) == ImovelConta.PERFIL_BONUS_SOCIAL &&
		    		imovel.getConsumoAgua() != null &&
		    		imovel.getConsumoAgua().getConsumoCobradoMes() > 10 ){
		    		
		    		continue;
	    	}
	    	
		    if (!valorCreditoMaiorValorConta) {
		    	dados = new String[3];
				// 1.1.2
				dados[0] = dadosCreditosRealizado.getDescricao();
				// 1.1.3
				dados[2] = Util.formatarDoubleParaMoedaReal(dadosCreditosRealizado.getValor());
				retorno.addElement(dados);

		    }
		    // //caso o valor dos créditos seja maior que o valor das
		    // contas sem os créditos
		    else {
				if (!naoEmitirMaisCreditos) {
				    double valorCredito = dadosCreditosRealizado.getValor();
				    valorContaResidual = valorContaSemCreditos - valorCredito;
				    // emite as créditos até o valor dos creditos ser
				    // menor que o valor da conta
				    if (valorContaResidual < 0d) {
//				    	valorContaResidual = valorContaResidual * -1;
				    	naoEmitirMaisCreditos = true;
	
					    dados = new String[3];
					    // 1.1.2
					    dados[0] = dadosCreditosRealizado.getDescricao();
					    // 1.1.3
	//				    dados[2] = Util.formatarDoubleParaMoedaReal(valorCredito);
					    dados[2] = Util.formatarDoubleParaMoedaReal(valorContaSemCreditos);
					    
					    retorno.addElement(dados);
	
				    }else{
				    	
				    	valorContaSemCreditos = valorContaSemCreditos - valorCredito;

				    	dados = new String[3];
					    // 1.1.2
					    dados[0] = dadosCreditosRealizado.getDescricao();
					    // 1.1.3
					    dados[2] = Util.formatarDoubleParaMoedaReal(valorCredito);
	//				    dados[2] = Util.formatarDoubleParaMoedaReal(valorContaSemCreditos);
					    
					    retorno.addElement(dados);			    	
				    }
				}
		    }
		}
	    } else {
		double soma = imovel.getValorCreditos();
		// for ( int i = 0; i < imovel.getRegistros5().size(); i++ ){
		//
		// RegistroDescricaoValor dadosCreditosRealizado = (
		// RegistroDescricaoValor ) imovel.getRegistros5().elementAt( i
		// );
		// soma += dadosCreditosRealizado.getValor();
		// }
		dados = new String[3];
		// 1.1.2
		dados[0] = "CREDITOS";
		// 1.1.3
		dados[2] = Util.formatarDoubleParaMoedaReal(soma);
		retorno.addElement(dados);
	    }
	}
	return retorno;
    }

    /**
     * [SB0007] - Gerar Linhas Impostos Retidos
     * 
     * @return Os dados estão dividos em 3 partes Descricao, de indice 0
     *         Consumo, de indice 1 Valor, de indice 2
     */
    private Vector gerarLinhasImpostosRetidos() {
	Vector retorno = new Vector();
	// Os dados estão dividos em 3 partes
	// Descricao, de indice 0
	// Consumo, de indice 1
	// Valor, de indice 2
	String[] dados = new String[3];
	// 3
	if (imovel.getRegistros6() != null) {
	    String dadosImposto = "";
	    for (int i = 0; i < imovel.getRegistros6().size(); i++) {
		ImovelReg6 imoReg6 = (ImovelReg6) imovel.getRegistros6().elementAt(i);
		String descricaoImposto = imoReg6.getDescricaoImposto();
		String percentualAliquota = Util.formatarDoubleParaMoedaReal(imoReg6.getPercentualAlicota());
		dadosImposto += descricaoImposto + "-" + percentualAliquota + "% ";
	    }
	    dados = new String[3];
	    // 1.1.2
	    dados[0] = "DED. IMPOSTOS LEI FEDERAL N.9430 DE 27/12/1996";
	    // 1.1.3
	    dados[2] = Util.formatarDoubleParaMoedaReal(imovel.getValorImpostos());
	    retorno.addElement(dados);
	    dados = new String[3];
	    // 1.1.2
	    dados[0] = dadosImposto;
	    retorno.addElement(dados);
	}
	return retorno;
    }

    private static int retornaIndicadorDiscriminar(int quantidadeMaximaLinhas, int quantidadeLinhasAtual, char servicos) {
	int indicadorDiscriminarDescricao = 1;
	int linhasRestantesDescricao = 0;
	switch (servicos) {
	case 'd':
	    // linhas que ainda aparecerão depois do débio (crédito e imposto)
	    // linhas de crédito
	    if (imovel.getRegistros5() != null) {
		linhasRestantesDescricao = linhasRestantesDescricao + 1;
	    }
	    // linhas de imposto
	    if (imovel.getRegistros6() != null) {
		linhasRestantesDescricao = linhasRestantesDescricao + 2;
	    }
	    // linhas de débito
	    if (imovel.getRegistros4() != null) {
		// linhasRestantesDescricao = linhasRestantesDescricao + 1;
		int limiteDescriminar = quantidadeMaximaLinhas - quantidadeLinhasAtual - linhasRestantesDescricao;
		int quantidadeDebitos = imovel.getRegistros4().size();
		if (quantidadeDebitos > limiteDescriminar) {
		    indicadorDiscriminarDescricao = 2;
		}
	    }
	    break;
	case 'c':
	    // linhas que ainda aparecerão depois do débio (crédito e imposto)
	    // linhas de imposto
	    if (imovel.getRegistros6() != null) {
		linhasRestantesDescricao = linhasRestantesDescricao + 2;
	    }
	    // linhas de credito
	    if (imovel.getRegistros5() != null) {
		// linhasRestantesDescricao = linhasRestantesDescricao + 1;
		int limiteDescriminar = quantidadeMaximaLinhas - quantidadeLinhasAtual - linhasRestantesDescricao;
		int quantidadeCreditos = imovel.getRegistros5().size();
		if (quantidadeCreditos > limiteDescriminar) {
		    indicadorDiscriminarDescricao = 2;
		}
	    }
	    break;
	}
	return indicadorDiscriminarDescricao;
    }

    private static String dividirLinha(int fonte, int tamanhoFonte, int x, int y, String texto, int tamanhoLinha, int deslocarPorLinha) {
	String retorno = "";
	int contador = 0;
	int i;
	for (i = 0; i < texto.length(); i += tamanhoLinha) {
	    contador += tamanhoLinha;
	    if (contador > texto.length()) {
		retorno += "T " + fonte + " " + tamanhoFonte + " " + x + " " + y + " " + texto.substring(i, texto.length()).trim() + "\n";
	    } else {
		retorno += "T " + fonte + " " + tamanhoFonte + " " + x + " " + y + " " + texto.substring(i, contador).trim() + "\n";
	    }
	    y += deslocarPorLinha;
	}
	return retorno;
    }

    private String formarLinha(int font, int tamanhoFonte, int x, int y, String texto, int adicionarColuna, int adicionarLinha) {
	return "T " + font + " " + tamanhoFonte + " " + (x + adicionarColuna) + " " + (y + adicionarLinha) + " " + texto + "\n";
    }

    private Vector categoriasEconomias(Vector regsTipo2) {
	Vector retorno = new Vector();
	int quantidadeEconomiasResidencial = 0;
	int quantidadeEconomiasComercial = 0;
	int quantidadeEconomiasIndustrial = 0;
	int quantidadeEconomiasPublico = 0;
	String descricaoResidencial = "";
	String descricaoComercial = "";
	String descricaoIndustrial = "";
	String descricaoPublico = "";
	for (int i = 0; i < regsTipo2.size(); i++) {
	    ImovelReg2 reg2 = (ImovelReg2) regsTipo2.elementAt(i);
	    // if ( controladorImoveis.tipoTarifaPorCategoria( imovel ) ){
	    // retorno += formarLinha( 0, 0, 510, 418,
	    // reg2.getDescricaoAbreviadaCategoria(), i*58, 0 );
	    // } else {
	    // retorno += formarLinha( 0, 0, 510, 418,
	    // reg2.getCodigoSubcategoria(), i*51, 0 );
	    // }
	    String descricaoCategoria = reg2.getDescricaoCategoria();
	    if (descricaoCategoria.length() > 8) {
		descricaoCategoria = descricaoCategoria.substring(0, 8);
	    }
	    switch (reg2.getCodigoCategoria()) {
	    case ImovelReg2.RESIDENCIAL:
		quantidadeEconomiasResidencial += reg2.getQtdEconomiasSubcategoria();
		descricaoResidencial = descricaoCategoria;
		break;
	    case ImovelReg2.COMERCIAL:
		quantidadeEconomiasComercial += reg2.getQtdEconomiasSubcategoria();
		descricaoComercial = descricaoCategoria;
		break;
	    case ImovelReg2.INDUSTRIAL:
		quantidadeEconomiasIndustrial += reg2.getQtdEconomiasSubcategoria();
		descricaoIndustrial = descricaoCategoria;
		break;
	    case ImovelReg2.PUBLICO:
		quantidadeEconomiasPublico += reg2.getQtdEconomiasSubcategoria();
		descricaoPublico = descricaoCategoria;
		break;
	    }
	}
	if (quantidadeEconomiasResidencial > 0) {
	    Object[] dadosResidencial = new Object[2];
	    dadosResidencial[0] = descricaoResidencial;
	    dadosResidencial[1] = new Integer(quantidadeEconomiasResidencial);
	    retorno.addElement(dadosResidencial);
	}
	if (quantidadeEconomiasComercial > 0) {
	    Object[] dadosComercial = new Object[2];
	    dadosComercial[0] = descricaoComercial;
	    dadosComercial[1] = new Integer(quantidadeEconomiasComercial);
	    retorno.addElement(dadosComercial);
	}
	if (quantidadeEconomiasIndustrial > 0) {
	    Object[] dadosIndustrial = new Object[2];
	    dadosIndustrial[0] = descricaoIndustrial;
	    dadosIndustrial[1] = new Integer(quantidadeEconomiasIndustrial);
	    retorno.addElement(dadosIndustrial);
	}
	if (quantidadeEconomiasPublico > 0) {
	    Object[] dadosPublico = new Object[2];
	    dadosPublico[0] = descricaoPublico;
	    dadosPublico[1] = new Integer(quantidadeEconomiasPublico);
	    retorno.addElement(dadosPublico);
	}
	return retorno;
    }

    public String imprimirNotificacaoDebito() {
		String retorno = "";
		try {

		    retorno = "! 0 200 200 1720 1\n";

			//Daniel - incluindo data de impressao na conta
		    //Versao do Leitura e Impressao Simultanea e
		    //Data e hora de impressão
		    retorno +=
		    "T 0 2 135 121 " + "Versao: " + Fachada.getInstancia().getVersao() + " - "+ Util.getDataHora() + " /" + imovel.getQuantidadeContasImpressas() + "\n";
		    // Matricula do imovel
		    retorno +=
		    // "T 0 0 341 80 Matricula:\n" +
		    "T 7 1 464 90 " + imovel.getMatricula() + "\n";
		    // Ano mes de referencia da conta
		    retorno +=
		    // "T 0 0 596 80 Referencia:\n" +
		    "T 7 1 669 90 " + Util.retornaDescricaoAnoMes(imovel.getAnoMesConta()) + "\n";
		    // Cnpj da empresa
		    retorno +=
		    // "T 7 0 46 186 CNPJ:\n" +
		    "T 0 0 201 47 " + Util.formatarCnpj(ImovelReg11.getInstancia().getRegistro11().getCnpjEmpresa()).trim() + "\n";
		    // Inscrição estadual
		    retorno +=
		    // "T 7 0 46 217 INSC. ESTADUAL:\n" +
		    "T 0 0 285 64 " + ImovelReg11.getInstancia().getRegistro11().getInscricaoEstadualEmpresa().trim() + "\n";
		    // Grupo
		    retorno += formarLinha(0, 0, 222, 81, imovel.getGrupoFaturamento() + "", 0, 0);
		  retorno +=
		    formarLinha(0, 0, 140, 108, (imovel.getEnderecoAtendimento() != null && !imovel.getEnderecoAtendimento().equals("") ? imovel.getEnderecoAtendimento() + " - " : "")
			    + (imovel.getTelefoneLocalidadeDDD() != null && !imovel.getTelefoneLocalidadeDDD().equals("") ? imovel.getTelefoneLocalidadeDDD().trim() : ""), 0, 0);
		    String cpfCnpjFormatado = "";
		    if (imovel.getCpfCnpjCliente() != null && !imovel.getCpfCnpjCliente().equals("")) {
			cpfCnpjFormatado = imovel.getCpfCnpjCliente().trim();
		    }
		    // Dados do cliente
		    if (imovel.getEnderecoEntrega().trim().length() == 0){
			    retorno +=
		    	formarLinha(0, 2, 52, 172, imovel.getNomeUsuario().trim(), 0, 0) + formarLinha(0, 2, 52, 199, cpfCnpjFormatado, 0, 0) + dividirLinha(0, 2, 434, 169, imovel.getEndereco(), 40, 27);
		    }else{
			    retorno +=
		    	formarLinha(0, 2, 52, 172, imovel.getNomeUsuario().trim(), 0, 0) + formarLinha(0, 2, 52, 199, cpfCnpjFormatado, 0, 0) + dividirLinha(0, 2, 434, 169, imovel.getEnderecoEntrega(), 40, 27);
		    }
		    // Inscricao
		    retorno += formarLinha(7, 0, 15, 250, imovel.getInscricaoFormatada(), 0, 0);
		    // Codigo da Rota
		    retorno += formarLinha(7, 0, 315, 250, imovel.getCodigoRota() + "", 0, 0);
		    // Sequencial da Rota
		    retorno += formarLinha(7, 0, 415, 250, imovel.getSequencialRota() + "", 0, 0);
		    // Verificamos é por categoria ou subcategoria
		    Vector regsTipo2 = imovel.getRegistros2();
		    Vector quantidadeEconomias = categoriasEconomias(regsTipo2);
		    
		    for (int i = 0; i < quantidadeEconomias.size(); i++) {
				Object[] dadosCategoria = (Object[]) quantidadeEconomias.elementAt(i);
				retorno += formarLinha(0, 0, 470, 254, dadosCategoria[0] + "", i * 85, 0);
				retorno += formarLinha(7, 0, 539, 250, dadosCategoria[1] + "", i * 85, 0);
		    }
		    
		    // Numero do Hidrometro
		    String hidrometro = "NÃO MEDIDO";
		    String dataInstacao = Constantes.NULO_STRING;
		    String situacaoAgua = imovel.getDescricaoSitLigacaoAgua(imovel.getSituacaoLigAgua());
		    String situacaoEsgoto = imovel.getDescricaoSitLigacaoEsgoto(imovel.getSituacaoLigEsgoto());
		    ImovelReg8 registro8Agua = imovel.getRegistro8(Constantes.LIGACAO_AGUA);
		    ImovelReg8 registro8Poco = imovel.getRegistro8(Constantes.LIGACAO_POCO);
		    int tipoConsumo = 0;
		    
		    if (registro8Agua != null) {
				hidrometro = registro8Agua.getNumeroHidrometro();
				dataInstacao = Util.dateToString(registro8Agua.getDataInstalacao());

		    } else if (registro8Poco != null) {
				hidrometro = registro8Poco.getNumeroHidrometro();
				dataInstacao = Util.dateToString(registro8Poco.getDataInstalacao());
		
		    }
		    
		    double valorDebitosAnteriores = imovel.getValorDebitosAnteriores();

		    // Hidrometro
		    retorno += formarLinha(7, 0, 48, 301, hidrometro, 0, 0);
		    // Data da Instalação
		    retorno += formarLinha(7, 0, 248, 301, dataInstacao, 0, 0);
		    // Situacao da ligacao de Agua

		    if (situacaoAgua.length() > 13) {
		    	retorno += formarLinha(7, 0, 446, 301, situacaoAgua.substring(0, 13), 0, 0);
		    } else {
		    	retorno += formarLinha(7, 0, 446, 301, situacaoAgua, 0, 0);
		    }
		    // Situacao da ligacao de esgoto
		    retorno += formarLinha(7, 0, 627, 301, situacaoEsgoto, 0, 0);

		    retorno += formarLinha(7, 1, 300, 350, "AVISO DE DÉBITO", 0, 0);	

		    retorno += formarLinha(7, 0, 37, 390, "Prezado Cliente,", 0, 0);

  		    retorno += dividirLinha(7, 0, 37, 426, "Até o presente momento não registramos a confirmação do Paga-mento da(s) conta(s) abaixo:", 61, 24);
		    
		    // Referencia Conta / Vencimento / Valor (R$)
		    retorno += formarLinha(7, 0, 53, 486, "MÊS REFERENCIA", 0, 0) + formarLinha(7, 0, 400, 486, "VENCIMENTO", 0, 0) + formarLinha(7, 0, 697, 486, "VALOR(R$)", 0, 0);
	
		    int totalLinhas = imovel.getRegistros7().size();
		    int qtdLinhasDebitoImpressas = 0;
		    double subTotal = 0;
		    
		    for (int i = 0; i < totalLinhas; i++) {
				ImovelReg7 imovelReg7 = ((ImovelReg7) imovel.getRegistros7().elementAt(i));
				
				if (totalLinhas > 11){
					if ((totalLinhas - i) == 11){
						retorno += formarLinha(7, 0, 83, 510, "OUTROS", 0, qtdLinhasDebitoImpressas * 24);
						retorno += formarLinha(7, 0, 405, 510, "     --", 0, qtdLinhasDebitoImpressas * 24);
						retorno += formarLinha(7, 0, 707, 510, Util.formatarDoubleParaMoedaReal(subTotal), 0, qtdLinhasDebitoImpressas * 24);
						qtdLinhasDebitoImpressas++;
						
					}
					if ( (totalLinhas - i) > 11 ){
						subTotal += imovelReg7.getValor();
					}else{
						retorno += formarLinha(7, 0, 83, 510, Util.formatarAnoMesParaMesAno(imovelReg7.getAnoMes() + ""), 0, qtdLinhasDebitoImpressas * 24);
						retorno += formarLinha(7, 0, 405, 510, Util.dateToString(imovelReg7.getDataVencimento()), 0, qtdLinhasDebitoImpressas * 24);
						retorno += formarLinha(7, 0, 707, 510, Util.formatarDoubleParaMoedaReal(imovelReg7.getValor()), 0, qtdLinhasDebitoImpressas * 24);
						qtdLinhasDebitoImpressas++;

					}
				
				}else {
					
					retorno += formarLinha(7, 0, 83, 510, Util.formatarAnoMesParaMesAno(imovelReg7.getAnoMes() + ""), 0, qtdLinhasDebitoImpressas * 24);
					retorno += formarLinha(7, 0, 405, 510, Util.dateToString(imovelReg7.getDataVencimento()), 0, qtdLinhasDebitoImpressas * 24);
					retorno += formarLinha(7, 0, 707, 510, Util.formatarDoubleParaMoedaReal(imovelReg7.getValor()), 0, qtdLinhasDebitoImpressas * 24);
					qtdLinhasDebitoImpressas++;
				}
					
				
				
				
//				if (qtdLinhasDebitoImpressas >= 11) {
//					subTotal += imovelReg7.getValor();
//				
//				}else{
//					retorno += formarLinha(7, 0, 83, 510, Util.formatarAnoMesParaMesAno(imovelReg7.getAnoMes() + ""), 0, qtdLinhasDebitoImpressas * 24);
//					retorno += formarLinha(7, 0, 405, 510, Util.dateToString(imovelReg7.getDataVencimento()), 0, qtdLinhasDebitoImpressas * 24);
//					retorno += formarLinha(7, 0, 707, 510, Util.formatarDoubleParaMoedaReal(imovelReg7.getValor()), 0, qtdLinhasDebitoImpressas * 24);
//				}
//				
//				if(qtdLinhasDebitoImpressas == (totalLinhas-1)){
//					qtdLinhasDebitoImpressas = 11;
//					retorno += formarLinha(7, 0, 83, 510, "   --", 0, qtdLinhasDebitoImpressas * 24);
//					retorno += formarLinha(7, 0, 405, 510, "     --", 0, qtdLinhasDebitoImpressas * 24);
//					retorno += formarLinha(7, 0, 707, 510, Util.formatarDoubleParaMoedaReal(subTotal), 0, qtdLinhasDebitoImpressas * 24);
//					break;
//				}

		    }
		    
  		    retorno += dividirLinha(7, 0, 37, (546 + (qtdLinhasDebitoImpressas*24)) , "Pertencente(s) ao imóvel localizado na " + imovel.getEndereco() + ".", 61, 24);

  		    retorno += dividirLinha(7, 0, 37, (606 + (qtdLinhasDebitoImpressas*24)) , "De acordo com a legislação em vigor (Lei número 11.445/07), o não pagamento " +
  		    		"deste débito nos autoriza a suspender o forne-cimento de água/esgoto para o seu imóvel", 61, 24);
  		    
  		    retorno += dividirLinha(7, 0, 37, (690 + (qtdLinhasDebitoImpressas*24)) , "Estamos à sua disposição em nossas lojas de atendimento, escri-tórios regionais e " +
  		    		"no telefone 0800-7071-195, ligação gratuita, para esclarecer qualquer dúvida.", 63, 24);
  		    
  		    retorno += dividirLinha(7, 0, 37, (774 + (qtdLinhasDebitoImpressas*24)) , "Para sua comodidade, utilize o código de barras abaixo para  pagamento deste débito" +
  		    		" e fique sempre em dia com a COSANPA.  Lembre-se da importância que a água tratada tem na sua vida.", 61, 24);
  		   
  		    retorno += dividirLinha(7, 0, 37, (858 + (qtdLinhasDebitoImpressas*24)) , "Caso o(s) débito(s) já esteja(m) quitado(s), pedimos desculpas e que desconsidere este aviso.", 62, 24);

  		    retorno += formarLinha(7, 1, 160, 1210, Util.dateToString(imovel.getDataVencimento()), 0, 0);
  		    retorno += formarLinha(4, 0, 640, 1210, Util.formatarDoubleParaMoedaReal(valorDebitosAnteriores), 0, 0);
  		    retorno += formarLinha(0, 2, 155, 1265, "Nº Documento: ", 0, 0);
  		    retorno += formarLinha(5, 0, 265, 1266, imovel.getNumeroDocumentoNotificacaoDebito(), 0, 0);
  		    retorno += formarLinha(0, 2, 440, 1265, "OPCAO PELO DEB. AUTOMATICO: ", 0, 0) + formarLinha(5, 0, 663, 1266, ( imovel.getOpcaoDebitoAutomatico() == Constantes.NULO_INT ? "" : imovel.getOpcaoDebitoAutomatico()+"" ), 0, 0);

		 // Canhoto do aviso
		  //----------------------------------------------------------------------------------------------------------------------------------		    
//		    retorno += linhas;
//		    retorno += formarLinha(0, 2, 35, 1463, "EMISSAO:", 0, 0) + formarLinha(7, 1, 81, 1478, Util.dateToString(Util.dataAtual()), 0, 0);
//		    retorno += formarLinha(0, 2, 235, 1463, "TOTAL A PAGAR:", 0, 0) + formarLinha(4, 0, 640, 1478, Util.formatarDoubleParaMoedaReal(valorDebitosAnteriores), 0, 0);
//		    retorno += formarLinha(0, 2, 340, 1587, "MATRICULA", 0, 0) + formarLinha(0, 2, 443, 1587, "N. DOCUMENTO", 0, 0) + formarLinha(0, 2, 558, 1587, "VENCIMENTO", 0, 0) + formarLinha(0, 2, 694, 1587, "TOTAL A PAGAR", 0, 0) 
//		    + formarLinha(0, 2, 340, 1619, imovel.getMatricula() + "", 0, 0)
//			+ formarLinha(0, 2, 443, 1619, imovel.getNumeroDocumentoNotificacaoDebito() + "", 0, 0) 
//			+ formarLinha(0, 2, 558, 1619, Util.dateToString(imovel.getDataVencimento()), 0, 0) 
//			+ formarLinha(0, 2, 694, 1619, Util.formatarDoubleParaMoedaReal(valorDebitosAnteriores), 0, 0);
//		    
//		    String representacaoNumericaCodBarraFormatada = Util.formatarCodigoBarras(imovel.getNumeroCodigoBarraNotificacaoDebito());
//		    
//		    retorno += formarLinha(5, 0, 66, 1646, representacaoNumericaCodBarraFormatada, 0, 0);
//		    
//		    if (representacaoNumericaCodBarraFormatada != null && !representacaoNumericaCodBarraFormatada.equals("")) {
//				String representacaoCodigoBarrasSemDigitoVerificador = representacaoNumericaCodBarraFormatada.substring(0, 11) 
//																	+ representacaoNumericaCodBarraFormatada.substring(14, 25) 
//																	+ representacaoNumericaCodBarraFormatada.substring(28, 39)
//																	+ representacaoNumericaCodBarraFormatada.substring(42, 53);
//				retorno += "B I2OF5 1 2 90 35 1667 " + representacaoCodigoBarrasSemDigitoVerificador + "\n";
//		    }
//		    
  		    retorno += formarLinha(0, 2, 344, 1456, imovel.getMatricula() + "", 0, 0) + formarLinha(0, 2, 443, 1456, Util.formatarAnoMesParaMesAno(imovel.getAnoMesConta()), 0, 0) + formarLinha(0, 2, 558, 1456, Util.dateToString(imovel.getDataVencimento()), 0, 0) + formarLinha(0, 2, 694, 1456, Util.formatarDoubleParaMoedaReal(valorDebitosAnteriores), 0, 0);

		    retorno += "FORM\n" + "PRINT ";
		} catch (Exception ex) {
		    ex.printStackTrace();
		    Util.mostrarErro("Erro na impressão...", ex);
		}
		System.out.println( retorno );
		return retorno;
    
    }

    /**
     * Método que imprime o extrato de consumo do macro medidor dos imóveis
     * condomínio seguindo layout compesa
     * 
     * @author Bruno Barros
     * @date 09/02/2010
     */
    public String imprimirExtratoConsumoMacroMedidor() {

	String retorno = "";

	try {

	    retorno = "! 0 200 200 1720 1\n";

	    // Grade do extrato de consumo
	    retorno +=
		// Caixa e linhas das leituras atual\anterior\consumo\num. de dias
		"BOX 32 411 802 435 1\n" + 
		"BOX 32 436 802 482 1\n" + 
		"LINE 720 415 720 455 1\n" + 
		"LINE 403 415 403 477 1\n" + 
		"LINE 290 415 290 477 1\n" + 
		"BOX 30 848 812 875 1\n";
	    
// Daniel - incluindo data de impressao na conta
	    //Data e hora de impressão
	    retorno += "T 0 2 138 121 " + "Versao: " + Fachada.getInstancia().getVersao() + " - "+ Util.getDataHora() + "\n";

	    // Matricula do imovel
	    retorno += "T 7 1 464 90 " + imovel.getMatricula() + "\n";

	    // Ano Mes da Conta
	    retorno += "T 7 1 669 90 " + Util.retornaDescricaoAnoMes(imovel.getAnoMesConta()) + "\n";

	    // Cnpj da empresa
	    retorno += "T 0 0 201 47 " + Util.formatarCnpj(ImovelReg11.getInstancia().getRegistro11().getCnpjEmpresa()).trim() + "\n";
	    // Inscrição estadual
	    retorno += "T 0 0 285 64 " + ImovelReg11.getInstancia().getRegistro11().getInscricaoEstadualEmpresa().trim() + "\n";
	    // Grupo
	    retorno += formarLinha(0, 0, 222, 81, imovel.getGrupoFaturamento() + "", 0, 0);
	    // Escritorio de atendimento
	    retorno += formarLinha(0, 0, 140, 108, (imovel.getEnderecoAtendimento() != null && !imovel.getEnderecoAtendimento().equals("") ? imovel.getEnderecoAtendimento() + " - " : "")
		    + (imovel.getTelefoneLocalidadeDDD() != null && !imovel.getTelefoneLocalidadeDDD().equals("") ? imovel.getTelefoneLocalidadeDDD().trim() : ""), 0, 0);
	    String cpfCnpjFormatado = "";
	    if (imovel.getCpfCnpjCliente() != null && !imovel.getCpfCnpjCliente().equals("")) {
		cpfCnpjFormatado = imovel.getCpfCnpjCliente().trim();
	    }
	    // Dados do cliente
	    retorno +=
	    // "T 7 0 46 254 Dados do Cliente\n" +
	    formarLinha(0, 2, 52, 172, imovel.getNomeUsuario().trim(), 0, 0) + formarLinha(0, 2, 52, 199, cpfCnpjFormatado, 0, 0) + dividirLinha(0, 2, 434, 169, imovel.getEndereco(), 40, 27);
	    // Inscricao
	    retorno +=
	    // "T 7 0 112 394 Inscricao\n" +
	    formarLinha(7, 0, 15, 250, imovel.getInscricaoFormatada(), 0, 0);
	    // Codigo da Rota
	    retorno +=
	    // "T 7 0 306 394 Rota\n" +
	    formarLinha(7, 0, 315, 250, imovel.getCodigoRota() + "", 0, 0);
	    // Sequencial da Rota
	    retorno +=
	    // "T 7 0 373 394 Seq. Rota\n" +
	    formarLinha(7, 0, 415, 250, imovel.getSequencialRota() + "", 0, 0);
	    // Verificamos é por categoria ou subcategoria
	    Vector regsTipo2 = imovel.getRegistros2();
	    Vector quantidadeEconomias = categoriasEconomias(regsTipo2);
	    for (int i = 0; i < quantidadeEconomias.size(); i++) {
			Object[] dadosCategoria = (Object[]) quantidadeEconomias.elementAt(i);
			retorno += formarLinha(0, 0, 470, 254, dadosCategoria[0] + "", i * 85, 0);
			retorno += formarLinha(7, 0, 539, 250, dadosCategoria[1] + "", i * 85, 0);
	    }
	    // Numero do Hidrometro
	    String hidrometro = "NÃO MEDIDO";
	    String dataInstacao = Constantes.NULO_STRING;
	    String situacaoAgua = imovel.getDescricaoSitLigacaoAgua(imovel.getSituacaoLigAgua());
	    String situacaoEsgoto = imovel.getDescricaoSitLigacaoEsgoto(imovel.getSituacaoLigEsgoto());
	    String leituraAnteriorInformada = Constantes.NULO_STRING;
	    String leituraAtualInformada = Constantes.NULO_STRING;
	    String leituraAnteriorFaturada = Constantes.NULO_STRING;
	    String leituraAtualFaturada = Constantes.NULO_STRING;

	    String consumo = Constantes.NULO_STRING;
	    String diasConsumo = Constantes.NULO_STRING;
	    Consumo consumoAgua = null;
	    Consumo consumoEsgoto = null;
	    ImovelReg8 registro8Agua = imovel.getRegistro8(Constantes.LIGACAO_AGUA);
	    ImovelReg8 registro8Poco = imovel.getRegistro8(Constantes.LIGACAO_POCO);
	    String dataLeituraAnteriorInformada = "";
	    String dataLeituraAtualInformada = "";
	    String dataLeituraAnteriorFaturada = "";
	    String dataLeituraAtualFaturada = "";
	    String media = "0";
	    int tipoConsumo = 0;
	   
	    if (registro8Agua != null) {

			hidrometro = registro8Agua.getNumeroHidrometro();
			media = String.valueOf(registro8Agua.getConsumoMedio());
	
			if (registro8Agua.getLeituraAnteriorInformada() != Constantes.NULO_INT){
				leituraAnteriorInformada = registro8Agua.getLeituraAnteriorInformada() + "";
			}
			dataLeituraAnteriorFaturada = Util.dateToString(registro8Agua.getDataLeituraAnteriorFaturada());
			dataLeituraAnteriorInformada = Util.dateToString(registro8Agua.getDataLeituraAnteriorInformada());
			dataLeituraAtualInformada = Util.dateToString(registro8Agua.getDataLeitura());
			dataInstacao = Util.dateToString(registro8Agua.getDataInstalacao());
	
			if(registro8Agua.getLeituraAnteriorFaturamento() != Constantes.NULO_INT){
				leituraAnteriorFaturada = String.valueOf(registro8Agua.getLeituraAnteriorFaturamento());
			}
			
		    if (registro8Agua.getLeituraAtualFaturamento() != Constantes.NULO_INT) {
		    	
		    	leituraAtualFaturada = String.valueOf(imovel.getConsumoAgua().getLeituraAtual()); 
		    	dataLeituraAtualFaturada = Util.dateToString(registro8Agua.getDataLeituraAtualFaturamento());
				diasConsumo = Long.toString(registro8Agua.getQtdDiasAjustado());

		    } else {

		    	leituraAtualFaturada = "";
		    }
			
			if (registro8Agua.getLeitura()!= Constantes.NULO_INT ){
				
				leituraAtualInformada = registro8Agua.getLeitura() + "";				
			}

		    if (consumoAgua != null) {
	
				tipoConsumo = consumoAgua.getTipoConsumo();
				
			    if (registro8Agua.getLeituraAtualFaturamento() != Constantes.NULO_INT) {
			    	consumo = consumoAgua.getConsumoCobradoMes() + "";
	
			    } else {
	
			    	consumo = consumoAgua.getConsumoCobradoMes() + "";
					diasConsumo = consumoAgua.getDiasConsumo() + "";
			    }
	
				if (registro8Agua.getLeitura()!= Constantes.NULO_INT &&
						consumoAgua.getLeituraAtual() != Constantes.NULO_INT) {
				} else {
				    leituraAtualInformada = "";
				    dataLeituraAtualInformada = "";
				}
			}

	    } else if (registro8Poco != null) {

			media = String.valueOf(registro8Poco.getConsumoMedio());
			hidrometro = registro8Poco.getNumeroHidrometro();
	
			if (registro8Poco.getLeituraAnteriorInformada() != Constantes.NULO_INT){
				leituraAnteriorInformada = registro8Poco.getLeituraAnteriorInformada()+ "";
			}
			dataLeituraAnteriorFaturada = Util.dateToString(registro8Poco.getDataLeituraAnteriorFaturada());
			dataLeituraAnteriorInformada = Util.dateToString(registro8Poco.getDataLeituraAnteriorInformada());
			dataLeituraAtualInformada = Util.dateToString(registro8Poco.getDataLeitura());
			dataInstacao = Util.dateToString(registro8Poco.getDataInstalacao());
	
			if(registro8Poco.getLeituraAnteriorFaturamento() != Constantes.NULO_INT){
				leituraAnteriorFaturada = String.valueOf(registro8Poco.getLeituraAnteriorFaturamento());
			}

			if (registro8Poco.getLeituraAtualFaturamento() != Constantes.NULO_INT) {
				
		    	leituraAtualFaturada = String.valueOf(imovel.getConsumoEsgoto().getLeituraAtual());
		    	dataLeituraAtualFaturada = Util.dateToString(registro8Poco.getDataLeituraAtualFaturamento());
				diasConsumo = Long.toString(registro8Poco.getQtdDiasAjustado());

		    } else {
		    	leituraAtualFaturada = "";
		    }

			if (registro8Poco.getLeitura()!= Constantes.NULO_INT){
			    leituraAtualInformada = registro8Poco.getLeitura() + "";				
			}

			if (consumoEsgoto != null) {
				tipoConsumo = consumoEsgoto.getTipoConsumo();
	
				if (registro8Poco.getLeituraAtualFaturamento() != Constantes.NULO_INT) {
						consumo = consumoEsgoto.getConsumoCobradoMes() + "";
			    } else {
				    	consumo = consumoEsgoto.getConsumoCobradoMes() + "";
				    	diasConsumo = consumoEsgoto.getDiasConsumo() + "";
			    }
	
				if (registro8Poco.getLeitura()!= Constantes.NULO_INT &&
						consumoEsgoto.getLeituraAtual() != Constantes.NULO_INT) {
				} else {
				    leituraAtualInformada = "";
				    dataLeituraAtualInformada = "";
				}
	
			}
	    }

	    else if (registro8Agua == null && registro8Poco == null) {

			if (consumoAgua != null) {
	
		    	leituraAtualInformada = "";
			    dataLeituraAtualInformada = "";
			    consumo = consumoAgua.getConsumoCobradoMes() + "";
	
			    // Daniel - Numero de dias de consumo nunca deve ser ZERO mesmo para imoveis fixos.
			    diasConsumo =  Long.toString(Util.quantidadeDiasMes(Calendar.getInstance())) + "";
			}
	    }
	    // Hidrometro
	    retorno += formarLinha(7, 0, 48, 301, hidrometro, 0, 0);
	    // Data da Instalação
	    retorno += formarLinha(7, 0, 248, 301, dataInstacao, 0, 0);
	    // Situacao da ligacao de Agua
	    retorno += formarLinha(7, 0, 446, 301, situacaoAgua, 0, 0);
	    // Situacao da ligacao de esgoto
	    retorno += formarLinha(7, 0, 627, 301, situacaoEsgoto, 0, 0);

	    
//	  Daniel - LEITURA INFORMADA
	    // Leitura Anterior e Atual
	    retorno += formarLinha(7, 0, 188, 330, "LEITURA", 0, 0) + formarLinha(7, 0, 205, 354, leituraAnteriorInformada, 0, 0) + formarLinha(7, 0, 205, 378, leituraAtualInformada, 0, 0);
	    // Data Leitura Anterior e Atual
	    retorno += formarLinha(7, 0, 320, 330, "DATA", 0, 0) + formarLinha(7, 0, 298, 354, dataLeituraAnteriorInformada, 0, 0) + formarLinha(7, 0, 298, 378, dataLeituraAtualInformada, 0, 0);

	    retorno += formarLinha(7, 0, 37, 354, "ANTERIOR", 0, 0) + formarLinha(7, 0, 37, 378, "ATUAL", 0, 0);

//Daniel - imprimindo Anormalidade de Leitura quando existe.
		if (imovel.getConsumoAgua() != null){
			if (imovel.getConsumoAgua().getAnormalidadeLeituraFaturada() != 0 &&
					imovel.getConsumoAgua().getAnormalidadeLeituraFaturada() != Constantes.NULO_INT	){

				retorno += formarLinha(0, 2, 460, 374, "ANORM. LEITURA: " + FileManager.getAnormalidade(imovel.getConsumoAgua().getAnormalidadeLeituraFaturada()).getDescricao(), 0, 0);
			}
		}
	    
//	    Daniel - LEITURA FATURADA
		
	    // Leitura Anterior
	    retorno += formarLinha(7, 0, 173, 412, "FATURADO", 0, 0) + formarLinha(7, 0, 205, 436, leituraAnteriorFaturada, 0, 0) + formarLinha(7, 0, 205, 460, leituraAtualFaturada, 0, 0);
	    // Leitura Atual
	    retorno += formarLinha(7, 0, 320, 412, "DATA", 0, 0) + formarLinha(7, 0, 298, 436, dataLeituraAnteriorFaturada, 0, 0) + formarLinha(7, 0, 298, 460, dataLeituraAtualFaturada, 0, 0);
	    // Consumo
    	retorno +=	formarLinha(7, 0, 412, 412, ControladorConta.getInstancia().getTipoConsumoToPrint(tipoConsumo), 0, 0);
    	retorno +=	formarLinha(7, 0, 511, 436, String.valueOf(Integer.parseInt(leituraAtualFaturada) - Integer.parseInt(leituraAnteriorFaturada)) , 0, 0);
	    // Numero de dias
	    retorno += formarLinha(7, 0, 745, 412, "DIAS", 0, 0) + formarLinha(7, 0, 760, 436, diasConsumo, 0, 0);

//Daniel - imprimindo Anormalidade de Consumo quando existe.
		if (imovel.getConsumoAgua() != null){
			String anormalidadeConsumo = Util.validarAnormalidadeConsumo(imovel.getConsumoAgua());
			if( anormalidadeConsumo != null){
				retorno += formarLinha(0, 2, 460, 460, "ANORM. CONSUMO: " + anormalidadeConsumo, 0, 0);
			}
		}
	    // Média de dias
	    // Data das Leituras
	    retorno += formarLinha(7, 0, 37, 436, "ANTERIOR", 0, 0) + formarLinha(7, 0, 37, 460, "ATUAL", 0, 0);
	    
	    Vector regsTipo3 = imovel.getRegistros3();

	    if (regsTipo3 != null) {

	    	retorno += formarLinha(7, 0, 50, 499, "ULTIMOS CONSUMOS", 0, 0);
	    	retorno += "LINE 115 525 115 671 1\n";
		
		    // Ultimos Consumos
			for (int i = 0; i < regsTipo3.size(); i++) {

				ImovelReg3 reg3 = (ImovelReg3) regsTipo3.elementAt(i);

			    retorno += formarLinha(0, 2, 44, 520, Util.getAnoBarraMesReferencia(reg3.getAnoMesReferencia()) + "", 0, i * 25);
			    String anormalidade = "";
			    if (reg3.getAnormalidadeLeitura() != Constantes.NULO_INT && reg3.getAnormalidadeLeitura() != 0) {
			    	anormalidade = "A. Leit.:" + reg3.getAnormalidadeLeitura() + "";
			    } else if (reg3.getAnormalidadeConsumo() != Constantes.NULO_INT && reg3.getAnormalidadeConsumo() != 0) {
			    	anormalidade = "A. Cons.:" + reg3.getAnormalidadeConsumo() + "";
			    }
			    retorno += formarLinha(0, 2, 127, 520, Util
			    		.verificarNuloInt(reg3.getConsumo()) + " m3 " + anormalidade, 0, i * 25);
			}
	    }else{
	    	retorno += formarLinha(7, 0, 50, 499, "HISTORICO DE CONSUMO", 0, 0) + formarLinha(7, 0, 50, 520, "INEXISTENTE", 0, 0);

	    }

		EfetuarRateioConsumoDispositivoMovelHelper helper = imovel.getEfetuarRateioConsumoDispositivoMovelHelper();

		retorno += formarLinha(7, 0, 75, 672, "MEDIA(m3):", 0, 0) + formarLinha(7, 0, 195, 672, media, 0, 0);

	    retorno += formarLinha(7, 0, 200, 700, "EXTRATO DE CONSUMO DO MACROMEDIDOR", 0, 0);
	    retorno += formarLinha(7, 0, 53, 725, "CONSUMO DO IMÓVEL CONDOMÍNIO", 0, 0) + formarLinha(7, 0, 571, 725, imovel.getConsumoAgua().getConsumoCobradoMes() + " m3", 0, 0);
	    retorno += formarLinha(7, 0, 53, 750, "SOMA DOS CONSUMOS DOS IMÓVEIS VINCULADOS", 0, 0) + formarLinha(7, 0, 571, 750, imovel.getConsumoAgua().getConsumoCobradoMesImoveisMicro() + " m3", 0, 0);
	    retorno += formarLinha(7, 0, 53, 775, "QUANTIDADE IMÓVEIS VINCULADOS", 0, 0) + formarLinha(7, 0, 571, 775, helper.getQuantidadeEconomiasAguaTotal()+ "", 0, 0);

   		int consumoAguaASerRateadoAgua = imovel.getConsumoAgua().getConsumoCobradoMesOriginal()	- helper.getConsumoLigacaoAguaTotal();

   		if (consumoAguaASerRateadoAgua > 0){
	    	retorno += formarLinha(7, 0, 53, 800, "VALOR RATEADO", 0, 0) + formarLinha(7, 0, 571, 800, " R$ " + helper.getContaParaRateioAgua(), 0, 0);
	    } else {
	    	retorno += formarLinha(7, 0, 53, 800, "VALOR RATEADO", 0, 0) + formarLinha(7, 0, 571, 800, " R$ 0,00", 0, 0);
	    }
   		
	    String valorRateio = Util.formatarDoubleParaMoedaReal(helper.getContaParaRateioAgua() / helper.getQuantidadeEconomiasAguaTotal());
	    retorno += formarLinha(7, 0, 53, 825, "VALOR RATEADO POR UNIDADE", 0, 0) + formarLinha(7, 0, 571, 825, " R$ " + valorRateio, 0, 0);

	    retorno += formarLinha(7, 0, 367, 850, "IMPORTANTE", 0, 0);
	    retorno += formarLinha(7, 0, 53, 900, "CASO O VALOR DO RATEIO ESTEJA ELEVADO", 0, 0);
	    retorno += formarLinha(7, 0, 63, 925, "1. Confirme a leitura do macro", 0, 0);
	    retorno += formarLinha(7, 0, 63, 950, "2. Verifique os reservatórios", 0, 0);
	    retorno += formarLinha(7, 0, 63, 975, "3. Verifique se há apartamento ligado clandestino", 0, 0);
	    retorno += formarLinha(7, 0, 53, 1025, "QUALQUER IRREGULARIDADE COMUNIQUE A COSANPA ATRAVÉS DO", 0, 0);
	    retorno += formarLinha(7, 0, 53, 1050, "SERTOR DE ATENDIMENTO", 0, 0);
	    retorno += formarLinha(7, 0, 53, 1075, "RATEIO: Obtido atraves da diferença do consumo entre", 0, 0);
	    retorno += formarLinha(7, 0, 53, 1100, "o macromedidor e os consumos dos apartamentos", 0, 0);
	    retorno += formarLinha(0, 2, 344, 1456, imovel.getMatricula() + "", 0, 0);
	    retorno += formarLinha(5, 0, 109, 1661, imovel.getGrupoFaturamento() + "", 0, 0);
	    // TODO - Verificar como pega o código da empresa que está prestando
	    // o servico a compesa
	    retorno += formarLinha(5, 0, 352, 1661, "4", 0, 0);
	    retorno += "FORM\n" + "PRINT ";
	} catch (Exception ex) {
	    ex.printStackTrace();
	    Util.mostrarErro("Erro na impressão...", ex);
	}
	System.out.println(retorno);
	return retorno;
    }
}
