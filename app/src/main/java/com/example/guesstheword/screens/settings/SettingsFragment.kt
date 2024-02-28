package com.example.guesstheword.screens.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.guesstheword.R
import com.example.guesstheword.databinding.FragmentSettingsBinding
import com.example.guesstheword.datamodel.UserPreferences
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {

    private lateinit var binding : FragmentSettingsBinding

    private val settingsVM : SettingsVM by viewModels<SettingsVM> {  SettingsVM.Factory }

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
        binding = FragmentSettingsBinding.inflate(inflater,container,false)

        setListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCollectors()

    }

    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsVM.uiState.collect { userSettings ->
                    binding.etUserName.setText(userSettings.name)
                    when (userSettings.level) {
                        UserPreferences.LOW -> binding.rbLow.isChecked = true
                        UserPreferences.MEDIUM -> binding.rbMedium.isChecked = true
                        UserPreferences.HIGH -> binding.rbHard.isChecked = true
                    }
                }
            }
        }
    }

    private fun setListeners() {
        binding.btnBackSettings.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSaveSettings.setOnClickListener {
            validateName(binding.etUserName.text.toString())
        }
    }

    private fun validateName(name: String) {
        if(name.isBlank())
            Snackbar.make(requireView(),getString(R.string.word_is_empty),Snackbar.LENGTH_SHORT).show()
        else
            settingsVM.saveSettings(name,setLevelInt())
    }

    private fun setLevelInt(): Int {
        return when {
            binding.rbLow.isChecked -> UserPreferences.LOW
            binding.rbMedium.isChecked -> UserPreferences.MEDIUM
            else -> UserPreferences.HIGH
        }
    }
}