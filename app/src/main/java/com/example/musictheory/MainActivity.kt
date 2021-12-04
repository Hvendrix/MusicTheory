package com.example.musictheory

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityCallback {

    private var _navView: BottomNavigationView? = null
    private val navView get() = _navView!!

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
        setContentView(R.layout.activity_main)
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

        // Также можно использовать способ, как в TrainingTestViewModel
//        lifecycleScope.launch {
//            val sections = async { dataStoreMusicEducation.getTests() }
//            showDataFromServer(sections.await())
//        }
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

    override fun onDestroy() {
        super.onDestroy()
        _navView = null
    }
}
