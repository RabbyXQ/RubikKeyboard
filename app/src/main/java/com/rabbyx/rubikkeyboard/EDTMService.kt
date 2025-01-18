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
                // Update the input connection if necessary
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

        // Handle key press actions
        when (primaryCode) {
            Keyboard.KEYCODE_DONE -> {
                inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
            }
            Keyboard.KEYCODE_DELETE -> {
                inputConnection.deleteSurroundingText(1, 0)
                webView.dispatchKeyEvent(
                    KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)
                )
                webView.dispatchKeyEvent(
                    KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL)
                )
            }


            // Section SET
            1111-> inputConnection.commitText("\\{1,2,3}", 1)
            1112->inputConnection.commitText("\\in", 1)
            1113->inputConnection.commitText("\\not\\in", 1)
            1114->inputConnection.commitText("\\subset", 1)
            1115->inputConnection.commitText("\\subseteq", 1)
            1116->inputConnection.commitText("\\not\\subset", 1)
            1117->inputConnection.commitText("\\supset", 1)
            1118->inputConnection.commitText("\\supseteq", 1)
            1119->inputConnection.commitText("\\cup", 1)
            1120->inputConnection.commitText("\\cap", 1)
            1121->inputConnection.commitText("\\bigcup_{n=1}^{10}A_n", 1)
            1122->inputConnection.commitText("\\bigcap_{n=1}^{10}A_n", 1)
            1123->inputConnection.commitText("\\emptyset", 1)
            1124->inputConnection.commitText("\\mathcal{P}", 1)
            1125->inputConnection.commitText("\\min", 1)
            1126->inputConnection.commitText("\\max", 1)
            1127->inputConnection.commitText("\\sup", 1)
            1128->inputConnection.commitText("\\inf", 1)
            1129->inputConnection.commitText("\\limsup", 1)
            1130->inputConnection.commitText("\\liminf", 1)
            1131->inputConnection.commitText("\\overline{A}", 1)

            //Calculus

            1132-> {

                inputConnection.commitText("\\frac{d^n}{dx^n} f(x)", 1)
                sendTextToMathField("\\\\frac{d^n}{dx^n}f(x)")

            }
            1133-> {
                inputConnection.commitText("\\f'", 1)
            }
            1134-> {
                inputConnection.commitText("\\frac{\\partial f}{\\partial x}", 1)
            }
            1135->inputConnection.commitText("\\frac{\\partial f}{\\partial x}", 1)
            1136->inputConnection.commitText("\\int_a^b", 1)
            1137->inputConnection.commitText("\\int", 1)
            1138->inputConnection.commitText("\\iint", 1)
            1139->inputConnection.commitText("\\iint\\limits_{a}^{b} f(x, y) \\,dx\\,dy", 1)
            1140->inputConnection.commitText("\\lim_{x\\to \\infty}", 1)
            1141->inputConnection.commitText("\\sum_{n=1}^{\\infty}a_n", 1)
            1142->inputConnection.commitText("\\prod_{n=1}^{\\infty}a_n", 1)


            /// Logic

            1143->inputConnection.commitText("\\sim", 1)
            1144->inputConnection.commitText("\\land", 1)
            1145->inputConnection.commitText("\\lor", 1)
            1146->inputConnection.commitText("\\to", 1)
            1147->inputConnection.commitText("\\leftrightarrow", 1)
            1148->inputConnection.commitText("\\equiv", 1)
            1149->inputConnection.commitText("\\therefore", 1)
            1150->inputConnection.commitText("\\exists", 1)
            1151->inputConnection.commitText("\\forall", 1)
            1152->inputConnection.commitText("\\rightarrow", 1)
            1153->inputConnection.commitText("\\leftarrow", 1)


            //Linear Algebra


            1154->inputConnection.commitText("\\vect{v}", 1)
            1155->inputConnection.commitText("\\mathbf{v}", 1)
            1156->inputConnection.commitText("||\\vec{v}||", 1)
            1157->inputConnection.commitText("\\left[{array}{ccc} 1 & 2 & 3 \\\\ 4 & 5 & 6 \\\\ 7 & 8 & 9 \\end{array} \\right]", 1)
            1158->inputConnection.commitText("\\left|{array}{ccc} 1 & 2 & 3 \\\\ 4 & 5 & 6 \\\\ 7 & 8 & 9 \\end{array} \\right|", 1)
            1159->inputConnection.commitText("\\detA", 1)
            1160->inputConnection.commitText("\\operatorname{tr}{A}", 1)
            1161->inputConnection.commitText("\\operatorname{tr}(A)", 1)
            1162->inputConnection.commitText("\\dim(V)", 1)


            // Functions

            1163->inputConnection.commitText("\\to", 1)
            1164->inputConnection.commitText("\\circ", 1)
            1165->inputConnection.commitText("|x|=\\begin{cases}x & x\\ge 0\\\\-x & x<0\\end{cases}", 1)
            1179->inputConnection.commitText("a^n", 1)
            1180->inputConnection.commitText("\\sqrt[n]{a}", 1)
            1181->inputConnection.commitText("\\sqrt{a}", 1)
            1182->inputConnection.commitText("\\ln(x)", 1)
            1183->inputConnection.commitText("\\log_{10}(x)", 1)
            1184->inputConnection.commitText("\\log_b(x)", 1)
            1185->inputConnection.commitText("\\sin(x)", 1)
            1186->inputConnection.commitText("\\cos(x)", 1)
            1187->inputConnection.commitText("\\tan(x)", 1)
            1188->inputConnection.commitText("\\csc(x)", 1)
            1189->inputConnection.commitText("\\sec(x)", 1)
            1190->inputConnection.commitText("\\cot(x)", 1)
            1191->inputConnection.commitText("\\sin^{-1}(x) \\quad", 1)
            1192->inputConnection.commitText("\\cos^{-1}(x) \\quad ", 1)
            1193->inputConnection.commitText("\\tan^{-1}(x) \\quad", 1)
            1194->inputConnection.commitText("\\sinh(x)", 1)
            1195->inputConnection.commitText("\\cot^{-1}(x) \\quad", 1)
            1196->inputConnection.commitText("\\csc^{-1}(x) \\quad", 1)
            1197->inputConnection.commitText("\\sec^{-1}(x) \\quad", 1)
            1198->inputConnection.commitText("\\sinh(x)", 1)
            1199->inputConnection.commitText("\\cosh(x)", 1)
            1200->inputConnection.commitText("\\tanh(x)", 1)
            1201->inputConnection.commitText("\\coth(x)", 1)
            1202->inputConnection.commitText("\\operatorname{csch}(x)", 1)
            1203->inputConnection.commitText("\\operatorname{sech}(x)", 1)
            1204->inputConnection.commitText("n!", 1)
            1205->inputConnection.commitText("\\Gamma(z)", 1)
            1206->inputConnection.commitText("\\lfloor x \\rfloor", 1)
            1207->inputConnection.commitText("\\lceil x \\rceil", 1)
            1208->inputConnection.commitText("|x|", 1)
            1209->inputConnection.commitText("\\gcd(a, b)", 1)
            1210->inputConnection.commitText("\\operatorname{lcm}(a, b)", 1)
            1211->inputConnection.commitText("\\sum_{i=1}^{n} a_i", 1)
            1212->inputConnection.commitText("\\prod_{i=1}^{n} a_i", 1)
            1213->inputConnection.commitText("\\int_a^b f(x) \\, dx", 1)
            1214->inputConnection.commitText("\\int f(x) \\, dx", 1)
            1215->inputConnection.commitText("e^x", 1)
            1216->inputConnection.commitText("\\operatorname{sgn}(x)", 1)
            1217->inputConnection.commitText("\\ln(x)", 1)
            1218->inputConnection.commitText("\\log_{10}(x)", 1)
            1219->inputConnection.commitText("\\log_{2}(x)", 1)
            1220->inputConnection.commitText("\\log_b(x)", 1)
            1221->inputConnection.commitText("a^x", 1)
            1222->inputConnection.commitText("y_0 e^{kt}", 1)
            1223->inputConnection.commitText("\\log_b\\left(\\frac{x}{y}\\right)", 1)
            1224->inputConnection.commitText("\\sqrt[n]{x} ", 1)
            1225->inputConnection.commitText("deg(f)", 1)








            //Vector

            1166->inputConnection.commitText("\\cdot", 1)
            1167->inputConnection.commitText("\\times", 1)
            1168->inputConnection.commitText("|\\mathbf{A}|", 1)
            1169->inputConnection.commitText("\\hat{\\mathbf{A}}", 1)
            1170->inputConnection.commitText("{|\\mathbf{A}|}", 1)
            1171->inputConnection.commitText("z_1", 1)
            1172->inputConnection.commitText("a+bi", 1)
            1173->inputConnection.commitText("|z|", 1)
            1174->inputConnection.commitText("\\sqrt{a^2 + b^2}", 1)
            1175->inputConnection.commitText("\\overline{z} ", 1)
            1176->inputConnection.commitText("re^{i\\theta}", 1)
            1177->inputConnection.commitText("\\tan^{-1}\\left(\\frac{b}{a}\\right)", 1)
            1178->inputConnection.commitText("\\arg(z)", 1)






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
                switchToLayout("set")
            }
            -5 -> {
                val script = "javascript:document.getElementById('mathField').value = '';"
                webView.evaluateJavascript(script) { result ->
                    Log.d("EDTMService", "Backspace executed: $result")
                }
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
                sendTextToMathField(character.toString())

            }
        }

        // Update suggestion bar with text based on input
        updateSuggestionBar("Suggestions for: ${primaryCode.toChar()}")
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
