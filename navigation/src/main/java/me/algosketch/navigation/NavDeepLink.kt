package me.algosketch.navigation

import java.util.regex.Pattern

class NavDeepLink(
    private val uriPattern: String? = null,
) {
    private val pathArgs = mutableListOf<String>()
    private var pathRegex: String? = null

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

    init {
        parsePath()
    }
}