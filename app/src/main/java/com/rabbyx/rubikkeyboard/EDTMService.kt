package com.rabbyx.rubikkeyboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.util.Log

class EDTMService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private lateinit var keyboardView: KeyboardView
    private lateinit var suggestionBar: LinearLayout
    private lateinit var suggestionText: TextView
    private lateinit var keyboard: Keyboard

    private var isGreekLayout = false
    private var isAlgebraLayout = false
    private var isCalculusLayout = false
    private var isMatrixLayout = false
    private var isVectorLayout = false

    override fun onCreateInputView(): View? {
        // Inflate the keyboard layout and set up the keyboard
        val inputView = layoutInflater.inflate(R.layout.keyboard, null)


        // Get references to the suggestion bar and keyboard view
        suggestionBar = inputView.findViewById(R.id.suggestionBar)
        suggestionText = inputView.findViewById(R.id.suggestionText)


        // Load the default layout (QWERTY)
        keyboard = Keyboard(this, R.xml.qwerty)
        keyboardView = inputView.findViewById(R.id.keyboardView)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)

        return inputView
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val inputConnection = currentInputConnection

        // Handle key press actions
        when (primaryCode) {
            Keyboard.KEYCODE_DONE -> {
                inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
            }
            Keyboard.KEYCODE_DELETE -> {
                inputConnection.deleteSurroundingText(1, 0)
            }
            43 -> inputConnection.commitText("rabby", 1)
            -1 -> {
                // Switch to QWERTY layout
                switchToLayout("qwerty")
            }
            -2 -> {
                // Switch to Greek layout
                switchToLayout("greek")
            }
            -3 -> {
                // Switch to Algebra layout
                switchToLayout("algebra")
            }
            -999 -> {
                // Switch to Calculus layout
                switchToLayout("calculus")
            }
            -1000 -> {
                // Switch to Matrix layout
                switchToLayout("matrix")
            }
            -666 -> {
                // Switch to Vector layout
                switchToLayout("vector")
            }
            else -> {
                // Send the corresponding character for the key pressed
                val character = primaryCode.toChar()
                inputConnection.commitText(character.toString(), 1)
            }
        }

        // Update suggestion bar with text based on input
        updateSuggestionBar("Suggestions for: ${primaryCode.toChar()}")
    }

    // Method to update the suggestion bar visibility and content
    private fun updateSuggestionBar(suggestion: String) {
        suggestionText.text = suggestion
        suggestionBar.visibility = if (suggestion.isNotEmpty()) View.VISIBLE else View.GONE
    }

    // Method to switch layouts
    private fun switchToLayout(layoutType: String) {
        Log.d("EDTMService", "Switching to layout: $layoutType")
        when (layoutType) {
            "qwerty" -> {
                if (isGreekLayout || isAlgebraLayout || isCalculusLayout || isMatrixLayout || isVectorLayout) {
                    resetLayouts()
                    keyboard = Keyboard(this, R.xml.qwerty)
                    keyboardView.keyboard = keyboard
                    Toast.makeText(this, "QWERTY Layout", Toast.LENGTH_SHORT).show()
                }
            }
            "greek" -> {
                if (!isGreekLayout) {
                    resetLayouts()
                    isGreekLayout = true
                    keyboard = Keyboard(this, R.xml.greek)
                    keyboardView.keyboard = keyboard
                    Toast.makeText(this, "Greek Layout", Toast.LENGTH_SHORT).show()
                }
            }
            "algebra" -> {
                if (!isAlgebraLayout) {
                    resetLayouts()
                    isAlgebraLayout = true
                    keyboard = Keyboard(this, R.xml.algebra)
                    keyboardView.keyboard = keyboard
                    Toast.makeText(this, "Algebra Layout", Toast.LENGTH_SHORT).show()
                }
            }
            "calculus" -> {
                if (!isCalculusLayout) {
                    resetLayouts()
                    isCalculusLayout = true
                    keyboard = Keyboard(this, R.xml.calculus) // Load Calculus Layout
                    keyboardView.keyboard = keyboard
                    Toast.makeText(this, "Calculus Layout", Toast.LENGTH_SHORT).show()
                }
            }
            "matrix" -> {
                if (!isMatrixLayout) {
                    resetLayouts()
                    isMatrixLayout = true
                    keyboard = Keyboard(this, R.xml.matrix) // Load Matrix Layout
                    keyboardView.keyboard = keyboard
                    Toast.makeText(this, "Matrix Layout", Toast.LENGTH_SHORT).show()
                }
            }
            "vector" -> {
                if (!isVectorLayout) {
                    resetLayouts()
                    isVectorLayout = true
                    keyboard = Keyboard(this, R.xml.vector)
                    keyboardView.keyboard = keyboard
                    Toast.makeText(this, "Vector Layout", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Reset all layout states before switching
    private fun resetLayouts() {
        isGreekLayout = false
        isAlgebraLayout = false
        isCalculusLayout = false
        isMatrixLayout = false
        isVectorLayout = false
    }

    override fun onPress(primaryCode: Int) {
        // Handle key press action if needed
    }

    override fun onRelease(primaryCode: Int) {
        // Handle key release action if needed
    }

    override fun onText(text: CharSequence?) {
        // Handle text input if needed
    }

    override fun swipeLeft() {
        // Handle swipe left action if needed
    }

    override fun swipeRight() {
        // Handle swipe right action if needed
    }

    override fun swipeDown() {
        // Handle swipe down action if needed
    }

    override fun swipeUp() {
        // Handle swipe up action if needed
    }
}
