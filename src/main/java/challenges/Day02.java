package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Day02 {

    public static void main(String[] args) {

        BufferedReader reader = null;

        try {
            reader = Fichier.reader("day02");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Clavier1 clavier1 = new Clavier1();
        Clavier2 clavier2 = new Clavier2();
        StringBuilder result1 = new StringBuilder();
        StringBuilder result2 = new StringBuilder();

        reader.lines().forEach(line -> {
            line.chars().forEach(c -> {
                clavier1.deplacer((char) c);
                clavier2.deplacer((char) c);
            });
            result1.append(clavier1.getNumero());
            result2.append(clavier2.getNumero());
        });

        System.out.println("Le code du clavier 1 est : " + result1.toString());
        System.out.println("Le code du clavier 2 est : " + result2.toString());

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Clavier1 {
        private int x = 1;
        private int y = 1; //Debut sur le 5, au milieu, en case (1,1)

        private int[][] clavier = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        void deplacer(char direction) {
            switch (direction) {
                case 'U':
                    if (y > 0) {
                        y--;
                    }
                    break;
                case 'D':
                    if (y < 2) {
                        y++;
                    }
                    break;
                case 'L':
                    if (x > 0) {
                        x--;
                    }
                    break;
                case 'R':
                    if (x < 2) {
                        x++;
                    }
                    break;
            }
        }

        int getNumero() {
            return clavier[y][x];
        }
    }

    private static class Clavier2 {

        private int x = 0;
        private int y = 2;
        private char[][] clavier = {{0, 0, '1', 0, 0}, {0, '2', '3', '4', 0}, {'5', '6', '7', '8', '9'}, {0, 'A', 'B', 'C', 0}, {0, 0, 'D', 0, 0}};

        void deplacer(char direction) {
            switch (direction) {
                case 'U':
                    if (y > 0 && clavier[y - 1][x] != 0) {
                        y--;
                    }
                    break;
                case 'D':
                    if (y < 4 && clavier[y + 1][x] != 0) {
                        y++;
                    }
                    break;
                case 'L':
                    if (x > 0 && clavier[y][x - 1] != 0) {
                        x--;
                    }
                    break;
                case 'R':
                    if (x < 4 && clavier[y][x + 1] != 0) {
                        x++;
                    }
                    break;
            }
        }

        char getNumero() {
            return clavier[y][x];
        }
    }

}
