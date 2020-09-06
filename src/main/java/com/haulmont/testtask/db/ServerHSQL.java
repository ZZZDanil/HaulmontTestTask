package com.haulmont.testtask.db;

import org.hsqldb.server.Server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class ServerHSQL implements ServletContextListener {

    private ResourceBundle properties = ResourceBundle.getBundle("resource");
    private Connection connection;
    private Statement stat;
    private Server s;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        s = new Server();
        s.setDatabaseName(0, properties.getString("db.name"));
        s.setDatabasePath(0, properties.getString("db.path"));
        s.start();
        loadDriver();
        doStartSQL();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (s != null) {
            s.stop();
        }
    }

    private void loadDriver() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void doStartSQL() {
        System.out.println("start!!!");
        try {
            connection = DriverManager.getConnection(properties.getString("db.url"),
                    properties.getString("db.user"),
                    properties.getString("db.password"));
            stat = connection.createStatement();
            String path = Objects.requireNonNull(getClass().getClassLoader().getResource("start.sql")).getFile();
            path = path.substring(1).replace('\\', '/');
            path = path.replace("%20", " ");
            Path p = Paths.get(path);
            String sql = new String(Files.readAllBytes(p), StandardCharsets.UTF_8);
            partitionSQL(sql);
//            ResultSet sel = stat.executeQuery(
//                    "select concat(d.name, ' ', d.surname, ' ', d.patronymic), (SELECT COUNT(0) FROM recipes r WHERE r.doctor = d.id) AS recipes from doctors d ;");
//            while (sel.next()) {
//                String name = sel.getString(1);
//                Long count = sel.getLong(2);
//                System.out.println(name + ": " + count);
//            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stat.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    private void partitionSQL(String s) {
        String[] t = s.split(";");
        for (int i = 0; i < t.length - 1; i++) {
            try {
                System.out.println(i + "    " + t[i]);
                stat.execute(t[i]);
            } catch (SQLException e) {
                System.out.println("Error in: " + t[i]);
                e.printStackTrace();
            }
        }
    }

}
