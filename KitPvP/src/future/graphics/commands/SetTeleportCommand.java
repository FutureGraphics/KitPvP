package future.graphics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import future.graphics.main.main;

public class SetTeleportCommand implements CommandExecutor {

	private main plugin;
	
	public SetTeleportCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("kitpvp.setteleport")) {
				if(args.length == 1) {
					plugin.getConfigManager().setSpawn(player.getLocation(), args[0]);
					player.sendMessage(plugin.prefix + "§eTeleporter Spawn wurde gesetzt");
				} else {
					player.sendMessage(plugin.prefix + "§c/setteleport [namen]");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		}
		return true;
	}

}
