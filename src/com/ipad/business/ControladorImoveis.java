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

import java.util.Date;
import java.util.Vector;

import com.ipad.background.BackGroundTaskEnviarImovelOnline;
import com.ipad.basic.Configuracao;
import com.ipad.basic.Consumo;
import com.ipad.basic.DadosFaturamento;
import com.ipad.basic.DadosFaturamentoFaixa;
import com.ipad.basic.DadosRelatorio;
import com.ipad.basic.DadosTarifa;
import com.ipad.basic.ImovelConta;
import com.ipad.basic.ImovelReg10;
import com.ipad.basic.ImovelReg11;
import com.ipad.basic.ImovelReg12;
import com.ipad.basic.ImovelReg13;
import com.ipad.basic.ImovelReg14;
import com.ipad.basic.ImovelReg2;
import com.ipad.basic.ImovelReg3;
import com.ipad.basic.ImovelReg6;
import com.ipad.basic.ImovelReg7;
import com.ipad.basic.ImovelReg8;
import com.ipad.basic.ImovelReg9;
import com.ipad.basic.RegistroBasico;
import com.ipad.basic.Tarifa;
import com.ipad.component.Progress;
import com.ipad.facade.Fachada;
import com.ipad.filters.FiltroImovel;
import com.ipad.io.Repositorio;
import com.ipad.io.binarytree.BinaryTreeItem;
import com.ipad.util.Constantes;
import com.ipad.util.Util;
import com.ipad.view.Abas;

/**
 * A classe ControladorImoveis é responsável por toda a lógica referente aos
 * imóveis, como navegar na lista de imóveis e validar valores digitados pelo
 * leiturista. Dentro do padrão MVC esta classe representa um Controller.
 */
public class ControladorImoveis {

    /**
     * Quantidade mínima de imóveis com atualizações enviados ao servidor a cada
     * tentativa.
     */
    private static final byte MIN_IMOVEIS_POR_ENVIO = 10;

    /**
     * Constante que indica a direção PRÓXIMO da navegação no vetor de imóveis.
     */
    private static final byte NEXT = 0;

    /**
     * Constante que indica a direção ANTERIOR da navegação no vetor de imóveis.
     */
    private static final byte PREV = 1;

    private static ImovelConta imovelSelecionado = new ImovelConta();

    private static int qtdImoveis = 0;
    private int qtdRegistros = 0;
    private static int linhasLidas = 0;

    /**
     * Índice do imóvel selecionado.
     */
    private int idImovelSelecionado = 1;

    private int indiceImovelCondomio = 1;

    public static ControladorImoveis instancia;

    /**
     * Tipo de Medicao do Imovel selecionado.
     */
    private int tipoMedicaoSelecionado;

    // Guardamos sempre o imovel que é o hidrometro macro
    // para que quando terminarmos de contar a quantidade
    // de imoveis que compõem a medição individualizada
    private ImovelConta imovelHidrometroMacro;
    // Contador para sabermos a quantidade de imoveis
    private int qtdImoveisMedicaoIndividualizada;

    /**
     * Contador de imóveis visitados.
     */
    private int contadorVisitados = 0;

    /**
     * Contador usado para controlar as tentativas de envio das atualizações
     * pendentes.
     */
    private int contadorEnvio;

    /**
     * Indica se a rota está invertida.
     */
    private boolean roteiroEstaInvertido;

    /**
     * Cria uma instância do objeto ControladorImoveis.
     */
    public ControladorImoveis() {
	this.roteiroEstaInvertido = false;

	// Colocamos sempre o ultimo imovel que parou
	if (Configuracao.getInstancia().getIdImovelSelecionado() != 0) {
	    idImovelSelecionado = Configuracao.getInstancia()
		    .getIdImovelSelecionado();

	    ImovelReg12.getInstancia().carregarRegistros12(
		    Repositorio.listarObjetos(ImovelReg12.class));
	    ImovelReg13.getInstancia().carregarRegistros13(
		    Repositorio.listarObjetos(ImovelReg13.class));

	    if (Configuracao.getInstancia().getIndiceImovelCondominio() != 0) {
		indiceImovelCondomio = Configuracao.getInstancia()
			.getIndiceImovelCondominio();
	    }
	}
    }

    /**
     * Retorna o imóvel selecionado.
     * 
     * @return imóvel selecionado.
     */
    public ImovelConta getImovelSelecionado() {
	return imovelSelecionado;
    }

    public void setImovelSelecionado(ImovelConta imovel) {
	imovelSelecionado = imovel;
	idImovelSelecionado = imovel.getId();

	if (imovelSelecionado.getIndcCondominio() == Constantes.SIM) {
	    this.indiceImovelCondomio = 1;
	} else if (imovelSelecionado.getIdImovelCondominio() != Constantes.NULO_INT) {
	    indiceImovelCondomio = imovelSelecionado.getId()
		    - (imovelSelecionado.getIdImovelCondominio() - 1);
	}

    }

    public static ControladorImoveis getInstancia() {

	if (ControladorImoveis.instancia == null) {
	    ControladorImoveis.instancia = new ControladorImoveis();
	}

	return ControladorImoveis.instancia;
    }

    /**
     * Retorna se é o momento para realizar uma tentativa de envio das
     * atualizações pendentes.
     * 
     * @return true se for para enviar, false caso contrário.
     */
    public boolean deveTentarEnviar() {
	boolean resposta = this.contadorEnvio == MIN_IMOVEIS_POR_ENVIO;
	if (resposta) {
	    this.contadorEnvio = 0;
	}

	return resposta;
    }

    /**
     * Retorna o contador de imóveis visitados.
     * 
     * @return Vetor de imóveis.
     */
    public int getContadorVisitados() {
	return this.contadorVisitados;
    }

    public void setContadorVisitados(int contadorVisitados) {
	this.contadorVisitados = contadorVisitados + 1;

    }

    /*
     * public ImovelConta getImovel() { //return imovel; } public void
     * setImovel(ImovelConta imovel) { //this.imovel = imovel; }
     */

    /**
     * Inverte o roteiro.
     */
    public void inverterRoteiro() {
	this.roteiroEstaInvertido = !this.roteiroEstaInvertido;
    }

    /**
     * Restorna se o roteiro está ou não invertido.
     * 
     * @return true se tiver e false caso contrário.
     */
    public boolean roteiroEstaInvertido() {
	return this.roteiroEstaInvertido;
    }

    /**
     * Retorna o índice do imóvel selecionado.
     * 
     * @return índice do imóvel selecionado.
     */
    public int getIndiceAtual() {
	return this.idImovelSelecionado;
    }

    /**
     * Retorna o índice do imóvel condominio.
     * 
     * @return índice do imóvel condominio.
     */
    public int getIndiceAtualImovelCondominio() {
	return this.indiceImovelCondomio;
    }

    /**
     * Segue para o PRÓXIMO imóvel.
     * 
     * @return O identificador do erro de validação.
     */
    public int proximo() {
	
	int resposta = Constantes.ID_SEM_ERRO;
	if (this.roteiroEstaInvertido) {
	    resposta = this.andar(PREV);
	} else {
	    resposta = this.andar(NEXT);
	}

	return resposta;
    }

    /**
     * Volta para o imóvel anterior.
     * 
     * @return O identificador do erro de validação.
     */
    public int anterior() {

	int resposta = Constantes.ID_SEM_ERRO;

	if (this.roteiroEstaInvertido) {
	    resposta = this.andar(NEXT);
	} else {
	    resposta = this.andar(PREV);
	}

	return resposta;
    }

    /**
     * Segue para o PRÓXIMO imóvel ou volta para o anterior, salvando os valores
     * alterados caso seja necessário.
     * 
     * @param direction
     *            A direção que se deve mover.
     * @param leitura
     *            Leitura digitada pelo usuário.
     * @param anormalidade
     *            Anormalidade digitada pelo usuário.
     * @return O identificador do erro de validação.
     */
    private int andar(int direction) {

	// Antes de enviarmos, verificamos se o imovel é imovel
	// condomínio, pois o mesmo só deve ser enviado no final
	if ( !ControladorImoveis.getInstancia().getImovelSelecionado().isImovelCondominio() ) {
	    
	    if ( ImovelReg11.getInstancia().getIndicadorTransmissaoOffline() == Constantes.NAO || 
		 ImovelReg11.getInstancia().getIndicadorTransmissaoOffline() == Constantes.NULO_INT ) {

// Daniel - Somente envia se não foi transmitido anteriormente
	    	if(ControladorImoveis.getInstancia().getImovelSelecionado().getIndcImovelEnviado() == Constantes.NAO){

	    		BackGroundTaskEnviarImovelOnline bk = 
				    new BackGroundTaskEnviarImovelOnline(ControladorImoveis.getInstancia().getImovelSelecionado());
				bk.start();	    		
	    	}
	    }
	}
	
	// Marcamos esse imovel medido na rota de
	// marcação quando o mesmo calculado. Os não
	// Medidos serão marcador apenas quando forem impressos
	if ( Configuracao.getInstancia().getIndcRotaMarcacaoAtivada() == Constantes.SIM &&
	     ( ControladorImoveis.getInstancia().getImovelSelecionado().getIndcImovelCalculado() == Constantes.SIM &&
	    		 ControladorImoveis.getInstancia().getImovelSelecionado().getSequencialRotaMarcacao() == Constantes.NULO_INT ) ){	    
		ControladorImoveis.getInstancia().getImovelSelecionado().setSequencialRotaMarcacao( Configuracao.getInstancia().getSequencialAtualRotaMarcacao() );
	    Repositorio.salvarObjeto( ControladorImoveis.getInstancia().getImovelSelecionado() );	    
	}
	
//	imovel = null;	


	switch (direction) {
	case NEXT:
	    // avança para o próximo imóvel
	    this.idImovelSelecionado++;

	    if (this.idImovelSelecionado > Configuracao.getInstancia().getQtdImoveis()) {
		this.idImovelSelecionado = 1;
	    }

	    break;
	case PREV:
	    // retorna para o imóvel anterior
	    if (this.idImovelSelecionado == 1) {
		this.idImovelSelecionado = Configuracao.getInstancia().getQtdImoveis();
	    } else {
		this.idImovelSelecionado--;
	    }

	    break;
	}

	Configuracao.getInstancia().setIdImovelSelecionado(idImovelSelecionado);

	imovelSelecionado = new ImovelConta();

	Repositorio.carregarObjeto(imovelSelecionado, Configuracao.getInstancia()
		.getIdImovelSelecionado());

	if (imovelSelecionado.getIndcCondominio() == Constantes.SIM) {
	    this.indiceImovelCondomio = 1;
	} else if (imovelSelecionado.getIdImovelCondominio() != Constantes.NULO_INT) {
	    indiceImovelCondomio = imovelSelecionado.getId()
		    - (imovelSelecionado.getIdImovelCondominio() - 1);
	}

	Configuracao.getInstancia().setIndiceImovelCondominio(indiceImovelCondomio);
	Repositorio.salvarObjeto(Configuracao.getInstancia());

	// Verificamos se estamos trabalhando com imoveis condomínio
	if (imovelSelecionado.getIndcCondominio() == Constantes.SIM) {
	    indiceImovelCondomio = 1;
	}

	return Constantes.ID_SEM_ERRO;
    }

    public void setIndiceImovelCondomio(int indiceImovelCondomio) {
	this.indiceImovelCondomio = indiceImovelCondomio;
    }

    /**
     * Carrega o vetor de imóveis e salva cada registro no RMS caso necessário.
     * 
     * @param input
     *            Vetor onde cada elemento uma linha do arquivo de entrada.
     */
    public ImovelConta carregarDadosParaRecordStore(Vector input,
	    Progress progresso, ImovelConta ultimoImovelProcessado) {
	String record = null;
	ImovelConta imovel = ultimoImovelProcessado;
	DadosRelatorio dadosRelatorio = DadosRelatorio.getInstancia();

	for (int i = 0; i < input.size(); i++) {
	    record = (String) input.elementAt(i);
	    record = Util.reencode( record );

	    linhasLidas++;

	    if (i == 0 && qtdImoveis == 0) {
		qtdRegistros = Integer.parseInt(record);
		continue;
	    }

	    // Informamos quanto ainda falta
	    double d = (double) (linhasLidas) / qtdRegistros;
	    byte percentual = (byte) ((d) * 100);

	    progresso.setProgress(percentual);
	    progresso.repaint();

	    int tipoRegistro = Integer.parseInt(record.substring(0, 2));

	    if (tipoRegistro == Constantes.REGISTRO_TIPO_1) {

		if (imovel != null) {
		    if (imovelSelecionado == null) {
			imovelSelecionado = imovel;
		    }

		    
		    Repositorio.salvarObjeto(imovel);

//Daniel  				
		    System.out.println(imovel.getId() +" -imovel.getNumeroConta(): " + imovel.getNumeroConta());
		    System.out.println(imovel.getId() + " - imovel.getMatricula(): " + imovel.getMatricula());

			//Daniel - Cuidando dos casos de imoveis sem conta - informativos.
//		     Verifica se o imovel ainda nao foi adicionado aos vetores de configuração.
		    if ( imovel.getId() != 0 &&
		    	!Configuracao.getInstancia().getIdsImoveisConcluidos().contains(new Integer(imovel.getId())) &&
		    	!Configuracao.getInstancia().getIdsImoveisPendentes().contains(new Integer(imovel.getId())) ){
			    
		    	if( (!imovel.isImovelInformativo()) || 
		    			(imovel.getIndcCondominio() == Constantes.SIM) ){
			    	
					// Daniel - para imoveis com endereço de entrega alternativo			    
					if(imovel.getEnderecoEntrega().trim().length() != 0){					
					    Configuracao.getInstancia().getIdsImoveisEndereçoEntrega()
					    .addElement(new Integer(imovel.getId()));
					}
	
					Configuracao.getInstancia().getIdsImoveisPendentes()
				    .addElement(new Integer(imovel.getId()));
				    
				    DadosRelatorio.getInstancia().idsNaoLidosRelatorio.
			    	    addElement(new Integer(imovel.getId()));
				}else{
	
					Configuracao.getInstancia().getIdsImoveisConcluidos()
				    .addElement(new Integer(imovel.getId()));
	
				    DadosRelatorio.getInstancia().idsLidosRelatorio.
			    	    addElement(new Integer(imovel.getId()));
	
				}
		    }
		    Repositorio.indiceMatriculaImovel
			    .insertId(new BinaryTreeItem(imovel.getMatricula(),
			    		imovel.getId()));
		    Repositorio.indiceQuadraImovel.insertId(new BinaryTreeItem(
				    imovel.getQuadra(), imovel.getId()));
		    Repositorio.indiceSequencialRotaImovel
			    .insert(new BinaryTreeItem(imovel
					    .getSequencialRota(), imovel.getId()));

			String hidrometro = imovel.getNumeroHidrometro(Constantes.LIGACAO_AGUA);
			if (hidrometro == null){
				hidrometro = imovel.getNumeroHidrometro(Constantes.LIGACAO_POCO);
			}
			if (hidrometro != null){
				Repositorio.indiceNumeroHidrometroImovel.insert(new BinaryTreeItem(
						Util.convertendoLetraParaNumeros(hidrometro),imovel.getId()));
			}else {
		    	if(	!imovel.isImovelInformativo() && 
		    		imovel.getId() != 0 &&
		    		!Configuracao.getInstancia().getIdsNaoHidrometrados().contains(new Integer(imovel.getId())) ){
					
		    		Configuracao.getInstancia().getIdsNaoHidrometrados().addElement(new Integer(imovel.getId()));
		    	}		
			}
		}

		imovel = new ImovelConta(record);

		// Caso o imovel seja o hidrometro macro, guardamos
		if (imovel.getIndcCondominio() == Constantes.SIM) {
		    if (imovelHidrometroMacro != null) {
		    	imovelHidrometroMacro.setQuantidadeImoveisCondominio(qtdImoveisMedicaoIndividualizada);
		    	qtdImoveisMedicaoIndividualizada = 0;
		    	Repositorio.salvarObjeto(imovelHidrometroMacro);
		    	imovelHidrometroMacro = null;
		    }

		    // Contamos o imovel como medicao individualizada
		    qtdImoveisMedicaoIndividualizada++;
		    imovelHidrometroMacro = imovel;

		    // Verificamos se mudamos de condominio
		} else if (imovelHidrometroMacro != null) {

// Daniel - verificar se imovel pertence ao condominio e se possui conta (nao deve ser imovel informativo!)
//			if (imovelHidrometroMacro.getMatricula() == imovel.getMatriculaCondominio() &&
//					imovel.getNumeroConta() != Constantes.NULO_INT) {
			if (imovelHidrometroMacro.getMatricula() == imovel.getMatriculaCondominio()) {
				
				imovel.setIdImovelCondominio(imovelHidrometroMacro.getId());
				imovelHidrometroMacro.setAnoMesConta(imovel.getAnoMesConta());
				qtdImoveisMedicaoIndividualizada++;
				Repositorio.salvarObjeto(imovel);
		    }
		}
		qtdImoveis++;
		
		
		int quadra = imovel.getQuadra();
		boolean contem = dadosRelatorio.quadras.contains(new Integer(quadra));
		
		if(!contem){
			
		      
		      dadosRelatorio.quadras.addElement(new Integer(quadra));
		      dadosRelatorio.valoresRelatorio = dadosRelatorio.valoresRelatorio+ "(" + 
		      Util.adicionarZerosEsquerdaNumero(4, String.valueOf(quadra)) + ")" + 
		      Util.adicionarZerosEsquerdaNumero(4, String.valueOf(1))+ 
		      Util.adicionarZerosEsquerdaNumero(4, String.valueOf(0))+ 
		      Util.adicionarZerosEsquerdaNumero(4, String.valueOf(1));
			
		    }else{
		      int indice = dadosRelatorio.valoresRelatorio.indexOf("(" + Util.adicionarZerosEsquerdaNumero(4, 
			      String.valueOf(quadra)) + ")");
		      String quadraAlteracao = dadosRelatorio.valoresRelatorio.substring(indice, indice + 18);
		      Util.inserirValoresStringRelatorioCarregamento("(" + Util.adicionarZerosEsquerdaNumero(4, 
			      String.valueOf(quadra)) + ")",quadraAlteracao, false, false);
			
		    }
		
		   
	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_2) {

		imovel.adicionaRegistro2(new ImovelReg2(record));

	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_3) {

		imovel.adicionaRegistro3(new ImovelReg3(record));

	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_4) {
//daniel
//	    	if(imovel.isRegistro3Empty()){
//	    		imovel.
//	    		String registro3 = "0300" + Integer.toString(imovel.getMatricula()) + "00000000000000000";
// falta buscar a datareferencia do tipo 01	    		
//	    		imovel.adicionaRegistro3(new ImovelReg3(registro3));	
//}
		imovel.adicionaRegistro4(new RegistroBasico(record));

	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_5) {

		imovel.adicionaRegistro5(new RegistroBasico(record));

	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_6) {

		imovel.adicionaRegistro6(new ImovelReg6(record));

	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_7) {

		imovel.adicionaRegistro7(new ImovelReg7(record));

	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_8) {

		ImovelReg8 reg8 = new ImovelReg8(record);
		imovel.adicionaRegistro8(reg8);

		Repositorio.indiceNumeroHidrometroImovel
			.insert(new BinaryTreeItem(
				reg8
					.getNumeroHidrometroConvertendoLetraParaNumeros(),
				imovel.getId()));
		if(Configuracao.getInstancia().getIdsNaoHidrometrados().contains(new Integer(imovel.getId()))){
			Configuracao.getInstancia().getIdsNaoHidrometrados().removeElement( new Integer(imovel.getId()));
		}
		
	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_9) {
		// Dados da Tarifa
		imovel.adicionaRegistro9(new ImovelReg9(record));

	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_10) {
		imovel.adicionaRegistro10(new ImovelReg10(record));

	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_11) {
		ImovelReg11.getInstancia().adicionaRegistro11(
			new ImovelReg11(record));

		if (imovelHidrometroMacro != null) {
		    imovelHidrometroMacro
			    .setQuantidadeImoveisCondominio(qtdImoveisMedicaoIndividualizada);
		    qtdImoveisMedicaoIndividualizada = 0;
		    imovelHidrometroMacro.setAnoMesConta(imovel
			    .getAnoMesConta());
		    Repositorio.salvarObjeto(imovelHidrometroMacro);
		}
		
	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_12) {
		ImovelReg12.getInstancia().adicionaRegistro12(
			new ImovelReg12(record));
	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_13) {
		ImovelReg13.getInstancia().adicionaRegistro13(
			new ImovelReg13(record));
	    } else if (tipoRegistro == Constantes.REGISTRO_TIPO_14) {
		ImovelReg14.getInstancia().adicionaRegistro14(
			new ImovelReg14(record));

	    }

	    
	    // Salvamos o ultimo imovel e
	    // as configuracoes
	    // Foi feito dessa forma para economizar memoria
	    if (i == input.size() - 1) {

		if (imovelSelecionado == null) {
		    imovelSelecionado = imovel;
		}

		boolean novoImovel = imovel.getId() == 0;

		int id = Repositorio.salvarObjeto(imovel);

		if (novoImovel) {

			//Daniel - Cuidando dos casos de imoveis sem conta - informativos.
	    	if( !imovel.isImovelInformativo() || 
	    			imovel.getIndcCondominio() == Constantes.SIM ){

		    	
				// Daniel - para imoveis com endereço de entrega alternativo			    
				if(imovel.getEnderecoEntrega().trim().length() != 0){					
				    Configuracao.getInstancia().getIdsImoveisEndereçoEntrega()
				    .addElement(new Integer(imovel.getId()));
				}

				Configuracao.getInstancia().getIdsImoveisPendentes()
			    .addElement(new Integer(imovel.getId()));
			    
			    DadosRelatorio.getInstancia().idsNaoLidosRelatorio.
		    	    addElement(new Integer(imovel.getId()));
			}else{

				Configuracao.getInstancia().getIdsImoveisConcluidos()
			    .addElement(new Integer(imovel.getId()));

			    DadosRelatorio.getInstancia().idsLidosRelatorio.
		    	    addElement(new Integer(imovel.getId()));

			}

			Repositorio.indiceMatriculaImovel
			    .insertId(new BinaryTreeItem(imovel.getMatricula(),
				    id));

		    Repositorio.indiceQuadraImovel.insertId(new BinaryTreeItem(
			    imovel.getQuadra(), id));

		    Repositorio.indiceSequencialRotaImovel
			    .insertId(new BinaryTreeItem(imovel
				    .getSequencialRota(), id));

			String hidrometro = imovel.getNumeroHidrometro(Constantes.LIGACAO_AGUA);
			if (hidrometro == null){
				hidrometro = imovel.getNumeroHidrometro(Constantes.LIGACAO_POCO);
			}
			if (hidrometro != null){
				Repositorio.indiceNumeroHidrometroImovel.insertId(new BinaryTreeItem(
					Util.convertendoLetraParaNumeros(hidrometro),id));

			}else {
		    	if(!imovel.isImovelInformativo() && 
		    		imovel.getId() != 0 &&
					!Configuracao.getInstancia().getIdsNaoHidrometrados().contains(new Integer(imovel.getId())) ){
					
		    		Configuracao.getInstancia().getIdsNaoHidrometrados().addElement(new Integer(imovel.getId()));
		    	}		
			}

		}
	    }
	}
	
	Repositorio.salvarObjeto(ImovelReg14.getInstancia());
	Repositorio.salvarObjeto(dadosRelatorio);

	return imovel;
    }

    /**
     * Tipo de Medicao do Imovel Atual
     * 
     * @return Tipo de Medição
     */
    public int getTipoMedicaoSelecionado() {
	return tipoMedicaoSelecionado;
    }

    /**
     * Vai para o tipoMedicao Poço do Imovel selecionado, salvando os valores
     * alterados caso seja necessário.
     * 
     * @param leitura
     *            Leitura digitada pelo usuário.
     * @param anormalidade
     *            Anormalidade digitada pelo usuário.
     * @return O identificador do erro de validação.
     */
    public int poco(String leitura, int anormalidade, int tipoMedicao) {
	int resposta = Constantes.ID_SEM_ERRO;
	// resposta = this.andar(POCO, leitura, anormalidade,tipoMedicao);

	return resposta;
    }

    /**
     * Verifica os dados informados antes de gerar a conta..
     * 
     * @param leitura
     *            Leitura digitada pelo usuário.
     * @param anormalidade
     *            Anormalidade digitada pelo usuário.
     * @return O identificador do erro de validação.
     */
    public int andarConta(String leitura, int anormalidade, int tipoMedicao) {
	int resposta = Constantes.ID_SEM_ERRO;
	// resposta = this.andar(CONTA, leitura, anormalidade,tipoMedicao);

	return resposta;
    }

    /*
     * public void proximoSemValidarLeitura(){ if(this.roteiroEstaInvertido()){
     * if (this.indiceSelecionado == 0) { this.indiceSelecionado =
     * imoveis.size() - 1; } else { this.indiceSelecionado =
     * this.indiceSelecionado - 1; } }else{ this.indiceSelecionado =
     * (this.indiceSelecionado + 1) % imoveis. size(); }
     * this.tipoMedicaoSelecionado = ((ImovelReg8)getImovelSelecionado().
     * getRegistros8().elementAt(0)).getTipoMedicao(); } public void
     * anteriorSemValidarLeitura(){ if(this.roteiroEstaInvertido()){
     * this.indiceSelecionado = (this.indiceSelecionado + 1) % imoveis. size();
     * }else{ if (this.indiceSelecionado == 0) { this.indiceSelecionado =
     * imoveis.size() - 1; } else { this.indiceSelecionado =
     * this.indiceSelecionado - 1; } } this.tipoMedicaoSelecionado =
     * ((ImovelReg8)getImovelSelecionado().
     * getRegistros8().elementAt(0)).getTipoMedicao(); }
     */
    public boolean tipoTarifaPorCategoria(ImovelConta imovel) {

	for (int i = 0; i < imovel.getRegistros9().size(); i++) {
	    ImovelReg9 registro = (ImovelReg9) imovel.getRegistros9()
		    .elementAt(i);

	    if (registro.getCodigoSubcategoriaReg9() == Constantes.NULO_INT) {
		return true;
	    }
	}

	return false;
    }

    /**
     * Calcula o consumo minimo do imovel. Inicialmente tentamos pesquisar por
     * subcategoria, e caso nao consigamos, pesquisamos por categorias.
     * 
     * @param imovel
     * @return consumo minimo do imovel
     */
    public int calcularConsumoMinimoImovel(ImovelConta imovel,
	    Date dataInicioVigencia) {

		// Retorno
		int consumoMinimoImovel = 0;
	
		
		for (int i = 0; i < imovel.getRegistros2().size(); i++) {
		    ImovelReg2 reg2 = (ImovelReg2) imovel.getRegistros2().elementAt(i);
	
		    int codigoSubcategoria = 0;
		    if (reg2.getCodigoSubcategoria() != null
			    && !reg2.getCodigoSubcategoria().equals("")) {
			codigoSubcategoria = Integer.parseInt(reg2
				.getCodigoSubcategoria());
		    }
		    boolean calculoPorSubcategoria = false;
		    // Calculamos por subcategoria
		    for (int j = 0; j < imovel.getRegistros9().size(); j++) {
	
		      ImovelReg9 reg9 = (ImovelReg9) imovel.getRegistros9()
				.elementAt(j);
	
				if (Util.compararData(dataInicioVigencia, reg9
					.getDataVigenciaReg9()) == 0
					&& imovel.getCodigoTarifa() == reg9
						.getCodigoTarifaReg9()
					&& reg2.getCodigoCategoria() == reg9
						.getCodigoCategoriaReg9()
					&& codigoSubcategoria == reg9
						.getCodigoSubcategoriaReg9()) {
				    consumoMinimoImovel += reg9
					    .getConsumoMinimoSubcategoriaReg9()
					    * reg2.getQtdEconomiasSubcategoria();
				    calculoPorSubcategoria = true;
				    break;
				}
		    }
		    if(!calculoPorSubcategoria){
			    // Calculamos por categoria
			    for (int j = 0; j < imovel.getRegistros9().size(); j++) {
		
				    ImovelReg9 reg9 = (ImovelReg9) imovel.getRegistros9()
					    .elementAt(j);
		
				    if (Util.compararData(dataInicioVigencia, reg9
					    .getDataVigenciaReg9()) == 0
					    && imovel.getCodigoTarifa() == reg9
						    .getCodigoTarifaReg9()
					    && reg2.getCodigoCategoria() == reg9
						    .getCodigoCategoriaReg9()
					    && (reg9.getCodigoSubcategoriaReg9() == Constantes.NULO_INT || reg9
						    .getCodigoSubcategoriaReg9() == 0)) {
						consumoMinimoImovel += reg9
							.getConsumoMinimoSubcategoriaReg9()
							* reg2.getQtdEconomiasSubcategoria();
						break;
				    }
				}
		    }
		}

	  return consumoMinimoImovel;
    }

    /**
     * seleciona as faixas para calcular o valor faturado
     * 
     * @param tipoTarifaPorCategoria
     *            informa se o tipo de calculo da tarifa é por categoria
     * @param inicioVigencia
     *            data de inicio da vigencia
     * @param codigo
     *            código da categoria ou subcategoria
     * @return
     */
    /*
     * public Vector selecionarFaixasCalculoValorFaturado( boolean
     * tipoTarifaPorCategoria, String codigo, int codigoTarifa ,Date
     * dataInicioVigencia ){ Vector retorno = new Vector(); for ( int i = 0; i <
     * registrosTipo10.size(); i++ ){ ImovelReg10 registro = ( ImovelReg10 )
     * registrosTipo10.elementAt( i ); if ( tipoTarifaPorCategoria ){ if (
     * Util.compararData(dataInicioVigencia,
     * registro.getDataInicioVigenciaReg10()) == 0 && codigoTarifa ==
     * registro.getCodigoTarifaReg10() && Integer.parseInt( codigo ) ==
     * registro.getCodigoCategoriaReg10() && (
     * registro.getCodigoSubcategoriaReg10() == Constantes.NULO_INT ||
     * registro.getCodigoSubcategoriaReg10() == 0 ) ){ retorno.addElement(
     * registro ); } } else { if ( Util.compararData(dataInicioVigencia,
     * registro.getDataInicioVigenciaReg10()) == 0 && codigoTarifa ==
     * registro.getCodigoTarifaReg10() && Integer.parseInt( codigo ) ==
     * registro.getCodigoCategoriaReg10() && Integer.parseInt( codigo ) ==
     * registro.getCodigoSubcategoriaReg10() ){ retorno.addElement( registro );
     * } } } return retorno; }
     */

    /**
     * Segundo o [UC0743] 2.1. o subfluxo [SB0001 - Cáculo Simples para uma
     * única Tarifa] É aplicado quando todos os dados (Registro do tipo 1) têm a
     * mesma data inicial de vigência.
     * 
     * @return Verdadeiro se for para aplicar o cálculo simples, e falso caso
     *         contrário.
     */
    public Object[] deveAplicarCalculoSimples(ImovelConta imovel) {

	Object[] retorno = new Object[2];
	Boolean calculoSimples = Boolean.TRUE;
	Date[] dataInicioVigencia = new Date[4];
	int tamanho = 0;
	if (imovel.getRegistros9() != null){
		tamanho = imovel.getRegistros9().size();
    }
	int indiceData = 0;
	// ImovelReg9 registro = ( ImovelReg9 ) this.registrosTipo9.elementAt( i
	// );
	Date data0 = ((ImovelReg9) imovel.getRegistros9().elementAt(0))
		.getDataVigenciaReg9();

	dataInicioVigencia[indiceData] = data0;

	Date data1 = null;
	for (int i = 1; i < tamanho; i++) {
	    data1 = ((ImovelReg9) imovel.getRegistros9().elementAt(i))
		    .getDataVigenciaReg9();

	    if (Util.compararData(data0, data1) != 0) {
		calculoSimples = Boolean.FALSE;
		indiceData++;
		data0 = data1;
		dataInicioVigencia[indiceData] = data0;
	    }
	}
	retorno[0] = calculoSimples;
	retorno[1] = dataInicioVigencia;
	return retorno;
    }

    /**
     * [UC0743] Calcular Valores de Água/Esgoto
     */
    public void calcularValores(ImovelConta imovel, Consumo consumo,
	    int tipoMedicao) {
	Object[] dadosCalculo = this.deveAplicarCalculoSimples(imovel);
	boolean calculoSimples = ((Boolean) dadosCalculo[0]).booleanValue();
	Date[] dataInicioVigencia = (Date[]) dadosCalculo[1];
	if (calculoSimples) {
	    this.calculoSimples(imovel, consumo, tipoMedicao,
		    dataInicioVigencia[0]);
	} else {
	    this.calculoProporcionalMaisUmaTarifa(imovel, consumo, tipoMedicao);
	}
    }

    /**
     * [SB0001] - Cálculo Simples para uma Única Tarifa.
     */
    private void calculoSimples(ImovelConta imovel, Consumo consumo,
	    int tipoMedicao, Date dataInicioVigencia) {

	// 1. Verificamos se o tipo de calculo é por categoria ou por
	// subcategoria
	boolean tipoTarifaPorCategoria = ControladorImoveis.getInstancia()
		.tipoTarifaPorCategoria(imovel);

	// Consumo minimo do imovel
	int consumoMinimoImovel = 0;
	int excessoImovel = 0;

	// 3. Caso o tipo de calculo da tarifa seja igual a 3
	if (imovel.getTipoCalculoTarifa() == Tarifa.TIPO_CALCULO_TARIFA_3) {
	    // 3.1.1 Calculamos o consumo minimo do imovel com a data de
	    // vigencia do primeiro
	    // dado tarifa que encontramos

	    //Daniel - Novo método de calculo - condominio
	    if (imovel.getIndcCondominio() == Constantes.SIM && imovel.getMatriculaCondominio() == Constantes.NULO_INT){
	    	consumoMinimoImovel = 0;
	    }else{
	    	consumoMinimoImovel = ControladorImoveis.getInstancia().calcularConsumoMinimoImovel(imovel, dataInicioVigencia);
	    }

	    // 3.1.2 Calculamos o consumo em excesso, onde
	    // retiramos o consumo a ser cobrado do mes o consumo
	    // minimo do imovel
	    Double consumoCobradoMes;

	    //Daniel - Novo método de calculo - condominio
	    if (imovel.getIndcCondominio() == Constantes.SIM && imovel.getMatriculaCondominio() == Constantes.NULO_INT){

	    	if (tipoMedicao == ControladorConta.LIGACAO_AGUA) {
		    	consumoCobradoMes = new Double(imovel.getEfetuarRateioConsumoDispositivoMovelHelper().getConsumoParaRateioAgua());
		    }else{
		    	consumoCobradoMes = new Double(imovel.getEfetuarRateioConsumoDispositivoMovelHelper().getConsumoParaRateioEsgoto());
		    }
	    	
	    }else{
		    consumoCobradoMes = new Double(consumo.getConsumoCobradoMes());
	    }
	    excessoImovel = consumoCobradoMes.intValue() - consumoMinimoImovel;
	}

	// 4. Calculamos o consumo por economia
	int consumoPorEconomia;

	//Daniel - Novo método de calculo - condominio
    if (imovel.getIndcCondominio() == Constantes.SIM && imovel.getMatriculaCondominio() == Constantes.NULO_INT){

    	if (tipoMedicao == ControladorConta.LIGACAO_AGUA) {
        	consumoPorEconomia = imovel.getEfetuarRateioConsumoDispositivoMovelHelper().getConsumoParaRateioAgua() 
        	/ imovel.getQuantidadeEconomiasTotal();
	    }else{
	    	consumoPorEconomia = imovel.getEfetuarRateioConsumoDispositivoMovelHelper().getConsumoParaRateioEsgoto() 
	    	/ imovel.getQuantidadeEconomiasTotal();
	    }

    }else{
    	consumoPorEconomia = consumo.getConsumoCobradoMes()
		/ imovel.getQuantidadeEconomiasTotal();
    }
	
	int consumoEconomiaCategoriaOuSubcategoria = 0;
	int consumoFaturadoCategoriaOuSubcategoria = 0;

	// 5. Para cada registro tipo 2
	for (int i = 0; i < imovel.getRegistros2().size(); i++) {

	    // Pegamos o regitro tipo 2 atual
	    ImovelReg2 dadosEconomiasSubcategorias = (ImovelReg2) imovel
		    .getRegistros2().elementAt(i);

	    // 5.1 Quantidade de economias para cada categoria ou subcategoria
	    int quantidadeEconomiasCategoriaSubCategoria;

	    if (!dadosEconomiasSubcategorias.getFatorEconomiaCategoria().trim()
		    .equals(Constantes.NULO_STRING)) {
		quantidadeEconomiasCategoriaSubCategoria = Integer
			.parseInt(dadosEconomiasSubcategorias
				.getFatorEconomiaCategoria().trim());
	    } else {
		if (tipoTarifaPorCategoria) {
		    quantidadeEconomiasCategoriaSubCategoria = imovel
			    .getQuantidadeEconomias(dadosEconomiasSubcategorias
				    .getCodigoCategoria(),
				    dadosEconomiasSubcategorias
					    .getCodigoSubcategoria());
		} else {
		    quantidadeEconomiasCategoriaSubCategoria = dadosEconomiasSubcategorias
			    .getQtdEconomiasSubcategoria();
		}

	    }

	    // 5.2 Seleciona as tarifas de consumo para cada categoria ou
	    // subcategoria do imovel
	    ImovelReg9 dadosTarifa = null;

	    if (tipoTarifaPorCategoria) {
		dadosTarifa = imovel.pesquisarDadosTarifaImovel(
			tipoTarifaPorCategoria, dadosEconomiasSubcategorias
				.getCodigoCategoria()
				+ "", dadosEconomiasSubcategorias
				.getCodigoSubcategoria(), imovel
				.getCodigoTarifa(), dataInicioVigencia);
	    } else {
		dadosTarifa = imovel.pesquisarDadosTarifaImovel(
			tipoTarifaPorCategoria, dadosEconomiasSubcategorias
				.getCodigoCategoria()
				+ "", dadosEconomiasSubcategorias
				.getCodigoSubcategoria(), imovel
				.getCodigoTarifa(), dataInicioVigencia);

		if (dadosTarifa == null) {
		    dadosTarifa = imovel.pesquisarDadosTarifaImovel(true,
			    dadosEconomiasSubcategorias.getCodigoCategoria()
				    + "", dadosEconomiasSubcategorias
				    .getCodigoSubcategoria(), imovel
				    .getCodigoTarifa(), dataInicioVigencia);
		}
	    }

	    dadosEconomiasSubcategorias.setTarifa(new DadosTarifa(dadosTarifa));

	    // 5.3 Calcula os seguintes valores, da categoria ou subcategoria
	    // pesquisada.
	    // 5.3.1
	    double valorTarifaMinima = dadosTarifa
		    .getTarifaMinimaCategoriaReg9()
		    * quantidadeEconomiasCategoriaSubCategoria;

	    // 5.3.2
	    int consumoMinimo = dadosTarifa.getConsumoMinimoSubcategoriaReg9()
		    * quantidadeEconomiasCategoriaSubCategoria;

	    // 5.3.3
	    double valorPorEconomia = dadosTarifa
		    .getTarifaMinimaCategoriaReg9();
	    	
	    //Daniel - Novo método de calculo - condominio
	    // Para imovel MACRO Condominio
	    if (imovel.getIndcCondominio() == Constantes.SIM && imovel.getMatriculaCondominio() == Constantes.NULO_INT && consumoPorEconomia <= 0){
		    
	    	valorTarifaMinima =0;
		    consumoMinimo = 0;
		    valorPorEconomia = 0;	
	    }
	    
	    // 5.3.4
	    if (consumoPorEconomia > dadosTarifa
		    .getConsumoMinimoSubcategoriaReg9()) {
		consumoEconomiaCategoriaOuSubcategoria = dadosTarifa
			.getConsumoMinimoSubcategoriaReg9();
	    } else {
		consumoEconomiaCategoriaOuSubcategoria = consumoPorEconomia;
	    }

	    int consumoExcedente = 0;

	    // 5.3.5
	    if (imovel.getTipoCalculoTarifa() == Tarifa.TIPO_CALCULO_TARIFA_3) {
		// 5.3.5.1
		if (excessoImovel > 0) {
		    // 5.3.5.1.1
		    consumoExcedente = (int) (excessoImovel * consumoMinimo)
			    / consumoMinimoImovel;

		    consumoExcedente = consumoExcedente
			    / quantidadeEconomiasCategoriaSubCategoria;
		}
	    } else {
		consumoExcedente = consumoPorEconomia
			- dadosTarifa.getConsumoMinimoSubcategoriaReg9();

	    }

	    Vector faixasParaInclusao = new Vector();

	    // 5.4
	        if (consumoExcedente > 0) {
	        // 5.4.2
	        Vector faixas = null;

	        if (tipoTarifaPorCategoria) {
	            faixas = imovel.selecionarFaixasCalculoValorFaturado(
	                tipoTarifaPorCategoria, dadosEconomiasSubcategorias
	                    .getCodigoCategoria()
	                    + "",dadosEconomiasSubcategorias
	                    .getCodigoSubcategoria(), imovel.getCodigoTarifa(),
	                dataInicioVigencia);
	        } else {
	            faixas = imovel.selecionarFaixasCalculoValorFaturado(
	                tipoTarifaPorCategoria, dadosEconomiasSubcategorias.getCodigoCategoria()
	                + "", dadosEconomiasSubcategorias
	                .getCodigoSubcategoria(), imovel
	                    .getCodigoTarifa(), dataInicioVigencia);

	            if (faixas == null || faixas.isEmpty()) {
	            faixas = imovel.selecionarFaixasCalculoValorFaturado(
	                true, dadosEconomiasSubcategorias
	                    .getCodigoCategoria()
	                    + "",dadosEconomiasSubcategorias
	                    .getCodigoSubcategoria(),imovel.getCodigoTarifa(),
	                dataInicioVigencia);
	            }
	        }

		int consumoFaturadoFaixa = 0;
		double valorFaturadoFaixa = 0d;
		int limiteInicialConsumoFaixa = 0;
		int limiteFinalConsumoFaixa = 0;
		double valorTarifaFaixa = 0d;
		int limiteFaixaFimAnterior = 0;
		if (dadosTarifa.getConsumoMinimoSubcategoriaReg9() != Constantes.NULO_INT) {
		    limiteFaixaFimAnterior = dadosTarifa
			    .getConsumoMinimoSubcategoriaReg9();
		}
		// double valorEconomia = 0;
		// int consumoEconomia = 0;

		// 5.4.3
		if (imovel.getTipoCalculoTarifa() == Tarifa.TIPO_CALCULO_TARIFA_2) {
		    // 5.4.3.1
		    for (int j = 0; j < faixas.size(); j++) {
			ImovelReg10 faixa = (ImovelReg10) faixas.elementAt(j);

			if (consumoExcedente <= faixa
				.getLimiteFinalFaixaReg10()
				&& consumoExcedente >= faixa
					.getLimiteInicialFaixaReg10()) {

			    valorFaturadoFaixa = this
				    .calcularValorFaturadoFaixa(
					    consumoPorEconomia,
					    valorTarifaMinima, faixa
						    .getValorM3FaixaReg10());

			    limiteInicialConsumoFaixa = faixa
				    .getLimiteInicialFaixaReg10();
			    limiteFinalConsumoFaixa = faixa
				    .getLimiteFinalFaixaReg10();

			    consumoFaturadoFaixa = faixa
				    .getLimiteFinalFaixaReg10()
				    - limiteFaixaFimAnterior;

			    DadosFaturamentoFaixa faixaParaInclusao = new DadosFaturamentoFaixa(
				    consumoFaturadoFaixa, valorFaturadoFaixa,
				    limiteInicialConsumoFaixa,
				    limiteFinalConsumoFaixa, faixa
					    .getValorM3FaixaReg10());

			    faixasParaInclusao.addElement(faixaParaInclusao);
			    valorPorEconomia = valorFaturadoFaixa;
			    consumoEconomiaCategoriaOuSubcategoria = consumoFaturadoFaixa;

			    break;
			}
		    }
		} else {
		    // 5.4.4
		    for (int j = 0; j < faixas.size() && consumoExcedente > 0; j++) {
			ImovelReg10 faixa = (ImovelReg10) faixas.elementAt(j);

			// 5.4.4.1.1
			consumoFaturadoFaixa = (faixa
				.getLimiteFinalFaixaReg10() - limiteFaixaFimAnterior);

			// 5.4.4.1.2
			if (consumoExcedente < consumoFaturadoFaixa) {
			    consumoFaturadoFaixa = consumoExcedente;
			}

			// 5.4.4.1.3
			valorFaturadoFaixa = consumoFaturadoFaixa
				* faixa.getValorM3FaixaReg10();

			// 5.4.4.1.4
			valorPorEconomia += valorFaturadoFaixa;

			// valorFaturadoFaixa = valorFaturadoFaixa *
			// quantidadeEconomiasCategoriaSubCategoria;

			// 5.4.4.1.5

			consumoEconomiaCategoriaOuSubcategoria = consumoEconomiaCategoriaOuSubcategoria
				+ consumoFaturadoFaixa;

			// 5.4.4.1.6
			limiteInicialConsumoFaixa = faixa
				.getLimiteInicialFaixaReg10();

			// 5.4.4.1.7
			limiteFinalConsumoFaixa = faixa
				.getLimiteFinalFaixaReg10();

			// 5.4.4.1.8
			valorTarifaFaixa = faixa.getValorM3FaixaReg10();

			// 5.4.4.1.9
			consumoExcedente -= consumoFaturadoFaixa;

			DadosFaturamentoFaixa faixaParaInclusao = new DadosFaturamentoFaixa(
				consumoFaturadoFaixa, valorFaturadoFaixa,
				limiteInicialConsumoFaixa,
				limiteFinalConsumoFaixa, valorTarifaFaixa);

			faixasParaInclusao.addElement(faixaParaInclusao);
			// Recupera a faixa final da faixa anterior
			limiteFaixaFimAnterior = faixa
				.getLimiteFinalFaixaReg10();
		    }
		}
	    }

	    // 5.5
	    double valorFaturado = 0d;
	    if (imovel.getTipoCalculoTarifa() == Tarifa.TIPO_CALCULO_TARIFA_2) {
	    	valorFaturado = valorPorEconomia;

	    } else {
	    	valorFaturado = valorPorEconomia * quantidadeEconomiasCategoriaSubCategoria;
	    }

	    // 5.6
	    if (imovel.getTipoCalculoTarifa() == Tarifa.TIPO_CALCULO_TARIFA_2) {
	    	consumoFaturadoCategoriaOuSubcategoria = consumoEconomiaCategoriaOuSubcategoria;

	    } else {
	    	consumoFaturadoCategoriaOuSubcategoria = consumoEconomiaCategoriaOuSubcategoria
				* quantidadeEconomiasCategoriaSubCategoria;
	    }

	    if (imovel.getIndcFaturamentoAgua() == Constantes.SIM){
	    	
	    }
	    
	    if (tipoMedicao == Constantes.LIGACAO_POCO) {
			valorFaturado = Util.arredondar(valorFaturado
				* (imovel.getPercentCobrancaEsgoto() / 100),2);
			valorTarifaMinima = Util.arredondar(valorTarifaMinima
				* (imovel.getPercentCobrancaEsgoto() / 100),2);
	    }

	    DadosFaturamento faturamento = new DadosFaturamento(valorFaturado,
		    consumoFaturadoCategoriaOuSubcategoria, valorTarifaMinima,
		    consumoMinimo, faixasParaInclusao);

	    if (tipoMedicao == ControladorConta.LIGACAO_AGUA) {
	    	dadosEconomiasSubcategorias.setFaturamentoAgua(faturamento);
	    } else {
	    	dadosEconomiasSubcategorias.setFaturamentoEsgoto(faturamento);
	    }
	}
    }

    /**
     * Calculamos o valor a ser faturado na faixa
     * 
     * @param consumoFaturado
     *            consumo que foi faturafo
     * @param valorTarifaMinimaCategoria
     *            Tarifa minima da categoria
     * @param valorTarifaFaixa
     *            Tarifa na faixa
     * @return
     */
    private double calcularValorFaturadoFaixa(int consumoFaturado,
	    double valorTarifaMinimaCategoria, double valorTarifaFaixa) {

	// Legenda: x = consumoFaturado; NI = valorTarifaMinima

	double retorno = 0;

	int CONSUMO_SUPERIOR = 201;

	// Consumidores Taxados
	if (consumoFaturado < CONSUMO_SUPERIOR) {

	    double divisor = 10000;
	    int multiplicadorExp = 7;

	    // NI
	    if (consumoFaturado <= 10) {
		retorno = valorTarifaMinimaCategoria;
	    }
	    // NI(7x² + valorTarifaFaixa * x) / 10000
	    else {

		double parcial = consumoFaturado * consumoFaturado;
		parcial = parcial * multiplicadorExp;

		double parcial2 = valorTarifaFaixa * consumoFaturado;

		double parcialFinal = parcial + parcial2;

		parcialFinal = valorTarifaMinimaCategoria * parcialFinal;

		retorno = parcialFinal / divisor;
	    }
	}
	// Consumo Superior = NI(valorTarifaFaixa * x - 11,2)
	else {

	    double valor1 = 11.2;

	    double parcial = valorTarifaFaixa * consumoFaturado;

	    parcial = parcial - valor1;

	    retorno = valorTarifaMinimaCategoria * parcial;
	}

	return retorno;
    }

    public void carregarImovelSelecionado() {
	// Verificamos qual o ultimo imovel selecionado
	Repositorio.carregarObjeto(imovelSelecionado, Configuracao
		.getInstancia().getIdImovelSelecionado());
	
	this.setImovelSelecionado( imovelSelecionado );
	
	idImovelSelecionado = imovelSelecionado.getId();
    }

    public int getQuantidadeImoveis() {
	return ControladorImoveis.qtdImoveis;
    }

    public int getQtdRegistros() {
	return qtdRegistros;
    }

    public void setQtdRegistros(int qtdRegistros) {
	this.qtdRegistros = qtdRegistros;
    }

    /**
     * [UC0743] Calcular Valores de Água/Esgoto no Dispositivo Móvel [SB0002 –
     * Cálculo Proporcional Para Mais de Uma Tarifa]
     * 
     * @author Bruno Barros
     * @date 16/10/2009
     */
    public void calculoProporcionalMaisUmaTarifa(ImovelConta imovel,
	    Consumo consumo, int tipoMedicao) {

	/*
	 * 1.O sistema seleciona as tarifas vigentes para o imóvel no período de
	 * leitura da seguinte forma: 1.1.Seleciona todas as ocorrências de
	 * registro 9 com código da tarifa igual ao código da tarifa do imóvel e
	 * com data de início da vigência entre as datas de leitura anterior e a
	 * data corrente, inclusive; 1.2.Caso não seja selecionada nenhuma
	 * ocorrência no item 1.1 ou caso nenhuma ocorrência selecionada tenha
	 * data de início da vigência=data de leitura anterior, selecionar
	 * também a ocorrência de registro 9 com código da tarifa igual ao
	 * código da tarifa do imóvel e com a maior data de início da vigência
	 * que seja menor que a data de leitura anterior. 1.3.As tarifas
	 * vigentes para o período de leitura serão as ocorrências selecionadas
	 * nos passos 1.1 e 1.2 e devem estar classificadas pela data de início
	 * da vigência. TODOS OS REGISTROS TIPO 9 PRESENTES NO IMOVEL JA
	 * RESPEITAM AS CONDIÇÕES SUPRECITADAS, LOGO APENAS SELECIONAMOS
	 */
	Vector regsTipo9 = imovel.getRegistros9();

	// Selecionamos a data de leitura anterior
	// dando prioridade ao ligação de água

	Date dataLeituraAnterior = null;
	if (imovel.getRegistro8(Constantes.LIGACAO_AGUA) != null
		&& !imovel.getRegistro8(Constantes.LIGACAO_AGUA).equals("")) {
	    dataLeituraAnterior = imovel.getRegistro8(Constantes.LIGACAO_AGUA)
		    .getDataLeituraAnteriorFaturada();
	} else {
	    if (imovel.getRegistro8(Constantes.LIGACAO_POCO) != null
		    && !imovel.getRegistro8(Constantes.LIGACAO_POCO).equals("")) {
		dataLeituraAnterior = imovel.getRegistro8(
			Constantes.LIGACAO_POCO).getDataLeituraAnteriorFaturada();
	    }
	}

	if (dataLeituraAnterior == null) {
	    dataLeituraAnterior = imovel.getDataLeituraAnteriorNaoMedido();
	}

	// 2.Calcula a quantidade de dias entre as leituras = data corrente -
	// data de leitura anterior;
	long qtdDiasEntreLeituras = Util.obterModuloDiferencasDatasDias(Util
		.dataAtual(), dataLeituraAnterior);

	// 3.Data da vigência inicial = data da leitura anterior
	Date dataVigenciaInicial = dataLeituraAnterior;

	// 4.Para cada tarifa vigente para o período de leitura, obtida no passo
	// 1.3,
	// ordenando por data de início da vigência, o sistema efetua os
	// seguintes
	// procedimentos
	for (int i = 0; i < regsTipo9.size(); i++) {
	    // 4.1.[SB0001 – Cálculo Simples Para Uma Única Tarifa];
	    ImovelReg9 reg9 = (ImovelReg9) regsTipo9.elementAt(i);
	    this.calculoSimples(imovel, consumo, tipoMedicao, reg9
		    .getDataVigenciaReg9());

	    // 4.2.Caso exista próxima tarifa vigente então data da
	    // vigência final = data de início da vigência da próxima
	    // tarifa vigente menos um dia, caso contrário, data da vigência
	    // final = data corrente;
	    Date dataVigenciaFinal = null;

	    if (i < regsTipo9.size() - 1) {
		ImovelReg9 proxReg9 = (ImovelReg9) regsTipo9.elementAt(i + 1);
		System.out.println("Data REG 9"
			+ Util.formatarData(proxReg9.getDataVigenciaReg9()));
		dataVigenciaFinal = Util.adicionarNumeroDiasDeUmaData(proxReg9
			.getDataVigenciaReg9(), -1);
	    } else {
		dataVigenciaFinal = Util.dataAtual();
	    }

	    // 4.3.Caso seja a primeira tarifa, a quantidade de dias de vigência
	    // da tarifa dentro do período de leitura = data da vigência final –
	    // data da vigência inicial;

	    long qtdDiasVigenciaTarifaDentroPeriodoLeitura;

	    if (i == 0) {
		qtdDiasVigenciaTarifaDentroPeriodoLeitura = Util
			.obterModuloDiferencasDatasDias(dataVigenciaFinal,
				dataVigenciaInicial);

		// 4.4.Caso contrário a quantidade de dias de vigência da tarifa
		// dentro do
		// período de leitura = data da vigência final – data da
		// vigência inicial
		// + 1 dia;
	    } else {

		qtdDiasVigenciaTarifaDentroPeriodoLeitura = Util
			.obterModuloDiferencasDatasDias(dataVigenciaFinal,
				dataVigenciaInicial);

		qtdDiasVigenciaTarifaDentroPeriodoLeitura = qtdDiasVigenciaTarifaDentroPeriodoLeitura + 1;
	    }

	    // 4.5.Calcula o fator de vigência da tarifa = quantidade de dias de
	    // vigência
	    // da tarifa no período de leitura / quantidade de dias entre as
	    // leituras;
	    double fatorVigenciaTarifa = Util.arredondar(
		    (double) qtdDiasVigenciaTarifaDentroPeriodoLeitura
			    / (double) qtdDiasEntreLeituras, 4);

	    /*
	     * 1.1.Para cada Categoria, aplica o fator de vigência da tarifa
	     * sobre os seguintes atributos obtidos nos cálculos efetuados no
	     * passo 4.1, arredondando para duas casas decimais: 1.1.1.Valor
	     * faturado; 1.1.2.Valor da tarifa mínima; 1.1.3.Para cada faixa da
	     * tarifa de consumo: 1.1.3.1.Valor faturado na faixa; 1.1.3.2.Valor
	     * da tarifa na faixa.
	     */
	    for (int j = 0; j < imovel.getCategorias().size(); j++) {

		ImovelReg2 reg2 = (ImovelReg2) imovel.getCategorias()
			.elementAt(j);

		DadosFaturamento dadosFaturamento;
		// cria o objteto de faturamento proporcional para somar os
		// valores
		// por categoria de cada data de vigencia
		DadosFaturamento dadosFaturamentoProporcional;

		if (tipoMedicao == Constantes.LIGACAO_AGUA) {
		    dadosFaturamento = reg2.getFaturamentoAgua();
		    dadosFaturamentoProporcional = reg2
			    .getFaturamentoAguaProporcional();
		} else {
		    dadosFaturamento = reg2.getFaturamentoEsgoto();
		    dadosFaturamentoProporcional = reg2
			    .getFaturamentoEsgotoProporcional();
		}

		if (dadosFaturamentoProporcional == null
			|| dadosFaturamentoProporcional.equals("") || i == 0) {
		    dadosFaturamentoProporcional = new DadosFaturamento();
		    dadosFaturamentoProporcional.setValorFaturado(0d);
		    dadosFaturamentoProporcional.setValorTarifaMinima(0d);

		}
		double valorFaturadoPorFator = dadosFaturamentoProporcional
			.getValorFaturado();
		valorFaturadoPorFator = valorFaturadoPorFator
			+ Util.arredondar(dadosFaturamento.getValorFaturado()
				* fatorVigenciaTarifa, 2);

		double valorTarifaMinimaPorFator = dadosFaturamentoProporcional
			.getValorTarifaMinima();
		valorTarifaMinimaPorFator = valorTarifaMinimaPorFator
			+ Util.arredondar(dadosFaturamento
				.getValorTarifaMinima()
				* fatorVigenciaTarifa, 2);

		// seta os valores adicionados para os dados de faturamento
		// proporcional
		dadosFaturamentoProporcional
			.setValorFaturado(valorFaturadoPorFator);
		dadosFaturamentoProporcional
			.setValorTarifaMinima(valorTarifaMinimaPorFator);

		// seta os dados do faturamento proporcional no dado de
		// faturamento
		dadosFaturamento.setValorFaturado(valorFaturadoPorFator);
		dadosFaturamento
			.setValorTarifaMinima(valorTarifaMinimaPorFator);

		Vector faixasProporcional = new Vector();

		for (int k = 0; k < dadosFaturamento.getFaixas().size(); k++) {
		    DadosFaturamentoFaixa faixa = (DadosFaturamentoFaixa) dadosFaturamento
			    .getFaixas().elementAt(k);
		    DadosFaturamentoFaixa faixaProporcional = null;

		    if (dadosFaturamentoProporcional.getFaixas() == null
			    || dadosFaturamentoProporcional.getFaixas().equals(
				    "") || i == 0) {
			faixaProporcional = new DadosFaturamentoFaixa();
			faixaProporcional.setValorFaturado(0d);
			faixaProporcional.setValorTarifa(0d);
		    } else {
			faixaProporcional = (DadosFaturamentoFaixa) dadosFaturamentoProporcional
				.getFaixas().elementAt(k);
		    }

		    double valorFaturadoPorFatorNaFaixa = faixaProporcional
			    .getValorFaturado();
		    valorFaturadoPorFatorNaFaixa = valorFaturadoPorFatorNaFaixa
			    + Util.arredondar(faixa.getValorFaturado()
				    * fatorVigenciaTarifa, 2);

		    double valorTarifaPorFatorNaFaixa = faixaProporcional
			    .getValorTarifa();
		    valorTarifaPorFatorNaFaixa = valorTarifaPorFatorNaFaixa
			    + Util.arredondar(faixa.getValorTarifa()
				    * fatorVigenciaTarifa, 2);

		    // seta os valores adicionados para os dados de faturamento
		    // proporcional
		    faixaProporcional
			    .setValorFaturado(valorFaturadoPorFatorNaFaixa);
		    faixaProporcional
			    .setValorTarifa(valorTarifaPorFatorNaFaixa);
		    faixasProporcional.addElement(faixaProporcional);

		    // seta os dados do faturamento proporcional no dado de
		    // faturamento
		    faixa.setValorFaturado(valorFaturadoPorFatorNaFaixa);
		    faixa.setValorTarifa(valorTarifaPorFatorNaFaixa);

		}

		// seta as faixas proporcionais no dado de faturamento
		// proporcional
		dadosFaturamentoProporcional.setFaixas(faixasProporcional);
		if (tipoMedicao == Constantes.LIGACAO_AGUA) {
		    reg2
			    .setFaturamentoAguaProporcional(dadosFaturamentoProporcional);
		} else {
		    reg2
			    .setFaturamentoEsgotoProporcional(dadosFaturamentoProporcional);
		}
	    }

	    // 4.8.Calcula data da vigência inicial = data da vigência final + 1
	    // dia.
	    dataVigenciaFinal = Util.adicionarNumeroDiasDeUmaData(
		    dataVigenciaFinal, +1);

	    dataVigenciaInicial = dataVigenciaFinal;
	}
    }

    public void proximoPendente() {
	if ( Configuracao.getInstancia().getIdsImoveisPendentes() != null && 
	     Configuracao.getInstancia().getIdsImoveisPendentes().size() > 0) {
	    ImovelConta imovel = new ImovelConta();

	    Repositorio.carregarObjeto(imovel, ((Integer) Configuracao.getInstancia()
		    .getIdsImoveisPendentes().elementAt(0)).intValue());

	    if (imovel != null) {
		this.setImovelSelecionado(imovel);
		Abas.getInstancia().criarAbas();
	    }
// Daniel - verificando existencia de imoveis nao impressos.
	} else if ( Configuracao.getInstancia().getIdsImoveisPendentes() != null && 
		     Configuracao.getInstancia().getIdsImoveisPendentes().size() > 0){

		ImovelConta imovel = new ImovelConta();

// Localizar primeiro imóveis com endereço de entrega normal.
//		if (Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().size() > 0 &&
//				(Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().size() < Configuracao.getInstancia().getIdsImoveisPendentes().size())){
//			for(int i =0; i < Configuracao.getInstancia().getIdsImoveisPendentes().size(); i++){
//				if(!Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(Configuracao.getInstancia()
//					    .getIdsImoveisPendentes().elementAt(i))){
//
//					Repositorio.carregarObjeto(imovel, ((Integer) Configuracao.getInstancia()
//						    .getIdsImoveisPendentes().elementAt(i)).intValue());
//					break;
//				}
//			}
//		}else{
			Repositorio.carregarObjeto(imovel, ((Integer) Configuracao.getInstancia()
				    .getIdsImoveisPendentes().elementAt(0)).intValue());
//		}
		

	    if (imovel != null) {
		this.setImovelSelecionado(imovel);
		Abas.getInstancia().criarAbas();
	    }
		
	}	else {
	    Util.mostrarErro("Todos os imóveis foram lidos e impressos...");
	}
    }
    
// Daniel - busca proximo imovel nao-hidrometrado.    
    public void imprimeNaoHidrometrados(Progress progresso){
		ImovelConta imovel = new ImovelConta();
		int i = 0;


		int numeroImoveis = Configuracao.getInstancia().getIdsNaoHidrometrados().size();
		System.out.println("imprimindo " + numeroImoveis + " imoveis fixos");
    	
		while ( 0 < Configuracao.getInstancia().getIdsNaoHidrometrados().size() ){
    	    // Informamos quanto ainda falta
    	    double d = (double) (i) / numeroImoveis;
    	    byte percentual = (byte) ((d) * 100);

    	    progresso.setProgress(percentual);
    	    progresso.repaint();

    	    i++;
    	    
			Repositorio.carregarObjeto(imovel, ((Integer) Configuracao.getInstancia()
				    .getIdsNaoHidrometrados().elementAt(0)).intValue());

		    if (imovel != null) {
				this.setImovelSelecionado(imovel);
		    }

			//Daniel - Verificar se a data atual é anterior ao mes de referencia da rota em andamento.
			if(Util.compararData(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), Util.dataAtual()) > 0){
				Util.MensagemConfirmacao("Data do celular está   errada. Por favor, verifique a configuração do celular e tente novamente.");
				break;
			
			}else{			    
	    		
	    	    System.out.println("imprimindo conta: " + i);
	    		
				BusinessConta.getInstancia().chamarCalculoConsumo(true);
	
			    double valorConta = imovel.getValorConta();			
	
	    		if ((imovel.getIndcEmissaoConta() == 2) ||
	       			(Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(imovel.getId()))) ||
					(!imovel.isValorContaAcimaDoMinimo()) ||
					((Util.validarValor(valorConta, ControladorConta.VALOR_LIMITE_CONTA) == false )) ||
//					(imovel.getIndicadorParalizarFaturamentoAgua() == Constantes.SIM) ||
//					(imovel.getIndicadorParalizarFaturamentoEsgoto() == Constantes.SIM) ||
					(valorConta == 0d && imovel.getValorResidualCredito() == 0d) ) {
	
	    				// Daniel - Remove id da lista.    			
	    				Configuracao.getInstancia().getIdsNaoHidrometrados().removeElementAt(0);
	    			
	    				// Daniel - lista de imoveis impressos
						Configuracao.getInstancia().getIdsImoveisPendentes().removeElement( new Integer(imovel.getId()) );
						if (!Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(imovel.getId()) )) {
							Configuracao.getInstancia().getIdsImoveisConcluidos().addElement( new Integer(imovel.getId()) );				
						}

	    		} else {
						// Validar situacao do retorno do calculo
					    boolean pesquisarDispositivos = true;
					    boolean erroImpressao = false;
	
							if (Fachada.getInstancia().getPrinter().equals("1")){
					    	//Daniel - Verificamos se algo ja foi salvo nas configurações de Bluetooth.
						    if (!Constantes.NULO_STRING.equals(Configuracao.getInstancia().getBluetoothAddress())) {
						    	pesquisarDispositivos = false;
								erroImpressao = Util.imprimirConta(Constantes.IMPRIME_IMOVEIS_FIXOS);
							}
				
							if (pesquisarDispositivos) {
							    if (Configuracao.getInstancia().getBluetoothAddress() == Constantes.NULO_STRING){
									Util.mostrarErro("Por favor, selecione uma impressora antes da impressão. Operação cancelada!");
							    	break;
							    }
							}
					    }else{
							erroImpressao = Util.imprimirConta(Constantes.IMPRIME_IMOVEIS_FIXOS);				    	
					    }
					    	
					if (erroImpressao){
						Util.mostrarErro("Erro ao imprimir imovel de matrícula" + imovel.getMatricula() + ". OPERAÇÃO CANCELADA!");
						break;
					}
				}
			}
		}
    	imovel = null;
    }
    
 // Daniel - imprime todos os imoveis fixos e hidrometrados. Hidrometrados calculados pela média.    
    public void imprimeTodosPelaMedia(Progress progresso){
		ImovelConta imovel = new ImovelConta();
		int i = 0;
    	int numeroImoveis = Configuracao.getInstancia().getIdsImoveisPendentes().size();
    	System.out.println("imprimindo " + numeroImoveis + " imoveis");
    	
    	while ( 0 < Configuracao.getInstancia().getIdsImoveisPendentes().size() ){
    	    // Informamos quanto ainda falta
    	    double d = (double) (i) / numeroImoveis;
    	    byte percentual = (byte) ((d) * 100);

    	    progresso.setProgress(percentual);
    	    progresso.repaint();

    	    i++;
    		
			Repositorio.carregarObjeto(imovel, ((Integer) Configuracao.getInstancia()
				    .getIdsImoveisPendentes().elementAt(0)).intValue());

		    if (imovel != null) {
				this.setImovelSelecionado(imovel);
		    }
		    
			//Daniel - Verificar se a data atual é anterior ao mes de referencia da rota em andamento.
			if(Util.compararData(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), Util.dataAtual()) > 0){
				Util.MensagemConfirmacao("Data do celular está   errada. Por favor, verifique a configuração do celular e tente novamente.");
				break;
			
			}else{			    
		    		
	
	    	    System.out.println("imprimindo conta: " + (i - 1) + " matricula: " + imovel.getMatricula());
	
	    	    BusinessConta.getInstancia().chamarCalculoConsumoMedio(true);
	
			    double valorConta = imovel.getValorConta();			
	
	    		if ((imovel.getIndcEmissaoConta() == 2) ||
	    			(Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(imovel.getId()))) ||
					(!imovel.isValorContaAcimaDoMinimo()) ||
//					(imovel.getIndicadorParalizarFaturamentoAgua() == Constantes.SIM) ||
//					(imovel.getIndicadorParalizarFaturamentoEsgoto() == Constantes.SIM) ||
					((Util.validarValor(valorConta, ControladorConta.VALOR_LIMITE_CONTA) == false )) ||
					(valorConta == 0d && imovel.getValorResidualCredito() == 0d) ) {
	
	// Daniel - Remove id da lista.    			
	    				Configuracao.getInstancia().getIdsImoveisPendentes().removeElementAt(0);
						if (!Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(imovel.getId()) )) {
							Configuracao.getInstancia().getIdsImoveisConcluidos().addElement( new Integer(imovel.getId()) );				
						}

				} else {
						// Validar situacao do retorno do calculo
					    boolean pesquisarDispositivos = true;
					    boolean erroImpressao = false;
	
						if (Fachada.getInstancia().getPrinter().equals("1")){
					    	//Daniel - Verificamos se algo ja foi salvo nas configurações de Bluetooth.
						    if (!Constantes.NULO_STRING.equals(Configuracao.getInstancia().getBluetoothAddress())) {
						    	pesquisarDispositivos = false;
								erroImpressao = Util.imprimirConta(Constantes.IMPRIME_TODOS_IMOVEIS_CONSUMO_MEDIO);
							}
				
							if (pesquisarDispositivos) {
							    if (Configuracao.getInstancia().getBluetoothAddress() == Constantes.NULO_STRING){
									Util.mostrarErro("Por favor, selecione uma impressora antes da impressão. Operação cancelada!");
							    	break;
							    }
							}
					    }else{
							erroImpressao = Util.imprimirConta(Constantes.IMPRIME_TODOS_IMOVEIS_CONSUMO_MEDIO);				    	
					    }
					    	
					if (erroImpressao){
						Util.mostrarErro("Erro ao imprimir imovel de matrícula" + imovel.getMatricula() + ". OPERAÇÃO CANCELADA!");
						break;
					}
				}
	    	}
    	}
    	imovel = null;
    }
    
    
    public void proximoARevisitar() {
	
	if ( Configuracao.getInstancia().getMatriculasRevisitar() != null && 
	     !Configuracao.getInstancia().getMatriculasRevisitar().isEmpty() ) {
	    Vector resultados = Repositorio.consultarImoveis(
		    FiltroImovel.PESQUISAR_POR_MATRICULA,
		    Configuracao.getInstancia().getMatriculasRevisitar().elementAt(0).toString() );	    

	    ControladorImoveis.getInstancia().setImovelSelecionado(
		    (ImovelConta) resultados.elementAt(0));
	    
	    Abas.getInstancia().criarAbas();	    
	} else {
	    Util.MensagemConfirmacao("Todos os imóveis foram revisitados...");
	}
    }
    
}