jjb
JJBApplication  oncreate()中
  1、paper存储初始化
  2、retrofit动态切换url
  3、新增异步分发初始化加载一些依赖的第3方库，就加快app启动速度
JueJinBaoLaunchActivity
  1、全屏广告页
  2.RxPermissions 全局权限控制  
  3、 /**
         * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
         */
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
   4.  倒计时写法
     private CountDownTimer mCountDownTimer = new CountDownTimer(COUNTER_LONG, 1000) {
             @Override
             public void onTick(long millisUntilFinished) {
                 mViewBinding.button.setText("跳过 " + millisUntilFinished / 1000);
             }
     
             @Override
             public void onFinish() {
                 getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                 Intent intent = new Intent(SplashActivity.this, JunjinBaoMainActivity.class);
                 startActivity(intent);
                 finish();
             }
         };
SplashActivity
   1.   //运行时权限处理
             List<String> permissionList = new ArrayList<>();
             if (ContextCompat.checkSelfPermission(this,
                     Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                 permissionList.add(Manifest.permission.READ_PHONE_STATE);
             }
             if (ContextCompat.checkSelfPermission(this,
                     Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                 permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
             }
             if (ContextCompat.checkSelfPermission(this,
                     Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                 permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
             }
             if (!permissionList.isEmpty()) {
                 String[] permissions = permissionList.toArray(new String[permissionList.size()]);
                 ActivityCompat.requestPermissions(this, permissions, 1);
             } else {
            
                 if (ActivityCollector.isActivityExist(JunjinBaoMainActivity.class)) {
                             isBackGroundChange = true;
                             // mainactivity已存在 说明是后台切换至前台
                             long currentTimeMillis = System.currentTimeMillis();
                             if (currentTimeMillis - lastBackGroundTime > backGroundIntervalTime && isCloseOpenAD != 1) {
                                 openAd();
                             } else {
                                 finish();
                             }
                         } else {
                             isBackGroundChange = false;
                             // mainactivity不存在 说明启动app
                             if (System.currentTimeMillis() - lastExitAppTime > exitAppShowAdTime && isCloseOpenAD != 1) {
                                 openAd();
                             } else {
                                 mViewBinding.container.postDelayed(new Runnable() {
                                     @Override
                                     public void run() {
                                         Intent intent = new Intent(SplashActivity.this, JunjinBaoMainActivity.class);
                                         startActivity(intent);
                                         SplashActivity.this.finish();
                                     }
                                 },2000);
                             }
                         }
             }
             