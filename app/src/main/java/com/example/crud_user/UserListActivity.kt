package com.example.crud_user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class UserListActivity : AppCompatActivity() {

    private lateinit var lvUsers: ListView
    private lateinit var btnAddUser: Button
    private val userList = mutableListOf<User>()
    private lateinit var adapter: UserAdapter
    private lateinit var addUserLauncher: ActivityResultLauncher<Intent>

    private lateinit var editUserLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list)

        // Kết nối view
        lvUsers = findViewById(R.id.lvUsers)
        btnAddUser = findViewById(R.id.btnAddUser)

        // Tạo dữ liệu mẫu
        userList.add(User("Nguyễn Văn A", "nvana", "example@gmail.com", "0123456789", "12345", R.drawable.ic_launcher_foreground))
        userList.add(User("Trần Thị B", "ttb", "email@example.com", "0987654321", "67890", R.drawable.ic_launcher_foreground))

        // Cấu hình adapter với callback chỉnh sửa và xóa
        adapter = UserAdapter(
            context = this,
            userList = userList,
            onEditClick = { position -> // Callback chỉnh sửa
                val intent = Intent(this, EditUserActivity::class.java)
                intent.putExtra("user", userList[position])
                intent.putExtra("position", position)
                editUserLauncher.launch(intent)
            },
            onDeleteClick = { position -> // Callback xóa
                userList.removeAt(position)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Đã xóa người dùng", Toast.LENGTH_SHORT).show()
            }
        )
        lvUsers.adapter = adapter

        // Khởi tạo launcher cho màn hình chỉnh sửa người dùng
        editUserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val position = data.getIntExtra("position", -1)
                    if (position != -1) {
                        val name = data.getStringExtra("name") ?: ""
                        val username = data.getStringExtra("username") ?: ""
                        val phone = data.getStringExtra("phone") ?: ""
                        val email = data.getStringExtra("email") ?: ""

                        val updatedUser = userList[position].copy(
                            name = name,
                            username = username,
                            email = email,
                            phone = phone
                        )
                        userList[position] = updatedUser
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }

        // Sự kiện nút thêm người dùng
        btnAddUser.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }

        addUserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val name = data.getStringExtra("name") ?: ""
                    val username = data.getStringExtra("username") ?: ""
                    val email = data.getStringExtra("email") ?: ""
                    val phone = data.getStringExtra("phone") ?: ""
                    val password = data.getStringExtra("password") ?: ""

                    userList.add(User(name, username, email, phone, password, R.drawable.ic_launcher_foreground))
                    adapter.notifyDataSetChanged()
                }
            }
        }

        btnAddUser.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            addUserLauncher.launch(intent)
        }
    }
}
