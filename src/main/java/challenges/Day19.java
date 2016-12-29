package challenges;

import java.util.ArrayList;
import java.util.List;

public class Day19 {

    public static void main(String[] args) {
        int input = 3014603;
        challenge1(input);
        challenge2(input);
    }

    private static void challenge2(int input) {
        if (input == 1) {
            System.out.println("Lucky elf is alone : 1");
            return;
        }
        int highestPowerOfThree = highestPowerThree(input);
        int remain = input - highestPowerOfThree;
        int lucky = Math.max(remain, 2 * remain - highestPowerOfThree);
        System.out.println("Lucky elf n°2 is number " + lucky);
    }

    private static void generateSampleChallenge2(int number) {
        for (int i = 1; i <= number; i++) {
            Simulation sim = new Simulation(i);
            System.out.println(i + " : " + sim.run());
        }
    }

    private static void challenge1(int input) {
        int highestPowerTwo = highestPowerTwo(input);
        int remain = input - highestPowerTwo;
        int lucky = (2 * remain) + 1;
        System.out.println("Lucky elf n°1 is number " + lucky); //Josephus problem
    }

    private static int highestPowerTwo(int n) {
        int pow = 0;
        while (Math.pow(2, pow) <= n) {
            pow++;
        }
        return (int) Math.pow(2, pow - 1);
    }

    private static int highestPowerThree(int n) {
        int pow = 0;
        while (Math.pow(3, pow) < n) {
            pow++;
        }
        return (int) Math.pow(3, pow - 1);
    }


    private static class Simulation {

        private List<Integer> members;

        Simulation(int size) {
            members = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                members.add(i + 1);
            }
        }

        Integer run() {
            int current = 0; //index
            while (members.size() > 1) {
                int size = members.size();
                //kill
                int offset;
                if (size % 2 == 0) {
                    offset = size / 2;
                } else {
                    offset = (size - 1) / 2;
                }
                int killed = (current + offset) % size;
                members.remove(killed);
                //next
                if (current <= killed) {
                    current = (current + 1) % members.size();
                } else {
                    current = current % members.size();
                }

            }
            return members.get(0);
        }

    }
}
