package com.ipad.basic;

import java.util.Date;
import net.sourceforge.floggy.persistence.IDable;
import net.sourceforge.floggy.persistence.Persistable;
import com.ipad.io.Repositorio;
import com.ipad.util.Constantes;
import com.ipad.util.ParserUtil;
import com.ipad.util.Util;

public class ImovelReg11 implements Persistable, IDable {

    private String tipoRegistro;
    private Date dataReferenciaArrecadacao;
    private String anoMesFaturamento;
    private String codigoEmpresaFebraban;
    private String telefone0800;
    private String cnpjEmpresa;
    private String inscricaoEstadualEmpresa;
    private double valorMinimEmissaoConta;
    private double percentToleranciaRateio;
    private int decrementoMaximoConsumoEconomia;
    private int incrementoMaximoConsumoEconomia;
    private short indcTarifaCatgoria;
    private String login;
    private String senha;

    private Date dataAjusteLeitura;
    private int indicadorAjusteConsumo;
    private int indicadorTransmissaoOffline;
    private String versaoCelular;
    private short indcAtualizarSequencialRota;

    private short indcBloquearReemissaoConta;
    
    private int qtdDiasAjusteConsumo;

    // Daniel - indicador Rota Dividida
    private int indicadorRotaDividida = 999999;

    // Daniel - id da Rota
    private int idRota = 9999;

    // Daniel - Indicador de calculo de consumo pela médio do HIDROMETRO.
    private int indicadorCalculoPelaMedia = Constantes.NAO;

    private int moduloVerificadorCodigoBarras;

    private Date dataInicio;
    
    private Date dataFim;
    
    private int indcOpcaoTornarOffline = Constantes.NAO;


    public short getIndcBloquearReemissaoConta() {
	return indcBloquearReemissaoConta;
    }

    public void setIndcBloquearReemissaoConta(String indcBloquearReemissaoConta) {
	this.indcBloquearReemissaoConta = Util
		.verificarNuloShort(indcBloquearReemissaoConta);
    }

    public static final short CALCULO_POR_CATEGORA = 1;

    private static ImovelReg11 instancia;

    private int id;

    public ImovelReg11(String linhaArquivo) {

	Util.resumoMemoria();

	ParserUtil parser = new ParserUtil(linhaArquivo);

	Integer.valueOf(parser.obterDadoParser(2)).intValue();

	this.setCodigoEmpresaFebraban(parser.obterDadoParser(4));

	this.setDataReferenciaArrecadacao(parser.obterDadoParser(8));

	this.setAnoMesFaturamento(parser.obterDadoParser(6));

	this.setTelefone0800(parser.obterDadoParser(12));

	this.setCnpjEmpresa(parser.obterDadoParser(14));

	this.setInscricaoEstadualEmpresa(parser.obterDadoParser(20));

	this.setValorMinimEmissaoConta(parser.obterDadoParser(14));

	this.setPercentToleranciaRateio(parser.obterDadoParser(4));

	this.setDecrementoMaximoConsumoEconomia(parser.obterDadoParser(6));

	this.setIncrementoMaximoConsumoEconomia(parser.obterDadoParser(6));

	this.setIndcTarifaCatgoria(parser.obterDadoParser(1));

	this.setLogin(parser.obterDadoParser(11));

	this.setSenha(parser.obterDadoParser(40));

	this.setDataAjusteLeitura(parser.obterDadoParser(8));

	this.setIndicadorAjusteConsumo(parser.obterDadoParser(1));

	this.setIndicadorTransmissaoOffline(parser.obterDadoParser(1));

	this.setVersaoCelular(parser.obterDadoParser(10));

	// Criado apenas para testes, deve ser descomentado assim que a
	// parâmetro comecar a vir
	this.setIndcBloquearReemissaoConta(parser.obterDadoParser(1));
	
	// Indica se a rota está passivel de ser rota de marcação	
	this.setIndcAtualizarSequencialRota( parser.obterDadoParser(1) );

	this.setQtdDiasAjusteConsumo( parser.obterDadoParser(2) );

	//indica se o codigo de barras da conta utilizará o módulo 10 ou 11
	this.setModuloVerificadorCodigoBarras(parser.obterDadoParser(2));

	this.setDataInicio( parser.obterDadoParser(8));
	
	this.setDataFim( parser.obterDadoParser(8));

// Daniel set Id da rota
	this.setIdRota( parser.obterDadoParser(4));
	
// Daniel - indicador Rota Dividida
// Indica se a rota foi dividida e contém o numero de sub rotas.	
	this.setIndicadorRotaDividida( parser.obterDadoParser(2) );

	if(linhaArquivo.length() == 191){
    	this.setIdCalculoMedia( parser.obterDadoParser(1) );
    }
    }

    public ImovelReg11() {
	super();
    }

    public String getTipoRegistro() {
	return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
	this.tipoRegistro = tipoRegistro;
    }

    public Date getDataReferenciaArrecadacao() {
	return dataReferenciaArrecadacao;
    }

    public void setDataReferenciaArrecadacao(String dataReferenciaArrecadacao) {
	this.dataReferenciaArrecadacao = Util.getData(Util
		.verificarNuloString(dataReferenciaArrecadacao));
    }
    
    public String getAnoMesFaturamento() {
    	return anoMesFaturamento;
        }

    public void setAnoMesFaturamento(String anoMesFaturamento) {
    	this.anoMesFaturamento = Util.verificarNuloString(anoMesFaturamento);
    }

    public String getCodigoEmpresaFebraban() {
	return codigoEmpresaFebraban;
    }

    public void setCodigoEmpresaFebraban(String codigoEmpresaFebraban) {
	this.codigoEmpresaFebraban = codigoEmpresaFebraban;
    }

    public String getTelefone0800() {
	return telefone0800;
    }

    public void setTelefone0800(String telefone0800) {
	this.telefone0800 = telefone0800;
    }

    public String getCnpjEmpresa() {
	return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
	this.cnpjEmpresa = cnpjEmpresa;
    }

    public String getInscricaoEstadualEmpresa() {
	return inscricaoEstadualEmpresa;
    }

    public void setInscricaoEstadualEmpresa(String inscricaoEstadualEmpresa) {
	this.inscricaoEstadualEmpresa = inscricaoEstadualEmpresa;
    }

    public double getValorMinimEmissaoConta() {
	return valorMinimEmissaoConta;
    }

    public void setValorMinimEmissaoConta(String valorMinimEmissaoConta) {
	this.valorMinimEmissaoConta = Util
		.verificarNuloDouble(valorMinimEmissaoConta);
    }

    public double getPercentToleranciaRateio() {
	return percentToleranciaRateio;
    }

    public void setPercentToleranciaRateio(String percentToleranciaRateio) {
	this.percentToleranciaRateio = Util
		.verificarNuloDouble(percentToleranciaRateio);
    }

    public int getDecrementoMaximoConsumoEconomia() {
	return decrementoMaximoConsumoEconomia;
    }

    public void setDecrementoMaximoConsumoEconomia(
	    String decrementoMaximoConsumoEconomia) {
	this.decrementoMaximoConsumoEconomia = Util
		.verificarNuloInt(decrementoMaximoConsumoEconomia);
    }

    public int getIncrementoMaximoConsumoEconomia() {
	return incrementoMaximoConsumoEconomia;
    }

    public void setIncrementoMaximoConsumoEconomia(
	    String incrementoMaximoConsumoEconomia) {
	this.incrementoMaximoConsumoEconomia = Util
		.verificarNuloInt(incrementoMaximoConsumoEconomia);
    }

    public short getIndcTarifaCatgoria() {
	return indcTarifaCatgoria;
    }

    public void setIndcTarifaCatgoria(String indcTarifaCatgoria) {
	this.indcTarifaCatgoria = Util.verificarNuloShort(indcTarifaCatgoria);
    }

    public String getRecordStoreName() {

	return "imovelReg11";
    }

    public void setId(int id) {

	this.id = id;
    }

    public int getId() {
	return id;
    }

    public void adicionaRegistro11(ImovelReg11 reg11) {

	ImovelReg11.instancia = reg11;
	Repositorio.salvarObjeto(ImovelReg11.instancia);

    }

    public ImovelReg11 getRegistro11() {

	return ImovelReg11.instancia;

    }

    public static ImovelReg11 getInstancia() {

	if (ImovelReg11.instancia == null) {
	    ImovelReg11.instancia = (ImovelReg11) Repositorio
		    .pesquisarPrimeiroObjeto(ImovelReg11.class);

	    if (instancia == null) {
		instancia = new ImovelReg11();
	    }
	}

	return ImovelReg11.instancia;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login.trim();
    }

    public String getSenha() {
	return senha;
    }

    public void setSenha(String senha) {
	if (senha != null)
	    this.senha = senha.trim();
	else
	    this.senha = senha;
    }

    public Date getDataAjusteLeitura() {
	return dataAjusteLeitura;
    }

    public void setDataAjusteLeitura(String dataAjusteLeitura) {
	this.dataAjusteLeitura = Util.getData(Util
		.verificarNuloString(dataAjusteLeitura));
    }

    public int getIndicadorAjusteConsumo() {
	return indicadorAjusteConsumo;
    }

    public void setIndicadorAjusteConsumo(String indicadorAjusteConsumo) {
	this.indicadorAjusteConsumo = Util
		.verificarNuloInt(indicadorAjusteConsumo);
    }

    public int getIndicadorTransmissaoOffline() {
	return indicadorTransmissaoOffline;
    }

    public void setIndicadorTransmissaoOffline(
	    String indicadorTransmissaoOffline) {
	this.indicadorTransmissaoOffline = Util
		.verificarNuloInt(indicadorTransmissaoOffline);
    }

    public String getVersaoCelular() {
	return versaoCelular;
    }

    public void setVersaoCelular(String versaoCelular) {
	this.versaoCelular = Util.verificarNuloString(versaoCelular);
    }

    public void setIndcAtualizarSequencialRota(String indcAtualizarSequencialRota) {
	this.indcAtualizarSequencialRota = Util.verificarNuloShort( indcAtualizarSequencialRota );
    }

    public short getIndcAtualizarSequencialRota() {
	return indcAtualizarSequencialRota;
    }

    public int getQtdDiasAjusteConsumo() {
        return qtdDiasAjusteConsumo;
    }

    public void setQtdDiasAjusteConsumo(String qtdDiasAjusteConsumo) {
	this.qtdDiasAjusteConsumo = Util.verificarNuloInt(qtdDiasAjusteConsumo);
    }

    // Daniel - get indicador Rota Dividida
    public int getIndicadorRotaDividida() {
        return indicadorRotaDividida;
    }

    // Daniel - set indicador Rota Dividida
    public void setIndicadorRotaDividida(String indicadorRotaDividida) {
	this.indicadorRotaDividida = Util.verificarNuloInt(indicadorRotaDividida);
    }

    // Daniel - get Id de Rota
    public int getIdRota() {
        return idRota;
    }

    // Daniel - set Id de Rota
    public void setIdRota(String idRota) {
	this.idRota = Util.verificarNuloInt(idRota);
    }

    // Daniel - get Indicador de calculo de consumo pela média do Hidrometro.
    public int getIdCalculoMedia() {
    	return indicadorCalculoPelaMedia;
    }

    // Daniel - set Indicador de calculo de consumo pela média do Hidrometro.
    public void setIdCalculoMedia(String indicadorCalculoPelaMedia) {
	this.indicadorCalculoPelaMedia = Util.verificarNuloInt(indicadorCalculoPelaMedia);
    }
   
    public int getModuloVerificadorCodigoBarras() {
        return moduloVerificadorCodigoBarras;
    }

    public void setModuloVerificadorCodigoBarras(String moduloVerificadorCodigoBarras) {
        this.moduloVerificadorCodigoBarras = Util.verificarNuloInt(moduloVerificadorCodigoBarras);
    }

    public Date getDataInicio() {
	return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
	this.dataInicio = Util.getData(Util.verificarNuloString(dataInicio));
    }
	
    public Date getDataFim() {
	return dataFim;
    }

    public void setDataFim(String dataFim) {
	this.dataFim = Util.getData(Util.verificarNuloString(dataFim));
    }

    public int getIndcOpcaoTornarOffline() {
        return indcOpcaoTornarOffline;
    }

    public void setIndcOpcaoTornarOffline(int indcOpcaoTornarOffline) {
        this.indcOpcaoTornarOffline = indcOpcaoTornarOffline;
    }
    
}
