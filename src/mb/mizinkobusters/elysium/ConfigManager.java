package mb.mizinkobusters.elysium;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private String worldName;
    private Integer originX;
    private Integer originY;
    private Integer originZ;

    private Integer ratioCoal;
    private Integer ratioIron;
    private Integer ratioGold;
    private Integer ratioDiamond;
    private Integer ratioEmerald;
    private Integer ratioLapisLazuli;
    private Integer ratioRedstone;

    public void loadConfig() {
        worldName = getData("setOriginBlock.world", String.class);
        originX = getData("setOriginBlock.x", Integer.class);
        originY = getData("setOriginBlock.y", Integer.class);
        originZ = getData("setOriginBlock.z", Integer.class);

        ratioCoal = getData("setBlockRatio.coal", Integer.class);
        ratioIron = getData("setBlockRatio.iron", Integer.class);
        ratioGold = getData("setBlockRatio.gold", Integer.class);
        ratioDiamond = getData("setBlockRatio.diamond", Integer.class);
        ratioEmerald = getData("setBlockRatio.emerald", Integer.class);
        ratioLapisLazuli = getData("setBlockRatio.lapisLazuli", Integer.class);
        ratioRedstone = getData("setBlockRatio.redstone", Integer.class);
    }

    private <T> T getData(String path, Class<? extends T> type) {
        FileConfiguration config = Main.getInstance().getConfig();
        if (!config.contains(path)) {
            return null;
        }

        Object data = config.get(path);
        if (data != null && data.getClass() == type) {
            return (T) data;
        }
        Bukkit.getLogger().info(path + "が不正です");
        return null;
    }

    public String getWorldName() {
        return worldName;
    }

    public Integer getOriginX() {
        return originX;
    }

    public Integer getOriginY() {
        return originY;
    }

    public Integer getOriginZ() {
        return originZ;
    }

    public Integer getRatioCoal() {
        return ratioCoal;
    }

    public Integer getRatioIron() {
        return ratioIron;
    }

    public Integer getRatioGold() {
        return ratioGold;
    }

    public Integer getRatioDiamond() {
        return ratioDiamond;
    }

    public Integer getRatioEmerald() {
        return ratioEmerald;
    }

    public Integer getRatioLapisLazuli() {
        return ratioLapisLazuli;
    }

    public Integer getRatioRedstone() {
        return ratioRedstone;
    }
}
