package com.decode.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.LAUNCHER_APPS_SERVICE;
import static com.decode.gallery.GalleryActivity.PREVIEW_REQUEST_TYPE;

/**
 * Created by Trifa on 2/28/2018.
 */

public class GalleryFragment extends Fragment implements View.OnClickListener {

    private int type;
    private Button previewButton;
    private RecyclerView recycleView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        recycleView = root.findViewById(R.id.recycler_view);
        type = getArguments() != null ?
                getArguments().getInt("type", 0) : 0;
        recycleView.setAdapter(new Adapter(type));
        recycleView.setLayoutManager(new GridLayoutManager(getContext(),3));
        return root;
    }

    @Override
    public void onClick(View view) {
        if (getActivity() instanceof ICallback && !getActivity().isDestroyed() && !getActivity().isFinishing()) {
            ((ICallback) getActivity()).preview((Media) view.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mLabel;
        public SquareRelativeLayout mSquare;

        public ViewHolder(View v) {
            super(v);
            mLabel = v.findViewById(R.id.imageText);
            mSquare = v.findViewById(R.id.item_media);
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private Media[] mMedia;

        @Override
        public ViewHolder onCreateViewHolder(
                ViewGroup parent, int viewType) {
            LayoutInflater in = LayoutInflater.from(getContext());
            View v = in.inflate(R.layout.item_media,
                    parent, false);
            return new ViewHolder(v);
        }

        private Adapter(int type) {
            mMedia = Media.getMedia(type);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mLabel.setText(mMedia[position].getName());
            holder.mSquare.setBackgroundColor(mMedia[position].getColor());
            holder.itemView.setTag(mMedia[position]);
            holder.itemView.setOnClickListener(GalleryFragment.this);
        }

        @Override
        public int getItemCount() {
            return mMedia.length;
        }
    }

}
