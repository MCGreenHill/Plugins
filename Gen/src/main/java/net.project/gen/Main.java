package feier68.gen;


import com.google.gson.*;
import feier68.gen.Commands.getSpawnGen;
import feier68.gen.Commands.placegen;
import feier68.gen.Commands.reloadgen;
import feier68.gen.Commands.removegen;
import feier68.gen.Listener.BlockBreak;
import feier68.gen.Listener.PistonEvent;
import feier68.gen.Listener.genSpawn;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


public final class Main extends JavaPlugin implements Listener {

    public static JsonArray objecktList = new JsonArray();
    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    public static void setPlugin(Main plugin) {
        Main.plugin = plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        setPlugin(this);
        File file = new File("plugins/Gen", "Gens.json");
        File file1 = new File("plugins/Gen");
        JsonParser parser = new JsonParser();
        Path path = Paths.get(file.getParent());


        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!(file.length() == 0)) {
            try (FileReader reader = new FileReader(file)) {
                JsonElement element = parser.parse(reader);
                JsonArray array = element.getAsJsonArray();
                objecktList = array;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("placegen")).setExecutor(new placegen());
        Objects.requireNonNull(getCommand("placegen")).setTabCompleter(new placegen());
        Objects.requireNonNull(getCommand("removegen")).setExecutor(new removegen());
        Objects.requireNonNull(getCommand("removegen")).setTabCompleter(new removegen());
        Objects.requireNonNull(getCommand("genitem")).setExecutor(new getSpawnGen());
        Objects.requireNonNull(getCommand("genitem")).setTabCompleter(new getSpawnGen());
        Objects.requireNonNull(getCommand("reloadgen")).setExecutor(new reloadgen());

        this.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        this.getServer().getPluginManager().registerEvents(new genSpawn(), this);
        this.getServer().getPluginManager().registerEvents(new PistonEvent(), this);
    }

    @Override
    public void onDisable() {
        Gson gson = new Gson();
        Path path = Paths.get("plugins//Gen/Gens.json");
        File file = path.toFile();

        for (int i = 0; i < objecktList.size(); i++) {
            JsonObject gen = objecktList.get(i).getAsJsonObject();
            gen.addProperty("countdown", Boolean.FALSE);
        }


        try (FileWriter writer = new FileWriter(file)) {
            JsonArray newArray = gson.toJsonTree(objecktList).getAsJsonArray();
            writer.write(gson.toJson(newArray));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
