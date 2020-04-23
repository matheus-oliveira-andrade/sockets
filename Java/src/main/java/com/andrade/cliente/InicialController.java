package com.andrade.cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class InicialController implements Initializable {

	@FXML
	private TextField txtfServer;

	@FXML
	private TextField txtfPorta;

	@FXML
	private TextField txtfMensagem;

	@FXML
	private TextArea txtaResposta;

	@FXML
	private Button btnEnviar;

    @FXML
    private Button btnConectar;
	
    private Socket cliente;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	private void btnConectarClicked(MouseEvent event) {						
		try {
			String host = txtfServer.getText();
			int porta = Integer.parseInt(txtfPorta.getText());
			
			// Conexão com o servidor
			cliente = new Socket(host, porta);					
					
		} catch (UnknownHostException e) {
			Alerta(e.getMessage(), AlertType.ERROR, "Unknown host exception");
		} catch (IOException e) { 
			Alerta(e.getMessage(), AlertType.ERROR, "IO Exception");
		}
	}
	
	@FXML
	private void btnEnviarClicked(MouseEvent event) {
		
		String mensagem = txtfMensagem.getText();

		try {			
			OutputStream saida = cliente.getOutputStream();
			
			// Converte mensagem em array de bytes e adiciona a stream de saída
			saida.write(mensagem.getBytes());			
			
			// Resposta do servidor						
			InputStream entrada = cliente.getInputStream();
			
			byte[] buffer = new byte[512];
			// Reads some number of bytes from the input stream and stores them into the
			// buffer array b. The number of bytes actually
			// read is returned as an integer. This METHOD BLOCKS until input data
			// is available, end of file is detected, or an exception is thrown.
			entrada.read(buffer);			
			
			// Mostra na tela
			txtaResposta.appendText(new String(buffer));								
		} catch (IOException e) {
			Alerta(e.getMessage(), AlertType.ERROR, "IO Exception");
		}
	}
	
	private void Alerta(String mensagem, AlertType tipo, String header) {
		Alert alert = new Alert(tipo);
        
        alert.setTitle("Alert");
        alert.setHeaderText(header);
        alert.setContentText(mensagem);

        alert.show();
	}
}
