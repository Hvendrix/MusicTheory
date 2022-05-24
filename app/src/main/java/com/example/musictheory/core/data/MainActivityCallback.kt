package com.example.musictheory.core.data

import com.example.musictheory.account.data.model.UserFlask
import com.example.musictheory.trainingtest.data.model.MusicTest

/**
 * @author Владислав Хвесюк 26.10.2021
 */

interface MainActivityCallback {
    fun hideBottomNavigationView()
    fun showBottomNavigationView()
    fun goTestFragment(position: String)
    fun goResultFragment(id: Long)
    fun goAddTestFragment()
    fun goAddTestFragment(id: String)
    fun goAccount(email: String, role: String)
    fun setToken(token: String)
    fun getToken(): String
    fun setUser(user: UserFlask?)
    fun getUser(): UserFlask?
}
