package future.graphics.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.mysql.MySQLPlayer;
import future.graphics.main.main;

public class InventoryClickListener implements Listener {

	private main plugin;

	public InventoryClickListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

	}

	@SuppressWarnings({ })
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		if (e.getInventory().getName().equals("§7Shop")) {
			e.setCancelled(true);
			if (!e.getCurrentItem().equals(null)) {
				if (plugin.getConfigManager().getKitNameByDisplayName(
						e.getCurrentItem().getItemMeta().getDisplayName().replace("§", "&")) != null) {
					String kitName = plugin.getConfigManager().getKitNameByDisplayName(
							e.getCurrentItem().getItemMeta().getDisplayName().replace("§", "&"));
					if (MySQLPlayer.getCoins(player.getUniqueId().toString()) >= plugin.getConfigManager()
							.getKitCost(kitName)) {
						plugin.getPlayerConfigManager().addKitList(player, kitName);
						MySQLPlayer.updateCoins(player.getUniqueId(), plugin.getConfigManager().getKitCost(kitName), true, player.getName());
						player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f, 1f);
						player.sendMessage(plugin.prefix + "§eDu hast erfolgreich das Kit §c"
								+ plugin.getConfigManager().getKitDisplayName(kitName).replace("&", "§")
								+ " §egekauft");
						player.closeInventory();
					} else {
						player.sendMessage(plugin.prefix + "§cDu hast nicht genügend Coins");
					}
				}
			}
		} else if (e.getInventory().getName().equals("§8Kits")) {
			e.setCancelled(true);
			if (!e.getCurrentItem().equals(null)) {
				if (plugin.getConfigManager().getKitNameByDisplayName(
						e.getCurrentItem().getItemMeta().getDisplayName().replace("§", "&")) != null) {
					String kitName = plugin.getConfigManager().getKitNameByDisplayName(
							e.getCurrentItem().getItemMeta().getDisplayName().replace("§", "&"));
					if (kitName != null) {
						ApiManager.clearAllFromPlayer(player);
						for(String format : plugin.getConfigManager().getKitItems(kitName)) {
							ItemStack item = plugin.getConfigManager().getItemStack(format);
							if(ApiManager.isArmor(item.getType())) {
								if(ApiManager.isHelment(item.getType())) {
									player.getInventory().setHelmet(item);
								} else if(ApiManager.isChestplate(item.getType())) {
									player.getInventory().setChestplate(item);
								} else if(ApiManager.isLeggins(item.getType())) {
									player.getInventory().setLeggings(item);
								} else if(ApiManager.isBoot(item.getType())) {
									player.getInventory().setBoots(item);
								}
							} else {
								player.getInventory().addItem(item);
							}
						}
						
						player.sendMessage(plugin.prefix + "§eDu hast das §c"
								+ plugin.getConfigManager().getKitDisplayName(kitName).replace("&", "§")
								+ " §eKit bekommen");
						player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1f, 1f);
						if(plugin.kitMap.containsKey(player)) {
							plugin.kitMap.remove(player);
						}
						plugin.kitMap.put(player, kitName);
						player.closeInventory();
					}
				}
			}

		}
	}

}
