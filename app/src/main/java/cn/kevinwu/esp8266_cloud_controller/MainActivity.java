package cn.kevinwu.esp8266_cloud_controller;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import cn.kevinwu.esp8266_cloud_controller.config.SPStaticKey;
import cn.kevinwu.esp8266_cloud_controller.ui.MainControllerFG;
import cn.kevinwu.esp8266_cloud_controller.util.common.SPUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new MainControllerFG()).commit();
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            editChipIdDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void editChipIdDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_edit_chipid,
                null);
        final EditText et = (EditText) view.findViewById(R.id.editText);
        final EditText et2 = (EditText) view.findViewById(R.id.editText2);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("设置设备id")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strChipid=et.getText().toString();
                        String strAppid=et2.getText().toString();
                        SPUtil.put(MyApplication.AppContext, SPStaticKey.PLUS_SP_FILE,SPStaticKey.CHIPID,strChipid);
                        SPUtil.put(MyApplication.AppContext, SPStaticKey.PLUS_SP_FILE,SPStaticKey.APPID,strAppid);

                    }
                }).setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.setView(view);
        dialog.show();
    }
}

