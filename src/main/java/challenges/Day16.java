package challenges;

import input.Fichier;

import java.io.BufferedReader;

public class Day16 {

    public static void main(String[] args) {
        try {
            BufferedReader reader = Fichier.reader("day16");

            String input = reader.readLine();
            String input2 = input;

            //CHALLENGE 1

            int desiredSize = 272;

            while (input.length() < desiredSize) {
                input = applyDragonCurve(input);
            }

            String checksum = input.substring(0, desiredSize);

            while (checksum != null && checksum.length() % 2 == 0) {
                checksum = getChecksum(checksum);
            }
            System.out.println("Final checksum for challenge 1 is : " + checksum);

            //CHALLENGE 2

            int desiredSize2 = 35651584;

            while (input2.length() < desiredSize2) {
                input2 = applyDragonCurve(input2);
            }

            String checksum2 = input2.substring(0, desiredSize2);

            while (checksum2 != null && checksum2.length() % 2 == 0) {
                checksum2 = getChecksum(checksum2);
            }

            System.out.println("Final checksum for challenge 2 is : " + checksum2);

            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }


    private static String getChecksum(String input) {
        if (input.length() % 2 == 0) {
            StringBuilder checksum = new StringBuilder();
            int i = 1;
            while (i < input.length()) {
                char c1 = input.charAt(i - 1);
                char c2 = input.charAt(i);
                checksum.append(c1 == c2 ? '1' : '0');
                i += 2;
            }
            return checksum.toString();
        }
        return null;
    }

    private static String applyDragonCurve(String a) {
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < a.length(); i++) {
            char character = a.charAt(i);
            if (character == '1') {
                character = '0';
            } else {
                character = '1';
            }
            b.append(character);
        }

        b.reverse();

        return a + "0" + b;
    }
}