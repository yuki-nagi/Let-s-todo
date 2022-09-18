package accountModel;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// パスワードをハッシュ化するメソッド
// ソルトにdateを利用していることに注意

public class PasswordHash {
	public String passwordHash(String password,String date) {
		try {
			MessageDigest sha3_256 = MessageDigest.getInstance("SHA3-256");
			byte[]sha3_256_result = sha3_256.digest((password+date).getBytes());
			String hash = String.format("%040x", new BigInteger(1,sha3_256_result));
			return hash;
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "-1";
		}
	}

}
