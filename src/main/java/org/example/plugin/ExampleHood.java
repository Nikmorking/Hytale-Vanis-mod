package org.example.plugin;

import javax.annotation.Nonnull;

import org.hytaledevlib.lib.EntityHelper;

import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.Entity;
import com.hypixel.hytale.server.core.universe.world.World;

/**
 * This is an example command that will simply print the name of the plugin in chat when used.
 */
public class ExampleHood extends CommandBase {


    public ExampleHood(String pluginName, String pluginVersion) {
        super("start", "Hello WalkerWorld !");
        this.setPermissionGroup(GameMode.Adventure); // Allows the command to be used by anyone, not just OP
    }

    @Override
    protected void executeSync(@Nonnull CommandContext ctx) {
        World world = ExamplePlugin.GetWorld();
        ctx.sendMessage(Message.raw("Hello WalkerWorld !"));
        Entity player = ExamplePlugin.GetPlayer(world);
        ctx.sendMessage(Message.raw(world.toString()+player.toString()));
        EntityHelper.spawnNPC(world, "Cow", EntityHelper.getPosition(player));
    }
}
