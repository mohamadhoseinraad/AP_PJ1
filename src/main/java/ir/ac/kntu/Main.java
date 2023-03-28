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
        int e1 = checkPackage(input);
        int e2 = checkImports(input);
        int e3 = check80Char(input);
        int e4 = checkSemicolon(input);
        int e5 = checkClass(input);
        int e6 = checkMainMethod(input);
        int e7 = checkIndentation(input);


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
        if (input.get(0).length() != 0) {
            if (!input.get(0).matches("package [A-Za-z.]+;") && !input.get(0).startsWith("import")) {
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
            if (countSemi(s) > 1) {
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
                    System.out.printf("Warring Line %d : Error in use (class) syntax or Naming\n", (input.indexOf(s) + 1));
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
            if (s.contains("public class")) {
                count++;
            }
        }
        if (count != 1) {
            return false;
        }
        return true;
    }

    public static int checkMainMethod(ArrayList<String> input) {
        boolean findError = false;
        int fromLine = whereIsClass(input);
        for (String s : input) {
            if (s.toLowerCase().contains("main")) {
                int line = input.indexOf(s) + 1;
                if (!s.trim().equals("public static void main(String[] args) {")) {
                    System.out.printf("Warring Line %d : use tru signature of main method\n", line);
                    findError = true;
                }
                if (line < fromLine) {
                    System.out.printf("Warring Line %d : use main method out of public class !\n", line);
                    findError = true;
                }
                if (!s.contains("main")) {
                    System.out.printf("Warring Line %d : naming style must camelCase\n", line);
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
            if (s.contains("public class")) {
                return (input.indexOf(s) + 1);
            }
        }
        return 0;
    }

    public static int checkIndentation(ArrayList<String> input) {
        String base = "    ";
        String checkSpace = "";
        for (String s : input) {
            int line = input.indexOf(s) + 1;
            if (s.length() != 0 && !s.contains("}")) {
                if (checkSpace.length() != 0) {
                    if (!(s.trim().length() == s.length() - checkSpace.length())) {
                        System.out.printf("Warring Line %d : fail in indentation use space\n", line);
                    }
                }
            }
            if (s.contains("{")) {
                if (!s.endsWith("{")) {
                    System.out.printf("Warring Line %d : fail in indentation {  must end of line\n", line);
                }
                checkSpace += base;
            } else if (s.contains("}")) {
                if (checkSpace.length() >= 4) {
                    checkSpace = createSpace(checkSpace);
                }
                if (!s.equals(checkSpace + "}")) {
                    System.out.printf("Warring Line %d : fail in indentation } must end of line alone\n", line);
                }
            }
            if (s.contains("{") && s.contains("}")) {
                System.out.printf("Warring Line %d : fail in indentation { and } not at same line\n", line);
            }

        }
        return 0;
    }

    public static String createSpace(String input) {
        String result = "";
        String base = "    ";
        int times = input.length() / 4 - 1;
        for (int i = 0; i < times; i++) {
            result += base;
        }
        return result;
    }
}
