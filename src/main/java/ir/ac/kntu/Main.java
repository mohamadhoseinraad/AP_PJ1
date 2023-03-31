package ir.ac.kntu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    ArrayList<String> ss = new ArrayList<>();
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

        String result = CheckStyle.checkAllStyle(input);
        System.out.println(result);
    }

    public static int Fail(int a, int b, int c) {
        return 0;
    }


}
