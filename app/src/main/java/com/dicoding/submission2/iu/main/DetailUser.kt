package com.dicoding.submission2.iu.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.submission2.*
import com.dicoding.submission2.adapter.SectionPager
import com.dicoding.submission2.viewmodel.DetailViewModel
import com.dicoding.submission2.databinding.ActivityDetailUserBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailViewModel :DetailViewModel
    private var isFavorite : Boolean = false
    private var user: UserResponseItem? = null
    private lateinit var detail: UserDetail


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = obtainViewModel(this@DetailUser)

        val username = intent.getStringExtra(EXTRA_USER)

        detailViewModel.detailUser.observe(this,{ v->

            if(v.name== null)binding.tvNameDetail.setText(R.string.Name)
             else binding.tvNameDetail.text = v.name

            binding.tvUsernameDetail.text = v.login

            if(v.company== null){binding.tvCompanyDetail.setText(R.string.Company)}
             else binding.tvCompanyDetail.text = v.company

            if(v.location == null){binding.tvLocationDetail.setText(R.string.Location)}
             else binding.tvLocationDetail.text = v.location

            if(v.repository == null){binding.tvRepositoryDetail.setText(R.string.Repository)}
             else binding.tvRepositoryDetail.text = v.repository.toString()

            Glide.with(this@DetailUser)
                .load(v.avatarUrl)
                .into(binding.ivPhoto)
            detail = v
        })

        username?.let { detailViewModel.showDetailUser(it) }

        val sectionsPagerAdapter = username?.let { SectionPager(it, this) }
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tl_follow_following)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        addBackButton()

        detailViewModel.isLoading.observe(this,{
            showLoading(it)
        })

        detailViewModel.getAllFavorite().observe(this,{fav->
            fav.forEach(){
                if(it.login == username){
                    isFavorite = true
                    showFavorited()
                }
            }

        })
        binding.fabAdd.setOnClickListener {
            if(isFavorite){
                val mySnackBar = Snackbar.make(it, "Removed from favorite.", Snackbar.LENGTH_LONG)
                mySnackBar.setAction(getString(R.string.Show), FovoriteIntent(this))
                mySnackBar.show()
                detailViewModel.delete(detail.id)
                isFavorite = false
                showFavorited()
            }else if (!isFavorite){
                val mySnackBar = Snackbar.make(it, "Added to favorite.", Snackbar.LENGTH_LONG)
                mySnackBar.setAction(getString(R.string.Show), FovoriteIntent(this))
                mySnackBar.show()

                user = UserResponseItem(
                    id = detail.id,
                    login = detail.login,
                    avatarUrl = detail.avatarUrl
                )
                detailViewModel.insert(user)
                isFavorite = true
                showFavorited()
            }
        }
    }

    private fun showFavorited() {
        if(isFavorite){
            binding.fabAdd.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        else{
            binding.fabAdd.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }


    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.share_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share ->{
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        StringBuilder().apply {
                            append("Say hello to " + binding.tvUsernameDetail.text+",")
                            append("\n")
                            append("Now, you can see "+binding.tvUsernameDetail.text+" in GitHub.")
                            append("\n")
                            append("Directly Link in bellow: ")
                            append("\n")
                            append("https://github.com/"+binding.tvUsernameDetail.text)
                        }.toString()
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                return true
            }
            else -> return false
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.spinKit.visibility = View.VISIBLE
        }
        else{
            binding.spinKit.visibility = View.GONE
        }
    }

    private fun addBackButton() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    class FovoriteIntent(private val context:Context): View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = Intent(context, FavoriteActivity::class.java)
            context.startActivity(intent)
        }

    }

    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
}


