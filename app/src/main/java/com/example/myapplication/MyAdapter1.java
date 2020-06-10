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

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder> {
    private List<VideoMessage> mDataset;
    private Context mcontext;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;
        TextView like;
        ImageButton cover;
        ImageView start;


        MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.tv_title);
            author = v.findViewById(R.id.tv_author);
            like = v.findViewById(R.id.tv_like);
            cover = v.findViewById(R.id.btn_cover);
            start = v.findViewById(R.id.iv_start);
        }
    }


    void setData(List<VideoMessage> myDataset) {
        mDataset = myDataset;
    }
    void setContext(Context context) {
        mcontext = context;
    }

    @Override
    public MyAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item1, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
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
//                Bundle bundle = new Bundle();
//                bundle.putString("mURL",temp);
//                intent.putExtras(bundle);
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
