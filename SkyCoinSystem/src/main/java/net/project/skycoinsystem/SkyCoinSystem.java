package net.project.skycoinsystem;

import net.project.api.ProjectPlugin;
import net.project.skycoinsystem.data.Vault;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;


@Plugin(name = "SkyCoinSystemPlugin", version = "${version}")
@Dependency("Vault")
@ApiVersion(ApiVersion.Target.v1_19)
public final class SkyCoinSystem extends ProjectPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        Vault.setupVault();
        if (!Vault.setupEconomy()) {
            System.out.println((String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName())));
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
