package ir.ac.kntu;

import java.util.ArrayList;

public class CheckHeader {
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

    //check Class :
    public static int whereIsClass(ArrayList<String> input) {
        for (String s : input) {
            if (s.contains("public class") && !s.contains("\"")) {
                return (input.indexOf(s) + 1);
            }
        }
        return 0;
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
}
