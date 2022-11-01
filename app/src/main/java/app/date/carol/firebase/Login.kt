package app.date.carol.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.date.carol.firebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    lateinit var loginBinding: ActivityLoginBinding

    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        supportActionBar?.title = "LogIn"

        loginBinding.btnSignin.setOnClickListener {

            val userEmail = loginBinding.email.text.toString()
            val userPass = loginBinding.password.text.toString()
            loginUserWithFirebase(userEmail, userPass)

        }

        loginBinding.btnSignup.setOnClickListener {
            val intent = Intent(this@Login,Register::class.java)
            startActivity(intent)
        }

        loginBinding.btnForgotPass.setOnClickListener {
            val intent = Intent(this@Login, Forget::class.java)
            startActivity(intent)
        }

        loginBinding.signInwithPhone.setOnClickListener {
            val intent = Intent(this@Login, Phone::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun loginUserWithFirebase(userEmail : String, userPassword: String) {
        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this){ task ->

                if (task.isSuccessful){
                    Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, task.exception?.toString(), Toast.LENGTH_SHORT).show()
                }

            }
    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser
        if (user != null){
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}