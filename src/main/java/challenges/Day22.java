package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 {

    public static void main(String[] args) {

        try {
            BufferedReader reader = Fichier.reader("day22");

            Pattern p = Pattern.compile("/dev/grid/node-x(?<x>\\d+)-y(?<y>\\d+) *(?<size>\\d+)T *(?<used>\\d+)T *(\\d+)T *(\\d+)%");

            /*
            Create nodes
             */
            List<Node> allNodes = new ArrayList<>();
            int maxX = 0;
            int maxY = 0;

            String input;
            while ((input = reader.readLine()) != null) {
                Matcher m = p.matcher(input);
                if (m.matches()) {
                    int x = Integer.parseInt(m.group("x"));
                    int y = Integer.parseInt(m.group("y"));
                    int size = Integer.parseInt(m.group("size"));
                    int used = Integer.parseInt(m.group("used"));
                    Node node = new Node(x, y, size, used);
                    allNodes.add(node);

                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }

            int pairs = 0;

            for (int i = 0; i < allNodes.size(); i++) {
                Node node = allNodes.get(i);

                for (int j = 0; j < allNodes.size(); j++) {
                    if (i != j) {
                        Node other = allNodes.get(j);

                        if (node.used <= other.avail && node.used > 0) {
                            pairs++;
                        }
                    }
                }
            }

            System.out.println(pairs);


        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    static class Node {
        int x;
        int y;
        int capacity;
        int used;
        int avail;

        Node(int x, int y, int capacity, int used) {
            this.x = x;
            this.y = y;
            this.capacity = capacity;
            this.used = used;
            this.avail = capacity - used;
        }
    }
}
