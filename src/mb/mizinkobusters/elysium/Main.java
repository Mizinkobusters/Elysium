package mb.mizinkobusters.elysium;

import mb.mizinkobusters.elysium.command.Randomstone;
import mb.mizinkobusters.elysium.command.Start;
import mb.mizinkobusters.elysium.command.Xpranking;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getPluginCommand("xpranking").setExecutor(new Xpranking());
        Bukkit.getPluginCommand("randomstone").setExecutor(new Randomstone(this));
        Bukkit.getPluginCommand("start").setExecutor(new Start(this));

        String name = this.getDescription().getName();
        String ver = this.getDescription().getVersion();
        List<String> author = this.getDescription().getAuthors();

        Bukkit.getLogger().info(name + "(v." + ver + ") by " + author);
        Bukkit.getLogger().info("Now Loading...");

    }

    public void onDisable() {

        Bukkit.getLogger().info("See You Next Play!");

    }

}
