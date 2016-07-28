package fr.kyolo.restrictormod;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Util {

	private Util() {}
	
	public static String getModid(Item item){
		GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(item);
		
		return id == null || id.modId.equals("") ? "minecraft" : id.modId;
	}
	
	public static String getModid(ItemStack item){
		return getModid(item.getItem());
	}
	
	public static String getModid(String unlocalizedName){
		//return getModid((Item)GameData.getItemRegistry().getObject(unlocalizedName));
		return getModid(Item.getItemById(GameData.getItemRegistry().getId(unlocalizedName)));
	}

}
