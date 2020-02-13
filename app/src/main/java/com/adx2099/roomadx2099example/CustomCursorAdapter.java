package com.adx2099.roomadx2099example;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.adx2099.roomadx2099example.data.GameContract;

public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.TaskViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public CustomCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.game_layout, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomCursorAdapter.TaskViewHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(GameContract.GameEntry._ID);
        int descriptionIndex = mCursor.getColumnIndex(GameContract.GameEntry.COLUMN_DESCRIPTION);
        int priorityIndex = mCursor.getColumnIndex(GameContract.GameEntry.COLUMN_OWNED);
        mCursor.moveToPosition(position);

        final int id = mCursor.getInt(idIndex);
        String description = mCursor.getString(descriptionIndex);
        int priority = mCursor.getInt(priorityIndex);

        holder.itemView.setTag(id);
        holder.gameDescriptionView.setText(description);

        String priorityString = "" + priority; // converts int to String
        holder.ownedView.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.ownedView.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getOwnedColor(priority);
        priorityCircle.setColor(priorityColor);

    }

    private int getOwnedColor(int owned) {
        int ownedColor = 0;
        switch(owned) {
            case 1: ownedColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2: ownedColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3: ownedColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default: break;
        }
        return ownedColor;

    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView gameDescriptionView;
        TextView ownedView;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            gameDescriptionView = (TextView) itemView.findViewById(R.id.gameDescription);
            ownedView = (TextView) itemView.findViewById(R.id.ownTextView);
        }
    }

}
