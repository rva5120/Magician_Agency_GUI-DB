/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author raquelalvarez
 */
public class WaitlistClass 
{
    private String customer;
    private String holiday;
    //private String timestamp;
    
    public WaitlistClass () {}
    
    public WaitlistClass (Customer customer, Holiday holiday)
    {
        setCustomer(customer.getName());
        setHoliday(holiday.getName());
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
    
    
}
