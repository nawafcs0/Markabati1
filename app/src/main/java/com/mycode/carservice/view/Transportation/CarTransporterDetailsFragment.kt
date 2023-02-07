package com.mycode.carservice.view.Transportation

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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mycode.carservice.R
import com.mycode.carservice.model.CarTransporter
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "transporter"


class CarTransporterDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var param1: CarTransporter

    private lateinit var fab: FloatingActionButton
    private lateinit var transporterTitleTv: TextView
    private lateinit var transporterRateTv: TextView
    private lateinit var transporterLocationTv: TextView
    private lateinit var transporterPriceTv: TextView
    private lateinit var transporterImage: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as CarTransporter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_transporter_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Toast.makeText(activity, "this is the id $param1", Toast.LENGTH_SHORT).show()

        fab=view.findViewById(R.id.fab_addSpare)
        transporterTitleTv = view.findViewById(R.id.spareDetails_title)
        transporterRateTv = view.findViewById(R.id.carTransporterDetails_rating)
        transporterLocationTv = view.findViewById(R.id.spareDetails_location)
        transporterPriceTv = view.findViewById(R.id.spareDetails_price)
        transporterImage=view.findViewById(R.id.img_car_transporter_details)

        transporterTitleTv.text = param1.name
        transporterRateTv.text = param1.rate.toString()
        transporterLocationTv.text = param1.city
        transporterPriceTv.text = param1.price
        if (param1.img.isNotEmpty())
            Picasso.get().load(param1.img).placeholder(R.drawable.car_trasnporter_ic).resize(1100,700).into(transporterImage)

        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + param1.transporterPhoneNumber))
            startActivity(intent)
        })

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            CarTransporterDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)

                }
            }
    }
}