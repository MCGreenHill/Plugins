package net.project.gen.Commands;


import de.unpixelt.locale.Translate;
import net.project.api.ProjectPlayer;
import net.project.gen.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class getSpawnGen implements CommandExecutor, TabCompleter {
    public static ItemStack GenPlaceEgg = new ItemStack(Material.BAT_SPAWN_EGG, 1);
    public static ItemStack GenRemoveEgg = new ItemStack(Material.CHICKEN_SPAWN_EGG, 1);

    public static ItemStack createGenPlaceEgg(String material, int cooldown, ResourceBundle bundle, Player player) {
        ItemStack item = new ItemStack(Material.BAT_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        Material material1 = Material.matchMaterial(material);

        meta.setDisplayName(bundle.getString("gen.command.genitem.name"));
        List<String> lore = new ArrayList<>();
        if (material1 != null) {
            String itemname = Translate.getMaterial(player, material1);
            lore.add(String.format(bundle.getString("gen.command.genitem.lore1"), itemname));
        } else lore.add(String.format(bundle.getString("gen.command.genitem.lore1"), material));

        lore.add(bundle.getString("gen.command.genitem.lore2"));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 0, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(Main.getPlugin(), "cooldown"), PersistentDataType.INTEGER, cooldown);
        data.set(new NamespacedKey(Main.getPlugin(), "Material"), PersistentDataType.STRING, String.valueOf(material));
        data.set(new NamespacedKey(Main.getPlugin(), "Gen"), PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);
        GenPlaceEgg = item;
        return item;
    }
    public static ItemStack createGenRemoveEgg(ResourceBundle bundle) {
        ItemStack item = new ItemStack(Material.CHICKEN_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(bundle.getString("gen.command.genitemremove.name"));
        List<String> lore = new ArrayList<>();
        lore.add(bundle.getString("gen.command.genitemremove.lore1"));
        lore.add(bundle.getString("gen.command.genitemremove.lore2"));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(Main.getPlugin(), "Gen"), PersistentDataType.INTEGER, 0);
        item.setItemMeta(meta);
        GenRemoveEgg = item;
        return item;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        if (!(player instanceof Player)) {
            return false;
        }

        ProjectPlayer player1 = new ProjectPlayer(player);
        Locale playerLocale = player1.getProjectLocale();
        ResourceBundle bundle = Main.getMessagesBundle(playerLocale);

        switch (args[0].toLowerCase()) {
            case "block":
                if (args.length == 1) {
                    player.sendMessage("zu wenig argumente");
                    return false;
                }
                if (args.length == 2) {
                    @NotNull String material;

                    if (Material.matchMaterial(args[1].toUpperCase()) == null) {
                        player.sendMessage("angabe ist kein block");
                        return false;
                    }

                    if (Objects.requireNonNull(Material.matchMaterial(args[1].toUpperCase())).isBlock()) {
                        material = String.valueOf(Material.getMaterial(args[1].toUpperCase()));
                    } else return false;

                    createGenPlaceEgg(material, 0, bundle, player);
                    ItemStack item = GenPlaceEgg;
                    if (!(player.getInventory().firstEmpty() <= -1)) {
                        int i = player.getInventory().firstEmpty();

                        player.getInventory().setItem(i, item);
                        break;
                    } else player.sendMessage("kein slot frei");
                }
                if (args.length == 3) {
                    int cooldown;
                    @NotNull String material;
                    try {
                        cooldown = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        player.sendMessage("cooldown ist keine zahl");
                        break;
                    }

                    if (!(cooldown < 0)) {
                        if (Objects.requireNonNull(Material.matchMaterial(args[1].toUpperCase())).isBlock()) {
                            material = String.valueOf(Material.getMaterial(args[1].toUpperCase()));
                        } else return false;

                        createGenPlaceEgg(material, cooldown, bundle, player);
                        ItemStack item = GenPlaceEgg;
                        if (!(player.getInventory().firstEmpty() <= -1)) {
                            int i = player.getInventory().firstEmpty();

                            player.getInventory().setItem(i, item);
                            break;
                        } else player.sendMessage("kein slot frei");
                    } else player.sendMessage("keine negativ zahlen");
                    break;
                }
            case "list":
                if (args.length == 1) {
                    player.sendMessage("zu wenig argumente");
                    return false;
                }
                if (args.length == 2) {
                    @NotNull String material1;
                    if (!(player.getInventory().firstEmpty() <= -1)) {
                        int i = player.getInventory().firstEmpty();

                        if (args[1].toLowerCase().equals("stonelist")) {
                            material1 = "stoneList";
                            createGenPlaceEgg(material1, 0, bundle, player);
                            ItemStack item = GenPlaceEgg;
                            player.getInventory().setItem(i, item);
                            break;
                        } else if (args[1].toLowerCase().equals("woodlist")) {
                            material1 = "woodList";
                            createGenPlaceEgg(material1, 0, bundle, player);
                            ItemStack item = GenPlaceEgg;
                            player.getInventory().setItem(i, item);
                            break;
                        }

                    } else player.sendMessage("kein slot frei");
                    break;
                }
                if (args.length == 3) {
                    int cooldown;
                    try {
                        cooldown = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        player.sendMessage("cooldown ist eine zahl");
                        break;
                    }
                    if (!(cooldown < 0)) {
                        @NotNull String material1;
                        if (!(player.getInventory().firstEmpty() <= -1)) {
                            int i = player.getInventory().firstEmpty();

                            if (args[1].toLowerCase().equals("stonelist")) {
                                material1 = "stoneList";
                                createGenPlaceEgg(material1, cooldown, bundle, player);
                                ItemStack item = GenPlaceEgg;
                                player.getInventory().setItem(i, item);
                                break;
                            } else if (args[1].toLowerCase().equals("woodlist")) {
                                material1 = "woodList";
                                createGenPlaceEgg(material1, cooldown, bundle, player);
                                ItemStack item = GenPlaceEgg;
                                player.getInventory().setItem(i, item);
                                break;
                            }

                        } else player.sendMessage("kein slot frei");
                    } else player.sendMessage("keine negativ zahlen");
                    break;
                }
            case "remove":
                if (args.length == 1) {
                    if (!(player.getInventory().firstEmpty() <= -1)) {
                        int i = player.getInventory().firstEmpty();
                        createGenRemoveEgg(bundle);
                        ItemStack item = GenRemoveEgg;
                        player.getInventory().setItem(i, item);


                    } else player.sendMessage("kein slot frei");
                }

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
            arguments.add("Remove");
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

                case "remove":
                    arguments.clear();
                    return arguments;
            }
        }


        return null;
    }


}
