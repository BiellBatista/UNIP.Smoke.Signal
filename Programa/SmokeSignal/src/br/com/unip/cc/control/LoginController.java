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

package br.com.unip.cc.control;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.unip.cc.dao.EmailDAO;
import br.com.unip.cc.dao.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Esta classe controlas as ações realizadas na janela login
 * 
 * @version 0.1 18/Mai/2017
 * @author Igor Faggion
 */

public class LoginController implements Initializable {
	@FXML private JFXTextField txtEmail = null;
	@FXML private JFXPasswordField pwdSenha = null;
	@FXML private JFXButton btnEntrar = null;
	@FXML private JFXButton btnCadastre = null;
	@FXML private JFXButton btnInfo = null;

	private Stage stage = null;
	private Scene scene = null;
	private Parent root = null;
	private ResultSet rs = null;
	private EmailDAO email = null;
	private UsuarioDAO usuario = null;
	private String nome = null;

	public void initialize(URL location, ResourceBundle resources) {
		btnEntrar.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				email = new EmailDAO();
				usuario = new UsuarioDAO();
				rs = email.pesquisarEmail(txtEmail.getText(), pwdSenha.getText());

				try{
					if(rs.next()) {
						stage = new Stage();

						if(rs.next())
							rs = usuario.pesquisarUsuario(txtEmail.getText(), pwdSenha.getText());

						nome = rs.getString(1);
						root = FXMLLoader.load(getClass().getResource("/br/com/unip/cc/windows/FXMLChat.fxml"));

						scene = new Scene(root);
						stage.setTitle("Chat");
						stage.getIcons().add(new Image("icon.png"));
						stage.setScene(scene);
						btnEntrar.getScene().getWindow().hide();
						stage.show();
					}

					else
						JOptionPane.showMessageDialog(null, "E-mail ou senha inválido", "Uuário Inválido", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		btnCadastre.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					root = FXMLLoader.load(getClass().getResource("/br/com/unip/cc/windows/FXMLCadastro.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}

				scene = new Scene(root);
				stage.setTitle("Cadastro");
				stage.getIcons().add(new Image("icon.png"));
				stage.setScene(scene);
				stage.show();

				btnCadastre.getScene().getWindow().hide();
			}
		});

		btnInfo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					root = FXMLLoader.load(getClass().getResource("/br/com/unip/cc/windows/FXMLInfo.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}

				scene = new Scene(root);
				stage.setTitle("Autores");
				stage.getIcons().add(new Image("icon.png"));
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
			}
		});
	}
	
	public String getNome() {
		return nome;
	}
}