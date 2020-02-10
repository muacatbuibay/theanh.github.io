package com.example.administrator.datban

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.datban_cs.*
import java.text.SimpleDateFormat
import java.util.*

class DatbanCS: Fragment(), FragListener {
    override fun changeFragment(fragment: Fragment) {
        childFragmentManager
                .beginTransaction()
                .replace(R.id.childContainer, fragment)
                .addToBackStack(null)
                .commit()
    }

    companion object {
        fun newIns(): DatbanCS{
            return DatbanCS()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.datban_cs, container, false)


        val dateList = mutableListOf<String>()
        dateList.clear()
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        var currentDate = ""

        val spinnerNgay = view.findViewById<Spinner>(R.id.spinner_ngay)

        val spinnerTg = view.findViewById<Spinner>(R.id.spinner_tg)

        for(i in 0..6){
            currentDate = sdf.format(calendar.time)
            dateList.add(currentDate)
            calendar.add(Calendar.DATE, 1)
        }

        val adapterNgay = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, dateList)
        adapterNgay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNgay.adapter = adapterNgay
        var ngayChon = spinnerNgay.selectedItem.toString()
        spinnerNgay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                ngayChon = dateList[0]
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ngayChon = spinnerNgay.selectedItem.toString()
                val tang1 = DatbanTang1.newInstance(ngayChon, "Sáng")
                changeFragment(tang1)
            }

        }


        val listGio = arrayOf("Sáng","Trưa","Tối")

        val aa = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, listGio)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTg.adapter = aa
        var tgChon: String
        spinnerTg.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tgChon = spinnerTg.selectedItem.toString()
                val tang1 = DatbanTang1.newInstance(ngayChon, tgChon)
                changeFragment(tang1)


            }

        }

//        val tang1 = DatbanTang1.newInstance(ngayChon, tgChon)
//        changeFragment(tang1)



        return view
    }
}