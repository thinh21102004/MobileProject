package com.example.crud_user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.DrawableRes

class UserAdapter(
    private val context: Context,
    private val userList: List<User>,
    private val onEditClick: (Int) -> Unit, // Callback cho sự kiện chỉnh sửa
    private val onDeleteClick: (Int) -> Unit // Callback cho sự kiện xóa
) : BaseAdapter() {

    override fun getCount(): Int = userList.size

    override fun getItem(position: Int): Any = userList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)

        val user = userList[position]
        val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
        val tvUserName = view.findViewById<TextView>(R.id.tvUserName)
        val tvUserEmail = view.findViewById<TextView>(R.id.tvUserEmail)
        val tvUserPhone = view.findViewById<TextView>(R.id.tvUserPhone)
        val btnEdit = view.findViewById<Button>(R.id.btnEdit)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)

        // Gán dữ liệu người dùng
        ivAvatar.setImageResource(user.avatar)
        tvUserName.text = "Họ tên: ${user.name}"
        tvUserEmail.text = "Email: ${user.email}"
        tvUserPhone.text = "SĐT: ${user.phone}"

        // Gán sự kiện nút sửa
        btnEdit.setOnClickListener {
            onEditClick(position)
        }

        // Gán sự kiện nút xóa
        btnDelete.setOnClickListener {
            onDeleteClick(position)
        }

        return view
    }
}
