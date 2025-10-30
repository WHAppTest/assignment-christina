package at.willhaben.applicants_test_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import at.willhaben.applicants_test_project.fragments.DetailsFragment
import at.willhaben.applicants_test_project.fragments.ListFragment

class MainActivity : AppCompatActivity(R.layout.main_layout), OpenDetailsScreenCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container, ListFragment.newInstance())
            }
        }
    }

    override fun openScreen(title: String) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, DetailsFragment.newInstance(title))
            addToBackStack("")
        }
    }
}

interface OpenDetailsScreenCallback {
    fun openScreen(title: String)
}