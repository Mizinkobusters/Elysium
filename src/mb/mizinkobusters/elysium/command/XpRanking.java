package mb.mizinkobusters.elysium.command;

import mb.mizinkobusters.elysium.RankingUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XpRanking implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("xpranking")) {
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can execute this command.");
            return true;
        }

        RankingUtil.showHighestPlayer();
        return true;
    }
}
