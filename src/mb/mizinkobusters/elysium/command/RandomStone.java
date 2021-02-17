package mb.mizinkobusters.elysium.command;

import mb.mizinkobusters.elysium.GenerateBlockThread;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomStone implements CommandExecutor {

    private final JavaPlugin plugin;

    public RandomStone(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("randomstone")) {
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can execute this command.");
            return true;
        }

        Player player = (Player) sender;

        if (!Bukkit.getScheduler().getActiveWorkers().isEmpty()) {
            player.sendMessage("§cブロックを生成できません");
            return true;
        }

        GenerateBlockThread thread = new GenerateBlockThread(plugin);

        player.sendMessage("§a生成を開始しています...");
        player.sendMessage("§7この操作には時間がかかる場合があります");
        thread.generate();
        return true;
    }
}
