package net.project.gen.Commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.project.gen.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class reloadgen implements CommandExecutor {

    JsonArray objecktList = Main.objecktList;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        File file = new File("plugins/Gen", "Gens.json");
        File file1 = new File("plugins/Gen");
        JsonParser parser = new JsonParser();
        Path path = Paths.get(file.getParent());

        JsonArray objecktList1 = new JsonArray();
        objecktList = objecktList1;
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
                Main.objecktList = array;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
