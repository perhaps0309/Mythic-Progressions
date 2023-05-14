package perhaps.progressions.capabilities.skills;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class SkillProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Map<String, Skill>> playerSkillsCapability = CapabilityManager.get(new CapabilityToken<Map<String, Skill>>() {});

    private Map<String, Skill> playerSkills = null;
    private final LazyOptional<Map<String, Skill>> optional = LazyOptional.of(this::createPlayerSkills);

    private Map<String, Skill> createPlayerSkills() {
        if (this.playerSkills == null) {
            this.playerSkills = Map.ofEntries(
                    Map.entry("arcane_mastery", new Skill()),
                    Map.entry("elemental_control", new Skill()),
                    Map.entry("alchemy", new Skill()),
                    Map.entry("enchanting", new Skill()),
                    Map.entry("conjuration", new Skill()),
                    Map.entry("meditation", new Skill()),
                    Map.entry("herbalism", new Skill()),
                    Map.entry("mining", new Skill()),
                    Map.entry("smithing", new Skill()),
                    Map.entry("archery", new Skill()),
                    Map.entry("swordsmanship", new Skill()),
                    Map.entry("athletics", new Skill()),
                    Map.entry("excavation", new Skill()),
                    Map.entry("lumberjack", new Skill())
            );
        }

        return this.playerSkills;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == playerSkillsCapability) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        System.out.println("SERIALIZING");
        CompoundTag nbt = new CompoundTag();
        createPlayerSkills().forEach((playerSkill, skillData) -> {
            skillData.saveNBTData(nbt);
        });
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        System.out.println("DESERIALIZING");
        createPlayerSkills().forEach((playerSkill, skillData) -> {
            skillData.loadNBTData(nbt);
        });
    }
}
