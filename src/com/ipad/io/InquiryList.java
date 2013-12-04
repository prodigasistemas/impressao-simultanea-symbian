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

import java.io.IOException;
import java.util.Vector;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

import net.java.dev.marge.inquiry.DeviceDiscoverer;
import net.java.dev.marge.inquiry.InquiryListener;
import net.java.dev.marge.inquiry.ServiceDiscoverer;
import net.java.dev.marge.inquiry.ServiceSearchListener;

import com.ipad.basic.Configuracao;
import com.ipad.business.ControladorImoveis;
import com.ipad.util.Constantes;
import com.ipad.util.Util;
import com.ipad.view.Abas;
import com.ipad.view.TelaMenuPrincipal;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;

public class InquiryList implements ActionListener, ServiceSearchListener,
	InquiryListener {

    private Command select;
    private Command stopOrBack;
    private Command cancel;
    private Command repetir;
    private Command voltar;
    private Vector devices;
    private List devicesList;
    private Form fmInquiry;

    private int dispositivosEncontrados = 0;
    private boolean imprimirContaImovelSelecionado = false;

    private static InquiryList instancia;

    public static InquiryList getInstancia() {
	if (instancia == null) {
	    instancia = new InquiryList();
	}

	return instancia;
    }

    public void criarTelaPesquisaDispositivos(
	    boolean imprimirContaImovelSelecionado) {

	this.imprimirContaImovelSelecionado = imprimirContaImovelSelecionado;

	fmInquiry = new Form("Buscando Dispositivos...");

	devicesList = new List();
	
	// ("Buscando...");
	fmInquiry.setLayout( new BorderLayout() );
	fmInquiry.addComponent( BorderLayout.CENTER, devicesList );
	this.select = new Command("Selecionar");
	this.devices = new Vector(5);
	this.cancel = new Command("Parar");
	this.repetir = new Command("Repetir");
	this.voltar = new Command("Voltar");
	fmInquiry.addCommand(this.stopOrBack = cancel);
	fmInquiry.setCommandListener(this);
	fmInquiry.show();

	new Thread() {

	    public void run() {
		try {
		    DeviceDiscoverer.getInstance().startInquiryGIAC(
			    InquiryList.this);
		} catch (BluetoothStateException e) {
		    e.printStackTrace();
		}
	    }
	}.start();
    }

    private InquiryList() {
    }

    public void deviceNotReachable() {
	fmInquiry.setTitle("Não encontrado");
    }

    public void serviceSearchCompleted(RemoteDevice remoteDevice,
	    ServiceRecord[] services) {

//	ImovelConta imovel = ControladorImoveis.getInstancia().getImovelSelecionado();

	// Gravamos o escolhido na memoria
	Configuracao.getInstancia().setBluetoothAddress(
		"btspp://" + remoteDevice.getBluetoothAddress() + ":1");
	Repositorio.salvarObjeto(Configuracao.getInstancia());
	
	//ControladorImoveis.getInstancia().carregarImovelSelecionado();

	// Verificamos se é imovel condominio
	if (!ControladorImoveis.getInstancia().getImovelSelecionado().isImovelCondominio()) {

	    if (imprimirContaImovelSelecionado) {
		fmInquiry.setTitle("Enviando comando.");
		Util.imprimirConta(Constantes.IMPRESSAO_NORMAL);
		fmInquiry.setTitle("Dados enviados com sucesso.");
		ControladorImoveis.getInstancia().proximo();
	    } else {
		if ( ControladorImoveis.getInstancia().getImovelSelecionado().getMatricula() == Constantes.NULO_INT ){		    
		    ControladorImoveis.getInstancia().carregarImovelSelecionado();
		}
	    }

	    Abas.getInstancia().criarAbas();
	} else {
	    Abas.getInstancia().criarAbas();
	    
	    if ( imprimirContaImovelSelecionado ){
		Abas.getInstancia().actionPerformed(
		    new ActionEvent(Abas.imprimirImovelCondominio));
	    }
	}
// Daniel - setting imovel to null
//	imovel = null;
    }

    public void serviceSearchError() {
    	Util.mostrarErro("Erro na busca!");
    }

    public void deviceDiscovered(RemoteDevice device, DeviceClass deviceClass) {
	/*
	 * Uma vez que pesquisa de disposidivos demanda algum tempo e nos já
	 * somos forçados a completar uma requisição inteira, nós não
	 * pesquisaremos por todos os tipos de dispositivos que não sejam do
	 * tipo Image. A classe desse dispositivo é 0x600, conforme definido no
	 * Bluetooth Assigned Numbers document.
	 */
	if (deviceClass.getMajorDeviceClass() == 0x600) {
	    
	     /* 
	     * Dispositivos de imagem podem ser um monitor, uma câmera um
	     * scanner, ou uma impressora. Se o dispositivo é uma impressora,
	     * então o bit 7 deve vir do minor device class de acordo com o
	     * Bluetooth Assigned Numbers document.
	     */
	     
	    if ((deviceClass.getMinorDeviceClass() & 0x80) != 0) {
		/*
		 * Now we know that it is a printer. Now we will verify that it
		 * has a rendering service on it. A rendering service may allow
		 * us to print. We will have to do a service search to get more
		 * information if a rendering service exists. If this device has
		 * a rendering service then bit 18 will be set in the major
		 * service classes.
		 */
		 
		if ((deviceClass.getServiceClasses() & 0x40000) != 0) {
		    this.devices.addElement(device);

		    this.dispositivosEncontrados = this.devices.size();

		    fmInquiry
			    .setTitle("Buscando... " + dispositivosEncontrados);
		    try {
			String nome = device.getFriendlyName(false);
			devicesList.addItem(nome);
		    } catch (IOException e) {
			Label nameException = new Label(device
				.getBluetoothAddress());
			fmInquiry.addComponent(nameException);
			e.printStackTrace();
		    }
		    fmInquiry.show();
		}
	    }
	}
    }

    public void inquiryCompleted(RemoteDevice[] devices) {
	fmInquiry.setTitle(Integer.toString(this.devices.size())
		+ " encontrados");

	if (dispositivosEncontrados > 0) {
	    fmInquiry.addCommand(this.select);
	}

	fmInquiry.addCommand(this.repetir);
	fmInquiry.removeCommand(this.stopOrBack);
	this.stopOrBack = new Command("Voltar");
	fmInquiry.addCommand(this.stopOrBack);

    }

    public void inquiryError() {
    	Util.mostrarErro("Erro causado na requisição!");
    }

    public void actionPerformed(ActionEvent evt) {
	if (evt.getCommand() == stopOrBack) {
	    if (evt.getCommand() == cancel) {
		try {
		    DeviceDiscoverer.getInstance().cancelInquiry();

		    fmInquiry.setTitle("Busca cancelada");

		    fmInquiry.removeCommand(cancel);

		    if (dispositivosEncontrados > 0) {
			fmInquiry.addCommand(this.select);
		    } else {
			fmInquiry.removeCommand(this.select);
		    }

		    fmInquiry.addCommand(this.repetir);
		    fmInquiry.addCommand(this.voltar);
		} catch (BluetoothStateException ex) {
		    ex.printStackTrace();
		}
	    } else {		
//		Abas.getInstancia().criarAbas();
		TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();

	    }
	} else if (evt.getCommand() == repetir) {

	    InquiryList.getInstancia().criarTelaPesquisaDispositivos(
		    imprimirContaImovelSelecionado);

	} else if (evt.getCommand() == voltar) {	    
//	    Abas.getInstancia().criarAbas();
		TelaMenuPrincipal.getInstancia().criarTelaMenuPrincipal();
		
	} else if (evt.getCommand() == select) {

	    try {
		int selecionado = devicesList.getSelectedIndex();
		RemoteDevice deviceSelecionado = (RemoteDevice) this.devices
			.elementAt(selecionado);
		DeviceDiscoverer.getInstance().cancelInquiry();
		ServiceDiscoverer service = ServiceDiscoverer.getInstance();

		service.startSearch(deviceSelecionado, this);
	    } catch (BluetoothStateException e) {
		Util.mostrarErro( e.getMessage(), e );
	    }
	}

    }
}