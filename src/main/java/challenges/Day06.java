package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.util.*;

public class Day06 {

    public static void main(String[] args) {

        try {
            BufferedReader reader = Fichier.reader("day06");

            List<Map<Character, Integer>> occurrences = new ArrayList<>();

            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {

                for (int i = 0; i < line.toCharArray().length; i++) {
                    char c = line.charAt(i);

                    if (occurrences.size() <= i) {
                        occurrences.add(new HashMap<>());
                    }

                    Map<Character, Integer> map = occurrences.get(i);

                    if (map.containsKey(c)) {
                        map.put(c, map.get(c) + 1);
                    } else {
                        map.put(c, 1);
                    }
                }

                line = reader.readLine();
            }
            reader.close();


            //RESULT
            String result = "";
            String result2 = "";
            for (Map<Character, Integer> map : occurrences) {

                Character c = map.entrySet().stream()
                                 .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                 .findFirst().get().getKey();

                Character c2 = map.entrySet().stream()
                                  .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                                  .findFirst().get().getKey();

                result += c;
                result2 += c2;
            }

            System.out.println("Le code est : " + result);
            System.out.println("Le code 2 est : " + result2);

        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
