package com.ar50645.jokesviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.math.abs


class JokesFragment: Fragment() {
    private var initTouchY = 0
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

        // Moving finger up or down changes jokes
        parentView.setOnTouchListener { v, event ->
            var returnVal = true
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initTouchY = event.y.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val y = event.y.toInt()

                    // See if movement is at least 20 pixels
                    if (abs(y - initTouchY) >= 40) {
                        if (y > initTouchY) {
                            updateJoke()
                        } else {
                            updateJoke()
                        }
                        initTouchY = y
                    }
                }
                else -> returnVal = false
            }
            returnVal
        }

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