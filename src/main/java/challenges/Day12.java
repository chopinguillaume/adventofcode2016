package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {

    public static void main(String[] args) {

        try {
            BufferedReader reader = Fichier.reader("day12");

            Assembunny assembunny1 = new Assembunny(0, 0, 0, 0);
            Assembunny assembunny2 = new Assembunny(0, 0, 1, 0);

            String line;
            while ((line = reader.readLine()) != null) {
                assembunny1.parseAndAddInstruction(line);
                assembunny2.parseAndAddInstruction(line);
            }

            while (assembunny1.canRun()) {
                assembunny1.run();
            }
            System.out.println(assembunny1.getA());

            while (assembunny2.canRun()) {
                assembunny2.run();
            }
            System.out.println(assembunny2.getA());

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Assembunny {
        private int a = 0;
        private int b = 0;
        private int c = 0;
        private int d = 0;
        private List<InstrBunny> instructions = new ArrayList<>();
        private int currentInstr = 0;
        private Pattern pcpyInt = Pattern.compile("cpy (\\d+) (\\w)");
        private Pattern pcpyReg = Pattern.compile("cpy (\\w) (\\w)");
        private Pattern pinc = Pattern.compile("inc (\\w)");
        private Pattern pdec = Pattern.compile("dec (\\w)");
        private Pattern pjnzInt = Pattern.compile("jnz (\\d+) (-?\\d+)");
        private Pattern pjnz = Pattern.compile("jnz (\\w) (-?\\d+)");

        Assembunny(int a, int b, int c, int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        private void cpy(int value, String registry) {
            setRegistry(registry, value);
        }

        private void setRegistry(String registry, int value) {
            switch (registry) {
                case "a":
                    setA(value);
                    break;
                case "b":
                    setB(value);
                    break;
                case "c":
                    setC(value);
                    break;
                case "d":
                    setD(value);
                    break;
            }
        }

        private int getRegistry(String registry) {
            switch (registry) {
                case "a":
                    return getA();
                case "b":
                    return getB();
                case "c":
                    return getC();
                case "d":
                    return getD();
            }
            return 0;
        }

        private void cpy(String registrySrc, String registryDest) {
            setRegistry(registryDest, getRegistry(registrySrc));
        }

        private void inc(String registry) {
            setRegistry(registry, getRegistry(registry) + 1);
        }

        private void dec(String registry) {
            setRegistry(registry, getRegistry(registry) - 1);
        }

        private void jnz(String registry, int jump) {
            if (getRegistry(registry) != 0) {
                if (currentInstr + jump > 0) {
                    currentInstr = currentInstr + jump - 1;
                }
            }
        }

        private void jnz(int cond, int jump) {
            if (cond != 0) {
                if (currentInstr + jump > 0) {
                    currentInstr = currentInstr + jump - 1;
                }
            }
        }

        void run() {
            if (currentInstr < instructions.size()) {
                InstrBunny nextInstr = instructions.get(currentInstr);
                nextInstr.execute();
                currentInstr++;
            }
        }

        void addInstruction(InstrBunny instr) {
            instructions.add(instr);
        }

        void parseAndAddInstruction(String line) {
            Matcher mcpyInt = pcpyInt.matcher(line);
            if (mcpyInt.find()) {
                addInstruction(() -> cpy(Integer.parseInt(mcpyInt.group(1)), mcpyInt.group(2)));
                return;
            }
            Matcher mcpyReg = pcpyReg.matcher(line);
            if (mcpyReg.find()) {
                addInstruction(() -> cpy(mcpyReg.group(1), mcpyReg.group(2)));
                return;
            }
            Matcher minc = pinc.matcher(line);
            if (minc.find()) {
                addInstruction(() -> inc(minc.group(1)));
                return;
            }
            Matcher mdec = pdec.matcher(line);
            if (mdec.find()) {
                addInstruction(() -> dec(mdec.group(1)));
                return;
            }
            Matcher mjnzInt = pjnzInt.matcher(line);
            if (mjnzInt.find()) {
                addInstruction(() -> jnz(Integer.parseInt(mjnzInt.group(1)), Integer.parseInt(mjnzInt.group(2))));
                return;
            }
            Matcher mjnz = pjnz.matcher(line);
            if (mjnz.find()) {
                addInstruction(() -> jnz(mjnz.group(1), Integer.parseInt(mjnz.group(2))));
                return;
            }
        }

        int getA() {
            return a;
        }

        void setA(int a) {
            this.a = a;
        }

        int getB() {
            return b;
        }

        void setB(int b) {
            this.b = b;
        }

        int getC() {
            return c;
        }

        void setC(int c) {
            this.c = c;
        }

        int getD() {
            return d;
        }

        void setD(int d) {
            this.d = d;
        }

        boolean canRun() {
            return currentInstr < instructions.size();
        }

        interface InstrBunny {
            void execute();
        }
    }
}