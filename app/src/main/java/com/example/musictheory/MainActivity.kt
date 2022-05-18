package com.example.musictheory

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musictheory.core.data.MainActivityCallback
import com.example.musictheory.core.data.model.ServerResponse
import com.example.musictheory.core.presenter.ThemeManager
import com.example.musictheory.core.presenter.ThemeManager.DARK_MODE
import com.example.musictheory.core.presenter.ThemeManager.LIGHT_MODE
import com.example.musictheory.databinding.ActivityMainBinding
import com.example.musictheory.trainingtest.data.model.MusicTest
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityCallback {

    private val nightMode = "NIGHT_MODE"
    private val argIdResult = "id_result"

    private var _navView: BottomNavigationView? = null
    private val navView get() = _navView!!

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    /**
     * Вариант получения данных с сервера также можно использовать, как в TrainingTestViewModel
     *
     * @see com.example.musictheory.trainingtest.presentation.ui.viewmodel.TrainingTestViewModel
     */
//    @Inject
//    lateinit var dataStoreMusicEducation: DataStoreMusicEducation

    private val viewModel: MainActivityViewModel
            by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        val sharedName = "SharedPref"
        val settings = getSharedPreferences(sharedName, 0)
        val editor = settings.edit()
        when (settings.getString(nightMode, "none")) {
            "none" -> {
                editor.putString(nightMode, "light")
                editor.apply()
            }
            "light" -> toggleTheme(true)
            "dark" -> toggleTheme(false)
        }

        var binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        _navView = findViewById(R.id.nav_view)
        _navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nested_navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.action_global_nested_personal_account
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

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

    override fun goTestFragment(oid: String) {
        val bundle = Bundle()
        bundle.putString("categoryNumber", oid)
        navController.navigate(R.id.action_global_nested_navigation_training_test, bundle)
    }

    override fun goResultFragment(id: Long) {
        val bundle = Bundle()
        bundle.putLong(argIdResult, id)
        navController.navigate(
            R.id.action_navigation_nested_navigation_training_test_to_resultFragment,
            bundle
        )
    }

    override fun goAddTestFragment() {
        navController.navigate(R.id.action_accountFragment_to_addTestFragment)
    }

    override fun goAddTestFragment(id: String) {
        val bundle = Bundle()
        bundle.putString(Companion.testId, id)
        navController.navigate(R.id.action_global_nested_personal_account, bundle)

//        navController.navigate(R.id.action_accountFragment_to_addTestFragment)
    }

    private fun toggleTheme(isDark: Boolean): Boolean {
        val mode = when (isDark) {
            true -> LIGHT_MODE
            false -> DARK_MODE
        }
        ThemeManager.applyTheme(mode)
        return true
    }

    override fun goAccount(email: String, role: String) {
//        val bundle = Bundle()
//        val bundle = bundleOf(email to "name", role to "role")
//        bundle.putString("name", email)
//        bundle.putString("role", role)
//        Timber.v("t1 email " + email)
//        navController.navigate(R.id.action_studentPersonalAccountLoginFragment_to_accountFragment, bundle)
//        navController.navigate(
//            R.id.action_studentPersonalAccountLoginFragment_to_accountFragment
// //            bundle
//        )
        navController.navigate(R.id.accountFragment)
    }

    override fun setToken(token: String) {
        viewModel.setToken(token)
    }

    override fun getToken(): String {
        return viewModel.token.value
    }

    override fun onDestroy() {
        super.onDestroy()
        _navView = null
    }

    companion object {
        const val testId = "TEST_ID"
    }
}
