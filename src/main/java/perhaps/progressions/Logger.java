package perhaps.progressions;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.Objects;

public class Logger {
    public enum LoggingType {
        INFO,
        WARN,
        ERROR
    }

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_PURPLE = "\u001B[35m";

    public static void log(Object message, @Nullable LoggingType type, @Nullable Boolean inChat) {
        if (type == null) type = LoggingType.INFO;
        if (inChat == null) inChat = false;

        Player player = Minecraft.getInstance().player;
        String prefix = inChat ? ChatFormatting.LIGHT_PURPLE + "[MP] " + ChatFormatting.WHITE : ANSI_PURPLE + "[MP] " + ANSI_RESET;
        switch (type) {
            case INFO -> {
                prefix += inChat ? ChatFormatting.BLUE + "[INFO]: " + ChatFormatting.WHITE : ANSI_BLUE + "[INFO]: " + ANSI_RESET;
            }
            case WARN -> {
                prefix += inChat ? ChatFormatting.YELLOW + "[WARN]: " + ChatFormatting.WHITE : ANSI_YELLOW + "[WARN]: " + ANSI_RESET;
            }
            case ERROR -> {
                prefix += inChat ? ChatFormatting.RED + "[ERROR]: " + ChatFormatting.WHITE : ANSI_RED + "[ERROR]: " + ANSI_RESET;
            }
        }

        if (inChat) {
            try {
                TextComponent chatMessage = new TextComponent(prefix + message);
                Objects.requireNonNull(player).sendMessage(chatMessage, Util.NIL_UUID);
                return;
            } catch (Exception exception) {
                // This likely happened because chat isn't initialized yet
                // Redirecting to standard output
                log(message, type, false);
                return;
            }
        }
        System.out.println(prefix + message);
    }
}
