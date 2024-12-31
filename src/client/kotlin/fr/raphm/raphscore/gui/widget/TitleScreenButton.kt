package fr.raphm.raphscore.gui.widget

import fr.raphm.raphscore.util.Animator
import fr.raphm.raphscore.util.DrawContextEx.Companion.fillGradientLTR
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.render.RenderLayer
import net.minecraft.text.Text
import net.minecraft.util.Identifier

/*
 * Button made
 */
class TitleScreenButton(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    message: Text,
    onPress: PressAction,
    narrationSupplier: NarrationSupplier
) : ButtonWidget(x, y, width, height, message, onPress, narrationSupplier) {

    private var xOffset: Int = 0
    private var ttb: Boolean = false
    private var size: Int = 16
    private var fakeWidth: Int = width
    private var fakeHeight: Int = height
    private val iconSizeAnimator = Animator(16f, 20f, 3f) { newSize -> size = newSize.toInt() }

    private val widthAnimator = Animator(width.toFloat(), width.toFloat() - 16, 3f) { newWidth ->
        fakeWidth = newWidth.toInt()
    }
    private val heightAnimator = Animator(height.toFloat(), height.toFloat() - 16, 3f) { newHeight ->
        fakeHeight = newHeight.toInt()
    }

    private var animIsHovered: Boolean = false
    private var animIsSelected: Boolean = false

    override fun renderWidget(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        iconSizeAnimator.update(delta)
        widthAnimator.update(delta)
        heightAnimator.update(delta)

        handleAnimation()

        drawBackground(context)
        drawIcon(context)
        drawText(context)
    }

    private fun handleAnimation() {
        if (isHovered != animIsHovered) {
            animIsHovered = isHovered
            val (start, end) = if (isHovered) Pair(16f, 20f) else Pair(20f, 16f)
            iconSizeAnimator.startValue = start
            iconSizeAnimator.endValue = end
            iconSizeAnimator.start()
        }
    }

    private fun drawBackground(context: DrawContext?) {
        context?.fill(
            x + size - 16,
            y + size - 16,
            x + fakeWidth + size - 16,
            y + fakeHeight + size - 16,
            0x60161616.toInt()
        )

        context?.fill(x, y, x + fakeWidth, y + fakeHeight, 0xFF161616.toInt())

        context?.fillGradientLTR(
            x + 2, y + 2, x + fakeWidth - 2, y + fakeHeight - 2,
            0x600000FFu.toInt(),
            0x000000FFu.toInt()
        )
    }

    private fun drawIcon(context: DrawContext?) {
        val iconX = x + (fakeHeight / 2 - size / 2)
        val iconY = y + (fakeHeight / 2 - size / 2)

        // TODO: allow changing the icon
        context?.drawTexture(
            RenderLayer::getGuiTextured,
            Identifier.of("minecraft", "textures/item/netherite_sword.png"),
            iconX,
            iconY,
            0f, 0f,
            size, size,
            size, size,
            size, size);
    }

    private fun drawText(context: DrawContext?) {
        val textRenderer = MinecraftClient.getInstance().textRenderer
        val textX = x + (fakeHeight / 2 - size / 2) + size + 5
        val textY = y + ((fakeHeight + 4) / 2 - 10 / 2)
        context?.drawText(textRenderer, this.message, textX, textY, 0xFFFFFF, true)
    }
}