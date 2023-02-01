package net.project.skycoinsystem;

import net.project.api.ProjectPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;


@Plugin(name = "SkyCoinSystemPlugin", version = "${version}")
@ApiVersion(ApiVersion.Target.v1_19)
public final class SkyCoinSystem extends ProjectPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
