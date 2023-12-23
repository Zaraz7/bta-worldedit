package cookie.worldedit.commands;

import cookie.worldedit.WandPlayerData;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

public class CommandSetPos1 extends Command {

    public CommandSetPos1() {
        super("/setPos1", "");
    }

    @Override
    public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
        int[] hitPosition = {(int) commandSender.getPlayer().x, ((int) commandSender.getPlayer().y - 1), (int) commandSender.getPlayer().z};
        WandPlayerData.primaryPositions.put(commandSender.getPlayer().username, hitPosition);
        commandSender.sendMessage("Set primary position at " + commandSender.getPlayer().x + ", " + (commandSender.getPlayer().y - 1) + ", " + commandSender.getPlayer().z);
        return true;
    }

    @Override
    public boolean opRequired(String[] strings) {
        return true;
    }

    @Override
    public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
        commandSender.sendMessage("//setpos1");
    }
}