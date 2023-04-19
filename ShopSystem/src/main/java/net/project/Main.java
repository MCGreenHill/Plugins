package net.project;

import net.milkbowl.vault.economy.Economy;
import net.project.commands.addItemToShop;
import net.project.commands.shopOpen;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

import static net.project.data.Vault.setupEconomy;


public final class Main extends JavaPlugin {
    private static Main plugin;
    FileConfiguration config = getConfig();

    private static Economy economy;

    public static Economy getEconomy() {
        return economy;
    }

    public static void setEconomy(Economy economy) {
        Main.economy = economy;
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static void setPlugin(Main plugin) {
        Main.plugin = plugin;

    }

    @Override
    public void onEnable() {
        plugin = this;
        setPlugin(this);
        if (!setupEconomy()) {
            Logger.getLogger(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


        Objects.requireNonNull(getCommand("shop")).setExecutor(new shopOpen());
        Objects.requireNonNull(getCommand("adshopitem")).setExecutor(new addItemToShop());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
