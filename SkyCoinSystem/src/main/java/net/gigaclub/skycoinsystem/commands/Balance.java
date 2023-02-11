package net.gigaclub.skycoinsystem.commands;

import net.gigaclub.api.ProjectPlayer;
import net.gigaclub.skycoinsystem.SkyCoinSystem;
import net.gigaclub.skycoinsystem.data.Vault;
import net.gigaclub.skycoinsystem.tools.MoneyParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "balance", permission = "skycoinsystem.balance"))
@Permission(name = "skycoinsystem.balance", defaultValue = PermissionDefault.TRUE)
public class Balance implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player bukkitPlayer)) {
            sender.sendMessage("This command should be executed as a user!");
            return true;
        }
        ProjectPlayer player = new ProjectPlayer(bukkitPlayer);
        Locale playerLocale = player.getProjectLocale();
        ResourceBundle bundle = SkyCoinSystem.getMessagesBundle(playerLocale);
        if (args.length == 0) {
            double balance = Vault.getBalance(bukkitPlayer);
            sender.sendMessage(String.format(bundle.getString("command.balance.own"), MoneyParser.formatNumber(balance)));
            return true;
        }
        String searchedPlayerName = args[0];
        Player searchedPlayer = SkyCoinSystem.getPlugin().getServer().getPlayerExact(searchedPlayerName);
        if (searchedPlayer == null) {
            sender.sendMessage(String.format(bundle.getString("command.balance.player.does.not.exist"), searchedPlayerName));
            return true;
        }
        double balance = Vault.getBalance(searchedPlayer);
        sender.sendMessage(String.format(bundle.getString("command.balance.of.player"), searchedPlayer.getName(), MoneyParser.formatNumber(balance)));
        return true;
    }
}
