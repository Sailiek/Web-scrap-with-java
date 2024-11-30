package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbConnection {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/WebScrap";
        String username = "root";
        String password = "";


        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Connected to the database!");

            
            String sql = "SELECT * FROM offre";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("Id"));
                    System.out.println("Titre: " + resultSet.getString("Titre"));
                    System.out.println("Location: " + resultSet.getString("Localisation"));
                    System.out.println("Contrat: " + resultSet.getString("Contrat"));
                    System.out.println("Niveau d'étude: " + resultSet.getString("Niveau_Etude"));
                    System.out.println("Eperience: " + resultSet.getString("Eperience"));
                    System.out.println("-------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

