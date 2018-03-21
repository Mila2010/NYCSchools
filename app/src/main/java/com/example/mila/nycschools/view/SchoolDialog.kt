package com.example.mila.nycschools.view

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mila.nycschools.R
import com.example.mila.nycschools.model.Constants
import com.example.mila.nycschools.model.NYCSchools
import kotlinx.android.synthetic.main.fragment_school_dialog.view.*

/**
 * Created by mila on 3/20/18.
 */
class SchoolDialog : DialogFragment() {


    companion object {
        //companion object is being used to declare static function in Kotlin, newInstance creating new instance of SchoolDialog
        //JvmStatic is used to allow Java class to see method in companion object as static
        @JvmStatic
        fun newInstance(school: NYCSchools): SchoolDialog {
            val dialog = SchoolDialog()
            val args = Bundle()
            args.putParcelable(Constants.SCHOOL_TO_SHOW, school)
            dialog.arguments = args
            return dialog
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val schoolView = inflater.inflate(R.layout.fragment_school_dialog, container, false)
        dialog.setCanceledOnTouchOutside(true)
        return schoolView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val school = arguments!!.getParcelable<NYCSchools>(Constants.SCHOOL_TO_SHOW)
        view.school_title.text = school.getmSchools().schoolName
        view.phone_value.text = school.getmSchools().phoneNumber
        view.url.text = school.getmSchools().website
        view.main_study.text = getString(R.string.study_string, school.getmSchools().mainStudy, (if (school.getmSchools().secondStudy != null) school.getmSchools().secondStudy else " "))
        view.reading_sat.text = school.getmSAT().readingScore
        view.writing_sat.text = school.getmSAT().writingScore
        view.math_sat.text = school.getmSAT().mathScore

    }
}