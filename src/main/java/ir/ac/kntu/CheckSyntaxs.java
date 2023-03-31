package ir.ac.kntu;

import java.util.ArrayList;

public class CheckSyntaxs {
    public static int checkIf(ArrayList<String> input) {
        boolean findError = false;
        for (String s : input) {
            int line = input.indexOf(s) + 1;
            if (s.contains("else if")) {
                if (!s.matches("\s*\\} else if (.*) \\{")) {
                    findError = true;
                    System.out.printf("Warring Line %d : use true signature of elseif\n", line);
                }
            } else if (s.contains("else")) {
                if (!s.matches("\s*\\} else \\{")) {
                    findError = true;
                    System.out.printf("Warring Line %d : use true signature of else\n", line);
                }
            } else if (s.contains("if")) {
                if (!s.matches("\s*if (.*) \\{")) {
                    findError = true;
                    System.out.printf("Warring Line %d : use true signature of if\n", line);
                }
            }
        }
        if (findError) {
            return 1;
        }
        return 0;
    }

    public static int checkWhile(ArrayList<String> input) {
        boolean findError = false;
        for (String s : input) {
            if (s.contains("while")) {
                int line = input.indexOf(s) + 1;
                if (!s.matches("\s*while \\(.*\\) \\{")) {
                    System.out.printf("Warring Line %d : use true signature of while\n", line);
                    findError = true;
                }
            }
        }
        if (findError) {
            return 1;
        }
        return 0;
    }

    public static int checkSwitch(ArrayList<String> input) {
        boolean findError = false;
        for (int i = 0; i < input.size(); i++) {
            String se = input.get(i);
            if (se.contains("switch")) {
                boolean findDefault = false;
                for (int j = i; input.get(j).trim().equals("}"); j++) {
                    String ss = input.get(j);
                    if (!ss.matches("case .*: .* | default: .*") && ss.length() != 0) {
                        System.out.printf("Warring Line %d : use true signature of switch\n", j + 1);
                        findError = true;
                    }
                    if (ss.contains("default")) {
                        findDefault = true;
                    }
                }
                if (!findDefault) {
                    System.out.printf("Warring Line %d : switch doesn't have default\n", i + 1);
                }
            }
        }
        if (findError) {
            return 1;
        }
        return 0;
    }

    public static int checkFor(ArrayList<String> input) {
        boolean findError = false;
        for (int i = 0; i < input.size(); i++) {
            String se = input.get(i);
            if (se.contains("for")) {
                if (!se.matches("\s*for \\([A-Za-z]+ [a-z]{1}[A-Za-z]* .*\\) \\{")) {
                    System.out.printf("Warring Line %d : fail signature of for or naming\n", i + 1);
                    findError = true;
                }
            }
        }
        if (findError) {
            return 1;
        }
        return 0;
    }

}
