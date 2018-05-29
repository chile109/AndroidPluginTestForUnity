package com.example.unity;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

public class FragmentDemo extends Fragment {
    private static final String TAG = "MyPlugin";
    private static FragmentDemo Instance = null;
    private String gameObjectName;

    public static FragmentDemo GetInstance(String gameObject) {
        if (Instance == null) {
            Instance = new FragmentDemo();
            Instance.gameObjectName = gameObject;
            UnityPlayer.currentActivity.getFragmentManager().beginTransaction().add(Instance, TAG).commit();
        }
        return Instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);  // 这一句很重要，保存对该Fragment的引用，防止在旋转屏幕等操作时时丢失引用（Fragment隶属于Activity）

        Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
    }


    //示例方法一：简单的向Unity回调
    public void SayHello() {
        UnityPlayer.UnitySendMessage(gameObjectName, "PluginCallBack", "Hello Unity!");
    }

    //示例方法二：计算传入的参数并返回计算结果
    public int CalculateAdd(int one, int another) {
        return one + another;
    }
}