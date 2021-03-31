package br.com.prjRafael.bc;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.prjRafael.base.BC;
import br.com.prjRafael.bean.Usuario;
import br.com.prjRafael.dao.UsuarioDAO;
import br.com.prjRafael.util.Util;

@Component
@Path("/usuario")
@RestController
public class UsuarioBC extends BC {
	
	@Autowired
	private UsuarioDAO dao;
	
	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)	
	@RequestMapping(value = "/usuario/login/{login}/{senha}/{token}", method  = RequestMethod.POST, headers = "Accept=application/json")
	public String login(@FormParam("login") @PathVariable String login, @FormParam("senha") @PathVariable String senha, @FormParam("token") @PathVariable String token){
						
		//dao = new UsuarioDAO();
		//beanFactory.autowireBean(dao);
		
		System.out.println(Util.md5(login + senha));
		
		
		Usuario usr = new Usuario();
		usr.setUsuario(login);
		usr.setSenha(senha);
		usr = dao.login(usr);
		
		return gson.toJson(usr);		
	}
	
	@Path("/getAll")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value= "/usuario/getAll/{token}", method = RequestMethod.POST, headers = "Accept=application/json")	
	public String getAll(@PathVariable @FormParam ("token") String token){
		
		if(!checkToken(token)){
			return null;
		}
				
		Usuario usr = new Usuario();
		
		return gson.toJson(dao.getAll(usr));		
	}
/*	
//	@POST
//	@Path("/getById")
//	@Produces(MediaType.APPLICATION_JSON)
	public String getById(@FormParam ("id")Integer id, @FormParam ("token")String token){
		
		if(!checkToken(token)){
			return null;
		}
				
		Usuario usr = new Usuario();
		usr.setId(id);
		
		return gson.toJson(dao.getById(usr));		
	}		*/
	
}
