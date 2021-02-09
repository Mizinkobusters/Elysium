package mb.mizinkobusters.elysium.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomStone implements CommandExecutor {

    private final JavaPlugin plugin;

    public RandomStone(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("randomstone")) {
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can execute this command.");
            return true;
        }

        Player player = (Player) sender;

        if (!Bukkit.getScheduler().getActiveWorkers().isEmpty()) {
            player.sendMessage("§cブロックを生成できません");
            return true;
        }

        player.sendMessage("§a生成を開始しています...");
        player.sendMessage("§7この操作には時間がかかる場合があります");
        generateBlocks();
        return true;
    }

    private void generateBlocks() {
        FileConfiguration config = plugin.getConfig();
        if (!config.contains("setOriginBlock.world") || !config.isString("setOriginBlock.world")) {
            return;
        }

        String world = config.getString("setOriginBlock.world");
        if (world == null || Bukkit.getWorld(world) == null) {
            return;
        }

        List<Block> blocks = getBlocks();
        if (blocks == null) {
            return;
        }
        for (Block block : blocks) {
            Location loc = block.getLocation();
            loc.getBlock().setType(getGenerateMaterial().getType());
        }
    }

    private List<Block> getBlocks() {
        FileConfiguration config = plugin.getConfig();
        if (!(config.contains("setOriginBlock.world")
                || config.contains("setOriginBlock.x")
                || config.contains("setOriginBlock.y")
                || config.contains("setOriginBlock.z"))) {
            return null;
        }
        if (!(config.isString("setOriginBlock.world")
                || config.isInt("setOriginBlock.x")
                || config.isInt("setOriginBlock.y")
                || config.isInt("setOriginBlock.z"))) {
            return null;
        }

        String worldName = config.getString("setOriginBlock.world");
        if (worldName == null) {
            return null;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        }

        int x1 = config.getInt("setOriginBlock.x");
        int y1 = config.getInt("setOriginBlock.y");
        int z1 = config.getInt("setOriginBlock.z");

        int x2 = x1 + 96;
        int y2 = y1 + 1;
        int z2 = z1 + 96;

        int lowestX = Math.min(x1, x2);
        int lowestY = Math.min(y1, y2);
        int lowestZ = Math.min(z1, z2);

        int highestX = lowestX == x1 ? x2 : x1;
        int highestY = lowestY == y1 ? y2 : y1;
        int highestZ = lowestZ == z1 ? z2 : z1;

        List<Block> blocks = new ArrayList<>();
        for (int x = lowestX; x <= highestX; x++) {
            for (int y = lowestY; y <= highestY; y++) {
                for (int z = lowestZ; z <= highestZ; z++) {
                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    private ItemStack getGenerateMaterial() {
        FileConfiguration config = plugin.getConfig();
        if (!config.contains("setOriginRation.coal") || !config.isInt("setOriginRation.coal")
                || !config.contains("setBlockRatio.iron") || !config.isInt("setBlockRatio.iron")
                || !config.contains("setBlockRatio.gold") || !config.isInt("setBlockRatio.gold")
                || !config.contains("setBlockRatio.diamond") || !config.isInt("setBlockRatio.diamond")
                || !config.contains("setBlockRatio.emerald") || !config.isInt("setBlockRatio.emerald")
                || !config.contains("setBlockRatio.lapisLazuli") || !config.isInt("setBlockRatio.lapisLazuli")
                || !config.contains("setBlockRatio.redstone") || !config.isInt("setBlockRatio.redstone")) {
            return new ItemStack(Material.AIR);
        }

        int ratio_coal = config.getInt("setBlockRatio.coal");
        int ratio_iron = config.getInt("setBlockRatio.iron");
        int ratio_gold = config.getInt("setBlockRatio.gold");
        int ratio_diamond = config.getInt("setBlockRatio.diamond");
        int ratio_emerald = config.getInt("setBlockRatio.emerald");
        int ratio_lapisLazuli = config.getInt("setBlockRatio.lapisLazuli");
        int ratio_redstone = config.getInt("setBlockRatio.redstone");
        if (ratio_coal + ratio_iron + ratio_gold + ratio_diamond + ratio_emerald + ratio_lapisLazuli + ratio_redstone != 100) {
            return new ItemStack(Material.AIR);
        }

        Random random = new Random();

        int result_coal = 100 / ratio_coal;
        if (random.nextInt(result_coal) == 0) {
            return new ItemStack(Material.COAL_ORE);
        }

        int result_iron = 100 / ratio_iron;
        if (random.nextInt(result_iron) == 0) {
            return new ItemStack(Material.IRON_ORE);
        }

        int result_gold = 100 / ratio_gold;
        if (random.nextInt(result_gold) == 0) {
            return new ItemStack(Material.GOLD_ORE);
        }

        int result_diamond = 100 / ratio_diamond;
        if (random.nextInt(result_diamond) == 0) {
            return new ItemStack(Material.DIAMOND_ORE);
        }

        int result_emerald = 100 / ratio_emerald;
        if (random.nextInt(result_emerald) == 0) {
            return new ItemStack(Material.EMERALD_ORE);
        }

        int result_lapisLazuli = 100 / ratio_lapisLazuli;
        if (random.nextInt(result_lapisLazuli) == 0) {
            return new ItemStack(Material.LAPIS_ORE);
        }

        int result_redstone = 100 / ratio_redstone;
        if (random.nextInt(result_redstone) == 0) {
            return new ItemStack(Material.REDSTONE_ORE);
        }

        return new ItemStack(Material.STONE);
    }
}
