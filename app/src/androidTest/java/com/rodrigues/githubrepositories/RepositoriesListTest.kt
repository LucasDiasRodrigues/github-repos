package com.rodrigues.githubrepositories

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.adevinta.android.barista.interaction.BaristaSwipeRefreshInteractions.refresh
import com.adevinta.android.barista.rule.BaristaRule
import com.rodrigues.githubrepositories.util.EspressoIdlingResource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoriesListTest {

    @Rule
    @JvmField
    val baristaRule: BaristaRule<MainActivity> =
        BaristaRule.create(MainActivity::class.java)

    @Before
    fun setUp() {
        baristaRule.launchActivity()

        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun itShouldLoadMoreItemsWhenListIsScrolledToTheBottom() {
        scrollListToPosition(R.id.repositoriesRecyclerView, 29)

        assertListItemCount(R.id.repositoriesRecyclerView, 60)

        scrollListToPosition(R.id.repositoriesRecyclerView, 59)

        assertListItemCount(R.id.repositoriesRecyclerView, 90)
    }

    @Test
    fun itShouldResetListWhenSwipeToRefreshIsTriggered() {
        scrollListToPosition(R.id.repositoriesRecyclerView, 29)

        assertListItemCount(R.id.repositoriesRecyclerView, 60)

        scrollListToPosition(R.id.repositoriesRecyclerView, 0)

        refresh()

        assertListItemCount(R.id.repositoriesRecyclerView, 30)
    }
}