package com.transpos.market.utils.usbPrint;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * USB打印机工具类
 */
public class UsbPrintUtils {
    public static int blength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public static ArrayList<String> spiltStr(String str, int size) {
        ArrayList<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            /* 获取一个字符 */
            String temp = str.substring(i, i + 1);

            if (blength(sb.toString()) >= size) {
                list.add(sb.toString());
                sb = new StringBuilder();
                sb.append(temp);
            } else {
                sb.append(temp);
            }
        }
        if (!TextUtils.isEmpty(sb.toString())) {
            list.add(sb.toString());
        }
        return list;
    }

    public static byte[] addSetAbsolutePrintPosition(short n) {
        byte[] command = new byte[]{(byte) 27, (byte) 36, (byte) 0, (byte) 0};
        byte nl = (byte) (n % 256);
        byte nh = (byte) (n / 256);
        command[2] = nl;
        command[3] = nh;
        return command;
    }

    public static byte[] setChineses() {
        return new byte[]{(byte) 27, (byte) 64};
    }

    public static byte[] setSize(int size) {
        return new byte[]{(byte) 29, (byte) 33, (byte) (size)};
    }

    public static byte[] setChinesesDouble() {
        return new byte[]{(byte) 28, (byte) 33, (byte) 12};
    }

    public static byte[] setNoChinesesDouble() {
        return new byte[]{(byte) 28, (byte) 33, (byte) 0};
    }

    public static byte[] setPosition(int i) {
        return new byte[]{(byte) 27, (byte) 97, (byte) i};
    }


    public static ArrayList<byte[]> PrintOrderList58mm2(String name, String value, String num, String price, String oriPrice) {
        ArrayList<byte[]> printBytes = new ArrayList<>();
        try {
            if (blength(name) > 12) {
                if (blength(name) > 32) {
                    int a = name.length() / 16;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 16, (b + 1) * 16) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 16 != 0) {
                        String elsename = name.substring(a * 16, name.length());
                        if (blength(elsename) > 12) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            printBytes.add("            ".getBytes("GB2312"));
                            int pricelen = (value).length();
                            if (pricelen > 8) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if (((8 - pricelen) / 2) != 0) {
                                    printBytes.add(getSpace((8 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                if((8 - (8 - pricelen) / 2 - blength(value))!=0) {
                                    printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                                }
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 6) {
                                printBytes.add(num.getBytes("GB2312"));
                            } else {
                                if (((6 - quantitylen) / 2) != 0) {
                                    printBytes.add(getSpace((6 - quantitylen) / 2));
                                }
                                printBytes.add(num.getBytes("GB2312"));
                                printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                            }
                            int totallen = (price + "").length();
                            if (totallen > 6) {
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(6 - totallen));
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            }
                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            printBytes.add(getSpace(12 - blength(elsename)));
                            int pricelen = (value).length();
                            if (pricelen > 8) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if (((8 - pricelen) / 2) != 0) {
                                    printBytes.add(getSpace((8 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 6) {
                                printBytes.add(num.getBytes("GB2312"));
                            } else {
                                if (((6 - quantitylen) / 2) != 0) {
                                    printBytes.add(getSpace((6 - quantitylen) / 2));
                                }
                                printBytes.add(num.getBytes("GB2312"));
                                printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                            }
                            int totallen = (price + "").length();
                            if (totallen > 6) {
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(6 - totallen));
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            }
                        }
                    } else {
                        int pricelen = (value).length();
                        printBytes.add("            ".getBytes("GB2312"));
                        if (pricelen > 8) {
                            printBytes.add(value.getBytes("GB2312"));
                        } else {
                            if (((8 - pricelen) / 2) != 0) {
                                printBytes.add(getSpace((8 - pricelen) / 2));
                            }
                            printBytes.add(value.getBytes("GB2312"));
                            printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                        }
                        int quantitylen = (num).length();
                        if (quantitylen > 6) {
                            printBytes.add(num.getBytes("GB2312"));
                        } else {
                            if (((6 - quantitylen) / 2) != 0) {
                                printBytes.add(getSpace((6 - quantitylen) / 2));
                            }
                            printBytes.add(num.getBytes("GB2312"));
                            printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                        }
                        int totallen = (price + "").length();
                        if (totallen > 6) {
                            printBytes.add((price + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(getSpace(6 - totallen));
                            printBytes.add((price + "\n").getBytes("GB2312"));
                        }
                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    printBytes.add("            ".getBytes("GB2312"));
                    int pricelen = (value).length();
                    if (pricelen > 8) {
                        printBytes.add(value.getBytes("GB2312"));
                    } else {
                        if (((8 - pricelen) / 2) != 0) {
                            printBytes.add(getSpace((8 - pricelen) / 2));
                        }
                        printBytes.add(value.getBytes("GB2312"));
                        printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                    }
                    int quantitylen = (num).length();
                    if (quantitylen > 6) {
                        printBytes.add(num.getBytes("GB2312"));
                    } else {
                        if (((6 - quantitylen) / 2) != 0) {
                            printBytes.add(getSpace((6 - quantitylen) / 2));
                        }
                        printBytes.add(num.getBytes("GB2312"));
                        printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                    }
                    int totallen = (price + "").length();
                    if (totallen > 6) {
                        printBytes.add((price + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(getSpace(6 - totallen));
                        printBytes.add((price + "\n").getBytes("GB2312"));
                    }
                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                printBytes.add(getSpace(12 - blength(name)));
                int pricelen = (value).length();
                if (pricelen > 8) {
                    printBytes.add(value.getBytes("GB2312"));
                } else {
                    if (((8 - pricelen) / 2) != 0) {
                        printBytes.add(getSpace((8 - pricelen) / 2));
                    }
                    printBytes.add(value.getBytes("GB2312"));
                    printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                }
                int quantitylen = (num).length();
                if (quantitylen > 6) {
                    printBytes.add(num.getBytes("GB2312"));
                } else {
                    if (((6 - quantitylen) / 2) != 0) {
                        printBytes.add(getSpace((6 - quantitylen) / 2));
                    }
                    printBytes.add(num.getBytes("GB2312"));
                    printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                }
                int totallen = (price + "").length();
                if (totallen > 6) {
                    printBytes.add((price + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(getSpace(6 - totallen));
                    printBytes.add((price + "\n").getBytes("GB2312"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printBytes;
    }

    public static ArrayList<byte[]> PrintOrderList58mm(String name, String value, String num, String price) {
        ArrayList<byte[]> printBytes = new ArrayList<>();
        try {
            if (blength(name) > 12) {
                if (blength(name) > 32) {
                    int a = name.length() / 16;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 16, (b + 1) * 16) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 16 != 0) {
                        String elsename = name.substring(a * 16, name.length());
                        if (blength(elsename) > 12) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            printBytes.add("            ".getBytes("GB2312"));
                            int pricelen = (value).length();
                            if (pricelen > 8) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if (((8 - pricelen) / 2) != 0) {
                                    printBytes.add(getSpace((8 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                if((8 - (8 - pricelen) / 2 - blength(value))!=0) {
                                    printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                                }
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 6) {
                                printBytes.add(num.getBytes("GB2312"));
                            } else {
                                if (((6 - quantitylen) / 2) != 0) {
                                    printBytes.add(getSpace((6 - quantitylen) / 2));
                                }
                                printBytes.add(num.getBytes("GB2312"));
                                printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                            }
                            int totallen = (price + "").length();
                            if (totallen > 6) {
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(6 - totallen));
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            }
                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            printBytes.add(getSpace(12 - blength(elsename)));
                            int pricelen = (value).length();
                            if (pricelen > 8) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if (((8 - pricelen) / 2) != 0) {
                                    printBytes.add(getSpace((8 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 6) {
                                printBytes.add(num.getBytes("GB2312"));
                            } else {
                                if (((6 - quantitylen) / 2) != 0) {
                                    printBytes.add(getSpace((6 - quantitylen) / 2));
                                }
                                printBytes.add(num.getBytes("GB2312"));
                                printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                            }
                            int totallen = (price + "").length();
                            if (totallen > 6) {
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(6 - totallen));
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            }
                        }
                    } else {
                        int pricelen = (value).length();
                        printBytes.add("            ".getBytes("GB2312"));
                        if (pricelen > 8) {
                            printBytes.add(value.getBytes("GB2312"));
                        } else {
                            if (((8 - pricelen) / 2) != 0) {
                                printBytes.add(getSpace((8 - pricelen) / 2));
                            }
                            printBytes.add(value.getBytes("GB2312"));
                            printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                        }
                        int quantitylen = (num).length();
                        if (quantitylen > 6) {
                            printBytes.add(num.getBytes("GB2312"));
                        } else {
                            if (((6 - quantitylen) / 2) != 0) {
                                printBytes.add(getSpace((6 - quantitylen) / 2));
                            }
                            printBytes.add(num.getBytes("GB2312"));
                            printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                        }
                        int totallen = (price + "").length();
                        if (totallen > 6) {
                            printBytes.add((price + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(getSpace(6 - totallen));
                            printBytes.add((price + "\n").getBytes("GB2312"));
                        }
                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    printBytes.add("            ".getBytes("GB2312"));
                    int pricelen = (value).length();
                    if (pricelen > 8) {
                        printBytes.add(value.getBytes("GB2312"));
                    } else {
                        if (((8 - pricelen) / 2) != 0) {
                            printBytes.add(getSpace((8 - pricelen) / 2));
                        }
                        printBytes.add(value.getBytes("GB2312"));
                        printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                    }
                    int quantitylen = (num).length();
                    if (quantitylen > 6) {
                        printBytes.add(num.getBytes("GB2312"));
                    } else {
                        if (((6 - quantitylen) / 2) != 0) {
                            printBytes.add(getSpace((6 - quantitylen) / 2));
                        }
                        printBytes.add(num.getBytes("GB2312"));
                        printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                    }
                    int totallen = (price + "").length();
                    if (totallen > 6) {
                        printBytes.add((price + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(getSpace(6 - totallen));
                        printBytes.add((price + "\n").getBytes("GB2312"));
                    }
                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                printBytes.add(getSpace(12 - blength(name)));
                int pricelen = (value).length();
                if (pricelen > 8) {
                    printBytes.add(value.getBytes("GB2312"));
                } else {
                    if (((8 - pricelen) / 2) != 0) {
                        printBytes.add(getSpace((8 - pricelen) / 2));
                    }
                    printBytes.add(value.getBytes("GB2312"));
                    printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                }
                int quantitylen = (num).length();
                if (quantitylen > 6) {
                    printBytes.add(num.getBytes("GB2312"));
                } else {
                    if (((6 - quantitylen) / 2) != 0) {
                        printBytes.add(getSpace((6 - quantitylen) / 2));
                    }
                    printBytes.add(num.getBytes("GB2312"));
                    printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                }
                int totallen = (price + "").length();
                if (totallen > 6) {
                    printBytes.add((price + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(getSpace(6 - totallen));
                    printBytes.add((price + "\n").getBytes("GB2312"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printBytes;
    }

    private static byte[] getSpace(int num) {
        byte[] result = null;
        if (num != 0) {
            String result1 = "";
            for (int i = 0; i < num; i++) {
                result1 += " ";
            }
            try {
                result = result1.getBytes("GB2312");
            } catch (Exception e) {

            }
        } else {
            try {
                result = "".getBytes("GB2312");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static ArrayList<byte[]> PrintOrderList58mmType2(String name, String num) {
        ArrayList<byte[]> printBytes = new ArrayList<>();
        try {
            if (blength(name) > 24) {
                if (blength(name) > 32) {
                    int a = name.length() / 16;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 16, (b + 1) * 16) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 16 != 0) {
                        String elsename = name.substring(a * 16, name.length());
                        if (blength(elsename) > 24) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            printBytes.add("                        ".getBytes("GB2312"));
                            int numlen = (num).length();
                            if (numlen > 8) {
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(8 - numlen));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }

                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            printBytes.add(getSpace(24 - blength(elsename)));
                            int numlen = (num).length();
                            if (numlen > 8) {
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(8 - numlen));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }
                        }
                    } else {
                        printBytes.add("                        ".getBytes("GB2312"));
                        int numlen = (num).length();
                        if (numlen >8) {
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(getSpace(8 - numlen));
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        }
                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    printBytes.add("                        ".getBytes("GB2312"));
                    int numlen = (num).length();
                    if (numlen > 8) {
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(getSpace(8 - numlen));
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    }
                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                printBytes.add(getSpace(24 - blength(name)));
                int numlen = (num).length();
                if (numlen > 8) {
                    printBytes.add((num + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(getSpace(8 - numlen));
                    printBytes.add((num + "\n").getBytes("GB2312"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printBytes;
    }

    public static ArrayList<byte[]> PrintOrderList58mmType3(String name, String num) {
        ArrayList<byte[]> printBytes = new ArrayList<>();
        try {
            if (blength(name) > 8) {
                if (blength(name) > 24) {
                    int a = name.length() / 12;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 12, (b + 1) * 12) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 12 != 0) {
                        String elsename = name.substring(a * 12, name.length());
                        if (blength(elsename) > 8) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            int numlen = (num).length();
                            if (numlen > 4) {
                                printBytes.add(addSetAbsolutePrintPosition((short) (4 * 72)));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(addSetAbsolutePrintPosition((short) (4 * 72 + (2 - numlen) * 36)));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }

                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            int numlen = (num).length();
                            if (numlen > 4) {
                                printBytes.add(addSetAbsolutePrintPosition((short) (4 * 72)));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(addSetAbsolutePrintPosition((short) (4 * 72 + (2 - numlen) * 36)));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }
                        }
                    } else {
                        int numlen = (num).length();
                        if (numlen > 4) {
                            printBytes.add(addSetAbsolutePrintPosition((short) (4 * 72)));
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(addSetAbsolutePrintPosition((short) (4 * 72 + (2 - numlen) * 36)));
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        }
                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    int numlen = (num).length();
                    if (numlen > 4) {
                        printBytes.add(addSetAbsolutePrintPosition((short) (4 * 72)));
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(addSetAbsolutePrintPosition((short) (4 * 72 + (2 - numlen) * 36)));
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    }
                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                int numlen = (num).length();
                if (numlen > 4) {
                    printBytes.add(addSetAbsolutePrintPosition((short) (4 * 72)));
                    printBytes.add((num + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(addSetAbsolutePrintPosition((short) (4 * 72 + (2 - numlen) * 36)));
                    printBytes.add((num + "\n").getBytes("GB2312"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printBytes;
    }

    public static ArrayList<byte[]> PrintOrderList88mm(String name, String value, String num, String price) {
        ArrayList<byte[]> printBytes = new ArrayList<>();
        try {
            if (blength(name) > 28) {
                if (blength(name) > 48) {
                    int a = name.length() / 24;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 24, (b + 1) * 24) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 24 != 0) {
                        String elsename = name.substring(a * 24, name.length());
                        if (blength(elsename) > 28) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            printBytes.add("                            ".getBytes("GB2312"));
                            int pricelen = (value).length();
                            if (pricelen > 8) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if (((8 - pricelen) / 2) != 0) {
                                    printBytes.add(getSpace((8 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                if((8 - (8 - pricelen) / 2 - blength(value))!=0) {
                                    printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                                }

                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 6) {
                                printBytes.add(num.getBytes("GB2312"));
                            } else {
                                if (((6 - quantitylen) / 2) != 0) {
                                    printBytes.add(getSpace((6 - quantitylen) / 2));
                                }
                                printBytes.add(num.getBytes("GB2312"));
                                printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                            }
                            int totallen = (price + "").length();
                            if (totallen > 6) {
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(6 - totallen));
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            }
                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            printBytes.add(getSpace(28 - blength(elsename)));
                            int pricelen = (value).length();
                            if (pricelen > 8) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if (((8 - pricelen) / 2) != 0) {
                                    printBytes.add(getSpace((8 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 6) {
                                printBytes.add(num.getBytes("GB2312"));
                            } else {
                                if (((6 - quantitylen) / 2) != 0) {
                                    printBytes.add(getSpace((6 - quantitylen) / 2));
                                }
                                printBytes.add(num.getBytes("GB2312"));
                                printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                            }
                            int totallen = (price + "").length();
                            if (totallen > 6) {
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(6 - totallen));
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            }
                        }
                    } else {
                        int pricelen = (value).length();
                        printBytes.add("                        ".getBytes("GB2312"));
                        if (pricelen > 8) {
                            printBytes.add(value.getBytes("GB2312"));
                        } else {
                            if (((8 - pricelen) / 2) != 0) {
                                printBytes.add(getSpace((8 - pricelen) / 2));
                            }
                            printBytes.add(value.getBytes("GB2312"));
                            printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                        }
                        int quantitylen = (num).length();
                        if (quantitylen > 6) {
                            printBytes.add(num.getBytes("GB2312"));
                        } else {
                            if (((6 - quantitylen) / 2) != 0) {
                                printBytes.add(getSpace((6 - quantitylen) / 2));
                            }
                            printBytes.add(num.getBytes("GB2312"));
                            printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                        }
                        int totallen = (price + "").length();
                        if (totallen > 6) {
                            printBytes.add((price + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(getSpace(6 - totallen));
                            printBytes.add((price + "\n").getBytes("GB2312"));
                        }
                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    printBytes.add("                          ".getBytes("GB2312"));
                    int pricelen = (value).length();
                    if (pricelen > 8) {
                        printBytes.add(value.getBytes("GB2312"));
                    } else {
                        if (((8 - pricelen) / 2) != 0) {
                            printBytes.add(getSpace((8 - pricelen) / 2));
                        }
                        printBytes.add(value.getBytes("GB2312"));
                        printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                    }
                    int quantitylen = (num).length();
                    if (quantitylen > 6) {
                        printBytes.add(num.getBytes("GB2312"));
                    } else {
                        if (((6 - quantitylen) / 2) != 0) {
                            printBytes.add(getSpace((6 - quantitylen) / 2));
                        }
                        printBytes.add(num.getBytes("GB2312"));
                        printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                    }
                    int totallen = (price + "").length();
                    if (totallen > 6) {
                        printBytes.add((price + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(getSpace(6 - totallen));
                        printBytes.add((price + "\n").getBytes("GB2312"));
                    }
                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                printBytes.add(getSpace(28 - blength(name)));
                int pricelen = (value).length();
                if (pricelen > 8) {
                    printBytes.add(value.getBytes("GB2312"));
                } else {
                    if (((8 - pricelen) / 2) != 0) {
                        printBytes.add(getSpace((8 - pricelen) / 2));
                    }
                    printBytes.add(value.getBytes("GB2312"));
                    printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                }
                int quantitylen = (num).length();
                if (quantitylen > 6) {
                    printBytes.add(num.getBytes("GB2312"));
                } else {
                    if (((6 - quantitylen) / 2) != 0) {
                        printBytes.add(getSpace((6 - quantitylen) / 2));
                    }
                    printBytes.add(num.getBytes("GB2312"));
                    printBytes.add(getSpace(6 - (6 - quantitylen) / 2 - blength(num)));
                }
                int totallen = (price + "").length();
                if (totallen > 6) {
                    printBytes.add((price + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(getSpace(6 - totallen));
                    printBytes.add((price + "\n").getBytes("GB2312"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return printBytes;
    }

    public static ArrayList<byte[]> PrintOrderList88mmType2(String name, String num) {
        ArrayList<byte[]> printBytes = new ArrayList<>();
        try {
            if (blength(name) > 40) {
                if (blength(name) > 48) {
                    int a = name.length() / 24;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 24, (b + 1) * 24) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 24 != 0) {
                        String elsename = name.substring(a * 24, name.length());
                        if (blength(elsename) > 40) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            printBytes.add("                                       ".getBytes("GB2312"));
                            int numlen = (num).length();
                            if (numlen > 8) {
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(8 - numlen));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }

                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            printBytes.add(getSpace(40 - blength(elsename)));
                            int numlen = (num).length();
                            if (numlen > 8) {
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(8 - numlen));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }
                        }
                    } else {
                        printBytes.add("                        ".getBytes("GB2312"));
                        int numlen = (num).length();
                        if (numlen > 8) {
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(getSpace(8 - numlen));
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        }
                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    printBytes.add("                          ".getBytes("GB2312"));
                    int numlen = (num).length();
                    if (numlen > 8) {
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(getSpace(8 - numlen));
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    }
                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                printBytes.add(getSpace(40 - blength(name)));
                int numlen = (num).length();
                if (numlen > 8) {
                    printBytes.add((num + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(getSpace(8 - numlen));
                    printBytes.add((num + "\n").getBytes("GB2312"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printBytes;
    }

    public static ArrayList<byte[]> PrintOrderList88mmType3(String name, String num) {
        ArrayList<byte[]> printBytes = new ArrayList<>();
        try {
            if (blength(name) > 40) {
                if (blength(name) > 48) {
                    int a = name.length() / 24;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 24, (b + 1) * 24) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 24 != 0) {
                        String elsename = name.substring(a * 24, name.length());
                        if (blength(elsename) > 40) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            printBytes.add("                                       ".getBytes("GB2312"));
                            int numlen = (num).length();
                            if (numlen > 8) {
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(8 - numlen));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }

                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            printBytes.add(getSpace(40 - blength(elsename)));
                            int numlen = (num).length();
                            if (numlen > 8) {
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(8 - numlen));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }
                        }
                    } else {
                        printBytes.add("                        ".getBytes("GB2312"));
                        int numlen = (num).length();
                        if (numlen > 8) {
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(getSpace(8 - numlen));
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        }
                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    printBytes.add("                          ".getBytes("GB2312"));
                    int numlen = (num).length();
                    if (numlen > 8) {
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(getSpace(8 - numlen));
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    }
                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                printBytes.add(getSpace(40 - blength(name)));
                int numlen = (num).length();
                if (numlen > 8) {
                    printBytes.add((num + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(getSpace(8 - numlen));
                    printBytes.add((num + "\n").getBytes("GB2312"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printBytes;
    }

    public static ArrayList<byte[]> print58mmType4(String name, String value, String num) {
        ArrayList<byte[]> printBytes = new ArrayList<>();
        try {
            if (blength(name) > 12) {
                if (blength(name) > 32) {
                    int a = name.length() / 16;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 16, (b + 1) * 16) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 16 != 0) {
                        String elsename = name.substring(a * 16, name.length());
                        if (blength(elsename) > 12) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            printBytes.add("            ".getBytes("GB2312"));
                            int pricelen = (value).length();
                            if (pricelen > 8) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if ((8 - pricelen) / 2 != 0) {
                                    printBytes.add(getSpace((8 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 12) {
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(12 - quantitylen));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }

                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            printBytes.add(getSpace(12 - blength(elsename)));
                            int pricelen = (value).length();
                            if (pricelen > 8) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if ((8 - pricelen) / 2 != 0) {
                                    printBytes.add(getSpace((8 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 12) {
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(12 - quantitylen));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }

                        }
                    } else {
                        printBytes.add("            ".getBytes("GB2312"));
                        int pricelen = (value).length();
                        if (pricelen > 8) {
                            printBytes.add(value.getBytes("GB2312"));
                        } else {
                            if ((8 - pricelen) / 2 != 0) {
                                printBytes.add(getSpace((8 - pricelen) / 2));
                            }
                            printBytes.add(value.getBytes("GB2312"));
                            printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                        }
                        int quantitylen = (num).length();
                        if (quantitylen > 12) {
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(getSpace(12 - quantitylen));
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        }

                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    printBytes.add("            ".getBytes("GB2312"));
                    int pricelen = (value).length();
                    if (pricelen > 8) {
                        printBytes.add(value.getBytes("GB2312"));
                    } else {
                        if ((8 - pricelen) / 2 != 0) {
                            printBytes.add(getSpace((8 - pricelen) / 2));
                        }
                        printBytes.add(value.getBytes("GB2312"));
                        printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                    }
                    int quantitylen = (num).length();
                    if (quantitylen > 12) {
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(getSpace(12 - quantitylen));
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    }

                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                printBytes.add(getSpace(12 - blength(name)));
                int pricelen = (value).length();
                if (pricelen > 8) {
                    printBytes.add(value.getBytes("GB2312"));
                } else {
                    if ((8 - pricelen) / 2 != 0) {
                        printBytes.add(getSpace((8 - pricelen) / 2));
                    }
                    printBytes.add(value.getBytes("GB2312"));
                    printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(value)));
                }
                int quantitylen = (num).length();
                if (quantitylen > 12) {
                    printBytes.add((num + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(getSpace(12 - quantitylen));
                    printBytes.add((num + "\n").getBytes("GB2312"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("测试：：：：：" + e.toString());
        }
        return printBytes;

    }

    public static ArrayList<byte[]> print88mmType4(String name, String value, String num) {
        ArrayList<byte[]> printBytes = new ArrayList<>();
        try {
            if (blength(name) > 40) {
                if (blength(name) > 48) {
                    int a = name.length() / 24;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 24, (b + 1) * 24) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 24 != 0) {
                        String elsename = name.substring(a * 24, name.length());
                        if (blength(elsename) > 18) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            printBytes.add("                  ".getBytes("GB2312"));
                            int pricelen = (value).length();
                            if (pricelen > 12) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if ((12 - pricelen) / 2 != 0) {
                                    printBytes.add(getSpace((12 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                printBytes.add(getSpace(12 - (12 - pricelen) / 2 - blength(value)));
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 18) {
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(18 - quantitylen));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }

                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            printBytes.add(getSpace(18 - blength(elsename)));
                            int pricelen = (value).length();
                            if (pricelen > 12) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if ((12 - pricelen) / 2 != 0) {
                                    printBytes.add(getSpace((12 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                printBytes.add(getSpace(12 - (12 - pricelen) / 2 - blength(value)));
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 18) {
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(18 - quantitylen));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }

                        }
                    } else {
                        printBytes.add("                  ".getBytes("GB2312"));
                        int pricelen = (value).length();
                        if (pricelen > 12) {
                            printBytes.add(value.getBytes("GB2312"));
                        } else {
                            if ((12 - pricelen) / 2 != 0) {
                                printBytes.add(getSpace((12 - pricelen) / 2));
                            }
                            printBytes.add(value.getBytes("GB2312"));
                            printBytes.add(getSpace(12 - (12 - pricelen) / 2 - blength(value)));
                        }
                        int quantitylen = (num).length();
                        if (quantitylen > 18) {
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(getSpace(18 - quantitylen));
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        }

                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    printBytes.add("                  ".getBytes("GB2312"));
                    int pricelen = (value).length();
                    if (pricelen > 12) {
                        printBytes.add(value.getBytes("GB2312"));
                    } else {
                        if ((12 - pricelen) / 2 != 0) {
                            printBytes.add(getSpace((12 - pricelen) / 2));
                        }
                        printBytes.add(value.getBytes("GB2312"));
                        printBytes.add(getSpace(12 - (12 - pricelen) / 2 - blength(value)));
                    }
                    int quantitylen = (num).length();
                    if (quantitylen > 18) {
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(getSpace(18 - quantitylen));
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    }

                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                printBytes.add(getSpace(18 - blength(name)));
                int pricelen = (value).length();
                if (pricelen > 12) {
                    printBytes.add(value.getBytes("GB2312"));
                } else {
                    if ((12 - pricelen) / 2 != 0) {
                        printBytes.add(getSpace((12 - pricelen) / 2));
                    }
                    printBytes.add(value.getBytes("GB2312"));
                    printBytes.add(getSpace(12 - (12 - pricelen) / 2 - blength(value)));
                }
                int quantitylen = (num).length();
                if (quantitylen > 18) {
                    printBytes.add((num + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(getSpace(18 - quantitylen));
                    printBytes.add((num + "\n").getBytes("GB2312"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("测试：：：：：" + e.toString());
        }
        return printBytes;

    }

    public static ArrayList<byte[]> PrintOrderList58mm5(String name, String value, String num, String price) {
        ArrayList<byte[]> printBytes = new ArrayList<>();
        try {
            if (blength(name) > 10) {
                if (blength(name) > 32) {
                    int a = name.length() / 16;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 16, (b + 1) * 16) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 16 != 0) {
                        String elsename = name.substring(a * 16, name.length());
                        if (blength(elsename) > 10) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            printBytes.add("          ".getBytes("GB2312"));
                            int pricelen = (value).length();
                            if (pricelen > 6) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if ((6 - pricelen) / 2 != 0) {
                                    printBytes.add(getSpace((6 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                printBytes.add(getSpace(6 - (6 - pricelen) / 2 - blength(value)));
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 8) {
                                printBytes.add(num.getBytes("GB2312"));
                            } else {
                                if ((8 - quantitylen) / 2 != 0) {
                                    printBytes.add(getSpace((8 - quantitylen) / 2));
                                }
                                printBytes.add(num.getBytes("GB2312"));
                                printBytes.add(getSpace(8 - (8 - quantitylen) / 2 - blength(num)));
                            }
                            int totallen = (price + "").length();
                            if (totallen > 8) {
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(8 - totallen));
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            }
                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            printBytes.add(getSpace(12 - blength(elsename)));
                            int pricelen = (value).length();
                            if (pricelen > 6) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if ((6 - pricelen) / 2 != 0) {
                                    printBytes.add(getSpace((6 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                printBytes.add(getSpace(6 - (6 - pricelen) / 2 - blength(value)));
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 8) {
                                printBytes.add(num.getBytes("GB2312"));
                            } else {
                                if ((8 - quantitylen) / 2 != 0) {
                                    printBytes.add(getSpace((8 - quantitylen) / 2));
                                }
                                printBytes.add(num.getBytes("GB2312"));
                                printBytes.add(getSpace(8 - (8 - quantitylen) / 2 - blength(num)));
                            }
                            int totallen = (price + "").length();
                            if (totallen > 8) {
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(8 - totallen));
                                printBytes.add((price + "\n").getBytes("GB2312"));
                            }
                        }
                    } else {
                        int pricelen = (value).length();
                        printBytes.add("          ".getBytes("GB2312"));
                        if (pricelen > 6) {
                            printBytes.add(value.getBytes("GB2312"));
                        } else {
                            if ((6 - pricelen) / 2 != 0) {
                                printBytes.add(getSpace((6 - pricelen) / 2));
                            }
                            printBytes.add(value.getBytes("GB2312"));
                            printBytes.add(getSpace(6 - (6 - pricelen) / 2 - blength(value)));
                        }
                        int quantitylen = (num).length();
                        if (quantitylen > 8) {
                            printBytes.add(num.getBytes("GB2312"));
                        } else {
                            if ((8 - quantitylen) / 2 != 0) {
                                printBytes.add(getSpace((8 - quantitylen) / 2));
                            }
                            printBytes.add(num.getBytes("GB2312"));
                            printBytes.add(getSpace(8 - (8 - quantitylen) / 2 - blength(num)));
                        }
                        int totallen = (price + "").length();
                        if (totallen > 8) {
                            printBytes.add((price + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(getSpace(8 - totallen));
                            printBytes.add((price + "\n").getBytes("GB2312"));
                        }
                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    printBytes.add("          ".getBytes("GB2312"));
                    int pricelen = (value).length();
                    if (pricelen > 6) {
                        printBytes.add(value.getBytes("GB2312"));
                    } else {
                        if ((6 - pricelen) / 2 != 0) {
                            printBytes.add(getSpace((6 - pricelen) / 2));
                        }
                        printBytes.add(value.getBytes("GB2312"));
                        printBytes.add(getSpace(6 - (6 - pricelen) / 2 - blength(value)));
                    }
                    int quantitylen = (num).length();
                    if (quantitylen > 8) {
                        printBytes.add(num.getBytes("GB2312"));
                    } else {
                        if ((8 - quantitylen) / 2 != 0) {
                            printBytes.add(getSpace((8 - quantitylen) / 2));
                        }
                        printBytes.add(num.getBytes("GB2312"));
                        printBytes.add(getSpace(8 - (8 - quantitylen) / 2 - blength(num)));
                    }
                    int totallen = (price + "").length();
                    if (totallen > 8) {
                        printBytes.add((price + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(getSpace(8 - totallen));
                        printBytes.add((price + "\n").getBytes("GB2312"));
                    }
                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                printBytes.add(getSpace(10 - blength(name)));
                int pricelen = (value).length();
                if (pricelen > 6) {
                    printBytes.add(value.getBytes("GB2312"));
                } else {
                    if ((6 - pricelen) / 2 != 0) {
                        printBytes.add(getSpace((6 - pricelen) / 2));
                    }
                    printBytes.add(value.getBytes("GB2312"));
                    printBytes.add(getSpace(6 - (6 - pricelen) / 2 - blength(value)));
                }
                int quantitylen = (num).length();
                if (quantitylen > 8) {
                    printBytes.add(num.getBytes("GB2312"));
                } else {
                    if ((8 - quantitylen) / 2 != 0) {
                        printBytes.add(getSpace((8 - quantitylen) / 2));
                    }
                    printBytes.add(num.getBytes("GB2312"));
                    printBytes.add(getSpace(8 - (8 - quantitylen) / 2 - blength(num)));
                }
                int totallen = (price + "").length();
                if (totallen > 8) {
                    printBytes.add((price + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(getSpace(8 - totallen));
                    printBytes.add((price + "\n").getBytes("GB2312"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printBytes;
    }

    public static ArrayList<byte[]> PrintOrderList88mm5(String name, String value, String num, String price) {

        return PrintOrderList88mm(name,value,num,price);
    }

    /**
     * 采购预览单
     * 按名称   单价    采购数量  来打印
     *
     * @param name
     * @param price
     * @param amount
     * @return
     */
    public static ArrayList<byte[]> getPrintSKU58mm(String name, String price, String amount) {
        ArrayList<byte[]> printBytes = new ArrayList<>();
        try {
            if (blength(name) > 12) {
                if (blength(name) > 32) {
                    int a = name.length() / 16;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 16, (b + 1) * 16) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 16 != 0) {
                        String elsename = name.substring(a * 16, name.length());
                        if (blength(elsename) > 12) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            printBytes.add("            ".getBytes("GB2312"));
                            int pricelen = (price).length();
                            if (pricelen > 8) {
                                printBytes.add(price.getBytes("GB2312"));
                            } else {
                                if ((8 - pricelen) / 2 != 0) {
                                    printBytes.add(getSpace((8 - pricelen) / 2));
                                }
                                printBytes.add(price.getBytes("GB2312"));
                                printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(price)));
                            }

                            int totallen = (amount + "").length();
                            if (totallen > 12) {
                                printBytes.add((amount + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(12 - totallen));
                                printBytes.add((amount + "\n").getBytes("GB2312"));
                            }
                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            printBytes.add(getSpace(12 - blength(elsename)));
                            int pricelen = (price).length();
                            if (pricelen > 8) {
                                printBytes.add(price.getBytes("GB2312"));
                            } else {
                                if ((8 - pricelen) / 2 != 0) {
                                    printBytes.add(getSpace((8 - pricelen) / 2));
                                }
                                printBytes.add(price.getBytes("GB2312"));
                                printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(price)));
                            }

                            int totallen = (amount + "").length();
                            if (totallen > 12) {
                                printBytes.add((amount + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(12 - totallen));
                                printBytes.add((amount + "\n").getBytes("GB2312"));
                            }
                        }
                    } else {
                        int pricelen = (price).length();
                        printBytes.add("          ".getBytes("GB2312"));
                        if (pricelen > 8) {
                            printBytes.add(price.getBytes("GB2312"));
                        } else {
                            if ((8 - pricelen) / 2 != 0) {
                                printBytes.add(getSpace((8 - pricelen) / 2));
                            }
                            printBytes.add(price.getBytes("GB2312"));
                            printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(price)));
                        }

                        int totallen = (amount + "").length();
                        if (totallen > 12) {
                            printBytes.add((amount + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(getSpace(8 - totallen));
                            printBytes.add((amount + "\n").getBytes("GB2312"));
                        }
                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    printBytes.add("          ".getBytes("GB2312"));
                    int pricelen = (price).length();
                    if (pricelen > 8) {
                        printBytes.add(price.getBytes("GB2312"));
                    } else {
                        if ((8 - pricelen) / 2 != 0) {
                            printBytes.add(getSpace((8 - pricelen) / 2));
                        }
                        printBytes.add(price.getBytes("GB2312"));
                        printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(price)));
                    }

                    int totallen = (amount + "").length();
                    if (totallen > 12) {
                        printBytes.add((amount + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(getSpace(12 - totallen));
                        printBytes.add((amount + "\n").getBytes("GB2312"));
                    }
                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                printBytes.add(getSpace(12 - blength(name)));
                int pricelen = (price).length();
                if (pricelen > 8) {
                    printBytes.add(price.getBytes("GB2312"));
                } else {
                    if ((8 - pricelen) / 2 != 0) {
                        printBytes.add(getSpace((8 - pricelen) / 2));
                    }
                    printBytes.add(price.getBytes("GB2312"));
                    printBytes.add(getSpace(8 - (8 - pricelen) / 2 - blength(price)));
                }

                int totallen = (amount + "").length();
                if (totallen > 12) {
                    printBytes.add((amount + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(getSpace(12 - totallen));
                    printBytes.add((amount + "\n").getBytes("GB2312"));
                }
            }

        } catch (Exception e) {
            System.out.println("测试：" + e.toString());
            e.printStackTrace();
        }


        return printBytes;
    }

    public static ArrayList<byte[]> getPrintSKU88mm(String name, String value, String num) {
        ArrayList<byte[]> printBytes = new ArrayList<>();

        try {
            if (blength(name) > 40) {
                if (blength(name) > 48) {
                    int a = name.length() / 24;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 24, (b + 1) * 24) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 24 != 0) {
                        String elsename = name.substring(a * 24, name.length());
                        if (blength(elsename) > 18) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            printBytes.add("                  ".getBytes("GB2312"));
                            int pricelen = (value).length();
                            if (pricelen > 12) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if ((12 - pricelen) / 2 != 0) {
                                    printBytes.add(getSpace((12 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                printBytes.add(getSpace(12 - (12 - pricelen) / 2 - blength(value)));
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 18) {
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(18 - quantitylen));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }

                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            printBytes.add(getSpace(18 - blength(elsename)));
                            int pricelen = (value).length();
                            if (pricelen > 12) {
                                printBytes.add(value.getBytes("GB2312"));
                            } else {
                                if ((12 - pricelen) / 2 != 0) {
                                    printBytes.add(getSpace((12 - pricelen) / 2));
                                }
                                printBytes.add(value.getBytes("GB2312"));
                                printBytes.add(getSpace(12 - (12 - pricelen) / 2 - blength(value)));
                            }
                            int quantitylen = (num).length();
                            if (quantitylen > 18) {
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(18 - quantitylen));
                                printBytes.add((num + "\n").getBytes("GB2312"));
                            }

                        }
                    } else {
                        printBytes.add("                  ".getBytes("GB2312"));
                        int pricelen = (value).length();
                        if (pricelen > 12) {
                            printBytes.add(value.getBytes("GB2312"));
                        } else {
                            if ((12 - pricelen) / 2 != 0) {
                                printBytes.add(getSpace((12 - pricelen) / 2));
                            }
                            printBytes.add(value.getBytes("GB2312"));
                            printBytes.add(getSpace(12 - (12 - pricelen) / 2 - blength(value)));
                        }
                        int quantitylen = (num).length();
                        if (quantitylen > 18) {
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(getSpace(18 - quantitylen));
                            printBytes.add((num + "\n").getBytes("GB2312"));
                        }

                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    printBytes.add("                  ".getBytes("GB2312"));
                    int pricelen = (value).length();
                    if (pricelen > 12) {
                        printBytes.add(value.getBytes("GB2312"));
                    } else {
                        if ((12 - pricelen) / 2 != 0) {
                            printBytes.add(getSpace((12 - pricelen) / 2));
                        }
                        printBytes.add(value.getBytes("GB2312"));
                        printBytes.add(getSpace(12 - (12 - pricelen) / 2 - blength(value)));
                    }
                    int quantitylen = (num).length();
                    if (quantitylen > 18) {
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(getSpace(18 - quantitylen));
                        printBytes.add((num + "\n").getBytes("GB2312"));
                    }

                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                printBytes.add(getSpace(18 - blength(name)));
                int pricelen = (value).length();
                if (pricelen > 12) {
                    printBytes.add(value.getBytes("GB2312"));
                } else {
                    if ((12 - pricelen) / 2 != 0) {
                        printBytes.add(getSpace((12 - pricelen) / 2));
                    }
                    printBytes.add(value.getBytes("GB2312"));
                    printBytes.add(getSpace(12 - (12 - pricelen) / 2 - blength(value)));
                }
                int quantitylen = (num).length();
                if (quantitylen > 18) {
                    printBytes.add((num + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(getSpace(18 - quantitylen));
                    printBytes.add((num + "\n").getBytes("GB2312"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("测试：：：：：" + e.toString());
        }

        return printBytes;
    }

    /**
     * 原料入库
     *
     * @param name   商品名称
     * @param price  单价
     * @param amount 数量
     * @param money  金额
     * @return
     */
    public static ArrayList<byte[]> getPrintMaterial58mm(String name, String price, String amount, String money) {
        ArrayList<byte[]> printBytes = new ArrayList<>();
        try {
            if (blength(name) > 10) {
                if (blength(name) > 32) {
                    int a = name.length() / 16;
                    for (int b = 0; b < a; b++) {
                        printBytes.add((name.substring(b * 16, (b + 1) * 16) + "\n").getBytes("GB2312"));
                    }
                    if (name.length() % 16 != 0) {
                        String elsename = name.substring(a * 16, name.length());
                        if (blength(elsename) > 10) {
                            printBytes.add((elsename + "\n").getBytes("GB2312"));
                            printBytes.add("          ".getBytes("GB2312"));
                            int pricelen = (price).length();
                            if (pricelen > 6) {
                                printBytes.add(price.getBytes("GB2312"));
                            } else {
                                if ((6 - pricelen) / 2 != 0) {
                                    printBytes.add(getSpace((6 - pricelen) / 2));
                                }
                                printBytes.add(price.getBytes("GB2312"));
                                printBytes.add(getSpace(6 - (6 - pricelen) / 2 - blength(price)));
                            }
                            int quantitylen = (amount).length();
                            if (quantitylen > 8) {
                                printBytes.add(amount.getBytes("GB2312"));
                            } else {
                                if ((8 - quantitylen) / 2 != 0) {
                                    printBytes.add(getSpace((8 - quantitylen) / 2));
                                }
                                printBytes.add(amount.getBytes("GB2312"));
                                printBytes.add(getSpace(8 - (8 - quantitylen) / 2 - blength(amount)));
                            }
                            int totallen = (money + "").length();
                            if (totallen > 8) {
                                printBytes.add((money + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(8 - totallen));
                                printBytes.add((money + "\n").getBytes("GB2312"));
                            }
                        } else {
                            printBytes.add(elsename.getBytes("GB2312"));
                            printBytes.add(getSpace(12 - blength(elsename)));
                            int pricelen = (price).length();
                            if (pricelen > 6) {
                                printBytes.add(price.getBytes("GB2312"));
                            } else {
                                if ((6 - pricelen) / 2 != 0) {
                                    printBytes.add(getSpace((6 - pricelen) / 2));
                                }
                                printBytes.add(price.getBytes("GB2312"));
                                printBytes.add(getSpace(6 - (6 - pricelen) / 2 - blength(price)));
                            }
                            int quantitylen = (amount).length();
                            if (quantitylen > 8) {
                                printBytes.add(amount.getBytes("GB2312"));
                            } else {
                                if ((8 - quantitylen) / 2 != 0) {
                                    printBytes.add(getSpace((8 - quantitylen) / 2));
                                }
                                printBytes.add(amount.getBytes("GB2312"));
                                printBytes.add(getSpace(8 - (8 - quantitylen) / 2 - blength(amount)));
                            }
                            int totallen = (money + "").length();
                            if (totallen > 8) {
                                printBytes.add((money + "\n").getBytes("GB2312"));
                            } else {
                                printBytes.add(getSpace(8 - totallen));
                                printBytes.add((money + "\n").getBytes("GB2312"));
                            }
                        }
                    } else {
                        int pricelen = (price).length();
                        printBytes.add("          ".getBytes("GB2312"));
                        if (pricelen > 6) {
                            printBytes.add(price.getBytes("GB2312"));
                        } else {
                            if ((6 - pricelen) / 2 != 0) {
                                printBytes.add(getSpace((6 - pricelen) / 2));
                            }
                            printBytes.add(price.getBytes("GB2312"));
                            printBytes.add(getSpace(6 - (6 - pricelen) / 2 - blength(price)));
                        }
                        int quantitylen = (amount).length();
                        if (quantitylen > 8) {
                            printBytes.add(amount.getBytes("GB2312"));
                        } else {
                            if ((8 - quantitylen) / 2 != 0) {
                                printBytes.add(getSpace((8 - quantitylen) / 2));
                            }
                            printBytes.add(amount.getBytes("GB2312"));
                            printBytes.add(getSpace(8 - (8 - quantitylen) / 2 - blength(amount)));
                        }
                        int totallen = (money + "").length();
                        if (totallen > 8) {
                            printBytes.add((money + "\n").getBytes("GB2312"));
                        } else {
                            printBytes.add(getSpace(8 - totallen));
                            printBytes.add((money + "\n").getBytes("GB2312"));
                        }
                    }
                } else {
                    printBytes.add((name + "\n").getBytes("GB2312"));
                    printBytes.add("          ".getBytes("GB2312"));
                    int pricelen = (price).length();
                    if (pricelen > 6) {
                        printBytes.add(price.getBytes("GB2312"));
                    } else {
                        if ((6 - pricelen) / 2 != 0) {
                            printBytes.add(getSpace((6 - pricelen) / 2));
                        }
                        printBytes.add(price.getBytes("GB2312"));
                        printBytes.add(getSpace(6 - (6 - pricelen) / 2 - blength(price)));
                    }
                    int quantitylen = (amount).length();
                    if (quantitylen > 8) {
                        printBytes.add(amount.getBytes("GB2312"));
                    } else {
                        if ((8 - quantitylen) / 2 != 0) {
                            printBytes.add(getSpace((8 - quantitylen) / 2));
                        }
                        printBytes.add(amount.getBytes("GB2312"));
                        printBytes.add(getSpace(8 - (8 - quantitylen) / 2 - blength(amount)));
                    }
                    int totallen = (money + "").length();
                    if (totallen > 8) {
                        printBytes.add((money + "\n").getBytes("GB2312"));
                    } else {
                        printBytes.add(getSpace(8 - totallen));
                        printBytes.add((money + "\n").getBytes("GB2312"));
                    }
                }
            } else {
                printBytes.add(name.getBytes("GB2312"));
                printBytes.add(getSpace(10 - blength(name)));
                int pricelen = (price).length();
                if (pricelen > 6) {
                    printBytes.add(price.getBytes("GB2312"));
                } else {
                    if ((6 - pricelen) / 2 != 0) {
                        printBytes.add(getSpace((6 - pricelen) / 2));
                    }
                    printBytes.add(price.getBytes("GB2312"));
                    printBytes.add(getSpace(6 - (6 - pricelen) / 2 - blength(price)));
                }
                int quantitylen = (amount).length();
                if (quantitylen > 8) {
                    printBytes.add(amount.getBytes("GB2312"));
                } else {
                    if ((8 - quantitylen) / 2 != 0) {
                        printBytes.add(getSpace((8 - quantitylen) / 2));
                    }
                    printBytes.add(amount.getBytes("GB2312"));
                    printBytes.add(getSpace(8 - (8 - quantitylen) / 2 - blength(amount)));
                }
                int totallen = (money + "").length();
                if (totallen > 8) {
                    printBytes.add((money + "\n").getBytes("GB2312"));
                } else {
                    printBytes.add(getSpace(8 - totallen));
                    printBytes.add((money + "\n").getBytes("GB2312"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return printBytes;
    }

    public static ArrayList<byte[]> getPrintMaterial88mm(String name, String price, String amount, String money) {

        return PrintOrderList88mm(name,price,amount,money);
    }
}
