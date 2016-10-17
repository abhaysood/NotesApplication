package com.abhay23.notes.util;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class GrayFilterTouchImageView extends ImageView {

  private ColorMatrixColorFilter grayScaleFilter;
  private ColorMatrixColorFilter normalScaleFilter;

  public GrayFilterTouchImageView(Context context) {
    super(context);
    init();
  }

  public GrayFilterTouchImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public GrayFilterTouchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    ColorMatrix grayScaleMatrix = new ColorMatrix();
    grayScaleMatrix.setSaturation(0);
    grayScaleFilter = new ColorMatrixColorFilter(grayScaleMatrix);

    ColorMatrix normalScaleMatrix = new ColorMatrix();
    grayScaleMatrix.setSaturation(1);
    normalScaleFilter = new ColorMatrixColorFilter(normalScaleMatrix);

    setOnTouchListener(imageViewOnTouchListener);
  }

  View.OnTouchListener imageViewOnTouchListener = (v, event) -> {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      setColorFilter(grayScaleFilter);
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      setColorFilter(normalScaleFilter);
    }

    return super.onTouchEvent(event);
  };
}
