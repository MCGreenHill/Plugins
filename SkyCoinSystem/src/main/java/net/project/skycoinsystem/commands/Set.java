package net.project.skycoinsystem.commands;

import net.project.api.ProjectPlayer;
import net.project.skycoinsystem.SkyCoinSystem;
import net.project.skycoinsystem.data.Vault;
import net.project.skycoinsystem.tools.MoneyParser;
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

@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "set", permission = "skycoinsystem.set"))
@Permission(name = "skycoinsystem.set", defaultValue = PermissionDefault.OP)
public class Set implements CommandExecutor {
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
            sender.sendMessage(bundle.getString("command.set.not.enough.arguments"));
            return true;
        }
        String searchedPlayerName = args[0];
        Player searchedPlayer = SkyCoinSystem.getPlugin().getServer().getPlayerExact(searchedPlayerName);
        if (searchedPlayer == null) {
            sender.sendMessage(String.format(bundle.getString("command.set.player.does.not.exist"), searchedPlayerName));
            return true;
        }
        double amount;
        try {
            amount = MoneyParser.doubleParser(Double.parseDouble(args[1]));
        } catch (Exception e) {
            sender.sendMessage(bundle.getString("command.set.amount.has.to.be.a.number"));
            return true;
        }
        Vault.remMoney(searchedPlayer, Vault.getBalance(searchedPlayer));
        Vault.addMoney(searchedPlayer, amount);
        sender.sendMessage(String.format(bundle.getString("command.set.amount.set"), searchedPlayer.getName(), MoneyParser.formatNumber(amount)));
        if (bukkitPlayer != searchedPlayer) {
            searchedPlayer.sendMessage(String.format(bundle.getString("command.set.other.player.amount.set.message"), MoneyParser.formatNumber(amount)));
        }
        return true;
    }
}
