package com.abhay23.notes.util;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class GrayFilterTouchImageView extends ImageView {

  private Rect rect;
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
    rect = new Rect();
    ColorMatrix grayScaleMatrix = new ColorMatrix();
    grayScaleMatrix.setSaturation(0);
    grayScaleFilter = new ColorMatrixColorFilter(grayScaleMatrix);

    ColorMatrix normalScaleMatrix = new ColorMatrix();
    grayScaleMatrix.setSaturation(1);
    normalScaleFilter = new ColorMatrixColorFilter(normalScaleMatrix);

    handleTouch();
  }

  private void handleTouch() {
    setOnTouchListener((view, event) -> {
      switch (event.getAction()) {

        case MotionEvent.ACTION_DOWN:
          rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
          GrayFilterTouchImageView.this.setColorFilter(grayScaleFilter);
          break;

        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
          GrayFilterTouchImageView.this.setColorFilter(normalScaleFilter);
          break;

        case MotionEvent.ACTION_MOVE:
          if (!isTouchWithinBounds(view, event)) {
            GrayFilterTouchImageView.this.setColorFilter(normalScaleFilter);
          }
          break;
      }
      return super.onTouchEvent(event);
    });
  }

  private boolean isTouchWithinBounds(View view, MotionEvent event) {
    return rect.contains(view.getLeft() + (int) event.getX(), view.getTop() + (int) event.getY());
  }
}
