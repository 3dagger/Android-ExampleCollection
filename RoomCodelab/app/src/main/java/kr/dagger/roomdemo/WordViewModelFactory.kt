package kr.dagger.roomdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
			return WordViewModel(repository) as T
		}
		throw IllegalStateException("UnKnown ViewModel class")
	}


}