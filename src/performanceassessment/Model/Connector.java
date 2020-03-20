/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package performanceassessment.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author wpittman
 */
public class Connector {
    
   public static Connection db() throws SQLException{
       Connection conn = DriverManager.getConnection(
               "jdbc:mysql://3.227.166.251/U04SfQ",
               "U04SfQ",
               "53688329578"
       );
       return conn;
   }
    
}
