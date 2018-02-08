package Pwd.src.Controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Pwd.src.Services.LoginService;

@Path("login")
public class LoginController {
	private LoginService loginService = new LoginService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() {
		return "gotttt";
	}

	
}
