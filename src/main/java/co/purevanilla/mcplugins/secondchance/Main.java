package co.purevanilla.mcplugins.secondchance;

import co.purevanilla.mcplugins.secondchance.events.Death;
import co.purevanilla.mcplugins.secondchance.util.Registry;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Plugin plugin;
    public static Registry registry;

    @Override
    public void onEnable() {
        super.onEnable();

        plugin = this;
        this.saveDefaultConfig();
        registry=new Registry(this);

        getServer().getPluginManager().registerEvents(new Death(), this);

    }
}
