package cookie.worldedit.commands;

import cookie.worldedit.WandClipboard;
import cookie.worldedit.WandPlayerData;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.world.World;

public class CommandCopy extends Command {

    public CommandCopy() {
        super("/copy", "");
    }

    @Override
    public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
        if (strings.length == 0) {
            int[] primaryPos = WandPlayerData.primaryPositions.get(commandSender.getPlayer().username);
            int[] secondaryPos = WandPlayerData.secondaryPositions.get(commandSender.getPlayer().username);

            if (primaryPos == null || secondaryPos == null) {
                commandSender.getPlayer().addChatMessage("Positions aren't set!");
                return true;
            }

            int minX = primaryPos[0];
            int minY = primaryPos[1];
            int minZ = primaryPos[2];
            int maxX = secondaryPos[0];
            int maxY = secondaryPos[1];
            int maxZ = secondaryPos[2];

            int swap; // If the minimum is greater than the max, set this to the min value and swap them around.
            if (minX > maxX) {
                swap = minX;
                minX = maxX;
                maxX = swap;
            }

            if (minY > maxY) {
                swap = minY;
                minY = maxY;
                maxY = swap;
            }

            if (minZ > maxZ) {
                swap = minZ;
                minZ = maxZ;
                maxZ = swap;
            }

            WandClipboard copyClipboard = WandPlayerData.copyClipboards.computeIfAbsent(commandSender.getPlayer().username, k -> new WandClipboard());

            // If the clipboard page is -1, or null, create a new one.
            if (copyClipboard.page == -1)
                copyClipboard.createNewPage();

            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        World world = commandSender.getPlayer().world;
                        int id = world.getBlockId(x, y, z);
                        int meta = world.getBlockMetadata(x, y, z);

//                      copyClipboard.putBlock(x - primaryPosition[0], y - primaryPosition[1], z - primaryPosition[2], id, meta);
                        copyClipboard.putBlock(x, y, z, id, meta);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean opRequired(String[] strings) {
        return true;
    }

    @Override
    public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
        commandSender.sendMessage("//copy");
    }
}
