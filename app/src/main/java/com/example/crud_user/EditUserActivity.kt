package com.example.crud_user

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class EditUserActivity : AppCompatActivity() {

    private lateinit var ivBackToUserList: ImageView
    private lateinit var ivAvatar: ImageView
    private lateinit var etName: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnEditPassword: Button
    private lateinit var btnSave: Button

    private var user: User? = null // Khai báo biến user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        // Kết nối view
        ivBackToUserList = findViewById(R.id.ivBackToUserList)
        ivAvatar = findViewById(R.id.ivAvatar)
        etName = findViewById(R.id.etName)
        etUsername = findViewById(R.id.etUsername)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        btnEditPassword = findViewById(R.id.btnEditPassword)
        btnSave = findViewById(R.id.btnSave)

        // Nhận dữ liệu từ Intent
        user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("user", User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("user")
        }
        val position = intent.getIntExtra("position", -1)

        // Điền thông tin người dùng vào các ô
        user?.let {
            etName.setText(it.name)
            etUsername.setText(it.username)
            etPhone.setText(it.phone)
            etEmail.setText(it.email)
        }

        // Nút quay lại
        ivBackToUserList.setOnClickListener {
            finish()
        }

        // Nút sửa mật khẩu
        btnEditPassword.setOnClickListener {
            showChangePasswordDialog()
        }

        // Nút lưu thay đổi
        btnSave.setOnClickListener {
            val updatedName = etName.text.toString()
            val updatedUsername = etUsername.text.toString()
            val updatedPhone = etPhone.text.toString()
            val updatedEmail = etEmail.text.toString()

            if (updatedName.isNotEmpty() && updatedUsername.isNotEmpty() && updatedPhone.isNotEmpty() && updatedEmail.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("position", position)
                resultIntent.putExtra("name", updatedName)
                resultIntent.putExtra("username", updatedUsername)
                resultIntent.putExtra("phone", updatedPhone)
                resultIntent.putExtra("email", updatedEmail)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showChangePasswordDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_change_password, null)
        val etOldPassword = dialogView.findViewById<EditText>(R.id.etOldPassword)
        val etNewPassword = dialogView.findViewById<EditText>(R.id.etNewPassword)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            val oldPassword = etOldPassword.text.toString()
            val newPassword = etNewPassword.text.toString()

            if (user?.password == oldPassword) { // So sánh với mật khẩu cũ
                user?.password = newPassword
                Toast.makeText(this, "Mật khẩu đã được thay đổi!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Mật khẩu cũ không chính xác!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}
