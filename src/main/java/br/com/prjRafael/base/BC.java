package br.com.prjRafael.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.prjRafael.bean.Usuario;
import br.com.prjRafael.util.Util;

@Component
public class BC {
	
	public Gson gson = new Gson();
	
	protected ApplicationContext context; 	
	protected DefaultListableBeanFactory beanFactory; 

	protected Usuario usuarioLogado;
	
	public boolean checkToken(String token){
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		int index = token.indexOf("*");
		
		System.out.println(Util.md5("yka655q@"+dateFormat.format(date)+token.substring(index+1), 15)+"*"+token.substring(index+1));
		
		try {
			if(token.equals(Util.md5("yka655q@"+dateFormat.format(date)+token.substring(index+1), 15)+"*"+token.substring(index+1))){
				return true;
			} else {
				return false;
			}
		} catch (StringIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	@Autowired 
    public void setApplicationContext(ApplicationContext ctx) { 
        if (!DefaultListableBeanFactory.class.isAssignableFrom(ctx.getAutowireCapableBeanFactory().getClass())) { 
            throw new IllegalArgumentException("BeanFactory deve ser do tipo DefaultListableBeanFactory"); 
        } 
        
        this.context = ctx; 
        this.beanFactory = (DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory(); 
    } 	
}
