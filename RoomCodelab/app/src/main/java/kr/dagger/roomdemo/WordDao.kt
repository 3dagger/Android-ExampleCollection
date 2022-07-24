package kr.dagger.roomdemo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


// Data Access Object(DAO) 는 인터페이스 또는 추상 클래스여야 한다
@Dao
interface WordDao {

	// 모든 단어를 알파벳순으로 정렬
	// 데이터 옵저빙을 위해 Flow 타입으로 Wrapping
	@Query("SELECT * FROM word_table ORDER BY word ASC")
	fun getAlphabetizedWords(): Flow<List<Word>>

	// 단어 삽입
	// @Insert 어노테이션은 SQL 을 제공하지 않아도 되는 특수 DAO 메서드 어노테이션
	// onConflict Strategy 는 이미 목록에 있는 단어와 정확하게 같다면 새단어를 무시함
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insert(word: Word)

	// 모든 단어 삭제
	@Query("DELETE FROM word_table")
	suspend fun deleteAll()


}