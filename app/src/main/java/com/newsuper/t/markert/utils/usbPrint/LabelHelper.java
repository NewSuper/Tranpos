package com.newsuper.t.markert.utils.usbPrint;

import java.util.Vector;

public class LabelHelper {
    public static Vector printMaterialLabel(final String name, final String price,
                                            final String printDate, final String goodsp_code, final String printUnit){
        TscCommand tsc = new TscCommand(40, 30, 2);//设置标签尺寸宽度、高度、间隙
        tsc.addDIRECTION(TscCommand.DIRECTION.BACKWARD); //设置原点坐标
        tsc.addShif(0);
        tsc.addPeel(EscCommand.ENABLE.OFF);
        tsc.addCls();
        if (name.length() > 30) {
            tsc.addText(15, 15, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, name.substring(0, 8));
            tsc.addText(15, 42, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, name.substring(8, 19));
            tsc.addText(15, 69, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, price + printUnit);

            tsc.addText(15, 96, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, printDate );
        } else if (name.length() > 19) {
            tsc.addText(15, 15, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, name.substring(0, 8));
            tsc.addText(15, 42, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, name.substring(8, 19));
            tsc.addText(15, 69, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, price + printUnit);

            tsc.addText(15, 96, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, printDate);
        } else if (name.length() > 8) {
            tsc.addText(15, 15, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, name.substring(0, 8));
            tsc.addText(15, 42, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, name.substring(8, name.length()));
            tsc.addText(15, 69, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, price + printUnit);


            tsc.addText(15, 96, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, printDate );
        } else {
            tsc.addText(15, 15, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, name);
            tsc.addText(15, 42, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, price + printUnit);


            tsc.addText(15, 69, TscCommand.FONTTYPE.SIMPLIFIED_CHINESE,
                    TscCommand.ROTATION.ROTATION_0, TscCommand.FONTMUL.MUL_1, TscCommand.FONTMUL.MUL_1, printDate);
        }
        tsc.add1DBarcode(15, 125, TscCommand.BARCODETYPE.EAN128, 80, TscCommand.READABEL.DISABLE, TscCommand.ROTATION.ROTATION_0, goodsp_code);

        tsc.addPrint(1);

        Vector<Byte> Command = new Vector<Byte>(4096, 1024);
        Command = tsc.getCommand();//获取上面编辑的打印命令
        return Command;
    }
}
