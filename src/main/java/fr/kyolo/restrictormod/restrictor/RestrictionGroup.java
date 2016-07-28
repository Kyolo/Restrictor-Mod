package fr.kyolo.restrictormod.restrictor;

import java.util.HashSet;

import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.registry.GameRegistry;
import fr.kyolo.restrictormod.Util;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;

public class RestrictionGroup {
	
	private final String groupName;
	/**
	 * A set containing all the player belonging to this group
	 */
	private HashSet<String> player;

	/**
	 * The sets containing banned things for players in this group
	 */
	private HashSet<String> bannedMod;
	private HashSet<String> bannedItems;
	
	/**
	 * Set containing item allowed, even if they are in the banned mod list
	 */
	private HashSet<String> exceptionItems;
	
	public RestrictionGroup(String name) {
		this.groupName = name;
		player  = new HashSet<String>();
		bannedMod = new HashSet<String>();
		bannedItems = new HashSet<String>();
		bannedItems = new HashSet<String>();
		exceptionItems = new HashSet<String>();
	}
	
	public void addPlayer(String playerName){
		player.add(playerName);
	}
	
	public void removePlayer(String playerName){
		player.remove(playerName);
	}
	
	public void addBannedMod(String modid){
		bannedMod.add(modid);
	}
	
	public void addBannedItem(String unlocalizedItemName){
		bannedItems.add(unlocalizedItemName);
	}
	
	public void addException(String unlocalizedItemName){
		exceptionItems.add(unlocalizedItemName);
	}
	
	public boolean isBanned(Item item){
		
		String modid = Util.getModid(item);
		
		if(bannedMod.contains(modid))
			return true;
		
		if(bannedItems.contains(item.getUnlocalizedName()))
			return true;
		
		return false;
	}
	
	public boolean isAllowed(Item item){
		
		if(exceptionItems.isEmpty()){
			if(exceptionItems.contains(item.getUnlocalizedName()))
				return true;
		}
		
		return !isBanned(item);
		
	}

	
	public boolean containPlayer(String playerName){
		return player.contains(playerName);
	}
	
	public String getGroupName(){
		return groupName;
	}
	
	@Override
	public boolean equals(Object other){
		if(other==null)
			return false;
		
		if(!(other instanceof RestrictionGroup))
			return false;
		
		RestrictionGroup otherGr = (RestrictionGroup)other;
		
		if(!(groupName.equals(otherGr.getGroupName())))
				return false;
		
		return true;
	}

}
