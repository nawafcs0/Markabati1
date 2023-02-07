package com.mycode.carservice.view

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask


class FirebaseStorageManager {

    private var storageRef=FirebaseStorage.getInstance().reference

    fun uploadWorkshopImg(context: Context,imageUri: Uri){

        val uploadTask=storageRef.child("workshop/Image${System.currentTimeMillis()}").putFile(imageUri)
            .addOnSuccessListener {
            storageRef.downloadUrl

        }

        uploadTask.addOnSuccessListener {

            Log.d("Firebase Storage","Added successfully")
        }.addOnFailureListener {
            Log.d("Firebase Storage","Failed !")
        }
    }

    fun downloadWorkshopImage(context: Context){

    }

    fun uploadTransporterImg(){



    }
    fun uploadSparePartsImg(){

    }



}