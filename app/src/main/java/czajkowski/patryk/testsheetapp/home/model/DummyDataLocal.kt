package czajkowski.patryk.testsheetapp.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DummyDataLocal (
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") var id: Int,
    @SerializedName("userId") var userId: Int,
    @SerializedName("title") var title: String?,
    @SerializedName("body") var body: String?
) {
    constructor(data: DummyDataModelWrapper): this(
        id = data.id,
        userId = data.userId,
        title = data.title,
        body = data.body
    )
}