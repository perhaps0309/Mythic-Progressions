package perhaps.progressions.capabilities.perks;

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

public class PerkProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Map<String, Perk>> playerPerksCapability = CapabilityManager.get(new CapabilityToken<>() {});

    private Map<String, Perk> playerPerks = null;
    private final LazyOptional<Map<String, Perk>> optional = LazyOptional.of(this::createPlayerPerks);

    private Map<String, Perk> createPlayerPerks() {
        if (this.playerPerks == null) {
            this.playerPerks = Map.ofEntries(
                    Map.entry("miners_instinct", new Perk()),
                    Map.entry("warriors_might", new Perk()),
                    Map.entry("archers_precision", new Perk()),
                    Map.entry("farmers_bounty", new Perk()),
                    Map.entry("alchemists_wisdom", new Perk()),
                    Map.entry("wizards_sorcery", new Perk())
            );
        }

        return this.playerPerks;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == playerPerksCapability) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        System.out.println("SERIALIZING");
        CompoundTag nbt = new CompoundTag();
        createPlayerPerks().forEach((playerPerk, perkData) -> {
            CompoundTag perkNBT = new CompoundTag();
            perkData.saveNBTData(perkNBT);
            nbt.put(playerPerk, perkNBT);
        });
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        System.out.println("DESERIALIZING");
        createPlayerPerks().forEach((playerPerk, perkData) -> {
            if (nbt.contains(playerPerk)) {
                perkData.loadNBTData(nbt.getCompound(playerPerk));
            }
        });
    }
}
