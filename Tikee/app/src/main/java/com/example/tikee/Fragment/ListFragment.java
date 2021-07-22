package com.example.tikee.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.tikee.PostResultMessageLab;
import com.example.tikee.R;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    public MyListAdapter mAdapter;



    private String mParam;
    private Activity mActivity;
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mParam = getArguments().getString("param_key");  //获取参数
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        //TextView view = root.findViewById(R.id.textView4);
        //view.setText(mParam);
        mRecyclerView = root.findViewById(R.id.recycler);
        return root;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        mRecyclerView.setLayoutManager(manager);
        int divider = dp2px(getContext(),4);
        RecyclerView.ItemDecoration gridItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                int spanIndex = layoutParams.getSpanIndex();
                int position = parent.getChildAdapterPosition(view);
                outRect.bottom = divider;
                if (position == 0 || position == 1) {
                    outRect.top = divider * 2;
                } else {
                    outRect.top = 0;
                }
                if (spanIndex % 2 == 0) {//偶数项
                    outRect.left = divider;
                    outRect.right = divider / 2;
                } else {
                    outRect.left = divider / 2;
                    outRect.right = divider;
                }
            }
        };

        mRecyclerView.addItemDecoration(gridItemDecoration);
        mAdapter = new MyListAdapter(new ArrayList<>());
        PostResultMessageLab.getData(getContext(), mAdapter);
        mRecyclerView.setAdapter(mAdapter);
        handler.post(runnable);

    }
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            PostResultMessageLab.updateData(getContext(), mAdapter);
                handler.postDelayed(this,500);

    }
    };
    public  void onPause() {
        super.onPause();
        Log.d("info", "Pause");
        handler.removeCallbacks(runnable);
    }
    public void onResume() {
        super.onResume();
        Log.d("info", "Resume");
        handler.post(runnable);
    }

    public static ListFragment newInstance(String str) {
        ListFragment frag = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param_key", str);
        frag.setArguments(bundle);   //设置参数
        return frag;
    }

    private RecyclerView mRecyclerView;

    private int dp2px(Context ctx, float dp) {
        float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

