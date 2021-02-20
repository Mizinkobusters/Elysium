package mb.mizinkobusters.elysium;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerTeleporter {

    public void teleport(Player player) {
        ConfigManager configManager = Main.getInstance().configManager;
        String worldName = configManager.getTpWorldName();
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            Bukkit.getLogger().info("ワールド: " + worldName + "が存在しません");
            return;
        }
        double tpX = configManager.getTpX();
        double tpY = configManager.getTpY();
        double tpZ = configManager.getTpZ();
        float tpYaw = configManager.getTpYaw();
        float tpPitch = configManager.getTpPitch();

        player.teleport(new Location(Bukkit.getWorld(worldName), tpX, tpY, tpZ, tpYaw, tpPitch));
    }
}
