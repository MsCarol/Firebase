package app.date.carol.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.date.carol.firebase.databinding.ActivityAddUserBinding
import app.date.carol.firebase.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Update : AppCompatActivity() {

    lateinit var updateBinding: ActivityUpdateBinding

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference : DatabaseReference = database.reference.child("MyUsers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        val view = updateBinding.root
        setContentView(view)

        supportActionBar?.title = "Update User"

        getAndSetData()

        updateBinding.btnUpdateUser.setOnClickListener {
            updateData()
        }


    }

    private fun updateData() {
        val updateName = updateBinding.editTxtUpdateName.text.toString()
        val updateAge = updateBinding.editTxtUpdateAge.text.toString().toInt()
        val updateEmail = updateBinding.editTxtUpdateEmail.text.toString()
        val userId = intent.getStringExtra("id").toString()

        val userMap = mutableMapOf<String, Any>()
        userMap["userId"] = userId
        userMap["userName"] = updateName
        userMap["userAge"] = updateAge
        userMap["userEmail"] = updateEmail

        reference.child(userId).updateChildren(userMap).addOnCompleteListener { task ->

            if (task.isSuccessful){
                Toast.makeText(applicationContext, "The user has been updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun getAndSetData(){

        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age",0).toString()
        val email = intent.getStringExtra("email")

        updateBinding.editTxtUpdateName.setText(name)
        updateBinding.editTxtUpdateAge.setText(age)
        updateBinding.editTxtUpdateEmail.setText(email)




    }
}