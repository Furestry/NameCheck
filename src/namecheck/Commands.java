package namecheck;

import java.io.File;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

public class Commands implements CommandExecutor{

	private Main plugin;
	
	private HashMap<Integer, String> newtext = new HashMap<Integer, String>();	
	private HashMap<Integer, String> newperm = new HashMap<Integer, String>();

	public Commands(Main plugin) {
	
	this.plugin = plugin;
	
	}

	@Override
	public boolean onCommand(CommandSender sender,  Command cmd,  String label,
		 String[] args) {		
		
		cmd.setDescription("§aПерезагружает конфиг плагина.");
		cmd.setUsage(" Используйте /namecheck reload");
	
		if(!sender.hasPermission("hidename.command")) {
		
			sender.sendMessage("§cУ вас недостаточно прав для использования данной команды!");
			return true;
		}
	
		if(args.length < 0) {
			return false;
		}
	
		if(args.length > 1) {
			return false;
		}
	
		if(args[0].equalsIgnoreCase("reload")) {
		
			try {
			
				plugin.getDataFolder().mkdirs();
			
				if (!new File(plugin.getDataFolder() + File.separator + "config.yml").exists()) {
				
					plugin.getConfig().options().copyDefaults(true);
					plugin.saveDefaultConfig();
					plugin.getLogger().info("Конфиг не обнаружен, поэтому создан новый!");
					sender.sendMessage("§aКонфиг не обнаружен, поэтому создан новый!");
				
				}
				
				plugin.reloadConfig();
				
				Main.setMsg(plugin.getConfig().getString("text"));
				Main.setCustom_Text(plugin.getConfig().getBoolean("custom_text"));
				Main.setVault(plugin.getConfig().getBoolean("vault"));
				Main.setEconomy(plugin.getConfig().getBoolean("economy"));
				if(Main.getCustom_Text()) {
					ConfigurationSection ct = plugin.getConfig().getConfigurationSection("custom");
					int nextKey = (ct == null)?0:ct.getKeys(false).size();
					for(int k = 0; k <= nextKey; k ++) {
					
						Main.getCPerm().remove(k);
						Main.getCText().remove(k);
						newtext.put(k, plugin.getConfig().getString("custom." + k + ".text"));
						newperm.put(k, plugin.getConfig().getString("custom." + k + ".perm"));
					
					}
				}
				
				Main.setCText(newtext);
				Main.setCPerm(newperm);
				
				plugin.saveDefaultConfig();
				
				sender.sendMessage("§aКонфиг успешно перезагружен!");
			
			} catch (Exception e) {
			
				plugin.getLogger().info("Не удалось перезагрузить конфиг. Ошибка - " + e);
				plugin.getLogger().info("Проверьте синтаксис файла.");
				sender.sendMessage("§cНе удалось перезагрузить конфиг. Смотрите ошибку в консоли.");
			
			}
			
			return true;
		}

	
		return true;
	}

}
