package pl.pwr.shopassistant.services.hash;


public interface HashService {
    public String hash(byte[] data);
    public String hash(String data);
}
