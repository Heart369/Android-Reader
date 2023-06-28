package com.exam.zhouyaosen.main.tool;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
    private final boolean mIncludePad;
    private final int mWidth;
    private final int mHeight;
    private final float mSpacingMult;
    private final float mSpacingAdd;
    private final CharSequence mText;
    private final TextPaint mPaint;
    private final List mPages;

    public Pagination(CharSequence text, int pageW, int pageH, TextPaint paint, float spacingMult, float spacingAdd, boolean inclidePad) {
        this.mText = text;
        this.mWidth = pageW;
        this.mHeight = pageH;
        this.mPaint = paint;
        this.mSpacingMult = spacingMult;
        this.mSpacingAdd = spacingAdd;
        this.mIncludePad = inclidePad;
        this.mPages = new ArrayList<>();
        layout();
    }

    private void layout() {
        final StaticLayout layout = new StaticLayout(mText, mPaint, mWidth, Layout.Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, mIncludePad);
        final int lines = layout.getLineCount();
        final CharSequence text = layout.getText();
        int startOffset = 0;
        int height = mHeight;

        for (int i = 0; i < lines; i++) {
            if (height < layout.getLineBottom(i) && startOffset != layout.getLineStart(i)) {
                // 当超过页面高度时，将文本添加到新的页面
                addPage(text.subSequence(startOffset, layout.getLineStart(i)));
                startOffset = layout.getLineStart(i);
                height = layout.getLineTop(i) + mHeight;
            }

            if (i == lines - 1) {
                // 将剩余的文本添加到最后一页
                addPage(text.subSequence(startOffset, layout.getLineEnd(i)));
                return;
            }
        }
    }
    private void addPage(CharSequence text) {
        mPages.add(text);
    }

    public int size() {
        return mPages.size();
    }

    public CharSequence get(int index) {
        return (index >= 0 && index < mPages.size()) ? (CharSequence) mPages.get(index) : null;
    }
}
