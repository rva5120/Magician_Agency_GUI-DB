/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author raquelalvarez
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import java.sql.Timestamp;
import java.util.Calendar;

public class CustomerQueries 
{
    private static final String URL = "jdbc:derby://localhost:1527/AgencyDB";
    private static final String USERNAME = "ag";
    private static final String PASSWORD = "ag";
    
    private static Connection connection;
    private static PreparedStatement selectAllCustomers;
    private static PreparedStatement addToWaitlist;
    private static PreparedStatement selectMagicianAndHolidayForSelectedCustomer;
    private static PreparedStatement addCustomerToCustomersTable;
    private static Timestamp currentTimestamp;
    
    public CustomerQueries()
    {
        try
        {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            
            selectAllCustomers = connection.prepareStatement("SELECT Customer,Holiday FROM AG.BOOKINGS");
            addToWaitlist = connection.prepareStatement("INSERT INTO AG.WAITLIST (Customer, Holiday, Timestamp) VALUES (?,?,?)");
            selectMagicianAndHolidayForSelectedCustomer = connection.prepareStatement("SELECT Magician,Holiday FROM AG.BOOKINGS WHERE Customer = ?");
            addCustomerToCustomersTable = connection.prepareStatement("INSERT INTO AG.CUSTOMERS (Customer, Holiday) VALUES (?,?)");
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            System.exit(1);
        }
    }
    
    public static void addCustomerToCustomersTable(String Customer, String Holiday)
    {
        int result = 0;
        try
        {
            addCustomerToCustomersTable.setString(1, Customer);
            addCustomerToCustomersTable.setString(2, Holiday);
            
            result = addCustomerToCustomersTable.executeUpdate();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
    }
    
    public static List<Customer> getAllCustomers()
    {
        List<Customer> results = null;
        ResultSet resultSet = null;
        
        try
        {
            resultSet = selectAllCustomers.executeQuery();
            results = new ArrayList<>();
            
            while (resultSet.next())
            {
                results.add(new Customer(
                        resultSet.getString("Customer"),
                        resultSet.getString("Holiday")
                ));
            }
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }

        return results;
    }
    
    public static int addToWaitlist(Customer customer)
    {
        int result = 0;
        
        currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        
        try
        {
            addToWaitlist.setString(1, customer.getName());
            addToWaitlist.setString(2, customer.getHoliday());
            addToWaitlist.setTimestamp(3, currentTimestamp);
            
            result = addToWaitlist.executeUpdate();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
        
        return result;
    }
    
    static void populateTableWithCustomer(String customerToDisplayInTable, JTable customerStatusTable) 
    {
        ResultSet resultSet = null;
        
        try
        {
            selectMagicianAndHolidayForSelectedCustomer.setString(1, customerToDisplayInTable);
            resultSet = selectMagicianAndHolidayForSelectedCustomer.executeQuery();
            
            int row = 0;
            
            while (resultSet.next())
            {
                customerStatusTable.setValueAt(resultSet.getString("Magician"), row, 0);
                customerStatusTable.setValueAt(resultSet.getString("Holiday"), row, 1);
                row++;
            }
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
    }
    
    public static List<BookingClass> getMagicianAndHolidayForSelectedCustomer (String customer)
    {
        List<BookingClass> results;
        results = null;
        ResultSet resultSet;
        
        try
        {
            results = new ArrayList<>();
            
            selectMagicianAndHolidayForSelectedCustomer.setString(1, customer);
            resultSet = selectMagicianAndHolidayForSelectedCustomer.executeQuery();
            
            while (resultSet.next())
            {
                results.add(new BookingClass(customer, 
                        resultSet.getString("Holiday"), 
                        resultSet.getString("Magician")));
            }
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
        
        
        return results;
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
