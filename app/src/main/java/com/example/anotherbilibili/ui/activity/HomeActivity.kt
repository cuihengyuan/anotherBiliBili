package com.example.anotherbilibili.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.example.anotherbilibili.R
import com.example.anotherbilibili.base.baseActivity
import com.example.anotherbilibili.ui.fragment.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.textColor
import cn.leancloud.AVUser
import com.example.anotherbilibili.mvp.contract.HomeContract
import com.example.anotherbilibili.utils.AVobjectUtils
import com.example.anotherbilibili.utils.themeChage.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.jetbrains.anko.toast
import com.lxj.xpopup.XPopup
import org.jetbrains.anko.startActivity

/**
 *首页
 *
 */


class HomeActivity : baseActivity(), HomeContract.view {

    private val mIconSelectIds = intArrayOf(
        R.mipmap.ic_category_selected,
        R.mipmap.ic_home_selected,
        R.mipmap.ic_dynamic_selected
    )
    private val mIconUnselectIds = intArrayOf(
        R.mipmap.ic_category_unselected,
        R.mipmap.ic_home_unselected,
        R.mipmap.ic_dynamic_unselected
    )


    private var homeFragment: HomeFragment? = null
    private var catalogFragment: CatalogFragment? = null
    private var distinctiveFragment: DistinctiveFragment? = null
    private var courrentIndex = 2  //第一次进入推荐页面

    override fun getLayoutId(): Int = R.layout.activity_home


    override fun initData() {

        val currentUser = AVUser.getCurrentUser()

        if (currentUser != null) {

            toast("你好" + currentUser.username)

            if (getThemeType(currentUser) == ThemeType.Pink) {
                return
            }

            ThemeManager.get().showTheme(this, currentUser)

        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        setUpToolBar(toolbar_main)
        setListener()
        ll_home.setOnClickListener {
            changeFragment(2)
        }
        ll_catalog.setOnClickListener {
            changeFragment(1)
        }
        ll_distinct.setOnClickListener {
            changeFragment(3)
        }


    }

    override fun chageTheme() {


    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun finalPrepare() {
        changeFragment(courrentIndex)
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun changeFragment(positon: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        setTextDefault()
        setIconDefault()
        val pinkcolor = Color.parseColor("#fffb7299")
        when {
            positon == 1 -> {
                catalogFragment?.let {
                    transaction.show(it)
                } ?: CatalogFragment.getInstance().let {
                    catalogFragment = it
                    transaction.add(R.id.container, it, "catalogFragment")
                }
                im_catalog.setImageDrawable(getDrawable(mIconSelectIds[0]))
                tv_catalog.setTextColor(pinkcolor)

            }
            positon == 2 -> {
                homeFragment?.let {
                    transaction.show(it)
                } ?: HomeFragment.getInstance().let {
                    homeFragment = it
                    transaction.add(R.id.container, it, "homeFragment")
                }
                im_home.setImageDrawable(getDrawable(mIconSelectIds[1]))
                tv_home.setTextColor(pinkcolor)


            }
            positon == 3 -> {
                distinctiveFragment?.let {
                    transaction.show(it)
                } ?: DistinctiveFragment.getInstance().let {
                    distinctiveFragment = it
                    transaction.add(R.id.container, it, "distinctFragment")
                }
                im_distinct.setImageDrawable(getDrawable(mIconSelectIds[2]))
                tv_distinct.setTextColor(pinkcolor)

            }

        }
        transaction.commit()
        courrentIndex = positon
    }


    private fun hideFragments(transaction: FragmentTransaction) {
        homeFragment?.let { transaction.hide(it) }
        catalogFragment?.let { transaction.hide(it) }
        distinctiveFragment?.let { transaction.hide(it) }
    }


    private fun setTextDefault() {
        with(R.color.text_default) {
            tv_home.textColor = this
            tv_catalog.textColor = this
            tv_distinct.textColor = this
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setIconDefault() {
        im_home.setImageDrawable(getDrawable(mIconUnselectIds[1]))
        im_catalog.setImageDrawable(getDrawable(mIconUnselectIds[0]))
        im_distinct.setImageDrawable(getDrawable(mIconUnselectIds[2]))
    }


    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        if (tab_layout != null) {
            outState.putInt("currTabIndex", courrentIndex)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        if (savedInstanceState != null) {
            courrentIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState, persistentState)

    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu)
//        inflater.inflate(R.menu.toolbar_menu, menu)
//    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun removeLoading() {
    }

    override fun showIsLoading() {

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        when (id) {
            R.id.action_download -> {

            }
            R.id.action_search -> {

            }

        }
        return super.onOptionsItemSelected(item)

    }

    protected fun setUpToolBar(toolBar: Toolbar) {
        setSupportActionBar(toolBar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
    }


    override fun onBackPressed() {
        if (dl_main.isDrawerOpen(GravityCompat.START)) run { dl_main.closeDrawer(GravityCompat.START) }
        else run {
            super.onBackPressed()
        }

    }


    fun setListener() {
        ll_top_menu_nav.isVerticalScrollBarEnabled = false
        ll_top_menu_nav.setOnClickListener {
            if (dl_main.isDrawerOpen(GravityCompat.START)) {
                dl_main.closeDrawer(GravityCompat.START)
            } else {
                dl_main.openDrawer(GravityCompat.START)
            }
        }

        main_nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_color_lens -> {
                    showThemeChangeDialog()
                    true
                }
                else -> {
                    startActivity<NothingActivity>()
                    true
                }
            }

        }
        main_nav_view.getHeaderView(0).setOnClickListener {

            val currentUser = AVobjectUtils.getCurentUser(this)
            if (currentUser == null) {
                // 跳到首页
                // 显示注册或登录页面
                startActivity<LoginActivity>()
                toast("当前为游客状态")
                finish()
            }

        }


    }


    override fun showThemeChangeDialog() {
        val currentUser = AVUser.getCurrentUser()
        if (currentUser == null) {
            toast("请先登陆")
            return
        }

        XPopup.Builder(this)
            //.maxWidth(600)
            .asCenterList(
                "请选择主题", arrayOf("少女粉", "早苗绿", "胖次蓝")
            ) { position, text ->
                when (position) {
                    0 -> ThemeManager.get().showTheme(this, iThemeView = PinkThemeView())
                    1 -> ThemeManager.get().showTheme(this, iThemeView = GreenThemeView())
                    2 -> ThemeManager.get().showTheme(this, iThemeView = BuleThemeView())
                    else -> ThemeManager.get().showTheme(this, iThemeView = PinkThemeView())
                }


            }
            .show()
    }


}
