package namecheck;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

public class VaultManager {

private static  Chat capi;

private static  Economy api;
	
	public static void initChat() {
		
		RegisteredServiceProvider<Chat> provider = Bukkit.getServicesManager().getRegistration(Chat.class);
		
		if (provider != null) {
			
		    capi = provider.getProvider();
		}
	}
	
	public static void initEconomy() {
		
		RegisteredServiceProvider<Economy> provider = Bukkit.getServicesManager().getRegistration(Economy.class);
		
		if (provider != null) {
			
		    api = provider.getProvider();
		}
	}
	
	public static String getPref(Player player) {
		
		return capi.getPlayerPrefix(player);
		
	}
	
	public static String getSuff(Player player) {
		
		return capi.getPlayerSuffix(player);
		
	}
	
	public static Double getBal(Player player) {
		
		return api.getBalance(player, player.getWorld().toString());
		
	}
	
}
