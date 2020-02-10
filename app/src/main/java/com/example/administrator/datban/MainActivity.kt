package com.example.administrator.datban

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.widget.Toast


class MainActivity : FragmentActivity(), FragListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            /*supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, MainMenu.newInstance())
                    .commit()*/
            val mm = MainMenu.newInstance()
            changeFragment(mm)
        }



    }

    override fun changeFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()

    }

//    override fun changeFragmentWnmb(k: String) {
//
////        Toast.makeText(this, k.toString(), Toast.LENGTH_SHORT).show()
//
//        val nhapTTKH = NhapTTKH.newInst()
//
//        supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.fragmentContainer, nhapTTKH)
//                .addToBackStack(null)
//                .commit()
////        nhapTTKH.nhanid(k)
//    }
}
