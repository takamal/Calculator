package my.application.calculator;

import java.math.BigDecimal;
//import java.text.DecimalFormat;

import android.app.Activity;//def
import android.os.Bundle;//def

import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Vibrator;

import android.content.SharedPreferences;


@SuppressWarnings("deprecation")
public class Calculator extends Activity {

	String strTemp="";
	String strResult="0";
	Integer operator=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        		super.onCreate(savedInstanceState);
        		setContentView(R.layout.main);
        		readPreferences();
	}
    
    
    public void numKeyOnClick(View v){
    			TextView sp=(TextView)this.findViewById(R.id.SubPanel);	//テキストビューのインスタンス生成
    			String strSp=sp.getText().toString();				//テキストビューの値を　ストリング型で保持　
    			if(strSp.indexOf("=") == strSp.length()-1){				
    				sp.setText(""); 								//いままでに値が入力されていなくてイコールを押されたら値の表示はなし。
    			}

    	((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(50);
    	
    	String strInKey=((Button)v).getText().toString();			//押したボタンのインスタンス生成、すかさずストリング型で保持
    	if(strInKey.equals(".")){									//押されたボタンが"."ドットだった場合、
					if(strTemp.length() == 0){								//先頭が０でしたら、”0.”を表示する。
		    			strTemp="0.";
		    		}else{
		    			if(strTemp.indexOf(".")==-1){		//先頭が０ではなかった場合。＆　最初の一行が.ドットでだった場合は
		    				strTemp=strTemp+".";	//押されたボタンの前、小数点があれば、ドットを入力する
		    			}
		    		}
    	}else{

    		//英文字キーから数字へ変換するコードを書く---------------------------------------
    		
    		switch(v.getId()){
	    		case R.id.Keypad10:
	    			strInKey="10";
	    			break;
	    			
	    		case R.id.Keypad11:
	    			strInKey="11";
	    			break;
	    			
	    		case R.id.Keypad12:
	    			strInKey="12";
	    			break;
	    		case R.id.Keypad13:
	    			strInKey="13";
	    			break;
	    		case R.id.Keypad14:
	    			strInKey="14";
	    			break;
	    		case R.id.Keypad15:
	    			strInKey="15";
	    			break;
    		}
    		//-----------------------------------
						strTemp=strTemp+strInKey;
		}
		showNumber(strTemp);
    }
    
    public void functionKeyOnClick(View v){
    	
    			((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(50);
    			
    			switch(v.getId()){
    			case R.id.KeypadAC:
    						strTemp="";
    						strResult="0";
    						operator=0;
    						((TextView)findViewById(R.id.SubPanel)).setText("");
    						break;
    			case R.id.KeypadC:
    						strTemp="";
    						break;
    			case R.id.KeypadBS:
    						if(strTemp.length()==0)return;
    						else strTemp=strTemp.substring(0,strTemp.length()-1);
    						break;
    			case R.id.KeypadCopy:
    						ClipboardManager cm=(ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    						cm.setText(((TextView)findViewById(R.id.DisplayPanel)).getText());
    						return;
    			}
    			showNumber(strTemp);
    	    }

    public void operatorKeyOnClick(View v){
   
    			((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(50);

    			TextView sp=(TextView) findViewById(R.id.SubPanel);
    			String op2=((Button)findViewById(v.getId())).getText().toString();
    			
    			if(operator!=0){
    						String op1=((Button)findViewById(operator)).getText().toString();
    	    				if(strTemp.length()>0){
    	    							sp.setText(strResult+op1+strTemp+op2);
    	    							strResult=doCalc();
    	    							showNumber(strResult);
    	    				}
    	    				else{
    	    							sp.setText(strResult+op2);
    	    				}
    			}
    			else{
    	    				if(strTemp.length()>0){
    									strResult=strTemp;
    	    				}
    	    				sp.setText(strResult+op2);
    	    	}
    	    	strTemp="";
    	    	
    	    	if(v.getId()==R.id.KeypadEq){
    	    				operator=0;
    	    	}else{
    	    				operator=v.getId();
    	    	}
    	}
    			
    private void showNumber(String strNum){
	
//    	DecimalFormat form=new DecimalFormat("#,###0");
    	String strDecimal="";
    	String strInt="";
    	String fText="";
    	
    	if(strNum.length()>0){    		
    		
    				Integer decimalPoint=strNum.indexOf(".");
    				if(decimalPoint>-1){	//小数点が入力されたか、今まであったか？
								
    							strDecimal=strNum.substring(decimalPoint);
//    							strDecimal=strDecimal.substring(1);							//少数点が入っているところを削除
								Log.d("小数点以下の値注目１：", strDecimal);
								strInt=strNum.substring(0,decimalPoint);
								Log.d("小数点より上の値２：", strInt);
    				}else{
    							strInt=strNum;
    							Log.d("小数点より上の値	３：", strInt);
    				}

    				fText=(Integer.toHexString(Integer.parseInt(strInt))) + strDecimal;
    				
    				//16進数へ変換するコードを書く---------------------------------------

//    				fText=(Integer.toHexString(Integer.parseInt(strInt)));
//    				if(decimalPoint>-1){
//    					String bText = "." + (Integer.toHexString(Integer.parseInt(strDecimal)));
//    					fText = fText + bText;
//    				}
//    				Log.d("小数点以下の値注目４：", strDecimal);

					//-----------------------------------------------    				
    				
    	}else fText="0";
    	
		((TextView)findViewById(R.id.DisplayPanel)).setText(fText);    							
    }

    private String doCalc(){
    			BigDecimal bd1=new BigDecimal(strResult);
    			BigDecimal bd2=new BigDecimal(strTemp);
    			BigDecimal result=BigDecimal.ZERO;
    			
    			switch(operator){
    			case R.id.KeypadAdd:
    						result=bd1.add(bd2);
    						break;
    			case R.id.KeypadSub:
    						result=bd1.subtract(bd2);
    						break;
    			case R.id.KeypadMulti:
    						result=bd1.multiply(bd2);
    						break;
    			case R.id.KeypadDiv:
    						if(!bd2.equals(BigDecimal.ZERO)){
    									result=bd1.divide(bd2, 12, 3);
    						}else{
    									Toast toast=Toast.makeText(this, R.string.toast_div_by_zero, 1000);
    									toast.show();
    						}
    						break;
    			}
    			
    			if(result.toString().indexOf(".")>=0){
    						return result.toString().replaceAll("\\.0+$|0+$", "");
    			}else{
    						return result.toString();
    			}
    }
    
	void readPreferences(){
  				SharedPreferences prefs=this.getSharedPreferences("CalcPrefs",MODE_PRIVATE);
  				strTemp=prefs.getString("strTemp", "");
				strResult=prefs.getString("strResult", "0");
				operator=prefs.getInt("operator", 0);
				((TextView)findViewById(R.id.DisplayPanel)).setText(prefs.getString("strDisplay","0"));
				((TextView)findViewById(R.id.SubPanel)).setText(prefs.getString("strSubDisplay","0"));
  }
  
	void writePreferences(){
				SharedPreferences prefs=getSharedPreferences("CalcPrefs", MODE_PRIVATE);
				SharedPreferences.Editor editor=prefs.edit();
				editor.putString("strTemp",strTemp);
				editor.putString("strResult",strResult);
				editor.putInt("operator",operator);
				editor.putString("strDisplay",((TextView)findViewById(R.id.DisplayPanel)).getText().toString());
				editor.putString("strSubDisplay",((TextView)findViewById(R.id.SubPanel)).getText().toString());
				editor.commit();
	}
	
	@Override
	protected void onStop(){
				super.onStop();
				writePreferences();
	}
}
  
  

    
