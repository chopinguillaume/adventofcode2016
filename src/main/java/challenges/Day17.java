package challenges;

import input.Fichier;
import util.State;
import util.SubState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day17 {
    public static void main(String[] args) {

        try {
            BufferedReader reader = Fichier.reader("day17");
            String input = reader.readLine();

            visitBFS(input);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void visitBFS(String input) {
        List<State> open = new ArrayList<>();
        List<State> terminals = new ArrayList<>();
        State init = new State(null, new RoomState(input, 0, 0));
        open.add(init);

        while (open.size() != 0) {
            State courant = open.get(0);
            open.remove(0);

            if (isTerminal(courant)) {
                terminals.add(courant);
            } else {
                for (RoomState r : ((RoomState) courant.getObject()).openRooms()) {
                    State newState = new State(courant, r);
                    if (!open.contains(newState)) {
                        open.add(newState);
                    }
                }
            }
        }

        int min = Integer.MAX_VALUE;
        String minPath = "";
        int max = Integer.MIN_VALUE;
        for (State state : terminals) {
            max = Math.max(state.getDepth(), max);
            if (state.getDepth() < min) {
                min = state.getDepth();
                minPath = getPath(input, state);
            }
        }
        System.out.println("First shortest path found : " + minPath);
        System.out.println("Length of longest path = " + max);
    }

    private static String getPath(String input, State state) {
        RoomState room = (RoomState) state.getObject();
        return room.input.replace(input, "");
    }

    private static boolean isTerminal(State courant) {
        return ((RoomState) courant.getObject()).isTerminal();
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private static class RoomState implements SubState {
        private String input;
        private int x;
        private int y;
        private String md5 = null;

        RoomState(String input, int x, int y) {
            this.x = x;
            this.y = y;
            this.input = input;
        }

        private boolean canMove(Direction direction) {
            //Cas particuliers
            if (x == 0 && direction == Direction.LEFT || y == 0 && direction == Direction.UP || x == 3 && direction == Direction.RIGHT || y == 3 && direction == Direction.DOWN) {
                return false;
            }

            if (md5 == null) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");

                    byte[] bytesInput = input.getBytes("UTF-8");
                    byte[] thedigest = md.digest(bytesInput);

                    BigInteger bigInt = new BigInteger(1, thedigest);
                    md5 = bigInt.toString(16);

                    while (md5.length() < 32) {
                        md5 = "0" + md5;
                    }
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            int index = 0;
            switch (direction) {
                case UP:
                    index = 0;
                    break;
                case DOWN:
                    index = 1;
                    break;
                case LEFT:
                    index = 2;
                    break;
                case RIGHT:
                    index = 3;
                    break;
            }

            return md5.charAt(index) >= 'b' && md5.charAt(index) <= 'f';
        }

        List<RoomState> openRooms() {
            List<RoomState> openRooms = new ArrayList<>();

            if (canMove(Direction.UP)) {
                openRooms.add(new RoomState(input + "U", x, y - 1));
            }
            if (canMove(Direction.DOWN)) {
                openRooms.add(new RoomState(input + "D", x, y + 1));
            }
            if (canMove(Direction.LEFT)) {
                openRooms.add(new RoomState(input + "L", x - 1, y));
            }
            if (canMove(Direction.RIGHT)) {
                openRooms.add(new RoomState(input + "R", x + 1, y));
            }

            return openRooms;
        }

        @Override
        public boolean isValid() {
            return x >= 0 && x < 4 && y >= 0 && y < 4;
        }

        @Override
        public boolean equals(SubState other) {
            return other instanceof RoomState && ((RoomState) other).x == x && ((RoomState) other).y == y && Objects.equals(((RoomState) other).input, input);
        }

        boolean isTerminal() {
            return x == 3 && y == 3;
        }
    }
}
