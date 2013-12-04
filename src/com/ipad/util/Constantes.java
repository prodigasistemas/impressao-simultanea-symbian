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

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Font;

/**
 * 
 * @author Rafael Palermo de Araújo
 */
public class Constantes {

	public static final int IMPRESSAO_NORMAL = 0;
	public static final int IMPRIME_IMOVEIS_FIXOS = 1;
	public static final int IMPRIME_TODOS_IMOVEIS_CONSUMO_MEDIO = 2;
	
	public static final int NULO_INT = Integer.MIN_VALUE;
    public static final short NULO_SHORT = Short.MIN_VALUE;
    public static final String NULO_STRING = "";
    public static final double NULO_DOUBLE = Double.MIN_VALUE;

    public static final int MENSAGEM_AVISO = 4000;

    public static final int TIME_OUT_DIALOG = 6000;

    public static final int BGTRANSPARENCY_DIALOG = 200;

    public static final String[] CONSUMO = { "INDEFINIDO (0)", "REAL (1)",
	    "AJUSTADO (2)", "MÉDIA HIDR. (3)", "INFORMADO (4)",
	    "NAO MEDIDO (5)", "ESTIMADO (6)", "CONSUMO_TIPO_MINIMO_FIX(7)",
	    "CONSUMO_TIPO_SEM(8)", "CONSUMO_TIPO_MEDIA_IMOV(9)" };

    public static final String[] ANORM_CONSUMO = { "SEM ANORMALIDADE(0)",
	    "CONSUMO ALTERADO(1)", "CONSUMO INFORMADO(2)",
	    "CONSUMO RETIFICADO(3)", "BAIXO CONSUMO (4)", "ESTOURO (5)",
	    "ALTO CONSUMO (6)", "LEITURA MENOR QUE PROJETADA (7)",
	    "LEITURA MENOR QUE ANTERIOR (8)", "HIDRO. SUBSTITUÍDO INFO (9)",
	    "LEITURA não INFORMADA (10)", "ESTOURO MÉDIA (11)", "(12)",
	    "FORA DE FAIXA (13)", "HIDR. SUBSTITUÍDO não INFO (14)",
	    "FATURAMENTO ANTECIPADO (15)", "VIRADA hidrômetro (16)" };

    public static final String STR_MENU_PRINCIPAL = "Menu Principal";
    public static final String STR_LISTA_DE_IMOVEIS = "Lista de imóveis";
    public static final String STR_INVERTER_ROTEIRO = "Inverter Roteiro";
    public static final String STR_CONSULTAS = "Consultas";
    public static final String STR_RELATORIOS = "Relatórios";
    public static final String STR_FINALIZAR_ROTEIRO = "Finalizar Roteiro";
    public static final String STR_SOBRE = "Sobre...";
    public static final String STR_APAGAR_TUDO = "Apagar Tudo";
    public static final String STR_ERRO = "Erro";
    public static final String STR_AVISO = "Aviso";
    public static final String STR_DE = " de ";
    public static final String STR_ATENCAO = "Atenção";

    public static final int SIM = 1;
    public static final int NAO = 2;

    public static final String MSG_DESEJA_APAGAR_TUDO = "Deseja apagar todos os dados?";

    public static final String MSG_ROTEIRO_FINALIZADO = "Roteiro finalizado com sucesso";

    public static final String MSG_SERV_FORA_DO_AR = "Servidor fora do ar";

    public static final String[] STATUS_MESSAGES = {
	    "abrindo conexão com o servidor...",
	    "enviando requisição ao servidor...",
	    "recebendo dados do servidor...", "lendo dados de entrada...",
	    "leitura terminada", "salvando arquivo...", null };

    // Situações de ligação
    public static final String LIGADO = "3";
    public static final String CORTADO = "5";

    /**
     * Comando OK.
     */
    public static final Command CMD_OK = new Command("OK", Command.BACK, 1);

    /**
     * Comando PROXIMO.
     */
    public static final Command CMD_PROXIMO = new Command("PROXIMO",
	    Command.SCREEN, 2);

    /**
     * Comando ANTERIOR.
     */
    public static final Command CMD_ANTERIOR = new Command("ANTERIOR",
	    Command.SCREEN, 2);

    /**
     * Comando IMPRIMIR.
     */
    public static final Command CMD_IMPRIMIR = new Command("IMPRIMIR",
	    Command.BACK, 1);

    /**
     * Comando PROXIMO NAO LIDO.
     */
    public static final Command CMD_PROXIMO_NAO_LIDO = new Command(
	    "PRÓXIMO não LIDO", Command.SCREEN, 2);

    /**
     * Comando CONSULTAS.
     */
    public static final Command CMD_CONSULTAS = new Command("CONSULTAS",
	    Command.SCREEN, 2);

    /**
     * Comando INVERTER ROTEIRO.
     */
    public static final Command CMD_INVERTER_ROTEIRO = new Command(
	    "INVERTER ROTEIRO", Command.SCREEN, 2);

    /**
     * Comando Conta.
     */
    public static final Command CMD_CONTA = new Command("CONTA", Command.BACK,
	    1);

    /**
     * Comando poço.
     */
    public static final Command CMD_POCO = new Command("poço", Command.BACK, 1);

    /**
     * Comando SAIR.
     */
    public static final Command CMD_SAIR = new Command("SAIR", Command.EXIT, 2);

    /**
     * Comando VOLTAR.
     */
    public static final Command CMD_VOLTAR = new Command("VOLTAR", Command.OK,
	    2);
    /**
     * Comando SIM.
     */
    public static final Command CMD_SIM = new Command("SIM", Command.BACK, 1);

    /**
     * Comando NAO.
     */
    public static final Command CMD_NAO = new Command("não", Command.OK, 1);

    /**
     * Comando RETRANSMITIR.
     */
    public static final Command CMD_RETRANSMITIR = new Command("RETRANSMITIR",
	    Command.SCREEN, 2);

    /**
     * Comando GERAR ARQUIVO.
     */
    public static final Command CMD_GERAR_ARQUIVO = new Command(
	    "GERAR ARQUIVO", Command.EXIT, 2);

    /**
     * Resource ID da imagem do ícone para a opção Lista de imóveis.
     */
    public static final byte RES_ID_IMG_IMOVEIS = 0;

    /**
     * Resource ID da imagem do ícone para a opção Inverter Roteiro.
     */
    public static final byte RES_ID_IMG_INVERTER = 1;

    /**
     * Resource ID da imagem do ícone para a opção Consultas;
     */
    public static final byte RES_ID_IMG_CONSULTAS = 2;

    /**
     * Resource ID da imagem do ícone para a opção Relatórios.
     */
    public static final byte RES_ID_IMG_RELATORIOS = 3;

    /**
     * Resource ID da imagem do ícone para a opção Gerar Arquivo.
     */
    public static final byte RES_ID_IMG_GERAR = 4;

    /**
     * Resource ID da imagem do ícone para a opção Apagar Tudo...
     */
    public static final byte RES_ID_IMG_APAGAR = 5;

    /**
     * Resource ID da imagem do ícone para a opção Sobre...
     */
    public static final byte RES_ID_IMG_SOBRE = 6;

    /**
     * Resource ID do arquivo de anormalidades.
     */
    public static final byte RES_ID_ARQ_ANORMALIDADES_SEM_HIDROMETRO = 8;

    /**
     * Resource ID da imagem do logotipo do Ministério das Cidades.
     */
    public static final byte RES_ID_IMG_LOGO_MINISTERIO = 9;

    /**
     * Resource ID da imagem do logotipo do GCom.
     */
    public static final byte RES_ID_IMG_LOGO_GCOM = 10;

    public static final Font FONT_ITALIC = Font.getFont(Font.FACE_SYSTEM,
	    Font.STYLE_ITALIC, Font.SIZE_SMALL);

    public static final com.sun.lwuit.Font FONT_SMALL = com.sun.lwuit.Font
	    .createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
		    Font.SIZE_SMALL);

    public static final com.sun.lwuit.Font FONT_BOLD_SMALL = com.sun.lwuit.Font
	    .createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD,
		    Font.SIZE_SMALL);

    public static final int SCREEN_WIDTH = 300;

    public static final int SECONDS_1_00 = 1000;

    /**
     * Valor constante padrão para a leitura inválida.
     */
    public static final int LEITURA_INVALIDA = -1;

    /**
     * Identificador da pesquisa por quadra.
     */
    public static final byte POR_QUADRA = 0;

    /**
     * Identificador da pesquisa por hidrômetro.
     */
    public static final byte POR_HIDROMETRO = 1;

    /**
     * Identificador da pesquisa por matrícula.
     */
    public static final byte POR_MATRICULA = 2;

    /**
     * Identificador da pesquisa por Sequencial de Rota.
     */
    public static final byte POR_SEQUENCIAL_DE_ROTA = 3;

    /**
     * Validate Error = ID_SEM_ERRO indica leitura com um valor válido.
     */
    public static final byte ID_OK = 2;

    public static final int ID_SEM_ERRO = Integer.MIN_VALUE;

    public static final int ID_ABRIU_DIRETORIO = ID_SEM_ERRO + 1;
    public static final int ID_ABRIU_ARQUIVO = ID_SEM_ERRO + 2;
    public static final int ID_BAIXOU_COM_SUCESSO = ID_SEM_ERRO + 3;
    public static final int ID_FINALIZOU_ROTEIRO = ID_SEM_ERRO + 4;
    public static final int ID_IMPRIMIU_COM_SUCESSO = ID_SEM_ERRO + 5;

    public static final int ID_AVISO_ROTEIRO_INVERTIDO = -2;
    public static final int ID_AVISO_ROTEIRO_PERCORRIDO = -1;
    /**
     * Validate Error = ID_ERRO_LEITURA_VALOR_NEGATIVO indica leitura com valor
     * negativo.
     */
    public static final int ID_ERRO_LEITURA_VALOR_NEGATIVO = 0;

    /**
     * Validate Error = ID_ERRO_LEITURA_FORA_DE_FAIXA indica leitura com valor
     * fora da faixa.
     */
    public static final int ID_ERRO_LEITURA_FORA_DE_FAIXA = 1;

    /**
     * Identificador de Erro de conexão.
     */
    public static final int ID_ERRO_CONEXAO = 2;

    /**
     * Identificador de Erro de Arquivo não Encontrado.
     */
    public static final int ID_ERRO_ARQUIVO_NAO_ENCONTRADO = 3;

    /**
     * Identificador de Erro do Servidor.
     */
    public static final int ID_ERRO_SERVIDOR = 4;

    public static final int ID_ERRO_QUADRA_NAO_ENCONTRADA = 5;

    public static final int ID_ERRO_HIDROMETRO_NAO_ENCONTRADO = 6;
    public static final int ID_ERRO_MATRICULA_NAO_ENCONTRADA = 7;
    public static final int ID_ERRO_CHAVE_EM_BRANCO = 8;
    public static final int ID_ERRO_ROTEIRO_NAO_PERCORRIDO = 9;
    public static final int ID_ERRO_GERAR_ARQUIVO = 10;
    public static final int ID_ERRO_SEM_ROTA = 11;
    public static final int ID_ERRO_ESCOLHER_ARQUIVO = 12;
    public static final int ID_ERRO_SEQUENCIAL_NAO_ENCONTRADA = 13;
    public static final int ID_ERRO_ANORMALIDADE_SEM_LEITURA = 14;
    public static final int ID_ERRO_ANORMALIDADE_COM_LEITURA = 15;
    public static final int ID_ERRO_FALTA_LEITURA_PARA_GERAR_CONTA = 16;

    public static final String[] MENSAGENS_AVISO = { "Roteiro percorrido",
	    "Roteiro invertido " + "\n" + "com sucesso", };

    public static final String[] MENSAGENS_ERRO = {
	    "Leitura com valor negativo", "Leitura fora de faixa",
	    "Servidor fora do ar ou URL incorreta", "Arquivo não encontrado\n",
	    "Erro do servidor\n", "Quadra não encontrada",
	    "hidrômetro não encontrada", "matrícula não encontrada",
	    "O campo chave está em branco", "Roteiro não percorrido",
	    "Erro ao gerar Arquivo de Saída",
	    "Nenhum Rota Disponível no Momento", "Servidor Fora do Ar",
	    "Sequencial de Rota não encontrado",
	    "Essa Anormalidade não Pode Ter Leitura",
	    "Informe a Leitura da Anormalidade",
	    "Falta Leitura para Gerar a Conta" };

    /**
     * Medição Tipo
     */
    public static final int LIGACAO_AGUA = 1;
    public static final int LIGACAO_POCO = 2;

    public static final String AGUA = "Água";
    public static final String POCO = "Poço";

    // Tipos de registro
    public static final int REGISTRO_TIPO_1 = 1;
    public static final int REGISTRO_TIPO_2 = 2;
    public static final int REGISTRO_TIPO_3 = 3;
    public static final int REGISTRO_TIPO_4 = 4;
    public static final int REGISTRO_TIPO_5 = 5;
    public static final int REGISTRO_TIPO_6 = 6;
    public static final int REGISTRO_TIPO_7 = 7;
    public static final int REGISTRO_TIPO_8 = 8;
    public static final int REGISTRO_TIPO_9 = 9;
    public static final int REGISTRO_TIPO_10 = 10;
    public static final int REGISTRO_TIPO_11 = 11;
    public static final int REGISTRO_TIPO_12 = 12;
    public static final int REGISTRO_TIPO_13 = 13;
    public static final int REGISTRO_TIPO_14 = 14;

    public static final int LIMITE_SUPERIOR_FAIXA_FINAL = 999999;

    // Opcoes Menu Principal
    public static final int LISTAR_IMOVEIS = 0;
    // public static final int INVERTER_ROTEIRO = 1;

    public static final int INDC_FINALIZAR_ROTEIRO = 2;
    public static final int INDC_FINALIZAR_ROTEIRO_INCOMPLETO = 6;
    public static final int INDC_FINALIZAR_ROTEIRO_TODOS_IMOVEIS = 7;

    public static final int FINALIZAR_ROTEIRO = 1;
    public static final int CONSULTAS = 2;
    public static final int RELATORIOS = 3;
    public static final int APAGAR_TUDO = 4;
    public static final int SELECIONAR_IMPRESSORA = 5;
    public static final int FINALIZAR_ROTEIRO_INCOMPLETO = 6;
    public static final int ENVIAR_TODOS_IMOVEIS_CONCLUIDOS_E_NAO_ENVIADOS = 7;
    public static final int SELECIONAR_PAPEL = 8;
    public static final int TORNAR_OFFLLINE = 9;
    public static final int GERAR_ARQUIVO_RETORNO_COMPLETO = 10;
    public static final int INFORMACOES_ROTA = 11;
//    public static final int ROTA_DE_MARCACAO = 12;
    public static final int IMPRIMIR_IMOVEIS_NAO_HIDROMETRADOS = 12;
    public static final int CALCULAR_E_IMPRIMIR_TODOS_IMOVEIS_MEDIA = 13;
    

    public static final String SENHA_ADMINISTRADOR = "mobile";
    public static final String SENHA_LEITURISTA = "apagar";

    // Tipos de situação de leitura
    public static final int LEITURA_REALIZADA = 0;
    public static final int LEITURA_CONFIRMADA = 1;

    /**
     * Identificador da requisição Cliente->Cliente de abertura do diretório
     * GComMovel.
     */
    public static final byte CC_ABRIR_DIRETORIO = 50;

    /**
     * Identificador da requisição Cliente->Cliente de abertura do arquivo de
     * com os imóveis do roteiro.
     */
    public static final byte CC_ABRIR_ARQUIVO_IMOVEIS = 51;

    /**
     * Identificador da requisição Cliente->Cliente de geração do arquivo.
     */
    public static final byte CC_GERAR_ARQUIVO = 52;

    /**
     * Identificador da requisição Servidor->Cliente de impressão de conta.
     * 
     * OBS.: Neste caso o dispositivo móvel é o servidor e a impressora o
     * cliente.
     */
    public static final byte SC_IMPRIMIR_CONTA = 100;
    /**
     * Identificador do status Abrindo conexão.
     */
    public static final int ABRINDO_CONEXAO = 0;

    /**
     * Identificador do status Enviando requisição.
     */
    public static final int ENVIANDO_REQUISICAO = 1;

    /**
     * Identificador do status Aguardando Dados.
     */
    public static final int AGUARDANDO_DADOS = 2;

    /**
     * Identificador do status Lendo Arquivo.
     */
    public static final int LENDO_ARQUIVO = 3;

    /**
     * Identificador do status Leitura Terminada.
     */
    public static final int LEITURA_TERMINADA = 4;

    /**
     * Identificador do status Escrevendo um Registro.
     */
    public static final int ESCREVENDO_REGISTRO = 5;

    /**
     * Identificador do status Fim de Escrita.
     */
    public static final int FIM_DE_ESCRITA = 6;

    public static final int QUEBRA_CONTAS_IMOVEL_CONDOMINIO = 5;

    public static final String CODIFICACAO = "UTF-8";

//Daniel
    /**
     * COD. FEBRABAN da COSANPA
     */
    public static final String CODIGO_FEBRABAN_COSANPA = "0022";
    /**
     * COD. FEBRABAN da COMPESA
     */
    public static final String CODIGO_FEBRABAN_COMPESA = "0018";

    /**
     * COD. FEBRABAN da CAER
     */
    public static final String CODIGO_FEBRABAN_CAER = "0004";

    /**
     * COD. FEBRABAN da CAERN
     */
    public static final String CODIGO_FEBRABAN_CAERN = "0006";


    public static final short PAPEL_REGISPEL = 0;
    public static final short PAPEL_CENTAURO = 1;

    public static final char TIPO_ARQUIVO_TXT = 'T';
    public static final char TIPO_ARQUIVO_GZ = 'G';

}