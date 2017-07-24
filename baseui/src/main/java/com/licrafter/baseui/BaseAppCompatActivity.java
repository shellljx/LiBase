package com.licrafter.baseui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.licrafter.baseui.utils.StatusBarUtils;

import butterknife.ButterKnife;

/**
 * Created by lijx on 2017/7/24.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        StatusBarUtils.initStatusBar(this);
        ButterKnife.bind(this);
        initView(savedInstanceState);
        setListeners();
        bind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind();
    }

    /**
     * 返回content layout id
     *
     * @return
     */
    public abstract int getContentView();

    public abstract void initView(Bundle savedInstanceState);

    public abstract void setListeners();

    public abstract void bind();

    public abstract void unBind();
}
