package de.tommy.mobchunkchallenge.managers;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChunkManager {

    private final Map<String, UUID> chunkMobs = new HashMap<>();

    private String key(Chunk chunk) {
        return chunk.getWorld().getName()
                + ":" + chunk.getX()
                + ":" + chunk.getZ();
    }

    public boolean hasMob(Chunk chunk) {
        return chunkMobs.containsKey(key(chunk));
    }

    public UUID getMob(Chunk chunk) {
        return chunkMobs.get(key(chunk));
    }

    public void setMob(Chunk chunk, Entity entity) {
        chunkMobs.put(key(chunk), entity.getUniqueId());
    }

    public void removeMob(Chunk chunk) {
        chunkMobs.remove(key(chunk));
    }
}
