package future.graphics.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import future.graphics.main.main;

public class DeathListener implements Listener {

	private main plugin;
	
	public DeathListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	public Map<Player, Integer> killstreak = new HashMap<Player, Integer>();
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setKeepInventory(true);
		e.setDroppedExp(0);
		if(e.getEntity() instanceof Player) {
			if(e.getEntity().getKiller() instanceof Player) {
				Player player = e.getEntity();
				Player killer = e.getEntity().getKiller();
				killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*5, 2));
				if(killstreak.containsKey(killer)) {
					int kills = killstreak.get(killer)+1;
					killstreak.remove(killer);
					Bukkit.broadcastMessage(plugin.prefix + "§eSpieler §c" + killer.getName() + " §ehat einen §lKillstreak von §c§l" + kills);
					killstreak.put(killer, kills);
				} else {
					killstreak.put(killer, 1);
				}
				
				if(killstreak.containsKey(player)) {
					killstreak.remove(player);
					player.sendMessage("§4Du hast deinen Killstreak Status verlohren");
				}
			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
	}

}
