/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities;

import java.io.Serializable;



public class Notes implements Serializable {

    private static final long serialVersionUID = 1L;

    protected NotesPK notesPK;

    private String noteTitle;

    private String content;

    private Customers customersCuId;

    private Employees employees;

    public Notes() {
    }

    public Notes(NotesPK notesPK) {
        this.notesPK = notesPK;
    }

    public Notes(NotesPK notesPK, String noteTitle, String content) {
        this.notesPK = notesPK;
        this.noteTitle = noteTitle;
        this.content = content;
    }

    public Notes(int noteId, int employeesEmpId) {
        this.notesPK = new NotesPK(noteId, employeesEmpId);
    }

    public NotesPK getNotesPK() {
        return notesPK;
    }

    public void setNotesPK(NotesPK notesPK) {
        this.notesPK = notesPK;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public Customers getCustomersCuId() {
        return customersCuId;
    }

    public void setCustomersCuId(Customers customersCuId) {
        this.customersCuId = customersCuId;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }


    
}
