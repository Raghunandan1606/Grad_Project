package Pwd.src.Services;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

import Pwd.src.DBConnect.Constants_PWD;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTAuthenticateService {
	public static void main(String[] args) {
		String sy = createJWT("");
	}

	public static String createJWT(String userId) {
		try {
			LocalDate localDate = LocalDate.now().plusDays(1);
			Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			String jwt = Jwts.builder().setSubject("users/TzMUocMF4p").setExpiration(date)
					.claim(userId, Constants_PWD.TokenSystem).claim("scope", "self groups/admins")
					.signWith(SignatureAlgorithm.HS256, Constants_PWD.jwtSecretKey.getBytes("UTF-8")).compact();
			// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2Vycy9Uek1Vb2NNRjRwIiwiZXhwIjoxNTIwNDg1MjAwLCJuYW1lIjoiUm9iZXJ0IFRva2VuIE1hbiIsInNjb3BlIjoic2VsZiBncm91cHMvYWRtaW5zIn0.dRzfuyTDmA9ZOY_2-QRrlsI2KgWIAGMTWjpC1jl6mJw
			// boolean isTrue = validateJWT(jwt);
			// System.out.println("Finished JWT:" + jwt);
			// if (isTrue)
			return jwt.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "false";
		}
	}

	public static boolean validateJWT(String res) {
		String jwt = res;
		Jws<Claims> claims;
		try {
			System.out.println("Validating");
			claims = Jwts.parser().setSigningKey(Constants_PWD.jwtSecretKey.getBytes("UTF-8")).parseClaimsJws(jwt);
			System.out.println("Succesfully validated");
			return true;
		} catch (ExpiredJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (UnsupportedJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (MalformedJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
