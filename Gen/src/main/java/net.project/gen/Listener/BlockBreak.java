package net.project.gen.Listener;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.project.gen.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BlockBreak implements Listener {

    public static String[] stoneList = {"STONE", "STONE", "STONE", "STONE", "STONE", "STONE", "DIORITE", "ANDESITE", "GRANITE", "CALCITE", "DEEPSLATE"};
    public static String[] woodList = {"OAK_LOG", "OAK_LOG", "OAK_LOG", "OAK_LOG", "OAK_LOG", "ACACIA_LOG", "BIRCH_LOG", "DARK_OAK_LOG", "JUNGLE_LOG", "MANGROVE_LOG", "SPRUCE_LOG"};
    public static String[] coralList = {"BRAIN_CORAL_BLOCK", "TUBE_CORAL_BLOCK", "BUBBLE_CORAL_BLOCK", "FIRE_CORAL_BLOCK", "HORN_CORAL_BLOCK"};
    public static String[] genList = {"stoneList", "woodList","coralList"};
    JsonArray objecktList = Main.objecktList;
    List<Integer> taskIds = new ArrayList<>();
    int counter = 0;

    @EventHandler
    public void handelBlockBreak(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();


        Bukkit.getServer().getWorld("world").getSpawnLocation();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();


        if (objecktList.isEmpty()) {
            return;
        }

        World world = location.getWorld();
        for (int i = 0; i < objecktList.size(); i++) {


            JsonObject gen = objecktList.get(i).getAsJsonObject();

            if (x == gen.get("x").getAsInt() && y == gen.get("y").getAsInt() && z == gen.get("z").getAsInt()) {
                if (gen.get("countdown").getAsBoolean()) {
                    event.setCancelled(true);
                    break;
                }
                if (!gen.get("countdown").getAsBoolean()) {


                    if (!(Material.getMaterial(gen.get("material").getAsString()) == null)) {
                        Bukkit.getScheduler().runTask(Main.getPlugin(), () -> event.getBlock().setType(Material.getMaterial(gen.get("material").getAsString()), false));
                        if (!(gen.get("cooldown").getAsInt() == 0)) {
                            countdown(location, Material.getMaterial(gen.get("material").getAsString()), gen.get("cooldown").getAsInt(), event, gen);
                        }
                        break;

                    }

                    for (int i1 = 0; i1 < genList.length; i1++) {

                        if (gen.get("material").getAsString().equalsIgnoreCase(genList[i1])) {
                            String listName = genList[i1];
                            Random random = new Random();
                            if (listName.equalsIgnoreCase("stoneList")) {

                                int r = random.nextInt(0, stoneList.length);
                                Material material = Material.getMaterial(stoneList[r]);
                                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> event.getBlock().setType(material));
                                if (!(gen.get("cooldown").getAsInt() == 0)) {
                                    countdown(location, material, gen.get("cooldown").getAsInt(), event, gen);
                                }
                                break;
                            } else if (listName.equalsIgnoreCase("woodList")) {

                                int r = random.nextInt(0, woodList.length);
                                Material material = Material.getMaterial(woodList[r]);
                                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> event.getBlock().setType(material));
                                if (!(gen.get("cooldown").getAsInt() == 0)) {
                                    countdown(location, material, gen.get("cooldown").getAsInt(), event, gen);
                                }
                                break;
                            } else if (listName.equalsIgnoreCase("coralList")) {
                                int r = random.nextInt(0, coralList.length);
                                Material material = Material.getMaterial(coralList[r]);
                                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> event.getBlock().setType(material));
                                if (!(gen.get("cooldown").getAsInt() == 0)) {
                                    countdown(location, material, gen.get("cooldown").getAsInt(), event, gen);
                                }
                                break;
                            } else break;
                        }
                    }
                }
            }
        }
    }

    public void countdown(Location location, Material material, int cooldown, BlockBreakEvent event, JsonObject gen) {

        int savedCounter = counter;

        int taskID;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            int countdown = cooldown;

            public void run() {
                if (!(location.getBlock().getType() == Material.WHITE_TERRACOTTA)) {
                    location.getBlock().setType(Material.WHITE_TERRACOTTA, false);
                }

                gen.addProperty("countdown", Boolean.TRUE);

                if (countdown <= 0) {
                    location.getBlock().setType(material, false);
                    gen.addProperty("countdown", Boolean.FALSE);
                    Bukkit.getScheduler().cancelTask(taskIds.get(savedCounter));
                }
                countdown--;
            }
        }, 0, 20);

        taskIds.add(counter, taskID);

        counter++;


    }

}