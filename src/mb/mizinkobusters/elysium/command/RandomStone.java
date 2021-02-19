package mb.mizinkobusters.elysium.command;

import mb.mizinkobusters.elysium.BlockGenerator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RandomStone implements CommandExecutor {

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

        BlockGenerator thread = new BlockGenerator();

        player.sendMessage("§a生成を開始しています...");
        player.sendMessage("§7この操作には時間がかかる場合があります");
        thread.generate();
        return true;
    }
}
