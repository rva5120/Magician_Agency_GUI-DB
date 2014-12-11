
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
public class MagicianQueries
{
    
    private final String URL = "jdbc:derby://localhost:1527/AgencyDB";
    private final String USERNAME = "ag";
    private final String PASSWORD = "ag";
    
    private static Connection connection;
    private static PreparedStatement selectAllFreeMagicians;
    private static PreparedStatement selectAllMagicians;
    private static PreparedStatement bookMagician;
    private static PreparedStatement selectCustomerAndHolidayForSelectedMagician;
    private static PreparedStatement deleteMagician;
    
    public MagicianQueries()
    {
        try
        {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            selectAllFreeMagicians = 
                    connection.prepareStatement("SELECT Magician FROM AG.MAGICIANS WHERE Magician not in (SELECT Magician FROM AG.BOOKINGS WHERE Holiday = ?)");
            selectAllMagicians = 
                    connection.prepareStatement("SELECT * FROM AG.MAGICIANS");
            //bookMagician = connection.prepareStatement("");
            selectCustomerAndHolidayForSelectedMagician = 
                    connection.prepareStatement("SELECT Customer,Holiday FROM AG.BOOKINGS WHERE Magician = ?");         // ** Add method for this and connect to Status tab's JComboBox and JTable
            deleteMagician =
                    connection.prepareStatement("DELETE FROM AG.MAGICIANS WHERE Magician = ?");
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            System.exit(1);
        }
    }
    
    public static void removeMagician (Magician magician)
    {
        try
        {
            deleteMagician.setString(1, magician.getName());
            deleteMagician.executeUpdate();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            close();
        }
    }
    
    public static String getAllFreeMagicians(String holiday)
    {
        //List<Magician> results = null;
        ResultSet resultSet = null;
        String results = null;
        
        try
        {
            selectAllFreeMagicians.setString(1, holiday);
            resultSet = selectAllFreeMagicians.executeQuery();
            //results = new ArrayList<>();
        
            if (resultSet.next() == false)
            {
                results = null;
            }
            else
            {
                results = resultSet.getString("Magician");
            }
            
//            while (resultSet.next())
//            {
//                    results.add(new Magician(
//                            resultSet.getString("Magician")
//                    ));
//            }
            
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
        
        return results;
    }
    
    public static List<Magician> getAllMagicians()
    {
        List<Magician> results = null;
        ResultSet resultSet = null;
        
        try
        {
            resultSet = selectAllMagicians.executeQuery();
            results = new ArrayList<>();
            
            while (resultSet.next())
            {
                results.add(new Magician(
                        resultSet.getString("Magician")
                ));
            }
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
        
        return results;
    }
    
//    public static int bookMagician (Magician magician)
//    {
//        int result = 0;
//        
//        try
//        {
//            bookMagician.setString(1, magician.getName());
//            result = bookMagician.executeUpdate();
//        }
//        catch (SQLException sqle)
//        {
//            sqle.printStackTrace();
//            close();
//        }
//        
//        return result;
//    }
    
    static void populateTableWithMagician(Magician magicianToDisplayInTable, JTable magicianStatusTable) 
    {
        String magicianToDisplay = magicianToDisplayInTable.getName();
        ResultSet resultSet = null;
        
        try
        {
            selectCustomerAndHolidayForSelectedMagician.setString(1, magicianToDisplay);
            resultSet = selectCustomerAndHolidayForSelectedMagician.executeQuery();
            
            int row  = 0;
            
            while (resultSet.next())
            {
                magicianStatusTable.setValueAt(resultSet.getString("Customer"), row, 0);
                magicianStatusTable.setValueAt(resultSet.getString("Holiday"), row, 1);
                
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
