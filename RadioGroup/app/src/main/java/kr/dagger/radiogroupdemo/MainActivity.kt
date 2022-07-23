package kr.dagger.radiogroupdemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.orhanobut.logger.Logger
import kr.dagger.radiogroupdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val viewModel : MainViewModel by viewModels()

		val viewDataBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this@MainActivity, R.layout.activity_main)
		setContentView(viewDataBinding.root)

		viewDataBinding.run {
			lifecycleOwner = this@MainActivity
			vm = viewModel
		}

		viewDataBinding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
			when (radioGroup.checkedRadioButtonId) {
				viewDataBinding.rbOne.id -> {
					Logger.d("one")
				}
				viewDataBinding.rbTwo.id -> {
					Logger.d("two")
				}
				viewDataBinding.rbThree.id -> {
					Logger.d("three")
				}
				viewDataBinding.rbFour.id -> {
					Logger.d("four")
				}
				viewDataBinding.rbFive.id -> {
					Logger.d("five")
				}
			}
		}

		viewModel.radioIndex.observe(this) {
			Logger.d("observe it :: $it")
		}

	}
}