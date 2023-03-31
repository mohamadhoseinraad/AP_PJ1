package ir.ac.kntu;

import java.util.ArrayList;

public class HelpMethods {
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
}
