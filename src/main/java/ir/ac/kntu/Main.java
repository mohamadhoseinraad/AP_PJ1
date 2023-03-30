package ir.ac.kntu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        BufferedReader reader;
        ArrayList<String> file = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader("src/main/java/ir/ac/kntu/" + fileName));
            String line = reader.readLine();

            while (line != null) {
                //System.out.println(line);
                file.add(line);
                line = reader.readLine();
            }
            checkStyle(file);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkStyle(ArrayList<String> input) {
        ArrayList<String> old = input;
        input = updateFile(input);
        int e1 = checkPackage(input);
        int e2 = checkImports(input);
        int e3 = check80Char(old);
        int e4 = checkSemicolon(input);
        int e5 = checkClass(input);
        int e6 = checkMethods(input);
        int e7 = checkIndentation(input);
        int e8 = checkIf(input);
        int e9 = checkWhile(input);
        int e10 = checkSwitch(input);
        int e11 = checkFor(input);
        int e12 = checkCamel(input);
    }

    public static ArrayList<String> updateFile(ArrayList<String> input) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : input) {
            s = s.replaceAll("\".*\"", "");
            s = s.replaceAll("'.*'", "");
            if (s.trim().startsWith("//")) {
                result.add("");
            } else {
                result.add(s);
            }
        }
        return result;
    }

    public static int checkPackage(ArrayList<String> input) {
        boolean findeError = false;
        for (String s : input) {
            if (s.contains("package")) {
                if (input.indexOf(s) != 0) {
                    int line = input.indexOf(s);
                    System.out.printf("Warring Line %d : in use package | must write in line 1\n", line);
                    findeError = true;
                }
            }
        }
        if (input.get(0).contains("package")) {
            if (!input.get(0).matches("package [A-Za-z.]+;")) {
                System.out.println("Warring Line 1 :  in use package\n");
                findeError = true;
            }
        }
        if (findeError) {
            return 1;
        }
        return 0;
    }

    public static int checkImports(ArrayList<String> input) {
        boolean findError = false;
        for (String s : input) {
            int classLine = whereIsClass(input);
            if (s.contains("import")) {
                if (!s.matches("import [A-Za-z.*]+;")) {
                    int line = input.indexOf(s);
                    System.out.printf("Warring Line %d : In use import\n", line + 1);
                    findError = true;
                }
            }
        }
        if (findError) {
            return 1;
        }
        return 0;
    }

    public static int check80Char(ArrayList<String> input) {
        boolean findError = false;
        for (String s : input) {
            if (s.length() > 80) {
                int line = input.indexOf(s) + 1;
                System.out.printf("Warring Line %d : Each line must less than 80 character : %d\n", line + 1, s.length());
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

    public static int checkClass(ArrayList<String> input) {
        boolean findError = false;
        if (!isOneClass(input)) {
            findError = true;
            System.out.println("Warring : You must use just one class in your code!");
        }
        if (!checkClassFormat(input)) {
            findError = true;
        }
        if (findError) {
            return 1;
        }
        return 0;
    }

    public static boolean checkClassFormat(ArrayList<String> input) {
        for (String s : input) {
            if (s.contains("public class")) {
                if (!s.matches("public class [A-Z]{1}[A-Za-z]+ .*")) {
                    System.out.printf("Warring Line %d : Error in use (class) Naming\n", (input.indexOf(s) + 1));
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isOneClass(ArrayList<String> input) {
        int count = 0;
        for (String s : input) {
            int line = input.indexOf(s) + 1;
            if (s.matches("\s*public class [A-Za-z]+.*")) {
                count++;
            }
            if (count > 1) {
                System.out.printf("Warring Line %d : Use one class in code\n", line);
            }
        }
        if (count > 1) {
            return false;
        }
        return true;
    }

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

    public static int whereIsClass(ArrayList<String> input) {
        for (String s : input) {
            if (s.contains("public class") && !s.contains("\"")) {
                return (input.indexOf(s) + 1);
            }
        }
        return 0;
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
