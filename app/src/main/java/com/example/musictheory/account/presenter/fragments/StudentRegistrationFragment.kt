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
import com.example.musictheory.core.data.MainActivityCallback
import com.example.musictheory.databinding.FragmentStudentRegistrationBinding
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
import timber.log.Timber
import java.lang.Exception

@AndroidEntryPoint
class StudentRegistrationFragment : Fragment() {

    private val personalAccountViewModel: PersonalAccountViewModel
    by hiltNavGraphViewModels<PersonalAccountViewModel>(R.id.nested_personal_account)

    private var _binding: FragmentStudentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var mailEditText: EditText

    private lateinit var registerButton: Button

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
        _binding = FragmentStudentRegistrationBinding.inflate(inflater, container, false)
        val view = binding.root

        loginEditText = binding.loginEt
        passwordEditText = binding.passwordEt
        registerButton = binding.registerButton

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this.activity, gso)

        val account = GoogleSignIn.getLastSignedInAccount(context)

        if (account != null && account.idToken != null && account.email != null) {
//            postSignUpToServer(
//                account.idToken,
//                account.email,
//                binding.checkboxTeacher.isChecked,
//                ""
//            )
        }
//        updateUI(account)

        binding.signInButton.setOnClickListener {
            signIn()
        }

        binding.loginButton.setOnClickListener {
            personalAccountViewModel.setRegister(PersonalAccountFragments.LOGIN)
        }

        binding.registerButton.setOnClickListener {
//            postSignUpToServer(
//                binding.loginEt.text.toString().trim(),
//                binding.loginEt.text.toString().trim(),
//                binding.checkboxTeacher.isChecked,
//                binding.passwordEt.text.toString()
//            )
            postSignUpFlaskToServer(
                binding.loginEt.text.toString().trim(),
                binding.checkboxTeacher.isChecked,
                binding.passwordEt.text.toString()
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    personalAccountViewModel.user.collect {
                        if (it != null && it.login.isNotEmpty() && it.role.isNotEmpty()) {
                            personalAccountViewModel.setRegister(PersonalAccountFragments.ACCOUNT)
                            if (activity is MainActivityCallback) {
                                (activity as MainActivityCallback).setUser(it)
                            }
                        }
//                        binding.loginEt.setText(it)
//                        try {
//                            Toast.makeText(context, "token server ${it}", Toast.LENGTH_SHORT).show()
//                        } catch (e: Exception){
//                            Toast.makeText(context, "not rignt,", Toast.LENGTH_SHORT).show()
//
//                        }
                    }
                }
            }
        }
        return view
    }

    private fun postSignUpToServer(token: String, name: String, teacher: Boolean, pass: String) {
        Timber.v("t1 teacher " + teacher)
        lifecycleScope.launch {
            val signUpResponse = async {
                personalAccountViewModel.postSignUp(token, name, teacher, pass)
            }
//            x.await().body()?.let { it1 -> personalAccountViewModel.setEmail(it1) }
            val signUpResponseAwait = signUpResponse.await().body()
            when {
                signUpResponseAwait == null -> {
                    Toast.makeText(
                        context,
                        "Пользователь уже существует",
                        Toast.LENGTH_SHORT
                    ).show()
                    personalAccountViewModel.setRegister(PersonalAccountFragments.REGISTRATION)
                }
                !signUpResponseAwait.name.isNullOrEmpty() -> {
                    personalAccountViewModel.setUser(signUpResponseAwait)
                }
                else -> {
                    Toast.makeText(
                        context,
                        "${signUpResponseAwait.result}",
                        Toast.LENGTH_SHORT
                    ).show()

                    val responseLogin = async {
                        personalAccountViewModel.postLogin(token, pass)
                    }
                    val responseLoginAwait = responseLogin.await().body()
                    when {
                        responseLoginAwait == null -> {
                            personalAccountViewModel.setRegister(
                                PersonalAccountFragments.REGISTRATION
                            )
                        }
                        responseLoginAwait.name.isNotEmpty() -> {
                            personalAccountViewModel
                                .setUser(responseLoginAwait)
                        }
                        else -> {
                            Toast.makeText(
                                context,
                                "Что-то пошло не так",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }


    private fun postSignUpFlaskToServer(email: String, teacher: Boolean, pass: String) {
        lifecycleScope.launch {
            val signUpResponse = async {
                personalAccountViewModel.postSignUpFlask(email, teacher, pass)
            }
//            x.await().body()?.let { it1 -> personalAccountViewModel.setEmail(it1) }
            val signUpResponseAwait = signUpResponse.await().body()
            if (activity is MainActivityCallback) {
                (activity as MainActivityCallback).setToken(signUpResponseAwait ?.token?: "")
                val responseUser = async {
//                if(signUpResponseAwait!=null)
//                personalAccountViewModel.getUserFlask("Bearer " + signUpResponseAwait?.token ?: "")
                    personalAccountViewModel.getUserFlask( (activity as MainActivityCallback).getToken())
                }
                val responseUserAwait = responseUser.await().body()?.data
                when {
                    responseUserAwait == null -> {
                        Toast.makeText(
                            context,
                            "Пользователь уже существует",
                            Toast.LENGTH_SHORT
                        ).show()
                        personalAccountViewModel.setRegister(PersonalAccountFragments.REGISTRATION)
                    }
                    !responseUserAwait.login.isNullOrEmpty() -> {
                        personalAccountViewModel.setUser(responseUserAwait)
                    }
                    else -> {
                        Toast.makeText(
                            context,
                            "${signUpResponseAwait?.result}",
                            Toast.LENGTH_SHORT
                        ).show()

//                    val responseLogin = async {
//                        personalAccountViewModel.postLogin(token, pass)
//                    }
//                    val responseLoginAwait = responseLogin.await().body()
//                    when {
//                        responseLoginAwait == null -> {
//                            personalAccountViewModel.setRegister(
//                                PersonalAccountFragments.REGISTRATION
//                            )
//                        }
//                        responseLoginAwait.name.isNotEmpty() -> {
//                            personalAccountViewModel
//                                .setEmail(responseLoginAwait)
//                        }
//                        else -> {
//                            Toast.makeText(
//                                context,
//                                "Что-то пошло не так",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
                    }
                }
            }


        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account == null) {
//            Toast.makeText(context, "account is null", Toast.LENGTH_SHORT).show()
            return
        }
//        personalAccountViewModel.setRegister(PersonalAccountFragments.REGISTRATION)
    }

    private fun signIn() {
        try {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startForResult.launch(signInIntent)
        } catch (e: Exception){
            Timber.i(e)
        }

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (account != null && account.idToken != null && account.email != null) {
//                postSignUpToServer(
//                    account.idToken,
//                    account.email,
//                    binding.checkboxTeacher.isChecked,
//                    ""
//                )
            }

            // Signed in successfully, show authenticated UI.
//            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
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
