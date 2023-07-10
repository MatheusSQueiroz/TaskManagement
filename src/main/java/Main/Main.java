/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Util.ConnectionFactory;
import java.sql.Connection;

/**
 *
 * @author mathe
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Connection c = ConnectionFactory.getConnection();
        
        ConnectionFactory.closeConnection(c);
    }
    
}
