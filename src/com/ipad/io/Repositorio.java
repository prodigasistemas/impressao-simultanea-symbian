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

import java.util.Vector;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;

import net.sourceforge.floggy.persistence.Filter;
import net.sourceforge.floggy.persistence.FloggyException;
import net.sourceforge.floggy.persistence.ObjectSet;
import net.sourceforge.floggy.persistence.Persistable;
import net.sourceforge.floggy.persistence.PersistableManager;

import com.ipad.basic.Configuracao;
import com.ipad.basic.DadosRelatorio;
import com.ipad.basic.ImovelConta;
import com.ipad.business.ControladorImoveis;
import com.ipad.filters.FiltroImovel;
import com.ipad.io.binarytree.BinaryTreeItem;
import com.ipad.io.binarytree.index.IndiceMatriculaImovel;
import com.ipad.io.binarytree.index.IndiceNumeroHidrometroImovel;
import com.ipad.io.binarytree.index.IndiceQuadraImovel;
import com.ipad.io.binarytree.index.IndiceSequencialRotaImovel;
import com.ipad.util.Constantes;
import com.ipad.util.Util;

public class Repositorio {

    /**
     * Indica se existe um roteito em andamento para um novo Record Store não
     * ser criado por cima do existente.
     */
    private byte existeRoteiroEmAndamento = NAO_EXISTE;
    /**
     * Constante usada para verificar se existe roteiro em andamento.
     */
    private final static byte EXISTE = 0;
    /**
     * Constante usada para verificar se não existe roteiro em andamento.
     */
    private final static byte NAO_EXISTE = 1;
    private static Repositorio repositorio;
    
    // Todos os indices que forem criados devem ser colocados aqui.
    public static IndiceMatriculaImovel indiceMatriculaImovel = new IndiceMatriculaImovel();
    public static IndiceNumeroHidrometroImovel indiceNumeroHidrometroImovel = new IndiceNumeroHidrometroImovel();
    public static IndiceQuadraImovel indiceQuadraImovel = new IndiceQuadraImovel();
    public static IndiceSequencialRotaImovel indiceSequencialRotaImovel = new IndiceSequencialRotaImovel();
    public static boolean indicesCarregados = false;

//    public static PersistableManager pm = PersistableManager.getInstance();
    
    public static Persistable carregarObjeto(Class classe, Filter filtro) {
	ObjectSet objetos = null;
	Persistable retorno = null;
		try {
			objetos = PersistableManager.getInstance().find(classe, filtro, null);
		    if (objetos != null && objetos.size() > 0) {
			retorno = (Persistable) objetos.get(0);
		    }
		} catch (FloggyException e) {
			Util.mostrarErro("carregarObjeto: Erro na pesquisa", e);
		}
	return retorno;
    }

    public static Persistable carregarObjeto(int id) {
    	Persistable objeto = null;
		try {
		    PersistableManager.getInstance().load(objeto, id, false);
		} catch (FloggyException e) {
		    System.gc();
			Util.mostrarErro("carregarObjeto 2: Erro na pesquisa", e);
		}
		return objeto;
    }

    public static void carregarObjeto(Persistable objeto, int id) {
		try {
		    PersistableManager.getInstance().load(objeto, id, false);
		} catch (FloggyException e) {
		    System.gc();
			Util.mostrarErro("carregarObjeto 2: Erro na pesquisa", e);
		}
    }

    public static int salvarObjeto(Persistable objeto) {
	int retorno = 0;
	 	try {
	 		
	 		PersistableManager.getInstance().setProperty(PersistableManager.BATCH_MODE, Boolean.TRUE);
	 		retorno = PersistableManager.getInstance().save(objeto);
	 		PersistableManager.getInstance().setProperty(PersistableManager.BATCH_MODE, Boolean.FALSE);

	 	} catch (FloggyException e) {
		    System.gc();
		    retorno = -1;
		    Util.mostrarErro("salvarObjeto: Erro tentando salvar o objeto:", e);
		}
		return retorno;
    }

    public static void salvarObjetosInvertidos(Vector objetos) {
		for (int i = objetos.size() - 1; i >= 0; i--) {

	 		PersistableManager.getInstance().setProperty(PersistableManager.BATCH_MODE, Boolean.TRUE);
	 		
	 		Persistable objeto = (Persistable) objetos.elementAt(i);
		 	try {
				PersistableManager.getInstance().save(objeto);
		 	} catch (FloggyException e) {
		 	// Daniel - Problema de acesso ao banco ocorre com frequencia,
			    System.gc();
			    Util.mostrarErro("Erro tentando salvar o objeto:", e);
		 	}
	 		PersistableManager.getInstance().setProperty(PersistableManager.BATCH_MODE, Boolean.FALSE);
		}
    }

    public static void salvarObjetos(Vector objetos) {
		for (int i = 0; i < objetos.size(); i++) {
	 		PersistableManager.getInstance().setProperty(PersistableManager.BATCH_MODE, Boolean.TRUE);
	
			Persistable objeto = (Persistable) objetos.elementAt(i);
		 	try {
				PersistableManager.getInstance().save(objeto);
		 	} catch (FloggyException e) {
		 	// Daniel - Problema de acesso ao banco ocorre com frequencia,
			    System.gc();
			    Util.mostrarErro("salvarObjetos: Erro tentando salvar os objetos:", e);
		 	}
	 		PersistableManager.getInstance().setProperty(PersistableManager.BATCH_MODE, Boolean.FALSE);
		}
    }

    public static ObjectSet listarObjetos(Class classe) {
	ObjectSet objetos = null;
		try {
		    objetos = PersistableManager.getInstance().find(classe, null, null);
		} catch (FloggyException e) {
			// Daniel - Problema de acesso ao banco ocorre com frequencia,
		    System.gc();
		    Util.mostrarErro("listarObjetos: Erro na pesquisa", e);
		}
	return objetos;
    }

    public static Repositorio getInstancia() {
	if (repositorio == null) {
	    repositorio = new Repositorio();
	}
	return repositorio;
    }

    /**
     * Verifica se há um roteiro não percorrido em andamento.
     * 
     * @return Verdadeiro se há um roteiro em andamento, e falso caso contrório.
     */
    public boolean existeRoteiroEmAndamento() {
	if (this.existeRoteiroEmAndamento == NAO_EXISTE) {
	    ObjectSet set = listarObjetos(ImovelConta.class);
	    if (set != null && set.size() > 0) {
		this.existeRoteiroEmAndamento = EXISTE;
	    } else {
		this.existeRoteiroEmAndamento = NAO_EXISTE;
	    }
	}
	return this.existeRoteiroEmAndamento == EXISTE;
    }

    public static ObjectSet listarObjetos(Class classe, Filter filtro) {
	ObjectSet objetos = null;
	try {
	    objetos = PersistableManager.getInstance().find(classe, filtro, null);
	} catch (FloggyException e) {
		// Daniel - Problema de acesso ao banco ocorre com frequencia,
	    System.gc();
	    Util.mostrarErro("listarObjetos: Erro na pesquisa", e);
	}
	return objetos;
    }

    public static Persistable pesquisarPrimeiroObjeto(Class classe) {
	ObjectSet objetos = null;
	Persistable objeto = null;
	try {
	    objetos = PersistableManager.getInstance().find(classe, null, null);
	    if (objetos != null && objetos.size() != 0) {
		objeto = objetos.get(0);
	    }
	} catch (FloggyException e) {
		// Daniel - Problema de acesso ao banco ocorre com frequencia,
	    System.gc();
	    Util.mostrarErro(":pesquisarPrimeiroObjeto: Erro na pesquisa", e);
	}
	return objeto;
    }

    public static void deleteRecordStore() {

	String[] records = RecordStore.listRecordStores();

	for (int i = 0; i < records.length; i++) {
	    if (records[i].equals("FloggyProperties")) {
		continue;
	    }

	    RecordStore rs = null;

	    try {
		rs = RecordStore.openRecordStore(records[i], false);

		// Tentamos apagar, se ainda estiver aberto, tentamos de novo
		// até conseguir
		while (true) {
		    try {
			RecordStore.deleteRecordStore(records[i]);
			break;
		    } catch (RecordStoreException e) {
			try {
			    rs.closeRecordStore();
			} catch (RecordStoreNotFoundException e1) {
			} catch (RecordStoreException e1) {
			}
		    }
		}
	    } catch (RecordStoreNotFoundException e) {
	    	Util.mostrarErro("Erro ao excluir!");
	    } catch (RecordStoreException e) {
	    	Util.mostrarErro("Erro ao excluir!");
	    }
	}
    }

    public double calculoImoveisVisitados() {
	double imoveisVisitados = 0;
	// System.out.println("Calculo"+100 *
	// ControladorImoveis.getInstancia().getContadorVisitados() /
	// Configuracao.getInstancia().getQtdImoveis()
	// );
	if (Configuracao.getInstancia().getContadorVisitados() == 0) {
	    imoveisVisitados = 0;
	} else {
	    // imoveisVisitados = 100 *
	    // ControladorImoveis.getInstancia().getContadorVisitados() /
	    // Configuracao.getInstancia().getQtdImoveis();
	    imoveisVisitados = Configuracao.getInstancia().getContadorVisitados()
		    / Configuracao.getInstancia().getQtdImoveis();
	}
	return imoveisVisitados;
    }

    public int calculoImoveisVisitar() {
	int imoveisVisitar = 0;
	
	if (Configuracao.getInstancia().getIdsImoveisPendentes().size() == 0) {
	    imoveisVisitar = Configuracao.getInstancia().getQtdImoveis();
	} else {
	    System.out.println("Qtde Imoveis: " + Configuracao.getInstancia().getQtdImoveis());
	    System.out.println("Visitados calcular:"
		    + Configuracao.getInstancia().getContadorVisitados());
	    imoveisVisitar = Configuracao.getInstancia().getQtdImoveis() - Configuracao.getInstancia().getIdsImoveisConcluidos().size();
	    System.out.println("A visitar:" + imoveisVisitar);
	}
	return imoveisVisitar;
    }

    public double porcentagemImoveisVisitadosAnormalidade() {
	double imoveisVisitadosAnormalidade = 0;
	
	double qtdImoveis = Configuracao.getInstancia().getQtdImoveis();

	int imoveisLidosAnormalidade = Integer.parseInt(DadosRelatorio.getInstancia().valoresRelatorio.substring(7, 11));
	
	if (imoveisLidosAnormalidade == 0) {
	    imoveisVisitadosAnormalidade = 0;
	} else {
	    imoveisVisitadosAnormalidade = imoveisLidosAnormalidade / qtdImoveis;
	    imoveisVisitadosAnormalidade = imoveisVisitadosAnormalidade * 100;
	}
	imoveisVisitadosAnormalidade = Util.arredondar(
		imoveisVisitadosAnormalidade, 2);
	
	return imoveisVisitadosAnormalidade;
    }

    public double porcentagemImoveisVisitadosSemAnormalidade() {
	double imoveisVisitadosSemAnormalidade = 0;
	DadosRelatorio dadosRelatorio = DadosRelatorio.getInstancia();
	
	double qtdImoveis = Configuracao.getInstancia().getQtdImoveis();
	int imoveisLidosSemAnormalidade = Integer.parseInt(dadosRelatorio.valoresRelatorio.substring(1, 5));
	
	if (imoveisLidosSemAnormalidade == 0) {
	    imoveisVisitadosSemAnormalidade = 0;
	} else {
	    imoveisVisitadosSemAnormalidade = 100
		    * imoveisLidosSemAnormalidade
		    / qtdImoveis;
	    // imoveisVisitados =
	    // ControladorImoveis.getInstancia().getContadorVisitados() /
	    // Configuracao.getInstancia().getQtdImoveis();
	}
	imoveisVisitadosSemAnormalidade = Util.arredondar(
		imoveisVisitadosSemAnormalidade, 2);
	
	return imoveisVisitadosSemAnormalidade;
    }

    public double porcentagemImoveisVisitar() {
	double imoveisVisitar = 0;
	DadosRelatorio dadosRelatorio = DadosRelatorio.getInstancia();
	
	double qtdImoveis = Configuracao.getInstancia().getQtdImoveis();
	int imoveisVisitados = dadosRelatorio.idsLidosRelatorio.size();
	
	if (imoveisVisitados == 0) {
	    imoveisVisitar = 100;
	} else {
	    imoveisVisitar = qtdImoveis - imoveisVisitados;
	    imoveisVisitar = imoveisVisitar / qtdImoveis;
	    imoveisVisitar = 100 * imoveisVisitar;
	    imoveisVisitar = Util.arredondar(imoveisVisitar, 2);
	}
	return imoveisVisitar;
    }


    public byte getExisteRoteiroEmAndamento() {
	return existeRoteiroEmAndamento;
    }

    public void setExisteRoteiroEmAndamento(byte existeRoteiroEmAndamento) {
	this.existeRoteiroEmAndamento = existeRoteiroEmAndamento;
    }

    public static Vector consultarImoveis(int tipoPesquisa, String chave) {
//	PersistableManager pm = PersistableManager.getInstance();
	chave = chave.toUpperCase();
	Vector retorno = new Vector();
	try {
	    ImovelConta imovel = new ImovelConta();
	    if (tipoPesquisa == FiltroImovel.PESQUISAR_POR_MATRICULA) {
// Daniel	    	
//			int id = indiceMatriculaImovel.lookup(Integer.parseInt(chave));
	    	BinaryTreeItem item = indiceMatriculaImovel.lookupNode(
	    			new BinaryTreeItem(Integer.parseInt(chave), -1));
	    	if (item != null){
	    		if (item.getId() != -1) {
	    			PersistableManager.getInstance().load(imovel, (item.getId()), false);
	    			retorno.addElement(imovel);
	    		}
	    	}
	    } else if (tipoPesquisa == FiltroImovel.PESQUISAR_POR_NUMERO_HIDROMETRO) {
			int id = indiceNumeroHidrometroImovel.lookup(Util.convertendoLetraParaNumeros(chave));
//	    	BinaryTreeItem item = indiceNumeroHidrometroImovel.lookupNode(
//	    			new BinaryTreeItem(Util.convertendoLetraParaNumeros(chave), -1));
		if (id != -1) {
		    PersistableManager.getInstance().load(imovel, id, false);
		    retorno.addElement(imovel);
		}
	    } else if (tipoPesquisa == FiltroImovel.PESQUISAR_POR_QUADRA) {
		BinaryTreeItem item = indiceQuadraImovel
			.lookupNode(new BinaryTreeItem(Integer.parseInt(chave),
				-1));
		if (item != null) {
		    int[] ids = item.getIds();
		    for (int i = 0; i < ids.length; i++) {
			imovel = new ImovelConta();
			PersistableManager.getInstance().load(imovel, ids[i], false);
			retorno.addElement(imovel);
		    }
		}
		/*
		 * ObjectSet os = PersistableManager.getInstance().find( ImovelConta.class , new
		 * FiltroImovelQuadra( Integer.parseInt( chave ) ), null ); for
		 * ( int j = 0; j < os.size(); j++ ){ retorno.addElement(
		 * os.get( j ) ); }
		 */
	    } else if (tipoPesquisa == FiltroImovel.PESQUISAR_POR_SEQUENCIAL_ROTA) {
		BinaryTreeItem item = indiceSequencialRotaImovel
			.lookupNode(new BinaryTreeItem(Integer.parseInt(chave),
				-1));

		if (item != null) {
		    int[] ids = item.getIds();
		    for (int i = 0; i < ids.length; i++) {
			imovel = new ImovelConta();
			PersistableManager.getInstance().load(imovel, ids[i], false);
			retorno.addElement(imovel);
		    }
		}
	    }
	} catch (FloggyException e) {
	    System.gc();
	    Util.mostrarErro("Erro na pesquisa", e);
	}
	return retorno;
    }

    public static ImovelConta proximoImovelNaoLido() {
	int comercarDe = ControladorImoveis.getInstancia()
		.getImovelSelecionado().getId() + 1;
	int qtdImoveis = Configuracao.getInstancia().getQtdImoveis();
	ImovelConta imovel = null;
	try {
	    for (int i = comercarDe; i <= qtdImoveis; i++) {
		imovel = new ImovelConta();
		PersistableManager.getInstance().load(imovel, i, true);
		if (imovel.getIndcImovelCalculado() == Constantes.NAO) {
		    imovel = new ImovelConta();
		    PersistableManager.getInstance().load(imovel, i, false);
		    break;
		}
		imovel = null;
	    }
	    if (imovel == null) {
		for (int i = 1; i < comercarDe; i++) {
		    imovel = new ImovelConta();
		    PersistableManager.getInstance().load(imovel, i, true);
		    if (imovel.getIndcImovelCalculado() == Constantes.NAO) {
			imovel = new ImovelConta();
			PersistableManager.getInstance().load(imovel, i, false);
			break;
		    }
		    imovel = null;
		}
	    }
	} catch (FloggyException e) {
	    System.gc();
	    Util.mostrarErro("Erro ao pesquisar próximo não lido");
	    e.printStackTrace();
	}
	return imovel;
    }

    /**
     * Exclue um objeto salvo anteriormente
     * 
     * @param objeto
     */
    public static void apagarObjeto(Persistable objeto) {
	 	try {
			PersistableManager.getInstance().delete(objeto);
	 	} catch (FloggyException e) {
	 	// Daniel - Problema de acesso ao banco ocorre com frequencia,
		    System.gc();
		    Util.mostrarErro("Erro tentando apagar o objeto:", e);
	 	}
    }
}

