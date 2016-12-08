package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08 {

    public static void main(String[] args) {

        try {
            BufferedReader reader = Fichier.reader("day08");
            Ecran ecran = new Ecran();

            String instr = reader.readLine();
            while (instr != null && !instr.isEmpty()) {

                //PARSE INSTRUCTION
                if (instr.startsWith("rect")) {
                    Pattern pattern = Pattern.compile("(\\d+)x(\\d+)");
                    Matcher matcher = pattern.matcher(instr);
                    if (matcher.find()) {
                        int width = Integer.parseInt(matcher.group(1));
                        int height = Integer.parseInt(matcher.group(2));

                        ecran.createRectangle(width, height);
                    }
                } else if (instr.startsWith("rotate")) {
                    Pattern pattern = Pattern.compile("(\\d+) by (\\d+)");
                    Matcher matcher = pattern.matcher(instr);
                    if (matcher.find()) {
                        int columnOrRow = Integer.parseInt(matcher.group(1));
                        int offset = Integer.parseInt(matcher.group(2));

                        if (instr.contains("column")) {
                            ecran.rotateColumn(columnOrRow, offset);
                        } else if (instr.contains("row")) {
                            ecran.rotateRow(columnOrRow, offset);
                        }
                    }
                }
                instr = reader.readLine();
            }

            ecran.print();
            System.out.println("Nombre de LEDs allumées = " + ecran.numberLit());

            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static class Ecran {

        boolean[][] ecran = new boolean[6][50];

        void createRectangle(int width, int height) {
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    ecran[h][w] = true;
                }
            }
        }

        void rotateRow(int row, int offset) {
            boolean[] copy = new boolean[ecran[row].length];
            //SHIFT
            for (int i = 0; i < ecran[row].length; i++) {
                int newi = (i + offset) % ecran[row].length;
                copy[newi] = ecran[row][i];
            }
            //COPY
            System.arraycopy(copy, 0, ecran[row], 0, copy.length);
        }

        void rotateColumn(int column, int offset) {
            boolean[] copy = new boolean[ecran.length];
            //SHIFT
            for (int i = 0; i < ecran.length; i++) {
                int newi = (i + offset) % ecran.length;
                copy[newi] = ecran[i][column];
            }
            //COPY
            for (int i = 0; i < copy.length; i++) {
                ecran[i][column] = copy[i];
            }
        }

        void print() {
            for (boolean[] line : ecran) {
                for (boolean b : line) {
                    if (b) {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println();
            }
        }

        int numberLit() {
            int lit = 0;
            for (boolean[] line : ecran) {
                for (boolean b : line) {
                    if (b) {
                        lit++;
                    }
                }
            }
            return lit;
        }
    }
}
