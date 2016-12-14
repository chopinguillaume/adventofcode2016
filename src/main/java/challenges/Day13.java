package challenges;

import util.State;
import util.SubState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

    public static void main(String[] args) {
        int code = 1362;

        //Challenge 1
        List<GridState> steps = solve(code, 31, 39);
        printGrid(code, 33, 41, steps);
        System.out.println("Number of steps = " + (steps.size() - 1));

        //Challenge 2
        List<GridState> visited = visit(code, 50);
        printGrid(code, 25, 25, visited);
        System.out.println(visited.size());
    }

    private static List<GridState> visit(int code, int maxDepth) {

        State init = new State(null, new GridState(code, 1, 1));
        List<State> visited = new ArrayList<>();
        List<GridState> visitedGS = new ArrayList<>();
        List<State> open = new ArrayList<>(); //FIFO
        open.add(init);

        State courant;
        while ((courant = open.get(0)).getDepth() <= maxDepth) {
            open.remove(0);//pop
            visited.add(courant);
            visitedGS.add((GridState) courant.getObject());
            for (GridState gs : ((GridState) courant.getObject()).adjacentOpenSpaces()) {
                State gsState = new State(courant, gs);
                courant.addChild(gsState);
                if (!visited.contains(gsState) && !open.contains(gsState)) {
                    open.add(gsState);
                }
            }
        }

        return visitedGS;
    }

    private static void printGrid(int code, int maxX, int maxY, List<GridState> steps) {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                final int xf = x;
                final int yf = y;
                boolean isAStep = steps.stream().anyMatch(gs -> gs.x == xf && gs.y == yf);
                if (isAStep) {
                    System.out.print("O");
                } else if (new GridState(code, x, y).isValid()) {
                    System.out.print(" ");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    private static List<GridState> solve(int code, int destX, int destY) {

        State init = new State(null, new GridState(code, 1, 1));
        State terminal = new State(null, new GridState(code, destX, destY));
        List<State> open = new ArrayList<>();
        List<State> visited = new ArrayList<>();
        open.add(init);

        State courant = init;
        while (open.size() > 0 && !(courant = open.get(0)).equals(terminal)) {
            open.remove(0); //pop
            visited.add(courant);

            for (GridState gs : ((GridState) courant.getObject()).adjacentOpenSpaces()) {
                State gsState = new State(courant, gs);
                courant.addChild(gsState);
                if (!open.contains(gsState) && !visited.contains(gsState)) {
                    open.add(gsState);
                }
            }
        }

        LinkedList<GridState> steps = new LinkedList<>();
        steps.addLast((GridState) courant.getObject());
        while ((courant = courant.getParent()) != null) {
            steps.addLast((GridState) courant.getObject());
        }
        return steps;
    }

    static class GridState implements SubState {
        int code;
        int x;
        int y;

        GridState(int code, int x, int y) {
            this.code = code;
            this.x = x;
            this.y = y;
        }

        List<GridState> adjacentOpenSpaces() {
            List<GridState> adjacent = new ArrayList<>();

            adjacent.add(new GridState(code, x - 1, y));
            adjacent.add(new GridState(code, x + 1, y));
            adjacent.add(new GridState(code, x, y - 1));
            adjacent.add(new GridState(code, x, y + 1));

            adjacent = adjacent.stream().filter(GridState::isValid).collect(Collectors.toList());

            return adjacent;
        }

        @Override
        public boolean isValid() {
            return x >= 0 && y >= 0 && isOpenSpace();
        }

        private boolean isOpenSpace() {
            int n = x * x + 3 * x + 2 * x * y + y + y * y + code;
            String bin = Integer.toBinaryString(n);
            boolean even = true;
            for (int c = 0; c < bin.length(); c++) {
                if (bin.charAt(c) == '1') {
                    even = !even;
                }
            }
            return even;
        }

        @Override
        public boolean equals(SubState other) {
            if (other instanceof GridState) {
                GridState otherS = (GridState) other;
                return x == otherS.x && y == otherS.y;
            }
            return false;
        }

        @Override
        public String toString() {
            return "GridState{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

}