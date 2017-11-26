package ru.xmn.kotlinstarter.features.movies

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.text.Html
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.widget.TextView
import ru.xmn.kotlinstarter.R

class ExpandableTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : android.support.v7.widget.AppCompatTextView(context, attrs) {

    private var originalText: CharSequence? = null
    private var trimmedText: CharSequence? = null
    private var bufferType: TextView.BufferType? = null
    private var trim = true
    private var trimLength: Int = 0
    private var trimLines = 3
    private var mCollapsedHeight: Int = 0

    private val displayableText: CharSequence?
        get() = if (trim) trimmedText else originalText

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        this.trimLines = typedArray.getInt(R.styleable.ExpandableTextView_trimLines, DEFAULT_TRIM_LINES)
        typedArray.recycle()

        setOnClickListener {
            trim = !trim
            updateTrimmedAndSetText()
            animateCollapseExpand(trim)
        }
    }

    private fun animateCollapseExpand(needToCollaps: Boolean) {
        if (needToCollaps) {
            animateHeight(height, mCollapsedHeight)
        } else {
            animateHeight(height, height + getRealTextViewHeight(this) - height)
        }
    }

    private fun animateHeight(height: Int, endHeight: Int) {
        ObjectAnimator
                .ofInt(this, "height", height, endHeight)
                .apply {
                    addListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(p0: Animator?) {
                        }

                        override fun onAnimationEnd(p0: Animator?) {
                            clearAnimation()
                        }

                        override fun onAnimationCancel(p0: Animator?) {
                        }

                        override fun onAnimationStart(p0: Animator?) {
                        }
                    })
                }
                .start()
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (trim)
            mCollapsedHeight = measuredHeight

        updateTrimmedAndSetText()
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        originalText = text
        bufferType = type
        updateTrimmedAndSetText()
    }

    private fun updateTrimmedAndSetText() {
        updateTrimmedText()
        super.setText(Html.fromHtml(displayableText!!.toString()), bufferType)
    }

    private fun updateTrimmedText() {
        val paint = paint
        paint.textSize = textSize
        val wordwidth = paint.measureText("a", 0, 1).toInt()
        val num = width / wordwidth
        trimLength = num * trimLines
        trimmedText = getTrimmedText()
    }

    private fun getTrimmedText(): CharSequence? {
        return if (originalText != null && originalText!!.length > trimLength) {
            val spannableStringBuilder = SpannableStringBuilder(originalText, 0, trimLength + 1)
            spannableStringBuilder.trim()
            spannableStringBuilder.append(ELLIPSIS)
        } else {
            originalText
        }
    }

    private fun getRealTextViewHeight(textView: TextView): Int {
        val textHeight = textView.layout.getLineTop(textView.lineCount)
        val padding = textView.compoundPaddingTop + textView.compoundPaddingBottom
        return textHeight + padding
    }

    companion object {

        private val DEFAULT_TRIM_LINES = 3
        private val ELLIPSIS = "... <html><body><font color='#000000'><big>More</big></font></body></html>"
    }
}

