/* %W% %E% Gabriel Batista
 *
 * Copyright (c) 2000-2012 Razão Social Ltda. Todos os direitos reservados.
 *
 * Esse software é informação confidencial e proprietária da Razão
 * Social Ltda. ("Informação Confidencial"). Você não deve divulgar
 * tal Informação Confidencial e deve usá-la apenas em concordância
 * com os termos de acordo de licença que você entrou com a Laranja Games Ltda.
 *
 * 
 * LARANJA GAMES LTDA NÃO FAZ REPRESENTAÇÃO OU DÁ GARANTIAS SOBRE A
 * ADEQUAÇÃO DO SOFTWARE, SEJA EXPRESSA OU IMPLICADA, INCLUINDO MAS
 * NÃO LIMITANDO AS GARANTIAS DE COMERCIALIZAÇÃO, ADEQUAÇÃO PARA UM
 * DETERMINADO FIM PARTICULAR OU NÃO-VIOLAÇÃO. LARANJA GAMES LTDA NÃO
 * SERÁ RESPONSÁVEL POR QUALQUER DANO SOFRIDO PELO LICENCIADO EM
 * DECORRÊNCIA DO USO, MODIFICAÇÃO OU DISTRIBUIÇÃO DESSE SOFTWARE OU
 * SEUS DERIVADOS.
 */

package br.com.unip.cc.hosting;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Esta classe representa o servidor do chat
 * 
 * @version 0.1 26/Abr/2017
 * @author Anderson Schinaid
 */

public class ChatServer {
	private List<PrintWriter> escritores = null;
	private ServerSocket server = null;
	private Socket socket = null;
	private PrintWriter p = null;
	private String texto = null;

	public ChatServer(){
		escritores = new ArrayList<>();
		
		try {
			server = new ServerSocket(5000);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while(true) {
			try {
				socket = server.accept();
				new Thread(new EscutaCliente(socket)).start();
				p = new PrintWriter(socket.getOutputStream());
				escritores.add(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void encaminharParaTodos(String texto){
		for(PrintWriter w : escritores){
			w.println(texto);
			w.flush();
		}
	}

	private class EscutaCliente implements Runnable {
		Scanner leitor = null;
		
		public EscutaCliente(Socket socket) {
			try {
				leitor = new Scanner(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try{
				while ((texto = leitor.nextLine()) != null)
					encaminharParaTodos(texto);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}