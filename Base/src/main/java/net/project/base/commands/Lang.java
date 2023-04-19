package net.project.base.commands;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.project.api.ProjectPlayer;
import net.project.base.Base;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


@Commands(@org.bukkit.plugin.java.annotation.command.Command(
        name = "lang", permission = "base.lang"
))
@Permission(name = "base.lang", defaultValue = PermissionDefault.TRUE)
public class Lang implements CommandExecutor, TabCompleter {

    private static List<String> possibleLanguageList;

    public Lang() {
        possibleLanguageList = Arrays.asList("de", "en");
    }
    MiniMessage mm = MiniMessage.miniMessage();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player bukkitPlayer)) {
            sender.sendMessage("This command should be executed as a user!");
            return true;
        }
        ProjectPlayer player = new ProjectPlayer(bukkitPlayer);
        Locale playerLocale = player.getProjectLocale();
        ResourceBundle bundle = Base.getMessagesBundle(playerLocale);
        if (args.length == 0) {
            Audience player1 = (Audience) sender;
            Component pars = mm.deserialize(String.format(bundle.getString("command.lang"), playerLocale));
            player1.sendMessage(pars);
            return true;
        }
        String selectedLang = args[0];
        if (!possibleLanguageList.contains(selectedLang)) {
            sender.sendMessage(String.format(bundle.getString("command.lang.wrong.language"), selectedLang));
            return true;
        }
        player.setProjectLocale(new Locale(selectedLang));
        sender.sendMessage(String.format(bundle.getString("command.lang.set"), selectedLang));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            return null;
        }
        String langArg = args[0].toLowerCase();
        if (langArg.equals("")) {
            return possibleLanguageList;
        }
        List<String> res = new ArrayList<>();
        for (String lang : possibleLanguageList) {
            if (lang.toLowerCase().startsWith(langArg)) {
                res.add(lang);
            }
        }
        return res;
    }
}
