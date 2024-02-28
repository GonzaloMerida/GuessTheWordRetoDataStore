package com.example.guesstheword.screens.word

import android.app.Service
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.guesstheword.R
import com.example.guesstheword.databinding.FragmentInsertWordBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class InsertWordFragment : Fragment() {

    private lateinit var binding : FragmentInsertWordBinding

    private val wordVM : InsertWordVM by viewModels<InsertWordVM> { InsertWordVM.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInsertWordBinding.inflate(inflater,container,false)

        setListeners()

        return binding.root
    }

    private fun setListeners() {
        binding.btnInsert.setOnClickListener {
            if(binding.etWord.text.isNotEmpty()) {
                wordVM.insertWord(binding.etWord.text.toString().lowercase())
            } else {
                Snackbar.make(requireView(),getString(R.string.word_is_empty),Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(InsertWordFragmentDirections.actionInsertWordFragmentToTitleFragment())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCollectors()
    }

    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                wordVM.insertWordUiState.collect { insertWordState ->
                    if(insertWordState.insertedWord)
                        Snackbar.make(requireView(),getString(R.string.inserted_word,insertWordState.title),Snackbar.LENGTH_SHORT).show()
                    if(insertWordState.existWord)
                        Snackbar.make(requireView(),getString(R.string.allready_exist_word,insertWordState.title),Snackbar.LENGTH_SHORT).show()
                    if(insertWordState.insertedWord || insertWordState.existWord) {
                        hideKeyboard()
                        binding.etWord.setText("")
                        wordVM.messageShown()
                    }
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}