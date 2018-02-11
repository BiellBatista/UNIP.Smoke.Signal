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
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.unip.cc.dao.EmailDAO;
import br.com.unip.cc.dao.UsuarioDAO;
import br.com.unip.cc.model.Email;
import br.com.unip.cc.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * Esta classe controlas as ações realizadas na janela cadastro
 * 
 * @version 0.1 18/Mai/2017
 * @author Igor Faggion
 */

public class CadastroController implements Initializable {
	ObservableList<String> tipoSexo = FXCollections.observableArrayList("Masculino","Feminino");
	ObservableList<String> tipoHierarquia = FXCollections.observableArrayList("Gerente","Analista","Programador");

	@FXML private JFXButton btnCadastrar;
	@FXML private JFXButton btnVoltar;
	@FXML private JFXTextField txtEmail;
	@FXML private JFXTextField txtNome;
	@FXML private JFXPasswordField pswSenha1;
	@FXML private JFXPasswordField pswSenha2;
	@SuppressWarnings("rawtypes")
	@FXML private JFXComboBox cbSexoCadastro;
	@FXML private JFXDatePicker dtNascimentoCadastro;
	@SuppressWarnings("rawtypes")
	@FXML private JFXComboBox cbHierarquiaCadastro;
	@FXML private MenuItem itemSair;
	@FXML private MenuItem itemAutores;
	
	private Stage stage = null;
	private Parent root = null;
	private Scene scene = null;
	private Email email = null;
	private Usuario usuario = null;
	private EmailDAO obEmail = null;
	private UsuarioDAO obUsuario = null;
	private int ok = 0;

	public void initialize(URL url, ResourceBundle rb) {
		stage = new Stage();
		usuario = new Usuario();
		email = new Email();
		obUsuario = new UsuarioDAO();
		obEmail = new EmailDAO();

		btnCadastrar.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				usuario.setUsu_nome(txtNome.getText());

				if(cbSexoCadastro.getValue().equals("Masculino"))
					usuario.setUsu_sexo("M");
				else
					usuario.setUsu_sexo("F");

				usuario.setUsu_idade(2015 - Integer.parseInt((dtNascimentoCadastro.getValue().toString().substring(0, 4))));
				usuario.setUsu_cargo(cbHierarquiaCadastro.getValue().toString());
				email.setEmail_email(txtEmail.getText());

				if(pswSenha1.getText().equals(pswSenha2.getText())) {
					email.setEmail_senha(pswSenha1.getText());

					ok = Integer.parseInt(obEmail.inserirEmail(email));
					ok += Integer.parseInt(obUsuario.inserirUsuario(usuario));
					if(ok == 2) {
						JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso", "Sucesso", JOptionPane.PLAIN_MESSAGE);
						
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
						
						btnCadastrar.getScene().getWindow().hide();
					}
				} else
					JOptionPane.showMessageDialog(null, "As senhas não são iguais.", "Senhas divergentes", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnVoltar.setOnAction(new EventHandler<ActionEvent>() {
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

				btnVoltar.getScene().getWindow().hide();
			}
		});

		itemSair.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				btnVoltar.getScene().getWindow().hide();
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
	}    

	@SuppressWarnings("unchecked")
	@FXML public void addSexo(){
		cbSexoCadastro.setItems(tipoSexo);
	}

	@SuppressWarnings("unchecked")
	@FXML public void addHierarquia(){
		cbHierarquiaCadastro.setItems(tipoHierarquia);
	}
}