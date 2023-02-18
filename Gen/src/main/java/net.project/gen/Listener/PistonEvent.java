package net.project.gen.Listener;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.project.gen.Main;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import java.util.List;

public class PistonEvent implements Listener {

    JsonArray gens = Main.objecktList;

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        List<Block> blockslist = event.getBlocks();

        for (int i = 0; i < blockslist.size(); i++) {
            Block block = blockslist.get(i);

            Location location = block.getLocation();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();

            for (int i2 = 0; i2 < gens.size(); i2++) {
                JsonObject gen = gens.get(i2).getAsJsonObject();
                if (x == gen.get("x").getAsInt() && y == gen.get("y").getAsInt() && z == gen.get("z").getAsInt()) {


                    event.setCancelled(true);
                    break;
                }
            }


        }
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        List<Block> blockslist = event.getBlocks();

        for (int i = 0; i < blockslist.size(); i++) {
            Block block = blockslist.get(i);

            Location location = block.getLocation();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();

            for (int i2 = 0; i2 < gens.size(); i2++) {
                JsonObject gen = gens.get(i2).getAsJsonObject();
                if (x == gen.get("x").getAsInt() && y == gen.get("y").getAsInt() && z == gen.get("z").getAsInt()) {
                    event.setCancelled(true);
                    break;
                }
            }


        }
    }

}
