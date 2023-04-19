package net.project.data;

import net.milkbowl.vault.economy.Economy;
import net.project.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;


public class Vault {

    public static boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Main.getPlugin().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        Main.setEconomy(rsp.getProvider());
        return Main.getEconomy() != null;
    }

    public static double getBalance(Player player) {
        return Main.getEconomy().getBalance(player);
    }

    public static void addMoney(Player player, double amount) {
        Main.getEconomy().depositPlayer(player, amount);
    }

    public static void remMoney(Player player, double amount) {
        Main.getEconomy().withdrawPlayer(player, amount);
    }
}
