package com.example.checkregister

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.checkregister.databinding.ActivityRegisterBinding
import android.widget.Toast
import com.example.checkregister.databinding.ActivityConstrainLayoutBinding
import java.util.Calendar

class ConstrainsRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConstrainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstrainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Đặt sự kiện cho nút Register
        binding.btnRegister.setOnClickListener {
            val inputValidationMessage = validateInput()
            val genderValidationMessage = validateGender()

            if (inputValidationMessage == null) {
                if (binding.ckbAgree.isChecked) {
                    if (genderValidationMessage == null) {
                        // Thành công - Đặt tất cả trường văn bản về giá trị rỗng
                        binding.edtFName.text.clear()
                        binding.edtLName.text.clear()
                        binding.edtBirthday.text = null
                        binding.edtAddress.text.clear()
                        binding.edtEmail.text.clear()

                        // Đặt tất cả RadioButton về trạng thái không chọn
                        binding.rdMale.isChecked = false
                        binding.rdFemale.isChecked = false

                        // Đặt trạng thái CheckBox về không chọn
                        binding.ckbAgree.isChecked = false

                        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                    } else {
                        showErrorMessage("Lỗi giới tính", genderValidationMessage)
                    }
                } else {
                    showErrorMessage("Lỗi điều khoản", "Vui lòng đồng ý với điều khoản sử dụng.")
                }
            } else {
                showErrorMessage("Lỗi đăng ký", inputValidationMessage)
            }
        }


        // Đặt sự kiện cho nút Select (chọn ngày sinh)
        val today = Calendar.getInstance()
        val startYear = today.get(Calendar.YEAR)
        val startMonth = today.get(Calendar.MONTH)
        val startDay = today.get(Calendar.DAY_OF_MONTH)
        binding.btnSelect.setOnClickListener {
            DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    binding.edtBirthday.setText("$dayOfMonth/${month + 1}/$year")
                }, startYear, startMonth, startDay).show()
        }
    }

    // Phương thức kiểm tra thông tin đã nhập và trả về thông báo chi tiết nếu có lỗi
    private fun validateInput(): String? {
        // Lấy ra các giá trị
        val fName = binding.edtFName.text.toString()
        val lName = binding.edtLName.text.toString()
        val birthday = binding.edtBirthday.text.toString()
        val address = binding.edtAddress.text.toString()
        val email = binding.edtEmail.text.toString()

        if (fName.isEmpty()) {
            return "Vui lòng nhập Họ và tên đệm."
        }
        if (lName.isEmpty()) {
            return "Vui lòng nhập Tên."
        }
        if (birthday.isEmpty()) {
            return "Vui lòng nhập Ngày sinh."
        }
        if (address.isEmpty()) {
            return "Vui lòng nhập Địa chỉ."
        }
        if (email.isEmpty()) {
            return "Vui lòng nhập Email."
        }
        return null
    }

    // Phương thức kiểm tra giới tính và trả về thông báo chi tiết nếu có lỗi
    private fun validateGender(): String? {
        if (!binding.rdMale.isChecked && !binding.rdFemale.isChecked) {
            return "Vui lòng chọn giới tính."
        }
        return null
    }

    // Hiển thị thông báo lỗi
    private fun showErrorMessage(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }
}
