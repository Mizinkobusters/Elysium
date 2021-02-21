package mb.mizinkobusters.elysium;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockGenerator {

    public void generate() {
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
        ConfigManager configManager = Main.getInstance().configManager;
        int x1 = configManager.getOriginX();
        int y1 = configManager.getOriginY();
        int z1 = configManager.getOriginZ();

        int x2 = x1 + 96;
        int y2 = y1 - 50;
        int z2 = z1 + 96;

        List<Block> blocks = new ArrayList<>();
        String worldName = configManager.getOriginWorldName();
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            Bukkit.getLogger().info("ワールド: " + worldName + "が存在しません");
            return null;
        }
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y2 < y; y--) {
                for (int z = z1; z < z2; z++) {
                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    private ItemStack getGenerateMaterial() {
        ConfigManager configManager = Main.getInstance().configManager;
        int ratioCoal = configManager.getRatioCoal();
        int ratioIron = configManager.getRatioIron();
        int ratioGold = configManager.getRatioGold();
        int ratioDiamond = configManager.getRatioDiamond();
        int ratioEmerald = configManager.getRatioEmerald();
        int ratioLapisLazuli = configManager.getRatioLapisLazuli();
        int ratioRedstone = configManager.getRatioRedstone();
        if (ratioCoal + ratioIron + ratioGold + ratioDiamond + ratioEmerald + ratioLapisLazuli + ratioRedstone != 100) {
            return new ItemStack(Material.AIR);
        }

        Random random = new Random();

        int result_coal = 100 / ratioCoal;
        if (random.nextInt(result_coal) == 0) {
            return new ItemStack(Material.COAL_ORE);
        }

        int result_iron = 100 / ratioIron;
        if (random.nextInt(result_iron) == 0) {
            return new ItemStack(Material.IRON_ORE);
        }

        int result_gold = 100 / ratioGold;
        if (random.nextInt(result_gold) == 0) {
            return new ItemStack(Material.GOLD_ORE);
        }

        int result_diamond = 100 / ratioDiamond;
        if (random.nextInt(result_diamond) == 0) {
            return new ItemStack(Material.DIAMOND_ORE);
        }

        int result_emerald = 100 / ratioEmerald;
        if (random.nextInt(result_emerald) == 0) {
            return new ItemStack(Material.EMERALD_ORE);
        }

        int result_lapisLazuli = 100 / ratioLapisLazuli;
        if (random.nextInt(result_lapisLazuli) == 0) {
            return new ItemStack(Material.LAPIS_ORE);
        }

        int result_redstone = 100 / ratioRedstone;
        if (random.nextInt(result_redstone) == 0) {
            return new ItemStack(Material.REDSTONE_ORE);
        }

        return new ItemStack(Material.STONE);
    }
}
