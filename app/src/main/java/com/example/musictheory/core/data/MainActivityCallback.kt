package com.example.musictheory.core.data

/**
 * @author Владислав Хвесюк 26.10.2021
 */

interface MainActivityCallback {
    fun hideBottomNavigationView()
    fun showBottomNavigationView()
    fun goTestFragment(position: String)
    fun goResultFragment(id: Long)
    fun goAddTestFragment()
    fun goAccount(email: String, role: String)
    fun setToken(token: String)
    fun getToken(): String
}
