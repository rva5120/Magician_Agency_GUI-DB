/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author raquelalvarez
 */
public class BookingClass 
{
    private String customer;
    private String holiday;
    private String magician;
    
    public BookingClass()
    {
        
    }
    
    public BookingClass(String customer, String holiday, String magician)
    {
        setCustomer(customer);
        setHoliday(holiday);
        setMagician(magician);
    }

    /**
     * @return the customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

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
     * @return the magician
     */
    public String getMagician() {
        return magician;
    }

    /**
     * @param magician the magician to set
     */
    public void setMagician(String magician) {
        this.magician = magician;
    }
    
}
