package mb.mizinkobusters.elysium;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateBlockThread {

    private final JavaPlugin plugin;

    public GenerateBlockThread(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void generate() {
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
        if (!config.contains("setOriginBlock.world") || !config.isString("setOriginBlock.world")
                || !config.contains("setOriginBlock.x") || !config.isInt("setOriginBlock.x")
                || !config.contains("setOriginBlock.y") || !config.isInt("setOriginBlock.y")
                || !config.contains("setOriginBlock.z") || !config.isInt("setOriginBlock.z")) {
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

        List<Block> blocks = new ArrayList<>();
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                for (int z = z1; z < z2; z++) {
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
