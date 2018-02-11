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

package br.com.unip.cc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * Esta classe cuida da conexao com o SGBD Oracle 11g
 * 
 * @version 0.1 14/Abr/2017
 * @author Gabriel Batista
 */

public class Conexao {
	private static String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE"; //url do banco local
	private static String login = "Gabriel"; //usuario do banco (Mude conforme o seu BD local)
	private static String senha = "gabriel2b21"; //senha do usuario (Mude conforme o seu BD local)
	private static Connection con = null; //objeto que aponta para a conexao
	
	public static Connection getConexao() {
		if(con == null) { //caso nao exista uma conexao
			try{
				Class.forName("oracle.jdbc.driver.OracleDriver"); //recuperando o driver do oracle
				con = DriverManager.getConnection(url, login, senha); //conectando ao banco
			} catch(ClassNotFoundException e) { //caso nao encontre o driver
				JOptionPane.showMessageDialog(null, "Classe do Driver de conexão com Oracle não encontrada!", e.getMessage(), JOptionPane.ERROR_MESSAGE);
			} catch(SQLException e) { //caso a url ou o login ou a senha esteja incorreto
				JOptionPane.showMessageDialog(null, "Problemas com os parâmetros de conexão!", e.getMessage(), JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return con;
	}
	
	public static void closeConexao() {
		try {
			con.close(); //fecha a conexao com o banco
		} catch(SQLException e) { //caso não seja possível fechar
			JOptionPane.showMessageDialog(null, "Não foi possível fechar a conexão com BD!", e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}
}