package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Arrays;

public class CheckNaming {

    public static int checkMethods(ArrayList<String> input) {
        boolean findError = false;
        for (String s : input) {
            int line = input.indexOf(s) + 1;
            if (s.matches("\s*(public|private) (static)?.*") && !s.contains("class")) {
                if (!s.matches("\s*(public|private)( static)? [A-Za-z\\[\\]<>]+ [a-z]{1}[A-Za-z\\[\\]]+.*")) {
                    System.out.printf("Warring Line %d : Fail naming method\n", line);
                    findError = true;
                }
                if (!checkArgument(s)) {
                    System.out.printf("Warring Line %d : Fail naming argument of method\n", line);
                    findError = true;
                }
            }
        }
        if (findError) {
            return 1;
        }
        return 0;
    }


    public static int checkCamel(ArrayList<String> input) {
        boolean findError = false;
        for (String s : input) {
            if (haveDecler(s)) {
                if (!s.matches("\s* [A-Za-z<>\\[\\]]+ [a-z]{1}[A-Za-z0-9]+ .*")) {
                    int line = input.indexOf(s) + 1;
                    findError = true;
                    System.out.printf("Warring Line %d : Fail check StyleNaming\n", line);
                }
            }
        }
        if (findError) {
            return 1;
        }
        return 0;
    }

    public static boolean checkArgument(String s) {
        String temp = s.replaceAll(".*\\(|private|public|static|\\(|\\)|\\{|\\}", "");
        String[] argument = temp.split(",");
        for (int i = 0; i < argument.length; i++) {
            String name = argument[i].trim();
            if (!name.matches("[A-Za-z<>\\[\\]]+ [a-z]{1}[A-Za-z0-9]+")) {
                System.out.print(name+" : ");
                return false;
            }
        }
        return true;
    }

    public static boolean haveDecler(String s) {
        if (s.contains("for")) {
            return false;
        }
        if (s.contains("ArrayList<")) {
            return true;
        }
        if (s.matches("\s+int(\\[\\])*.*") || s.matches("\s+int(\\[\\])*.*")) {
            return true;
        }
        if (s.matches("\s+double(\\[\\])*.*") || s.matches("\s+float(\\[\\])*.*")) {
            return true;
        }
        if (s.matches("\s+String(\\[\\])*.*") || s.matches("\s+char(\\[\\])*.*")) {
            return true;
        }
        if (s.matches("\s+byte(\\[\\])*.*") || s.matches("\s+boolean(\\[\\])*.*")) {
            return true;
        }
        return false;
    }


}
