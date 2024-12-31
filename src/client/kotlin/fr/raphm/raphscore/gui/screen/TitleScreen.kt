package fr.raphm.raphscore.gui.screen

import fr.raphm.raphscore.gui.widget.TitleScreenButton
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.LogoDrawer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.SplashTextRenderer
import net.minecraft.client.gui.screen.world.SelectWorldScreen
import net.minecraft.client.gui.widget.ButtonWidget.NarrationSupplier
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import java.util.function.Supplier

class TitleScreen : Screen(Text.literal("Title screen")) {
    private var logoDrawer: LogoDrawer? = null
    private var splashText: SplashTextRenderer? = null
    private var singleplayerBtn: TitleScreenButton? = null
    private val COPYRIGHT: Text = Text.translatable("title.credits")
    private val RAPHSCORE_COPYRIGHT: Text = Text.literal("Copyright (C) 2025 Raphaël M. Raph's Core is open source and licensed under the MIT License")

    private fun getLogoBottom(): Int {
        return LogoDrawer.LOGO_BASE_Y +
                44 + // LogoDrawer.LOGO_TEXTURE_HEIGHT
                14 - // LogoDrawer.EDITION_TEXTURE_HEIGHT
                7    // LogoDrawer.LOGO_AND_EDITION_OVERLAP
    }

    fun getLogoHeight(): Int {
        return 44 + // LogoDrawer.LOGO_TEXTURE_HEIGHT
                14 - // LogoDrawer.EDITION_TEXTURE_HEIGHT
                7    // LogoDrawer.LOGO_AND_EDITION_OVERLAP
    }

    override fun renderBackground(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        super.renderBackground(context, mouseX, mouseY, delta)
        renderPanoramaBackground(context, delta)
    }

    override fun init() {
        super.init()

        singleplayerBtn = TitleScreenButton(
            30, getLogoBottom() + 30,
            120,
            40,
            Text.literal("Singleplayer"),
            { btn ->
                client?.setScreen(SelectWorldScreen(this))
            },
            NarrationSupplier { textSupplier: Supplier<MutableText?> -> textSupplier.get() }
        )
        addDrawableChild(singleplayerBtn)

        logoDrawer = LogoDrawer(false)
        splashText = SplashTextRenderer("Hello, World!")
    }

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)

        // Render da copyright text
        var i = this.textRenderer.getWidth(COPYRIGHT);
        var j = this.width - i - 2;
        context?.drawText(textRenderer, COPYRIGHT, j, this.height - 10, 0xFFFFFF, false);

        // Render the logo & the beautiful splash text.
        logoDrawer?.draw(context, width, 1.0f)
        splashText?.render(context, width, textRenderer, 255);

        context?.drawText(
            textRenderer,
            "RAPHSCORE0.0.1-MC1.21.4",
            4, 4,
            0xFFFFFF,
            true
        )

        // Draw categories text
        context?.drawText(
            textRenderer,
            "Play",
            30, getLogoBottom() + 10,
            0xFFFFFF,
            true
        )
    }
}
