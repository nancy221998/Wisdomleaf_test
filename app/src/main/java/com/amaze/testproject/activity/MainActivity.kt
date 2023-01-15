package com.amaze.testproject.activity

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amaze.testproject.ApiInterface
import com.amaze.testproject.R
import com.amaze.testproject.ServiceBuilder
import com.amaze.testproject.adapter.ImageListAdapter
import com.amaze.testproject.model.ImageModel
import com.amaze.testproject.model.ImageModelItem
import com.amaze.testproject.other.ProgressDialogScreen
import com.amaze.testproject.other.Utilities
import com.amaze.testproject.other.Utilities.setStatusBarColor
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var imageModel : MutableList<ImageModelItem> = ArrayList()
    var searchImageList : MutableList<ImageModelItem> = ArrayList()
    lateinit var  imageListAdapter: ImageListAdapter
    var scrollablePosition=0
    var page_no=1
    var isLoading=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        setStatusBarColor(this)
        init()
    }

    fun init(){
        addItemInList()
        searchEt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!!.length>0){
                    filter(s.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.length>0){
                    filter(s.toString())
                }
            }
        })

        swipe.setOnRefreshListener {
            page_no=1
            scrollablePosition=0
            imageModel.clear()
            addItemInList()
        }
     }

    fun filter(text: String) {
        val temp: MutableList<ImageModelItem> = ArrayList<ImageModelItem>()
        if(imageModel.size>0) {
            for (d in imageModel) {
                if (d.author.trim().toLowerCase().contains(text.trim().toLowerCase())
                ) {
                    temp.add(d)
                }
            }
            imageListAdapter.updateAdapter(temp)
        }
    }

    private fun addItemInList() {
        ProgressDialogScreen.showProgressDialog(this)
        if(Utilities.isNetworkAvailable(this)){
            val request = ServiceBuilder.buildService(ApiInterface::class.java)
            val call = request.getListData(page_no.toString(),"20")
            call?.enqueue(object : Callback<ImageModel> {
                override fun onResponse(call: Call<ImageModel>, response: Response<ImageModel>) {
                    Log.d("response",response.toString())
                    ProgressDialogScreen.hideProgressDialog()
                    if(response.isSuccessful){
                        response.body().let {
                          //  imageModel=it as MutableList<ImageModelItem>
                            imageModel.addAll(it as MutableList<ImageModelItem>)
                            setDataInList()


                        }
                    }
                }
                override fun onFailure(call: Call<ImageModel>, t: Throwable) {
                    Log.d("TAG", "onFaliure: " + t.message)
                }
            })
        }else{
            Utilities.noInternetDialog(this)
        }
    }

    fun setDataInList(){
        if(swipe.isRefreshing){
            Handler().postDelayed(Runnable {
                swipe.isRefreshing = false
            }, 1000)
        }
        if(imageModel.size==0){
            isLoading = false
            recycleList.visibility= View.GONE
            hintText.visibility= View.VISIBLE
        }else{
            isLoading = true
            recycleList.visibility= View.VISIBLE
            hintText.visibility= View.GONE
        }
        recycleList.apply {
             layoutManager= LinearLayoutManager(this@MainActivity)
             imageListAdapter= ImageListAdapter(imageModel,this@MainActivity)
             adapter=imageListAdapter
             isNestedScrollingEnabled=false
            scrollToPosition(scrollablePosition)
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && isLoading) {
                        isLoading=false
                        page_no += 1
                        scrollablePosition=imageModel.size-1
                        addItemInList()
                    }
                }
            })
        }
    }
   }