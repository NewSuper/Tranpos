package com.juejinchain.steplibrary;

interface ISportStepInterface {
    /**
     * 获取当前时间运动步数
     */
     long getCurrentTimeSportStep();

     /**
     * 步数清空
     */
     void clean();

     /**
     * 是否有计步器
     */
     boolean canWalk();
}
