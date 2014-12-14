Magician_Agency_GUI-DB
======================

Tools used for this project: JAVA SQL OOD GUI DB

Java GUI Application Scheduler

This is a Java application developed in Netbeans. It uses a derby database with SQL queries. 

It is a scheduling application that allows you to:
  - Select a holiday and type the name of a customer to schedule it.
  - You may add any holidays you want.
  - You can also add and drop any magician.
    - When you drop a magician, the previously scheduled customers will be put to the top of the waitlist.
    - When you add a magician, the top customers of the waitlist will be assigned that magician.
  - Each magician can only work one holiday. So if the magicians are all booked for a selected holiday, the customer will be put on the waitlist.
  - There is a Status tab that will let you select a:
    - holiday, which will populate a table with all the customers and assigned magicians for that holiday,
    - customer, which will populate a table with all the magicians and selected holidays for that customer,
    - magician, which  will populate a table with all the customers and selected holidays for that magician.
  - You may also cancel any customers either booked or in the waitlist.
    - If you cancel a booked customer, then a magician will become available, and it will be automatically assigned to the first customer on the waitlist.
  - Every time you enter a customer, they get a timestamp.
  - The customers on the waitlist are ordered by timestamp. When a magician is dropped, previously booked customers will be placed on the top of the waitlist and will be booked when the next magician becomes available.
  
  
For more information: rva5120@psu.edu
