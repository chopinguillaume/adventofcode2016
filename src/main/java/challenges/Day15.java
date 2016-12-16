package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {
    public static void main(String[] args) {
        try {
            BufferedReader reader = Fichier.reader("day15challenge2");

            Pattern p = Pattern.compile("Disc #(\\d+) has (\\d+) positions; at time=0, it is at position (\\d+).");
            Discs discs = new Discs();
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                Matcher m = p.matcher(line);
                if (m.find()) {
                    int numDisc = Integer.parseInt(m.group(1));
                    int nPos = Integer.parseInt(m.group(2));
                    int startPos = Integer.parseInt(m.group(3));
                    discs.add(numDisc, nPos, startPos);
                }
            }

            int startingTime = 0;
            while (!discs.fallsThroughAllDiscs(startingTime)) {
                startingTime++;
            }
            System.out.println("First valid starting time = " + startingTime);

            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private static class Discs {
        List<Disc> discList = new ArrayList<>();

        void add(int numero, int nPos, int startPos) {
            discList.add(new Disc(numero, nPos, startPos));
        }

        boolean fallsThroughAllDiscs(int startingTime) {
            boolean through = true;
            int d = 0;
            while (through && d < discList.size()) {
                through = discList.get(d).fallsThrough(startingTime);
                d++;
            }
            return through;
        }

        private class Disc {
            int numero;
            int numberOfPositions;
            int startingPosition;

            public Disc(int numero, int numberOfPositions, int startingPosition) {
                this.numero = numero;
                this.numberOfPositions = numberOfPositions;
                this.startingPosition = startingPosition;
            }

            boolean fallsThrough(int startingTime) {
                return (startingPosition + numero + startingTime) % numberOfPositions == 0;
            }
        }
    }
}
