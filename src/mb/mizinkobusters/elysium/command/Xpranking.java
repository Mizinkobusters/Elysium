package mb.mizinkobusters.elysium.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class Xpranking implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!cmd.getName().equalsIgnoreCase("xpranking")) {
            return true;
        }

        Player player;
        if (!(sender instanceof Player)) {

            sender.sendMessage("§cOnly players can execute this command.");
            return true;

        } else {
            player = (Player) sender;
        }

        if (!player.isOp()) {
            player.sendMessage("§c権限がありません");
        }

        Bukkit.broadcastMessage("§f-----------");
        Bukkit.broadcastMessage("§c最も経験値が高いプレイヤー");
        Bukkit.broadcastMessage("§7名前: §a" + getHighestLeveler().getName());
        Bukkit.broadcastMessage("§7レベル: §e" + getHighestLeveler().getLevel());
        Bukkit.broadcastMessage("§f-----------");

        return false;

    }

    public Player getHighestLeveler() {

        HashMap<UUID, Integer> ranking = new HashMap<>();

        for (Player players : Bukkit.getOnlinePlayers()) {

            if (players.getLevel() < 1) {
                continue;
            }

            ranking.put(players.getUniqueId(), players.getLevel());
        }

        List<UUID> playerslist = new ArrayList<>();

        playerslist.addAll(ranking.keySet());
        playerslist.sort(Comparator.reverseOrder());

        return Bukkit.getPlayer(playerslist.get(0));

    }

}
