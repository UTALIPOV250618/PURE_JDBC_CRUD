package util;

import java.sql.*;

public class Util {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String username = "postgres";
    private static final String password = "postgres";

    /**
     * There should be a method to connect with DB
     */
    public static Connection connection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successfully executed to DB");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
               /** CREATE Method*/
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS developers" +
                "(id SERIAL PRIMARY KEY NOT NULL, " +
                "name varchar(50) NOT NULL," +
                "surname varchar(50) NOT NULL)";
        try {
            Connection connection = connection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


                   /** Add new user Method*/
    public static void addUser(String name, String surname) {
        String sql = "INSERT INTO developers(name,surname) values(?,?)";
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.executeUpdate();
            System.out.println("New user added successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
                /** Get All Users Method*/
    public static void getAllUsers() {
        String sql = "SELECT*FROM developers";
        try (Connection connection = connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)
        ) {
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " " + resultSet.getString("name") + " " + resultSet.getString("surname"));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


                   /** Get Users by ID Method*/
    public static void getUserById(int id) {
        String sql = "SELECT * FROM developers WHERE Id =?";
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ) {

            /** Parametreyi figurnaya skopkanin icine yazmak gerekiyor  */

            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " " +
                        resultSet.getString("name") +
                        resultSet.getString("surname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
                /** Delete users from Table Method*/
    public static void deleteUserById(int id) {
        String sql = "DELETE FROM developers WHERE id=?";
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

                        /** Update users name Method*/
    public static void updateUserName(int id,String name){
        String sql = "UPDATE Developers SET name =? "+"WHERE id =?";
        try(Connection connection  = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            System.out.println("Changes executed successfully");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}

