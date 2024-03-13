package me.barnaby.cauldronpoints.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonDataManager {

    private final JavaPlugin plugin;
    private final Gson gson;
    private final File dataFolder;

    public JsonDataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.dataFolder = new File(plugin.getDataFolder(), "json_data");

        // Create the data folder if it doesn't exist
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    public <T> T loadObject(String fileName, Class<T> type) {
        File file = new File(dataFolder, fileName);

        if (!file.exists()) {
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> void saveObject(String fileName, T object) {
        File file = new File(dataFolder, fileName);

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
