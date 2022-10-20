package com.wibisa.fruitcollector.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wibisa.fruitcollector.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseAuthenticationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base_authentication, container, false)
    }
}