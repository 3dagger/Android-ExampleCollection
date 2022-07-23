package kr.dagger.radiogroupdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
	private val _radioIndex = MutableLiveData<Int>()
	val radioIndex: LiveData<Int>
		get() = _radioIndex

	fun getRadioIndex() {
		_radioIndex.value
	}

	fun setRadioIndex(index: Int) {
		_radioIndex.value = index
	}
}