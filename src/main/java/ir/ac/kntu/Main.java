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
        input = updateFile(input);
        int e1 = checkPackage(input);
        int e2 = checkImports(input);
        //int e3 = check80Char(input);
        int e4 = checkSemicolon(input);
        int e5 = checkClass(input);
        int e6 = checkMethods(input);
        //int e7 = checkIndentation(input);
        //int e8 = checkCamelCase(input);

    }

    public static ArrayList<String> updateFile(ArrayList<String> input) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : input) {
            s = s.replaceAll("\".*\"", "");
            if (!s.trim().startsWith("//")) {
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
                int line = input.indexOf(s);
                System.out.printf("Warring Line %d : Each line must less than 80 character\n", line + 1);
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
                if (!s.matches("public class [A-Z]{1}[A-Za-z]*\s[{]{1}")) {
                    System.out.printf("Warring Line %d : Error in use (class) Naming\n", (input.indexOf(s) + 1));
                    return false;
                }
                break;
            }
        }
        return true;
    }

    public static boolean isOneClass(ArrayList<String> input) {
        int count = 0;
        for (String s : input) {
            if (s.matches("\s*public class [A-Za-z]+.*")) {
                count++;
            }
        }
        if (count != 1) {
            return false;
        }
        return true;
    }

    public static int checkMethods(ArrayList<String> input) {
        boolean findError = false;
        for (String s : input) {
            int line = input.indexOf(s) + 1;
            if (s.matches("\s*public static.*")) {
                if (!s.matches("\s*public static [A-Za-z\\[ \\]]+ [a-z]{1}[A-Za-z\\[ \\]]+.*")) {
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
//        String base = "    ";
//        String checkSpace = "";
        int tab = 4;
        for (int i = 142; i < input.size(); i++) {
            String s = input.get(i);
            int line = i + 1;
            int checkTab = updateTabNumber(input, line - 1);
//            if (!s.matches("( ){" + checkTab + "}[^\s]*") && !s.trim().contains("//")) {
//                System.out.printf("Warring Line %d : Fail indentation\n", line);
//            }
            boolean nulAndComment = s.trim().contains("//") || (s.length() == 0);
            if (s.trim().length() + checkTab != s.length() && !nulAndComment) {
                System.out.printf("Warring Line %d : Fail indentation\n", line);
            }
        }
        return 0;
    }

    public static int countOpenBrace(ArrayList<String> input, int to) {
        int number = 0;
        for (int i = 0; i < to; i++) {
            String s = input.get(i);
            s = s.replaceAll("(\\\\){1}(\\{){1}|(\\\\){1}(\\}){1}", "");
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == '{') {
                    number++;
                }
            }
        }
        return number;
    }

    public static int countCloseBrace(ArrayList<String> input, int to) {
        int number = 0;
        for (int i = 0; i <= to; i++) {
            String s = input.get(i);
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == '}') {
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
