package namecheck;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import nms.V1_12;
import nms.V1_13;
import nms.V1_14;
import nms.V1_15;

public class Main extends JavaPlugin {
	
	public static NMS nms;
	
	private String v = version();
	
	private static boolean vault;	
	private static boolean economy;
	private static boolean custom_text;
	
	private static String msg;
	
	private static HashMap<Integer,String> cperm = new HashMap<Integer, String>();
	private static HashMap<Integer,String> ctext = new HashMap<Integer, String>();
	
	public void onEnable(){
		
		File file = new File(getDataFolder() + File.separator + "config.yml");
		
		getVersion(v);
		
		getCommand("namecheck").setExecutor(new Commands(this));		
		getCommand("namecheck").setTabCompleter(new TabComplete());
		
		if (!file.exists()) {
			
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
			getLogger().info("Создан новый конфиг");
			
		}
		
		FileConfiguration conf = getConfig();
		
		Bukkit.getPluginManager().registerEvents(new Handler(this), this);
		
		try {
			msg = conf.getString("text");
			
			vault = conf.getBoolean("vault");
			custom_text = conf.getBoolean("custom_text");
			economy = conf.getBoolean("economy");
			
			if(custom_text) {
				
				ConfigurationSection ct = getConfig().getConfigurationSection("custom");
				int nextKey = (ct == null)?0:ct.getKeys(false).size();
				for(int k = 0; k <= nextKey; k ++) {
					
					ctext.put(k, getConfig().getString("custom." + k + ".text"));
					cperm.put(k, getConfig().getString("custom." + k + ".perm"));
					
				}
				
			}
		} catch (Exception e) {
			
			getLogger().info("Не удалось загрузить конфиг. Ошибка - " + e);
			getLogger().info("Проверьте синтаксис файла");
			
		}
		
		if(economy) {
			
			VaultManager.initEconomy();
			
		}
		
		if(vault) {
			
			VaultManager.initChat();
			
		}
		
	}
	
	public void onDisable() {
		
		getLogger().info("Disable");
		
	}
	
	public static boolean getCustom_Text() {
		
		return custom_text;
		
	}
	
	public static void setCustom_Text(boolean custom_text) {
		
		Main.custom_text = custom_text;
		
	}
	
	public static HashMap<Integer, String> getCPerm() {
		
		return cperm;
		
	}

	public static void setCPerm(HashMap<Integer, String> cperm) {
	
		Main.cperm = cperm;
	
	}
	
	public static HashMap<Integer, String> getCText() {
		
		return ctext;
		
	}

	public static void setCText(HashMap<Integer, String> ctext) {
	
		Main.ctext = ctext;
	
	}
	
	
	public static String getMsg() {
		
		return msg;
		
	}
	
	public static void setMsg(String msg) {
		
		Main.msg = msg;
		
	}
	
	public static boolean getVault() {
		
		return vault;
		
	}
	
	public static void setVault(boolean vault) {
		
		Main.vault = vault;
	
	}
	
	public static boolean getEconomy() {
		
		return economy;
		
	}
	
	public static void setEconomy(boolean economy) {
		
		Main.economy = economy;
	
	}
	
	private static String version() {
		
		String p = Bukkit.getServer().getClass().getPackage().getName();
		return p.substring(p.lastIndexOf('.') + 1);
		
	}
	
	private void getVersion(String v) {
		
		switch(v) {
		
			case "v1_12_R1":
				nms = new V1_12();
				break;
			case "v1_12_R2":
				nms = new V1_12();
				break;
			case "v1_13_R1":
				nms = new V1_13();
				break;
			case "v1_13_R2":
				nms = new V1_13();
				break;
			case "v1_14_R1":
				nms = new V1_14();
				break;
			case "v1_14_R2":
				nms = new V1_14();
				break;
			case "v1_14_R3":
				nms = new V1_14();
				break;
			case "v1_14_R4":
				nms = new V1_14();
				break;
			case "v1_15_R1":
				nms = new V1_15();
				break;
			case "v1_15_R2":
				nms = new V1_15();
				break;
			default:
				Bukkit.getPluginManager().disablePlugin(this);
				return;
				
		}
	}

}
