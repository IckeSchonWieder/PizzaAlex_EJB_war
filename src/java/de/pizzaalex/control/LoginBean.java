/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pizzaalex.control;

import de.pizzaalex.model.User;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author AWagner
 */
@SessionScoped
@Named
public class LoginBean implements Serializable{
    private User user;
    private Boolean loggedIn;
    private String role;

    
    
    public LoginBean() {
        user = new User();
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public void settingRole(HttpServletRequest request) {
        if (request.isUserInRole("customerRole")){
            role = "customer";
        } else if (request.isUserInRole("cookRole")) {
            role = "cook";
        } else if (request.isUserInRole("managerRole")) {
            role = "manager";
        } else {
            role = null; 
        }
    }
    
    
    public String login(HttpServletRequest req){
        System.out.println("Login: " +user.toString());
        //HttpServletRequest req=(HttpServletRequest) FacesContext.getCurrentInstance()
        //        .getExternalContext().getRequest();
        try {
            
            req.login(user.getUsername(), user.getPassword());
            loggedIn=true;
            settingRole(req);
            return "customer";
            
        } catch (ServletException ex) {
            loggedIn=false;
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
        
    }
    
    public void logout(HttpServletRequest req) {
       // HttpServletRequest req=(HttpServletRequest) FacesContext.getCurrentInstance()
         //       .getExternalContext().getRequest();
        try {
            req.logout();
            loggedIn = false;
            role = null;
        } catch (ServletException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
