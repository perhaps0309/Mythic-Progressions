package perhaps.progressions.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WheelSelectionScreen extends Screen {
    private final List<String> options;
    private int centerX;
    private int centerY;
    private final int wheelRadius;
    private double angleStep;

    protected WheelSelectionScreen() {
        super(new TextComponent("Wheel Selection"));
        options = new ArrayList<>();
        options.add("Perks");
        options.add("Abilities");
        options.add("Skills");
        options.add("Stats");
        wheelRadius = 90;
    }

    @Override
    protected void init() {
        super.init();
        centerX = width / 2;
        centerY = height / 2;
        angleStep = 360.0 / options.size();
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        int selectedIndex = getHoveredOptionIndex(mouseX, mouseY);
        for (int i = 0; i < options.size(); i++) {
            drawOption(poseStack, options.get(i), i, i == selectedIndex);
        }
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private void drawOption(PoseStack poseStack, String text, int index, boolean isSelected) {
        double angle = Math.toRadians((index * 90) - 90);
        int x = centerX + (int) (wheelRadius * Math.cos(angle));
        int y = centerY + (int) (wheelRadius * Math.sin(angle));
        int color = 0xFFFFFF;
        float scale = isSelected ? 1.5f : 1.0f;
        drawScaledText(poseStack, text, x, y, color, scale, true);
    }

    private void drawScaledText(PoseStack poseStack, String text, int x, int y, int color, float scale, boolean center) {
        poseStack.pushPose();
        poseStack.scale(scale, scale, 1.0f);
        int newX = center ? (int) (x - font.width(text) * scale / 2) : x;
        int newY = center ? (int) (y - 4 * scale) : y;
        font.draw(poseStack, text, newX / scale, newY / scale, color);
        poseStack.popPose();
    }

    private int getHoveredOptionIndex(int mouseX, int mouseY) {
        for (int i = 0; i < options.size(); i++) {
            double angle = Math.toRadians(i * angleStep - 90);
            int x = centerX + (int) (wheelRadius * Math.cos(angle));
            int y = centerY + (int) (wheelRadius * Math.sin(angle));
            Rectangle bounds = new Rectangle(x - 20, y - 20, 40, 40);
            if (bounds.contains(mouseX, mouseY)) {
                return i;
            }
        }
        return -1;
    }
}