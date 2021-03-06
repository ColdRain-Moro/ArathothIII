package kim.bifrost.rain.arathoth.api.data

/**
 * kim.bifrost.rain.arathoth.api.data.AttributeData
 * Arathoth
 *
 * @author 寒雨
 * @since 2022/3/19 15:19
 **/
interface AttributeData {
    fun append(data: AttributeData): AttributeData

    operator fun plus(data: AttributeData?): AttributeData {
        if (data == null) return this
        return append(data)
    }
}