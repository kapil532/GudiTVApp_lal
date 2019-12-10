package com.tv.guditvapp.dashboard.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.locationlib.LocationUtils;
import com.tv.guditvapp.R;
import com.tv.guditvapp.base.BaseActivity;
import com.tv.guditvapp.dashboard.model.DashBoardModel;
import com.tv.guditvapp.dashboard.model.SectionDataModel;
import com.tv.guditvapp.dashboard.model.SectionOneListModel;
import com.tv.guditvapp.dashboard.model.SectionOneModel;
import com.tv.guditvapp.dashboard.model.SectionThreeModel;
import com.tv.guditvapp.dashboard.model.SectionTwoModel;
import com.tv.guditvapp.dashboard.utils.ScreenListType;
import com.tv.guditvapp.login.ui.LoginActivity;
import com.tv.guditvapp.utils.AppSharedPreUtils;
import com.tv.guditvapp.utils.GudiLogs;
import com.tv.guditvapp.utils.ScreenType;
import com.tv.guditvapp.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends BaseActivity {

    private static final String TAG = "DashBoardActivity";

    private static final String KEY_VIDEO_URI = "video_uri";
    private int mScreenType = 0;

    private VideoView mVideoViewSectionOne;
    private VideoView mVideoViewSectionTwo;

    private SectionDataModel dashBoardSectionModel;
    private ImageView mImageViewSectionOne;
    private ImageView mImageViewSectionTwo;
    private TextView mTvBelowText;
    private ArrayList<SectionOneListModel> mSectionOneList = new ArrayList<>();
    private ArrayList<String> mSectionOneVideList = new ArrayList<>();
    private ArrayList<String> mSectionOneImageList = new ArrayList<>();
    private LocationUtils mLocationUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreenType = 3;//AppSharedPreUtils.getInstance(this).getIntValues(AppSharedPrefConst.APP_SCREEN_TYPE);
        mLocationUtils = new LocationUtils(this);
        mLocationUtils.startLocation();
        setContentViewScreen(mScreenType);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        dashBoardSectionModel = AppSharedPreUtils.getInstance(this).getDashBoardSectionData();
//        dashBoardSectionModel = readJsonData().getSectionData();
        if (dashBoardSectionModel == null) {
            startLoginActivity();
        }
        initialize();
    }

    private void setContentViewScreen(int type) {

        if (type == ScreenType.ScreenType.SCREEN_1.getPos()) {
            setContentView(R.layout.layout_screen1);

        } else if (type == ScreenType.ScreenType.SCREEN_2.getPos()) {
            setContentView(R.layout.layout_screen2);

        } else if (type == ScreenType.ScreenType.SCREEN_3.getPos()) {
            setContentView(R.layout.layout_screen3);
        }
    }


    /**
     * screen view only for video and image
     */
    private void initViewSection1() {
        mImageViewSectionOne = findViewById(R.id.img_play);
        mVideoViewSectionOne = findViewById(R.id.video_play);
    }

    /**
     * screen view for video/image and below Text
     */
    private void initViewSection2() {
        mTvBelowText = findViewById(R.id.tv_section_two);
        mImageViewSectionOne = findViewById(R.id.img_play);
        mVideoViewSectionOne = findViewById(R.id.video_play);
    }

    /**
     * screen view for video/image in section1 and section2 and below Text
     */
    private void initViewSection3() {
        mTvBelowText = findViewById(R.id.tv_section_three);
        mImageViewSectionOne = findViewById(R.id.img_play);
        mVideoViewSectionOne = findViewById(R.id.video_play);
        mImageViewSectionTwo = findViewById(R.id.img_play_section3);
        mVideoViewSectionTwo = findViewById(R.id.video_play_section3);
    }

    private void initialize() {

        if (mScreenType == ScreenType.ScreenType.SCREEN_1.getPos()) {
            initViewSection1();
            //videoViewSectionOne.setUseController(false);

        } else if (mScreenType == ScreenType.ScreenType.SCREEN_2.getPos()) {
            initViewSection2();
            if (dashBoardSectionModel != null) {
                sectionOneVideoImageView();
                sectionThreeTextView();
            }
        } else if (mScreenType == ScreenType.ScreenType.SCREEN_3.getPos()) {
            initViewSection3();

            if (dashBoardSectionModel != null) {
//                sectionOneVideoImageView();
//                sectionTwoVideoImageView();
                sectionThreeTextView();
                changeLoopToList();
            }

        }
    }


    ArrayList<UrlAndType> sectionOneArrayList;
    ArrayList<UrlAndType> sectiontwoArrayList;

    class UrlAndType {
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private String url;
        private String type;
    }

    //Based on loop make arraylist of section and use that one
    void changeLoopToList() {
        List<SectionOneModel> sectionOneList = dashBoardSectionModel.getSectionOne();
        List<SectionTwoModel> sectionTwoList = dashBoardSectionModel.getSectionTwo();
        sectionOneArrayList = new ArrayList<>();
        sectiontwoArrayList = new ArrayList<>();
        UrlAndType myUrlandType = null;
        for (int i = 0; i < sectionOneList.size(); i++) {
            if (sectionOneList.get(i).getLoop() > 1) {
                for (int p = 0; p < sectionOneList.get(i).getLoop(); p++) {
                    myUrlandType = new UrlAndType();
                    myUrlandType.setType(sectionOneList.get(i).getType());
                    myUrlandType.setUrl(sectionOneList.get(i).getLink());
                }

            } else {
                myUrlandType = new UrlAndType();
                myUrlandType.setType(sectionOneList.get(i).getType());
                myUrlandType.setUrl(sectionOneList.get(i).getLink());

            }

            sectionOneArrayList.add(myUrlandType);
        }


        UrlAndType myUrlandType_sectTwo = null;
        for (int j = 0; j < sectionTwoList.size(); j++) {
            if (sectionTwoList.get(j).getLoop() > 1) {
                for (int q = 0; q < sectionOneList.get(j).getLoop(); q++) {
                    myUrlandType_sectTwo = new UrlAndType();
                    myUrlandType_sectTwo.setType(sectionTwoList.get(j).getType());
                    myUrlandType_sectTwo.setUrl(sectionTwoList.get(j).getLink());
                }

            } else {
                myUrlandType_sectTwo = new UrlAndType();
                myUrlandType_sectTwo.setType(sectionTwoList.get(j).getType());
                myUrlandType_sectTwo.setUrl(sectionTwoList.get(j).getLink());

            }

            sectiontwoArrayList.add(myUrlandType_sectTwo);
        }


        if (sectionOneArrayList.size() > 0) {
            if (sectionOneArrayList.get(0).getType().equalsIgnoreCase("image")) {
                playImageForOneSection();
            } else {
                playVideoForOneSection();
            }
        }


        if (sectiontwoArrayList.size() > 0) {
            GudiLogs.logDebug(TAG,""+sectiontwoArrayList.get(0).getType()+"---"+sectiontwoArrayList.get(0).getUrl());
            if (sectiontwoArrayList.get(0).getType().equalsIgnoreCase("image")) {
                playImageForTwoSection();
            } else {
                playVideoForTwoSection();
            }
        }


    }

    private void setVideoView(boolean isVideo) {
        if (isVideo) {
            mImageViewSectionOne.setVisibility(View.GONE);
            mVideoViewSectionOne.setVisibility(View.VISIBLE);
        } else {
            mImageViewSectionOne.setVisibility(View.VISIBLE);
            mVideoViewSectionOne.setVisibility(View.GONE);
        }
    }

    private void setVideoViewSecTwo(boolean isVideo) {
        if (isVideo) {
            mImageViewSectionTwo.setVisibility(View.GONE);
            mImageViewSectionTwo.setVisibility(View.VISIBLE);
        } else {
            mImageViewSectionTwo.setVisibility(View.VISIBLE);
            mImageViewSectionTwo.setVisibility(View.GONE);
        }
    }

    int videoAndImageSection = 0;


    void playVideoForOneSection() {
        setVideoView(true);


        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoViewSectionOne);
        mVideoViewSectionOne.setMediaController(mediaController);
        mVideoViewSectionOne.setVideoURI(Uri.parse(sectionOneArrayList.get(videoAndImageSection).getUrl()));
        mVideoViewSectionOne.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoViewSectionOne.start();
            }
        });
        mVideoViewSectionOne.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    mVideoViewSectionOne.setVisibility(View.GONE);
                    mp.reset();
                    mp.release();
                    mp = null;
                } catch (Exception e) {
                    GudiLogs.logDebug("Releasing mp", e.toString());
                }

                if (videoAndImageSection < sectionOneArrayList.size() - 1) {
                    videoAndImageSection = videoAndImageSection + 1;
                } else {
                    videoAndImageSection = 0;
                }
                if (sectionOneArrayList.get(videoAndImageSection).getType().equalsIgnoreCase("image")) {
                    playImageForOneSection();
                } else if (sectionOneArrayList.get(videoAndImageSection2).getType().equalsIgnoreCase("video")) {
                    playVideoForTwoSection();
                }

            }
        });

    }

    CountDownTimer yourCountDo;

    void playImageForOneSection() {
        setVideoView(false);

        yourCountDo = new CountDownTimer(2000, 100) {

            public void onTick(long millisUntilFinished) {
                Glide.with(DashBoardActivity.this).load(sectionOneArrayList.
                        get(videoAndImageSection).getUrl()).into(mImageViewSectionOne);
            }

            public void onFinish() {
                closeTimer();
                GudiLogs.logDebug(TAG, "FINISH " + sectionOneArrayList.size());
                if (videoAndImageSection < sectionOneArrayList.size() - 1) {
                    videoAndImageSection = videoAndImageSection + 1;
                    GudiLogs.logDebug(TAG, "INSIDE IF LOOP " + sectionOneArrayList.size());
                } else {
                    GudiLogs.logDebug(TAG, "FINISH elsepart ");
                    videoAndImageSection = 0;
                }
                GudiLogs.logDebug(TAG, "FINISH " + videoAndImageSection);
                if (sectionOneArrayList.get(videoAndImageSection).getType().equalsIgnoreCase("image")) {
                    GudiLogs.logDebug(TAG, "IMAGES ");
                    playImageForOneSection();
                } else if (sectionOneArrayList.get(videoAndImageSection).getType().equalsIgnoreCase("video")) {
                    GudiLogs.logDebug(TAG, "VIDEO ");
                    playVideoForOneSection();
                }

            }
        }.start();


    }

    void closeTimer() {
        yourCountDo.cancel();
    }

    void closeTimer2() {
        yourCountDo2.cancel();
    }


    //second layout

    CountDownTimer yourCountDo2;
    int videoAndImageSection2=0;
    void playVideoForTwoSection()
    {
        setVideoViewSecTwo(true);

        try {


            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(mVideoViewSectionTwo);
            mVideoViewSectionTwo.setMediaController(mediaController);
            GudiLogs.logDebug(TAG, "MESSAGE11");
            mVideoViewSectionTwo.setVideoURI(Uri.parse(sectiontwoArrayList.get(videoAndImageSection2).getUrl()));
            GudiLogs.logDebug(TAG, "MESSAGE" + sectiontwoArrayList.get(videoAndImageSection2).getUrl());
            mVideoViewSectionTwo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mVideoViewSectionTwo.start();
                }
            });
            mVideoViewSectionTwo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    try {
                        mVideoViewSectionTwo.setVisibility(View.GONE);
                        mp.reset();
                        mp.release();
                        // mp = null;
                    } catch (Exception e) {
                        GudiLogs.logDebug(TAG, e.toString());
                    }
                    GudiLogs.logDebug(TAG, "----MESSAGE");

                    if (videoAndImageSection2 < sectiontwoArrayList.size() - 1) {
                        videoAndImageSection2 = videoAndImageSection2 + 1;
                    } else {
                        videoAndImageSection2 = 0;
                    }
                    if (sectiontwoArrayList.get(videoAndImageSection2).getType().equalsIgnoreCase("image")) {
                        playImageForTwoSection();
                    } else if (sectiontwoArrayList.get(videoAndImageSection2).getType().equalsIgnoreCase("video")) {

                        playVideoForTwoSection();
                    }

                }
            });

        }
        catch (Exception e)
        {


        }
    }


    void playImageForTwoSection() {
        setVideoViewSecTwo(false);


        yourCountDo2 = new CountDownTimer(2000, 100) {

            public void onTick(long millisUntilFinished) {
                GudiLogs.logDebug(TAG,"IMAGE URL  --->"+sectiontwoArrayList.get(videoAndImageSection2).
                        getUrl());
                Glide.with(DashBoardActivity.this).load(sectiontwoArrayList.get(videoAndImageSection2).
                        getUrl()).into(mImageViewSectionTwo);
            }

            public void onFinish() {
                closeTimer2();
                GudiLogs.logDebug(TAG, "FINISH " + sectiontwoArrayList.size());
                if (videoAndImageSection2 < sectiontwoArrayList.size() - 1) {
                    videoAndImageSection2 = videoAndImageSection2 + 1;
                    GudiLogs.logDebug(TAG, "INSIDE IF LOOP " + sectiontwoArrayList.size());
                } else {
                    GudiLogs.logDebug(TAG, "FINISH elsepart ");
                    videoAndImageSection2 = 0;
                }
                GudiLogs.logDebug(TAG, "FINISH " + videoAndImageSection2);
                if (sectiontwoArrayList.get(videoAndImageSection2).getType().equalsIgnoreCase("image")) {
                    GudiLogs.logDebug(TAG, "IMAGES ");
                    playImageForTwoSection();
                } else if (sectiontwoArrayList.get(videoAndImageSection2).getType().equalsIgnoreCase("video")) {
                    GudiLogs.logDebug(TAG, "VIDEO ");
                    playVideoForTwoSection();
                }

            }
        }.start();

    }


    private void sectionOneVideoImageView() {
        List<SectionOneModel> sectionOneList = dashBoardSectionModel.getSectionOne();
        int sectionOneSize = sectionOneList.size();
        if (sectionOneSize > 0) {
            mSectionOneList.clear();
            mSectionOneVideList.clear();
            mSectionOneImageList.clear();
            for (int i = 0; i < sectionOneSize; i++) {

                if (sectionOneList.get(i).getType()
                        .equalsIgnoreCase(ScreenType.ScreenViewType.SCREEN_SECTION_VIDEO.getView())) {
                    for (int j = 0; j < sectionOneList.get(i).getLoop(); j++) {
                        mSectionOneVideList.add(sectionOneList.get(i).getLink());
                    }

                } else {
                    SectionOneListModel oneListModel = new SectionOneListModel();
                    oneListModel.setUrl(sectionOneList.get(i).getLink());
                    oneListModel.setType(ScreenListType.IMAGE);
                    oneListModel.setSecond(sectionOneList.get(i).getLoop());
                    mSectionOneList.add(oneListModel);
                }

               /* SectionOneListModel oneListModel;
                if (sectionOneList.get(i).getType()
                        .equalsIgnoreCase(ScreenType.ScreenViewType.SCREEN_SECTION_VIDEO.getView())) {
                    for (int j = 0; j < sectionOneList.get(i).getLoop(); j++) {
                        oneListModel = new SectionOneListModel();
                        oneListModel.setUrl(sectionOneList.get(i).getLink());
                        oneListModel.setType(ScreenListType.VIDEO);
                        mSectionOneList.add(oneListModel);
                    }

                } else {
                    oneListModel = new SectionOneListModel();
                    oneListModel.setUrl(sectionOneList.get(i).getLink());
                    oneListModel.setType(ScreenListType.IMAGE);
                    oneListModel.setSecond(sectionOneList.get(i).getLoop());
                    mSectionOneList.add(oneListModel);
                }*/
            }
            if (mSectionOneVideList != null) {
                GudiLogs.logDebug(TAG, "mVideoListSectionOne Image -> " + mSectionOneVideList.size());
                setVideoView(true);
                playLoopVideo(mSectionOneVideList);
            }
            /*if(mSectionOneList!=null){
                setVideoView(false);
                setImageViewByGlideAsList(mImageViewSectionOne, mSectionOneList);
            }*/
            /*if (mSectionOneList.size() > 0) {
                GudiLogs.logDebug(TAG, "mVideoListSectionOne Image -> " + mSectionOneList.size());
                playInLoopVideoImage(mSectionOneList);
            }*/
        }
    }

    /*private void sectionTwoVideoImageView(){
        List<DashBoardModel.GudiBean.SectionTwoBean> twoBeanList = dashBoardModel.getGudi().getSectionTwo();
        int sectionTwoSize = twoBeanList.size();
        if(sectionTwoSize > 0){
            for(int i = 0; i < sectionTwoSize; i++){
                if(twoBeanList.get(0).getType().equalsIgnoreCase(ScreenType.ScreenViewType.SCREEN_SECTION_VIDEO.getView())){

                }else{

                    //Utilities.setImageViewByGlide(this,mImageViewSectionOne, twoBeanList.get(0).getUrl());
                }
            }
        }
    }*/

    private void sectionThreeTextView() {
        mTvBelowText.setSelected(true);
        Utilities.setMarqueeSpeed(mTvBelowText, 50000);
        List<SectionThreeModel> threeBeanList = dashBoardSectionModel.getSectionThree();
        int sectionThreeSize = threeBeanList.size();
        if (sectionThreeSize > 0) {
            String textDisplay = "";
            for (int i = 0; i < sectionThreeSize; i++) {
                textDisplay = textDisplay + threeBeanList.get(i).getMessage() + "\n\n\n";
            }
            mTvBelowText.setText(textDisplay);
        }
    }

    int i = 0;

    private void playInLoopVideoImage(final ArrayList<SectionOneListModel> mSectionOneList) {
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoViewSectionOne);
        mVideoViewSectionOne.setMediaController(mediaController);

        for (i = 0; i < mSectionOneList.size(); i++) {
            if (mSectionOneList.get(i).getType() == ScreenListType.VIDEO) {
                setVideoView(true);
                mVideoViewSectionOne.setVideoURI(Uri.parse(mSectionOneList.get(i).getUrl()));
                mVideoViewSectionOne.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mVideoViewSectionOne.start();
                    }
                });

                mVideoViewSectionOne.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (mSectionOneList.get(i + 1).getType() == ScreenListType.VIDEO) {
                            setVideoView(true);
                            if ((i + 1) <= mSectionOneList.size()) {
                                i++;
                                mVideoViewSectionOne.setVideoURI(Uri.parse(mSectionOneList.get(i + 1).getUrl()));
                                mVideoViewSectionOne.start();
                            }
                        } else {
                            setVideoView(false);
                            //setImageViewByGlide(DashBoardActivity.this, mImageViewSectionOne, mSectionOneList,mSectionOneList.get(i).getSecond(),i);
                        }

                    }
                });
            } else {
                setVideoView(false);
                //setImageViewByGlide(DashBoardActivity.this, mImageViewSectionOne, mSectionOneList, mSectionOneList.get(i).getSecond(),i);
            }
        }
    }


    int video_counter = 0;

    private void playLoopVideo(final ArrayList<String> mSectionOneVideList) {
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoViewSectionOne);
        mVideoViewSectionOne.setMediaController(mediaController);

        mVideoViewSectionOne.setVideoURI(Uri.parse(mSectionOneVideList.get(video_counter)));
        mVideoViewSectionOne.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoViewSectionOne.start();
            }
        });

        mVideoViewSectionOne.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if ((video_counter + 1) <= mSectionOneVideList.size()) {
                    video_counter++;
                    mVideoViewSectionOne.setVideoURI(Uri.parse(mSectionOneVideList.get(video_counter + 1)));
                    mVideoViewSectionOne.start();
                }
            }
        });
    }


    public void setImageViewByGlideAsList(final ImageView imageView,
                                          final ArrayList<SectionOneListModel> mImageListSectionOne) {
        // Glide.with(context).load(imageUrl).into(imageView);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                //imageView1.setImageResource(imageArray[i]);
                Glide.with(DashBoardActivity.this).load(mImageListSectionOne.get(i)).into(imageView);
                i++;
                if (i > mImageListSectionOne.size() - 1) {
                    i = 0;
                }
                handler.postDelayed(this, mImageListSectionOne.get(i).getSecond() * 1000);
            }
        };
        handler.postDelayed(runnable, mImageListSectionOne.get(i).getSecond() * 1000);
    }


    private void startLoginActivity() {
        Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private DashBoardModel readJsonData() {
        String jsonString = Utilities.getAssetJsonData(this, "gudi.json");
        Gson gson = new Gson();
        return gson.fromJson(jsonString, DashBoardModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationUtils.registrationReceiver();
        GudiLogs.logDebug("DashBoardActivity", "CurrentAddress -> " + mLocationUtils.getCurrentAddress());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationUtils.unRegistrationReceiver();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
