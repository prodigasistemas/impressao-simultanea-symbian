package com.ipad.basic;

import java.util.Vector;

import net.sourceforge.floggy.persistence.ObjectSet;
import net.sourceforge.floggy.persistence.Persistable;

import com.ipad.io.Repositorio;
import com.ipad.util.ParserUtil;
import com.ipad.util.Util;

/**
 * 
 * @author Breno Santos
 * 
 */

public class ImovelReg13 extends Registro implements Persistable {

    private static ImovelReg13 instancia;
    private int id;

    // Reg13
    private int idConsumoAnormalidade;
    private String mensagemConta;
    private Vector registros13;

    public ImovelReg13() {

    }

    public ImovelReg13(String linhaArquivo) {

	// Pulamos a matricula do imovel
	// Util.resumoMemoria();

	ParserUtil parser = new ParserUtil(linhaArquivo);

	// Tipo de registro
	parser.obterDadoParser(02);

	this.setIdConsumoAnormalidade(parser.obterDadoParser(2));
	this.setMensagemConta(parser.obterDadoParser(120));
	// Util.resumoMemoria();
    }

    public int getIdConsumoAnormalidade() {
	return idConsumoAnormalidade;
    }

    public void setIdConsumoAnormalidade(String idConsumoAnormalidade) {
	this.idConsumoAnormalidade = Util
		.verificarNuloInt(idConsumoAnormalidade);
    }

    public String getMensagemConta() {
	return mensagemConta;
    }

    public void setMensagemConta(String mensagemConta) {
	this.mensagemConta = Util.verificarNuloString(mensagemConta);
    }

    public void adicionaRegistro13(ImovelReg13 reg13) {
	if (this.registros13 == null) {
	    this.registros13 = new Vector();
	}

	this.registros13.addElement(reg13);
	Repositorio.salvarObjetos(this.registros13);
    }

    public ImovelReg13 getRegistro13(int id) {
	ImovelReg13 registro13 = null;
	int tamanho = 0;
	if (registros13 != null && !registros13.isEmpty()){
		tamanho = registros13.size();
	}

	for (int i = 0; i < tamanho; i++) {
	    ImovelReg13 reg3 = (ImovelReg13) registros13.elementAt(i);
	    int idReg13 = reg3.getIdConsumoAnormalidade();

	    if (id == idReg13) {
		registro13 = (ImovelReg13) registros13.elementAt(i);
	    }
	}
	return registro13;
    }

    public static ImovelReg13 getInstancia() {

	if (ImovelReg13.instancia == null) {
	    ImovelReg13.instancia = new ImovelReg13();
	}

	return ImovelReg13.instancia;
    }
    
    public void carregarRegistros13(ObjectSet registros) {

	this.registros13 =  new Vector();
	int tamanho = registros.size();

	for (int i = 1; i <= tamanho; i++) {
	    ImovelReg13 imovelReg13 = new ImovelReg13();
	    Repositorio.carregarObjeto(imovelReg13, i);
	    this.registros13.addElement(imovelReg13);
	}
    }

    public void setId(int arg0) {
	this.id = arg0;
    }

    public int getId() {
	return this.id;
    }

}
