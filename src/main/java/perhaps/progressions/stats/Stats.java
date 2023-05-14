package perhaps.progressions.stats;

import net.minecraft.resources.ResourceLocation;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.client.gui.WheelSelectionManager;
import perhaps.progressions.client.gui.scroll_wheels.ScrollWheel;
import perhaps.progressions.client.gui.scroll_wheels.WheelOption;

import java.util.Map;

import static perhaps.progressions.client.gui.WheelSelectionManager.*;
import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.*;
import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.ABILITY_INFORMATION;

public class Stats {
    public void initializeStats() {
        stats.forEach((statName, statObject) -> {
            String displayName = (String) statObject.get("displayName");
            String description = (String) statObject.get("description");
            String saveName = (String) statObject.get("saveName");
            Runnable callback = (Runnable) statObject.get("callback");

            addStat(displayName, description, saveName, callback);
        });
    }

    private final Map<String, Map<String, Object>> stats = Map.ofEntries(
            Map.entry("magic_information", Map.of(
                    "displayName", "Magic Information",
                    "saveName", "magic_information",
                    "description", "Regeneration Rate: 2/1s\nMax Mana: 175\nMana Efficiency: 75%",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("general_information", Map.of(
                    "displayName", "General Information",
                    "saveName", "general_information",
                    "description", "Run Speed: 150%\nJump Height: 165%\nAttack Damage: 184%\nAttack Speed: 362%",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )
    ));

    public static void addStat(String displayName, String description, String saveName, Runnable callback) {
        ResourceLocation tempLocation = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/" + saveName + ".png");
        statsScrollWheel.addOption(new WheelOption(tempLocation, displayName, description, callback));
    }
}
