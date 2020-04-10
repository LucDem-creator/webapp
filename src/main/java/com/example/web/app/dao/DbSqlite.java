package com.example.web.app.dao;

import com.example.web.app.api.request.ListId;
import com.example.web.app.dao.model.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DbSqlite implements InitializingBean {
    private Logger log = Logger.getLogger(getClass().getName());

    private String dbPath = "webappp-example.db";

    @Override
    public void afterPropertiesSet() throws Exception {
        initDb();
    }

    public void initDb() {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            }
        } catch (ClassNotFoundException | SQLException ex) {
            log.log(Level.WARNING, "База не подключена", ex);
        }
    }

    public Boolean execute(String query) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            return stat.execute(query);
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return false;
        }
    }

    public User selectUserById(int id) {
        String query = "select * from USER where id = " + id;
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setNumberPhone(resultSet.getString("phone_number"));
            user.setActivity(resultSet.getString("activity"));
            return user;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return new User();
        }
    }

    public Boolean createNewUser(User user) {
        StringBuffer query = new StringBuffer("insert into USER (name, birthday, phone_number, activity ) values ('");
        query.append(user.getName());
        query.append("','");
        query.append(user.getTimeBirthday());
        query.append("','");
        query.append(user.getNumberPhone());
        query.append("','");
        query.append(user.getActivity());
        query.append("');");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            return stat.execute(query.toString());
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось добавить пользователя", ex);
            return null;
        }
    }

    public ListId getAllUsersId() {
        String query = "select ID from USER";
        ListId listId = new ListId();
        List<Integer> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            while (resultSet.next()) {
                list.add(resultSet.getInt("ID"));
            }
            listId.setListId(list);
            return listId;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось получить список ID", ex);
            return new ListId();
        }
    }
}