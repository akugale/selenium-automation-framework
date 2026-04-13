package com.careir.automation.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Loads JSON test payloads from the classpath (UTF-8).
 */
public final class JsonDataReader {

    private static final Logger LOG = LogManager.getLogger(JsonDataReader.class);
    private static final Gson GSON = new Gson();

    private JsonDataReader() {
    }

    public static JsonObject readObject(String classpathRelativePath) {
        try (InputStream in = open(classpathRelativePath);
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
            LOG.error("Failed to read JSON object {}", classpathRelativePath, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds a TestNG {@code Object[][]} from a JSON array of objects; each object's values are row cells in declaration order.
     */
    public static Object[][] readArrayAsTable(String classpathRelativePath) {
        try (InputStream in = open(classpathRelativePath);
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            JsonArray arr = JsonParser.parseReader(reader).getAsJsonArray();
            List<Object[]> rows = new ArrayList<>();
            for (JsonElement el : arr) {
                JsonObject o = el.getAsJsonObject();
                rows.add(new Object[]{GSON.toJson(o)});
            }
            return rows.toArray(new Object[0][]);
        } catch (Exception e) {
            LOG.error("Failed to read JSON table {}", classpathRelativePath, e);
            throw new RuntimeException(e);
        }
    }

    private static InputStream open(String classpathRelativePath) {
        return Objects.requireNonNull(
                JsonDataReader.class.getClassLoader().getResourceAsStream(classpathRelativePath),
                "Classpath resource not found: " + classpathRelativePath);
    }
}
