package com.newsuper.t.consumer.manager;


import com.newsuper.t.consumer.bean.AddressBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class AddressManager  {
    private static AddressManager manager;
    private ArrayList<AddressBean.AddressList> addresslist;
    public static String cAddressId = "-1";
    private AddressManager(){
        addresslist = new ArrayList<>();
    }
    public static AddressManager getInstance(){
        if (manager == null){
            manager = new AddressManager();
        }
        return manager;
    }

    public void saveAddressList(ArrayList<AddressBean.AddressList> lists){
        if (lists != null && lists.size() > 0){
            addresslist.clear();
            addresslist.addAll(lists);
        }
    }
    public void addAddressList(AddressBean.AddressList lists){
        if (lists != null){
            if (addresslist.size() > 0){
                for(AddressBean.AddressList l:addresslist){
                    if (l.id.equals(lists.id)){
                        addresslist.remove(l);
                        addresslist.add(lists);
                    }
                }
            }else {
                addresslist.add(lists);
            }
        }
    }
    public  AddressBean.AddressList getAddress() {
        AddressBean.AddressList lists = null;
        if (addresslist.size() > 0) {
            lists = new AddressBean.AddressList();
            for (AddressBean.AddressList l : addresslist) {
                if (l.id.equals(cAddressId)) {
                    lists = l;
                }
            }
        }
        return lists;
    }
    public ArrayList<AddressBean.AddressList> getAddressList(){
       return addresslist;
    }

}
