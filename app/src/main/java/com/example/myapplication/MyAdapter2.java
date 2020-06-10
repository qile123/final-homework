package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {
    private List<VideoMessage> mDataset;
    Context mcontext;

    static class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;
        TextView like;
        ImageButton cover;
        ImageView start;

        MyViewHolder2(View v) {
            super(v);
            title = v.findViewById(R.id.tv_title2);
            author = v.findViewById(R.id.tv_author2);
            like = v.findViewById(R.id.tv_like2);
            cover = v.findViewById(R.id.ibtn_cover2);
            start = v.findViewById(R.id.iv_start2);
        }
    }


     void setData(List<VideoMessage> myDataset) {
        mDataset = myDataset;
    }
     void setContext(Context context) {
        mcontext = context;
    }

    @NonNull
    @Override
    public MyAdapter2.MyViewHolder2 onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item2, parent, false);
        MyViewHolder2 vh = new MyViewHolder2(v);
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder2 holder, final int position) {
        holder.title.setText(mDataset.get(position).description);
        holder.author.setText(mDataset.get(position).nickname + "的小视频");
        holder.start.setAlpha(150);
        Glide.with(mcontext)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                )
                .load(mDataset.get(position).feedUrl)
                .into(holder.cover);
        holder.like.setText("点赞：" + Integer.toString(mDataset.get(position).likeCount));
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "url:" + position, Toast.LENGTH_SHORT).show();
                Log.d("URL", mDataset.get(position).feedUrl);//视频地址
                String temp = mDataset.get(position).feedUrl;
                Log.d("URL", temp);//视频地址

                Intent intent = new Intent(mcontext, VideoPlayer.class);//选择跳转到的页面
                intent.putExtra("url",temp);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        };
        holder.cover.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

}
