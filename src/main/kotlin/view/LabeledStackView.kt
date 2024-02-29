package view

import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

/**
 * This class represents a specialized [CardStack] designed for [CardView] elements, each standardized at
 * 130px in width and 200px in height. It's intended for use with all game stacks displayed on the table.
 *
 * By default, it features a [CompoundVisual] that merges a translucent white [ColorVisual]
 * with a [TextVisual] to display a specified label.
 *
 * @param label Specifies the text label for this stack view. If not provided, it defaults to an empty string.
 * @param rotate When set to true, the label text will be rotated by 180 degrees.
 */
class LabeledStackView(posX: Number, posY: Number, label: String = "", rotate: Int = 0) :
    CardStack<CardView>(height = 200, width = 130, posX = posX, posY = posY) {

    init {
        visual = CompoundVisual(
            ColorVisual(Color(255, 255, 255, 50)),
            TextVisual(label)
        ).apply {
            rotation = when (rotate) {
                90 -> 90.0
                180 -> 180.0
                270 -> 270.0
                else -> 0.0
            }
        }
    }

}