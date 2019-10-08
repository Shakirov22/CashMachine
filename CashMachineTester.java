package edu.CashMachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CashMachineTester {

    private static ByteArrayOutputStream baos = new ByteArrayOutputStream();

    private void callMethod(String[] args) {
        CashMachine.main(args);
    }

    @BeforeEach
    public void beforeEach() {
        baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
    }

    @Test
    public void testAmount5Coins23() throws IOException {
        String amount = "5";
        String coins = "2 3";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        callMethod(input);

        List<Long> expected = new ArrayList<Long>(Arrays.asList(2L, 3L));

        ByteArrayInputStream programOutputAsStream = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader br = new BufferedReader(new InputStreamReader(programOutputAsStream));
        String combinationString = br.readLine().trim();
        assertCombinationString(expected, combinationString);
        assertEquals("1", br.readLine().trim());
    }

    @Test
    public void testAmount5Coins32() throws IOException {
        String amount = "5";
        String coins = "3 2";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        callMethod(input);

        List<Long> expected = new ArrayList<Long>(Arrays.asList(2L, 3L));

        ByteArrayInputStream programOutputAsStream = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader br = new BufferedReader(new InputStreamReader(programOutputAsStream));
        String combinationString = br.readLine().trim();
        assertCombinationString(expected, combinationString);
        assertEquals("1", br.readLine().trim());

    }


    @Test
    public void testAmount5Coins12() throws IOException {
        String amount = "5";
        String coins = "1 2";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        callMethod(input);

        List<List<Long>> expected =
                new ArrayList<List<Long>>(
                        Arrays.asList(
                                new ArrayList<Long>(Arrays.asList(1L, 1L, 1L, 1L, 1L)),
                                new ArrayList<Long>(Arrays.asList(1L, 1L, 1L, 2L)),
                                new ArrayList<Long>(Arrays.asList(1L, 2L, 2L))
                        )
                );

        ByteArrayInputStream programOutputAsStream = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader br = new BufferedReader(new InputStreamReader(programOutputAsStream));
        int expectedCount = 3;
        assertListOfCombinations(expected, br, expectedCount);
        assertEquals("3", br.readLine().trim());
    }

    @Test
    public void testAmount7Coins54() throws IOException {
        String amount = "7";
        String coins = "5 4";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        callMethod(input);

        ByteArrayInputStream programOutputAsStream = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader br = new BufferedReader(new InputStreamReader(programOutputAsStream));

        assertEquals("0", br.readLine().trim());
    }

    @Test
    public void testAmount0Coins54() throws IOException {
        String amount = "0";
        String coins = "5 4";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        callMethod(input);
        ByteArrayInputStream programOutputAsStream = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader br = new BufferedReader(new InputStreamReader(programOutputAsStream));

        assertEquals("0", br.readLine().trim());
    }

    @Test
    public void testAmountMinus1Coins54() throws IOException {
        String amount = "-1";
        String coins = "5 4";
        String input[] = String.format("%s %s", amount, coins).split(" ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Сумма должна быть неотрицательна", exception.getMessage());
    }

    @Test
    public void testAmount10CoinsEmpty() throws IOException {
        String amount = "10";
        String coins = "";
        String input[] = String.format("%s %s", amount, coins).split(" ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Недостаточно аргументов", exception.getMessage());
    }

    @Test
    public void testAllEmpty() throws IOException {
        String input[] = {};

        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Недостаточно аргументов", exception.getMessage());
    }

    @Test
    public void testAmount10Coins01() throws IOException {
        String amount = "10";
        String coins = "0 1";
        String input[] = String.format("%s %s", amount, coins).split(" ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Номинал монеты должен быть положительным", exception.getMessage());
    }

    @Test
    public void testAmount10CoinsMinus11() throws IOException {
        String amount = "10";
        String coins = "-1 1";
        String input[] = String.format("%s %s", amount, coins).split(" ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Номинал монеты должен быть положительным", exception.getMessage());
    }

    @Test
    public void testAmountASDFCoinsMinus11() throws IOException {
        String amount = "asdf";
        String coins = "-1 1";
        String input[] = String.format("%s %s", amount, coins).split(" ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Все аргументы должны быть целыми числами", exception.getMessage());
    }

    @Test
    public void testAmount10Coins1a() throws IOException {
        String amount = "10";
        String coins = "1 a";
        String input[] = String.format("%s %s", amount, coins).split(" ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Все аргументы должны быть целыми числами", exception.getMessage());
    }

    @Test
    public void testAmount100000Coins1() throws IOException {
        String amount = "100000";
        String coins = "1";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        callMethod(input);

        ByteArrayInputStream programOutputAsStream = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader br = new BufferedReader(new InputStreamReader(programOutputAsStream));
        assertEquals("1", getLastLine(br).trim());
    }

    @Test
    public void testAmount1000Coins12() throws IOException {
        String amount = "1000";
        String coins = "1 2";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        callMethod(input);

        ByteArrayInputStream programOutputAsStream = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader br = new BufferedReader(new InputStreamReader(programOutputAsStream));
        assertEquals("501", getLastLine(br).trim());
    }

    @Test
    public void testAmount10000Coins12() throws IOException {
        String amount = "10000";
        String coins = "1 2";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        callMethod(input);

        ByteArrayInputStream programOutputAsStream = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader br = new BufferedReader(new InputStreamReader(programOutputAsStream));
        assertEquals("5001", getLastLine(br).trim());
    }

    @Test
    public void testAmount1000Coins111duplicates() throws IOException {
        String amount = "1000";
        String coins = "1 1 1";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        callMethod(input);

        ByteArrayInputStream programOutputAsStream = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader br = new BufferedReader(new InputStreamReader(programOutputAsStream));
        assertEquals("1", getLastLine(br).trim());
    }

    @Test
    public void testAmount5Coins121duplicates() throws IOException {
        String amount = "5";
        String coins = "1 2 1";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        callMethod(input);

        ByteArrayInputStream programOutputAsStream = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader br = new BufferedReader(new InputStreamReader(programOutputAsStream));
        assertEquals("3", getLastLine(br).trim());
    }

    @Test
    public void testAmountDoubleCoins12() throws IOException {
        String amount = "10.0";
        String coins = "1 2";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Все аргументы должны быть целыми числами", exception.getMessage());
    }

    @Test
    public void testAmountDoubleFractionalCoins12() throws IOException {
        String amount = "10.5";
        String coins = "1 2";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Все аргументы должны быть целыми числами", exception.getMessage());
    }

    @Test
    public void testAmount10FractionalCoinsFractional() throws IOException {
        String amount = "10";
        String coins = "1.0 2";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Все аргументы должны быть целыми числами", exception.getMessage());
    }

    @Test
    public void testAmountExpressionCoinsValid() throws IOException {
        String amount = "10 + 5";
        String coins = "1 2";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Все аргументы должны быть целыми числами", exception.getMessage());
    }

    @Test
    public void testAmountValidCoinsExpression() throws IOException {
        String amount = "10";
        String coins = "2+5 2";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Все аргументы должны быть целыми числами", exception.getMessage());
    }

    @Test
    public void testAmountSpecialCharacterCoinsValid() throws IOException {
        String amount = "+";
        String coins = "1 2";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Все аргументы должны быть целыми числами", exception.getMessage());
    }

    @Test
    public void testAmountSpecialCharacter2CoinsValid() throws IOException {
        String amount = "/";
        String coins = "1 2";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Все аргументы должны быть целыми числами", exception.getMessage());
    }

    @Test
    public void testAmountSpecialCharacter3CoinsValid() throws IOException {
        String amount = "\\d";
        String coins = "1 2";
        String input[] = String.format("%s %s", amount, coins).split(" ");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> callMethod(input));
        assertEquals("Все аргументы должны быть целыми числами", exception.getMessage());
    }

    private String getLastLine(BufferedReader br) throws IOException {
        String sCurrentLine = br.readLine();
        String lastLine = "";
        while (sCurrentLine != null) {
            lastLine = sCurrentLine;
            sCurrentLine = br.readLine();
        }
        return lastLine;
    }

    private void assertListOfCombinations(List<List<Long>> expected, BufferedReader br, int expectedCount) throws IOException {
        List<String> combinationStrings = new ArrayList<>(expectedCount);
        for (int i = 1; i <= expectedCount; i++) {
            combinationStrings.add(br.readLine().trim());
        }
        List<String> sortedCombinations = combinationStrings.stream().sorted().collect(Collectors.toList());
        for (int i = 0; i <= expectedCount - 1; i++) {
            assertCombinationString(expected.get(i), sortedCombinations.get(i));
        }
    }

    private void assertCombinationString(List<Long> expected, String combinationString) {
        List<Long> actual = Arrays
                .stream(combinationString.split(" "))
                .map(Long::parseLong)
                .sorted()
                .collect(Collectors.toList());
        assertEquals(expected, actual);
    }

}
