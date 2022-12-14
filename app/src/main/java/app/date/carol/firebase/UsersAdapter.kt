package app.date.carol.firebase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import app.date.carol.firebase.databinding.UserItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

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

        val imageUrl = userList[position].url
        Picasso.get().load(imageUrl).into(holder.adapterBinding.imageView, object : Callback{
            override fun onSuccess() {
                holder.adapterBinding.progressBar2.visibility = View.INVISIBLE
            }

            override fun onError(e: Exception?) {
                Toast.makeText(context, e?.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        holder.adapterBinding.linearLayout.setOnClickListener {
            val intent  = Intent(context, Update::class.java)

            intent.putExtra("id", userList[position].userId)
            intent.putExtra("name", userList[position].userName)
            intent.putExtra("age", userList[position].userAge)
            intent.putExtra("email", userList[position].userEmail)
            intent.putExtra("imageUrl", imageUrl)
            intent.putExtra("imageName", userList[position].imageName)

            context.startActivity(intent)



        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun getUserId(position: Int) : String{
        return userList[position].userId
    }

    fun getImageName(position: Int) :String {
        return userList[position].imageName
    }


}