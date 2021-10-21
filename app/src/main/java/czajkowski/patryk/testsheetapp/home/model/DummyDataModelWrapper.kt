package czajkowski.patryk.testsheetapp.home.model

data class DummyDataModelWrapper (
    val id: Int,
    val userId: Int,
    val title: String?,
    val body: String?
) {
    constructor(data: DummyDataLocal): this(data.id, data.userId, data.title, data.body)
    constructor(data: DummyDataResponse): this(data.id, data.userId, data.title, data.body)
}