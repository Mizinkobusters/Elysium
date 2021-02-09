package mb.mizinkobusters.elysium.command;

import mb.mizinkobusters.elysium.RankingUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Start implements CommandExecutor {

    private final JavaPlugin plugin;

    public Start(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("start")) {
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can execute this command.");
            return true;
        }

        Player player = (Player) sender;
        if (!Bukkit.getScheduler().getActiveWorkers().isEmpty()) {
            player.sendMessage("§c実行できませんでした");
            return true;
        }

        ItemStack tool = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta meta = tool.getItemMeta();
        if (meta == null) {
            return true;
        }
        tool.addEnchantment(Enchantment.DIG_SPEED, 2);
        meta.setUnbreakable(true);
        tool.setItemMeta(meta);

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.getInventory().addItem(tool);
            players.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 127, false, false));
            players.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000000, 127, false, false));
            players.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 127, false, false));
        }

        // 動作不良のためとりあえずHide
        // Bukkit.dispatchCommand(player, "randomstone");

        new BukkitRunnable() {
            int i = 3;

            @Override
            public void run() {
                if (i < 0) {
                    this.cancel();
                    return;
                }

                if (i == 0) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.sendTitle("スタート!", "", 5, 100, 5);
                        players.playSound(players.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                    }
                    return;
                }
                Bukkit.broadcastMessage(i + "...");
                i--;
            }
        }.runTaskTimer(plugin, 0, 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                RankingUtil.showHighestPlayer();
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendTitle("残り20分", "", 5, 100, 5);
                }
            }
        }.runTaskLater(plugin, 12000);

        new BukkitRunnable() {
            @Override
            public void run() {
                RankingUtil.showHighestPlayer();
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendTitle("残り10分", "", 5, 100, 5);
                }
            }
        }.runTaskLater(plugin, 24000);

        new BukkitRunnable() {
            int i = 5;
            int n = 11;

            @Override
            public void run() {
                if (n < 0) {
                    this.cancel();
                    return;
                }

                if (i == 0) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.sendTitle("終了!!", "", 5, 100, 5);
                        players.playSound(players.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                        players.sendMessage("-------------------");
                        players.sendMessage("今回の優勝者は...");
                    }
                }

                if (n == 0) {
                    Player highestLeveler = RankingUtil.getHighestLeveler();
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        players.sendMessage("-------------------");
                        players.sendMessage("名前:" + highestLeveler.getName());
                        players.sendMessage("レベル:" + highestLeveler.getLevel());
                        players.sendMessage("-------------------");
                    }
                }

                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (0 < i) {
                        players.sendMessage(i + "...");
                        players.playSound(players.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 1);
                    }
                }
                i--;
                n--;
            }
        }.runTaskTimer(plugin, 35900, 20);

        return true;
    }
}
