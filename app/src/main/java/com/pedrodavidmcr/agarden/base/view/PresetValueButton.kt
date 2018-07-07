package com.pedrodavidmcr.agarden.base.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.pedrodavidmcr.agarden.R


class PresetValueButton : RelativeLayout, RadioCheckable {
  // Views
  private var mValueTextView: TextView? = null
  private var mUnitTextView: TextView? = null

  // Attribute Variables
  var value: String? = null
  var unit: String? = null
  private var mValueTextColor: Int = 0
  private var mUnitTextColor: Int = 0
  private var mPressedTextColor: Int = 0

  // Variables
  private var mInitialBackgroundDrawable: Drawable? = null
  private var mOnClickListener: View.OnClickListener? = null
  private var mOnTouchListener: View.OnTouchListener? = null
  private var mChecked: Boolean = false
  private val mOnCheckedChangeListeners = ArrayList<RadioCheckable.OnCheckedChangeListener>()


  //================================================================================
  // Constructors
  //================================================================================

  constructor(context: Context) : super(context) {
    setupView()
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    parseAttributes(attrs)
    setupView()
  }

  @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    parseAttributes(attrs)
    setupView()
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
    parseAttributes(attrs)
    setupView()
  }

  //================================================================================
  // Init & inflate methods
  //================================================================================

  private fun parseAttributes(attrs: AttributeSet) {
    val a = context.obtainStyledAttributes(attrs,
        R.styleable.PresetValueButton, 0, 0)
    val resources = context.resources
    try {
      value = a.getString(R.styleable.PresetValueButton_presetButtonValueText)
      unit = a.getString(R.styleable.PresetValueButton_presetButtonUnitText)
      mValueTextColor = a.getColor(R.styleable.PresetValueButton_presetButtonValueTextColor, resources.getColor(android.R.color.black))
      mPressedTextColor = a.getColor(R.styleable.PresetValueButton_presetButtonPressedTextColor, Color.WHITE)
      mUnitTextColor = a.getColor(R.styleable.PresetValueButton_presetButtonUnitTextColor, resources.getColor(android.R.color.darker_gray))
    } finally {
      a.recycle()
    }
  }

  // Template method
  private fun setupView() {
    inflateView()
    bindView()
    setCustomTouchListener()
  }

  protected fun inflateView() {
    val inflater = LayoutInflater.from(context)
    inflater.inflate(R.layout.custom_preset_button, this, true)
    mValueTextView = findViewById(R.id.text_view_value)
    mUnitTextView = findViewById(R.id.text_view_unit)
    mInitialBackgroundDrawable = background
  }

  protected fun bindView() {
    if (mUnitTextColor != DEFAULT_TEXT_COLOR) {
      mUnitTextView!!.setTextColor(mUnitTextColor)
    }
    if (mValueTextColor != DEFAULT_TEXT_COLOR) {
      mValueTextView!!.setTextColor(mValueTextColor)
    }
    mUnitTextView!!.text = unit
    mValueTextView!!.text = value
  }

  //================================================================================
  // Overriding default behavior
  //================================================================================

  override fun setOnClickListener(@Nullable l: View.OnClickListener?) {
    mOnClickListener = l
  }

  private fun setCustomTouchListener() {
    super.setOnTouchListener(TouchListener())
  }

  override fun setOnTouchListener(onTouchListener: View.OnTouchListener) {
    mOnTouchListener = onTouchListener
  }

  fun getOnTouchListener(): View.OnTouchListener? {
    return mOnTouchListener
  }

  private fun onTouchDown(motionEvent: MotionEvent) {
    isChecked = true
  }

  private fun onTouchUp(motionEvent: MotionEvent) {
    // Handle user defined click listeners
    if (mOnClickListener != null) {
      mOnClickListener!!.onClick(this)
    }
  }
  //================================================================================
  // Public methods
  //================================================================================

  fun setCheckedState() {
    setBackgroundResource(R.drawable.background_shape_preset_button__pressed)
    mValueTextView!!.setTextColor(mPressedTextColor)
    mUnitTextView!!.setTextColor(mPressedTextColor)
  }

  fun setNormalState() {
    setBackgroundDrawable(mInitialBackgroundDrawable)
    mValueTextView!!.setTextColor(mValueTextColor)
    mUnitTextView!!.setTextColor(mUnitTextColor)
  }

  //================================================================================
  // Checkable implementation
  //================================================================================

  override fun setChecked(checked: Boolean) {
    if (mChecked != checked) {
      mChecked = checked
      if (!mOnCheckedChangeListeners.isEmpty()) {
        for (i in 0 until mOnCheckedChangeListeners.size) {
          mOnCheckedChangeListeners.get(i).onCheckedChanged(this, mChecked)
        }
      }
      if (mChecked) {
        setCheckedState()
      } else {
        setNormalState()
      }
    }
  }

  override fun isChecked(): Boolean {
    return mChecked
  }

  override fun toggle() {
    isChecked = !mChecked
  }

  override fun addOnCheckChangeListener(onCheckedChangeListener: RadioCheckable.OnCheckedChangeListener) {
    mOnCheckedChangeListeners.add(onCheckedChangeListener)
  }

  override fun removeOnCheckChangeListener(onCheckedChangeListener: RadioCheckable.OnCheckedChangeListener) {
    mOnCheckedChangeListeners.remove(onCheckedChangeListener)
  }

  //================================================================================
  // Inner classes
  //================================================================================
  private inner class TouchListener : View.OnTouchListener {

    override fun onTouch(v: View, event: MotionEvent): Boolean {
      when (event.action) {
        MotionEvent.ACTION_DOWN -> onTouchDown(event)
        MotionEvent.ACTION_UP -> onTouchUp(event)
      }
      if (mOnTouchListener != null) {
        mOnTouchListener!!.onTouch(v, event)
      }
      return true
    }
  }

  companion object {

    // Constants
    val DEFAULT_TEXT_COLOR = Color.TRANSPARENT
  }
}