package mb.mizinkobusters.elysium;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerExecutor extends BukkitRunnable {

    BossBar bar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SEGMENTED_10);
    int timer = 30 * 60 + 5 + 4;
    int countdown = 0;

    private void setCountdown() {
        switch (timer) {
            case 30 * 60 + 5 + 3:
                countdown = 3;
            case 30 * 60 + 5 + 2:
            case 30 * 60 + 5 + 1:
                Bukkit.broadcastMessage(countdown-- + "...");
                break;
            case 30 * 60 + 5:
                Bukkit.getOnlinePlayers().forEach(players -> {
                    players.sendTitle("スタート!", "", 5, 100, 5);
                    players.playSound(players.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                });
                showTimerBar();
                break;
            case 20 * 60 + 5:
            case 10 * 60 + 5:
                RankingUtil.showHighestPlayer();
                Bukkit.getOnlinePlayers().forEach(players -> players.sendTitle("残り" + (timer / 60) + "分", "", 5, 100, 5));
                break;
            case 5 + 5:
                countdown = 5;
            case 5 + 4:
            case 5 + 3:
            case 5 + 2:
            case 5 + 1:
                Bukkit.getOnlinePlayers().forEach(players -> {
                    players.sendMessage(countdown-- + "...");
                    players.playSound(players.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 1);
                });
                break;
            case 5:
                Bukkit.getOnlinePlayers().forEach(players -> {
                    players.sendTitle("終了!!", "", 5, 100, 5);
                    players.playSound(players.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                    players.sendMessage("-------------------");
                    players.sendMessage("今回の優勝者は...");
                });
                break;
            case 0:
                Player highestLeveler = RankingUtil.getHighestLeveler();
                Bukkit.getOnlinePlayers().forEach(players -> {
                    players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    players.sendMessage("-------------------");
                    players.sendMessage("名前: " + highestLeveler.getName());
                    players.sendMessage("レベル: " + highestLeveler.getLevel());
                    players.sendMessage("-------------------");
                });
                break;
            case -1:
                this.cancel();
                hideTimerBar();
                break;
        }
    }

    private void updateTimerBar() {
        int min = (timer - 5) / 60;
        int sec = (timer - 5) % 60;
        bar.setTitle("§l残り時間: " + min + ":" + String.format("%02d", sec));
    }

    private void showTimerBar() {
        Bukkit.getOnlinePlayers().forEach(players -> bar.addPlayer(players));
    }

    private void hideTimerBar() {
        Bukkit.getOnlinePlayers().forEach(players -> bar.removePlayer(players));
    }

    @Override
    public void run() {
        setCountdown();
        if (0 < timer && timer <= 30 * 60 + 5) {
            updateTimerBar();
        }
        timer--;
    }
}
