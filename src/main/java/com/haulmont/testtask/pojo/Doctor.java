package com.haulmont.testtask.pojo;

import java.io.Serializable;

public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String specialisation;

    public Doctor(Long id, String name, String surname, String patronymic, String specialisation) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.specialisation = specialisation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }
}
