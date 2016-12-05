package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day05 {

    public static void main(String[] args) {

        try {
            BufferedReader reader = Fichier.reader("day05");

            String doorID = reader.readLine();

            int i = 0;
            String result1 = "";

            int cpt2 = 0;
            char[] result2 = new char[8];

            while (cpt2 < 8) {

                String idPlusi = doorID + i;
                byte[] bytesOfDoorID = idPlusi.getBytes("UTF-8");

                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] thedigest = md.digest(bytesOfDoorID);

                BigInteger bigInt = new BigInteger(1,thedigest);
                String hashtext = bigInt.toString(16);

                while(hashtext.length() < 32 ){
                    hashtext = "0"+hashtext;
                }

                if (hashtext.startsWith("00000")) {

                    //CHALLENGE 1
                    result1 += hashtext.charAt(5);

                    //CHALLENGE 2
                    int position = hashtext.charAt(5);
                    position -= '0';
                    if(position >= 0 && position < 8 && result2[position]==0){
                        result2[position] = hashtext.charAt(6);
                        cpt2++;
                    }
                }

                i++;
            }

            System.out.println("Code 1 : "+result1.substring(0,8));
            System.out.print("Code 2 : ");
            for (char c : result2) {
                System.out.print(c);
            }

        } catch (java.io.IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
