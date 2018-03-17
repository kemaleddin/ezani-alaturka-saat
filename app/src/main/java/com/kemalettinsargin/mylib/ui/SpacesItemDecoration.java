package com.kemalettinsargin.mylib.ui;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int orientation = LinearLayoutManager.VERTICAL;
    private boolean hasHeader;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    public SpacesItemDecoration(int space, boolean hasHeader) {
        this.space = space;
        this.hasHeader = hasHeader;
    }


    public SpacesItemDecoration setOrientation(int orientation) {
        this.orientation = orientation;
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) == 0 && hasHeader) {
            switch (orientation) {
                case LinearLayoutManager.HORIZONTAL:
                    outRect.right=space;
                    return;
                case LinearLayoutManager.VERTICAL:
                    outRect.bottom = space;
                    return;
            }

        }

        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;
        switch (orientation) {
            case LinearLayoutManager.HORIZONTAL:
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.left = space;
                } else {
                    outRect.left = 0;
                }
                break;
            case LinearLayoutManager.VERTICAL:
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = space;
                } else {
                    outRect.top = 0;
                }
                break;
        }
    }
}