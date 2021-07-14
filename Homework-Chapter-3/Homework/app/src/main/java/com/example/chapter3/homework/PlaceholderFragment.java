package com.example.chapter3.homework;


import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.math.MathContext;

public class PlaceholderFragment extends Fragment {

    private LottieAnimationView mLottieAnimationView;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View view=inflater.inflate(R.layout.fragment_placeholder, container, false);
        mLottieAnimationView=view.findViewById(R.id.ani);
        mRecyclerView=view.findViewById(R.id.recycler);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入

                mRecyclerView.setAlpha(0f);
                mRecyclerView.setVisibility(View.VISIBLE);
                mRecyclerView.animate().alpha(1f).setDuration(1000).setListener(null);

                mLottieAnimationView.animate().alpha(0f).setDuration(1000).setListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        mLottieAnimationView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

                initView();

            }
        }, 5000);
    }
    private void initView(){
        //更改数据时不会变更宽高
        mRecyclerView.setHasFixedSize(true);
        //创建线性布局管理器
        layoutManager = new LinearLayoutManager(this.getActivity());
        //设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //创建Adapter
        mAdapter = new MyAdapter(TestDataSet.getData());
        //设置Adapter
        mRecyclerView.setAdapter(mAdapter);
        //分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL));

    }
}
