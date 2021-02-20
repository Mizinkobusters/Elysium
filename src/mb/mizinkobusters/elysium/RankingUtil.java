package mb.mizinkobusters.elysium;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankingUtil {

    public static Player getHighestLeveler() {
        // TODO 同じ経験値数のPlayerが複数いた場合を考慮
        List<Player> sortedPlayers = Bukkit.getOnlinePlayers().stream()
                .sorted(Comparator.comparingInt(Player::getExpToLevel))
                .collect(Collectors.toList());

        return sortedPlayers.get(0).getPlayer();
    }

    public static void showHighestPlayer() {
        Player highestLeveler = getHighestLeveler();

        Bukkit.broadcastMessage("§f-----------");
        Bukkit.broadcastMessage("§c最も経験値が高いプレイヤー");
        Bukkit.broadcastMessage("§7名前: §a" + highestLeveler.getName());
        Bukkit.broadcastMessage("§7レベル: §e" + highestLeveler.getLevel());
        Bukkit.broadcastMessage("§f-----------");
    }
}
