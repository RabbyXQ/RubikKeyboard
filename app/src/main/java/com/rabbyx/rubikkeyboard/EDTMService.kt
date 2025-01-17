package com.rabbyx.rubikkeyboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.KeyEvent
import android.view.inputmethod.InputConnection
import android.widget.Toast

class EDTMService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private lateinit var keyboardView: KeyboardView
    private lateinit var keyboard: Keyboard
    private var isGreekLayout = false  // Flag to track the Greek layout
    private var isMathLayout = false   // Flag to track the Math layout

    override fun onCreateInputView(): KeyboardView {
        // Inflate the keyboard layout and set up the keyboard
        keyboardView = layoutInflater.inflate(R.layout.keyboard, null) as KeyboardView

        // Load the default layout (QWERTY)
        keyboard = Keyboard(this, R.xml.qwerty)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)

        return keyboardView
    }

    override fun onPress(primaryCode: Int) {
        // Handle key press actions if needed
    }

    override fun onRelease(primaryCode: Int) {
        // Handle key release actions if needed
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val inputConnection: InputConnection = currentInputConnection

        when (primaryCode) {
            Keyboard.KEYCODE_DONE -> {
                // Handle "done" or "enter" key
                inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
            }
            Keyboard.KEYCODE_DELETE -> {
                // Handle delete key
                inputConnection.deleteSurroundingText(1, 0)
            }
            -1 -> {
                // Switch to QWERTY layout
                switchToQwertyLayout()
            }
            -2 -> {
                // Switch to Greek layout
                switchToGreekLayout()
            }
            -3 -> {
                // Switch to Math layout
                switchToMathLayout()
            }
            else -> {
                // Send character corresponding to the primary key code
                val character = primaryCode.toChar()
                inputConnection.commitText(character.toString(), 1)
            }
        }
    }

    override fun onText(text: CharSequence?) {
        // Not implemented
    }

    // Method to switch to QWERTY layout
    private fun switchToQwertyLayout() {
        if (isMathLayout || isGreekLayout) {
            // Reset the flags when switching back to QWERTY layout
            isMathLayout = false
            isGreekLayout = false
            keyboard = Keyboard(this, R.xml.qwerty)
            keyboardView.keyboard = keyboard
            Toast.makeText(this, "QWERTY Layout", Toast.LENGTH_SHORT).show()
        }
    }

    // Method to switch to Greek layout
    private fun switchToGreekLayout() {
        if (!isGreekLayout) {
            // Reset other layouts and switch to Greek
            isGreekLayout = true
            isMathLayout = false
            keyboard = Keyboard(this, R.xml.greek)
            keyboardView.keyboard = keyboard
            Toast.makeText(this, "Greek Layout", Toast.LENGTH_SHORT).show()
        }
    }

    // Method to switch to Math layout
    private fun switchToMathLayout() {
        if (!isMathLayout) {
            // Reset other layouts and switch to Math
            isMathLayout = true
            isGreekLayout = false
            keyboard = Keyboard(this, R.xml.math)
            keyboardView.keyboard = keyboard
            Toast.makeText(this, "Math Layout", Toast.LENGTH_SHORT).show()
        }
    }

    override fun swipeLeft() {
        // Switch to Math layout on swipe left
        if (!isMathLayout) {
            switchToMathLayout()
        }
    }

    override fun swipeRight() {
        // Switch to Greek layout on swipe right
        if (!isGreekLayout) {
            switchToGreekLayout()
        }
    }

    override fun swipeDown() {
        // Handle swipe down gesture if needed
    }

    override fun swipeUp() {
        // Handle swipe up gesture if needed
    }
}
