package com.github.antoine_gnl.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chouaps on 09/12/17.
 */

public class WatchAdapter extends RecyclerView.Adapter<WatchAdapter.WatchAdapterViewHolder> {

    private String[] mWatchData;

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final WatchAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface WatchAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    // COMPLETED (4) Add a ForecastAdapterOnClickHandler as a parameter to the constructor and store it in mClickHandler

    /**
     * Creates a SensorTagAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public WatchAdapter(WatchAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }
    /**
     * Cache of the children views for a forecast list item.
     */
    public class WatchAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mWatchTextView;

        public WatchAdapterViewHolder(View view) {
            super(view);
            mWatchTextView= (TextView) view.findViewById(R.id.tv_watch_data);

            view.setOnClickListener(this);
        }
        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = mWatchData[adapterPosition];
            mClickHandler.onClick(weatherForDay);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new SensorAdapterViewHolder that holds the View for each list item
     */
    @Override
    public WatchAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.watch_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new WatchAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the sensed
     * acceleration by the watch for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param WatchAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(WatchAdapterViewHolder WatchAdapterViewHolder, int position) {
        String watchValue = mWatchData[position];
        WatchAdapterViewHolder.mWatchTextView.setText(watchValue);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our watch values
     */
    @Override
    public int getItemCount() {
        if (null == mWatchData) return 0;
        return mWatchData.length;
    }

    /**
     * This method is used to set the weather forecast on a SensorTagAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new SensorTagAdapter to display it.
     *
     * @param watchData The new weather data to be displayed.
     */
    public void setmWatchData(String[] watchData) {
        mWatchData = watchData;
        notifyDataSetChanged();
    }
}
