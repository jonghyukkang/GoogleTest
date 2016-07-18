package kang.test01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

// 1. 아들이 나오면 다음 집, 딸이 나오면 아들이 나올 때까지 계속
// 2. 자식 수가 10000명이 되면 각 집마다 아들,딸의 개수 구하기

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int MEN = 1;
    private static final int WOMEN = 0;
    private static final int BABY_LIMIT = 10000;
    private Random random = new Random();
    private House mHouse;
    private ArrayList<House> mHouseNumber = new ArrayList<>();
    private int mHomeCount = 0;
    private int mTotalMen = 0;
    private int mTotalWomen = 0;
    private float MenAverage = 0;
    private int mCurrentWomen, mCurrentMen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv_sumHome = (TextView)findViewById(R.id.sumHome);
        final TextView tv_sumWomen = (TextView)findViewById(R.id.sumWomen);
        final TextView tv_sumMen = (TextView)findViewById(R.id.sumMen);
        final TextView tv_menAverage = (TextView)findViewById(R.id.menAverage);
        final TextView tv_infoMen = (TextView)findViewById(R.id.info_men);
        final TextView tv_infoWomen = (TextView)findViewById(R.id.info_women);
        final TextView tv_womenNumber = (TextView)findViewById(R.id.women_number);
        final TextView tv_menNumber = (TextView)findViewById(R.id.men_number);
        final Button btn_reset = (Button)findViewById(R.id.btn_reset);
        final Button btn_homeInfo = (Button)findViewById(R.id.btn_homeInfo);
        final EditText edit_home = (EditText) findViewById(R.id.edit_home);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHouseNumber.clear();
                mHomeCount = 0;
                mTotalMen = 0;
                mTotalWomen = 0;
                test();
                tv_sumHome.setText(" 총 가정의 수 : "+mHomeCount);
                tv_sumWomen.setText(" 총 딸의 수 : "+mTotalWomen);
                tv_sumMen.setText(" 총 아들의 수 : "+mTotalMen);
                tv_menAverage.setText(" 총 아들 비율 : "+MenAverage);
            }
        });

        btn_homeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int homes = Integer.parseInt(edit_home.getText().toString());
                mCurrentWomen = 0;
                mCurrentMen = 0;
                for(int i=0; i<homes; i++){
                    mCurrentWomen += mHouseNumber.get(i).getWomen();
                    mCurrentMen += mHouseNumber.get(i).getMen();
                }
                try {
                    tv_infoWomen.setText("딸의 수 : " + mHouseNumber.get(homes).getWomen());
                    tv_infoMen.setText("아들의 수 : " + mHouseNumber.get(homes).getMen());
                    tv_womenNumber.setText("이 집 까지의 딸의 수 : " + mCurrentWomen);
                    tv_menNumber.setText("이 집 까지의 아들의 수 : " + mCurrentMen);
                }catch(IndexOutOfBoundsException e){
                    Log.d(TAG,"Last Home");
                }
            }
        });
    }

    public void test() {
        int mBaby;
        int i = -1;

        while(true) {
            i++;
            mHouse = new House();
            mBaby = random.nextInt(2);

            if (mBaby == MEN) {
                mTotalMen++;
                mHouse.men++;
                mHomeCount++;
            } else {
                while (mBaby == WOMEN) {
                    mTotalWomen++;
                    mHouse.women++;
                    mBaby = random.nextInt(2);
                }
                mTotalMen++;
                mHouse.men++;
                mHomeCount++;
            }

            mHouseNumber.add(mHouse);

//            Log.d(TAG, "======================================================");
//            Log.d(TAG, "" + mHomeCount + " 번째 집 ");
//            Log.d(TAG, "여자 아이 수 : " + mHouseNumber.get(i).getWomen());
//            Log.d(TAG, "남자 아이 수 : " + mHouseNumber.get(i).getMen());
//            Log.d(TAG, "======================================================");

            if(mTotalMen+mTotalWomen > BABY_LIMIT)
                break;
        }

        MenAverage = ((float)(mTotalMen)/BABY_LIMIT * 100);

//        Log.d(TAG, "총 가정의 수 : " + mHomeCount);
//        Log.d(TAG, "총 남자 아이 수 : " + mTotalMen);
//        Log.d(TAG, "총 여자 아이 수 : " + mTotalWomen);
//        Log.d(TAG, "남자 아이 평균 수 : " + MenAverage);
    }

    class House {
        int men = 0;
        int women = 0;

        public int getMen() {
            return men;
        }

        public int getWomen() {
            return women;
        }
    }
}


