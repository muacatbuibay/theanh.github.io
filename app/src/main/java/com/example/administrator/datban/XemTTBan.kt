package com.example.administrator.datban

import android.nfc.Tag
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.xem_tt_ban.*

class XemTTBan: Fragment() {

    companion object {
        fun newInst(): XemTTBan{
            return XemTTBan()
        }
    }

    /*  init {


      }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.xem_tt_ban, container, false)
        val database = FirebaseDatabase.getInstance().getReference()
        /*database.child("Tang1").child("Ban1").addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {

                var dv :String = p0?.getValue().toString()
                textDichvu.append(dv+"\n")

            }

                /*var dv : String = p0?.getValue().toString()
                textDichvu.append(dv)
                //textGhichu.append()*/




            override fun onChildRemoved(p0: DataSnapshot?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })*/
        database.child("Tang1").child("Ban1").child("Dichvu").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val dv : String = p0?.getValue().toString()
                textDichvu.append(dv)
            }
        })
        database.child("Tang1").child("Ban1").child("Gia").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val gia : String = p0?.getValue().toString()
                textGia.append(gia)
            }
        })
        database.child("Tang1").child("Ban1").child("Socho").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val socho : String = p0?.getValue().toString()
                textSocho.append(socho)
            }
        })
        database.child("Tang1").child("Ban1").child("Ghichu").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val ghichu : String = p0?.getValue().toString()
                textGhichu.append(ghichu)
            }
        })

        return view

    }
}



