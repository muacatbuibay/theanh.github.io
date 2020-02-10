package com.example.administrator.datban

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.database.DataSetObserver
import android.support.v4.app.FragmentManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class DatBanAdapter(val mCtx:Context, val layoutResId:Int, val tableList: MutableList<Thongtin>) : ListAdapter {

    override fun isEmpty(): Boolean {
        return false
    }
    override fun registerDataSetObserver(observer: DataSetObserver?) {
        return
    }
    override fun getItemViewType(position: Int): Int {
        return 0
    }
    override fun getItem(position: Int): Any {
        return  tableList[position]
    }
    override fun getViewTypeCount(): Int {
        return 1
    }
    override fun isEnabled(position: Int): Boolean {
        return true
    }
    override fun getItemId(position: Int): Long {
        return  position.toLong()
    }
    override fun hasStableIds(): Boolean {
        return true
    }
    override fun areAllItemsEnabled(): Boolean {
        return true
    }
    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        return
    }
    override fun getCount(): Int {
        return tableList.size
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view = layoutInflater.inflate(layoutResId, null)
        val textViewBan = view.findViewById<TextView>(R.id.textViewBan) // text view
        val textViewSDT = view.findViewById<TextView>(R.id.textViewSDT) // text view
        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        textViewBan.text=tableList[position].Mban //Gan thong tin
        textViewSDT.text=tableList[position].SDT // Gan thong tin
        textViewName.text=tableList[position].Ten

        val phiendat = tableList[position]

        view.setOnClickListener{
            zepopup(phiendat, view)
        }
        return view
    }

    private fun zepopup(phiendat: Thongtin, convertView: View) {
        val popup = PopupMenu(mCtx, convertView)
        popup.inflate(R.menu.popup_dsdb)
        popup.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.sua_item -> {
                    zeSuaTTDialog(phiendat)
                }
                else -> {
                    xoaTTKH(phiendat)
                }
            }
            true
        }
        popup.show()
    }

    private fun xoaTTKH(phiendat: Thongtin) {
        val window = PopupWindow(mCtx)
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.window, null)
        window.contentView = view
        window.width = 900
        window.height = 1000
        window.isFocusable = true

        val confirmbtn = view.findViewById<Button>(R.id.confirmbtn)
        confirmbtn.setOnClickListener{
            val database = FirebaseDatabase.getInstance().getReference("TTKhachHang")
            database.child(phiendat.id).removeValue()
            window.dismiss()


            Toast.makeText(mCtx, "Phiên đặt bàn đã được hủy.", Toast.LENGTH_SHORT).show()
        }
        window.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    private fun zeSuaTTDialog(phiendat: Thongtin) {

        val builer = AlertDialog.Builder(mCtx)

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.sua_thong_tin_kh, null)

        val maban = view.findViewById<EditText>(R.id.txtMaban)
        val tenKH = view.findViewById<EditText>(R.id.txtTen)
        val soDT = view.findViewById<EditText>(R.id.txtSDT)
        val ngay = view.findViewById<Spinner>(R.id.txtDate)
        val gio = view.findViewById<Spinner>(R.id.txtTime)

        maban.setText(phiendat.Mban)
        tenKH.setText(phiendat.Ten)
        soDT.setText(phiendat.SDT)


        val cal = Calendar.getInstance()
        val listNgay = mutableListOf(phiendat.Date)
        val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())

        loop@for( i in 0..6){
            val currDate = sdf.format(cal.time)
            cal.add(Calendar.DATE, 1)
            if (currDate == phiendat.Date){
                continue@loop
            }
            listNgay.add(currDate)
        }

        val adapterNgay = ArrayAdapter(mCtx, android.R.layout.simple_spinner_dropdown_item, listNgay)
        adapterNgay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ngay.adapter = adapterNgay



        val listGio = arrayOf("Sáng","Trưa","Tối")

        for (i in 1..2){
            if (listGio[i] == phiendat.Time){
                val tg = listGio[0]
                listGio[0] = listGio[i]
                listGio[i] = tg
            }
        }

        val aa = ArrayAdapter(mCtx, android.R.layout.simple_spinner_dropdown_item, listGio)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gio.adapter = aa

        builer.setView(view)

        builer.setPositiveButton("Lưu", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                val database = FirebaseDatabase.getInstance().getReference("TTKhachHang")

                val maBan = maban.text.toString().trim()
                if (maBan.isEmpty()){
                    maban.error = "Nhập mã bàn!"
                    maban.requestFocus()
                    return
                }

                val tenKh = tenKH.text.toString().trim()
                if (tenKh.isEmpty()){
                    tenKH.error = "Nhập tên khách hàng!"
                    tenKH.requestFocus()
                    return
                }

                val soDt = soDT.text.toString().trim()
                if (soDt.isEmpty()){
                    soDT.error = "Nhập mã bàn!"
                    soDT.requestFocus()
                    return
                }

                val ngaY = ngay.selectedItem.toString().trim()

                val giO = gio.selectedItem.toString().trim()

                val phienDB = Thongtin(phiendat.id, tenKh, soDt, ngaY, giO, maBan)

                database.child(phienDB.id).setValue(phienDB)

                Toast.makeText(mCtx, "Chỉnh sửa thành công.", Toast.LENGTH_SHORT).show()
            }

        })

        builer.setNegativeButton("Hủy", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }

        })

        val alertDialog = builer.create()
        alertDialog.show()

    }


}