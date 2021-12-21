package com.example.musictheory.account.presenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.musictheory.R
import com.example.musictheory.account.loginScreen.PersonalAccountFragments
import com.example.musictheory.account.presenter.viewmodels.PersonalAccountViewModel
import com.example.musictheory.databinding.FragmentStudentPersonalAccountLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudentPersonalAccountLoginFragment : Fragment() {
    private val personalAccountViewModel: PersonalAccountViewModel
    by hiltNavGraphViewModels<PersonalAccountViewModel>(R.id.nested_personal_account)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentStudentPersonalAccountLoginBinding.inflate(inflater)
        initNestedFragments()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {

                personalAccountViewModel.goRegister.collect {
                    when (it) {
                        PersonalAccountFragments.REGISTRATION -> {
                            val studentRegistrationFragment = StudentRegistrationFragment()
                            childFragmentManager.beginTransaction().apply {
                                disallowAddToBackStack()
                                replace(R.id.login_body, studentRegistrationFragment)
                                commit()
                            }
                        }
                        PersonalAccountFragments.LOGIN -> {
                            val studentRegistrationFragment = StudentLoginFragment()
                            childFragmentManager.beginTransaction().apply {
                                disallowAddToBackStack()
                                replace(R.id.login_body, studentRegistrationFragment)
                                commit()
                            }
                        }
                        PersonalAccountFragments.ACCOUNT -> {
                            val accountFragment = AccountFragment()
                            childFragmentManager.beginTransaction().apply {
                                disallowAddToBackStack()
                                replace(R.id.login_body, accountFragment)
                                commit()
                            }
                        }
                        PersonalAccountFragments.ADDTEST -> {
                            val addTestFragment = AddTestFragment()
                            childFragmentManager.beginTransaction().apply {
                                replace(R.id.login_body, addTestFragment)
                                commit()
                            }
                        }
                    }
                }
            }
        }
        return binding.root
//        return inflater.inflate(R.layout.fragment_student_personal_account_login, container, false)
    }
    private fun initNestedFragments() {
        val studentLoginFragment = StudentLoginFragment()
        childFragmentManager.beginTransaction().apply {
            disallowAddToBackStack()
            add(R.id.login_body, studentLoginFragment)
            commit()
        }
    }
}
