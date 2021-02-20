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
    int readyCountdown = 3;
    int timer = 30 * 60;
    int resultCountdown = 5;

    private void setReadyCountdown() {
        if (1 <= readyCountdown && readyCountdown <= 3) {
            Bukkit.broadcastMessage(readyCountdown-- + "...");
        }
    }

    private void setTimer() {
        switch (timer) {
            case 30 * 60:
                Bukkit.getOnlinePlayers().forEach(players -> {
                    players.sendTitle("スタート!", "", 5, 100, 5);
                    players.playSound(players.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                });
                showTimerBar();
                break;
            case 20 * 60:
            case 10 * 60:
                RankingUtil.showHighestPlayer();
                Bukkit.getOnlinePlayers().forEach(players -> players.sendTitle("残り" + (timer / 60) + "分", "", 5, 100, 5));
                break;
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                Bukkit.getOnlinePlayers().forEach(players -> {
                    players.sendMessage(timer + "...");
                    players.playSound(players.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                });
                break;
            case 0:
                Bukkit.getOnlinePlayers().forEach(players -> {
                    players.sendTitle("終了!!", "", 5, 100, 5);
                    players.playSound(players.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                    players.sendMessage("-------------------");
                    players.sendMessage("今回の優勝者は...");
                });
                hideTimerBar();
                break;
        }
        timer--;
    }

    private void setResultCountdown() {
        if (1 <= resultCountdown && resultCountdown <= 5) {
            resultCountdown--;
        }
        if (resultCountdown == 0) {
            Player highestLeveler = RankingUtil.getHighestLeveler();
            Bukkit.getOnlinePlayers().forEach(players -> {
                players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                players.sendMessage("-------------------");
                players.sendMessage("名前: " + highestLeveler.getName());
                players.sendMessage("レベル: " + highestLeveler.getLevel());
                players.sendMessage("-------------------");
            });
        }
        resultCountdown--;
    }

    private void updateTimerBar() {
        int min = timer / 60;
        int sec = timer % 60;
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
        if (0 <= timer && timer <= 30 * 60) {
            updateTimerBar();
        }
        if (readyCountdown == 0) {
            setTimer();
        }
        if (timer < 0) {
            setResultCountdown();
        }
        setReadyCountdown();
    }
}
