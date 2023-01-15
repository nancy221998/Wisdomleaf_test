package com.amaze.testproject.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amaze.testproject.R
import com.amaze.testproject.model.ImageModelItem
import com.amaze.testproject.other.ProgressDialogScreen
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_item.view.*
import java.util.*

class ImageListAdapter
    (imageList: MutableList<ImageModelItem>, context: Context)
    : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {
    var context: Context
    var imageList: MutableList<ImageModelItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false))
        }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
       var bean=imageList.get(i)
             Picasso.with(context).load(imageList.get(i).download_url).resize(bean.width,bean.height).into(holder.imageView)
             holder.nameTv.text=bean.author
             holder.dateTv.text=" Id : " +bean.id +" "
            holder.titleTv.text=" Description : " +bean.download_url +" "

            holder.itemView.setOnClickListener(){
                showDiscription(i)
            }
        }

    fun showDiscription(pos:Int) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_desc_item)
        var editnotes=dialog.findViewById(R.id.editnotes) as TextView
        var cancelIv=dialog.findViewById(R.id.cancelIv) as ImageView
        editnotes.setText(imageList.get(pos).author +" \n" +imageList.get(pos).download_url)
        cancelIv.setOnClickListener(){
            dialog.dismiss()
        }
        dialog.show()
    }

    fun updateAdapter(temp: MutableList<ImageModelItem>) {
        imageList=temp
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTv = itemView.nameTv
        val dateTv = itemView.dateTv
        var titleTv=itemView.titleTv
        val imageView = itemView.imageView
    }

    init {
        this.imageList = imageList
        this.context = context
    }
}
