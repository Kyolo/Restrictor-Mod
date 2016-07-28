package fr.kyolo.restrictormod;

import java.io.File;
import java.util.HashMap;

import fr.kyolo.restrictormod.restrictor.RestrictionGroup;
import fr.kyolo.restrictormod.restrictor.Restrictor;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigUtil {

	private ConfigUtil() {}

	
	/**
	 * The config object
	 */
	private static Configuration conf = null;
	
	/**
	 * The directory where the groups' configurations are stored
	 */
	private static String configDir = null;

	/**
	 * The names of all the groups
	 */
	private static String[] groupNameList = null;
	
	/**
	 * Contains all configuration for the mod
	 */
	public static final HashMap<String, String> config = new HashMap<String, String>();
	
	private static boolean debug = false;
	
	public static void loadConfig(File suggestedConfigurationFile, File modConfigurationDirectory) {
		
		configDir = modConfigurationDirectory.getAbsolutePath();//We store the directory containing the groups' config
		
		if(!configDir.endsWith("/"))
			configDir+="/";
		
		conf = new Configuration(suggestedConfigurationFile);
		
		conf.load();
		
		groupNameList = conf.get("Restrictor", "GroupList", new String[]{"default"}, "A name containing the name of all groups").getStringList();
		
		loadAllGroupConfigs(groupNameList, modConfigurationDirectory);//We load the groups config
		
		loadOtherConfig();//And then the other, may need to be switched with loadAllGroupConfigs
		
		conf.save();
	}
	
	
	private static void loadOtherConfig() {
		debug = conf.get("Restrictor","debug",false).getBoolean();
		
	}

	private static void loadAllGroupConfigs(String[] nameList, File directory){
		String dir = directory.getAbsolutePath();//Just to avoid problem
		
		if(!dir.endsWith("/"))
			dir+="/";
		
		for(int i = 0; i < nameList.length; i++){
			loadGroupConfig(new Configuration(new File(dir+"Restrictor/"+nameList[i]+".cfg")));
		}
	}
	
	/**
	 * Load the configuration in config 
	 * @param config : configuration object containing the information
	 */
	private static void loadGroupConfig(Configuration config){
		config.load();
		
		String name = config.get("Info", "name", "none").getString();
		
		String[] players = config.get("Info", "players", new String[]{}, "All players in the group").getStringList();
		
		String[] bannedMods = config.get("BannedFeatures", "mods", new String[]{},"The banned mods of this groups").getStringList();
		
		String[] bannedItems = config.get("BannedFeatures", "items", new String[]{},"The banned items of this groups").getStringList();
		
		String[] exceptionItems = config.get("Exceptions","items",new String[]{}).getStringList();
		
		config.save();
		
		RestrictionGroup res = new RestrictionGroup(name);
		
		for(String plName: players){
			res.addPlayer(plName);
		}
		
		for(String bannedMod: bannedMods){
			res.addBannedMod(bannedMod);
		}
		
		for(String bannedItem: bannedItems){
			res.addBannedMod(bannedItem);
		}
		
		for(String exceptionItem: exceptionItems){
			res.addException(exceptionItem);
		}
		
		Restrictor.addGroup(res);
	}
	
	public static boolean isDebug(){
		return debug;
	}


	public static void addPlayerInConfig(String groupName, String plrName) {
		Configuration config = new Configuration(new File(configDir+"Restrictor/"+groupName+".cfg"));
		config.load();
		
		Property prop = config.get("Info", "players", new String[]{});
		String[] old = prop.getStringList();
		String[] replace = new String[old.length+1];
		
		for(int i = 0;i<old.length;i++){
			replace[i]=old[i];
		}
		
		replace[old.length]=plrName;
		prop.set(replace);
		
	}

}
