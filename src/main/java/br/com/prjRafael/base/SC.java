package br.com.prjRafael.base;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

		
public abstract class SC extends Bean {
	
	protected final String WHERE = " where ";
	protected final String AND = " and ";
	protected final String OR = " or ";
	
	private String filter;
	
	public String sqlFilter = "";
	
	private String whereAnd() {
		return this.filter.trim() == "" ? WHERE : AND;
	}

	protected MapSqlParameterSource parameters; 
	
	public MapSqlParameterSource getParameters() {
		return parameters;
	}

	public String filterSQL() {
//		filter = "";
		filter = sqlFilter;
		parameters = new MapSqlParameterSource();		
		
		prepareFilter();
		
		return filter;
	}
	
	abstract protected void prepareFilter();
		
	protected void addFilter(String f) {
		addFilter(f, whereAnd());		
	}
	
	protected void addFilter(String f, String condition) {
		filter += " " + condition + f + " ";
	}
	
}
