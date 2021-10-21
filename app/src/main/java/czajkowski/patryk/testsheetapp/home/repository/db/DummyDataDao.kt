package czajkowski.patryk.testsheetapp.home.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import czajkowski.patryk.testsheetapp.home.model.DummyDataLocal
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface DummyDataDao {

    @Query("SELECT * from DummyDataLocal where id = :id")
    fun selectDataById(id: Int): Maybe<DummyDataLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: DummyDataLocal): Completable

    @Query("DELETE FROM DummyDataLocal WHERE id = :id")
    fun deleteData(id: Int): Completable

}