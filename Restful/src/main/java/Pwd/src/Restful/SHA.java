package Pwd.src.Restful;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
	public static String getEncryptedPassword(String clearTextPassword)   {  
		  AES aes = new AES();
	    
	    try {
	      MessageDigest md = MessageDigest.getInstance("SHA-256");
	      md.update(clearTextPassword.getBytes());
	      return new sun.misc.BASE64Encoder().encode(md.digest());
	    } catch (NoSuchAlgorithmException e) {
	      //_log.error("Failed to encrypt password.", e);
	    }
	    return "";
	  }
	public static void main(String[] args) {
		final String secretKey = "ssshhhhhhhhhhh!!!!";

		String originalString = "howtodoinjava.com";
		String encryptedString = AES.encrypt(originalString, secretKey);
		String decryptedString = AES.decrypt(encryptedString, secretKey);

		System.out.println(originalString);
		System.out.println(encryptedString);
		System.out.println(getEncryptedPassword(encryptedString));
		System.out.println(decryptedString);
	}

}
