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
                System.out.println(line);
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

    }

    public static int checkPackage(ArrayList<String> input) {
        for (String s : input) {
            if (s.contains("package")) {
                if (input.indexOf(s) != 0) {
                    System.out.println("Error in use package in line" + (input.indexOf(s) + 1) + " must write in line 1");
                    return -1;
                }
            }
        }
        if (!input.get(0).matches("package [A-Za-z.]+;")) {
            System.out.println("Error in use package in line 1 // use like this : package address;");
        }
        return 0;
    }

}
