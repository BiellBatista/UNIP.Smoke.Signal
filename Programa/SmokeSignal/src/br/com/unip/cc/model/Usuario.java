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

package br.com.unip.cc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Esta classe representa a entidade usuario e cuida de capturar as informacoes
 * 
 * @version 0.1 14/Abr/2017
 * @author Gabriel Batista
 */

@Entity //Diz que a classe � uma entidade
@Table(name = "USUARIO")
public class Usuario {
	private int usu_codigo = 0;
	private int ema_codigo1 = 0;
	private String usu_nome = null;
	private String usu_sexo = null;
	private int usu_idade = 0;
	private String usu_cargo = null;
	
	@Id //Diz que o pr�ximo m�todo � a chave prim�ria
	@GeneratedValue //Diz que ele deve ser auto-incremento
	public int getUsu_codigo() {
		return usu_codigo;
	}
	
	public void setUsu_codigo(int usu_codigo) {
		this.usu_codigo = usu_codigo;
	}
	
	public int getEma_codigo1() {
		return ema_codigo1;
	}

	public void setEma_codigo1(int ema_codigo1) {
		this.ema_codigo1 = ema_codigo1;
	}
	
	@Column(name = "USU_NOME", length = 30, nullable = false)
	public String getUsu_nome() {
		return usu_nome;
	}
	
	public void setUsu_nome(String usu_nome) {
		this.usu_nome = usu_nome;
	}
	
	@Column(name = "USU_SEXO", length = 2, nullable = false)
	public String getUsu_sexo() {
		return usu_sexo;
	}
	
	public void setUsu_sexo(String usu_sexo) {
		this.usu_sexo = usu_sexo;
	}
	
	@Column(name = "USU_IDADE", length = 8, nullable = false)
	public int getUsu_idade() {
		return usu_idade;
	}
	
	public void setUsu_idade(int usu_idade) {
		this.usu_idade = usu_idade;
	}
	
	@Column(name = "USU_IDADE", length = 30, nullable = false)
	public String getUsu_cargo() {
		return usu_cargo;
	}
	
	public void setUsu_cargo(String usu_cargo) {
		this.usu_cargo = usu_cargo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + usu_codigo;
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Usuario other = (Usuario) obj;
		
		if (usu_codigo != other.usu_codigo)
			return false;
		
		return true;
	}
}