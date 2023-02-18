package feier68.gen.Commands;

import feier68.gen.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class getSpawnGen implements CommandExecutor, TabCompleter {
    public static ItemStack GenPlaceEgg = new ItemStack(Material.BAT_SPAWN_EGG, 1);
    public static ItemStack GenRemoveEgg = new ItemStack(Material.CHICKEN_SPAWN_EGG, 1);

    public static ItemStack createGenPlaceEgg(String material, int cooldown) {
        ItemStack item = new ItemStack(Material.BAT_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        Material.getMaterial(material,true);
        meta.setDisplayName("gen name");
        List<String> lore = new ArrayList<>();
        lore.add(material + " Gen");
        lore.add("right click to place the generator");
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

    public static ItemStack createGenRemoveEgg() {
        ItemStack item = new ItemStack(Material.CHICKEN_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Remove Gen");
        List<String> lore = new ArrayList<>();
        lore.add("Remove Gen");
        lore.add("right click to Remove the generator");
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

        switch (args[0].toLowerCase()) {
            case "block":
                if(args.length == 1){
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

                    createGenPlaceEgg(material, 0);
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

                        createGenPlaceEgg(material, cooldown);
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
                if(args.length == 1){
                    player.sendMessage("zu wenig argumente");
                    return false;
                }
                if (args.length == 2) {
                    @NotNull String material1;
                    if (!(player.getInventory().firstEmpty() <= -1)) {
                        int i = player.getInventory().firstEmpty();

                        if (args[1].toLowerCase().equals("stonelist")) {
                            material1 = "stoneList";
                            createGenPlaceEgg(material1, 0);
                            ItemStack item = GenPlaceEgg;
                            player.getInventory().setItem(i, item);
                            break;
                        } else if (args[1].toLowerCase().equals("woodlist")) {
                            material1 = "woodList";
                            createGenPlaceEgg(material1, 0);
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
                                createGenPlaceEgg(material1, cooldown);
                                ItemStack item = GenPlaceEgg;
                                player.getInventory().setItem(i, item);
                                break;
                            } else if (args[1].toLowerCase().equals("woodlist")) {
                                material1 = "woodList";
                                createGenPlaceEgg(material1, cooldown);
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
                        createGenRemoveEgg();
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
