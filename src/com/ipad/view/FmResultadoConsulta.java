package com.ipad.view;

import java.util.Vector;

import com.ipad.basic.ImovelConta;
import com.ipad.business.ControladorImoveis;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;

public class FmResultadoConsulta extends Form implements ActionListener {

    private static Command cmdVoltar = new Command("Voltar");
    // private static Label lbResultado = new Label( "Resultado" );
    private static List lstResultado;

    private static FmResultadoConsulta instancia;

    private FmResultadoConsulta() {
	super();
    }

    public void criarResultado() {

	instancia.setCommandListener(this);
	instancia.show();

    }

    public static FmResultadoConsulta getInstancia(Vector resultado) {

	if (instancia == null) {
	    instancia = new FmResultadoConsulta();

	    instancia.addCommand(cmdVoltar);
	    instancia.setScrollableY(false);
	    instancia.setScrollableX(false);
	}

	if (lstResultado != null) {
	    instancia.removeComponent(lstResultado);
	}

	lstResultado = new List() {

	    public void keyPressed(int arg0) {

		if (arg0 == -5) {

		    ControladorImoveis.getInstancia().setImovelSelecionado(
			    (ImovelConta) this.getSelectedItem());

		    Abas.getInstancia().criarAbas();
		} else {
		    super.keyPressed(arg0);
		}
	    }
	};

	lstResultado.setIsScrollVisible(false);
	instancia.addComponent(lstResultado);

	for (int i = 0; i < resultado.size(); i++) {
	    lstResultado.addItem((ImovelConta) resultado.elementAt(i));
	}

	return instancia;
    }

    public void actionPerformed(ActionEvent arg0) {

	if (arg0.getCommand() == cmdVoltar) {
	    Consultas.getInstancia().criarConsulta();
	}

    }

}
