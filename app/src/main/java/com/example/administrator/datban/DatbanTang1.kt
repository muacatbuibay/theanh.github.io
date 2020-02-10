package com.example.administrator.datban

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DatbanTang1: Fragment() {

    private lateinit var fragListener: FragListener
    private var xemTTBan = XemTTBan.newInst()


    companion object {
        fun newInstance(ngay: String, gio: String):DatbanTang1{
            val t1Ins = DatbanTang1()
            val arg = Bundle()
            arg.putString("ngay", ngay)
            arg.putString("gio", gio)
            t1Ins.arguments = arg
            return t1Ins
        }
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        val parrent = childFragment!!.parentFragment
        if(parrent is FragListener){
            fragListener = parrent
        }
        else{
            throw ClassCastException(childFragment.toString()+ "shit happened")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.datban_tang1, container, false)
        onAttachFragment(this)

        val ngay = arguments!!.getString("ngay")
        val gio = arguments!!.getString("gio")

        val khList = mutableListOf<Thongtin>() // Item cua 1 khach
        //Database


//        val cucu = khList[0]


//        Toast.makeText(context, cucu.Mban, Toast.LENGTH_SHORT).show()

        val btnto2nd = view?.findViewById(R.id.btnto2nd) as Button
        btnto2nd.setOnClickListener{btnto2ndClicked(ngay, gio)}

        val ban1 = view.findViewById(R.id.b1) as Button

        val ban2 = view.findViewById(R.id.b2) as Button

        val ban3 = view.findViewById(R.id.b3) as Button

        val ban4 = view.findViewById(R.id.b4) as Button

        val ban5 = view.findViewById(R.id.b5) as Button

        val ban6 = view.findViewById(R.id.b6) as Button

        val ban7 = view.findViewById(R.id.b7) as Button

        val ban8 = view.findViewById(R.id.b8) as Button

        val ban9 = view.findViewById(R.id.b9) as Button

        val ban10 = view.findViewById(R.id.b10) as Button

        val btnArray = arrayListOf(ban1, ban2, ban3, ban4, ban5, ban6, ban7, ban8, ban9, ban10)

        val ref = FirebaseDatabase.getInstance().getReference("TTKhachHang")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            //Data base
            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()) {
                    for (h in p0.children) {
                        val kh = h.getValue(Thongtin::class.java)
                        if (kh!!.Date == ngay && kh.Time == gio) {
                            khList.add(kh)
                            Log.e("OOP", kh.Ten)
                        }
                    }
                    for((x, ban) in btnArray.withIndex()){
                        ban.background = ContextCompat.getDrawable(context!!, R.mipmap.ban)
                        for (pdb in khList){
                            if(pdb.Mban == (x+1).toString()){
                                ban.background = ContextCompat.getDrawable(context!!, R.mipmap.ban_booked)
                            }
                        }

                        ban.setOnClickListener{
                            tblChosen(ban.id, x+1, ngay, gio)
                        }
                    }
                }
            }
        })



        return view
    }

//    private fun changingIcon(ban: Button, mb: Int, ngay: String?, gio: String?) {
////        TODO("change tables' icons here")
//        if()
//
//    }

    private fun tblChosen(id: Int, mb: Int, ngay: String, gio: String) {
        val popup = PopupMenu(activity, view?.findViewById(id))
        popup.inflate(R.menu.popup)
        popup.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.thongtinban -> fragListener.changeFragment(xemTTBan)
                R.id.datban -> {
                    val nhapTTKH = NhapTTKH.newInst(mb, ngay, gio)
                    fragListener.changeFragment(nhapTTKH)
                }
                else -> popupconfirm()
            }
            true
        }
        popup.show()
    }

    fun popupconfirm() {
            val window = PopupWindow(activity)
            val view = layoutInflater.inflate(R.layout.window, null)
            window.contentView = view
            window.width = 900
            window.height = 1000
            window.isFocusable = true

            val confirmbtn = view.findViewById<Button>(R.id.confirmbtn)
            confirmbtn.setOnClickListener{
                window.dismiss()
                Toast.makeText(activity, "Phiên đặt bàn đã được hủy.", Toast.LENGTH_SHORT).show()
            }
            window.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    fun btnto2ndClicked(ngay: String, gio: String) {
        val dbTang2 = DatbanTang2.newInstance(ngay, gio)
        fragListener.changeFragment(dbTang2)
    }
}


