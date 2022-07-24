package kr.dagger.roomdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kr.dagger.roomdemo.databinding.ActivityNewWordBinding

class NewWordActivity : AppCompatActivity() {
	private lateinit var binding: ActivityNewWordBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityNewWordBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.buttonSave.setOnClickListener {
			val replyIntent = Intent()
			if (TextUtils.isEmpty(binding.editWord.text)) {
				setResult(Activity.RESULT_CANCELED, replyIntent)
			} else {
				val word = binding.editWord.text.toString()
				replyIntent.putExtra(EXTRA_REPLY, word)
				setResult(Activity.RESULT_OK, replyIntent)
			}
			finish()
		}
	}

	companion object {
		const val EXTRA_REPLY = "EXTRA_REPLY"
	}
}