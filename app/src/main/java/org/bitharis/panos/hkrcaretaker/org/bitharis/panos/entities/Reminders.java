/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


/**
 *
 * @author Chris
 */

public class Reminders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer remId;

    private Date remDate;

    private Date remTime;

    private Collection<Tasks> tasksCollection;

    public Reminders() {
    }

    public Reminders(Integer remId) {
        this.remId = remId;
    }

    public Reminders(Integer remId, Date remDate, Date remTime) {
        this.remId = remId;
        this.remDate = remDate;
        this.remTime = remTime;
    }

    public Integer getRemId() {
        return remId;
    }

    public void setRemId(Integer remId) {
        this.remId = remId;
    }

    public Date getRemDate() {
        return remDate;
    }

    public void setRemDate(Date remDate) {
        this.remDate = remDate;
    }

    public Date getRemTime() {
        return remTime;
    }

    public void setRemTime(Date remTime) {
        this.remTime = remTime;
    }

    public Collection<Tasks> getTasksCollection() {
        return tasksCollection;
    }

    public void setTasksCollection(Collection<Tasks> tasksCollection) {
        this.tasksCollection = tasksCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (remId != null ? remId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reminders)) {
            return false;
        }
        Reminders other = (Reminders) object;
        if ((this.remId == null && other.remId != null) || (this.remId != null && !this.remId.equals(other.remId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Reminders[ remId=" + remId + " ]";
    }
    
}
