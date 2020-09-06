package com.haulmont.testtask.dao;

import com.haulmont.testtask.pojo.Patient;

import java.util.List;

public interface DaoPatient {

    List<Patient> getAllPatient();

    boolean setPatient(Patient recipe);

    boolean updatePatientByID(Patient patient);

    boolean deletePatientByID(long id);
}
