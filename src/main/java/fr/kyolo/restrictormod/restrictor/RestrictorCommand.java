package fr.kyolo.restrictormod.restrictor;

import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class RestrictorCommand implements ICommand {

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCommandName() {
		return "restrictor";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		if(sender.getCommandSenderName().equals("Rcon"))
			return "restrictor <name> <group>";
		
		return "/restrictor [name] <group>";
	}

	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(sender.getCommandSenderName().equals("Rcon")){
			if(args.length!=2){
				sender.addChatMessage(new ChatComponentText("Wrong arguments").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
				return;
			}
			
			if(!Restrictor.isPlayerInOneGroup(args[0])||Restrictor.isPlayerInDefaultGroup(args[0])){
				Restrictor.addPlayerToGroup(args[1], args[0]);
				Restrictor.removePlayerFromGroup(args[0],Restrictor.DEFAULT_NAME);
				return;
			}
			
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

}
