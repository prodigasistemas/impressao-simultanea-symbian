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

package com.ipad.basic.helper;

import java.util.Vector;

import net.sourceforge.floggy.persistence.IDable;
import net.sourceforge.floggy.persistence.Persistable;

/**
 * [UC0970] Efetuar Rateio de Consumo no Dispositivo Movel
 * 
 * Metodo responsavel em efeturar a divisão da diferença entre o consumo
 * coletado no hidrometro macro e a soma dos hidrometros micro
 * 
 * Helper responsavel pela passagem de parametros entre os métodos do UC supra
 * citado.
 * 
 * @date 26/11/2009
 * @author Bruno Barros
 * 
 */
public class EfetuarRateioConsumoDispositivoMovelHelper implements Persistable,	IDable {
    
    private int id;

    private int idUltimoImovelMicro;

    // Agua
//    int quantidadeEconomiasAguaMedidas = 0;
//  int quantidadeEconomiasAguaNaoMedidas = 0;
    int quantidadeEconomiasAguaTotal = 0;
    int consumoLigacaoAguaTotal = 0;

    // Esgoto
//    int quantidadeEconomiasEsgotoMedidas = 0;
//    int quantidadeEconomiasEsgotoNaoMedidas = 0;
    int quantidadeEconomiasEsgotoTotal = 0;
    int consumoLigacaoEsgotoTotal = 0;

    // Geral
    int consumoMinimoTotal = 0;
    int consumoParaRateioAgua = 0;
    double contaParaRateioAgua = 0;

    int consumoParaRateioEsgoto = 0;
    double contaParaRateioEsgoto = 0;
    boolean reterImpressaoContas = false;
    int passos = 0;
    
    private Vector idsAindaFaltamSerCalculador;    
    
    public Vector getIdsAindaFaltamSerCalculador() {
        return idsAindaFaltamSerCalculador;
    }

    public int getConsumoParaRateioEsgoto() {
        return consumoParaRateioEsgoto;
    }

    public void setConsumoParaRateioEsgoto(int consumoParaRateioEsgoto) {
        this.consumoParaRateioEsgoto = consumoParaRateioEsgoto;
    }

    public double getContaParaRateioEsgoto() {
        return contaParaRateioEsgoto;
    }

    public void setContaParaRateioEsgoto(double contaParaRateioEsgoto) {
        this.contaParaRateioEsgoto = contaParaRateioEsgoto;
    }

    public int getPassos() {
	return passos;
    }

    public void setPassos(int passos) {
	this.passos = passos;
    }

    public int getConsumoParaRateioAgua() {
	return consumoParaRateioAgua;
    }

    public void setConsumoParaRateioAgua(int consumoParaRateioAgua) {
	this.consumoParaRateioAgua = consumoParaRateioAgua;
    }

    public double getContaParaRateioAgua() {
    	return contaParaRateioAgua;
	}

	public void setContaParaRateioAgua(double contaParaRateioAgua) {
    	this.contaParaRateioAgua = contaParaRateioAgua;
	}

//    public int getQuantidadeEconomiasAguaMedidas() {
//	return quantidadeEconomiasAguaMedidas;
//    }

//    public int getQuantidadeEconomiasAguaNaoMedidas() {
//	return quantidadeEconomiasAguaNaoMedidas;
//    }

	public int getQuantidadeEconomiasAguaTotal() {
		return quantidadeEconomiasAguaTotal;
    }

    public int getConsumoLigacaoAguaTotal() {
	return consumoLigacaoAguaTotal;
    }

//    public int getQuantidadeEconomiasEsgotoMedidas() {
//	return quantidadeEconomiasEsgotoMedidas;
//    }

//  public int getQuantidadeEconomiasEsgotoNaoMedidas() {
//	return quantidadeEconomiasEsgotoNaoMedidas;
//    }

    public int getQuantidadeEconomiasEsgotoTotal() {
    	return quantidadeEconomiasEsgotoTotal;
    }

    public int getConsumoLigacaoEsgotoTotal() {
	return consumoLigacaoEsgotoTotal;
    }

    public int getConsumoMinimoTotal() {
	return consumoMinimoTotal;
    }

    public int getIdUltimoImovelMicro() {
	return idUltimoImovelMicro;
    }

    public EfetuarRateioConsumoDispositivoMovelHelper( int idImovelMacro, int idUltimoImovelMicro ){ 
	super();
// Daniel - cuidar do caso onde existe imovel cortado no condominio. Este deve ser removido da lista de IDs
	this.idUltimoImovelMicro = idUltimoImovelMicro;
	this.idsAindaFaltamSerCalculador = new Vector( (idUltimoImovelMicro+1) - idImovelMacro );
	
	for ( int i = idImovelMacro; i <= idUltimoImovelMicro; i++ ){
	    this.idsAindaFaltamSerCalculador.addElement( new Integer( i ) );
	}
    }
    
    public EfetuarRateioConsumoDispositivoMovelHelper(){ 
	super();
    }    

    public String getRecordStoreName() {
	return "helper";
    }

    public boolean getReterImpressaoConta(){
    	return this.reterImpressaoContas;
    }

//    public void setQuantidadeEconomiasAguaMedidas(int quantidadeEconomiasAguaMedidas) {
//        this.quantidadeEconomiasAguaMedidas = quantidadeEconomiasAguaMedidas;
//    }

//    public void setQuantidadeEconomiasAguaNaoMedidas(
//    	int quantidadeEconomiasAguaNaoMedidas) {
//        this.quantidadeEconomiasAguaNaoMedidas = quantidadeEconomiasAguaNaoMedidas;
//    }

    public void setQuantidadeEconomiasAguaTotal(int quantidadeEconomiasAguaTotal) {
    	this.quantidadeEconomiasAguaTotal = quantidadeEconomiasAguaTotal;
    }

    public void setConsumoLigacaoAguaTotal(int consumoLigacaoAguaTotal) {
        this.consumoLigacaoAguaTotal = consumoLigacaoAguaTotal;
    }

//    public void setQuantidadeEconomiasEsgotoMedidas(
//    	int quantidadeEconomiasEsgotoMedidas) {
//        this.quantidadeEconomiasEsgotoMedidas = quantidadeEconomiasEsgotoMedidas;
//    }

//    public void setQuantidadeEconomiasEsgotoNaoMedidas(
//    	int quantidadeEconomiasEsgotoNaoMedidas) {
//        this.quantidadeEconomiasEsgotoNaoMedidas = quantidadeEconomiasEsgotoNaoMedidas;
//    }

	public void setQuantidadeEconomiasEsgotoTotal(int quantidadeEconomiasEsgotoTotal) {
		this.quantidadeEconomiasEsgotoTotal = quantidadeEconomiasEsgotoTotal;
	}

    public void setConsumoLigacaoEsgotoTotal(int consumoLigacaoEsgotoTotal) {
        this.consumoLigacaoEsgotoTotal = consumoLigacaoEsgotoTotal;
    }

    public void setConsumoMinimoTotal(int consumoMinimoTotal) {
        this.consumoMinimoTotal = consumoMinimoTotal;
    }

    public void setId(int arg0) {
	this.id = arg0;
    }
    
    public int getId(){
	return id;
    }
    
    public void setReterImpressaoConta(boolean reter){
    	this.reterImpressaoContas = reter;
    }
    
}
