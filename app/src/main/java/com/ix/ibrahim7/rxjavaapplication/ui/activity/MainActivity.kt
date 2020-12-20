package com.ix.ibrahim7.rxjavaapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ix.ibrahim7.rxjavaapplication.databinding.ActivityMainBinding
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.HomeViewModel
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.activity_main.*
import util.Constant.setUpStatusBar

class MainActivity : AppCompatActivity() {



    lateinit var mbinding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        setUpStatusBar(this,0)
        setSupportActionBar(toolbar)
        toolbar.visibility=View.GONE


    }


}