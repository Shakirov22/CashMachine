package edu.CashMachine;

import java.util.ArrayList;
import java.util.Collections;

class CounterOptions {

    private int index;
    private int remainder;
    private int faceValue;
    private ArrayList<Integer> indexArray;
    private ArrayList<Integer> remainderArray;
    private int recursionIndex;
    private StringBuffer output;
    private long numberOptions;
    private ArrayList<Integer> faceValuesArray;
    private int sum;
    private boolean flagPrinting;

    CounterOptions(ArrayList<Integer> faceValuesArray, int sum) {
        this.indexArray = new ArrayList<>();
        this.remainderArray = new ArrayList<>();
        this.faceValuesArray = faceValuesArray;
        this.flagPrinting = true;

        this.sum = sum;
        this.recursionIndex = 0;
        this.numberOptions = 0;
        this.output = new StringBuffer("");
    }

    private boolean checkAndPrintLowestFaceValue() {
        if (this.remainder % this.faceValue == 0) {
            if (flagPrinting) {
                this.output.append(new String(new char[this.remainder / this.faceValue]).replace("\0", Integer.toString(this.faceValue) + " "));
                System.out.print(this.output + "\n");
            }
            return true;
        }
        return false;
    }

    private void goToPreviousRecursionIndex() {
        this.recursionIndex--;
        this.indexArray.set(this.recursionIndex, this.indexArray.get(this.recursionIndex) - 1);
        if (flagPrinting) {
            this.output.delete(2 * this.recursionIndex, 2 * this.output.length());
        }
    }

    private boolean checkExitEvent() {
        if (this.recursionIndex == 0) {
            return true;
        } else {
            this.goToPreviousRecursionIndex();
            return false;
        }
    }

    private boolean checkTheLowestFaceValue() {
        if (this.checkAndPrintLowestFaceValue()) {
            this.numberOptions++;
        }
        return checkExitEvent();
    }

    private void checkIndexArraySize() {
        if (this.recursionIndex >= this.indexArray.size()) {
            this.indexArray.add(0);
            this.remainderArray.add(0);
        }
    }

    private void goToNextRecursionIndex() {
        this.recursionIndex++;
        this.checkIndexArraySize();
        this.indexArray.set(this.recursionIndex, this.index);
        remainderArray.set(this.recursionIndex, this.remainder - this.faceValue);
        if (flagPrinting) {
            this.output.append(Integer.toString(this.faceValue) + " ");
        }
    }

    private void checkRemainderGoToNext() {
        if (this.remainder - this.faceValue == 0) {
            if (flagPrinting) {
                System.out.print(this.output + Integer.toString(this.faceValue) + "\n");
            }
            this.numberOptions++;
        }
        this.indexArray.set(this.recursionIndex, this.index - 1);
    }

    private void checkNotTheLowestFaceValue() {
        if (this.remainder - this.faceValue > 0) {
            this.goToNextRecursionIndex();
        } else {
            this.checkRemainderGoToNext();
        }
    }

    private void countAndPrintOptions() {
        boolean exitFlag = false;

        this.indexArray.add(this.faceValuesArray.size() - 1);
        this.remainderArray.add(this.sum);

        while (!exitFlag) {
            this.index = this.indexArray.get(this.recursionIndex);
            this.faceValue = this.faceValuesArray.get(this.index);
            this.remainder = this.remainderArray.get(this.recursionIndex);

            if (this.index == 0) {
                exitFlag = this.checkTheLowestFaceValue();
            } else {
                this.checkNotTheLowestFaceValue();
            }
        }
    }

    long getNumberOptions() {
        this.countAndPrintOptions();
        return this.numberOptions;
    }
}

public class CashMachine {

    private static void checkForArguments(String[] args) {
        if (args.length <= 1) {
            throw new IllegalArgumentException("Недостаточно аргументов");
        }
    }

    private static void checkSum(int sum) {
        if (sum < 0) {
            throw new IllegalArgumentException("Сумма должна быть неотрицательна");
        }
    }

    private static int getSum(String[] args) {
        int sum = 0;
        try {
            sum = Integer.parseInt(args[0]);
            checkSum(sum);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Все аргументы должны быть целыми числами");
        }
        return sum;
    }

    private static void checkFaceValue(int faceValue) {
        if (faceValue <= 0) {
            throw new IllegalArgumentException("Номинал монеты должен быть положительным");
        }
    }

    private static ArrayList<Integer> getFaceValuesArray(String[] args) {
        ArrayList<Integer> faceValuesArray = new ArrayList<>();
        try {
            for (int i = 1; i < args.length; i++) {
                int faceValue = Integer.parseInt(args[i]);
                checkFaceValue(faceValue);
                if (!faceValuesArray.contains(faceValue)) {
                    faceValuesArray.add(faceValue);
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Все аргументы должны быть целыми числами");
        }
        return faceValuesArray;
    }

    private static boolean checkNullSum(int sum) {
        if (sum == 0) {
            System.out.print(0);
            return true;
        }
        return false;
    }

    private static void printNumberOptions(long numberOptions) {
        System.out.print(numberOptions);
    }

    public static void main(String[] args) {
        checkForArguments(args);
        int sum = getSum(args);
        ArrayList<Integer> faceValuesArray;
        faceValuesArray = getFaceValuesArray(args);
        if (!checkNullSum(sum)) {
            Collections.sort(faceValuesArray);
            CounterOptions counterOptions = new CounterOptions(faceValuesArray, sum);
            long numberOptions = counterOptions.getNumberOptions();
            printNumberOptions(numberOptions);
        }
    }
}

