package org.wordpress.aztec.demo

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import org.wordpress.aztec.AztecText
import org.wordpress.aztec.toolbar.AztecToolbar
import org.wordpress.aztec.*
import org.wordpress.aztec.source.Format
import org.wordpress.aztec.source.SourceViewEditText

class MainActivity : AppCompatActivity() {
    companion object {
        private val BOLD = "<b>Bold</b><br>"
        private val ITALIC = "<i>Italic</i><br>"
        private val UNDERLINE = "<u>Underline</u><br>"
        private val STRIKETHROUGH = "<s class=\"test\">Strikethrough</s><br>" // <s> or <strike> or <del>
        private val BULLET = "<ul><li>asdfg</li></ul>"
        private val QUOTE = "<blockquote>Quote</blockquote>"
        private val LINK = "<a href=\"https://github.com/wordpress-mobile/WordPress-Aztec-Android\">Link</a><br>"
        private val UNKNOWN = "<iframe class=\"classic\">Menu</iframe><br>"
        private val COMMENT = "<!--This is a comment--><br>"
        private val HIDDEN = 
                "<span></span>" +
                "<div class=\"first\">" +
                "    <div class=\"second\">" +
                "        <div class=\"third\">" +
                "            Div<br><span><b>Span</b></span><br>Hidden" +
                "        </div>" +
                "        <div class=\"fourth\"></div>" +
                "        <div class=\"fifth\"></div>" +
                "    </div>" +
                "    <span class=\"second last\"></span>" +
                "</div>" +
                "<br>"
        private val EXAMPLE = BOLD + ITALIC + UNDERLINE + STRIKETHROUGH + BULLET + QUOTE + LINK + UNKNOWN + COMMENT + HIDDEN
    }

    private lateinit var aztec: AztecText
    private lateinit var source: SourceViewEditText
    private lateinit var formattingToolbar: AztecToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        aztec = findViewById(R.id.aztec) as AztecText
        source = findViewById(R.id.source) as SourceViewEditText
        source.history = aztec.history

        formattingToolbar = findViewById(R.id.formatting_toolbar) as AztecToolbar
        formattingToolbar.setEditor(aztec, source)

        // initialize the text & HTML
        aztec.fromHtml(EXAMPLE)
        source.displayStyledAndFormattedHtml(aztec.toHtml())
        aztec.fromHtml(source.getPureHtml())

        aztec.setSelection(aztec.editableText.length)
        aztec.history.clearHistory()

        // ImageGetter coming soon...
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.undo ->
                if (aztec.visibility == View.VISIBLE) {
                    aztec.undo()
                } else {
                    source.undo()
                }
            R.id.redo ->
                if (aztec.visibility == View.VISIBLE) {
                    aztec.redo()
                } else {
                    source.redo()
                }
            else -> {
            }
        }

        return true
    }
}