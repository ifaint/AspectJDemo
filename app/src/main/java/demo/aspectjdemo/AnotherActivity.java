package demo.aspectjdemo;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by zhangxiaofei on 2017/3/13.
 */

public class AnotherActivity extends Activity {


    @Override @DebugTrace
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_another);
    }

    @Override @DebugTrace
    public void onResume(){
        super.onResume();
    }

    @Override @DebugTrace
    public void onPause(){
        super.onPause();
    }

    @Override @DebugTrace
    public void onDestroy(){
        super.onDestroy();
    }
}
