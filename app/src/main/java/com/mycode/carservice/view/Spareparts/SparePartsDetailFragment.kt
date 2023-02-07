package com.mycode.carservice.view.Spareparts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycode.carservice.R
import com.mycode.carservice.model.NewSpareRequest
import com.mycode.carservice.model.SpareParts
import com.mycode.carservice.model.userId
import com.mycode.carservice.view.Transportation.CarTransporterDetailsFragment
import com.squareup.picasso.Picasso
import java.time.LocalDate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "spareParts"



class SparePartsDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var param1: SpareParts

    val db = Firebase.firestore
    private lateinit var fab:FloatingActionButton
    private lateinit var spareTitleTv: TextView
    private lateinit var spareTypeTv: TextView
    private lateinit var spareLocationTv: TextView
    private lateinit var sparePriceTv: TextView
    private lateinit var spareImg: ImageView
    lateinit var sparePartId:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as SpareParts
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spare_part_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Toast.makeText(activity, "this is the id $param1", Toast.LENGTH_SHORT).show()

        fab=view.findViewById(R.id.fab_addSpare)
        spareTitleTv = view.findViewById(R.id.spareDetails_title)
        spareTypeTv = view.findViewById(R.id.spareDetails_type)
        spareLocationTv = view.findViewById(R.id.spareDetails_location)
        sparePriceTv = view.findViewById(R.id.spareDetails_price)
        spareImg = view.findViewById(R.id.img_spareDetails)

        spareTitleTv.text = param1.partName
        spareLocationTv.text = param1.city
        sparePriceTv.text = param1.price
        spareTypeTv.text = param1.type
        sparePartId = param1.partId
        if (param1.img.isNotEmpty())
            Picasso.get().load(param1.img).resize(1100,800).into(spareImg)

        fab.setOnClickListener(View.OnClickListener {
            val newSparePart = NewSpareRequest(sparePartId, userId,LocalDate.now().plusDays(param1.daysToDeliver).toString(),param1.img)
            db.collection("SparePartsRequests")
                .add(newSparePart).addOnSuccessListener {
                    Log.d("TAG", "onViewCreated: ${it.id}")
                    Toast.makeText(requireContext(), "تم الطلب بنجاح!", Toast.LENGTH_SHORT).show()

                }

        })

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            SparePartsDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)

                }
            }
    }
}