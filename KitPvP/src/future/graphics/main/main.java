package future.graphics.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import future.graphics.commands.KitCommand;
import future.graphics.commands.KitPvPCommand;
import future.graphics.commands.SetTeleportCommand;
import future.graphics.commands.ShopCommand;
import future.graphics.configmanager.ApiManager;
import future.graphics.configmanager.ConfigManager;
import future.graphics.configmanager.PlayerConfigManager;
import future.graphics.listeners.DeathListener;
import future.graphics.listeners.Funktionen;
import future.graphics.listeners.InventoryClickListener;
import future.graphics.listeners.JoinListener;

public class main extends JavaPlugin {
	
	public String prefix = "§8|§aXZ§8 » ";
	public String noAccess = prefix + "§cKeine Berechtigung!";
	public String playerNotFound = prefix + "§cSpieler nicht gefunden";
	public String noConsole = prefix + "§cDie Console kann diesen Command nicht ausführen!";
	
	private ConfigManager configManager;
	private ApiManager apiManager;
	private PlayerConfigManager playerConfigManager;
	
	private JoinListener joinListener;
	private InventoryClickListener inventoryClickListener;
	private DeathListener deathListener;
	
	private KitCommand kitCommand;
	private KitPvPCommand kitPvPCommand;
	private ShopCommand shopCommand;
	private SetTeleportCommand setTeleportCommand;
	
	private Funktionen funktionen;
	
	@Override
	public void onEnable() {
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-");
		System.out.println("Plugin By FutureGraphics");
		System.out.println("KitPvP: Status Enabled");
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-");
		loadCommands();
		loadListeners();
		loadConfigManager();
		for(Player player : Bukkit.getOnlinePlayers()) {
			getPlayerConfigManager().createPlayerConfiguration(player);
		}
	}
	
	@Override
	public void onDisable() {
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-");
		System.out.println("Plugin By FutureGraphics");
		System.out.println("KitPvP: Status Disabled");
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-");
	}
	
	public void loadCommands() {
		kitCommand = new KitCommand(this);
		getCommand("kit").setExecutor(kitCommand);
		
		kitPvPCommand = new KitPvPCommand(this);
		getCommand("kitpvp").setExecutor(kitPvPCommand);
		
		shopCommand = new ShopCommand(this);
		getCommand("shop").setExecutor(shopCommand);
		
		setTeleportCommand = new SetTeleportCommand(this);
		getCommand("setteleport").setExecutor(setTeleportCommand);
	}
	
	public void loadListeners() {
		joinListener = new JoinListener(this);
		inventoryClickListener = new InventoryClickListener(this);
		deathListener = new DeathListener(this);
		funktionen = new Funktionen(this);
	}
	
	public void loadConfigManager() {
		configManager = new ConfigManager(this);
		apiManager = new ApiManager(this);
		playerConfigManager = new PlayerConfigManager(this);
	}
	
	public SetTeleportCommand getSetTeleportCommand() {
		return setTeleportCommand;
	}
	
	public Funktionen getFunktionen() {
		return funktionen;
	}
	
	public DeathListener getDeathListener() {
		return deathListener;
	}
	
	public InventoryClickListener getInventoryClickListener() {
		return inventoryClickListener;
	}
	
	public ShopCommand getShopCommand() {
		return shopCommand;
	}
	
	public KitPvPCommand getKitPvPCommand() {
		return kitPvPCommand;
	}
	
	public KitCommand getKitCommand() {
		return kitCommand;
	}
	
	public PlayerConfigManager getPlayerConfigManager() {
		return playerConfigManager;
	}
	
	public JoinListener getJoinListener() {
		return joinListener;
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public ApiManager getApiManager() {
		return apiManager;
	}

}
