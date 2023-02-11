package net.gigaclub.referralsystem;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public final class ReferralSystem extends Plugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getProxy().getPluginManager().registerListener(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onProxyLogin(LoginEvent e) {
        System.out.println(e.getConnection().getVirtualHost().getHostName());
    }

}
