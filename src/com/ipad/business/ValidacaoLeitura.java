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

import com.ipad.basic.Anormalidade;
import com.ipad.basic.ImovelConta;
import com.ipad.io.Repositorio;
import com.ipad.util.Constantes;
import com.ipad.util.Util;
import com.ipad.view.AbaHidrometroAgua;
import com.ipad.view.AbaHidrometroPoco;

public class ValidacaoLeitura {

    private static ValidacaoLeitura instancia;
    private int contagemValidacoesAgua = 0;
    private int contagemValidacoesPoco = 1;

    public static ValidacaoLeitura getInstancia() {

	if (ValidacaoLeitura.instancia == null) {
	    ValidacaoLeitura.instancia = new ValidacaoLeitura();
	}

	return ValidacaoLeitura.instancia;
    }
    
    /**
     * 
     * Metodo usuado para validar leituras de agua e de poco,
     * junto com suas anormalidades
     * 
     * @author Bruno Barros
     * @date 28/02/2010
     * 
     * @param leituraDigitada - Leitura digitada na aba
     * @param anormalidadeDigitada - Anormalidade digitada na aba
     * @param tipoValidacao - Validação de Agua ? Ou de poco ?
     * 
     * @return caso a leitura seja inválida, retorna true;
     */
    public boolean validarLeituraAnormalidade( String leituraDigitada, Anormalidade anormalidade, int tipoValidacao  ){
	// Variavel de controle
	boolean error = false;
	boolean leituraInvalida = false;
	String strTipoValidacao = "";
	int anormalidadeValor = Constantes.NULO_INT;
	
	if ( tipoValidacao == Constantes.LIGACAO_AGUA ){
	    strTipoValidacao = "água"; 
	} else {
	    strTipoValidacao = "poço";
	}
	
	// Incializamos as leituras e as anormalidades
	int intLeitura = Constantes.NULO_INT;

	// Verificamos se foram informadas leituras e/ou anormalidade
	if ( leituraDigitada != null && !leituraDigitada.equals( "" ) ) {
	    intLeitura = Integer.parseInt( leituraDigitada );
	} else {
		intLeitura = Constantes.NULO_INT;
	}
	
	

	
	if(anormalidade != null){
	    anormalidadeValor = anormalidade.getCodigo();
	}
	
	if ( getImovelSelecionado().getRegistro8(tipoValidacao) != null ){
	    // Validamos a leitura do hidrometro

		
	    //if ( intLeitura == 0 ) {
	    if (leituraDigitada == null || leituraDigitada.equalsIgnoreCase("")) {
		if (anormalidade == null || anormalidadeValor == 0) {
			Util.MensagemConfirmacao("Leitura e anormalidade de " + strTipoValidacao + " em branco! ou  código de anormalidade inválido!");
		    leituraInvalida = true;	
		    error = true;
		}
		
		if (!error) {
		    if ((anormalidade != null && anormalidadeValor != 0) && anormalidade.getIndicadorLeitura() == Anormalidade.TER_LEITURA) {
		    	Util.mensagemAviso("Anormalidade", "Informe a Leitura da Anormalidade de " + strTipoValidacao + "!");
			leituraInvalida = true;
			error = true;
		    }
		}
	    }	    
	    
	    // Validar anormalidade caso leitura diferente de "0"
	    if (!error) {
		//if (intLeitura != 0) {
	     if (!leituraDigitada.equals("")) {
		    if ( (anormalidade != null && anormalidadeValor != 0) && anormalidade.getIndicadorLeitura() == Anormalidade.NAO_TER_LEITURA) {
		    	Util.mostrarErro("Essa anormalidade de " + strTipoValidacao + " não pode ter leitura!");
			leituraInvalida = true;
			error = true;
		    }
		}
	    }
	    
	    if ( !error ){
			int numeroDigitosHidrometro = 0;
			
// Daniel - Obtém o número de dígitos do hidrometro.
	 		if (getImovelSelecionado().getRegistro8(Constantes.LIGACAO_AGUA) != null){
	 			numeroDigitosHidrometro = getImovelSelecionado().getRegistro8(
	 					Constantes.LIGACAO_AGUA).getNumDigitosLeitura();

			}else if(getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO) != null){
				numeroDigitosHidrometro = getImovelSelecionado().getRegistro8(
						Constantes.LIGACAO_POCO).getNumDigitosLeitura();		
			}
			int leituraMaximaPermitida = ((int) Util.pow(10,numeroDigitosHidrometro)) - 1;

		    if (intLeitura > leituraMaximaPermitida){
		    	Util.mostrarErro("Leitura informada maior que a leitura máxima permitida pelo hidrometro!");
		    	leituraInvalida = true;
		    	error = true;
		    }
	    }

	    if ( !error ){

//	Daniel - verifica se valor esta fora de faixa    	
			if ( 
				( intLeitura != Constantes.NULO_INT && 
						( (getImovelSelecionado().getRegistro8(tipoValidacao).getLeituraEsperadaInicial() < getImovelSelecionado().getRegistro8(tipoValidacao).getLeituraEsperadaFinal()) &&
								(intLeitura < getImovelSelecionado().getRegistro8(tipoValidacao).getLeituraEsperadaInicial() || 
								 intLeitura > getImovelSelecionado().getRegistro8(tipoValidacao).getLeituraEsperadaFinal()) ) 
								 																								)
	
					||
					
			   ( intLeitura != Constantes.NULO_INT && 
					   ( (getImovelSelecionado().getRegistro8(tipoValidacao).getLeituraEsperadaInicial() > getImovelSelecionado().getRegistro8(tipoValidacao).getLeituraEsperadaFinal()) &&
								(intLeitura < getImovelSelecionado().getRegistro8(tipoValidacao).getLeituraEsperadaInicial() && 
								 intLeitura > getImovelSelecionado().getRegistro8(tipoValidacao).getLeituraEsperadaFinal()) ) 
								 																								)	
																																	) {
			    
				
			    int contagemValidacoes = 0;
			    
			    if ( tipoValidacao == Constantes.LIGACAO_AGUA ){		    
					this.contagemValidacoesAgua = getImovelSelecionado().getContagemValidacaoAgua();
					contagemValidacoes = this.contagemValidacoesAgua;
			    } else {
					this.contagemValidacoesPoco = getImovelSelecionado().getContagemValidacaoPoco();
					contagemValidacoes = this.contagemValidacoesPoco;
			    }
	
			    if ( !leituraInvalida ) {
				
					int leituraDigitadaAnterior;
					
					if ( tipoValidacao == Constantes.LIGACAO_AGUA ){
					    leituraDigitadaAnterior = AbaHidrometroAgua.getInstancia().getLeituraDigitada();
					} else {
					    leituraDigitadaAnterior = getImovelSelecionado().getRegistro8(tipoValidacao).getLeitura();
					}
					
					if ( intLeitura == leituraDigitadaAnterior  && contagemValidacoes != 0) {			    
					    if ( tipoValidacao == Constantes.LIGACAO_AGUA ){
					    	this.contagemValidacoesAgua = 0;
					    } else {
					    	this.contagemValidacoesPoco = 0;
					    }			    
					    
					    getImovelSelecionado().getRegistro8(tipoValidacao).setLeitura( intLeitura );
					    Repositorio.salvarObjeto( getImovelSelecionado() );			    
					} else {
					    if ( tipoValidacao == Constantes.LIGACAO_AGUA ){		    
					    	getImovelSelecionado().setContagemValidacaoAgua( getImovelSelecionado().getContagemValidacaoAgua() + 1);

					    	AbaHidrometroAgua.getInstancia().setLeituraDigitada( intLeitura );
							AbaHidrometroAgua.getInstancia().setLeituraCampo("");
							AbaHidrometroAgua.getInstancia().getLeituraCampo().requestFocus();
					    } else {

					    	getImovelSelecionado().setContagemValidacaoPoco(getImovelSelecionado().getContagemValidacaoPoco() + 1);
					    	getImovelSelecionado().getRegistro8(tipoValidacao).setLeitura( intLeitura );
							AbaHidrometroPoco.getInstancia().setLeituraCampo("");
							AbaHidrometroPoco.getInstancia().getLeituraCampo().requestFocus();
					    }			    
					    
					    Repositorio.salvarObjeto( getImovelSelecionado() );			    
		
					    Util.mostrarErro("Leitura de " + strTipoValidacao
								+ " informada fora de faixa ou \"zero\"");
						leituraInvalida = true;
						error = true;
					}
			    }
			}
	
			// Valida se a leitura e negativa
			//if ( intLeitura < 0 ) {
		    	if (intLeitura < 0 && intLeitura != Constantes.NULO_INT) {
		    		Util.mostrarErro("Leitura de " + strTipoValidacao + " negativa!");
				    leituraInvalida = true;
				    error = true;
			}		
	    }
	}
	
	// Limpamos a memoria
//	ligacao = null;
//	imovelSelecionado = null;
//	ahp = null;
//	aha = null;
	anormalidade = null;
	strTipoValidacao = null;
	leituraDigitada = null;	
	
//	System.gc();	
	
	return leituraInvalida;
    }
    
    private ImovelConta getImovelSelecionado(){
    	return ControladorImoveis.getInstancia().getImovelSelecionado();
    }
}
