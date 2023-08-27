package com.kinnerapriyap.project

import io.kvision.*
import io.kvision.core.Background
import io.kvision.core.Color
import io.kvision.core.CssSize
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.html.h2
import io.kvision.html.image
import io.kvision.panel.Root
import io.kvision.panel.root
import io.kvision.utils.em

class App : Application() {
    override fun start() {
        root("kvapp") {
            h2(content = "Dibba Dibba")
            val pizzazz = getPizzaSlices()
            div { height = 2.em }
            div {
                pizzazz.subList(0, 2).forEachIndexed { index, (path, color) ->
                    getImage("name$index", path, color)
                }
                js("document.querySelector('#name0').style.transform = 'rotate(0deg)'")
                js("document.querySelector('#name1').style.transform = 'rotate(90deg)'")
            }
            div {
                pizzazz.subList(2, 4).forEachIndexed { index, (path, color) ->
                    getImage("name${index + 2}", path, color)
                }
                js("document.querySelector('#name2').style.transform = 'rotate(270deg)'")
                js("document.querySelector('#name3').style.transform = 'rotate(180deg)'")
            }
            div { height = 2.em }
            div {
                button("Next dibba pwease (this is not a typo)") {
                    onClick {
                        js("window.location.reload()")
                    }
                }
            }
        }
        js("document.getElementById('kvapp').setAttribute('align', 'center')")
    }
}

private fun Root.getImage(name: String, path: String, color: Color, size: CssSize = 16.em) =
    image(path) {
        id = name
        height = size
        background = Background(color)
    }

private fun getPizzaSlices(): List<Pair<String, Color>> {
    val sauces = rearrangeSaucesFromRange(PIZZA_SAUCES)
    return (0..3).map { getRandomSliceFromRange(PIZZA_SLICES) to sauces[it] }
}

private fun getRandomSliceFromRange(slices: Set<String>): String = slices.random()

private fun rearrangeSaucesFromRange(sauces: Set<Color>): List<Color> = sauces.shuffled()

private val PIZZA_SLICES = setOf(
    "img/cbo.png",
    "img/cpb.png",
    "img/cto.png",
    "img/mtb.png",
    "img/mto.png",
    "img/ppp.png"
)

private val PIZZA_SAUCES = setOf(
    Color.hex(0x89e7fe), // BLUE
    Color.hex(0xe46c6c), // RED
    Color.hex(0xfff17e), // YELLOW
    Color.hex(0x3cc764), // GREEN
)

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        ToastifyModule,
        CoreModule
    )
}
