package future.graphics.configmanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import future.graphics.main.main;

public class ConfigManager {

	private main plugin;
	private File file;
	private FileConfiguration config;

	public ConfigManager(main plugin) {
		this.plugin = plugin;
		this.file = new File(this.plugin.getDataFolder(), "config.yml");
		this.config = YamlConfiguration.loadConfiguration(file);
		load();
	}

	public void load() {
		if (!file.exists()) {
			config.options().copyDefaults(true);
			config.addDefault("PublicDeathMessage", false);
			config.addDefault("DeathMessage", "&c%killer% &4hatt &c%spieler% &4getötet");
			// [Material], [Namen], [Anzahl], [UnterKategorie], [Enchantment],
			// [EnchantLvL], [Lore]
			String[] arrayString = { "IRON_SWORD", "&eStart-Schwert", "1", "0", "16", "1", "&eStart-Schwert" };
			config.addDefault("Kit.Start.DisplayName", "&eIchBinGeil");
			config.addDefault("Kit.Start.Cost", 1);
			config.addDefault("Kit.Start.Icon", Material.LEATHER_BOOTS.toString());
			config.addDefault("Kit.Start.Items.1", arrayString);
			config.addDefault("Kit.Start.ItemList", 1);
			List<String> kitlist = new ArrayList<String>();
			kitlist.add("Start");
			config.addDefault("KitList", kitlist);
			save();
		} else {
			try {
				config.load(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
		}
	}

	public String getKitNameByDisplayName(String displayname) {
		for (String allKits : getAllKits()) {
			if (config.getString("Kit." + allKits + ".DisplayName").equals(displayname)) {
				return allKits;
			}
		}
		return null;
	}

	public void addTeleportList(String name) {
		if (config.contains("Teleport.List")) {
			List<String> list = config.getStringList("Teleport.List");
			if (!list.contains(name)) {
				list.add(name);
				config.set("Teleport.List", list);
				save();
			}
		} else {
			List<String> list = new ArrayList<String>();
			list.add(name);
			config.set("Teleport.List", list);
			save();
		}
	}

	public List<String> getTeleportList() {
		if (config.contains("Teleport.List")) {
			return config.getStringList("Teleport.List");
		}
		return null;
	}

	public void setSpawn(Location loc, String name) {
		config.set("Teleport." + name + ".World", loc.getWorld().getName());
		config.set("Teleport." + name + ".X", loc.getX());
		config.set("Teleport." + name + ".Y", loc.getY());
		config.set("Teleport." + name + ".Z", loc.getZ());
		config.set("Teleport." + name + ".Yaw", loc.getYaw());
		config.set("Teleport." + name + ".Pitch", loc.getPitch());
		addTeleportList(name);
		save();
	}

	public Location getSpawn(String name) {
		Location loc = null;
		if (config.contains("Teleport." + name + ".World")) {
			loc = new Location(plugin.getServer().getWorld(config.getString("Teleport." + name + ".World")),
					config.getDouble("Teleport." + name + ".X"), config.getDouble("Teleport." + name + ".Y"),
					config.getDouble("Teleport." + name + ".Z"));
			loc.setYaw((float) config.getDouble("Teleport." + name + ".Yaw"));
			loc.setPitch((float) config.getDouble("Teleport." + name + ".Pitch"));
		}
		return loc;
	}

	public int getKitItemList(String name) {
		return config.getInt("Kit." + name + ".ItemList");
	}

	public String[] test() {
		return new String[] { "Hello" };
	}

	public List<?> getKitItems(String name, Integer item) {
		return config.getList("Kit." + name + ".Items." + item.toString());
	}

	public String getKitIcon(String name) {
		return config.getString("Kit." + name + ".Icon");
	}

	public int getKitCost(String name) {
		return config.getInt("Kit." + name + ".Cost");
	}

	public String getKitDisplayName(String name) {
		return config.getString("Kit." + name + ".DisplayName");
	}

	public String getDeathMessage(Player player, Player killer) {
		String deathmessage = config.getString("DeathMessage");
		return deathmessage;
	}

	public Boolean isDeathMessageOn() {
		return config.getBoolean("PublicDeathMessage");
	}
	
	public List<String> getAllKits() {
		return config.getStringList("KitList");
	}

}
