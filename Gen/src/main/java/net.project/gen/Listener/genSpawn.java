package feier68.gen.Listener;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import feier68.gen.Commands.getSpawnGen;
import feier68.gen.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


public class genSpawn implements Listener {

    JsonArray objecktList = Main.objecktList;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getHand() != null && event.getHand().equals(EquipmentSlot.HAND)) {
                ItemStack item = event.getItem();
                if (item != null && item.getItemMeta() != null && item.getItemMeta().getLore() != null) {

                    ItemMeta meta = item.getItemMeta();
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    @Nullable Integer gen = data.get(new NamespacedKey(Main.getPlugin(), "Gen"), PersistentDataType.INTEGER);


                    if (gen == 1) {
                        String genmaterial = data.get(new NamespacedKey(Main.getPlugin(), "Material"), PersistentDataType.STRING);
                        @Nullable Integer cooldown = data.get(new NamespacedKey(Main.getPlugin(), "cooldown"), PersistentDataType.INTEGER);

                        Location spawnLocation;
                        event.setCancelled(true);
                        if (Objects.equals(genmaterial, "stoneList")) {
                            String material = "stoneList";

                            if (event.getClickedBlock().isPassable()) {

                                spawnLocation = event.getClickedBlock().getLocation();
                                setGen(spawnLocation.add(0.5, 0, 0.5), material, cooldown, event.getPlayer());
                                spawnLocation.getWorld().spawnParticle(Particle.COMPOSTER, spawnLocation.add(0, 1, 0), 50);

                            } else {

                                spawnLocation = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation();
                                setGen(spawnLocation, material, cooldown, event.getPlayer());
                                spawnLocation.getWorld().spawnParticle(Particle.COMPOSTER, spawnLocation.add(0, 1, 0), 50);
                            }

                        } else if (Objects.equals(genmaterial, "woodList")) {
                            String material = "woodList";
                            if (event.getClickedBlock().isPassable()) {

                                spawnLocation = event.getClickedBlock().getLocation();
                                setGen(spawnLocation.add(0.5, 0, 0.5), material, cooldown, event.getPlayer());
                                spawnLocation.getWorld().spawnParticle(Particle.COMPOSTER, spawnLocation.add(0, 1, 0), 50);

                            } else {

                                spawnLocation = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation();
                                setGen(spawnLocation, material, cooldown, event.getPlayer());
                                spawnLocation.getWorld().spawnParticle(Particle.COMPOSTER, spawnLocation.add(0, 1, 0), 50);
                            }
                            if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                                item.setAmount(item.getAmount() - 1);
                            }

                        } else if (Objects.equals(genmaterial, "coralList")) {
                            String material = "coralList";
                            if (event.getClickedBlock().isPassable()) {

                                spawnLocation = event.getClickedBlock().getLocation();
                                setGen(spawnLocation.add(0.5, 0, 0.5), material, cooldown, event.getPlayer());
                                spawnLocation.getWorld().spawnParticle(Particle.COMPOSTER, spawnLocation.add(0, 1, 0), 50);

                            } else {

                                spawnLocation = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation();
                                setGen(spawnLocation, material, cooldown, event.getPlayer());
                                spawnLocation.getWorld().spawnParticle(Particle.COMPOSTER, spawnLocation.add(0, 1, 0), 50);
                            }

                            if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                                item.setAmount(item.getAmount() - 1);
                            }

                        } else if (Material.getMaterial(genmaterial).isBlock()) {
                            String material = genmaterial;
                            if (event.getClickedBlock().isPassable()) {

                                spawnLocation = event.getClickedBlock().getLocation();
                                setGen(spawnLocation.add(0.5, 0, 0.5), material, cooldown, event.getPlayer());
                                spawnLocation.getWorld().spawnParticle(Particle.COMPOSTER, spawnLocation.add(0, 1, 0), 50);

                            } else {

                                spawnLocation = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation();
                                setGen(spawnLocation, material, cooldown, event.getPlayer());
                                spawnLocation.getWorld().spawnParticle(Particle.COMPOSTER, spawnLocation.add(0, 1, 0), 50);
                            }
                            if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                                item.setAmount(item.getAmount() - 1);
                            }
                        }


                    } else if (gen == 0) {

                        event.setCancelled(true);

                        Player player = event.getPlayer();
                        Location location = event.getClickedBlock().getLocation();
                        int x = location.getBlockX();
                        int y = location.getBlockY();
                        int z = location.getBlockZ();
                        for (int i = 0; i < objecktList.size(); i++) {
                            JsonObject gen2 = objecktList.get(i).getAsJsonObject();
                            if (x == gen2.get("x").getAsInt() && y == gen2.get("y").getAsInt() && z == gen2.get("z").getAsInt()) {
                                if (!player.hasPermission("gen.remove")) {
                                    if (gen2.get("player").getAsString().equals(player.getUniqueId().toString())) {
                                    } else {
                                        player.sendMessage("der Gen ist nicht deiner");
                                        return;
                                    }
                                }
                                player.sendMessage("Remove Gen");
                                location.getBlock().setType(Material.AIR);
                                location.getWorld().spawnParticle(Particle.LAVA, location.add(0.5, 0, 0.5), 80);
                                getSpawnGen.createGenPlaceEgg(gen2.get("material").getAsString(), gen2.get("cooldown").getAsInt());
                                Main.objecktList.remove(i);
                                ItemStack genPlaceEgg = getSpawnGen.GenPlaceEgg;
                                if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                                    item.setAmount(item.getAmount() - 1);
                                }

                                Bukkit.getScheduler().runTask(Main.getPlugin(), new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!(player.getInventory().firstEmpty() <= -1)) {
                                            int slot = player.getInventory().firstEmpty();
                                            if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                                                player.getInventory().setItem(slot, genPlaceEgg);
                                            }

                                        }
                                    }
                                });

                            } else if (i == objecktList.size()) {
                                player.sendMessage("kein gen");
                                break;
                            }

                        }
                    }
                }
            }
        }
    }

    private void setGen(Location location, String material, int cooldown, Player player) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        JsonObject genList = new JsonObject();
        genList.addProperty("x", String.valueOf(x));
        genList.addProperty("y", String.valueOf(y));
        genList.addProperty("z", String.valueOf(z));
        genList.addProperty("cooldown", cooldown);
        genList.addProperty("countdown", false);
        genList.addProperty("material", String.valueOf(material));
        genList.addProperty("player", String.valueOf(player.getUniqueId()));
        if (material.equalsIgnoreCase("stoneList")) {
            location.getWorld().getBlockAt(location).setType(Material.STONE);
        } else if (material.equalsIgnoreCase("woodList")) {
            location.getWorld().getBlockAt(location).setType(Material.OAK_LOG);
        } else if (Material.getMaterial(material).isBlock()) {
            Material material1 = Material.getMaterial(material);
            location.getWorld().getBlockAt(location).setType(material1);
        } else return;
        objecktList.add(genList);
    }
}