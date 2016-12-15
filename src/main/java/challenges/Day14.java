package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Day14 {
    public static void main(String[] args) {
        try {
            BufferedReader reader = Fichier.reader("day14");
            String salt = reader.readLine();
            reader.close();

            int hashIndex = 0;
            int count = 0;

            HashGetter hasher = new HashGetter(2017); // Set to 1 for challenge 1

            long startTime = System.nanoTime();

            while (count < 64) {
                String hash = hasher.getHashOf(salt + hashIndex);
                char repeatedChar = containsTriple(hash);
                if (repeatedChar != 0) {
                    String keyHash = isKey(salt, hashIndex, repeatedChar, hasher);
                    if (keyHash != null) {
                        count++;
                        System.out.println(count + ":\t" + hashIndex);
                        System.out.println(hash);
                        System.out.println(keyHash);
                    }
                }
                hashIndex++;
            }
            System.out.println("64th key is index : " + (hashIndex - 1));

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println(duration / 1000000);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String isKey(String salt, int hashIndex, char repeatedChar, HashGetter hasher) {
        int i = hashIndex + 1;
        while (i <= hashIndex + 1000) {
            String hash = hasher.getHashOf(salt + i);
            if (containsFive(hash, repeatedChar)) {
                return hash;
            }
            i++;
        }
        return null;
    }

    private static boolean containsFive(String hash, char repeatedChar) {
        int i = 4;
        while (i < hash.length()) {
            if (repeatedChar == hash.charAt(i - 4) &&
                    hash.charAt(i - 4) == hash.charAt(i - 3) &&
                    hash.charAt(i - 3) == hash.charAt(i - 2) &&
                    hash.charAt(i - 2) == hash.charAt(i - 1) &&
                    hash.charAt(i - 1) == hash.charAt(i)) {
                return true;
            }
            i++;
        }
        return false;
    }

    private static char containsTriple(String hash) {
        int i = 2;
        while (i < hash.length()) {
            if (hash.charAt(i - 2) == hash.charAt(i - 1) && hash.charAt(i - 1) == hash.charAt(i)) {
                return hash.charAt(i);
            }
            i++;
        }
        return 0;
    }

    private static class HashGetter {
        private int numberOfHash;
        private Map<String, String> hashed = new HashMap<>();

        HashGetter(int numberOfHash) {
            this.numberOfHash = numberOfHash;
        }

        String getHashOf(String input) {
            if (hashed.containsKey(input)) {
                return hashed.get(input);
            }
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");

                String hashtext = input;
                int i = 0;
                while (i < numberOfHash) {
                    i++;
                    byte[] bytesInput = hashtext.getBytes("UTF-8");
                    byte[] thedigest = md.digest(bytesInput);

                    BigInteger bigInt = new BigInteger(1, thedigest);
                    hashtext = bigInt.toString(16);

                    while (hashtext.length() < 32) {
                        hashtext = "0" + hashtext;
                    }
                }
                hashed.put(input, hashtext);
                return hashtext;

            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }

    }
}