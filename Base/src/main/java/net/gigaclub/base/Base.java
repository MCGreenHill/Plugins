package net.gigaclub.base;

import net.gigaclub.api.ProjectPlugin;
import net.gigaclub.base.commands.Lang;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;

import java.util.Objects;

@Plugin(name = "BasePlugin", version = "${version}")
@ApiVersion(ApiVersion.Target.v1_19)
public final class Base extends ProjectPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands() {
        Lang lang = new Lang();
        Objects.requireNonNull(getCommand("lang")).setExecutor(lang);
        Objects.requireNonNull(getCommand("lang")).setTabCompleter(lang);
    }


}
