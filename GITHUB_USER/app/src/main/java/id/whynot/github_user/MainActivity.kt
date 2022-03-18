package id.whynot.github_user


import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.whynot.github_user.adapter.PersonAdapter
import id.whynot.github_user.databinding.ActivityMainBinding
import id.whynot.github_user.model.person
import id.whynot.github_user.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private var listDataUser: ArrayList<person> = ArrayList()
    private lateinit var listAdapter: PersonAdapter
    private lateinit var mainViewModel: MainViewModel
    private var ListUserAdapter: ArrayList<person> = ArrayList()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "listDataUser: ${listDataUser.size}")
        listAdapter = PersonAdapter(listDataUser)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        viewConfig()
        runGetDataGit()
        configMainViewModel(listAdapter)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.find_username)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    listDataUser.clear()
                    viewConfig()
                    showLoading(true)
                    Handler(Looper.getMainLooper()).postDelayed({
                        listDataUser.clear()
                        viewConfig()
                        mainViewModel.getDataGitSearch(query, applicationContext)
                        configMainViewModel(listAdapter)
                    }, 1000L)

                } else {
                    return true
                }
                return true
            }


            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })



        return super.onCreateOptionsMenu(menu)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun viewConfig() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.setHasFixedSize(true)

        listAdapter.notifyDataSetChanged()
        Log.d(TAG, "viewConfig: ${listAdapter}")
        binding.rvUsers.adapter = listAdapter

    }


    private fun runGetDataGit() {
        mainViewModel.getDataGit(applicationContext)
        showLoading(true)
    }

    private fun configMainViewModel(adapter: PersonAdapter) {
        mainViewModel.getListUsers().observe(this, { listUsers ->
            if (listUsers != null) {

                listUsers.distinct()


                ListUserAdapter = (listUsers.distinctBy { it.name } as ArrayList<person>?)!!
                adapter.setData(listUsers.distinctBy { it.name } as ArrayList<person>)
                showLoading(false)
            }
        })
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            binding.loadingProgress.visibility = View.VISIBLE
        } else {
            binding.loadingProgress.visibility = View.INVISIBLE
        }
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName

    }
}