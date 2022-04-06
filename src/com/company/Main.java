package com.company;
import java.io.IOException;
import java.util.*;

import static com.company.Main.Tools.*;

public class Main {
    // ������ �� ������������
    static String example;

    // 2 ���������� �� ������ example
    static String firstStr;
    static String secondStr;
    // �������� �� ������������
    static String operation;
    // int ���������� ������������
    static int firstInt;
    static int secondInt;
    static int resultArab;
    static String resultRome;
    // ������ ������� ��������
    static Map<String, String> romeNumerals = new HashMap<String, String>();

    // ������������� ��������� �� ������� �����
    static String answer;

    public static void main(String[] args) {
        enterInformation();
        parsingExpression(example);
        if (validParseInt(firstStr) && validParseInt(secondStr)){
            calculation(parseInt(firstStr), operation, parseInt(secondStr));
        }
        if (!validParseInt(firstStr) && !validParseInt(secondStr)){
            initArray();
            firstInt = translateRomeToArab(firstStr, romeNumerals);
            secondInt = translateRomeToArab(secondStr, romeNumerals);
            calculation(firstInt, operation, secondInt);
            answer = translateArabToRomeAll(resultArab);
            System.out.println(answer);
        }
        else{
            System.out.println("������� �� ���������� ��������");
        }
    }
    public static class Tools{
        static void parsingExpression(String example) {
            String str1 = "";
            String str2 = "";
            // local var
            boolean triggerToChangeTwo = false;
            char[] word = example.toCharArray();
            ArrayList<String> number = new ArrayList<>();
            ArrayList<String> number2 = new ArrayList<>();
            for (int i = 0; i < word.length; i++) {
                if (word[i] == '*' || word[i] == '/' || word[i] == '-' || word[i] == '+') {
                    operation = String.valueOf(word[i]);
                    triggerToChangeTwo = true;
                    continue;
                }
                if (word[i] == ' ' && number.isEmpty()) {
                    triggerToChangeTwo = false;
                    continue;
                } else if (!triggerToChangeTwo && word[i] != ' ') {
                    number.add(String.valueOf(word[i]));
                    continue;
                }
                if (word[i] == ' ' && number.size() != 0) {
                    triggerToChangeTwo = true;
                    continue;
                } else if (triggerToChangeTwo) {
                    number2.add(String.valueOf(word[i]));
                    continue;
                }
            }
            for (String s : number) {
                str1 += s;
            }
            for (String s : number2) {
                str2 += s;
            }
            firstStr = str1;
            secondStr = str2;
        }
        static void calculation(int first, String operation, int second) {
            int answer = 0;
            switch (operation) {
                case ("*"):
                    answer = first * second;
                    break;
                case ("/"):
                    answer = first / second;
                    break;
                case ("+"):
                    answer = first + second;
                    break;
                case ("-"):
                    answer = first - second;
                    break;
            }
            resultArab = answer;
        }
        static void initArray() {
            romeNumerals.put("I", "1");
            romeNumerals.put("V", "5");
            romeNumerals.put("X", "10");
            romeNumerals.put("L", "50");
            romeNumerals.put("C", "100");
            romeNumerals.put("D", "500");
            romeNumerals.put("M", "1000");
        }
        static void enterInformation (){
            System.out.println("������� ��������");
            Scanner in = new Scanner(System.in);
            example = in.nextLine();
        }
        static boolean validParseInt (String value){
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        static int parseInt (String value){
            int res = 0;
            res = Integer.parseInt(value);
            return res;
        }
        static int translateRomeToArab(String number, Map<String, String> romeNumerals) {
            char [] symbols = number.toCharArray();
            int resultRomeToArab;
            int[] arabNum = new int[symbols.length];
            for (int i = 0; i < symbols.length; i++) {
                String value = String.valueOf(symbols[i]);
                arabNum[i] = Integer.parseInt(romeNumerals.get(value));
            }
            if (symbols.length == 1) {
                resultRomeToArab = arabNum[0];
                return resultRomeToArab;
            } else {
                for (int i = 0; i < arabNum.length; i++) {
                    int twoIndexVal = i + 1;
                    if (arabNum[i] == 1 && arabNum[twoIndexVal] == 5
                            || arabNum[i] == 10 && arabNum[twoIndexVal] == 50
                            || arabNum[i] == 100 && arabNum[twoIndexVal] == 500) {
                        arabNum[i] *= -1;
                    } else if (arabNum[i] == 1 && arabNum[twoIndexVal] == 10
                            || arabNum[i] == 10 && arabNum[twoIndexVal] == 100
                            || arabNum[i] == 100 && arabNum[twoIndexVal] == 1000) {
                        arabNum[i] *= -1;
                    }
                    if (twoIndexVal == arabNum.length - 1) {
                        break;
                    }
                }
                int sum = 0;
                for (int j = 0; j < arabNum.length; j++) {
                    sum = sum + arabNum[j];
                }
                return sum;
            }

        }
        static String translateArabToRomeAll(int val) {
            int firstInt = 1;
            String result = "";
            String valString = ""+val;
            char [] arr = valString.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                int depth  = String.valueOf(Math.abs(val)).length() - 1 - i;
                String firstString = String.valueOf(arr[i] );
                firstInt = Integer.parseInt(firstString) * (int)Math.pow(10, depth);
                result += translateArabToRomeOne(firstInt);
            }
            return result;
        }
        static String translateArabToRomeOne(int val){
            // ������������� ��������
            String [] one = {"I", "V", "X"};
            String [] two = {"X", "L", "C"};
            String [] three = {"C", "D", "M"};
            String [] four = {"M", "MD"};
            // �������� ���������� �����
            int depth  = String.valueOf(Math.abs(val)).length() - 1;

            // �������� ������ ��������
            String valString = ""+val;
            char [] arr = valString.toCharArray();
            String firstString = String.valueOf(arr[0]);
            int firstInt = Integer.parseInt(firstString);
            String [] actualArray = new String[3];
            //������� �����������
            if (depth == 0){
                actualArray = one;
            }
            if (depth == 1){
                actualArray = two;
            }
            if (depth == 2){
                actualArray = three;
            }
            if (depth == 3){
                actualArray = four;
            }
            if (firstInt == 1){
                return actualArray[0];
            }
            if (firstInt == 2){
                return actualArray[0] + actualArray[0];
            }
            if (firstInt == 3){
                return actualArray[0] + actualArray[0] + actualArray[0];
            }
            if (firstInt == 4){
                return actualArray[0] + actualArray[1];
            }
            if (firstInt == 5){
                return actualArray[1];
            }
            if (firstInt == 6){
                return actualArray[1] + actualArray[0];
            }
            if (firstInt == 7){
                return actualArray[1] + actualArray[0] + actualArray[0];
            }
            if (firstInt == 8){
                return actualArray[1] + actualArray[0] + actualArray[0] + actualArray[0];
            }
            if (firstInt == 9 ){
                return actualArray[0] + actualArray[2];
            }
            if (firstInt == 10){
                return actualArray[0];
            }
            return "";
        }
    }
}

