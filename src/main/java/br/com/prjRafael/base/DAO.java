package br.com.prjRafael.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.prjRafael.bean.Usuario;
import br.com.prjRafael.util.Util;

@Repository
public abstract class DAO {
	
	protected final String WHERE = " where ";
	protected final String AND = " and ";
	protected final String OR = " or ";
	protected final String WHERE_AND = ""; 	
	
	protected String whereAnd(String f) {
		return f.trim() == "" ? WHERE : AND;
	}
	
	//public Empresa empresaLogado;
	public Usuario usuarioLogado;
	
	//protected HashMap<String, Object> params;
	
	protected  MapSqlParameterSource parameters;
	
	@Autowired
	protected NamedParameterJdbcTemplate namedJdbcTemplate; 

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	abstract protected Object insert(Bean object);
	abstract protected Object update(Bean object);

//    abstract public Object post(Bean object);
	abstract public Object getById(Bean object);

	abstract public List<?> getByFilter(String filter);
	//abstract public List<?> getAll();
	abstract public List<?> getAll(Bean object);
	
	abstract public Boolean remove(Bean object);
	
	abstract protected void fillParameters(Bean object);			
	
	public Object post(Bean object) {
		fillParameters(object);		
		
		if (Util.returnNull(object.getId()) == null){
			return this.insert(object);
		} else {
			return this.update(object);				
		}
	}

	protected String toFilter(Bean object){
		return this.filter;
	}
	
	protected String filterSQL(Bean object) {
		filter = "";
		
		return prepareFilter(object);
	}
		
	private String prepareFilter(Bean object) {
		
		if (object instanceof SC) {
			SC sc = (SC) object;
			
			String f = sc.filterSQL();
			this.parameters = sc.getParameters();

			return f;
		} else {
			fillParameters(object);

			return toFilter(object);
		}
	}	
	
	private String filter = "";
	
	protected void addFilter(String f) {
		addFilter(f, whereAnd(filter));		
	}
	
	protected void addFilter(String f, String condition) {
		filter += " " + condition + f + " ";
	}	
		
}
