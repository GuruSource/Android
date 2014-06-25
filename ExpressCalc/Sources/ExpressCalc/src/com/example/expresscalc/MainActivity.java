package com.example.expresscalc;


import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	EditText n1;
	TextView t1;
	TextView m[] = new TextView[8];
	
	public static int iCount=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		//begin add guru
		n1=(EditText)findViewById(R.id.etInput);
		t1=(TextView)findViewById(R.id.tvOutput);
		m[0]= (TextView)findViewById(R.id.tvM1);
		m[1]= (TextView)findViewById(R.id.tvM2);
		m[2]= (TextView)findViewById(R.id.tvM3);
		m[3]= (TextView)findViewById(R.id.tvM4);
		m[4]= (TextView)findViewById(R.id.tvM5);
		m[5]= (TextView)findViewById(R.id.tvM6);
		m[6]= (TextView)findViewById(R.id.tvM7);
		m[7]= (TextView)findViewById(R.id.tvM8);
		
		//end add guru
		   n1.addTextChangedListener(mTextEditorWatcher);
		   
		   //n1.setInputType(InputType.TYPE_NULL);
		  // n1.setTextIsSelectable(true);
		   n1.setOnTouchListener(new OnTouchListener() {

		        @Override
		        public boolean onTouch(View v, MotionEvent event) {
		            v.onTouchEvent(event);
		            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		            if (imm != null) {
		                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		            }             
		           
		            return true;
		        }

		    });
		   
	}

	 // EditTextWacther  Implementation
	 
    private final TextWatcher  mTextEditorWatcher = new TextWatcher() {
        
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
                    // When No Password Entered
                 //  t1.setText("Not Entered");
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        	//t1.setText("Hmmmmmm");
        }

        public void afterTextChanged(Editable s)
        {
        	calc a = new calc();
        	try
        	{
        		//t1.setText(n1.getText());
        	t1.setText(a.findValueInBraces(n1.getText().toString()));
        	}
        	catch(Exception e){
        		t1.setText("");
        	}
        	
 
        }

	
		
			
		};
		
		public void Clear(View v){

			n1.setText("");
		}


public void onClicker(View v)
{
	switch(v.getId())
    {
    case R.id.b0 :
    	n1.setText(n1.getText()+ "0");
    	break;
    case R.id.b1 :
    	n1.setText(n1.getText()+ "1");
    	break;
    case R.id.b2 :
    	n1.setText(n1.getText()+ "2");
    	break;
    case R.id.b3 :
    	n1.setText(n1.getText()+ "3");
    	break;
    case R.id.b4 :
    	n1.setText(n1.getText()+ "4");
    	break;
    case R.id.b5 :
    	n1.setText(n1.getText()+ "5");
    	break;
    case R.id.b6 :
    	n1.setText(n1.getText()+ "6");
    	break;
    case R.id.b7 :
    	n1.setText(n1.getText()+ "7");
    	break;
    case R.id.b8 :
    	n1.setText(n1.getText()+ "8");
    	break;
    case R.id.b9 :
    	n1.setText(n1.getText()+ "9");
    	break;
    
    case R.id.bAdd :
    	n1.setText(n1.getText()+ "+");
    	break;
    case R.id.bSub :
    	n1.setText(n1.getText()+ "-");
    	break;
    case R.id.bMul :
    	n1.setText(n1.getText()+ "*");
    	break;
    case R.id.bDiv :
    	n1.setText(n1.getText()+ "/");
    	break;
    case R.id.bDot :
    	n1.setText(n1.getText()+ ".");
    	break;
    case R.id.bDel :
    	if ( n1.getSelectionStart() != n1.getSelectionEnd())
    	n1.setText(n1.getText().toString().substring(0, n1.getSelectionStart()) + n1.getText().toString().substring(n1.getSelectionEnd(), n1.getText().toString().length()) );
    	if ( n1.getSelectionStart() !=0 )
    	n1.setText(n1.getText().toString().substring(0, n1.getText().toString().length()-1));
    	
    	break;
    case R.id.bBrackOpen :
    	n1.setText(n1.getText() + "(");
    	break;
    case R.id.bBrackClose :
    	n1.setText(n1.getText() + ")");
    	break;
    case R.id.bClear :
    	n1.setText("");
    	break;
    case R.id.tvOutput :
    /*	if (n1.getText().length()!=0)
    	{
    	m[iCount++].setText(t1.getText().toString());
    	if (iCount == 8 )
    			iCount=0;
    	break;
    	}
    	*/
    	if ((t1.getText().length()!=0))
    	{
    		if (iCount==0)
    	m[iCount++].setText(t1.getText().toString());
    		else
    			if ((!(t1.getText().toString().equals(m[iCount-1].getText().toString()))) )
    				m[iCount++].setText(t1.getText().toString());
    				if (iCount == 8 )
    			iCount=0;
        	break;
        }
    	break;
    	
   
case R.id.bM:
	if ((n1.getText().length()!=0))
	{
		if (iCount==0)
	m[iCount++].setText(n1.getText().toString());
		else
			if ((!(n1.getText().toString().equals(m[iCount-1].getText().toString()))) )
				m[iCount++].setText(n1.getText().toString());
				if (iCount == 8 )
			iCount=0;
    	break;
    }
	break;
case R.id.bC:
	for (iCount=0;iCount<=7;iCount++)
	{
		m[iCount].setText("");
	}
		iCount=0;
	break;
	
case R.id.tvM1 :
	n1.setText(n1.getText() +  m[0].getText().toString());
	break;
case R.id.tvM2 :
	n1.setText(n1.getText() +  m[1].getText().toString());
	break;
case R.id.tvM3 :
	n1.setText(n1.getText() +  m[2].getText().toString());
	break;
case R.id.tvM4 :
	n1.setText(n1.getText() +  m[3].getText().toString());
	break;
case R.id.tvM5 :
	n1.setText(n1.getText() +  m[4].getText().toString());
	break;
case R.id.tvM6 :
	n1.setText(n1.getText() +  m[5].getText().toString());
	break;
case R.id.tvM7 :
	n1.setText(n1.getText() +  m[6].getText().toString());
	break;
case R.id.tvM8 :
	n1.setText(n1.getText() +  m[7].getText().toString());
	break;

	
    }
	 n1.setSelection(n1.getText().length());
    	
}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
