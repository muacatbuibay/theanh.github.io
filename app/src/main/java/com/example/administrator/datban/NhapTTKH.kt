package com.example.administrator.datban

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class NhapTTKH:Fragment() {
    lateinit var txtMaban: TextView
    lateinit var txtTen:EditText
    lateinit var txtSDT:EditText
    lateinit var txtDate:Spinner
    lateinit var txtTime:Spinner
    lateinit var buttonBook:Button
//    lateinit var btnHuy: Button
//    lateinit var tg: String



    companion object {
        fun newInst(k: Int, ngay: String, gio: String): NhapTTKH {
            val frag = NhapTTKH()
            val args = Bundle()
            args.putInt("maban",k)
            args.putString("ngay", ngay)
            args.putString("gio", gio)
            frag.arguments = args
            return frag
        }
    }

//    var gio = arrayOf("Sáng","Trưa","Tối")
//
//
//    val cal = Calendar.getInstance()
//    val listNgay = mutableListOf<String>()
//    val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
//    lateinit var currDate: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.nhap_thong_tin_kh, container, false)

        txtMaban = view.findViewById(R.id.txtMaban)
//        txtMaban.addTextChangedListener(object : TextWatcher{
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//        })

        val mb = arguments!!.getInt("maban")
        val ngay = arguments!!.getString("ngay")
        val tg = arguments!!.getString("gio")

        txtMaban.text = mb.toString()

        txtTen = view.findViewById(R.id.txtTen)
        txtSDT = view.findViewById(R.id.txtSDT)
//        txtDate = view.findViewById(R.id.txtDate)
//        txtTime = view.findViewById(R.id.txtTime)
        buttonBook = view.findViewById(R.id.buttonBook)
        buttonBook.setOnClickListener {
            saveThongtin(ngay, tg)
//            TODO("so sanh thong tin nhap voi database de xac dinh xem co trung khong")
        }
//        btnHuy.setOnClickListener{
//            TODO("tro lai layout truoc, xoa het du lieu da nhap")
//        }

//        txtTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                Toast.makeText(activity, "thoi gian duoc chon: "+ txtTime.selectedItem.toString(), Toast.LENGTH_SHORT).show()
//            }
//
//        }
//        val aa = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, gio)
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        txtTime.adapter = aa
//
//
//        for(i in 0..6){
//            currDate = sdf.format(cal.time)
//            cal.add(Calendar.DATE, 1)
//            listNgay.add(currDate)
//        }
//        val adapterNgay = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, listNgay)
//        adapterNgay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        txtDate.adapter = adapterNgay


        return view
    }



    private fun saveThongtin(ngay: String, tg: String) {

        val Mban = txtMaban.text.toString().trim()
        if (Mban.isEmpty()){
            txtMaban.error = "Nhập mã bàn"
            return
        }
        val Ten = txtTen.text.toString().trim()
        if (Ten.isEmpty()){
            txtTen.error = "Nhập họ tên khách hàng"
            return
        }
        val SDT = txtSDT.text.toString().trim()
        if (SDT.isEmpty()){
            txtSDT.error = "Nhập số điện thoại"
            return
        }
        val Date = ngay.trim()
        if (Date.isEmpty()){
            return
        }
        val Time = tg.trim()
        if (Time.isEmpty()){
            return
        }
        //val kh = Thongtin(Khid, Ten, SDT, Date, Time)
        val ref = FirebaseDatabase.getInstance().getReference("TTKhachHang")
        val khId = ref.push().key
        val kh = Thongtin(khId, Ten, SDT, Date, Time, Mban)
        ref.child(khId).setValue(kh).addOnCompleteListener {
            Toast.makeText(activity, "Đặt bàn thành công", Toast.LENGTH_LONG).show()
        }

    }
//    fun nhanid(k: String){
////        Toast.makeText(activity,"hi",Toast.LENGTH_SHORT).show()
////        val cucu = view?.findViewById<TextView>(R.id.txtMaban)
//        this.txtMaban.text = k
//    }
}