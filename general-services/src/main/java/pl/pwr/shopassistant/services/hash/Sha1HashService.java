package pl.pwr.shopassistant.services.hash;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class Sha1HashService implements HashService {

    public String hash(byte[] data) {
        MessageDigest mDigest;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(data);
            StringBuffer stringBuffer = new StringBuffer();
            for (byte aResult : result) {
                stringBuffer.append(Integer.toString((aResult & 0xff) + 0x100, 16).substring(1));
            }

            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String hash(String data) {
        return this.hash(data.getBytes());
    }
}
