
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
public class Booking 
{
    private final String URL = "jdbc:derby://localhost:1527/AgencyDB";
    private final String USERNAME = "ag";
    private final String PASSWORD = "ag";
    
    private static Connection connection;
    private static PreparedStatement addToBookings;
    private static PreparedStatement selectAllBookings;
    private static PreparedStatement deleteBookingsEntry;
    private static PreparedStatement selectBookedHolidaysForMagician;
    
    public Booking()
    {
        try
        {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            // add new customer and magician to bookings table
            addToBookings = connection.prepareStatement("INSERT INTO AG.BOOKINGS (Customer, Magician, Holiday) VALUES (?,?,?)");
            selectAllBookings = connection.prepareStatement("SELECT Customer,Magician,Holiday FROM AG.BOOKINGS");
            deleteBookingsEntry = connection.prepareStatement("DELETE FROM AG.BOOKINGS WHERE Customer = ? AND Holiday = ? AND Magician = ?");
            selectBookedHolidaysForMagician = connection.prepareStatement("SELECT Holiday FROM AG.BOOKINGS WHERE Magician = ?");
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            System.exit(1);
        }
    }
    
    public static List<Holiday> getBookedHolidaysForMagician (Magician magician)
    {
        List<Holiday> results;
        results = null;
        ResultSet resultSet = null;
        
        try
        {
            results = new ArrayList<>();
            selectBookedHolidaysForMagician.setString(1, magician.getName());
            
            resultSet = selectBookedHolidaysForMagician.executeQuery();
            
            while (resultSet.next())
            {
                results.add(new Holiday(resultSet.getString("Holiday")));
            }
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
        
        return results;
    }
    
    public static void deleteBookingsEntry (BookingClass entry)
    {
        int results = 0;
        
        try
        {
            deleteBookingsEntry.setString(1, entry.getCustomer());
            deleteBookingsEntry.setString(2, entry.getHoliday());
            deleteBookingsEntry.setString(3, entry.getMagician());
            
            results = deleteBookingsEntry.executeUpdate();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
                
    }
    
    public static void addToBookings (Customer customer, Magician magician)
    {
        try
        {
            addToBookings.setString(1, customer.getName());
            addToBookings.setString(2, magician.getName());
            addToBookings.setString(3, customer.getHoliday());
            
            addToBookings.executeUpdate();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
    }
    
    public static List<BookingClass> getAllBookings()
    {
        List<BookingClass> results = null;
        results = null;
        ResultSet resultSet = null;
        
        try
        {
            resultSet = selectAllBookings.executeQuery();
            results = new ArrayList<>();
            
            while(resultSet.next())
            {
                results.add(new BookingClass(resultSet.getString("Customer"), 
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
    
    static void populateTableWithBookings(JTable bookingsTable) 
    {
        ResultSet resultSet = null;
        
        // clear table first
        bookingsTable.removeAll();
        
        try
        {
            resultSet = selectAllBookings.executeQuery();
            
            int row = 0;
            
            while (resultSet.next())
            {
                bookingsTable.setValueAt(resultSet.getString("Customer"), row, 0);
                bookingsTable.setValueAt(resultSet.getString("Magician"), row, 1);
                bookingsTable.setValueAt(resultSet.getString("Holiday"), row, 2);
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