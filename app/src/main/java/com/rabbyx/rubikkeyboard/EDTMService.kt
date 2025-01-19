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
import android.webkit.WebView
import android.webkit.WebViewClient

class EDTMService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private lateinit var keyboardView: KeyboardView
    private lateinit var suggestionBar: LinearLayout
    private lateinit var keyboard: Keyboard
    private lateinit var webView: WebView
    private var isGreekLayout = false
    private var isAlgebraLayout = false
    private var isCalculusLayout = false
    private var isMatrixLayout = false
    private var isVectorLayout = false

    override fun onCreateInputView(): View? {
        // Inflate the keyboard layout and set up the keyboard
        val inputView = layoutInflater.inflate(R.layout.keyboard, null)




        webView = inputView.findViewById(R.id.webView)

        // Enable JavaScript
        webView.settings.javaScriptEnabled = true

        // Set WebViewClient to handle link clicks inside WebView
        webView.webViewClient = WebViewClient()

        // Load the HTML file from assets
        webView.loadUrl("file:///android_asset/input.html")

        webView.addJavascriptInterface(object {
            @android.webkit.JavascriptInterface
            fun onInputUpdated(value: String) {
               currentInputConnection.commitText(value, 1)
            }
        }, "AndroidBridge")
        // Get references to the suggestion bar and keyboard view
        suggestionBar = inputView.findViewById(R.id.suggestionBar)


        // Load the default layout (QWERTY)
        keyboard = Keyboard(this, R.xml.qwerty)
        keyboardView = inputView.findViewById(R.id.keyboardView)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)



        return inputView
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val inputConnection = currentInputConnection

        when (primaryCode) {
            Keyboard.KEYCODE_DONE -> {
                inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
            }
            Keyboard.KEYCODE_DELETE -> {
                inputConnection.deleteSurroundingText(1, 0)
                webView.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                webView.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL))
            }

            // Section: Set Operations
            1111 -> sendTextToMathField("\\{1,2,3}")
            1112 -> sendTextToMathField("\\in")
            1113 -> sendTextToMathField("\\not\\in")
            1114 -> sendTextToMathField("\\subset")
            1115 -> sendTextToMathField("\\subseteq")
            1116 -> sendTextToMathField("\\not\\subset")
            1117 -> sendTextToMathField("\\supset")
            1118 -> sendTextToMathField("\\supseteq")
            1119 -> sendTextToMathField("\\cup")
            1120 -> sendTextToMathField("\\cap")
            1121 -> sendTextToMathField("\\bigcup_{n=1}^{10}A_n")
            1122 -> sendTextToMathField("\\bigcap_{n=1}^{10}A_n")
            1123 -> sendTextToMathField("\\emptyset")
            1124 -> sendTextToMathField("\\mathcal{P}")
            1125 -> sendTextToMathField("\\min")
            1126 -> sendTextToMathField("\\max")
            1127 -> sendTextToMathField("\\sup")
            1128 -> sendTextToMathField("\\inf")
            1129 -> sendTextToMathField("\\limsup")
            1130 -> sendTextToMathField("\\liminf")
            1131 -> sendTextToMathField("\\overline{A}")

            // Section: Calculus
            1132 -> sendTextToMathField("\\\\frac{d^n}{dx^n}f(x)")
            1133 -> sendTextToMathField("\\f'")
            1134 -> sendTextToMathField("\\frac{\\partial f}{\\partial x}")
            1135 -> sendTextToMathField("\\frac{\\partial f}{\\partial x}")
            1136 -> sendTextToMathField("\\int_a^b")
            1137 -> sendTextToMathField("\\int")
            1138 -> sendTextToMathField("\\iint")
            1139 -> sendTextToMathField("\\iint\\limits_{a}^{b} f(x, y) \\,dx\\,dy")
            1140 -> sendTextToMathField("\\lim_{x\\to \\infty}")
            1141 -> sendTextToMathField("\\sum_{n=1}^{\\infty}a_n")
            1142 -> sendTextToMathField("\\prod_{n=1}^{\\infty}a_n")

            // Section: Logic
            1143 -> sendTextToMathField("\\sim")
            1144 -> sendTextToMathField("\\land")
            1145 -> sendTextToMathField("\\lor")
            1146 -> sendTextToMathField("\\to")
            1147 -> sendTextToMathField("\\leftrightarrow")
            1148 -> sendTextToMathField("\\equiv")
            1149 -> sendTextToMathField("\\therefore")
            1150 -> sendTextToMathField("\\exists")
            1151 -> sendTextToMathField("\\forall")
            1152 -> sendTextToMathField("\\rightarrow")
            1153 -> sendTextToMathField("\\leftarrow")

            // Section: Linear Algebra
            1154 -> sendTextToMathField("\\vect{v}")
            1155 -> sendTextToMathField("\\mathbf{v}")
            1156 -> sendTextToMathField("||\\vec{v}||")
            1157 -> sendTextToMathField("\\left[{array}{ccc} 1 & 2 & 3 \\\\ 4 & 5 & 6 \\\\ 7 & 8 & 9 \\end{array} \\right]")
            1158 -> sendTextToMathField("\\left|{array}{ccc} 1 & 2 & 3 \\\\ 4 & 5 & 6 \\\\ 7 & 8 & 9 \\end{array} \\right|")
            1159 -> sendTextToMathField("\\detA")
            1160 -> sendTextToMathField("\\operatorname{tr}{A}")
            1161 -> sendTextToMathField("\\operatorname{tr}(A)")
            1162 -> sendTextToMathField("\\dim(V)")

            // Section: Functions
            1163 -> sendTextToMathField("\\to")
            1164 -> sendTextToMathField("\\circ")
            1165 -> sendTextToMathField("|x|=\\begin{cases}x & x\\ge 0\\\\-x & x<0\\end{cases}")
            1166 -> sendTextToMathField("\\cdot")
            1167 -> sendTextToMathField("\\times")
            1168 -> sendTextToMathField("|\\mathbf{A}|")
            1169 -> sendTextToMathField("\\hat{\\mathbf{A}}")
            1170 -> sendTextToMathField("{|\\mathbf{A}|}")
            1171 -> sendTextToMathField("z_1")
            1172 -> sendTextToMathField("a+bi")
            1173 -> sendTextToMathField("|z|")
            1174 -> sendTextToMathField("\\sqrt{a^2 + b^2}")
            1175 -> sendTextToMathField("\\overline{z}")

            // Handle switching layouts or custom actions
            -1 -> switchToLayout("qwerty")
            -2 -> switchToLayout("greek")
            -3 -> switchToLayout("set")
            -5 -> webView.evaluateJavascript("document.getElementById('mathField').value = ''", null)
            -999 -> switchToLayout("calculus")
            -1000 -> switchToLayout("matrix")
            -666 -> switchToLayout("vector")

            // Default character handling
            else -> {
                val character = primaryCode.toChar().toString()
                sendTextToMathField(character)
            }
        }
    }

    // Method to update the suggestion bar visibility and content
    private fun updateSuggestionBar(suggestion: String) {
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
            "set" -> {
                if (!isAlgebraLayout) {
                    resetLayouts()
                    isAlgebraLayout = true
                    keyboard = Keyboard(this, R.xml.set)
                    keyboardView.keyboard = keyboard
                    Toast.makeText(this, "Set Layout", Toast.LENGTH_SHORT).show()
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

    private fun sendTextToMathField(text: String) {
        if (text == "BACKSPACE") {
            // Remove the last character from the math field
            val script = "javascript:document.getElementById('mathField').value = document.getElementById('mathField').value.slice(0, -1);"
            webView.evaluateJavascript(script, null)
        } else {
            // Inject JavaScript to update the math-field with the new text
            val script = "javascript:document.getElementById('mathField').value += '$text';"
            webView.evaluateJavascript(script, null)
        }
    }





}
