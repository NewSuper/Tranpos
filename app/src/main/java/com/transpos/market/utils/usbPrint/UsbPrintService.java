package com.transpos.market.utils.usbPrint;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.transpos.market.entity.OrderItem;
import com.transpos.market.entity.OrderPay;
import com.transpos.market.utils.ActivityCollector;

import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class UsbPrintService extends IntentService {
    private static final String TAG = "UsbPrintService";
    private DecimalFormat df;
    List<OrderItem> orderList;
    OrderPay payType;

    @Override
    public void onCreate() {
        super.onCreate();
        df = new DecimalFormat("#0.00");
    }

    public UsbPrintService() {
        super("UsbPrintService");
    }

    public UsbPrintService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (intent.getAction().equals(PrintUtil.ACTION_PRINT_EMPTY_TEST)) {
            PrintEmptyTest();
        } else if (intent.getAction().equals(PrintUtil.ACTION_PRINT_ORDER)) {
            orderList = (List<OrderItem>) intent.getSerializableExtra("orderList");
            payType = (OrderPay) intent.getSerializableExtra("payType");
            String storeName = intent.getStringExtra("storeName");
            String workerNo = intent.getStringExtra("workerNo");
            String workName = intent.getStringExtra("workName");
            PrintOrder(orderList, payType, storeName, workerNo, workName);
        }else if (intent.getAction().equalsIgnoreCase(PrintUtil.OpenCash)) {
            OpenQianXiang();
        }
    }

    private void PrintOrder(List<OrderItem> orderList, OrderPay payType, String storeName, String workerNo, String workName) {
        try {
            new Thread() {
                @Override
                public void run() {
                    ArrayList<byte[]> byteArray = new ArrayList<>();
                    EscCommand esc = new EscCommand();
                    esc.addArrayToCommand(UsbPrintUtils.setChineses());
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);//设置打印居中
                    esc.addArrayToCommand(UsbPrintUtils.setSize(17));
                    esc.addText(storeName + "\n", "GB2312");
                    esc.addText("结账单" + "\n", "GB2312");
                    esc.addPrintAndLineFeed();
                    esc.addArrayToCommand(UsbPrintUtils.setSize(0));
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                    esc.addText("单号：" + payType.getTradeNo() + "\n", "GB2312");
                    esc.addText("收银员：" + workName + "\n", "GB2312");
                    esc.addText("销售时间：" + payType.getModifyDate() + "\n", "GB2312");
                    esc.addText("导购：" + workerNo + "\n", "GB2312");
                    esc.addText("--------------------------------\n", "GB2312");
                    esc.addText("品名          数量   单价   小计\n", "GB2312");
                    int num = 0;
                    float total_price = 0;
                    ArrayList<byte[]> byteOrder = new ArrayList<byte[]>();
                    for (int i = 0; i < orderList.size(); i++) {
                        String name = "";
                        double price = 0;
                        String buy_price = "";
                        name += orderList.get(i).getProductName();
                        if (Double.parseDouble(orderList.get(i).getPrice() + "") != 0) {
                            price = Double.parseDouble(orderList.get(i).getPrice() + "") * orderList.get(i).getQuantity();
                            buy_price = df.format(Double.parseDouble(orderList.get(i).getPrice() + ""));
                            total_price += price;
                        } else {
                            buy_price = "0";
                            total_price += 0;
                        }
                        byteOrder.addAll(UsbPrintUtils.PrintOrderList58mm(name, orderList.get(i).getQuantity() + "", buy_price, price + ""));
                        num += orderList.get(i).getQuantity();
                    }
                    for (int i = 0; i < byteOrder.size(); i++) {
                        esc.addArrayToCommand(byteOrder.get(i));
                    }
                    esc.addText("--------------------------------\n", "GB2312");
                    //    if (orderList.getChangeAmount() != 0) {
//                        BigDecimal a = new BigDecimal(orderObject.getPaidAmount());
//                        BigDecimal b = new BigDecimal(orderObject.getChangeAmount());
//                        esc.addText("    共" + num + "件商品   实付金额：" + a.add(b).doubleValue() + "\n", "GB2312");
//                        esc.addText("                原价金额：" + orderObject.getAmount() + "\n", "GB2312");
//                    } else {
                    esc.addText("    共" + num + "件商品   实付金额：" + total_price + "\n", "GB2312");
                    esc.addText("                原价金额：" + total_price + "\n", "GB2312");
                    //  }
                    esc.addText("                优惠金额：0" + "\n", "GB2312");
                    esc.addText("                抹零金额：0" + "\n", "GB2312");
                    esc.addText("              付款详情：" + "\n", "GB2312");
                    if (null != payType) {
                        //  for (int i = 0; i < payType.size(); i++) {
                        //  if (orderObject.getChangeAmount() != 0) {
                        esc.addText("                  " + payType.getName() + "：" + payType.getInputAmount() + "\n", "GB2312");
                        esc.addText("                    找零：0" + "\n", "GB2312");
//                            } else {
//                                esc.addText("                  " + payType.getName() + "：" + payType.getPaidAmount() + "\n", "GB2312");
//                            }
                        //    }
                    }
                    esc.addText("--------------------------------\n", "GB2312");
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//                    if (null != orderObject.getMember()) {
//                        esc.addText("会员号：" + UIUtils.hidePhoneNum(orderObject.getMember().getMobile()) + "\n", "GB2312");
//                        esc.addText("剩余积分：" + orderObject.getMember().getTotalPoint() + "\n", "GB2312");
//                        esc.addText("本次积分：" + currentOrderPoint / 100 + "\n", "GB2312");
//                        esc.addText("会员余额：" + orderObject.getMember().getTotalAmount() + "\n", "GB2312");
//                    }
//                    esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);//设置打印居中
//                    if (!TextUtils.isEmpty(orderObject.getMemberMobileNo())) {
//                        esc.addText("电话：" + orderObject.getMemberMobileNo() + "\n", "GB2312");
//                    }
                    esc.addText("谢谢惠顾！欢迎您的下次光临" + "\n", "GB2312");
                    esc.addPrintAndLineFeed();
                    esc.addPrintAndFeedLines((byte) 1);
                    esc.addCutAndFeedPaper((byte) 1);
                    Vector<Byte> datas = esc.getCommand(); //发送数据
                    Byte[] Bytes = datas.toArray(new Byte[datas.size()]);
                    byte[] bytes = ArrayUtils.toPrimitive(Bytes);
                    String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
                    byte[] data2 = Base64.decode(sss, 0);
                    Vector vector = new Vector();
                    byte[] var9 = data2;
                    int var10 = data2.length;
                    for (int var7 = 0; var7 < var10; ++var7) {
                        byte b1 = var9[var7];
                        vector.add(Byte.valueOf(b1));
                    }
                    Vector<Byte> Command = vector;
                    if (Command != null && Command.size() > 0) {
                        byte[] sendData = new byte[Command.size()];
                        for (int e = 0; e < Command.size(); ++e) {
                            sendData[e] = ((Byte) Command.get(e)).byteValue();
                        }
                        byteArray.add(sendData);
                    }
                    if (byteArray.size() > 0) {
                        LoopPrint(byteArray);
                    }
                    //   }
                }
            }.start();
        } catch (Exception e) {
        }
    }
    private void OpenQianXiang() {
        try {
//            if (!HardwareUtils.CanUseIndexPrint()) {
//                isInner = sp.getBoolean("is_out_printer", true);
//            } else {
//                isInner = sp.getBoolean("is_out_printer", false);
//            }
          //  if (isInner) {
                new Thread() {
                    @Override
                    public void run() {
                        ArrayList<byte[]> byteArray = new ArrayList<>();
                        EscCommand esc = new EscCommand();
                        esc.addGeneratePlus(TscCommand.FOOT.F5, (byte) 255, (byte) 255);
                        esc.addPrintAndFeedLines((byte) 1);
                        Vector<Byte> datas = esc.getCommand(); //发送数据
                        Byte[] Bytes = datas.toArray(new Byte[datas.size()]);
                        byte[] bytes = ArrayUtils.toPrimitive(Bytes);
                        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
                        byte[] data2 = Base64.decode(sss, 0);
                        Vector vector = new Vector();
                        byte[] var9 = data2;
                        int var10 = data2.length;
                        for (int var7 = 0; var7 < var10; ++var7) {
                            byte b1 = var9[var7];
                            vector.add(Byte.valueOf(b1));
                        }
                        Vector<Byte> Command = vector;
                        if (Command != null && Command.size() > 0) {
                            byte[] sendData = new byte[Command.size()];

                            for (int e = 0; e < Command.size(); ++e) {
                                sendData[e] = ((Byte) Command.get(e)).byteValue();
                            }
                            byteArray.add(sendData);
                        }

                        if (byteArray.size() > 0) {
                            LoopPrint(byteArray);
                        }
                    }
                }.start();
         //   }
        } catch (Exception e) {

        }
    }

    //打印测试
    private void PrintEmptyTest() {
        try {
            new Thread() {
                @Override
                public void run() {
                    ArrayList<byte[]> byteArray = new ArrayList<>();
                    for (int l = 0; l < 1; l++) {
                        int total_num = 0;
                        EscCommand esc = new EscCommand();
                        esc.addInitializePrinter();
                        esc.addSelectInternationalCharacterSet(EscCommand.CHARACTER_SET.CHINA);
                        esc.addArrayToCommand(UsbPrintUtils.setSize(0));
                        esc.addText("");
                        esc.addPrintAndLineFeed();
                        esc.addPrintAndFeedLines((byte) 1);
                        esc.addCutAndFeedPaper((byte) 1);
                        Vector<Byte> datas = esc.getCommand(); //发送数据
                        Byte[] Bytes = datas.toArray(new Byte[datas.size()]);
                        byte[] bytes = ArrayUtils.toPrimitive(Bytes);
                        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
                        byte[] data2 = Base64.decode(sss, 0);
                        Vector vector = new Vector();
                        byte[] var9 = data2;
                        int var10 = data2.length;
                        for (int var7 = 0; var7 < var10; ++var7) {
                            byte b1 = var9[var7];
                            vector.add(Byte.valueOf(b1));
                        }
                        Vector<Byte> Command = vector;
                        if (Command != null && Command.size() > 0) {
                            byte[] sendData = new byte[Command.size()];
                            for (int e = 0; e < Command.size(); ++e) {
                                sendData[e] = ((Byte) Command.get(e)).byteValue();
                            }
                            byteArray.add(sendData);
                        }
                    }
                    if (byteArray.size() > 0) {
                        LoopPrint(byteArray);
                    }
                }
            }.start();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    /**
     * 循环打印
     * 需要修改相应包名
     */
    private synchronized void LoopPrint(ArrayList<byte[]> data) {
        if (!ActivityCollector.isServiceRunning(getApplicationContext(),
                "com.transpos.market.utils.usbPrint.UsbPhoneService")) {
            Intent intent = new Intent(this, UsbPhoneService.class);
            startService(intent);
            Intent it = new Intent();
            it.setAction(UsbPhoneService.ACTION_USB_ADD_USBREMIN_DATA);
            it.putExtra("data", (Serializable) data);
            sendBroadcast(it);
        } else {
            Intent it = new Intent();
            it.setAction(UsbPhoneService.ACTION_USB_ADD_USBREMIN_DATA);
            it.putExtra("data", (Serializable) data);
            sendBroadcast(it);
        }
    }
}
