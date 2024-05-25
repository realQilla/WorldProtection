package net.qilla.worldprotection.Utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public enum SoundSystem {
    ACTION_NOT_ALLOWED {
        @Override
        public void play(Player player) {
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 0.75f, 1);
        }
    };

    public abstract void play(Player player);
}
