/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sakshi
 */


public class DBConnect {
    
  
private Statement st;
private ResultSet rs;
    
    public Connection Connect()
    {

       
        try{
            String url="jdbc:mysql://localhost:3306/banking";
            String user="root";
            String pass="root";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(url,user,pass);
            return con;
            
        }
        catch(Exception e)
        {
            
        }
        return null;
    }
    
    public int newUser(String name,String accountNo,String pin)
    {
        Connection con = null;
         try
       {
           System.out.println("hi");
           
           int b=0;
           con= Connect();
           PreparedStatement ps = con.prepareStatement("INSERT INTO userinfo(name,account_no,pin,balance) VALUES ('"+name+"', '"+accountNo+"', '"+pin+"','"+b+"')");
         
           ps.executeUpdate();
         
           System.out.println("Insert Successfully");
           return 0;
          
       }catch(Exception e)
       {
           if(e.toString().contains("PRIMARY"))
               return(1);
           else 
               return(2);
       }
       finally
       {
          closeConnection(con);
       }
    }
    
    public String getName(String an)
    {
       Connection con=null;
        String username = null;
        try
        {
            con=Connect();
            st=con.createStatement();
            String query = "SELECT name FROM userinfo WHERE account_no = '"+an+"' ";
            rs = st.executeQuery(query);
            if(rs.next())
            {
                username = rs.getString("name");
            }
        }catch(Exception ex)
        {
            System.out.println("Error" +ex);
        }
        finally
        {
            closeConnection(con);
        }
        return username;
    }
    
      public String getPin(String an)
    {
       Connection con=null;
        String pin = null;
        try
        {
            con=Connect();
            st=con.createStatement();
            String query = "SELECT pin FROM userinfo WHERE account_no = '"+an+"' ";
            rs = st.executeQuery(query);
            if(rs.next())
            {
                pin = rs.getString("pin");
            }
        }catch(Exception ex)
        {
            System.out.println("Error" +ex);
        }
        finally
        {
            closeConnection(con);
        }
        return pin;
    }
      
       public long getBalance(String an)
    {
       Connection con=null;
        long balance = 0;
        try
        {
            con=Connect();
            st=con.createStatement();
            String query = "SELECT balance FROM userinfo WHERE account_no = '"+an+"' ";
            rs = st.executeQuery(query);
            
            if(rs.next())
            {
                balance = rs.getInt("balance");
            }
            else
            {
                closeConnection(con);
                return -1;
            }
                
            
            {
                
            }
        }catch(Exception ex)
        {
            System.out.println("Error" +ex);
           
        }
        finally
        {
            closeConnection(con);
        }
        
           
       
         return balance;
        
    }
    
       void setPin(String accno ,String newp) throws SQLException
    {
        Connection con=null;
       try
       {
           con=Connect();
           PreparedStatement ps;
           ps = con.prepareStatement("UPDATE userinfo SET pin = '"+newp+"' WHERE account_no = '"+accno+"'");
           ps.executeUpdate();
           System.out.println("Password Successfully Changed..");
           
       }catch(Exception ex)
       {
           System.out.println("Error" +ex);
       }
       finally{
            con.close();
       }
   }   
       
       void setBalance(String accno ,long newBal) throws SQLException
    {
        Connection con=null;
       try
       {
           con=Connect();
           PreparedStatement ps;
           ps = con.prepareStatement("UPDATE userinfo SET balance = '"+newBal+"' WHERE account_no = '"+accno+"'");
           ps.executeUpdate();
           System.out.println("Balance updated");
           
       }catch(Exception ex)
       {
           System.out.println("Error" +ex);
       }
       finally{
            con.close();
       }
   }   
       
       
       
       
       
    
    void closeConnection(Connection con)
    {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
    
