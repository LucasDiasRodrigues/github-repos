package com.rodrigues.githubrepositories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodrigues.githubrepositories.ui.repositorieslist.RepoListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, RepoListFragment.newInstance())
                    .commitNow()
        }
    }
}