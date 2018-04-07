package Pwd.src.Controllers;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.sun.media.jfxmedia.Media;

import Pwd.src.DBConnect.Constants_PWD;
import Pwd.src.Services.JWTAuthenticateService;
import Pwd.src.Services.LoginService;

@Path("login")
public class LoginController {
	private LoginService loginService = new LoginService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() {
		System.out.println("Request received");
		return "gotttt";
	}

	@Path("/createAccount")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(@HeaderParam(Constants_PWD.userInput) String userInput) {
		System.out.println("In create account for " + userInput);
		boolean response = loginService.createUser(userInput);
		if (response) {
			System.out.println("Response is " + response);
			String jsonResponse = new JSONObject().put("CreatedUser", response).toString();
			return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(404).entity("ERROR while SignUp").type(MediaType.APPLICATION_JSON).build();
		}
	}

	@Path("/loginUser")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(@HeaderParam(Constants_PWD.userInput) String userInput) {
		System.out.println("In Login User:" + userInput);
		String responseUserId = loginService.userAuthenticate(userInput);
		System.out.println();
		if (responseUserId != "false") {
			//System.out.println("Response is " + responseUserId);
			String jsonResponse = new JSONObject().put("JWTToken", jwtUser(responseUserId)).toString();
			System.out.println(jsonResponse);
			return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).build();
		} else {
			System.out.println(new Date().getTime());
			return Response.status(404).entity("ERROR while Login").type(MediaType.APPLICATION_JSON).build();
		}
	}

	@Path("/loginUserJWT/{userId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String jwtUser(@PathParam(Constants_PWD.jwtToken) String jwtToken) {
		JWTAuthenticateService jwtAuth = new JWTAuthenticateService();
		String res = jwtAuth.createJWT(jwtToken);
		return res;
	}
}
