package perhaps.progressions.abilities;

import perhaps.progressions.client.gui.scroll_wheels.WheelOption;

import java.util.Map;

import static perhaps.progressions.client.gui.WheelSelectionManager.abilitiesScrollWheel;
import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.ABILITIES_ICON;

public class Abilities {
    public void initializeAbilities() {
        abilities.forEach((abilityName, abilityObject) -> {
            String displayName = (String) abilityObject.get("displayName");
            String description = (String) abilityObject.get("description");
            String saveName = (String) abilityObject.get("saveName");
            Runnable callback = (Runnable) abilityObject.get("callback");

            addAbility(displayName, description, saveName, () -> tempAbilityCallback(displayName));
        });
    }

    private final Map<String, Map<String, Object>> abilities = Map.ofEntries(

    );

    private static void tempAbilityCallback(String displayName) {
        System.out.println("Pressed " + displayName + "!");
    }

    public static void addAbility(String displayName, String description, String saveName, Runnable callback) {
        abilitiesScrollWheel.addOption(new WheelOption(ABILITIES_ICON, displayName, description, callback));
    }
}
