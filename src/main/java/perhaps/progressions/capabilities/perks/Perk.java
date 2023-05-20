package perhaps.progressions.capabilities.perks;

import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Perk {
    public static Map<String, Float> perkData = new HashMap<>();

    static {
        perkData.put("equipped", 0f); // 0 = not equipped, 1 = equipped
        perkData.put("baseLevel", 1f);
        perkData.put("level", 1f);
        perkData.put("prestige", 1f);

        perkData.put("xp", 0.0f);
        perkData.put("neededXp", 100f);

        perkData.put("baseLevelMin", 1f);
        perkData.put("baseLevelMax", 3f);
        perkData.put("levelMin", 1f);
        perkData.put("levelMax", 10f);
        perkData.put("xpMin", 0f);
        perkData.put("xpMax", 1000000f);
    }

    public Map<String, Float> getPerkData() {
        return perkData;
    }

    public Float getMin(String statName) {
        if (perkData.containsKey(statName + "Min")) {
            return perkData.get(statName + "Min");
        }
        return null;
    }

    public Float getMax(String statName) {
        if (perkData.containsKey(statName + "Max")) {
            return perkData.get(statName + "Max");
        }
        return null;
    }

    public Float calculateMax(String statName, Float value) {
        Float max = getMax(statName);
        Float min = getMin(statName);
        if (max != null && min != null) {
            return Math.max(min, Math.min(max, value));
        }
        return value;
    }

    public Float get(String statName) {
        Float value = perkData.get(statName);
        if (value == null) {
            System.out.println("Failed to get " + statName);
            return null;
        }

        return value;
    }

    public void set(String statName, Float value) {
        if (Objects.equals(statName, "level") || Objects.equals(statName, "base_level")) {
            float currentLevel = get("level");
            float currentBase = get("base_level");
            float neededXp = 100f;
            for (int level = 2; level <= currentLevel; level++) {
                double prestigeMultiplier = 1 + (65.0 * currentBase / 100.0);
                neededXp *= prestigeMultiplier;
            }

            perkData.put("neededXp", neededXp);
        }

        perkData.put(statName, calculateMax(statName, value));
    }

    public void add(String statName, Float value) {
        set(statName, get(statName) + value);
    }

    public void subtract(String statName, Float value) {
        set(statName, get(statName) - value);
    }

    public void multiply(String statName, Float value) {
        set(statName, get(statName) * value);
    }

    public void divide(String statName, Float value) {
        set(statName, get(statName) / value);
    }

    public void saveNBTData(CompoundTag nbt) {
        for (Map.Entry<String, Float> entry : new HashMap<>(perkData).entrySet()) {
            nbt.putFloat(entry.getKey(), entry.getValue());
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        for (String key : new HashMap<>(perkData).keySet()) {
            if (nbt.contains(key)) {
                perkData.put(key, nbt.getFloat(key));
            }
        }
    }

    public void copyFrom(Perk perk) {
        perkData.putAll(perk.getPerkData());
    }
}
