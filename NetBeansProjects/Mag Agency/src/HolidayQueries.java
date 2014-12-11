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

import javax.swing.table.*;

public class HolidayQueries 
{
    private static final String URL = "jdbc:derby://localhost:1527/AgencyDB";
    private static final String USERNAME = "ag";
    private static final String PASSWORD = "ag";
    
    private static Connection connection;
    private static PreparedStatement selectAllHolidays;
    private static PreparedStatement selectCustomersAndMagiciansForSelectedHoliday;
    private static PreparedStatement addHolidayToHolidayTable;
            
    
    //private static ResultSetTableModel tablemodel;

   

    
    
    
    public HolidayQueries ()
    {
        try
        {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            
            // return all holidays in the Holidays Table
            selectAllHolidays = connection.prepareStatement("SELECT * FROM ag.HOLIDAYS");
            selectCustomersAndMagiciansForSelectedHoliday = connection.prepareStatement("SELECT Customer,Magician FROM AG.BOOKINGS WHERE Holiday = ? ");
            addHolidayToHolidayTable = connection.prepareStatement("INSERT INTO AG.HOLIDAYS (Holiday) VALUES (?)");
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            System.exit(1);
        }
    }
    
    
    static void addHolidayToHolidayTable(Holiday holidayToAdd) 
    {
        int result = 0;
        
        try
        {
            addHolidayToHolidayTable.setString(1, holidayToAdd.getName());
            result = addHolidayToHolidayTable.executeUpdate();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
    }
    
    public static List<Holiday> getAllHolidays()
    {
        List<Holiday> results = null;
        ResultSet resultSet = null;
        
        try
        {
            resultSet = selectAllHolidays.executeQuery();
            results = new ArrayList<>();
            
            while(resultSet.next())
            {
                results.add(new Holiday(
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
    
    static void populateTableWithHoliday(Holiday holidayToDisplay, JTable table) 
    {
        String holidayName = holidayToDisplay.getName();
        ResultSet resultSet = null;
        
        
        try
        {
            selectCustomersAndMagiciansForSelectedHoliday.setString(1, holidayName);
            resultSet = selectCustomersAndMagiciansForSelectedHoliday.executeQuery();
            
            int row = 0;
            
            while (resultSet.next())
            {
                table.setValueAt(resultSet.getString("Customer"), row , 0);
                table.setValueAt(resultSet.getString("Magician"), row, 1);
                
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
