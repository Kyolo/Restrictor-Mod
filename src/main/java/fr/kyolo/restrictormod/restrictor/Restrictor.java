package fr.kyolo.restrictormod.restrictor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import fr.kyolo.restrictormod.ConfigUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class Restrictor {

    private static ArrayList<RestrictionGroup> groupList = new ArrayList<RestrictionGroup>();
    
    public static final String DEFAULT_NAME = "default";
	
	private Restrictor() {}
	
	public static void addGroup(RestrictionGroup gr){
		groupList.add(gr);
	}
	
	public static RestrictionGroup getGroupByPlayerName(String playerName){
		Iterator<RestrictionGroup> iter = groupList.iterator();
		
		while(iter.hasNext()){
			RestrictionGroup gr = iter.next();
			if(gr==null)
				continue;
			if(gr.containPlayer(playerName))
				return gr;
		}
		
		return null;
	}
	
	public static RestrictionGroup getGroupByName(String groupName){
		Iterator<RestrictionGroup> iter = groupList.iterator();
		
		while(iter.hasNext()){
			RestrictionGroup gr = iter.next();
			if(gr==null)
				continue;
			if(gr.getGroupName().equals(groupName))
				return gr;
		}
		
		return null;
	}
	
	public static void addPlayerToGroup(int grName, String plrName){
		RestrictionGroup gr = groupList.get(grName);
		ConfigUtil.addPlayerInConfig(gr.getGroupName(),plrName);
		groupList.get(grName).addPlayer(plrName);
	}
	
	public static boolean isPlayerInOneGroup(String playerName){
		return getGroupByPlayerName(playerName) != null;
	}
	
	public static boolean isPlayerInDefaultGroup(String playerName){
		return getGroupByPlayerName(playerName).getGroupName().equals(DEFAULT_NAME);
	}

	public static int getGroupNumber() {
		return groupList.size();
	}
	
	public static RestrictionGroup getGroupByNumber(int i){
		return groupList.get(i);
	}

	public static void addPlayerToGroup(String grName, String plrName) {
		for(int i = 0;i<groupList.size();i++){
			if(groupList.get(i).getGroupName().equals(grName)){
				addPlayerToGroup(i, plrName);
				return;
			}
		}
		
	}

	public static void removePlayerFromGroup(String plrName, String groupName) {
		getGroupByName(groupName).removePlayer(plrName);
	}
	
	public static void movePlayerGroup(String plrName, String oldGroup, String newGroup){
		removePlayerFromGroup(plrName, oldGroup);
		addPlayerToGroup(newGroup, plrName);
	}
	
	

}
