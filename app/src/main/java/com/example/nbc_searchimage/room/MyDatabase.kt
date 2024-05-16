package com.example.nbc_searchimage.room

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
//todo migration 공부
@Database(entities = [SelectedItemEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun selectedItemDao(): SelectedItemDAO

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null
//        private val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {  }
//        }

        //todo context 넘기지 말고 application으로 변경
        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}