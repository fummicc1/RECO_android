package dev.fummicc1.sample.taskcalendar.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dev.fummicc1.sample.taskcalendar.R
import dev.fummicc1.sample.taskcalendar.ui.viewmodels.TaskListViewModel

class TaskListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = ViewModelProvider(this).get(TaskListViewModel::class.java)
        viewModel.isLoggedIn.observe(viewLifecycleOwner, Observer {
            if (!it) {
                findNavController().navigate(TaskListFragmentDirections.actionTaskListFragmentToSignInFragment())
            }
        })
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }
}