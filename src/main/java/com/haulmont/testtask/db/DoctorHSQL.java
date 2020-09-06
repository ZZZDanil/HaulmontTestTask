package com.haulmont.testtask.db;

import com.haulmont.testtask.dao.DaoDoctor;
import com.haulmont.testtask.pojo.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DoctorHSQL implements DaoDoctor {

    private static ResourceBundle properties = ResourceBundle.getBundle("resource");
    private Connection connection;
    private Statement stat;

    @Override
    public List<Doctor> getAllDoctor() {
        try {
            initConnection();
            String getAll = "SELECT * FROM doctors";
            List<Doctor> result = new ArrayList<>();
            ResultSet sel = stat.executeQuery(getAll);
            while (sel.next()) {
                Long id = sel.getLong(1);
                String name = sel.getString(2);
                String surname = sel.getString(3);
                String patronymic = sel.getString(4);
                String specialisation = sel.getString(5);
                result.add(new Doctor(id, name, surname, patronymic, specialisation));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                stat.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    @Override
    public boolean setDoctor(Doctor recipe) {
        try {
            initConnection();
            String getAll = "insert into doctors(name, surname, patronymic, specialisation) " +
                    "values('" + recipe.getName() + "','"
                    + recipe.getSurname() + "','" + recipe.getPatronymic()
                    + "', '" + recipe.getSpecialisation() + "');";
            int row = stat.executeUpdate(getAll);
            System.out.println("add " + row + " row");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                stat.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    @Override
    public boolean updateDoctorByID(Doctor doctor) {
        try {
            initConnection();
            String del = "update doctors set name = '" + doctor.getName()
                    + "', surname = '" + doctor.getSurname()
                    + "', patronymic = '" + doctor.getPatronymic()
                    + "', specialisation = '" + doctor.getSpecialisation()
                    + "' where id = " + doctor.getId() + ";";
            int row = stat.executeUpdate(del);
            System.out.println("delete " + row + " row");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                stat.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    @Override
    public boolean deleteDoctorByID(long idd) {
        try {
            initConnection();
            String del = "delete from doctors where id = " + idd + ";";
            int row = stat.executeUpdate(del);
            System.out.println("delete " + row + " row");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                stat.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    @Override
    public List<List<String>> getStatisticOfDoctors() {
        try {
            initConnection();
            String getStat =
                    "select concat(d.name, ' ', d.surname, ' ', d.patronymic), " +
                            "(SELECT COUNT(0) FROM recipes r WHERE r.doctor = d.id) AS recipes from doctors d ;";
            List<List<String>> result = new ArrayList<>();
            ResultSet sel = stat.executeQuery(getStat);
            while (sel.next()) {
                List<String> buf = new ArrayList<>();
                buf.add(sel.getString(1));
                buf.add(""+sel.getLong(2));
                System.out.println(buf);
                result.add(buf);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
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
