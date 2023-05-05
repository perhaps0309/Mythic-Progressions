package perhaps.progressions;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.TextComponent;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WheelSelectionScreen extends Screen {
    private int centerX;
    private int centerY;
    private int wheelRadius;
    private double angleStep;

    private class WheelOption {
        ResourceLocation icon;
        String title;
        String description;

        WheelOption(ResourceLocation icon, String title, String description) {
            this.icon = icon;
            this.title = title;
            this.description = description;
        }
    }

    private List<WheelOption> options;

    private static final ResourceLocation PERKS_ICON = new ResourceLocation("textures/gui/icons/perks.png");
    private static final ResourceLocation ABILITIES_ICON = new ResourceLocation("textures/gui/icons/abilities.png");
    private static final ResourceLocation SKILLS_ICON = new ResourceLocation("textures/gui/icons/skills.png");
    private static final ResourceLocation STATS_ICON = new ResourceLocation("textures/gui/icons/stats.png");
    protected WheelSelectionScreen() {
        super(new TextComponent("Wheel Selection"));
        options = new ArrayList<>();
        options.add(new WheelOption(PERKS_ICON, "Perks", "Select a perk to unlock"));
        options.add(new WheelOption(ABILITIES_ICON, "Abilities", "Gain an advantage"));
        options.add(new WheelOption(SKILLS_ICON, "Skills", "Upgrade your skills"));
        options.add(new WheelOption(STATS_ICON, "Stats", "View your stats"));
        wheelRadius = 100;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        centerX = width / 2;
        centerY = height / 2;
        angleStep = 360.0 / options.size();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        int selectedIndex = getHoveredOptionIndex(mouseX, mouseY);
        for (int i = 0; i < options.size(); i++) {
            drawCircle(poseStack, centerX, centerY, wheelRadius + 15, wheelRadius - 15, 0x888888, i == selectedIndex ? i : -1);
        }

        for (int i = 0; i < options.size(); i++) {
            drawOption(poseStack, options.get(i), i, i == selectedIndex);
        }

        if (selectedIndex >= 0) {
            WheelOption selectedOption = options.get(selectedIndex);
            drawScaledText(poseStack, selectedOption.title, centerX, centerY - 10, 0xFFFFFF, 1.15F, true);
            drawScaledText(poseStack, selectedOption.description, centerX, centerY + 5, 0xFFFFFF, 0.95F, true); // Change the scale here for the description
        }

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private void drawOption(PoseStack poseStack, WheelOption option, int index, boolean isSelected) {
        double angle = Math.toRadians((index * angleStep) - 90 + 45);

        int x = centerX + (int) (wheelRadius * Math.cos(angle));
        int y = centerY + (int) (wheelRadius * Math.sin(angle));

        drawIcon(poseStack, option.icon, x, y, 1.0f);
    }

    private void drawIcon(PoseStack poseStack, ResourceLocation texture, int x, int y, float scale) {
        poseStack.pushPose();
        poseStack.scale(scale, scale, 1.0f);
        int size = 16;
        int newX = (int) (x - size * scale / 2);
        int newY = (int) (y - size * scale / 2);

        RenderSystem.setShaderTexture(0, texture);
        blit(poseStack, newX, newY, 0, 0, size, size, size, size);
        poseStack.popPose();
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
            double angle = Math.toRadians(i * angleStep - 90 + 45);
            int x = centerX + (int) (wheelRadius * Math.cos(angle));
            int y = centerY + (int) (wheelRadius * Math.sin(angle));
            Rectangle bounds = new Rectangle(x - 20, y - 20, 40, 40);
            if (bounds.contains(mouseX, mouseY)) {
                return i;
            }
        }
        return -1;
    }

    private void drawCircle(PoseStack poseStack, int centerX, int centerY, int outerRadius, int innerRadius, int color, int selectedIndex) {
        int numSegments = 360;
        float angleStep = 360.0f / numSegments;

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        poseStack.pushPose();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

        Matrix4f matrix = poseStack.last().pose();

        for (int i = 0; i <= numSegments; i++) {
            double angle = Math.toRadians(angleStep * i);
            float outerX = centerX + (float) (outerRadius * Math.cos(angle));
            float outerY = centerY + (float) (outerRadius * Math.sin(angle));
            float innerX = centerX + (float) (innerRadius * Math.cos(angle));
            float innerY = centerY + (float) (innerRadius * Math.sin(angle));

            int currentColor = color;
            if (selectedIndex == i * options.size() / numSegments) {
                currentColor = 0x0000FF;
            }

            buffer.vertex(matrix, outerX, outerY, 0.0F).color((currentColor >> 16) & 255, (currentColor >> 8) & 255, currentColor & 255, 255).endVertex();
            buffer.vertex(matrix, innerX, innerY, 0.0F).color((currentColor >> 16) & 255, (currentColor >> 8) & 255, currentColor & 255, 255).endVertex();
        }

        buffer.end();
        BufferUploader.end(buffer);

        poseStack.popPose();

        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }
}