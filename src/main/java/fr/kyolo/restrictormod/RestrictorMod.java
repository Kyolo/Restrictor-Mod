package fr.kyolo.restrictormod;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import fr.kyolo.restrictormod.restrictor.RestrictorCommand;
import fr.kyolo.restrictormod.restrictor.RestrictorEventHandler;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = RestrictorMod.MODID, version = RestrictorMod.VERSION, name = RestrictorMod.NAME)
public class RestrictorMod
{
    public static final String MODID = "restrictormod";
    public static final String VERSION = "0.1";
    public static final String NAME = "Restrictor Mod";
    
    @Instance(MODID)
    public static RestrictorMod modInstance;
    
    private static RestrictorEventHandler handler; 
    
    public static Logger log = null;
    
    public static Item restrictorItem = null;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event){
    	log = event.getModLog();
    	
    	log.info("Loading Configuration");
    	ConfigUtil.loadConfig(event.getSuggestedConfigurationFile(),event.getModConfigurationDirectory());
    	
    	log.info("Creating Event Handlers");
    	handler = new RestrictorEventHandler();
    	MinecraftForge.EVENT_BUS.register(handler);
    	FMLCommonHandler.instance().bus().register(handler);
    	
    	log.info("Creating items");
    	restrictorItem = new RestrictorItem();
    	
    	log.info("Item registration");
    	GameRegistry.registerItem(restrictorItem, "restrictorItem");
    	
    }
    
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event){
      event.registerServerCommand(new RestrictorCommand());
    }
}
