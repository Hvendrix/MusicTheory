package com.example.musictheory.account.presenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.musictheory.R
import com.example.musictheory.account.data.model.ResponseLogin
import com.example.musictheory.account.loginScreen.PersonalAccountFragments
import com.example.musictheory.account.presenter.viewmodels.PersonalAccountViewModel
import com.example.musictheory.core.data.MainActivityCallback
import com.example.musictheory.core.presenter.ThemeManager
import com.example.musictheory.databinding.AccountFragmentBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private val nightMode = "NIGHT_MODE"

    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val personalAccountViewModel: PersonalAccountViewModel
    by hiltNavGraphViewModels<PersonalAccountViewModel>(R.id.nested_personal_account)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = AccountFragmentBinding.inflate(inflater)

//        val gso = GoogleSignInOptions
//            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.web_client_id))
//            .requestEmail()
//            .build()
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this.activity, gso)

        binding.textViewAccountName.text = personalAccountViewModel.user.value?.login
        when (personalAccountViewModel.user.value?.role) {
            null -> {
                binding.buttonAddTestAccount.visibility = View.GONE
            }
            "teacher", "admin" -> {
                binding.buttonAddTestAccount.visibility = View.VISIBLE
            }
            else -> {
                binding.buttonAddTestAccount.visibility = View.GONE
            }
        }

        binding.buttonChangeThemeAccount.setOnClickListener {
            val sharedName = "SharedPref"
            val settings = this.parentFragment?.activity?.getSharedPreferences(sharedName, 0)
            val editor = settings?.edit()
            when (settings?.getString(nightMode, "none")) {
                "light" -> {
                    toggleTheme(false)
                    editor?.putString(nightMode, "dark")
                    editor?.apply()
                }
                "dark" -> {
                    toggleTheme(true)
                    editor?.putString(nightMode, "light")
                    editor?.apply()
                }
            }
        }

        binding.buttonAddTestAccount.setOnClickListener {
            personalAccountViewModel.setOid("")
            personalAccountViewModel.setRegister(PersonalAccountFragments.ADD_TEST)

//            if (activity is MainActivityCallback) {
//                (activity as MainActivityCallback).goAddTestFragment()
//            }
        }

        binding.buttonExitAccount.setOnClickListener {
            signOut()
        }

        return binding.root
    }

    private fun signOut() {
        personalAccountViewModel.setUser(null)

        if (activity is MainActivityCallback) {
            (activity as MainActivityCallback).setToken("")
            (activity as MainActivityCallback).setUser(null)
        }
        personalAccountViewModel.setRegister(PersonalAccountFragments.LOGIN)
        try {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                personalAccountViewModel.setUser(ResponseLogin("", "", "", "", ""))
                personalAccountViewModel.setRegister(PersonalAccountFragments.LOGIN)
//                personalAccountViewModel.setEmail(ResponseLogin("","","","",""))
//                personalAccountViewModel.setRegister(PersonalAccountFragments.NONE)
            }
        } catch (e: Exception) {
        }
//        personalAccountViewModel.setEmail(ResponseLogin("","","","",""))
//        personalAccountViewModel.setRegister(PersonalAccountFragments.LOGIN)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        if (activity is MainActivityCallback) {
//            (activity as MainActivityCallback).hideBottomNavigationView()
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        if (activity is MainActivityCallback) {
//            (activity as MainActivityCallback).showBottomNavigationView()
//        }
    }

    private fun toggleTheme(isDark: Boolean): Boolean {

        val mode = when (isDark) {
            true -> ThemeManager.LIGHT_MODE
            false -> ThemeManager.DARK_MODE
        }
        ThemeManager.applyTheme(mode)

        return true
    }
}
