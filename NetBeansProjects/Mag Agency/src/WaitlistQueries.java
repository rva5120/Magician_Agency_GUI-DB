
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author raquelalvarez
 */
public class WaitlistQueries 
{
    private static final String URL = "jdbc:derby://localhost:1527/AgencyDB";
    private static final String PASSWORD = "ag";
    private static final String USERNAME = "ag";
    
    private static Connection connection;
    private static PreparedStatement selectAllWaitlist;
    private static PreparedStatement selectHolidaysForCustomerInWaitlist;
    private static PreparedStatement selectCustomersInWaitlistForAvailableMagician;
    private static PreparedStatement removeCustomerFromWaitlist;
    
    
    public WaitlistQueries ()
    {
        try
        {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            selectAllWaitlist = connection.prepareStatement("SELECT * FROM AG.WAITLIST");
            selectCustomersInWaitlistForAvailableMagician = connection.prepareStatement("SELECT * FROM AG.WAITLIST WHERE Holiday not in (SELECT Holiday FROM AG.BOOKINGS WHERE Magician = ?)");
            removeCustomerFromWaitlist = connection.prepareStatement("DELETE FROM AG.WAITLIST WHERE Customer = ?");
            selectHolidaysForCustomerInWaitlist = connection.prepareStatement("SELECT Holiday FROM AG.WAITLIST WHERE Customer = ?");
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            System.exit(1);
        }
    }
    
    public static List<Holiday> getHolidaysForCustomerInWaitlist (Customer customer)
    {
        List<Holiday> results = null;
        ResultSet resultSet;
        
        try
        {
            resultSet = null;
            results = new ArrayList<>();
            
            selectHolidaysForCustomerInWaitlist.setString(1, customer.getName());
            resultSet = selectHolidaysForCustomerInWaitlist.executeQuery();
            
            while (resultSet.next())
            {
                results.add(new Holiday(
                        resultSet.getString("Holiday")));
            }
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
        
        return results;
    }
    
    public static List<Customer> getAllWaitlist ()
    {
        List<Customer> results = null;
        ResultSet resultSet;
        
        try
        {
            results = new ArrayList<>();
            resultSet = null;
            
            resultSet = selectAllWaitlist.executeQuery();
            
            while(resultSet.next())
            {
                results.add(new Customer (
                        resultSet.getString("Customer"),
                        resultSet.getString("Holiday")));
            }
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
        
        return results;
    }
    
    public static void removeCustomerFromWaitlist (Customer customer)
    {  
        try
        {
            removeCustomerFromWaitlist.setString(1, customer.getName());
            removeCustomerFromWaitlist.executeUpdate();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
    }
    
    public static List<Customer> getCustomersInWaitlistForAvailableMagician (Magician magician)
    {
        List<Customer> results;
        results = null;
        ResultSet resultSet = null;
        
        try
        {
            results = new ArrayList<>();
            
            selectCustomersInWaitlistForAvailableMagician.setString(1, magician.getName());
            resultSet = selectCustomersInWaitlistForAvailableMagician.executeQuery();
            
            while(resultSet.next())
            {
                results.add(new Customer(
                        resultSet.getString("Customer"), 
                        resultSet.getString("Holiday")));
            }
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
        
        return results;
    }
    
    public static void populateTableWithWaitlist(JTable waitlistTable)
    {
        ResultSet resultSet;
        
        // clear table first
        waitlistTable.removeAll();
        
        try
        {
            //resultSet = null;
            resultSet = selectAllWaitlist.executeQuery();
            
            int row = 0;
            
            while (resultSet.next())
            {
                waitlistTable.setValueAt(resultSet.getString("Customer"), row, 0);
                waitlistTable.setValueAt(resultSet.getString("Holiday"), row, 1);
                waitlistTable.setValueAt(resultSet.getString("Timestamp"), row, 2);
                row++;
            }
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
    }
    
    public static void close()
    {
        try
        {
            connection.close();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
}
