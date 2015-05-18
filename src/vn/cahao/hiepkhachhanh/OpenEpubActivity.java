package vn.cahao.hiepkhachhanh;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import vn.cahao.hiepkhachhanh.util.ChangeCSSMenu;
import vn.cahao.hiepkhachhanh.util.EpubNavigator;
import vn.cahao.hiepkhachhanh.util.SplitPanel;
import vn.hongcuu.hongcuu.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
public class OpenEpubActivity extends SherlockFragmentActivity {
	private Button btn_raovat;
	private Button btn_chuong;
	
	public EpubNavigator navigator;
	protected static int bookSelector = 0;
	protected int panelCount;
	protected String[] settings;
	private String pathbook=null;
//	private static int indexBook = 0;
	//private int MAX_BOOKS_OPEN = 10;
	private int MAX_BOOKS_OPEN = 10;
	File rootDir = Environment.getExternalStorageDirectory();
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_openepub);
		customActionBar();
		navigator = new EpubNavigator(MAX_BOOKS_OPEN,this);
		
		panelCount = 0;		
		settings = new String[8];
		// LOADSTATE
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		loadState(preferences);
		navigator.loadViews(preferences);		
		if (panelCount == 0) {
			bookSelector = 0;
			Intent goToChooser = new Intent(this, FileChooser.class);
			startActivityForResult(goToChooser, 0);
			
		}
		
		/*
		if(pathbook!=null){
//			navigator.closeView(bookSelector);			
			navigator.openBook(pathbook,bookSelector);
			bookSelector++;
			if(bookSelector>=MAX_BOOKS_OPEN)
				bookSelector = 0;
			//setBackColor("0xff00ff");
			//setCSS();
		}
		*/
		
		
	}
	
	protected void onResume() {
		super.onResume();
		if (panelCount == 0) {
			SharedPreferences preferences = getPreferences(MODE_PRIVATE);
			navigator.loadViews(preferences);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		Editor editor = preferences.edit();
		saveState(editor);
		editor.commit();
	}

	// load the selected book
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (panelCount == 0) {
			SharedPreferences preferences = getPreferences(MODE_PRIVATE);
			navigator.loadViews(preferences);
		}

		if (resultCode == Activity.RESULT_OK) {
			String path = data.getStringExtra(getString(R.string.bpath));
			navigator.openBook(path, bookSelector);
		}
	}
	
	// ---- Panels Manager
		public void addPanel(SplitPanel p) {
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.MainLayout, p, p.getTag());
			fragmentTransaction.commit();

			panelCount++;
		}

		public void attachPanel(SplitPanel p) {
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.attach(p);
			fragmentTransaction.commit();

			panelCount++;
		}

		public void detachPanel(SplitPanel p) {
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.detach(p);
			fragmentTransaction.commit();

			panelCount--;
		}

		public void removePanelWithoutClosing(SplitPanel p) {
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.remove(p);
			fragmentTransaction.commit();

			panelCount--;
		}

		public void removePanel(SplitPanel p) {
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.remove(p);
			fragmentTransaction.commit();

			panelCount--;
			if (panelCount <= 0)
				finish();
		}

		// ----

		// ---- Language Selection
//		public void chooseLanguage(int book) {
//
//			String[] languages;
//			languages = navigator.getLanguagesBook(book);
//			if (languages.length == 2)
//				refreshLanguages(book, 0, 1);
//			else if (languages.length > 0) {
//				Bundle bundle = new Bundle();
//				bundle.putInt(getString(R.string.tome), book);
//				bundle.putStringArray(getString(R.string.lang), languages);
//
//				LanguageChooser langChooser = new LanguageChooser();
//				langChooser.setArguments(bundle);
//				langChooser.show(getFragmentManager(), "");
//			} else {
//				errorMessage(getString(R.string.error_noOtherLanguages));
//			}
//		}

		public void refreshLanguages(int book, int first, int second) {
			navigator.parallelText(book, first, second);
		}

		// ----

		// ---- Change Style
		
		public void setCSS() {
			navigator.changeCSS(bookSelector, settings);
		}

		public void setBackColor(String my_backColor) {
			settings[1] = my_backColor;
		}

		public void setColor(String my_color) {
			settings[0] = my_color;
		}

		public void setFontType(String my_fontFamily) {
			settings[2] = my_fontFamily;
		}

		public void setFontSize(String my_fontSize) {
			settings[3] = my_fontSize;
		}

		public void setLineHeight(String my_lineHeight) {
			if (my_lineHeight != null)
				settings[4] = my_lineHeight;
		}

		public void setAlign(String my_Align) {
			settings[5] = my_Align;
		}

		public void setMarginLeft(String mLeft) {
			settings[6] = mLeft;
		}

		public void setMarginRight(String mRight) {
			settings[7] = mRight;
		}

		// ----

		// change the views size, changing the weight
		public void changeViewsSize(float weight) {
			navigator.changeViewsSize(weight);
		}

		public int getHeight() {
			LinearLayout main = (LinearLayout) findViewById(R.id.MainLayout);
			return main.getMeasuredHeight();
		}

		public int getWidth() {
			LinearLayout main = (LinearLayout) findViewById(R.id.MainLayout);
			return main.getWidth();
		}

		// Save/Load State
		protected void saveState(Editor editor) {
			navigator.saveState(editor);
		}

		protected void loadState(SharedPreferences preferences) {
			if (!navigator.loadState(preferences))
				errorMessage(getString(R.string.error_cannotLoadState));
		}

		public void errorMessage(String message) {
			Context context = getApplicationContext();
			Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			toast.show();
		}	
		//=======================================
//		// ---- Menu
//		@Override
//		public boolean onCreateOptionsMenu(Menu menu) {
//			getMenuInflater().inflate(R.menu.main_epub, menu);
//			return true;
//		}
//
//		@Override
//		public boolean onPrepareOptionsMenu(Menu menu) {
//
//			if (navigator.isParallelTextOn() == false && navigator.exactlyOneBookOpen() == false) {
//				//menu.findItem(R.id.meta1).setVisible(true);
//				//menu.findItem(R.id.meta2).setVisible(true);
//				menu.findItem(R.id.toc1).setVisible(true);
//				menu.findItem(R.id.toc2).setVisible(true);
//				//menu.findItem(R.id.FirstFront).setVisible(true);
//				///menu.findItem(R.id.SecondFront).setVisible(true);
//			}
//
//			if (navigator.exactlyOneBookOpen() == false) {
//				menu.findItem(R.id.Synchronize).setVisible(true);
//				menu.findItem(R.id.Align).setVisible(true);
//				// menu.findItem(R.id.SyncScroll).setVisible(true);
//				menu.findItem(R.id.StyleBook1).setVisible(true);
//				menu.findItem(R.id.StyleBook2).setVisible(true);
//				//menu.findItem(R.id.firstAudio).setVisible(true);
//				//menu.findItem(R.id.secondAudio).setVisible(true);
//			}
//
//			if (navigator.exactlyOneBookOpen() == true || navigator.isParallelTextOn() == true) {
//				//menu.findItem(R.id.meta1).setVisible(false);
//				//menu.findItem(R.id.meta2).setVisible(false);
//				menu.findItem(R.id.toc1).setVisible(false);
//				menu.findItem(R.id.toc2).setVisible(false);
//				//menu.findItem(R.id.FirstFront).setVisible(false);
//				//menu.findItem(R.id.SecondFront).setVisible(false);
//			}
//
//			if (navigator.exactlyOneBookOpen() == true) {
//				menu.findItem(R.id.Synchronize).setVisible(false);
//				menu.findItem(R.id.Align).setVisible(false);
//				menu.findItem(R.id.SyncScroll).setVisible(false);
//				menu.findItem(R.id.StyleBook1).setVisible(false);
//				menu.findItem(R.id.StyleBook2).setVisible(false);
//				//menu.findItem(R.id.firstAudio).setVisible(false);
//				//menu.findItem(R.id.secondAudio).setVisible(false);
//			}
//
//			// if there is only one view, option "changeSizes" is not visualized
//			if (panelCount == 1)
//				menu.findItem(R.id.changeSize).setVisible(false);
//			else
//				menu.findItem(R.id.changeSize).setVisible(true);
//
//			return true;
//		}

		
//		@Override
//		public boolean onOptionsItemSelected(MenuItem item) {
//
//			switch (item.getItemId()) {
////			case R.id.FirstEPUB:
////				bookSelector = 0;
////				Intent goToChooser1 = new Intent(this, FileChooser.class);
////				goToChooser1.putExtra(getString(R.string.second),
////						getString(R.string.time));
////				startActivityForResult(goToChooser1, 0);
////				return true;
////
////			case R.id.SecondEPUB:
////				bookSelector = 1;
////				Intent goToChooser2 = new Intent(this, FileChooser.class);
////				goToChooser2.putExtra(getString(R.string.second),
////						getString(R.string.time));
////				startActivityForResult(goToChooser2, 0);
////				return true;
//
//			case R.id.PconS:
//				try {
//					boolean yes = navigator.synchronizeView(1, 0);
//					if (!yes) {
//						errorMessage(getString(R.string.error_onlyOneBookOpen));
//					}
//				} catch (Exception e) {
//					errorMessage(getString(R.string.error_cannotSynchronize));
//				}
//				return true;
//
//			case R.id.SconP:
//				try {
//					boolean ok = navigator.synchronizeView(0, 1);
//					if (!ok) {
//						errorMessage(getString(R.string.error_onlyOneBookOpen));
//					}
//				} catch (Exception e) {
//					errorMessage(getString(R.string.error_cannotSynchronize));
//				}
//				return true;
//
//			case R.id.Synchronize:
//
//				boolean sync = navigator.flipSynchronizedReadingActive();
//				if (!sync) {
//					errorMessage(getString(R.string.error_onlyOneBookOpen));
//				}
//				return true;
//
//			case R.id.Metadata:
//				if (navigator.exactlyOneBookOpen() == true || navigator.isParallelTextOn() == true) {
//					navigator.displayMetadata(0);
//				} else {
//				}
//				return true;
//
//			case R.id.meta1:
//				if (!navigator.displayMetadata(0))
//					errorMessage(getString(R.string.error_metadataNotFound));
//				return true;
//
//			case R.id.meta2:
//				if (!navigator.displayMetadata(1))
//					errorMessage(getString(R.string.error_metadataNotFound));
//				return true;
//
//			case R.id.tableOfContents:
//				if (navigator.exactlyOneBookOpen() == true || navigator.isParallelTextOn() == true)
//				{
//					//Toast.makeText(getBaseContext(), "caca", Toast.LENGTH_SHORT).show();//bookSelector
//					navigator.displayTOC(0);
//					
//				}
//				return true;
//
//			case R.id.toc1:
//				if (!navigator.displayTOC(0))
//					errorMessage(getString(R.string.error_tocNotFound));
//				return true;
//			case R.id.toc2:
//				if (navigator.displayTOC(1))
//					errorMessage(getString(R.string.error_tocNotFound));
//				return true;
//			case R.id.changeSize:
//				try {
//					DialogFragment newFragment = new SetPanelSize();
//					newFragment.show(getFragmentManager(), "");
//				} catch (Exception e) {
//					errorMessage(getString(R.string.error_cannotChangeSizes));
//				}
//				return true;
//			case R.id.Style: // work in progress...
//				try {
//					if (navigator.exactlyOneBookOpen() == true) {
//						DialogFragment newFragment = new ChangeCSSMenu();
//						newFragment.show(getFragmentManager(), "");
//						bookSelector = 0;
//					}
//				} catch (Exception e) {
//					errorMessage(getString(R.string.error_CannotChangeStyle));
//				}
//				return true;
//
//			case R.id.StyleBook1:
//				try {
//					{
//						DialogFragment newFragment = new ChangeCSSMenu();
//						newFragment.show(getFragmentManager(), "");
//						bookSelector = 0;
//					}
//				} catch (Exception e) {
//					errorMessage(getString(R.string.error_CannotChangeStyle));
//				}
//				return true;
//
//			case R.id.StyleBook2:
//				try {
//					{
//						DialogFragment newFragment = new ChangeCSSMenu();
//						newFragment.show(getFragmentManager(), "");
//						bookSelector = 1;
//					}
//				} catch (Exception e) {
//					errorMessage(getString(R.string.error_CannotChangeStyle));
//				}
//				return true;
//
//				/*
//				 * case R.id.SyncScroll: syncScrollActivated = !syncScrollActivated;
//				 * return true;
//				 */
//
//			
//			default:
//				return super.onOptionsItemSelected(item);
//			}
//		}
		
		// ----
		//======================================
		@SuppressLint({ "InlinedApi", "InflateParams" })
		private void customActionBar() {
			View customNav = LayoutInflater.from(this).inflate(R.layout.custom_actionbar_home, null);
			btn_raovat=(Button)customNav.findViewById(R.id.btn_raovat);
			btn_chuong=(Button)customNav.findViewById(R.id.btn_chuong);
			btn_chuong.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (navigator.exactlyOneBookOpen() == true || navigator.isParallelTextOn() == true)
					{
						navigator.displayTOC(0);	
					}

					
				}
			});
			
			btn_raovat.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				
					 Intent i=new Intent(getBaseContext(),ListAppTruyenActivity.class);
					 startActivity(i);
					
					//Intent i=new Intent(getBaseContext(),DocListEpubTrongMayActivity.class);
					// startActivity(i);
					/*
					Intent goToChooser1 = new Intent(getBaseContext(), FileChooser.class);
					goToChooser1.putExtra(getString(R.string.second),getString(R.string.time));
					startActivityForResult(goToChooser1, 0);
					*/
				}
			});
			
			com.actionbarsherlock.app.ActionBar.LayoutParams lp = new com.actionbarsherlock.app.ActionBar.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
					Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

			getSupportActionBar().setCustomView(customNav, lp);
			getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			getSupportActionBar().setDisplayUseLogoEnabled(false);
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			getSupportActionBar().setDisplayShowHomeEnabled(false);
			getSupportActionBar().setIcon(R.drawable.zero_dimen_icon);
		}			
	
}
