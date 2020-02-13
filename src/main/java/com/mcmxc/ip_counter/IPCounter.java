package com.mcmxc.ip_counter;

import java.util.Scanner;
import java.util.stream.Stream;

public class IPCounter {

    public static final String regex = "\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String firstIPAsString;
        do {
            System.out.println("Enter first IP:");
            firstIPAsString = in.nextLine();
        }
        while (!validateIP(firstIPAsString));

        String secondIPAsString;
        do {
            System.out.println("Enter second IP:");
            secondIPAsString = in.nextLine();
        }
        while (!validateIP(firstIPAsString));

        long firstIPAsLong = getLongFromString(firstIPAsString);
        long secondIPAsLong = getLongFromString(secondIPAsString);


        for (long i = firstIPAsLong + 1; i < secondIPAsLong; i++) {
            System.out.println(getStringFromLong(i));
        }


    }

    public static long getLongFromString(String iPAsString) {
        int[] first = Stream.of(iPAsString.split("\\."))
                .mapToInt(Integer::valueOf)
                .toArray();
        long result = 256 * 256 * 256 * first[0] + 256 * 256 * first[1] + 256 * first[2] + first[3];
        return result;
    }


    public static String getStringFromLong(long iPAsLong) {
        return String.format("%d.%d.%d.%d",
                (iPAsLong >>> 24) & 0xff,
                (iPAsLong >>> 16) & 0xff,
                (iPAsLong >>> 8) & 0xff,
                (iPAsLong) & 0xff);
    }

    public static boolean validateIP(String iPAsString) {
        boolean result = iPAsString.matches(regex);
        if (!result) {
            System.out.println("Please, enter correct IP");
        }
        return result;
    }


}
