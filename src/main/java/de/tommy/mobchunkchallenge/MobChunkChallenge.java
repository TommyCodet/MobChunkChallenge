package de.tommy.mobchunkchallenge;

import de.tommy.mobchunkchallenge.listeners.ChunkMoveListener;
import de.tommy.mobchunkchallenge.listeners.MobDeathListener;
import de.tommy.mobchunkchallenge.managers.ChunkManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MobChunkChallenge extends JavaPlugin {

    private static MobChunkChallenge instance;
    private ChunkManager chunkManager;

    @Override
    public void onEnable() {

        instance = this;

        chunkManager = new ChunkManager();

        getServer().getPluginManager().registerEvents(
                new ChunkMoveListener(chunkManager),
                this
        );

        getServer().getPluginManager().registerEvents(
                new MobDeathListener(chunkManager),
                this
        );
    }

    public static MobChunkChallenge getInstance() {
        return instance;
    }
}
