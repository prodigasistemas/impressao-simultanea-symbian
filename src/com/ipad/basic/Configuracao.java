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

import java.util.Vector;
import net.sourceforge.floggy.persistence.Persistable;
import com.ipad.business.ControladorImoveis;
import com.ipad.filters.FiltroImovel;
import com.ipad.io.Repositorio;
import com.ipad.util.Constantes;
import com.ipad.util.Util;

public class Configuracao implements Persistable {

    private String nomeArquivoImoveis;
    private String bluetoothAddress;
    private int idImovelSelecionado;
    private int indiceImovelCondominio;
    private Boolean sucessoCarregamento;
    private short indcRotaMarcacaoAtivada;
    private int sequencialAtualRotaMarcacao = 0;

//    private Vector idsNaoLidos = new Vector();
    private Vector idsNaoHidrometrados = new Vector();
    
//    private Vector idsLidos = new Vector();
  //Daniel - Rota completa
    private Vector idsImoveisConcluidos = new Vector();
    private Vector idsImoveisPendentes = new Vector();
    
    //Daniel - Endereço alternativo
    private Vector idsImoveisEndereçoAlternativo = new Vector();
    
    private Vector matriculasRevisitar = new Vector();

    public int getIndiceImovelCondominio() {
	return indiceImovelCondominio;
    }

    public void setIndiceImovelCondominio(int indiceImovelCondominio) {
	this.indiceImovelCondominio = indiceImovelCondominio;
    }

    private int qtdImoveis;
    private String nomeArquivoRetorno;

    private short tipoPapel = Constantes.PAPEL_REGISPEL;

    public String getNomeArquivoRetorno() {
	return nomeArquivoRetorno;
    }

    public void setNomeArquivoRetorno(String nomeArquRetorno) {
	nomeArquivoRetorno = nomeArquRetorno;
    }

    /**
     * Contador de imóveis visitados.
     */
    private int contadorVisitados = 0;
    private int contadorVisitadosAnormalidade = 0;
    private int contadorVisitadosSemAnormalidade = 0;

    public static Configuracao instancia;

    /**
     * Retorna o contador de imóveis visitados.
     * 
     * @return Vetor de imóveis.
     */
    public int getContadorVisitados() {
	return this.contadorVisitados;
    }

    public void setContadorVisitados(int contadorVisitados) {
	this.contadorVisitados = contadorVisitados;
	// System.out.println("Contador Visitados: "+this.contadorVisitados);
    }

    public void incrementarContadorVisitados() {

	if (ControladorImoveis.getInstancia().getImovelSelecionado()
		.getRegistro8(Constantes.LIGACAO_AGUA) != null) {

	    int anormalidade = ControladorImoveis.getInstancia()
		    .getImovelSelecionado().getRegistro8(
			    Constantes.LIGACAO_AGUA).getAnormalidade();

	    if (anormalidade == 0) {
		this
			.setContadorVisitadosSemAnormalidade(Configuracao
				.getInstancia()
				.getContadorVisitadosSemAnormalidade() + 1);
	    } else {
		this.setContadorVisitadosAnormalidade(Configuracao
			.getInstancia().getContadorVisitadosAnormalidade() + 1);
	    }
	} else if (ControladorImoveis.getInstancia().getImovelSelecionado()
		.getRegistro8(Constantes.LIGACAO_POCO) != null) {

	    int anormalidade = ControladorImoveis.getInstancia()
		    .getImovelSelecionado().getRegistro8(
			    Constantes.LIGACAO_POCO).getAnormalidade();

	    if (anormalidade == 0) {
		this
			.setContadorVisitadosSemAnormalidade(Configuracao
				.getInstancia()
				.getContadorVisitadosSemAnormalidade() + 1);
	    } else {
		this.setContadorVisitadosAnormalidade(Configuracao
			.getInstancia().getContadorVisitadosAnormalidade() + 1);
	    }
	}

	this.setContadorVisitados(Configuracao.getInstancia()
		.getContadorVisitados() + 1);
	Repositorio.salvarObjeto(Configuracao.getInstancia());
	System.out.println("Visitados: " + this.getContadorVisitados());
    }

    public int getQtdImoveis() {
	return qtdImoveis;
    }

    public void setQtdImoveis(int qtdImoveis) {
	this.qtdImoveis = qtdImoveis;
    }

    public int getIdImovelSelecionado() {
	return idImovelSelecionado;
    }

    public void setIdImovelSelecionado(int idImovelSelecionado) {
	this.idImovelSelecionado = idImovelSelecionado;
    }

    public String getBluetoothAddress() {
	return Util.verificarNuloString(bluetoothAddress);
    }

    public void setBluetoothAddress(String bluetoothAddress) {
	this.bluetoothAddress = bluetoothAddress;
    }

    public String getNomeArquivoImoveis() {
	return Util.verificarNuloString(nomeArquivoImoveis);
    }

    public void setNomeArquivoImoveis(String nomeArquivoImoveis) {
	this.nomeArquivoImoveis = nomeArquivoImoveis;
    }

    public int getContadorVisitadosAnormalidade() {
	return contadorVisitadosAnormalidade;
    }

    public void setContadorVisitadosAnormalidade(
	    int contadorVisitadosAnormalidade) {
	this.contadorVisitadosAnormalidade = contadorVisitadosAnormalidade;
    }

    public int getContadorVisitadosSemAnormalidade() {
	return contadorVisitadosSemAnormalidade;
    }

    public void setContadorVisitadosSemAnormalidade(
	    int contadorVisitadosSemAnormalidade) {
	this.contadorVisitadosSemAnormalidade = contadorVisitadosSemAnormalidade;
    }

    public String getRecordStoreName() {
	return "Configuracao";
    }

    public static Configuracao getInstancia() {

    if (Configuracao.instancia == null) {
	    Configuracao.instancia = (Configuracao) Repositorio
		    .pesquisarPrimeiroObjeto(Configuracao.class);

	    if (instancia == null) {
	    	instancia = new Configuracao();
	    }
	}

	return Configuracao.instancia;
    }

    public Boolean getSucessoCarregamento() {
	return sucessoCarregamento;
    }

    public void setSucessoCarregamento(Boolean sucessoCarregamento) {
	this.sucessoCarregamento = sucessoCarregamento;
    }

    public void setTipoPapel(short tipoPapel) {
	this.tipoPapel = tipoPapel;
    }

    public short getTipoPapel() {
	return tipoPapel;
    }

    public void setIndcRotaMarcacaoAtivada(short indcRotaMarcacaoAtivada) {
	this.indcRotaMarcacaoAtivada = indcRotaMarcacaoAtivada;
    }

    public short getIndcRotaMarcacaoAtivada() {
	return indcRotaMarcacaoAtivada;
    }

    public int getSequencialAtualRotaMarcacao() {
	++sequencialAtualRotaMarcacao;	
	Repositorio.salvarObjeto( this );
	return sequencialAtualRotaMarcacao;
    }

//    public Vector getIdsNaoLidos() {
//    	return idsNaoLidos;
//    }

    public Vector getIdsNaoHidrometrados() {
    	return idsNaoHidrometrados;
    }

//    public Vector getIdsLidos() {
//	return idsLidos;
//    }

//Daniel - Rota completa
    public Vector getIdsImoveisConcluidos() {
    	return idsImoveisConcluidos;
    }

  //Daniel - Rota completa
    public Vector getIdsImoveisPendentes() {
    	return idsImoveisPendentes;
    }

    //Daniel - lista de imoveis com Endereço alternativo
    public Vector getIdsImoveisEndereçoEntrega() {
    	return idsImoveisEndereçoAlternativo;
    }
    
    
    public void setMatriculasRevisitar( String idsRevisitar) {
	
	String[] strVector = Util.split( idsRevisitar, ',' );	
	
	if ( this.matriculasRevisitar == null  ){
	    this.matriculasRevisitar = new Vector();
	}
	
	for ( int i = 0; i < strVector.length; i++ ){	    
	    if ( !this.matriculasRevisitar.contains( strVector[i] ) ){
		this.matriculasRevisitar.addElement( strVector[i] );

		
		// Selecionamos o imovel(eis) passado(s)
		Vector resultados = Repositorio.consultarImoveis(
			FiltroImovel.PESQUISAR_POR_MATRICULA, strVector[i] );
		
		ImovelConta imovel = (ImovelConta) resultados.elementAt( 0 );

		
/*		// anormalidade anterior do imóvel
		int anormalidadeRelatorio = 0;
		int leituraRelatorio = 0;
		
		//Informações da quadra
		int quadra = imovel.getQuadra();
		String stringQuadra = Util.adicionarZerosEsquerdaNumero(4, String
			.valueOf(quadra));
		
		//Informações referentes ao relatorio
		if (imovel.getRegistro8(Constantes.LIGACAO_AGUA) != null) {
			leituraRelatorio = imovel.getRegistro8(
				Constantes.LIGACAO_AGUA).getLeituraRelatorio();
			anormalidadeRelatorio = imovel.getRegistro8(
				Constantes.LIGACAO_AGUA)
				.getAnormalidadeRelatorio();

			*//**
			 * Caso o imóvel tenha anormalidade antes da alteração,
			 * será retirado uma unidade dos lidos com anormalidade.
			 *//*
			if (anormalidadeRelatorio != 0) {

			    Util.inserirValoresStringRelatorioConsumoNulo("("
				    + stringQuadra + ")", true, false);

			}

			*//**
			 * Caso o imóvel possua anteriormente leitura, é
			 * retirado uma unidade de lidos com leitura.
			 *//*
			if (anormalidadeRelatorio == 0
				&& leituraRelatorio != Constantes.NULO_INT) {

			    Util.inserirValoresStringRelatorioConsumoNulo("("
				    + stringQuadra + ")", false, true);
			}

		    
		}
		    
		if (imovel.getRegistro8(Constantes.LIGACAO_POCO) != null
			 && imovel.getRegistro8(Constantes.LIGACAO_AGUA) == null) {
			leituraRelatorio = imovel.getRegistro8(
				Constantes.LIGACAO_POCO).getLeituraRelatorio();
			anormalidadeRelatorio = imovel.getRegistro8(
				Constantes.LIGACAO_POCO)
				.getAnormalidadeRelatorio();

			*//**
			 * Caso o imóvel tenha anormalidade antes da alteração,
			 * será retirado uma unidade dos lidos com anormalidade.
			 *//*
			if (anormalidadeRelatorio != 0) {

			    Util.inserirValoresStringRelatorioConsumoNulo("("
				    + stringQuadra + ")", true, false);

			}

			*//**
			 * Caso o imóvel possua anteriormente leitura, é
			 * retirado uma unidade de lidos com leitura.
			 *//*
			if (anormalidadeRelatorio == 0
				&& leituraRelatorio != Constantes.NULO_INT) {

			    Util.inserirValoresStringRelatorioConsumoNulo("("
				    + stringQuadra + ")", false, true);
			}

		    }*/
		
		
		ImovelReg8 hidrometroAgua = imovel.getRegistro8( Constantes.LIGACAO_AGUA );
		ImovelReg8 hidrometroPoco = imovel.getRegistro8( Constantes.LIGACAO_POCO );
		
		if ( hidrometroAgua != null ){
		    hidrometroAgua.setLeitura( Constantes.NULO_INT );
		    hidrometroAgua.setAnormalidade( Constantes.NULO_INT );
		    hidrometroAgua.setDataLeitura( null );
		    hidrometroAgua.setLeituraAnterior( Constantes.NULO_INT );
		    hidrometroAgua.setDataLeituraAtualFaturamento( null );
		    hidrometroAgua.setQtdDiasAjustado( Constantes.NULO_INT );
		    hidrometroAgua.setLeituraAtualFaturamento( Constantes.NULO_INT );
		    //hidrometroAgua.setLeituraRelatorio( Constantes.NULO_INT );
		    //hidrometroAgua.setAnormalidadeRelatorio( Constantes.NULO_INT );
		}
		
		if ( hidrometroPoco != null ){
		    hidrometroPoco.setLeitura( Constantes.NULO_INT );
		    hidrometroPoco.setAnormalidade( Constantes.NULO_INT );
		    hidrometroPoco.setDataLeitura( null );
		    hidrometroPoco.setLeituraAnterior( Constantes.NULO_INT );
		    hidrometroPoco.setDataLeituraAtualFaturamento( null );
		    hidrometroPoco.setQtdDiasAjustado( Constantes.NULO_INT );
		    hidrometroPoco.setLeituraAtualFaturamento( Constantes.NULO_INT );
		    //hidrometroPoco.setLeituraRelatorio( Constantes.NULO_INT );
		    //hidrometroPoco.setAnormalidadeRelatorio( Constantes.NULO_INT );
		}
		
		imovel.setConsumoAgua( null );
		imovel.setConsumoEsgoto( null );
		imovel.setIndcImovelCalculado( Constantes.NAO );
		imovel.setIndcImovelContado( Constantes.NAO );
		imovel.setIndcImovelEnviado( Constantes.NAO );
		imovel.setIndcImovelImpresso( Constantes.NAO );
				
/*		this.idsLidos.removeElement( new Integer( imovel.getId() ) );
		
		if(!this.idsNaoLidos.contains(new Integer( imovel.getId() ))){
		    this.idsNaoLidos.addElement( new Integer( imovel.getId() ) );  
		}*/
		
/*		if(!DadosRelatorio.getInstancia().idsNaoLidosRelatorio.contains(new Integer( imovel.getId() ))){
		    DadosRelatorio.getInstancia().idsNaoLidosRelatorio.addElement( new Integer( imovel.getId() ) );  
		}*/
		
		Repositorio.salvarObjeto( DadosRelatorio.getInstancia());
		Repositorio.salvarObjeto(Configuracao.getInstancia());
		Repositorio.salvarObjeto( imovel );
	    }
	}
    }

    public Vector getMatriculasRevisitar() {
	return matriculasRevisitar;
    }


}
