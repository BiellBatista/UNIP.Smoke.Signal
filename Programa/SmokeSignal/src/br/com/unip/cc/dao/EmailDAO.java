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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.unip.cc.model.Email;

/**
 * Esta classe cuida de inserir e recuperar e-mail no banco
 * 
 * @version 0.1 14/Abr/2017
 * @author Gabriel Batista
 */

public class EmailDAO {
	private Connection con = null;
	private PreparedStatement stmt = null;
	private String sql = null;
	/**
	 * metodo que insere um novo email
	 * @param email
	 * @return "0"
	 */
	public String inserirEmail(Email email) {
		con = Conexao.getConexao();
		sql = "INSERT INTO EMAIL VALUES(AUTO_INCREMENTAL_E.NEXTVAL, ?, ?)";

		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, email.getEmail_email());
			stmt.setString(2, email.getEmail_senha());
			stmt.execute();

			return "1";
		} catch(SQLException e) {
			return e.getMessage();
		}
	}
	/**
	 * metodo que busca um cadastro
	 * @param email, senha
	 * @return rs
	 */
	public ResultSet pesquisarEmail(String email, String senha) {
		ResultSet rs = null;
		con = Conexao.getConexao();
		sql = "SELECT * FROM EMAIL WHERE EMA_EMAIL = ? AND EMA_SENHA = ?";

		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, senha);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}
}