package net.qilla.worldprotection.GeneralProtection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class OtherProtection implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.getCause().equals(WeatherChangeEvent.Cause.NATURAL)) event.setCancelled(true);
    }
}
