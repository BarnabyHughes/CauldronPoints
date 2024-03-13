package me.barnaby.cauldronpoints.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.UUID;

public class ThrowItemListener implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Item thrownItem = event.getItemDrop();
        Location itemLocation = thrownItem.getLocation();
        Player player = event.getPlayer();

        // Check if the item is close to a cauldron of lava
        if (isNearLavaCauldron(itemLocation)) {
            // The item was thrown into a cauldron of lava
            // Your code here
            player.sendMessage("testing");
        }
        else player.sendMessage("testing no");
    }

    private boolean isNearLavaCauldron(Location location) {
        int radius = 1; // Adjust the radius as needed

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location blockLocation = location.clone().add(x, y, z);
                    Block block = blockLocation.getBlock();

                    if (block.getType() == Material.CAULDRON && block.getData() == 1) {
                        // Check if the cauldron is filled with lava
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof Item) {
            Item item = (Item) event.getEntity();
            UUID itemUUID = item.getUniqueId();

            System.out.println("test");
            // Check if the changed block is a cauldron filled with lava
            if (event.getTo() == Material.CAULDRON && event.getBlock().getData() == 1) {
                // Check if the item was previously thrown by a player
                Player throwingPlayer = Bukkit.getPlayer(item.getThrower());
                if (throwingPlayer != null) {
                    throwingPlayer.sendMessage("Item thrown into a cauldron of lava!");
                }

                // Optionally, remove the item entity or handle it in some way
                item.remove();
            }
        }
    }


}
