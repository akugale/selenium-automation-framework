package com.careir.automation.db;

import com.careir.automation.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Thin JDBC helper for SQL Server validations (post UI action or for test data setup).
 * Enable only when {@code db.enabled=true} and JDBC URL is configured.
 */
public final class DatabaseClient {

    private static final Logger LOG = LogManager.getLogger(DatabaseClient.class);

    private DatabaseClient() {
    }

    public static boolean isEnabled() {
        return ConfigReader.getBoolean("db.enabled", false);
    }

    public static Connection openConnection() throws Exception {
        if (!isEnabled()) {
            throw new IllegalStateException("DatabaseClient disabled (db.enabled=false)");
        }
        String url = ConfigReader.get("db.url");
        String user = ConfigReader.get("db.user");
        String password = ConfigReader.get("db.password", "");
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Runs a SELECT and returns rows as ordered maps (column label → value string).
     */
    public static List<Map<String, String>> query(String sql) {
        List<Map<String, String>> out = new ArrayList<>();
        try (Connection c = openConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            int cols = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 1; i <= cols; i++) {
                    String label = rs.getMetaData().getColumnLabel(i);
                    row.put(label, rs.getString(i));
                }
                out.add(row);
            }
        } catch (Exception e) {
            LOG.error("DB query failed: {}", sql, e);
            throw new RuntimeException(e);
        }
        return out;
    }
}
