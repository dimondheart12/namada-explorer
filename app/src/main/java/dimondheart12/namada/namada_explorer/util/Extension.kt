package dimondheart12.namada.namada_explorer.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

private val dateFormatters = listOf(
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault()),
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
)
private val numberFormat = DecimalFormat("#,###")
private val dateFormatter = SimpleDateFormat("HH:mm, dd/MM/yyyy", Locale.getDefault())

val String.date: Date?
    get() {
        for (format in dateFormatters) {
            try {
                return format.parse(this)
            } catch (e: Exception) {
                continue
            }
        }
        return null
    }

val Number.formatWithCommas: String
    get() = numberFormat.format(this)

fun Date?.toString(): String {
    return if (this == null) {
        ""
    } else {
        dateFormatter.format(this)
    }
}

val String.formatDate: String
    get() {
        val date = this.date
        return if (date == null) {
            this
        } else {
            dateFormatter.format(date)
        }
    }

fun String.timeAgoString(now: Date = Date()): String {
    val date = this.date ?: return this
    val timeDifference = TimeUnit.MILLISECONDS.toSeconds(now.time - date.time)
    return when {
        timeDifference < 60 -> "1 minute ago"
        timeDifference < 3600 -> "${timeDifference / 60} minutes ago"
        timeDifference < 7200 -> "1 hour ago"
        timeDifference < 86400 -> "${timeDifference / 3600} hours ago"
        timeDifference < 172800 -> "1 day ago"
        else -> formatDate
    }
}

fun Double.formatWithCommas(decimalPlaces: Int): String {
    val pattern = StringBuilder("#,##0.")
    repeat(decimalPlaces) { pattern.append("#") }
    val df = DecimalFormat(pattern.toString())
    return df.format(this)
}