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

package com.ipad.background;

import com.ipad.component.Progress;
import com.sun.lwuit.Display;

/**
* A tool allowing to respond to an event in the background possibly with
* progress indication inspired by Swings "SwingWorker" tool. This class
* should be used from event dispatching code to prevent the UI from blocking.
* State can be stored in this class the separate thread and it can be used by
* the finish method which will be invoked after running.
*
* @author Shai Almog
*/
public abstract class BackgroundTask {
	
	protected Progress p;
	private Thread t = 
		new Thread(new Runnable() {
	       public void run() {
	           if(Display.getInstance().isEdt()) {
	               taskFinished();
	           } else {
	               try{
	            	   performTask();
	               }catch (Exception e) {
					// TODO: handle exception
	            	   
	               }
	               Display.getInstance().callSerially(this);
	           }
	       }
	   });
	
	public void setP(Progress p) {
		this.p = p;
	}

	protected BackgroundTask(){
		super();
	}
	
	public BackgroundTask( Progress p ){
		super();
		this.p = p;
	}	

	/**
	* Start this task
	*/
	public final void start() {
	   if(Display.getInstance().isEdt()) {
	       taskStarted();
	   } else {
	       Display.getInstance().callSeriallyAndWait(new Runnable() {
	           public void run() {
	               taskStarted();
	           }
	       });
	   }	   

	   t.start();
	}

	/**
	* Invoked on the LWUIT EDT before spawning the background thread, this allows
	* the developer to perform initialization easily.
	*/
	public abstract void taskStarted();
	
	/**
	* Invoked on a separate thread in the background, this task should not alter
	* UI except to indicate progress.
	*/
	public abstract void performTask();

	/**
	* Invoked on the LWUIT EDT after the background thread completed its
	* execution.
	*/
	public void taskFinished(){
	    // Invocamos o Garbage Colector para destruir o que não é mais necessário
	    System.gc();
	}
	
	/**
	 * 
	 * Seta a prioridade da Thread
	 * 
	 * @param priority prioridade da Thread a ser criada
	 */
	public void setPriority( int priority ){
		t.setPriority( priority );
	}
}
