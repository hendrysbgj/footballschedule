package com.omrobbie.footballapps.view.teamsDetail

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.adapter.ViewPagerAdapter
import com.omrobbie.footballapps.model.TeamsItem

import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_teams_detail.*

import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity

class TeamsDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun start(context: Context?, team: TeamsItem) {
            context?.startActivity<TeamsDetailActivity>(EXTRA_PARAM to team)
        }
    }

    private lateinit var team: TeamsItem

    private var menuFavorites: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams_detail)

        setupEnv()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupEnv() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadData()

        view_pager.adapter = ViewPagerAdapter(supportFragmentManager,
                mapOf(
                        getString(R.string.title_overview) to TeamsOverviewFragment.newInstance(team.strDescriptionEN.toString()),
                        getString(R.string.title_players) to TeamsPlayersFragment.newInstance(team.strTeam.toString())
                )
        )
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun loadData() {
        team = intent.getParcelableExtra(EXTRA_PARAM)

        Picasso.get()
                .load(team.strTeamBadge)
                .into(iv_team)

        tv_name.text = team.strTeam
        tv_year.text = team.intFormedYear
        tv_stadium.text = team.strStadium
    }
}
