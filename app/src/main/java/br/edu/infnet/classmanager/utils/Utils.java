package br.edu.infnet.classmanager.utils;

public class Utils {

    public static String getNameFromInstitutionalMail(String email) throws IndexOutOfBoundsException{

        //Assuming it's a valid infnet email (TODO: should check)
        String[] parts = email.split("[.@]");
        if (parts.length > 2){
            String firstName = capitalize(parts[0]);
            String lastName = capitalize(parts[1]);
            return  firstName + " " + lastName;
        }

        return null;
    }

    public static String capitalize(String word){
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
