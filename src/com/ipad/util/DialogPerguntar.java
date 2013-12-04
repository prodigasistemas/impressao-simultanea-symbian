package com.ipad.util;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

public class DialogPerguntar implements ActionListener {
    
    private Dialog dialogPerguntar;
    private Command cmdNao;
    private Command cmdSim;
    private Label lbTxSenha;
    private TextField txSenha;
    private boolean validarSenha = false;
    private boolean validarSenhaAdministrador = false;
    public boolean confirmouPergunta = false;

    public void actionPerformed(ActionEvent arg0) {
	if (arg0.getCommand() == cmdSim) {
	    if (validarSenha) {

		String senha = "";

		if (validarSenhaAdministrador) {
		    senha = Constantes.SENHA_ADMINISTRADOR;
		} else {
		    senha = Constantes.SENHA_LEITURISTA;
		}

		if (txSenha.getText().equals(senha)) {
		    confirmouPergunta = true;
		} else {
		    Util.mostrarErro("Senha incorreta!");
		    confirmouPergunta = false;
		}
	    } else {
		confirmouPergunta = true;
	    }
	}

	if (arg0.getCommand() == cmdNao) {
	    confirmouPergunta = false;
	}	
    }
    
    public DialogPerguntar( String pergunta, boolean validarSenha, boolean validarSenhaAdministrador ){
	super();
	
	this.validarSenha = validarSenha;
	this.validarSenhaAdministrador = validarSenhaAdministrador;
	
	// Criamos
	dialogPerguntar = new Dialog("Pergunta...");
	dialogPerguntar.setCommandListener( this );	  
	    
	cmdNao = new Command("Nao");
	cmdSim = new Command("Sim");
		    
	dialogPerguntar.getTitleStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	dialogPerguntar.getDialogStyle().setBgTransparency(Constantes.BGTRANSPARENCY_DIALOG);
	dialogPerguntar.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
	dialogPerguntar.addCommand(cmdNao);
	dialogPerguntar.addCommand(cmdSim);
	
	// Dividimos a pergunta
	String[] stringDividida = Util.dividirString( pergunta , 23 );
	
	for ( int i = 0; i < stringDividida.length; i++ ){
	    Label lbPartePergunta = new Label( stringDividida[i] );	   
	    lbPartePergunta.getStyle().setMargin(0, 0, 0, 0);
	    lbPartePergunta.getStyle().setPadding(0, 0, 0, 0);
	    
	    dialogPerguntar.addComponent( lbPartePergunta );
	}	
	
	// Verificamos se é necessário a confirmação da senha
	if ( validarSenha ){	    
	    Label linha = new Label();
	    Label linha1 = new Label();
	    Label linha2 = new Label();
	    
	    lbTxSenha = new Label("Senha");
	    lbTxSenha.getStyle().setPadding(0, 1, 0, 0);
	    lbTxSenha.getStyle().setMargin(0, 1, 0, 0);
	    
	    txSenha = new TextField();
	    txSenha.setConstraint(TextArea.PASSWORD);
	    txSenha.setInputMode("abc");
	    
	    dialogPerguntar.addComponent(linha);
	    dialogPerguntar.addComponent(linha1);
	    dialogPerguntar.addComponent(linha2);
	    dialogPerguntar.addComponent(lbTxSenha);
	    dialogPerguntar.addComponent(txSenha);
	}
	
	dialogPerguntar.showDialog();
	
    }
}
