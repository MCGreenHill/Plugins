package net.gigaclub.skycoinsystem.data;

import net.gigaclub.skycoinsystem.SkyCoinSystem;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;

public class Vault {
    private static VaultEconomy econ;

    public static boolean setupVault() {
        if (SkyCoinSystem.getPlugin().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            econ = SkyCoinSystem.getEconomyImplementer();
            Bukkit.getServicesManager().register(Economy.class, econ, SkyCoinSystem.getPlugin(), ServicePriority.Normal);
            return econ != null;
        }
    }

    public static boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = SkyCoinSystem.getPlugin().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        SkyCoinSystem.setEconomy(rsp.getProvider());
        return SkyCoinSystem.getEconomy() != null;
    }

    public static double getBalance(Player player) {
        return SkyCoinSystem.getEconomy().getBalance(player);
    }

    public static void addMoney(Player player, double amount) {
        SkyCoinSystem.getEconomy().depositPlayer(player, amount);
    }

    public static void remMoney(Player player, double amount) {
        SkyCoinSystem.getEconomy().withdrawPlayer(player, amount);
    }
}
