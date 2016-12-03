package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Day03 {
    public static void main(String[] args) {

        BufferedReader reader = null;

        try {
            reader = Fichier.reader("day03");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            int total = 0;
            int total2 = 0;

            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {

                String line2 = reader.readLine();
                String line3 = reader.readLine();

                String[][] cotes = new String[3][3];

                cotes[0] = line.trim().split("( )+");
                cotes[1] = line2.trim().split("( )+");
                cotes[2] = line3.trim().split("( )+");

                //CHALLENGE 1
                if(isTriangle(cotes[0][0], cotes[0][1], cotes[0][2])){ total++; }
                if(isTriangle(cotes[1][0], cotes[1][1], cotes[1][2])){ total++; }
                if(isTriangle(cotes[2][0], cotes[2][1], cotes[2][2])){ total++; }

                //CHALLENGE 2
                if(isTriangle(cotes[0][0], cotes[1][0], cotes[2][0])){ total2++; }
                if(isTriangle(cotes[0][1], cotes[1][1], cotes[2][1])){ total2++; }
                if(isTriangle(cotes[0][2], cotes[1][2], cotes[2][2])){ total2++; }



                line = reader.readLine();
            }

            System.out.println("Nombre de triangles horizontalement = " + total);
            System.out.println("Nombre de triangles verticalement = " + total2);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isTriangle(String as, String bs, String cs) {
        int a = Integer.parseInt(as);
        int b = Integer.parseInt(bs);
        int c = Integer.parseInt(cs);
        return (a + b > c && a + c > b && b + c > a);
    }
}
