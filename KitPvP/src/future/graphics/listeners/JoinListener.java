package future.graphics.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import future.graphics.main.main;

public class JoinListener implements Listener {

	private main plugin;
	
	public JoinListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		plugin.getPlayerConfigManager().createPlayerConfiguration(player);
	}

}
