package com.example.sportetu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_entrainement.*
import kotlinx.android.synthetic.main.fragment_entrainement.PlanifierView
import kotlinx.android.synthetic.main.fragment_entrainement.view.*
import kotlinx.android.synthetic.main.fragment_succes.*
import kotlinx.android.synthetic.main.fragment_succes.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SuccesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SuccesFragment : Fragment() {
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
        mView=inflater.inflate(R.layout.fragment_succes, container, false)

        // modifier objectif
        mView.ModifierObjectifView.setOnClickListener(View.OnClickListener {
            // modifie authentification par le nom de l'activite ou tu veux aller
            val intent = Intent(activity, Authentification::class.java)
            startActivity(intent)

        })
        //fixer un objectif

        mView.ObjectifView.setOnClickListener(View.OnClickListener {
            // modifie authentification par le nom de l'activite ou tu veux aller
            val intent = Intent(activity, Authentification::class.java)
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
         * @return A new instance of fragment SuccesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SuccesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}