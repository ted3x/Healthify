package ge.c0d3in3.components.edit_text

import android.content.Context
import android.text.InputType
import android.text.InputType.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import ge.c0d3in3.components.R
import ge.c0d3in3.components.databinding.EditTextWithTitleLayoutBinding

class EditTextWithTitle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by lazy {
        EditTextWithTitleLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var text = ""
        get() = binding.editText.text.toString()
        set(value) {
            field = value
            binding.editText.setText(value)
        }

    var title = ""
        get() = binding.editTextTitle.text.toString()
        set(value) {
            field = value
            binding.editTextTitle.text = value
        }

    var hint = ""
        get() = binding.editText.hint.toString()
        set(value) {
            field = value
            binding.editText.hint = value
        }

    var inputType = 0
        set(value) {
            field = value
            with(binding.editText) {
                when (value) {
                    TYPE_CLASS_TEXT -> inputType = InputType.TYPE_CLASS_TEXT
                    TYPE_CLASS_NUMBER -> inputType = InputType.TYPE_CLASS_NUMBER
                    TYPE_NUMBER_FLAG_DECIMAL -> inputType =
                        InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                    TYPE_TEXT_VARIATION_EMAIL_ADDRESS -> inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    TYPE_TEXT_VARIATION_PASSWORD
                    -> inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
            }
        }

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.EditTextWithTitle)
            text = typedArray.getString(R.styleable.EditTextWithTitle_etText) ?: ""
            hint = typedArray.getString(R.styleable.EditTextWithTitle_etHint) ?: ""
            title = typedArray.getString(R.styleable.EditTextWithTitle_etTitle) ?: ""
            inputType = typedArray.getInt(R.styleable.EditTextWithTitle_etInputType, 0)
            typedArray.recycle()
        }
    }

    fun onChange(callback: (String) -> Unit) {
        binding.editText.doAfterTextChanged {
            callback.invoke(it.toString())
        }
    }

    companion object {
        const val TYPE_CLASS_TEXT = 0
        const val TYPE_CLASS_NUMBER = 1
        const val TYPE_NUMBER_FLAG_DECIMAL = 2
        const val TYPE_TEXT_VARIATION_PASSWORD = 3
        const val TYPE_TEXT_VARIATION_EMAIL_ADDRESS = 4
        const val TYPE_CLASS_PHONE = 5
    }
}