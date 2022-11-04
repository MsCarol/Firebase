package app.date.carol.firebase

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.date.carol.firebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference : DatabaseReference = database.reference.child("MyUsers")
    val userList = ArrayList<Users>()
    val imageNameList = ArrayList<String>()
    lateinit var usersAdapter: UsersAdapter

    val firebaseStorage : FirebaseStorage = FirebaseStorage.getInstance()
    val storageReference : StorageReference = firebaseStorage.reference


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            mainBinding = ActivityMainBinding.inflate(layoutInflater)
            val view = mainBinding.root
        setContentView(view)

            mainBinding.floatingActionButton.setOnClickListener {
                val intent = Intent(this,AddUser::class.java)
                startActivity(intent)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                   val id = usersAdapter.getUserId(viewHolder.adapterPosition)
                    reference.child(id).removeValue()

                    val imageName = usersAdapter.getImageName(viewHolder.adapterPosition)
                    val imageReference = storageReference.child("images").child(imageName)

                    imageReference.delete()

                    Toast.makeText(applicationContext, "User was deleted", Toast.LENGTH_SHORT).show()
                }

            }).attachToRecyclerView(mainBinding.recyclerview)

          retrieveDataFromDatabase()


    }

    fun retrieveDataFromDatabase(){
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()

                for (eachuser in snapshot.children){
                    val user = eachuser.getValue(Users::class.java)
                    if(user != null){

                        println("userId : ${user.userId}")
                        println("userName : ${user.userName}")
                        println("userAge : ${user.userAge}")
                        println("userEmail: ${user.userEmail}")
                        println("******************************}")

                        userList.add(user)




                    }
                    usersAdapter = UsersAdapter(this@MainActivity, userList)

                    mainBinding.recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                    mainBinding.recyclerview.adapter = usersAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_all,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all) {
            showDialogMessage()
        } else if(item.itemId == R.id.signOut){
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDialogMessage() {
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete All Users")
        dialogMessage.setMessage("If you click yes all items will be deleted" + "" +
                "if you want to delete a single item swipe left or right")
        dialogMessage.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
        })

        dialogMessage.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->

            reference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (eachuser in snapshot.children) {
                        val user = eachuser.getValue(Users::class.java)
                        if (user != null) {
                            imageNameList.add(user.imageName)




                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


            reference.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful){

                    for (imageName in imageNameList) {

                        val imageReference = storageReference.child("images").child(imageName)
                        imageReference.delete()
                    }

                    usersAdapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext, "All users were deleted", Toast.LENGTH_SHORT).show()
                }

            }
        })

        dialogMessage.create().show()
    }
}