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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.StreamConnection;

import com.ipad.basic.Configuracao;
import com.ipad.basic.Consumo;
import com.ipad.basic.DadosRelatorio;
import com.ipad.basic.ImovelConta;
import com.ipad.basic.ImovelReg11;
import com.ipad.business.ControladorImoveis;
import com.ipad.facade.Fachada;
import com.ipad.facade.FachadaRede;
import com.ipad.io.ArquivoRetorno;
import com.ipad.io.FileManager;
import com.ipad.io.Repositorio;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BoxLayout;

/**
 * @author Rafael Palermo de Araújo
 */
public class Util {
    
    private static int repetirPergunta = 0;
    

    /**
     * Verifica se o valor da String.trim() veio como null ou como
     * Constantes.NULO_STRING, setando como Constantes.NULO_DOUBLE caso
     * verdadeiro
     * 
     * @param valor
     * @return
     */
    public static double verificarNuloDouble(String valor) {
	if (valor == null || valor.trim().equals(Constantes.NULO_STRING)) {
	    return Constantes.NULO_DOUBLE;
	} else {
	    return Double.parseDouble(valor.trim());
	}
    }

    /**
     * Verifica se o valor da String.trim() veio como null ou como
     * Constantes.NULO_STRING, setando como Constantes.NULO_STRING caso
     * verdadadeiro
     * 
     * @param valor
     * @return
     */
    public static String verificarNuloString(String valor) {
	if (valor == null || valor.trim().equals(Constantes.NULO_STRING) || valor.trim().equals("null")) {
	    return Constantes.NULO_STRING;
	} else {
	    return valor.trim();
	}
    }

    /**
     * Verifica se o valor da String.trim() veio como null ou como
     * Constantes.NULO_STRING, setando como Constantes.NULO_INT caso
     * verdadadeiro
     * 
     * @param valor
     * @return
     */
    public static int verificarNuloInt(String valor) {
	if (valor == null || valor.trim().equals(Constantes.NULO_STRING)) {
	    return Constantes.NULO_INT;
	} else {
	    return Integer.parseInt(valor.trim());
	}
    }

    /**
     * Verifica se o valor da String.trim() veio como null ou como
     * Constantes.NULO_STRING, setando como Constantes.NULO_INT caso
     * verdadadeiro
     * 
     * @param valor
     * @return
     */
    public static short verificarNuloShort(String valor) {
	if (valor == null || valor.trim().equals(Constantes.NULO_STRING)) {
	    return Constantes.NULO_SHORT;
	} else {
	    return Short.parseShort(valor.trim());
	}
    }

    public static void resumoMemoria() {
	// System.out.println( "" );
	// System.out.println(
	// "*******************************************************" );
	// System.out.println( "Memoria Usada: " +
	// Runtime.getRuntime().freeMemory() );
	// System.out.println( "Total Disponivel: " +
	// Runtime.getRuntime().totalMemory() );
	// System.out.println( "% Uso da memoria: " + ( (
	// Runtime.getRuntime().freeMemory() /
	// Runtime.getRuntime().totalMemory() ) * 100 ) );
	// System.out.println(
	// "*******************************************************" );
	// System.out.println( "" );
    }

    public static String encrypt(String str) {
	int tab[] = { 77, 110, 70, 114, 90, 100, 86, 103, 111, 75 };
	int i;
	int value = 0;
	int len = str.length();

	String response = "";

	for (i = 0; i < len; i++) {
	    value = (int) str.charAt(i);
	    response += (char) tab[(value - 48)];
	}

	return response;
    }

    public static String decrypt(String str) {
	int tab[] = { 77, 110, 70, 114, 90, 100, 86, 103, 111, 75 };
	int i;
	int j;
	int value = 0;
	int len = str.length();
	String response = "";

	for (i = 0; i < len; i++) {
	    value = (int) str.charAt(i);
	    for (j = 0; j < 10; j++) {
		if (value == tab[j]) {
		    response += String.valueOf(j).trim();
		}
	    }
	}
	return response;
    }

    /**
     * Método responsável por transformar um vetor de parâmetros em uma mensagem
     * um array de bytes.
     * 
     * @param parameters
     *            Vetor de parâmetros.
     * @return O array de bytes com os parâmetros empacotados.
     */
    public static byte[] empacotarParametros(Vector parametros) {

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	DataOutputStream dos = new DataOutputStream(baos);

	byte[] resposta = null;

	parametros.trimToSize();

	try {
	    // escreve os dados no OutputStream

	    if (parametros != null) {
		int tamanho = parametros.size();
		for (int i = 0; i < tamanho; i++) {
		    Object param = parametros.elementAt(i);
		    if (param instanceof Byte) {
			dos.writeByte(((Byte) param).byteValue());
		    } else if (param instanceof Integer) {
			dos.writeInt(((Integer) param).intValue());
		    } else if (param instanceof Long) {
			dos.writeLong(((Long) param).longValue());
		    } else if (param instanceof String) {
			dos.writeUTF((String) param);
		    } else if (param instanceof byte[]) {
			dos.write((byte[]) param);
		    }
		}
	    }

	    // pega os dados enpacotados
	    resposta = baos.toByteArray();

	    if (dos != null) {
		dos.close();
		dos = null;
	    }
	    if (baos != null) {
		baos.close();
		baos = null;
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	// retorna o array de bytes
	return resposta;
    }

    public static Date getData(String data) {

	if (data.equals(Constantes.NULO_STRING)) {
	    return null;
	} else {
	    Calendar calendario = Calendar.getInstance();
	    calendario.set(Calendar.YEAR, Integer.valueOf(data.substring(0, 4)).intValue());
	    calendario.set(Calendar.MONTH, Integer.valueOf(data.substring(4, 6)).intValue() - 1);
	    calendario.set(Calendar.DAY_OF_MONTH, Integer.valueOf(data.substring(6, 8)).intValue());
	    calendario.set(Calendar.HOUR, 0);
	    calendario.set(Calendar.HOUR_OF_DAY, 0);
	    calendario.set(Calendar.MINUTE, 0);
	    calendario.set(Calendar.SECOND, 0);
	    calendario.set(Calendar.MILLISECOND, 0);

	    return new Date(calendario.getTime().getTime());
	}

    }

    public static double strToDouble(String str) {
	StringBuffer buffer = new StringBuffer();
	buffer.append(str);
	buffer.insert(str.length() - 2, '.');

	return Double.valueOf(buffer.toString()).doubleValue();
    }

    public static int pow(int arg, int times) {
	int ret = 1;
	for (int i = 1; i <= times; i++) {
	    ret = ret * arg;
	}
	return ret;
    }

    /**
     * Arredonda as casas decimais.
     * 
     * @param valor
     *            O valor a ser arrendondado.
     * @param casasDecimais
     *            O número de casas decimais.
     * @return O valor arredondado. Ex.: arredondar(abcd.xyzw, 3) = abcd.xyz,
     *         para w <5 = abcd.xyk, para w >= 5 e k = z + 1
     */
    public static double arredondar(double valor, int casasDecimais) {

	int mult = pow(10, casasDecimais);
	valor = (double) valor * mult;
	int inteiro = (int) Math.floor(valor + 0.5);
	valor = (double) inteiro / mult;
	return valor;
    }

    public static int convertArrayByteToInt(byte[] b) {
	int i = 0;
	i += unsignedByteToInt(b[0]) << 24;
	i += unsignedByteToInt(b[1]) << 16;
	i += unsignedByteToInt(b[2]) << 8;
	i += unsignedByteToInt(b[3]) << 0;
	return i;

    }

    /**
     * [UC0631] Processar requisições do Dispositivo móvel. Método auxiliar para
     * converter byte em int.
     * 
     * @author Thiago Nascimento
     * @date 14/08/2007
     * @param b
     * @return
     */
    public static int unsignedByteToInt(byte b) {
	return (int) b & 0xFF;
    }

    /**
     * Diferença entre datas em dias
     * 
     * @param data1
     * @param data2
     * @return
     */
    public static long obterModuloDiferencasDatasDias(Date data1, Date data2) {
	long dias;
	long umDia = 86400000; // um dia possui 86400000ms
	long date1, date2;
	date1 = data1.getTime();
	date2 = data2.getTime();
	dias = (date2 - date1) / umDia;
	return (dias < 0) ? dias * -1 : dias;
    }

    /**
     * Diferença entre datas em dias
     * 
     * @param data1
     * @param data2
     * @return
     */
    public static long obterDiferencasDatasDias(Date data1, Date data2) {
	long dias;
	long umDia = 86400000; // um dia possui 86400000ms
	long date1, date2;
	date1 = data1.getTime();
	date2 = data2.getTime();
	dias = (date2 - date1) / umDia;
	return dias;
    }

    /**
     * Subtrai a data no formato AAAAMM Exemplo 200508 retorna 200507 Author:
     * Sávio Luiz Data: 20/01/2006
     * 
     * @param data
     *            com a barra
     * @return Uma data no formato yyyyMM (sem a barra)
     */
    public static int subtrairMesDoAnoMes(int anoMes, int qtdMeses) {

	String dataFormatacao = "" + anoMes;

	int ano = Integer.parseInt(dataFormatacao.substring(0, 4));
	int mes = Integer.parseInt(dataFormatacao.substring(4, 6));

	int mesTemp = (mes - qtdMeses);

	if (mesTemp <= 0) {
	    mesTemp = (12 + mes) - qtdMeses;
	    ano = ano - 1;
	}

	String anoMesSubtraido = null;
	String tamanhoMes = "" + mesTemp;

	if (tamanhoMes.length() == 1) {
	    anoMesSubtraido = ano + "0" + mesTemp;
	} else {
	    anoMesSubtraido = ano + "" + mesTemp;
	}
	return Integer.parseInt(anoMesSubtraido);
    }

    public static int obterMes(int anoMes) {

	String dataFormatacao = "" + anoMes;

	int mes = Integer.parseInt(dataFormatacao.substring(4, 6));

	return mes;
    }

    public static int obterAno(int anoMes) {

	String dataFormatacao = "" + anoMes;

	int ano = Integer.parseInt(dataFormatacao.substring(0, 4));

	return ano;
    }

    /**
     * Método que recebe um java.util.Date e retorna uma String no formato
     * dia/mês/ano.
     * 
     * @param date
     *            Data a ser formatada.
     * @return String no formato dia/mês/ano.
     */
    public static String dateToString(Date date) {
	StringBuffer retorno = new StringBuffer();

	if (date != null && !date.equals("")) {

	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    // Dia
	    retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.DAY_OF_MONTH) + ""));
	    retorno.append("/");
	    // Mes
	    retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.MONTH) + 1 + ""));
	    // Ano
	    retorno.append("/");
	    retorno.append(calendar.get(Calendar.YEAR));
	}

	return retorno.toString();
    }

    /**
     * Método que recebe um java.util.Date e retorna uma String no formato
     * Hora:Minuto:Segundo.
     * 
     * @param date
     *            Data a ser formatada.
     * @return String no formato Hora:Minuto:Segundo.
     */
    public static String dateToHoraString(Date date) {
	StringBuffer retorno = new StringBuffer();

	if (date != null && !date.equals("")) {

	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    // Hora
	    retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.HOUR_OF_DAY) + ""));
	    retorno.append(":");
	    // Minuto
	    retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.MINUTE) + 1 + ""));
	    // Segundo
	    retorno.append(":");
	    retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.SECOND) + ""));
	}

	return retorno.toString();
    }

    /**
     * Compara duas datas sem verificar a hora.
     * 
     * @param data1
     *            A primeira data
     * @param data2
     *            A segunda data
     * @author Rafael Francisco Pinto
     * @return -1 se a data1 for menor que a data2, 0 se as datas forem iguais, 1
     *         se a data1 for maior que a data2.
     */
    public static int compararData(Date data1, Date data2) {

	Calendar calendar1;
	Calendar calendar2;

	int ano1;
	int ano2;
	int mes1;
	int mes2;
	int dia1;
	int dia2;

	int resultado;

	calendar1 = Calendar.getInstance();
	calendar1.setTime(data1);

	ano1 = calendar1.get(Calendar.YEAR);
	mes1 = calendar1.get(Calendar.MONTH);
	dia1 = calendar1.get(Calendar.DAY_OF_MONTH);

	calendar2 = Calendar.getInstance();
	calendar2.setTime(data2);

	ano2 = calendar2.get(Calendar.YEAR);
	mes2 = calendar2.get(Calendar.MONTH);
	dia2 = calendar2.get(Calendar.DAY_OF_MONTH);

	if (ano1 == ano2) {

	    if (mes1 == mes2) {

		if (dia1 == dia2) {
		    resultado = 0;
		} else if (dia1 < dia2) {
		    resultado = -1;
		} else {
		    resultado = 1;
		}
	    } else if (mes1 < mes2) {
		resultado = -1;
	    } else {
		resultado = 1;
	    }
	} else if (ano1 < ano2) {
	    resultado = -1;
	} else {
	    resultado = 1;
	}
	return resultado;
    }

    /**
     * Retorna a descrição abreviada do ano Mes
     * 
     * @param anoMes
     * @author Rafael Francisco Pinto
     */

    public static String retornaDescricaoAnoMes(String anoMes) {

	int mes = Integer.parseInt(anoMes.substring(4, 6));
	String ano = anoMes.substring(0, 4);

	String descricao = retornaDescricaoMes(mes) + "/" + ano;

	return descricao;
    }

    /**
     * Retorna a descrição do mes
     * 
     * @param mes
     * @author Rafael Francisco Pinto
     * @return a descrição do mês
     */

    public static String retornaDescricaoMes(int mes) {

	String meses[] = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" };

	String mesPorExtenso = meses[mes - 1];// mes-1 pq o indice do array
	// começa no zero

	return mesPorExtenso;
    }

    /**
     * Retorna o valor de cnpjFormatado
     * 
     * @return O valor de cnpjFormatado
     */
    public static String formatarCnpj(String cnpj) {
	String cnpjFormatado = cnpj;
	String zeros = "";

	if (cnpjFormatado != null) {

	    for (int a = 0; a < (14 - cnpjFormatado.length()); a++) {
		zeros = zeros.concat("0");
	    }
	    // concatena os zeros ao numero
	    // caso o numero seja diferente de nulo
	    cnpjFormatado = zeros.concat(cnpjFormatado);

	    cnpjFormatado = cnpjFormatado.substring(0, 2) + "." + cnpjFormatado.substring(2, 5) + "." + cnpjFormatado.substring(5, 8) + "/" + cnpjFormatado.substring(8, 12) + "-" + cnpjFormatado.substring(12, 14);
	}

	return cnpjFormatado;
    }

    /**
     * Converte um valor double para uma string formatada
     * 
     * @param valor
     *            valor a ser formatado
     * @return String formatada
     */
    public static String formatarDoubleParaMoedaReal(double d) {
	double valorArredondado = Util.arredondar(d, 2);
	int inteiro = (int) valorArredondado;
	double decimal = Util.arredondar(valorArredondado - (int) inteiro, 2);

	String inteiroString = inteiro + "";
	int contador = 0;
	String comPontoInvertido = "";

	// Agrupamos de 3 em 3
	for (int i = inteiroString.length() - 1; i >= 0; i--) {
	    contador++;
	    comPontoInvertido += inteiroString.charAt(i);

	    if (contador % 3 == 0 && i != 0) {
		comPontoInvertido += ".";
	    }
	}

	String comPonto = "";

	// Invertemos
	for (int i = comPontoInvertido.length() - 1; i >= 0; i--) {
	    contador++;
	    comPonto += comPontoInvertido.charAt(i);
	}

	String decimalString = decimal + "";
	decimalString = decimalString.substring(2, decimalString.length());

	// Colocamos a virgula
	return comPonto + ',' + adicionarZerosDireitaNumero(2, decimalString);
    }

    /**
     * Separa uma String na primeira ocorrencia do caracter passado
     * 
     * @param string
     *            String a ser dividida
     * @param caracter
     *            caracter que dividira a String
     * @return vetor com 2 posicoes. A primeira com a primeira parte da String e
     *         a segunda com a segunda parte da String
     */
    public static String[] split(String texto, char separador) {

	if (texto == null) {
	    return null;
	}

	// verifica se existe alguma informação na string texto
	int tamanhoTexto = texto.length();

	if (tamanhoTexto == 0) {
	    return null;
	}

	Vector lista = new Vector();
	
	int i = 0;
	int start = 0;
	boolean permite = false;

	while (i < tamanhoTexto) {
	    // percorre caracter a caracter tentando encontrar o separador
	    // se encontrar o separador no primeiro carcater ele ignora
	    if (texto.charAt(i) == separador) {
		if (permite) {
		    // pegando o pedaço da string entre os separadores
		    lista.addElement(texto.substring(start, i).trim());
		    permite = false;
		}

		// recebo a posição de onde posso começar a pegar os caracteres,
		// até a próxima vez que encontrar o separador ou terminar os
		// caracteres
		start = ++i;
		continue;
	    }
	    
	    permite = true;
	    i++;
	}
	
	if (permite) {
	    // guardando a informação em uma posição da lista
	    lista.addElement(texto.substring(start, i).trim());
	}

	// convertendo o vetor em arrray
	String[] listaElementos = new String[lista.size()];
	lista.copyInto(listaElementos);

	return listaElementos;
    }

    public static String formatarAnoMesParaMesAno(String anoMes) {

	String anoMesFormatado = "";
	String anoMesRecebido = anoMes;
	if (anoMesRecebido.length() < 6) {
	    anoMesFormatado = anoMesRecebido;
	} else {
	    String mes = anoMesRecebido.substring(4, 6);
	    String ano = anoMesRecebido.substring(0, 4);
	    anoMesFormatado = mes + "/" + ano;
	}
	return anoMesFormatado;
    }

    public static String formatarAnoMesParaMesAnoSemBarra(String anoMes) {

	String anoMesFormatado = "";
	String anoMesRecebido = anoMes;
	if (anoMesRecebido.length() < 6) {
	    anoMesFormatado = anoMesRecebido;
	} else {
	    String mes = anoMesRecebido.substring(4, 6);
	    String ano = anoMesRecebido.substring(0, 4);
	    anoMesFormatado = mes + ano;
	}
	return anoMesFormatado;
    }

    /**
     * Obtém a representação númerica do código de barras de um pagamento de
     * acordo com os parâmetros informados [UC0229] Obter Representação Numérica
     * do Código de Barras
     * 
     * @author Pedro Alexandre
     * @date 20/04/2006
     * @param tipoPagamento
     * @param valorCodigoBarra
     * @param idLocalidade
     * @param matriculaImovel
     * @param anoMesReferenciaConta
     * @param digitoVerificadorRefContaModulo10
     * @param idTipoDebito
     * @param anoEmissaoGuiaPagamento
     * @param sequencialDocumentoCobranca
     * @param idTipoDocumento
     * @param idCliente
     * @param seqFaturaClienteResponsavel
     * @return
     * @throws ParametroNaoInformadoException
     */
    public static String obterRepresentacaoNumericaCodigoBarra(
	    Integer tipoPagamento, 
	    double valorCodigoBarra, 
	    Integer idLocalidade, 
	    Integer matriculaImovel, 
	    String mesAnoReferenciaConta, 
	    Integer digitoVerificadorRefContaModulo10, 
	    Integer idTipoDebito, 
	    String anoEmissaoGuiaPagamento,
	    String sequencialDocumentoCobranca, 
	    Integer idTipoDocumento, 
	    Integer idCliente, 
	    Integer seqFaturaClienteResponsavel) {

	// [FS0001] Verificar compatibilidade dos campos informados com o tipo
	// de pagamento
	if (tipoPagamento == null) {
	    return "atencao.parametros.incompletos.codigobarra";
	} else {
	    // Caso o tipo de pagamento seja referente a conta
	    if (tipoPagamento.intValue() == 3) {

		// Caso o código da localidade ou a matrícula do imóvel ou o
		// mês/ano da referência da conta ou o dígito verificador da
		// referência da conta no módulo 10
		// não forem informados levanta uma exceção para o usuário
		// indicando que os parâmetros para geração do código de barras
		// está incompleto.
		if (idLocalidade == null || matriculaImovel == null || mesAnoReferenciaConta == null || digitoVerificadorRefContaModulo10 == null) {
		    return "atencao.parametros.incompletos.codigobarra";
		}

		// Caso o tipo de pagamento seja referente a guia de pagamento
	    } else if (tipoPagamento.intValue() == 4) {

		// Caso o código da localidade ou a matrícula do imóvel ou o
		// tipo de débito ou o ano da emissão da guia de pagamento
		// não forem informados levanta uma exceção para o usuário
		// indicando que os parâmetros para geração do código de barras
		// está incompleto.
		if (idLocalidade == null || matriculaImovel == null || idTipoDebito == null || anoEmissaoGuiaPagamento == null) {
		    return "atencao.parametros.incompletos.codigobarra";
		}

		// Caso a tipo de pagamento seja referente a documento de
		// cobrança
	    } else if (tipoPagamento.intValue() == 5) {

		// Caso o código da localidade ou a matrícula do imóvel ou o
		// sequencial do documento de cobrança ou o tipo de documento
		// não forem informados levanta uma exceção para o usuário
		// indicando que os parâmetros para geração do código de barras
		// está incompleto.
		if (idLocalidade == null || matriculaImovel == null || sequencialDocumentoCobranca == null || idTipoDocumento == null) {
		    return "atencao.parametros.incompletos.codigobarra";
		}

		// Caso o tipo de pagamento seja referente a fatura do cliente
		// responsável
	    } else if (tipoPagamento.intValue() == 7) {
		// Caso o código do cliente ou o mês/ano da referência da conta
		// ou o sequencial da fatura do cliente responsável
		// não forem informados levanta uma exceção para o usuário
		// indicando que os parâmetros para geração do código de barras
		// está incompleto.
		if (idCliente == null || mesAnoReferenciaConta == null || seqFaturaClienteResponsavel == null) {
		    return "atencao.parametros.incompletos.codigobarra";
		}

		// Caso a tipo de pagamento seja referente a guia de pagamento
	    } else if (tipoPagamento.intValue() == 6) {
		// Caso o código da localidade ou id do cliente ou o
		// tipo de débito ou o ano da emissão da guia de pagamento
		// não forem informados levanta uma exceção para o usuário
		// indicando que os parâmetros para geração do código de barras
		// está incompleto.
		if (idLocalidade == null || idCliente == null || idTipoDebito == null || anoEmissaoGuiaPagamento == null) {
		    return "atencao.parametros.incompletos.codigobarra";
		}
	    } else if (tipoPagamento.intValue() == 8) {

		// Caso o código do cliente ou o
		// sequencial do documento de cobrança ou o tipo de documento
		// não forem informados levanta uma exceção para o usuário
		// indicando que os parâmetros para geração do código de barras
		// está incompleto.
		if (idCliente == null || sequencialDocumentoCobranca == null || idTipoDocumento == null) {
		    return "atencao.parametros.incompletos.codigobarra";
		}
	    }
	}

	// Cria a variável que vai armazenar a representação númerica do código
	// de barras
	String representacaoNumericaCodigoBarra = "";

	// G.05.1 - Identificação do produto
	String identificacaoProduto = "8";
	representacaoNumericaCodigoBarra = representacaoNumericaCodigoBarra + identificacaoProduto;

	// G.05.2 - Identificação do segmento
	String identificacaoSegmento = "2";
	representacaoNumericaCodigoBarra = representacaoNumericaCodigoBarra + identificacaoSegmento;

	// G.05.3 - Identificação dovalor real ou referência

	// MODULO 11
	// String identificacaoValorRealOuReferencia = "8";

	// MODULO 10
	String identificacaoValorRealOuReferencia = "6";

	representacaoNumericaCodigoBarra = representacaoNumericaCodigoBarra + identificacaoValorRealOuReferencia;

	String valorContaString = Util.formatarDoubleParaMoedaReal(valorCodigoBarra);

	valorContaString = replaceAll(valorContaString, ".", "");
	valorContaString = replaceAll(valorContaString, ",", "");

	// G.05.5 - Valor do código de barras
	String valorCodigoBarraFormatado = Util.adicionarZerosEsquerdaNumero(11, valorContaString);
	representacaoNumericaCodigoBarra = representacaoNumericaCodigoBarra + valorCodigoBarraFormatado;

	// G.05.6 - Identificação da empresa
	// Fixo por enquanto
	String identificacaoEmpresa = ImovelReg11.getInstancia().getCodigoEmpresaFebraban();
	identificacaoEmpresa = Util.adicionarZerosEsquerdaNumero(4, identificacaoEmpresa);
	representacaoNumericaCodigoBarra = representacaoNumericaCodigoBarra + identificacaoEmpresa;

	// G.05.7 Identificação do pagamento
	// [SB0001] Obter Identificação do Pagamento
	String identificacaoPagamento = obterIdentificacaoPagamento(tipoPagamento, idLocalidade, matriculaImovel, mesAnoReferenciaConta, digitoVerificadorRefContaModulo10, idTipoDebito, anoEmissaoGuiaPagamento, sequencialDocumentoCobranca, idTipoDocumento, idCliente, seqFaturaClienteResponsavel);

	representacaoNumericaCodigoBarra = representacaoNumericaCodigoBarra + identificacaoPagamento + tipoPagamento.toString();

	// G.05.4 - Dígito verificador geral
	// [SB0002] Obter Dígito verificador geral
	String digitoVerificadorGeral = (Util.obterDigitoVerificadorGeral(representacaoNumericaCodigoBarra)).toString();

	// Monta a representaçaõ númerica com todos os campos informados
	representacaoNumericaCodigoBarra = identificacaoProduto + identificacaoSegmento + identificacaoValorRealOuReferencia + digitoVerificadorGeral + valorCodigoBarraFormatado + identificacaoEmpresa + identificacaoPagamento + tipoPagamento.toString();

	// Cria as variáveis que vão armazenar o código de barra separado por
	// campos
	// e seus respectivos dígitos verificadores se existirem
	String codigoBarraCampo1 = null;
	String codigoBarraDigitoVerificadorCampo1 = null;
	String codigoBarraCampo2 = null;
	String codigoBarraDigitoVerificadorCampo2 = null;
	String codigoBarraCampo3 = null;
	String codigoBarraDigitoVerificadorCampo3 = null;
	String codigoBarraCampo4 = null;
	String codigoBarraDigitoVerificadorCampo4 = null;

	// Separa as 44 posições do código de barras em 4 grupos de onze
	// posições
	// e para cada um dos grupos calcula o dígito verificador do módulo 11
	codigoBarraCampo1 = representacaoNumericaCodigoBarra.substring(0, 11);
	// codigoBarraDigitoVerificadorCampo1 =
	// (obterDigitoVerificadorModulo11(new Long(
	// codigoBarraCampo1))).toString();
	codigoBarraDigitoVerificadorCampo1 = (Util.obterDigitoVerificadorModulo10(new Long(Long.parseLong(codigoBarraCampo1)))).toString();
	codigoBarraCampo2 = representacaoNumericaCodigoBarra.substring(11, 22);
	/*
	 * codigoBarraDigitoVerificadorCampo2 =
	 * (obterDigitoVerificadorModulo11(new Long(
	 * codigoBarraCampo2))).toString();
	 */
	codigoBarraDigitoVerificadorCampo2 = (Util.obterDigitoVerificadorModulo10(new Long(Long.parseLong(codigoBarraCampo2)))).toString();
	codigoBarraCampo3 = representacaoNumericaCodigoBarra.substring(22, 33);
	/*
	 * codigoBarraDigitoVerificadorCampo3 =
	 * (obterDigitoVerificadorModulo11(new Long(
	 * codigoBarraCampo3))).toString();
	 */
	codigoBarraDigitoVerificadorCampo3 = (Util.obterDigitoVerificadorModulo10(new Long(Long.parseLong(codigoBarraCampo3)))).toString();
	codigoBarraCampo4 = representacaoNumericaCodigoBarra.substring(33, 44);
	/*
	 * codigoBarraDigitoVerificadorCampo4 =
	 * (obterDigitoVerificadorModulo11(new Long(
	 * codigoBarraCampo4))).toString();
	 */
	codigoBarraDigitoVerificadorCampo4 = (Util.obterDigitoVerificadorModulo10(new Long(Long.parseLong(codigoBarraCampo4)))).toString();

	// Monta a representação númerica do código de barras com os dígitos
	// verificadores
	representacaoNumericaCodigoBarra = codigoBarraCampo1 + codigoBarraDigitoVerificadorCampo1 + codigoBarraCampo2 + codigoBarraDigitoVerificadorCampo2 + codigoBarraCampo3 + codigoBarraDigitoVerificadorCampo3 + codigoBarraCampo4 + codigoBarraDigitoVerificadorCampo4;

	// Retorna a representação númerica do código de barras
	return representacaoNumericaCodigoBarra;
    }

    /**
     * Adiciona zeros a esqueda do número informado tamamho máximo campo 6
     * Número 16 retorna 000016
     * 
     * @param tamanhoMaximoCampo
     *            Descrição do parâmetro
     * @param numero
     *            Descrição do parâmetro
     * @return Descrição do retorno
     */
    public static String adicionarZerosEsquerdaNumero(int tamanhoMaximoCampo, String numero) {
	String zeros = "";

	String retorno = null;

	boolean ehNegativo = numero != null && !numero.equals(Constantes.NULO_STRING) && !numero.equals(Constantes.NULO_DOUBLE + "") && !numero.equals(Constantes.NULO_INT + "") && !numero.equals(Constantes.NULO_SHORT + "") && numero.charAt(0) == '-';

	if (ehNegativo) {
	    numero = numero.substring(1);
	}

	if (numero != null && !numero.equals("") && !numero.equals(Constantes.NULO_INT + "")) {
	    for (int a = 0; a < (tamanhoMaximoCampo - numero.length()); a++) {
		zeros = zeros.concat("0");
	    }
	    // concatena os zeros ao numero
	    // caso o numero seja diferente de nulo
	    retorno = zeros.concat(numero);
	} else {
	    for (int a = 0; a < tamanhoMaximoCampo; a++) {
		zeros = zeros.concat("0");
	    }
	    // retorna os zeros
	    // caso o numero seja nulo
	    retorno = zeros;
	}

	if (ehNegativo) {
	    retorno = "-" + retorno.substring(1);
	}

	return retorno;
    }

    /**
     * Adiciona zeros a esqueda do número informado tamamho máximo campo 6
     * Número 16 retorna 000016
     * 
     * @param tamanhoMaximoCampo
     *            Descrição do parâmetro
     * @param numero
     *            Descrição do parâmetro
     * @return Descrição do retorno
     */
    public static String adicionarCharEsquerda(int tamanhoMaximoCampo, String string, char c) {
	String repetido = "";

	String retorno = null;

	if (string != null && !string.equals("") && !string.equals(Constantes.NULO_INT + "")) {
	    for (int a = 0; a < (tamanhoMaximoCampo - string.length()); a++) {
		repetido = repetido.concat(c + "");
	    }
	    // concatena os zeros ao numero
	    // caso o numero seja diferente de nulo
	    retorno = repetido.concat(string);
	} else {
	    for (int a = 0; a < tamanhoMaximoCampo; a++) {
		repetido = repetido.concat(c + "");
	    }
	    // retorna os zeros
	    // caso o numero seja nulo
	    retorno = repetido;
	}

	return retorno;
    }

    /**
     * Obtém a representação númerica do código de barras de um pagamento de
     * acordo com os parâmetros informados [UC0229] Obter Representação Numérica
     * do Código de Barras Formata a identificação do pagamento de acordo com o
     * tipo de pagamento informado [SB0001] Obter Identificação do Pagamento
     * 
     * @author Pedro Alexandre
     * @date 20/04/2006
     * @param tipoPagamento
     * @param idLocalidade
     * @param matriculaImovel
     * @param anoMesReferenciaConta
     * @param digitoVerificadorRefContaModulo10
     * @param idTipoDebito
     * @param anoEmissaoGuiaPagamento
     * @param sequencialDocumentoCobranca
     * @param idTipoDocumento
     * @param idCliente
     * @param seqFaturaClienteResponsavel
     * @return
     */
    public static String obterIdentificacaoPagamento(Integer tipoPagamento, Integer idLocalidade, Integer matriculaImovel, String mesAnoReferenciaConta, Integer digitoVerificadorRefContaModulo10, Integer idTipoDebito, String anoEmissaoGuiaPagamento, String sequencialDocumentoCobranca,
	    Integer idTipoDocumento, Integer idCliente, Integer seqFaturaClienteResponsavel) {

	// Cria a variável que vai armazenar o identificador do pagamento
	// formatado
	String identificacaoPagamento = "";

	// Caso o tipo de pagamento seja referente a conta
	if (tipoPagamento.intValue() == 3) {
	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(8, "" + matriculaImovel);
	    identificacaoPagamento = identificacaoPagamento + "000";
	    identificacaoPagamento = identificacaoPagamento + mesAnoReferenciaConta;
	    identificacaoPagamento = identificacaoPagamento + digitoVerificadorRefContaModulo10;
	    identificacaoPagamento = identificacaoPagamento + "000";

	    // Caso o tipo de pagamento seja referente a guia de pagamento
	    // (Imóvel)
	} else if (tipoPagamento.intValue() == 4) {
	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(8, "" + matriculaImovel);
	    identificacaoPagamento = identificacaoPagamento + "000";
	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(3, idTipoDebito.toString()));
	    identificacaoPagamento = identificacaoPagamento + anoEmissaoGuiaPagamento;
	    identificacaoPagamento = identificacaoPagamento + "000";

	    // Caso a tipo de pagamento seja referente a documento de cobrança
	} else if (tipoPagamento.intValue() == 5) {
	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(8, "" + matriculaImovel);
	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(9, sequencialDocumentoCobranca));
	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(2, idTipoDocumento.toString()));
	    identificacaoPagamento = identificacaoPagamento + "00";

	    // Caso o tipo de pagamento seja referente a guia de pagamento
	    // (Cliente)
	} else if (tipoPagamento.intValue() == 6) {
	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(8, "" + idCliente);
	    identificacaoPagamento = identificacaoPagamento + "000";
	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(3, idTipoDebito.toString()));
	    identificacaoPagamento = identificacaoPagamento + anoEmissaoGuiaPagamento;
	    identificacaoPagamento = identificacaoPagamento + "000";

	    // Caso o tipo de pagamento seja referente a fatura do cliente
	    // responsável
	} else if (tipoPagamento.intValue() == 7) {
	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(9, idCliente.toString()));
	    identificacaoPagamento = identificacaoPagamento + "00";
	    identificacaoPagamento = identificacaoPagamento + mesAnoReferenciaConta;
	    identificacaoPagamento = identificacaoPagamento + digitoVerificadorRefContaModulo10;
	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(6, seqFaturaClienteResponsavel.toString()));
	    // Caso a tipo de pagamento seja referente a documento de cobrança
	    // cliente
	} else if (tipoPagamento.intValue() == 8) {
	    identificacaoPagamento = identificacaoPagamento + "000";
	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(8, idCliente.toString()));
	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(9, sequencialDocumentoCobranca));
	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(2, idTipoDocumento.toString()));
	    identificacaoPagamento = identificacaoPagamento + "00";
	}

	// Retorna o identificador do pagamento formatado
	return identificacaoPagamento;
    }

    /**
     * <Breve descrição sobre o caso de uso> [UC0260] Obter Dígito Verificador
     * Módulo 10
     * 
     * @author Rafael Rossiter
     * @date 17/03/2006
     * @param numero
     * @return
     */
    public static Integer obterDigitoVerificadorModulo10(Long numero) {

	long entrada = numero.longValue();

	String entradaString = String.valueOf(entrada);

	int sequencia = 2;
	int contEntrada, digito, contAuxiliar, produto, contProduto;
	String produtoString;
	int somaDigitosProduto = 0;

	contAuxiliar = 1;
	for (contEntrada = 0; contEntrada < entradaString.length(); contEntrada++) {

	    digito = Integer.parseInt(entradaString.substring(entradaString.length() - contAuxiliar, entradaString.length() - contEntrada));

	    produto = digito * sequencia;
	    produtoString = String.valueOf(produto);

	    for (contProduto = 0; contProduto < produtoString.length(); contProduto++) {
		somaDigitosProduto = somaDigitosProduto + Integer.parseInt(produtoString.substring(contProduto, contProduto + 1));
	    }

	    if (sequencia == 2) {
		sequencia = 1;
	    } else {
		sequencia = 2;
	    }

	    contAuxiliar++;
	}

	int resto = (somaDigitosProduto % 10);

	int dac;
	if (resto == 0) {
	    dac = 0;
	} else {
	    dac = 10 - resto;
	}

	return new Integer(dac);
    }

    /**
     * Obtém a representação númerica do código de barras de um pagamento de
     * acordo com os parâmetros informados [UC0229] Obter Representação Numérica
     * do Código de Barras Obtém o dígito verificador geral do código de barra
     * com 43 posições [SB0002] Obter Dígito Verificador Geral
     * 
     * @author Pedro Alexandre
     * @date 20/04/2006
     * @param codigoBarraCom43Posicoes
     * @return
     */
    public static Integer obterDigitoVerificadorGeral(String codigoBarraCom43Posicoes) {
	// Recupera o dígito verificador do módulo 11 para o código de barra com
	// 43 posições
	// Passando uma string como parâmetro

	// MUDOU PARA O MODULO 10
	// Integer digitoVerificadorGeral =
	// obterDigitoVerificadorModulo11(codigoBarraCom43Posicoes);

	Integer digitoVerificadorGeral = obterDigitoVerificadorModulo10(codigoBarraCom43Posicoes);

	// Retorna o dígito verificador calculado
	return digitoVerificadorGeral;
    }

    /**
     * <Breve descrição sobre o caso de uso> [UC0260] Obter Dígito Verificador
     * Módulo 10
     * 
     * @author Rafael Rossiter
     * @date 17/03/2006
     * @param numero
     * @return
     */
    public static Integer obterDigitoVerificadorModulo10(String numero) {

	String entradaString = numero;

	int sequencia = 2;
	int contEntrada, digito, contAuxiliar, produto, contProduto;
	String produtoString;
	int somaDigitosProduto = 0;

	contAuxiliar = 1;
	for (contEntrada = 0; contEntrada < entradaString.length(); contEntrada++) {

	    digito = Integer.parseInt(entradaString.substring(entradaString.length() - contAuxiliar, entradaString.length() - contEntrada));

	    produto = digito * sequencia;
	    produtoString = String.valueOf(produto);

	    for (contProduto = 0; contProduto < produtoString.length(); contProduto++) {
		somaDigitosProduto = somaDigitosProduto + Integer.parseInt(produtoString.substring(contProduto, contProduto + 1));
	    }

	    if (sequencia == 2) {
		sequencia = 1;
	    } else {
		sequencia = 2;
	    }

	    contAuxiliar++;
	}

	int resto = (somaDigitosProduto % 10);

	int dac;
	if (resto == 0) {
	    dac = 0;
	} else {
	    dac = 10 - resto;
	}

	return new Integer(dac);
    }

    public static String replaceAll(String text, String searchString, String replacementString) {

	StringBuffer sBuffer = new StringBuffer();
	int pos = 0;

	while ((pos = text.indexOf(searchString)) != -1) {
	    sBuffer.append(text.substring(0, pos) + replacementString);
	    text = text.substring(pos + searchString.length());
	}

	sBuffer.append(text);

	return sBuffer.toString();
    }

    /**
     * Adiciona zeros a direita do número informado tamamho máximo campo 6
     * Número 16 retorna 000016
     * 
     * @param tamanhoMaximoCampo
     *            Descrição do parâmetro
     * @param numero
     *            Descrição do parâmetro
     * @return Descrição do retorno
     */
    public static String adicionarZerosDireitaNumero(int tamanhoMaximoCampo, String numero) {
	String retorno = "";

	String zeros = "";

	for (int i = 0; i < (tamanhoMaximoCampo - numero.length()); i++) {
	    zeros += "0";
	}

	retorno += numero + zeros;

	return retorno;
    }

    /**
     * < <Descrição do método>>
     * 
     * @param data
     *            Descrição do parâmetro
     * @return Descrição do retorno
     */
    public static String formatarData(Date data) {
	StringBuffer dataBD = new StringBuffer();

	if (data != null) {
	    Calendar dataCalendar = Calendar.getInstance();
	    dataCalendar.setTime(data);

	    dataBD.append(dataCalendar.get(Calendar.YEAR) + "-");

	    // Obs.: Janeiro no Calendar é mês zero
	    if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
		dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "-");
	    } else {
		dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1) + "-");
	    }

	    if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
		dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
	    } else {
		dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
	    }

	    dataBD.append(" ");

	    if (dataCalendar.get(Calendar.HOUR_OF_DAY) > 9) {
		dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
	    } else {
		dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
	    }

	    dataBD.append(":");

	    if (dataCalendar.get(Calendar.MINUTE) > 9) {
		dataBD.append(dataCalendar.get(Calendar.MINUTE));
	    } else {
		dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
	    }

	    dataBD.append(":");

	    if (dataCalendar.get(Calendar.SECOND) > 9) {
		dataBD.append(dataCalendar.get(Calendar.SECOND));
	    } else {
		dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
	    }

	    dataBD.append(".");

	    dataBD.append(Util.adicionarZerosEsquerdaNumero(6, dataCalendar.get(Calendar.MILLISECOND) + ""));
	}

	return dataBD.toString();
    }

    /**
     * < <Descrição do método>>
     * 
     * @param data
     *            Descrição do parâmetro
     * @return Descrição do retorno
     */
    public static String formatarDataSemHora(Date data) {
	StringBuffer dataBD = new StringBuffer();

	if (data != null) {
	    Calendar dataCalendar = Calendar.getInstance();
	    dataCalendar.setTime(data);

	    dataBD.append(dataCalendar.get(Calendar.YEAR) + "-");

	    // Obs.: Janeiro no Calendar é mês zero
	    if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
		dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "-");
	    } else {
		dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1) + "-");
	    }

	    if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
		dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
	    } else {
		dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
	    }

	    dataBD.append(" ");

	    // dataBD.append(".");

	    // dataBD.append( Util.adicionarZerosEsquerdaNumero( 6,
	    // dataCalendar.get(Calendar.MILLISECOND)+"" ) );
	}

	return dataBD.toString();
    }

    public static String formatarDataPTBR(String data) {

	String ano = "";
	String mes = "";
	String dia = "";
	String dataFormatada = "";

	ano = data.substring(0, 4);
	mes = data.substring(5, 7);
	dia = data.substring(8, 11);

	dataFormatada = dia + "/" + mes + "/" + ano;

	return dataFormatada;
    }

    public static void mostrarErro(String erro, Exception error) {
	error.printStackTrace();
	FileManager.salvarErro(error);
	mostrarErro(erro);
    }

    /**
     * @author Breno Santos
     * @return Mensagem na tela
     */
    public static void mostrarErro(String erro) {

	// Tamanho maximo para uma linha da mensagem do Dialog
	int tamanhoDialog = 23;
	// Tamanho do texto recebido
	int tamanhoMensagem = erro.length();

	int indice = 0;

	Dialog dialogMensagem = new Dialog("Mensagem do Sistema");

	if (tamanhoMensagem < tamanhoDialog) {
	    Label lbPartePergunta = new Label(erro);

	    dialogMensagem.getTitleStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	    dialogMensagem.getDialogStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	    dialogMensagem.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
	    dialogMensagem.addComponent(lbPartePergunta);
	    dialogMensagem.setTimeout(Constantes.TIME_OUT_DIALOG);

	    dialogMensagem.show();
	} else {

	    int falta = tamanhoMensagem;

	    while (falta % tamanhoDialog > 0) {

		Label lbPartePergunta = null;

		if (falta < tamanhoDialog) {
		    lbPartePergunta = new Label(erro.substring(tamanhoDialog * indice, tamanhoMensagem));
		} else {
		    lbPartePergunta = new Label(erro.substring(tamanhoDialog * indice, tamanhoDialog * (indice + 1)));
		}

		lbPartePergunta.getStyle().setMargin(0, 0, 0, 0);
		lbPartePergunta.getStyle().setPadding(0, 0, 0, 0);

		dialogMensagem.addComponent(lbPartePergunta);

		falta -= tamanhoDialog;
		++indice;
	    }

	    dialogMensagem.getTitleStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	    dialogMensagem.getDialogStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	    dialogMensagem.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
	    dialogMensagem.setTimeout(Constantes.TIME_OUT_DIALOG);

	    dialogMensagem.show();
	}

    }

    /**
     * Mostra a pergunta e retorna true se sim
     * 
     * @param pergunta
     *            Pergunta a ser mostrada
     * @return
     */
    public static boolean perguntarAcao( String pergunta, boolean verificarSenha, boolean verificarSenhaAdmistrador ) {
		try{
		    DialogPerguntar acao = new DialogPerguntar( pergunta, verificarSenha, verificarSenhaAdmistrador );
		    repetirPergunta = 0;
		    return acao.confirmouPergunta;
		} catch ( Exception e ){
		    try{
		    	if ( repetirPergunta < 30 ){
		    	    repetirPergunta++;
		    	    return perguntarAcao( pergunta , verificarSenha, verificarSenhaAdmistrador);
		    	} else {
		    	    repetirPergunta = 0;
		    	    Util.mostrarErro( "Erro ao montar ação de pergunta" );
		    	}
		    } catch ( Exception e1 ){
			repetirPergunta = 0;
			Util.mostrarErro( "Erro ao montar ação de pergunta" );		
		    }
		}
		return true;
    }

    /**
     * Mostra a mensagem e espera confirmação do usuário
     * 
     * @param mensagem
     *            Pergunta a ser mostrada
     * @return
     */
    public static boolean MensagemConfirmacao( String mensagem) {
		try{
			DialogConfirma acao = new DialogConfirma( mensagem);
		    repetirPergunta = 0;
		    return acao.confirmouPergunta;
		} catch ( Exception e ){
		    try{
		    	if ( repetirPergunta < 30 ){
		    	    repetirPergunta++;
		    	    return MensagemConfirmacao(mensagem);
		    	} else {
		    	    repetirPergunta = 0;
		    	    Util.mostrarErro( "Erro ao montar ação de mensagem" );
		    	}
		    } catch ( Exception e1 ){
			repetirPergunta = 0;
			Util.mostrarErro( "Erro ao montar ação de mensagem" );		
		    }
		}
	
		return true;
    }

    /**
     * Envia um comando para a impressora
     * 
     * @author Bruno Barros
     * @date 23/11/2009
     * @return erro Houve algum erro ?
     * @param comando
     *            comando a ser enviado
     */
    public static boolean enviarComandoImpressora(String comando) {

	String SERVICE_URL = Configuracao.getInstancia().getBluetoothAddress();
	StreamConnection conn = null;
	DataOutputStream in = null;

	if (Fachada.getInstancia().getPrinter().equals("1")){
		try {
		    conn = (StreamConnection) Connector.open(SERVICE_URL, Connector.WRITE);
		    in = conn.openDataOutputStream();
		    in.write(comando.getBytes());
		    in.flush();
		} catch ( IOException e) {
		    if (Util.perguntarAcao("Erro ao imprimir! Tentar novamente?", false, false )) {
			return enviarComandoImpressora(comando);
		    } else {
			return true;
		    }
		} finally {
		    try {
			if (in != null) {
			    in.close();
			    in = null;
			}
	
			if (conn != null) {
			    conn.close();
			    conn = null;
			}
		    } catch (IOException e) {
			Util.mostrarErro("Erro ao enviar comando para impressora");
			return true;
		    }
		}
	 }
	return false;
    }

    /**
     * Envia o imovel passado para o servidor
     * 
     * @author Bruno Barros
     * @date 23/11/2009
     * @param imovelParaProcessar
     *            imovel a ser enviado
     */
    public static void enviarImovelOnLine(ImovelConta imovelParaProcessar) {

	StringBuffer mensagem = ArquivoRetorno.getInstancia().gerarArquivoRetornoOnLine(imovelParaProcessar);
	    
	try {
	    FachadaRede.getInstancia().enviarImovel(mensagem.toString().getBytes());

	    if (FachadaRede.getInstancia().isRequestOK()) {
	    	imovelParaProcessar.setIndcImovelEnviado(Constantes.SIM);
	    	Repositorio.salvarObjeto(imovelParaProcessar);	    	
//	    	mensagemSucesso("Imovel enviado!");
	    } else {
//	    	mensagemAviso("Aviso:", "Imovel não enviado!");
	    }	    
	} catch (IOException e) {
	    // Exceção silenciosa, pois nao paramos o processamento caso
	    // aconteça algum problema
	    Util.mostrarErro("Erro no envio");
	}
    }

    /**
     * Envia o imovel passado para o servidor
     * 
     * @author Bruno Barros
     * @date 23/11/2009
     * @param imovelParaProcessar
     *            imovel a ser enviado
     */
    public static void enviarImovelOnLine(Vector imoveisParaProcessar) {

	StringBuffer mensagem = new StringBuffer();

	for (int i = 0; i < imoveisParaProcessar.size(); i++) {
//	    ControladorImoveis.getInstancia().setImovelSelecionado((ImovelConta) imoveisParaProcessar.elementAt(i));
		mensagem = mensagem.append(ArquivoRetorno.getInstancia().gerarArquivoRetornoOnLine((ImovelConta) imoveisParaProcessar.elementAt(i)));
	}

	try {
	    FachadaRede.getInstancia().enviarImovel(mensagem.toString().getBytes());
	} catch (IOException e) {
	    // Excessão silenciosa, pois nao paramos o processamento caso
	    // aconteça algum problema
	    Util.mostrarErro("Erro no envio");
	}

	if (FachadaRede.getInstancia().isRequestOK()) {

	    for (int i = 0; i < imoveisParaProcessar.size(); i++) {

	    	ImovelConta imovel = (ImovelConta) imoveisParaProcessar.elementAt(i);

	    	imovel.setIndcImovelEnviado(Constantes.SIM);
	    	Repositorio.salvarObjeto(imovel);
	    }
	    Util.mensagemSucesso("Imóveis enviados com \n sucesso!");
    } else {
    	mensagemAviso("Aviso:","Imóveis não enviados!");
    }	    
    }

    /**
     * @author Breno Santos
     * @return Mensagem na tela
     */
    public static void mensagemSucesso(String mensagem) {

	// Tamanho maximo para uma linha da mensagem do Dialog
	int tamanhoDialog = 23;
	// Tamanho do texto recebido
	int tamanhoMensagem = mensagem.length();

	int indice = 0;

	Dialog dialogMensagem = new Dialog("Sucesso!");

	if (tamanhoMensagem < tamanhoDialog) {
	    Label lbPartePergunta = new Label(mensagem);

	    dialogMensagem.getTitleStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	    dialogMensagem.getDialogStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	    dialogMensagem.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
	    dialogMensagem.addComponent(lbPartePergunta);
	    dialogMensagem.setTimeout(Constantes.TIME_OUT_DIALOG);

	    dialogMensagem.show();
	} else {

	    int falta = tamanhoMensagem;

	    while (falta % tamanhoDialog > 0) {

		Label lbPartePergunta = null;

		if (falta < tamanhoDialog) {
		    lbPartePergunta = new Label(mensagem.substring(tamanhoDialog * indice, tamanhoMensagem));
		} else {
		    lbPartePergunta = new Label(mensagem.substring(tamanhoDialog * indice, tamanhoDialog * (indice + 1)));
		}

		lbPartePergunta.getStyle().setMargin(0, 0, 0, 0);
		lbPartePergunta.getStyle().setPadding(0, 0, 0, 0);

		dialogMensagem.addComponent(lbPartePergunta);

		falta -= tamanhoDialog;
		++indice;
	    }

	    dialogMensagem.getTitleStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	    dialogMensagem.getDialogStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	    dialogMensagem.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
	    dialogMensagem.setTimeout(Constantes.TIME_OUT_DIALOG);

	    dialogMensagem.show();
	}
    }

    /**
     * @author Breno Santos
     * @return Mensagem na tela
     */

    public static void mensagemAviso(String titulo, String mensagem) {

	// Tamanho maximo para uma linha da mensagem do Dialog
	int tamanhoDialog = 23;
	// Tamanho do texto recebido
	int tamanhoMensagem = mensagem.length();

	int indice = 0;

	Dialog dialogMensagem = new Dialog(titulo);

	if (tamanhoMensagem < tamanhoDialog) {
	    Label lbPartePergunta = new Label(mensagem);

	    dialogMensagem.getTitleStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	    dialogMensagem.getDialogStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	    dialogMensagem.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
	    dialogMensagem.addComponent(lbPartePergunta);
	    dialogMensagem.setTimeout(Constantes.MENSAGEM_AVISO);

	    dialogMensagem.show();
	} else {

	    int falta = tamanhoMensagem;

	    while (falta % tamanhoDialog > 0) {

		Label lbPartePergunta = null;

		if (falta < tamanhoDialog) {
		    lbPartePergunta = new Label(mensagem.substring(tamanhoDialog * indice, tamanhoMensagem));
		} else {
		    lbPartePergunta = new Label(mensagem.substring(tamanhoDialog * indice, tamanhoDialog * (indice + 1)));
		}

		lbPartePergunta.getStyle().setMargin(0, 0, 0, 0);
		lbPartePergunta.getStyle().setPadding(0, 0, 0, 0);

		dialogMensagem.addComponent(lbPartePergunta);

		falta -= tamanhoDialog;
		++indice;
	    }

	    dialogMensagem.getTitleStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	    dialogMensagem.getDialogStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	    dialogMensagem.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
	    dialogMensagem.setTimeout(Constantes.MENSAGEM_AVISO);

	    dialogMensagem.show();
	}
    }

    /**
     * Como o celular nao implementa timezone removemos sempre tres horas da
     * hora atual O celular precisa ser sempre configurado para GMT-3:00
     * 
     * @author Bruno Barros
     * @return Data menos 3 horas
     */
    public static Date dataAtual() {
   	 return new Date( (new Date()).getTime());
//	 return new Date( (new Date()).getTime() - 10800000 );
//	return new Date();
    }

    public static String getDataHora() {
    	//recupera data e hora atual do sistema
    	Calendar calendario = Calendar.getInstance();
//    	Date date = new Date((new Date()).getTime() - 10800000 );
    	Date date = new Date((new Date()).getTime());
    	calendario.setTime(date);
    	String mes = String.valueOf(calendario.get(Calendar.MONTH) + 1);
    	String dia = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
    	String ano = String.valueOf(calendario.get(Calendar.YEAR));
    	String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY));
    	String minutos = String.valueOf(calendario.get(Calendar.MINUTE));
    	String segundos = String.valueOf(calendario.get(Calendar.SECOND));
    	//
    	calendario = null;
    	date = null;
    	//
    	//formata a data de modo que o tamanho do resultado seja sempre fixo
    	//dia
    	if (dia.length() < 2) { dia = "0" + dia; } //mes
    	if (mes.length() < 2) { mes = "0" + mes; } //horas
    	if (horas.length() < 2) { horas = "0" + horas; } //minutos
    	if (minutos.length() < 2) { minutos = "0" + minutos; } //segundos
    	if (segundos.length() < 2) { segundos = "0" + segundos; } //
    	return dia + "/" + mes + "/" + ano + " " + horas + ":" + minutos + ":" + segundos;
    } 
    
    public static String getAnoBarraMesReferencia(int valor) {
    	if(valor != 0){
        	return String.valueOf(valor).substring(0, 4) + "/" + String.valueOf(valor).substring(4, 6);

    	}else{
    		return "";
    	}
    }

    
    public static String validarAnormalidadeConsumo(Consumo consumo) {
	String mensagemAnormalidade = null;

	if (consumo != null && consumo.getAnormalidadeConsumo() != Constantes.NULO_INT) {
	    int anormalidadeConsumo = consumo.getAnormalidadeConsumo();
	    switch (anormalidadeConsumo) {
	    case Consumo.CONSUMO_ANORM_BAIXO_CONSUMO:
		mensagemAnormalidade = "BAIXO CONSUMO";
		break;
	    case Consumo.CONSUMO_ANORM_ESTOURO:
		mensagemAnormalidade = "ESTOURO DO CONSUMO";
		break;
	    case Consumo.CONSUMO_ANORM_ALTO_CONSUMO:
		mensagemAnormalidade = "ALTO CONSUMO";
		break;
	    case Consumo.CONSUMO_ANORM_LEIT_MENOR_PROJ:
		mensagemAnormalidade = "LEITURA MENOR PROJETADA";
		break;
	    case Consumo.CONSUMO_ANORM_LEIT_MENOR_ANTE:
		mensagemAnormalidade = "LEITURA MENOR ANTERIOR";
		break;
	    case Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO:
		mensagemAnormalidade = "HIDR. SUBSTITUIDO INFORM.";
		break;
	    case Consumo.CONSUMO_ANORM_LEITURA_N_INFO:
		mensagemAnormalidade = "LEITURA NAO INFORMADA";
		break;
	    case Consumo.CONSUMO_ANORM_ESTOURO_MEDIA:
		mensagemAnormalidade = "ESTOURO MEDIA";
		break;
	    case Consumo.CONSUMO_ANORM_FORA_DE_FAIXA:
		mensagemAnormalidade = "FORA DE FAIXA";
		break;
	    case Consumo.CONSUMO_ANORM_HIDR_SUBST_N_INFO:
		mensagemAnormalidade = "HIDR. SUBST. NAO INFORM.";
		break;
	    case Consumo.CONSUMO_ANORM_VIRADA_HIDROMETRO:
		mensagemAnormalidade = "VIRADA DE HIDROMETRO";
		break;
	    }
	}

	return mensagemAnormalidade;
    }

    // Complementa o tamanho da string com espaços em branco.
    // Autor:Sávio Luiz
    public static String completaString(String str, int tm) {

	String string = null;

	int tamanhoString = 0;
	if (str != null) {
	    tamanhoString = str.length();
	}

	// caso o tamanho da string seja maior do que o tamanho especificado
	if (tamanhoString > tm) {
	    // trunca a String
	    string = str.substring(0, tm);

	} else {
	    string = str;
	    for (int i = 0; i < (tm - tamanhoString); i++) {
		string = string + " ";
	    }
	}

	return string;
    }

    /**
     * Author: Pedro Alexandre Data: 08/01/2006 Adiciona nº de dias para uma
     * data
     * 
     * @param numeroDias
     * @param data
     * @return data menos o nº de dias informado
     */
    public static Date adicionarNumeroDiasDeUmaData(Date data, long numeroDias) {
	Date dataAlterada = new Date(data.getTime());

	numeroDias = numeroDias * 86400000;
	// seta a data
	dataAlterada.setTime(dataAlterada.getTime() + (numeroDias));

	// retorna a nova data
	return dataAlterada;
    }

    public static String formatarCodigoBarras(String codigoBarras) {
	String retorno = "";
	// return "82600000010-7 63620006190-2 01531394000-7 08200920003-9";
	if (codigoBarras != null && codigoBarras.length() >= 48) {
	    retorno = codigoBarras.substring(0, 11).trim() + "-" + codigoBarras.substring(11, 12).trim() + " " + codigoBarras.substring(12, 23).trim() + "-" + codigoBarras.substring(23, 24).trim() + " " + codigoBarras.substring(24, 35).trim() + "-" + codigoBarras.substring(35, 36).trim() + " "
		    + codigoBarras.substring(36, 47).trim() + "-" + codigoBarras.substring(47, 48);
	}

	return retorno;
    }

    /**
     * Troca letrar por numeros, na seguinte sequencia: A - 1 B - 2 C - 3 D - 4
     * . . . Z - 26
     * 
     * @param string
     *            string a ser convertida
     * @return long com a representacao
     */
    public static double convertendoLetraParaNumeros(String string) {

	char[] hidrometro = string.toUpperCase().toCharArray();
	String retorno = "";

	for (int i = 0; i < hidrometro.length; i++) {

	    if (hidrometro[i] == 'A') {
		retorno += "1";
	    } else if (hidrometro[i] == 'B') {
		retorno += "2";
	    } else if (hidrometro[i] == 'C') {
		retorno += "3";
	    } else if (hidrometro[i] == 'D') {
		retorno += "4";
	    } else if (hidrometro[i] == 'E') {
		retorno += "5";
	    } else if (hidrometro[i] == 'F') {
		retorno += "6";
	    } else if (hidrometro[i] == 'G') {
		retorno += "7";
	    } else if (hidrometro[i] == 'H') {
		retorno += "9";
	    } else if (hidrometro[i] == 'I') {
		retorno += "9";
	    } else if (hidrometro[i] == 'J') {
		retorno += "10";
	    } else if (hidrometro[i] == 'K') {
		retorno += "11";
	    } else if (hidrometro[i] == 'L') {
		retorno += "12";
	    } else if (hidrometro[i] == 'M') {
		retorno += "13";
	    } else if (hidrometro[i] == 'N') {
		retorno += "14";
	    } else if (hidrometro[i] == 'O') {
		retorno += "15";
	    } else if (hidrometro[i] == 'P') {
		retorno += "16";
	    } else if (hidrometro[i] == 'Q') {
		retorno += "17";
	    } else if (hidrometro[i] == 'R') {
		retorno += "18";
	    } else if (hidrometro[i] == 'S') {
		retorno += "19";
	    } else if (hidrometro[i] == 'T') {
		retorno += "20";
	    } else if (hidrometro[i] == 'U') {
		retorno += "21";
	    } else if (hidrometro[i] == 'V') {
		retorno += "22";
	    } else if (hidrometro[i] == 'W') {
		retorno += "23";
	    } else if (hidrometro[i] == 'X') {
		retorno += "24";
	    } else if (hidrometro[i] == 'Y') {
		retorno += "25";
	    } else if (hidrometro[i] == 'Z') {
		retorno += "26";
	    } else if (hidrometro[i] == '\\') {
		retorno += "27";
	    } else if (hidrometro[i] == '/') {
		retorno += "28";
	    } else if (hidrometro[i] == ' ') {
		retorno += "29";
	    } else if (hidrometro[i] == '.') {
		retorno += "30";
	    } else if (hidrometro[i] == ',') {
		retorno += "31";
	    } else if (hidrometro[i] == '#') {
		retorno += "32";
	    } else if (hidrometro[i] == '-') {
		retorno += "33";
	    } else if (hidrometro[i] == ';'){
		retorno += "34";
	    } else if (hidrometro[i] == '`'){
		retorno += "35";		
	    } else if (hidrometro[i] == 'À'){
		retorno += "36";
	    } else if (hidrometro[i] == '='){
		retorno += "37";
	    } else {
		retorno += hidrometro[i];
	    }
	}

	double retornoLong = 0d;
	try {
	    if (retorno != null && !retorno.equals("")) {
		retornoLong = Double.parseDouble(retorno);
	    }
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	    System.out.println("Hidrometro caractere: "+ retorno);
	    Util.perguntarAcao( "Número do Hidrometro com problema: " + string + ". Visualizado ?", false, false );
	    throw e;
	}

	return retornoLong;
    }

    public static int getMes(Date date) {
	Calendar dataCalendar = Calendar.getInstance();
	dataCalendar.setTime(date);

	return (dataCalendar.get(Calendar.MONTH) + 1);
    }

    public static int getAno(Date date) {
	Calendar dataCalendar = Calendar.getInstance();
	dataCalendar.setTime(date);

	return dataCalendar.get(Calendar.YEAR);
    }

    public static int getAnoMes(Date date) {
	int mes = getMes(date);
	String sMes = mes + "";
	if (sMes.length() == 1) {
	    sMes = "0" + sMes;
	}
	int ano = getAno(date);

	return Integer.parseInt(ano + "" + sMes);
    }

    public static String getMesAno(String anoMes) {
    	String mes = anoMes.substring(4, 6);
    	String ano = anoMes.substring(0, 4);
    	return mes + ano;
        }

    /**
     * Retorna o IMEI (International Mobile Equipment Identifier) do
     * dispositivo.
     * 
     * @return O IMEI do dispositivo.
     */
    public static long getIMEI() {

	long imei = 0;

	// chamada proprietária da NOKIA
	// String strIMEI = System.getProperty("com.nokia.mid.imei");

	String strIMEI = "356211003394768";

	if (strIMEI != null) {
	    imei = Long.parseLong(strIMEI);
	}

	return imei;
    }

    /**
     * Recebe um número double que será arredondado para um inteiro, levando o
     * seguinte criterio: - Se a parte fracionada do numero for superior a 0,5
     * adicionamos 1 ao inteiro retornado, senão, retornamos apenas a parte
     * inteira sem o incremento
     * 
     * @author Bruno / Sávio
     * @date 09/12/2009
     * @param numero
     *            Número double para ser convertido para inteiro
     * @return
     */
    public static int arredondar(double numero) {
	int inteiro = (int) numero;
	double fracionado = numero - inteiro;

	if (fracionado >= .5) {
	    ++inteiro;
	}

	return inteiro;
    }

    /**
     * Transforma uma string de ISO-8859-1 para UTF-8
     * 
     * @param string
     * @return
     */
    public static String reencode(String string) {

	String retorno = "";

	try {
	    retorno = new String( string.getBytes("ISO-8859-1") );//, "UTF-8" );
	} catch (Exception e) {
	    retorno = string;
	}

	return retorno;
    }

    /**
     * Função que decide qual dos layouts será usado para a empresa atual
     * 
     * @param imovelSelecionado
     *            Imovel que será impresso
     * @return Caso aconteça algum erro
     */
    public static boolean imprimirConta(int tipoImpressao) {
	boolean error = false;

	try {
// Daniel
		if (ImovelReg11.getInstancia().getCodigoEmpresaFebraban().equals(Constantes.CODIGO_FEBRABAN_COSANPA)){
			
			if ( Configuracao.getInstancia().getTipoPapel() == Constantes.PAPEL_CENTAURO ){
			    error = Util.enviarComandoImpressora(ImpressaoContaCosanpa.getInstancia(ControladorImoveis.getInstancia().getImovelSelecionado()).imprimirConta());
			} else if ( Configuracao.getInstancia().getTipoPapel() == Constantes.PAPEL_REGISPEL ){
			    error = Util.enviarComandoImpressora(ImpressaoContaCosanpa.getInstancia(ControladorImoveis.getInstancia().getImovelSelecionado()).imprimirConta());
			}
			
			// Daniel - Notificação de débito
//			if ( Configuracao.getInstancia().getTipoPapel() == Constantes.PAPEL_CENTAURO ){
//			    error = Util.enviarComandoImpressora(ImpressaoContaCosanpa.getInstancia(ControladorImoveis.getInstancia().getImovelSelecionado()).imprimirNotificacaoDebito());
//			} else if ( Configuracao.getInstancia().getTipoPapel() == Constantes.PAPEL_REGISPEL ){
//			    error = Util.enviarComandoImpressora(ImpressaoContaCosanpa.getInstancia(ControladorImoveis.getInstancia().getImovelSelecionado()).imprimirNotificacaoDebito());
//			}
	    	
	    }	    
	    if (!error) {
	    	ControladorImoveis.getInstancia().getImovelSelecionado().setIndcImovelImpresso(Constantes.SIM);

	    	ControladorImoveis.getInstancia().getImovelSelecionado().setQuantidadeContasImpressas(1+(ControladorImoveis.getInstancia().getImovelSelecionado().getQuantidadeContasImpressas()));
//	    	ControladorImoveis.getInstancia().getImovelSelecionado().getValoresContasImpressas().addElement(String.valueOf(ControladorImoveis.getInstancia().getImovelSelecionado().getValorConta()));
	    	
	    	System.out.println("Quantidade de vezes impressas: " + ControladorImoveis.getInstancia().getImovelSelecionado().getQuantidadeContasImpressas());
	    	
	    	// Daniel - guarda a data da impressao da conta de imovel nao-medido. Já que não possui data de leitura.
	    	if (ControladorImoveis.getInstancia().getImovelSelecionado().getRegistro8(Constantes.LIGACAO_AGUA) == null && 
	    		ControladorImoveis.getInstancia().getImovelSelecionado().getRegistro8(Constantes.LIGACAO_POCO) == null){
		    	
	    		ControladorImoveis.getInstancia().getImovelSelecionado().setDataImpressaoNaoMedido(Util.dataAtual());
	    	}
	    	
	    	Repositorio.salvarObjeto(ControladorImoveis.getInstancia().getImovelSelecionado());

			// Daniel - remove imovel da lista de imoveis pendentes
			Configuracao.getInstancia().getIdsImoveisPendentes().removeElement( new Integer(ControladorImoveis.getInstancia().getImovelSelecionado().getId()) );
			
			if (!Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(ControladorImoveis.getInstancia().getImovelSelecionado().getId()) )) {
				Configuracao.getInstancia().getIdsImoveisConcluidos().addElement( new Integer(ControladorImoveis.getInstancia().getImovelSelecionado().getId()) );
			}

			// caso seja imovel impresso pela opcao de imprimir todos os nao-hidrometrados
			// E imovel possui endereço de entrega alternativo
			// remove este imovel da lista de imoveis com endereço de entrega. 
			if (tipoImpressao == Constantes.IMPRIME_IMOVEIS_FIXOS){			
				// Daniel - Remove id da lista dos imoveis fixos (nao-hidrometrados).    			
				Configuracao.getInstancia().getIdsNaoHidrometrados().removeElementAt(0);
				
//				if(	Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(ControladorImoveis.getInstancia().getImovelSelecionado().getId())) ){
//					Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().removeElement( new Integer(ControladorImoveis.getInstancia().getImovelSelecionado().getId()) );
//				}
			
			}else if (tipoImpressao == Constantes.IMPRIME_TODOS_IMOVEIS_CONSUMO_MEDIO){
				
//				if(	Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(ControladorImoveis.getInstancia().getImovelSelecionado().getId())) ){
//					Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().removeElement( new Integer(ControladorImoveis.getInstancia().getImovelSelecionado().getId()) );
//				}				
			}
	    }
	} catch (Exception e) {
	    Util.mostrarErro("Erro ao imprimir a conta", e);
	}

	return error;
    }
    
    /**
     * Função que decide qual dos layouts será usado para a empresa atual
     * 
     * @param imovelSelecionado
     *            Imovel que será impresso
     * @return Caso aconteça algum erro
     */
    public static boolean imprimirNotificacaoDebito() {
	boolean error = false;
//Daniel
	try {
	    if (ImovelReg11.getInstancia().getCodigoEmpresaFebraban().equals(Constantes.CODIGO_FEBRABAN_COMPESA)) {
		
//		Configuracao conf = Configuracao.getInstancia();
//		
//		if ( Configuracao.getInstancia().getTipoPapel() == Constantes.PAPEL_CENTAURO ){
//		    if ( imovelSelecionado.getValorDebitosAnteriores() > 0 ){
//			error = Util.enviarComandoImpressora(ImpressaoContaCompesaCentauro.getInstancia(imovelSelecionado).imprimirNotificacaoDebito());
//		    }
//		    
//		} else if ( Configuracao.getInstancia().getTipoPapel() == Constantes.PAPEL_REGISPEL ){
//		    if ( imovelSelecionado.getValorDebitosAnteriores() > 0 ){
//			error = Util.enviarComandoImpressora(ImpressaoContaCompesaRegispel.getInstancia(imovelSelecionado).imprimirNotificacaoDebito());
//		    }		    
//		}
//Daniel
	    } else if (ImovelReg11.getInstancia().getCodigoEmpresaFebraban().equals(Constantes.CODIGO_FEBRABAN_COSANPA)){
	    	
			if ( Configuracao.getInstancia().getTipoPapel() == Constantes.PAPEL_CENTAURO ){
			    if ( ControladorImoveis.getInstancia().getImovelSelecionado().getValorDebitosAnteriores() > 0 ){
			    	error = Util.enviarComandoImpressora(ImpressaoContaCosanpa.getInstancia(ControladorImoveis.getInstancia().getImovelSelecionado()).imprimirNotificacaoDebito());
			    }
			    
			} else if ( Configuracao.getInstancia().getTipoPapel() == Constantes.PAPEL_REGISPEL ){
			    if ( ControladorImoveis.getInstancia().getImovelSelecionado().getValorDebitosAnteriores() > 0 ){
			    	error = Util.enviarComandoImpressora(ImpressaoContaCosanpa.getInstancia(ControladorImoveis.getInstancia().getImovelSelecionado()).imprimirNotificacaoDebito());
			    }		    
			}	    	
	    } else if (ImovelReg11.getInstancia().getCodigoEmpresaFebraban().equals(Constantes.CODIGO_FEBRABAN_CAERN)) {
//			if ( imovelSelecionado.getValorDebitosAnteriores() > 0 ){
//			    error = Util.enviarComandoImpressora(ImpressaoContaCaern.getInstancia(imovelSelecionado).imprimirNotificacaoDebito());
//			}
	    }
	} catch (Exception e) {
	    Util.mostrarErro("Erro ao imprimir a conta", e);
	}

	return error;
    }
    

    /**
     * Função que decide qual dos layouts será usado para a empresa atual
     * 
     * @param imovelSelecionado
     *            Imovel que será impresso
     * @return Caso aconteça algum erro
     */
    public static boolean imprimirExtratoConsumoMacroMedidor(ImovelConta imovelSelecionado) {

	boolean error = false;

//Daniel
	try {
	    if (ImovelReg11.getInstancia().getCodigoEmpresaFebraban().equals(Constantes.CODIGO_FEBRABAN_COSANPA)){
			
			if ( Configuracao.getInstancia().getTipoPapel() == Constantes.PAPEL_CENTAURO ){
			    error = Util.enviarComandoImpressora(ImpressaoContaCosanpa.getInstancia(imovelSelecionado).imprimirExtratoConsumoMacroMedidor());
			} else if ( Configuracao.getInstancia().getTipoPapel() == Constantes.PAPEL_REGISPEL ){
			    error = Util.enviarComandoImpressora(ImpressaoContaCosanpa.getInstancia(imovelSelecionado).imprimirExtratoConsumoMacroMedidor());
			}	    	
	    }
	} catch (Exception e) {
	    Util.mostrarErro("Erro ao imprimir a conta", e);
	}

	return error;
    }

    public static String retirarCaracteresString(String string, String caractere) {

	if (string != null && caractere != null) {

	    int tamanhoString = string.length();
	    String stringFinal = "";

	    for (int i = 0; i < tamanhoString; i++) {
		char c = string.charAt(i);

		if (String.valueOf(c).equals(caractere) == false) {
		    stringFinal += String.valueOf(c);
		}
	    }
	    
	    return stringFinal;
	    
	} else {
	    return null;
	}
    }

    public static boolean validarValor(double valorInicial, int valorFinal) {

	boolean validacao = true;

	if (valorInicial > valorFinal) {

	    validacao = false;

	}
	return validacao;
    }
    
    public static boolean validarDataAtual(){
	Date dataAtual = new Date();
	String data = Util.formatarDataSemHora(dataAtual);
	String dataFormatada = Util.formatarDataPTBR(data);
	System.out.println("Data = " + dataFormatada);

	if (Util.perguntarAcao("A data atual é: " + dataFormatada
		+ ". Confirma?", false, false)) {
	    return true;
	} else {
	    Util.mensagemAviso("Alterar Data!", "Altere a data"
		    + "do celular antes de iniciar a aplicacao!");
	}

	return false;	
    }
    
    public static InputStream inflateFile(InputStream is, int tamanhoInput) throws IOException {
	
	DataInputStream disArquivoCompactado = new DataInputStream(is);
	byte[] arrayArquivoCompactado = new byte[ tamanhoInput ];
	disArquivoCompactado.readFully(arrayArquivoCompactado);
	arrayArquivoCompactado = GZIP.inflate(arrayArquivoCompactado);
	
	ByteArrayInputStream byteArray = new ByteArrayInputStream(arrayArquivoCompactado);
	
	disArquivoCompactado.close();
	disArquivoCompactado = null;
	arrayArquivoCompactado = null;
	
	return byteArray;	
    }
    
    public static Boolean validarFalhaCarregamento(){
	
	Boolean falhaCarregamento;
	
	falhaCarregamento = Configuracao.getInstancia().getSucessoCarregamento();
	
	return falhaCarregamento;
	
    }
    
    /**
     * 
     * Divide uma String em várias partes, dependendo
     * do tamanho máximo permitido
     * 
     * @author Bruno Barros
     * @date 25/03/2010
     * @param mensagem Mensagem a ser quebrada
     * @param max máximo de caracteres por linha
     * @return String quebradas por linhas
     * 
     */
    public static String[] dividirString( String mensagem, int max ){
	
	// Encontramos em quantas strings precisaremos dividir
	short qtdLinhas = (short) ( mensagem.length() / max );
	
	// Verificamos se sobrou alguma coisa para a ultima linha
	if ( mensagem.length() % max != 0 ){
	    qtdLinhas++;
	}
	
	String[] retorno = new String[ qtdLinhas ];
	int limiteString = mensagem.length();
	
	for ( int i = 0; i < qtdLinhas; i++ ){
	    int inicio = max*i;
	    int fim = ( max*(i+1) );
	    
	    retorno[i] = mensagem.substring( inicio , ( fim > limiteString ? limiteString : fim )  );
	}
	
	return retorno;	
    }
    
    public static int divideDepoisMultiplica(int numerador, int denominador,
		int numeroMultiplicado) {

	double resultado = 0;
	double numeradorDouble = numerador;
	double denominadorDouble = denominador;
	
	numeradorDouble = numeradorDouble * 10000;

	denominadorDouble = denominadorDouble * 10000;

	 resultado = Util.arredondar(numeradorDouble / denominadorDouble, 4);
	
	resultado = resultado * numeroMultiplicado;

	return ( int )Util.arredondar(resultado,0);

    }
    
    public static int quantidadeDiasMes(Calendar data){
	
	int mes = data.get(Calendar.MONTH);
	int ano = data.get(Calendar.YEAR);
	int qtdDiasMes = 0;
	
	if(mes == Calendar.JANUARY){
	    qtdDiasMes = 31;
	}
	
	else if(mes == Calendar.FEBRUARY){
	    
	    if(verificarAnoBissexto(ano)){
		qtdDiasMes = 29;
	    }else{
		qtdDiasMes = 28;
	    }
	}
	
	else if(mes == Calendar.MARCH){
	    qtdDiasMes = 31;
	}
	
	else if(mes == Calendar.APRIL){
	    qtdDiasMes = 30;
	}
	
	else if(mes == Calendar.MAY){
	    qtdDiasMes = 31;
	}
	
	else if(mes == Calendar.JUNE){
	    qtdDiasMes = 30;
	}
	
	else if(mes == Calendar.JULY){
	    qtdDiasMes = 31;
	}
	
	else if(mes == Calendar.AUGUST){
	    qtdDiasMes = 30;
	}
	
	else if(mes == Calendar.SEPTEMBER){
	    qtdDiasMes = 31;
	}
	
	else if(mes == Calendar.OCTOBER){
	    qtdDiasMes = 30;
	}
	
	else if(mes == Calendar.NOVEMBER){
	    qtdDiasMes = 31;
	}
	
	else if(mes == Calendar.DECEMBER){
	    qtdDiasMes = 30;
	}
	
	return qtdDiasMes;
    }
    
    public static boolean verificarAnoBissexto(int ano) {

	boolean anoBissexto = false;
	String anoString = String.valueOf(ano);
	int ultimosDigitosAno = Integer.parseInt(anoString.substring(2,
		anoString.length()));

	if (ultimosDigitosAno != 00 && ano % 4 == 0) {
	    anoBissexto = true;
	}

	if (ultimosDigitosAno == 00 && ano % 400 == 0) {
	    anoBissexto = true;
	}

	return anoBissexto;
    }

    public static void inserirValoresStringRelatorio(String quadra, boolean inseridoAnormalidade, 
	    boolean inseridoLeitura){
	
	String valoresRelatorio =  DadosRelatorio.getInstancia().valoresRelatorio;
	String quadraAlterada =  null;
	int quadraInt = 0;
	int total = 0;
	int visitados = 0;
	int naoVisitados = 0;
	
	int indice = 0;
	if (valoresRelatorio.indexOf(quadra) != -1){
		indice = valoresRelatorio.indexOf(quadra);
		
	}
	String quadraAlteracao =  valoresRelatorio.substring(indice, indice + 18);
	//String lidosLeitura = valoresRelatorio.substring(valoresRelatorio.indexOf("[" + String.valueOf(valoresRelatorio.charAt(1)) + "]"));
	String lidosLeitura = valoresRelatorio.substring(1,5);
	//String lidosAnormalidade = valoresRelatorio.substring(valoresRelatorio.indexOf("[" + String.valueOf(valoresRelatorio.charAt(5)) + "]"));
	String lidosAnormalidade = valoresRelatorio.substring(7,11);
	
	if(inseridoAnormalidade){
	    int lidos = Integer.parseInt(lidosAnormalidade) + 1;
	    valoresRelatorio = replace(valoresRelatorio, "[" + 
		    Util.adicionarZerosEsquerdaNumero(4, lidosAnormalidade) + "]", "[" + 
		    Util.adicionarZerosEsquerdaNumero(4, String.valueOf(lidos)) + "]");
	}
	
	if(inseridoLeitura){
	    int lidos = Integer.parseInt(lidosLeitura) + 1;
	    valoresRelatorio = replace(valoresRelatorio, "{" + 
		    Util.adicionarZerosEsquerdaNumero(4, lidosLeitura) + "}", "{" + 
		    Util.adicionarZerosEsquerdaNumero(4, String.valueOf(lidos)) + "}");
	}

	   quadraInt = Integer.parseInt(valoresRelatorio.substring(indice + 1 ,indice + 5));
	   total = Integer.parseInt(valoresRelatorio.substring(indice + 6,indice + 10));
	   visitados = Integer.parseInt(valoresRelatorio.substring(indice + 10, indice + 14)) + 1;
	   naoVisitados = Integer.parseInt(valoresRelatorio.substring(indice + 14, indice + 18)) - 1;
	   
	   if(naoVisitados < 0 ){
	       naoVisitados = 0;
	   }
	   
	   if(visitados < 0){
	       visitados = 0;
	   }
	   
	   quadraAlterada = "(" + Util.adicionarZerosEsquerdaNumero(4, String.valueOf(quadraInt)) + ")" + 
	   	Util.adicionarZerosEsquerdaNumero(4, String.valueOf(total)) + 
	   	Util.adicionarZerosEsquerdaNumero(4, String.valueOf(visitados)) + 
	   	Util.adicionarZerosEsquerdaNumero(4, String.valueOf(naoVisitados));
	   
	   valoresRelatorio = replace(valoresRelatorio, quadraAlteracao, quadraAlterada);
	   
	   DadosRelatorio.getInstancia().valoresRelatorio =  valoresRelatorio;
    }
    
    public static void inserirValoresStringRelatorioCarregamento(String quadra, String quadraAlteracao, boolean inseridoAnormalidade, 
	    boolean inseridoLeitura){
	
	String valoresRelatorio =  DadosRelatorio.getInstancia().valoresRelatorio;
	String quadraAlterada =  null;
	int quadraInt = 0;
	int total = 0;
	int visitados = 0;
	int naoVisitados = 0;
	
	   int indice = valoresRelatorio.indexOf(quadra);
	   quadraInt = Integer.parseInt(valoresRelatorio.substring(indice + 1 ,indice + 5));
	   total = Integer.parseInt(valoresRelatorio.substring(indice + 6,indice + 10)) + 1;
	   visitados = Integer.parseInt(valoresRelatorio.substring(indice + 10, indice + 14));
	   naoVisitados = Integer.parseInt(valoresRelatorio.substring(indice + 14, indice + 18)) + 1;
	   
	   quadraAlterada = "(" + Util.adicionarZerosEsquerdaNumero(4, String.valueOf(quadraInt)) + ")" + 
	   	Util.adicionarZerosEsquerdaNumero(4, String.valueOf(total)) + 
	   	Util.adicionarZerosEsquerdaNumero(4, String.valueOf(visitados)) +
	   	Util.adicionarZerosEsquerdaNumero(4, String.valueOf(naoVisitados));
	   
	   valoresRelatorio = replace(valoresRelatorio, quadraAlteracao, quadraAlterada);
	   
	   DadosRelatorio.getInstancia().valoresRelatorio = valoresRelatorio;
    }
    
    public static void inserirValoresStringRelatorioConsumoNulo(String quadra, boolean inseridoAnormalidade, 
	    boolean inseridoLeitura){
	
	String valoresRelatorio = DadosRelatorio.getInstancia().valoresRelatorio;
	String quadraAlterada = null;
	int quadraInt = 0;
	int total = 0;
	int visitados = 0;
	int naoVisitados = 0;

	int indice = valoresRelatorio.indexOf(quadra);
	String quadraAlteracao = valoresRelatorio.substring(indice, indice + 18);
	String lidosLeitura = valoresRelatorio.substring(1, 5);
	String lidosAnormalidade = valoresRelatorio.substring(7, 11);

	if (inseridoAnormalidade) {
	    int lidos = Integer.parseInt(lidosAnormalidade) - 1;
	    valoresRelatorio = replace(valoresRelatorio, "["
		    + Util.adicionarZerosEsquerdaNumero(4, lidosAnormalidade) + "]", "[" + 
		    Util.adicionarZerosEsquerdaNumero(4, String.valueOf(lidos)) + "]");
	}

	if (inseridoLeitura) {
	    int lidos = Integer.parseInt(lidosLeitura) - 1;
	    valoresRelatorio = replace(valoresRelatorio, "{" + Util.adicionarZerosEsquerdaNumero(4, lidosLeitura)
		    + "}", "{" + Util.adicionarZerosEsquerdaNumero(4, String.valueOf(lidos)) + "}");
	}

	quadraInt = Integer.parseInt(valoresRelatorio.substring(indice + 1, indice + 5));
	total = Integer.parseInt(valoresRelatorio.substring(indice + 6, indice + 10));
	visitados = Integer.parseInt(valoresRelatorio.substring(indice + 10, indice + 14)) - 1;
	naoVisitados = Integer.parseInt(valoresRelatorio.substring(indice + 14, indice + 18)) + 1;

	if(naoVisitados < 0) {
	    naoVisitados = 0;
	}
	
	if(visitados < 0 ){
	    visitados = 0;
	}

	quadraAlterada = "(" + Util.adicionarZerosEsquerdaNumero(4, String.valueOf(quadraInt)) + ")"
		+ Util.adicionarZerosEsquerdaNumero(4, String.valueOf(total))
		+ Util.adicionarZerosEsquerdaNumero(4, String.valueOf(visitados))
		+ Util.adicionarZerosEsquerdaNumero(4, String.valueOf(naoVisitados));

	valoresRelatorio = replace(valoresRelatorio, quadraAlteracao, quadraAlterada);

	DadosRelatorio.getInstancia().valoresRelatorio = valoresRelatorio;
    }
    
    
    public static String replace(String text, String searchStr, String replacementStr){
	 // String buffer to store str
	 StringBuffer sb = new StringBuffer();
	 
	 // Search for search
	 int searchStringPos = text.indexOf(searchStr);  
	 int startPos = 0;  
	 int searchStringLength = searchStr.length();
	 
	 // Iterate to add string
	 while (searchStringPos != -1) {
	   sb.append(text.substring(startPos, searchStringPos)).append(replacementStr);
	   startPos = searchStringPos + searchStringLength;
	   searchStringPos = text.indexOf(searchStr, startPos);
	 }
	 
	 // Create string
	 sb.append(text.substring(startPos,text.length()));

	 return sb.toString();
    }
    
    /**
     * 
     * Ordena um vetor de INTEIROS
     * 
     * @author Bruno Barros
     * @date 12/08/2010
     * 
     * @param vec - Vetor a ser ordenado
     * 
     */
    public static void bubbleSort(Vector vec) {
	for (int i = 0; i < vec.size(); i++) {
	    for (int j = 0; j < vec.size() - (1 + i); j++) {
		Integer t1 = (Integer) vec.elementAt(j);
		Integer t2 = (Integer) vec.elementAt(j + 1);

		if (t1.intValue() > t2.intValue()) {
		    vec.setElementAt(t2, j);
		    vec.setElementAt(t1, j + 1);
		}
	    }
	}
    } 

    /**
     * 
     * Verifica se existe acesso a internet
     * Tentando abrir conexao a http://200.178.173.131:8080/gsan/
     * 
     * @author Daniel Zaccarias
     * @date 20/01/2011
     * 
     */
    public static boolean hasInternetAccess() {
    	boolean hasConnection = false;
	    try {
			// Abrimos a conexão
//	    	HttpConnection conexao = (HttpConnection) Connector.open("http://www.google.com.br");
	    	HttpConnection conexao = (HttpConnection) Connector.open("http://200.178.173.131:8080/gsan/");

			// lê a resposta do servidor
			int rcLocal = conexao.getResponseCode();

			// Verificamos se a resposta da conexão foi ok
			if (rcLocal == HttpConnection.HTTP_OK) {
				hasConnection = true;			
			}

	    } catch (IOException e) {
		    	mostrarErro("Acesso à Internet Indisponível! Por favor, verifique a configuração de acesso à internet.");				
	    } catch (SecurityException se) {
		    	mostrarErro("Acesso a internet nao  permitido!");		    		    	
		}
	    return hasConnection;
    } 

}


