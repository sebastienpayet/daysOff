package com.archeon.daysoff.business.util

import java.text.Normalizer
import java.util.*

object StringUtil {
    private fun String.removeNonSpacingMarks() =
        Normalizer.normalize(this, Normalizer.Form.NFD)
            .replace("\\p{Mn}+".toRegex(), "")

    fun String.removeNonSpacingMarksAndLowerCase() = removeNonSpacingMarks().lowercase(Locale.getDefault())
}
