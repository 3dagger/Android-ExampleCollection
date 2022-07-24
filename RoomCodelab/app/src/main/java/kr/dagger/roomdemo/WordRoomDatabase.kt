package kr.dagger.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Room 데이터베이스 클래스는 abstract 이고 RoomDatabase 를 확장해야 함
@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

	abstract fun wordDao(): WordDao

	class WordDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
		override fun onCreate(db: SupportSQLiteDatabase) {
			super.onCreate(db)
			INSTANCE?.let { database ->
				scope.launch {
					populateDatabase(database.wordDao())
				}
			}
		}

		suspend fun populateDatabase(wordDao: WordDao) {
			wordDao.deleteAll()

			// Add sample words

			var word = Word("Hello World")
			wordDao.insert(word)
			word = Word("Come on")
			wordDao.insert(word)
		}
	}

	companion object {

		@Volatile
		private var INSTANCE: WordRoomDatabase? = null

		// 싱글턴을 반환
		fun getDatabase(
			context: Context,
			scope: CoroutineScope
		): WordRoomDatabase {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					WordRoomDatabase::class.java,
					"word_database"
				).addCallback(WordDatabaseCallback(scope))
				.build()
				INSTANCE = instance
				return instance
			}
		}
	}
}