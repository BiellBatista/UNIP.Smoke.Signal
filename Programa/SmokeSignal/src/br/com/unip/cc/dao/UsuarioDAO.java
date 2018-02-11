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

package br.com.unip.cc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.unip.cc.model.Usuario;

/**
 * Esta classe cuida de inserir e recuperar usuarios no banco
 * 
 * @version 0.1 14/Abr/2017
 * @author Gabriel Batista
 */

public class UsuarioDAO {
	private Connection con = null;
	private PreparedStatement stmt = null;
	private String sql = null;
	/**
	 * metodo que insere um novo usuario
	 * @param usuario
	 * @return "0"
	 */
	public String inserirUsuario(Usuario usuario) {
		con = Conexao.getConexao();
		sql = "INSERT INTO USUARIO VALUES(AUTO_INCREMENTAL_U.NEXTVAL, AUTO_INCREMENTAL_EU.NEXTVAL, ?, ?, ?, ?)";
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, usuario.getUsu_nome());
			stmt.setString(2, usuario.getUsu_sexo());
			stmt.setInt(3, usuario.getUsu_idade());
			stmt.setString(4, usuario.getUsu_cargo());
			stmt.execute();
			
			return "1";
		} catch(SQLException e) {
			return e.getMessage();
		}
	}
	/**
	 * metodo que busca um usuario
	 * @param email, senha
	 * @return rs
	 */
	public ResultSet pesquisarUsuario(String email, String senha) {
		ResultSet rs = null;
		con = Conexao.getConexao();
		sql = "SELECT USU_NOME FROM USUARIO WHERE USU_CODIGO = (SELECT EMA_CODIGO FROM EMAIL WHERE EMA_EMAIL = ? AND EMA_SENHA = ?)";

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