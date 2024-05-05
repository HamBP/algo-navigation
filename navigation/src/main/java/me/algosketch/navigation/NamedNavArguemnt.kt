package me.algosketch.navigation

import java.lang.IllegalStateException

fun navArgument(
    name: String,
    builder: NamedNavArgument.Builder.() -> Unit,
): NamedNavArgument {
    return NamedNavArgument.Builder().apply(builder).build(name)
}

class NamedNavArgument internal constructor(
    val name: String,
    val type: NavType<Any?>,
    val isNullable: Boolean,
) {
    @Suppress("UNCHECKED_CAST")
    class Builder {
        private var _type: NavType<*>? = null
        var type: NavType<*>
            get() {
                return _type ?: throw IllegalStateException("NavType이 빌더에서 초기화되지 않았어요.")
            }
            set(value) {
                _type = value
            }

        var isNullable = false

        fun build(name: String): NamedNavArgument {
            val finalType = type as NavType<Any?>
            return NamedNavArgument(name, finalType, isNullable)
        }
    }
}
