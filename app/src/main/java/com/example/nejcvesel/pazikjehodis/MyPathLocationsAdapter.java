package com.example.nejcvesel.pazikjehodis;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.nejcvesel.pazikjehodis.retrofitAPI.BackendAPICall;
import com.example.nejcvesel.pazikjehodis.retrofitAPI.Models.Location;
import com.example.nejcvesel.pazikjehodis.retrofitAPI.Models.Path;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nejcvesel on 15/02/17.
 */

public class MyPathLocationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> items;

    private final int LOCATION = 0, PATH = 1;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyPathLocationsAdapter(List<Object> items) {
        this.items = items;
    }

    public MyPathLocationsAdapter()
    {
        this.items = new ArrayList<Object>();
    }

    public void addData(Object obj) {
        items.add(obj);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Location) {
            return LOCATION;
        } else if (items.get(position) instanceof Path) {
            return PATH;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case LOCATION:
                View v1 = inflater.inflate(R.layout.path_locations_viewholder1, viewGroup, false);
                viewHolder = new PathLocationsViewHolder1(v1);
                break;
            case PATH:
                View v2 = inflater.inflate(R.layout.path_locations_viewholder2, viewGroup, false);
                viewHolder = new PathLocationsViewHolder2(v2);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new PathLocationsViewHolder1(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case LOCATION:
                PathLocationsViewHolder1 vh1 = (PathLocationsViewHolder1) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case PATH:
                PathLocationsViewHolder2 vh2 = (PathLocationsViewHolder2) viewHolder;
                configureViewHolder2(vh2, position);
                break;
            default:
                PathLocationsViewHolder1 vh = (PathLocationsViewHolder1) viewHolder;
                configureViewHolder1(vh, position);
                break;
        }
    }

    private void configureViewHolder1(PathLocationsViewHolder1 vh1, int position) {
        Location loc = (Location) items.get(position);
        if (loc != null) {
            vh1.getAddress().setText(loc.getAddress());
            vh1.getName().setText(loc.getName());
            vh1.getTitle().setText(loc.getTitle());

            Context context = vh1.getImg().getContext();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            Picasso.with(context).load("http://10.0.2.2:8000/"+ BackendAPICall.repairURL(loc.getPicture()))
                    .resize(width-40,(int)(height/2.5f))
                    .centerCrop()
                    .into(vh1.getImg());

        }
    }

    private void configureViewHolder2(PathLocationsViewHolder2 vh2, int position) {
        Path path = (Path) items.get(position);
        if (path != null)
        {
            vh2.getPath_owner().setText(path.getOwner());
            vh2.getPath_description().setText(path.getDescription());
            vh2.getPathCity().setText(path.getCity());
            vh2.getPathName().setText(path.getName());
            vh2.getPathNumOfLocations().setText(String.valueOf(path.getPathLocations().size()));

        }

    }

}