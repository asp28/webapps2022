/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.jsf;

import com.webapps2022.ejb.UserService;
import com.webapps2022.entity.SystemUser;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author ankee
 */
@Named
@RequestScoped
public class AdminUserManagementBean {
    
    @EJB
    private UserService userService;
    
    public List<SystemUser> getAllUsers() {
        return userService.getAllUsers();
    }
    
}
