package net.project.gen.Commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.project.gen.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class removegen implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        World world = player.getWorld();
        JsonArray gens = Main.objecktList;

        if (args.length == 0) {
            Block lookblock;


            if (player.getTargetBlockExact(6) == null) {
                player.sendMessage("kein block in reichweite");
                return false;
            }

            lookblock = player.getTargetBlockExact(6);
            Location location = lookblock.getLocation();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();

            for (int i = 0; i < gens.size(); i++) {
                JsonObject gen = gens.get(i).getAsJsonObject();
                if (x == gen.get("x").getAsInt() && y == gen.get("y").getAsInt() && z == gen.get("z").getAsInt()) {
                    location.getBlock().setType(Material.AIR);
                    location.getWorld().spawnParticle(Particle.LAVA, location.add(0.5, 0, 0.5), 80);
                    Main.objecktList.remove(i);
                    break;
                }
            }


        } else if (args.length == 2) {
            player.sendMessage("less arguments");
        } else if (args.length == 3) {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);
            Location location = new Location(world, x, y, z);
            for (int i = 0; i < gens.size(); i++) {
                JsonObject gen = gens.get(i).getAsJsonObject();
                if (x == gen.get("x").getAsInt() && y == gen.get("y").getAsInt() && z == gen.get("z").getAsInt()) {
                    location.getBlock().setType(Material.AIR);
                    location.getWorld().spawnParticle(Particle.LAVA, location.add(0.5, 0, 0.5), 80);
                    Main.objecktList.remove(i);
                    break;
                } else if (i == gens.size()) player.sendMessage("block ist kein gen");
            }
            if (args.length >= 4) player.sendMessage("to mutch argument");


        }


        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (!(sender instanceof Player player)) {
            return completions;
        }

        int x = player.getLocation().getBlockX();
        int y = checkCoords(player.getLocation().getBlockY());
        int z = player.getLocation().getBlockZ();


        if (args.length == 1) {
            completions.add(x + " " + y + " " + z);
        } else if (args.length == 2) {
            completions.add(y + " " + z);
        } else if (args.length == 3) {
            completions.add(String.valueOf(z));
        }

        return completions;
    }

    private int checkCoords(int coord) {
        if (coord >= 0)
            return coord - 1;
        return coord + 1;
    }

}
