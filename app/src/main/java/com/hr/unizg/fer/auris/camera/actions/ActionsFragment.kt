package com.hr.unizg.fer.auris.camera.actions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.camera.capturechannel.CameraActions
import com.hr.unizg.fer.auris.camera.capturechannel.PreviewActionsChannel
import com.hr.unizg.fer.auris.di.ACTIONS_FRAGMENT_SCOPE_ID
import com.hr.unizg.fer.auris.navigation.FragmentRouterImpl
import kotlinx.android.synthetic.main.fragment_actions.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

@FlowPreview
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

    private val previewActionsChannel: PreviewActionsChannel by getKoin()
        .getOrCreateScope(ACTIONS_FRAGMENT_SCOPE_ID, named<ActionsFragment>())
        .inject()

//    private val actionsFragmentViewModel: ActionsFragmentViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_actions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        router.showViewFinderFragment()

        shutterButton.setOnClickListener {
            lifecycleScope.launch { previewActionsChannel.sendAction(CameraActions::capture) }
        }
    }
}
