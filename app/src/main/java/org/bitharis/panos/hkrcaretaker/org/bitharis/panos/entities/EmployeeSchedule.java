/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities;

import java.io.Serializable;
import java.util.Date;



public class EmployeeSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer schId;

    //@Temporal(TemporalType.DATE)
    private String schDate;

    //@Temporal(TemporalType.TIME)
    private String schFromTime;

    //@Temporal(TemporalType.TIME)
    private String schUntilTime;

    private boolean emplVisitedCust;

    private Customers customersCuId;

    private Employees employeesEmpId;

    public EmployeeSchedule() {
    }

    public EmployeeSchedule(Integer schId) {
        this.schId = schId;
    }

    public EmployeeSchedule(String cuId, String cuLname, String cuFname, String cuAddress,
                            String schDate, String schFromTime, String schUntilTime, String emplVisitedCust) {

        Integer i = new Integer(Integer.valueOf(cuId));
        this.customersCuId = new Customers(i);
        this.customersCuId.setCuLastname(cuLname);
        this.customersCuId.setCuFirstname(cuFname);
        this.customersCuId.setCuAddress(cuAddress);
        this.schDate = schDate;
        this.schFromTime = schFromTime;
        this.schUntilTime = schUntilTime;
        Boolean b = new Boolean(Boolean.valueOf(emplVisitedCust));
        this.emplVisitedCust = b.booleanValue();
    }
    public EmployeeSchedule(Integer schId, String schDate, String schFromTime, String schUntilTime, boolean emplVisitedCust) {
        this.schId = schId;
        this.schDate = schDate;
        this.schFromTime = schFromTime;
        this.schUntilTime = schUntilTime;
        this.emplVisitedCust = emplVisitedCust;
    }

    public Integer getSchId() {
        return schId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }

    public String getSchDate() {
        return schDate;
    }

    public void setSchDate(String schDate) {
        this.schDate = schDate;
    }

    public String getSchFromTime() {
        return schFromTime;
    }

    public void setSchFromTime(String schFromTime) {
        this.schFromTime = schFromTime;
    }

    public String getSchUntilTime() {
        return schUntilTime;
    }

    public void setSchUntilTime(String schUntilTime) {
        this.schUntilTime = schUntilTime;
    }

    public boolean getEmplVisitedCust() {
        return emplVisitedCust;
    }

    public void setEmplVisitedCust(boolean emplVisitedCust) {
        this.emplVisitedCust = emplVisitedCust;
    }
    
    public Customers getCustomersCuId() {
        return customersCuId;
    }

    public void setCustomersCuId(Customers customersCuId) {
        this.customersCuId = customersCuId;
    }

    public Employees getEmployeesEmpId() {
        return employeesEmpId;
    }

    public void setEmployeesEmpId(Employees employeesEmpId) {
        this.employeesEmpId = employeesEmpId;
    }

    @Override
    public String toString(){
        return this.customersCuId.getCuLastname()+" "+this.customersCuId.getCuFirstname()+" "+this.getSchDate();
    }

    
}
