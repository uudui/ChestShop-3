package com.Acrobot.ChestShop.Plugins;

import com.Acrobot.ChestShop.Containers.MinecraftChest;
import com.Acrobot.ChestShop.Events.ProtectionCheckEvent;
import com.Acrobot.ChestShop.Utils.uName;
import com.Acrobot.ChestShop.Utils.uSign;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Acrobot
 */
public class ChestShop implements Listener {
    @EventHandler
    public static void onProtectionCheck(ProtectionCheckEvent event) {
        if (event.getResult() == Event.Result.DENY) {
            return;
        }

        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (!canAccess(player, block)) {
            event.setResult(Event.Result.DENY);
        }
    }

    public static boolean canAccess(Player player, Block block) {
        if (!canBeProtected(block)) {
            return true;
        }

        if (isSign(block)) {
            Sign sign = (Sign) block.getState();

            if (!uSign.isValid(sign)) {
                return true;
            }

            if (!isShopMember(player, sign)) {
                return false;
            }
        }

        if (isChest(block)) {
            MinecraftChest chest = new MinecraftChest((Chest) block.getState());

            Sign sign = chest.findShopSign();

            if (sign != null && !isShopMember(player, sign)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isChest(Block block) {
        return block.getType() == Material.CHEST;
    }

    private static boolean isSign(Block block) {
        return uSign.isSign(block);
    }

    private static boolean canBeProtected(Block block) {
        return isSign(block) || isChest(block);
    }

    private static boolean isShopMember(Player player, Sign sign) {
        return uName.canUseName(player, sign.getLine(0));
    }
}
