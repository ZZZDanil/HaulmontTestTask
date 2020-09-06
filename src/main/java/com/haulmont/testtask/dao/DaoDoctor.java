package com.haulmont.testtask.dao;

import com.haulmont.testtask.pojo.Doctor;

import java.sql.SQLException;
import java.util.List;

public interface DaoDoctor {

    List<Doctor> getAllDoctor() throws SQLException;

    boolean setDoctor(Doctor recipe);

    boolean updateDoctorByID(Doctor doctor);

    boolean deleteDoctorByID(long id);

    List<List<String>> getStatisticOfDoctors();

}
