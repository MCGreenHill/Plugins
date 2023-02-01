package net.project.skycoinsystem.data;

import net.milkbowl.vault.economy.Economy;
import net.project.skycoinsystem.SkyCoinSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;

public class Vault {
    private static VaultEconomy econ = null;

    public static boolean setupVault() {
        if (SkyCoinSystem.getPlugin().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            Bukkit.getServicesManager().register(Economy.class, econ, SkyCoinSystem.getPlugin(), ServicePriority.Normal);
            return econ != null;
        }
    }

    public static boolean setupEconomy() {
        if (SkyCoinSystem.getPlugin().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<VaultEconomy> rsp = SkyCoinSystem.getPlugin().getServer().getServicesManager().getRegistration(VaultEconomy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static double getBalance(Player player) {
        return econ.getBalance(player);
    }

    public static void addMoney(Player player, double amount) {
        econ.depositPlayer(player, amount);
    }

    public static void remMoney(Player player, double amount) {
        econ.withdrawPlayer(player, amount);
    }
}
