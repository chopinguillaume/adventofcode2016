package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day04 {

    public static void main(String[] args) {

        try {
            BufferedReader reader = Fichier.reader("day04");

            String line = reader.readLine();
            int total = 0;

            while (line != null && !line.isEmpty()) {

                if (isReal(line)) {
                    //CHALLENGE 1
                    total += sectorID(line);

                    //CHALLENGE 2
                    System.out.println(decipher(line));
                }

                line = reader.readLine();
            }

            System.out.println("Total des SectorID = " + total);

            reader.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private static int sectorID(String line) {
        String sector = line.replaceFirst("[\\w-]*(\\d{3})\\[\\w*\\]", "$1"); //Garde les 3 chiffres
        return Integer.parseInt(sector);
    }

    private static String checksum(String line) {
        return line.replaceFirst("[\\w-]*\\d{3}\\[(\\w*)\\]", "$1"); //Garde la partie entre crochets
    }

    private static boolean isReal(String line) {
        return checksum(line).equals(compute(line));
    }

    private static String compute(String line) {

        //Nettoyage de la ligne
        line = line.replaceFirst("([\\w-]*)\\d{3}\\[\\w*\\]", "$1"); //Garde seulement la premiere partie
        line = line.replace("-", "");

        Map<Character, Integer> letters = new HashMap<>();

        //Comptage des lettres
        for (char c : line.toCharArray()) {
            if (letters.containsKey(c)) {
                letters.put(c, letters.get(c) + 1);
            } else {
                letters.put(c, 1);
            }
        }

        LinkedHashMap<Character, Integer> sorted =
                letters.entrySet()
                       .stream()
                       .sorted(Map.Entry.comparingByKey())
                       .sorted(Map.Entry
                               .comparingByValue(Collections.reverseOrder()))
                       .collect(Collectors.toMap(
                               Map.Entry::getKey,
                               Map.Entry::getValue,
                               (e1, e2) -> e1,
                               LinkedHashMap::new
                       ));

        String result = "";
        Iterator<Map.Entry<Character, Integer>> it = sorted.entrySet().iterator();

        for (int i = 0; i < 5; i++) {
            Character character = it.next().getKey();
            result += character;
        }

        return result;
    }

    private static String decipher(String line) {
        int offset = sectorID(line);

        line = line.replaceFirst("([\\w-]*\\d{3})\\[\\w*\\]", "$1"); //Garde la premiere partie + les chiffres
        line = line.replace("-", " ");

        String result = "";
        for (char c : line.toCharArray()) {
            if (Character.isLetter(c)) {
                //Remplacer la lettre par la nouvelle
                c += (offset % 26);
                if( c > 'z' ){
                    c -= 26;
                }
            }
            result += c;
        }

        return result;
    }

}
