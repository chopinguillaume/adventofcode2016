package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Day18 {
    public static void main(String[] args) {

        String input = "";
        try {
            BufferedReader reader = Fichier.reader("day18");
            input = reader.readLine();
            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        List<String> rows = new ArrayList<>();
        rows.add(input);

        int length = input.length();
        int i = 1;
        while (i < 400000) {
            StringBuilder row = new StringBuilder();
            int pos = 0;
            while (pos < length) {
                row.append(getTile(pos, rows.get(i - 1)));
                pos++;
            }
            rows.add(row.toString());
            i++;
        }

        int count = 0;
        for (String row : rows) {
            for (char c : row.toCharArray()) {
                if (c == '.') {
                    count++;
                }
            }
        }
        System.out.println("Number of safe tiles = " + count);
    }

    private static String getTile(int pos, String prevRow) {
        boolean left; //true = trap, false = safe
        boolean right;
        left = (pos != 0) && (prevRow.charAt(pos - 1) == '^');
        right = (pos != (prevRow.length() - 1)) && (prevRow.charAt(pos + 1) == '^');

        return left ^ right ? "^" : ".";
    }
}
