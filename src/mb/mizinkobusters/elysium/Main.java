package mb.mizinkobusters.elysium;

import mb.mizinkobusters.elysium.command.RandomStone;
import mb.mizinkobusters.elysium.command.Start;
import mb.mizinkobusters.elysium.command.XpRanking;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginCommand("xpranking").setExecutor(new XpRanking());
        Bukkit.getPluginCommand("randomstone").setExecutor(new RandomStone(this));
        Bukkit.getPluginCommand("start").setExecutor(new Start(this));

        String name = this.getDescription().getName();
        String ver = this.getDescription().getVersion();
        List<String> author = this.getDescription().getAuthors();

        saveDefaultConfig();

        Bukkit.getLogger().info(name + "(v." + ver + ") by " + author);
        Bukkit.getLogger().info("Now Loading...");
    }

    public void onDisable() {
        reloadConfig();
        Bukkit.getLogger().info("See You Next Play!");
    }
}
