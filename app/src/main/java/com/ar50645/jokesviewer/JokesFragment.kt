package com.ar50645.jokesviewer

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlin.math.abs

const val JOKE_STATE = "jokeState"

class JokesFragment: Fragment() {
    private var initTouchY = 0
    private var jokeIndex = 0

    private lateinit var jokeTextView: TextView
    var jokesArray: Array<String> = emptyArray()

    companion object {
        fun newInstance() = JokesFragment()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.context_menu, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val parentView = inflater.inflate(R.layout.fragment_joke, container, false)
        jokeTextView = parentView.findViewById(R.id.jokeTextView)
        jokesArray = getResources().getStringArray((R.array.jokes))

        registerForContextMenu(jokeTextView)

        // Moving finger up or down changes jokes
        parentView.setOnTouchListener { v, event ->
            var returnVal = true
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initTouchY = event.y.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val y = event.y.toInt()

                    // See if movement is at least 500 pixels
                    if (abs(y - initTouchY) >= 500) {
                        if (y > initTouchY) {
                            jokeIndex--
                            updateJoke()
                        } else {
                            jokeIndex++
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(JOKE_STATE, jokeIndex)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            updateJoke()
        } else {
            jokeIndex = savedInstanceState.getInt(JOKE_STATE)
            updateJoke()
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.next -> {
                jokeIndex++
                updateJoke()
                true
            }
            R.id.prev -> {
                jokeIndex--
                updateJoke()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun updateJoke() {
        if(jokeIndex < 0 || jokeIndex >= jokesArray.size) {
            Toast.makeText(activity, "no more jokes to display", Toast.LENGTH_SHORT).show()
            return
        }
        jokeTextView.text = jokesArray.get(jokeIndex)
    }
}