package org.enigma.data.db.secret;

import org.enigma.data.db.AppDatabase;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.time.ZonedDateTime;

public class ResourceManagment {
    private String password;
    private String verifyPassword;
    private boolean needPassword;
    private String time;
    private Integer numberOfReads;

    ResourceManagment(Integer numberOfReads,String password, String verifyPassword, boolean needPassword, String time) {
        this.password = password;
        this.verifyPassword = verifyPassword;
        this.needPassword = needPassword;
        this.time = time;
        this.numberOfReads = numberOfReads;
    }

    public static void checkNumberOfReads(Integer numberOfReads, String uuid){
        if (numberOfReads == 1){
            AppDatabase db;
            try {
                db = AppDatabase.getInstance();
                String queryDel = String.format("DELETE FROM SECRET WHERE UUID='%s'", uuid);
                db.getConnection().createStatement().executeUpdate(queryDel);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean checkPasswordOption(String password, String verifyPassword, boolean needPassword) {
        boolean flag;
        if (!needPassword) {
            flag = true;
        }
        else if (!verifyPassword.isEmpty()) {
            if (password.equals(verifyPassword)) {
                flag = true;

            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }
    public static long changeTime(String time) {
        long hour;
        switch(time) {
            case "1H":
                hour = 1;
                break;
            case "12H":
                hour = 12;
                break;
            case "1D":
                hour = 24;
                break;
            case "3D":
                hour = 72;
                break;
            case "1W":
                hour = 168;
                break;
            default:
                hour = 0;
                break;
        }
        if (hour == 0){
            return 0;
        }
        else{
        return ZonedDateTime.now().plus(hour, ChronoUnit.HOURS).toInstant().toEpochMilli();
        }
    }
}
