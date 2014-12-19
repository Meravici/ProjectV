package com.steps.Objects;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Alex on 12/19/2014.
 */
public class Task implements Serializable {
    private int id;
    private String name;
    private Date creationDate;
    private Date dueDate;
    private int status;
    private User author;
    private User volunteer;

    public Task(int id, String name, Date creationDate, Date dueDate, int status, User author, User volunteer) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.status = status;
        this.author = author;
        this.volunteer = volunteer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(User volunteer) {
        this.volunteer = volunteer;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", dueDate=" + dueDate +
                ", status=" + status +
                ", author=" + author +
                ", volunteer=" + volunteer +
                '}';
    }
}
