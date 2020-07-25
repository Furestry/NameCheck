package namecheck;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Handler implements Listener {
	
	private String msg;
	
	private HashMap<Integer, String> cperm;		
	private HashMap<Integer, String> ctext;
	
	private Main plugin;

	public Handler(Main plugin) {
	
		this.plugin = plugin;
	
	}
	
	@EventHandler
	
	public void Click(PlayerInteractEntityEvent e) {
		int i = 0;
		Player p = e.getPlayer();
		Entity ent = e.getRightClicked();
		
		if(ent instanceof Player) {
			if(p.hasPermission("namecheck")) {
				if(Main.getCustom_Text()) {
					cperm = Main.getCPerm();
					ctext = Main.getCText();
					msg = Main.getMsg();
					ConfigurationSection ct = plugin.getConfig().getConfigurationSection("custom");
					int nextKey = (ct == null)?0:ct.getKeys(false).size();
					for(int k = 0; k < nextKey; k ++) {
						if(ent.hasPermission(cperm.get(k))) {
							msg = ctext.get(k);
							sendAction(p, ent, msg);
							break;
						} else {
							i++;
						}
					}
					
					if(i == nextKey) {
						sendAction(p, ent, msg);
					}
				}
			
				else {
					
					sendAction(p,ent,Main.getMsg());
				}
				
			}
			
		}
	}
	
	private void sendAction(Player p, Entity ent, String msg) {
		
		if(Main.getVault()) {			
			if(Main.getEconomy()) {
				if(msg.contains("%balance%")) {
					try {
						msg = msg.replace("%balance%", "" + VaultManager.getBal(((Player) ent).getPlayer()));
					} catch (Exception e1) {
						msg = msg.replace("%balance%", "");
						plugin.getLogger().info("Возникла ошибка при считывании баланса! Есть ли он вообще?");
					}
				}				
			}
			if(msg.contains("%player%")){
				msg = msg.replace("%player%", ent.getName());
			} 
			if(msg.contains("%prefix%")) {
				try {
					msg = msg.replace("%prefix%", VaultManager.getPref(((Player) ent).getPlayer()));
				} catch (Exception e1) {
					msg = msg.replace("%prefix%", "");
					plugin.getLogger().info("Возникла ошибка при считывании префикса! Есть ли он вообще?");
				}
			}
			if(msg.contains("%suffix%")) {
				try {
					msg = msg.replace("%suffix%", VaultManager.getSuff(((Player) ent).getPlayer()));
				} catch (Exception e1) {
					msg = msg.replace("%suffix%", "");
					plugin.getLogger().info("Возникла ошибка при считывании суффикса! Есть ли он вообще?");
				}
			}
			if(msg.contains("%hearts%")) {
				msg = msg.replace("%hearts%", "" + ((Player) ent).getHealth());
			}
			if(msg.contains("%&%")) {
				msg = msg.replace("%&%", "&");
			}
			else if(msg.contains("&")) {
				msg = msg.replace("&", "§");
			}
			if(msg.contains("''")) {
				msg = msg.replace("''", "\"");
			}
			Main.nms.sendActionBar(p, msg);
		}		
	}
	
}
