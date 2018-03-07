package Pwd.src.Controllers;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Pwd.src.Services.JWTAuthenticateService;
import Pwd.src.Services.LoginService;
@Path("login")
public class LoginController {
	private LoginService loginService = new LoginService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() {
		return "gotttt";
	}

	@Path("/loginUser")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(@PathParam("userId+Password") String userId) {
		System.out.println(userId);
		//YET TO BE DONE: TODO: List<Field> userFields = userService.getAllFields(userId);
		return Response.ok(userId).build();
	}
	
	@Path("/loginUserJWT")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response jwtUser(@PathParam("userId") String userId,@PathParam("password") String password) {
		System.out.println("Got here");
		JWTAuthenticateService jwtAuth = new JWTAuthenticateService();
		String res = jwtAuth.createJWT(userId,password);
		String jwt = res;
		
		return Response.ok(res).build();
	}
}
