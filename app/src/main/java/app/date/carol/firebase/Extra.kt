package app.date.carol.firebase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.*

class Extra {

//    lateinit var editTxtName : EditText
//    lateinit var buttonSend : Button
//    lateinit var tvName : TextView
//
//    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
//    val reference : DatabaseReference = database.reference.child("Users")
//    val reference2 : DatabaseReference = database.reference
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        editTxtName = findViewById(R.id.editName)
//        buttonSend = findViewById(R.id.button)
//        tvName = findViewById(R.id.tvName)
//
//        reference2.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val realName: String = snapshot.child("Users").child("name").value as String
//                tvName.text = realName
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//
//        })
//        buttonSend.setOnClickListener {
//            val username : String = editTxtName.text.toString()
//            reference.child("userName").setValue(username)
//
//        }
//
//    }
}