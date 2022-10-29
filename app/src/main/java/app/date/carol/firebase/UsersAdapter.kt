package app.date.carol.firebase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.date.carol.firebase.databinding.UserItemBinding

class UsersAdapter(
    var context : Context,
    var userList : ArrayList<Users>
)  : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(){

    inner class UsersViewHolder(val adapterBinding: UserItemBinding) :
        RecyclerView.ViewHolder(adapterBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {

        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

        holder.adapterBinding.tvName.text = userList[position].userName
        holder.adapterBinding.tvAge.text = userList[position].userAge.toString()
        holder.adapterBinding.tvEmail.text = userList[position].userEmail

        holder.adapterBinding.linearLayout.setOnClickListener {
            val intent  = Intent(context, Update::class.java)

            intent.putExtra("id", userList[position].userId)
            intent.putExtra("name", userList[position].userName)
            intent.putExtra("age", userList[position].userAge)
            intent.putExtra("email", userList[position].userEmail)

            context.startActivity(intent)



        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun getUserId(position: Int) : String{
        return userList[position].userId
    }


}