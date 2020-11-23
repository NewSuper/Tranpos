package cn.jzvd;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.View;

/**
 * <p>参照Android系统的VideoView的onMeasure方法
 * <br>注意!relativelayout中无法全屏，要嵌套一个linearlayout</p>
 * <p>Referring Android system Video View of onMeasure method
 * <br>NOTE! Can not fullscreen relativelayout, to nest a linearlayout</p>
 * Created by Nathen
 * On 2016/06/02 00:01
 */
public class JZTextureView extends TextureView {
    protected static final String TAG = "JZResizeTextureView";

    public int currentVideoWidth = 0;
    public int currentVideoHeight = 0;

    public JZTextureView(Context context) {
        super(context);
        currentVideoWidth = 0;
        currentVideoHeight = 0;
    }

    public JZTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentVideoWidth = 0;
        currentVideoHeight = 0;
    }

    public void setVideoSize(int currentVideoWidth, int currentVideoHeight) {
        if (this.currentVideoWidth != currentVideoWidth || this.currentVideoHeight != currentVideoHeight) {
            this.currentVideoWidth = currentVideoWidth;
            this.currentVideoHeight = currentVideoHeight;
            requestLayout();
        }
    }

    @Override
    public void setRotation(float rotation) {
        if (rotation != getRotation()) {
            super.setRotation(rotation);
            requestLayout();
        }
    }

    private Jzvd jzvd;
    public void setJzvd(Jzvd jzvd){
        this.jzvd = jzvd;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.i(TAG, "onMeasure " + " [" + this.hashCode() + "] ");
        int viewRotation = (int) getRotation();
        int videoWidth = currentVideoWidth;
        int videoHeight = currentVideoHeight;

//        Log.e("zy" , "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" );
//        Log.e("zy" , "videoWidth = " + videoWidth + " , videoHeight = " + videoHeight);
//
//        Log.e("zy" , "viewRotation = " + viewRotation);


        //横竖屏判断
        int parentWidth = ((View) getParent()).getMeasuredWidth();
        int parentHeight = ((View) getParent()).getMeasuredHeight();

        //只有电影才这样操作，其他视频不影响
        if(jzvd.isMovie) {
            if (getOri() == Configuration.ORIENTATION_LANDSCAPE) {
//                Log.e("zy", "横屏");
                if (parentWidth < parentHeight) {
                    int temp = parentHeight;
                    parentHeight = parentWidth;
                    parentWidth = temp;
                }
            } else {
//                Log.e("zy", "竖屏");
                if (parentWidth > parentHeight) {
                    int temp = parentHeight;
                    parentHeight = parentWidth;
                    parentWidth = temp;
                }
            }
        }


//        Log.e("zy" , "parentWidth = " + parentWidth + " , parentHeight = " + parentHeight);
        if (parentWidth != 0 && parentHeight != 0 && videoWidth != 0 && videoHeight != 0) {
            if (Jzvd.VIDEO_IMAGE_DISPLAY_TYPE == Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT) {
                if (viewRotation == 90 || viewRotation == 270) {
                    int tempSize = parentWidth;
                    parentWidth = parentHeight;
                    parentHeight = tempSize;
                }
                /**强制充满**/
                videoHeight = videoWidth * parentHeight / parentWidth;
            }
        }

        // 如果判断成立，则说明显示的TextureView和本身的位置是有90度的旋转的，所以需要交换宽高参数。
        if (viewRotation == 90 || viewRotation == 270) {
            int tempMeasureSpec = widthMeasureSpec;
            widthMeasureSpec = heightMeasureSpec;
            heightMeasureSpec = tempMeasureSpec;
        }

        int width = getDefaultSize(videoWidth, widthMeasureSpec);
        int height = getDefaultSize(videoHeight, heightMeasureSpec);
        if (videoWidth > 0 && videoHeight > 0) {

            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

            Log.i(TAG, "widthMeasureSpec  [" + MeasureSpec.toString(widthMeasureSpec) + "]");
            Log.i(TAG, "heightMeasureSpec [" + MeasureSpec.toString(heightMeasureSpec) + "]");

            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
                // the size is fixed
                width = widthSpecSize;
                height = heightSpecSize;
                // for compatibility, we adjust size based on aspect ratio
                if (videoWidth * height < width * videoHeight) {
                    width = height * videoWidth / videoHeight;
                } else if (videoWidth * height > width * videoHeight) {
                    height = width * videoHeight / videoWidth;
                }
            } else if (widthSpecMode == MeasureSpec.EXACTLY) {
                // only the width is fixed, adjust the height to match aspect ratio if possible
                width = widthSpecSize;
                height = width * videoHeight / videoWidth;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    height = heightSpecSize;
                    width = height * videoWidth / videoHeight;
                }
            } else if (heightSpecMode == MeasureSpec.EXACTLY) {
                // only the height is fixed, adjust the width to match aspect ratio if possible
                height = heightSpecSize;
                width = height * videoWidth / videoHeight;
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    width = widthSpecSize;
                    height = width * videoHeight / videoWidth;
                }
            } else {
                // neither the width nor the height are fixed, try to use actual video size
                width = videoWidth;
                height = videoHeight;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // too tall, decrease both width and height
                    height = heightSpecSize;
                    width = height * videoWidth / videoHeight;
                }
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // too wide, decrease both width and height
                    width = widthSpecSize;
                    height = width * videoHeight / videoWidth;
                }
            }
        } else {
            // no size yet, just adopt the given spec sizes
        }


        int type = Jzvd.VIDEO_IMAGE_DISPLAY_TYPE;
        //电影模式竖屏为100%
        if(jzvd.isMovie && getOri() == Configuration.ORIENTATION_PORTRAIT){
            type = Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL;
        }

        if (parentWidth != 0 && parentHeight != 0 && videoWidth != 0 && videoHeight != 0) {
            if (type == Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL) {
//                Log.e("zy" , "100%");
                /**原图**/
                float percentageW =  1f * parentWidth / videoWidth ;
                float percentageH =  1f * parentHeight / videoHeight ;

                if(percentageW <= percentageH){
                    height = (int) (videoHeight * percentageW);
                    width = (int) (videoWidth * percentageW);
                }else{
                    height = (int) (videoHeight * percentageH);
                    width = (int) (videoWidth * percentageH);
                }
            }
            //75%
            else if (type == Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL_75) {
                Log.e("zy" , "75%");
                float percentageW =  1f * parentWidth / videoWidth ;
                float percentageH =  1f * parentHeight / videoHeight ;

                if(percentageW <= percentageH){
                    height = (int) (videoHeight * percentageW * 0.75f);
                    width = (int) (videoWidth * percentageW* 0.75f);
                }else{
                    height = (int) (videoHeight * percentageH* 0.75f);
                    width = (int) (videoWidth * percentageH* 0.75f);
                }
//                height = videoHeight;
//                width = videoWidth;
            }
            //50%
            else if (type == Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL_50) {
                Log.e("zy" , "50%");
                float percentageW =  1f * parentWidth / videoWidth ;
                float percentageH =  1f * parentHeight / videoHeight ;

                if(percentageW <= percentageH){
                    height = (int) (videoHeight * percentageW * 0.5f);
                    width = (int) (videoWidth * percentageW * 0.5f);
                }else{
                    height = (int) (videoHeight * percentageH * 0.5f);
                    width = (int) (videoWidth * percentageH * 0.5f);
                }
//                height = videoHeight;
//                width = videoWidth;
            }

            else if (type == Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP) {
                if (viewRotation == 90 || viewRotation == 270) {
                    int tempSize = parentWidth;
                    parentWidth = parentHeight;
                    parentHeight = tempSize;
                }
                /**充满剪切**/
                if (((double) videoHeight / videoWidth) > ((double) parentHeight / parentWidth)) {
                    height = (int) (((double) parentWidth / (double) width * (double) height));
                    width = parentWidth;
                } else if (((double) videoHeight / videoWidth) < ((double) parentHeight / parentWidth)) {
                    width = (int) (((double) parentHeight / (double) height * (double) width));
                    height = parentHeight;
                }
            }
        }


        //重新编写逻辑
//        Log.e("zy" , "w = " + width + " , h = " + height);
        setMeasuredDimension(width, height);
    }


    private int getOri(){
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        return mConfiguration.orientation; //获取屏幕方向
//        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
//            //横屏
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
//        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
//            //竖屏
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
//        }
    }
}
