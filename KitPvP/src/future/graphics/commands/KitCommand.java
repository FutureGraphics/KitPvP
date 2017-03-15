package future.graphics.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import future.graphics.main.main;

public class KitCommand implements CommandExecutor {

	private main plugin;
	
	public KitCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("kitpvp.kit")) {
				Inventory kitInv = Bukkit.createInventory(player, 18, "§8Kits");
				int i = 0;
				for(String allKits : plugin.getConfigManager().getAllKits()) {
					if(plugin.getPlayerConfigManager().getBuyedKits(player).contains(allKits)) {
						String name = plugin.getConfigManager().getKitDisplayName(allKits);
						name = name.replace("&", "§");
						String icon = plugin.getConfigManager().getKitIcon(allKits);
						Material material = Material.getMaterial(icon);
						
						ItemStack item = plugin.getApiManager().createItem(material, name, 1, 0, null, 0, "");
						kitInv.setItem(i, item);
						i++;
					}
				}
				player.openInventory(kitInv);
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			sender.sendMessage(plugin.noConsole);
		}
		return true;
	}

}
