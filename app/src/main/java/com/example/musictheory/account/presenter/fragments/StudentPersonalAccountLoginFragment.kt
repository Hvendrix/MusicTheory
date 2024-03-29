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
import com.example.musictheory.MainActivity
import com.example.musictheory.R
import com.example.musictheory.account.loginScreen.PersonalAccountFragments
import com.example.musictheory.account.presenter.viewmodels.PersonalAccountViewModel
import com.example.musictheory.core.data.MainActivityCallback
import com.example.musictheory.databinding.FragmentStudentPersonalAccountLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class StudentPersonalAccountLoginFragment : Fragment() {
    private val personalAccountViewModel: PersonalAccountViewModel
            by hiltNavGraphViewModels<PersonalAccountViewModel>(R.id.nested_personal_account)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentStudentPersonalAccountLoginBinding.inflate(inflater)
        initNestedFragments()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
//                launch {
//                    personalAccountViewModel.token.collect {
//                        when {
//                            it.isNullOrEmpty() -> personalAccountViewModel.setRegister(
//                                PersonalAccountFragments.LOGIN)
//                            else -> personalAccountViewModel.setRegister(PersonalAccountFragments.ACCOUNT)
//                        }
//                    }
//                }

                launch {
                    personalAccountViewModel.goRegister.collect {
                        Timber.i("t1 $it")
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
                            PersonalAccountFragments.ADD_TEST -> {
                                val addTestFragment = AddTestFragment()
                                childFragmentManager.beginTransaction().apply {
                                    replace(R.id.login_body, addTestFragment)
                                    addToBackStack(null)
                                    commit()
                                }
                            }
                            PersonalAccountFragments.ADD_QUESTION -> {
                                val addQuestionFragment = AddQuestionFragment()
                                childFragmentManager.beginTransaction().apply {
                                    replace(R.id.login_body, addQuestionFragment)
                                    addToBackStack(null)
                                    commit()
                                }
                            }
                        }
                    }
                }
            }
        }
        val oid = arguments?.getString(MainActivity.testId)
        personalAccountViewModel.setOid(oid.toString())
        if(!oid.isNullOrBlank()){
            personalAccountViewModel.setRegister(PersonalAccountFragments.ADD_TEST)
        }
        return binding.root
//        return inflater.inflate(R.layout.fragment_student_personal_account_login, container, false)
    }

    private fun initNestedFragments() {
        if (activity is MainActivityCallback) {
            val token = (activity as MainActivityCallback).getToken()
            if(token.isNotEmpty()){
                if((personalAccountViewModel.user.value == null) || (personalAccountViewModel.user.value!!.login.isNullOrBlank())){
                    if (activity is MainActivityCallback) {
                        lifecycleScope.launch {
                            personalAccountViewModel.loginIn((activity as MainActivityCallback).getToken())
                        }
                    }
                }
                personalAccountViewModel.setRegister(PersonalAccountFragments.ACCOUNT)
            } else {
                personalAccountViewModel.setRegister(PersonalAccountFragments.LOGIN)
            }
        }


//        val studentLoginFragment = StudentLoginFragment()
//        childFragmentManager.beginTransaction().apply {
//            disallowAddToBackStack()
//            add(R.id.login_body, studentLoginFragment)
//            commit()
//        }
    }
}
