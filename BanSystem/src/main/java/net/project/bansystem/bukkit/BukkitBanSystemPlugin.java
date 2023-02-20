package net.project.bansystem.bukkit;

import eu.cloudnetservice.ext.platforminject.api.PlatformEntrypoint;
import eu.cloudnetservice.ext.platforminject.api.stereotype.PlatformPlugin;
import jakarta.inject.Singleton;

@Singleton
@PlatformPlugin(
        platform = "bukkit",
        name = "BanSystem",
        authors = "GigaClub",
        version = "1.0.0"
)
public class BukkitBanSystemPlugin implements PlatformEntrypoint {
}
