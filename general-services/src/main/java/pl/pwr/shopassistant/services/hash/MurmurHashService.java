package pl.pwr.shopassistant.services.hash;

import org.springframework.stereotype.Service;

@Service
public class MurmurHashService implements HashService {

    private int calcHash(byte[] data) {
        int m = 0x5bd1e995;
        int r = 24;

        int len = data.length;
        int h = 31337 ^ len;

        int i = 0;
        while (len  >= 4) {
            int k = data[i + 0] & 0xFF;
            k |= (data[i + 1] & 0xFF) << 8;
            k |= (data[i + 2] & 0xFF) << 16;
            k |= (data[i + 3] & 0xFF) << 24;

            k *= m;
            k ^= k >>> r;
            k *= m;

            h *= m;
            h ^= k;

            i += 4;
            len -= 4;
        }

        switch (len) {
            case 3: h ^= (data[i + 2] & 0xFF) << 16;
            case 2: h ^= (data[i + 1] & 0xFF) << 8;
            case 1: h ^= (data[i + 0] & 0xFF);
                h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;

        return h;
    }

    private String toReadableString(int hash) {
        StringBuilder readableString = new StringBuilder();
        String available = "abcdef0123456789";    // alfabet

        int mask = 0x0F;

        for(int i = 0; i<8; i++) {
            int tmp = hash & mask;
            mask = mask << 4;
            tmp = tmp >> 4*i;
            readableString.append(available.charAt(tmp & 0x0F));
        }

        return readableString.toString();
    }

    public String hash(byte[] data) {
        Integer hash = calcHash(data);
        String readable = toReadableString(hash);
        return readable;
    }

    public String hash(String data) {
        return hash(data.getBytes());
    }
}
