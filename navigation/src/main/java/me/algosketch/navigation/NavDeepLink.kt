package me.algosketch.navigation

import android.net.Uri
import android.os.Bundle
import java.util.regex.Pattern

class NavDeepLink(
    val uriPattern: String? = null,
) {
    private val pathArgs = mutableListOf<String>()
    private var pathRegex: String? = null
    private val pathPattern by lazy {
        pathRegex?.let { Pattern.compile(it, Pattern.CASE_INSENSITIVE) }
    }

    private companion object {
        private val FILL_IN_PATTERN = Pattern.compile("\\{(.+?)\\}")
    }

    private fun parsePath() {
        if (uriPattern == null) return

        val uriRegex = StringBuilder("")

        buildRegex(uriPattern, pathArgs, uriRegex)

        pathRegex = uriRegex.toString()
    }

    private fun buildRegex(uri: String, args: MutableList<String>, uriRegex: StringBuilder) {
        val matcher = FILL_IN_PATTERN.matcher(uri)
        var appendPos = 0
        while (matcher.find()) {
            val argName = matcher.group(1)
            args.add(argName)

            if (matcher.start() > appendPos) {
                uriRegex.append(Pattern.quote(uri.substring(appendPos, matcher.start())))
            }
            uriRegex.append("([^/]+?)")
            appendPos = matcher.end()
        }
        if (appendPos < uri.length) {
            uriRegex.append(Pattern.quote(uri.substring(appendPos)))
        }
    }

    fun getMatchingArguments(
        deepLink: String,
        arguments: Map<String, NamedNavArgument>
    ): Bundle? {
        val matcher = pathPattern?.matcher(deepLink) ?: return null

        if (!matcher.matches()) return null

        val bundle = Bundle()
        pathArgs.forEachIndexed { index, argumentName ->
            val value = Uri.decode(matcher.group(index + 1))
            val argument = arguments[argumentName]
            if (argument != null) {
                val type = argument.type
                type.parseAndPut(bundle, argumentName, value)
            }
        }

        return bundle
    }

    init {
        parsePath()
    }
}
