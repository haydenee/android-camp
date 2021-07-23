package com.example.tikee.Utils;

import android.content.Context;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tikee.Fragment.MyListAdapter;
import com.example.tikee.Net.ResultMessageGetHttp;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class PostResultMessageLab {
    private static PostResultMessageLab sPostResultMessageLab;
    private static List<PostResultMessage> mItems;

    private static List<GetResultMessageCallback>cbLists;


    public static void getData(Context context, GetResultMessageCallback<PostResultMessage>cb){
        if(sPostResultMessageLab==null){
            sPostResultMessageLab=new PostResultMessageLab(context);
            updateData(context,cb);//需要update
        } else{
            cb.setData(mItems);    //直接callback
        }
    }
    private PostResultMessageLab(Context context){
        mItems=new ArrayList<>();
        cbLists=new ArrayList<>();
    }
    public static int getSize(){
        if(sPostResultMessageLab==null){
            return  0;
        }
        return mItems.size();
    }
    public static void updateData(Context context,GetResultMessageCallback<PostResultMessage>cb){
       new Thread(new Runnable() {
           @Override
           public void run() {
               List<PostResultMessage>res= ResultMessageGetHttp.getData(Constants.studentId);
               if(res!=null){
                   mItems=res;
                   new Handler(context.getMainLooper()).post(new Runnable() {
                       @Override
                       public void run() {
                           cb.setData(res);//回调进行设置
                           for(int i = 0; i< min(res.size(), MyListAdapter.Pre_Num); i++){
                               String next_url=res.get(i).getImageUrl();
                               Glide.with(context).load(next_url).diskCacheStrategy(DiskCacheStrategy.DATA).preload();
                           }
                       }
                   });
               }

           }
       }).start();
    }


}
