package top.dragonte.keepfoodlevel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;

public class Keepfoodlevel implements ModInitializer {
HashMap<String,Integer> foodMap = new HashMap<>();
    @Override
    public void onInitialize() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, reason) -> {
            if (entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) entity;
                foodMap.put(player.getName().getString(), player.getHungerManager().getFoodLevel());
            }
        });
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, reason) -> {
            if (foodMap.containsKey(newPlayer.getName().getString())) {
                newPlayer.getHungerManager().setFoodLevel(Math.max(foodMap.get(newPlayer.getName().getString()), 7));
                foodMap.remove(newPlayer.getName().getString());
            }
        });
    }
}
