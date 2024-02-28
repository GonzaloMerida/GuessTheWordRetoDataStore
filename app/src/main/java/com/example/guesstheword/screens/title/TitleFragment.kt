/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.guesstheword.screens.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.guesstheword.databinding.TitleFragmentBinding
import com.example.guesstheword.screens.settings.SettingsVM

/**
 * Fragment for the starting or title screen of the app
 */
class TitleFragment : Fragment() {

    private lateinit var binding : TitleFragmentBinding

    private val args : TitleFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = TitleFragmentBinding.inflate(inflater,container,false)

        binding.playGameButton.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment(
                args.difficultySelected,
                args.userNameLoaded
            ))
        }

        binding.bntInsertWord.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToInsertWordFragment())
        }

        binding.fabSettings.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToSettingsFragment())
        }

        return binding.root
    }
}
