/*
 * Copyright (c) wengelef 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wengelef.floatingactionmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fwengelewski on 2/15/16.
 */
public class FloatingActionMenu extends FrameLayout {

    public enum State {
        EXPANDED(R.drawable.ic_clear_white_24dp), COLLAPSED(R.drawable.ic_add_white_24dp);

        @DrawableRes int mBackgroundResource;

        State(@DrawableRes int bgResource) {
            mBackgroundResource = bgResource;
        }

        @DrawableRes int getBackgroundResource() {
            return mBackgroundResource;
        }
    }

    @NonNull
    private List<FloatingActionButton> mFabs = new ArrayList<>();
    @Nullable
    private FloatingActionButton mMenuButton;

    private State mState = State.COLLAPSED;

    private int mMarginBottom;
    private int mMarginEnd;
    private int mVerticalPadding = 20;

    public FloatingActionMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @NonNull AttributeSet attrs) {
        setClickable(false);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionMenu);

        mMarginBottom = (int) a.getDimension(R.styleable.FloatingActionMenu_mainMarginBottom, 0);
        mMarginEnd = (int) a.getDimension(R.styleable.FloatingActionMenu_mainMarginEnd, 0);
        mVerticalPadding = (int) a.getDimension(R.styleable.FloatingActionMenu_verticalPadding, 0);

        a.recycle();

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mMenuButton != null) {
                    mMenuButton.setY(getMeasuredHeight() - mMarginBottom);
                    mMenuButton.setX(getMeasuredWidth() - mMarginEnd);

                    FloatingActionMenu.this.requestLayout();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        mMenuButton = new FloatingActionButton(context, attrs);
        mMenuButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mMenuButton.setOnClickListener(getOnClickListener());
        mMenuButton.setImageDrawable(getResources().getDrawable(mState.getBackgroundResource()));

        addView(mMenuButton);
    }

    public void addFab(MenuAction menuAction) {
        FloatingActionButton fab = new FloatingActionButton(getContext(), null);
        fab.setOnClickListener(menuAction.mActionClickListener);
        fab.setImageDrawable(getResources().getDrawable(menuAction.mActionIcon));
        fab.setVisibility(GONE);
        fab.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(fab);
        mFabs.add(fab);
    }

    public void expand() {
        mState = getIsExpanded() ? State.COLLAPSED : State.EXPANDED;

        if (mMenuButton != null) {
            mMenuButton.setImageDrawable(getResources().getDrawable(mState.getBackgroundResource()));

            for (final FloatingActionButton fab : mFabs) {
                fab.setVisibility(VISIBLE);
                int fabPositionY = (int) mMenuButton.getY() - ((mFabs.indexOf(fab) + 1) * mMenuButton.getHeight()) - ((mFabs.indexOf(fab) + 1) * mVerticalPadding);
                fab.setX(mMenuButton.getX());

                fab.setY(getIsExpanded() ? mMenuButton.getY() : fabPositionY);

                fab.animate()
                        .y(getIsExpanded() ? fabPositionY : mMenuButton.getY())
                        .alpha(getIsExpanded() ? 1.0f : 0.0f)
                        .setListener(getAnimatorListener(fab))
                        .start();
            }
        }
    }

    public boolean getIsExpanded() {
        return mState == State.EXPANDED;
    }

    public View.OnClickListener getOnClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                expand();
            }
        };
    }

    public AnimatorListenerAdapter getAnimatorListener(final FloatingActionButton fab) {
        return new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fab.setVisibility(getIsExpanded() ? VISIBLE : GONE);
            }
        };
    }

    public static final class MenuAction {
        @NonNull View.OnClickListener mActionClickListener;
        @DrawableRes int mActionIcon;

        public MenuAction(@NonNull OnClickListener actionClicklistener, @DrawableRes int actionIcon) {
            this.mActionClickListener = actionClicklistener;
            this.mActionIcon = actionIcon;
        }
    }
}