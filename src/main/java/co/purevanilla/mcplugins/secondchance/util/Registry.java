package co.purevanilla.mcplugins.secondchance.util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.time.Instant;

public class Registry {

    Plugin plugin;

    public Registry(Plugin plugin){
        this.plugin = plugin;
    }

    public Plugin getPlugin(){
        return this.plugin;
    }

    public FileConfiguration getConfig(){
        return this.getPlugin().getConfig();
    }

    public boolean hadSecondChance(Player player) throws Exception {

        ConfigurationSection sec = this.getConfig().getConfigurationSection("players.used");
        if (sec != null) {
            if(sec.getKeys(false).contains(player.getUniqueId().toString())){
                long deathEpoch = this.getConfig().getLong("players.used."+player.getUniqueId().toString());
                long now = Instant.now().toEpochMilli();
                if((now-deathEpoch)>(604800*1000)){
                    // more than a week ago
                    return false;
                } else {
                    // less than a week ago
                    return true;
                }
            } else {
                return false;
            }
        } else {
            throw new Exception("Unknown config file");
        }

    }

    public boolean markPlayer(Player player){
        long now = Instant.now().toEpochMilli();
        this.getConfig().set("players.used."+player.getUniqueId().toString(),now);
        this.getPlugin().saveConfig();
        return true;
    }

}
