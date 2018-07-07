package com.pedrodavidmcr.agarden.base.view


import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.pedrodavidmcr.agarden.R
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class PresetRadioGroup : LinearLayout {
  private val sNextGeneratedId = AtomicInteger(1)

  // Attribute Variables
  private var mCheckedId = View.NO_ID
  private var mProtectFromCheckedChange = false
  // Variables
  //================================================================================
  // Public methods
  //================================================================================


  var onCheckedChangeListener: OnCheckedChangeListener? = null
  private val mChildViewsMap = HashMap<Int, View>()
  private var mPassThroughListener: PassThroughHierarchyChangeListener? = null
  private var mChildOnCheckedChangeListener: RadioCheckable.OnCheckedChangeListener? = null


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
        R.styleable.PresetRadioGroup, 0, 0)
    try {
      mCheckedId = a.getResourceId(R.styleable.PresetRadioGroup_presetRadioCheckedId, View.NO_ID)

    } finally {
      a.recycle()
    }
  }

  // Template method
  private fun setupView() {
    mChildOnCheckedChangeListener = CheckedStateTracker()
    mPassThroughListener = PassThroughHierarchyChangeListener()
    super.setOnHierarchyChangeListener(mPassThroughListener)
  }


  //================================================================================
  // Overriding default behavior
  //================================================================================
  override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
    if (child is RadioCheckable) {
      val button = child as RadioCheckable
      if (button.isChecked) {
        mProtectFromCheckedChange = true
        if (mCheckedId != View.NO_ID) {
          setCheckedStateForView(mCheckedId, false)
        }
        mProtectFromCheckedChange = false
        setCheckedId(child.id, true)
      }
    }

    super.addView(child, index, params)
  }

  override fun setOnHierarchyChangeListener(listener: ViewGroup.OnHierarchyChangeListener) {
    // the user listener is delegated to our pass-through listener
    mPassThroughListener!!.mOnHierarchyChangeListener = listener
  }

  override fun onFinishInflate() {
    super.onFinishInflate()
    // checks the appropriate radio button as requested in the XML file
    if (mCheckedId != View.NO_ID) {
      mProtectFromCheckedChange = true
      setCheckedStateForView(mCheckedId, true)
      mProtectFromCheckedChange = false
      setCheckedId(mCheckedId, true)
    }
  }

  private fun setCheckedId(@IdRes id: Int, isChecked: Boolean) {
    mCheckedId = id
    if (onCheckedChangeListener != null) {
      onCheckedChangeListener!!.onCheckedChanged(this, mChildViewsMap[id]!!, isChecked, mCheckedId)
    }
  }

  override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
    return p is LayoutParams
  }

  fun clearCheck() {
    check(View.NO_ID)
  }

  fun check(@IdRes id: Int) {
    // don't even bother
    if (id != View.NO_ID && id == mCheckedId) {
      return
    }

    if (mCheckedId != View.NO_ID) {
      setCheckedStateForView(mCheckedId, false)
    }

    if (id != View.NO_ID) {
      setCheckedStateForView(id, true)
    }

    setCheckedId(id, true)
  }

  private fun setCheckedStateForView(viewId: Int, checked: Boolean) {
    var checkedView: View?
    checkedView = mChildViewsMap[viewId]
    if (checkedView == null) {
      checkedView = findViewById(viewId)
      if (checkedView != null) {
        mChildViewsMap[viewId] = checkedView
      }
    }
    if (checkedView != null && checkedView is RadioCheckable) {
      (checkedView as RadioCheckable).isChecked = checked
    }
  }

  override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
    return LayoutParams(context, attrs)
  }


  //================================================================================
  // Nested classes
  //================================================================================
  interface OnCheckedChangeListener {
    fun onCheckedChanged(radioGroup: View, radioButton: View, isChecked: Boolean, checkedId: Int)
  }

  class LayoutParams : LinearLayout.LayoutParams {
    /**
     * {@inheritDoc}
     */
    constructor(c: Context, attrs: AttributeSet) : super(c, attrs) {}

    /**
     * {@inheritDoc}
     */
    constructor(w: Int, h: Int) : super(w, h) {}

    /**
     * {@inheritDoc}
     */
    constructor(w: Int, h: Int, initWeight: Float) : super(w, h, initWeight) {}

    /**
     * {@inheritDoc}
     */
    constructor(p: ViewGroup.LayoutParams) : super(p) {}

    /**
     * {@inheritDoc}
     */
    constructor(source: ViewGroup.MarginLayoutParams) : super(source) {}

    /**
     *
     * Fixes the child's width to
     * [android.view.ViewGroup.LayoutParams.WRAP_CONTENT] and the child's
     * height to  [android.view.ViewGroup.LayoutParams.WRAP_CONTENT]
     * when not specified in the XML file.
     *
     * @param a          the styled attributes set
     * @param widthAttr  the width attribute to fetch
     * @param heightAttr the height attribute to fetch
     */
    override fun setBaseAttributes(a: TypedArray,
                                   widthAttr: Int, heightAttr: Int) {

      if (a.hasValue(widthAttr)) {
        width = a.getLayoutDimension(widthAttr, "layout_width")
      } else {
        width = ViewGroup.LayoutParams.WRAP_CONTENT
      }

      if (a.hasValue(heightAttr)) {
        height = a.getLayoutDimension(heightAttr, "layout_height")
      } else {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
      }
    }
  }

  //================================================================================
  // Inner classes
  //================================================================================
  private inner class CheckedStateTracker : RadioCheckable.OnCheckedChangeListener {
    override fun onCheckedChanged(buttonView: View, isChecked: Boolean) {
      // prevents from infinite recursion
      if (mProtectFromCheckedChange) {
        return
      }

      mProtectFromCheckedChange = true
      if (mCheckedId != View.NO_ID) {
        setCheckedStateForView(mCheckedId, false)
      }
      mProtectFromCheckedChange = false

      val id = buttonView.id
      setCheckedId(id, true)
    }
  }

  private inner class PassThroughHierarchyChangeListener : ViewGroup.OnHierarchyChangeListener {
    var mOnHierarchyChangeListener: ViewGroup.OnHierarchyChangeListener? = null

    /**
     * {@inheritDoc}
     */
    override fun onChildViewAdded(parent: View, child: View) {
      if (parent === this@PresetRadioGroup && child is RadioCheckable) {
        var id = child.id
        // generates an id if it's missing
        if (id == View.NO_ID) {
          id = generateViewId()
          child.id = id
        }
        (child as RadioCheckable).addOnCheckChangeListener(
            mChildOnCheckedChangeListener!!)
        mChildViewsMap[id] = child
      }

      mOnHierarchyChangeListener?.onChildViewAdded(parent, child)
    }

    /**
     * {@inheritDoc}
     */
    override fun onChildViewRemoved(parent: View, child: View) {
      if (parent === this@PresetRadioGroup && child is RadioCheckable) {
        (child as RadioCheckable).removeOnCheckChangeListener(mChildOnCheckedChangeListener!!)
      }
      mChildViewsMap.remove(child.id)
      mOnHierarchyChangeListener?.onChildViewRemoved(parent, child)
    }

    fun generateViewId(): Int {

      if (Build.VERSION.SDK_INT < 17) {
        while (true) {
          val result = sNextGeneratedId.get()
          // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
          var newValue = result + 1
          if (newValue > 0x00FFFFFF)
            newValue = 1 // Roll over to 1, not 0.
          if (sNextGeneratedId.compareAndSet(result, newValue)) {
            return result
          }
        }
      } else {
        return View.generateViewId()
      }

    }
  }
}
