package perhaps.progressions.abilities;

import perhaps.progressions.client.gui.WheelSelectionManager;
import perhaps.progressions.client.gui.scroll_wheels.ScrollWheel;
import perhaps.progressions.client.gui.scroll_wheels.WheelOption;

import java.util.Map;

import static perhaps.progressions.client.gui.WheelSelectionManager.*;
import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.*;

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
            Map.entry("arcane_mastery", Map.of(
                    "displayName", "Elemental Affinity",
                    "description", "Improves your resistance to the primary types of elemental magic.",
                    "saveName", "elemental_affinity",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("mana_channel", Map.of(
                    "displayName", "Mana Channel",
                    "description", "Increases your mana regeneration rate for a short period.",
                    "saveName", "elemental_control",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("aetherial_shift", Map.of(
                    "displayName", "Aetherial Shift",
                    "description", "Allows you to briefly become ethereal, passing through solid objects and avoiding damage.",
                    "saveName", "aetherial_shift",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("quick_step", Map.of(
                    "displayName", "Quick Step",
                    "description", "Allows you to move at an increased speed for a short period.",
                    "saveName", "quick_step",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("arcane_boost", Map.of(
                    "displayName", "Arcane Boost",
                    "description", "Enhances your damage, range, and accuracy with magic for a short period.",
                    "saveName", "arcane_boost",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("elemental_surge", Map.of(
                    "displayName", "Elemental Surge",
                    "description", "Unleashes a powerful burst of elemental energy, damaging nearby enemies.",
                    "saveName", "elemental_control",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("enchantment_infusion", Map.of(
                    "displayName", "Enchantment Infusion",
                    "description", "Your next enchantment is guaranteed to be that maximum level, with a small chance to go above.",
                    "saveName", "enchantment_infusion",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("etheral_summon", Map.of(
                    "displayName", "Etheral Summon",
                    "description", "Summons a creature from another plane of existence to aid you in combat.",
                    "saveName", "etheral_summon",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("ore_sense", Map.of(
                    "displayName", "Ore Sense",
                    "description", "Reveals the location of valuable minerals nearby.",
                    "saveName", "ore_sense",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("swordmasters_strike", Map.of(
                    "displayName", "Swordmaster's Strike",
                    "description", "Unleashes a powerful sword strike, dealing massive damage to your target.",
                    "saveName", "swordmasters_strike",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("atheletes_dash", Map.of(
                    "displayName", "Athlete's Dash",
                    "description", "Temporarily enhances your running speed, jump height, and swimming speed.",
                    "saveName", "atheletes_dash",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("excavators_might", Map.of(
                    "displayName", "Excavator's Might",
                    "description", "Temporarily enhances your digging speed and increases the chance of finding rare items.",
                    "saveName", "excavators_might",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("lumberjacks_fury", Map.of(
                    "displayName", "Lumberjack's Fury",
                    "description", "Enhances your efficiency and speed when chopping down trees",
                    "saveName", "lumberjacks_fury",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            ))
    );

    private static void tempAbilityCallback(String displayName) {
        System.out.println("Pressed " + displayName + "!");
    }

    public static void addAbility(String displayName, String description, String saveName, Runnable callback) {
        abilitiesScrollWheel.addOption(new WheelOption(ABILITIES_ICON, displayName, description, () -> {
            callback.run();
            ScrollWheel abilityWheel = new ScrollWheel(abilitiesScrollWheel);
            abilityWheel.addOption(new WheelOption(PRESTIGE_ICON, "Reroll", "Reroll your ability upon reaching level 10.", () -> {

            }));

            abilityWheel.addOption(new WheelOption(UPGRADE_ICON, "Level Up", "Level up your ability", () -> {

            }));

            WheelOption abilityOption = new WheelOption(ABILITIES_ICON, displayName, "Ability Level: 1/10\nXP: 78/100", () -> {

            });

            abilityOption.addHover(() -> {
                abilityOption.description = "Ability Level: 10/10\nXP: 78/100";
            });

            abilityWheel.addOption(abilityOption);
            WheelSelectionManager.openWheelSelectionScreen(globalPlayer, abilityWheel);
        }));
    }
}
