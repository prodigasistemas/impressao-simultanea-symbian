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

package com.ipad.business;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.ipad.background.BackGroundTaskEnviarImoveisCondominioOnline;
import com.ipad.basic.Anormalidade;
import com.ipad.basic.Configuracao;
import com.ipad.basic.Consumo;
import com.ipad.basic.DadosRelatorio;
import com.ipad.basic.ImovelConta;
import com.ipad.basic.ImovelReg11;
import com.ipad.basic.ImovelReg12;
import com.ipad.basic.ImovelReg2;
import com.ipad.basic.ImovelReg3;
import com.ipad.basic.ImovelReg8;
import com.ipad.basic.RegistroBasico;
import com.ipad.basic.SituacaoTipo;
import com.ipad.basic.helper.EfetuarRateioConsumoDispositivoMovelHelper;
import com.ipad.component.Progress;
import com.ipad.io.FileManager;
import com.ipad.io.Repositorio;
import com.ipad.util.Constantes;
import com.ipad.util.Util;

public class ControladorConta {

    private static ControladorConta instancia = null;

// Daniel - Valor limite estipulado para cada Perfil
    // Valor limite para a conta
    public static final int VALOR_LIMITE_CONTA = 100000;
    public static final int VALOR_LIMITE_PERFIL_CORPORATIVO = 100000;
    public static final int VALOR_LIMITE_PERFIL_CONDOMINIAL = 100000;
    public static final int VALOR_LIMITE_PERFIL_GOVERNO_METROPOLITANO = 100000;
    public static final int VALOR_LIMITE_PERFIL_GOVERNO_INTERIOR = 100000;
    public static final int VALOR_LIMITE_PERFIL_GRANDE = 100000;
    public static final int VALOR_LIMITE_PERFIL_ESPECIAL = 100000;
    public static final int VALOR_LIMITE_PERFIL_NORMAL = 100000;
    public static final int VALOR_LIMITE_PERFIL_GRANDE_MES = 100000;
    public static final int VALOR_LIMITE_PERFIL_COLABORADOR = 100000;
    public static final int VALOR_LIMITE_PERFIL_BONUS_SOCIAL = 100000;

    /**
     * Medição Tipo
     */
    public static final int LIGACAO_AGUA = 1;
    public static final int LIGACAO_POCO = 2;

    public static final int SIM = 1;
    public static final int NAO = 2;

    public static final int CONSUMO_TIPO_INDEFINIDO = 0;
    public static final int CONSUMO_TIPO_REAL = 1;
    public static final int CONSUMO_TIPO_AJUSTADO = 2;
    public static final int CONSUMO_TIPO_MEDIA_HIDR = 3;
    public static final int CONSUMO_TIPO_INFORMADO = 4;
    public static final int CONSUMO_TIPO_NAO_MEDIDO = 5;
    public static final int CONSUMO_TIPO_ESTIMADO = 6;
    public static final int CONSUMO_TIPO_MINIMO_FIX = 7;
    public static final int CONSUMO_TIPO_SEM = 8;
    public static final int CONSUMO_TIPO_MEDIA_IMOV = 9;
    public static final int CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL = 10;

    public static final int LEITURA_SITU_INDEFINIDO = 0;
    public static final int LEITURA_SITU_REALIZADA = 1;
    public static final int LEITURA_SITU_NAO_REALIZ = 2;
    public static final int LEITURA_SITU_CONFIRMADA = 3;
    public static final int LEITURA_SITU_ALTERADA = 4;

    public static final int ANORM_HIDROMETRO_PARADO = 30;
    public static final int ANORM_HIDR_SEM_CONSUMO = 38;
    public static final int ANORM_HIDR_LEITURA_IMPEDIDA_CLIENTE = 4;
    public static final int ANORM_HIDR_PORTAO_FECHADO = 5;

    /*
     * Leitura Anormalidade Consumo
     */
    private static final int NAO_OCORRE = 0;
    private static final int MINIMO = 1;
    private static final int MEDIA = 2;
    private static final int NORMAL = 3;
    private static final int MAIOR_ENTRE_O_CONSUMO_MEDIO = 5;
    private static final int MENOR_ENTRE_O_CONSUMO_MEDIO = 6;
    public static final int FIXO = 7;
    public static final int NAO_MEDIDO = 8;

    /**
     * Leitura Anormalidade Leitura
     */
    private static final int ANTERIOR_MAIS_A_MEDIA = 0;
    private static final int ANTERIOR = 1;
    private static final int ANTERIOR_MAIS_O_CONSUMO = 2;
    private static final int INFORMADO = 3;

    private Consumo consumoAgua;
    private Consumo consumoEsgoto;


    public ControladorConta() {

    }

    public static ControladorConta getInstancia() {
	if (instancia == null) {
	    instancia = new ControladorConta();
	}
	return instancia;
    }

    /**
     * [UC0740] Calcular Consumo no Dispositivo Móvel
     */
    public void calcularConta() {

	// [UC0740] 2.
   	if ( (getImovelSelecionado().getIndcFaturamentoAgua() == SIM) ||
   		 (getImovelSelecionado().getIndcFaturamentoAgua() == NAO && getImovelSelecionado().isImovelMicroCondominio()) ){

	    // [UC0740] 2.1.
	    if (getImovelSelecionado().getNumeroHidrometro(LIGACAO_AGUA) != null) {
			this.calcularConsumo(LIGACAO_AGUA);
	
			// Caso o consumo a ser cobrado no mês seja menor que o consumo
			// mínimo de água
			if ((getImovelSelecionado().getConsumoMinAgua() != Constantes.NULO_INT)
				&& (consumoAgua.getConsumoCobradoMes() < getImovelSelecionado().getConsumoMinAgua())) {
			    // Seta o consumo histórico
				consumoAgua.setConsumoCobradoMes(getImovelSelecionado().getConsumoMinAgua());
			    // Seta o consumo anormalidade
				consumoAgua.setTipoConsumo(CONSUMO_TIPO_MINIMO_FIX);
			}
		// [UC0740] 2.2.
	    } else {
			int consumoMinimo = 0;
			int consumoTipo = 0;
			if (getImovelSelecionado().getConsumoMinAgua() != Constantes.NULO_INT) {
			    consumoMinimo = getImovelSelecionado().getConsumoMinAgua();
			    consumoTipo = CONSUMO_TIPO_MINIMO_FIX;
			} else {
			    consumoMinimo = getImovelSelecionado().getconsumoMinimoImovelNaoMedido();
			    consumoTipo = CONSUMO_TIPO_NAO_MEDIDO;
			}
	
			this.setConsumoAgua(new Consumo(Constantes.NULO_INT,
				consumoMinimo, Constantes.NULO_INT, consumoTipo,
				Constantes.NULO_INT, Constantes.NULO_INT));
	
			// verifica se existe situação tipo
			if (getImovelSelecionado().getSituacaoTipo() != null
				&& !getImovelSelecionado().getSituacaoTipo().equals("")) {
			    
			    if (getImovelSelecionado().getSituacaoTipo().getIndcValidaAgua() == Constantes.SIM) {
			    	dadosFaturamentoEspecialNaoMedido(consumoAgua,LIGACAO_AGUA);
			    }
			}
	    }
	} else {
	    this.consumoAgua = null;
	}

	// [UC0740] 4.
	int qtdEconomias = getImovelSelecionado().getQuantidadeEconomiasTotal();

	if (qtdEconomias > 1) {
	    // [SB0009]
	    if (consumoAgua != null
		    && consumoAgua.getConsumoCobradoMes() != Constantes.NULO_INT) {
	    	
	    	int leituraAnterior = Constantes.NULO_INT;

			if (getImovelSelecionado().getRegistro8(LIGACAO_AGUA) != null) {
			    leituraAnterior = getImovelSelecionado().getRegistro8(LIGACAO_AGUA)
				    .getLeituraAnterior();
			}
	
			consumoAgua.ajustar(qtdEconomias, leituraAnterior, LIGACAO_AGUA);
	    }
	}

	if (consumoAgua != null
		&& (consumoAgua.getTipoConsumo() != CONSUMO_TIPO_NAO_MEDIDO || consumoAgua
			.getTipoConsumo() != CONSUMO_TIPO_MINIMO_FIX)) {
	    ImovelReg8 reg8 = getImovelSelecionado().getRegistro8(Constantes.LIGACAO_AGUA);
	    if (reg8 != null) {
	    	consumoAgua.setDiasConsumo(Util.obterModuloDiferencasDatasDias(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), Util.dataAtual()));
	    }
	}

	// Guardamos o consumo original
	if (consumoAgua != null) {
		consumoAgua.setConsumoCobradoMesOriginal(consumoAgua.getConsumoCobradoMes());
	}

	// [UC0740] 3.
	if (getImovelSelecionado().getIndcFaturamentoEsgoto() == SIM) {

	    // [UC0740] 3.1.
	    if (getImovelSelecionado().getNumeroHidrometro(LIGACAO_POCO) != null) {
			this.calcularConsumo( LIGACAO_POCO);
	
			// Caso exista Consumo a Ser Cobrado no Mês da ligação de água,
			// o Consumo a Ser Cobrado no Mês de esgoto = (Cobrado no Mês da
			// ligação de água + Consumo a Ser Cobrado no Mês);
			// caso contrário, o Consumo a Ser Cobrado no Mês de esgoto =
			// Consumo a Ser Cobrado no Mês.
			if (consumoAgua != null && consumoAgua.getConsumoCobradoMes() != Constantes.NULO_INT) {
			    // Seta o consumo histórico
			    int consumoFaturadoMes = consumoAgua.getConsumoCobradoMes()
				    + consumoEsgoto.getConsumoCobradoMes();
	
			    consumoEsgoto.setConsumoCobradoMes(consumoFaturadoMes);
			}

		// [UC0740] 3.2.
	    } else {

			int consumoMinnoEsgoto = 0;
			if (getImovelSelecionado().getConsumoMinEsgoto() != Constantes.NULO_INT) {
			    consumoMinnoEsgoto = getImovelSelecionado().getConsumoMinEsgoto();
			}
	
			this.setConsumoEsgoto(new Consumo(Constantes.NULO_INT,
				consumoMinnoEsgoto, Constantes.NULO_INT,
				CONSUMO_TIPO_NAO_MEDIDO, Constantes.NULO_INT,
				Constantes.NULO_INT));
	
			// android
		   	//Daniel - Se o imóvel possui ligação de agua ligada
			if ( getImovelSelecionado().getIndcFaturamentoAgua() == SIM){
				consumoEsgoto.setConsumoCobradoMes(consumoAgua
				    .getConsumoCobradoMes());
		   	
			 //Daniel - Se o imóvel possui ligação de agua CORTADO, será cobrado o mínimo da subCategoria 
		   	} else {
				consumoEsgoto.setConsumoCobradoMes(getImovelSelecionado()
				    .getconsumoMinimoImovelNaoMedido());
			}
	
			// Caso o consumo a ser cobrado mês seja inferior ao consumo
			// mínimo
			if ((getImovelSelecionado().getConsumoMinEsgoto() != Constantes.NULO_INT)
				&& consumoEsgoto.getConsumoCobradoMes() < getImovelSelecionado()
					.getConsumoMinEsgoto()) {
	
			    // O consumo a ser cobrado mês será o consumo mínimo da
			    // ligação de esgoto
				consumoEsgoto.setConsumoCobradoMes(getImovelSelecionado()
				    .getConsumoMinEsgoto());
	
			    // A anormalidade de consumo será o consumo mínimo fixado de
			    // esgoto
				consumoEsgoto.setAnormalidadeConsumo(Consumo.CONSUMO_MINIMO_FIXADO);
	
			    /*
			     * Colocado por Raphael Rossiter em 17/03/2009 - Analista:
			     * Aryed Lins O TIPO do consumo será CONSUMO_MINIMO_FIXADO
			     */
				consumoEsgoto.setTipoConsumo(CONSUMO_TIPO_MINIMO_FIX);
	
			}
	
			// verifica se existe situação tipo
			if (getImovelSelecionado().getSituacaoTipo() != null
				&& !getImovelSelecionado().getSituacaoTipo().equals("")) {
			    SituacaoTipo situacaoTipo = getImovelSelecionado().getSituacaoTipo();
			    if (situacaoTipo.getIndcValidaEsgoto() == Constantes.SIM) {
				dadosFaturamentoEspecialNaoMedido(consumoEsgoto, LIGACAO_POCO);
			    }
			}
	    }

	    // [UC0740] 3.3.	    this.consumoEsgoto
		    .setConsumoCobradoMes(Util.arredondar(((consumoEsgoto
			    .getConsumoCobradoMes() * getImovelSelecionado()
			    .getPercentColetaEsgoto()) / 100)));
	}
	
	/*
	 * 4.Caso o imóvel não esteja mais cortado (Situação da Ligação de Agua <> 5 do registro tipo 1), 
	 * ou o consumo de água tenha sido real e maior que zero (Tipo de Consumo = 1 e o Consumo a ser 
	 * Cobrado no Mês maior que zero da tabela ),  excluir o  débito a cobrar referente a Tarifa de 
	 * Cortado Dec. 18.251/94 ( excluir registro do tipo 4 com Código do débito igual ao código do 
	 * débito 99).
	 */	
	RegistroBasico debito = getImovelSelecionado().getDebito( RegistroBasico.TARIFA_CORTADO_DEC_18_251_94 );
	
	// Caso o débito exista, reiniciamos para o estado inicial,
	// caso haja necessidade de recalculo. 
	if ( debito != null ){
	    debito.setIndcUso( (short)Constantes.SIM );
	    getImovelSelecionado().setIndcFaturamentoAgua( Constantes.SIM+"" );
	}
	
	if ( ( !getImovelSelecionado().getSituacaoLigAgua().equals( Constantes.CORTADO ) ) ||
	     ( consumoAgua != null && 
	       consumoAgua.getTipoConsumo() == CONSUMO_TIPO_REAL && 
	       consumoAgua.getConsumoCobradoMes() > 0 ) ){
	    
	    if ( debito != null ){
	    	debito.setIndcUso( (short) Constantes.NAO );
	    }
	}

	if (qtdEconomias > 1) {
	    if (consumoEsgoto != null
		    && consumoEsgoto.getConsumoCobradoMes() != Constantes.NULO_INT) {
			int leituraAnterior = Constantes.NULO_INT;
			
			if (getImovelSelecionado().getRegistro8(LIGACAO_POCO) != null) {
			    leituraAnterior = getImovelSelecionado().getRegistro8(LIGACAO_POCO)
				    .getLeituraAnterior();
			}
			consumoEsgoto.ajustar(qtdEconomias, leituraAnterior, LIGACAO_POCO);
	    }
	}

	if (consumoEsgoto != null && 
			(consumoEsgoto.getTipoConsumo() != CONSUMO_TIPO_NAO_MEDIDO || 
			 consumoEsgoto.getTipoConsumo() != CONSUMO_TIPO_MINIMO_FIX)) {
	    
			ImovelReg8 reg8 = getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO);
			if (reg8 != null) {
				consumoEsgoto.setDiasConsumo(Util.obterModuloDiferencasDatasDias(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), Util.dataAtual()));
			}
	}

	if (consumoEsgoto != null) {
		consumoEsgoto.setConsumoCobradoMesOriginal(consumoEsgoto
		    .getConsumoCobradoMes());
	}

	// Caso nao seja calculado o consumo de agua nem o de poco, porem o
	// imovel possua débitos
	// diminuimos ele dos não lidos
	if (consumoAgua == null && consumoEsgoto == null
		&& getImovelSelecionado().getValorDebitos() >= 0) {

	    if (getImovelSelecionado().isImovelCondominio()) {

			EfetuarRateioConsumoDispositivoMovelHelper helper = null;
			ImovelConta macro = null;
	
			if (getImovelSelecionado().getIdImovelCondominio() != Constantes.NULO_INT) {
			    macro = new ImovelConta();
			    Repositorio.carregarObjeto(macro, getImovelSelecionado()
				    .getIdImovelCondominio());
			    helper = macro
				    .getEfetuarRateioConsumoDispositivoMovelHelper();
			} else if (getImovelSelecionado().getIndcCondominio() == Constantes.SIM) {
			    helper = getImovelSelecionado()
				    .getEfetuarRateioConsumoDispositivoMovelHelper();
			}
	
			helper.getIdsAindaFaltamSerCalculador().removeElement(
				new Integer(getImovelSelecionado().getId()));
	
			if (macro != null) {
			    macro.setEfetuarRateioConsumoDispositivoMovelHelper(helper);
			    Repositorio.salvarObjeto(macro);
			} else {
			    getImovelSelecionado()
				    .setEfetuarRateioConsumoDispositivoMovelHelper(helper);
			}
	    }

	    Integer id = new Integer(getImovelSelecionado().getId());
	    int quadra = getImovelSelecionado().getQuadra();
	    String stringQuadra = Util.adicionarZerosEsquerdaNumero(4, String
		    .valueOf(quadra));

	    DadosRelatorio.getInstancia().idsNaoLidosRelatorio.removeElement(id);

	    if (!DadosRelatorio.getInstancia().idsLidosRelatorio.contains(id)) {
	    	DadosRelatorio.getInstancia().idsLidosRelatorio.addElement(id);

		Util.inserirValoresStringRelatorio("(" + stringQuadra + ")",
			false, true);

	    }
	    
	}
	
	/*
	 * É possivel que mesmo que imóvel
	 * não possua consumo de agua e/ou de esgoto
	 * ele tenha hidrometro. Sendo assim a leitura
	 * precisa ser enviada para o gsa, sendo assim
	 * guardada no historico.
	 * 
	 * Ex: (CAERN)
	 *   Imovel cortado de agua e com hidrometro
	 *   Imovel ligado de esgoto e sem hidrometro de poço
	 *   
	 *   Mes não tento consumo de agua, a leitura
	 *   é enviada ao GSAN.
	 * 
	 */
	if (getImovelSelecionado().getNumeroHidrometro(LIGACAO_AGUA) != null) {
	    ImovelReg8 reg8 = getImovelSelecionado().getRegistro8(LIGACAO_AGUA);
    	 	reg8.setDataLeitura(Util.dataAtual());
	}
    	 
	if (getImovelSelecionado().getNumeroHidrometro(LIGACAO_POCO) != null) {
	    ImovelReg8 reg8 = getImovelSelecionado().getRegistro8(LIGACAO_POCO);
	    reg8.setDataLeitura(Util.dataAtual());
	}	
	
	Repositorio.salvarObjeto(getImovelSelecionado());

	// Verifica se o imóvel tem um percentual de esgoto alternativo
	if (consumoEsgoto != null) {
	    getImovelSelecionado().verificarPercentualEsgotoAlternativo(consumoEsgoto
		    .getConsumoCobradoMes());
	}

	// [UC0740] 5. - Calcular Valores de Água/Esgoto
	if (!getImovelSelecionado().isImovelCondominio()) {
	    this.calcularValores();
	}
    }

    /**
     * [SB0000] - Calcular Consumo
     */
    private void calcularConsumo(int tipoMedicao) {

	// Apagamos as mensagens, caso tenha sido colocadas anteriormente
	// de Alto, Baixo, ou de Estouro de consumo
	getImovelSelecionado().setMensagemEstouroConsumo1(null);
	getImovelSelecionado().setMensagemEstouroConsumo2(null);
	getImovelSelecionado().setMensagemEstouroConsumo3(null);

	ImovelReg8 reg8 = getImovelSelecionado().getRegistro8(tipoMedicao);

	int cMedio;
	// Verificamos se o consumo médio veio do registro tipo 8 ou do imóvel
	if (reg8 != null) {
	    cMedio = reg8.getConsumoMedio();
	} else {
	    cMedio = getImovelSelecionado().getConsumoMedio();
	}

	reg8.setDataLeitura(Util.dataAtual());

	// leitura atual informada
	int leitura = reg8.getLeitura();

	Consumo consumo = null;

	if (tipoMedicao == LIGACAO_AGUA) {
	    this.consumoAgua = new Consumo();
	    consumo = this.consumoAgua;
	} else {
	    this.consumoEsgoto = new Consumo();
	    consumo = this.consumoEsgoto;
	}
	
	consumo.setAnormalidadeConsumo(Constantes.NULO_INT);
      
	// seta a anormalidade informada na anormalidade de leitura faturada
	if (reg8.getAnormalidade() > 0) {
	    consumo.setAnormalidadeLeituraFaturada(reg8.getAnormalidade());
	}

	int leituraAnterior = this.obterLeituraAnterior(reg8);
	reg8.setLeituraAnterior(leituraAnterior);

    if (isImovelHidrometroSubstituido(tipoMedicao)) {

    	// Daniel - verifica se a leitura informada é maior que a leitura do hidrometro no momento da substituiçao
    	if( leitura > reg8.getLeituraInstalacaoHidrometro()  ){
    		
    		consumo.setConsumoMedidoMes(leitura - reg8.getLeituraInstalacaoHidrometro());
    		consumo.setConsumoCobradoMes(leitura - reg8.getLeituraInstalacaoHidrometro());
    		consumo.setLeituraAtual(leitura);
    	
    		if (!verificarEstouroConsumo(consumo, reg8)){
    		    verificarAltoConsumo(consumo, reg8);
    		}
    		
    	// Daniel - Se for menor, faz o calculo pela média.
    	}else{
    		consumo.setConsumoMedidoMes(Constantes.NULO_INT);
    		consumo.setConsumoCobradoMes(cMedio);
    		consumo.setLeituraAtual(reg8.getLeituraInstalacaoHidrometro() + cMedio);
            consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
    	}

//		Faz ajuste de consumo.
		chamaAjusteConsumo(tipoMedicao);		
		
   		consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO);
   		
   		leituraAnterior = reg8.getLeituraInstalacaoHidrometro();
   		reg8.setLeituraAnterior(leituraAnterior);

   		// se houve leitura
   		if (leitura != Constantes.NULO_INT){
   			
   			// se a leitura for maior que a informada na substituiçao do hidrometro. Caso contrario já foi definido tipo = Consumo Médio acima.
   			if(leitura > reg8.getLeituraInstalacaoHidrometro()){
   				
	   			int sitAnt = reg8.getSituacaoLeituraAnterior();
	   			
	   			if (sitAnt == LEITURA_SITU_REALIZADA || sitAnt == LEITURA_SITU_CONFIRMADA) {
	   			    consumo.setTipoConsumo(CONSUMO_TIPO_REAL);

	   			} else {
	   			    consumo.setTipoConsumo(CONSUMO_TIPO_ESTIMADO);
	   			
	   			}
   			}
  		}
    }

	// [SB0000] 1. Caso a leitura tenha sido coletada:
	if (leitura != Constantes.NULO_INT && consumo.getAnormalidadeConsumo() != Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO) {

	    // [SB0000] 1.1. Caso a leitura atual informada seja maior que a
	    // leitura anterior
	    if (leitura > leituraAnterior) {

		int sitAnt = reg8.getSituacaoLeituraAnterior();
		if (sitAnt == LEITURA_SITU_REALIZADA || sitAnt == LEITURA_SITU_CONFIRMADA) {
		    consumo.setTipoConsumo(CONSUMO_TIPO_REAL);
		} else {
		    consumo.setTipoConsumo(CONSUMO_TIPO_ESTIMADO);
		}

		consumo.setConsumoCobradoMes(leitura - leituraAnterior);
		consumo.setConsumoMedidoMes(leitura - leituraAnterior);
		consumo.setLeituraAtual(leitura);

//		Faz ajuste de consumo.
		chamaAjusteConsumo(tipoMedicao);					
		
		if (isForaDeFaixa(reg8)) {
			consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_FORA_DE_FAIXA);
		
		} else {
		    consumo.setAnormalidadeConsumo(Constantes.NULO_INT);
		}

		// [SB0000] 1.2. Caso a leitura atual informada seja igual à
		// leitura anterior
	    } else if (leitura == leituraAnterior) {

		// [SB0002] - Dados para Faturamento para leitura Igual à
		// Anterior

		// [SB0002] 1.
		consumo.setConsumoMedidoMes(0);
		consumo.setConsumoCobradoMes(0);
		consumo.setLeituraAtual(leitura);

//		Faz ajuste de consumo.
		chamaAjusteConsumo(tipoMedicao);		
		
		int sitAnt = reg8.getSituacaoLeituraAnterior();
		if (sitAnt == LEITURA_SITU_REALIZADA || sitAnt == LEITURA_SITU_CONFIRMADA) {
		    consumo.setTipoConsumo(CONSUMO_TIPO_REAL);
		} else {
		    consumo.setTipoConsumo(CONSUMO_TIPO_ESTIMADO);
		}

		consumo.setAnormalidadeConsumo(Constantes.NULO_INT);

		// [SB0002] 2.
		if (leitura > 1 && reg8.getAnormalidade() == 0) {

		    if (getImovelSelecionado().getIndicadorAbastecimentoAgua() == Constantes.SIM
			    && getImovelSelecionado().getIndicadorImovelSazonal() == Constantes.NAO) {

			// 2.1
			if (tipoMedicao == LIGACAO_AGUA) {
			    boolean primeiraCondicao = false;
			    boolean hidrometroParado = false;

			    // Não exista ligação de esgoto
			    /*
			     * if (getImovelSelecionado().getIndcFaturamentoEsgoto() != SIM) {
			     * primeiraCondicao = true; }
			     */
			    // Caso exista, o valor do consumo mínimo fixado de
			    // esgoto seja igual a nulo
			    if (getImovelSelecionado().getConsumoMinEsgoto() == Constantes.NULO_INT) {

			    	primeiraCondicao = true;
			    }

			    // Não exista poço no imóvel
			    if (primeiraCondicao
				    && (getImovelSelecionado().getTipoPoco() == Constantes.NULO_INT)) {

			    	hidrometroParado = true;
			    }

// Daniel - Imovel condominio em situação cortado (é pre-faturado) nao deve gerar anormalidade de leitura = Hidrometro Parado.
			    if (hidrometroParado && 
			    	(!getImovelSelecionado().isImovelMicroCondominio() ||
			    	(!getImovelSelecionado().getSituacaoLigAgua().equals(Constantes.CORTADO)))) {

				/*
				 * O sistema gera a Anormalidade de Leitura de
				 * Faturamento com o valor correspondente a
				 * hidrômetro parado
				 */
				consumo
					.setAnormalidadeLeituraFaturada(ANORM_HIDROMETRO_PARADO);

			    } else {

					// 2.1.1
					if (getImovelSelecionado().getTipoPoco() != Constantes.NULO_INT
						|| getImovelSelecionado().getConsumoMinEsgoto() != Constantes.NULO_INT) {
					    
							consumo.setAnormalidadeLeituraFaturada(ANORM_HIDR_SEM_CONSUMO);
					}
			    }
			}

			else if (tipoMedicao == LIGACAO_POCO) {
			    // 2.2.1
			    if ((getImovelSelecionado().getRegistro8(LIGACAO_AGUA) != null && (consumoAgua != null && 
			    	consumoAgua.getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_BAIXO_CONSUMO))) {
				
			    	consumo.setAnormalidadeLeituraFaturada(ANORM_HIDROMETRO_PARADO);
				// 2.2.2
			    } else {
			    	consumo.setAnormalidadeLeituraFaturada(ANORM_HIDR_SEM_CONSUMO);
			    }
			}
		    }
		}

		// [SB0000] 1.3.
	    } else {
			boolean voltarFluxoPrincipal = false;
	
			// [SB0003] 1.1.
			// Verifica se foi virada de hidrometro.
			if (!voltarFluxoPrincipal) {
			    int n = reg8.getNumDigitosLeitura();
			    int _10n = Util.pow(10, n);
			    int consumoCalculado = (reg8.getLeitura() + _10n)
				    - reg8.getLeituraAnterior();
	
			    // [SB0003] 1.2.
			    if (((consumoCalculado <= 200) || (consumoCalculado <= 2 * cMedio))
				    && (consumoCalculado > 0)) {
	
				// [SB0003] 1.2.1.
				consumo.setConsumoMedidoMes(consumoCalculado);
				consumo.setConsumoCobradoMes(consumoCalculado);
				consumo.setLeituraAtual(leitura);
	
//				Faz ajuste de consumo.
				chamaAjusteConsumo(tipoMedicao);		

				int sitAnt = reg8.getSituacaoLeituraAnterior();
				if (sitAnt == LEITURA_SITU_REALIZADA
					|| sitAnt == LEITURA_SITU_CONFIRMADA) {
				    consumo.setTipoConsumo(CONSUMO_TIPO_REAL);
				} else {
				    consumo.setTipoConsumo(CONSUMO_TIPO_ESTIMADO);
				}

// Daniel - verificar se o ajuste de consumo desfez a virada de hidrometro.
				
				if(reg8.getLeituraAtualFaturamento() != Constantes.NULO_INT && 
						reg8.getLeituraAtualFaturamento() < leituraAnterior){
					
					consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_VIRADA_HIDROMETRO);

				}

				if (reg8.getLeituraEsperadaInicial() > reg8.getLeituraEsperadaFinal()){
					
					//	Daniel - Verifica se está fora de faixa
					if (isForaDeFaixa(reg8)) {

						consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_FORA_DE_FAIXA);
					}
				}
				
				// [SB0003] 1.2.2.
				voltarFluxoPrincipal = true;
			    }
			}
		
			// [SB0003] 3.
			if (!voltarFluxoPrincipal) {
			    consumo.setConsumoMedidoMes(Constantes.NULO_INT);
			    consumo.setConsumoCobradoMes(cMedio);
			    consumo.setLeituraAtual(leituraAnterior + cMedio);
			    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
			    if (reg8.getSituacaoLeituraAnterior() == LEITURA_SITU_CONFIRMADA
				    || reg8.getSituacaoLeituraAnterior() == LEITURA_SITU_REALIZADA) {
				consumo
					.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_LEIT_MENOR_ANTE);
			    } else {
				consumo
					.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_LEIT_MENOR_PROJ);
			    }
	
//				Faz ajuste de consumo.
				chamaAjusteConsumo(tipoMedicao);		

			}
			
	    }
	} else {
	    // [SB0000] 3.
	    if (leitura == Constantes.NULO_INT
		    && consumo.getAnormalidadeLeituraFaturada() == Constantes.NULO_INT) {
		// [SB0005]
		// [SB0005] 1.
		consumo.setConsumoMedidoMes(Constantes.NULO_INT);
		consumo.setConsumoCobradoMes(cMedio);
		consumo.setLeituraAtual(leituraAnterior + cMedio);
		consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		//		Daniel - se já tiver identificado Hidrometro substituido, nao redefinir anormalidade de consumo.
		if(consumo.getAnormalidadeConsumo() != Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO){
			consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_LEITURA_N_INFO);
		}
		consumo.setAnormalidadeLeituraFaturada(Constantes.NULO_INT);
		
//		Faz ajuste de consumo.
		chamaAjusteConsumo(tipoMedicao);		

	    }
	}

	// [SB0000] 2. Caso a anormalidade de leitura tenha sido informada
	if (consumo.getAnormalidadeLeituraFaturada() > 0) {

	    Anormalidade anormalidade = null;

	    try {
		anormalidade = (Anormalidade) FileManager
			.getAnormalidade(consumo
				.getAnormalidadeLeituraFaturada());
	    } catch (IOException e) {
		e.printStackTrace();
		Util.mostrarErro("Erro ao carregar arquivo de anormalidade");
	    }

	    // [SB0004] - Dados para Faturamento com Anormalidade de leitura
	    // 1. leitura Atual Informada não coletada
	    if (leitura == Constantes.NULO_INT) {
			// 1.1
			consumo.setConsumoMedidoMes(Constantes.NULO_INT);

//			Daniel - se já tiver identificado Hidrometro substituido, nao redefinir anormalidade de consumo.
			if(consumo.getAnormalidadeConsumo() != Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO){
				consumo.setAnormalidadeConsumo(Consumo.ANORMALIDADE_LEITURA);
			}
	
	
			// 1.2
			// 1.2.1
			if (anormalidade.getIdConsumoACobrarSemLeitura() == NAO_OCORRE) {
			    consumo.setConsumoCobradoMes(cMedio);
			    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
			}
			// 1.2.2
			else if (anormalidade.getIdConsumoACobrarSemLeitura() == MINIMO) {
			    consumo.setConsumoCobradoMes(getImovelSelecionado()
				    .getConsumoMinimoImovel());
			    consumo.setTipoConsumo(CONSUMO_TIPO_MINIMO_FIX);
			}
			// 1.2.3
			else if (anormalidade.getIdConsumoACobrarSemLeitura() == MEDIA) {
			    consumo.setConsumoCobradoMes(cMedio);
			    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
			}
	
			// 1.3
			// 1.3.1
			if (anormalidade.getIdLeituraFaturarSemLeitura() == ANTERIOR_MAIS_A_MEDIA) {
			    consumo.setLeituraAtual(leituraAnterior + cMedio);
			}
			// 1.3.2
			else if (anormalidade.getIdLeituraFaturarSemLeitura() == ANTERIOR) {
			    consumo.setLeituraAtual(leituraAnterior + cMedio);
			}
			// 1.3.3
			else if (anormalidade.getIdLeituraFaturarSemLeitura() == ANTERIOR_MAIS_O_CONSUMO) {
			    consumo.setLeituraAtual(leituraAnterior + consumo.getConsumoCobradoMes());
			}
	
			// 1.1.4. O sistema deverá aplicar o fator definido sem leitura
			// no sistema ao consumo apurado
			// de acordo com o definido na anormalidade especificada
			// (LTAN_NNFATORSEMLEITURA da tabela LEITURA_ANORMALIDADE
			// com LTAN_ID = anormalidade informada).
			if (consumo.getConsumoCobradoMes() != Constantes.NULO_INT
				&& anormalidade.getNumeroFatorSemLeitura() != Constantes.NULO_DOUBLE) {
	
			    double consumoFaturadoMesSemLeitura = consumo
				    .getConsumoCobradoMes();
	
			    consumoFaturadoMesSemLeitura = anormalidade
				    .getNumeroFatorSemLeitura()
				    * consumoFaturadoMesSemLeitura;
	
			    consumo
				    .setConsumoCobradoMes((int) consumoFaturadoMesSemLeitura);
	
			}
	
//			Faz ajuste de consumo.
			chamaAjusteConsumo(tipoMedicao);		
	    }
	    // 2.Leitura Atual Informada
	    else {
		// 2.2
		// 2.2.1
		if (anormalidade.getIdConsumoACobrarComLeitura() == NAO_OCORRE) {
		    consumo.setConsumoCobradoMes(cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		}
		// 2.2.2
		else if (anormalidade.getIdConsumoACobrarComLeitura() == MINIMO) {
		    consumo.setConsumoCobradoMes(0);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MINIMO_FIX);
		}
		// 2.2.3
		else if (anormalidade.getIdConsumoACobrarComLeitura() == MEDIA) {
		    consumo.setConsumoCobradoMes(cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		}
		// 2.2.4
		else if (anormalidade.getIdConsumoACobrarComLeitura() == NORMAL) {
		    // Fazer nada já calculado
		}
		// 2.2.5
		else if (anormalidade.getIdConsumoACobrarComLeitura() == MAIOR_ENTRE_O_CONSUMO_MEDIO) {
		    if (cMedio > consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }
		}
		// 2.2.6
		else if (anormalidade.getIdConsumoACobrarComLeitura() == MENOR_ENTRE_O_CONSUMO_MEDIO) {
		    if (cMedio < consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		}
		// 2.3.1
		if (anormalidade.getIdLeituraFaturarComLeitura() == ANTERIOR_MAIS_A_MEDIA) {
		    consumo.setLeituraAtual(leituraAnterior + cMedio);
		}
		// 2.3.2
		else if (anormalidade.getIdLeituraFaturarComLeitura() == ANTERIOR) {
		    consumo.setLeituraAtual(leituraAnterior + cMedio);
		}
		// 2.3.3
		else if (anormalidade.getIdLeituraFaturarComLeitura() == ANTERIOR_MAIS_O_CONSUMO) {
		    consumo.setLeituraAtual(leituraAnterior + consumo.getConsumoCobradoMes());
		}
		// 2.3.4
		else if (anormalidade.getIdLeituraFaturarComLeitura() == INFORMADO) {
		    // Fazer nada Já calculado anteriormente
		}

		// 1.2.3. O sistema deverá aplicar o fator definido com leitura
		// no sistema ao consumo apurado de acordo com o
		// definido na anormalidade especificada (LTAN_NNFATORCOMLEITURA
		// da tabela LEITURA_ANORMALIDADE
		// com LTAN_ID = anormalidade informada).
		if (consumo.getConsumoCobradoMes() != Constantes.NULO_INT
			&& anormalidade.getNumeroFatorComLeitura() != Constantes.NULO_DOUBLE) {

		    double consumoFaturadoMesComLeitura = consumo
			    .getConsumoCobradoMes();

		    consumoFaturadoMesComLeitura = anormalidade
			    .getNumeroFatorComLeitura()
			    * consumoFaturadoMesComLeitura;

		    consumo
			    .setConsumoCobradoMes((int) consumoFaturadoMesComLeitura);

		}

	    }

	}

// - Somente fazer verificaçoes se nao for hidrometro substituido e não for leitura menor que anterior.
	if (consumo.getAnormalidadeConsumo() != Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO &&
			consumo.getAnormalidadeConsumo() != Consumo.CONSUMO_ANORM_LEIT_MENOR_ANTE  &&
			consumo.getAnormalidadeConsumo() != Consumo.CONSUMO_ANORM_LEIT_MENOR_PROJ){
		// [SB0000] 4.
		// [SB0006] - Verificar Estouro de Consumo
		// [SB0006] 1.

		if (!verificarEstouroConsumo(consumo, reg8)) {
		    // [SB0000] 5. Caso não tenha estouro de consumo
		    // [SB0007] - Verificar Alto Consumo
		    verificarAltoConsumo(consumo, reg8);
		}
		// [SB0000] 6. Caso o tipo de poço corresponda à sem poço (valor zero),
		// a leitura tenha sido coletada, e a anormalidade não tenha sido
		// informada
		int sitLigAgua = Util.verificarNuloInt(getImovelSelecionado().getSituacaoLigAgua());
	
		if (sitLigAgua != ImovelConta.CORTADO
			&& getImovelSelecionado().getTipoPoco() == Constantes.NULO_INT
			&& leitura != Constantes.NULO_INT
			&& consumo.getAnormalidadeLeituraFaturada() <= 0) {

			verificarBaixoConsumo( consumo, reg8);
		}
			
		//Verificar se leitura faturada é maior que o permitido pelo hidrometro

		// Obtém 10 elevado ao numeroDigitosHidrometro
		int dezElevadoNumeroDigitos = (int) Util.pow(10,
				this.obterNumeroDigitosHidrometro(tipoMedicao));

		// Caso a leitura faturada maior que dez elevado ao número de dígitos menos um
		if (consumo.getLeituraAtual() > (dezElevadoNumeroDigitos - 1) ) {

			reg8.setLeituraAtualFaturamento(consumo.getLeituraAtual() - (dezElevadoNumeroDigitos-1));
			consumo.setLeituraAtual(consumo.getLeituraAtual() - (dezElevadoNumeroDigitos-1));
				
		}
	}
	// verifica se existe situação tipo
	if (getImovelSelecionado().getSituacaoTipo() != null
		&& !getImovelSelecionado().getSituacaoTipo().equals("")) {
	    // se existe e o tipo de ligação é agua,
	    // determina o consumo para água
	    if (tipoMedicao == LIGACAO_AGUA) {
			if (getImovelSelecionado().getSituacaoTipo().getIndcValidaAgua() == Constantes.SIM) {
			    dadosFaturamentoEspecialMedido(consumo, tipoMedicao);
			}
	    } else {
			// determina o consumo para esgoto
			if (tipoMedicao == LIGACAO_POCO) {
			    if (getImovelSelecionado().getSituacaoTipo().getIndcValidaEsgoto() == Constantes.SIM) {
				dadosFaturamentoEspecialMedido(consumo,
					tipoMedicao);
			    }
			}
	    }
	}

	System.out.println("********** " + getImovelSelecionado().getMatricula() + "********** ");
	System.out.println("Consumo Agua: " + (consumoAgua != null ? consumoAgua.getConsumoCobradoMes() + "" : "Nulo"));
	System.out.println("Consumo Esgoto: " + (consumoEsgoto != null ? consumoEsgoto.getConsumoCobradoMes() + "" : "Nulo"));
	System.out.println("*************************** ");	
    }

    public void calcularValores() {

//	ControladorImoveis controladorImoveis = ControladorImoveis.getInstancia();
	
	boolean imovelComDebitoTipoCortado = false;
	
	RegistroBasico debito = getImovelSelecionado().getDebito( RegistroBasico.TARIFA_CORTADO_DEC_18_251_94 );
	
	if ( debito != null && debito.getIndcUso() == Constantes.SIM ){
	    imovelComDebitoTipoCortado = true;
	}

	// [UC0743] 2.
	if ( (getImovelSelecionado().getIndcFaturamentoAgua() == SIM)&& 
	     getImovelSelecionado().getIndicadorParalizarFaturamentoAgua() == NAO && !imovelComDebitoTipoCortado ) {
		
		ControladorImoveis.getInstancia().calcularValores(
		    getImovelSelecionado(), 
		    consumoAgua,
		    ControladorConta.LIGACAO_AGUA);
	} else {	    
	    // Agora o indicador de faturamento
	    // pode ser alterado dinamicamente.
	    // Sendo assim, zeramos os valores calculados
	    // de agua e de esgoto caso seja necessário
	    Vector categoriasSubcategorias = getImovelSelecionado().getRegistros2();
	    
	    for ( int i = 0; i < categoriasSubcategorias.size(); i++ ){		
			((ImovelReg2)(categoriasSubcategorias.elementAt(i))).setFaturamentoAgua( null );		
	    }
	}

	// [UC0743] 3.
	if ( (getImovelSelecionado().getIndcFaturamentoEsgoto() == SIM)
		&& getImovelSelecionado().getIndicadorParalizarFaturamentoEsgoto() == NAO) {
		ControladorImoveis.getInstancia().calcularValores(getImovelSelecionado(), consumoEsgoto,
		    ControladorConta.LIGACAO_POCO);
	}
	
	// Salvamos que o imovel ja foi calculado
	// getImovelSelecionado().setIndcImovelCalculado( Constantes.SIM );

	// Se o imóvel estiver com a situação referente a nitrato
	if (getImovelSelecionado().getSituacaoTipo() != null
		&& !getImovelSelecionado().getSituacaoTipo().equals("")) {
	    if (getImovelSelecionado().getSituacaoTipo().getTipoSituacaoEspecialFeturamento() == SituacaoTipo.NITRATO) {
		// calcula 50% do valor da água
		double valorCreditoNitrato = getImovelSelecionado().getValorAgua() / 2;
		// atualiza o crédito referente a Nitrato com o valor
		// calculado do crédito
		getImovelSelecionado().setValorCreditosNitrato(valorCreditoNitrato);
	    }
	}

	// Verifica se o valor de creditos é maior que o valor da conta
	double valorCreditos = getImovelSelecionado().getValorCreditos();

	if (valorCreditos != 0d) {
	    double valorContaSemCreditos = getImovelSelecionado().getValorContaSemCreditos();
	    if (valorContaSemCreditos < valorCreditos) {
		double valorResidual = valorCreditos - valorContaSemCreditos;
		getImovelSelecionado().setValorResidualCredito(valorResidual);
	    }

	}

	// Salvamos o objeto
	Repositorio.salvarObjeto(getImovelSelecionado());

    }

    public void calcularValoresCondominio(ImovelConta imovelMicro) {

//    	ControladorImoveis controladorImoveis = ControladorImoveis.getInstancia();
    	
    	boolean imovelComDebitoTipoCortado = false;
    	
    	RegistroBasico debito = imovelMicro.getDebito( RegistroBasico.TARIFA_CORTADO_DEC_18_251_94 );
    	
    	if ( debito != null && debito.getIndcUso() == Constantes.SIM ){
    	    imovelComDebitoTipoCortado = true;
    	}

    	// [UC0743] 2.
    	if ( (imovelMicro.getIndcFaturamentoAgua() == SIM)&& 
    	     imovelMicro.getIndicadorParalizarFaturamentoAgua() == NAO &&
    	     !imovelComDebitoTipoCortado ) {
    		ControladorImoveis.getInstancia().calcularValores(
    		    imovelMicro, 
    		    consumoAgua,
    		    ControladorConta.LIGACAO_AGUA);
    	} else {	    
    	    // Agora o indicador de faturamento
    	    // pode ser alterado dinamicamente.
    	    // Sendo assim, zeramos os valores calculados
    	    // de agua e de esgoto caso seja necessário
    	    Vector categoriasSubcategorias = imovelMicro.getRegistros2();
    	    
    	    for ( int i = 0; i < categoriasSubcategorias.size(); i++ ){		
    		ImovelReg2 categoriaSubcategoria = ( ImovelReg2 ) categoriasSubcategorias.elementAt( i );		
    		categoriaSubcategoria.setFaturamentoAgua( null );
    	    }
    	}

    	// [UC0743] 3.
    	if ( (getImovelSelecionado().getIndcFaturamentoEsgoto() == SIM)
    		&& imovelMicro.getIndicadorParalizarFaturamentoEsgoto() == NAO) {
    		ControladorImoveis.getInstancia().calcularValores(imovelMicro, consumoEsgoto,
    		    ControladorConta.LIGACAO_POCO);
    	}
    	
    	// Salvamos que o imovel ja foi calculado
    	// imovelMicro.setIndcImovelCalculado( Constantes.SIM );

    	// Se o imóvel estiver com a situação referente a nitrato
    	if (imovelMicro.getSituacaoTipo() != null
    		&& !imovelMicro.getSituacaoTipo().equals("")) {
    	    if (imovelMicro.getSituacaoTipo().getTipoSituacaoEspecialFeturamento() == SituacaoTipo.NITRATO) {
    		// calcula 50% do valor da água
    		double valorCreditoNitrato = imovelMicro.getValorAgua() / 2;
    		// atualiza o crédito referente a Nitrato com o valor
    		// calculado do crédito
    		imovelMicro.setValorCreditosNitrato(valorCreditoNitrato);
    	    }
    	}

    	// Verifica se o valor de creditos é maior que o valor da conta
    	double valorCreditos = imovelMicro.getValorCreditos();

    	if (valorCreditos != 0d) {
    	    double valorContaSemCreditos = imovelMicro.getValorContaSemCreditos();
    	    if (valorContaSemCreditos < valorCreditos) {
    		double valorResidual = valorCreditos - valorContaSemCreditos;
    		imovelMicro.setValorResidualCredito(valorResidual);
    	    }

    	}

    	// Salvamos o objeto
    	Repositorio.salvarObjeto(imovelMicro);

        }

    
    public void calcularValores(Consumo consumoAgua, Consumo consumoEsgoto) {

		this.setConsumoAgua(consumoAgua);
		this.setConsumoEsgoto(consumoEsgoto);
	
		calcularValores();
    }

    public void calcularValoresCondominio(ImovelConta imovelMicro, Consumo consumoAgua,
    	    Consumo consumoEsgoto) {

    	if (consumoAgua == null){
    		consumoAgua = new Consumo();
    	}
    	this.setConsumoAgua(consumoAgua);
    	
    	if (consumoEsgoto == null){
    		consumoEsgoto = new Consumo();
    	}
    	this.setConsumoEsgoto(consumoEsgoto);

    	calcularValoresCondominio(imovelMicro);
        }

    public Consumo getConsumoAgua() {
	return consumoAgua;
    }

    public Consumo getConsumoEsgoto() {
	return consumoEsgoto;
    }

    public void setConsumoAgua(Consumo consumoAgua) {
    	this.consumoAgua = consumoAgua;
    }

    public void setConsumoEsgoto(Consumo consumoEsgoto) {
    	this.consumoEsgoto = consumoEsgoto;
    }

    /**
     * [UC0970] Efetuar Rateio de Consumo no Dispositivo Movel Metodo
     * responsavel em efeturar a divisão da diferença entre o consumo coletado
     * no hidrometro macro e a soma dos hidrometros micro
     * 
     * @date 26/11/2009
     * @author Bruno Barros
     * @param idPrimeiroImovelMicro
     *            Identifacor, no floggy do imovel macro
     * @param idUltimoImovelMicro
     *            Identifacor, no floggy do último imovel micro
     * @return Object[] 0, Boolean: Caso algum error tenha ocorrido, informamos
     *         1, Boolean: Ainda existem imoveis a serem percorridos; 2,
     *         Integer: Id do imovel condominio que ainda precisa ser percorrido
     */
    public Object[] efetuarRateioConsumoDispositivoMovel(
	    int idPrimeiroImovelMicro, int idUltimoImovelMicro, Progress p) {

	// Carregamos as informações do hidrometro macro
	ImovelConta imovelMacro = new ImovelConta();
	Repositorio.carregarObjeto(imovelMacro, idPrimeiroImovelMicro - 1);

	Object[] retorno = determinarRateio(imovelMacro, p, idPrimeiroImovelMicro, idUltimoImovelMicro);
	
	// Remove todos os condôminos de pendentes e inclui nos concluidos
	if (((Boolean) retorno[0]).booleanValue() && ((Boolean) retorno[1]).booleanValue()) {
	    for (int indice = idPrimeiroImovelMicro - 1; indice <= idUltimoImovelMicro; indice++) {

			// Daniel - lista de imoveis impressos
			Configuracao.getInstancia().getIdsImoveisPendentes().removeElement( new Integer(indice) );

			if (!Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(indice) )) {
				Configuracao.getInstancia().getIdsImoveisConcluidos().addElement( new Integer(indice) );
			}
    		
//			if(	Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId())) ){
//    			Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().removeElement( new Integer(getImovelSelecionado().getId()) );
//    		}				
	    }
	}
	
	Repositorio.salvarObjeto( Configuracao.getInstancia() );
	return retorno;
    }

    /**
     * [UC0970] Efetuar Rateio de Consumo no Dispositivo Movel Metodo
     * responsavel em efetuar a divisão da diferença entre o consumo coletado
     * no hidrometro macro e a soma dos hidrometros micro
     * 
     * [SB0002] Determinar Rateio de Agua
     * 
     * @date 26/11/2009
     * @author Bruno Barros
     * 
     * @return Object[] 0, Boolean: Caso algum erro tenha ocorrido, informamos
     *         1, Boolean: Ainda existem imoveis a serem percorridos; 
     *         2, Integer: Id do imovel condominio que ainda precisa ser percorrido
     * 
     * @param helper
     *            Helper com os dados necessários para execução
     */
    private Object[] determinarRateio(ImovelConta imovelMacro, Progress p, int idPrimeiroImovelMicro, int idUltimoImovelMicro) {
	// Inicializamos o retorno
	Object[] retorno = new Object[3];
	retorno[0] = Boolean.FALSE;
	retorno[1] = Boolean.FALSE;
	retorno[2] = new Integer(Constantes.NULO_INT);
	        
	EfetuarRateioConsumoDispositivoMovelHelper helper = imovelMacro
		.getEfetuarRateioConsumoDispositivoMovelHelper();

	Vector ids = helper.getIdsAindaFaltamSerCalculador();

	// Verificamos se ainda existe algum imovel que não foi percorrido
	if (ids != null && ids.size() > 0) {

	    Integer id = null;

	    for (int i = 0; i < ids.size(); i++) {
		if (id == null
			|| id.intValue() > ((Integer) ids.elementAt(i))
				.intValue()) {
		    id = (Integer) ids.elementAt(i);
		}
	    }

	    Util
		    .mostrarErro("Nem todos os imóveis condominio foram calculados...");

	    retorno[0] = Boolean.FALSE;
	    retorno[1] = Boolean.TRUE;
	    retorno[2] = id;

	    return retorno;
	}

	// Calculamos o valor do consumo a ser rateado na ligação de agua
	if (imovelMacro.getIndcFaturamentoAgua() == Constantes.SIM) {
//   	if ( (getImovelSelecionado().getIndcFaturamentoAgua() == SIM) ||
//      	 (getImovelSelecionado().getIndcFaturamentoAgua() == NAO && getImovelSelecionado().isImovelMicroCondominio()) ){

//   	helper.setConsumoASerRateadoAgua(calcularConsumoAguaASerRateado(imovelMacro));
   		
   		int consumoAguaASerRateadoAgua = imovelMacro.getConsumoAgua().getConsumoCobradoMesOriginal()
		- helper.getConsumoLigacaoAguaTotal();

		System.out.println("MACRO:: " + imovelMacro.getConsumoAgua().getConsumoCobradoMesOriginal());
		System.out.println("TOTAL:: " + helper.getConsumoLigacaoAguaTotal());
		System.out.println("RATEIO:: " + consumoAguaASerRateadoAgua);

   		if (consumoAguaASerRateadoAgua > 0){
   			helper.setConsumoParaRateioAgua(consumoAguaASerRateadoAgua);
   		}else{
   			helper.setConsumoParaRateioAgua(0);
   		}
   		
   		double valorContaAgua = calcularContaAguaParaRateado(imovelMacro);
   		if (valorContaAgua > 0){
   			helper.setContaParaRateioAgua(valorContaAgua);
   			helper.setConsumoParaRateioAgua(consumoAguaASerRateadoAgua);

   		}else{
   			helper.setContaParaRateioAgua(0);
   			helper.setConsumoParaRateioAgua(0);
   		}
	}

	// Calculamos o valor do consumo a ser rateado na ligação de esgoto
	if (imovelMacro.getIndcFaturamentoEsgoto() == Constantes.SIM) {
//   	if (getImovelSelecionado().getIndcFaturamentoEsgoto() == SIM){
//		helper.setConsumoASerRateadoEsgoto(calcularConsumoEsgotoASerRateado(imovelMacro));

   		int consumoEsgotoASerRateadoEsgoto = imovelMacro.getConsumoEsgoto().getConsumoCobradoMesOriginal()
   		- helper.getConsumoLigacaoEsgotoTotal();

   		if (consumoEsgotoASerRateadoEsgoto > 0){
   			helper.setConsumoParaRateioEsgoto(consumoEsgotoASerRateadoEsgoto);
   		}else{
   			helper.setConsumoParaRateioEsgoto(0);
   		}
   		
		System.out.println("ESGOTO MACRO:: " + imovelMacro.getConsumoEsgoto().getConsumoCobradoMesOriginal());
		System.out.println("ESGOTO TOTAL:: " + helper.getConsumoLigacaoEsgotoTotal());
		System.out.println("ESGOTO RATEIO:: " + consumoEsgotoASerRateadoEsgoto);
		System.out.println("ESGOTO PORCENTAGEM:: " + imovelMacro.getPercentCobrancaEsgoto());

   		double valorContaEsgoto = calcularContaEsgotoParaRateado(imovelMacro);
   		if (valorContaEsgoto > 0){
   			helper.setContaParaRateioEsgoto(valorContaEsgoto);
   			helper.setConsumoParaRateioEsgoto(consumoEsgotoASerRateadoEsgoto);

   		}else{
   			helper.setContaParaRateioEsgoto(0);
   			helper.setConsumoParaRateioEsgoto(0);
   		}
	}

//   	if ( (getImovelSelecionado().getIndcFaturamentoAgua() == SIM) ||
//         (getImovelSelecionado().getIndcFaturamentoAgua() == NAO && getImovelSelecionado().isImovelMicroCondominio()) ){
	    // Atualizamos o consumo do hidrometro macro com os valores de agua

	if (imovelMacro.getIndcFaturamentoAgua() == Constantes.SIM) {
//Daniel - Novo método de calculo - condominio
//   		imovelMacro.setValorRateioAgua(helper.getContaASerRateadoAgua());
	    
   		imovelMacro.getConsumoAgua().setConsumoCobradoMesImoveisMicro(
		    helper.getConsumoLigacaoAguaTotal());
	}

	// Caso o consumo de água a ser rateado por economia MEDIDO seja maior
	// que o incremento máximo de consumo por
	// economia (Incremento Máximo de Consumo por Economia), o consumo de
	// água a ser rateado por economia =
	// incremento máximo de consumo por economia;
//	if (consumoAguaASerRateadoPorEconomiaMedido >= ImovelReg11
//		.getInstancia().getIncrementoMaximoConsumoEconomia()) {
//	    consumoAguaASerRateadoPorEconomiaMedido = ImovelReg11
//		    .getInstancia().getIncrementoMaximoConsumoEconomia();
//	}

	// No imóvel MACRO, o consumo rateio esgoto do imóvel (Consumo Rateio
	// Esgoto) =
	// Consumo de esgoto a ser rateado e o consumo de esgoto dos imóveis
	// MICRO (Consumo Esgoto Imóveis MICRO) =
	// soma do consumo de esgoto dos imóveis MICRO;
//	if (imovelMacro.getIndcFaturamentoEsgoto() == Constantes.SIM) {
//Daniel - Novo método de calculo - condominio
	if (imovelMacro.getIndcFaturamentoEsgoto() == Constantes.SIM) {
//		imovelMacro.setValorRateioEsgoto(helper.getConsumoASerRateadoEsgoto());
	    imovelMacro.getConsumoEsgoto().setConsumoCobradoMesImoveisMicro(
		    helper.getConsumoLigacaoEsgotoTotal());
	}


//	if (consumoEsgotoASerRateadoPorEconomiaMedido > ImovelReg11
//		.getInstancia().getIncrementoMaximoConsumoEconomia()) {
//	    consumoEsgotoASerRateadoPorEconomiaMedido = ImovelReg11
//		    .getInstancia().getIncrementoMaximoConsumoEconomia();
//	}

	// Daniel - impedir impressao quando rateio for negativo.
//	if (helper.getConsumoASerRateadoAgua() < 0  || helper.getConsumoASerRateadoEsgoto() < 0){
//	    Util
//	    .mostrarErro("Rateio de consumo negativo. Calculo de todos os imoveis condominiais será realizado pelo valor médio.");
//
//	    recalcularContasCondominio(idPrimeiroImovelMicro, idUltimoImovelMicro, CONSUMO_TIPO_MEDIA_HIDR);
//	    
//		BackGroundTaskEnviarImoveisCondominioOnline bk = new BackGroundTaskEnviarImoveisCondominioOnline(null);
//		bk.start();
//
//		retorno[0] = Boolean.TRUE;
//	    retorno[1] = Boolean.TRUE;
//	    retorno[2] = new Integer(helper.getIdUltimoImovelMicro());
//
//
//	    return retorno;
//	}
	
	// Daniel - verifica se deve reter impressao das contas condominiais.
	if (helper.getReterImpressaoConta()){
	    Util.mostrarErro("Contas do condomínio retidas. Impressao não permitida!");

	    retorno[0] = Boolean.FALSE;
	    retorno[1] = Boolean.FALSE;
	    retorno[2] = new Integer(helper.getIdUltimoImovelMicro());
	    
		// Para todos os imoveis do condominio - Macro e micros
		for (int indiceHidrometroCondominio = imovelMacro.getId(); 
			indiceHidrometroCondominio <= helper.getIdUltimoImovelMicro(); indiceHidrometroCondominio++) {

		    ImovelConta imovelCondominio = new ImovelConta();
		    Repositorio.carregarObjeto(imovelCondominio, indiceHidrometroCondominio);

		    imovelCondominio.setIndcGeracao(Constantes.NAO);
		    
		    if (imovelCondominio.getValorRateioAgua() > 0){
			    imovelCondominio.setValorRateioAgua(0);		    	
		    }
		    
		    if (imovelCondominio.getValorRateioEsgoto() > 0){
			    imovelCondominio.setValorRateioEsgoto(0);		    	
		    }
		    
	    	Repositorio.salvarObjeto(imovelCondominio);
	    	
		    // Daniel - lista de imoveis impressos
			Configuracao.getInstancia().getIdsImoveisPendentes().removeElement( new Integer(imovelCondominio.getId()) );
			if (!Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(imovelCondominio.getId()) )) {
				Configuracao.getInstancia().getIdsImoveisConcluidos().addElement( new Integer(imovelCondominio.getId()) );
			}
		}
	    
		Repositorio.salvarObjeto( Configuracao.getInstancia() );	

	    return retorno;
	}
	
	int quebra = 0;
	int contadorImoveisParaEnvio = 1;
	boolean abortar = false;

	Vector imoveisParaEnvio = new Vector();

	// Caso o valor da conta seja menor que o valor
	// permitido
	// para ser impresso,
	// não imprimir a conta
	boolean imprimirEnviarValorContaMenorMinimo = true;

	// Calculamos o hidrometro macro
	ControladorConta.getInstancia().calcularValoresCondominio(imovelMacro,
		imovelMacro.getConsumoAgua(), imovelMacro.getConsumoEsgoto());
	imoveisParaEnvio.addElement(imovelMacro);

	// Para cada imóvel MICRO ligado ou cortado de água:
	for (int indiceHidrometroMicro = imovelMacro.getId() + 1; indiceHidrometroMicro <= helper
		.getIdUltimoImovelMicro(); indiceHidrometroMicro++) {

	    if (quebra == Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO) {
		if (!Util.perguntarAcao("As últimas "
			+ Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO
			+ " contas foram emitidas com sucesso ?", false, false)) {
		    if (Util
			    .perguntarAcao(
				    "Reemitir últimas "
					    + Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO
					    + " ?", false, false)) {
			indiceHidrometroMicro -= Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO;
			contadorImoveisParaEnvio -= Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO;
			quebra = 0;
		    } else {
			abortar = true;
			break;
		    }
		} else {
		    quebra = 0;
		}
	    }

	    double d = (double) (indiceHidrometroMicro - (imovelMacro.getId() + 1))
		    / (helper.getIdUltimoImovelMicro() - (imovelMacro.getId() + 1));
	    byte percentual = (byte) ((d) * 100);

	    p.setProgress(percentual);
	    p.repaint();

	    ImovelConta imovelMicro = new ImovelConta();
	    Repositorio.carregarObjeto(imovelMicro, indiceHidrometroMicro);

	    if (helper.getContaParaRateioAgua() >= 0){
		    
	    	if (imovelMicro.getConsumoAgua() == null){
	    		imovelMicro.setConsumoAgua(new Consumo());
	    	}
	    	
	    	imovelMicro.setValorRateioAgua(Util.arredondar(
	    			helper.getContaParaRateioAgua() * imovelMicro.getQuantidadeEconomiasTotal() 
	    			/ helper.getQuantidadeEconomiasAguaTotal(),2));
	    	
	    	imovelMicro.setConsumoRateioAgua(helper.getConsumoParaRateioAgua() * imovelMicro.getQuantidadeEconomiasTotal()
	    		    / helper.getQuantidadeEconomiasAguaTotal());
	    }
	    
	    if (helper.getContaParaRateioEsgoto() >= 0){
	    	
	    	if (imovelMicro.getConsumoEsgoto() == null){
	    		imovelMicro.setConsumoEsgoto(new Consumo());
	    	}

	    	imovelMicro.setValorRateioEsgoto(Util.arredondar(
	    			helper.getContaParaRateioEsgoto() * imovelMicro.getQuantidadeEconomiasTotal() 
	    			/ helper.getQuantidadeEconomiasEsgotoTotal(),2));

	    	imovelMicro.setConsumoRateioEsgoto(helper.getConsumoParaRateioEsgoto() * imovelMicro.getQuantidadeEconomiasTotal()
	    		    / helper.getQuantidadeEconomiasEsgotoTotal());
	    }
	    
	    Repositorio.salvarObjeto(imovelMicro);

	    // Calculamos as contas imovel a imovel
	    ControladorConta.getInstancia().calcularValoresCondominio(imovelMicro,
		    imovelMicro.getConsumoAgua(),
		    imovelMicro.getConsumoEsgoto());

	    // Caso o imóvel ja exista na lista não deve ser adicionado novamente
	    if (!imoveisParaEnvio.contains(imovelMicro)) {
		imoveisParaEnvio.addElement(imovelMicro);
	    }

	    // Caso o valor da conta seja menor que o valor
	    // permitido para ser impresso, não imprimir a conta
	    // ou
	    // o valor do crédito for maior que o valor da conta, não imprime a conta
	    imprimirEnviarValorContaMenorMinimo = imovelMicro.isValorContaAcimaDoMinimo();

	    boolean error = false;

	    // Imprimimos, Conta
	    if (imprimirEnviarValorContaMenorMinimo
		    && imovelMicro.getIndcEmissaoConta() == Constantes.SIM) {

		    if (imovelMicro != null) {
				ControladorImoveis.getInstancia().setImovelSelecionado(imovelMicro);
		    }

	    	error = Util.imprimirConta(Constantes.IMPRESSAO_NORMAL);
	    	quebra++;
	    
	    }else{
		    if (imovelMicro != null) {

				// Daniel - remove imovel da lista de imoveis pendentes
				Configuracao.getInstancia().getIdsImoveisPendentes().removeElement( new Integer(imovelMicro.getId()) );
				
				if (!Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(imovelMicro.getId()) )) {
					Configuracao.getInstancia().getIdsImoveisConcluidos().addElement( new Integer(imovelMicro.getId()) );
				}
		    }
	    }

	    if (error) {
		abortar = true;
		break;
	    }

	    System.out.println("********** " + imovelMicro.getMatricula() + "********** ");
	    System.out.println("Consumo Agua: " + (imovelMicro.getConsumoAgua() != null ? imovelMicro
			    .getConsumoAgua().getConsumoCobradoMes() + "" : "Nulo"));
	    System.out.println("Valor de Rateio de Agua: "  + (imovelMicro.getConsumoAgua() != null ? imovelMicro
			    .getValorRateioAgua() + "" : "Nulo"));
	    System.out.println("Consumo Esgoto: " + (imovelMicro.getConsumoEsgoto() != null ? imovelMicro
			    .getConsumoEsgoto().getConsumoCobradoMes() + "" : "Nulo"));
	    System.out.println("Valor de Rateio de Esgoto: " + (imovelMicro.getConsumoEsgoto() != null ? imovelMicro
			    .getValorRateioEsgoto() + "" : "Nulo"));
	    System.out.println("*************************** ");

	    // Perguntamos na ultima conta
	    if (indiceHidrometroMicro == helper.getIdUltimoImovelMicro()) {
			if (!Util.perguntarAcao("As últimas " + quebra
				+ " contas foram emitidas com sucesso ?", false, false)) {
			    if (Util.perguntarAcao("Reemitir últimas " + quebra + " ?",
				    false, false)) {
				indiceHidrometroMicro -= quebra;
				contadorImoveisParaEnvio -= quebra;
				quebra = 0;
			    } else {
				abortar = true;
				break;
			    }
			    // android
			} else {
			    quebra = 0;
			    imovelMacro.setIndcImovelImpresso(Constantes.SIM);
			    Repositorio.salvarObjeto(imovelMacro);
	
			    // Daniel - lista de imoveis impressos
				Configuracao.getInstancia().getIdsImoveisPendentes().removeElement( new Integer(imovelMacro.getId()) );
				if (!Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(imovelMacro.getId()) )) {
					Configuracao.getInstancia().getIdsImoveisConcluidos().addElement( new Integer(imovelMacro.getId()) );
				}
			}
	    }
	}

	if (!abortar) {
	    Util.imprimirExtratoConsumoMacroMedidor(imovelMacro);

	    // Caso o valor da conta seja menor que o valor
	    // permitido
	    // para ser impresso,
	    // não envia as contas do condomínio
	    // ou
	    // o valor do crédito for maior que o valor da conta,
	    // não envia as contas do condomínio
	    if (imprimirEnviarValorContaMenorMinimo) {
		// Mandamos via tread
		BackGroundTaskEnviarImoveisCondominioOnline bk = new BackGroundTaskEnviarImoveisCondominioOnline(
			imoveisParaEnvio);
		bk.start();
	    }

	    retorno[0] = Boolean.FALSE;
	} else {
	    retorno[0] = Boolean.TRUE;
	}

	return retorno;
    }

    private int calcularConsumoAguaASerRateado(ImovelConta imovelMacro) {

	EfetuarRateioConsumoDispositivoMovelHelper helper = imovelMacro
		.getEfetuarRateioConsumoDispositivoMovelHelper();

	// Calculamos o valor do consumo a ser rateado na ligação de agua

	int consumoAguaASerRateadoAgua = imovelMacro.getConsumoAgua().getConsumoCobradoMesOriginal()
		- helper.getConsumoLigacaoAguaTotal();

	// Caso o consumo de agua a ser rateado seja maior que zero e o consumo
	// de agua do imovel macro seja menor ou igual a soma dos consumo
	// mínimos, atribuir
	// valor zero ao consumo de agua a ser rateado

//Daniel - Novo método de calculo - condominio
	if (consumoAguaASerRateadoAgua > 0
		&& imovelMacro.getConsumoAgua().getConsumoCobradoMesOriginal() <= helper
			.getConsumoMinimoTotal()) {
	    consumoAguaASerRateadoAgua = 0;
	}

	// Caso o valor absoluto do consumo de Agua a ser rateado seja menor
	// ou igual a consumo de Agua do imovel macro * percentual de
	// tolerancia para
	// rateio do consumo atribuir zero ao consumo Agua a ser rateado

//Daniel - Novo método de calculo - condominio
	if (Math.abs(consumoAguaASerRateadoAgua) <= imovelMacro
		.getConsumoAgua().getConsumoCobradoMesOriginal()
		* (ImovelReg11.getInstancia().getPercentToleranciaRateio() / 100)) {
	    consumoAguaASerRateadoAgua = 0;
	}
		return consumoAguaASerRateadoAgua;
    }
    
    private int calcularConsumoEsgotoASerRateado(ImovelConta imovelMacro) {

	EfetuarRateioConsumoDispositivoMovelHelper helper = imovelMacro
		.getEfetuarRateioConsumoDispositivoMovelHelper();

	// Calculamos o valor do consumo a ser rateado na ligação de esgoto
	// Consumo de esgoto a ser rateado = consumo de esgoto do imóvel MACRO –
	// soma do consumo de esgoto dos imóveis MICRO;
	int consumoEsgotoASerRateadoEsgoto = imovelMacro.getConsumoEsgoto()
		.getConsumoCobradoMesOriginal()
		- helper.getConsumoLigacaoEsgotoTotal();

	// Caso o consumo de Esgoto a ser rateado seja maior que sero
	// e o consumo de Esgoto do imovel macro seja menor ou igual a soma dos
	// consumo
	// mínimos, atrituir valor zero ao consumo de Esgoto a ser rateado

//Daniel - Novo método de calculo - condominio
	if (consumoEsgotoASerRateadoEsgoto > 0
		&& imovelMacro.getConsumoEsgoto()
			.getConsumoCobradoMesOriginal() <= helper
			.getConsumoMinimoTotal()) {
	    consumoEsgotoASerRateadoEsgoto = 0;
	}

	// Caso o valor absoluto do consumo de Esgoto a ser rateado seja menor
	// ou igual a consumo de Esgoto do imovel marco * percentual de
	// tolerancia para rateio do consumo
	// atribuir zero ao consumo Esgoto a ser rateado

//Daniel - Novo método de calculo - condominio
	if (Math.abs(consumoEsgotoASerRateadoEsgoto) <= imovelMacro
		.getConsumoEsgoto().getConsumoCobradoMesOriginal()
		* (ImovelReg11.getInstancia().getPercentToleranciaRateio() / 100)) {
	    consumoEsgotoASerRateadoEsgoto = 0;
	}

	return consumoEsgotoASerRateadoEsgoto;
    }

  //Daniel - Novo método de calculo - condominio
    private double calcularContaAguaParaRateado(ImovelConta imovelMacro) {

    	EfetuarRateioConsumoDispositivoMovelHelper helper = imovelMacro
    		.getEfetuarRateioConsumoDispositivoMovelHelper();

    	boolean imovelComDebitoTipoCortado = false;
    	
    	RegistroBasico debito = getImovelSelecionado().getDebito( RegistroBasico.TARIFA_CORTADO_DEC_18_251_94 );
    	
    	if ( debito != null && debito.getIndcUso() == Constantes.SIM ){
    	    imovelComDebitoTipoCortado = true;
    	}

    	if ( ((imovelMacro.getIndcFaturamentoAgua() == SIM) ||  (imovelMacro.getIndcFaturamentoAgua() == NAO && imovelMacro.isImovelMicroCondominio()))&& 
    			imovelMacro.getIndicadorParalizarFaturamentoAgua() == NAO && !imovelComDebitoTipoCortado ) {
    		
    		ControladorImoveis.getInstancia().calcularValores(imovelMacro, 
    			    										  consumoAgua,
    			    										  ControladorConta.LIGACAO_AGUA);
    	}
    	System.out.println("Conta de água a ser rateada: " + imovelMacro.getValorAgua());
    	return imovelMacro.getValorAgua();
    }

  //Daniel - Novo método de calculo - condominio
    private double calcularContaEsgotoParaRateado(ImovelConta imovelMacro) {

    	EfetuarRateioConsumoDispositivoMovelHelper helper = imovelMacro
    		.getEfetuarRateioConsumoDispositivoMovelHelper();

    	if ( (getImovelSelecionado().getIndcFaturamentoEsgoto() == SIM) && getImovelSelecionado().getIndicadorParalizarFaturamentoEsgoto() == NAO) {
    		
    		ControladorImoveis.getInstancia().calcularValores(imovelMacro, 
    														  consumoEsgoto,
    														  ControladorConta.LIGACAO_POCO);
    	}
    	
    	System.out.println("Conta de esgoto a ser rateada: " + imovelMacro.getValorEsgoto());

    	return imovelMacro.getValorEsgoto();
	}

    public boolean verificarEstouroConsumo(Consumo consumo,
	    ImovelReg8 reg8) {

	int cMedio;

	// Verificamos se o consumo médio veio do
	// registro tipo 8 ou do imóvel
	if (reg8 != null) {
	    cMedio = reg8.getConsumoMedio();
	} else {
	    cMedio = getImovelSelecionado().getConsumoMedio();
	}

	boolean estouro = false;

	// [SB0000] 4.
	// [SB0006] - Verificar Estouro de Consumo
	// [SB0006] 1.
	int resultado = Util.arredondar(getImovelSelecionado().getFatorMultEstouro() * cMedio);

	if (consumo.getConsumoCobradoMes() > getImovelSelecionado().getConsumoEstouro()
		&& consumo.getConsumoCobradoMes() > resultado) {

	    int anormConsumo = Consumo.CONSUMO_ANORM_ESTOURO;

	    int idImovelPerfil = Util
		    .verificarNuloInt(ControladorImoveis.getInstancia().getImovelSelecionado().getCodigoPerfil());

	    int categoriaPrincipal = getImovelSelecionado().pesquisarPrincipalCategoria();

	    ImovelReg12 consumoAnormalidadeAcao = ImovelReg12.getInstancia()
		    .getRegistro12(anormConsumo, categoriaPrincipal,
			    idImovelPerfil);

	    if (consumoAnormalidadeAcao != null) {

		int idLeituraAnormalidadeConsumo = Constantes.NULO_INT;
		double numerofatorConsumo = Constantes.NULO_DOUBLE;
		String mensagemContaPrimeiroMes = consumoAnormalidadeAcao
			.getMensagemContaPrimeiroMes();
		String mensagemContaSegundoMes = consumoAnormalidadeAcao
			.getMensagemContaSegundoMes();
		String mensagemContaTerceiroMes = consumoAnormalidadeAcao
			.getMensagemContaTerceiroMes();

		// [SB0006] 1.1.
		/*
		 * Calendar c = Calendar.getInstance();
		 * c.setTime(reg8.getDataLeitura());
		 */

		/*
		 * int anoLeitura = c.get(Calendar.YEAR); int mesLeitura =
		 * c.get(Calendar.MONTH); int dia = c.get( Calendar.DAY_OF_MONTH
		 * );
		 */

		int anoMes = Util.subtrairMesDoAnoMes(Util
			.verificarNuloInt(ImovelReg11.getInstancia().getRegistro11().getAnoMesFaturamento()), 1);
		// int anoMes =
		// Util.subtrairMesDoAnoMes(Util.getAnoMes(c.getTime()), 1);

		ImovelReg3 reg3MesAnterior = getImovelSelecionado().getRegistro3(anoMes,
			anormConsumo);

		if (reg3MesAnterior == null || reg3MesAnterior.equals("")) {
		    idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
			    .getIdLeituraAnormalidadeConsumoPrimeiroMes();

		    numerofatorConsumo = consumoAnormalidadeAcao
			    .getFatorConsumoPrimeiroMes();

		    if (mensagemContaPrimeiroMes != null) {

			String[] mensagem = Util.dividirString(
				mensagemContaPrimeiroMes, 40);

			switch (mensagem.length) {
			case 3:
			    getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			case 2:
			    getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			case 1:
			    getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
			    break;
			}

			/*
			 * getImovelSelecionado().setMensagemConta1(mensagemContaPrimeiroMes
			 * .substring(0, 40));
			 * getImovelSelecionado().setMensagemConta2(mensagemContaPrimeiroMes
			 * .substring(40, 80));5
			 * getImovelSelecionado().setMensagemConta3(mensagemContaPrimeiroMes
			 * .substring(80, mensagemContaPrimeiroMes .length()));
			 */
			Repositorio.salvarObjeto(getImovelSelecionado());
		    }

		} else {
		    anoMes = Util.subtrairMesDoAnoMes(Util
			    .verificarNuloInt(getImovelSelecionado().getAnoMesConta()), 2);

		    ImovelReg3 reg3SegundoMesAnterior = getImovelSelecionado().getRegistro3(
			    anoMes, anormConsumo);

		    if (reg3SegundoMesAnterior == null
			    || reg3SegundoMesAnterior.equals("")) {
			idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
				.getIdLeituraAnormalidadeConsumoSegundoMes();

			numerofatorConsumo = consumoAnormalidadeAcao
				.getFatorConsumoSegundoMes();

			if (mensagemContaSegundoMes != null) {

			    String[] mensagem = Util.dividirString(
				    mensagemContaSegundoMes, 40);

			    switch (mensagem.length) {
			    case 3:
				getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			    case 2:
				getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			    case 1:
				getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
				break;
			    }

			    Repositorio.salvarObjeto(getImovelSelecionado());
			}

		    } else {
			idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
				.getIdLeituraAnormalidadeConsumoTerceiroMes();

			numerofatorConsumo = consumoAnormalidadeAcao
				.getFatorConsumoTerceiroMes();

			if (mensagemContaTerceiroMes != null) {

			    String[] mensagem = Util.dividirString(
				    mensagemContaTerceiroMes, 40);

			    switch (mensagem.length) {
			    case 3:
				getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			    case 2:
				getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			    case 1:
				getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
				break;
			    }

			    Repositorio.salvarObjeto(getImovelSelecionado());
			}

		    }
		}

		// 3.1.1.1. O sistema gera a Anormalidade de Consumo com o valor
		// correspondente a estouro de consumo da tabela
		// CONSUMO_ANORMALIDADE
		consumo.setAnormalidadeConsumo(anormConsumo);

		if (idLeituraAnormalidadeConsumo == NAO_OCORRE) {

		    consumo.setConsumoCobradoMes(getImovelSelecionado().getConsumoMedio());
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo == MINIMO) {

		    // O Consumo a Ser Cobrado no Mês será o valor retornado
		    // por [UC0105 – Obter Consumo Mínimo da Ligação
		    consumo.setConsumoCobradoMes(getImovelSelecionado()
			    .getConsumoMinimoImovel());
		    // Seta o tipo de consumo
		    consumo.setTipoConsumo(CONSUMO_TIPO_MINIMO_FIX);

		} else if (idLeituraAnormalidadeConsumo == MEDIA) {

		    // Consumo a ser cobrado no mês será o consumo médio do
		    // hidrômetro
		    consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo == NORMAL) {

		    // Fazer nada já calculado

		} else if (idLeituraAnormalidadeConsumo == MAIOR_ENTRE_O_CONSUMO_MEDIO) {

		    if (cMedio > consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		} else if (idLeituraAnormalidadeConsumo == MENOR_ENTRE_O_CONSUMO_MEDIO) {
		    if (cMedio < consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		}

		// 3.1.4. O consumo a Ser Cobrado no Mês será igual
		// ao Consumo a Ser Cobrado no Mês multiplicado pelo
		// fator de multiplicação da quantidade de vezes a média
		// (CSAA_NNFATORCONSUMOMES(1,2 ou 3), dependendo do mês
		// calculado anteriormente
		if (numerofatorConsumo != Constantes.NULO_DOUBLE) {
		    double consumofaturadoMes = consumo.getConsumoCobradoMes();
		    consumofaturadoMes = consumofaturadoMes
			    * numerofatorConsumo;
		    int consumofaturadoMesInt = Util
			    .arredondar(consumofaturadoMes);
		    consumo.setConsumoCobradoMes(consumofaturadoMesInt);
		}

	    } else {
	        // [SB0006] 1.1.
	        //Calendar c = Calendar.getInstance();
	        //c.setTime( getImovelSelecionado().getdata );

	        int anoMes = Util.subtrairMesDoAnoMes( Integer.parseInt( ImovelReg11.getInstancia().getRegistro11().getAnoMesFaturamento() ) , 1);
	        ImovelReg3 reg3 = getImovelSelecionado().getRegistro3(anoMes);


		int anormConsumoAnterior = Constantes.NULO_INT;
		if (reg3 != null) {
		    anormConsumoAnterior = reg3.getAnormalidadeConsumo();
		}

	        boolean cond1 = anormConsumoAnterior != Constantes.NULO_INT
	            && anormConsumoAnterior != Consumo.CONSUMO_ANORM_ESTOURO
	            && anormConsumoAnterior != Consumo.CONSUMO_ANORM_ESTOURO_MEDIA;

	        // [SB0006] 1.1. (continuação)
	        if (cond1 || 
	            consumo.getConsumoCobradoMes() > getImovelSelecionado().getConsumoMaximo() || 
	            anormConsumoAnterior == Constantes.NULO_INT) {
	            // [SB0006] 1.1.1.
	            consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_ESTOURO_MEDIA);

	            // [SB0006] 1.1.2.
	            consumo.setConsumoCobradoMes(cMedio);

	            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
	            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);

	            // [SB0006] 1.1.3.
	            consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

	            // [SB0006] 1.2.
	        } else {
	            consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_ESTOURO);
	        }


	    }
	    estouro = true;
	}

	return estouro;
    }

    public void verificarAltoConsumo(Consumo consumo,
	    ImovelReg8 reg8) {

	int cMedio;

	// Verificamos se o consumo médio veio do
	// registro tipo 8 ou do imóvel
	if (reg8 != null) {
	    cMedio = reg8.getConsumoMedio();
	} else {
	    cMedio = getImovelSelecionado().getConsumoMedio();
	}

	// [SB0007] - Verificar Alto Consumo
	int resultado = Util.arredondar(getImovelSelecionado().getFatorMultMediaAltoConsumo()
		* cMedio);

	if (consumo.getConsumoCobradoMes() > getImovelSelecionado().getAltoConsumo()
		&& consumo.getConsumoCobradoMes() > resultado) {

	    int anormConsumo = Consumo.CONSUMO_ANORM_ALTO_CONSUMO;

	    int idImovelPerfil = Util
		    .verificarNuloInt(getImovelSelecionado().getCodigoPerfil());

	    int categoriaPrincipal = getImovelSelecionado().pesquisarPrincipalCategoria();

	    ImovelReg12 consumoAnormalidadeAcao = ImovelReg12.getInstancia()
		    .getRegistro12(anormConsumo, categoriaPrincipal,
			    idImovelPerfil);

	    if (consumoAnormalidadeAcao != null) {

		String mensagemContaPrimeiroMes = consumoAnormalidadeAcao
			.getMensagemContaPrimeiroMes();
		String mensagemContaSegundoMes = consumoAnormalidadeAcao
			.getMensagemContaSegundoMes();
		String mensagemContaTerceiroMes = consumoAnormalidadeAcao
			.getMensagemContaTerceiroMes();

		int idLeituraAnormalidadeConsumo = Constantes.NULO_INT;
		double numerofatorConsumo = Constantes.NULO_DOUBLE;

		/*
		 * Calendar c = Calendar.getInstance();
		 * c.setTime(reg8.getDataLeitura());
		 */

		int anoMes = Util.subtrairMesDoAnoMes(Util
			.verificarNuloInt(getImovelSelecionado().getAnoMesConta()), 1);
		// int anoMes =
		// Util.subtrairMesDoAnoMes(Util.getAnoMes(c.getTime()), 1);

		ImovelReg3 reg3MesAnterior = getImovelSelecionado().getRegistro3(anoMes,
			anormConsumo);

		if (reg3MesAnterior == null || reg3MesAnterior.equals("")) {
		    idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
			    .getIdLeituraAnormalidadeConsumoPrimeiroMes();

		    numerofatorConsumo = consumoAnormalidadeAcao
			    .getFatorConsumoPrimeiroMes();

		    if (mensagemContaPrimeiroMes != null) {

			String[] mensagem = Util.dividirString(
				mensagemContaPrimeiroMes, 40);

			switch (mensagem.length) {
			case 3:
			    getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			case 2:
			    getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			case 1:
			    getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
			    break;
			}

			Repositorio.salvarObjeto(getImovelSelecionado());
		    }

		} else {

		    anoMes = Util.subtrairMesDoAnoMes(Util
			    .verificarNuloInt(getImovelSelecionado().getAnoMesConta()), 2);
		    ImovelReg3 reg3SegundoMesAnterior = getImovelSelecionado().getRegistro3(
			    anoMes, anormConsumo);

		    if (reg3SegundoMesAnterior == null
			    || reg3SegundoMesAnterior.equals("")) {
			idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
				.getIdLeituraAnormalidadeConsumoSegundoMes();

			numerofatorConsumo = consumoAnormalidadeAcao
				.getFatorConsumoSegundoMes();

			if (mensagemContaSegundoMes != null) {

			    String[] mensagem = Util.dividirString(
				    mensagemContaSegundoMes, 40);

			    switch (mensagem.length) {
			    case 3:
				getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			    case 2:
				getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			    case 1:
				getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
				break;
			    }

			    Repositorio.salvarObjeto(getImovelSelecionado());
			}

		    } else {
			idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
				.getIdLeituraAnormalidadeConsumoTerceiroMes();

			numerofatorConsumo = consumoAnormalidadeAcao
				.getFatorConsumoTerceiroMes();

			if (mensagemContaTerceiroMes != null) {

			    String[] mensagem = Util.dividirString(
				    mensagemContaTerceiroMes, 40);

			    switch (mensagem.length) {
			    case 3:
				getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			    case 2:
				getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			    case 1:
				getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
				break;
			    }

			    Repositorio.salvarObjeto(getImovelSelecionado());
			}

		    }
		}

		// 3.1.1.1. O sistema gera a Anormalidade de Consumo com o valor
		// correspondente a estouro de consumo da tabela
		// CONSUMO_ANORMALIDADE
		consumo.setAnormalidadeConsumo(anormConsumo);

		if (idLeituraAnormalidadeConsumo == NAO_OCORRE) {

		    consumo.setConsumoCobradoMes(cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo == MINIMO) {

		    // O Consumo a Ser Cobrado no Mês será o valor retornado
		    // por [UC0105 – Obter Consumo Mínimo da Ligação
		    consumo.setConsumoCobradoMes(getImovelSelecionado()
			    .getConsumoMinimoImovel());
		    // Seta o tipo de consumo
		    consumo.setTipoConsumo(CONSUMO_TIPO_MINIMO_FIX);

		} else if (idLeituraAnormalidadeConsumo == MEDIA) {

		    // Consumo a ser cobrado no mês será o consumo médio do
		    // hidrômetro
		    consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo == NORMAL) {

		    // Fazer nada já calculado

		} else if (idLeituraAnormalidadeConsumo == MAIOR_ENTRE_O_CONSUMO_MEDIO) {

		    if (cMedio > consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		} else if (idLeituraAnormalidadeConsumo == MENOR_ENTRE_O_CONSUMO_MEDIO) {
		    if (cMedio < consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		}

		// 3.1.4. O consumo a Ser Cobrado no Mês será igual
		// ao Consumo a Ser Cobrado no Mês multiplicado pelo
		// fator de multiplicação da quantidade de vezes a média
		// (CSAA_NNFATORCONSUMOMES(1,2 ou 3), dependendo do mês
		// calculado anteriormente
		if (numerofatorConsumo != Constantes.NULO_DOUBLE) {
		    double consumofaturadoMes = consumo.getConsumoCobradoMes();
		    consumofaturadoMes = consumofaturadoMes
			    * numerofatorConsumo;
		    int consumofaturadoMesInt = Util
			    .arredondar(consumofaturadoMes);
		    consumo.setConsumoCobradoMes(consumofaturadoMesInt);
		}

	    } else {
	    	consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_ALTO_CONSUMO);
	    }

	}

    }

    public void verificarBaixoConsumo(Consumo consumo,
	    ImovelReg8 reg8) {

	int cMedio;

	// Verificamos se o consumo médio veio do
	// registro tipo 8 ou do imóvel
	if (reg8 != null) {
	    cMedio = reg8.getConsumoMedio();
	} else {
	    cMedio = getImovelSelecionado().getConsumoMedio();
	}

	// [SB0008] - Verificar Baixo Consumo
	double percentual = getImovelSelecionado().getPercentBaixoConsumo() / 100;
	double consumoMedioPercent = cMedio * percentual;

	if (cMedio > getImovelSelecionado().getBaixoConsumo()
		&& consumo.getConsumoCobradoMes() < consumoMedioPercent) {

	    int anormConsumo = Consumo.CONSUMO_ANORM_BAIXO_CONSUMO;

	    int idImovelPerfil = Util
		    .verificarNuloInt(getImovelSelecionado().getCodigoPerfil());

	    int categoriaPrincipal = getImovelSelecionado().pesquisarPrincipalCategoria();

	    ImovelReg12 consumoAnormalidadeAcao = ImovelReg12.getInstancia()
		    .getRegistro12(anormConsumo, categoriaPrincipal,
			    idImovelPerfil);

	    if (consumoAnormalidadeAcao != null) {

		int idLeituraAnormalidadeConsumo = Constantes.NULO_INT;
		double numerofatorConsumo = Constantes.NULO_DOUBLE;

		/*
		 * Calendar c = Calendar.getInstance();
		 * c.setTime(reg8.getDataLeitura());
		 */

		int anoMes = Util.subtrairMesDoAnoMes(Util
			.verificarNuloInt(ImovelReg11.getInstancia().getRegistro11().getAnoMesFaturamento()), 1);
		// int anoMes =
		// Util.subtrairMesDoAnoMes(Util.getAnoMes(c.getTime()), 1);
		ImovelReg3 reg3MesAnterior = getImovelSelecionado().getRegistro3(anoMes,
			anormConsumo);

		String mensagemContaPrimeiroMes = consumoAnormalidadeAcao
			.getMensagemContaPrimeiroMes();
		String mensagemContaSegundoMes = consumoAnormalidadeAcao
			.getMensagemContaSegundoMes();
		String mensagemContaTerceiroMes = consumoAnormalidadeAcao
			.getMensagemContaTerceiroMes();

		if (reg3MesAnterior == null || reg3MesAnterior.equals("")) {
		    idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
			    .getIdLeituraAnormalidadeConsumoPrimeiroMes();

		    numerofatorConsumo = consumoAnormalidadeAcao
			    .getFatorConsumoPrimeiroMes();

		    if (mensagemContaPrimeiroMes != null) {

			String[] mensagem = Util.dividirString(
				mensagemContaPrimeiroMes, 40);

			switch (mensagem.length) {
			case 3:
			    getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			case 2:
			    getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			case 1:
			    getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
			    break;
			}

			/*
			 * getImovelSelecionado().setMensagemConta1(mensagemContaPrimeiroMes
			 * .substring(0, 40));
			 * getImovelSelecionado().setMensagemConta2(mensagemContaPrimeiroMes
			 * .substring(40, 80));
			 * getImovelSelecionado().setMensagemConta3(mensagemContaPrimeiroMes
			 * .substring(80, mensagemContaPrimeiroMes .length()));
			 */

			Repositorio.salvarObjeto(getImovelSelecionado());

		    }

		} else {

		    anoMes = Util.subtrairMesDoAnoMes(Util
			    .verificarNuloInt(getImovelSelecionado().getAnoMesConta()), 2);
		    ImovelReg3 reg3SegundoMesAnterior = getImovelSelecionado().getRegistro3(
			    anoMes, anormConsumo);

		    if (reg3SegundoMesAnterior == null
			    || reg3SegundoMesAnterior.equals("")) {
			idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
				.getIdLeituraAnormalidadeConsumoSegundoMes();

			numerofatorConsumo = consumoAnormalidadeAcao
				.getFatorConsumoSegundoMes();

			if (mensagemContaSegundoMes != null) {

			    String[] mensagem = Util.dividirString(
				    mensagemContaSegundoMes, 40);

			    switch (mensagem.length) {
			    case 3:
				getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			    case 2:
				getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			    case 1:
				getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
				break;
			    }
			    Repositorio.salvarObjeto(getImovelSelecionado());
			}

		    } else {
			idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
				.getIdLeituraAnormalidadeConsumoTerceiroMes();

			numerofatorConsumo = consumoAnormalidadeAcao
				.getFatorConsumoTerceiroMes();

			if (mensagemContaTerceiroMes != null) {

			    String[] mensagem = Util.dividirString(
				    mensagemContaTerceiroMes, 40);

			    switch (mensagem.length) {
			    case 3:
				getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			    case 2:
				getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			    case 1:
				getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
				break;
			    }
			    Repositorio.salvarObjeto(getImovelSelecionado());
			}
		    }
		}

		// 3.1.1.1. O sistema gera a Anormalidade de Consumo com o valor
		// correspondente a estouro de consumo da tabela
		// CONSUMO_ANORMALIDADE
		consumo.setAnormalidadeConsumo(anormConsumo);

		if (idLeituraAnormalidadeConsumo == NAO_OCORRE) {

		    consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo == MINIMO) {

		    // O Consumo a Ser Cobrado no Mês será o valor retornado
		    // por [UC0105 – Obter Consumo Mínimo da Ligação
		    consumo.setConsumoCobradoMes(getImovelSelecionado()
			    .getConsumoMinimoImovel());
		    // Seta o tipo de consumo
		    consumo.setTipoConsumo(CONSUMO_TIPO_MINIMO_FIX);

		} else if (idLeituraAnormalidadeConsumo == MEDIA) {

		    // Consumo a ser cobrado no mês será o consumo médio do
		    // hidrômetro
		    consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo == NORMAL) {

		    // Fazer nada já calculado

		} else if (idLeituraAnormalidadeConsumo == MAIOR_ENTRE_O_CONSUMO_MEDIO) {

		    if (cMedio > consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		} else if (idLeituraAnormalidadeConsumo == MENOR_ENTRE_O_CONSUMO_MEDIO) {
		    if (cMedio < consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		}

		// 3.1.4. O consumo a Ser Cobrado no Mês será igual
		// ao Consumo a Ser Cobrado no Mês multiplicado pelo
		// fator de multiplicação da quantidade de vezes a média
		// (CSAA_NNFATORCONSUMOMES(1,2 ou 3), dependendo do mês
		// calculado anteriormente
		if (numerofatorConsumo != Constantes.NULO_DOUBLE) {
		    double consumofaturadoMes = consumo.getConsumoCobradoMes();
		    consumofaturadoMes = consumofaturadoMes
			    * numerofatorConsumo;
		    int consumofaturadoMesInt = Util
			    .arredondar(consumofaturadoMes);
		    consumo.setConsumoCobradoMes(consumofaturadoMesInt);
		}

	    } else {
   
			consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_BAIXO_CONSUMO);

	    }

	}

    }

    /**
     * [UC0101] - Consistir Leituras e Calcular Consumos [SF0012] - Obter
     * Leitura Anterior
     * 
     * @author: Breno Santos
     */
    protected int obterLeituraAnterior(ImovelReg8 reg8) {
	int retorno = 0;

	if (reg8 != null) {

	    if (reg8.getLeituraAnteriorInformada() != Constantes.NULO_INT
		    && reg8.getLeitura() != Constantes.NULO_INT) {

		if (reg8.getLeituraAnteriorInformada() == reg8.getLeitura()) {
		    retorno = reg8.getLeituraAnteriorInformada();
		} else {
		    retorno = reg8.getLeituraAnteriorFaturamento();
		}

	    } else {

		retorno = reg8.getLeituraAnteriorFaturamento();
	    }

	}
	return retorno;
    }

    private void dadosFaturamentoEspecialNaoMedido(Consumo consumo, int ligacaoTipo) {

	ImovelReg8 reg8 = getImovelSelecionado().getRegistro8(ligacaoTipo);

	int cMedio;

	// Verificamos se o consumo médio veio do
	// registro tipo 8 ou do imóvel
	if (reg8 != null) {
	    cMedio = reg8.getConsumoMedio();
	} else {
	    cMedio = getImovelSelecionado().getConsumoMedio();
	}

	if (getImovelSelecionado().getSituacaoTipo() != null) {
	    if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == NAO_OCORRE) {
		consumo.setConsumoCobradoMes(cMedio);
		consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_IMOV);
	    } else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == MINIMO) {
	    	consumo.setConsumoCobradoMes(getImovelSelecionado().getConsumoMinimoImovel());
		consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
	    } else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == MEDIA) {
		consumo.setConsumoCobradoMes(cMedio);
		consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
// Daniel - Situaçao especial de faturamento - "FATURAR CONSUMO/VOLUME INFORMADO"
	    } else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == FIXO) {
			consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);

			if (ligacaoTipo == Constantes.LIGACAO_AGUA) {
			    if (getImovelSelecionado().getSituacaoTipo().getConsumoAguaNaoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {
				    consumo.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo().getConsumoAguaNaoMedidoHistoricoFaturamento());
			    }
			} else if (getImovelSelecionado().getSituacaoTipo().getVolumeEsgotoNaoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {
				consumo.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo().getVolumeEsgotoNaoMedidoHistoricoFaturamento());
			}

	    } else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraSemLeitura() == FIXO) {
		consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);

		if (ligacaoTipo == Constantes.LIGACAO_AGUA) {

		    if (getImovelSelecionado().getSituacaoTipo()
			    .getConsumoAguaNaoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {

			/*
			 * Caso o consumo calculado seja MENOR que o consumo
			 * fixo, colocar o consumo calculado; caso contrário,
			 * colocar o consumo fixo.
			 */
			if (consumo.getConsumoCobradoMes() > getImovelSelecionado().getSituacaoTipo()
				.getConsumoAguaNaoMedidoHistoricoFaturamento()) {

			    consumo
				    .setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo()
					    .getConsumoAguaNaoMedidoHistoricoFaturamento());
			}
		    }

		} else if (getImovelSelecionado().getSituacaoTipo()
			.getVolumeEsgotoNaoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {
		    /*
		     * Caso o volume calculado seja MENOR que o volume fixo,
		     * colocar o volume calculado; caso contrário, colocar o
		     * volume fixo.
		     */
		    if (consumo.getConsumoCobradoMes() > getImovelSelecionado().getSituacaoTipo()
			    .getVolumeEsgotoNaoMedidoHistoricoFaturamento()) {

			consumo
				.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo()
					.getVolumeEsgotoNaoMedidoHistoricoFaturamento());
		    }
		}

	    } else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraSemLeitura() == NAO_MEDIDO) {
		// Seta o tipo de consumo
		consumo.setTipoConsumo(CONSUMO_TIPO_ESTIMADO);

		consumo.setConsumoCobradoMes(getImovelSelecionado().getConsumoMinimoImovel());
	    }
	}
    }

    private void dadosFaturamentoEspecialMedido(Consumo consumo, int ligacaoTipo) {

	ImovelReg8 imReg8 = getImovelSelecionado().getRegistro8(ligacaoTipo);

	int cMedio;

	// Verificamos se o consumo médio veio do
	// registro tipo 8 ou do imóvel
	if (imReg8 != null) {
	    cMedio = imReg8.getConsumoMedio();
	} else {
	    cMedio = getImovelSelecionado().getConsumoMedio();
	}

	int leituraAnterior = obterLeituraAnterior(imReg8);

	if (getImovelSelecionado().getSituacaoTipo() != null) {
	    if (imReg8 != null && imReg8.getLeitura() == Constantes.NULO_INT) {

		if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == NAO_OCORRE) {
		    consumo.setConsumoCobradoMes(cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_IMOV);
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == MINIMO) {
			consumo.setConsumoCobradoMes(getImovelSelecionado().getConsumoMinimoImovel());
			consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == MEDIA) {
		    consumo.setConsumoCobradoMes(cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		 // Daniel - Situaçao especial de faturamento - "FATURAR CONSUMO/VOLUME INFORMADO"
	    } else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == FIXO) {
			consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);

			if (ligacaoTipo == Constantes.LIGACAO_AGUA) {
			    if (getImovelSelecionado().getSituacaoTipo().getConsumoAguaMedidoHistoricoFaturamento() != Constantes.NULO_INT) {
					consumo.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo().getConsumoAguaMedidoHistoricoFaturamento());
			    }
			} else if (getImovelSelecionado().getSituacaoTipo().getVolumeEsgotoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {
			    consumo.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo().getVolumeEsgotoMedidoHistoricoFaturamento());
			}

		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraSemLeitura() == FIXO) {
		    consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);

		    if (ligacaoTipo == Constantes.LIGACAO_AGUA) {

			if (getImovelSelecionado().getSituacaoTipo()
				.getConsumoAguaMedidoHistoricoFaturamento() != Constantes.NULO_INT) {

			    /*
			     * Caso o consumo calculado seja MENOR que o consumo
			     * fixo, colocar o consumo calculado; caso
			     * contrário, colocar o consumo fixo.
			     */
			    if (consumo.getConsumoCobradoMes() > getImovelSelecionado().getSituacaoTipo()
				    .getConsumoAguaMedidoHistoricoFaturamento()) {

				consumo
					.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo()
						.getConsumoAguaMedidoHistoricoFaturamento());
			    }
			}

		    } else if (getImovelSelecionado().getSituacaoTipo()
			    .getVolumeEsgotoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {
			/*
			 * Caso o volume calculado seja MENOR que o volume fixo,
			 * colocar o volume calculado; caso contrário, colocar o
			 * volume fixo.
			 */
			if (consumo.getConsumoCobradoMes() > getImovelSelecionado().getSituacaoTipo()
				.getVolumeEsgotoMedidoHistoricoFaturamento()) {

			    consumo
				    .setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo()
					    .getVolumeEsgotoMedidoHistoricoFaturamento());
			}
		    }

		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraSemLeitura() == NAO_MEDIDO) {
		    // Seta o tipo de consumo
		    consumo.setTipoConsumo(CONSUMO_TIPO_ESTIMADO);

		    consumo.setConsumoCobradoMes(getImovelSelecionado()
			    .getConsumoMinimoImovel());
		}

		// Caso leitura atual informada diferente de zero
	    } else if (imReg8 != null
		    && imReg8.getLeitura() != Constantes.NULO_INT) {
		// Caso a leitura anormalidade leitura com leitura seja igual a
		// leitura anormalidade consumo não ocorre
		if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == NAO_OCORRE) {
		    // O consumo a ser cobrado no mes será o consumo médio do
		    // hidrômetro
		    consumo.setConsumoCobradoMes(cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		}
		// Caso a leitura anormalidade leitura com leitura seja igual a
		// leitura anormalidade consumo mínimo
		else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == MINIMO) {

		    consumo.setConsumoCobradoMes(getImovelSelecionado().getConsumoMinimoImovel());
		    consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);

		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == MEDIA) {
		    // O consumo a ser cobrado no mes será o consumo médio do
		    // hidrômetro
		    consumo.setConsumoCobradoMes(cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    // Caso a leitura anormalidade leitura com leitura seja
		    // igual a
		    // leitura anormalidade consumo medido
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == MAIOR_ENTRE_O_CONSUMO_MEDIO) {
		    // Caso o consumo médio hidrômetro seja maior que o consumo
		    // medido
		    if (cMedio > consumo.getConsumoCobradoMes()) {
			// Consumo a ser cobrado no mês será o já calculado
			consumo.setConsumoCobradoMes(cMedio);
			// Seta o tipo de consumo
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    } else {
			consumo.setConsumoCobradoMes(consumo
				.getConsumoCobradoMes());
		    }

		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == MENOR_ENTRE_O_CONSUMO_MEDIO) {
		    // Caso o consumo médio hidrômetro seja maior que o consumo
		    // medido
		    if (cMedio < consumo.getConsumoCobradoMes()) {
			// Consumo a ser cobrado no mês será o já calculado
			consumo.setConsumoCobradoMes(cMedio);
			// Seta o tipo de consumo
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    } else {
			consumo.setConsumoCobradoMes(consumo
				.getConsumoCobradoMes());
		    }

		}

		/*
		 * Colocado por Raphael Rossiter em 12/08/2008 - Analista:
		 * Rosana Carvalho OBJ: Verificar a situação especial de
		 * faturamento quando o consumo de água e/ou volume de esgoto
		 * está fixo.
		 */
		else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == FIXO) {

		    // Seta o tipo de consumo
		    consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);

		    // Consumo a ser cobrado no mês será o consumo fixado no
		    // histórico da situação especial
		    if (ligacaoTipo == Constantes.LIGACAO_AGUA) {

			if (getImovelSelecionado().getSituacaoTipo()
				.getConsumoAguaMedidoHistoricoFaturamento() != Constantes.NULO_INT) {

			    consumo
				    .setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo()
					    .getConsumoAguaMedidoHistoricoFaturamento());

			}

		    } else if (getImovelSelecionado().getSituacaoTipo()
			    .getVolumeEsgotoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {

			consumo.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo()
				.getVolumeEsgotoMedidoHistoricoFaturamento());

		    }
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == NAO_MEDIDO) {

		    // Seta o tipo de consumo
		    consumo.setTipoConsumo(CONSUMO_TIPO_ESTIMADO);

		    consumo.setConsumoCobradoMes(getImovelSelecionado()
			    .getConsumoMinimoImovel());

		}

		// Caso a leitura anormalidade leitura com leitura
		// seja igual a leitura anormaliade leitura ->
		// <<anterior mais média>>
		if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraComLeitura() == ANTERIOR_MAIS_A_MEDIA) {
		    // Seta a leitura atual de faturamento
		    consumo.setLeituraAtual(leituraAnterior + cMedio);
            imReg8.setLeituraAtualFaturamento(leituraAnterior + cMedio);
		    // <<anterior>>
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraComLeitura() == ANTERIOR) {
		    // Seta a leitura atual de faturamento
		    consumo.setLeituraAtual(leituraAnterior);
            imReg8.setLeituraAtualFaturamento(leituraAnterior);
		    // <<anterior mais consumo>>
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraComLeitura() == ANTERIOR_MAIS_O_CONSUMO) {
		    // Seta a leitura atual de faturamento
		    consumo.setLeituraAtual(leituraAnterior + consumo.getConsumoCobradoMes());
            imReg8.setLeituraAtualFaturamento(leituraAnterior + consumo.getConsumoCobradoMes());
		    // <<leitura informada>>
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraComLeitura() == INFORMADO) {
		    consumo.setLeituraAtual(consumo.getLeituraAtual());
            imReg8.setLeituraAtualFaturamento(consumo.getLeituraAtual());
		}
	    }
	}
    }


    /**
     * Consistir Leituras
     */
    protected void ajusteLeitura( ImovelReg8 reg8,
	    int tipoMedicao, Consumo consumo, ImovelReg11 reg11) {
    	
    }
    
    /**
     * [UC0101] - Consistir Leituras e Calcular Consumos [SF0017] - Ajuste
     * Mensal de Consumo
     */
    protected void ajusteMensalConsumo( ImovelReg8 reg8,
	    int tipoMedicao, Consumo consumo) {

	Date dataLeituraAnteriorFaturamento = null;
	Date dataLeituraLeituraAtualFaturamento = null;
	int leituraAjustada = reg8.getLeitura();
	
	dataLeituraAnteriorFaturamento = reg8.getDataLeituraAnteriorFaturada();
	dataLeituraLeituraAtualFaturamento = reg8.getDataLeitura();

	System.out.println("Leitura Anterior Faturamento: "
			+ dataLeituraAnteriorFaturamento);
	System.out.println("Data do cronograma Mes Anterior: "
			+ getImovelSelecionado().getDataLeituraAnteriorNaoMedido());
	System.out.println("Leitura Atual Faturamento: "
		+ dataLeituraLeituraAtualFaturamento);
	
	int quantidadeDiasConsumoAjustado = 0;

	// Obtém a quantidade de dias de consumo
	int quantidadeDiasConsumo = 0;

	//    Daniel - Imovel antes fixo e agora hidrometrado.
	if (isImovelFixoComHidrometroInstalado(tipoMedicao)){

		quantidadeDiasConsumo = (int) Util.obterModuloDiferencasDatasDias(
				getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), reg8.getDataLeitura());

	}else{
		quantidadeDiasConsumo = (int) Util.obterModuloDiferencasDatasDias(
				getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), dataLeituraLeituraAtualFaturamento);
	}

	/*
	 * Caso a quantidade de dias não seja maior do que zero, retornar
	 * para correspondente no subfluxo que chamou.
	 * 
	 * Data: 05/06/2011 Desenvolvedor:Daniel Zaccarias
	 */

	if (quantidadeDiasConsumo > 0) {

		// Seta a data com a data de referencia da rota/grupo atual
		Date dataLeituraNaoMedidoAtual = Util.adicionarNumeroDiasDeUmaData(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), (long)ImovelReg11.getInstancia().getQtdDiasAjusteConsumo());

		int diasConsumoLido  = (int) Util.obterModuloDiferencasDatasDias(reg8.getDataLeitura(), reg8.getDataInstalacao());
		
//	    Daniel - Imovel com nova instalacao de fornecimento hidrometrado.
		if (isImovelNovaInstalacaoHidrometro(tipoMedicao)){

			// Numero de dias ajustado é diferença entre a data de referencia nao-medido do mes atual e a data da instalaçao do hidrometro  		    
			quantidadeDiasConsumoAjustado = (int) Util.obterModuloDiferencasDatasDias(reg8.getDataInstalacao(), dataLeituraNaoMedidoAtual);
		
//	    Daniel - Imovel antes fixo e agora hidrometrado.
		}else if (isImovelFixoComHidrometroInstalado(tipoMedicao)){

			quantidadeDiasConsumoAjustado = (int) ImovelReg11.getInstancia().getQtdDiasAjusteConsumo();			
    	
	    }// Verifica se a data do ajuste é não nula
		else if (ImovelReg11.getInstancia().getDataAjusteLeitura() != null) {
		// Obtém a quantidade de dias de consumo ajustado
			quantidadeDiasConsumoAjustado = (int) Util
				.obterModuloDiferencasDatasDias(
						getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), ImovelReg11.getInstancia().getDataAjusteLeitura());

	    } else {

			// Obtém a quantidade de dias de consumo ajustado
			quantidadeDiasConsumoAjustado = ImovelReg11.getInstancia().getQtdDiasAjusteConsumo();
	    }

	    reg8.setQtdDiasAjustado(quantidadeDiasConsumoAjustado);

	    // Obtém os dias de ajuste
	    int diasAjuste = quantidadeDiasConsumoAjustado
		    - quantidadeDiasConsumo;

	    if ( (diasAjuste < -3 || diasAjuste > 3) || isImovelHidrometroSubstituido(tipoMedicao) ) {

			// Cálculo para obter a leitura ajustada
			//Daniel - correçao bug: leitura faturada gerado incoerentemente em situacoes em que nao é registrado leitura e sim anormalidade.
		    //Daniel - Nao deve ajustar para anormalidade de consumo - leitura menor que anterior.

	    	double consumoDiario = 0;
	    	//Daniel - Hidrometro substituido.
			if (reg8.getLeitura() != Constantes.NULO_INT && 
				isImovelHidrometroSubstituido( tipoMedicao) &&
				consumo.getTipoConsumo() != CONSUMO_TIPO_MEDIA_HIDR){

				if (diasConsumoLido != 0){
					consumoDiario = Util.arredondar(((double) (reg8.getLeitura() - reg8.getLeituraInstalacaoHidrometro() )/ (double) diasConsumoLido), 3);
				}
				
				//Seta a data com a data de referencia da rota/grupo atual
				Date dataLeituraReferenciaAtual = Util.adicionarNumeroDiasDeUmaData(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), (long)ImovelReg11.getInstancia().getQtdDiasAjusteConsumo());
				
				int diasConsumoLidoAjustado = (int) Util.obterModuloDiferencasDatasDias( dataLeituraReferenciaAtual, reg8.getDataInstalacao() );
					
				leituraAjustada = reg8.getLeituraInstalacaoHidrometro() + (int) Util.arredondar((consumoDiario * diasConsumoLidoAjustado), 0);
			
//		    Daniel - Imovel antes fixo e agora hidrometrado.
			}else if(reg8.getLeitura() != Constantes.NULO_INT &&
					isImovelFixoComHidrometroInstalado(tipoMedicao) &&
					consumo.getTipoConsumo() != CONSUMO_TIPO_MEDIA_HIDR){
			    	
				if (diasConsumoLido != 0){
					consumoDiario = Util.arredondar(((double) (reg8.getLeitura() - this.obterLeituraAnterior(reg8) )/ (double) diasConsumoLido), 3);
				}

				int diasConsumoLidoAjustado = (int) Util.obterModuloDiferencasDatasDias( dataLeituraNaoMedidoAtual, reg8.getDataInstalacao() );
					
				leituraAjustada = this.obterLeituraAnterior(reg8) + (int) Util.arredondar((consumoDiario * diasConsumoLidoAjustado), 0);
			
			}else if (reg8.getLeitura() != Constantes.NULO_INT &&	consumo.getTipoConsumo() != CONSUMO_TIPO_MEDIA_HIDR){

		    	leituraAjustada = reg8.getLeitura()
					+ Util.divideDepoisMultiplica(consumo
							.getConsumoCobradoMes(), quantidadeDiasConsumo,	diasAjuste);
		    
		    }else if ((consumo.getTipoConsumo() == CONSUMO_TIPO_MEDIA_HIDR) ||
		    		(reg8.getLeitura() == Constantes.NULO_INT && consumo.getAnormalidadeLeituraFaturada() > 0)){
		    	
		    	leituraAjustada = consumo.getLeituraAtual();
		    
		    }else{
		    
		    	leituraAjustada = reg8.getLeitura();
		    }
		    
	
//Daniel - Consumo realizado pelo valor medio nao deve fazer reajuste de consumo
//pois o consumo médio já é baseado no numero de dias ajustado (28 - 31 dias).
			
			// Obtém o consumo a ser cobrado mês
			
			if ((leituraAjustada != Constantes.NULO_INT && 
				 reg8.getLeitura() != Constantes.NULO_INT && 
				 consumo.getTipoConsumo() != CONSUMO_TIPO_MEDIA_HIDR) &&
				(isImovelHidrometroSubstituido(reg8.getTipoMedicao()) || 
				 isImovelFixoComHidrometroInstalado(reg8.getTipoMedicao()))){
				
				if (diasConsumoLido > 9) {
				    consumo.setConsumoCobradoMes((int) Util.arredondar((consumoDiario * quantidadeDiasConsumoAjustado), 0));
				    consumo.setTipoConsumo(CONSUMO_TIPO_ESTIMADO);
				}else{
				    consumo.setConsumoCobradoMes(getImovelSelecionado().getconsumoMinimoImovelNaoMedido());
				    consumo.setTipoConsumo(CONSUMO_TIPO_MINIMO_FIX);
				}

			}else if (leituraAjustada != Constantes.NULO_INT && 
					  reg8.getLeitura() != Constantes.NULO_INT && 
					  consumo.getTipoConsumo() != CONSUMO_TIPO_MEDIA_HIDR){
				
				int consumoASerCobradoMes = Util.divideDepoisMultiplica(consumo
						.getConsumoCobradoMes(), quantidadeDiasConsumo,
						quantidadeDiasConsumoAjustado);
				
				// Seta o consumo a ser cobrado mês
				consumo.setConsumoCobradoMes(consumoASerCobradoMes);
			}
			// Adiciona ou subtrai de acordo com os dias de ajuste
			Date dataLeituraAtualFaturamento = Util.adicionarNumeroDiasDeUmaData(reg8.getDataLeitura(),	diasAjuste);
				
			// Seta a data da leitura atual de faturamento
			reg8.setDataLeituraAtualFaturamento(dataLeituraAtualFaturamento);
			
			if (consumo.getTipoConsumo() != CONSUMO_TIPO_MEDIA_HIDR &&
				consumo.getTipoConsumo() != CONSUMO_TIPO_MINIMO_FIX &&
				consumo.getTipoConsumo() != CONSUMO_TIPO_NAO_MEDIDO &&
				consumo.getTipoConsumo() != CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL){
				
					consumo.setTipoConsumo(CONSUMO_TIPO_AJUSTADO);
				
			}
	    }else{
			reg8.setDataLeituraAtualFaturamento(reg8.getDataLeitura());

	    	// Se nao foi necessario reajuste de leitura, mantem a leitura informada.
	    	if(reg8.getLeitura() != Constantes.NULO_INT){
		    	reg8.setLeituraAtualFaturamento(reg8.getLeitura());
			    consumo.setLeituraAtual(reg8.getLeituraAtualFaturamento());
	    	}
	    }
	}
	
	// Obtém 10 elevado ao numeroDigitosHidrometro
	int dezElevadoNumeroDigitos = (int) Util.pow(10,
			this.obterNumeroDigitosHidrometro(tipoMedicao));

	// Caso a leitura ajustada menor que zero
	//Daniel - correçao bug: leitura faturada gerado incoerente em situacoes em que nao é regitrado leitura e sim anormalidade.
	if (leituraAjustada == Constantes.NULO_INT){

		if ( consumo.getLeituraAtual() > (dezElevadoNumeroDigitos - 1) ) {

			reg8.setLeituraAtualFaturamento(consumo.getLeituraAtual() - (dezElevadoNumeroDigitos-1));
			leituraAjustada = leituraAjustada - (dezElevadoNumeroDigitos-1);
				
			// Daniel - caso devido ao valor da leitura ajustada ocasione em virada de hidrometro e nao houver nenhuma outra anormalidade já configurada.
			if(consumo.getAnormalidadeConsumo() == 0 || 
					consumo.getAnormalidadeConsumo() == Consumo.ANORMALIDADE_LEITURA){
				
				consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_VIRADA_HIDROMETRO);
			}
				
		}else{
			reg8.setLeituraAtualFaturamento(consumo.getLeituraAtual());
			
		}
		    
	}else if (leituraAjustada < 0) {
			
		reg8.setLeituraAtualFaturamento(leituraAjustada + dezElevadoNumeroDigitos);

		// Caso a leitura ajustada maior que dez elevado ao número de dígitos menos um
	} else if (leituraAjustada > (dezElevadoNumeroDigitos - 1) ) {

		reg8.setLeituraAtualFaturamento(leituraAjustada - (dezElevadoNumeroDigitos-1));
		leituraAjustada = leituraAjustada - (dezElevadoNumeroDigitos-1);
			
		// Daniel - caso devido ao valor da leitura ajustada ocasione em virada de hidrometro e nao houver nenhuma outra anormalidade já configurada.
		if(consumo.getAnormalidadeConsumo() == 0 || 
				consumo.getAnormalidadeConsumo() == Consumo.ANORMALIDADE_LEITURA){
			
			consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_VIRADA_HIDROMETRO);
		}
			
	    // Caso demais casos
	} else {
	    reg8.setLeituraAtualFaturamento(leituraAjustada);
	}
		
    consumo.setLeituraAtual(reg8.getLeituraAtualFaturamento());

	System.out.println("Data Leitura Atual Faturada!!!!!!!!: "
			+ reg8.getDataLeituraAtualFaturamento());
	System.out.println("leituraAjustada!!!!!!!!: " + leituraAjustada);
	
    }
    
    protected ImovelConta getImovelSelecionado(){
    	return ControladorImoveis.getInstancia().getImovelSelecionado();
    }
    
    public boolean recalcularContasCondominio(int idPrimeiroImovelMicro, int idUltimoImovelMicro, int consumoTipo){
    	return true;
    }

    // android
 // Daniel - Verifica se o imovel era Nao_medido e agora recebeu um hidrometro.    
     public boolean isImovelFixoComHidrometroInstalado(int tipoMedicao){
 		
     	boolean result = false;
     	// Imovel Cortado nao necessita verificar.
     	if (!(getImovelSelecionado().getSituacaoLigAgua()).equals(Constantes.CORTADO)){
 		
     		//Se data de instalaçao do hidrometro IGUAL data de leitura anterior faturada   E   
     		//Data de ligação de fornecimento ANTERIOR à data de instalaçao do Hidrometro E
     		// Nao houve leitura ou anormalidade no mês anterior.
 	   		if ( (Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacaoHidrometro(), 
 	   					getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeituraAnteriorFaturada()) == 0) &&
 	   			 (Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataLigacaoFornecimento(), 
 						getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacaoHidrometro()) < 0) &&
 				 (!houveLeituraOuAnormalidadeLeituraMesAnterior(tipoMedicao)) &&
 				 (getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeituraAnteriorInformada() == null)){
 	    			System.out.println("NAO-MEDIDO para MEDIDO");
 	   		 	result = true;
 	
 	   		}
 		}
     	return result;
     }
     
     // android
  // Daniel - Verifica se o imovel teve hidrometro substituido.    
     public boolean isImovelHidrometroSubstituido(int tipoMedicao){
    
     	boolean result = false;
     	// Imovel Cortado nao necessita verificar.
     	if (!(getImovelSelecionado().getSituacaoLigAgua()).equals(Constantes.CORTADO)){

     		//Houve leitura ou anormalidade no mes anterior (para diferenciar de nova instalação de hidrometro)
 	    	 if (houveLeituraOuAnormalidadeLeituraMesAnterior(tipoMedicao)) {
 	    		
 	    		 // Se data leitura informada for nulo, considera data de leitura faturada
 	    		 if (getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeituraAnteriorInformada() == null){

 	    	    		//Se data de instalaçao do hidrometro APOS OU IGUAL data de leitura anterior faturada   E   
 	    	    		//Data de instalaçao do hidrometro ANTERIOR OU IGUAL que data de leitura atual informada
 	    			 if ( (Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacao(), 
 	 	    			 	getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeituraAnteriorFaturada()) >= 0 ) && 
 	 		    			(Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacao(), 
 	 		    				getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeitura()) <= 0 ) ){
 	    				 
 	    					System.out.println("SUBSTITUICAO DE HIDROMETRO");
 	    					result = true;
 	    			 }

 	    		 }else{
 	    	    		//Se data de instalaçao do hidrometro APOS OU IGUAL data de leitura anterior informada   E   
 	    	    		//Data de instalaçao do hidrometro ANTERIOR OU IGUAL que data de leitura atual informada
 	    			 if ( (Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacao(), 
 	 	    			 	getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeituraAnteriorInformada()) >= 0 ) && 
 	 		    			(Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacao(), 
 	 		    				getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeitura()) <= 0 ) ){
 	    				 
 	 		    			System.out.println("SUBSTITUICAO DE HIDROMETRO");
 	 		    			result = true;
 	    			 }
 	    		 }
 	    	 }
     	}
     	return result;    
     }
     
     // android
//     Daniel - Verifica se é imovel com nova instalacao de fornecimento hidrometrado.
     public boolean isImovelNovaInstalacaoHidrometro(int tipoMedicao){

     	boolean result = false;

     	// Imovel Cortado nao necessita verificar.
     	if (!(getImovelSelecionado().getSituacaoLigAgua()).equals(Constantes.CORTADO)){
     		
     		//Data de ligação de fornecimento IGUAL à data de instalaçao do Hidrometro E
     		//Se data de instalaçao do hidrometro IGUAL data de leitura anterior faturada   E   
     		// Nao houve leitura ou anormalidade no mês anterior.
     		if ( (Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataLigacaoFornecimento(), 
 					getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacaoHidrometro()) == 0) &&
 				 (Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacao(), 
 		    	    getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeituraAnteriorFaturada()) == 0) &&
 				 (!houveLeituraOuAnormalidadeLeituraMesAnterior(tipoMedicao)) &&
 				 (getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeituraAnteriorInformada() == null)){
     			System.out.println("NOVA INSTALACAO!!!!");
     			
     	    	result =  true;
     		}
     	}
     	return result;
     }
    
    public boolean isForaDeFaixa(ImovelReg8 reg8){
		boolean foraDeFaixa = false;

//		Considerando a possibilidade de virada do hidrometro.
		if (reg8.getLeituraEsperadaInicial() > reg8.getLeituraEsperadaFinal()){
	
			if (reg8.getLeituraAtualFaturamento() < reg8.getLeituraEsperadaInicial() 
					&& reg8.getLeituraAtualFaturamento() > reg8.getLeituraEsperadaFinal()) {
			
				foraDeFaixa = true;
			}
		}else {
			if (reg8.getLeituraAtualFaturamento() < reg8.getLeituraEsperadaInicial() || 
					reg8.getLeituraAtualFaturamento() > reg8.getLeituraEsperadaFinal()) {
				
				foraDeFaixa = true;
			
			}
		}

		return foraDeFaixa;
    }
    
    public boolean houveLeituraOuAnormalidadeLeituraMesAnterior(int tipoMedicao){
    	boolean resultado = false;

    	if(getImovelSelecionado().getRegistro8(tipoMedicao).getLeituraAnteriorInformada() != Constantes.NULO_INT ){
    		resultado = true;
    	}
    	
    	if(!resultado){
	    	Vector regsTipo3 = getImovelSelecionado().getRegistros3();
		    if (regsTipo3 != null) {
		    	
		    	// Obtem o registro do mes anterior.
		    	ImovelReg3 reg3 = (ImovelReg3)regsTipo3.elementAt(0);
		    	
		    	if (reg3.getAnormalidadeLeitura() != Constantes.NULO_INT &&
		    			reg3.getAnormalidadeLeitura() != 0){
		    		resultado = true;
		    	}
		    }
		    if(regsTipo3 != null){
			    regsTipo3 = null;	    	
		    }
    	}
    	System.out.println("Houve leitura ou anormalidade: " + resultado);
    	return resultado;
    }
    
    public void chamaAjusteConsumo(int tipoMedicao){

    	// Caso esteja indicado o ajuste mensal do consumo
    	if (ImovelReg11.getInstancia().getIndicadorAjusteConsumo() != Constantes.NULO_INT
    		&& ImovelReg11.getInstancia().getIndicadorAjusteConsumo() == Constantes.SIM) {
    	    // [SF0017] - Ajuste Mensal do Consumo
    	    if (tipoMedicao == LIGACAO_AGUA) {
    		ajusteMensalConsumo(getImovelSelecionado().getRegistro8(tipoMedicao), tipoMedicao, consumoAgua);
    	    }
    	    if (tipoMedicao == LIGACAO_POCO) {
    		ajusteMensalConsumo(getImovelSelecionado().getRegistro8(tipoMedicao), tipoMedicao, consumoEsgoto);
    	    }
    	}
    	
    }
    
    public int obterNumeroDigitosHidrometro(int tipoMedicao){
    	int numeroDigitosHidrometro = 0;
    	
    	// Verifica qual o tipo de medição e obtém o número de dígitos
    	// do hidrômetro
    	if (tipoMedicao == LIGACAO_AGUA) {
    	    numeroDigitosHidrometro = getImovelSelecionado().getRegistro8(
    		    Constantes.LIGACAO_AGUA).getNumDigitosLeitura();

    	} else if (tipoMedicao == LIGACAO_POCO) {
    	    numeroDigitosHidrometro = getImovelSelecionado().getRegistro8(
    		    Constantes.LIGACAO_POCO).getNumDigitosLeitura();
    	}
    	return numeroDigitosHidrometro;

    }

    public String getTipoConsumoToPrint(int tipoConsumo){
    	
    	String resultado = "CONSUMO NAO MEDIDO(m3)";
    	
    	if (tipoConsumo == CONSUMO_TIPO_MEDIA_HIDR){
    		resultado =	"CONSUMO MEDIO(m3)";
    	
    	}else if (tipoConsumo == CONSUMO_TIPO_MINIMO_FIX){
    		resultado =	"CONSUMO MINIMO(m3)";

    	}else if (tipoConsumo == CONSUMO_TIPO_NAO_MEDIDO){
    		resultado =	"CONSUMO NAO MEDIDO(m3)";

    	}else if (tipoConsumo == CONSUMO_TIPO_REAL){
    		resultado =	"CONSUMO REAL(m3)";

    	}else if (tipoConsumo == CONSUMO_TIPO_AJUSTADO){
    		resultado =	"CONSUMO PROPOR. DIAS(m3)";

    	}else if (tipoConsumo == CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL){
    		resultado =	"CONSUMO SIT. ESPECIAL(m3)";

    	}
    	return resultado;
    }
}
