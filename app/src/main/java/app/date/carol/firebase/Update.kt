package app.date.carol.firebase

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import app.date.carol.firebase.databinding.ActivityAddUserBinding
import app.date.carol.firebase.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.util.*

class Update : AppCompatActivity() {

    lateinit var updateBinding: ActivityUpdateBinding

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference : DatabaseReference = database.reference.child("MyUsers")

    lateinit var activityResultLauncher : ActivityResultLauncher<Intent>

    var imageUri : Uri? = null

    val firebaseStorage : FirebaseStorage = FirebaseStorage.getInstance()
    val storageReference : StorageReference = firebaseStorage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        val view = updateBinding.root
        setContentView(view)

        supportActionBar?.title = "Update User"

        registerActivityForResult()

        getAndSetData()

        updateBinding.btnUpdateUser.setOnClickListener {
            uploadPhoto()
        }


        updateBinding.userProfileImage.setOnClickListener {

            chooseImage()

        }


    }

    fun chooseImage(){

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activityResultLauncher.launch(intent)

    }

    fun registerActivityForResult(){

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
            , ActivityResultCallback { result ->

                val resultCode = result.resultCode
                val imageData = result.data

                if (resultCode == RESULT_OK && imageData != null){

                    imageUri = imageData.data

                    //Picasso

                    imageUri?.let {

                        Picasso.get().load(it).into(updateBinding.userProfileImage)

                    }

                }

            })

    }

    private fun updateData(imageUrl: String, imageName : String) {
        val updateName = updateBinding.editTxtUpdateName.text.toString()
        val updateAge = updateBinding.editTxtUpdateAge.text.toString().toInt()
        val updateEmail = updateBinding.editTxtUpdateEmail.text.toString()
        val userId = intent.getStringExtra("id").toString()

        val userMap = mutableMapOf<String, Any>()
        userMap["userId"] = userId
        userMap["userName"] = updateName
        userMap["userAge"] = updateAge
        userMap["userEmail"] = updateEmail
        userMap["url"] =imageUrl
        userMap["imageName"] = imageName

        reference.child(userId).updateChildren(userMap).addOnCompleteListener { task ->

            if (task.isSuccessful){
                Toast.makeText(applicationContext, "The user has been updated", Toast.LENGTH_SHORT).show()
                updateBinding.btnUpdateUser.isClickable = true
                updateBinding.progressBarUpdate.visibility = View.INVISIBLE
                finish()
            }
        }
    }

    fun getAndSetData(){

        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age",0).toString()
        val email = intent.getStringExtra("email")
        val imageUrl = intent.getStringExtra("imageUrl").toString()

        updateBinding.editTxtUpdateName.setText(name)
        updateBinding.editTxtUpdateAge.setText(age)
        updateBinding.editTxtUpdateEmail.setText(email)
        Picasso.get().load(imageUrl).into(updateBinding.userProfileImage)




    }

    fun uploadPhoto(){

        updateBinding.btnUpdateUser.isClickable = false
        updateBinding.progressBarUpdate.visibility = View.VISIBLE

        //UUID

        val imageName = intent.getStringExtra("imageName").toString()

        val imageReference = storageReference.child("images").child(imageName)

        imageUri?.let { uri ->

            imageReference.putFile(uri).addOnSuccessListener {

                Toast.makeText(applicationContext,"Image updated",Toast.LENGTH_SHORT).show()

                //downloadable url

                val myUploadedImageReference = storageReference.child("images").child(imageName)

                myUploadedImageReference.downloadUrl.addOnSuccessListener { url ->

                    val imageURL = url.toString()

                    updateData(imageURL,imageName)

                }

            }.addOnFailureListener {

                Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_SHORT).show()

            }

        }

    }
}