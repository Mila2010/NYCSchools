package com.example.mila.nycschools.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mila.nycschools.R
import com.example.mila.nycschools.model.NYCSchools
import kotlinx.android.synthetic.main.vh_school.view.*

/**
 * Created by mila on 3/20/18.
 */
class SchoolVH (parent: ViewGroup?)
    : RecyclerView.ViewHolder(inflateView(parent!!)) {

    private var view: View = itemView

    fun bindSchools(school: NYCSchools, onClickListener: (school: NYCSchools) -> Unit) {
        view.school_name.text = school.getmSchools().schoolName
        view.location.text = school.getmSchools().borough
        view.setOnClickListener { onClickListener.invoke(school) }

    }

    companion object {

        fun inflateView(parent: ViewGroup): View {
            val inflater = LayoutInflater.from(parent.context)
            return inflater.inflate(R.layout.vh_school, parent, false)
        }

    }
}