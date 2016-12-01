package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static challenges.Day01.Direction.*;

public class Day01 {

    private int x = 0;
    private int y = 0;
    private Direction current = NORD;

    private Set<String> lieuxVisites = new HashSet<>();
    private boolean dejaTrouveHQ = false;
    private int xHQ;
    private int yHQ;

    private Day01() {
        lieuxVisites.add("0,0");
    }

    public static void main(String[] args) {
        try {
            //PREPARE DATA
            BufferedReader brTest = Fichier.reader("day01");
            String inputLine = brTest.readLine();

            String[] instructions = inputLine.split(",");
            for (int i = 0; i < instructions.length; i++) {
                instructions[i] = instructions[i].trim();
            }

            List<String> inputs = Arrays.asList(instructions);

            //USE DATA
            Day01 santa = new Day01();

            inputs.forEach(s -> {
                char direction = s.charAt(0);
                int distance = Integer.parseInt(s.substring(1));
                santa.deplacer(direction, distance);
            });

            //CHALLENGE 1
            System.out.println("Depart en : (0, 0)");
            System.out.println("Arrivee en : (" + santa.x + ", " + santa.y + ")");
            System.out.println("Distance du départ = " + (Math.abs(santa.x) + Math.abs(santa.y)));

            //CHALLENGE 2
            int xHQ = santa.xHQ;
            int yHQ = santa.yHQ;

            System.out.println("Premier lieu visite deux fois : (" + xHQ + ", " + yHQ + ")");
            System.out.println("Distance du lieu = " + (Math.abs(xHQ) + Math.abs(yHQ)));

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void deplacer(char direction, int distance) {
        tourner(direction);
        avancer(distance);
    }

    private void avancer(int distance) {
        if (distance >= 1) {
            switch (current) {
                case NORD:
                    y -= 1;
                    break;
                case SUD:
                    y += 1;
                    break;
                case EST:
                    x += 1;
                    break;
                case OUEST:
                    x -= 1;
                    break;
            }
            if (!dejaTrouveHQ && lieuxVisites.contains(x + "," + y)) {
                dejaTrouveHQ = true;
                xHQ = x;
                yHQ = y;
            }
            lieuxVisites.add(x + "," + y);
            avancer(distance - 1);
        }
    }

    private void tourner(char direction) {
        switch (current) {
            case NORD:
                current = (direction == 'R' ? EST : OUEST);
                break;
            case SUD:
                current = (direction == 'R' ? OUEST : EST);
                break;
            case EST:
                current = (direction == 'R' ? SUD : NORD);
                break;
            case OUEST:
                current = (direction == 'R' ? NORD : SUD);
                break;
        }
    }

    enum Direction {
        NORD, SUD, EST, OUEST
    }
}
