
package de.pizzaalex.control;

import de.pizzaalex.ejb.DataBeanRemote;
import de.pizzaalex.model.Customer;
import de.pizzaalex.model.Order;
import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;

/**
 *
 * @author AWagner
 */

@SessionScoped
@Named
public class CustomerBean extends LookUpData implements Serializable {
    private Customer selectedCustomer;
    private boolean hasAcc;
    private DataBeanRemote dbr;
    private ArrayList<Customer> customers;
    
    
    public CustomerBean() {
        hasAcc = false;
        dbr = lookupDataBeanRemote();
        customers = dbr.getCustomers();
    }
   
    
    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }
       
    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    
    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    
//    public void setCustomers(ArrayList<Customer> customers) {
//        this.customers = customers;
//    }
    
    public boolean isHasAcc() {
        return hasAcc;
    }

    public void setHasAcc(boolean hasAcc) {
        this.hasAcc = hasAcc;
    }

  
    
    public Customer getCustById(int id){
        Customer found = null;
        
        for (Customer cus:customers) {
            if (cus.getId() == id) {
                found = cus;
                //System.out.println("Kunde gefunden: " + cus.id + " " + found.id);
            } 
        }
        return found;
    }
    
    public String newCust(){
        selectedCustomer = new Customer();
        hasAcc = false;
        return "newCustomer";
    }
     
    
    public void storeCustomer(Customer cus) {
        dbr.storeCustomer(cus);
        
    }
    
    
    public String checkOrder(Order order){
        if (!hasAcc) {
            storeCustomer(selectedCustomer);
        }
        order.setCus(selectedCustomer);
        return "check";
    }
}
