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

import net.sourceforge.floggy.persistence.Persistable;

import com.ipad.business.ControladorConta;
import com.ipad.business.ControladorImoveis;
import com.ipad.util.Constantes;


public class Consumo implements Persistable {
    
    private int consumoMedidoMes;
    private int consumoCobradoMes;
    private int consumoCobradoMesImoveisMicro;
    
    // Esse campo foi criado para que
    // possamos guardar o consumo que foi calculado
    // originalmente, sem o rateio. Isso é necessário
    // para que caso o seja necessário refazer o calculo
    // esse valor possa ser recuperado
    private int consumoCobradoMesOriginal;
    
    public int getConsumoCobradoMesOriginal() {
		return consumoCobradoMesOriginal;
	}

	public void setConsumoCobradoMesOriginal(int consumoCobradoMesOriginal) {
		this.consumoCobradoMesOriginal = consumoCobradoMesOriginal;
	}

	public int getConsumoCobradoMesImoveisMicro() {
		return consumoCobradoMesImoveisMicro;
	}

	public void setConsumoCobradoMesImoveisMicro(int consumoCobradoMesImoveisMicro) {
		this.consumoCobradoMesImoveisMicro = consumoCobradoMesImoveisMicro;
	}

    private int leituraAtual;
    private int tipoConsumo;
    private long diasConsumo;
    private int anormalidadeConsumo;
	private int anormalidadeLeituraFaturada;
    
    //Constantes
    public static final int CONSUMO_ANORM_INFORMADO         = 2;
    public static final int CONSUMO_ANORM_BAIXO_CONSUMO     = 4;
    public static final int CONSUMO_ANORM_ESTOURO           = 5;
    public static final int CONSUMO_ANORM_ALTO_CONSUMO      = 6;
    public static final int CONSUMO_ANORM_LEIT_MENOR_PROJ   = 7;
    public static final int CONSUMO_ANORM_LEIT_MENOR_ANTE   = 8;
    public static final int CONSUMO_ANORM_HIDR_SUBST_INFO   = 9;
    public static final int CONSUMO_ANORM_LEITURA_N_INFO    = 10;
    public static final int CONSUMO_ANORM_ESTOURO_MEDIA     = 11;
    public static final int CONSUMO_MINIMO_FIXADO           = 12;
    public static final int CONSUMO_ANORM_FORA_DE_FAIXA     = 13;
    public static final int CONSUMO_ANORM_HIDR_SUBST_N_INFO = 14;
    public static final int CONSUMO_ANORM_VIRADA_HIDROMETRO = 16;
    public static final int ANORMALIDADE_LEITURA = 17;
    
    
    public int getAnormalidadeLeituraFaturada() {
		return anormalidadeLeituraFaturada;
	}

	public void setAnormalidadeLeituraFaturada(int anormalidadeLeituraFaturada) {
		this.anormalidadeLeituraFaturada = anormalidadeLeituraFaturada;
	}

	public Consumo() {
        super();
    }
    
    public Consumo(int consumoMedidoMes, int consumoCobradoMes,
            int leituraAtual, int tipoConsumo, int anormalidadeConsumo, int anormalidadeLeiruaFaturada ) {

    	super();
    	
        this.consumoMedidoMes = consumoMedidoMes;   
        this.consumoCobradoMes = consumoCobradoMes;  
        this.leituraAtual = leituraAtual;       
        this.tipoConsumo = tipoConsumo;        
        this.anormalidadeConsumo = anormalidadeConsumo;     
        this.anormalidadeLeituraFaturada = anormalidadeLeiruaFaturada;
    }

    public int getConsumoMedidoMes() {
        return consumoMedidoMes;
    }

    public void setConsumoCobradoMes(int consumoCobradoMes) {
        this.consumoCobradoMes = consumoCobradoMes;
    }

    public int getConsumoCobradoMes() {
        return consumoCobradoMes;
    }

    public void setConsumoMedidoMes(int consumoMedidoMes) {
        this.consumoMedidoMes = consumoMedidoMes;
    }

    public void setLeituraAtual(int leituraAtual) {
        this.leituraAtual = leituraAtual;
    }

    public void setTipoConsumo(int tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public void setAnormalidadeConsumo(int anormalidadeConsumo) {
        this.anormalidadeConsumo = anormalidadeConsumo;
    }

    /**
     * [SB0009] - Ajuste do Consumo para o Múltiplo da Quantidade de Economias
     */
    public void ajustar(int qtdEconomias, int leituraAnterior, int tipoMedicao) {
        
        // [SB0009] 1.
        int restoDiv = this.consumoCobradoMes % qtdEconomias;
        int leituraFaturadaAtual = 0;
//        ImovelConta imovel = ControladorImoveis.getInstancia().getImovelSelecionado();
        // [SB0009] 2.1.
        if(leituraAnterior != Constantes.NULO_INT){
          
          if(tipoMedicao == ControladorConta.LIGACAO_AGUA){
              
              if (ControladorImoveis.getInstancia().getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_AGUA) != null) {
        	 
        	 if(ControladorImoveis.getInstancia().getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_AGUA).
        		 getLeituraAtualFaturamento()!= Constantes.NULO_INT){
        	     
        	     	leituraFaturadaAtual = ControladorImoveis.getInstancia().getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_AGUA).
        	     		getLeituraAtualFaturamento() - restoDiv;
        	  
        	     	ControladorImoveis.getInstancia().getImovelSelecionado().
        	  		getRegistro8(ControladorConta.LIGACAO_AGUA).setLeituraAtualFaturamento(leituraFaturadaAtual);
        	   }
        	 
              }
              
          }else{
              
              if (ControladorImoveis.getInstancia().getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_POCO) != null) {
         	 
         	 if(ControladorImoveis.getInstancia().getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_POCO).
         		 getLeituraAtualFaturamento()!= Constantes.NULO_INT){
         	     
         	     	leituraFaturadaAtual = ControladorImoveis.getInstancia().getImovelSelecionado().getRegistro8(ControladorConta.LIGACAO_POCO).
         	     		getLeituraAtualFaturamento() - restoDiv;
         	  
         	     	ControladorImoveis.getInstancia().getImovelSelecionado().
         	  		getRegistro8(ControladorConta.LIGACAO_POCO).setLeituraAtualFaturamento(leituraFaturadaAtual);
         	   }
         	 
               }
          }
          
          
          if (this.getLeituraAtual() == leituraAnterior + this.consumoCobradoMes ) {
            this.leituraAtual = this.leituraAtual - restoDiv ;
          }
        }

        // [SB0009] 2.2.
        this.setConsumoCobradoMes( this.consumoCobradoMes - restoDiv) ;
    }

    public int getAnormalidadeConsumo() {
        return anormalidadeConsumo;
    }

    public int getTipoConsumo() {
        return tipoConsumo;
    }

    public int getLeituraAtual() {
        return leituraAtual;
    }

    public long getDiasConsumo() {
        return diasConsumo;
    }

    public void setDiasConsumo(long diasConsumo) {
        this.diasConsumo = diasConsumo;
    }
    
    
}
