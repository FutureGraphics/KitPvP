package future.graphics.listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import essentials.future.code.mysql.MySQLPlayer;
import future.graphics.main.main;

public class InventoryClickListener implements Listener {

	private main plugin;

	public InventoryClickListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

	}

	@SuppressWarnings({ "deprecation" })
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
						player.getInventory().clear();
						player.getInventory().setHelmet(null);
						player.getInventory().setChestplate(null);
						player.getInventory().setLeggings(null);
						player.getInventory().setBoots(null);
						int kitcount = plugin.getConfigManager().getKitItemList(kitName);
						int count = 1;
						int i = 1;
						do {
							List<?> array = plugin.getConfigManager().getKitItems(kitName, i);
								Material material = Material.getMaterial(array.get(0).toString());
								String name = array.get(1).toString();
								name = name.replace("&", "§");
								int Amount = Integer.parseInt(array.get(2).toString());
								int under = Integer.parseInt(array.get(3).toString());
								Enchantment enchantment = Enchantment.getById(Integer.parseInt(array.get(4).toString()));
								int lvl = Integer.parseInt(array.get(5).toString());
								String lore = array.get(6).toString();
								lore = lore.replace("&", "§");
								ItemStack itemInv = plugin.getApiManager().createItem(material, name, Amount, under,
										enchantment, lvl, lore);
								player.getInventory().addItem(itemInv);
								i++;
								count++;
						} while (count < kitcount);
						player.sendMessage(plugin.prefix + "§eDu hast das §c"
								+ plugin.getConfigManager().getKitDisplayName(kitName).replace("&", "§")
								+ " §eKit bekommen");
						player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1f, 1f);
						player.closeInventory();

					}
				}
			}

		}
	}

}
