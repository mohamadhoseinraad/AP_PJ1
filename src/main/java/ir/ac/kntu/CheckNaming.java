package ir.ac.kntu;

import java.util.ArrayList;

public class CheckNaming {

    public static int checkMethods(ArrayList<String> input) {
        boolean findError = false;
        for (String s : input) {
            int line = input.indexOf(s) + 1;
            if (s.matches("\s*public static.*")) {
                if (!s.matches("\s*public static [A-Za-z\\[ \\]<>]+ [a-z]{1}[A-Za-z\\[ \\]]+.*")) {
                    System.out.printf("Warring Line %d : Fail naming method\n", line);
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
                if (!s.matches("\s* [A-Za-z]+ [a-z]{1}[A-Za-z0-9]+ .*")) {
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

    public static boolean haveDecler(String s) {
        if (s.contains("for")) {
            return false;
        }
        if (s.contains("ArrayList")) {
            return false;
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
