package br.com.prjRafael.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import br.com.prjRafael.base.Bean;
import br.com.prjRafael.base.DAO;
import br.com.prjRafael.bean.Usuario;
import br.com.prjRafael.util.Util;

@Repository
public class UsuarioDAO extends DAO {
	
	private String passwordMd5(Usuario usuario) {
		return Util.md5(usuario.getUsuario() + usuario.getSenha());
	}	

	private final RowMapper<Usuario> rowMapper = new RowMapper<Usuario>(){
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        	
        	Usuario usr = new Usuario();
        	usr.setId(rs.getInt("usr_id"));
        	usr.setNome(rs.getString("usr_nome"));
        	usr.setUsuario(rs.getString("usr_nome_usuario"));
        	usr.setSenha(rs.getString("usr_senha"));
        	//usr.setAtivo(rs.getBoolean("usr_ativo"));
        	//usr.setAlterarSenha(rs.getBoolean("usr_alterar_senha"));
        	//usr.setExcluido(rs.getBoolean("usr_excluido"));
        	//usr.setFoto(rs.getBytes("usr_foto"));
        	//usr.setControle(rs.getInt("usr_controle"));
        	
        	//if (Util.hasColumn(rs, "med_id"))        	
//           		usr.setMedico((Medico) medico(rs));  

        	//usr.setOwnerId(rs.getInt("owner_id"));
        	//usr.setEmpresaId(rs.getInt("empresa_id"));
        	//usr.setDataCadastro(rs.getTimestamp("data_cadastro"));

            return usr;		
        }
    };	
	
	public Usuario login(Usuario usuario){
		System.out.println(usuario.getUsuario() + " . " + usuario.getSenha() + " : " + this.passwordMd5(usuario));
		
		//Buscando usuario, posterior validação do Login -> controle de Acesso (3-tentativas)
		String sql = " select USR.usr_id "
				   + "   from usuario USR "
				   + "  where USR.usr_ativo = 1 "
				   + "    and USR.usr_nome_usuario = ? ";
		
		Integer usrId = 0;
		
		try {
			usrId = jdbcTemplate.queryForObject(sql, new Object[] {usuario.getUsuario()}, Integer.class);
		} catch (EmptyResultDataAccessException e) {			
			return null;
		}			
		

		//Autenticar com credenciais informadas
		sql =	"    select " +
				"         USR.* " +
				"       , MED.* " +
				"      From usuario USR " +
				" left join medico MED    on USR.usr_id = MED.usr_id " +
				" left join pessoa MEDPES on MED.pes_id = MEDPES.pes_id " +
				
				"     where USR.usr_ativo   = 1 " +
				"       and USR.usr_nome_usuario = ? " + 
				"       and USR.usr_senha   = ? ";
		
		Usuario usr = null;		
		try {
			usr = jdbcTemplate.queryForObject(sql,
					new Object[] {usuario.getUsuario(), this.passwordMd5(usuario)}, 
					rowMapper);
			
			//loginSucess(usr.getId());
			
			return usr;
		} catch (EmptyResultDataAccessException e) {			
			//loginError(usrId);
			
			Usuario objUsr = new Usuario();
			objUsr.setId(usrId);
			
			usr = (Usuario) this.getById(objUsr);
			
			if (usr != null) usr.setId(-1);
			
			return usr;
		}							
	}	
			
	@Override
	protected Object insert(Bean object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object update(Bean object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getById(Bean object) {
		Usuario usr = (Usuario) object;
		
		String sql = " select USR.* " +
					 "   From usuario USR " +
				"  where USR.usr_id = ? ";
		
		try {			
			return jdbcTemplate.queryForObject(sql, 
					new Object[] {usr.getId()}, rowMapper);	
			
		} catch (EmptyResultDataAccessException e) {			
			return null;
		}							
	}


	@Override
	public List<Usuario> getByFilter(String filter) {

		String sql = " select " +
					 "	   USR.* " +
					 "   from usuario USR " +
					 filter +				
					 "  order by USR.usr_nome ";

		return (List<Usuario>) namedJdbcTemplate.query(sql, parameters, 
				new RowMapperResultSetExtractor<Usuario>(rowMapper));
	}

	@Override
	public List<Usuario> getAll(Bean object) {
		Usuario usr = (Usuario) object;

		usr.setUsuario(Util.prepareFilter(usr.getUsuario()));

		fillParameters(object);
				
		String filter = "";
		
		if (usr.getUsuario().trim() != "")
			filter += " where USR.usr_nome_usuario ilike :usuario ";
		
		return getByFilter(filter);
	}

	@Override
	public Boolean remove(Bean object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void fillParameters(Bean object) {
		Usuario usr = (Usuario) object;
		
		parameters = new MapSqlParameterSource();		
		parameters.addValue("id", usr.getId());
		parameters.addValue("nome", usr.getNome());
		parameters.addValue("usuario", usr.getUsuario());
		parameters.addValue("senha", usr.getSenha());	
		//parameters.addValue("ativo", usr.getAtivo());
		//parameters.addValue("alterar_senha", usr.getAlterarSenha());
		//parameters.addValue("foto", usr.getFoto());
		//parameters.addValue("owner_id", usr.getOwnerId());
		//parameters.addValue("empresa_id", usr.getEmpresaId());		
	}	
	
}
