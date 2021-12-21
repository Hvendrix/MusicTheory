package com.example.musictheory.account.presenter.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.musictheory.R
import com.example.musictheory.account.loginScreen.PersonalAccountFragments
import com.example.musictheory.account.presenter.viewmodels.PersonalAccountViewModel
import com.example.musictheory.databinding.FragmentStudentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudentLoginFragment : Fragment() {
    private var _binding: FragmentStudentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText

    private lateinit var enterButton: Button
    private lateinit var registerButton: Button

    private val personalAccountViewModel: PersonalAccountViewModel
    by hiltNavGraphViewModels<PersonalAccountViewModel>(R.id.nested_personal_account)

    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn
                .getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null && account.idToken != null) {
            postLoginToServer(account.idToken, "")
        }
//        updateUI(account)

        mGoogleSignInClient = GoogleSignIn.getClient(this.activity, gso)

        loginEditText = binding.loginEt
        passwordEditText = binding.passwordEt
        enterButton = binding.enterButton
        registerButton = binding.registerButton

        enterButton.setOnClickListener {
            postLoginToServer(
                binding.loginEt.text.toString().trim(),
                binding.passwordEt.text.toString()
            )
        }

        registerButton.setOnClickListener {
            personalAccountViewModel.setRegister(PersonalAccountFragments.REGISTRATION)
        }

        binding.signInButton.setOnClickListener {
            signIn()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    personalAccountViewModel.email.collect {
                        if (it != null && it.name.isNotEmpty() && it.role.isNotEmpty()) {
                            personalAccountViewModel.setRegister(PersonalAccountFragments.ACCOUNT)
//                            if (activity is MainActivityCallback) {
//                                (activity as MainActivityCallback).goAccount(it.name, it.role)
//                            }
                        }
                    }
                }
                launch {
                    personalAccountViewModel.serverResponse.collect {
                        when {
                            it == null -> {
//                                personalAccountViewModel.setRegister(PersonalAccountFragments.REGISTRATION)
                            }
                            it.name.isNotEmpty() -> {
                                personalAccountViewModel.setEmail(it)
                            }
                            else -> {
                                Toast.makeText(context, "${it.result}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        return view
    }

    private fun postLoginToServer(token: String, pass: String) {
        lifecycleScope.launch {
            val responseLogin = async {
                personalAccountViewModel.postLogin(token, pass)
            }
            val responseLoginAwait = responseLogin.await().body()
            when {
                responseLoginAwait == null -> {
                    personalAccountViewModel.setRegister(PersonalAccountFragments.REGISTRATION)
                }
                responseLoginAwait.name.isNotEmpty() -> {
                    personalAccountViewModel.setEmail(responseLoginAwait)
                }
                else -> {
                    Toast.makeText(context, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
//        if(account == null){
//            Toast.makeText(context, "account is null", Toast.LENGTH_SHORT).show()
//            return
//        }
//        personalAccountViewModel.setRegister(PersonalAccountFragments.REGISTRATION)
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startForResult.launch(signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null && account.idToken != null) {
                postLoginToServer(
                    account.idToken,
                    ""
                )
            }

            updateUI(account)
        } catch (e: ApiException) {
//            Toast.makeText(context,
//                "signInResult:failed code=" + e.statusCode, Toast.LENGTH_SHORT).show()
            updateUI(null)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
