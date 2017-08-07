package demo.aspectjdemo;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Range;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import demo.aspectjdemo.aop.HookMethod;
import demo.aspectjdemo.aop.LogMethod;
import demo.aspectjdemo.aop.Trace;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashSet set = new HashSet<>();
        set.add("1");
        set.add("2");
        set.add("5");
        testAnnotationMethod(set);
    }

    @LogMethod
    private void testAnnotationMethod(HashSet set){
        try {
            int[] sample1 = new int[]{1,2,-20,35,0,2000,3};
            int[] sample2 = null;
            int[] result = getMaxN(sample1,3);
            result = getMaxN(sample1,10);
            result = getMaxN(sample1,0);
            result = getMaxN(sample2,2);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashSet set = new HashSet<>();
                set.add("1");
                set.add("2");
                set.add("5");
                testAnnotationMethod(set);
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this,AnotherActivity.class);
//                startActivity(intent);
            }
        });
        int result = MathKit.square(2);
        int result2 = MathKit.square(result);
    }


    int[] getMaxN(int[] arr, int k) {
        if(arr == null || k>arr.length || k<0)
            return null;
        Arrays.sort(arr);
        return Arrays.copyOfRange(arr,arr.length-k,arr.length);
    }



    private int[][] sample1 = {{1,3}, {3,5}, {1,4}, {6,10}, {10,11}};
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    int[][] merge(int[][] input){
        if(input.length<2)
            return input;
        ArrayList<MyRange> ranges = new ArrayList<>();
        ranges.add(new MyRange(input[0][0],input[0][1]));
        for(int i=1;i<input.length;i++){
            int begin = input[i][0];
            int end = input[i][1];
            MyRange anotherRange = new MyRange(begin,end);
            for(int j=0;j<ranges.size();j++){
                MyRange range = ranges.get(j);
                if(range.intersect(anotherRange) ){
                    ranges.remove(range);
                    ranges.add(range.extend(anotherRange));
                }
                else if(j==ranges.size()-1)
                    ranges.add(anotherRange);
            }
        }
        int[][] result = new int[ranges.size()][];
        for(int i=0;i<ranges.size();i++){
            result[i] = ranges.get(i).toIntArray();
        }
        return result;
    }

    public void testMerge(){
        int[][] result = merge(sample1);
        Log.d("",result.toString());

    }


    class MyRange {
        Range<Integer> range;
        public MyRange(int begin, int end){
            range = new Range(begin,end);
        }
        public MyRange(Range range){
            this.range = range;
        }


        public boolean intersect(MyRange another){
            if(range.getUpper().intValue()<another.getLower().intValue() || range.getLower().intValue()>another.getUpper().intValue())
                return false;
            else
                return true;
        }

        public MyRange extend(MyRange another){
            return new MyRange(range.extend(another.range));
        }

        public Integer getLower(){
            return range.getLower();
        }
        public Integer getUpper(){
            return range.getUpper();
        }

        public int[] toIntArray(){
            return new int[]{range.getLower(),range.getUpper()};
        }

    }

    @Override
    public void onPause(){
        super.onPause();
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
