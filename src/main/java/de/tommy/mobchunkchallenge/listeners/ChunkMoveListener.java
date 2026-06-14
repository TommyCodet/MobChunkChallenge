package de.tommy.mobchunkchallenge.listeners;

import de.tommy.mobchunkchallenge.MobChunkChallenge;
import de.tommy.mobchunkchallenge.managers.ChunkManager;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.UUID;

public class ChunkMoveListener implements Listener {

    private final ChunkManager manager;
    private final Random random = new Random();

    private final EntityType[] mobs = {
            EntityType.ZOMBIE,
            EntityType.SKELETON,
            EntityType.CREEPER,
            EntityType.SPIDER,
            EntityType.ENDERMAN,
            EntityType.HUSK,
            EntityType.STRAY,
            EntityType.WITCH,
            EntityType.PILLAGER,
            EntityType.VINDICATOR
    };

    public ChunkMoveListener(ChunkManager manager) {
        this.manager = manager;

        new BukkitRunnable() {
            @Override
            public void run() {
                drawBorders();
            }
        }.runTaskTimer(
                MobChunkChallenge.getInstance(),
                20L,
                20L
        );
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        if (event.getTo() == null) return;

        Chunk from = event.getFrom().getChunk();
        Chunk to = event.getTo().getChunk();

        if (from.equals(to)) return;

        UUID uuid = manager.getMob(from);

        if (uuid != null) {

            Entity mob = Bukkit.getEntity(uuid);

            if (mob != null && !mob.isDead()) {

                event.setTo(event.getFrom());

                event.getPlayer().sendActionBar(
                        "§cTöte zuerst das Mob dieses Chunks!"
                );

                return;
            }
        }

        if (!manager.hasMob(to)) {

            EntityType type = mobs[random.nextInt(mobs.length)];

            LivingEntity entity = (LivingEntity)
                    to.getWorld().spawnEntity(
                            event.getPlayer().getLocation(),
                            type
                    );

            entity.setCustomName("§cChunk Mob");
            entity.setCustomNameVisible(true);

            manager.setMob(to, entity);

            event.getPlayer().sendMessage(
                    "§eEin " + type.name() + " ist erschienen!"
            );
        }
    }

    private void drawBorders() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            Chunk chunk = player.getLocation().getChunk();

            if (!manager.hasMob(chunk))
                continue;

            int minX = chunk.getX() << 4;
            int minZ = chunk.getZ() << 4;

            World world = player.getWorld();

            for (int i = 0; i < 16; i++) {

                world.spawnParticle(
                        Particle.END_ROD,
                        minX + i,
                        player.getY(),
                        minZ,
                        1
                );

                world.spawnParticle(
                        Particle.END_ROD,
                        minX + i,
                        player.getY(),
                        minZ + 16,
                        1
                );

                world.spawnParticle(
                        Particle.END_ROD,
                        minX,
                        player.getY(),
                        minZ + i,
                        1
                );

                world.spawnParticle(
                        Particle.END_ROD,
                        minX + 16,
                        player.getY(),
                        minZ + i,
                        1
                );
            }
        }
    }
}
