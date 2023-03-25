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
        checkPackage(input);
        checkImports(input);


    }

    public static int checkPackage(ArrayList<String> input) {

        for (String s : input) {
            if (s.contains("package")) {
                if (input.indexOf(s) != 0) {
                    int line = input.indexOf(s);
                    System.out.printf("Warring Line %d in use package | must write in line 1\n",line);
                    return -1;
                }
            }
        }
        if (input.get(0).length() != 0) {
            if (!input.get(0).matches("package [A-Za-z.]+;") && !input.get(0).startsWith("import")) {
                System.out.println("Warring Line 1 in use package\n");
            }
        }
        return 0;
    }

    public static void checkImports(ArrayList<String> input) {
        for (String s : input) {
            if (s.startsWith("import")) {
                if (!s.matches("import [A-Za-z.*]+;")) {
                    int line = input.indexOf(s);
                    System.out.printf("Warring Line %d in use import\n", line+1);
                }
            }
        }
    }
}
