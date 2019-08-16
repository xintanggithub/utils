package com.tson.utils.fr

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 *  Date 2019-08-15 14:30
 *
 * @author tangxin
 */
class MarketAppAdapter
/**
 * Instantiates a new Market app adapter.
 *
 * @param fm the fm
 */
(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var mAllFragment: TestFragment? = null
    private var mInstallFragment: TestFragment? = null
    private var mUpdateFragment: TestFragment? = null

    override fun getItem(position: Int): Fragment? {
        val bundle = Bundle()
        if (position == 0) {
            if (mAllFragment == null) {
                mAllFragment = TestFragment()
                bundle.putInt("index", 1)
                mAllFragment!!.setArguments(bundle)
            }
            return mAllFragment
        } else if (position == 1) {
            if (mInstallFragment == null) {
                mInstallFragment = TestFragment()
                bundle.putInt("index", 2)
                mInstallFragment!!.setArguments(bundle)
            }
            return mInstallFragment
        } else if (position == 2) {
            if (mUpdateFragment == null) {
                mUpdateFragment = TestFragment()
                bundle.putInt("index", 3)
                mUpdateFragment!!.setArguments(bundle)
            }
            return mUpdateFragment
        }
        return null
    }

    override fun getCount(): Int {
        return 3
    }
}