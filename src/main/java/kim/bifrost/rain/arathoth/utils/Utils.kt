package kim.bifrost.rain.arathoth.utils

import org.bukkit.command.CommandSender
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.function.console
import taboolib.module.chat.colored
import taboolib.platform.util.isNotAir
import java.io.File
import java.util.function.Consumer
import java.util.regex.Pattern

/**
 * kim.bifrost.rain.arathoth.utils.Utils
 * Arathoth
 *
 * @author 寒雨
 * @since 2022/3/18 18:41
 **/
fun File.new(name: String): File {
    return File(this, name)
}

inline fun <reified T: Event> subscribe(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline subscriber: T.() -> Unit
): Events<T> {
    return Events.subscribe(T::class.java, Consumer { it.subscriber() })
}

fun info(message: String) {
    console().sendMessage("&7&l[&f&lArathoth&7&l] &7$message".colored())
}

fun error(message: String) {
    console().sendMessage("&c&l[&4&lArathoth&c&l] &c$message".colored())
}

fun CommandSender.info(message: String) {
    sendMessage("&7&l[&f&lArathoth&7&l] &7$message".colored())
}

fun CommandSender.error(message: String) {
    sendMessage("&c&l[&4&lArathoth&c&l] &c$message".colored())
}

fun String.asRegexPattern(): Pattern {
    return Pattern.compile(
        this.replace("[VALUE]", "(?<value>\\S+)")
            .replace("[NUMBER]", "(?<number>\\d+)")
    )
}

fun LivingEntity.getEntityItems(): List<ItemStack> {
    val equip = equipment ?: return emptyList()
    return buildList {
        addAll(equip.armorContents.filter { it.isNotAir() })
        if (equip.itemInMainHand.isNotAir()) add(equip.itemInMainHand)
        if (equip.itemInOffHand.isNotAir()) add(equip.itemInOffHand)
    }
}

fun <T> buildList(block: MutableList<T>.() -> Unit): List<T> {
    return mutableListOf<T>().apply(block)
}

val ItemStack.lore: List<String>
    get() {
        val lore = mutableListOf<String>()
        val meta = itemMeta
        if (meta != null) {
            if (meta.hasLore()) {
                lore.addAll(meta.lore!!)
            }
        }
        return lore
    }