package account;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
	public String passwordHash(String password,String date) {
		try {
			MessageDigest sha3_256 = MessageDigest.getInstance("SHA3-256");
			byte[]sha3_256_result = sha3_256.digest((password+date).getBytes());
			String hash = String.format("%040x", new BigInteger(1,sha3_256_result));
			return hash;
			
		} catch (NoSuchAlgorithmException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return "-1";
		}
	}

}
