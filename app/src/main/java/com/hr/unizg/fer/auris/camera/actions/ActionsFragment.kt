package com.hr.unizg.fer.auris.camera.actions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.navigation.FragmentRouterImpl
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ActionsFragment : Fragment() {

    companion object {
        const val TAG = "ActionsFragment"
        fun newInstance(): Fragment {
            return ActionsFragment()
        }
    }

    private val router: FragmentRouterImpl by inject { parametersOf(childFragmentManager) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_actions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        router.showViewFinderFragment()
    }
}