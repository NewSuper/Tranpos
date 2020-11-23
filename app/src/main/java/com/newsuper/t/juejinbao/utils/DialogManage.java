package com.newsuper.t.juejinbao.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;

import com.newsuper.t.juejinbao.base.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

public class DialogManage {


    private static DialogManage instance;

    private List<DialogBean> dialogList = new ArrayList<>();//还未显示的dialog的list，这里是无序的
    private List<DialogBean> currentDialogList = new ArrayList<>();//当前显示的dialog的list，最后一个就是显示在最上面的，是按优先级正序的。这里需要理解下，因为只有比上一个dialog优先级高的时候，才会显示并加入到这个list。

    public DialogManage() {

    }

    /**
     * 创建单例
     */
    public static DialogManage getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                instance = new DialogManage();
            }
        }
        return instance;
    }

    /**
     * 添加一个dialog，什么时候显示dialog交给DialogManage来管理
     * 默认最低优先级
     * @param dialog
     */
    public void addDialog(Dialog dialog) {
        if(dialog!=null){
            DialogBean dialogBean = new DialogBean();
            dialogBean.setDialog(dialog);
            dialogBean.setDialogView(dialog.getWindow().getDecorView());
            show(dialogBean);
        }
    }

    /**
     * 添加一个dialog，什么时候显示dialog交给DialogManage来管理
     * @param dialog
     * @param priority 优先级
     */
    public void addDialog(Dialog dialog, int priority) {
        if(dialog!=null) {
            DialogBean dialogBean = new DialogBean();
            dialogBean.setDialog(dialog);
            dialogBean.setDialogView(dialog.getWindow().getDecorView());
            dialogBean.setPriority(priority);
            show(dialogBean);
        }
    }

    private void show(final DialogBean newDialog) {
        //都需要设置监听
        newDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                currentDialogList.remove(newDialog);//关闭后从显示列表上删除
                nextDialog();
            }
        });

        //先计算出要弹出的弹窗
        if (currentDialogList.size() == 0) {//没有弹窗显示时
            //直接显示新的dialog
            newDialog.getDialog().show();
            currentDialogList.add(newDialog);//加入到已显示的列表
        } else {//有弹窗显示时
            if (newDialog.getPriority() > currentDialogList.get(currentDialogList.size() - 1).getPriority()) {//优先级高于当前显示的dialog
                //显示新的dialog
                newDialog.getDialog().show();
                //之前显示的弹窗暂时隐藏掉
                currentDialogList.get(currentDialogList.size() - 1).getDialogView().setVisibility(View.INVISIBLE);
                currentDialogList.add(newDialog);//加入到已显示的列表
            } else {//优先级和当前的dialog相等或者低于当前
                dialogList.add(newDialog);//加入到待显示列表
            }
        }
    }

    /**
     * 计算得出下一个要展示的dialog
     */
    private void nextDialog() {
        //未显示列表里无dialog时
        if (dialogList.size() == 0) {
            //显示列表不为空时
            if (currentDialogList.size() > 0) {
                //继续展示之前已经展示出来的
                currentDialogList.get(currentDialogList.size()-1).getDialogView().setVisibility(View.VISIBLE);
            }else {
                //显示列表为空时
                //不做处理
            }
        }
        //未显示列表里有dialog时
        else {
            //显示列表不为空时
            if (currentDialogList.size() > 0) {
                //1、先拿取已显示list里优先级最高的
                DialogBean currentDialogBean = currentDialogList.get(currentDialogList.size() - 1);//最后一个就是优先级最高的
                //2、再拿取未显示list里优先级最高的
                DialogBean notShowDialogBean = dialogList.get(0);
                for (int i = 0; i < dialogList.size(); i++) {
                    if (notShowDialogBean.getPriority() < dialogList.get(i).getPriority()) {
                        notShowDialogBean = dialogList.get(i);
                    }
                }
                //3、对比出优先级最高的，如果优先级一样。那么依旧显示之前显示的
                //4、显示优先级较高的
                if (currentDialogBean.getPriority() < notShowDialogBean.getPriority()) {//未展示的优先级较高
                    notShowDialogBean.getDialog().show();//展示未展示的
                    currentDialogList.add(notShowDialogBean);//加入到展示列表里
                    dialogList.remove(notShowDialogBean);//从未显示列表里删除
                } else {
                    //继续展示之前已经展示出来的
                    currentDialogList.get(currentDialogList.size()-1).getDialogView().setVisibility(View.VISIBLE);
                }
            } else {
                //显示列表为空时
                //1、再拿取未显示list里优先级最高的
                DialogBean notShowDialogBean = dialogList.get(0);
                for (int i = 0; i < dialogList.size(); i++) {
                    if (notShowDialogBean.getPriority() < dialogList.get(i).getPriority()) {
                        notShowDialogBean = dialogList.get(i);
                    }
                }

                //2、显示优先级较高的
                notShowDialogBean.getDialog().show();
                currentDialogList.add(notShowDialogBean);//加入到展示列表里
                dialogList.remove(notShowDialogBean);//从未显示列表里删除
            }
        }
    }

    public static class DialogBean {
        private Dialog dialog;
        private View dialogView;//dialog的view
        private int priority;//优先级

        public Dialog getDialog() {
            return dialog;
        }

        public void setDialog(Dialog dialog) {
            this.dialog = dialog;
        }

        public View getDialogView() {
            return dialogView;
        }

        public void setDialogView(View dialogView) {
            this.dialogView = dialogView;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }
    }
}
