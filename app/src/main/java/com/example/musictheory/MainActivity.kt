package com.example.musictheory

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musictheory.core.data.MainActivityCallback
import com.example.musictheory.core.data.model.ServerResponse
import com.example.musictheory.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityCallback {

    private var _navView: BottomNavigationView? = null
    private val navView get() = _navView!!


    private val G_PLUS_SCOPE = "oauth2:https://www.googleapis.com/auth/plus.me"
    private val USERINFO_SCOPE = "https://www.googleapis.com/auth/userinfo.profile"
    private val EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email"
    private val SCOPES = "$G_PLUS_SCOPE $USERINFO_SCOPE $EMAIL_SCOPE"


    var RC_SIGN_IN = 123
    lateinit var mGoogleSignInClient : GoogleSignInClient
    /**
     * Вариант получения данных с сервера также можно использовать, как в TrainingTestViewModel
     *
     * @see com.example.musictheory.trainingtest.presentation.ui.viewmodel.TrainingTestViewModel
     */
//    @Inject
//    lateinit var dataStoreMusicEducation: DataStoreMusicEducation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())



        var binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        _navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nested_navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.action_global_nested_navigation_training_test
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)




        binding.signInButton.setOnClickListener {

            signIn();
//            val intent: Intent = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                AccountManager.newChooseAccountIntent(null, null,
//                    arrayOf(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE), null, null, null, null);
//            } else {
//                AccountManager.newChooseAccountIntent(null, null,
//                    arrayOf(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE), false, null, null, null, null);
//            }

            startActivityForResult(intent, 123)
        }
    }

    fun updateUI(account: GoogleSignInAccount?){
        if(account!=null){
            Toast.makeText(
                this,
                "not null",
                Toast.LENGTH_SHORT
            ).show()
        } else{
            Toast.makeText(
                this,
                "null",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken
            // Signed in successfully, show authenticated UI.
            updateUI(account)
//            Toast.makeText(this, account.idToken?.subSequence(0, 10)?:"null token", Toast.LENGTH_SHORT).show()
//            Toast.makeText(this, account?.email ?: "null email", Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "signInResult:failed code=" + e.statusCode, Toast.LENGTH_SHORT).show()
            updateUI(null)
        }
    }

    // Так тоже работало, хочу еще раз через этот способ попробовать
//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 123 && resultCode == RESULT_OK) {
//            val accountName = data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
//            Toast.makeText(this, "${accountName?:"null token"}", Toast.LENGTH_SHORT).show()
//            Log.e("tag", "${accountName?:"null token"}")
//            val getToken: AsyncTask<Void?, Void?, String?> =
//                object : AsyncTask<Void?, Void?, String?>() {
//                    override fun doInBackground(vararg params: Void?): String? {
//                        try {
//                            val token = GoogleAuthUtil.getToken(applicationContext, accountName,
//                            SCOPES)
//                            Toast.makeText(applicationContext, "${token.subSequence(0, 10)?:"null token"}}", Toast.LENGTH_SHORT).show()
//                            Log.e("tag", "${token.subSequence(0, 10)?:"null token"}")
//                            return token
//                        } catch (userAuthEx: UserRecoverableAuthException) {
//                            startActivityForResult(userAuthEx.intent, 123)
//                        } catch (ioEx: IOException) {
//                            Timber.v("t1 IOException")
//                        } catch (fatalAuthEx: GoogleAuthException) {
//                            Timber.v(
//                                "t1 Fatal Authorization Exception" + fatalAuthEx.localizedMessage
//                            )
//                        }
//                        val token = null
//                        return token
//                    }
//
//                    override fun onPostExecute(token: String?) {
//                        Timber.v("t1 token is $token")
////                        reg(token)
//                    }
//                }
//            getToken.execute(null, null, null)
//        }
//    }


    private suspend fun showDataFromServer(
        serverResponse: ServerResponse
    ) = withContext(Dispatchers.Main) {
        Toast.makeText(
            this@MainActivity,
            "${serverResponse.data.collection.size}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun hideBottomNavigationView() {
        navView.visibility = View.INVISIBLE
    }

    override fun showBottomNavigationView() {
        navView.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _navView = null
    }
}
