package com.example.ewalle.presentation.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.ewalle.App
import com.example.ewalle.R
import com.example.ewalle.data.net.NetReqState
import com.example.ewalle.data.net.dpToPx
import com.example.ewalle.databinding.ActivityMainBinding
import com.example.ewalle.presentation.factory.DaggerViewModelFactory
import com.example.ewalle.presentation.home.HomeFragment
import com.example.ewalle.presentation.login.LoginFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), LoginFragmentInteractor {

    companion object {
        private const val DURATION: Long = 500
        private const val ROTATION = -15F
        private const val SCALE = 0.7F
        private const val TRANSLATION = 180
    }

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    private lateinit var viewModel: MenuViewModel

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val animatorSet = AnimatorSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initDagger()
        initViewModule()
        setListeners()
        setObservers()
        initDrawerLayout()
        initNavigation()
        initObjectAnimator()
        initToggle()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, LoginFragment()).commit()
        }
    }

    private fun setListeners() {
        binding.menuLogout.setOnClickListener {
            binding.toggleButton.toggle()
            viewModel.deleteData()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment()).commit()
        }
    }

    private fun initDagger() {
        (this.application as App).getAppComponent()
            .registerMenuComponent()
            .create()
            .inject(this)
    }

    private fun initViewModule() {
        viewModel = ViewModelProvider(this, viewModelFactory)[MenuViewModel::class.java]
    }

    private fun setObservers() {
        viewModel.user.observe(this) { user ->
            val header = binding.navigationViewMenu.getHeaderView(0)
            val imageUser = header.findViewById<ImageView>(R.id.menuImage)
            val townUser = header.findViewById<TextView>(R.id.menuTown)
            val nameUser = header.findViewById<TextView>(R.id.menuUserName)
            nameUser.text = user.name
            townUser.text = user.town
            Glide.with(this).load(user.urlImage).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .apply(RequestOptions.circleCropTransform())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageUser.setImageResource(R.drawable.image_user)
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                }).into(imageUser)
        }

        viewModel.toggleVisibility.observe(this) { it ->
            binding.toggleButton.isVisible = it
        }

        viewModel.networkState.observe(this) {
            it?.let {
                when (it) {
                    NetReqState.SUCCESS -> {
                        val header = binding.navigationViewMenu.getHeaderView(0)
                        val shimmer = header.findViewById<ViewGroup>(R.id.menuShimmer)
                        val linear = header.findViewById<ViewGroup>(R.id.menuLinear)
                        shimmer.isVisible = false
                        linear.isVisible = true
                    }
                    NetReqState.ERROR -> {
                        AlertDialog.Builder(this).setTitle(getString(R.string.error_title))
                            .setMessage(getString(R.string.error_mesege))
                            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                                viewModel.getCurrentData()
                            }
                            .setNegativeButton(getString(R.string.no)) { _, _ ->
                            }.create().show()
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //DrawerLayout ???? ???????????????? ???????????? ??????????
        if (binding.toggleButton.isChecked)
            binding.toggleButton.toggle()
        super.onSaveInstanceState(outState)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initToggle() {
        binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.container.background = getDrawable(R.drawable.round_fragment)
                binding.drawerLayout.openDrawer(binding.navigationView)
                animatorSet.start()
            } else {
                animatorSet.reverse()
                binding.container.background = getDrawable(R.color.back_fragment)
                binding.drawerLayout.closeDrawer(binding.navigationView)
            }
        }
    }

    private fun initNavigation() {
        setStyleText(binding.navigationViewMenu.menu.getItem(0).itemId)
        binding.navigationViewMenu.setNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.nav_home -> Toast.makeText(
                    this,
                    getString(R.string.menu_home),
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_profile -> Toast.makeText(
                    this,
                    getString(R.string.menu_profile),
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_accounts -> Toast.makeText(
                    this,
                    getString(R.string.menu_accounts),
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_transactions -> Toast.makeText(
                    this,
                    getString(R.string.menu_transaction),
                    Toast.LENGTH_SHORT
                )
                    .show()
                R.id.nav_starts -> Toast.makeText(
                    this,
                    getString(R.string.menu_stats),
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_settings -> Toast.makeText(
                    this,
                    getString(R.string.menu_settings),
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_help -> Toast.makeText(
                    this,
                    getString(R.string.menu_help),
                    Toast.LENGTH_SHORT
                ).show()
            }
            setStyleText(it.itemId)
            true
        }
    }

    private fun getStyleSpannableString(s: String, typeface: Int): SpannableString {
        val str = SpannableString(s)
        str.setSpan(
            StyleSpan(typeface),
            0,
            s.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return str
    }


    private fun setStyleText(itemId: Int) {
        binding.navigationViewMenu.menu.forEach { item ->
            if (item.itemId == itemId) {
                item.title = getStyleSpannableString(item.title.toString(), Typeface.BOLD)
            } else {
                item.title = getStyleSpannableString(item.title.toString(), Typeface.NORMAL)
            }
        }
    }

    private fun initObjectAnimator() {
        val animatorScaleY =
            ObjectAnimator.ofFloat(binding.container, View.SCALE_Y, 1F, SCALE).apply {
                duration = DURATION
                interpolator = LinearInterpolator()
            }
        val animatorScaleX =
            ObjectAnimator.ofFloat(binding.container, View.SCALE_X, 1F, SCALE).apply {
                duration = DURATION
                interpolator = LinearInterpolator()
            }
        val animatorRotation =
            ObjectAnimator.ofFloat(binding.container, View.ROTATION, 0.0F, ROTATION).apply {
                duration = DURATION
                interpolator = LinearInterpolator()
            }
        val animatorTranslationX =
            ObjectAnimator.ofFloat(binding.container,
                View.TRANSLATION_X,
                0F,
                TRANSLATION.dpToPx(this))
                .apply {
                    duration = DURATION
                    interpolator = LinearInterpolator()
                }

        animatorSet.playTogether(
            animatorScaleY,
            animatorScaleX,
            animatorRotation,
            animatorTranslationX
        )
    }

    private fun initDrawerLayout() {
        val drawerLayout = binding.drawerLayout
        drawerLayout.drawerElevation = 0F
        drawerLayout.setScrimColor(Color.TRANSPARENT)
    }

    override fun onLogin() {
        viewModel.getCurrentData()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment()).commit()
    }
}