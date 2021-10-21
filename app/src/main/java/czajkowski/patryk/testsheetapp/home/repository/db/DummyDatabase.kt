package czajkowski.patryk.testsheetapp.home.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import czajkowski.patryk.testsheetapp.home.model.DummyDataLocal


@Database(entities = [DummyDataLocal::class], version = DummyDatabase.VERSION)
abstract class DummyDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "dummy.db"
        const val VERSION = 1

        @Volatile
        private var INSTANCE: DummyDatabase? = null

        fun getInstance(context: Context): DummyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: bindDatabase(context).also { INSTANCE = it }
            }

        private fun bindDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, DummyDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun dummyDataDao(): DummyDataDao
}
