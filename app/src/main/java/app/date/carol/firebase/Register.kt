package app.date.carol.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.date.carol.firebase.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    lateinit var registerBinding: ActivityRegisterBinding
    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = registerBinding.root
        setContentView(view)

        supportActionBar?.title = "Register"


        registerBinding.btnSignup.setOnClickListener {
            val userEmail = registerBinding.emailReg.text.toString()
            val userPass = registerBinding.passwordReg.text.toString()
            signUpWithFirebase(userEmail, userPass)


        }
    }

    fun signUpWithFirebase(userEmail: String, userPassword : String){
        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(this){ task ->
            if (task.isSuccessful){
                Toast.makeText(applicationContext, "Your account has been created", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(applicationContext, task.exception?.toString(), Toast.LENGTH_SHORT).show()

            }

        }
    }
}