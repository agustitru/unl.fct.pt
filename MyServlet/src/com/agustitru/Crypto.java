package com.agustitru;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;



public class Crypto {
	
	private static final String ALGO = "AES";
	private static final byte[] keyValue =
	new byte[] { 'F', 'C', 'T', '/', 'U', 'N', 'L', 'r',
	'o', 'c', 'k','s', '!', '!', 'd', 'i' };
	
	Key key = new SecretKeySpec(keyValue, ALGO);
	public Crypto() {
	}
	
	public String encrypt(String Data) throws Exception {
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		
		return java.util.Base64.getEncoder().encodeToString(encVal);
	}
	
	public String decrypt(String encrypted) throws Exception {
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decodedValue = java.util.Base64.
		getDecoder().decode(encrypted);
		byte[] decValue = c.doFinal(decodedValue);
		return new String(decValue);
	}

}
