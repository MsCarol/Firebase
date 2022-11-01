package app.date.carol.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import app.date.carol.firebase.databinding.ActivityForgetBinding
import com.google.firebase.auth.FirebaseAuth

class Forget : AppCompatActivity() {

    lateinit var forget : ActivityForgetBinding
    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forget = ActivityForgetBinding.inflate(layoutInflater)
        val view = forget.root
        setContentView(view)

        supportActionBar?.title = "Forgot Password"

        forget.button.setOnClickListener {

            val email = forget.emailreset.text.toString()
            auth.sendPasswordResetEmail(email).addOnCompleteListener(){ task ->

                if (task.isSuccessful){
                    Toast.makeText(applicationContext, "We sent an email to reset your password", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, task.exception?.toString(), Toast.LENGTH_SHORT).show()
                }

            }

        }
    }
}