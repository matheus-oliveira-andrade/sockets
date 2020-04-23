package com.andrade.servidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		String nameHost = "localhost";
		int port = 8080;				
		String palavraMagica = "Fechar";		
		
		try {			
			System.out.println("Endereço: " + nameHost + ":" + port);
			System.out.print("[ Iniciando Servidor......................... ]");
			
			// Instância o servidor
			ServerSocket server = new ServerSocket(port);
			System.out.print("[ OK ]\n");

			System.out.print("[ Esperando por conexões .................... ]");
			
			// The METHOD BLOCKS until a connection is made.
			Socket cliente = server.accept();
			System.out.print("[ OK ]\n");			

			String mensagemRecebida = "";
			
			while (mensagemRecebida != palavraMagica) {
				
				mensagemRecebida = "";
				
				System.out.print("[ Aguardando mensagem ....................... ]");
										
				byte[] buffer = null;				
				InputStream entrada = cliente.getInputStream();				
				do {					
					buffer = new byte[25];			
					entrada.read(buffer);
					mensagemRecebida += new String(buffer);						
					
					System.out.print("[ OK ]\n");				
				}while(entrada.available() != 0);																	
				
				System.out.print("[ Mensagem ] - " + mensagemRecebida);
				
				String mensagem = "[ SERVIDOR ] Mensagem recebida";

				OutputStream saida = cliente.getOutputStream();
				saida.write(mensagem.getBytes());
				saida.flush();				
			}
			
			System.out.print("[ Encerrando servidor ................... ] " );
			
			// Fecha conexão com o cliente
			cliente.close();
			
			// Fecha o socket do servidor			
			server.close();
			
			System.out.print("[ OK ]\n");			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}