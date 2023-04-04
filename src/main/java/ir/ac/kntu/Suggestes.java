package ir.ac.kntu;

public class Suggestes {

    public static void sugesst80char(String line) {
        String baseSpace = createBaseSpace(line);
        System.out.println(createClean(line, baseSpace, 1));

    }

    private static String createClean(String input, String baseSpace, int numberbroke) {
        String result = "";
        if (numberbroke != 1) {
            result += baseSpace;
        }
        if (input.length() <= 80) {
            result += input;
        } else {
            for (int i = 79; i >= 0; i--) {
                if (broke(input.charAt(i))) {
                    String newLine = input.substring(i);
                    result += input.substring(0, i) + "\n" + createClean(newLine, baseSpace, numberbroke + 1);
                    return result;
                }
            }
        }
        return result;
    }

    //+, -, *, /, %, ^, &&, ||
    private static boolean broke(char ch) {
        if (ch == ',' || ch == '|' || ch == '&' || ch == ',') {
            return true;
        }
        if (ch == '+' || ch == '-' || ch == '/' || ch == '*' || ch == '%') {
            return true;
        }
        if (ch == '^') {
            return true;
        }
        return false;
    }

    public static String createBaseSpace(String input) {
        int numberSpace = input.length() - input.trim().length();
        String result = "";
        if (CheckNaming.haveDecler(input)) {
            numberSpace = input.indexOf('=') + 2;
        } else if (input.matches("\s*(public|private) (static)?.*") && !input.contains("class")) {
            numberSpace = input.indexOf("(");
        }

        for (int i = 0; i < numberSpace; i++) {
            result += " ";
        }

        return result;
    }
}
