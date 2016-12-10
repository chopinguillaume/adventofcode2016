package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {

    public static void main(String[] args) {

        try {
            BufferedReader reader = Fichier.reader("day10");

            Bots bots = new Bots(17, 61);

            //Initialize
            reader.lines().forEach(line -> {
                if (line.startsWith("value")) {
                    Pattern p = Pattern.compile("value (\\d+) goes to bot (\\d+)");
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        int value = Integer.parseInt(m.group(1));
                        int bot = Integer.parseInt(m.group(2));
                        bots.addValue(value, bot);
                    }
                } else {
                    Pattern p = Pattern.compile("bot (\\d+) gives low to (\\w+) (\\d+) and high to (\\w+) (\\d+)");
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        int bot = Integer.parseInt(m.group(1));
                        String lowDest = m.group(2);
                        int low = Integer.parseInt(m.group(3));
                        String highDest = m.group(4);
                        int high = Integer.parseInt(m.group(5));

                        bots.addInstruction(bot, lowDest, low, highDest, high);
                    }
                }
            });

            System.out.println(bots);
            System.out.println("----- Debut de la simulation -----");

            while (bots.hasMoreInstructions()) {
                bots.playNextInstruction();
            }

            System.out.println("----- Fin de la simulation -----");
            System.out.println(bots);
            System.out.println("Multiplication des 3 premiers output = " + bots.getMultiplication());

            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    static class Instruction {
        int bot;
        int low;
        int high;
        String lowDest;
        String highDest;

        Instruction(int bot, int low, int high, String lowDest, String highDest) {
            this.bot = bot;
            this.low = low;
            this.high = high;
            this.lowDest = lowDest;
            this.highDest = highDest;
        }

        @Override
        public String toString() {
            return String.format("bot %d gives low to %s %d and high to %s %d",
                    bot, lowDest, low, highDest, high);
        }
    }

    static class Bots {

        private Map<Integer, List<Integer>> bots = new HashMap<>();
        private Map<Integer, List<Integer>> output = new HashMap<>();
        private Map<Integer, Instruction> instructions = new HashMap<>();

        private int compare_low;
        private int compare_high;

        Bots(int compare_low, int compare_high) {
            this.compare_low = compare_low;
            this.compare_high = compare_high;
        }

        void addValue(int value, int bot) {
            if (bots.containsKey(bot)) {
                if (bots.get(bot).size() < 2) {
                    bots.get(bot).add(value);
                } else {
                    System.err.println("Three microchips in bot " + bot);
                }
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(value);
                bots.put(bot, list);
            }
        }

        boolean give(int bot, String lowDest, int low, String highDest, int high, int compareLow, int compareHigh) {
            boolean result = false;
            List<Integer> botValues = bots.get(bot);
            if (botValues != null && botValues.size() == 2) {
                int valueLow = Math.min(botValues.get(0), botValues.get(1));
                int valueHigh = Math.max(botValues.get(0), botValues.get(1));

                bots.remove(bot);

                if (compareLow == valueLow && compareHigh == valueHigh) {
                    result = true;
                }

                Map<Integer, List<Integer>> destinationLow;
                if (lowDest.equalsIgnoreCase("bot")) {
                    destinationLow = bots;
                } else {
                    destinationLow = output;
                }

                Map<Integer, List<Integer>> destinationHigh;
                if (highDest.equalsIgnoreCase("bot")) {
                    destinationHigh = bots;
                } else {
                    destinationHigh = output;
                }

                addValueToMap(destinationLow, low, valueLow);
                addValueToMap(destinationHigh, high, valueHigh);
            }
            return result;
        }

        private void addValueToMap(Map<Integer, List<Integer>> destMap, int key, int value) {
            if (destMap.containsKey(key)) {
                destMap.get(key).add(value);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(value);
                destMap.put(key, list);
            }
        }

        @Override
        public String toString() {
            return "Bots{" +
                    "bots=" + bots +
                    ", output=" + output +
                    '}';
        }

        void addInstruction(int bot, String lowDest, int low, String highDest, int high) {
            instructions.put(bot, new Instruction(bot, low, high, lowDest, highDest));
        }

        boolean hasMoreInstructions() {
            return instructions.size() > 0;
        }

        void playNextInstruction() {
            if (!hasMoreInstructions()) {
                return;
            }

            Optional<Map.Entry<Integer, List<Integer>>> optional = bots.entrySet().stream()
                                                                       .filter(entry -> entry.getValue().size() == 2)
                                                                       .findFirst();
            if (!optional.isPresent()) {
                return;
            }
            int bot = optional.get().getKey();


            Instruction instr = instructions.get(bot);
            if (give(instr.bot, instr.lowDest, instr.low, instr.highDest, instr.high, compare_low, compare_high)) {
                System.out
                        .println("Comparaison entre " + compare_low + " et " + compare_high + " faite par le bot " + instr.bot);
            }
            instructions.remove(bot);
        }

        int getMultiplication() {
            if (output.containsKey(0) && output.containsKey(1) && output.containsKey(2)) {
                int out0 = output.get(0).get(0);
                int out1 = output.get(1).get(0);
                int out2 = output.get(2).get(0);
                return out0 * out1 * out2;
            }
            return 0;
        }
    }
}
