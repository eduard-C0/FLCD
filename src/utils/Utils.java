package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {
    public static Pattern identifierPattern = Pattern.compile("[a-zA-z][0-9a-zA-Z]*");
    public static Pattern integerPattern = Pattern.compile("0|(-?[1-9][0-9]*)");
    public static Pattern stringPattern = Pattern.compile("\"([0-9a-zA-Z])+\"");
    public static List<String> separators = populateSeparators();
    public static List<String> separatorsRegex = populateSeparatorsRegex();

    private static List<String> populateSeparatorsRegex(){
        List<String> regexSeparators = Arrays.asList(
                "==",
                "<=",
                ">=",
                "!=",
                "&&",
                "\\|\\|",
                "\\+",
                "-",
                "/",
                "\\*",
                "<",
                ">",
                "!",
                "\\(",
                "\\)",
                "\\{",
                "\\}",
                "\\[",
                "\\]",
                ";",
                ",",
                "print",
                "int",
                "char",
                "string",
                "boolean",
                "array",
                "true",
                "false",
                "if",
                "otherwise",
                "for",
                "while",
                "init",
                "read"

        );
        return regexSeparators;
    }

    private static List<String> populateSeparators(){
        List<String> separatorsList = Arrays.asList(
                "==",
                "<=",
                ">=",
                "!=",
                "&&",
                "||",
                "+",
                "-",
                "/",
                "*",
                "<",
                ">",
                "!",
                "(",
                ")",
                "{",
                "}",
                "[",
                "]",
                ";",
                ",",
                "print",
                "int",
                "char",
                "string",
                "boolean",
                "array",
                "true",
                "false",
                "if",
                "otherwise",
                "for",
                "while",
                "init",
                "read"

        );
        return separatorsList;
    }
}
