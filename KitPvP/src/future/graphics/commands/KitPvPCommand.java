package future.graphics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import future.graphics.main.main;

public class KitPvPCommand implements CommandExecutor {

	private main plugin;
	
	public KitPvPCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("kitpvp.reload")) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("reload")) {
						plugin.getConfigManager().load();
						plugin.getPlayerConfigManager().load();
						player.sendMessage(plugin.prefix + "§eDie Config wurde neugeladen");
					} else if(args[0].equalsIgnoreCase("test")) {
				        int kitamount = plugin.getConfigManager().getKitItemList("Start");
				        int count = 1;
				        do {
				        	player.sendMessage("" + kitamount);
				        } while(count < kitamount);
					} else {
						player.sendMessage(plugin.prefix + "§c/kitpvp [reload]");
					}
				} else {
					player.sendMessage(plugin.prefix + "§c/kitpvp [reload]");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			sender.sendMessage(plugin.noConsole);
		}
		return true;
	}

}
