package com.ipad.util;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

public class DialogConfirma implements ActionListener {
    
    private Dialog dialogConfirma;
    private Command ok;
    public boolean confirmouPergunta = false;

    public void actionPerformed(ActionEvent arg0) {
		if (arg0.getCommand() == ok) {
			confirmouPergunta = true;
		}
    }
    
    public DialogConfirma( String mensagem){
	super();
	
	// Criamos
	dialogConfirma = new Dialog("Mensagem...");
	dialogConfirma.setCommandListener( this );	  
	    
	ok = new Command("Ok");
		    
	dialogConfirma.getTitleStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	dialogConfirma.getDialogStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	dialogConfirma.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
	dialogConfirma.addCommand(ok);
	
	// Dividimos a pergunta
	String[] stringDividida = Util.dividirString( mensagem , 23 );
	
	for ( int i = 0; i < stringDividida.length; i++ ){
	    Label lbParteMensagem = new Label( stringDividida[i] );	   
	    lbParteMensagem.getStyle().setMargin(0, 0, 0, 0);
	    lbParteMensagem.getStyle().setPadding(0, 0, 0, 0);
	    
	    dialogConfirma.addComponent( lbParteMensagem );
	}	
	
	dialogConfirma.showDialog();
	
    }
}
