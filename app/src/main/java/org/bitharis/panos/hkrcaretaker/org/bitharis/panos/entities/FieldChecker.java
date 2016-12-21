package org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities;

/**
 * Created by panos on 12/18/2016.
 */

public class FieldChecker {


    /**
     * Receives a String and checks if it could correspond to a valid first name or lastname
     * according to the fields in our database
     * @param parameter
     * @return
     */
    public boolean checkNameTypeStrings(String parameter){
        boolean valid=false;
        if(parameter!="" || parameter!=null ){
             if(parameter.length()<=20 && parameter.length()>0){
                valid =  parameter.matches("[a-zA-Z]*");
             }
        }else{
            return valid;
        }
        System.out.println("Name type field is valid? "+valid);
        return valid;
    }

    /**
     * Receives a String and checks if it could correspond to a valid phonenumber
     * according to the fields in our database
     * @param parameter
     * @return
     */
    public boolean checkPhoneTypeStrings(String parameter){
        boolean valid=false;
        if(parameter!="" || parameter!=null ){
            if(parameter.length()<=15 && parameter.length()>0){
                valid =  parameter.matches("[0-9]*");
            }
        }else{
            return valid;
        }
        return valid;
    }
}
