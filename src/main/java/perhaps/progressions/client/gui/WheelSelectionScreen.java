package perhaps.progressions.client.gui;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.TextComponent;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;
import perhaps.progressions.MythicProgressions;

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
    private float rotation = 0;

    private static final ResourceLocation PERKS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/perks.jpg");
    private static final ResourceLocation ABILITIES_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/abilities.png");
    private static final ResourceLocation SKILLS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/skills.png");
    private static final ResourceLocation STATS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/stats.png");
    public WheelSelectionScreen() {
        super(new TextComponent("Wheel Selection"));
        options = new ArrayList<>();
        options.add(new WheelOption(PERKS_ICON, "Perks", "Select a perk to unlock"));
        options.add(new WheelOption(ABILITIES_ICON, "Abilities", "Gain an advantage"));
        options.add(new WheelOption(SKILLS_ICON, "Skills", "Upgrade your skills"));
        options.add(new WheelOption(STATS_ICON, "Stats", "View your stats"));
        wheelRadius = 90;
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
        rotation += 0.002f; // Adjust the speed of the rotation here
        int selectedIndex = getHoveredOptionIndex(mouseX, mouseY, (float) (rotation * 180 / Math.PI));

        for (int i = 0; i < options.size(); i++) {
            drawCircle(poseStack, centerX, centerY, wheelRadius + 15, wheelRadius - 15, 0x888888, selectedIndex, i);
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
        double angle = Math.toRadians((index * angleStep) - 90 + 45) + rotation;

        int x = centerX + (int) (wheelRadius * Math.cos(angle));
        int y = centerY + (int) (wheelRadius * Math.sin(angle));

        // Adjust the position of the icon based on the current rotation
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

    private double calculateAngle(int x1, int y1, int x2, int y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        return Math.atan2(deltaY, deltaX);
    }

    private int getHoveredOptionIndex(int mouseX, int mouseY, float rotation) {
        int distanceX = mouseX - centerX;
        int distanceY = mouseY - centerY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        // Check if the mouse position is within the circle
        if (distance <= wheelRadius + 15 && distance >= wheelRadius - 15) {
            double mouseAngle = Math.toDegrees(calculateAngle(centerX, centerY, mouseX, mouseY));
            if (mouseAngle < 0) {
                mouseAngle += 360;
            }

            // Reset rotation angle to avoid it getting too big
            float rotationInDegrees = (float) (rotation * 180 / Math.PI) % 360;

            mouseAngle = (mouseAngle - rotation * 180 / Math.PI + angleStep / 2) % 360;
            if (mouseAngle < 0) {
                mouseAngle += 360;
            }

            for (int i = 0; i < options.size(); i++) {
                double startAngle = (i * angleStep) % 360;
                double endAngle = ((i + 1) * angleStep) % 360;

                if (startAngle < endAngle && mouseAngle >= startAngle && mouseAngle <= endAngle) {
                    return i;
                } else if (startAngle > endAngle && (mouseAngle >= startAngle || mouseAngle <= endAngle)) {
                    return i;
                }
            }
        }

        return -1;
    }

    private void drawCircle(PoseStack poseStack, int centerX, int centerY, int outerRadius, int innerRadius, int color, int selectedIndex, int currentIndex) {
        int numSegments = 360;
        float angleStep = 360.0f / numSegments;

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        poseStack.pushPose();
        Matrix4f matrix = poseStack.last().pose();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

        int optionsSize = options.size();
        for (int i = 0; i <= numSegments; i++) {
            if (currentIndex != (i * optionsSize / numSegments)) {
                continue;
            }

            double angle = Math.toRadians(angleStep * i) + rotation;
            float outerX = centerX + (float) (outerRadius * Math.cos(angle));
            float outerY = centerY + (float) (outerRadius * Math.sin(angle));
            float innerX = centerX + (float) (innerRadius * Math.cos(angle));
            float innerY = centerY + (float) (innerRadius * Math.sin(angle));

            int currentColor = selectedIndex == currentIndex ? 0x0000FF : color;
            buffer.vertex(matrix, outerX, outerY, 0.0F).color((currentColor >> 16) & 255, (currentColor >> 8) & 255, currentColor & 255, 255).endVertex();
            buffer.vertex(matrix, innerX, innerY, 0.0F).color((currentColor >> 16) & 255, (currentColor >> 8) & 255, currentColor & 255, 255).endVertex();
        }

        buffer.end();
        BufferUploader.end(buffer);

        // Draw outline
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder outlineBuffer = Tesselator.getInstance().getBuilder();
        outlineBuffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

        float outlineWidth = 0.35f; // You can adjust the outline width here
        for (int i = 0; i <= numSegments; i++) {
            if (currentIndex != (i * optionsSize / numSegments)) {
                continue;
            }

            double angle = Math.toRadians(angleStep * i) + rotation;
            float outerX = centerX + (float) ((outerRadius + outlineWidth) * Math.cos(angle));
            float outerY = centerY + (float) ((outerRadius + outlineWidth) * Math.sin(angle));
            float innerX = centerX + (float) ((outerRadius - outlineWidth) * Math.cos(angle));
            float innerY = centerY + (float) ((outerRadius - outlineWidth) * Math.sin(angle));

            outlineBuffer.vertex(matrix, outerX, outerY, 0.0F).color(0, 0, 0, 255).endVertex();
            outlineBuffer.vertex(matrix, innerX, innerY, 0.0F).color(0, 0, 0, 255).endVertex();
        }

        outlineBuffer.end();
        BufferUploader.end(outlineBuffer);

        // Draw outline
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        outlineBuffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

        for (int i = 0; i <= numSegments; i++) {
            if (currentIndex != (i * optionsSize / numSegments)) {
                continue;
            }

            double angle = Math.toRadians(angleStep * i) + rotation;
            float outerX = centerX + (float) ((innerRadius + outlineWidth) * Math.cos(angle));
            float outerY = centerY + (float) ((innerRadius + outlineWidth) * Math.sin(angle));
            float innerX = centerX + (float) ((innerRadius - outlineWidth) * Math.cos(angle));
            float innerY = centerY + (float) ((innerRadius - outlineWidth) * Math.sin(angle));

            outlineBuffer.vertex(matrix, outerX, outerY, 0.0F).color(0, 0, 0, 255).endVertex();
            outlineBuffer.vertex(matrix, innerX, innerY, 0.0F).color(0, 0, 0, 255).endVertex();
        }

        outlineBuffer.end();
        BufferUploader.end(outlineBuffer);

        poseStack.popPose();

        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }
}