package future.graphics.configmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import future.graphics.main.main;

public class ApiManager {

	@SuppressWarnings("unused")
	private main plugin;
	
	public ApiManager(main plugin) {
		this.plugin = plugin;
	}
	
	void getList(ItemMeta itemmeta, String... lore) {
		List<String> loreList = new ArrayList<String>();
		for (String lores : lore) {
			loreList.add(lores);
		}
		itemmeta.setLore(loreList);
	}
	
	public ItemStack createSkullItem(String owner, int amount, String name, String... lore) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, amount);
		SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
		skullmeta.setDisplayName(name);
		skullmeta.setOwner(owner);
		getList(skullmeta, lore);
		skull.setDurability((short)3);
		skull.setItemMeta(skullmeta);
		return skull;
	}
	
	public ItemStack createItem(Material material,String name, int amount, int alias, Enchantment enchantment, int entchantlvl, String... lore) {
		if(enchantment == null || entchantlvl == 0) {
			ItemStack item = new ItemStack(material, amount, (short) alias);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(name);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			getList(meta, lore);
			item.setItemMeta(meta);
			return item;
		} else {
			ItemStack item = new ItemStack(material, amount, (short) alias);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(name);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addEnchant(enchantment, entchantlvl, true);
			getList(meta, lore);
			item.setItemMeta(meta);
			return item;
		}
	}
	
    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }
	
	public ItemStack createPotion(String name, int amount,PotionEffectType mainEffect, PotionEffect... effekt) {
		ItemStack item = new ItemStack(Material.POTION, amount);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.setDisplayName(name);
		meta.setMainEffect(mainEffect);
		for(PotionEffect effect : effekt) {
			meta.addCustomEffect(effect, true);
		}
		
		item.setItemMeta(meta);
		return item;
	}
	
	public PotionEffect createPotionEffect(int Timer, int Strengh, PotionEffectType effect) {
		return new PotionEffect(effect, 20*Timer, Strengh);
	}
	
	public ItemStack createLeatherArmor(Material material, String name, Color color, int amount, Enchantment enchantment, int lvl, String... lore ) {
		if(enchantment == null || lvl == 0) {
			
			ItemStack item = new ItemStack(material, amount);
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(color);
			meta.setDisplayName(name);
			getList(meta, lore);
			item.setItemMeta(meta);
			return item;
			
		} else {

			
			ItemStack item = new ItemStack(material, amount);
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(color);
			meta.setDisplayName(name);
			meta.addEnchant(enchantment, lvl, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			getList(meta, lore);
			item.setItemMeta(meta);
			return item;
		}
	}

}
