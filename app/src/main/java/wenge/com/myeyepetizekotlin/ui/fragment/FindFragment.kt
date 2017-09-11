package wenge.com.myeyepetizekotlin.ui.fragment

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_find.*
import org.jetbrains.anko.support.v4.toast
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.contract.FindContract
import wenge.com.myeyepetizekotlin.mvp.model.bean.FindBean
import wenge.com.myeyepetizekotlin.mvp.presenter.FindPresenter
import wenge.com.myeyepetizekotlin.ui.adapter.FindAdapter

/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */


class FindFragment : BaseFragment(), FindContract.View {
    var presenter: FindPresenter? = null
    var datas: MutableList<FindBean>? = null
    var mAdapter: FindAdapter? = null


    override fun getLayoutId(): Int {
        return R.layout.fragment_find
    }

    override fun initView() {
        presenter = FindPresenter(context, this)
        presenter?.start()
        recycler_find.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        mAdapter.getList().addAll(mList);
//        mAdapter.notifyDataSetChanged();
//
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT|ItemTouchHelper.UP|ItemTouchHelper.DOWN));
//        helper.attachToRecyclerView(mRecyclerView);

        recycler_find.itemAnimator = DefaultItemAnimator()

    }

    override fun setData(findBeans: MutableList<FindBean>) {
        datas = findBeans
        mAdapter = FindAdapter(datas!!) {
            toast(it.name)
        }
        recycler_find.adapter = mAdapter
    }
}