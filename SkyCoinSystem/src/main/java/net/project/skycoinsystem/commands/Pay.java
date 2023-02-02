package net.project.skycoinsystem.commands;

import net.project.api.ProjectPlayer;
import net.project.skycoinsystem.SkyCoinSystem;
import net.project.skycoinsystem.data.Vault;
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

@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "pay", permission = "skycoinsystem.pay"))
@Permission(name = "skycoinsystem.pay", defaultValue = PermissionDefault.TRUE)
public class Pay implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player bukkitPlayer)) {
            sender.sendMessage("This command should be executed as a user!");
            return true;
        }
        ProjectPlayer player = new ProjectPlayer(bukkitPlayer);
        Locale playerLocale = player.getProjectLocale();
        ResourceBundle bundle = SkyCoinSystem.getMessagesBundle(playerLocale);
        if (args.length < 2) {
            sender.sendMessage(bundle.getString("command.pay.not.enough.arguments"));
            return true;
        }
        String searchedPlayerName = args[0];
        Player searchedPlayer = SkyCoinSystem.getPlugin().getServer().getPlayer(searchedPlayerName);
        if (searchedPlayer == null) {
            sender.sendMessage(String.format(bundle.getString("command.pay.player.does.not.exist"), searchedPlayerName));
            return true;
        }
        double amount = Double.parseDouble(args[1]);
        if (amount <= 0) {
            sender.sendMessage(bundle.getString("command.pay.amount.less.then.null"));
            return true;
        }
        double playerBalance = Vault.getBalance(bukkitPlayer);
        if (amount > playerBalance) {
            sender.sendMessage(String.format(bundle.getString("command.pay.player.does.not.has.enough.money"), playerBalance - amount));
            return true;
        }
        Vault.remMoney(bukkitPlayer, amount);
        sender.sendMessage(String.format(bundle.getString("command.pay.sent.money.to.player"), amount, searchedPlayer.getName()));
        Vault.addMoney(searchedPlayer, amount);
        searchedPlayer.sendMessage(String.format(bundle.getString("command.pay.player.get.money.from.player"), amount, bukkitPlayer.getName()));
        return true;
    }
}
