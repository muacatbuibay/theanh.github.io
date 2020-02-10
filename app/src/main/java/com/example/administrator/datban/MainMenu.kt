package com.example.administrator.datban

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class MainMenu: Fragment() {
    private lateinit var fragListener:FragListener
    private var dbCS = DatbanCS.newIns()
    private var dsDatban = DanhsachDatban.newInstance()

    companion object {
        fun newInstance():MainMenu{
            return MainMenu()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is FragListener){
            fragListener = context
        }
        else{
            throw ClassCastException(context.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.main_menu, container, false)

        val btn1: Button? = view?.findViewById(R.id.datban)
        val btn2: Button? = view?.findViewById(R.id.danhsach)

        btn1?.setOnClickListener { btn1Clicked() }
        btn2?.setOnClickListener {btn2Clicked() }

        return view
    }

    private fun btn1Clicked() {
        fragListener.changeFragment(dbCS)

    }

    private fun btn2Clicked() {
        fragListener.changeFragment(dsDatban)
    }

}