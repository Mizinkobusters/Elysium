package mb.mizinkobusters.elysium;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankingUtil {

    public static List<Player> getHighestLevelers() {
        List<Player> sortedPlayers = Bukkit.getOnlinePlayers().stream()
                .sorted(Comparator.comparing(Player::getExp))
                .collect(Collectors.toList());

        sortedPlayers.forEach(player -> {
            if (sortedPlayers.get(0).getExp() > player.getExp()) {
                sortedPlayers.remove(player);
            }
        });

        return sortedPlayers;
    }

    public static void showHighestPlayer() {
        List<Player> highestLevelers = getHighestLevelers();
        String[] highestLevelerNames = highestLevelers.stream().map(HumanEntity::getName).toArray(String[]::new);

        Bukkit.broadcastMessage("§f-----------");
        Bukkit.broadcastMessage("§c最も経験値が高いプレイヤー");
        Bukkit.broadcastMessage("§7名前: §a" + String.join(", ", highestLevelerNames));
        Bukkit.broadcastMessage("§7レベル: §e" + highestLevelers.get(0).getExp());
        Bukkit.broadcastMessage("§f-----------");
    }
}
