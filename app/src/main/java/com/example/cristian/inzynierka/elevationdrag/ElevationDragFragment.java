package com.example.cristian.inzynierka.elevationdrag;

/**
 * Created by Cristian on 2017-09-02.
 */


import android.graphics.Outline;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import com.example.cristian.inzynierka.R;

import java.util.Locale;

public class ElevationDragFragment extends Fragment {

    public static final String TAG = "ElevationDragFragment";

    /* The circular outline provider */
    private ViewOutlineProvider mOutlineProviderCircle;

    /* The current elevation of the floating view. */
    private float mElevation = 0;

    /* The step in elevation when changing the Z value */
    private int mElevationStep;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOutlineProviderCircle = new CircleOutlineProvider();

        mElevationStep = getResources().getDimensionPixelSize(R.dimen.elevation_step);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ztranslation, container, false);

        /* Find the {@link View} to apply z-translation to. */
        final View floatingShape = rootView.findViewById(R.id.circle);

        /* Define the shape of the {@link View}'s shadow by setting one of the {@link Outline}s. */
        floatingShape.setOutlineProvider(mOutlineProviderCircle);

        /* Clip the {@link View} with its outline. */
        floatingShape.setClipToOutline(true);

        DragFrameLayout dragLayout = ((DragFrameLayout) rootView.findViewById(R.id.main_layout));

        dragLayout.setDragFrameController(new DragFrameLayout.DragFrameLayoutController() {

            @Override
            public void onDragDrop(boolean captured) {
                /* Animate the translation of the {@link View}. Note that the translation
                 is being modified, not the elevation. */
                floatingShape.animate()
                        .translationZ(captured ? 50 : 0)
                        .setDuration(100);
            }
        });

        dragLayout.addDragView(floatingShape);


        return rootView;
    }

    /**
     * ViewOutlineProvider which sets the outline to be an oval which fits the view bounds.
     */
    private class CircleOutlineProvider extends ViewOutlineProvider {
        @Override
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, view.getWidth(), view.getHeight());
        }
    }

}