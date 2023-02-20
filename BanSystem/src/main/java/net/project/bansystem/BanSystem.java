package net.project.bansystem;

import eu.cloudnetservice.common.document.gson.JsonDocument;
import eu.cloudnetservice.driver.module.ModuleLifeCycle;
import eu.cloudnetservice.driver.module.ModuleTask;
import eu.cloudnetservice.driver.module.driver.DriverModule;
import eu.cloudnetservice.node.cluster.sync.DataSyncHandler;
import eu.cloudnetservice.node.cluster.sync.DataSyncRegistry;
import jakarta.inject.Singleton;
import net.project.bansystem.config.BanSystemConfig;

import java.util.List;

@Singleton
public class BanSystem extends DriverModule {

    private volatile BanSystemConfig banSystemConfig;

    @ModuleTask(order = 127, lifecycle = ModuleLifeCycle.LOADED)
    public void registerDataSyncHandler(DataSyncRegistry dataSyncRegistry) {
        dataSyncRegistry.registerHandler(DataSyncHandler.<BanSystemConfig>builder()
                .key("cloudperms-config")
                .convertObject(BanSystemConfig.class)
                .currentGetter($ -> this.banSystemConfig)
                .singletonCollector(() -> this.banSystemConfig)
                .nameExtractor(cloudBanSystemConfig -> "BanSystem Config")
                .writer(config -> this.writeConfig(JsonDocument.newDocument(config)))
                .build());
    }

    @ModuleTask(lifecycle = ModuleLifeCycle.LOADED)
    public void initConfig() {
        this.banSystemConfig = this.readConfig(
                BanSystemConfig.class,
                () -> new BanSystemConfig(true, List.of()));
    }

    @ModuleTask(lifecycle = ModuleLifeCycle.RELOADING)
    public void reload() {
        this.initConfig();
    }

}
