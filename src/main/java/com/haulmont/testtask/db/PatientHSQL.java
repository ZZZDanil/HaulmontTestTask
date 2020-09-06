package com.haulmont.testtask.db;

import com.haulmont.testtask.dao.DaoPatient;
import com.haulmont.testtask.pojo.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PatientHSQL implements DaoPatient {


    private Connection connection;
    private Statement stat;
    private static ResourceBundle properties = ResourceBundle.getBundle("resource");

    @Override
    public List<Patient> getAllPatient() {
        try {
            initConnection();
            String getAll = "SELECT * FROM patients";
            List<Patient> result = new ArrayList<>();
            ResultSet sel = stat.executeQuery(getAll);

            System.out.println(sel);
            while(sel.next()){
                Long id = sel.getLong(1);
                String name = sel.getString(2);
                String surname = sel.getString(3);
                String patronymic = sel.getString(4);
                String phone = sel.getString(5);
                System.out.println(id + name + surname + patronymic + phone);
                result.add(new Patient(id, name, surname, patronymic, phone));
            }
            stat.close();
            return result;
        } catch (SQLException e) {e.printStackTrace(); return null;}finally {
            try {
                stat.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    @Override
    public boolean setPatient(Patient recipe) {
        try {
            initConnection();
            String getAll = "insert into patients(name, surname, patronymic, phone) " +
                    "values('"+ recipe.getName() +"','"
                    + recipe.getSurname()+"','"+ recipe.getPatronymic()
                    +"', '"+ recipe.getPhone()+"');";
            int row = stat.executeUpdate(getAll);
            System.out.println("add " + row + " row");
            return true;
        } catch (SQLException e) {e.printStackTrace(); return false;}finally {
            try {
                stat.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    @Override
    public boolean updatePatientByID(Patient patient) {
        try {
            initConnection();
            String del = "update patients set name = '"+ patient.getName()
                    +"', surname = '"+ patient.getSurname()
                    +"', patronymic = '"+ patient.getPatronymic()
                    +"', phone = '"+ patient.getPhone()
                    +"' where id = '"+ patient.getId() +"';";
            int row = stat.executeUpdate(del);
            System.out.println("update " + row + " row");
            return true;
        } catch (SQLException e) {e.printStackTrace(); return false;}
        finally {
            try {
                stat.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    @Override
    public boolean deletePatientByID(long id) {
        try {
            initConnection();
            String del = "delete from patients where id = "+ id +";";
            int row = stat.executeUpdate(del);
            System.out.println("delete " + row + " row");
            return true;
        } catch (SQLException e) {e.printStackTrace(); return false;}
        finally {
            try {
                stat.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
    }
    private void initConnection() {
        try {
            connection = DriverManager.getConnection(properties.getString("db.url"),
                    properties.getString("db.user"),
                    properties.getString("db.password"));
            stat = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
