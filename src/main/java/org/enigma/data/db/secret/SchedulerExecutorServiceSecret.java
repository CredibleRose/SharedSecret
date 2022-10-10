package org.enigma.data.db.secret;

import org.enigma.data.db.AppDatabase;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.*;
import java.util.*;

public class SchedulerExecutorServiceSecret {
    private static ScheduledExecutorService scheduler;

    public static void startScheduler() throws IOException, InterruptedException {
        scheduler = Executors.newScheduledThreadPool(1);

        Runnable deleteTask = () -> {
            AppDatabase db;
            try {
                db = AppDatabase.getInstance();
                System.out.println("Delete secret started at: " + new Date());
                String deleteSecretQuery = String.format("DELETE FROM SECRET WHERE (SELECT TRUNCATE(EXTRACT (EPOCH from CURRENT_TIMESTAMP()) * 1000)) >= TIME_TO_BURN AND TIME_TO_BURN <> 0;");
                db.getConnection().createStatement().executeUpdate(deleteSecretQuery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        File file = new File(System.getProperty("user.home"), "rate.txt");
        Long rate = null;
        if (file.createNewFile()) {
            rate = 60l;
            PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
            writer.println(rate.toString());
            writer.close();
        } else {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLong()) {
                rate = scanner.nextLong();
            }
        }
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(deleteTask, 0, rate, TimeUnit.SECONDS);
    }

    public static void stopScheduler() throws IOException, InterruptedException{
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException ie) {
            scheduler.shutdownNow();
        }
    }
}



