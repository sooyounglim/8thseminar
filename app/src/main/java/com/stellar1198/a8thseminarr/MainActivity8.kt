package com.stellar1198.a8thseminarr

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.stellar1198.a8thseminarr.R.id.main_fb_login_btn
import kotlinx.android.synthetic.main.activity_main8.*
import java.util.*

class MainActivity8 : AppCompatActivity() {

    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main8)
        callbackManager = CallbackManager.Factory.create()

        main_fb_login_btn.setOnClickListener {
            loginWithFacebook()
        }

//        try {
//            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//            }
//        }
//        catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
//        catch (e: NoSuchAlgorithmException) {
//            e.printStackTrace()
//        }
    }


    fun loginWithFacebook(){
        LoginManager.getInstance().logInWithReadPermissions(this@MainActivity8,
                Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val request: GraphRequest
                request = GraphRequest.newMeRequest(loginResult.accessToken) { user, response ->
                    if (response.error != null) {

                    } else {
                        Log.i("TAG", "user: " + user.toString())
                        Log.i("TAG", "AccessToken: " + loginResult.accessToken.token)
                        Log.i("TAG", "AccessToken: " + loginResult.accessToken.userId)

                        setResult(Activity.RESULT_OK)

                    }
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,name,email,gender,birthday")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // super.onActivityResult(requestCode, resultCode, data)
        // 액티비티에서 액티비티로 넘어갈 때 값을 전달한다면
        // 이전 액티비티에서 시그널을 주려고 할 때 쓰는 메소드
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
