package com.example.mila.nycschools.view

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.mila.nycschools.model.NYCSchools
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by mila on 3/20/18.
 */
class SchoolAdapter : RecyclerView.Adapter<SchoolVH>() {

    private val clickSubject = PublishSubject.create<NYCSchools>()
    private var schools: ArrayList<NYCSchools> = arrayListOf()
    val clickEvent: Observable<NYCSchools> = clickSubject

    fun setSchools(schoolList: Collection<NYCSchools>) {
        schools.clear()
        schools.addAll(schoolList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SchoolVH{
        return SchoolVH(parent)
    }

    override fun getItemCount(): Int = schools.size

    override fun onBindViewHolder(holder: SchoolVH?, position: Int) {
        //passing onSchoolSelected function reference to handle school item onClick
        holder?.bindSchools(schools.get(position), this::onSchoolSelected)
    }

    //function being called when school list item clicked, it's passing clicked school object to publish subject,
    // so that subscribed observers (in our case it is Presenter) can observe click event ans get school object
    private fun onSchoolSelected(school: NYCSchools) {
        clickSubject.onNext(school)
    }

}