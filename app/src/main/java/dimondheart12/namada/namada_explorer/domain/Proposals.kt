package dimondheart12.namada.namada_explorer.domain

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Proposals(
    val proposals: List<Proposal>
) : Parcelable {
    @Parcelize
    data class Proposal(
        val id: Long,
        val content: String,
        val kind: String,
        val author: Author,
        @SerializedName("start_epoch")
        val startEpoch: Long,
        @SerializedName("end_epoch")
        val endEpoch: Long,
        @SerializedName("grace_epoch")
        val graceEpoch: Long,
        val result: String,
        @SerializedName("yay_votes")
        val yayVotes: String,
        @SerializedName("nay_votes")
        val nayVotes: String,
        @SerializedName("abstain_votes")
        val abstainVotes: String
    ) : Parcelable {
        val resultValue: Result?
            get() = Result.fromValue(result)

        @Parcelize
        data class Author(
            @SerializedName("Account")
            val account: String
        ) : Parcelable

        @Parcelize
        enum class Result(val value: String) : Parcelable {
            Pending("Pending"),
            VotingPeriod("VotingPeriod"),
            Rejected("Rejected"),
            Passed("Passed");

            companion object {
                fun fromValue(value: String): Result? {
                    return entries.firstOrNull { it.value == value }
                }
            }

            val backgroundColor: Color
                get() = when (this) {
                    Pending -> Color.Yellow
                    VotingPeriod -> Color.Green
                    Rejected -> Color.Red
                    Passed -> Color.Yellow
                }

            val textColor: Color
                get() = when (this) {
                    Pending -> Color.Black
                    VotingPeriod -> Color.Black
                    Rejected -> Color.White
                    Passed -> Color.Black
                }
        }
    }
}