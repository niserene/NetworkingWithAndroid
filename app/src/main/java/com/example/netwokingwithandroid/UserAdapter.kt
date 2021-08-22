package com.example.netwokingwithandroid

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter():RecyclerView.Adapter<UserAdapter.UserViewHolder>(){


    private var data: List<User> = ArrayList()
    var onItemClick: ((login:String)->Unit)?=null

    fun swapData(data: List<User>){
        this.data = data;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: User){
            with(itemView) {

                textView1.text = item.login
                textView2.text = item.type
                Picasso.get().load(item.avatar_url).into(imageView)
                setOnClickListener{
                    onItemClick?.invoke(item.login)
                }
            }

        }
    }

}