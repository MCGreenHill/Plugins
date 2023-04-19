package net.project.commands;

import net.project.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import static net.project.guis.shopGui.itemlist;

public class addItemToShop implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("wie viel soll das item kosten");
            return false;
        } else if (args.length == 1) {
            try {
                Double price = Double.parseDouble(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage("angabe ist keine zahl");
                return false;
            }
            char ue = '\u00FC';
            if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
                ItemStack hand = player.getInventory().getItemInMainHand().clone();
                ItemMeta meta = hand.getItemMeta();
                PersistentDataContainer data = meta.getPersistentDataContainer();

                data.set(new NamespacedKey(Main.getPlugin(), "price"), PersistentDataType.DOUBLE, Double.parseDouble(args[0]));
                data.set(new NamespacedKey(Main.getPlugin(), "size"), PersistentDataType.INTEGER, 1);
                hand.setItemMeta(meta);
                itemlist.add(hand);
                if (meta.getDisplayName().isEmpty()) {
                    player.sendMessage(" Item wird f" + ue + "r " + args[0] + " Verkauft");
                } else
                    player.sendMessage(meta.getDisplayName() + " wird f" + ue + "r " + args[0] + " Verkauft");
            } else player.sendMessage("du hast nix in der hand");

        } else if (args.length == 2) {
            char ss = '\u00DF';
            char oe = '\u00F6';

            try {
                int size = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage("2te angabe ist keine zahl");
                return false;
            }
            if (Integer.parseInt(args[1]) <= 0) {
                player.sendMessage("du kanst nicht 0 oder weniger items verkaufen");
            } else if (Integer.parseInt(args[1]) <= 64) {
                try {
                    Double price = Double.parseDouble(args[0]);
                } catch (NumberFormatException e) {
                    player.sendMessage("1te angabe ist keine zahl");
                    return false;
                }
                char ue = '\u00FC';
                if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
                    ItemStack hand = player.getInventory().getItemInMainHand().clone();
                    ItemMeta meta = hand.getItemMeta();
                    PersistentDataContainer data = meta.getPersistentDataContainer();

                    data.set(new NamespacedKey(Main.getPlugin(), "price"), PersistentDataType.DOUBLE, Double.parseDouble(args[0]));
                    data.set(new NamespacedKey(Main.getPlugin(), "size"), PersistentDataType.INTEGER, Integer.parseInt(args[1]));

                    hand.setItemMeta(meta);
                    hand.setAmount(Integer.parseInt(args[1]));
                    itemlist.add(hand);
                    if (meta.getDisplayName().isEmpty()) {
                        player.sendMessage(Integer.parseInt(args[1]) + " Item wird f" + ue + "r " + args[0] + " Verkauft");
                    } else
                        player.sendMessage(Integer.parseInt(args[1]) + meta.getDisplayName() + " wird f" + ue + "r " + args[0] + " Verkauft");

                } else player.sendMessage("du hast nix in der hand");
            } else player.sendMessage("kann nicht gr" + oe + ss + "er als 64 sein");
        } else if (args.length > 2) player.sendMessage("zu viele argumente");

        return false;
    }
}
