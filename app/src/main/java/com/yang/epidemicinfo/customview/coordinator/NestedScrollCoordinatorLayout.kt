package com.yang.epidemicinfo.customview.coordinator

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat


/**
 * @program: EpidemicInfo
 *
 * @description: 自定义 CoordinatorLayout
 *
 * @author: YangRT
 *
 * @create: 2020-03-11 21:51
 **/

class NestedScrollCoordinatorLayout: CoordinatorLayout, NestedScrollingChild {

    private lateinit var  helper:NestedScrollingChildHelper
  

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    private fun init(){
        helper = NestedScrollingChildHelper(this)
        isNestedScrollingEnabled = true
//        val dummyView = View(context)
//        behavior = DummyBehavior()
//        ViewCompat.setElevation(dummyView,ViewCompat.getElevation(this))
//        dummyView.fitsSystemWindows = false
//        val lp = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
//        lp.behavior = behavior
//        addView(dummyView,lp)
    }

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        val tmp =  super.onStartNestedScroll(child, target, nestedScrollAxes)
        return startNestedScroll(nestedScrollAxes) || tmp
    }

    override fun onStopNestedScroll(target: View) {
        super.onStopNestedScroll(target)
        stopNestedScroll()
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        val tmp = Array(2){IntArray(2)}
        super.onNestedPreScroll(target, dx, dy, tmp[0])
        dispatchNestedPreScroll(dx,dy,tmp[1],null)
        consumed[0] = tmp[0][0] + tmp[1][0]
        consumed[1] = tmp[0][1] + tmp[1][1]
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        dispatchNestedScroll(dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,null)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        val tmp =  super.onNestedPreFling(target, velocityX, velocityY)
        return dispatchNestedPreFling(velocityX,velocityY) || tmp
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        val tmp =  super.onNestedFling(target, velocityX, velocityY, consumed)
        return dispatchNestedFling(velocityX,velocityY,consumed) || tmp
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        helper.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return helper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return helper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        helper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return helper.hasNestedScrollingParent()
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        return helper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        return helper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return helper.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return helper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    private  class DummyBehavior<DummyView: View>:Behavior<DummyView>(){

        private var cache:IntArray = IntArray(2)



        override fun onStartNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            child: DummyView,
            directTargetChild: View,
            target: View,
            axes: Int,
            type: Int
        ): Boolean {
            val scrollLayout = coordinatorLayout as NestedScrollCoordinatorLayout
           return scrollLayout.startNestedScroll(axes)
        }

        override fun onStopNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            child: DummyView,
            target: View,
            type: Int
        ) {
            val scrollLayout = coordinatorLayout as NestedScrollCoordinatorLayout
            return scrollLayout.stopNestedScroll()
        }

        override fun onNestedPreScroll(
            coordinatorLayout: CoordinatorLayout,
            child: DummyView,
            target: View,
            dx: Int,
            dy: Int,
            consumed: IntArray,
            type: Int
        ) {
            val scrollLayout = coordinatorLayout as NestedScrollCoordinatorLayout
            cache[0] = consumed[0]
            cache[1] = consumed[1]
            scrollLayout.dispatchNestedPreScroll(dx,dy, cache, null)
        }

        override fun onNestedPreFling(
            coordinatorLayout: CoordinatorLayout,
            child: DummyView,
            target: View,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            return true
        }


    }


}