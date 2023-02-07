package com.mycode.carservice.view.Workshop

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mycode.carservice.R
import com.mycode.carservice.model.WorkShop
import com.squareup.picasso.Picasso
import kotlinx.coroutines.SupervisorJob


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "workShop"

class WorkShopDetailsFragment() : Fragment() {

    lateinit var param1: WorkShop

    private lateinit var fab:FloatingActionButton
    private lateinit var workShopTitleTV:TextView
    private lateinit var workShopRatingTV:TextView
    private lateinit var workShopCityTV:TextView
    private lateinit var endHoursTV:TextView
    private lateinit var startHoursTV:TextView
    private lateinit var endDayTV:TextView
    private lateinit var startDayTV:TextView
    private lateinit var details:TextView
    private lateinit var workshop_img:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as WorkShop
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_shop_detalis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Toast.makeText(activity, "this is the id $param1", Toast.LENGTH_SHORT).show()

        fab=view.findViewById(R.id.fab_callWorkshop)

        workShopTitleTV = view.findViewById(R.id.workshopDetails_title)
        workShopRatingTV = view.findViewById(R.id.workshopDetails_rating)
        workShopCityTV = view.findViewById(R.id.workshopDetails_location)
        endHoursTV = view.findViewById(R.id.tv_endHours)
        startHoursTV = view.findViewById(R.id.tv_startHours)
        endDayTV = view.findViewById(R.id.tv_endDay)
        startDayTV = view.findViewById(R.id.tv_startDay)
        details=view.findViewById(R.id.tv_workshop_details)
        workshop_img=view.findViewById(R.id.img_workshopDetails)

        workShopTitleTV.text = param1.name
        workShopRatingTV.text = param1.rating.toString()
        workShopCityTV.text = param1.city
        endHoursTV.text = param1.closingHours
        startHoursTV.text = param1.openingHours
        endDayTV.text = param1.lastWorkingDay
        startDayTV.text = param1.startWorkingDay
        details.text=param1.detail

        if (param1.img.isNotEmpty())
             Picasso.get().load(param1.img).resize(1100,700).into(workshop_img)


        fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + param1.phoneNumber))
            startActivity(intent)
        }


    }



    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            WorkShopDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}