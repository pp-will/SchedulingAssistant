/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package performanceassessment.Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.prefs.Preferences;

/**
 *
 * @author wpittman
 */
public class UserOperations {
    
    public static String determineSqlError(int error, String userName) {
        String errorMessage;
        switch(error) {
            case 1062: errorMessage = "Username " + userName + " is already taken!\nPlease enter a new username and try again.";
            break;
            default: errorMessage = "";
        };
        return errorMessage;
    }
    
    public static int createNewUserQuery(String userName, String password, int active, LocalDateTime createDate, String createdBy, String lastUpdateBy) throws SQLException {
        Connector conn = new Connector();
        Connection db = conn.db();
        String separator = File.separator;
        
        String cwd = Paths.get("").toAbsolutePath().toString();
        cwd += separator + "src" + separator + "performanceassessment" + separator + "log" + separator + "userActivity.txt";
        File logFile = new File(cwd);
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
        
        Preferences prefs;
        prefs = Preferences.userNodeForPackage(UserOperations.class);
        
        Statement stmt = db.createStatement();
        String query = "insert into user (userName, password, active, createDate, createdBy, lastUpdateBy) values ('" 
                + userName + "', '"
                + password + "', "
                + active + ", '"
                + createDate + "', '" 
                + createdBy + "', '"
                + lastUpdateBy + "')";
        
        int result = 0;
        try {
        result = stmt.executeUpdate(query);
        System.out.println(result);
        prefs.put("userName", userName);
        } catch(SQLException e) {
            int err = e.getErrorCode();
            System.out.println(err);
            String errorMessage = determineSqlError(err, userName);
            prefs.put("errorMessage", errorMessage);
            
        }
        
        if(result == 1) {
            try {
                logFile.createNewFile();
                System.out.println(logFile.getCanonicalPath());
                fw = new FileWriter(logFile, true);
                
                bw = new BufferedWriter(fw);
                pw = new PrintWriter(bw);
                
                pw.println(userName + " ::: " + createDate);
                System.out.println("data logged");
                pw.flush();
                
                pw.close();
                bw.close();
                fw.close(); 
                
                
            } catch (IOException io) {
                io.printStackTrace();
            }
            
        }
        
        return result;
    }
}
