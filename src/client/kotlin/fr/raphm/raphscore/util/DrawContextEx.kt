package fr.raphm.raphscore.util

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import org.joml.Matrix4f
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

class DrawContextEx {

    companion object {

        fun DrawContext.getVertexConsumers(): VertexConsumerProvider.Immediate? {
            val secretField = DrawContext::class.declaredMemberProperties.find { it.name == "vertexConsumers" }
            secretField?.isAccessible = true
            return secretField?.get(this) as VertexConsumerProvider.Immediate?
        }

        fun DrawContext.fillGradientLTR(startX: Int, startY: Int, endX: Int, endY: Int, colorStart: Int, colorEnd: Int) {
            this.fillGradientLTR(startX, startY, endX, endY, 0, colorStart, colorEnd)
        }

        fun DrawContext.fillGradientLTR(startX: Int, startY: Int, endX: Int, endY: Int, z: Int, colorStart: Int, colorEnd: Int) {
            this.fillGradientLTR(RenderLayer.getGui(), startX, startY, endX, endY, colorStart, colorEnd, z)
        }

        fun DrawContext.fillGradientLTR(
            layer: RenderLayer?,
            startX: Int,
            startY: Int,
            endX: Int,
            endY: Int,
            colorStart: Int,
            colorEnd: Int,
            z: Int
        ) {
            val vertexConsumer: VertexConsumer = this.getVertexConsumers()?.getBuffer(layer) ?: return
            this.fillGradientLTR(vertexConsumer, startX, startY, endX, endY, z, colorStart, colorEnd)
        }

        fun DrawContext.fillGradientLTR(
            vertexConsumer: VertexConsumer,
            startX: Int,
            startY: Int,
            endX: Int,
            endY: Int,
            z: Int,
            colorStart: Int,
            colorEnd: Int
        ) {
            val matrix4f: Matrix4f = this.matrices.peek().positionMatrix
            vertexConsumer.vertex(matrix4f, startX.toFloat(), startY.toFloat(), z.toFloat()).color(colorStart)
            vertexConsumer.vertex(matrix4f, startX.toFloat(), endY.toFloat(), z.toFloat()).color(colorStart)
            vertexConsumer.vertex(matrix4f, endX.toFloat(), endY.toFloat(), z.toFloat()).color(colorEnd)
            vertexConsumer.vertex(matrix4f, endX.toFloat(), startY.toFloat(), z.toFloat()).color(colorEnd)
        }
    }
}