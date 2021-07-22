package com.example.tikee.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.tikee.R;
import com.example.tikee.Utils.GetResultMessageCallback;
import com.example.tikee.Utils.PostResultMessage;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> implements GetResultMessageCallback<PostResultMessage> {


    private List<PostResultMessage> data;
    public static final int Pre_Num = 6;

    public MyListAdapter(List<PostResultMessage> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PostResultMessage item = data.get(position);

        int width = holder.mThumb.getContext().getResources().getDisplayMetrics().widthPixels / 2;
        int height = (int) (width * (float) item.getImage_h() / (float) item.getImage_w());
        ViewGroup.LayoutParams layoutParams = holder.mThumb.getLayoutParams();
        layoutParams.height = height;
        holder.mThumb.setLayoutParams(layoutParams);
        holder.setPos(position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    @Override
    public void setData(List<PostResultMessage> item) {
        data = item;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mThumb;
        private TextView mTitle;
        private TextView mContent;
        public int mPosition;


        //设置pos
        public void setPos(int pos) {
            mPosition = pos;
        }

        //绑定item
        public void bindItem(PostResultMessage item) {


//            mTitle.setText(item.getTitle());
            Glide.with(mThumb.getContext())
                    .load(item.getImageUrl())
                    .placeholder(new ColorDrawable(Color.WHITE))
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    //.error(R.drawable.icon_error)
//                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))

                    .transition(DrawableTransitionOptions.withCrossFade())
                    .transform(new RoundedCorners(10))
                    .into(mThumb);

            if (mPosition + Pre_Num < getItemCount()) {
                String next_url = data.get(mPosition + Pre_Num).getImageUrl();
                Glide.with(mThumb.getContext()).load(next_url).diskCacheStrategy(DiskCacheStrategy.DATA).preload();
            }
            mTitle.setText(item.getExtraValue());
            mContent.setText(item.getTime().substring(0,10));
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mThumb = itemView.findViewById(R.id.iv_thumb);
            mTitle = itemView.findViewById(R.id.tv_title);
            mContent = itemView.findViewById(R.id.tv_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}


