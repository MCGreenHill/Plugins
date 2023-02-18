package net.project.gen.Commands;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.project.gen.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class placegen implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        JsonArray objecktList = Main.objecktList;

        switch (args[0].toLowerCase()) {
            case "block":
                if (args.length == 1) {player.sendMessage("zu wenig argumente"); return false;}
                if (args.length == 2) {
                    if (Material.matchMaterial(args[1].toUpperCase()) == null) {
                        player.sendMessage("angabe ist kein block");
                        return false;
                    }

                    if (Objects.requireNonNull(Material.matchMaterial(args[1].toUpperCase())).isBlock()) {
                        Material material = Material.getMaterial(args[1]);
                        Location location = player.getLocation();
                        int x = location.getBlockX();
                        int y = location.getBlockY();
                        int z = location.getBlockZ();
                        if (location.getBlock().getType() == Material.AIR) {
                            JsonObject genList = new JsonObject();
                            genList.addProperty("x", String.valueOf(x));
                            genList.addProperty("y", String.valueOf(y));
                            genList.addProperty("z", String.valueOf(z));

                            genList.addProperty("material", String.valueOf(material));
                            genList.addProperty("cooldown", 0);
                            genList.addProperty("countdown", Boolean.FALSE);
                            genList.addProperty("player", "");
                            player.sendMessage(x + " " + y + " " + z + " " + args[1]);

                            objecktList.add(genList);
                            location.getWorld().getBlockAt(location).setType(material);
                            location.getWorld().spawnParticle(Particle.COMPOSTER, location.add(0, 1, 0), 25);
                            break;
                        } else player.sendMessage("setzen durch block blockirt");
                    } else player.sendMessage("nicht richtig");
                } else player.sendMessage("zu kurtz oder lang");

                if (args.length == 3) {
                    try {
                        int cooldown = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        player.sendMessage("cooldown ist eine zahl");
                        break;
                    }
                    if (!(Integer.getInteger(args[2]) < 0)) {
                        if (Objects.requireNonNull(Material.matchMaterial(args[1].toUpperCase())).isBlock()) {
                            Material material = Material.getMaterial(args[1]);
                            Location location = player.getLocation();
                            int x = location.getBlockX();
                            int y = location.getBlockY();
                            int z = location.getBlockZ();
                            if (location.getBlock().getType() == Material.AIR) {
                                JsonObject genList = new JsonObject();
                                genList.addProperty("x", String.valueOf(x));
                                genList.addProperty("y", String.valueOf(y));
                                genList.addProperty("z", String.valueOf(z));
                                genList.addProperty("countdown", Boolean.FALSE);

                                genList.addProperty("material", String.valueOf(material));
                                genList.addProperty("cooldown", args[2]);
                                genList.addProperty("player", "");

                                player.sendMessage(x + " " + y + " " + z + " " + args[1]);

                                objecktList.add(genList);
                                location.getWorld().getBlockAt(location).setType(material);
                                location.getWorld().spawnParticle(Particle.COMPOSTER, location.add(0, 1, 0), 25);
                                break;
                            } else player.sendMessage("setzen durch block blockirt");
                        } else player.sendMessage("nicht richtig");
                    } else player.sendMessage("keine negativ zahlen");
                }
                break;

            case "list":
                if (args.length == 2) {

                    if (args[1].equalsIgnoreCase("stonelist") || args[1].equalsIgnoreCase("woodlist") || args[1].equalsIgnoreCase("corallist")) {

                        Location location = player.getLocation();
                        int x = location.getBlockX();
                        int y = location.getBlockY();
                        int z = location.getBlockZ();
                        if (location.getBlock().getType() == Material.AIR) {
                            JsonObject genList = new JsonObject();
                            genList.addProperty("x", String.valueOf(x));
                            genList.addProperty("y", String.valueOf(y));
                            genList.addProperty("z", String.valueOf(z));

                            genList.addProperty("material", args[1]);
                            genList.addProperty("cooldown", 0);
                            genList.addProperty("countdown", Boolean.FALSE);
                            genList.addProperty("player", "");

                            player.sendMessage("set " + args[1] + " at " + x + " " + y + " " + z);

                            if (args[1].equalsIgnoreCase("stonelist")) {

                                location.getWorld().getBlockAt(location).setType(Material.STONE);
                            } else if (args[1].equalsIgnoreCase("woodlist")) {

                                location.getWorld().getBlockAt(location).setType(Material.OAK_LOG);
                            } else if (args[1].equalsIgnoreCase("coralList")) {
                                location.getWorld().getBlockAt(location).setType(Material.FIRE_CORAL_BLOCK);
                            }

                            objecktList.add(genList);
                            location.getWorld().spawnParticle(Particle.COMPOSTER, location.add(0, 1, 0), 50);

                        } else player.sendMessage("setzen durch block blockirt");
                    } else player.sendMessage("nicht richtig");
                    break;
                }

                if (args.length == 3) {
                    try {
                        int cooldown = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        player.sendMessage("cooldown ist keine zahl");
                        break;
                    }
                    if (!(Integer.getInteger(args[2]) < 0)) {
                        if (Objects.requireNonNull(Material.matchMaterial(args[1].toUpperCase())).isBlock()) {
                            Material material = Material.getMaterial(args[1]);
                            Location location = player.getLocation();
                            int x = location.getBlockX();
                            int y = location.getBlockY();
                            int z = location.getBlockZ();
                            if (location.getBlock().getType() == Material.AIR) {
                                JsonObject genList = new JsonObject();
                                genList.addProperty("x", String.valueOf(x));
                                genList.addProperty("y", String.valueOf(y));
                                genList.addProperty("z", String.valueOf(z));
                                genList.addProperty("countdown", Boolean.FALSE);
                                genList.addProperty("material", String.valueOf(material));
                                genList.addProperty("cooldown", Integer.getInteger(args[2]));
                                genList.addProperty("player", "");

                                player.sendMessage(x + " " + y + " " + z + " " + args[1]);

                                objecktList.add(genList);
                                location.getWorld().getBlockAt(location).setType(material);
                                location.getWorld().spawnParticle(Particle.COMPOSTER, location.add(0, 1, 0), 50);
                                break;
                            } else player.sendMessage("setzen durch block blockirt");
                        } else player.sendMessage("nicht richtig");
                    } else player.sendMessage("keine negativ zahlen");
                } else player.sendMessage("zu kurtz oder lang");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + args[0].toLowerCase());
        }

        return false;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> arguments = new ArrayList<String>();

        if (args.length == 1) {
            arguments.clear();
            arguments.add("Block");
            arguments.add("List");
            return arguments;
        } else {
            switch (args[0].toLowerCase()) {
                case "block":
                    if (args.length == 2) {
                        arguments.clear();
                        for (Material mat : Material.values()) {
                            if (mat.isBlock()) {
                                arguments.add(mat.toString());
                            }
                        }
                    }
                    List<String> result = new ArrayList<String>();
                    if (args.length == 2) {
                        result.clear();
                        for (String a : arguments) {
                            if (a.toLowerCase().startsWith(args[1].toLowerCase())) {
                                result.add(a);
                            }
                        }
                    }
                    return result;
                case "list":
                    arguments.clear();
                    arguments.add("stoneList");
                    arguments.add("woodList");
                    arguments.add("coralList");
                    return arguments;

            }
        }

        return null;
    }


}





