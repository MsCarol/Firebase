package app.date.carol.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.date.carol.firebase.databinding.ActivityPhoneBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class Phone : AppCompatActivity() {

    lateinit var phoneBinding: ActivityPhoneBinding

    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var mCallbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    var verificationCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phoneBinding = ActivityPhoneBinding.inflate(layoutInflater)
        val view = phoneBinding.root
        setContentView(view)

        supportActionBar?.title = "Sign In with Phone Number"


        phoneBinding.smscode.setOnClickListener {
            val userPhone = phoneBinding.phonenumber.text.toString()
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(userPhone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this@Phone)
                .setCallbacks(mCallbacks)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)


        }
        phoneBinding.verifycode.setOnClickListener {

            signInWithSMSCode()


        }

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {

            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)

                verificationCode = p0
            }

        }
    }

    private fun signInWithSMSCode() {
        val userEnterCode = phoneBinding.verificationcode.text.toString()
        val credential = PhoneAuthProvider.getCredential(verificationCode,userEnterCode)
        signInWithAuthCredential(credential)
    }

    fun signInWithAuthCredential(credential: PhoneAuthCredential){
        auth.signInWithCredential(credential).addOnCompleteListener(){ task ->

            if (task.isSuccessful){
                val intent = Intent(this@Phone, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(applicationContext, "The Code you entered is incorrect", Toast.LENGTH_SHORT).show()

            }

        }

    }
}