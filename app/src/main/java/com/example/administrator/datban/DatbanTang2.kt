package com.example.administrator.datban

import android.content.Context
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
import kotlinx.android.synthetic.main.datban_tang2.*


class DatbanTang2: Fragment() {

    private lateinit var fragListener: FragListener
    private var xemTTBan = XemTTBan.newInst()
//    private var nhapTTKH = NhapTTKH.newInst()

    companion object {
        fun newInstance(ngay: String, gio: String):DatbanTang2{
            val t2Ins = DatbanTang2()
            val arg = Bundle()
            arg.putString("ngay", ngay)
            arg.putString("gio", gio)
            t2Ins.arguments = arg
            return t2Ins
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
        val view = inflater.inflate(R.layout.datban_tang2, container, false)

        onAttachFragment(this)

        val ngay = arguments!!.getString("ngay")
        val gio = arguments!!.getString("gio")

        val khList = mutableListOf<Thongtin>() // Item cua 1 khach

        val btnto1st = view?.findViewById(R.id.btnto1st) as Button
        btnto1st.setOnClickListener{btnto1stClicked(ngay, gio)}

        val ban11 = view.findViewById(R.id.b11) as Button

        val ban12 = view.findViewById(R.id.b12) as Button

        val ban13 = view.findViewById(R.id.b13) as Button

        val ban14 = view.findViewById(R.id.b14) as Button

        val ban15 = view.findViewById(R.id.b15) as Button

        val ban16 = view.findViewById(R.id.b16) as Button

        val ban17 = view.findViewById(R.id.b17) as Button

        val ban18 = view.findViewById(R.id.b18) as Button

        val ban19 = view.findViewById(R.id.b19) as Button

        val ban20 = view.findViewById(R.id.b20) as Button

        val btnArray = arrayListOf(ban11, ban12, ban13, ban14, ban15, ban16, ban17, ban18, ban19, ban20)
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
//            changingIcon(ban, x+1, ngay, gio)
                        for (pdb in khList){
//                Toast.makeText(context, pdb.Mban, Toast.LENGTH_SHORT).show()
//                Log.e("mban", pdb.Mban)
                            if(pdb.Mban == (x+1).toString()){
                                ban.background = ContextCompat.getDrawable(context!!, R.mipmap.ban_booked)
//                                Toast.makeText(context, " hulloo",Toast.LENGTH_SHORT).show()
//                                Log.e("himan", "hihi")
                            }
                        }

                        ban.setOnClickListener{
                            tblChosen(ban.id, x+11, ngay, gio)
                        }
                    }
                }
            }
        })


        return view
    }

    private fun tblChosen(id: Int, k: Int, ngay: String, gio: String) {
//        Toast.makeText(context, id.toString(), Toast.LENGTH_SHORT).show()
//        Toast.makeText(context, "aaaaaaaaaaaaa", Toast.LENGTH_SHORT).show()
        val popup = PopupMenu(activity, view?.findViewById(id))
        popup.inflate(R.menu.popup)
        popup.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.thongtinban -> fragListener.changeFragment(xemTTBan)
                R.id.datban -> {
                    val nhapTTKH = NhapTTKH.newInst(k, ngay, gio)
                    fragListener.changeFragment(nhapTTKH)
                }
                else -> popupconfirm2()
            }
            true
        }
        popup.show()
    }

    private fun popupconfirm2() {
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
        window.showAtLocation(b14, Gravity.CENTER, 0, 0)
    }

    private fun btnto1stClicked(ngay: String, gio: String) {
        val dbTang1 = DatbanTang1.newInstance(ngay, gio)
        fragListener.changeFragment(dbTang1)
    }

}