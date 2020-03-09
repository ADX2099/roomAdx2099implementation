package com.adx2099.roomadx2099example;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.adx2099.roomadx2099example.database.GameEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyy";
    final private ItemClickListener mItemClickListener;

    private List<GameEntry> mGameEntries;

    private Context mContext;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public GameAdapter(ItemClickListener mItemClickListener, Context mContext) {
        this.mItemClickListener = mItemClickListener;
        this.mContext = mContext;
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }


    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.game_layout, parent, false);

        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        GameEntry gameEntry = mGameEntries.get(position);
        String description = gameEntry.getDescription();
        int owned =gameEntry.getOwned();

        String updatedAt = dateFormat.format(gameEntry.getUpdatedAt());

        holder.gameDescriptionView.setText(description);
        holder.updatedAtView.setText(updatedAt);
        String ownedString = "" + owned;
        holder.ownTextView.setText(ownedString);

        GradientDrawable ownedCircle = (GradientDrawable) holder.ownTextView.getBackground();
        int ownedColor = getOwnedColor(owned);
        ownedCircle.setColor(ownedColor);


    }

    private int getOwnedColor(int owned) {
        int ownedColor = 0;

        switch (owned){
            case 1:
                owned = ContextCompat.getColor(mContext, R.color.materialGreen);
                break;
            case 2:
                owned = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
        }

        return ownedColor;
    }

    public void setGames(List<GameEntry> gameEntries){
        mGameEntries = gameEntries;
        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        if (mGameEntries == null) {
            return 0;
        }
        return mGameEntries.size();
    }

    public List<GameEntry> getGames(){
        return mGameEntries;
    }

    public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView gameDescriptionView;
        TextView ownTextView;
        TextView updatedAtView;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);

            gameDescriptionView = itemView.findViewById(R.id.gameDescription);
            ownTextView = itemView.findViewById(R.id.ownTextView);
            updatedAtView = itemView.findViewById(R.id.gameUpdatedAt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int elementId= mGameEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
