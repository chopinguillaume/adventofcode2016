package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

public class Day20 {

    public static void main(String[] args) {

        try {
            BufferedReader reader = Fichier.reader("day20");

            TreeSet<Range> ranges = new TreeSet<>();

            /*
            CREATE RANGES
             */
            String input;
            while ((input = reader.readLine()) != null) {
//                System.out.println(ranges);
//                System.out.println(input);
                String[] splits = input.split("-");
                double min = Double.parseDouble(splits[0]);
                double max = Double.parseDouble(splits[1]);

                addRange(ranges, min, max);
            }

            System.out.println(ranges);

            double maxIP = 4294967295.0;
            double ip = 0;
            double total = 0;
            double min = maxIP;
            Iterator<Range> iterator = ranges.iterator();
            Range current = iterator.next();

            while (ip <= maxIP) {
                if (current != null) {
                    if (ip < current.min) {
                        //on avance 1 par 1 jusqu'à tomber sur la range
                        if (ip < min) {
                            min = ip;
                        }
                        ip++;
                        total++;
                    } else {
                        //on est dans la range, on skip
                        ip = current.max + 1;
                        current = iterator.hasNext() ? iterator.next() : null;
                    }
                } else {
                    //plus de range, aller jusqu'au max
                    if (ip < min) {
                        min = ip;
                    }
                    ip++;
                    total++;
                }
            }

            System.out.println("Min available IP is " + min);
            System.out.println("Total of available IPs is " + total);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addRange(TreeSet<Range> ranges, double a, double b) {
        Range containsMin = null;
        Range containsMax = null;
        Range included = null;

        for (Range range : ranges) {
            if (range.contains(a)) {
                containsMin = range;
            }
            if (range.contains(b)) {
                containsMax = range;
            }
            if (range.between(a, b)) {
                included = range;
            }
        }

        if (containsMin != null && containsMax != null) {
            //Merge both
//                    System.out.println("Merge " + containsMin + " and " + containsMax);
            ranges.remove(containsMin);
            ranges.remove(containsMax);
            addRange(ranges, containsMin.min, containsMax.max);
        } else if (containsMin != null) {
            //Merge containsMin and current
//                    System.out.println("Merge " + containsMin + " and " + a + ", " + b);
            ranges.remove(containsMin);
            double newMax = Math.max(containsMin.max, b);
            addRange(ranges, containsMin.min, newMax);
        } else if (containsMax != null) {
            //Merge containsMax and current
//                    System.out.println("Merge " + containsMax + " and " + a + ", " + b);
            ranges.remove(containsMax);
            double newMin = Math.min(containsMax.min, a);
            addRange(ranges, newMin, containsMax.max);
        } else if (included != null) {
//                    System.out.println("Included " + included + " in " + a + ", " + b);
            ranges.remove(included);
            addRange(ranges, a, b);
        } else {
            //Create new range
//                    System.out.println("Create new range " + a + ", " + b);
            ranges.add(new Range(a, b));
        }
    }
}

class Range implements Comparable<Range> {
    double min;
    double max;

    Range(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public int compareTo(Range o) {
        return Double.compare(min, o.min);
    }

    boolean contains(double d) {
        return min <= d && d <= max;
    }

    @Override
    public String toString() {
        return "{" + min +
                ", " + max +
                '}';
    }

    boolean between(double a, double b) {
        return a < min && b > max;
    }
}
