package me.algosketch.navigation

abstract class NavType<T>(
    val isNullableAllowed: Boolean
) {
    abstract fun parseValue(value: String): T

    companion object {
        val IntType: NavType<Int> = object : NavType<Int>(false) {
            override fun parseValue(value: String): Int {
                return value.toInt()
            }
        }

        val StringType: NavType<String?> = object : NavType<String?>(true) {
            override fun parseValue(value: String): String? {
                return if (value == "null") null else value
            }
        }
    }
}
