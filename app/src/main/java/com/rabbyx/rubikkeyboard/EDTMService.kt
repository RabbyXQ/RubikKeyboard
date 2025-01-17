package com.rabbyx.rubikkeyboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.IBinder
import android.view.KeyEvent
import android.view.inputmethod.InputConnection
import android.view.inputmethod.EditorInfo
import android.widget.Toast

class EDTMService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private lateinit var keyboardView: KeyboardView
    private lateinit var keyboard: Keyboard

    override fun onCreateInputView(): KeyboardView {
        // Inflate the keyboard layout and set up the keyboard
        keyboardView = layoutInflater.inflate(R.layout.keyboard, null) as KeyboardView

        // Load the custom keyboard layout (make sure you have the keyboard XML in res/xml/keyboard.xml)
        keyboard = Keyboard(this, R.xml.qwerty) // Replace with your actual XML file

        // Set the keyboard to the view
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)

        // Return the KeyboardView to be shown to the user
        return keyboardView
    }

    override fun onPress(primaryCode: Int) {
        // Handle key press actions if needed
        // You can highlight keys or give feedback when keys are pressed
    }

    override fun onRelease(primaryCode: Int) {
        // Handle key release actions if needed
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val inputConnection: InputConnection = currentInputConnection

        when (primaryCode) {
            Keyboard.KEYCODE_DONE -> {
                // Handle "done" or "enter" key
                inputConnection.sendKeyEvent(android.view.KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                inputConnection.sendKeyEvent(android.view.KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
            }
            Keyboard.KEYCODE_DELETE -> {
                // Handle delete key
                inputConnection.deleteSurroundingText(1, 0)
            }
            else -> {
                // Send character corresponding to the primary key code
                val character = primaryCode.toChar()
                inputConnection.commitText(character.toString(), 1)
            }
        }
    }

    override fun onText(text: CharSequence?) {
        // Handle the text input if needed (optional)
    }

    override fun swipeLeft() {
        // Handle swipe left gesture if needed
    }

    override fun swipeRight() {
        // Handle swipe right gesture if needed
    }

    override fun swipeDown() {
        // Handle swipe down gesture if needed
    }

    override fun swipeUp() {
        // Handle swipe up gesture if needed
    }
}
