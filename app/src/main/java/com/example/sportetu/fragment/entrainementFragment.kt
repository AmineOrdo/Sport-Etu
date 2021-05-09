package com.example.sportetu.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sportetu.R
import com.example.sportetu.activite.programmationActivity
import com.example.sportetu.activite.consultationActivity
import kotlinx.android.synthetic.main.fragment_entrainement.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EntrainementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EntrainementFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_entrainement, container, false)

        //Accéder à l'activité pour planifier une activité
         mView.PlanifierView.setOnClickListener(View.OnClickListener {

             val intent = Intent(activity, programmationActivity::class.java)
             startActivity(intent)

         })
        //Accéder à l'activité pour consultation une activité

        mView.ModifierView.setOnClickListener(View.OnClickListener {

            val intent = Intent(activity, consultationActivity::class.java)
            startActivity(intent)
        })
        return mView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EntrainementFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EntrainementFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}