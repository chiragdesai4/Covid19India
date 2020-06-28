package com.chirag.covid19india.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.chirag.covid19india.R
import com.chirag.covid19india.databinding.ActivityMainBinding
import com.chirag.covid19india.dialog.MessageDialog
import com.chirag.covid19india.interfaces.OnRecyclerViewItemClicked
import com.chirag.covid19india.ui.DashboardFragment.Companion.setListener
import com.chirag.covid19india.ui.base.BaseActivity
import com.chirag.covid19india.ui.base.BaseFragment
import com.chirag.covid19india.util.AppConstants
import com.chirag.covid19india.util.Logger
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : BaseActivity(), OnRecyclerViewItemClicked {
    lateinit var mBinding: ActivityMainBinding

    /*Save current tabs identifier in this..*/
    @JvmField
    var mCurrentTab: String? = null

    /* A HashMap of stacks, where we use tab identifier as keys..*/
    private var mStacks: HashMap<String, Stack<Fragment>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setListener(this)
        prepareTabs()
        mBinding.root.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    private fun prepareTabs() {
        mStacks = HashMap()
        mStacks!![AppConstants.TAB_DASHBOARD] = Stack()
        mStacks!![AppConstants.TAB_STATE_LIST] = Stack()
        mStacks!![AppConstants.TAB_ABOUT] = Stack()
        mBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mCurrentTab = AppConstants.TAB_DASHBOARD
        mBinding.navigation.selectedItemId = R.id.navigation_dashboard
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
        when (item.itemId) {
            R.id.navigation_dashboard -> {
                selectedTab(AppConstants.TAB_DASHBOARD)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_states_list -> {
                selectedTab(AppConstants.TAB_STATE_LIST)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                selectedTab(AppConstants.TAB_ABOUT)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun selectedTab(tabId: String?) {
        mCurrentTab = tabId
        if (mStacks!![tabId]!!.size == 0) {
            when (tabId) {
                AppConstants.TAB_DASHBOARD -> pushFragment(tabId, DashboardFragment(), shouldAnimate = false, shouldAdd = true)
                AppConstants.TAB_STATE_LIST -> pushFragment(tabId, StateListFragment(), shouldAnimate = false, shouldAdd = true)
                AppConstants.TAB_ABOUT -> pushFragment(tabId, AboutFragment(), shouldAnimate = false, shouldAdd = true)
            }
        } else {
            pushFragment(tabId, mStacks!![tabId]!!.lastElement(), shouldAnimate = false, shouldAdd = false)
        }
    }

    private fun pushFragment(tag: String?, fragment: Fragment?, shouldAnimate: Boolean, shouldAdd: Boolean) {
        if (shouldAdd) mStacks!![tag]!!.push(fragment)
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        if (shouldAnimate) ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
        ft.replace(R.id.container, fragment!!)
        ft.commit()
        manager.executePendingTransactions()
    }

    private fun popFragment() {
        val fragment = mStacks!![mCurrentTab]!!.elementAt(mStacks!![mCurrentTab]!!.size - 2)
        mStacks!![mCurrentTab]!!.pop()
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        ft.replace(R.id.container, fragment)
        ft.commit()
    }

    override fun onBackPressed() {
        try {
            if (!(mStacks!![mCurrentTab]!!.lastElement() as BaseFragment).onBackPressed()) {
                if (mStacks!![mCurrentTab]!!.size == 1) {
                    //If current tab is not Home then set Home as current tab
                    if (mCurrentTab != AppConstants.TAB_DASHBOARD) {
                        mCurrentTab = AppConstants.TAB_DASHBOARD
                        mBinding.navigation.menu.getItem(0).isChecked = true
                        mBinding.navigation.selectedItemId = R.id.navigation_dashboard
                    } else {
                        try {
                            if (supportFragmentManager.backStackEntryCount <= 1) {
                                showLogoutPopup()
                            } else {
                                supportFragmentManager.popBackStack()
                            }
                        } catch (e: Exception) {
                            super.onBackPressed()
                        }
                    }
                } else {
                    popFragment()
                }
            } else {
                //do nothing.. fragment already handled back button press.
                Logger.e("BackPress Manage By Fragment")
            }
        } catch (e: Exception) {
            super.onBackPressed()
        }
    }

    private fun showLogoutPopup() {
        MessageDialog(this@MainActivity)
                .setTitle(getString(R.string.app_name))
                .setMessage("Do you really want to exit the app?")
                .cancelable(true)
                .setPositiveButton(getString(android.R.string.yes)) { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                    finish()
                }
                .setNegativeButton(getString(android.R.string.no)) { dialog: DialogInterface, _: Int -> dialog.dismiss() }.show()
    }

    override fun onItemClicked() {
        mCurrentTab = AppConstants.TAB_STATE_LIST
        mBinding.navigation.selectedItemId = R.id.navigation_states_list
    }
}