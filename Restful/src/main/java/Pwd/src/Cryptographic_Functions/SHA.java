package Pwd.src.Cryptographic_Functions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
	public static String getHashedPassword(String clearTextPassword) {

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(clearTextPassword.getBytes());
			return new sun.misc.BASE64Encoder().encode(md.digest());
		} catch (NoSuchAlgorithmException e) {
			// _log.error("Failed to encrypt password.", e);
		}
		return "";
	}

	public static void main(String[] args) {
		System.out.println(getHashedPassword("highjack@1"));
	}
}
