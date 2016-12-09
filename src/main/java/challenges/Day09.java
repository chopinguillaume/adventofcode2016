package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09 {

    public static void main(String[] args) {

        try {
            BufferedReader reader = Fichier.reader("day09");

            reader.lines().forEach(line -> {
                System.out.println(line);
                System.out.println("Premiere longueur = "+firstLength(line));
                System.out.println("Longueur totale = "+fullLength(line));
            });

            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static int firstLength(String line) {
        Pattern p = Pattern.compile("\\((\\d+)x(\\d+)\\)");
        int length = 0;
        int currentChar = 0;

        while (currentChar < line.length()) {
            if (line.charAt(currentChar) == '(') {//Repetition
                Matcher m = p.matcher(line);
                if (m.find(currentChar)) {
                    int sublength = Integer.parseInt(m.group(1));
                    int repeat = Integer.parseInt(m.group(2));
                    int subStart = m.end();
                    length += repeat * sublength;
                    currentChar = subStart + sublength;
                }
            } else {//Normal Character counts for 1
                currentChar++;
                length++;
            }
        }

        return length;
    }

    private static BigInteger fullLength(String line) {
        Pattern p = Pattern.compile("\\((\\d+)x(\\d+)\\)");
        BigInteger length = BigInteger.ZERO;
        int currentChar = 0;

        while (currentChar < line.length()) {
            if (line.charAt(currentChar) == '(') {//Repetition
                Matcher m = p.matcher(line);
                if (m.find(currentChar)) {
                    int sublength = Integer.parseInt(m.group(1));
                    int repeat = Integer.parseInt(m.group(2));
                    int subStart = m.end();
                    length = length.add(BigInteger.valueOf(repeat).multiply(fullLength(line
                            .substring(subStart, subStart + sublength))));
                    //Recursive call to compute repeats in the repeated part
                    currentChar = subStart + sublength;
                }
            } else {//Normal Character counts for 1
                currentChar++;
                length = length.add(BigInteger.ONE);
            }
        }
        return length;
    }
}
