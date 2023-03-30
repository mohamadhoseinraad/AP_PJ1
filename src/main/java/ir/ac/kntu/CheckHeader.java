package ir.ac.kntu;

import java.util.ArrayList;

public class CheckHeader {
    private ArrayList<String> file;
    private
    public CheckHeader(){
        file = new ArrayList<>();
    }
    public CheckHeader(ArrayList<String> file){
        this.file = file;
    }
    public static int checkPackage(ArrayList<String> input) {
        for (String s : input) {
            int classLine = Main.whereIsClass(input);
            if (s.contains("package") && !s.contains("\"")) {
                if (input.indexOf(s) != 0) {
                    int line = input.indexOf(s);
                    System.out.printf("Warring Line %d : in use package | must write in line 1\n", line);

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

}
