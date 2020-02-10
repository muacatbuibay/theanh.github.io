package com.example.administrator.datban

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class DanhsachDatban: Fragment() {

    lateinit var listview: ListView
    lateinit var khList: MutableList<Thongtin>
    lateinit var ref: DatabaseReference
//    lateinit var ngayChon: String
//    lateinit var gioChon: String

    companion object {
        fun newInstance():DanhsachDatban{
            return DanhsachDatban()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.danhsach_datban,container,false)
        val ngay = view.findViewById<Spinner>(R.id.s_chonngay)

        val cal = Calendar.getInstance()
        val listNgay = mutableListOf<String>()
        val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())

        for( i in 0..6){
            val currDate = sdf.format(cal.time)
            cal.add(Calendar.DATE, 1)
            listNgay.add(currDate)
        }

        val adapterNgay = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, listNgay)
        adapterNgay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ngay.adapter = adapterNgay
        var ngayChon = ngay.selectedItem.toString()
        ngay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                    Toast.makeText(context, ngay.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                ngayChon = ngay.selectedItem.toString()
                lapList(ngayChon, "Sáng")

            }
        }

        val gio = view.findViewById<Spinner>(R.id.s_chontg)

        val listGio = arrayOf("Sáng","Trưa","Tối")

        val aa = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, listGio)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gio.adapter = aa
        var gioChon = gio.selectedItem.toString()
        gio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val ngayChon = ngay.selectedItem.toString()
//                val gioChon = gio.selectedItem.toString()
                gioChon = gio.selectedItem.toString()
                lapList(ngayChon, gioChon)
            }
        }
        return view
    }

    private fun lapList(ngayChon: String, gioChon: String) {
        listview = view!!.findViewById(R.id.listView) //Listview
        khList = mutableListOf(Thongtin()) // Item cua 1 khach
        //Database
        ref = FirebaseDatabase.getInstance().getReference("TTKhachHang")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }
            //Data base
            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()){
                    for (h in p0.children){
                        val kh = h.getValue(Thongtin::class.java)
                        if (kh?.Date == ngayChon && kh.Time == gioChon) {
                            khList.add(kh)
                            Log.e("OOP", kh.Ten)
                        }
                    }
//                    khList.removeAt(0)
                    val adapter = DatBanAdapter(context!!,R.layout.booktable,khList) //Noi den adapter qua layout item_datban

                    listview.adapter = adapter

                }
            }

        })
    }
}




//BACKUP

//listview = view!!.findViewById(R.id.listView) //Listview
////listView.adapter= MyCustomAdapter(this)
//khList = mutableListOf(Thongtin()) // Item cua 1 khach
////Database
//ref = FirebaseDatabase.getInstance().getReference("TTKhachHang")
//ref.addValueEventListener(object : ValueEventListener{
//    override fun onCancelled(p0: DatabaseError?) {
//
//    }
//    //Data base
//    override fun onDataChange(p0: DataSnapshot?) {
//        if (p0!!.exists()){
//            for (h in p0.children){
//                val kh = h.getValue(Thongtin::class.java)
//                khList.add(kh!!)
//                Log.e("OOP",kh.Ten)
//            }
//            khList.removeAt(0)
//            val adapter = DatBanAdapter(context!!,R.layout.booktable,khList) //Noi den adapter qua layout item_datban
//            listview.adapter = adapter
//        }
//    }
//
//})


