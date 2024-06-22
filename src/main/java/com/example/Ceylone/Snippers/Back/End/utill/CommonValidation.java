package com.example.Ceylone.Snippers.Back.End.utill;

public class CommonValidation {
    public static boolean stringNullValidation(String inputString) {
        return inputString == null || inputString.isEmpty();
    }

//    --------------------------LENGTH VALIDATIONS----------------------------
    public static boolean isValidLength(String input) {
        return input != null && input.length() == 20;
    }
    public static boolean isValidLengthDiscriptions (String input) {
        return input != null && input.length() == 300;
    }
    public static boolean isValidLengthCatagoryName (String input) {
        return input != null && input.length() == 10;
    }


}
