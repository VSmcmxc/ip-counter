package com.mcmxc.ip_counter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IPCounterTest {


    static String[] testIPs;

    @BeforeAll
    public static void init() {
        testIPs = getTestIPs();

    }


    public static String[] getTestIPs() {
        return new String[]{
                "192.0.0.1",
                "192.0.2.1",
                "192.0.0.10",
                "test",
                "256.0.0.1",
                "257.0.0.1",
                "0.0.0.0",
                "0.256.0.1",
                "1.0.0.256",
                "192.0.0.255"};
    }


    @Test
    void validateIP() {
        boolean[] resultOfChecker = new boolean[testIPs.length];
        for (int i = 0; i < testIPs.length; i++) {
            resultOfChecker[i] = IPCounter.validateIP(testIPs[i]);
        }
        boolean[] expected = new boolean[]{
                true,
                true,
                true,
                false,
                false,
                false,
                true,
                false,
                false,
                true
        };
        Assertions.assertArrayEquals(expected, resultOfChecker);

    }

    @Test
    void getLongFromString() {
        long[] ipsAsLong = Arrays.stream(testIPs)
                .filter(IPCounter::validateIP)
                .mapToLong(IPCounter::getLongFromString)
                .toArray();
        long[] expected = new long[]{
                256 * 256 * 256 * 192 + 1,
                256 * 256 * 256 * 192 + 256 * 2 + 1,
                256 * 256 * 256 * 192 + 10,
                0,
                256 * 256 * 256 * 192 + 255};

        Assertions.assertArrayEquals(expected, ipsAsLong);
    }


    @Test
    void getStringFromLong() {
        long[] testIPsAsLong = new long[]{
                256 * 256 * 256 * 192 + 1,
                256 * 256 * 256 * 192 + 256 * 2 + 1,
                256 * 256 * 256 * 192 + 10,
                0,
                256 * 256 * 256 * 192 + 255};
        String[] ipsAsSting =
                Arrays.stream(testIPsAsLong)
                        .mapToObj(IPCounter::getStringFromLong)
                        .toArray(String[]::new);
        String[] expected = new String[]{
                "192.0.0.1",
                "192.0.2.1",
                "192.0.0.10",
                "0.0.0.0",
                "192.0.0.255"
        };

        Assertions.assertArrayEquals(expected, ipsAsSting);
    }


}