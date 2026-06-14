package de.tommy.mobchunkchallenge.listeners;

import de.tommy.mobchunkchallenge.managers.ChunkManager;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class MobDeathListener implements Listener {

    private final ChunkManager manager;

    public MobDeathListener(ChunkManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {

        Entity dead = event.getEntity();

        Chunk chunk = dead.getLocation().getChunk();

        UUID uuid = manager.getMob(chunk);

        if (uuid == null) return;

        if (uuid.equals(dead.getUniqueId())) {

            manager.removeMob(chunk);
        }
    }
}
