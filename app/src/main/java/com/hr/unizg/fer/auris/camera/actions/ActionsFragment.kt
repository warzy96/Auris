package com.hr.unizg.fer.auris.camera.actions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.navigation.FragmentRouterImpl
import com.hr.unizg.fer.auris.navigation.Router
import kotlinx.android.synthetic.main.fragment_actions.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
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

        router.showViewFinderFragment()

        shutterButton.setOnClickListener {
            actionsFragmentViewModel.dispatch(Router::showPreviewFragment)
        }
    }
}
