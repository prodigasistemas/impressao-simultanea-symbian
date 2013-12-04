package com.ipad.basic;

import java.util.Vector;

import net.sourceforge.floggy.persistence.IDable;
import net.sourceforge.floggy.persistence.Persistable;

import com.ipad.io.Repositorio;
import com.ipad.util.ParserUtil;
import com.ipad.util.Util;

public class ImovelReg14 implements Persistable, IDable {

	private int codigo;
	private String descricao;
	private int indicadorLeitura;
	private int idConsummoACobrarComLeitura;
	private int idConsumoACobrarSemLeitura;
	private int idLeituraFaturarComLeitura;
	private int idLeituraFaturarSemLeitura;
	private int indcUso;
	private double numeroFatorSemLeitura;
	private double numeroFatorComLeitura;
	
	private Vector registros14;

	private static ImovelReg14 instancia;

	private int id;

	public ImovelReg14(String linhaArquivo) {

		Util.resumoMemoria();

		ParserUtil parser = new ParserUtil(linhaArquivo);

		Integer.valueOf(parser.obterDadoParser(2)).intValue();

		this.setCodigo(parser.obterDadoParser(3));
		
		this.setDescricao(parser.obterDadoParser(25));
		
		this.setIndicadorLeitura(parser.obterDadoParser(1));
		
		this.setIdConsumoACobrarComLeitura(parser.obterDadoParser(2));
		
		this.setIdConsumoACobrarSemLeitura(parser.obterDadoParser(2));
		
		this.setIdLeituraFaturarComLeitura(parser.obterDadoParser(2));
		
		this.setIdLeituraFaturarSemLeitura(parser.obterDadoParser(2));
		
		this.setIndcUso(parser.obterDadoParser(1));
		
		this.setNumeroFatorSemLeitura(parser.obterDadoParser(4));
		
		this.setNumeroFatorComLeitura(parser.obterDadoParser(4));
		

	}

	public ImovelReg14() {
		super();
	}

	public String getRecordStoreName() {

		return "imovelReg14";
	}

	public void setId(int id) {

		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public void adicionaRegistro14(ImovelReg14 reg14) {
		
	    if (this.registros14 == null) {
		    this.registros14 = new Vector();
		}

		this.registros14.addElement(reg14);
	}

/*	public void adicionaRegistro14(ImovelReg14 reg14) {

		ImovelReg14.instancia = reg14;
		Repositorio.salvarObjeto(ImovelReg14.instancia);
		
	}*/

	/*public ImovelReg14 getRegistro14() {
		
		return ImovelReg14.instancia;
		
	}*/

	public static ImovelReg14 getInstancia() {
	    
	    if (ImovelReg14.instancia == null) {
		    ImovelReg14.instancia = (ImovelReg14) Repositorio.pesquisarPrimeiroObjeto(ImovelReg14.class);

		    if (instancia == null) {
			instancia = new ImovelReg14();
		    }
		}

		return ImovelReg14.instancia;
	}

	public int getCodigo() {
	    return codigo;
	}

	public void setCodigo(String codigo) {
	    this.codigo = Util.verificarNuloInt(codigo);
	}

	public String getDescricao() {
	    return descricao;
	}

	public void setDescricao(String descricao) {
	    this.descricao = descricao;
	}

	public int getIndicadorLeitura() {
	    return indicadorLeitura;
	}

	public void setIndicadorLeitura(String indicadorLeitura) {
	    this.indicadorLeitura = Util.verificarNuloInt(indicadorLeitura);
	}

	public int getIdConsumoACobrarComLeitura() {
	    return idConsummoACobrarComLeitura;
	}

	public void setIdConsumoACobrarComLeitura(String idConsummoACobrarComLeitura) {
	    this.idConsummoACobrarComLeitura = Util.verificarNuloInt(idConsummoACobrarComLeitura);
	}

	public int getIdConsumoACobrarSemLeitura() {
	    return idConsumoACobrarSemLeitura;
	}

	public void setIdConsumoACobrarSemLeitura(String idConsumoACobrarSemLeitura) {
	    this.idConsumoACobrarSemLeitura = Util.verificarNuloInt(idConsumoACobrarSemLeitura);
	}

	public int getIdLeituraFaturarComLeitura() {
	    return idLeituraFaturarComLeitura;
	}

	public void setIdLeituraFaturarComLeitura(String idLeituraFaturarComLeitura) {
	    this.idLeituraFaturarComLeitura = Util.verificarNuloInt(idLeituraFaturarComLeitura);
	}

	public int getIdLeituraFaturarSemLeitura() {
	    return idLeituraFaturarSemLeitura;
	}

	public void setIdLeituraFaturarSemLeitura(String idLeituraFaturarSemLeitura) {
	    this.idLeituraFaturarSemLeitura = Util.verificarNuloInt(idLeituraFaturarSemLeitura);
	}

	public int getIndcUso() {
	    return indcUso;
	}

	public void setIndcUso(String indcUso) {
	    this.indcUso = Util.verificarNuloInt(indcUso);
	}

	public double getNumeroFatorSemLeitura() {
	    return numeroFatorSemLeitura;
	}

	public void setNumeroFatorSemLeitura(String numeroFatorSemLeitura) {
	    this.numeroFatorSemLeitura = Util.verificarNuloDouble(numeroFatorSemLeitura);
	}

	public double getNumeroFatorComLeitura() {
	    return numeroFatorComLeitura;
	}

	public void setNumeroFatorComLeitura(String numeroFatorComLeitura) {
	    this.numeroFatorComLeitura = Util.verificarNuloDouble(numeroFatorComLeitura);
	}

	public Vector getRegistros14() {
	    return registros14;
	}

	public void setRegistros14(Vector registros14) {
	    this.registros14 = registros14;
	}


}
