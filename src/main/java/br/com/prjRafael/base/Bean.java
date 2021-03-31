package br.com.prjRafael.base;

import java.util.List;
import java.util.Map;

public class Bean {
	
	private Integer id;
	//private Timestamp dataCadastro;
	//private Integer ownerId;
	//private Integer empresaId;
	
	//public Empresa empresaLogado;
	//public Usuario usuarioLogado;
	public List<Map<String, Object>> parameters;

	public Integer getId() {
		return id;
	}
	
	
	public String paramText(String name){
		
		for (Map<String, Object> map : parameters) {
			
			if(map.get("nome").equals(name)){
				return map.get("valor").toString();
			}
		}
		return "";
	}
	
	public void setId(Integer value) {
		this.id = value;
	}
	
//	{public Timestamp getDataCadastro() {
//		return dataCadastro;
//	}
//	
//	public void setDataCadastro(Timestamp value) {
//		this.dataCadastro = value;
//	}
//	
//	public Integer getOwnerId() {
//		return ownerId;
//	}
//	
//	public void setOwnerId(Integer value) {
//		this.ownerId = value;
//	}

//	public Integer getEmpresaId() {
//		return empresaId;
//	}
//
//	public void setEmpresaId(Integer empresaId) {
//		this.empresaId = empresaId;
//	}
	
	public void copyInfo(Bean bean) {
//		this.empresaId = bean.empresaId;
//		this.ownerId   = bean.ownerId;

		//this.empresaLogado = bean.empresaLogado;
//		this.usuarioLogado = bean.usuarioLogado;
	}
	

}
