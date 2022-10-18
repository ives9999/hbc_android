package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.BaseList
import tw.com.bluemobile.hbc.extensions.setInfo
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.PERPAGE


open class ListActivity: BaseActivity() {

    var recyclerView: RecyclerView? = null

    //RecycleView scroll
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    lateinit var recyelerViewLinearLayoutManager: LinearLayoutManager
    val lastVisibleItemPosition: Int
        get() = recyelerViewLinearLayoutManager.findLastVisibleItemPosition()


    //var scrollListener: EndlessRecyclerViewScrollListener? = null
    //var refreshLayout: SwipeRefreshLayout? = null

    protected var page: Int = 1
    protected var perPage: Int = PERPAGE
    protected var totalCount: Int = 0
    protected var totalPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        super.init()

        loading = Loading(this)

        findViewById<SwipeRefreshLayout>(R.id.refresh) ?. let {
            it.setOnRefreshListener {
                refresh()
                it.isRefreshing = false
            }
        }

        findViewById<RecyclerView>(R.id.list) ?. let {

            recyclerView = it
            recyelerViewLinearLayoutManager = LinearLayoutManager(this)
            recyclerView?.layoutManager = recyelerViewLinearLayoutManager
            setRecyclerViewScrollListener()
            //scrollListener = EndlessRecyclerViewScrollListener(linearLayoutManager)
        }

    }

    open fun getList() {}

    protected fun refresh() {
        page = 1
        getList()
    }

    fun showNoRows() {
        findViewById<RelativeLayout>(R.id.root)?.setInfo(this, "目前暫無資料！！")
    }

    val onRefreshClick: (() -> Unit) = {
        refresh()
    }

    fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {

//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                val pos: Int = recyelerViewLinearLayoutManager.findLastVisibleItemPosition()
//                println(pos)
//                //println("dy:${dy}")
//                //if (currentRowsSize < totalCount) {
//                    //pos = layoutManager.findLastCompletelyVisibleItemPosition()
//                    //pos = layoutManager.findLastVisibleItemPosition()
//                    //println("pos:${pos}")
//                //}
//            }


            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager?.itemCount
                println(lastVisibleItemPosition)
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    //Log.d("MyTAG", "Load new list")
                    println("Load new list")
                    recyclerView.removeOnScrollListener(scrollListener)
                }
            }
        }

        recyclerView?.addOnScrollListener(scrollListener)
    }

//    protected open fun setRecyclerViewScrollListener() {
//
//        if (recyclerView != null) {
//            var pos: Int = 0
//
//            val scrollerListenr = object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    //println("dy:${dy}")
//                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                    if (currentRowsSize < totalCount) {
//                        //pos = layoutManager.findLastCompletelyVisibleItemPosition()
//                        pos = layoutManager.findLastVisibleItemPosition()
//                        //println("pos:${pos}")
//                    }
//                }
//
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//
//                    //println("tableLists.size:${tableLists.size}")
//                    if (currentRowsSize == pos + 1 && newState == RecyclerView.SCROLL_STATE_IDLE && currentRowsSize < totalCount && !loading) {
//                        getDataStart(page, perPage)
//                    }
//                }
//            }
//            recyclerView?.addOnScrollListener(scrollerListenr)
//        }
//    }
}

abstract class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 5

    // The current offset index of data you have loaded
    private var currentPage = 0

    // The total number of items in the dataset after the last load
    private var previousTotalItemCount = 0

    // True if we are still waiting for the last set of data to load.
    private var loading = true

    // Sets the starting page index
    private val startingPageIndex = 0
    var mLayoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: LinearLayoutManager) {
        mLayoutManager = layoutManager
    }

    constructor(layoutManager: GridLayoutManager) {
        mLayoutManager = layoutManager
        visibleThreshold = visibleThreshold * layoutManager.spanCount
    }

    constructor(layoutManager: StaggeredGridLayoutManager) {
        mLayoutManager = layoutManager
        visibleThreshold = visibleThreshold * layoutManager.spanCount
    }

    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount
        if (mLayoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions =
                (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
        } else if (mLayoutManager is GridLayoutManager) {
            lastVisibleItemPosition =
                (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
        } else if (mLayoutManager is LinearLayoutManager) {
            lastVisibleItemPosition =
                (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            loading = true
        }
    }

    // Call this method whenever performing new searches
    fun resetState() {
        currentPage = startingPageIndex
        previousTotalItemCount = 0
        loading = true
    }

    // Defines the process for actually loading more data based on page
    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)
}