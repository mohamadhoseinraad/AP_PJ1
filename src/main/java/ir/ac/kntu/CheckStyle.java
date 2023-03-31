package ir.ac.kntu;

import java.util.ArrayList;

public class CheckStyle {

    public static String checkAllStyle(ArrayList<String> input) {
        ArrayList<String> old = input;
        input = HelpMethods.updateFile(input);
        int checkHeaderErrors = checkHeader(input);
        int checkSyntaxErrors = checkSyntax(input);
        int checkBaseErrors = checkBase(input, old);
        int checkNamingErrors = checkNaming(input);
        int totalErrors = checkBaseErrors + checkNamingErrors + checkHeaderErrors + checkSyntaxErrors;
        String result1 = checkHeaderErrors + " field fail in check header\n";
        String result2 = checkBaseErrors + " field fail in check base\n";
        String result3 = checkSyntaxErrors + " field fail in check syntax\n";
        String result4 = checkNamingErrors + " field fail in check naming\n";
        String result0 = "Process complete with " + totalErrors + " errors :\n";
        String tmp = "----------------------------------\n";
        String result = tmp + result0 + result1 + result2 + result3 + result4;

        return result;
    }

    private static int checkHeader(ArrayList<String> input) {
        int e1 = CheckHeader.checkPackage(input);
        int e2 = CheckHeader.checkImports(input);
        int e3 = CheckHeader.checkClass(input);
        return e1 + e2 + e3;
    }

    private static int checkSyntax(ArrayList<String> input) {
        int e1 = CheckSyntaxs.checkIf(input);
        int e2 = CheckSyntaxs.checkWhile(input);
        int e3 = CheckSyntaxs.checkSwitch(input);
        int e4 = CheckSyntaxs.checkFor(input);
        return e1 + e2 + e3 + e4;
    }

    private static int checkBase(ArrayList<String> input, ArrayList<String> old) {
        int e1 = CheckBaseStyle.check80Char(old);
        int e2 = CheckBaseStyle.checkSemicolon(input);
        int e3 = CheckBaseStyle.checkIndentation(input);
        return e1 + e2 + e3;
    }

    private static int checkNaming(ArrayList<String> input) {
        int e1 = CheckNaming.checkCamel(input);
        int e2 = CheckNaming.checkMethods(input);
        return e1 + e2;
    }
}
