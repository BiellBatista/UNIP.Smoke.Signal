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

package br.com.unip.cc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Esta classe representa a entidade email e cuida de capturar as informacoes
 * 
 * @version 0.1 14/Abr/2017
 * @author Gabriel Batista
 */

@Entity //Diz que a classe é uma entidade
@Table(name = "EMAIL")
public class Email {
	private int email_codigo = 0;
	private String email_email = null;
	private String email_senha = null;
	
	@Id //Diz que o próximo método é a chave primária
	@GeneratedValue //Diz que ele deve ser auto-incremento
	public int getEmail_codigo() {
		return email_codigo;
	}
	
	public void setEmail_codigo(int email_codigo) {
		this.email_codigo = email_codigo;
	}
	
	@Column(name = "EMA_EMAIL", length = 30, nullable = false)
	public String getEmail_email() {
		return email_email;
	}
	
	public void setEmail_email(String email_email) {
		this.email_email = email_email;
	}
	
	@Column(name = "EMA_SENHA", length = 10, nullable = false)
	public String getEmail_senha() {
		return email_senha;
	}
	
	public void setEmail_senha(String email_senha) {
		this.email_senha = email_senha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + email_codigo;
		
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
		
		Email other = (Email) obj;
		
		if (email_codigo != other.email_codigo)
			return false;
		
		return true;
	}
}