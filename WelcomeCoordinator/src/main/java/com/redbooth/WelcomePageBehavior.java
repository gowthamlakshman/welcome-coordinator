package com.redbooth;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

public abstract class WelcomePageBehavior {
    private final static int NO_DESTINY_VIEW = -1;
    protected WelcomeCoordinatorLayout coordinatorLayout;
    private View targetView;
    private View destinyView;
    private WelcomePageLayout page;

    protected WelcomePageLayout getPage() {
        return page;
    }

    protected View getTargetView() {
        return targetView;
    }

    protected View getDestinyView() {
        if (targetView != null && destinyView == null && coordinatorLayout != null) {
            int destinyViewId = ((WelcomePageLayout.LayoutParams)targetView.getLayoutParams()).getDestinyViewId();
            if (destinyViewId != NO_DESTINY_VIEW) {
                destinyView = coordinatorLayout.findViewById(destinyViewId);
            }
        }
        return destinyView;
    }

    void setCoordinator(WelcomeCoordinatorLayout coordinator) {
        this.coordinatorLayout = coordinator;
        this.coordinatorLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        onCreate(coordinatorLayout);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            coordinatorLayout.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        } else {
                            coordinatorLayout.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                    }
                });
    }

    void setPage(WelcomePageLayout page) {
        this.page = page;
    }

    void setTarget(View target) {
        this.targetView = target;
    }

    protected abstract void onCreate(WelcomeCoordinatorLayout coordinator);

    protected abstract void onPlaytimeChange(WelcomeCoordinatorLayout coordinator,
                                             float newPlaytime, float newScrollPosition);
}