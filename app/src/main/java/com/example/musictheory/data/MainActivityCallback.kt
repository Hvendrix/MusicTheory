package com.example.musictheory.data

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn

/**
 * @author Владислав Хвесюк 26.10.2021
 */

interface MainActivityCallback {
    fun hideBottomNavigationView()
    fun showBottomNavigationView()
}