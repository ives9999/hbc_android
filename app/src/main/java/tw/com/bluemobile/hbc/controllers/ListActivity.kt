package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.BaseList
import tw.com.bluemobile.hbc.extensions.setInfo
import tw.com.bluemobile.hbc.models.BaseModel
import tw.com.bluemobile.hbc.models.MemberPetModel
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.PERPAGE


open class ListActivity<U: BaseModel>: BaseActivity() {

    open var rows: ArrayList<U> = arrayListOf()

    var recyclerView: RecyclerView? = null

    //RecycleView scroll
    //private lateinit var scrollListener: RecyclerView.OnScrollListener
    lateinit var recyelerViewLinearLayoutManager: LinearLayoutManager
//    val lastVisibleItemPosition: Int
//        get() = recyelerViewLinearLayoutManager.findLastVisibleItemPosition()


//    var scrollListener: BaseEndlessRecyclerViewScrollListener? = null
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
            //setRecyclerViewScrollListener()
            val scrollListener = BaseEndlessRecyclerViewScrollListener(recyelerViewLinearLayoutManager)
            recyclerView?.addOnScrollListener(scrollListener)
        }

    }

    open fun getList(page: Int, perPage: Int) {}

    protected fun refresh() {
        page = 1
        getList(page, PERPAGE)
    }

//    open fun setRows(rows: ArrayList<U>, isRefresh: Boolean = true) {
//
//        recyclerView!!.adapter!!.rows = rows
//        if (isRefresh) {
//            recyclerView!!.adapter!!.notifyDataSetChanged()
//        }
//    }


    fun showNoRows() {
        findViewById<RelativeLayout>(R.id.root)?.setInfo(this, "目前暫無資料！！")
    }

    val onRefreshClick: (() -> Unit) = {
        refresh()
    }

    inner class BaseEndlessRecyclerViewScrollListener(recyelerViewLinearLayoutManager: LinearLayoutManager): EndlessRecyclerViewScrollListener(recyelerViewLinearLayoutManager) {

        //page: 目前在第幾頁
        //totalItemCount: 已經下載幾頁
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
            //page已經+1了
            getList(page, PERPAGE)
        }

    }

//    fun setRecyclerViewScrollListener() {
//        scrollListener = object : RecyclerView.OnScrollListener() {
//
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                val totalItemCount = recyelerViewLinearLayoutManager.itemCount
//                println(lastVisibleItemPosition)
//                if (totalItemCount == lastVisibleItemPosition + 1) {
//                    //Log.d("MyTAG", "Load new list")
//                    println("Load new list")
//                    recyclerView.removeOnScrollListener(scrollListener)
//                }
//            }
//        }
//
//        recyclerView?.addOnScrollListener(scrollListener)
//    }

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
    // 剩下幾筆後，就要開始load下一頁
    private var visibleThreshold = 5

    // The current offset index of data you have loaded
    //目前在第幾頁
    private var currentPage = 0

    // The total number of items in the dataset after the last load
    // 已經下載下來的數量
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

        var lastVisibleItemPosition = 0 //最後一個出現在畫面上的項目idx
        val totalItemCount = mLayoutManager.itemCount //已經從網路取得的所有數量

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

        println("lastVisibleItemPosition:${lastVisibleItemPosition}")
        println("totalItemCount:${totalItemCount}")
        println("previousTotalItemCount:${previousTotalItemCount}")
        println("=================================================")

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        // 回復到原始狀況
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
        // 當下載後，totalItemCount就會增加，所以會大於之前儲存的數字，就要更新儲存的數字
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        // 如果超過要下載的剩下筆數，就澳開始下載
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