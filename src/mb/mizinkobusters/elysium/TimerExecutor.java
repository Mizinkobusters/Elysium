package mb.mizinkobusters.elysium;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerExecutor extends BukkitRunnable {

    int timer = 30 * 60 + 5;
    int countdown = 0;

    private void setCountdown() {

        switch (timer) {
            case 30 * 60 + 5:
                countdown = 3;
            case 30 * 60 + 5 - 1:
            case 30 * 60 + 5 - 2:
                Bukkit.broadcastMessage(countdown + "...");
                countdown--;
                break;
            case 30 * 60 + 5 - 3:
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendTitle("スタート!", "", 5, 100, 5);
                    players.playSound(players.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                }
                break;
            case 20 * 60 + 5:
            case 10 * 60 + 5:
                RankingUtil.showHighestPlayer();
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendTitle("残り" + (timer / 60) + "分", "", 5, 100, 5);
                }
                break;
            case 5 + 5:
                countdown = 5;
            case 5 + 4:
            case 5 + 3:
            case 5 + 2:
            case 5 + 1:
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(countdown + "...");
                    players.playSound(players.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 1);
                }
                countdown--;
                break;
            case 5:
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendTitle("終了!!", "", 5, 100, 5);
                    players.playSound(players.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                    players.sendMessage("-------------------");
                    players.sendMessage("今回の優勝者は...");
                }
                break;
            case 0:
                Player highestLeveler = RankingUtil.getHighestLeveler();
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    players.sendMessage("-------------------");
                    players.sendMessage("名前: " + highestLeveler.getName());
                    players.sendMessage("レベル: " + highestLeveler.getLevel());
                    players.sendMessage("-------------------");
                }
                break;
            case -1:
                this.cancel();
                break;
        }
    }

    @Override
    public void run() {
        setCountdown();
        timer--;
    }
}
