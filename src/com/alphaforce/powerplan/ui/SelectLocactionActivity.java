package com.alphaforce.powerplan.ui;

import com.alphaforce.powerplan.R;
import com.alphaforce.powerplan.common.Constants;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class SelectLocactionActivity extends Activity {
	private final static String TAG = "SelectLocactionActivity";
    private FrameLayout mFrameLayout;
    private Button mCancelBtn;
    private Button mOKBtn;

    // 定位相关
    LocationClient mLocClient;
    BDLocationListener mLocListener = new MyLocationListener();
    MapView mMapView;
    BaiduMap mBaiduMap;
    boolean isFirstLoc = true;// 是否首次定位
    LatLng locPoint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location_activity);

        mFrameLayout = (FrameLayout) findViewById(R.id.map_option);
        mOKBtn = (Button) findViewById(R.id.point_select_ok);
        mCancelBtn = (Button) findViewById(R.id.point_select_cancel);

        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);   //开启定位图层

        mCancelBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mFrameLayout.setVisibility(View.GONE);
            }
        });

        mOKBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.putExtra(Constants.LATITUDE_EXTRA,locPoint.latitude);
                it.putExtra(Constants.LONGITUDE_EXTRA, locPoint.longitude);
                setResult(Activity.RESULT_OK, it);
                finish();
            }
        });

        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(mLocListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_gcoding);

                MarkerOptions overlayOptions = new MarkerOptions()
                        .position(latLng)
                        .icon(bitmap);

                mBaiduMap.clear();
                mBaiduMap.addOverlay(overlayOptions);
                mFrameLayout.setVisibility(View.VISIBLE);

                locPoint = latLng;
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    @Override
    protected void onStop() {
        mLocClient.unRegisterLocationListener(mLocListener);
        mLocClient.stop();
        super.onStop();
    }

    class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation == null && mMapView ==null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }
    }

}
