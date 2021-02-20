package mb.mizinkobusters.elysium;

import mb.mizinkobusters.elysium.command.RandomStone;
import mb.mizinkobusters.elysium.command.Start;
import mb.mizinkobusters.elysium.command.XpRanking;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public class Main extends JavaPlugin {

    private static Main instance;

    final ConfigManager configManager = new ConfigManager();

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginCommand("xpranking").setExecutor(new XpRanking());
        Bukkit.getPluginCommand("randomstone").setExecutor(new RandomStone());
        Bukkit.getPluginCommand("start").setExecutor(new Start());

        String name = this.getDescription().getName();
        String ver = this.getDescription().getVersion();
        List<String> author = this.getDescription().getAuthors();
        Bukkit.getLogger().info(name + "(v." + ver + ") by " + author);
        Bukkit.getLogger().info("Now Loading...");

        saveDefaultConfig();
        configManager.loadConfig();
    }

    public void onDisable() {
        reloadConfig();
        Bukkit.getLogger().info("See You Next Play!");
    }

    public static Main getInstance() {
        return instance;
    }
}
