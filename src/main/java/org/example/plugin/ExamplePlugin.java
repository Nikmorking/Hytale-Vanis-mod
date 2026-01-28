package org.example.plugin;

import java.util.List;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import org.hytaledevlib.lib.EntityHelper;
import org.hytaledevlib.lib.EventHelper;
import org.hytaledevlib.lib.WorldHelper;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.entity.Entity;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;


/**
 * This class serves as the entrypoint for your plugin. Use the setup method to register into game registries or add
 * event listeners.
 */
public class ExamplePlugin extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    private String user_name = new String();

    private Item last_item = new Item("Rock_Crystal_Green_Large");


    public ExamplePlugin(@Nonnull JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Hello from " + this.getName() + " version " + this.getManifest().getVersion().toString());
    }

    @Override
    protected void setup() {
        //LOGGER.atInfo().log(player.toString());
        LOGGER.atInfo().log("Setting up plugin " + this.getName());
        EventHelper.onItemDrop(this, (itemId, quantity) -> 
        {
            getLogger().at(Level.INFO).log("[Drop] " + quantity + "x " + itemId);
            last_item = new Item(itemId);
        });
        LOGGER.atInfo().log("Setting up plugin without external libs...");

        this.getCommandRegistry().registerCommand(new ExampleCommand(this.getName(), this.getManifest().getVersion().toString()));
        this.getCommandRegistry().registerCommand(new ExampleHood(this.getName(), this.getManifest().getVersion().toString()));

        EventHelper.onPlayerChat(this, (username, message) ->
        {
            World firstWorld = GetWorld();
            LOGGER.atInfo().log(firstWorld.toString());
            getLogger().at(Level.INFO).log(username + " said: " + message);
            if(message.equals("Каска приди"))
                {
                    if (last_item.getId().equals("Armor_Thorium_Head")) {
                        getLogger().at(Level.INFO).log(username + " said: " + message);
                        WorldHelper.broadcastMessage(firstWorld, Message.raw("Касочник скоро предёт! Иди на спавн"));
                        WorldHelper.waitTicks(firstWorld, 960, () -> 
                        {
                            WorldHelper.broadcastMessage(firstWorld, Message.raw("Он прищёл!"));
                            WorldHelper.setDayTime(firstWorld, 0.825);
                            Vector3d spawnPos = EntityHelper.getPlayerRespawnPosition(GetPlayer(firstWorld)).getPosition(); 
                            Entity cow = EntityHelper.spawnNPC(firstWorld, "Cow", spawnPos);
                            if (cow != null) {
                                WorldHelper.log(firstWorld, "Successfully spawned a Cow!");
                            }
                        });
                    }
                    else{
                        WorldHelper.broadcastMessage(firstWorld, Message.raw("Чтобы ОН прищёл, нужно кинуть зелёный шлем"));
                        
                    }
            }
                    
        });
    }

    public static Entity GetPlayer(World firstWorld) {
        Entity player = new Entity() {};
         List<Entity> all = EntityHelper.getAllEntities(firstWorld);
                            for (Entity elem : all) {
                                if(EntityHelper.isPlayer(elem))
                                {
                                    player = elem;
                                }
                            }
        return player;
    }
    public static World GetWorld(){
        var ss = Universe.get().getWorlds();
        var firstWorld = ss.values().iterator().next();
        return firstWorld;
    }
}

















