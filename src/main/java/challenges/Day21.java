package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 {

    public static void main(String[] args) {

        try {
            BufferedReader reader = Fichier.reader("day21");

            final Pattern SWAPPOS = Pattern.compile("swap position (?<x>\\d+) with position (?<y>\\d+)");
            final Pattern SWAPLETTER = Pattern.compile("swap letter (?<x>\\w) with letter (?<y>\\w)");
            final Pattern ROTATEL = Pattern.compile("rotate left (?<n>\\d+) steps?");
            final Pattern ROTATER = Pattern.compile("rotate right (?<n>\\d+) steps?");
            final Pattern ROTATEPOS = Pattern.compile("rotate based on position of letter (?<x>\\w)");
            final Pattern REVERSE = Pattern.compile("reverse positions (?<x>\\d+) through (?<y>\\d+)");
            final Pattern MOVE = Pattern.compile("move position (?<x>\\d+) to position (?<y>\\d+)");

            List<Pattern> patterns = Arrays.asList(SWAPPOS, SWAPLETTER, ROTATEL, ROTATER, ROTATEPOS, REVERSE, MOVE);

            List<String> commandes = new ArrayList<>();
            String input;
            while ((input = reader.readLine()) != null) {
                commandes.add(input);
            }

            /*
            PART 1
             */
            String password1 = "abcdefgh";
            password1 = scramble(password1, commandes, patterns);
            System.out.println("Password 1 is : " + password1);

            /*
            PART 2
             */

            String password2 = "fbgdceah";
            password2 = unscramble(password2, commandes, patterns);
            System.out.println("Password 2 is : " + password2);

            /*
            VERIFICATION
             */
            password2 = scramble(password2, commandes, patterns);
            System.out.println("Scrambled p2 must be fbgdceah : " + password2 + " : " + (password2.equals("fbgdceah")));
            password1 = unscramble(password1, commandes, patterns);
            System.out.println("Unscrambled p1 must be abcdefgh : " + password1 + " : " + (password1.equals("abcdefgh")));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String unscramble(String password, List<String> commandes, List<Pattern> patterns) {

        for (int c = commandes.size() - 1; c >= 0; c--) {
            String cmd = commandes.get(c);

            for (int i = 0; i < patterns.size(); i++) {
                Pattern pattern = patterns.get(i);

                Matcher matcher = pattern.matcher(cmd);
                if (matcher.matches()) {

                    switch (i) {
                        case 0:
                            password = swappos(password, matcher.group("x"), matcher.group("y"));
                            break;
                        case 1:
                            password = swapletter(password, matcher.group("x"), matcher.group("y"));
                            break;
                        case 2:
                            password = rotater(password, matcher.group("n"));
                            break;
                        case 3:
                            password = rotatel(password, matcher.group("n"));
                            break;
                        case 4:
                            password = irotatepos(password, matcher.group("x"));
                            break;
                        case 5:
                            password = reverse(password, matcher.group("x"), matcher.group("y"));
                            break;
                        case 6:
                            password = move(password, matcher.group("y"), matcher.group("x"));
                            break;
                    }
                }
            }
        }
        return password;
    }

    private static String scramble(String password, List<String> commandes, List<Pattern> patterns) {
        for (String cmd : commandes) {

            for (int i = 0; i < patterns.size(); i++) {
                Pattern pattern = patterns.get(i);

                Matcher matcher = pattern.matcher(cmd);
                if (matcher.matches()) {

                    switch (i) {
                        case 0:
                            password = swappos(password, matcher.group("x"), matcher.group("y"));
                            break;
                        case 1:
                            password = swapletter(password, matcher.group("x"), matcher.group("y"));
                            break;
                        case 2:
                            password = rotatel(password, matcher.group("n"));
                            break;
                        case 3:
                            password = rotater(password, matcher.group("n"));
                            break;
                        case 4:
                            password = rotatepos(password, matcher.group("x"));
                            break;
                        case 5:
                            password = reverse(password, matcher.group("x"), matcher.group("y"));
                            break;
                        case 6:
                            password = move(password, matcher.group("x"), matcher.group("y"));
                            break;
                    }
                }
            }
        }
        return password;
    }

    private static String irotatepos(String password, String x) {
        int newIndex = password.indexOf(x);

        int i = 0;
        while (i < password.length()) {
            int n = i + 1;
            if (n > 4) {
                n++;
            }

            int oldi = newIndex - n;
            while (oldi < 0) {
                oldi += password.length();
            }

            if (oldi == i) {
                return rotatel(password, String.valueOf(n));
            }
            i++;
        }

        //Should not go here
        return password;
    }

    private static String swappos(String password, String x, String y) {
        int xpos = Integer.parseInt(x);
        int ypos = Integer.parseInt(y);
        if (xpos > ypos) {
            int tmp = ypos;
            ypos = xpos;
            xpos = tmp;
        }

        return password.substring(0, xpos) + password.charAt(ypos) + password.substring(xpos + 1, ypos) + password.charAt(xpos) + password.substring(ypos + 1);
    }

    private static String swapletter(String password, String x, String y) {
        int xpos = password.indexOf(x);
        int ypos = password.indexOf(y);
        if (xpos > ypos) {
            int tmp = ypos;
            ypos = xpos;
            xpos = tmp;
        }

        return password.substring(0, xpos) + password.charAt(ypos) + password.substring(xpos + 1, ypos) + password.charAt(xpos) + password.substring(ypos + 1);
    }

    private static String rotatel(String password, String n) {
        int nb = Integer.parseInt(n) % password.length();
        return password.substring(nb) + password.substring(0, nb);
    }

    private static String rotater(String password, String n) {
        int nb = Integer.parseInt(n) % password.length();
        return password.substring(password.length() - nb) + password.substring(0, password.length() - nb);
    }

    private static String rotatepos(String password, String x) {
        int nb = password.indexOf(x);
        if (nb >= 4) {
            nb += 2;
        } else {
            nb += 1;
        }
        nb = nb % password.length();
        return rotater(password, String.valueOf(nb));
    }

    private static String reverse(String password, String x, String y) {
        int xpos = Integer.parseInt(x);
        int ypos = Integer.parseInt(y);
        if (xpos > ypos) {
            int tmp = ypos;
            ypos = xpos;
            xpos = tmp;
        }

        String reversedMiddle = new StringBuilder(password.substring(xpos, ypos + 1)).reverse().toString();

        return password.substring(0, xpos) + reversedMiddle + password.substring(ypos + 1);
    }

    private static String move(String password, String x, String y) {
        int xpos = Integer.parseInt(x);
        int ypos = Integer.parseInt(y);

        if (xpos < ypos) {
            return password.substring(0, xpos) + password.substring(xpos + 1, ypos + 1) + password.charAt(xpos) + password.substring(ypos + 1);
        } else {
            return password.substring(0, ypos) + password.charAt(xpos) + password.substring(ypos, xpos) + password.substring(xpos + 1);
        }
    }
}
