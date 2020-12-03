package com.e.workshift;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DayView extends androidx.appcompat.widget.AppCompatTextView {
    private Date date;
    private List<DayDecorator> decorators;

    public DayView(Context context) {
        this(context, null, 0);
    }

    public DayView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DayView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (VERSION.SDK_INT < 3 || isInEditMode()) {
        }
    }

    public void bind(Date date2, List<DayDecorator> list) {
        this.date = date2;
        this.decorators = list;
        setText(String.valueOf(Integer.parseInt(new SimpleDateFormat("d").format(date2))));
    }

    public void decorate() {
        List<DayDecorator> list = this.decorators;
        if (list != null) {
            for (DayDecorator decorate : list) {
                decorate.decorate(this);
            }
        }
    }

    public Date getDate() {
        return this.date;
    }
}
