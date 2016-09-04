package metier;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Security {
	public static String encryptPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
		// Cryptage du mot de passe
		byte[] bytes;
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(salt);
		
		bytes = md.digest(password.getBytes());
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i< bytes.length ;i++)
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		return sb.toString();
	}
	
	public static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");	
		byte salt[] = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}
}
