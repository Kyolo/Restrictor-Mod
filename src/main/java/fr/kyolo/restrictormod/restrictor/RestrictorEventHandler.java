package fr.kyolo.restrictormod.restrictor;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import fr.kyolo.restrictormod.ConfigUtil;
import fr.kyolo.restrictormod.RestrictorMod;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class RestrictorEventHandler {

	@SubscribeEvent
	public void onTryPickup(EntityItemPickupEvent event){
		if(event.entityPlayer.capabilities.isCreativeMode)
			return;
		
		String itemName = event.item.getEntityItem().getUnlocalizedName();
		String playerName = event.entityPlayer.getDisplayName();
		
		RestrictionGroup group = Restrictor.getGroupByPlayerName(playerName);
		
		if(group==null)
			group = Restrictor.getGroupByName(Restrictor.DEFAULT_NAME);
		
		if(!group.isAllowed(event.item.getEntityItem().getItem()))
			event.setCanceled(true);
		
		if(ConfigUtil.isDebug()){
			RestrictorMod.log.info(playerName + " tried to pickup "+event.item.getEntityItem().getMaxStackSize() + "x "
					+ itemName + (group.isAllowed(event.item.getEntityItem().getItem()) ? "" : " but couldn't"));
		}
		
	}
	
	@SubscribeEvent
	public void playerTick(PlayerTickEvent event){
		if(event.player.capabilities.isCreativeMode)
			return;
		
		if(event.player.worldObj.isRemote)
			return;
		
		String playerName = event.player.getDisplayName();
		
		RestrictionGroup group = Restrictor.getGroupByPlayerName(playerName);
		
		if(group==null)
			group = Restrictor.getGroupByName(Restrictor.DEFAULT_NAME);
		
		ItemStack[] inventory = event.player.inventory.mainInventory;
		
		for(int i = 0;i<inventory.length;i++){
			
			ItemStack item = inventory[i];
			
			if(item==null)
				continue;
			
			if(!group.isAllowed(item.getItem())){
				
				for(int j = 0;j<item.stackSize;j++){
					event.player.dropItem(item.getItem(), 1);
					event.player.inventory.consumeInventoryItem(item.getItem());
				}
			}
		}
		
	}
	
	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event){
		
		if(!Restrictor.isPlayerInOneGroup(event.entityPlayer.getDisplayName()))
			return;
		
		RestrictionGroup group = Restrictor.getGroupByPlayerName(event.entityPlayer.getDisplayName());
		
		if(!group.isAllowed(event.itemStack.getItem())){
			event.toolTip.add("You can't have this");
		}
		
	}

}
