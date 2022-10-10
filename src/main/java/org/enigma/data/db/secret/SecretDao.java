package org.enigma.data.db.secret;

import org.enigma.data.db.AppDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class SecretDao {
    private AppDatabase db;

    public SecretDao() {
        try {
            db = AppDatabase.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkTheTable(){
        try {
            Statement statement = db.getConnection().createStatement();
            String quarry = "CREATE TABLE IF NOT EXISTS Secret(uuid uuid, encrypted_secret character large object, password character large object, time_to_burn bigint, show_and_burn boolean, number_of_reads integer, need_password boolean)";
            statement.execute(quarry);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void putSecret(SecretEntity secretEntity) {
        checkTheTable();
        String query = "INSERT INTO Secret(`uuid`, `encrypted_secret`, `password`, `time_to_burn`, `show_and_burn`, `number_of_reads`, `need_password`)" + "VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
            statement.setObject(1, secretEntity.getUuid());
            statement.setObject(2, secretEntity.getEncryptedSecret());
            statement.setObject(3, secretEntity.getPassword());
            statement.setObject(4, ResourceManagment.changeTime(secretEntity.getTimeToBurn()));
            statement.setObject(5, secretEntity.getShowAndBurn());
            statement.setObject(6, secretEntity.getNumberOfReads());
            statement.setObject(7, secretEntity.getNeedPassword());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SecretEntity getSecretById(Map<String, String> uuidAndPass) {
        checkTheTable();
        Map.Entry<String,String> entry = uuidAndPass.entrySet().iterator().next();
        String uuid = entry.getKey();
        String verifyPass = entry.getValue();
        String query = String.format("SELECT * FROM SECRET WHERE UUID='%s'", uuid);
        String queryUpd = String.format("UPDATE SECRET SET NUMBER_OF_READS = NUMBER_OF_READS - 1 WHERE UUID = '%s'",uuid);
        System.out.println("QUERY = " + query);
        SecretEntity result = null;
        try {
            Statement statement = db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String uuidDb = resultSet.getString("uuid");
                String encryptedSecret = resultSet.getString("encrypted_secret");
                String password = resultSet.getString("password");
                String timeToBurn = resultSet.getString("time_to_burn");
                Boolean showAndBurn = resultSet.getBoolean("show_and_burn");
                Boolean needPassword = resultSet.getBoolean("need_password");
                int numberOfReads = resultSet.getInt("number_of_reads");
                result = new SecretEntity(UUID.fromString(uuidDb), encryptedSecret, password, timeToBurn, showAndBurn, numberOfReads, needPassword);
                if (!ResourceManagment.checkPasswordOption(password, verifyPass, needPassword)) {
                    result = new SecretEntity(null, "", "", "", showAndBurn,0, needPassword);
                }
                else {
                    statement.executeUpdate(queryUpd);
                    ResourceManagment.checkNumberOfReads(numberOfReads, uuidDb);
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
