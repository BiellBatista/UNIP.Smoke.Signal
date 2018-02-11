/* %W% %E% Gabriel Batista
 *
 * Copyright (c) 2000-2012 Raz�o Social Ltda. Todos os direitos reservados.
 *
 * Esse software � informa��o confidencial e propriet�ria da Raz�o
 * Social Ltda. ("Informa��o Confidencial"). Voc� n�o deve divulgar
 * tal Informa��o Confidencial e deve us�-la apenas em concord�ncia
 * com os termos de acordo de licen�a que voc� entrou com a Laranja Games Ltda.
 *
 * 
 * LARANJA GAMES LTDA N�O FAZ REPRESENTA��O OU D� GARANTIAS SOBRE A
 * ADEQUA��O DO SOFTWARE, SEJA EXPRESSA OU IMPLICADA, INCLUINDO MAS
 * N�O LIMITANDO AS GARANTIAS DE COMERCIALIZA��O, ADEQUA��O PARA UM
 * DETERMINADO FIM PARTICULAR OU N�O-VIOLA��O. LARANJA GAMES LTDA N�O
 * SER� RESPONS�VEL POR QUALQUER DANO SOFRIDO PELO LICENCIADO EM
 * DECORR�NCIA DO USO, MODIFICA��O OU DISTRIBUI��O DESSE SOFTWARE OU
 * SEUS DERIVADOS.
 */

package br.com.unip.cc.control;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import br.com.unip.cc.hardware.VoIP;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Esta classe controlas as a��es realizadas na janela chat
 * 
 * @version 0.1 18/Mai/2017
 * @author Igor Faggion
 */

public class ChatController implements Initializable {
	@FXML private TextArea txtrRecebe = null;
	@FXML private Menu itemSmoke = null;
	@FXML private MenuItem itemDesconectar = null;
	@FXML private MenuItem itemSair = null;
	@FXML private MenuItem itemLigar = null;
	@FXML private MenuItem itemAutores = null;
	@FXML private JFXButton btnEnviar = null;
	@FXML private JFXButton btnChamar = null;
	@FXML private JFXTextField textEnvia = null;

	private Stage stage = null;
	private Parent root = null;
	private Scene scene = null;
	private PrintWriter escritor = null;
	private Scanner leitor = null;
	private Socket socket = null;
	private String texto = null;
	private String nome = null;

	public void initialize(URL url, ResourceBundle rb) {
		stage = new Stage();
		configurarRede();
		nome = new LoginController().getNome();

		btnEnviar.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				new FalaServidor().actionPerformed(null);
			}
		});

		btnChamar.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				new VoIP().captureAudio();
			}
		});

		itemSair.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		itemAutores.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {			
				try {
					root = FXMLLoader.load(getClass().getResource("/br/com/unip/cc/windows/FXMLInfo.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}

				scene = new Scene(root);
				stage.setTitle("Autores");
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
			}
		});

		itemDesconectar.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					root = FXMLLoader.load(getClass().getResource("/br/com/unip/cc/windows/FXMLLogin.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}

				scene = new Scene(root);
				stage.setTitle("Login");
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();

				btnEnviar.getScene().getWindow().hide();
			}
		});

		itemLigar.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				new VoIP().captureAudio();
			}
		});
	}

	private void configurarRede() {
		try {
			socket = new Socket("25.2.143.59", 1350);
			escritor = new PrintWriter(socket.getOutputStream());
			leitor = new Scanner(socket.getInputStream());
			new Thread(new EscutaServidor()).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class EscutaServidor implements Runnable {
		public void run() {
			while((texto = leitor.nextLine()) != null)
				txtrRecebe.appendText(texto + "\n");
		}
	}

	private class FalaServidor implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			escritor.println(nome + " : " + textEnvia.getText());
			escritor.flush();

			textEnvia.setText("");
			textEnvia.requestFocus(); 
		}
	}
}