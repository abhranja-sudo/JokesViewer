package com.ar50645.jokesviewer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class JokesFragment: Fragment() {
    private var initTouchX = 0
    private var initJokeArrayIndex = 0

    private lateinit var jokeTextView: TextView
    var jokesArray: Array<String> = emptyArray()

    companion object {
        fun newInstance() = JokesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val parentView = inflater.inflate(R.layout.fragment_joke, container, false)
        jokeTextView = parentView.findViewById(R.id.jokeTextView)
        jokesArray = getResources().getStringArray((R.array.jokes))
        return parentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            updateJoke()
        } else {
            //TODO: retrieve the value if thats saved
        }


    }

    private fun updateJoke() {
        jokeTextView.text = jokesArray.get(initJokeArrayIndex++)
    }
}