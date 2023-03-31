package ir.ac.kntu;

import java.util.ArrayList;

public class CheckBaseStyle {

    public static int check80Char(ArrayList<String> input) {
        boolean findError = false;
        for (String s : input) {
            if (s.length() > 80) {
                int line = input.indexOf(s) + 1;
                System.out.printf("Warring Line %d : Each line must less than 80 character : %d\n", line, s.length());
                findError = true;
            }
        }
        if (findError) {
            return 1;
        }
        return 0;
    }

    public static int checkSemicolon(ArrayList<String> input) {
        boolean findError = false;
        for (String s : input) {
            if (countSemi(s) > 1 && !s.contains("for")) {
                int line = input.indexOf(s);
                System.out.printf("Warring Line %d : Use just one \";\" for declaration or method\n", line + 1);
                findError = true;
            }
        }
        if (findError) {
            return 1;
        }
        return 0;
    }

    public static int countSemi(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ';') {
                count++;
            }
        }
        return count;
    }

    public static int checkIndentation(ArrayList<String> input) {

        for (int i = 0; i < input.size(); i++) {
            String se = input.get(i);
            int line = i + 1;
            int checkTab = updateTabNumber(input, line - 1);
            if (se.trim().length() + checkTab != se.length() && se.length() != 0) {
                System.out.printf("Warring Line %d : Fail indentation(not check after this)\n", line);
                return 1;
            }
        }
        return 0;
    }

    public static int countOpenBrace(ArrayList<String> input, int to) {
        int number = 0;
        for (int i = 0; i < to; i++) {
            String se = input.get(i);
            se = se.replaceAll("(\\\\){1}(\\{){1}|(\\\\){1}(\\}){1}", "");
            for (int j = 0; j < se.length(); j++) {
                if (se.charAt(j) == '{') {
                    number++;
                }
            }
        }
        return number;
    }

    public static int countCloseBrace(ArrayList<String> input, int to) {
        int number = 0;
        for (int i = 0; i <= to; i++) {
            String se = input.get(i);
            for (int j = 0; j < se.length(); j++) {
                if (se.charAt(j) == '}') {
                    number++;
                }
            }
        }
        return number;
    }

    public static int updateTabNumber(ArrayList<String> input, int to) {
        int braceOpen = countOpenBrace(input, to);
        int braceClose = countCloseBrace(input, to);
        int result = (braceOpen - braceClose) * 4;
        return result;
    }

}
