package future.graphics.configmanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import future.graphics.main.main;

public class PlayerConfigManager {

	private main plugin;
	private File file;
	private FileConfiguration config;

	public PlayerConfigManager(main plugin) {
		this.plugin = plugin;
		this.file = new File(this.plugin.getDataFolder(), "player.yml");
		this.config = YamlConfiguration.loadConfiguration(file);
		load();
	}

	public void load() {
		if (!file.exists()) {
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

	public void addKitList(Player player, String kit) {
		List<String> list = null;
		if (config.contains("Player." + player.getUniqueId() + ".Kits"))
			list = config.getStringList("Player." + player.getUniqueId() + ".Kits");
		else
			list = new ArrayList<String>();
		if (!list.contains(kit)) {
			list.add(kit);
			config.set("Player." + player.getUniqueId() + ".Kits", list);
			save();
		}
	}

	public List<String> getBuyedKits(Player player) {
		return config.getStringList("Player." + player.getUniqueId().toString() + ".Kits");
	}

	public void setCoinAmount(Player player, int amount) {
		config.set("Player." + player.getUniqueId().toString() + ".Coins", amount);
		save();
	}

	public void createPlayerConfiguration(Player player) {
		String uuid = player.getUniqueId().toString();
		if (!config.contains("Player." + uuid + ".Kits")) {
			config.set("Player." + uuid + ".Name", player.getName());
			List<String> list = new ArrayList<String>();
			list.add("Start");
			config.set("Player." + uuid + ".Kits", list);
			save();
		}
	}

}
