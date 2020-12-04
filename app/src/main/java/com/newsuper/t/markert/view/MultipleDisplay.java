package com.newsuper.t.markert.view;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


import com.newsuper.t.markert.entity.MultipleQueryProduct;

import java.util.List;

public class MultipleDisplay {

    private PayMoneyPresentation payMoneyPresentation;

    public  void ShowMoneyDisplay(Context context, List<MultipleQueryProduct> list, String num, String totalPrice, String youhui, String cardNo, String cardName, String cardPoint) {
        try {
            DisplayManager mDisplayManager = (DisplayManager) context.getSystemService(
                    Context.DISPLAY_SERVICE);
            Display[] displays = mDisplayManager.getDisplays();
            if (displays.length > 1) {
                if(payMoneyPresentation == null){
                    payMoneyPresentation = new PayMoneyPresentation(context, displays[1]);
                    payMoneyPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
                    payMoneyPresentation.show();
                }
                payMoneyPresentation.setDisplay(list, num, totalPrice, youhui, cardNo,cardName, cardPoint);
            }
        } catch (Exception e) {
            Log.i("HHHH", "-------------副屏错误1-----" + e.toString());
        }
    }
}
