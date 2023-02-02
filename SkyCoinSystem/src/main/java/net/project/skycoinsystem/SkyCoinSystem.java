package net.project.skycoinsystem;

import net.milkbowl.vault.economy.Economy;
import net.project.api.ProjectPlugin;
import net.project.skycoinsystem.commands.Balance;
import net.project.skycoinsystem.commands.Pay;
import net.project.skycoinsystem.data.Vault;
import net.project.skycoinsystem.data.VaultEconomy;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;

import java.util.Objects;


@Plugin(name = "SkyCoinSystemPlugin", version = "${version}")
@Dependency("Vault")
@ApiVersion(ApiVersion.Target.v1_19)
public final class SkyCoinSystem extends ProjectPlugin {

    private static VaultEconomy economyImplementer;
    private static Economy economy;

    public static VaultEconomy getEconomyImplementer() {
        return economyImplementer;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void setEconomyImplementer(VaultEconomy economyImplementer) {
        SkyCoinSystem.economyImplementer = economyImplementer;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static void setEconomy(Economy economy) {
        SkyCoinSystem.economy = economy;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        setEconomyImplementer(new VaultEconomy());
        if (!Vault.setupVault() || !Vault.setupEconomy()) {
            System.out.println((String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName())));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        registerCommands();
    }

    public void registerCommands() {
        Balance balance = new Balance();
        Pay pay = new Pay();
        Objects.requireNonNull(getCommand("balance")).setExecutor(balance);
        Objects.requireNonNull(getCommand("pay")).setExecutor(pay);
    }

}
