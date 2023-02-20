package net.project.bansystem.bungee;

import eu.cloudnetservice.ext.platforminject.api.PlatformEntrypoint;
import eu.cloudnetservice.ext.platforminject.api.stereotype.PlatformPlugin;
import jakarta.inject.Singleton;

@Singleton
@PlatformPlugin(
        platform = "bungee",
        name = "BanSystem",
        authors = "GigaClub",
        version = "1.0.0"
)
public class BungeeBanSystemPlugin implements PlatformEntrypoint {
}
