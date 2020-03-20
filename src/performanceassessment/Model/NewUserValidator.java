/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package performanceassessment.Model;

/**
 *
 * @author wpittman
 */
public class NewUserValidator {
    
    public static boolean userNameLengthCheck(String userName) throws NullPointerException{
        if(userName.length() < 6) {
            return false;
        } else if (userName.length() >= 6 && userName.length()<= 32) {
            return true;
        } else {
        return false;
        }
    }
    
    public static boolean passwordLengthCheck(String pwd) throws NullPointerException {
        if(pwd.length() < 6) {
            return false;
        } else if (pwd.length() >= 6 && pwd.length() <= 32) {
            return true;
        } else {
        return false;
        }
    }
    
    public static boolean passwordMatchCheck(String pwd1, String pwd2) {
        if(pwd1.equals(pwd2)) {
            return true;
        } else {
        return false;
        }
    }
    
}
