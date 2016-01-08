package org.morse.main;

import org.morse.constant.Event;
import org.morse.database.DBManager;

import com.ggy.morse.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private static MainActivity mainActivity;
	private Button translate_english_button,//翻译成英文按钮
				   translate_morse_button,//翻译成摩斯码按钮
				   delete_all;
	private ButtonClickListener btListener;//按钮监听器
	private EditText morse_edittext,//摩斯码编辑框
	                 english_edittext;//英文编辑框
	
	
	/**
	 * 初始化控件
	 */
	private void initWidget(){
		translate_english_button = (Button) findViewById(R.id.translate_english);
		translate_morse_button = (Button) findViewById(R.id.translate_morse);
		delete_all = (Button) findViewById(R.id.delete_all);
		btListener = new ButtonClickListener();
		translate_english_button.setOnClickListener(btListener);
		translate_morse_button.setOnClickListener(btListener);
		delete_all.setOnClickListener(btListener);
		morse_edittext = (EditText) findViewById(R.id.english);
		english_edittext = (EditText) findViewById(R.id.morse_code);
	}
	/**
	 * 初始化
	 */
	private void init(){
		mainActivity = this;
		initWidget();
		DBManager.getInstance(getApplicationContext());
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	class ButtonClickListener implements View.OnClickListener{
		public ButtonClickListener(){
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.translate_english:
				transToEng();
				break;
			case R.id.translate_morse:
				transToMor();
				break;
			case R.id.delete_all:
				deleteAll();
			default:
				break;
			}
		}
		/**
		 * 转换成摩斯码
		 */
		private void transToMor(){
			char[] english = getEng().toCharArray();
			StringBuilder builder = new StringBuilder();
			for(char eng :english){
				
				builder.append("");
			}
//			morse_edittext.setText(builder.toString());
			morse_edittext.setText("-/./.../-");
			handler.sendEmptyMessage(Event.EVENT_REFRESH_MORSE_EDIT);
		}
		/**
		 * 装换成英文
		 */
		private void transToEng(){
			String[] morses = getMor().split("/");
			StringBuilder builder = new StringBuilder();
			for(String morse : morses){
				builder.append("");
			}
//			english_edittext.setText(builder.toString());
			english_edittext.setText("test");
			handler.sendEmptyMessage(Event.EVENT_REFRESH_ENGLISH_EDIT);
		}
		/**
		 * 取得英文编辑框内容
		 * @return
		 */
		private String getEng(){
			String text = english_edittext.getText().toString();
			if (TextUtils.isEmpty(text)) {
				return "";
			}else {
				return text;
			}
			
		}
		/**
		 * 取得摩斯码编辑框内容
		 * @return
		 */
		private String getMor(){
			String text = morse_edittext.getText().toString();
			if (TextUtils.isEmpty(text)) {
				return "";
			}else {
				return text;
			}
		}
		private void deleteAll(){
			english_edittext.setText("");
			morse_edittext.setText("");
			handler.sendEmptyMessage(Event.EVENT_DELETE_ALL);
		}
	}
	static Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Event.EVENT_REFRESH_ENGLISH_EDIT:
				mainActivity.english_edittext.invalidate();
				break;
			case Event.EVENT_REFRESH_MORSE_EDIT:
				mainActivity.morse_edittext.invalidate();
				break;
			case Event.EVENT_DELETE_ALL:
				mainActivity.english_edittext.invalidate();
				mainActivity.morse_edittext.invalidate();
			default:
				break;
			}
		}
		
	};
}
