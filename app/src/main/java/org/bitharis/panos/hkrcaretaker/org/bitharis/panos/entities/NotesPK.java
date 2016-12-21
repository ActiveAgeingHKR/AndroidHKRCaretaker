/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities;

import java.io.Serializable;


public class NotesPK implements Serializable {


    private int noteId;

    private int employeesEmpId;

    public NotesPK() {
    }

    public NotesPK(int noteId, int employeesEmpId) {
        this.noteId = noteId;
        this.employeesEmpId = employeesEmpId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getEmployeesEmpId() {
        return employeesEmpId;
    }

    public void setEmployeesEmpId(int employeesEmpId) {
        this.employeesEmpId = employeesEmpId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) noteId;
        hash += (int) employeesEmpId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotesPK)) {
            return false;
        }
        NotesPK other = (NotesPK) object;
        if (this.noteId != other.noteId) {
            return false;
        }
        if (this.employeesEmpId != other.employeesEmpId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.NotesPK[ noteId=" + noteId + ", employeesEmpId=" + employeesEmpId + " ]";
    }
    
}
