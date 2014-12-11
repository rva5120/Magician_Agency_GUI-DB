/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author raquelalvarez
 */
public final class Customer 
{
    //private int customerID;
    private String name;
    private String holiday;
    
    public Customer(){}
    
    public Customer (String name, String holiday)
    {
        //setCustomerID(customerID);
        setName(name);
        setHoliday(holiday);
    }

    /**
     * @return the customerID
     */
//    public int getCustomerID() {
//        return customerID;
//    }
//
//    /**
//     * @param customerID the customerID to set
//     */
//    public void setCustomerID(int customerID) {
//        this.customerID = customerID;
//    }

    /**
     * @return the holiday
     */
    public String getHoliday() {
        return holiday;
    }

    /**
     * @param holiday the holiday to set
     */
    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
