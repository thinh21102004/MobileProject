package com.example.crud_user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AddUserActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etPassword: EditText
    private lateinit var ivAvatar: ImageView
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        // Kết nối view
        etName = findViewById(R.id.etName)
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        etPassword = findViewById(R.id.etPassword)
        ivAvatar = findViewById(R.id.ivAvatar)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        // Sự kiện nút lưu
        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()
            val password = etPassword.text.toString()

            if (name.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("name", name)
                resultIntent.putExtra("username", username)
                resultIntent.putExtra("email", email)
                resultIntent.putExtra("phone", phone)
                resultIntent.putExtra("password", password)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                // Hiển thị thông báo nếu thông tin không đầy đủ
                etName.error = "Vui lòng nhập đầy đủ thông tin"
            }
        }

        // Sự kiện nút hủy
        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
