package com.example.dolist.fragments

import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.dolist.R


class AboutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_about, container, false)

        val github: ImageView = view.findViewById(R.id.imageViewGithub)
        val facebook: ImageView = view.findViewById(R.id.imageViewFacebook)
        val twitter: ImageView = view.findViewById(R.id.imageViewTwitter)
        val insta: ImageView = view.findViewById(R.id.imageViewInsta)

        val profile: ImageView = view.findViewById(R.id.imageViewProfile)

        val matrix = ColorMatrix()
        matrix.setSaturation(0f)

        val filter = ColorMatrixColorFilter(matrix)
        profile.colorFilter = filter

        facebook.setOnClickListener{
            openFacebook(view)
        }

        github.setOnClickListener{
            openGit(view)
        }

        twitter.setOnClickListener{
            openTwitter(view)
        }

        insta.setOnClickListener{
            openInsta(view)
        }

        return view
    }




    fun openFacebook(view: View?) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Rahulch295/")))
    }

    fun openGit(view: View?) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/RahulRazj")))
    }

    fun openInsta(view: View?) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.instagram.com/rahul_razj/")
            )
        )
    }

    fun openTwitter(view: View?) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/RahulCh93424063")))
    }

}