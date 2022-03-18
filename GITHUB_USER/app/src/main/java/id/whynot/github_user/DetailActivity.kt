package id.whynot.github_user


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.view.Menu

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.whynot.github_user.adapter.DetailAdapter
import id.whynot.github_user.databinding.ActivityDetailBinding
import id.whynot.github_user.model.person

class DetailActivity : AppCompatActivity() {

    private lateinit var nameUser: String
    private lateinit var content: String
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = "Detail User"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setData()
        viewPagerConfig()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        if (menu != null) {
            val item = menu.findItem(R.id.search)
            item.isVisible = false
        }

        return super.onCreateOptionsMenu(menu)
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    private fun viewPagerConfig() {
        val viewPagerDetail = DetailAdapter(this)
        binding.viewPager.adapter = viewPagerDetail
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }


    private fun setData() {
        showLoading(true)

        Handler(Looper.getMainLooper()).postDelayed({
            showLoading(false)
            val user = intent.getParcelableExtra<person>(EXTRA_PERSON) as person
            val image = user.avatar
            nameUser = user.username.toString()
            content = "name : ${user.name.toString()}\n" +
                    "company : ${user.company.toString()}\n" +
                    "location : ${user.location.toString()}\n" +
                    "repository :  ${user.repository.toString()}\n"
            Glide.with(this).load(image).into(binding.ivAvatarReceived)
            binding.tvNameReceived.text = nameUser
            binding.tvObjectReceived.text = content
        }, 1000L)

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.loadingProgressUserDetail.visibility = View.VISIBLE
        } else {
            binding.loadingProgressUserDetail.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val EXTRA_PERSON = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}