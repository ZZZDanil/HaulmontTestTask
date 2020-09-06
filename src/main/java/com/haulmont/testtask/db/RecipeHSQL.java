package com.haulmont.testtask.db;

import com.haulmont.testtask.dao.DaoRecipe;
import com.haulmont.testtask.pojo.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RecipeHSQL implements DaoRecipe {

    private Connection connection;
    private Statement stat;
    private static ResourceBundle properties = ResourceBundle.getBundle("resource");

    @Override
    public List<Recipe> getAllRecipe() {
        try {
            connection = DriverManager.getConnection(properties.getString("db.url"),
                    properties.getString("db.user"),
                    properties.getString("db.password"));
            stat = connection.createStatement();
            String getAll = "SELECT * FROM recipes";
            List<Recipe> result = new ArrayList<>();
            ResultSet sel = null;
            sel = stat.executeQuery(getAll);

            while(sel.next()){
                Long id = sel.getLong(1);
                String about = sel.getString(2);
                long patient = sel.getLong(3);
                long doctor = sel.getLong(4);
                String createDate = sel.getString(5);
                String duration = sel.getString(6);
                String priority = sel.getString(7);
                result.add(new Recipe(id, about, patient, doctor, createDate, duration, priority));
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
    public boolean setRecipe(Recipe recipe) {
        try {
            initConnection();
            String getAll = "insert into recipes(about, patient, doctor, create_date, duration_days, priority) " +
                    "values('"+ recipe.getAbout() +"',"+ recipe.getPatient()+","
                    + recipe.getDoctor()+", '"+ recipe.getCreateDate()+"','"
                    + recipe.getDuration()+"', '"+ recipe.getPriority()+"');";
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
    public boolean updateRecipeByID(Recipe recipe) {
        try {
            initConnection();
            String del = "update recipes set about = '"+ recipe.getAbout()
                    +"', patient = "+ recipe.getPatient()
                    +", doctor = "+ recipe.getDoctor()
                    +", create_date = '"+ recipe.getCreateDate()
                    +"', duration_days = '"+ recipe.getDuration()
                    +"', priority = '" + recipe.getPriority()+"' where id = "+ recipe.getId() +";";
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
    public boolean deleteRecipeByID(long id) {
        try {
            initConnection();
            String del = "delete from recipes where id = "+ id +";";
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
