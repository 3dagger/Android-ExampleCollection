package kr.dagger.roomdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kr.dagger.roomdemo.databinding.ActivityMainBinding

class WordActivity : AppCompatActivity() {

	lateinit var binding: ActivityMainBinding
	private val wordListAdapter: WordListAdapter by lazy { WordListAdapter() }
	private val viewModel: WordViewModel by viewModels {
		WordViewModelFactory((application as App).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setRecyclerView()
		setFloatingActionButton()
		subscribeObservers()
	}

	private fun setRecyclerView() {
		binding.recyclerView.apply {
			adapter = wordListAdapter
			layoutManager = LinearLayoutManager(this@WordActivity)
		}
	}

	private fun setFloatingActionButton() {
		binding.fab.setOnClickListener {
			val intent = Intent(this, NewWordActivity::class.java)
			startActivityForResult(intent, REQUEST_CODE)
		}
	}

	private fun subscribeObservers() {
		viewModel.allWords.observe(this) {
			it?.let { wordListAdapter.submitList(it) }
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let { reply ->
				val word = Word(reply)
				viewModel.insert(word)
			}
		} else {
			Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
		}
	}

	companion object {
		const val REQUEST_CODE = 1
	}
}