package kr.dagger.roomdemo

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

	val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

	fun insert(word: Word) = viewModelScope.launch {
		repository.insert(word)
	}
}