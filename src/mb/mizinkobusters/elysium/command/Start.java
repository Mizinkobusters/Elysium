package mb.mizinkobusters.elysium.command;

import mb.mizinkobusters.elysium.GenerateBlockThread;
import mb.mizinkobusters.elysium.Main;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Start implements CommandExecutor {

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
        tool.addEnchantment(Enchantment.DIG_SPEED, 2);
        ItemMeta meta = tool.getItemMeta();
        if (meta == null) {
            return true;
        }
        meta.setUnbreakable(true);
        tool.setItemMeta(meta);

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.getInventory().addItem(tool);
            players.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 127, false, false));
            players.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000000, 127, false, false));
            players.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 127, false, false));
        }

        GenerateBlockThread thread = new GenerateBlockThread();
        thread.generate();

        new BukkitRunnable() {
            int timer = 30 * 60 + 5;
            int countdown = 0;

            @Override
            public void run() {
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
                }

                timer--;
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);

        return true;
    }
}
