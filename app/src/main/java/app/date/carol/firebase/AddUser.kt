package app.date.carol.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.date.carol.firebase.databinding.ActivityAddUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddUser : AppCompatActivity() {

    lateinit var addUserBinding: ActivityAddUserBinding

    val datebase: FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference: DatabaseReference = datebase.reference.child("MyUsers")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addUserBinding = ActivityAddUserBinding.inflate(layoutInflater)
        val view = addUserBinding.root
        setContentView(view)

        supportActionBar?.title = "Add User"

        addUserBinding.btnAddUser.setOnClickListener {
            addUserToDatabase()

        }
    }

    fun addUserToDatabase() {
        val name: String = addUserBinding.editTxtName.text.toString()
        val age: Int = addUserBinding.editTxtAge.text.toString().toInt()
        val email: String = addUserBinding.editTxtEmail.text.toString()

        val id: String = reference.push().key.toString()

        val user = Users(id, name, age, email)
        reference.child(id).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    applicationContext,
                    "User has beeen added to the database",
                    Toast.LENGTH_SHORT
                ).show()
                finish()

            } else {
                Toast.makeText(
                    applicationContext,
                    task.exception.toString(),
                    Toast.LENGTH_SHORT
                ).show()

            }


        }
    }
}