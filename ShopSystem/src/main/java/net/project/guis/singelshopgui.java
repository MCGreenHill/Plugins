package net.project.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.project.Builder.ItemBuilder;
import net.project.Builder.MoneyParser;
import net.project.Main;
import net.project.data.Vault;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class singelshopgui {

    public static List<ItemStack> itemlist = new ArrayList<>();

    public static void shop(Player player) {
        char euro = '\u20AC';
        ChestGui shop = new ChestGui(6, "Shop");
        StaticPane outline = new StaticPane(0, 0, 9, 1);
        StaticPane outline2 = new StaticPane(0, 1, 1, 4);
        StaticPane outline3 = new StaticPane(8, 1, 1, 4);
        ItemStack outlineintem = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName(" ").build();
        outline.setOnClick(event -> event.setCancelled(true));
        outline2.setOnClick(event -> event.setCancelled(true));
        outline3.setOnClick(event -> event.setCancelled(true));
        outline.fillWith(outlineintem);
        outline2.fillWith(outlineintem);
        outline3.fillWith(outlineintem);
        shop.addPane(outline);
        shop.addPane(outline2);
        shop.addPane(outline3);


        List<GuiItem> guiItems = new ArrayList<>();
        PaginatedPane shopitems = new PaginatedPane(1, 1, 7, 4);
        for (int i = 0; i < itemlist.size(); i++) {
            player.sendMessage(String.valueOf(itemlist.get(i)));
            ItemStack item = itemlist.get(i).clone();
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer data = meta.getPersistentDataContainer();
            Double price = data.get(new NamespacedKey(Main.getPlugin(), "price"), PersistentDataType.DOUBLE);
            Integer size = data.get(new NamespacedKey(Main.getPlugin(), "size"), PersistentDataType.INTEGER);
            data.set(new NamespacedKey(Main.getPlugin(), "paar"), PersistentDataType.INTEGER, i);
            List<String> lore = new ArrayList<>();
            lore.add("Price: " + price + " " + euro);
            lore.add("Size: " + size + "x");
            lore.add("Rechts click zum kaufen");
            lore.add("Links click zum Verkaufen");
            meta.setLore(lore);
            item.setItemMeta(meta);
            GuiItem guiItem = new GuiItem(item);
            guiItems.add(guiItem);
        }
        shopitems.populateWithGuiItems(guiItems);
        shopitems.setOnClick(event -> {
            Double playerMoney = Vault.getBalance(player);
            ItemStack item = event.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer data = meta.getPersistentDataContainer();
            Double price = data.get(new NamespacedKey(Main.getPlugin(), "price"), PersistentDataType.DOUBLE);
            Integer size = data.get(new NamespacedKey(Main.getPlugin(), "size"), PersistentDataType.INTEGER);
            Integer paar = data.get(new NamespacedKey(Main.getPlugin(), "paar"), PersistentDataType.INTEGER);
            event.setCancelled(true);
            if (event.isLeftClick()) {
                if (playerMoney >= price) {
                    Vault.remMoney(player, price);

                    if (!(player.getInventory().firstEmpty() <= -1)) {
                        int i = player.getInventory().firstEmpty();
                        ItemStack giveitem = itemlist.get(paar);
                        ItemMeta meta1 = giveitem.getItemMeta();
                        giveitem.setItemMeta(meta1);
                        giveitem.setAmount(size);
                        player.getInventory().setItem(i, giveitem);

                    } else player.sendMessage("kein slot frei");

                } else
                    player.sendMessage("du hast nicht genug geld dir fehlt " + MoneyParser.formatNumber((playerMoney - price) * -1));
            }
        });


        StaticPane navigation = new StaticPane(0, 5, 9, 1);

        navigation.setPriority(Pane.Priority.HIGHEST);
        navigation.addItem(new GuiItem(new ItemBuilder(Material.PLAYER_HEAD).setHeadDatabase(8784).setDisplayName(ChatColor.GRAY + "Back").build(), event -> {
            if (shopitems.getPage() > 0) {
                shopitems.setPage(shopitems.getPage() - 1);
                shop.update();
            } else event.setCancelled(true);
        }), 1, 0);

        navigation.addItem(new GuiItem(new ItemBuilder(Material.PLAYER_HEAD).setHeadDatabase(8782).setDisplayName(ChatColor.GRAY + "Next").build(), event -> {
            if (shopitems.getPage() < shopitems.getPages() - 1) {
                shopitems.setPage(shopitems.getPage() + 1);

                shop.update();
            } else event.setCancelled(true);
        }), 7, 0);

        navigation.addItem(new GuiItem(new ItemBuilder(Material.BARRIER).setDisplayName(ChatColor.RED + "Close").build(), event ->
                event.getWhoClicked().closeInventory()), 4, 0);
        navigation.fillWith(outlineintem);
        shop.addPane(navigation);
        shop.addPane(shopitems);


        shop.show(player);
    }

}
