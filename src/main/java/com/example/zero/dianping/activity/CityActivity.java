package com.example.zero.dianping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zero.dianping.R;
import com.example.zero.dianping.entity.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zero on 16-3-13.
 */
public class CityActivity extends Activity implements View.OnClickListener{
    private ImageView backImageView,refreshImageView;
    private ListView listView;
    private View headerView;
    private List<City> cityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        backImageView=(ImageView)findViewById(R.id.city_choose_back);
        refreshImageView=(ImageView)findViewById(R.id.city_choose_refresh);
        listView=(ListView)findViewById(R.id.city_choose_listView);
        headerView= LayoutInflater.from(this).inflate(R.layout.city_choose_header,null);

        listView.addHeaderView(headerView);
        //获取城市数据列表
        getCityDate();
        if (cityList!=null&&cityList.size()>0){
            MyAdapter adapter=new MyAdapter(cityList);
            listView.setAdapter(adapter);
        }

        backImageView.setOnClickListener(this);
        refreshImageView.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                TextView name=(TextView)view.findViewById(R.id.city_list_item_name);
                intent.putExtra("cityName",name.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.city_choose_back:
                finish();
                break;
            case R.id.city_choose_refresh:
                break;
            default:
                break;
        }
    }

    private void getCityDate() {
        String cityDatas=null;
        /*
         *从接口获取城市数据
         *
         * */

        //利用本地json文件中的数据,进行json解析
        try {
            InputStream inputStream =CityActivity.this.getClass().getClassLoader().getResourceAsStream("assets/" + "city.json");
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String s;
            StringBuffer stringBuffer=new StringBuffer();
            while ((s=bufferedReader.readLine())!=null){
                    stringBuffer.append(s);
            }
            cityDatas=stringBuffer.toString();
            Log.i("cityDatas",cityDatas);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (cityDatas!=null&&!cityDatas.equals("")) {
            Gson gson = new Gson();
            cityList = gson.fromJson(cityDatas, new TypeToken<List<City>>() {
            }.getType());
        }
    }

    StringBuffer buffer=new StringBuffer();
    List<String> list=new ArrayList<String>();


    public class MyAdapter extends BaseAdapter{
        private List<City> cityDatas;
        MyAdapter(List<City> cityList){
            this.cityDatas=cityList;
        }
        @Override
        public int getCount() {
            return cityDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return cityDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.home_city_list_item,null);
                viewHolder.sortKey= (TextView) convertView.findViewById(R.id.city_list_item_key);
                viewHolder.cityName= (TextView) convertView.findViewById(R.id.city_list_item_name);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            //设置数据
            City city=cityDatas.get(position);
            String key=city.sortKey;
            String name=city.name;
            //判读是否需要隐藏首字母
            if (buffer.indexOf(key)==-1){
                buffer.append(key);
                list.add(name);
            }

            if (list.contains(name)){
                viewHolder.sortKey.setText(key);
                viewHolder.sortKey.setVisibility(View.VISIBLE);
            }else{
                viewHolder.sortKey.setVisibility(View.GONE);
            }
            viewHolder.cityName.setText(name);
            return convertView;
        }
    }
    class ViewHolder{
        TextView sortKey;
        TextView cityName;
    }
}
