package perhaps.progressions.abilities;

import perhaps.progressions.client.gui.scroll_wheels.WheelOption;

import java.util.Map;

import static perhaps.progressions.client.gui.WheelSelectionManager.abilitiesScrollWheel;
import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.ABILITIES_ICON;

public class Abilities {
    public static void initializeAbilities() {
        abilities.forEach((skillId, skillObject) -> {
            String displayName = (String) skillObject.get("displayName");
            String description = (String) skillObject.get("description");
            String saveName = (String) skillObject.get("saveName");

            addAbility(displayName, description, saveName, () -> tempAbilityCallback(displayName));
        });
    }

    private static final Map<String, Map<String, Object>> abilities = Map.ofEntries(

    );

    private static void tempAbilityCallback(String displayName) {
        System.out.println("Pressed " + displayName + "!");
    }

    public static void addAbility(String displayName, String description, String saveName, Runnable callback) {
        abilitiesScrollWheel.addOption(new WheelOption(ABILITIES_ICON, displayName, description, callback));
    }
}
