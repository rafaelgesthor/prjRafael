package br.com.prjRafael.bean;

import br.com.prjRafael.base.Bean;

public class Usuario extends Bean {
	
	private String usuario,nome,senha;
	private Integer med_id, empresa_id;
	
	public Integer getEmpresa_id() {
		return empresa_id;
	}

	public void setEmpresa_id(Integer empresa_id) {
		this.empresa_id = empresa_id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getMed_id() {
		return med_id;
	}

	public void setMed_id(Integer med_id) {
		this.med_id = med_id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
