package test

import kotlin.experimental.or

object PasswordValidator {

    fun isValid(str: String): Boolean {
        if (str.length < 9 || str.distinctLength != str.length || str.contains(' ')) {
            return false
        }

        var flags: Byte = 0

        for (c in str) {
            flags = when {
                c.isLowerCase() -> flags or 0x01
                c.isUpperCase() -> flags or 0x02
                c.isDigit() -> flags or 0x04
                c.isSpecial() -> flags or 0x08
                else -> 0
            }
        }

        return flags.toInt() == 15
    }

    private val String.distinctLength: Int
        get() = toSet().size

    private fun Char.isSpecial(): Boolean =
        this == '!' || this == '@' || this == '#' || this == '$' || this == '%' || this == '^' || this == '&'
            || this == '*' || this == '(' || this == ')' || this == '-' || this == '+'

}

object RegexPasswordValidator {

    private val regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()-+])(?=\\S+\$).{9,}\$")

    fun isValid(str: String): Boolean =
        regex.matches(str)
            && str.asIterable().distinct().size == str.length

}
