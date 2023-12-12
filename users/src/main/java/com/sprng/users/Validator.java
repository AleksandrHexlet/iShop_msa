package com.sprng.users;


public class Validator {


    public  static boolean isValidString(String str, int min, int max){
        return  str.length() > min && str.length() < max;
    };

    public  static boolean isValidInteger(int integer,int min, int max ){
        return  integer > min && integer < max;
    }
}
