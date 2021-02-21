package mb.mizinkobusters.elysium.command;

import mb.mizinkobusters.elysium.BlockGenerator;
import mb.mizinkobusters.elysium.Main;
import mb.mizinkobusters.elysium.PlayerUtils;
import mb.mizinkobusters.elysium.TimerExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

        Bukkit.getOnlinePlayers().forEach(players -> {
            players.getInventory().addItem(tool);
            players.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 127, false, false));
            players.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000000, 127, false, false));
            players.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 127, false, false));
            PlayerUtils.teleport(players);
        });

        BlockGenerator generator = new BlockGenerator();
        generator.generate();

        new TimerExecutor().runTaskTimer(Main.getInstance(), 20, 20);

        return true;
    }
}
