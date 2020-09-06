package com.haulmont.testtask.pojo;

import java.io.Serializable;

public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String about;
    private long patient;
    private long doctor;
    private String createDate;
    private String duration;
    private String priority;

    public Recipe(Long id, String about, long patient, long doctor, String createDate, String duration, String priority) {
        this.id = id;
        this.about = about;
        this.patient = patient;
        this.doctor = doctor;
        this.createDate = createDate;
        this.duration = duration;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public long getPatient() {
        return patient;
    }

    public void setPatient(long patient) {
        this.patient = patient;
    }

    public long getDoctor() {
        return doctor;
    }

    public void setDoctor(long doctor) {
        this.doctor = doctor;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
