/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;



public class Tasks implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer taskId;

    private String taskTitle;

    private String taskContent;

    private String taskdueDate;

    private boolean taskCompl;

    private Collection<Reminders> remindersCollection;

    public Tasks() {
    }

    public Tasks(Integer taskId) {
        this.taskId = taskId;
    }

    public Tasks(Integer taskId, String taskTitle, String taskContent, boolean taskCompl) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskContent = taskContent;
        this.taskCompl = taskCompl;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskdueDate() {
        return taskdueDate;
    }

    public void setTaskdueDate(String taskdueDate) {
        this.taskdueDate = taskdueDate;
    }

    public boolean getTaskCompl() {
        return taskCompl;
    }

    public void setTaskCompl(boolean taskCompl) {
        this.taskCompl = taskCompl;
    }


    public Collection<Reminders> getRemindersCollection() {
        return remindersCollection;
    }

    public void setRemindersCollection(Collection<Reminders> remindersCollection) {
        this.remindersCollection = remindersCollection;
    }


    
}
