package fr.kyolo.restrictormod;

import java.util.List;

import fr.kyolo.restrictormod.restrictor.Restrictor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class RestrictorItem extends Item {

	private final String name;
	
	public RestrictorItem(){
		name = "restrictorItem";
		setUnlocalizedName(RestrictorMod.MODID+"_"+name);
		setTextureName(RestrictorMod.MODID+":itemRestrict");
		setMaxStackSize(1);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer plr, World p_77648_3_, int p_77648_4_,
			int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		
		if((Restrictor.isPlayerInOneGroup(plr.getDisplayName()))&&(!Restrictor.isPlayerInDefaultGroup(plr.getDisplayName()))){
			plr.addChatComponentMessage(new ChatComponentText("You already are in a group"));
			return false;
		}
		
		int groupNum = MathHelper.clamp_int(itemStack.getItemDamage(), 0, Restrictor.getGroupNumber());
		
		Restrictor.addPlayerToGroup(groupNum, plr.getDisplayName());
		Restrictor.removePlayerFromGroup(plr.getDisplayName(), "default");
		
		plr.inventory.consumeInventoryItem(itemStack.getItem());
		
		return false;
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer plr, List list, boolean p_77624_4_) {
		
		list.add("Linked to : "+Restrictor.getGroupByNumber(item.getItemDamage()).getGroupName());
		if((Restrictor.isPlayerInOneGroup(plr.getDisplayName()))&&(!Restrictor.isPlayerInDefaultGroup(plr.getDisplayName()))){
			list.add("You already are in a group");
		} else {
			list.add("Right click to use");
		}
		
		super.addInformation(item, plr, list, p_77624_4_);
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List lst) {
		for(int i = 0; i < Restrictor.getGroupNumber();i++){
			lst.add(new ItemStack(this, i));
		}
	}

}
