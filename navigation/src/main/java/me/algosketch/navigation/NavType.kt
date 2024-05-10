package me.algosketch.navigation

import android.os.Bundle

abstract class NavType<T>(
    val isNullableAllowed: Boolean
) {
    abstract fun parseValue(value: String): T

    abstract fun put(bundle: Bundle, key: String, value: T)

    fun parseAndPut(bundle: Bundle, key: String, value: String) {
        val parsedValue = parseValue(value)
        put(bundle, key, parsedValue)
    }

    companion object {
        val IntType: NavType<Int> = object : NavType<Int>(false) {
            override fun parseValue(value: String): Int {
                return value.toInt()
            }

            override fun put(bundle: Bundle, key: String, value: Int) {
                bundle.putInt(key, value)
            }
        }

        val StringType: NavType<String?> = object : NavType<String?>(true) {
            override fun parseValue(value: String): String? {
                return if (value == "null") null else value
            }

            override fun put(bundle: Bundle, key: String, value: String?) {
                bundle.putString(key, value)
            }
        }
    }
}
