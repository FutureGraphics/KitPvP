package future.graphics.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import future.graphics.main.main;

public class Funktionen implements Listener {

	private main plugin;

	public Funktionen(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

	}

	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		e.setCancelled(true);
	}

	public void onDrop(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}

	public List<Player> feuerList = new ArrayList<Player>();
	public List<Player> blitzList = new ArrayList<Player>();
	public List<Player> jumperList = new ArrayList<Player>();
	public List<Player> regeneration = new ArrayList<Player>();
	public List<Player> gift = new ArrayList<Player>();
	public List<Player> slow = new ArrayList<Player>();
	public List<Player> unsichtbar = new ArrayList<Player>();
	public List<Player> teleportieren = new ArrayList<Player>();
	public List<Player> freezz = new ArrayList<Player>();
	public Map<Player, Location> freezMap = new HashMap<Player, Location>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem() != null) {
				
				ItemStack item = e.getItem();
				String itemName = null;
				if (item.getItemMeta().getDisplayName() != null) {
					itemName = item.getItemMeta().getDisplayName().replace("§", "&");

					if (itemName.equals("&cHammer von Thor")) {
						e.setCancelled(true);
						if (blitzList.contains(player)) {
							player.sendMessage(plugin.prefix + "§cDu musst 5 Sekunden Warten");
						} else {
							player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1f, 1f);

							Location loc = new Location(player.getWorld(), player.getLocation().getX(),
									player.getLocation().getY() + 1, player.getLocation().getZ());

							player.getWorld().playEffect(loc, Effect.FIREWORKS_SPARK, 500);
							player.getWorld().playEffect(loc, Effect.FIREWORKS_SPARK, 500);

							HashSet<Byte> hashNull = null;
							Block block = player.getTargetBlock(hashNull, 50);
							Location location = block.getLocation();
							player.getWorld().strikeLightning(location);

							blitzList.add(player);

							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

								@Override
								public void run() {
									blitzList.remove(player);
									player.sendMessage(plugin.prefix + "§eDu kannst deine Fähigkeit wieder benutzen");
								}
							}, 20 * 5);
						}

					} else if (itemName.equals("&cFeuer")) {
						e.setCancelled(true);
						if (feuerList.contains(player)) {
							player.sendMessage(plugin.prefix + "§cDu musst 5 Sekunden Warten");
						} else {
							for (Player players : Bukkit.getOnlinePlayers()) {
								if (players.getLocation().distance(player.getLocation()) <= 5) {
									player.addPotionEffect(plugin.getApiManager().createPotionEffect(5, 1,
											PotionEffectType.FIRE_RESISTANCE));
									players.setFireTicks(20 * 5);

								}
							}
							feuerList.add(player);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

								@Override
								public void run() {
									feuerList.remove(player);
									player.sendMessage(plugin.prefix + "§eDu kannst deine Fähigkeit wieder benutzen");
								}
							}, 20 * 5);
						}
					} else if (itemName.equals("&cJumper")) {
						e.setCancelled(true);
						if (jumperList.contains(player)) {
							player.sendMessage(plugin.prefix + "§cDu musst 5 Sekunden Warten");
						} else {
							Vector v = player.getLocation().getDirection().multiply(2D);
							player.setVelocity(v);
							player.playSound(player.getLocation(), Sound.FIREWORK_BLAST, 1f, 1f);
							jumperList.add(player);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

								@Override
								public void run() {
									jumperList.remove(player);
									player.sendMessage(plugin.prefix + "§eDu kannst deine Fähigkeit wieder benutzen");
								}
							}, 20 * 5);
						}
					} else if (itemName.equals("&cRegeneration")) {
						if (regeneration.contains(player)) {
							player.sendMessage(plugin.prefix + "§cDu musst 10 Sekunden Warten");
						} else {
							player.addPotionEffect(plugin.getApiManager().createPotionEffect(20 * 5, 1,
									PotionEffectType.REGENERATION));
							player.sendMessage(plugin.prefix + "§eDu hast §c100 §eSekunden Regeneration");
							regeneration.add(player);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

								@Override
								public void run() {
									regeneration.remove(player);
									player.sendMessage(plugin.prefix + "§eDu kannst deine Fähigkeit wieder benutzen");
								}
							}, 20 * 10);
						}
					} else if (itemName.equals("&cVergiften")) {
						e.setCancelled(true);
						if (gift.contains(player)) {
							player.sendMessage(plugin.prefix + "§cDu musst 5 Sekunden Warten");
						} else {
							for (Player players : Bukkit.getOnlinePlayers()) {
								if (players.getLocation().distance(player.getLocation()) <= 5) {
									if (!players.getName().equals(player.getName())) {
										players.addPotionEffect(plugin.getApiManager().createPotionEffect(2, 1,
												PotionEffectType.POISON));
										players.sendMessage(plugin.prefix + "§eDu wurdest §b§lVERGIFTET");
										player.sendMessage(plugin.prefix + "§eDu hast §c§l" + players.getName() + " §evergiftet");
									}
								}
							}
							gift.add(player);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

								@Override
								public void run() {
									gift.remove(player);
									player.sendMessage(plugin.prefix + "§eDu kannst deine Fähigkeit wieder benutzen");
								}
							}, 20 * 5);
						}
					} else if (itemName.equals("&cSlow")) {
						if (slow.contains(player)) {
							player.sendMessage(plugin.prefix + "§cDu musst 5 Sekunden Warten");
						} else {
							for (Player players : Bukkit.getOnlinePlayers()) {
								if (players.getLocation().distance(player.getLocation()) <= 5) {
									if (!players.getName().equals(player.getName())) {
										players.addPotionEffect(
												plugin.getApiManager().createPotionEffect(5, 3, PotionEffectType.SLOW));
										players.sendMessage(plugin.prefix + "§eDu wurdest §d§lVerlangsamt");
										player.sendMessage(plugin.prefix + "§eDu hast §c§l" + players.getName() + " §everlangsamt");
									}
								}
							}
							slow.add(player);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

								@Override
								public void run() {
									slow.remove(player);
									player.sendMessage(plugin.prefix + "§eDu kannst deine Fähigkeit wieder benutzen");
								}
							}, 20 * 5);
						}
					} else if (itemName.equals("&cUnsichtbar")) {
						e.setCancelled(true);
						if (unsichtbar.contains(player)) {
							player.sendMessage(plugin.prefix + "§cDu musst 10 Sekunden Warten");
						} else {
							for (Player players : Bukkit.getOnlinePlayers()) {
								players.hidePlayer(player);
							}
							player.sendMessage(plugin.prefix + "§eDu bist nun Unsichtbar für §c5 §eSekunden");
							unsichtbar.add(player);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								@Override
								public void run() {
									for (Player players : Bukkit.getOnlinePlayers()) {
										players.showPlayer(player);
									}
									player.sendMessage(plugin.prefix + "§eDu bist wieder Sichtbar ");
								}
							}, 20 * 5);

							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								@Override
								public void run() {
									for (Player players : Bukkit.getOnlinePlayers()) {
										players.showPlayer(player);
									}
									unsichtbar.remove(player);
									player.sendMessage(plugin.prefix + "§eDu kannst deine Fähigkeit wieder benutzen");
								}
							}, 20 * 10);
						}
					} else if (itemName.equals("&cTeleportieren")) {
						if (teleportieren.contains(player)) {
							player.sendMessage(plugin.prefix + "§eDu musst §c2 §eMinuten warten");
						} else {
							int random = plugin.getApiManager().getRandom(1,
									plugin.getConfigManager().getTeleportList().size());
							Location loc = plugin.getConfigManager()
									.getSpawn(plugin.getConfigManager().getTeleportList().get(random));
							if (loc != null) {
								player.teleport(loc);
								player.sendMessage(plugin.prefix + "§eErfolgreich teleportiert");
								teleportieren.add(player);
								Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									@Override
									public void run() {
										teleportieren.remove(player);
										player.sendMessage(plugin.prefix + "§eDu kannst deine Fähigkeit wieder benutzen");
									}
								}, 20 * 120);
							} else {
								player.sendMessage(plugin.prefix + "§cTeleportation hat nicht funtkoniert versuche es erneut");
							}

						}
					} else if (itemName.equals("&cFreez")) {
						e.setCancelled(true);
						if (freezz.contains(player)) {
							player.sendMessage("§cDu musst 10 Sekunden Warten");
						} else {
							for (final Player players : Bukkit.getOnlinePlayers()) {
								if (players.getLocation().distance(player.getLocation()) <= 5) {
									if (!players.getName().equals(player.getName())) {
										if (!freezMap.containsKey(players)) {
											freezMap.put(players, players.getLocation());
											player.sendMessage(plugin.prefix + "§eDu hast §b" + players.getName() + " §eeingefrohren");
											players.sendMessage(plugin.prefix + "§cDu bist für §c2 §eSekunden eingefrohren");
											players.setAllowFlight(true);
											Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
												@Override
												public void run() {
													freezMap.remove(players);
													players.sendMessage(plugin.prefix + "§eDu bist nicht mehr eingefroren");
												}
											}, 20 * 2);
										}
									}
								}
							}
							freezz.add(player);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								@Override
								public void run() {
									freezz.remove(player);
									player.sendMessage(plugin.prefix + "§eDu kannst deine Fähigkeit wieder benutzen");
								}
							}, 20 * 10);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void playerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (freezMap.containsKey(player)) {
			player.teleport(freezMap.get(player));
		}
	}

}
