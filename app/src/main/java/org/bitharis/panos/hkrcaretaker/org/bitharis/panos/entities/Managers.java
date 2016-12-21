/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities;

import java.io.Serializable;
import java.util.Collection;


public class Managers implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer manId;

    private String manFirstname;

    private String manLastname;

    private String manUsername;

    private String manPassword;

    private String manEmail;

    private String manPhone;

    private Collection<Employees> employeesCollection;

    public Managers() {
    }

    public Managers(Integer manId) {
        this.manId = manId;
    }

    public Managers(Integer manId, String manFirstname, String manLastname, String manUsername, String manPassword, String manEmail, String manPhone) {
        this.manId = manId;
        this.manFirstname = manFirstname;
        this.manLastname = manLastname;
        this.manUsername = manUsername;
        this.manPassword = manPassword;
        this.manEmail = manEmail;
        this.manPhone = manPhone;
    }

    public Integer getManId() {
        return manId;
    }

    public void setManId(Integer manId) {
        this.manId = manId;
    }

    public String getManFirstname() {
        return manFirstname;
    }

    public void setManFirstname(String manFirstname) {
        this.manFirstname = manFirstname;
    }

    public String getManLastname() {
        return manLastname;
    }

    public void setManLastname(String manLastname) {
        this.manLastname = manLastname;
    }

    public String getManUsername() {
        return manUsername;
    }

    public void setManUsername(String manUsername) {
        this.manUsername = manUsername;
    }

    public String getManPassword() {
        return manPassword;
    }

    public void setManPassword(String manPassword) {
        this.manPassword = manPassword;
    }

    public String getManEmail() {
        return manEmail;
    }

    public void setManEmail(String manEmail) {
        this.manEmail = manEmail;
    }

    public String getManPhone() {
        return manPhone;
    }

    public void setManPhone(String manPhone) {
        this.manPhone = manPhone;
    }


    public Collection<Employees> getEmployeesCollection() {
        return employeesCollection;
    }

    public void setEmployeesCollection(Collection<Employees> employeesCollection) {
        this.employeesCollection = employeesCollection;
    }

    
}
