package com.e.workshift;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

public class TouchImageView extends AppCompatImageView {
    static final int CLICK = 3;
    static final int DRAG = 1;
    static final int NONE = 0;
    static final int ZOOM = 2;
    Context context;
    PointF last = new PointF();
    float[] m;
    ScaleGestureDetector mScaleDetector;
    Matrix matrix;
    float maxScale = 3.0f;
    float minScale = 1.0f;
    int mode = 0;
    int oldMeasuredHeight;
    int oldMeasuredWidth;
    protected float origHeight;
    protected float origWidth;
    float saveScale = 1.0f;
    PointF start = new PointF();
    int viewHeight;
    int viewWidth;

    private class ScaleListener extends SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            TouchImageView.this.mode = 2;
            return true;
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float f;
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            float f2 = TouchImageView.this.saveScale;
            TouchImageView.this.saveScale *= scaleFactor;
            if (TouchImageView.this.saveScale > TouchImageView.this.maxScale) {
                TouchImageView touchImageView = TouchImageView.this;
                touchImageView.saveScale = touchImageView.maxScale;
                f = TouchImageView.this.maxScale;
            } else {
                if (TouchImageView.this.saveScale < TouchImageView.this.minScale) {
                    TouchImageView touchImageView2 = TouchImageView.this;
                    touchImageView2.saveScale = touchImageView2.minScale;
                    f = TouchImageView.this.minScale;
                }
                if (TouchImageView.this.origWidth * TouchImageView.this.saveScale > ((float) TouchImageView.this.viewWidth) || TouchImageView.this.origHeight * TouchImageView.this.saveScale <= ((float) TouchImageView.this.viewHeight)) {
                    TouchImageView.this.matrix.postScale(scaleFactor, scaleFactor, (float) (TouchImageView.this.viewWidth / 2), (float) (TouchImageView.this.viewHeight / 2));
                } else {
                    TouchImageView.this.matrix.postScale(scaleFactor, scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                }
                TouchImageView.this.fixTrans();
                return true;
            }
            scaleFactor = f / f2;
            if (TouchImageView.this.origWidth * TouchImageView.this.saveScale > ((float) TouchImageView.this.viewWidth)) {
            }
            TouchImageView.this.matrix.postScale(scaleFactor, scaleFactor, (float) (TouchImageView.this.viewWidth / 2), (float) (TouchImageView.this.viewHeight / 2));
            TouchImageView.this.fixTrans();
            return true;
        }
    }

    /* access modifiers changed from: 0000 */
    public float getFixDragTrans(float f, float f2, float f3) {
        if (f3 <= f2) {
            return 0.0f;
        }
        return f;
    }

    /* access modifiers changed from: 0000 */
    public float getFixTrans(float f, float f2, float f3) {
        float f4;
        float f5;
        if (f3 <= f2) {
            f4 = f2 - f3;
            f5 = 0.0f;
        } else {
            f5 = f2 - f3;
            f4 = 0.0f;
        }
        if (f < f5) {
            return (-f) + f5;
        }
        if (f > f4) {
            return (-f) + f4;
        }
        return 0.0f;
    }

    public TouchImageView(Context context2) {
        super(context2);
        sharedConstructing(context2);
    }

    public TouchImageView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        sharedConstructing(context2);
    }

    private void sharedConstructing(Context context2) {
        super.setClickable(true);
        this.context = context2;
        this.mScaleDetector = new ScaleGestureDetector(context2, new ScaleListener());
        this.matrix = new Matrix();
        this.m = new float[9];
        setImageMatrix(this.matrix);
        setScaleType(ScaleType.MATRIX);
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                TouchImageView.this.mScaleDetector.onTouchEvent(motionEvent);
                PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
                int action = motionEvent.getAction();
                if (action == 0) {
                    TouchImageView.this.last.set(pointF);
                    TouchImageView.this.start.set(TouchImageView.this.last);
                    TouchImageView.this.mode = 1;
                } else if (action == 1) {
                    TouchImageView.this.mode = 0;
                    int abs = (int) Math.abs(pointF.x - TouchImageView.this.start.x);
                    int abs2 = (int) Math.abs(pointF.y - TouchImageView.this.start.y);
                    if (abs < 3 && abs2 < 3) {
                        TouchImageView.this.performClick();
                    }
                } else if (action != 2) {
                    if (action == 6) {
                        TouchImageView.this.mode = 0;
                    }
                } else if (TouchImageView.this.mode == 1) {
                    float f = pointF.x - TouchImageView.this.last.x;
                    float f2 = pointF.y - TouchImageView.this.last.y;
                    TouchImageView touchImageView = TouchImageView.this;
                    float fixDragTrans = touchImageView.getFixDragTrans(f, (float) touchImageView.viewWidth, TouchImageView.this.origWidth * TouchImageView.this.saveScale);
                    TouchImageView touchImageView2 =TouchImageView.this;
                    TouchImageView.this.matrix.postTranslate(fixDragTrans, touchImageView2.getFixDragTrans(f2, (float) touchImageView2.viewHeight, TouchImageView.this.origHeight * TouchImageView.this.saveScale));
                    TouchImageView.this.fixTrans();
                    TouchImageView.this.last.set(pointF.x, pointF.y);
                }
                    TouchImageView touchImageView3 = TouchImageView.this;
                touchImageView3.setImageMatrix(touchImageView3.matrix);
                TouchImageView.this.invalidate();
                return true;
            }
        });
    }

    public void setMaxZoom(float f) {
        this.maxScale = f;
    }

    /* access modifiers changed from: 0000 */
    public void fixTrans() {
        this.matrix.getValues(this.m);
        float[] fArr = this.m;
        float f = fArr[2];
        float f2 = fArr[5];
        float fixTrans = getFixTrans(f, (float) this.viewWidth, this.origWidth * this.saveScale);
        float fixTrans2 = getFixTrans(f2, (float) this.viewHeight, this.origHeight * this.saveScale);
        if (fixTrans != 0.0f || fixTrans2 != 0.0f) {
            this.matrix.postTranslate(fixTrans, fixTrans2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.viewWidth = MeasureSpec.getSize(i);
        this.viewHeight = MeasureSpec.getSize(i2);
        int i3 = this.oldMeasuredHeight;
        if (!(i3 == this.viewWidth && i3 == this.viewHeight)) {
            int i4 = this.viewWidth;
            if (i4 != 0) {
                int i5 = this.viewHeight;
                if (i5 != 0) {
                    this.oldMeasuredHeight = i5;
                    this.oldMeasuredWidth = i4;
                    if (this.saveScale == 1.0f) {
                        Drawable drawable = getDrawable();
                        if (drawable != null && drawable.getIntrinsicWidth() != 0 && drawable.getIntrinsicHeight() != 0) {
                            int intrinsicWidth = drawable.getIntrinsicWidth();
                            float f = (float) intrinsicWidth;
                            float intrinsicHeight = (float) drawable.getIntrinsicHeight();
                            float min = Math.min(((float) this.viewWidth) / f, ((float) this.viewHeight) / intrinsicHeight);
                            this.matrix.setScale(min, min);
                            float f2 = (((float) this.viewHeight) - (intrinsicHeight * min)) / 2.0f;
                            float f3 = (((float) this.viewWidth) - (min * f)) / 2.0f;
                            this.matrix.postTranslate(f3, f2);
                            this.origWidth = ((float) this.viewWidth) - (f3 * 2.0f);
                            this.origHeight = ((float) this.viewHeight) - (f2 * 2.0f);
                            setImageMatrix(this.matrix);
                        } else {
                            return;
                        }
                    }
                    fixTrans();
                }
            }
        }
    }
}
