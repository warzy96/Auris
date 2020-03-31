package com.hr.unizg.fer.auris.camera.actions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.navigation.FragmentRouterImpl
import com.hr.unizg.fer.auris.utils.adjustViewSizeByAspectRatio
import com.hr.unizg.fer.auris.utils.aspectRatio
import kotlinx.android.synthetic.main.fragment_actions.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ActionsFragment : Fragment() {

    companion object {
        const val TAG = "ActionsFragment"
        fun newInstance(): Fragment {
            return ActionsFragment()
        }
    }

    private val router: FragmentRouterImpl by inject { parametersOf(childFragmentManager) }
    private val actionsFragmentViewModel: ActionsFragmentViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_actions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(fragment_actions_container_view) {
            post {
                val aspectRatio = aspectRatio(width, height)
                adjustViewSizeByAspectRatio(this, aspectRatio)
            }
        }

        router.showViewFinderFragment()
    }
}